package com.Over2Cloud.ctrl.wfpm.report.mail;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class SummaryReport extends Thread
{
	SessionFactory			connection	= null;
	CommunicationHelper	CH					= null;

	public SummaryReport(SessionFactory connection, CommunicationHelper CH)
	{
		this.connection = connection;
		this.CH = CH;
	}

	WFPMMailingReport	SRH	= new WFPMMailingReport();

	public void run()
	{
		try
		{
			/*while (true)
			{*/
				try
				{
					// method calling report generation.
					SRH.buildEmail(connection,"Mr. Sanjiv","sanjivs@dreamsol.biz","","","Sales Report","WFPM");
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
			Thread sumrep = new Thread(new SummaryReport(connection, CH));
			sumrep.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
