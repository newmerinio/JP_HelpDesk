package com.Over2Cloud.assetservice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class AssetMonitoringHelper
{

	@SuppressWarnings("unchecked")
	public void getMonitoringData(SessionFactory connection, CommunicationHelper CH, HelpdeskUniversalHelper HUH)
	{
		try
		{
			String ip = "NA", actualSize = "NA", currentsize = "NA", processList = "NA", temp_file_size = "NA", assetName = "NA", allotto, mobno = "NA", emailid = "NA", id = "NA", assetid = "NA";
			boolean tempStatus = false, ramStatus = false;

			List monitoringList = getDetail(connection);
			//System.out.println("List Size is  " + monitoringList.size());
			if (monitoringList != null && monitoringList.size() > 0)
			{
				//System.out.println("Inside if");
				for (Iterator iterator = monitoringList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						ip = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						actualSize = object[1].toString().trim();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						currentsize = object[2].toString().trim();
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						processList = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						temp_file_size = object[4].toString().trim();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						assetName = object[5].toString();
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						allotto = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						mobno = object[7].toString();
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						emailid = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						id = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						assetid = object[10].toString();
					}

					/*
					 * Code for Temp Memory Size
					 */
					
					String first_msg = "";
					String second_msg = "";
					tempStatus = getGreaterThan(temp_file_size, "25.00");
					if (tempStatus)
					{
						first_msg = "Dear Mr. Prabhat, Kindly take action for "+assetName+", "+ip+" as it is observed that temporary file size is increased from 25 MB, it's "+temp_file_size+" MB now.";
						second_msg = "Dear Mr. Shailendra, Kindly take action for "+assetName+", "+ip+" as it is observed that temporary file size is increased from 25 MB, it's "+temp_file_size+" MB now.";

						try
						{
							String query = "select id,date,time from machine_complaint where machine_table_id='" + id + "' and complaint_for='Temp Size'";
							List assetComplaintData = new createTable().executeAllSelectQuery(query, connection);
							if (assetComplaintData != null && assetComplaintData.size() > 0)
							{
								String updatedid = "", updatedDate = "", updatedTime = "";
								//System.out.println("Inside If ");
								for (Iterator iterator2 = assetComplaintData.iterator(); iterator2.hasNext();)
								{
									Object[] object2 = (Object[]) iterator2.next();
									updatedid = object2[0].toString();
									updatedDate = object2[1].toString();
									updatedTime = object2[2].toString();
								}

								String timedifference = DateUtil.time_difference(updatedDate, updatedTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
								boolean difference = getGreaterThan(timedifference.replace(":", "."), "00.31");
								if (difference)
								{
									String updateQry = "update machine_complaint set date='" + DateUtil.getCurrentDateUSFormat() + "',time='" + DateUtil.getCurrentTimeHourMin() + "' where id='" + updatedid + "'";
									boolean updateFlag = HUH.updateData(updateQry, connection);
									if (updateFlag)
									{
										CH.addMessage("Prabhat", "", "9811841099", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								}
							} else
							{
								//System.out.println("Inside Else ");
								try
								{
									String updateQry = "insert into machine_complaint(machine_table_id,date,time,complaint_for) values ('" + id + "','" + DateUtil.getCurrentDateUSFormat() + "','" + DateUtil.getCurrentTimeHourMin() + "','Temp Size')";
									boolean insertFlag = HUH.updateData(updateQry, connection);
									if (insertFlag)
									{
										CH.addMessage("Prabhat", "", "8860411723", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								} catch (Exception e)
								{
									e.printStackTrace();
								}

							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}

					/*
					 * Code for RAM Size
					 */

					int modifiedRAMSize = (Float.valueOf(actualSize).intValue() - 1);
					ramStatus = getGreaterThan(currentsize, String.valueOf(modifiedRAMSize));
					if (!ramStatus)
					 {
						first_msg = "Dear Mr. Prabhat, Kindly take action for "+assetName+", "+ip+" as it is observed that RAM size is decreased from "+actualSize+" GB, it's "+currentsize+" GB now.";
						second_msg = "Dear Mr. Shailendra, Kindly take action for "+assetName+", "+ip+" as it is observed that RAM size is decreased from "+actualSize+" GB, it's "+currentsize+" GB now.";

						try
						 {
							String query = "select id,date,time from machine_complaint where machine_table_id='" + id + "' and complaint_for='RAM Size'";
							List assetComplaintData = new createTable().executeAllSelectQuery(query, connection);
							if (assetComplaintData != null && assetComplaintData.size() > 0)
							{
								String updatedid = "", updatedDate = "", updatedTime = "";
								//System.out.println("Inside If ");
								for (Iterator iterator2 = assetComplaintData.iterator(); iterator2.hasNext();)
								{
									Object[] object2 = (Object[]) iterator2.next();
									updatedid = object2[0].toString();
									updatedDate = object2[1].toString();
									updatedTime = object2[2].toString();
								}

								String timedifference = DateUtil.time_difference(updatedDate, updatedTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
								boolean difference = getGreaterThan(timedifference.replace(":", "."), "00.31");
								if (difference)
								{
									String updateQry = "update machine_complaint set date='" + DateUtil.getCurrentDateUSFormat() + "',time='" + DateUtil.getCurrentTimeHourMin() + "' where id='" + updatedid + "'";
									boolean updateFlag = HUH.updateData(updateQry, connection);
									if (updateFlag)
									{
										CH.addMessage("Prabhat", "", "9811841099", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								}
							} else
							{
							//	System.out.println("Inside Else ");
								try
								{
									String updateQry = "insert into machine_complaint(machine_table_id,date,time,complaint_for) values ('" + id + "','" + DateUtil.getCurrentDateUSFormat() + "','" + DateUtil.getCurrentTimeHourMin() + "','RAM Size')";
									boolean insertFlag = HUH.updateData(updateQry, connection);
									if (insertFlag)
									{
										CH.addMessage("Prabhat", "", "9811841099", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								} catch (Exception e)
								{
									e.printStackTrace();
								}

							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}

					}

					/*
					 * Code for ProcessList
					 */

					String restrictedData = getRestrictedExeData(assetid, connection);
					List restrictedList = new ArrayList();
					if (restrictedData != null && restrictedData.length() > 0)
					{
						System.out.println("String Value is    "+restrictedData);
						String[] restrictedarr = restrictedData.split(",");
						System.out.println("Array Size is   "+restrictedarr.length);
						if (restrictedarr != null && restrictedarr.length > 0)
						{
							for (int i = 0; i < restrictedarr.length; i++)
							{
								System.out.println("Inside For "+i+"   Existing ProcessList   "+processList+"     >>>>>>>>>>>>   "+restrictedarr[i].toString());
								if (processList.contains(restrictedarr[i].toString()))
								{
									System.out.println("Inside   ");
									restrictedList.add(restrictedarr[i].toString());
								}
							}
						}
					}
					if (restrictedList != null && restrictedList.size() > 0)
					 {
						System.out.println("Inside If For Id  "+id);
						first_msg = "Dear Mr. Prabhat, Kindly take action for "+assetName+", "+ip+" as it is observed that restricted services "+restrictedList.toString().replace("[", "").replace("]", "")+" are found as running now.";
						second_msg = "Dear Mr. Shailendra, Kindly take action for "+assetName+", "+ip+" as it is observed that restricted services "+restrictedList.toString().replace("[", "").replace("]", "")+" are found as running now.";
						try
						{
							String query = "select id,date,time from machine_complaint where machine_table_id='" + id + "' and complaint_for='Restricted Exe'";
							List assetComplaintData = new createTable().executeAllSelectQuery(query, connection);
							if (assetComplaintData != null && assetComplaintData.size() > 0)
							{
								String updatedid = "", updatedDate = "", updatedTime = "";
								System.out.println("Inside If ");
								for (Iterator iterator2 = assetComplaintData.iterator(); iterator2.hasNext();)
								{
									Object[] object2 = (Object[]) iterator2.next();
									updatedid = object2[0].toString();
									updatedDate = object2[1].toString();
									updatedTime = object2[2].toString();
								}

								String timedifference = DateUtil.time_difference(updatedDate, updatedTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
								boolean difference = getGreaterThan(timedifference.replace(":", "."), "00.31");
								if (difference)
								{
									String updateQry = "update machine_complaint set date='" + DateUtil.getCurrentDateUSFormat() + "',time='" + DateUtil.getCurrentTimeHourMin() + "' where id='" + updatedid + "'";
									boolean updateFlag = HUH.updateData(updateQry, connection);
									if (updateFlag)
									{
										CH.addMessage("Prabhat", "", "9811841099", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								}
							} else
							{
								System.out.println("Inside Else ");
								try
								{
									String updateQry = "insert into machine_complaint(machine_table_id,date,time,complaint_for) values ('" + id + "','" + DateUtil.getCurrentDateUSFormat() + "','" + DateUtil.getCurrentTimeHourMin() + "','Restricted Exe')";
									boolean insertFlag = HUH.updateData(updateQry, connection);
									if (insertFlag)
									{
										CH.addMessage("Prabhat", "", "9811841099", first_msg, "", "Pending", "3", "Asset", connection);
										CH.addMessage("Shailendra", "", "9818666503", second_msg, "", "Pending", "3", "Asset", connection);
										String mailText1=getConfigMessage("Mr. Prabhat", first_msg);
										String mailText2=getConfigMessage("Mr. Prabhat", second_msg);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText1, "", "Pending", "0", "", "Asset",connection);
										CH.addMail("Prabhat", "", emailid,"System Alert for "+ip+"", mailText2, "", "Pending", "0", "", "Asset",connection);
										sendSMS("9811841099", first_msg);
										sendSMS("9818666503", second_msg);
									}
								} catch (Exception e)
								{
									e.printStackTrace();
								}

							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}

					}

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List getDetail(SessionFactory connection)
	{
		List dataList = null;
		Session session = null;
		Transaction transaction = null;
		StringBuilder query = new StringBuilder();

		try
		{
			query.append("select ip,asset_detail.ramsize as actual_size,ram_details,process_list,temp_file_size,asset_detail.assetname,emp.empName,emp.mobOne,emp.emailIdOne,machine.id,asset_detail.id as assetid from machine_details as machine");
			query.append(" left join asset_detail as asset_detail on machine.machine_id = asset_detail.serialno");
			query.append(" left join asset_allotment_detail as asset_allotment on asset_detail.id = asset_allotment.assetid");
			query.append(" left join compliance_contacts as contact on  asset_allotment.employeeid = contact.id");
			query.append(" left join employee_basic as emp on  contact.emp_id = emp.id");
			//System.out.println("query.toString() " + query.toString());
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dataList = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public String getRestrictedExeData(String assetid, SessionFactory connection)
	{
		String dataList = null;
		List list = null;
		Session session = null;
		Transaction transaction = null;
		StringBuilder query = new StringBuilder();

		try
		{
			query.append("select restrictedexe from restricted_exe_details where assetid='" + assetid + "'");
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			//System.out.println("Get Restricted Value  "+query.toString());
			list = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (list!=null && list.size()>0)
		{
			dataList =list.get(0).toString();
		}
		return dataList;
	}

	public static boolean getGreaterThan(String first, String two)
	{
		boolean flag = false;
		try
		{
			float firstVal = Float.valueOf(first);
			float secondVal = Float.valueOf(two);
			if (firstVal > secondVal)
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public String getConfigMessage(String name, String msg)
	 {
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><br><b>Hello !!!</b><br><br>");
		mailtext.append(" "+msg+" <br>");
		mailtext.append("<font face ='verdana' size='2'><br>Thanks !!!</FONT><br>");
		mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
		return mailtext.toString();
	 }

	
	
	// Method body for sending SMS @Khushal 24-01-2014
	@SuppressWarnings("deprecation")
	public void sendSMS(String msisdn, String fmsg)
	{
		try
		{
			String clientid = "dredst22";
			String encodedMsg = URLEncoder.encode(fmsg);

			if ((msisdn != null && !msisdn.equals("")) && msisdn.length() == 10
					&& ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg != null && !fmsg.equals("")))
			{
				try
				{
					String URL = null;
					StringBuffer url = new StringBuffer();
					URL = "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=demourl&password=demourl";
					URL = URL + "&clientid=" + clientid + "&to=";

					url.append(URL);
					url.append(msisdn);
					url.append("&text=");
					url.append(encodedMsg);
					

					URL codedURL = new URL(url.toString());
					HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
					HURLC.connect();
					int revertCode = HURLC.getResponseCode();
					HURLC.disconnect();
					if (revertCode == 1201 || revertCode == 1202 || revertCode == 1204)
					{
						url = null;
					}
					else if (revertCode == 200 || revertCode == 505 || revertCode == 503)
					{
						/*
						 * System.out.println("SMS Not Send Successfully From First Route and Get Revert Code :"
						 * +
						 * revertCode+"::: Now trying to send from second Route"
						 * );
						 * 
						 * String URL2 = null; StringBuffer url2 = new
						 * StringBuffer(); URL2 =
						 * "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=blkhdm&password=blk31"
						 * ; URL2=URL2+"&clientid="+clientid+"&to=";
						 * 
						 * url2.append(URL2); url2.append(msisdn);
						 * url2.append("&text="); url2.append(encodedMsg);
						 * url2.append("&Senderid="); url2.append(senderid);
						 * 
						 * URL codedURL2 = new URL(url2.toString());
						 * HttpURLConnection HURLC2
						 * =(HttpURLConnection)codedURL2.openConnection();
						 * HURLC2.connect(); int revertCode2=
						 * HURLC2.getResponseCode(); HURLC.disconnect(); if
						 * (revertCode2==1201 || revertCode2==1202 ||
						 * revertCode2==1204) {System.out.println(
						 * "SMS Send Successfully From Second Route and Get111 Revert Code :"
						 * +revertCode); url = null; } else {
						 * System.out.println(
						 * "SMS Not Send Successfully From Second Route and Get Revert Code :"
						 * +revertCode);updateQry=
						 * "update instant_msg set flag='err',status='Pending',update_date='"
						 * +DateUtil.getCurrentDateUSFormat()+"',update_time='"+
						 * DateUtil.getCurrentTime()+"' where id='"+id+"'"; new
						 * HelpdeskUniversalHelper().updateData(updateQry,
						 * connection); url = null; }
						 */
						System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :" + revertCode);
						url = null;
					}
					else
					{
						System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :" + revertCode);
						url = null;
					}
				}
				catch (Exception E)
				{
					try
					{
						String URL = null;
						StringBuffer url = new StringBuffer();
						URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
						URL = URL + "&clientid=" + clientid + "&to=";

						url.append(URL);
						url.append(msisdn);
						url.append("&text=");
						url.append(encodedMsg);

						URL codedURL = new URL(url.toString());
						HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
						HURLC.connect();
						int revertCode = HURLC.getResponseCode();
						HURLC.disconnect();
						if (revertCode == 1201 || revertCode == 1202 || revertCode == 1204)
						{
							System.out.println("SMS Send Successfully From Second Route and Get Revert Code :" + revertCode);
							url = null;
						}
						else if (revertCode == 200)
						{
							System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :" + revertCode);
							url = null;
						}
						else if (revertCode == 505)
						{
							System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :" + revertCode);
							url = null;
						}
						else
						{
							System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :" + revertCode);
							url = null;
						}
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
