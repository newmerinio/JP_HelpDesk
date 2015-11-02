package com.Over2Cloud.InstantCommunication;

import org.hibernate.SessionFactory;

public class Report_Old extends Thread{
SessionFactory connection=null;
public Report_Old(SessionFactory connection)
 {
	this.connection=connection;
 }
ReportHelper RH = new ReportHelper();
	
 //Start Point on calling a run method from main method
  public void run()
	 {
		try {
			while (true) {
				try {
					RH.ReportGeneration(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
  public static void main(String args[])
	{
		try {
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		      Thread esc=new Thread(new Report_Old(connection));
		      esc.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}