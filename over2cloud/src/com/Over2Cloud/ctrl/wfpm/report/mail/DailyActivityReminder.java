package com.Over2Cloud.ctrl.wfpm.report.mail;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class DailyActivityReminder extends Thread{
	SessionFactory			connection	= null;
	CommunicationHelper	CH					= null;

	public DailyActivityReminder(SessionFactory connection, CommunicationHelper CH)
	{
		this.connection = connection;
		this.CH = CH;
	}

	DailyActivityReminderMailText	mailtextobject	= new DailyActivityReminderMailText();

	public void run()
	{
		try
		{
			/*while (true)
			{*/
				try
				{
					// method calling report generation.
					//mailtextobject.buildEmail(connection,"Mr. Sanjiv","sanjivs@dreamsol.biz","","","Sales Report","WFPM");
					mailtextobject.buildEmailSales(connection, "Sanjiv Singh", "sanjivs@dreamsol.biz", "", "", "Daily Activity Reminder", "WFPM", "");
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................" + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
					Thread.sleep(1000 * 60);
					System.out.println("Woke Up......................." + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			/*}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		try
		{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			CommunicationHelper CH = new CommunicationHelper();
			Thread activityreminder = new Thread(new DailyActivityReminder(connection, CH));
			activityreminder.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
