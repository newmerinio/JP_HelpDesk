package com.Over2Cloud.ctrl.VAM.VisitorReports;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;


public class VisitorStayAlert extends Thread {
	SessionFactory connection = null;
	VisitorStayAlertHelperUniversal VSAHU = null;
	CommunicationHelper CH = null;
	public VisitorStayAlert(SessionFactory connection, VisitorStayAlertHelperUniversal VSAHU, CommunicationHelper CH)
	{
		this.connection = connection;
		this.VSAHU = VSAHU;
		this.CH = CH;
	}
	VisitorStayAlertHelper VSAH = new VisitorStayAlertHelper();
	
	public void run()
	{
		try {
			//method calling Alert generation.
			VSAH.alertGeneration(connection,VSAHU,CH);
			Runtime rt = Runtime.getRuntime();
			rt.gc();
			System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			Thread.sleep(1000*60);
			System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		try {
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-8");
			VisitorStayAlertHelperUniversal VSAHU = new VisitorStayAlertHelperUniversal();
			CommunicationHelper CH = new CommunicationHelper();
			Thread stayalrt = new Thread(new VisitorStayAlert(connection, VSAHU,CH));
			stayalrt.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
