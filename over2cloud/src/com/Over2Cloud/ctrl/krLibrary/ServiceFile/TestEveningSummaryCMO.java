package com.Over2Cloud.ctrl.krLibrary.ServiceFile;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.krLibrary.KRPojo;
import com.Over2Cloud.ctrl.productivityEvaluation.dashboard.ProductivityDashboardPojo;

public class TestEveningSummaryCMO extends Thread
{
	
private static SessionFactory connection=null;
KRServiceHelper SRH=null;
CommunicationHelper CH = null;

@SuppressWarnings("static-access")
public TestEveningSummaryCMO(SessionFactory connection,KRServiceHelper SRHObj,CommunicationHelper CHObj)
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
					List<KRPojo> sharedSummaryData=SRH.getAllSharedSummary(connection);
					//List<KRPojo> actionTakenData=SRH.getAllActionTakenData(connection);
					/*if ((sharedSummaryData!=null && sharedSummaryData.size()>0) || (actionTakenData!=null && actionTakenData.size()>0)) 
					{
						System.out.println("sharedSummaryData   "+sharedSummaryData.size());
						System.out.println("sharedSummaryData   "+sharedSummaryData.size());
						String mailtext = SRH.getConfigMailForReport("Mr. Lalit",sharedSummaryData,actionTakenData);
						//String filepath=SRH.writeDataInExcel(sharedSummaryData,actionTakenData);
						if (mailtext!=null && !mailtext.equals("") ) 
						{
							
							new GenericMailSender().sendMail("karnikag@dreamsol.biz", "Testing", mailtext, "", "smtp.gmail.com", "465", "karnikag@dreamsol.biz", "karnikagupta");
							
							
							//new CommunicationHelper().addMail("Mr. Lalit","Management","lalit.kaushik@jbm.co.in","Evening Report", mailtext,"","Pending", "0",null,"DAR",connection);
						}
						
					}*/
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
			 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-3");
		     CommunicationHelper CH = new CommunicationHelper();
		     KRServiceHelper SRHH=new KRServiceHelper();
		     Thread esc = new Thread(new TestEveningSummaryCMO(connection,SRHH,CH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 }
