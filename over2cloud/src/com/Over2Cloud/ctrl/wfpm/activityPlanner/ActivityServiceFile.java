package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.krLibrary.ServiceFile.KRServiceFileHelper;

public class ActivityServiceFile implements Runnable
{
	SessionFactory connection=null;
	public ActivityServiceFile(SessionFactory connection)
	{
		this.connection=connection;
	}
	ActivityServiceFileHelper ASF=new ActivityServiceFileHelper();
	KRServiceFileHelper KRH = new KRServiceFileHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					ASF.checkDeadline(connection);
					//KRH.checkReminder(connection);
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
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-2");
			  Thread esc = new Thread(new ActivityServiceFile(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


}
