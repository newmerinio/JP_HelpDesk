package com.Over2Cloud.ctrl.wfpm.report.mail;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class DailyActivityReminderMailText {
	/*public void buildEmail(SessionFactory factory, String name, String email, String mobile, String dept, String subject, String module)
	{
			try {
				String cId = "50";
				//String empId = "1341";
				//String date = DateUtil.getCurrentDateUSFormat();
				DailyActivityReminderMailHelper damh = new DailyActivityReminderMailHelper();
				StringBuilder mailtextvisitor = new StringBuilder();
				mailtextvisitor.append("<FONT face='sans-serif'>");

				try
				{
					// Greeting
					// *****************************************************************
					String subjectBody = "WFPM Activity Reminder for "+"Mr. "+name+" as on "+DateUtil.getCurrentDateIndianFormat();
					subject = subjectBody;
					mailtextvisitor.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
					mailtextvisitor.append("<TR><TD><b>Dear ");
					mailtextvisitor.append(name);
					mailtextvisitor.append(",</b></TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor.append("<TR><TD><b>Hello!!!</b></TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor
							.append("<TR><TD>Reminder for your today's activity. For dynamic graphical analysis, we request you to please <a href='http://115.112.237.111:8080/over2cloud' target='blank'>click here</a> and login with your respective credentials.</TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor.append("<TR><TD align='Center' bgcolor='#CDC9C9'><b>");
					mailtextvisitor.append(subjectBody);
					mailtextvisitor.append("</b></TD></TR>");
					mailtextvisitor.append("</TABLE>");
					mailtextvisitor.append("<BR><BR>");
					
					// activity details to sales person.
					ArrayList<ArrayList<String>> dailyActivityRemList = damh.dailyActivityReminder(cId, factory);
					mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
					mailtextvisitor.append("<tr bgcolor='#DAD7D7' align='center'><td><b>Organization Name</b></td><td><b>Name</b></td><td><b>Offering </b></td><td><b>Current Activity</b></td><td><b>Last Activity</b></td></tr>");
					if (dailyActivityRemList != null && !dailyActivityRemList.isEmpty())
					{
						for (ArrayList<String> list : dailyActivityRemList)
						{
							mailtextvisitor.append("<TR bgcolor='#F3F2F2'>");
							for (String data : list)
							{
								mailtextvisitor.append("<TD align='Center'>");
								mailtextvisitor.append(CommonHelper.getFieldValue(data));
								mailtextvisitor.append("</TD>");
							}
							mailtextvisitor.append("</TR>");
						}
					}
					else
					{
						mailtextvisitor.append("<TR><TD colspan='5' align='Center'  bgcolor='#F3F2F2'>NA</TD></TR>");
					}
					mailtextvisitor.append("</TABLE>");
					mailtextvisitor.append("<BR><BR>");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			

			mailtextvisitor.append("<br><br>");
			mailtextvisitor.append("<div class='gmail_default'><b style='color:rgb(0,0,0);font-family:arial,sans-serif'>Thanks &amp; Regards,<br>");

			mailtextvisitor
					.append("WFPM Application Team<br></b><span style='color:rgb(0,0,0);font-family:arial,sans-serif'>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>---------------------------</span><br style='color:rgb(0,0,0);font-family:arial,sans-serif'>");

			mailtextvisitor
					.append("<font face='TIMESROMAN' size='1' style='color:rgb(0,0,0)'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then&nbsp;<br>");

			mailtextvisitor
					.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over '<a href='http://www.dreamsol.biz/contact_us.html' style='text-decoration:none' target='_blank'>http://www.dreamsol.biz/<wbr>contact_us.html</a>' or you may kindly mail your feedback to&nbsp;<br>");

			mailtextvisitor
					.append("<a href='mailto:support@dreamsol.biz' style='text-decoration:none' target='_blank'>support@dreamsol.biz</a></font><font face='arial, sans-serif'><span style='font-size:14px'><br></span></font></div>");

			mailtextvisitor.append("</FONT>");
			// System.out.println(">mailtextvisitor>>"+mailtextvisitor);
			CommunicationHelper CH = new CommunicationHelper();
			CH.addMail(name, dept, email, subject, mailtextvisitor.toString(), "NA", "Pending", "0", "", module, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/
	
	public void buildEmailSales(SessionFactory factory, String name, String email, String mobile, String dept, String subject, String module, String cId)
	{
			try {
				//String cId = "12";
				//String empId = "16";
				//String date = DateUtil.getCurrentDateUSFormat();
				DailyActivityReminderMailHelper damh = new DailyActivityReminderMailHelper();
				StringBuilder mailtextvisitor = new StringBuilder();
				mailtextvisitor.append("<FONT face='sans-serif'>");

				try
				{
					// Greeting
					// *****************************************************************
					String subjectBody = "WFPM Activity Reminder for "+name+" as on "+DateUtil.getCurrentDateIndianFormat();
					subject = subjectBody;
					mailtextvisitor.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
					mailtextvisitor.append("<TR><TD><b>Dear ");
					mailtextvisitor.append(name);
					mailtextvisitor.append(",</b></TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor.append("<TR><TD><b>Hello!!!</b></TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor
							.append("<TR><TD>Reminder for your today's activity. For dynamic graphical analysis, we request you to please <a href='http://115.112.237.111:8080/over2cloud' target='blank'>click here</a> and login with your respective credentials.</TD></TR>");
					mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
					mailtextvisitor.append("<TR><TD align='Center' bgcolor='#CDC9C9'><b>");
					mailtextvisitor.append(subjectBody);
					mailtextvisitor.append("</b></TD></TR>");
					mailtextvisitor.append("</TABLE>");
					mailtextvisitor.append("<BR><BR>");
					
					// activity details to sales person.
					ArrayList<ArrayList<String>> dailyActivityRemList = damh.dailyActivityReminder(cId, factory);
					//System.out.println("dailyActivityRemList "+dailyActivityRemList.size());
					mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
					mailtextvisitor.append("<tr bgcolor='#DAD7D7' align='center'><td><b>Organization Name</b></td><td><b>Name</b></td><td><b>Offering </b></td><td><b>Current Activity</b></td><td><b>Last Activity</b></td></tr>");
					if (dailyActivityRemList != null && !dailyActivityRemList.isEmpty() && dailyActivityRemList.size()>1)
					{
						for (ArrayList<String> list : dailyActivityRemList)
						{
							mailtextvisitor.append("<TR bgcolor='#F3F2F2'>");
							for (String data : list)
							{
								mailtextvisitor.append("<TD align='Center'>");
								mailtextvisitor.append(CommonHelper.getFieldValue(data));
								mailtextvisitor.append("</TD>");
							}
							mailtextvisitor.append("</TR>");
						}
					}
					else
					{
						mailtextvisitor.append("<TR><TD colspan='5' align='Center'  bgcolor='#F3F2F2'>NA</TD></TR>");
					}
					mailtextvisitor.append("</TABLE>");
					mailtextvisitor.append("<BR><BR>");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			

			mailtextvisitor.append("<br><br>");
			mailtextvisitor.append("<div class='gmail_default'><b style='color:rgb(0,0,0);font-family:arial,sans-serif'>Thanks &amp; Regards,<br>");

			mailtextvisitor
					.append("WFPM Application Team<br></b><span style='color:rgb(0,0,0);font-family:arial,sans-serif'>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>---------------------------</span><br style='color:rgb(0,0,0);font-family:arial,sans-serif'>");

			mailtextvisitor
					.append("<font face='TIMESROMAN' size='1' style='color:rgb(0,0,0)'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then&nbsp;<br>");

			mailtextvisitor
					.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over '<a href='http://www.dreamsol.biz/contact_us.html' style='text-decoration:none' target='_blank'>http://www.dreamsol.biz/<wbr>contact_us.html</a>' or you may kindly mail your feedback to&nbsp;<br>");

			mailtextvisitor
					.append("<a href='mailto:support@dreamsol.biz' style='text-decoration:none' target='_blank'>support@dreamsol.biz</a></font><font face='arial, sans-serif'><span style='font-size:14px'><br></span></font></div>");

			mailtextvisitor.append("</FONT>");
			// System.out.println(">mailtextvisitor>>"+mailtextvisitor);
			CommunicationHelper CH = new CommunicationHelper();
			CH.addMail(name, dept, email, subject, mailtextvisitor.toString(), "NA", "Pending", "0", "", module, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}