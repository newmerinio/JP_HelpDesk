package com.Over2Cloud.assetReminder.serviceFiles;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminder;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

public class AssetReminderFile implements Runnable
{
	SessionFactory connection=null;
	public AssetReminderFile(SessionFactory connection)
	{
		this.connection=connection;
	}
	AssetReminderHelper ARH = new AssetReminderHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					ARH.checkReminder(connection);
					ARH.checkEscalation(connection);
					ARH.checkRecurring(connection);
					ARH.checkSnooze(connection);
					ARH.checkTodayPending(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(60*500);
					System.out.println("Woke Up........................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			    }
			    catch (Exception e) 
			    {
			    	e.printStackTrace();
			    }
		    }
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
			  Thread esc = new Thread(new AssetReminderFile(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
