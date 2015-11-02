package com.Over2Cloud.ctrl.WorkingHrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class WorkingHourHelper
{
	String workingHrs = "00:00";
	int nonWorkingDayCounter = 0;
	public List<String> getDayDate(String date, SessionFactory connection, CommonOperInterface cbt, String moduleName)
	{
		List<String> workingDayDate = new ArrayList<String>(); 
		try
		{
			StringBuilder query = new StringBuilder();
			/**
			 * Check Consider Holiday Or Not
			 * 
			 */
			
			query.append("SELECT start_date FROM holiday_list WHERE start_date='"+date+"'");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if(dataList!=null && dataList.size()>0)
			{
				workingDayDate = getDayDate(DateUtil.getNewDate("day", 1, dataList.get(0).toString()), connection, cbt, moduleName);
				dataList.clear();
			}
			else
			{
				String day = DateUtil.getDayofDate(date);
				query.append("SELECT day FROM working_timming WHERE  module_name='"+moduleName+"'");
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				
				if(dataList!=null && dataList.size()>0)
				{
					dataList.clear();
					query.append("SELECT day FROM working_timming WHERE day='"+day+"' AND module_name='"+moduleName+"'");
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
					if(dataList!=null && dataList.size()>0)
					{
						workingDayDate.add(day);
						workingDayDate.add(date);
					}
					else
					{
						workingDayDate = getDayDate(DateUtil.getNewDate("day", 1, date), connection, cbt, moduleName);
					}
				}
				else
				{
					workingDayDate.add(day);
					workingDayDate.add(date);
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return workingDayDate;
	}
	
	public List getWorkingTime(SessionFactory connection, CommonOperInterface cbt, String moduleName, String day)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT from_time,to_time,working_hrs,non_working_hrs FROM working_timming WHERE day='"+day+"' AND module_name='"+moduleName+"'");
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public String getWorkingHrs(String date, SessionFactory connection, CommonOperInterface cbt, String moduleName,String lastCheckDate)
	{
		try
		{
			if(!date.equals(lastCheckDate))
			{
				StringBuilder query = new StringBuilder();
				query.append("SELECT start_date FROM holiday_list WHERE start_date='"+date+"'");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				if(dataList!=null && dataList.size()>0)
				{
					getWorkingHrs(DateUtil.getNewDate("day", 1, dataList.get(0).toString()), connection, cbt, moduleName, lastCheckDate);
					dataList.clear();
				}
				else
				{
					String day = DateUtil.getDayofDate(date);
					query.append("SELECT day FROM working_timming WHERE  module_name='"+moduleName+"'");
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
					query.setLength(0);
					
					if(dataList!=null && dataList.size()>0)
					{
						dataList.clear();
						query.append("SELECT working_hrs FROM working_timming WHERE day='"+day+"' AND module_name='"+moduleName+"'");
						dataList = cbt.executeAllSelectQuery(query.toString(), connection);
						if(dataList!=null && dataList.size()>0)
						{
							workingHrs = DateUtil.addTwoTime(workingHrs, dataList.get(0).toString());
							getWorkingHrs(DateUtil.getNewDate("day", 1, date), connection, cbt, moduleName, lastCheckDate);
						}
						else
						{
							getWorkingHrs(DateUtil.getNewDate("day", 1, date), connection, cbt, moduleName, lastCheckDate);
						}
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return workingHrs;
	}
	
	public List getWorkingTimeDetails(String day, SessionFactory connection, CommonOperInterface cbt, String moduleName)
	{
		List dataList =null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT from_time,to_time,working_hrs,non_working_hrs FROM working_timming WHERE module_name='"+moduleName+"' AND day='"+day+"'");
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
		
	}
	
	public List applyedWorkingHrsDept( SessionFactory connection, CommonOperInterface cbt, String moduleName)
	{
		List dataList =null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT dept.id,dept.contact_subtype_name FROM working_timming AS wt");
			query.append(" LEFT JOIN contact_sub_type AS dept ON dept.id = wt.sub_type_id");
			if(!moduleName.equalsIgnoreCase("All"))
			{
				query.append(" WHERE wt.module_name = '"+moduleName+"'");
			}
			query.append(" ORDER BY dept.contact_subtype_name");
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
		
	}
	
	
	public int getTotalNonWorkingDay(String startDate, SessionFactory connection, CommonOperInterface cbt, String moduleName, String endDate)
	{
		try
		{
			boolean comStatus = DateUtil.comparetwoDatesForDAR(startDate,endDate);
	//		System.out.println(comStatus+" >>> "+startDate+" >>> "+endDate+" >>>> "+nonWorkingDayCounter);
			if(comStatus)
			{
				StringBuilder query = new StringBuilder();
				query.append("SELECT start_date FROM holiday_list WHERE start_date='"+startDate+"'");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				if(dataList!=null && dataList.size()>0)
				{
					nonWorkingDayCounter++;
					getTotalNonWorkingDay(DateUtil.getNewDate("day", 1, dataList.get(0).toString()), connection, cbt, moduleName, endDate);
					dataList.clear();
				}
				else
				{
					String day = DateUtil.getDayofDate(startDate);
					query.append("SELECT day FROM working_timming WHERE  module_name='"+moduleName+"'");
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
					query.setLength(0);
					
					if(dataList!=null && dataList.size()>0)
					{
						dataList.clear();
						query.append("SELECT day FROM working_timming WHERE day='"+day+"' AND module_name='"+moduleName+"'");
						System.out.println(query.toString());
						dataList = cbt.executeAllSelectQuery(query.toString(), connection);
						if(dataList==null || dataList.size()==0)
						{
						
							nonWorkingDayCounter++;
							getTotalNonWorkingDay(DateUtil.getNewDate("day", 1, startDate), connection, cbt, moduleName, endDate);
						}
					}
					getTotalNonWorkingDay(DateUtil.getNewDate("day", 1, startDate), connection, cbt, moduleName, endDate);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nonWorkingDayCounter;
	}
	public String checkWorkingDayDate(String date, SessionFactory connection, CommonOperInterface cbt, String moduleName)
	{
		StringBuilder data=new StringBuilder("");
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT start_date FROM holiday_list WHERE start_date='"+date+"'");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if(dataList!=null && dataList.size()>0)
			{
				return data.toString();
			}
			else
			{
				String day = DateUtil.getDayofDate(date);
				query.append("SELECT day,from_time,to_time,working_hrs FROM working_timming WHERE  module_name='"+moduleName+"' AND day='"+day+"'");
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						data.append(object[0]+" "+object[1]+" "+object[2]+" "+object[3]);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return data.toString();
	}
	
}
