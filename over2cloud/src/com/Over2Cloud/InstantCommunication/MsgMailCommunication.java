package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MsgMailCommunication extends ActionSupport{
	@SuppressWarnings("unchecked")
	public boolean addScheduleMail(String name, String dept,String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module,String sendDate,String sendTime,SessionFactory connectionSpace)
	 {
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		boolean sessionFlag = true;
		if (sessionFlag) {
			try {
				@SuppressWarnings("unchecked")
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
			        
			          TableColumes tc = new TableColumes();
					  
			          tc.setColumnname("name");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("dept");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("emailid");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("subject");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("mail_text");
					  tc.setDatatype("text");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("flag");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("status");
					  tc.setDatatype("varchar(50)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("attachment");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("module");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("userName");
					  tc.setDatatype("varchar(25)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  @SuppressWarnings("unused")
					  boolean aa=  cot.createTable22("instant_mail", tablecolumn, connectionSpace);
			        
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("emailid");
					 ob.setDataName(emailid);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("subject");
					 ob.setDataName(subject);
					 insertData.add(ob);
				 
				 
					 ob=new InsertDataTable();
					 ob.setColName("mail_text");
					 ob.setDataName(mailtext);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("attachment");
					 ob.setDataName(attachment);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName("Avinash");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(sendDate);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(sendTime);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000:00:00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00:00:00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_mail",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult = LOGIN;
		}
		return flag;
	}
	
	public boolean addScheduledMessage(String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module,String smsDate,String smsTime,SessionFactory connectionSpace )
	{
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		boolean sessionFlag = true;
		if (sessionFlag)
		{
			try
			{
				@SuppressWarnings("unchecked")
				    String userName = "test";
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        TableColumes ob1 = new TableColumes();
			        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
			
					ob1.setColumnname("name");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("dept");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("mobno");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
			
					ob1 = new TableColumes();
					ob1.setColumnname("msg_text");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("flag");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("module");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("user");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
			        cot.createTable22("instant_msg",TableColumnName,connectionSpace);
					
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobno");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName(userName);
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(smsDate);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(smsTime);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000-00-00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00-00-00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_msg",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {returnResult = LOGIN;}
		return flag;
	
	}
	public boolean addMessage(String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module)
	 {
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				    String userName = (String)session.get("uName");
				    SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        TableColumes ob1 = new TableColumes();
			        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
			
					ob1.setColumnname("name");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("sub_type_id");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("mobile_no");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
			
					ob1 = new TableColumes();
					ob1.setColumnname("msg_text");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("flag");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("module");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("user");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
			        cot.createTable22("instant_msg",TableColumnName,connectionSpace);
					
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					
					 ob=new InsertDataTable();
					 ob.setColName("sub_type_id");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobile_no");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName(userName);
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000-00-00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00-00-00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_msg",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {returnResult = LOGIN;}
		return flag;
	}
	
	
	public boolean addMail(String name, String dept,String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module)
	 {
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				    String userName = (String)session.get("uName");
				    SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
			        
			          TableColumes tc = new TableColumes();
					  
			          tc.setColumnname("name");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("sub_type_id");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("email_id");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("subject");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("mail_text");
					  tc.setDatatype("text");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("flag");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("status");
					  tc.setDatatype("varchar(50)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("attachment");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("module");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("userName");
					  tc.setDatatype("varchar(25)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  @SuppressWarnings("unused")
					  boolean aa=  cot.createTable22("instant_mail", tablecolumn, connectionSpace);
			        
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("sub_type_id");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("email_id");
					 ob.setDataName(emailid);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("subject");
					 ob.setDataName(subject);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("mail_text");
					 ob.setDataName(mailtext);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("attachment");
					 ob.setDataName(attachment);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName(userName);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000:00:00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00:00:00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_mail",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult = LOGIN;
		}
		return flag;
	}
	
	public boolean addMessage(String mobone, String msg, String ticketno, String status, String statusflag, String module, SessionFactory connection)
	 {
		boolean flag = false;
			try {
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("ticket_no");
					 ob.setDataName(ticketno);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobno");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName("escalate");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_msg",insertData,connection);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
  
  
  public boolean addMail(String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module, SessionFactory connection)
	 {
		boolean flag = false;
		
			try {
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("ticket_no");
					 ob.setDataName(ticketno);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("emailid");
					 ob.setDataName(emailid);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("subject");
					 ob.setDataName(subject);
					 insertData.add(ob);
				 
				 
					 ob=new InsertDataTable();
					 ob.setColName("mail_text");
					 ob.setDataName(mailtext);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("attachment");
					 ob.setDataName(attachment);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName("escalate");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_mail",insertData,connection);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
  public boolean addMessageHR(String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module,SessionFactory connectionSpace)
	 {
		System.out.println("mobone >>addMessageHR>>"   +mobone);
		System.out.println("INSIDE THIS");
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		//boolean sessionFlag = ValidateSession.checkSession();
		
		//System.out.println("sessionFlag"+sessionFlag);
		 {
			try {
				//Map session = ActionContext.getContext().getSession();
				    //String userName = (String)session.get("uName");
				   // SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        TableColumes ob1 = new TableColumes();
			        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
			
					ob1.setColumnname("name");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("dept");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("mobno");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
			
					ob1 = new TableColumes();
					ob1.setColumnname("msg_text");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("flag");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("module");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("user");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
			        cot.createTable22("instant_msg",TableColumnName,connectionSpace);
					
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobno");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName("Shailendra");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000-00-00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00-00-00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_msg",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		return flag;
	}


	public boolean addMailHR(String name, String dept,String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module,SessionFactory connectionSpace)
	 {
		System.out.println(">>>>>zcxcxc>");
		System.out.println("emailid "  +emailid);
		System.out.println();
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		//boolean sessionFlag = ValidateSession.checkSession();
		 {
			try {
				//Map session = ActionContext.getContext().getSession();
				   // String userName = (String)session.get("uName");
				   // SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
			        
			          TableColumes tc = new TableColumes();
					  
			          tc.setColumnname("name");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("dept");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("emailid");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("subject");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("mail_text");
					  tc.setDatatype("text");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("flag");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("status");
					  tc.setDatatype("varchar(50)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("attachment");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("module");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("userName");
					  tc.setDatatype("varchar(25)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  @SuppressWarnings("unused")
					  boolean aa=  cot.createTable22("instant_mail", tablecolumn, connectionSpace);
			        
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("emailid");
					 ob.setDataName(emailid);
					 System.out.println("getDataName "   +ob.getDataName());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("subject");
					 ob.setDataName(subject);
					 insertData.add(ob);
				 
				 
					 ob=new InsertDataTable();
					 ob.setColName("mail_text");
					 ob.setDataName(mailtext);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("attachment");
					 ob.setDataName(attachment);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName("userName");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000:00:00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00:00:00");
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_mail",insertData,connectionSpace);
			         insertData.clear();
			flag =true;
		}
			catch (Exception e) 
			{
			e.printStackTrace();
		}
		}
		
		return flag;
	}
	// Add Sms to client and server.
			public boolean addMessageClientServer(String mobone, String msg, String ticketno, String status, String statusflag, String module, SessionFactory connection)
			 {
				boolean flag = false;
				boolean statusserver = false;
					try {
					       // CommonOperInterface cot = new CommonConFactory().createInterface();
					        CommonOperInterface cbt = new CommonConFactory().createInterface();
					        
					        /* InsertDataTable ob=new InsertDataTable();
						     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							 ob.setColName("ticket_no");
							 ob.setDataName(ticketno);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("mobno");
							 ob.setDataName(mobone);
							 insertData.add(ob);
						 
							 ob=new InsertDataTable();
							 ob.setColName("msg_text");
							 ob.setDataName(msg);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("flag");
							 ob.setDataName(statusflag);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("status");
							 ob.setDataName(status);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("module");
							 ob.setDataName(module);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("user");
							 ob.setDataName("escalate");
							 insertData.add(ob);
							 
							 
							 ob=new InsertDataTable();
							 ob.setColName("date");
							 ob.setDataName(DateUtil.getCurrentDateUSFormat());
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("time");
							 ob.setDataName(DateUtil.getCurrentTime());
							 insertData.add(ob);
					 
					         cot.insertIntoTable("instant_msg",insertData,connection);
					         insertData.clear();*/
					         
					         /** 
								 * Code to insert data on server database.
								 * */
								PreRequestserviceStub objstatus=new PreRequestserviceStub();
							//	InsertIntoTable obj=new InsertIntoTable();
								List<String> Tablecolumename=new ArrayList<String>(); 
								List<String> Tablecolumevalue=new ArrayList<String>(); 
								/*Tablecolumename.add("ticket_no");
								Tablecolumevalue.add(ticketno);*/
								Tablecolumename.add("mobno");
								Tablecolumevalue.add(mobone);
								Tablecolumename.add("msg_text");
								Tablecolumevalue.add(msg);
								Tablecolumename.add("flag");
								Tablecolumevalue.add(statusflag);
								Tablecolumename.add("status");
								Tablecolumevalue.add(status);
								Tablecolumename.add("module");
								Tablecolumevalue.add(module);
								Tablecolumename.add("user");
								Tablecolumevalue.add("escalate");
								Tablecolumename.add("date");
								Tablecolumevalue.add(DateUtil.getCurrentDateUSFormat());
								Tablecolumename.add("time");
								Tablecolumevalue.add(DateUtil.getCurrentTime());
								StringBuilder createTableQuery = new StringBuilder("INSERT INTO " +"instant_msg"+" ("); 

								int i=1;
								// append Column 
								for(String h: Tablecolumename)
									{
										if(i<Tablecolumename.size())
											createTableQuery.append(h+", ");
										else
											createTableQuery.append(h+")");
										i++;
									}
								 
								createTableQuery.append(" VALUES (");
								i=1;
								for(String h:Tablecolumevalue)
								{
									if(i<Tablecolumevalue.size())
										createTableQuery.append("'"+h+"', ");
									else
										createTableQuery.append("'"+h+"')");
									i++;
								}
								createTableQuery.append(" ;");
								//System.out.println(">>>>>>>>>>>>>"+createTableQuery);
							//	obj.setCreateTableQuery(createTableQuery.toString());
							    
							   //insert into local database.
							    int maxId = cbt.insertIntoTable(createTableQuery.toString(),connection);
							  // insert into Server database.
							//    statusserver= objstatus.insertIntoTable(obj).get_return();
					         if(statusserver)
					         {
					        	 flag =true; 
					         }
					         else
					         {
					        	 flag = false;
					         }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return flag;
			}
			//add sms on server and client...
			public boolean addScheduledMessageClientServer(String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module,String smsDate,String smsTime,SessionFactory connectionSpace )
			{
				boolean flag = false;
				@SuppressWarnings("unused")
				String returnResult= ERROR;
				boolean statusserver = false;
				boolean sessionFlag = true;
				if (sessionFlag)
				{
					try
					{
						@SuppressWarnings("unchecked")
						    String userName = "test";
					        //CommonOperInterface cot = new CommonConFactory().createInterface();
					        CommonOperInterface cbt = new CommonConFactory().createInterface();
					        TableColumes ob1 = new TableColumes();
					        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
					
							ob1.setColumnname("name");
							ob1.setDatatype("varchar(80)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("dept");
							ob1.setDatatype("varchar(80)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("mobno");
							ob1.setDatatype("varchar(15)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
					
							ob1 = new TableColumes();
							ob1.setColumnname("msg_text");
							ob1.setDatatype("varchar(250)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("flag");
							ob1.setDatatype("varchar(10)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("status");
							ob1.setDatatype("varchar(100)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("module");
							ob1.setDatatype("varchar(20)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("user");
							ob1.setDatatype("varchar(100)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("date");
							ob1.setDatatype("varchar(10)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("time");
							ob1.setDatatype("varchar(10)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("update_date");
							ob1.setDatatype("varchar(10)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							ob1 = new TableColumes();
							ob1.setColumnname("update_time");
							ob1.setDatatype("varchar(10)");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
							
							cbt.createTable22("instant_msg",TableColumnName,connectionSpace);
							
						   	/* InsertDataTable ob=new InsertDataTable();
						     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							 ob.setColName("name");
							 ob.setDataName(name);
							 insertData.add(ob);
							
							 ob=new InsertDataTable();
							 ob.setColName("dept");
							 ob.setDataName(dept);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("mobno");
							 ob.setDataName(mobone);
							 insertData.add(ob);
						 
							 ob=new InsertDataTable();
							 ob.setColName("msg_text");
							 ob.setDataName(msg);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("flag");
							 ob.setDataName(statusflag);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("status");
							 ob.setDataName(status);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("module");
							 ob.setDataName(module);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("user");
							 ob.setDataName(userName);
							 insertData.add(ob);
							 
							 
							 ob=new InsertDataTable();
							 ob.setColName("date");
							 ob.setDataName(smsDate);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("time");
							 ob.setDataName(smsTime);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("update_date");
							 ob.setDataName("0000-00-00");
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("update_time");
							 ob.setDataName("00-00-00");
							 insertData.add(ob);
					 
					         cot.insertIntoTable("instant_msg",insertData,connectionSpace);
					         insertData.clear();*/
					         
					         /** 
								 * Code to insert data on server database.
							 * */
								PreRequestserviceStub objstatus=new PreRequestserviceStub();
							//	InsertIntoTable obj=new InsertIntoTable();
								List<String> Tablecolumename=new ArrayList<String>(); 
								List<String> Tablecolumevalue=new ArrayList<String>(); 
								Tablecolumename.add("name");
								Tablecolumevalue.add(name);
								Tablecolumename.add("dept");
								Tablecolumevalue.add(dept);
								Tablecolumename.add("mobno");
								Tablecolumevalue.add(mobone);
								Tablecolumename.add("msg_text");
								Tablecolumevalue.add(msg);
								Tablecolumename.add("flag");
								Tablecolumevalue.add(statusflag);
								Tablecolumename.add("status");
								Tablecolumevalue.add(status);
								Tablecolumename.add("module");
								Tablecolumevalue.add(module);
								Tablecolumename.add("user");
								Tablecolumevalue.add(userName);
								Tablecolumename.add("date");
								Tablecolumevalue.add(smsDate);
								Tablecolumename.add("time");
								Tablecolumevalue.add(smsTime);
								Tablecolumename.add("update_date");
								Tablecolumevalue.add("0000-00-00");
								Tablecolumename.add("update_time");
								Tablecolumevalue.add("00-00-00");
								StringBuilder createTableQuery = new StringBuilder("INSERT INTO " +"instant_msg"+" ("); 

								int i=1;
								// append Column 
								for(String h: Tablecolumename)
									{
										if(i<Tablecolumename.size())
											createTableQuery.append(h+", ");
										else
											createTableQuery.append(h+")");
										i++;
									}
								 
								createTableQuery.append(" VALUES (");
								i=1;
								for(String h:Tablecolumevalue)
								{
									if(i<Tablecolumevalue.size())
										createTableQuery.append("'"+h+"', ");
									else
										createTableQuery.append("'"+h+"')");
									i++;
								}
								createTableQuery.append(" ;");
							//	obj.setCreateTableQuery(createTableQuery.toString());
							    
							   //insert into local database.
							    int maxId = cbt.insertIntoTable(createTableQuery.toString(),connectionSpace);
							  // insert into Server database.
							//    statusserver= objstatus.insertIntoTable(obj).get_return();
					         if(statusserver)
					         {
					        	 flag =true; 
					         }
					         else
					         {
					        	 flag = false;
					         }
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				else {returnResult = LOGIN;}
				return flag;
			}


}
