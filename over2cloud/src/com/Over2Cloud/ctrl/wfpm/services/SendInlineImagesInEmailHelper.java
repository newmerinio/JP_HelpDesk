package com.Over2Cloud.ctrl.wfpm.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class SendInlineImagesInEmailHelper{
	private HttpServletRequest request;
	protected CommonOperInterface	coi	= new CommonConFactory().createInterface();
	String to = "";
	String subj;
	String[] from;
	String compnId,date,time,frequency,day;
	String mailtext;
	String imagepath;
	String fromemail;
	public void sendMailWithImage(SessionFactory connection)
	{
		List maildata = fetchMailDetails(connection);
		System.out.println("maildata>>>"+maildata.size());
		SendInlineImagesInEmail sendmailwithImage = new SendInlineImagesInEmail();
		System.out.println("Time"+DateUtil.getCurrentTime());
		if(maildata!=null && maildata.size()>0)
		{
			for(Iterator it=maildata.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 if(obdata[0]!=null)
				 {
					 subj = obdata[0].toString();	 
				 }
				// to.add(obdata[1].toString());
				 if(obdata[1]!=null)
				 {
				 to=obdata[1].toString();
				 }
				// fromemail = "service@dreamsol.biz";
				 if(obdata[2]!=null)
				 {
				 mailtext = obdata[2].toString();
				 }
				 if(obdata[3]!=null)
				 {
				 imagepath = obdata[3].toString();
				 }
				 if(obdata[4]!=null)
				 {
				 date=obdata[4].toString();
				 }
				 if(obdata[5]!=null)
				 {
				 time=obdata[5].toString();
				 }
				 if(obdata[6]!=null)
				 {
				 frequency=obdata[6].toString();
				 }
				 // day=obdata[7].toString();
				 if(obdata[8]!=null)
				 {
				 compnId = obdata[8].toString();
				 }
				 if(obdata[9]!=null)
				 {
				   fromemail = obdata[9].toString();
				 }
				 
				 from=fromemail.split("@");
				
				 System.out.println("in mail send imag path     "+imagepath);
			//	 System.out.println("from[0]  "+from[0]+"@punchsms.com");
			//	 System.out.println("mailtext  "+mailtext);
				// System.out.println("to  "+to.size());
				 System.out.println("date "+date);
				 System.out.println("time "+time);
				 System.out.println("Frequency "+frequency);
				 StringBuilder query = new StringBuilder();
				 if(DateUtil.getCurrentDateUSFormat().equalsIgnoreCase(date))
				 {System.out.println("Test 1");
					if(DateUtil.getCurrentTimeHourMin().equalsIgnoreCase(time))
					{System.out.println("Test 2");
						if (frequency!=null && !frequency.equalsIgnoreCase("") && !frequency.equalsIgnoreCase("-1"))
						   {
							    if (frequency.equalsIgnoreCase("Daily"))
								{
							    	sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
									query.append("update instant_mail set flag='0',status='Pending',date='"+DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat())+"',time='"+time+"',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
									coi.updateTableColomn(connection, query);
								}
							    else if(frequency.equalsIgnoreCase("One-Time"))
							    {
							    	sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
									query.append("update instant_mail set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
									coi.updateTableColomn(connection, query);
							    }
							    else if(frequency.equalsIgnoreCase("Weekly"))
							    {
							    	System.out.println("Test 2");
							    	sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
									query.append("update instant_mail set flag='0',status='Pending',date='"+DateUtil.getNewDate("week", 1, DateUtil.getCurrentDateUSFormat())+"',time='"+time+"',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
									coi.updateTableColomn(connection, query);
							    }
							    else if(frequency.equalsIgnoreCase("Monthly"))
							    {
							    	sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
									query.append("update instant_mail set flag='0',status='Pending',date='"+DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat())+"',time='"+time+"',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
									coi.updateTableColomn(connection, query);
							    }
							    else if(frequency.equalsIgnoreCase("Yearly"))
							    {
							    	sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
							    	query.append("update instant_mail set flag='0',status='Pending',date='"+DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat())+"',time='"+time+"',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
							    	coi.updateTableColomn(connection, query);
							    }
						   }
					  else
				         {
						  	 sendmailwithImage.mailWithImage(subj,  to,fromemail, mailtext, imagepath);
						  	 query.append("update instant_mail set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+compnId+"'");
				    	     coi.updateTableColomn(connection, query);
				         }
					//	to.clear();
					  }
				 }
				 
		  	// query.append("update instant_mail set flag = '3',status ='Sent' where id='"+compnId+"'");
				
				 // deptlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
	//	to.clear();
	}
	
	public List fetchMailDetails(SessionFactory connection)
	{
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("subject, emailid, mail_text, attachment,date ,time,frequency,day,id,from_email from instant_mail ");
		query.append(" where module = 'WFPM' and flag = 0  and attachment is not null");
		System.out.println("query  >"+query.toString());
		List maildata = coi.executeAllSelectQuery(query.toString(), connection);
		return maildata;
	}
}