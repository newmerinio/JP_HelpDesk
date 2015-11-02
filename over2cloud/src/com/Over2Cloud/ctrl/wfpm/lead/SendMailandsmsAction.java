package com.Over2Cloud.ctrl.wfpm.lead;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.SMSTest;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendMailandsmsAction extends ActionSupport
{
	Map							session					= ActionContext.getContext().getSession();
	String					userName				= (String) session.get("uName");
	String					accountID				= (String) session.get("accountid");
	SessionFactory	connectionSpace	= (SessionFactory) session.get("connectionSpace");

	private int			id							= 0;
	private String	emailone				= null;
	private String	emailtwo				= null;
	private String emailthree;
	private String	subject					= null;
	private String	mailbody				= null;
	private String	downloadFile;
	private String	sms;
	private String	mobileNo;
	private String	smsData;
	private String	tags;
	private String template_name;
	private File attachedDoc;
	private String kr;
	private String krname;
	private String mailtypeId;
	private String mailtype;
	private String frequencys;
	private String date;
	private String dates;
	private String hours;
	protected String							attachedDocFileName;
	protected String							attachedDocContentType;
	private String deptName;
	private String groupName;
	private String subGroupName;
	private String attachement1;
	private String frequency;
	
	public String getAttachedDocContentType() {
		return attachedDocContentType;
	}

	public void setAttachedDocContentType(String attachedDocContentType) {
		this.attachedDocContentType = attachedDocContentType;
	}

	private String day;
	
	@SuppressWarnings("unchecked")
	public String sendinvoicemail()
	{
		String returnResult = ERROR;
		String portno = null;
		String serverName = null;
		String senderemailid = null;
		String password = null;
		boolean instantmailstatus=false;
		boolean schedulemailstatus=false;
		String name = "Sanjiv";
		String dept = "IT";
		String renameFilePath = null;
		File theFile = null;
		String fileName = null;
		try
		{

			/*try
			{
				if (sms.equalsIgnoreCase("true"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder query1 = new StringBuilder("");
					query1.append(" select mobileNo from leadgeneration where id='" + id + "' ");
					//System.out.println("query1>>>.SMS>>>>>>>>" + query1);
					List data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					if (data != null)
					{
						mobileNo = data.get(0).toString();
						SMSTest fAct = new SMSTest();
						// String msg="";
						//System.out.println("smsData>>>>>>>>>> " + smsData);
						//System.out.println("SMS Sent <>>>>>>>>>" + fAct.sendSMS(smsData, mobileNo));
					}
				}
				CommonforClass eventDao = new CommanOper();
				List<Smtp> smtpDetails = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
				if (smtpDetails != null && smtpDetails.size() > 0)
				{
					for (Smtp smtp : smtpDetails)
					{
						serverName = smtp.getServer();
						portno = smtp.getPort();
						senderemailid = smtp.getEmailid();
						password = smtp.getPwd();

					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}*/
			//System.out.println("serverName>>"+serverName);
			//System.out.println("port >>"+portno);
			//System.out.println("getEmailone>>"+getEmailone());
			//System.out.println("getEmailtwo()>>"+getEmailtwo());
			//System.out.println("getEmailthree>>"+getEmailthree());
			//System.out.println("template_name>>"+template_name);
			//System.out.println("attachedDoc>>"+attachedDoc);
			//System.out.println("mailtype>>"+mailtype);
			//System.out.println("frequencys>>"+getFrequencys());
			//System.out.println("date>>"+date);
			//System.out.println("dates>>"+dates);
			//System.out.println("hours>>"+hours);
			//System.out.println("day>>"+day);
			//System.out.println("deptname>>"+getDeptName());
			//System.out.println("groupName>>"+getGroupName());
			//System.out.println("subGroupName>>"+getSubGroupName());
			//System.out.println("attachement1>>"+attachement1);
			Mailtest mailObjt = new Mailtest();
			StringBuffer bufferobj = new StringBuffer();
			bufferobj.append("<html>");
			bufferobj.append("<body>");
			bufferobj.append(mailbody);
			bufferobj.append("</body>");
			bufferobj.append("</html>");
			System.out.println("email data>>"+bufferobj.toString());
			
			if (attachedDoc != null)
			{
				//System.out.println("attachedDoc:" + attachedDoc);
				//System.out.println("attachedDocFileName:" + attachedDocFileName);
				//System.out.println("attachedDocContentType:" +attachedDocContentType);
				String filePath = new CreateFolderOs().createUserDir("WFPM_Mail_Attachement");
				fileName = filePath + "/mail_" + DateUtil.getCurrentDateIndianFormat().replaceAll("-", "_") + "_"
						+ DateUtil.getCurrentTime().replaceAll(":", "_") + "_" + attachedDocFileName;
				// //System.out.println("fileName:" + fileName);
				File fileToCreate = new File(fileName);
				FileUtils.copyFile(attachedDoc, fileToCreate);
				fileName = fileName.concat(","+attachement1);
				//System.out.println(fileName);
				//boolean flag = dtah.saveMOMFilePathToDb(connectionSpace, fileName, String.valueOf(id));
				// Save file path to database saveMOMFilePathToDb
			}

			
			
			
			
			if(mailtype.equalsIgnoreCase("Instant"))
			{
				//instantmailstatus = new MsgMailCommunication().addMail(name, deptName,getEmailone(),getEmailtwo(), getEmailthree(), subject, bufferobj.toString(), "","Pending", "0", fileName, "WFPM", groupName, subGroupName, mailtype);
				System.out.println("instantmailstatus>"+instantmailstatus);
				if (instantmailstatus)
				{
					addActionMessage("Instant mail saved successfully to send.");
					returnResult = SUCCESS;
				}
				else
				{
					addActionError("There is an exception during saving mail");
					returnResult = ERROR;
				}
			}else
			{
				if(mailtype.equalsIgnoreCase("Schedule"))
				{
					//schedulemailstatus = new MsgMailCommunication().addScheduleMail(name, deptName, getEmailone(),getEmailtwo(), getEmailthree(), subject, bufferobj.toString(), "","Pending", "0", fileName, "WFPM", date, hours, connectionSpace, groupName, subGroupName, mailtype, frequency);
				}
				System.out.println("schedulemailstatus>"+schedulemailstatus);
				if (schedulemailstatus)
				{
					addActionMessage("Mail scheduled successfully to send.");
					returnResult = SUCCESS;
				}
				else
				{
					addActionError("There is an exception during saving mail");
					returnResult = ERROR;
				}
			}
			
			
			// boolean mailstatus = mailObjt.confMailHTML(serverName, portno, senderemailid, password, getEmailone(), subject, bufferobj.toString());
			
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}

		return returnResult;
	}

	public String sendSMSandMail()
	{
		try
		{
			//System.out.println("><>>>>>>>>>>>> Inside  sendSMSandMail ");

			String portno = null;
			String serverName = null;
			String senderemailid = null;
			String password = null;
			boolean mailstatus = false;
			String mailid[] = null;
			String mobileNo[] = null;
			try
			{

				try
				{
					if (sms.equalsIgnoreCase("true"))
					{

						mobileNo = getMobileNo().split(",");
						SMSTest fAct = new SMSTest();
						for (int i = 0; i < mobileNo.length; i++)
						{
							if (!mobileNo[i].equalsIgnoreCase("NA"))
							{
								fAct.sendSMS(smsData, mobileNo[i].trim());
							}
						}
					}
					CommonforClass eventDao = new CommanOper();
					List<Smtp> smtpDetails = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
					if (smtpDetails != null && smtpDetails.size() > 0)
					{
						for (Smtp smtp : smtpDetails)
						{
							serverName = smtp.getServer();
							portno = smtp.getPort();
							senderemailid = smtp.getEmailid();
							password = smtp.getPwd();

						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				Mailtest mailObjt = new Mailtest();
				StringBuffer bufferobj = new StringBuffer();
				bufferobj.append("<html>");
				bufferobj.append("<body>");
				bufferobj.append(mailbody);
				bufferobj.append("</body>");
				bufferobj.append("</html>");

				mailid = getEmailone().split(",");
				for (int i = 0; i < mailid.length; i++)
				{
					mailstatus = mailObjt.confMailHTML(serverName, portno, senderemailid, password, mailid[i].trim(), subject, bufferobj.toString());
				}
				if (mailstatus)
				{
					addActionMessage("Mail And SMS Send Successfully");
				}
				else
				{
					addActionError("There is an Exception during Sending mial");
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			addActionMessage("Mail Send Successfully");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getEmailone()
	{
		return emailone;
	}

	public void setEmailone(String emailone)
	{
		this.emailone = emailone;
	}

	public String getEmailtwo()
	{
		return emailtwo;
	}

	public void setEmailtwo(String emailtwo)
	{
		this.emailtwo = emailtwo;
	}

	public String getMailbody()
	{
		return mailbody;
	}

	public void setMailbody(String mailbody)
	{
		this.mailbody = mailbody;
	}

	public String getSms()
	{
		return sms;
	}

	public void setSms(String sms)
	{
		this.sms = sms;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getSmsData()
	{
		return smsData;
	}

	public void setSmsData(String smsData)
	{
		this.smsData = smsData;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public String getEmailthree() {
		return emailthree;
	}

	public void setEmailthree(String emailthree) {
		this.emailthree = emailthree;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}


	public File getAttachedDoc() {
		return attachedDoc;
	}

	public void setAttachedDoc(File attachedDoc) {
		this.attachedDoc = attachedDoc;
	}

	public String getKr() {
		return kr;
	}

	public void setKr(String kr) {
		this.kr = kr;
	}

	public String getKrname() {
		return krname;
	}

	public void setKrname(String krname) {
		this.krname = krname;
	}

	public String getMailtypeId() {
		return mailtypeId;
	}

	public void setMailtypeId(String mailtypeId) {
		this.mailtypeId = mailtypeId;
	}

	public String getMailtype() {
		return mailtype;
	}

	public void setMailtype(String mailtype) {
		this.mailtype = mailtype;
	}

	public String getFrequencys() {
		return frequencys;
	}

	public void setFrequencys(String frequencys) {
		this.frequencys = frequencys;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getAttachedDocFileName() {
		return attachedDocFileName;
	}

	public void setAttachedDocFileName(String attachedDocFileName) {
		this.attachedDocFileName = attachedDocFileName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getAttachement1() {
		return attachement1;
	}

	public void setAttachement1(String attachement1) {
		this.attachement1 = attachement1;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	

}
