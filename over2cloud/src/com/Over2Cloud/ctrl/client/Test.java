package com.Over2Cloud.ctrl.client;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable;

public class Test {
public static void main(String[] args) {
	   try{
			PreRequestserviceStub objstatus=new PreRequestserviceStub();
			InsertIntoTable obj=new InsertIntoTable();
			StringBuilder createTableQuery = new StringBuilder("INSERT INTO " +"visitor_entry_details"+" (");  
			 List<String> Tablecolumedata=new ArrayList<String>(); 
			 Tablecolumedata.add("sr_number");
			 Tablecolumedata.add("visitor_name");
			 Tablecolumedata.add("visitor_mobile");
			 Tablecolumedata.add("visitor_company");
			 Tablecolumedata.add("address");
			 Tablecolumedata.add("purpose");
			 Tablecolumedata.add("visited_person");
			 Tablecolumedata.add("visited_mobile");
			 Tablecolumedata.add("deptName"); 
			 Tablecolumedata.add("visit_date"); 
			 Tablecolumedata.add("time_in");
				int i=1;
				// append Column 
				for(String h: Tablecolumedata)
					{
						if(i<Tablecolumedata.size())
							createTableQuery.append(h+", ");
						else
							createTableQuery.append(h+")");
						i++;
					}
			 createTableQuery.append(" VALUES (");
			 createTableQuery.append("'"+"vS100"+"',");
			 createTableQuery.append("'"+"Pankaj"+"',");
			 createTableQuery.append("'"+"9650937402"+"',");
			 createTableQuery.append("' Dreamsol',");
			 createTableQuery.append("'"+"Noida"+"',");
			 createTableQuery.append("'"+"Amoan"+"',");
			 createTableQuery.append("'"+"Amoan"+"',");
			 createTableQuery.append("'"+"9313115013"+"',");
			 createTableQuery.append("'"+"IT"+"',");
			 createTableQuery.append("'"+DateUtil.getCurrentDateIndianFormat()+"',");
			 createTableQuery.append("'"+DateUtil.getCurrentTime()+"'");
		     createTableQuery.append(")");
		     System.out.println("createTableQuery>>>>>>>>>>>>>"+createTableQuery);
			obj.setCreateTableQuery(createTableQuery.toString());
		    objstatus.insertIntoTable(obj);
           }catch (AxisFault e) {
               e.printStackTrace();
           } catch (RemoteException e) {
               e.printStackTrace();
             }
}
}
