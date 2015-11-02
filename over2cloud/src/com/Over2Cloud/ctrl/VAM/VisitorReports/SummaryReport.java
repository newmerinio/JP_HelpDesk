package com.Over2Cloud.ctrl.VAM.VisitorReports;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.VAM.common.SummaryReportHelperUniversal;

public class SummaryReport extends Thread
{
	SessionFactory connection = null;
	SummaryReportHelperUniversal SRHU = null;
	CommunicationHelper CH = null;
	public SummaryReport(SessionFactory connection,SummaryReportHelperUniversal SRHU, CommunicationHelper CH)
	{
		this.connection = connection;
		this.SRHU = SRHU;
		this.CH = CH;
	}
	SummaryReportHelper SRH = new SummaryReportHelper();
	public void run()
	{
		try
    {
			while(true)
			{
				try
	      {	
					//method calling report generation.
					SRH.reportGeneration(connection,SRHU,CH);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
	      } catch (Exception e)
	      {
		     e.printStackTrace();
	      }
		 }
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
	}
	public static void main(String[] args)
	{
		try
    {
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-8");
			SummaryReportHelperUniversal SRHU = new SummaryReportHelperUniversal();
			CommunicationHelper CH = new CommunicationHelper();
			Thread sumrep = new Thread(new SummaryReport(connection, SRHU,CH));
			sumrep.start();
    } catch (Exception e)
    {
	   e.printStackTrace();
    }
	}
}
