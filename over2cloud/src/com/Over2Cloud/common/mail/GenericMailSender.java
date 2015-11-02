package com.Over2Cloud.common.mail;
/**
 * 
 * @author Khushal Singh
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.TestProperty;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.sun.mail.smtp.SMTPSendFailedException;

public class GenericMailSender {
	
	
	/**
	 * @param toRecipient
	 * @param subject
	 * @param mailText
	 * @param attachFile
	 * @param server
	 * @param port
	 * @param from_emailid
	 * @param password
	 * @return boolean after sending mail
	 * 
	 *  Update : 19-04-2014  11:45 AM
	 */
	
	public void sendMail(String toRecipient,String subject,String mailText,String attachFile,String server,String port,String from_emailid,String password,String id,SessionFactory connection) 
	{
		final String username = "helpdesk@blkhospital.com";
		final String pwd = "help@123";
		
		HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
		if(toRecipient!=null && !toRecipient.equals(""))
		{
			// Getting Resolved Ip for DNS smtp.office365.com
			
			//List Contain All resolved IP
			List<String> ips = new ArrayList<String>();
			try {
	          String line;
	          Process p = Runtime.getRuntime().exec("nslookup smtp.office365.com");
	          
	           BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	           
	          while ((line = input.readLine()) != null) 
	          {
	        	  if(line.startsWith("Address:"))
	        	  {
	        		 String[] ip = line.split(":");
	        		 if(ip[1].indexOf("#") > 0) continue;
	        		 else
	        		 {
	        			 ips.add(ip[1].trim());
	        			 System.out.println("Ips of nslookup is as "+ip[1]);
	        		 }
	        	  }
	          }
	          input.close();
	      } catch (Exception err) {
	          err.printStackTrace();
	      }	
			
			
			if(ips.size()>0)
			{
			}
			else
			{
				try
				{
					TestProperty test=new TestProperty();
					Properties configProp=new Properties();
		            String ipsAdd=null;
		            InputStream in = test.getClass().getResourceAsStream("/com/Over2Cloud/common/mail/mailIps.properties");
		       	 	configProp.load(in);
		       	 	ipsAdd=configProp.getProperty("ipAdd");
		       	 	String arr[]=ipsAdd.split("#");
		       	 	for (int i = 0; i < arr.length; i++)
		       	 	{
		       	 		ips.add(arr[i]);
					}
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
			
			/*ips.add("115.115.12.35");
			ips.add("115.115.12.33");
			ips.add("115.115.12.32");
			ips.add("115.115.12.31");
			ips.add("115.115.12.38");
			ips.add("115.115.12.39");
			ips.add("115.115.12.54");*/
		  
			//Block Start For Sending MAil
	      
	     // System.out.println("Ips List size is as :::::   "+ips.size());
			if(ips.size()>0)
			{
				boolean sendStatus=false;
			      for(String ip:ips)
			      {
			    	//  System.out.println("Sending Mail through IP :: "+ip);
			    	 try
			    	 {/*
							Properties props = new Properties();
							
							// Code commented for BLK
							props.setProperty("mail.transport.protocol", "smtp");
							props.setProperty("mail.host",ip);
							props.put("mail.smtp.port",port);
							props.put("mail.smtp.auth", "true");
							
							//if(smtp.getPort().equals("25"))
							if(port.equals("25"))
							{
							//props.put("mail.smtp.starttls.enable","true");
							}
							else
							{
							//props.setProperty("mail.smtp.ssl.trust","*");
							props.put("mail.smtp.socketFactory.port",port);
							props.put("mail.smtp.socketFactory.fallback", "false");
							props.setProperty("mail.smtp.quitwait","false");
						    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
							}
							
							 // Code for setting the HTML body in the mail

							props.put("mail.smtp.auth", "true");
		                    props.put("mail.smtp.starttls.enable", "true");
		                    props.put("mail.smtp.host", ip);
		                    props.put("mail.smtp.port", "587");
		                    
							Session session = Session.getInstance(props,new MailAuthenticator(from_emailid,password));
							Transport transport = session.getTransport();	
							MimeMessage message = new MimeMessage(session);
							if(subject!=null && !subject.equals(""))
							 {	
								message.setSubject(subject);	
							 }
							else
							 {
							   // message.setSubject(smtp.getSubject());
							    message.setSubject("Testing Email");
							 }
							String recipients[]=null;
							if(toRecipient.contains(","))
							recipients=toRecipient.split(",");
							else
							{
								recipients=new String[1];
							    recipients[0]=toRecipient;	
							}
								
							message.setFrom(new InternetAddress(from_emailid));
					
							InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
							for (int i = 0; i < recipients.length; i++)
							  {
							        addressTo[i] = new InternetAddress(recipients[i]);
							  }
							
							MimeBodyPart mbp1 = new MimeBodyPart();
					        mbp1.setText(mailText);
					        mbp1.setContent(mailText,"text/html");
					        Multipart mp = new MimeMultipart();
					        mp.addBodyPart(mbp1);

					        if (attachFile!=null && !attachFile.equals("")) {
					            MimeBodyPart mbp = new MimeBodyPart();
					            FileDataSource fds = new FileDataSource(attachFile);
					            mbp.setDataHandler(new DataHandler(fds));
					            mbp.setFileName(fds.getName());
					            mp.addBodyPart(mbp);
							}
							message.setContent(mp);
						   	
						    //If setDebug value is turn true all the mail information will be display on console
							session.setDebug(false); 
							session.getDebugOut();
							transport.connect();	
							message.saveChanges();
							Transport.send(message);
							System.out.println("****** Mail Send Successfully on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+" Hrs ******");
							transport.close();
							
					*/		
			    		 System.out.println("Sending Mail to : "+toRecipient);
			    		 Properties props = new Properties();
							props.put("mail.smtp.auth", "true");
							props.put("mail.smtp.starttls.enable", "true");
							props.put("mail.smtp.host", ip);
							props.put("mail.smtp.port", "587");
					 
							Session session = Session.getInstance(props,
							  new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(username, pwd);
								}
							  });
							session.setDebug(false);
			    		 
							
							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("helpdesk@blkhospital.com"));
							message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toRecipient));
							message.setSubject(subject);
							
							MimeBodyPart mbp1 = new MimeBodyPart();
					        mbp1.setText(mailText);
					        mbp1.setContent(mailText,"text/html");
					        Multipart mp = new MimeMultipart();
					        mp.addBodyPart(mbp1);
							
					        if (attachFile!=null && !attachFile.equals("")) {
					            MimeBodyPart mbp = new MimeBodyPart();
					            FileDataSource fds = new FileDataSource(attachFile);
					            mbp.setDataHandler(new DataHandler(fds));
					            mbp.setFileName(fds.getName());
					            mp.addBodyPart(mbp);
							}
							message.setContent(mp);
							
						//	message.setText(mailText);
				 
							Transport trnsport;
							trnsport = session.getTransport("smtp");
							trnsport.connect();
							//Transport.send(message);
				 
							message.saveChanges(); 
							trnsport.sendMessage(message, message.getAllRecipients());
							trnsport.close();
							
							sendStatus=true;
			    	 }
			    	 catch (MessagingException e)
			    	 {
			    		 sendStatus=false;
			    		 System.out.println("Messaging Exception"+e);
			    	 }
			    	 catch (Exception e)
			    	 {
			    		 sendStatus=false;
			    		System.out.println("Exception"+e);
			    	 }
			    	 finally
			    	 {
			    		 if(sendStatus)
			    		 {
			    			 String updateQry="";
								if (toRecipient!=null &&!toRecipient.equals("") &&!toRecipient.equals("NA") && mailText!=null &&!mailText.equals("") &&!mailText.equals("NA")) {
									updateQry="update instant_mail set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								}
								else {
									updateQry="update instant_mail set flag='5',status='Error',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								}
		                     boolean update = HUH.updateData(updateQry, connection);
		                     System.out.println(" Updated inside if :: "+update);
			    			break; 
			    		 }
			    		 else
			    		 {
			    			 System.out.println("Update inside Else");
			    			 String updateQry1="update instant_mail set flag='5',status='Error',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
			    			 HUH.updateData(updateQry1, connection);
			    		 }
			    	 }
			      }
			}
			else
			{
				// Sending Mail Using Gmail
				
				
				System.out.println("GMail to be added...");
				//String toRecipient,String subject,String mailText,String attachFile,String server,String port,String from_emailid,String password,String id,SessionFactory connection
				sendMailByGmail(toRecipient, subject, mailText, attachFile,"smtp.gmail.com","465","o2chdm@gmail.com","dreamsol",id,connection);
			}
		}
	  }
	
	
	public void sendMailByGmail(String toRecipient,String subject,String mailText,String attachFile,String server,String port,String from_emailid,String password,String id,SessionFactory connection) 
	{boolean sendMailFlag=false;
	try {
		//System.out.println(""+toRecipient+"<><><><><><><>"+getSmtp());
		if(toRecipient!=null && !toRecipient.equals(""))
		{
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host",server);
		props.put("mail.smtp.port",port);
		props.put("mail.smtp.auth", "true");
		
		//if(smtp.getPort().equals("25"))
		if(port.equals("25"))
		{
		//props.put("mail.smtp.starttls.enable","true");
		}
		else
		{
		//props.setProperty("mail.smtp.ssl.trust","*");
		props.put("mail.smtp.socketFactory.port",port);
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait","false");
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		}
				
		Session session = Session.getInstance(props,new MailAuthenticator(from_emailid,password));
		Transport transport = session.getTransport();	
		MimeMessage message = new MimeMessage(session);
		if(subject!=null && !subject.equals(""))
		 {	
			message.setSubject(subject);	
		 }
		else
		 {
		   // message.setSubject(smtp.getSubject());
		    message.setSubject("Testing Email");
		 }
		String recipients[]=null;
		if(toRecipient.contains(","))
		recipients=toRecipient.split(",");
		else
		{
			recipients=new String[1];
		    recipients[0]=toRecipient;	
		}
			
		message.setFrom(new InternetAddress(from_emailid));

		InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
		for (int i = 0; i < recipients.length; i++)
		  {
		        addressTo[i] = new InternetAddress(recipients[i]);
		  }
		message.setRecipients(Message.RecipientType.TO, addressTo);	
	    message.setSentDate(new Date());
	    
	    
	    // Code for setting the HTML body in the mail
	    MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(mailText);
        mbp1.setContent(mailText,"text/html");
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);

        if (attachFile!=null && !attachFile.equals("")) 
        {
        	System.out.println(attachFile);
        	String[] fileName=null;
        	
        	fileName=attachFile.split(",");
			
            MimeBodyPart mbp = null;
            FileDataSource fds = null;
            if (fileName!=null && !fileName.toString().equalsIgnoreCase(""))
			{
            	  for (String file : fileName)
  				  {
  	            	mbp = new MimeBodyPart();
  		            fds = new FileDataSource(file);
  		            mbp.setDataHandler(new DataHandler(fds));
  		            mbp.setFileName(fds.getName());
  		            mp.addBodyPart(mbp);
  				  }
			}
            /*MimeBodyPart mbp = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachFile);
            mbp.setDataHandler(new DataHandler(fds));
            mbp.setFileName(fds.getName());
            mp.addBodyPart(mbp);*/
		}
		message.setContent(mp);
	   	
	    //If setDebug value is turn true all the mail information will be display on console
		session.setDebug(false); 
		session.getDebugOut();
		transport.connect();	
		message.saveChanges();
		Transport.send(message);
		System.out.println("****** Mail Send Successfully on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+" Hrs using Gmail ******");
		transport.close();
		sendMailFlag=true;
		}
	}
	catch(SMTPSendFailedException se)
		{
		se.printStackTrace();
		
		}
		catch(javax.mail.MessagingException me)
		{
			me.printStackTrace();
			
		}
	 catch (Exception e) {
		 e.printStackTrace();
		 
	 }
	 }
    /*public boolean sendMail(String toRecipient,String subject,String mailText,String attachFile,String server,String port,String from_emailid,String password)
	  {
		boolean sendMailFlag=false;
		try {
			//System.out.println(""+toRecipient+"<><><><><><><>"+getSmtp());
			if(toRecipient!=null && !toRecipient.equals(""))
			{
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host",server);
			props.put("mail.smtp.port",port);
			props.put("mail.smtp.auth", "true");
			
			//if(smtp.getPort().equals("25"))
			if(port.equals("25"))
			{
			//props.put("mail.smtp.starttls.enable","true");
			}
			else
			{
			//props.setProperty("mail.smtp.ssl.trust","*");
			props.put("mail.smtp.socketFactory.port",port);
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait","false");
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			}
					
			Session session = Session.getInstance(props,new MailAuthenticator(from_emailid,password));
			Transport transport = session.getTransport();	
			MimeMessage message = new MimeMessage(session);
			if(subject!=null && !subject.equals(""))
			 {	
				message.setSubject(subject);	
			 }
			else
			 {
			   // message.setSubject(smtp.getSubject());
			    message.setSubject("Testing Email");
			 }
			String recipients[]=null;
			if(toRecipient.contains(","))
			recipients=toRecipient.split(",");
			else
			{
				recipients=new String[1];
			    recipients[0]=toRecipient;	
			}
				
			message.setFrom(new InternetAddress(from_emailid));
	
			InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			for (int i = 0; i < recipients.length; i++)
			  {
			        addressTo[i] = new InternetAddress(recipients[i]);
			  }
			message.setRecipients(Message.RecipientType.TO, addressTo);	
		    message.setSentDate(new Date());
		    
		    
		    // Code for setting the HTML body in the mail
		    MimeBodyPart mbp1 = new MimeBodyPart();
	        mbp1.setText(mailText);
	        mbp1.setContent(mailText,"text/html");
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbp1);

	        if (attachFile!=null && !attachFile.equals("")) {
	            MimeBodyPart mbp = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(attachFile);
	            mbp.setDataHandler(new DataHandler(fds));
	            mbp.setFileName(fds.getName());
	            mp.addBodyPart(mbp);
			}
			message.setContent(mp);
		   	
		    //If setDebug value is turn true all the mail information will be display on console
			session.setDebug(false); 
			session.getDebugOut();
			transport.connect();	
			message.saveChanges();
			Transport.send(message);
			System.out.println("****** Mail Send Successfully on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+" Hrs ******");
			transport.close();
			sendMailFlag=true;
			}
		}catch(SMTPSendFailedException se)
			{
			se.printStackTrace();
			
			}
			catch(javax.mail.MessagingException me)
			{
				me.printStackTrace();
				
			}
		 catch (Exception e) {
			 e.printStackTrace();
			 
		 }
		 return sendMailFlag;
	}
	*/
	
}