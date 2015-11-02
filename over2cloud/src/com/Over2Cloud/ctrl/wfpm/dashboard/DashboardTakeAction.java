package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.loader.custom.Return;
import org.hibernate.SessionFactory;

import net.sf.json.JSONArray;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.VAM.visitor.MailText;
import com.Over2Cloud.ctrl.wfpm.achievement.AchievementHelper;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.lead.LeadActionControlDao;
import com.Over2Cloud.ctrl.wfpm.report.mail.DailyActivityReminderMailHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;

/**
 * @author Anoop This action class is to be used from WFPM dashboard on taking
 *         action
 */
public class DashboardTakeAction extends DashboardTakeActionPropertiesContainer
{
	String	accountID		= (String) session.get("accountid");
	String	empIdofuser	= session.get("empIdofuser").toString().split("-")[1];	// o
	private String page;
	public String beforeDashboardTakeActon()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// //System.out.println("11111111111111111111111111111111111111mmmmmmmm");
				DashboardTakeActionHelper dtah = new DashboardTakeActionHelper();
				
				//dtah.fetchIdData(connectionSpace, id, activityType);
				jsonObject = dtah.fetchTakeActionPageData(connectionSpace, id, activityType);
				//System.out.println("nbnbnb  "+dtah.fetchIdData(connectionSpace, id, activityType)); 
				String refId = dtah.fetchIdData(connectionSpace, id, activityType);
				String refBy = dtah.fetchRefById(refId);
				referedby = refBy.split("#")[0];
				referedbyID = refBy.split("#")[1];
				referenceContactMap = new LinkedHashMap<String, String>();
				if(!referedby.equalsIgnoreCase("NA") && !referedbyID.equalsIgnoreCase("NA"))
					{
					referenceContactMap = dtah.fetchAllRefContact(referedby, referedbyID);
					}
			
				//referenceContactDetails = dtah.fetchAllRefContactDetails(referedby, referedbyID);
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeCloseTakeActon()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// //System.out.println("2222222222222222222222222222mmmmmmmm");
				ClientHelper ch = new ClientHelper();
				clientStatus = new LinkedHashMap<String, String>();
				clientStatus = ch.fetchClientStatus(connectionSpace, activityType);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String dashboardAction()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// //System.out.println("33333333333333333333mmmmmmmm");
				DashboardTakeActionHelper dtah = new DashboardTakeActionHelper();
				/****************** Client ********************************************************/
				// fetch client id for auto achievement
				String organizationId = dtah.fetchOrganizationIdByContactId(connectionSpace, contactId, activityType);

