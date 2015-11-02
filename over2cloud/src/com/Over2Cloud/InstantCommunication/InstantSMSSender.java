package com.Over2Cloud.InstantCommunication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class InstantSMSSender implements Runnable{
	String id = "",mobno = "",msg_text = "",module = "",date = "",time = "",updateQry="",mappingUpdateQry="";
	SessionFactory connection=null;
	HelpdeskUniversalHelper HUH=null;
	@SuppressWarnings("unchecked")
	List listSMS=new ArrayList();
	
	private static CommonOperInterface cbt=new CommonConFactory().createInterface();

	
	public InstantSMSSender(SessionFactory connection,HelpdeskUniversalHelper HUHObj)
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
		    		listSMS.clear();
		    		String query="select id ,mobno ,msg_text,module,date,time from instant_msg where flag='0' and date<='"+DateUtil.getCurrentDateUSFormat()+"'";
		    		listSMS = HUH.getData(query,connection);
		    		if(listSMS!=null && listSMS.size()>0)
		    		{
		    			for (Iterator iterator = listSMS.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							id=object[0].toString();
							if (object[1]!=null && !object[1].toString().equals("")) {
								mobno=object[1].toString();
							}
							else {
								mobno="NA";
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								msg_text=object[2].toString();
							}
							else {
								msg_text="NA";
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								module=object[3].toString();
							}
							else {
								module="NA";
							}
							if (object[4]!=null && !object[4].toString().equals("")) {
								date=object[4].toString();
							}
							else {
								date="NA";
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								time=object[5].toString();
							}
							else {
								time="NA";
							}
							
						 if(date!=null && !date.equals("") && !date.equals("NA") && time!=null && !time.equals("") && !time.equals("NA")) 
						  {
							  boolean escnextlvl=DateUtil.time_update(date,time);
							  // If Escalating time passed away then go inside the If block
							   if(escnextlvl)
			           		      {
									    updateQry="update instant_msg set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
				                        boolean update = HUH.updateData(updateQry, connection);
				                        if (update)
				                         {
											if (mobno!=null && !mobno.equals("") && !mobno.equals("NA") && msg_text!=null && !msg_text.equals("") && !msg_text.equals("NA")) {
												sendSMS(mobno, msg_text,module,id,connection,HUH);
											}
										 }
					    		  }
						  }
		    		 }
		    		}
		    		Runtime rt = Runtime.getRuntime();
					rt.gc();
				    System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(5000);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
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
	
	public String getSenderId(String module,SessionFactory connection)
	{
		String senderId=null;
		List dataList=cbt.executeAllSelectQuery("select distinct senderId from module_clientid_mapping where moduleName='"+module+"'", connection);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
		{
			return dataList.get(0).toString();
		}
		return senderId;
	}
	
	public String getClientId(String module,SessionFactory connection)
	{
		String clientId=null;
		List dataList=cbt.executeAllSelectQuery("select distinct clientId from module_clientid_mapping where moduleName='"+module+"'", connection);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
		{
			return dataList.get(0).toString();
		}
		return clientId;
	}
	
	
	
	// Method body for sending SMS @Khushal 24-01-2014
	@SuppressWarnings("deprecation")
	public void sendSMS(String msisdn,String fmsg,String module, String id,SessionFactory connection,HelpdeskUniversalHelper HUH)
	 {
		try {
			  /*String  clientid = "blkdst1";
			  //String  clientid = "dmudst04";
			  String  senderid = "";*/
		      String encodedMsg = URLEncoder.encode(fmsg);
		      /*if (module!=null && !module.equals("") && (module.equals("HDM"))) {
		    	  senderid="BLKHDM";
			  }
		      else if (module!=null && !module.equals("") && (module.equals("HIS") || module.equals("T2M"))) {
		    	  senderid="BLKSSH";
			  }
		      else if (module!=null && !module.equals("") && (module.equals("FM"))) {
		    	  senderid="BLKCRM";
			  }
		      else {
		    	  senderid="BLKHDM";
			  }*/
		      
		      String  clientid =getClientId(module,connection);
				String  senderid =getSenderId(module,connection);

		      
		      
		      if((msisdn != null &&  !msisdn.equals("")) && msisdn.length() == 10 && ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg!= null && !fmsg.equals("")))
		        {
					try {
						  String URL = null;
				          StringBuffer url = new StringBuffer();
				          URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=blkhdm&password=blk31";
				           // URL = "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=demourl&password=demourl";
				          URL=URL+"&clientid="+clientid+"&to=";
			    	   
				          url.append(URL);
	   				      url.append(msisdn);
	   				      url.append("&text=");
	   				      url.append(encodedMsg);
	   				      url.append("&Senderid=");
	   				      url.append(senderid);
						  
						  URL codedURL = new URL(url.toString());
						  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
						  HURLC.connect();
						  int revertCode= HURLC.getResponseCode();
						  HURLC.disconnect();
						  if (revertCode==1201 || revertCode==1202 || revertCode==1204)
						   {
							  System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+revertCode); 
   						     url = null; 
						   }
						  else if(revertCode==200 || revertCode==505 || revertCode==503){
							    /*System.out.println("SMS Not Send Successfully From First Route and Get Revert Code :"+revertCode+"::: Now trying to send from second Route"); 
								
							     String URL2 = null;
		    				     StringBuffer url2 = new StringBuffer();
		    				     URL2 = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=blkhdm&password=blk31";
					             URL2=URL2+"&clientid="+clientid+"&to=";
				    	   
					             url2.append(URL2);
		   				         url2.append(msisdn);
		   				         url2.append("&text=");
		   				         url2.append(encodedMsg);
			   				     url2.append("&Senderid=");
			   				     url2.append(senderid);
							  
						         URL codedURL2 = new URL(url2.toString());
						         HttpURLConnection HURLC2 =(HttpURLConnection)codedURL2.openConnection();
						         HURLC2.connect();
						         int revertCode2= HURLC2.getResponseCode();
						         HURLC.disconnect();
						         if (revertCode2==1201 || revertCode2==1202 || revertCode2==1204)
								   {
						               System.out.println("SMS Send Successfully From Second Route and Get111 Revert Code :"+revertCode); 
		    						   url = null; 
								   }
						         else
								   {
						             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
		   						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
			                         new HelpdeskUniversalHelper().updateData(updateQry, connection);
			                         url = null;
								   }*/
							     System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	  						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
	  						     HUH.updateData(updateQry, connection);
		                         url = null;
						  }
						  else
						   {
				             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
  						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
  						     HUH.updateData(updateQry, connection);
	                         url = null;
						   }
					}
					catch (Exception E)
					  {
						try {
	   						 String URL = null;
	    				     StringBuffer url = new StringBuffer();
	    				     //URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
	    				     URL = "http://94.102.149.210:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
				             URL=URL+"&clientid="+clientid+"&to=";
			    	   
				             url.append(URL);
	   				         url.append(msisdn);
	   				         url.append("&text=");
	   				         url.append(encodedMsg);
		   				     url.append("&Senderid=");
		   				     url.append(senderid);
						  
					         URL codedURL = new URL(url.toString());
					         HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
					         HURLC.connect();
					         int revertCode= HURLC.getResponseCode();
					         HURLC.disconnect();
					         if (revertCode==1201 || revertCode==1202 || revertCode==1204)
							   {
					               System.out.println("SMS Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	    						   url = null; 
							   }
					         else if (revertCode==200) {
								    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
									updateQry="update instant_msg set flag='ui',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
									HUH.updateData(updateQry, connection);
			                        url = null;
							   }
							  else if (revertCode==505) {
								    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
									updateQry="update instant_msg set flag='vi',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
									HUH.updateData(updateQry, connection);
			                        url = null;
							   }
							  else
							   {
					             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	   						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
	   						     HUH.updateData(updateQry, connection);
		                         url = null;
							   }
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					 }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String args[])
		{
		  try{
				SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				Thread uclient=new Thread(new InstantSMSSender(connection,HUH));
				uclient.start();
				Thread.sleep(1000);
			 }
		  catch(Exception E){
				E.printStackTrace();
			}
		}
}