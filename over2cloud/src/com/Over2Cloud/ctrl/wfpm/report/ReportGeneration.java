package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class ReportGeneration {

	public static void setDSRRecords(String targetAchived, int empId,SessionFactory connectionSpace,String userName)
	{
		
		String[] months = { "January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December" };
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		/*String str[] = DateUtil.getCurrentDateIndianFormat().split("-");
		int day = Integer.parseInt(str[0]);*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//int month = cal.get(Calendar.MONTH);
		//int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		boolean firstWeek = false;
		boolean secondWeek = false;
		boolean thirdWeek = false;
		boolean fourthWeek = false;
		
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=null;
		
		String firstWeekReport = "0#0#0#0#0#0#0#";
		String secondWeekReport = "0#0#0#0#0#0#0#";
		String thirdWeekReport = "0#0#0#0#0#0#0#";
		String fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";
		
		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List <TableColumes> tableColumn=new ArrayList<TableColumes>();
		List<String> columnName = new ArrayList<String>();
		
		//columnName.add("id");
		columnName.add("user");
		columnName.add("currentDate");
		columnName.add("currentMonth");
		columnName.add("firstWeekReport");
		columnName.add("fourthWeekReport");
		columnName.add("kpi_id");
		columnName.add("kpi_target");
		columnName.add("secondWeekReport");
		columnName.add("thirdWeekReport");
		columnName.add("targetPeriodFlag");
		//columnName.add("historyIds");
		
		for(String cloumn : columnName)
		{
			TableColumes ob1=new TableColumes();
			ob1.setColumnname(cloumn);
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		}
			
		boolean status = cbt.createTable22("dailyreport",tableColumn,connectionSpace);
		
		if(days == 28)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#";
		}
		else if(days == 29) 
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#";
		}
		else if(days == 30)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#";
		}
		else if(days == 31)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";
		}
		if(day >= 1 && day <= 7 )
		{
			firstWeek = true;
		}
		else if(day >= 8 && day <= 14)
		{
			secondWeek = true;
		}
		else if(day >= 15 && day <= 21)
		{
			thirdWeek = true;
		}
		else
		{
			fourthWeek = true;
		}
		List report = null;
		String week = "";
		if(firstWeek)
		{
			week = "firstWeekReport";
			String query = "select firstWeekReport from dailyreport where kpi_id="+ targetAchived + " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else if (secondWeek) {
			week = "secondWeekReport";
			String query = "select secondweekreport from dailyreport where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else if (thirdWeek) {
			week = "thirdWeekReport";
			String query = "select thirdWeekReport from dailyreport where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else {
			week = "fourthWeekReport";
			String query = "select fourthWeekReport from dailyreport where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		insertData=new ArrayList<InsertDataTable>();
		ob=new InsertDataTable();
		ob.setColName("user");
		ob.setDataName(userName);
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("currentDate");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("currentMonth");
		//ob.setDataName(months[DateUtil.getCurrentMonth()-1]+"-"+ DateUtil.getCurretnYear());
		ob.setDataName(getCurrentMonthYear());
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("targetPeriodFlag");
		ob.setDataName("M");
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("kpi_id");
		ob.setDataName(targetAchived);
		insertData.add(ob);
		
		/**
		 *  for set target value in kpi_target column in dailyreport table.
		 * */
		Map<String, Object>wherClause=new HashMap<String, Object>();
		Map<String, Object>condtnBlock=new HashMap<String, Object>();
		String query12 = "select KPIId,targetvalue from target where empID='"+ empId +"' and targetMonth = '"+getCurrentMonthYear()+"'";
		List list = cbt.executeAllSelectQuery(query12, connectionSpace);
		String kpi = "";
		String targetValue = "";
		
		if(list != null)
		{
			for (Object c : list) {
				Object[] data = (Object[]) c;
				kpi = data[0].toString();
				targetValue = data[1].toString();
			}
			String s[] = kpi.split("#");
			String x[] = targetValue.split("#");
			
			
			for(int i=0; i<s.length; i++)
			{
				if(s[i].equalsIgnoreCase(targetAchived))
				{
					targetValue = x[i];
					//System.out.println("x[i]="+s[i]);
					ob=new InsertDataTable();
					ob.setColName("kpi_target");
					ob.setDataName(x[i]);
					insertData.add(ob);
					
					break;
				}
			}
		}
		
		if( report != null && report.size()>0)
		{
			String weekDays = report.toString();
			if(weekDays != null)
			{
				//System.out.println("report:"+report);
				weekDays = weekDays.substring(1, weekDays.length()-1);
				//System.out.println("weekDays:"+weekDays);
				String s[] = weekDays.split("#");
				int index=0;
				if(firstWeek)
				{
					index = day-1;
				}
				else if(secondWeek)
				{
					index = day-8;
				}
				else if(thirdWeek)
				{
					index = day-15;
				}
				else
				{
					index = day-22;
				}
				//increment in target achieved in week day
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				wherClause.put(week, stringBuffer.toString());
				wherClause.put("currentDate", DateUtil.getCurrentDateUSFormat());
				wherClause.put("kpi_target", targetValue);
				condtnBlock.put("kpi_id","'"+targetAchived+"'");
				condtnBlock.put("user","'"+userName+"'");
				condtnBlock.put("currentMonth","'"+getCurrentMonthYear()+"'");
				
				cbt.updateTableColomn("dailyreport", wherClause, condtnBlock,connectionSpace);
			}
		}
		else
		{
			int index=0;
			if(firstWeek)
			{
				String s[] = firstWeekReport.split("#");
				index = Math.abs(day-1);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				firstWeekReport = stringBuffer.toString();
			}
			else if(secondWeek)
			{
				String s[] = secondWeekReport.split("#");
				index = Math.abs(day-8);
				
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				secondWeekReport = stringBuffer.toString();
			}
			else if(thirdWeek)
			{
				String s[] = thirdWeekReport.split("#");
				index = Math.abs(day-15);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				thirdWeekReport = stringBuffer.toString();
			}
			else
			{
				String s[] = fourthWeekReport.split("#");
				index = Math.abs(day-22);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				fourthWeekReport = stringBuffer.toString();
			}
			
			ob=new InsertDataTable();
			ob.setColName("firstWeekReport");
			ob.setDataName(firstWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("secondWeekReport");
			ob.setDataName(secondWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("thirdWeekReport");
			ob.setDataName(thirdWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("fourthWeekReport");
			ob.setDataName(fourthWeekReport);
			insertData.add(ob);
			
			/*ob=new InsertDataTable();
			ob.setColName("historyIds");
			ob.setDataName(historyIdList);
			insertData.add(ob);*/
			
			cbt.insertIntoTable("dailyreport",insertData,connectionSpace);
		}
	}
	public static String getCurrentMonthYear()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		month = month+1;
		String Month = String.valueOf(month);
		if(month < 10)
			Month = "0"+month; 
		String date = Month+"-"+year;
		//System.out.println("date:"+date);//date in format dd-yyyy i.e 04-2013
		return date;
	}
}