				if (actionType == ActionType.ACTIVITY)
				{
					if (closeType == CloseType.NEXT_ACTIVITY)
					{
						boolean flag = false;
						if (activityType == ActivityType.client)
						{
							flag = new ClientHelper().createTableClientTakeAction(connectionSpace);
						}
						else if (activityType == ActivityType.associate)
						{
							flag = new AssociateHelper().createTableAssociateTakeAction(connectionSpace);
						}

						if (flag)
						{
							// //System.out.println("CloseType.nextActivity");
							// //System.out.println("statusId:" + statusId);
							System.out.println("chkmail_to_contact out if:" + chkmail_to_contact);
							if(chkmail_to_contact == null || chkmail_to_contact.equalsIgnoreCase("false"))
							{
								chkmail_to_contact = "0";
							}else{
								//System.out.println("chkmail_to_contact out if:" + chkmail_to_contact+" personName "+personName+" referedby "+referedby+" referedbyID "+referedbyID);
							}
							if(personName != null && !personName.equalsIgnoreCase("") && !personName.equalsIgnoreCase("-1"))
							{
								String referenceContactDetails = dtah.fetchAllRefContactDetails(referedby, referedbyID, personName);
								// add mails to reference contacts.
								 if(referenceContactDetails.split("#")[1] != null && !referenceContactDetails.split("#")[1].equals("") && !referenceContactDetails.split("#")[1].equals("NA"))
					                {
									 	String statusIdText = dtah.fetchClientStatusName(statusIdNew);
					                    boolean mailstatus = getMailText(personName, referenceContactDetails.split("#")[1],maxDateTimeOld,currentStatus,clientName, offeringName,clientAddress, statusIdText, contactId, offeringId, connectionSpace);
					                }
							}
							
							
							
							boolean statusFlag = dtah.saveTakeActionData(false, organizationId, connectionSpace, activityType, contactId, offeringId, statusIdNew,
									maxDateTime, comment, cId);
							if (statusFlag)
							{
								boolean status = dtah.closeActivity(connectionSpace, activityType, id, "Activity Closed", EntityTakeActionIsFinishedType.CLOSE, "id");
								// //System.out.println("status::::::::::::" + status);
								addActionMessage("Activity created successfully.");
							}
							else
							{
								addActionMessage("ERROR: There is some error in data !");
							}
						}
					} /*
						 * else if (closeType == CloseType.CLOSE_ACTIVITY) {
						 * ////System.out.println("CloseType.CLOSE_ACTIVITY"); boolean
						 * statusFlag = dtah.closeActivity( connectionSpace, activityType,
						 * id, comment, EntityTakeActionIsFinishedType.CLOSE); if
						 * (statusFlag) { addActionMessage("Activity closed successfully.");
						 * } else { addActionMessage("ERROR: There is some error !"); } }
						 */
					else
					{
						// //System.out.println("closeType: else");
					}
				}
				else if (actionType == ActionType.RESCHEDULE)
				{
					// //System.out.println("ActionType.reschedule");
					boolean statusFlag = dtah.rescheduleActivity(organizationId, connectionSpace, activityType, id, contactId, offeringId, statusId, maxDateTime,
							comment, cId);
					if (statusFlag)
					{
						addActionMessage("Activity created successfully.");
					}
					else
					{
						addActionMessage("ERROR: There is some error in data !");
					}
				}
				else if (actionType == ActionType.LOST)
				{
					// //System.out.println("CloseType.convertToExisting");
					boolean statusFlag = dtah.convertToLost(connectionSpace, activityType, RCA, CAPA, clientId, offeringId, lostId);
					if (statusFlag)
					{
						addActionMessage("Converted to lost successfully.");
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
					}
					
					// generate email to asociate..
					String clientRefData = new ClientHelper().fetchRefrenceData(clientId, connectionSpace);
					String clientData = new ClientHelper().fetchClientOfferingName(clientId,offeringId,connectionSpace);
					System.out.println("clientRefData "+clientRefData+"clientData "+clientData);
					String mailbody =null;
					if(clientRefData != null && clientData != null )
					{
						mailbody = "Dear "+clientRefData.split("#")[0]+", <br> Activity convert to lost for client "+clientData.split("#")[1]+" for offering "+clientData.split("#")[0]+" is done.";
					
					
					StringBuffer bufferobj = new StringBuffer();
					bufferobj.append("<html>");
					bufferobj.append("<body>");
					bufferobj.append(mailbody);
					bufferobj.append("</body>");
					bufferobj.append("</html>");
					//System.out.println("mailbody lost  "+mailbody);
					 new MsgMailCommunication().addMail(clientRefData.split("#")[0], "", clientRefData.split("#")[1], "Activity", bufferobj.toString(), "", "Pending", "0", "", "WFPM");
					}
				}
				else if (actionType == ActionType.REASSIGN)
				{
					//System.out.println("ActionType.REASSIGN");
					boolean statusFlag = dtah.reassignActivity(organizationId, connectionSpace, activityType, id, contactId, offeringId, statusId, maxDateTimeOld,
							comment, empId);
					if (statusFlag)
					{
						addActionMessage("Activity created successfully.");
					}
					else
					{
						addActionMessage("ERROR: There is some error in data !");
					}
				}
				else if (actionType == ActionType.CONVERT_TO_EXISTING)
				{
					String renameFilePath = null;
					File theFile = null;
					// For Auto Fill KPI Offering-1 ******************************
					LeadActionControlDao lacd = new LeadActionControlDao();
					lacd.createKPIAutofillTable(accountID, connectionSpace);
					lacd.insertInToKPIAutofillTable(empIdofuser, "8", userName, connectionSpace, clientId, "prospective Client");
					// //System.out.println("CloseType.convertToExisting");
					if (getPo_attachFileName() != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Activity") + "//" + DateUtil.mergeDateTime() + getPo_attachFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Activity") + "//" + getPo_attachFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(getPo_attach(), theFile);
									if (theFile.exists()) theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
					}

					//System.out.println("contactId:" + contactId + " ================== offeringId:" + offeringId);

					boolean statusFlag = dtah.convertToExisting(connectionSpace, activityType, poNumber, DateUtil.convertDateToUSFormat(poDate), clientId, offeringId,
							ammount, renameFilePath, comments, contactId);

					if (statusFlag)
					{
						if (activityType == ActivityType.client)
						{
							CommonOperInterface cbt = new CommonConFactory().createInterface();
							AchievementHelper AH = new AchievementHelper();
							CommonHelper CH = new CommonHelper();
							String level[] = CH.getOfferingName();
							String query = "SELECT id FROM client_offering_mapping WHERE clientName='" + clientId + "' AND offeringId='" + offeringId
									+ "' ORDER BY convertDate DESC LIMIT 0,1";
							List id = cbt.executeAllSelectQuery(query, connectionSpace);

							if (id != null && !id.get(0).equals("")) AH.addAchievementForOffering(empIdofuser, offeringId, ammount, DateUtil.getCurrentDateUSFormat(),
									level[2], userName, id.get(0).toString());
							
						}
						// generate email to associate.....
						
						String clientRefData = new ClientHelper().fetchRefrenceData(clientId, connectionSpace);
						String clientData = new ClientHelper().fetchClientOfferingName(clientId,offeringId,connectionSpace);
						String mailbody = "Dear "+clientRefData.split("#")[0]+", <br> Activity convert to existing for client "+clientData.split("#")[1]+" for offering "+clientData.split("#")[0]+" is done.";
						StringBuffer bufferobj = new StringBuffer();
						bufferobj.append("<html>");
						bufferobj.append("<body>");
						bufferobj.append(mailbody);
						bufferobj.append("</body>");
						bufferobj.append("</html>");
						new MsgMailCommunication().addMail(clientRefData.split("#")[0], "", clientRefData.split("#")[1], "Activity", bufferobj.toString(), "", "Pending", "0", "", "WFPM");
						//System.out.println("mailbody existing  "+mailbody);
						addActionMessage("Converted to existing successfully.");
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
					}
					
					
				}
				else
				{
					// //System.out.println("actionType: else");
				}

