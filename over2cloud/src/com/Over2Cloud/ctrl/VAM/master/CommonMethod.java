package com.Over2Cloud.ctrl.VAM.master;


import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.sun.org.apache.bcel.internal.generic.RETURN;


public class CommonMethod {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	public static Map<Integer,String> allLocationList(SessionFactory connectionSpace)
	{
		Map<Integer,String>locationlist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,name from location order by name");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 locationlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return locationlist;
	}
	public static Map<Integer,String> allDepartmentList(SessionFactory connectionSpace)
	{
		Map<Integer,String>deptlist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,deptName from department order by deptName");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 deptlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return deptlist;
	}
	public static Map<Integer,String> allVhDepartmentList(SessionFactory connectionSpace)
	{
		Map<Integer,String>deptlist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,jlocation_name from vh_location_jbm order by jlocation_name");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 deptlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return deptlist;
	}
	

	//function for count record on basic record;
	public static String countRecord(String query,SessionFactory connection )
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
        String countRecord=null;
		List listData;
		 listData=cbt.executeAllSelectQuery(query,connection);
			if(listData!=null)
			{
			 for(Object obj:listData)
			    {
				 countRecord=obj.toString();
			    }
			}
		
		
	  return countRecord;	
	}
	
	
	
	
	
	public static Map<Integer,String> allJLocationList(SessionFactory connectionSpace)
	{
		Map<Integer,String>deptlist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,jlocation_name from vh_location_jbm order by jlocation_name");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 deptlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return deptlist;
	}
	
	public static Map<Integer,String> alleEmployeeList(SessionFactory connectionSpace, String DeptID)
	{
		Map<Integer,String>emplist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select emb.id,emb.empName,emb.mobOne,emb.emailIdOne,elmp.location,gld.gate from employee_basic as emb left join emp_location_map_details as elmp on elmp.deptName = emb.deptname  left join location as loc on loc.id = elmp.location left join gate_location_details as gld on gld.id = elmp.gate where emb.deptname = '"+DeptID+"' and emb.groupId = '1'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 StringBuilder SB = new StringBuilder();
				 Object[] obdata=(Object[])it.next();
				 SB.append(obdata[1].toString());
				 SB.append("-");
				 SB.append(obdata[2].toString());
				 SB.append("-");
				 SB.append(obdata[4]);
				 SB.append("-");
				if(obdata[5] != null)
				{
					 SB.append(obdata[5].toString());
				}
				 emplist.put((Integer)obdata[0], SB.toString());
			}
		}
		return emplist;
	}
	/** 
	 * For getting only Employee.....
	 * */
	public static Map<Integer,String> allEmployeeOnly(SessionFactory connectionSpace, String DeptID)
	{
		Map<Integer,String>emplist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select emb.id,emb.empName,emb.mobOne,emb.emailIdOne,elmp.location,gld.gate from employee_basic as emb left join emp_location_map_details as elmp on elmp.deptName = emb.deptname left join location as loc on loc.id = elmp.location left join gate_location_details as gld on gld.id = elmp.gate where emb.deptname = '"+DeptID+"' and emb.groupId = '1'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 StringBuilder SB = new StringBuilder();
				 Object[] obdata=(Object[])it.next();
				 SB.append(obdata[1].toString());
				 emplist.put((Integer)obdata[0], SB.toString());
			}
		}
		return emplist;
	}
	
	public static Map<Integer,String> employeeList(SessionFactory connectionSpace, String DeptID)
	{
		Map<Integer,String>emplist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,empName,mobOne,emailIdOne from employee_basic where deptname = '"+DeptID+"' and groupId = '1'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 emplist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return emplist;
	}
	
	public static Map<Integer,String> allMappedGateList(SessionFactory connectionSpace, String locationID)
	{
		Map<Integer,String>gatelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,gate from gate_location_details where location = '"+locationID+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 gatelist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return gatelist;
	}
	
	public static String alertTime(SessionFactory connectionSpace, String purpose)
	{
		String alert=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select alert_after from purpose_admin where id = '"+purpose+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			alert = data.get(0).toString();
		}
		return alert;
	}
	public static Map<Integer,String> allPurpose(SessionFactory connectionSpace)
	{
		Map<Integer,String>purposelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,purpose from purpose_admin where purpose_for='Visitor' order by purpose");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 purposelist.put((Integer)obdata[0],obdata[1].toString());
				 //alertlist.add(obdata[2].toString());
			}
		}
		return purposelist;
	}
	public static Map<Integer,String> allVehiclePurpose(SessionFactory connectionSpace)
	{
		Map<Integer,String>purposelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,purpose from purpose_admin where purpose_for='Vehicle' order by purpose");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 purposelist.put((Integer)obdata[0],obdata[1].toString());
				 //alertlist.add(obdata[2].toString());
			}
		}
		return purposelist;
	}
	public static Map<Integer,String> allVisitorOrganization(SessionFactory connectionSpace)
	{
		int i=0;
		Map<Integer,String>vistororglist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("SELECT  id, visitor_company FROM prerequest_details group by visitor_company");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				Object[] obdata =(Object[])it.next();
				 vistororglist.put((Integer)obdata[0],obdata[1].toString());
				 i=((Integer)obdata[0]).intValue();
			}
			if(vistororglist.size()>0){
			vistororglist.put((i+1), "New");
			}
		}else
		{
			vistororglist.put(0, "New");
		}
		return vistororglist;
	}
	public static Map<Integer,String> allReqVisitor(SessionFactory connectionSpace, String orgname)
	{
		int i=0;
		Map<Integer,String>reqvisitorlist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		//code by azad 19may 2014
		query.append("SELECT  id,visitor_name FROM prerequest_details where visitor_company='"+orgname+"' group by visitor_name");
		//query.append("SELECT DISTINCT id, visitor_name FROM prerequest_details where visitor_company='"+orgname+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 reqvisitorlist.put((Integer)obdata[0],obdata[1].toString());
				 i = ((Integer)obdata[0]).intValue();
			}
			if(reqvisitorlist.size()>0){ 
			reqvisitorlist.put(i+1,"New");
			}
		}else
		{
			reqvisitorlist.put(0,"New");
		}
		return reqvisitorlist;
	}

	public static Map<Integer,String> allReqVisitorMob(SessionFactory connectionSpace, String vistorname)
	{
		int i= 0;
		Map<Integer,String>vismobilelist=new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		//code by azad 19may 2014
		query.append("SELECT  id,visitor_mobile FROM prerequest_details where visitor_name='"+vistorname+"' group by visitor_mobile");
		//query.append("SELECT  id, visitor_mobile FROM prerequest_details where visitor_name='"+vistorname+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vismobilelist.put((Integer)obdata[0],obdata[1].toString());
				 i = ((Integer)obdata[0]).intValue();
				 //alertlist.add(obdata[2].toString());
			}
			if(vismobilelist.size()>0){
			vismobilelist.put(i+1, "New");
			}
		}
		else
		{
			vismobilelist.put(0, "New");
		}
		return vismobilelist;
	}
	public static String getDeptName(SessionFactory connectionSpace, String empname)
	{
		String dptName=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select deptName from department where userName='"+empname+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			dptName = data.get(0).toString();
		}
		return dptName;
	}
	
	public int getSerialNumber(SessionFactory connectionSpace){
		int srNo = 0;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(sr_number) from prerequest_details");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			srNo = Integer.parseInt(data.get(0).toString()) ;
		}
		return srNo;
	}
	public String getVisitorSerialNumber(SessionFactory connectionSpace){
		String visitorSrNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(cast(SUBSTRING(sr_number, 6, 10) as unsigned)) from visitor_entry_details");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			visitorSrNo = data.get(0).toString() ;
		}
		return visitorSrNo;
	}
	public String getVehicleTripNumber(SessionFactory connectionSpace){
		String vhtipNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(cast(trip_no as unsigned)) from vehicle_entry_details");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			vhtipNo = data.get(0).toString() ;
		}
		return vhtipNo;
	}
	public String getVehicleSerialNumber(SessionFactory connectionSpace){
		String vehicleSrNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(cast(SUBSTRING(sr_number, 6, 10) as unsigned)) from vehicle_entry_details");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			vehicleSrNo = data.get(0).toString() ;
		}
		return vehicleSrNo;
	}
	public String getPreReqVisitorSerialNumber(SessionFactory connectionSpace){
		String visitorSrNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(cast(SUBSTRING(sr_number, 6, 10) as unsigned)) from prerequest_details");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			visitorSrNo = data.get(0).toString() ;
		}
		return visitorSrNo;
	}
	public String getSeries(SessionFactory connectionSpace, String prefix){
		String visitorSrNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select series from vam_idseries_details where prefix ='"+prefix+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			visitorSrNo = data.get(0).toString() ;
		}
		return visitorSrNo;
	}
	
	public static String getEmailId(SessionFactory connectionSpace, String empName)
	{
		String email=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select empbasic.emailIdOne from employee_basic as empbasic left join useraccount as useracct on useracct.id=empbasic.useraccountid where useracct.name = '"+empName+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			email = data.get(0).toString();
		}
		return email;
	}
	public static String getVisitorEmailId(SessionFactory connectionSpace, String empName){
		String empEmail=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select emailIdOne from employee_basic where empName= '"+empName+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			empEmail = data.get(0).toString();
		}
		return empEmail;
	}
	public static String getDeptForId(SessionFactory connectionSpace, String deptId)
	{
		String dept=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select deptName from department where id = '"+deptId+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			dept = data.get(0).toString();
		}
		return dept;
	}
	public static String getLocationJBMForId(SessionFactory connectionSpace, String deptId)
	{
		String dept=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select jlocation_name from vh_location_jbm where id = '"+deptId+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			dept = data.get(0).toString();
		}
		return dept;
	}
	public static String getMobileNo(SessionFactory connectionSpace, String empName)
	{
		String mobileno = null;
		CommonOperInterface commonoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select empbasic.mobOne from employee_basic as empbasic left join useraccount as useracct on useracct.id=empbasic.useraccountid where useracct.name='"+empName+"'");
		List mobData = commonoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(mobData!=null && mobData.size()>0)
		{
			mobileno = mobData.get(0).toString();
		}
		return mobileno;
	}
	public static String getEmpGateName(SessionFactory connectionSpace, String empName)
	{
		String mobileno = null;
		CommonOperInterface commonoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select gld.gate from emp_location_map_details as elpd left join gate_location_details as gld on elpd.gate = gld.id where elpd.empName = (select empbasic.id from employee_basic as empbasic left join useraccount as useracct on useracct.id=empbasic.useraccountid where useracct.name='"+empName+"')");
		System.out.println(">>>>>>>>"+query.toString());
		List mobData = commonoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(mobData!=null && mobData.size()>0)
		{
			mobileno = mobData.get(0).toString();
		}
		return mobileno;
	}
	
	public static String getLocationOnId(SessionFactory connectionSpace,String locID){
		String locName = null;
		CommonOperInterface commnintfcObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select name from location where id = '"+locID+"'");
		List locData = commnintfcObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(locData != null && locData.size()>0){
			locName = locData.get(0).toString();
		}
		return locName;
	}
	public static String getLocAndGateVal(SessionFactory connectionSpace,String locID, String gatename){
		String locandgateName = null;
		CommonOperInterface commnintfcObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select loc.name, gld.code from gate_location_details as gld left join location as loc on gld.location = loc.id where gld.location = '"+locID+"' and gld.gate ='"+gatename+"'");
		System.out.println(":::::::both QQQQQ>>"+query.toString());
		List locData = commnintfcObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(locData != null && locData.size()>0){
			for(Iterator it=locData.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 locandgateName = obdata[0].toString()+"#"+obdata[1].toString();
			}
			//locandgateName = locData.get(0).toString();j
		}
		return locandgateName;
	}
	public static String getPreRequestData(SessionFactory connectionSpace, String otp){
		String prerequestdatalist = null;
		String date = DateUtil.getCurrentDateIndianFormat();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select visitor_name,visitor_mobile,visitor_company,possession,address from prerequest_details where visit_date = '"+date+"' and one_time_pwd = '"+otp+"'");
		System.out.println("otp query**************************************"+query);
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{ String temp="";
	      Object[] obdata=null;
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				  obdata=(Object[])it.next();
				// prerequestdatalist = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString();
			}
		
		for(int i=0;  i<obdata.length; i++)
		{  
			if(obdata[i] != null)
			{   
				 temp = temp+obdata[i].toString()+"#";
			}
			else
			{   temp = temp+"NA".toString()+"#";
				
			}
		}
		prerequestdatalist=temp;
		}
		return prerequestdatalist;
	}
	public static String getPreVisitedVisitorData(SessionFactory connectionSpace, String visitormob){
		String previsiteddatalist = null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select visitor_name,visitor_mobile,visitor_company,possession,address from visitor_entry_details where visitor_mobile = '"+visitormob+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{    String temp="";
		     Object[] obdata=null;
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				  obdata=(Object[])it.next();
				 // previsiteddatalist = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString();
			}
			for(int i=0;  i<obdata.length; i++)
			{  
				if(obdata[i] != null)
				{   
					 temp = temp+obdata[i].toString()+"#";
				}
				else
				{   temp = temp+"NA".toString()+"#";
					
				}
			}
			previsiteddatalist=temp;
			
		}
		return previsiteddatalist;
	}
	public static List<Object> getAllVisitorData(SessionFactory connectionSpace){
		List<Object> previsiteddatalist = new ArrayList<Object>();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select ved.visitor_name,ved.visitor_mobile,ved.visitor_company,ved.address,purposeadmn.purpose,dept.deptName,ved.image from visitor_entry_details as ved left join purpose_admin as purposeadmn on ved.purpose=purposeadmn.id left join department as dept on ved.deptName=dept.id where purposeadmn.purpose is not null");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object obdata=it.next();
				 previsiteddatalist.add(obdata);
				 //previsiteddatalist = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString();
			}
		}
		return previsiteddatalist;
	}
	public static String[] getServerData(SessionFactory connectionSpace, String shortcode){
		String[] clientsystemrecords = new String[3];
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select ip, password, name from location where code = '"+shortcode+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			Object[] arrobj = (Object[])data.get(0); 
			clientsystemrecords[0] = arrobj[0].toString();
			clientsystemrecords[1] = arrobj[1].toString();
			clientsystemrecords[2] = arrobj[3].toString();
		}
		return clientsystemrecords;
	}
	public static String[] getRecordOnMobileNo(SessionFactory connectionSpace, String mobnumber){
		String[] visitorandemprec = new String[3];
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select visitor_mobile, visitor_name, visited_person from visitor_entry_details where visited_mobile = '"+mobnumber+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			Object[] arrobj = (Object[])data.get(0); 
			visitorandemprec[0] = arrobj[0].toString();
			visitorandemprec[1] = arrobj[1].toString();
			visitorandemprec[2] = arrobj[3].toString();
		}
		return visitorandemprec;
	}
	
	public static boolean updateVisitorentryTable(SessionFactory connectionSpace, String srnumber, String mobileno){
		boolean flag = false;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("update visitor_entry_details set accept='YES' where sr_number ='"+srnumber.trim()+"' and visited_mobile IN(select mobOne from employee_basic where mobOne = '"+mobileno.trim()+"')");
		commnoprObj.updateTableColomn(connectionSpace, query);
		flag = true;
		return flag;
	}
	public static boolean updateVisitorReject(SessionFactory connectionSpace, String srnumber, String mobileno, String timeout){
		boolean flag = false;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("update visitor_entry_details set reject='YES', status='1' ,timeout='"+ timeout+"'"+" where sr_number ='"+srnumber.trim()+"' and visited_mobile IN(select mobOne from employee_basic where mobOne = '"+mobileno.trim()+"')");
		commnoprObj.updateTableColomn(connectionSpace, query);
		flag = true;
		return flag;
	}
	// To get Gate name for logged-in user. 
	public static Map<Integer,String> allSelectedGateList(SessionFactory connectionSpace, String username)
	{
		Map<Integer,String>gatelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select elmd.id, gatelocdet.gate from gate_location_details as gatelocdet join emp_location_map_details as elmd on elmd.gate = gatelocdet.id where elmd.userName ='"+username+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 gatelist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return gatelist;
	}
	public static String getPreVehicleEntryData(SessionFactory connectionSpace, String drivermob){
		String prevehicleEntrydatalist = null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select ved.driver_name,ved.vehicle_model,ved.vehicle_number, ved.trip_no, ved.vehicle_owner, ved.vh_owner_mob, ved.location, ved.invoce_no, ved.destination,  ved.sr_number  from vehicle_entry_details as ved   where driver_mobile ='"+drivermob+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{       Object[] obdata = null;
		        String temp ="";
		
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 obdata=(Object[])it.next();
				// prevehicleEntrydatalist = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString()+"#"+obdata[5].toString()+"#"+obdata[6].toString()+"#"+obdata[7].toString();
			}
			for(int i=0;  i<obdata.length; i++)
			{  
				if(obdata[i] != null)
				{   
					 temp = temp+obdata[i].toString()+"#";
				}
				else
				{   temp = temp+"NA".toString()+"#";
					
				}
			}
			prevehicleEntrydatalist=temp;
		}
		return prevehicleEntrydatalist;
	}
	public static Map<Integer,String> allVendor(SessionFactory connectionSpace)
	{
		Map<Integer,String>vendorlist = new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,vender_type from vendor_admin_details order by vender_type");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vendorlist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return vendorlist;
	}
	public static Map<Integer,String> allVendorCompName(SessionFactory connectionSpace)
	{
		Map<Integer,String>vendorcomplist = new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,brief from vendor_admin_details order by brief");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vendorcomplist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return vendorcomplist;
	}
	public static Map<Integer,String> allVendorCompany(SessionFactory connectionSpace)
	{
		Map<Integer,String>vendorcmplist = new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,brief from vendor_admin_details order by brief");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vendorcmplist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return vendorcmplist;
	}
	
	public static Map<Integer,String> vendorNameList(SessionFactory connectionSpace, String vendorcompID)
	{
		Map<Integer,String>vandornamelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id,vender_name from front_office_details where brief = '"+vendorcompID+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vandornamelist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return vandornamelist;
	}
	public static Map<Integer,String> vendorCompNameList(SessionFactory connectionSpace, String vendortypeName)
	{
		Map<Integer,String>vandornamelist=new HashMap<Integer, String>();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id, brief from vendor_admin_details where vender_type = '"+vendortypeName+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vandornamelist.put((Integer)obdata[0],obdata[1].toString());
			}
		}
		return vandornamelist;
	}
	public static String getPrefixData(SessionFactory connectionSpace, String loc){
		String prefix = null;
		String date = DateUtil.getCurrentDateIndianFormat();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select code from gate_location_details where location = '"+loc+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object obdata=(Object)it.next();
				 prefix = obdata.toString();
			}
		}
		return prefix;
	}
	public static String getInstantVisitorStatus(SessionFactory connectionSpace, String id){
		String previsiteddatalist=null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		try
    {
			query.append("select accept,reject from visitor_entry_details where one_time_password IS NULL and id= '"+id+"'");
			List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 if(obdata[0] !=null)
					 {
						 previsiteddatalist = "Accepted";
						 System.out.println(obdata[0].toString());
					 }
					 else if(obdata[1] !=null)
					{
						 previsiteddatalist = "Rejected";
						 System.out.println(obdata[1].toString());
					}
					else
					{
						 previsiteddatalist = "Pending";
					}
				}
			}
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
		
		return previsiteddatalist;
	}
	public static String locationGateForEmp(SessionFactory connectionSpace, String empnameId){
		String locationGatelist = null;
		String date = DateUtil.getCurrentDateIndianFormat();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select loc.name, gld.gate from emp_location_map_details as elmd left join gate_location_details as gld on elmd.gate = gld.id left join location as loc on elmd.location = loc.id where elmd.empName = '"+empnameId+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 locationGatelist = obdata[0].toString()+"#"+obdata[1].toString();
			}
		}
		return locationGatelist;
	}
	public static String getPreviousVehicleData(SessionFactory connectionSpace, String vehicleno){
		String vehicledatalist = null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select ved.driver_name,ved.driver_mobile,ved.invoce_no,ved.material_details,ved.quantity,ved.destination,vlj.jlocation_name from vehicle_entry_details as ved left join vh_location_jbm as vlj on ved.deptName = vlj.id where ved.vehicle_number = '"+vehicleno+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vehicledatalist = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString()+"#"+obdata[5].toString()+"#"+obdata[6].toString();
			}
		}
		return vehicledatalist;
	}
	public static String getVendorData(SessionFactory connectionSpace, String vendrname){
		String vendordatalist = null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select mob_number, email_id from front_office_details where vender_name = '"+vendrname+"'");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object[] obdata=(Object[])it.next();
				 vendordatalist = obdata[0].toString()+"#"+obdata[1].toString();
			}
		}
		return vendordatalist;
	}
	
	public static String getVisitorMode(SessionFactory connectionSpace, String id){
		String previsiteddatalist=null;
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		try
    {
			query.append("select one_time_password, visitor_mobile from visitor_entry_details where id= '"+id+"'");
			List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 if(obdata[0] !=null)
					 {
						 previsiteddatalist = "Pre-Request";
					 }
					else
					{
						 previsiteddatalist = "Instant";
					}
				}
			}
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
		return previsiteddatalist;
	}
	public static String getUserType(SessionFactory connectionSpace, String username)
	{
		String usrtype=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		try {
			query.append("select userType from useraccount where userID = '"+Cryptography.encrypt(username,DES_ENCRYPTION_KEY)+"'");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			usrtype = data.get(0).toString();
		}
		return usrtype;
	}
	
	
	public static String subtractTime(String time1, String time2){
		int f, g, h = 0;
		String time = null;
		if(time2==null || time2.equalsIgnoreCase("NA")|| time2==""){
			return "NA";
		}
		String[] a = time1.split(":");
		String[] b = time2.split(":");
		try{
		int a1 = Integer.parseInt(a[0]);
		int b1 = Integer.parseInt(b[0]);
		int a2 = Integer.parseInt(a[1]);
		int b2 = Integer.parseInt(b[1]);
		int a3 = Integer.parseInt(a[2]);
		int b3 = Integer.parseInt(b[2]);
		if((b2-a2)<0){
			f=(b2-a2)+60;
			g=(b1-1)-a1;
		}else{
			f=b2-a2;
			g=b1-a1;
			h=b3-a3;
		}
		if(g>=0 && f>=0)
		{
			//time = g+":"+f+":"+h;
			time = g+":"+f;
		}
		
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return time; 
		}
	public static String dateBeforeNdays(int numofday)
	{
			long base=System.currentTimeMillis();  
			Date date=new Date(base-numofday*24*60*60*1000);
			String dateval = DateUtil.convertDateToString(date);
			return dateval;
			
	}
	 public static boolean getAlertStatus(String alert_time,int smsalert)
	 {  
		 boolean alrtstatus=false;
		 try{   
		
			long r=smsalert*60*60*1000;
			//System.out.println("time in purpose master>>"+r);
			String t2=DateUtil.getCurrentTime()+":00";
			if(Integer.parseInt(alert_time)<8)
			{
				alert_time = alert_time+":00";
			}
		   	Date d1=null,d2=null,d3=null;
		   	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		 	d1=sdf.parse(alert_time);
		 	d2=sdf.parse(t2);
		   	sdf.setTimeZone((TimeZone.getTimeZone("GMT")));
		   	d3=sdf.parse("01:00:00");
		   	long left = d2.getTime() - d1.getTime();
		   	//System.out.println("alert status>>>"+alrtstatus+"left>>"+left+"r>>>>"+r);
		  if(left>r)
		  {
			 
			  alrtstatus=true;
			  System.out.println("alrtstatus>>"+alrtstatus);
		  }
		 	}catch(Exception e)
			{
		 		e.printStackTrace();
			}
             return alrtstatus;
	     }
	 
	 public static int getTotalVisitorToday(SessionFactory connectionSpace, String today){
			int totalvisitor = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+today+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				totalvisitor = Integer.parseInt(data.get(0).toString()) ;
			}
			return totalvisitor;
		}
	 public static int getTotalVehicleToday(SessionFactory connectionSpace, String today){
			int totalvisitor = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+today+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				totalvisitor = Integer.parseInt(data.get(0).toString()) ;
			}
			return totalvisitor;
		}
	 public static int getVisitorInToday(SessionFactory connectionSpace, String today){
			int visitorin = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+today+"' and time_out is null");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorin = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorin;
		}
	 public static int getVehicleInToday(SessionFactory connectionSpace, String today){
			int visitorin = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+today+"' and out_time is null");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorin = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorin;
		}
	
	 public static int getVisitorExitToday(SessionFactory connectionSpace, String today){
			int visitorout = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+today+"' and time_out is not null");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorout = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorout;
		}
	 public static int getVehicleExitToday(SessionFactory connectionSpace, String today){
			int visitorout = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+today+"' and out_time is not null");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorout = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorout;
		}
	 public static List<Object> getVisitedDept(SessionFactory connectionSpace){
			List<Object> visiteddeptlist = new ArrayList<Object>();
			CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			query.append("select distinct(dept.deptName) from visitor_entry_details as ved left join department as dept on ved.deptName = dept.id where dept.deptName is not null");
			List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object obdata=it.next();
					 visiteddeptlist.add(obdata);
				}
			}
			return visiteddeptlist;
		}
	 public static List<Object> getVehicleDept(SessionFactory connectionSpace){
			List<Object> visiteddeptlist = new ArrayList<Object>();
			CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			query.append("select distinct(dept.jlocation_name) from vehicle_entry_details as ved left join vh_location_jbm as dept on ved.deptName = dept.id where dept.jlocation_name is not null");
			List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object obdata=it.next();
					 visiteddeptlist.add(obdata);
				}
			}
			return visiteddeptlist;
		}
	 public static List<Object> getOneWeekDate(SessionFactory connectionSpace, String fromdate, String currDate)
	 {
		 List<Object> datelist = new ArrayList<Object>();
		 CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		 StringBuilder query = new StringBuilder("");
		 query.append("select visit_date from visitor_entry_details where (visit_date >= '"+fromdate+"' and visit_date <= '"+currDate+"') group by visit_date order by visit_date desc");
		 List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		 if(data != null && data.size()>0)
		 {
			 for (Iterator iterator = data.iterator(); iterator.hasNext();) 
			 {
				Object objectdata = (Object) iterator.next();
				datelist.add(objectdata);
			 }
		 }
		 return datelist;
	 }
	 public static int getVisitorInTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitorinfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinfordept;
		} 
	 public static int getVehicleInTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitorinfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is null and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinfordept;
		}
	 public static int getVisitorOutTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitoroutfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is not null and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitoroutfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitoroutfordept;
		}
	 public static int getVehicleOutTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitoroutfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is not null and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitoroutfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitoroutfordept;
		}
	 public static int getVisitorPendingTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitorinfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and (accept is null and reject is null) and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinfordept;
		}
	 public static int getVehiclePendingTodayForDept(SessionFactory connectionSpace, String datetoday, String dept){
			int visitorinfordept = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is null and deptName = '"+dept+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinfordept = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinfordept;
		}
	 public static String getDeptId(SessionFactory connectionSpace, String deptName)
		{
			String depid=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select id from department where deptName = '"+deptName+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				depid = data.get(0).toString();
			}
			return depid;
		}
	 public static String getVehicleDeptId(SessionFactory connectionSpace, String deptName)
		{
			String depid=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select id from vh_location_jbm where jlocation_name = '"+deptName+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				depid = data.get(0).toString();
			}
			return depid;
		}
	public static String getLocationId(SessionFactory connectionSpace, String locationName)
	{
		String locationId=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select id from location where name = '"+locationName+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			locationId = data.get(0).toString();
		}
		return locationId;
	}
	public static List<Object> getVisitedPurpose(SessionFactory connectionSpace){
		List<Object> visitedpurposelist = new ArrayList<Object>();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select distinct(prp.purpose) from visitor_entry_details as ved left join purpose_admin as prp on ved.purpose = prp.id where prp.purpose is not null order by purpose");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object obdata=it.next();
				 visitedpurposelist.add(obdata);
			}
		}
		return visitedpurposelist;
	}
	public static List<Object> getVehiclePurpose(SessionFactory connectionSpace){
		List<Object> visitedpurposelist = new ArrayList<Object>();
		CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select distinct(prp.purpose) from vehicle_entry_details as ved left join purpose_admin as prp on ved.purpose = prp.id where prp.purpose is not null and prp.purpose_for='Vehicle' order by purpose");
		List data = commnoprObj.executeAllSelectQuery(query.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				 Object obdata=it.next();
				 visitedpurposelist.add(obdata);
			}
		}
		return visitedpurposelist;
	}
	 public static String getPurposeId(SessionFactory connectionSpace, String purposeName)
		{
			String purposeId=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select id from purpose_admin where purpose = '"+purposeName+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				purposeId = data.get(0).toString();
			}
			return purposeId;
		}
	 public static String getVehiclePurposeId(SessionFactory connectionSpace, String purposeName)
		{
			String purposeId=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select id from purpose_admin where purpose = '"+purposeName+"' and purpose_for = 'Vehicle'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				purposeId = data.get(0).toString();
			}
			return purposeId;
		}
	 public static String fetchPurpose(String paramValue, SessionFactory connectionSpace)
	 {   CommonOperInterface cbt=new CommonConFactory().createInterface();
		 List list=new ArrayList<String>();
		 String purpose = null;
         String query="select pa.purpose from purpose_admin as pa left"
                                                    + " join vehicle_entry_details as ved on ved.purpose=pa.id "
                                                     + "where ved.purpose="+paramValue+"";
                         
        list=cbt.executeAllSelectQuery(query, connectionSpace);
         if(list != null && list.size()>0)
          {
             for(Object ob:list)
                {

                   purpose=ob.toString();
	                   // purpose=ob1[0].toString();
                }
           }
		 return purpose;
	 }
	 public static int getVisitorInTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
			int visitorinforprpse = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and purpose = '"+purpose+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinforprpse = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinforprpse;
		}

