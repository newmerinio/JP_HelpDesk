package com.Over2Cloud.ctrl.wfpm.services;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;

public class ActivityGenerator extends Thread
{

	SessionFactory connection = null;

	public ActivityGenerator(SessionFactory connection)
	{
		this.connection = connection;
	}

	ActivityGeneratorHelper AGH=new ActivityGeneratorHelper();

	public void run()
	{
		try
		{
			while (true)
			{
				try
				{
					/**
					 * For Sending CRM Group mail with image.
					 * */
					// method calling report generation.
					AGH.createActivity(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................" + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
					Thread.sleep(1000 * 10);
					System.out.println("Woke Up......................." + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}

	}

	public static void main(String[] args)
	{
		try
		{
			SessionFactory dbconection = DBDynamicConnection.getSessionFactory();
			Thread ag = new Thread(new ActivityGenerator(dbconection));
			ag.start();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
