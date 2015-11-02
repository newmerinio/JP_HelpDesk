package com.Over2Cloud.assetservice;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.asset.NetworkMonitor.NetworkScannerHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class AssetMonitoring extends Thread{
	 SessionFactory connection=null;
	 CommunicationHelper CH=null;
	 HelpdeskUniversalHelper HUH = null;
	 CommonOperInterface cbt = new CommonConFactory().createInterface();
	 
	 public AssetMonitoring(SessionFactory connection,CommunicationHelper CH,HelpdeskUniversalHelper HUH)
	 {
		 this.connection=connection;
		 this.CH=CH;
		 this.HUH = HUH;
	 }
	 AssetMonitoringHelper AMH = new AssetMonitoringHelper();
	 NetworkScannerHelper NCH = new NetworkScannerHelper();
	@Override
	public void run() {
		while (true) {
		try {
			AMH.getMonitoringData(connection,CH,HUH);
			NCH.checkIP(connection,cbt);
			NCH.checkPort(connection,cbt);
			Runtime rt = Runtime.getRuntime();
			rt.gc();
		    System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			Thread.sleep(5000);
			System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-9");
			CommunicationHelper CH = new CommunicationHelper();
			HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
			AssetMonitoring AM = new AssetMonitoring(connection,CH,HUH);
			Thread thread = new Thread(AM);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
