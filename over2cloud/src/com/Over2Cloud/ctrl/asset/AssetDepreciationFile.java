package com.Over2Cloud.ctrl.asset;

import java.util.concurrent.TimeUnit;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class AssetDepreciationFile implements Runnable
{
	SessionFactory connection=null;
	public AssetDepreciationFile(SessionFactory connection)
	{
		this.connection=connection;
	}
	AssetDepreciationHelper ADF=new AssetDepreciationHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					ADF.updateDepression(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					TimeUnit.DAYS.sleep(1);
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
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-9");
			  Thread esc = new Thread(new AssetDepreciationFile(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

