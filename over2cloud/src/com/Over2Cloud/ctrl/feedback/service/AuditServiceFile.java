package com.Over2Cloud.ctrl.feedback.service;

import org.hibernate.SessionFactory;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class AuditServiceFile extends Thread{

	SessionFactory connection=null;
	public AuditServiceFile(SessionFactory connection) 
	{
		super();
		this.connection = connection;
	}

	public void run()
	{
		try
		{
			new AuditReportHelper().auditReportForFeedback(connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			connection.close();
		}
	}
	
	
	public static void main(String args[])
	{
		try
		{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			Thread uclient=new Thread(new AuditServiceFile(connection));
			uclient.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

