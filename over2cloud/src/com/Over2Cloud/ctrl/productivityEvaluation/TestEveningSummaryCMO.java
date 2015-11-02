package com.Over2Cloud.ctrl.productivityEvaluation;

import java.util.List;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.productivityEvaluation.dashboard.ProductivityDashboardPojo;

public class TestEveningSummaryCMO extends Thread
{
	
private static SessionFactory connection=null;
SummaryReportHelper SRH=null;
CommunicationHelper CH = null;

@SuppressWarnings("static-access")
public TestEveningSummaryCMO(SessionFactory connection,SummaryReportHelper SRHObj,CommunicationHelper CHObj)
  {
	this.connection=connection;
	this.SRH=SRHObj;
	this.CH=CHObj;
  }
	public void run()
	{
		try {
			 while (true) 
			 {
				try 
				{
					List<ProductivityDashboardPojo> sharedSummaryData=SRH.getAllSharedSummary(connection);
					List<ProductivityDashboardPojo> actionTakenData=SRH.getAllActionTakenData(connection);
					if (sharedSummaryData!=null && sharedSummaryData.size()>0 && actionTakenData!=null && actionTakenData.size()>0) 
					{
						String mailtext = SRH.getConfigMailForReport("Mr. Lalit",sharedSummaryData,actionTakenData);
						String filepath=SRH.writeDataInExcel(sharedSummaryData,actionTakenData);
						if (mailtext!=null && !mailtext.equals("") && filepath!=null && !filepath.equals("")) {
							new CommunicationHelper().addMail("Mr. Lalit","Management","lalit.kaushik@jbm.co.in","Evening Report", mailtext,"","Pending", "0",filepath,"DAR",connection);
						}
						if (mailtext!=null && !mailtext.equals("") && filepath!=null && !filepath.equals("")) 
						{
							mailtext = SRH.getConfigMailForReport("Mr. Sourabh",sharedSummaryData,actionTakenData);
							new CommunicationHelper().addMail("Mr. Sourabh","Management","sourabh.srivastava@jbm.co.in","Evening Report", mailtext,"","Pending", "0",filepath,"DAR",connection);
						}
					}
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	 {
		try {
			 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		     CommunicationHelper CH = new CommunicationHelper();
		     SummaryReportHelper SRHH=new SummaryReportHelper();
		     Thread esc = new Thread(new TestEveningSummaryCMO(connection,SRHH,CH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 }
