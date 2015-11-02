package com.Over2Cloud.InstantCommunication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class InstantMailSender implements Runnable{
	String id = "";
	String emailid = "";
	String subject = "";
	String mailtext="";
	String attachment="";
	/*String module = "";
	String server = "";
	String port = "";
	String from_emailid="";
	String password="";*/

	SessionFactory connection=null;
	HelpdeskUniversalHelper HUH=null;
	
	public InstantMailSender(SessionFactory connection,HelpdeskUniversalHelper HUHObj)
	{
		this.connection=connection;
		this.HUH=HUHObj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
     try {
		  while (true)
		   {
			 try {
                    GenericMailSender GMS = new GenericMailSender();
		    		List listMail=new ArrayList();
		    	
		    		String query="select id ,emailid ,subject ,mail_text ,attachment ,date ,time from instant_mail where flag='0' and date<='"+DateUtil.getCurrentDateUSFormat()+"'";
		    		listMail = HUH.getData(query,connection);
		    		
		    		if(listMail!=null && listMail.size()>0)
		    		 {
		    			for (Iterator iterator = listMail.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null && !object[0].toString().equals("")) {
								id=object[0].toString();
							}
							if (object[1]!=null && !object[1].toString().equals("")) {
								emailid=object[1].toString();
							}
							else {
								emailid="NA";
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								subject=object[2].toString();
							}
							else {
								subject="NA";
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								mailtext=object[3].toString();
							}
							else {
								mailtext="NA";
							}
							
							if (object[4]!=null && !object[4].toString().equals("")) {
								attachment=object[4].toString();
							}
							/*if (object[5]!=null && !object[5].toString().equals("")) {
								module=object[5].toString();
							}
							else {
								module="NA";
							}*/
							
							String updateQry="";
							System.out.println("Mail text "+mailtext);
							if (emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
								System.out.println("inside if");
								updateQry="update instant_mail set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								HUH.updateData(updateQry, connection);
							}
							else {
								System.out.println("inside if");
								updateQry="update instant_mail set flag='5',status='Error',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								HUH.updateData(updateQry, connection);
							}
	                       
							if (true)
							{
								System.out.println("Sending Mail to :: "+emailid);
								if (emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
									if (emailid!=null && !emailid.equals("") && !emailid.equals("NA") && !subject.equals("") && !subject.equals("NA")) {
										GMS.sendMail(emailid, subject, mailtext, attachment,"smtp.gmail.com","587","o2chdm@gmail.com","dreamsol",id,connection);
										//  GMS.sendMail(emailid, subject, mailtext,attachment,"smtp.gmail.com", "465", "karnikag@dreamsol.biz", "karnikagupta");
											
										
										
									}
								}
							}
						}
		    		}
			       
			        Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(30*1000);
					System.out.println("Woke Up======================="+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}		
	finally
	 {
		try {
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	}

	public static void main(String args[])
		{
			try{
				 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
				 HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				 Thread uclient=new Thread(new InstantMailSender(connection,HUH));
				 uclient.start();
				 Thread.sleep(100*1);
			}catch(Exception E){
				E.printStackTrace();
			}
		}
}