public static int getVehicleInTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
		int visitorinforprpse = 0;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is null and purpose = '"+purpose+"'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null && data.size()>0)
		{
			visitorinforprpse = Integer.parseInt(data.get(0).toString()) ;
		}
		return visitorinforprpse;
	}
	 public static int getVisitorOutTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
			int visitoroutforpurpose = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is not null and purpose = '"+purpose+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitoroutforpurpose = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitoroutforpurpose;
		}
	 public static int getVehicleOutTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
			int visitoroutforpurpose = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is not null and purpose = '"+purpose+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitoroutforpurpose = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitoroutforpurpose;
		}
	 public static int getVisitorPendingTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
			int visitorinforpurpose = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and (accept is null and reject is null) and purpose = '"+purpose+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinforpurpose = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinforpurpose;
		}
	 public static int getVehiclePendingTodayForPurpose(SessionFactory connectionSpace, String datetoday, String purpose){
			int visitorinforpurpose = 0;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select count(*) from vehicle_entry_details where entry_date = '"+datetoday+"' and out_time is null  and purpose = '"+purpose+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorinforpurpose = Integer.parseInt(data.get(0).toString()) ;
			}
			return visitorinforpurpose;
		}
	 public static String getVisitorNameforDeptId(SessionFactory connectionSpace, String datetoday, String deptId){
			String visitorname = null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select visitor_name from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and deptName = '"+deptId+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorname = data.get(0).toString();
			}
			return visitorname;
		}
	 public static String getVisitorInTime(SessionFactory connectionSpace, String datetoday, String deptId){
			String visitorentrytime = null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select SUBSTRING(time_in,1,5) from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and deptName = '"+deptId+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitorentrytime = data.get(0).toString();
				visitorentrytime.substring(0, 5);
			}
			return visitorentrytime;
		}
	 public static String getVisitPurposeTime(SessionFactory connectionSpace, String datetoday, String deptId){
			String visitpurposetime = null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select alert_after from visitor_entry_details where visit_date = '"+datetoday+"' and time_out is null and deptName = '"+deptId+"'");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				visitpurposetime = data.get(0).toString();
			}
			return visitpurposetime;
		}
	// method to set max (3000) connections in MySql
		public int createNConnections() {
			 int flag=0;
			 SessionFactory dbconection =DBDynamicConnection.getSessionFactory();
			 System.out.println("dbconection Connections>>>"+dbconection.toString());
			 CommonOperInterface commnoprObj = new CommonConFactory().createInterface();
			//String query = "set global max_connections=3000";
			//flag = commnoprObj.executeAllUpdateQuery(query, dbconection);
			//System.out.println("flag>>>>>"+flag+">>"+commnoprObj.executeAllUpdateQuery(query, dbconection));
			return flag;
			
		}
		public static TreeMap<String, String> getDeptwiseVisitorReport(SessionFactory connectionSpace)
		{
			TreeMap<String, String> graphMap = new TreeMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append(" select dept.deptName, count(*) from visitor_entry_details as vid left join department as dept on vid.deptName = dept.id where visit_date = '"+DateUtil.getCurrentDateUSFormat()+"' group by dept.deptName");
			List dataList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object!=null)
					{
						if(object[0]!=null && object[1]!=null)
						{
							graphMap.put(object[0].toString(),object[1].toString());
						}
					}
				}
			}
			return graphMap;
		}
		public static TreeMap<String, String> getDeptwiseVehicleReport(SessionFactory connectionSpace)
		{
			TreeMap<String, String> graphMap = new TreeMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append(" select dept.jlocation_name, count(*) from vehicle_entry_details as vid left join vh_location_jbm as dept on vid.deptName = dept.id where entry_date = '"+DateUtil.getCurrentDateUSFormat()+"' group by dept.jlocation_name");
			List dataList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object!=null)
					{
						if(object[0]!=null && object[1]!=null)
						{
							graphMap.put(object[0].toString(),object[1].toString());
						}
					}
				}
			}
			return graphMap;
		}
		public static TreeMap<String, String> getPurposewiseVisitorReport(SessionFactory connectionSpace)
		{
			TreeMap<String, String> graphMap = new TreeMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append(" select prd.purpose, count(*) from visitor_entry_details as vid left join purpose_admin as prd on vid.purpose = prd.id where prd.purpose_for = 'Visitor' and visit_date = '"+DateUtil.getCurrentDateUSFormat()+"' group by prd.purpose");
			List dataList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object!=null)
					{
						if(object[0]!=null && object[1]!=null)
						{
							graphMap.put(object[0].toString(),object[1].toString());
						}
					}
				}
			}
			return graphMap;
		}
		public static TreeMap<String, String> getPurposewiseVehicleReport(SessionFactory connectionSpace)
		{
			TreeMap<String, String> graphMap = new TreeMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append(" select prd.purpose, count(*) from vehicle_entry_details as vid left join purpose_admin as prd on vid.purpose = prd.id where prd.purpose_for = 'Vehicle' and entry_date = '"+DateUtil.getCurrentDateUSFormat()+"' group by prd.purpose ");
			System.out.println("Query*******************getPurposewiseVehicleReport******************"+query);
			List dataList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object!=null)
					{
						if(object[0]!=null && object[1]!=null)
						{
							graphMap.put(object[0].toString(),object[1].toString());
						}
					}
				}
			}
			return graphMap;
		}
	 
}