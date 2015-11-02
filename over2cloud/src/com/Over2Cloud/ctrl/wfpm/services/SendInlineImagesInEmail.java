package com.Over2Cloud.ctrl.wfpm.services;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendInlineImagesInEmail {

/*	public void mailWithImage(String subject, List<String> to, String from,String mailtext, String imagepath) {
		boolean status=false;
		String imagePath = imagepath;
		
		String mailfrom = from;

		final String username = "over2cloud1";// change accordingly
		final String password = "Dr3@ms0l1";// change accordingly

		// sending email server...
		
		String host = "202.162.242.232";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.localhost", "localhost");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			// Debugging the Session
			session.setDebug(false);
//			Transport transport = session.getTransport();	
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			// Set Subject: header field
			message.setSubject(subject);
			
			if(!imagePath.equalsIgnoreCase("NA")
					&& !imagePath.equalsIgnoreCase("empty") && imagePath != null)
			{
				//code for with image. 
				for (String toEmail : to) {

					// first Part
					BodyPart messageBodyPart1 = new MimeBodyPart();

					BodyPart messageBodyPart2 = new MimeBodyPart();

						
						//System.out.println("------------------------------------------>>>>>");
						messageBodyPart2.setHeader("Content-ID", "<image>");
						DataSource fds = new FileDataSource(imagePath); // Change
																		// Image
																		// Path
						// second part (the image)

						messageBodyPart2.setDataHandler(new DataHandler(fds)); // according
																				// to
					String headerString = mailtext;
					String htmlText = "NA";
					// This mail has 2 part, the BODY and the embedded image
					MimeMultipart multipart = new MimeMultipart("related");

					String footerString = "Thanks & Regards <br> <b>CRM Team</b><br>DreamSol TeleSolution Pvt. Ltd.<br> C-52, Sector-2<br> Noida.";

					 htmlText =
					 headerString+"<br /><center><img src=\"cid:image\"> </center><br/>"+footerString;
					//htmlText = headerString + " " + footerString;
					// first part (the html)
					messageBodyPart1.setContent(htmlText, "text/html");
					// add it
					multipart.addBodyPart(messageBodyPart1);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));

					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toEmail));

					if (!imagePath.equalsIgnoreCase("NA") && imagePath != null) {
						// / add image to the multipart
						multipart.addBodyPart(messageBodyPart2);
					}

					// put everything together
					message.setContent(multipart);
					// Send message
					Transport.send(message);
					System.out.println("Sent message successfully....");
				//	transport.close();
					status=true;
				}
			}
			else
			{
				// code for without image
				for (String toEmail : to) {

					// first Part
					BodyPart messageBodyPart1 = new MimeBodyPart();

					BodyPart messageBodyPart2 = new MimeBodyPart();

					if (!imagePath.equalsIgnoreCase("NA")
							&& !imagePath.equalsIgnoreCase("empty")) {

						
						System.out.println("------------without image------------------------------>>>>>");
						messageBodyPart2.setHeader("Content-ID", "<image>");
						DataSource fds = new FileDataSource(imagePath); // Change
																		// Image
																		// Path
						// second part (the image)

						messageBodyPart2.setDataHandler(new DataHandler(fds)); // according
																				// to
																				// system
					}
					String headerString = "";
					String htmlText = "NA";
					
					
					
					*//***
					 * 
	          
	        message.setContent("<h1>sending html mail check</h1>","text/html" );  
	    
					 *//*
					
					
					//MimeMultipart multipart = new MimeMultipart("related");

					String footerString = mailtext;

					// htmlText =
					// headerString+"<br /><center><img src=\"cid:image\"> </center><br/>"+footerString;
					htmlText = headerString + " " + footerString;
					// first part (the html)
					messageBodyPart1.setContent(htmlText, "text/html");
					// add it
					//multipart.addBodyPart(messageBodyPart1);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));

					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toEmail));

					if (!imagePath.equalsIgnoreCase("NA") && imagePath != null) {
						// / add image to the multipart
						//multipart.addBodyPart(messageBodyPart2);
					}

					// put everything together
					message.setContent(htmlText,"text/html");
					// Send message
					Transport.send(message);
					System.out.println("Sent message successfully....");
				//	transport.close();
					status=true;
				}
			}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		
		}
//return status;
	}
*/	
	
	public void mailWithImage(String subject, String to, String from,String mailtext, String imagepath)
	{
		System.out.println("HEllo*************************");
		//String str="Hi, I am &lt;image&lt; thanx";
		String strimagetag="&lt;image&gt;";
		String headerStr = mailtext;
		String footerStr = "";
				
		if(mailtext.contains(strimagetag))
		{
			
			headerStr = mailtext.substring(0, mailtext.indexOf("&lt;image&gt;"));
			footerStr = mailtext.substring(mailtext.indexOf("&lt;image&gt;")+strimagetag.length(),mailtext.length());
			
			//System.out.println(mailtext.substring(0, mailtext.indexOf("&lt;image&gt;")));
			//System.out.println(mailtext.substring(mailtext.indexOf("&lt;image&gt;")+strimagetag.length(),mailtext.length()));
		}
		
		
		
		//System.out.println("Final mailtext >"+mailtext);
		//System.out.println(str.substring(str.indexOf("&lt;image&lt;"),));
		
		
		String imagePath = imagepath;
		
		String mailfrom = from;

		final String username = "over2cloud1";// change accordingly
		final String password = "Dr3@ms0l1";// change accordingly

		// sending email server...
		
		String host = "202.162.242.232";
	
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.localhost", "localhost");
	
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			// Debugging the Session
			session.setDebug(false);

			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			// Set Subject: header field
			message.setSubject(subject);

			
			
			if(!imagePath.equalsIgnoreCase("NA")
					&& !imagePath.equalsIgnoreCase("empty") && imagePath != null && !imagePath.equalsIgnoreCase(""))
			{
				//code for with image. 
				//for (String toEmail : to) {

					// first Part
					BodyPart messageBodyPart1 = new MimeBodyPart();

					BodyPart messageBodyPart2 = new MimeBodyPart();

						
						//System.out.println("------------------------------------------>>>>>");
						messageBodyPart2.setHeader("Content-ID", "<image>");
						DataSource fds = new FileDataSource(imagePath); // Change
																		// Image
																		// Path
						// second part (the image)

						messageBodyPart2.setDataHandler(new DataHandler(fds)); // according
																				// to
					String headerString = headerStr;
					String htmlText = "NA";
					// This mail has 2 part, the BODY and the embedded image
					MimeMultipart multipart = new MimeMultipart("related");

					String footerString = footerStr;

					 htmlText =
					 headerString+"<br /><center><img src=\"cid:image\"> </center><br/>"+footerString;
					 System.out.println("*****************************************           "+       htmlText);
					//htmlText = headerString + " " + footerString;
					// first part (the html)
					messageBodyPart1.setContent(htmlText, "text/html");
					// add it
					multipart.addBodyPart(messageBodyPart1);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));

					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(to));

					if (!imagePath.equalsIgnoreCase("NA") && imagePath != null) {
						// / add image to the multipart
						multipart.addBodyPart(messageBodyPart2);
					}

					// put everything together
					message.setContent(multipart);
			//		System.out.println(message);
					// Send message
					
					Transport.send(message);

					System.out.println("Sent message successfully....");
				//}
			}
			else
			{
				// code for without image
				//for (String toEmail : to) {

					// first Part
					BodyPart messageBodyPart1 = new MimeBodyPart();

					BodyPart messageBodyPart2 = new MimeBodyPart();

					if (!imagePath.equalsIgnoreCase("NA")
							&& !imagePath.equalsIgnoreCase("empty")) {

						
						System.out.println("------------without image------------------------------>>>>>");
						messageBodyPart2.setHeader("Content-ID", "<image>");
						DataSource fds = new FileDataSource(imagePath); // Change
																		// Image
																		// Path
						// second part (the image)

						messageBodyPart2.setDataHandler(new DataHandler(fds)); // according
																				// to
																				// system
					}
					String headerString = mailtext;
					String htmlText = "NA";
					
					
					
					/***
					 * 
	          
	        message.setContent("<h1>sending html mail check</h1>","text/html" );  
	    
					 */
					
					
					//MimeMultipart multipart = new MimeMultipart("related");

					String footerString = "";

					// htmlText =
					// headerString+"<br /><center><img src=\"cid:image\"> </center><br/>"+footerString;
					htmlText = headerString + " " + footerString;
					// first part (the html)
					messageBodyPart1.setContent(htmlText, "text/html");
					// add it
					//multipart.addBodyPart(messageBodyPart1);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));

					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(to));

					if (!imagePath.equalsIgnoreCase("NA") && imagePath != null) {
						// / add image to the multipart
						//multipart.addBodyPart(messageBodyPart2);
					}

					// put everything together
					message.setContent(htmlText,"text/html");
					// Send message
					Transport.send(message);

					System.out.println("Sent message successfully....");
				//}
			}
			
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	
}