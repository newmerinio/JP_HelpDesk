package com.Over2Cloud.ctrl.asset.NetworkMonitor;


import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class NetworkScanner implements Runnable
{
	SessionFactory connection=null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public NetworkScanner(SessionFactory connection)
	{
		this.connection=connection;
	}
	NetworkScannerHelper NCH = new NetworkScannerHelper();
	
	public void run()
	{
		try
		{
			while (true) 
		    {
				try
				{
					NCH.checkIP(connection,cbt);
					NCH.checkPort(connection,cbt);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(5000);
					System.out.println("Woke Up........................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				}
				catch(Exception e)
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
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-9");
			  Thread esc = new Thread(new NetworkScanner(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