				/****************** More activities from take action **********************************************************/
				// //System.out.println("chkOther:" + chkOther);
				if (chkOther != null && !chkOther.equals(""))
				{
					int counter = dtah.moreAction(organizationId, connectionSpace, activityType, chkOther, statusIdOther, offeringIdOther, maxDateTimeOther,
							commentOther, contactId, userName);
					// //System.out.println("counter:" + counter);
				}

				/***************** MOM *******************************************************************************/
				// //System.out.println("############" + dateMOM);
				// //System.out.println("############" + fromTimeMOM);
				// //System.out.println("############" + toTimeMO);
				// //System.out.println("############" + agendaMOM);
				// //System.out.println("############" + clientContactMOM);
				// //System.out.println("############" + employeeMOM);
				// //System.out.println("############" + discussionMOM);
				// //System.out.println("############" + futureActionMOM);
				String momId = dtah.saveMOM(connectionSpace, activityType, dateMOM, fromTimeMOM, toTimeMO, agendaMOM, String.valueOf(id), userName);
				if (momId != null)
				{
					int count;
					if (clientContactMOM != null && !clientContactMOM.equals(""))
					{
						count = dtah.saveMOMEntityContact(connectionSpace, clientContactMOM, momId);
						// //System.out.println("count:" + count);
					}
					if (employeeMOM != null && !employeeMOM.equals(""))
					{
						count = dtah.saveMOMEmployee(connectionSpace, employeeMOM, momId);
						// //System.out.println("count:" + count);
					}
					if (discussionMOM != null && !discussionMOM.equals(""))
					{
						count = dtah.saveMOMDiscussion(connectionSpace, discussionMOM, momId);
						// //System.out.println("count:" + count);
					}
					if (futureActionMOM != null && !futureActionMOM.equals(""))
					{
						count = dtah.saveMOMFutureAction(connectionSpace, futureActionMOM, momId);
						// //System.out.println("count:" + count);
					}
				}

