package com.Over2Cloud.ctrl.wfpm.services;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;

/**
 * 
 * @author Sanjiv Singh
 * @version 07.11.14.1
 * 
 * Thread Priority : Normal
 * RUN Label       : Loop
 *  
 */
public class MailServic extends Thread{
	SessionFactory	connection	= null;

	public MailServic(SessionFactory connection)
	{
		this.connection = connection;
	}

	SendInlineImagesInEmailHelper	SIM	= new SendInlineImagesInEmailHelper();

	public void run()
	{
		try {
			while(true)
			{
				try
				{
					/** 
					 *  For Sending CRM Group mail with image.
					 * */
					// method calling report generation.
					//SIM.buildEmail(connection,"Mr. Sanjiv","sanjivs@dreamsol.biz","","","Sales Report","WFPM");
					SIM.sendMailWithImage(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................" + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
					Thread.sleep(1000 * 10);
					System.out.println("Woke Up......................." + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
				
				
	}

	public static void main(String[] args)
	{
		try
		{
			SessionFactory dbconection =DBDynamicConnection.getSessionFactory();
			Thread mailobj = new Thread(new MailServic(dbconection));
			mailobj.start();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
