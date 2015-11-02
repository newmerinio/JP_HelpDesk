
package com.Over2Cloud.assetReminder.serviceFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class AssetServiceHelper
{
	@SuppressWarnings("rawtypes")
    List dataList=new ArrayList();
	@SuppressWarnings("rawtypes")
    public List getReminderData(SessionFactory connectionSpace)
	{
		 try 
		 {
			 String query;
			 query="SELECT ad.assetname,ard.frequency,ad.serialno,ad.assetbrand,ard.remindMode,ard.reminder_for,ar.id AS reminderId,ard.id AS " +
			 			"assetId,ard.dueDate,ard.dueTime,ard.uploadDoc,ar.reminder_code,ar.remind_date,ar.reminder_name,ard.escalation  " +
			 			"FROM asset_reminder_details AS ard INNER JOIN asset_detail ad on ad.id=ard.assetid INNER JOIN asset_reminder_data ar ON  " +
			 			"ar.asseReminder_id=ard.id WHERE ar.remind_date <='"+DateUtil.getNewDate("day",-1,DateUtil.getCurrentDateUSFormat())+"' AND ar.reminder_status=0 AND ar.reminder_code<>'S'";
			 dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
			 if(dataList.size()==0)
			 {
			 	 query="SELECT ad.assetname,ard.frequency,ad.serialno,ad.assetbrand,ard.remindMode,ard.reminder_for,ar.id AS reminderId,ard.id AS " +
			 			"assetId,ard.dueDate,ard.dueTime,ard.uploadDoc,ar.reminder_code,ar.remind_date,ar.reminder_name,ard.escalation  " +
			 			"FROM asset_reminder_details AS ard INNER JOIN asset_detail ad on ad.id=ard.assetid INNER JOIN asset_reminder_data ar ON  " +
			 			"ar.asseReminder_id=ard.id WHERE ar.remind_date <='"+DateUtil.getCurrentDateUSFormat()+"' AND ar.remind_time <='"+DateUtil.getCurrentTimeHourMin()+"' AND ar.reminder_status=0 AND ar.reminder_code<>'S'";
			 	 dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
			 }
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
	@SuppressWarnings("rawtypes")
    public List getEscalationData(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT ad.assetname,ard.frequency,ad.serialno,ad.assetbrand,ard.remindMode,ard.reminder_for,ard.dueDate,ard.dueTime,ard.escalation_level_1, " +
		 				 "ard.l1EscDuration,ard.escalation_level_2,ard.l2EscDuration,ard.escalation_level_3,ard.l3EscDuration,ard.escalation_level_4, " +
		 				 "ard.l4EscDuration,ard.current_esc_level,ard.id AS reminderId FROM asset_reminder_details AS ard INNER JOIN asset_detail ad on ad.id=ard.assetid " +
		 				 " WHERE ard.escalation='Y' AND ard.actionTaken='Pending' AND ard.current_esc_level<5  AND ard.escalate_date='"+DateUtil.getCurrentDateUSFormat()+"' AND ard.escalate_time<='"+DateUtil.getCurrentTimeHourMin()+"'";
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
	@SuppressWarnings("rawtypes")
    public List getRecurringData(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT ard.id,frequency,escalation,dueDate,dueTime,escalation_level_1,l1EscDuration,escalation_level_2,l2EscDuration," +
		 			"escalation_level_3,l3EscDuration,escalation_level_4,l4EscDuration,uploadDoc,uploadDoc1,ard.userName ,ad.assetname,ad.serialno " +
		 			" FROM asset_reminder_details AS ard INNER JOIN asset_detail AS ad ON ard.assetid=ad.id "+
		 			"WHERE dueDate<='"+DateUtil.getCurrentDateUSFormat()+"' AND dueTime<='"+DateUtil.getCurrentTimeHourMin()+"' AND actionTaken='Recurring' AND frequency!='OT'";
		 	//System.out.println("Query String "+query);
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
	@SuppressWarnings("rawtypes")
    public List getReminderDate(String asseReminder_id,SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT reminder_code,remind_date,remind_interval,id FROM asset_reminder_data WHERE asseReminder_id='"+asseReminder_id+"'";
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
	/*@SuppressWarnings("unchecked")
	public List getSnoozeData(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT cd.id,cd.frequency,cd.escalation,cd.dueDate,cd.dueTime,cd.escalation_level_1,cd.l1EscDuration,cd.escalation_level_2," +
		 			"cd.l2EscDuration,cd.escalation_level_3,cd.l3EscDuration,cd.escalation_level_4,cd.l4EscDuration,cd.snooze_date,cd.snooze_time," +
		 			"cd.current_esc_level,cd.reminder_for,cd.msg,ctn.taskName,cty.taskType,cd.taskBrief FROM compliance_details AS cd INNER JOIN compliance_task AS ctn ON " +
		 			"ctn.id=cd.taskName  INNER JOIN compl_task_type AS cty ON cty.id=ctn.taskType WHERE cd.snooze_date<='"+DateUtil.getCurrentDateUSFormat()+"' AND cd.snooze_time<='"+DateUtil.getCurrentTimeHourMin()+"' AND cd.actionTaken='Snooze'";
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
*/
	@SuppressWarnings("rawtypes")
    public List getSnoozeData(SessionFactory connectionSpace)
    {
         try 
         {
             String query="SELECT ard.id,ard.frequency,ard.escalation,ard.dueDate,ard.dueTime,ard.escalation_level_1,ard.l1EscDuration,ard.escalation_level_2, " +
                     "ard.l2EscDuration,ard.escalation_level_3,ard.l3EscDuration,ard.escalation_level_4,ard.l4EscDuration,ard.snooze_date,ard.snooze_time, " +
                     "ard.current_esc_level,ard.reminder_for,ad.assetname,ad.serialno,ad.assetbrand,ad.assetmodel,ard.comments FROM asset_reminder_details AS ard  " +
                     "INNER JOIN asset_detail AS ad ON ad.id=ard.assetid WHERE ard.snooze_date<='"+DateUtil.getCurrentDateUSFormat()+"' AND ard.snooze_time<='"+DateUtil.getCurrentTimeHourMin()+"' AND ard.actionTaken='Snooze'";
             dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
         } 
         catch (Exception e) 
         {
            e.printStackTrace();
         } 
         return dataList;
    }
	
	@SuppressWarnings("rawtypes")
    public List getTodayReminderFor(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT id,reminder_for FROM asset_reminder_details WHERE dueDate='"+DateUtil.getCurrentDateUSFormat()+"' AND actionTaken='Pending'";
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
	
	@SuppressWarnings("rawtypes")
    public List getTodayPendingData(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT ct.taskName,cd.frequency,cd.taskBrief,cd.msg,cd.id,cd.dueDate,cd.dueTime,cd.reminder_for,cd.actionStatus " +
		 			"FROM compliance_details AS cd INNER JOIN compliance_task ct ON ct.id=cd.taskName WHERE cd.dueDate='"+DateUtil.getCurrentDateUSFormat()+"' AND cd.actionTaken='Pending'";
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	
   public boolean updateAssetAccordingToFrequency(String id,String frequency,String remindDate,SessionFactory connectionSpace)
   {
	   boolean returnFlag=false;
	   CommonOperInterface cbt=new CommonConFactory().createInterface();
	   if(frequency!=null && id!=null && frequency!="" && id!="")
	   {
		   Map<String, Object> setVariables=new HashMap<String, Object>();
		   Map<String, Object> whereCondition=new HashMap<String, Object>();
		   String nextUpdateDate="";
		   whereCondition.put("id",id);
		   if(frequency.equals("OT"))
		   {
			  setVariables.put("reminder_status", "311");
			  returnFlag=cbt.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
	       }
		   else if (frequency.equals("D")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("day", 1, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   else if (frequency.equals("W")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("week", 1, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
			   System.out.println(">>>> "+nextUpdateDate);
		   }
		   else if (frequency.equals("BM")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("day", 15, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   else if (frequency.equals("M")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("month", 1, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   else if (frequency.equals("Q")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("month", 3, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   else if (frequency.equals("HY")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("month", 6, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   else if (frequency.equals("Y")) 
		   {
			   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
			   nextUpdateDate=DateUtil.getNewDate("year", 1, remindDate);
			   
			   setVariables.put("remind_date", nextUpdateDate);
		   }
		   returnFlag=cbt.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
	   }
	   return returnFlag;
   } 
   
   public boolean updateNextEscDate(String id,SessionFactory connectionSpace,int nextEscLevel,String nextEscDateTime)
   {
	   boolean returnFlag=false;
	   if(id!=null && id!="")
	   {
		    String arr[]= nextEscDateTime.split("#");
		    Session session = null;  
			Transaction transaction = null;  
			try 
			{  
		            session=connectionSpace.openSession();
					transaction = session.beginTransaction();
					Query query=session.createSQLQuery("UPDATE asset_reminder_details SET current_esc_level='"+nextEscLevel+"',escalate_date='"+arr[0]+"',escalate_time='"+arr[1]+"' WHERE id="+id);
					int count=query.executeUpdate();
					if(count>0)
						returnFlag=true;
					transaction.commit(); 
					
				}catch (Exception ex)
				{
					ex.printStackTrace();
					transaction.rollback();
				} 
	   }
	   return returnFlag;
   } 
   
   @SuppressWarnings({ "rawtypes", "unused" })
public List getReportTime(SessionFactory connectionSpace)
   {
	   List dataList=new ArrayList();
	   try
	   {
		   
		   dataList=new createTable().executeAllSelectQuery("SHOW TABLES LIKE 'compl_report_time'",connectionSpace);
		   if(dataList==null)
		   {
			   	CommonOperInterface cbt=new CommonConFactory().createInterface();
				InsertDataTable ob=null;
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				List <TableColumes> tableColume=new ArrayList<TableColumes>();
				
				TableColumes ob1=new TableColumes();
				
				ob1=new TableColumes();
				ob1.setColumnname("reportDate");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("reportTime");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("msg");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("frequency");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("dueDate");
				ob1.setDatatype("varchar(15)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("dueTime");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("configStatus");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				boolean status=cbt.createTable22("comp_excelupload_data",tableColume,connectionSpace);
		   }
		   
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return dataList;
   }
   
   @SuppressWarnings("rawtypes")
public List getReportData(SessionFactory connectionSpace,String reportLevel,String reportType,String deptId)
   {
	   List reportDataList=new ArrayList();
	   try
	   {
		   StringBuilder stringBuilder=new StringBuilder();
		   stringBuilder.append("SELECT comp.id,comp.frequency,comp.taskBrief,comp.reminder_for,comp.dueDate,comp.actionTaken,compTask.taskName,compType.taskType,dept.deptName ");
		   stringBuilder.append("FROM compliance_details AS comp ");
		   stringBuilder.append("INNER JOIN compliance_task AS compTask ON compTask.id=comp.taskName ");
		   stringBuilder.append("INNER JOIN department AS dept ON dept.id=compTask.departName ");
		   stringBuilder.append("INNER JOIN compl_task_type AS compType ON compType.id=compTask.taskType ");
		   stringBuilder.append("WHERE comp.actionTaken='Pending'");
		   /*if(reportLevel!=null && reportLevel.equalsIgnoreCase("1") && deptId!=null)
		   {
			   stringBuilder.append(" AND compTask.departName="+deptId);
		   }*/
		   
		   if(reportType!=null)
		   {
			   if(reportType.equalsIgnoreCase("D"))
			   {
				   stringBuilder.append(" AND dueDate='"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
			   else if(reportType.equalsIgnoreCase("W"))
			   {
				   stringBuilder.append(" AND dueDate BETWEEN '"+DateUtil.getNewDate("week",-7, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
			   else if(reportType.equalsIgnoreCase("M"))
			   {
				   stringBuilder.append(" AND dueDate BETWEEN '"+DateUtil.getNewDate("month",-30, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
		   }
		   
		   reportDataList=new createTable().executeAllSelectQuery(stringBuilder.toString(),connectionSpace);
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   return reportDataList;
   }
   
   
   @SuppressWarnings("rawtypes")
public List getTodayActionData(SessionFactory connectionSpace,String reportLevel,String reportType,String deptId)
   {
	   List actionDataList=new ArrayList();
	   try
	   {
		   StringBuilder stringBuilder=new StringBuilder();
		   stringBuilder.append("SELECT comp.id,comp.frequency,comp.taskBrief,comp.reminder_for,comp.dueDate,compReport.actionTaken,compTask.taskName,compType.taskType,dept.deptName,usac.name,compReport.comments ");
		   stringBuilder.append("FROM compliance_report AS compReport ");
		   stringBuilder.append("INNER JOIN compliance_details AS comp ON comp.id=compReport.complID ");
		   stringBuilder.append("INNER JOIN compliance_task AS compTask ON compTask.id=comp.taskName ");
		   stringBuilder.append("INNER JOIN department AS dept ON dept.id=compTask.departName ");
		   stringBuilder.append("INNER JOIN compl_task_type AS compType ON compType.id=compTask.taskType ");
		   stringBuilder.append("INNER JOIN useraccount AS usac ON usac.userID=compReport.userName ");
		   stringBuilder.append("WHERE compReport.actionTaken<>'Pending'");
		   /*if(reportLevel!=null && reportLevel.equalsIgnoreCase("1") && deptId!=null)
		   {
			   stringBuilder.append(" AND compTask.departName="+deptId);
		   }*/
		   
		   if(reportType!=null)
		   {
			   if(reportType.equalsIgnoreCase("D"))
			   {
				   stringBuilder.append(" AND compReport.actionTakenDate='"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
			   else if(reportType.equalsIgnoreCase("W"))
			   {
				   stringBuilder.append(" AND compReport.actionTakenDate BETWEEN '"+DateUtil.getNewDate("week",-7, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
			   else if(reportType.equalsIgnoreCase("M"))
			   {
				   stringBuilder.append(" AND compReport.actionTakenDate BETWEEN '"+DateUtil.getNewDate("month",-30, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			   }
		   }
		   actionDataList=new createTable().executeAllSelectQuery(stringBuilder.toString(),connectionSpace);
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   return actionDataList;
   }
   @SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCompIdPrefix_Suffix(String deptId,String frq,SessionFactory connectionSpace)
   	{
   		List dataList=new ArrayList();
   		String complSeries = new HelpdeskUniversalHelper().getTicketNo(deptId,"COMPL", connectionSpace);
   		if(!frq.equalsIgnoreCase("-1"))
		{
		  if(frq.equalsIgnoreCase("OT") || frq.equalsIgnoreCase("BM") || frq.equalsIgnoreCase("HY"))
		  {
			  frq=String.valueOf(frq.charAt(0));
		  }
		}
   		if(complSeries!=null)
   		{
   			String prefix=complSeries.substring(0, 3);
   			prefix=prefix.concat(frq);
   			dataList.add(prefix);
   			String suffix=complSeries.substring(4, complSeries.length());
   			dataList.add(suffix);
   		}
   		return 	dataList;
   	}

}
