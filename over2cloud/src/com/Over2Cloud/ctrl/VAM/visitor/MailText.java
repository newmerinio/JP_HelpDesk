package com.Over2Cloud.ctrl.VAM.visitor;

public class MailText{
	public String getMailText(String empName, String visitorName, String company, String visitorMob, String imgpath, String address, String url, String srNumber){
		System.out.println("imgpath>>>>"+imgpath);
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear "+empName+",</b>");
		mailtext.append("<br><br>"+visitorName+" from "+company+" ("+visitorMob+") has arrived to meet you.");
		mailtext.append("<br><br><b> Visitor Details</b> <br>");
		mailtext.append("<table><tr><td><img src="+imgpath+" alt='Visitor Image' height='110' width='100'></td></tr></table>");
		mailtext.append("<br><b> Company Name:</b> "+company+" </b> <br><b> Company Address:</b>"+address+"</b>");
		mailtext.append("<br><br> In case it's a scheduled meeting you are requested to meet him/her at the reception.");
		mailtext.append("<br><br> For unscheduled requests you can accept or reject the request by clicking on the link below:");
		mailtext.append("<br><br>"+url+"meetrequestacceptreject.action?srnumberfrommail="+srNumber);
		mailtext.append("<br><br> You can also inform the security guard to accept or reject the request.");
		mailtext.append("<br> Pls Note : Once cancelled the visitor will be informed that you are busy in a meeting.");
		return mailtext.toString();
	}
	public String getVehicleMailText(String empName, String drivername, String company, String driverMob, String imgpath, String address, String url, String srNumber){
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear "+empName+",</b>");
		mailtext.append("<br><br>"+drivername+" from "+address+" ("+driverMob+") has entered in campus.");
		mailtext.append("<br><br><b> Driver Details</b> <br>");
		mailtext.append("<table><tr><td><img src="+imgpath+" alt='Visitor Image' height='110' width='100'></td></tr></table>");
		mailtext.append("<br><b> Company Name:</b> "+company+" </b> <br><b> Company Address:</b>"+address+"</b>");
		
		return mailtext.toString();
	}
	public String getPreReqMailText(String empName, String VisitorName, String compName, String location, String schedDate, String schedTime){
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear "+empName+"</b>,");
		mailtext.append("<br><br>"+VisitorName+" from "+compName+" will arrive at place "+location+" on date "+schedDate+" at time "+schedTime+".");
		mailtext.append("<br><br>It's a scheduled meeting you are requested to meet him/her.Thanks.");
		return mailtext.toString();
	}
}