				/************************************* Attach Doc *******************************************************/
				// Pending
				if (attachedDoc != null)
				{
					// //System.out.println("attachedDoc:" + attachedDoc);
					// //System.out.println("attachedDocFileName:" + attachedDocFileName);
					// //System.out.println("attachedDocContentType:" +
					// attachedDocContentType);
					String filePath = new CreateFolderOs().createUserDir("WFPM_MOM");
					String fileName = filePath + "/mom_" + DateUtil.getCurrentDateIndianFormat().replaceAll("-", "_") + "_"
							+ DateUtil.getCurrentTime().replaceAll(":", "_") + "_" + attachedDocFileName;
					// //System.out.println("fileName:" + fileName);
					File fileToCreate = new File(fileName);
					FileUtils.copyFile(attachedDoc, fileToCreate);

					boolean flag = dtah.saveMOMFilePathToDb(connectionSpace, fileName, String.valueOf(id));
					// Save file path to database saveMOMFilePathToDb
				}

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		// addActionMessage("Successfull");
		return returnValue;
	}

	public String showActivityBlock()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				DashboardTakeActionHelper dh = new DashboardTakeActionHelper();
				statusMap = dh.fetchEntityStatus(connectionSpace, activityType);
				offeringMap = dh.fetchOfferingForActivity(connectionSpace, offeringId.trim());

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeDashboard()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonArray = new DashboardMOMHelper().fetchEntityContactPersonByCTAId(connectionSpace, activityType, id);
				EmployeeHelper<JSONArray> ch = new EmployeeHelper<JSONArray>();
				jsonArrayOther = ch.fetchEmployee(EmployeeReturnType.JSONARRAY, TargetType.OFFERING);
				jsonObject = new DashboardMOMHelper().fetchEntityNameAndAddress(connectionSpace, activityType, id);
				if(getPage()!=null && getPage().equalsIgnoreCase("full"))
				{
					returnValue = "fsuccess";
				}
				else{
					returnValue = SUCCESS;	
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchMissedActivities()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			missedActivityList = new DashboardHelper().fetchMissedActivities();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public boolean getMailText(String personName, String email,String maxDateTimeOld,String currentStatus,String clientName, String offeringName, String clientAddress,String statusIdText,String contactId,String offeringId, SessionFactory connectionSpace)
	{
		boolean status = false;
		try {
			DailyActivityReminderMailHelper damh = new DailyActivityReminderMailHelper();
			String subject = null;
			StringBuilder mailtext = new StringBuilder();
			mailtext.append("<FONT face='sans-serif'>");
				// *****************************************************************
				String subjectBody = "Activity Update for "+clientName+" as on "+DateUtil.getCurrentDateIndianFormat();
				subject = subjectBody;
				mailtext.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
				mailtext.append("<TR><TD><b>Dear ");
				mailtext.append(personName);
				mailtext.append(",</b></TD></TR>");
				mailtext.append("<TR><TD>&nbsp;</TD></TR>");
				mailtext.append("<TR><TD><b>Hello!!!</b></TD></TR>");
				mailtext.append("<TR><TD>&nbsp;</TD></TR>");
				mailtext.append("<TR><TD>Here is activity update for valuable reference provided by you.</TD></TR>");
				mailtext.append("<TR><TD>&nbsp;</TD></TR>");
				mailtext.append("<TR><TD align='Center' bgcolor='#CDC9C9'><b>");
				mailtext.append(subjectBody);
				mailtext.append("</b></TD></TR>");
				mailtext.append("</TABLE>");
				mailtext.append("<BR><BR>");
				
				// activity details to reference person.
				mailtext.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtext.append("<tr bgcolor='#CDC9C9' align='center'><td><b>Opportunity Name</b></td><td><b>Address</b></td><td><b>For Offering </b></td></tr>");
				mailtext.append("<TR bgcolor='#F3F2F2'>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(clientName);
				mailtext.append("</TD>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(clientAddress);
				mailtext.append("</TD>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(offeringName);
				mailtext.append("</TD>");
				mailtext.append("</TABLE>");
				mailtext.append("<BR><BR>");
				
				mailtext.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
				mailtext.append("<TR><TD align='Center' bgcolor='#CDC9C9'><b>");
				mailtext.append("Activity Brief");
				mailtext.append("</b></TD></TR>");
				mailtext.append("<TR><TD>&nbsp;</TD></TR>");
				mailtext.append("</TABLE>");
				mailtext.append("<BR>");
				
				mailtext.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtext.append("<tr bgcolor='#CDC9C9' align='center'><td><b> Date</b></td><td><b> Activity</b></td><td><b>Comment</b></td></tr>");
				
				mailtext.append("<TR bgcolor='#F3F2F2' align='center'>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(maxDateTime);
				mailtext.append("</TD>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(statusIdText);
				mailtext.append("</TD>");
				mailtext.append("<TD align='Center'>");
				mailtext.append(comment);
				mailtext.append("</TD>");
				mailtext.append("</TR>");
				
				System.out.println("contactId "+contactId+"  offeringId "+offeringId);
				ArrayList<ArrayList<String>> dailyActivityRemList = damh.activityMailDataForRefContact(cId,maxDateTime,contactId,offeringId, connectionSpace);
				if (dailyActivityRemList != null && !dailyActivityRemList.isEmpty())
				{
					for (ArrayList<String> list : dailyActivityRemList)
					{
						mailtext.append("<TR bgcolor='#F3F2F2' align='center'>");
						for (String data : list)
						{
							mailtext.append("<TD align='Center'>");
							mailtext.append(CommonHelper.getFieldValue(data));
							mailtext.append("</TD>");
						}
						mailtext.append("</TR>");
					}
				}
				else
				{
					mailtext.append("<TR><TD colspan='4' align='Center'  bgcolor='#F3F2F2'>NA</TD></TR>");
				}
				
				
				mailtext.append("</TABLE>");
				mailtext.append("<BR><BR>");
				
				mailtext.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
				mailtext.append("<TR><TD>Kindly find the MOM (if any) with the mail.</TD></TR>");
				mailtext.append("<TR><TD>We thank you for your valuable support. We will keep you posted.</TD></TR>");
				mailtext.append("<TR><TD>&nbsp;</TD></TR>");
				mailtext.append("</TABLE>");
				mailtext.append("<BR><BR>");
				
				mailtext.append("</TABLE>");
				mailtext.append("<BR><BR>");
			
		
		
				mailtext.append("<br><br>");
				mailtext.append("<div class='gmail_default'><b style='color:rgb(0,0,0);font-family:arial,sans-serif'>Thanks &amp; Regards,<br>");
		
				mailtext
						.append("WFPM Application Team<br></b><span style='color:rgb(0,0,0);font-family:arial,sans-serif'>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>---------------------------</span><br style='color:rgb(0,0,0);font-family:arial,sans-serif'>");
		
				mailtext
						.append("<font face='TIMESROMAN' size='1' style='color:rgb(0,0,0)'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then&nbsp;<br>");
		
				mailtext
						.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over '<a href='http://www.dreamsol.biz/contact_us.html' style='text-decoration:none' target='_blank'>http://www.dreamsol.biz/<wbr>contact_us.html</a>' or you may kindly mail your feedback to&nbsp;<br>");
		
				mailtext
						.append("<a href='mailto:support@dreamsol.biz' style='text-decoration:none' target='_blank'>support@dreamsol.biz</a></font><font face='arial, sans-serif'><span style='font-size:14px'><br></span></font></div>");
		
				mailtext.append("</FONT>");
				System.out.println(">mailtext>>"+mailtext);
				CommunicationHelper CH = new CommunicationHelper();
				status = CH.addMail(personName, "dept", email, subject, mailtext.toString(), "NA", "Pending", "0", "", "WFPM", connectionSpace);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
		return status;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}
}
