package com.Over2Cloud.ctrl.krLibrary.ServiceFile;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class KRServiceFile implements Runnable
{
	SessionFactory connection=null;
	public KRServiceFile(SessionFactory connection)
	{
		this.connection=connection;
	}
	KRServiceFileHelper KRH = new KRServiceFileHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					KRH.checkReminder(connection);
					//KRH.checkRecurring(connection);
					//KRH.checkTodayPending(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(4000);
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
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-3");
			  Thread esc = new Thread(new KRServiceFile(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
