package com.Over2Cloud.ctrl.wfpm.report.mail;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class WFPMMailingReport
{
	public void buildEmail(SessionFactory factory, String name, String email, String mobile, String dept, String subject, String module)
	{
		try
		{
			// String cId = new
			// CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName,
			// userName, factory)[0];
			String cId = "12";
			String empId = "16";
			String date = DateUtil.getCurrentDateUSFormat();
			WFPMMailingReportHelper wmrh = new WFPMMailingReportHelper();
			StringBuilder mailtextvisitor = new StringBuilder();
			mailtextvisitor.append("<FONT face='sans-serif'>");

			try
			{
				// Greeting
				// *****************************************************************
				String subjectBody = "WFPM Activity Report of Mr. Varun as on "+DateUtil.getCurrentDateIndianFormat();
				subject = subjectBody;
				mailtextvisitor.append("<TABLE border='0' cellpadding='0' cellspacing='0' width='100%'>");
				mailtextvisitor.append("<TR><TD><b>Dear ");
				mailtextvisitor.append(name);
				mailtextvisitor.append(",</b></TD></TR>");
				mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
				mailtextvisitor.append("<TR><TD><b>Hello!!!</b></TD></TR>");
				mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
				mailtextvisitor
						.append("<TR><TD>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please <a href='http://115.112.237.111:8080/over2cloud' target='blank'>click here</a> and login with your respective credentials.</TD></TR>");
				mailtextvisitor.append("<TR><TD>&nbsp;</TD></TR>");
				mailtextvisitor.append("<TR><TD align='Center' bgcolor='#CDC9C9'><b>");
				mailtextvisitor.append(subjectBody);
				mailtextvisitor.append("</b></TD></TR>");
				mailtextvisitor.append("</TABLE>");
				mailtextvisitor.append("<BR><BR>");

				// WFPM Activity Report of <Varun> as on <Date>
				// KPI
				// ******************************************************************
				ArrayList<ArrayList<String>> KPIReportList = wmrh.fetchKPIReport(empId, factory);
				// int kpiColSize = KPIReportList.get(0).size();
				mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtextvisitor.append("<TR><TD colspan='4' align='Center'  bgcolor='#CDC9C9'><b>KPI</b></TD></TR>");
				mailtextvisitor
						.append("<tr bgcolor='#DAD7D7' align='center'><td><b>KPI</b></td><td><b>Monthly Target</b></td><td><b>Total Achievement</b></td><td><b>Today's Achievement</b></td></tr>");
				if (KPIReportList != null && !KPIReportList.isEmpty())
				{
					for (ArrayList<String> list : KPIReportList)
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
					mailtextvisitor.append("<TR><TD colspan='4' align='Center'  bgcolor='#F3F2F2'>NA</TD></TR>");
				}
				mailtextvisitor.append("</TABLE>");
				mailtextvisitor.append("<BR><BR>");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				// Lead
				// *****************************************************************
				ArrayList<ArrayList<String>> leadList = CommonHelper.convertDBRecordsToNestedArrayList(wmrh.fetchActivitiesForLead(factory, date, cId), -1, false);
				// int leadColSize = leadList.get(0).size();
				mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtextvisitor.append("<TR><TD colspan='4' align='Center'  bgcolor='#CDC9C9'><b>Lead</b></TD></TR>");
				mailtextvisitor
						.append("<tr bgcolor='#DAD7D7' align='center'><td><b>Name</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Moved To</b></td></tr>");
				if (leadList != null && !leadList.isEmpty())
				{
					for (ArrayList<String> list : leadList)
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
					mailtextvisitor.append("<TR><TD colspan='4' align='Center'  bgcolor='#F3F2F2'>NA</TD></TR>");
				}
				mailtextvisitor.append("</TABLE>");
				mailtextvisitor.append("<BR><BR>");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				// Client
				// ***************************************************************
				ArrayList<ArrayList<String>> clientList = CommonHelper.convertDBRecordsToNestedArrayList(wmrh.fetchActivitiesForClient(factory, date, cId), 4, true);
				// int clientColSize = clientList.get(0).size();
				mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtextvisitor.append("<TR><TD colspan='5' align='Center'  bgcolor='#CDC9C9'><b>Client</b></TD></TR>");
				mailtextvisitor
						.append("<tr bgcolor='#DAD7D7' align='center'><td><b>Name</b></td><td><b>Offering</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Date</b></td></tr>");
				if (clientList != null && !clientList.isEmpty())
				{
					for (ArrayList<String> list : clientList)
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				// Associate
				// ************************************************************
				ArrayList<ArrayList<String>> associateList = CommonHelper.convertDBRecordsToNestedArrayList(wmrh.fetchActivitiesForAssociate(factory, date, cId), 4,
						true);
				// int associateColSize = associateList.get(0).size();
				mailtextvisitor.append("<TABLE border='1' cellpadding='0' cellspacing='0' width='100%'>");
				mailtextvisitor.append("<TR><TD colspan='5' align='Center' bgcolor='#CDC9C9'><b>Associate</b></TD></TR>");
				mailtextvisitor
						.append("<tr bgcolor='#DAD7D7' align='center'><td><b>Name</b></td><td><b>Offering</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Date</b></td></tr>");
				if (associateList != null && !associateList.isEmpty())
				{
					for (ArrayList<String> list : associateList)
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
					mailtextvisitor.append("<TR><TD colspan='5' align='Center' bgcolor='#F3F2F2'>NA</TD></TR>");
				}
				mailtextvisitor.append("</TABLE>");
			}
			catch (Exception e)
			{
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
			CommunicationHelper CH = new CommunicationHelper();
			CH.addMail(name, dept, email, subject, mailtextvisitor.toString(), "NA", "Pending", "0", "", module, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
