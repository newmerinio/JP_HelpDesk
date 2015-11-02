package biomatricsIntegration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.leaveManagement.LeaveHelper;

import biomatricsIntegration.access.AccessDbCon;

public class DeviceDataIntegration implements Runnable 
{
	private static SessionFactory connection=null;
	

@SuppressWarnings("static-access")
public DeviceDataIntegration(SessionFactory connection)
  {
	this.connection=connection;
	
  }

	@SuppressWarnings("unused")
	public void run() {

		try {
			// Access Database Connection...
			Connection accessCon = AccessDbCon.getCon();
			
			CommonOperInterface cot = new CommonConFactory().createInterface();
			List<TableColumes> tableColume = new ArrayList<TableColumes>();
			TableColumes ob1 = null;
			
				ob1 = new TableColumes();
				ob1.setColumnname("Employee_ID");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				
				ob1 = new TableColumes();
				ob1.setColumnname("Employee_Name");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("CheckIn");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("CheckOut");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("Entrydate");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				cot.createTable22("emp_data", tableColume,connection);
			
			////System.out.println(accessCon);
			// Getting connection from 1_clouddb...
			String query = "select ed.Employee_ID,ed.Employee_Name,ta.CheckIn,ta.CheckOut,ta.Entrydate"
					+ "  from Employee_Details as ed "
					+ " INNER JOIN tblAttendance as ta on ed.Employee_ID = ta.empId ";
			
			//System.out.println(query);
			PreparedStatement accessPs = accessCon.prepareStatement(query);
			ResultSet rs = accessPs.executeQuery();
			
			String CheckIn=null;
			String CheckOut=null;
			String empId=null;
			String dateEntry=null;
			String emp_Name=null;
			
			while(rs != null &&rs.next())
			{
				empId=rs.getString(1);
				emp_Name=rs.getString(2);
				CheckIn=rs.getString(3);
				CheckOut=rs.getString(4);
				dateEntry= rs.getString(5);
				empId=empId.substring(1);
				if (CheckIn!=null) 
				{
					CheckIn=CheckIn.substring(10, 16);
				}
				else
				{
					CheckIn="00:00";
				}
				if (CheckOut!=null) 
				{
					CheckOut=CheckOut.substring(10, 16);
				}
				else 
				{
					CheckOut="00:00";
				}
				 dateEntry=dateEntry.substring(0, 10);
				 String day=null;
				  String totalTime=null;
				  String finalIncommin=null;
				  String finalOutgoing=null;
				  String workingFlag=null;
				  String inTime=null;
				  String outTime=null;
				 LeaveHelper LH=new LeaveHelper();
				 String empIDS[]=LH.getEmpId1(empId, connection);
				 //System.out.println("EMP IDSSS  :::  "+empIDS);
				 String contactID=LH.getContactIdForExcel("LM","1", empIDS[0], empIDS[1], connection);
				 
				 //System.out.println("contactID :::::" +contactID);
				 
					String query1="select ftime,ttime from time_slot where empname='"+contactID+"'";
					//System.out.println("queryTTTTTTT  :::" +query1);
					List timming=cot.executeAllSelectQuery(query1, connection);
					if(timming!=null && timming.size()>0)
					{
						Object[] object=null;
						for (Iterator iterator = timming.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							inTime=object[0].toString();
							outTime=object[1].toString();
						}
					}
				 
				 int i=0;
				 if(contactID==null ||empIDS[i]==null || inTime== null)
					{
					  List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						
							//System.out.println("cot ::: " +cot);
							
							ob = new InsertDataTable();
							ob.setColName("Employee_ID");
							ob.setDataName(empId);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("Employee_Name");
							ob.setDataName(emp_Name);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("CheckIn");
							ob.setDataName(CheckIn);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("CheckOut");
							ob.setDataName(CheckOut);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("Entrydate");
							ob.setDataName(dateEntry);
							insertData.add(ob);
							//System.out.println("insertData ::: " +insertData);
							

						new createTable().insertIntoTable("emp_data",insertData,connection);
					}
				  
				  
			     day=DateUtil.findDayFromDate(DateUtil.convertDateToIndianFormat(dateEntry));
				
				if(day!=null)
				{
					if(day.equalsIgnoreCase("Sunday")&& CheckIn.equalsIgnoreCase("00:00")&& CheckOut.equalsIgnoreCase("00:00"))
					{
						workingFlag="Holiday";
					}
					else
					{
						workingFlag="Working";
					}
				}
				
				if(CheckIn.equalsIgnoreCase("00:00")  && CheckOut.equalsIgnoreCase("00:00"))
				{
					totalTime="NA";
				}
				
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				
				
				
				//System.out.println("chck out" + CheckOut);
				System.out.println("@@@@@ "+inTime+" >>>>> "+CheckIn+" >>> "+CheckOut);
				if(CheckIn!=null && CheckOut.equalsIgnoreCase("00:00"))
				{
					totalTime="4:30";
					//System.out.println("totalTime ::::::  " +totalTime);
				}
				else
				{
					
					totalTime=DateUtil.findDifferenceTwoTime(CheckIn.trim(),CheckOut.trim());
				
				}
			
				 if(CheckIn.equalsIgnoreCase("00:00") && CheckIn!=null && CheckOut.equalsIgnoreCase("00:00") && CheckOut!=null)
				 {
			        finalIncommin="CV";
			        finalOutgoing="CV";
			        totalTime=DateUtil.findDifferenceTwoTime(CheckIn.trim(),CheckOut.trim());
			        //System.out.println("totalTime :::>>>>"  +totalTime);
				 }
				
				 if(inTime!=null && CheckIn!=null &&CheckIn.equalsIgnoreCase(inTime.trim()) && outTime!=null && CheckOut!=null && CheckOut.equalsIgnoreCase(outTime.trim()) )
				 {
						finalIncommin="On Time";
						finalOutgoing="On Time";
					}
				 if(inTime!=null && CheckIn.equalsIgnoreCase(inTime.trim())&& CheckIn!=null  && CheckOut.equalsIgnoreCase("00:00"))
				 {
					 //totalTime=DateUtil.findverageTwoTime(CheckIn.trim(),CheckOut.trim());
					 //System.out.println("totalTime checkgg " +totalTime);
					
					}
				 else if(CheckIn.equalsIgnoreCase("00:00") && inTime!=null && outTime!=null && CheckOut.equalsIgnoreCase("00:00")&& day.equalsIgnoreCase("Sunday"))
				 {
						finalIncommin="Holiday";
						finalOutgoing="Holiday";
					}
					
		     else if(CheckIn.equalsIgnoreCase("00:00")&& inTime!=null && outTime!=null && CheckOut.equalsIgnoreCase("00:00") && day.equalsIgnoreCase("Sunday"))
					{
						 finalIncommin="ABSENT";
						 finalOutgoing="ABSENT";
					 }
					else if(CheckIn!=null && inTime!=null)
					 {
						 String incomingStatus=new LeaveHelper().getIncommingStatusForBiometrics( CheckIn.trim(), inTime.trim());
						 if(incomingStatus.equalsIgnoreCase("0:0"))
						 {
							 finalIncommin="On Time"; 
						 }
						 else
						 {
							 finalIncommin=new LeaveHelper().getFinalIncommingstatusForBiometrics(incomingStatus); 
						 }
						 
								if (CheckOut != null && outTime != null)
								{
						 String outgoingStatus=new LeaveHelper().getIncommingStatusForBiometrics( CheckOut.trim(), outTime.trim());
						 if(outgoingStatus.equalsIgnoreCase("0:0"))
						 {
							 finalOutgoing="On Time"; 
						 }
						 else
						 {
							 finalOutgoing=new LeaveHelper().getFinalIncommingstatusForBiometrics(outgoingStatus); 
							 System.out.println("finalOutgoing + " +finalOutgoing);
						 }
					 
					 }
							}
					
				  // Getting contact id from empId  *****
				
				 if(inTime!=null || outTime!=null)
				 {
				   List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				      InsertDataTable ob=null;
				         ob=new InsertDataTable();
				         ob.setColName("date1");
						 ob.setDataName(dateEntry);
						 insertData.add(ob);
						 
				         if(day!=null)
				         {
						 ob=new InsertDataTable();
						 ob.setColName("day");
						 ob.setDataName(day);
						 insertData.add(ob);
						 //System.out.println("insertData     " + insertData);
					      }
					
						 ob=new InsertDataTable();
						 ob.setColName("working");
						 ob.setDataName(workingFlag);
						 insertData.add(ob);
						
						 ob=new InsertDataTable();
						 ob.setColName("timeslot");
						 //System.out.println(" insertData for timeslot" +ob.getDataName());
						 ob.setDataName(inTime+" to "+outTime);
						 insertData.add(ob);
						 //System.out.println(" insertData for timeslot" + insertData);
						
						 ob=new InsertDataTable();
				         ob.setColName("in_time");
						 ob.setDataName(CheckIn);
						 insertData.add(ob);
						   
						 ob=new InsertDataTable();
				         ob.setColName("out_time");
						 ob.setDataName(CheckOut);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("beforetime");
						 ob.setDataName(finalIncommin);
						 insertData.add(ob);
						 //System.out.println(" insertData for beforetime" + insertData);
						
						 ob=new InsertDataTable();
						 ob.setColName("aftertime");
						 ob.setDataName(finalOutgoing);
						 insertData.add(ob);
						 //System.out.println(" insertData for aftertime" + insertData);
						
						 ob=new InsertDataTable();
						 ob.setColName("totalhour");
						 ob.setDataName(totalTime);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("empname");
						 ob.setDataName(contactID);
						 insertData.add(ob);
						 //System.out.println("insertData SUMITIIII     " + insertData);
				 
					boolean status= cot.insertIntoTable("attendence_record",insertData,connection);
				 }
				//System.out.println("--------------------------------" );
				//System.out.println("EMPLOYEE ID :::  "+empId);
				//System.out.println("CheckIn :::  "+CheckIn);
				//System.out.println("CheckOut :::  "+CheckOut);
				//System.out.println("dateEntry :::  "+dateEntry);
				
				//System.out.println("--------------------------------");
			
			}
			} 
		catch (Exception E) 
		{
			E.printStackTrace();
		}

	}

	public static void main(String[] args)
	{
		 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		 Thread serviceRunner = new Thread(new DeviceDataIntegration(connection),"Device");
		 serviceRunner.start();
	}
}
