package com.Over2Cloud.ctrl.productivityEvaluation.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.productivityEvaluation.ProductivityEvaluationKaizenHelper;

public class ProductivityDashboardHelper 
{
	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getKaizenDashboardData(SessionFactory connectionSpace, String moduleName)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query =new StringBuilder();
			query.append("select gi.groupName AS fromOg,dept.deptName As fromplant,kad.title,kad.brief,kad.implementedIn,kad.upload1,kad.upload2,kad.upload3,kad.otherPlant,kad.userName,kad.date,kad.time,kad.status,kad.comment from kaizen_add_data AS kad LEFT JOIN employee_basic AS emp ON emp.id =  kad.empId  LEFT JOIN department AS dept ON dept.id =emp.deptname  LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId  LEFT JOIN department AS dept1 ON dept1.id =kad.otherPlant  LEFT JOIN groupinfo AS gi1 ON gi1.id=dept1.groupId  WHERE moduleName = '"+moduleName+"'");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				data=new ArrayList<ProductivityDashboardPojo>();
				ProductivityDashboardPojo PP=null;
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if (object!=null)
					{
						PP=new ProductivityDashboardPojo();
						PP.setFromOG(getValueWithNullCheck(object[0]));
						PP.setFromPlant(getValueWithNullCheck(object[1]));
						PP.setTitle(getValueWithNullCheck(object[2]));
						PP.setBrief(getValueWithNullCheck(object[3]));
						PP.setImplementedIn(getValueWithNullCheck(object[4]).split("-")[1]+"-"+getValueWithNullCheck(object[4]).split("-")[0]);
						PP.setToOG(KH.getOtherMultipleOGDetails(connectionSpace, getValueWithNullCheck(object[8])) );
						PP.setToPlant(KH.getotherMultiplePlantNames(connectionSpace,  getValueWithNullCheck(object[8])));
						PP.setUploadedBy(getValueWithNullCheck(object[9]));
						PP.setUploadedOn(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[10])));
						PP.setStatus(getValueWithNullCheck(object[12]));
						PP.setComment(getValueWithNullCheck(object[13]));
					}
					data.add(PP);
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getImprovedDashboardData(
			SessionFactory connectionSpace, String moduleName)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query =new StringBuilder();
			query.append("select gi.groupName AS fromOg,dept.deptName As fromplant,imp.title,imp.upload1 ");
			query.append(",imp.upload2,imp.upload3,imp.implementedIn,imp.otherPlant, ");
			query.append("dept2.deptName As byReview,emp1.empName As reviewName, ");
			query.append("imp.reviewDate,imp.userName,imp.date,imp.time,imp.status,imp.comment ");
			query.append("from improved_add_data AS imp LEFT JOIN employee_basic AS emp ON emp.id =  imp.empId ");
			query.append("LEFT JOIN department AS dept ON dept.id =emp.deptname  LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId ");
			query.append("LEFT JOIN department AS dept1 ON dept1.id =imp.otherPlant  LEFT JOIN groupinfo AS gi1 ON gi1.id=dept1.groupId  ");
			query.append("LEFT JOIN department AS dept2 ON dept2.id =imp.byReview ");
			query.append("LEFT JOIN employee_basic AS emp1 ON emp1.id =  imp.reviewName  WHERE moduleName = '"+moduleName+"' ");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				data=new ArrayList<ProductivityDashboardPojo>();
				ProductivityDashboardPojo PP=null;
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object!=null)
					{
						PP=new ProductivityDashboardPojo();
						PP.setFromOG(getValueWithNullCheck(object[0]));
						PP.setFromPlant(getValueWithNullCheck(object[1]));
						PP.setTitle(getValueWithNullCheck(object[2]));
						PP.setImplementedIn(getValueWithNullCheck(object[6]).split("-")[1]+"-"+getValueWithNullCheck(object[6]).split("-")[0]);
						PP.setToOG(KH.getOtherMultipleOGDetails(connectionSpace,getValueWithNullCheck(object[7])));
						PP.setToPlant(KH.getotherMultiplePlantNames(connectionSpace, getValueWithNullCheck(object[7])));
						PP.setReviewBy(getValueWithNullCheck(object[8]));
						PP.setReviewName(getValueWithNullCheck(object[9]));
						PP.setReviewDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[10])));
						PP.setUploadedBy(getValueWithNullCheck(object[11]));
						PP.setUploadedOn(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[12])));
						PP.setStatus(getValueWithNullCheck(object[14]));
						PP.setComment(getValueWithNullCheck(object[15]));
					}
					data.add(PP);
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public List<ProductivityDashboardPojo> getOperationalDashboardData(
			SessionFactory connectionSpace, String moduleName)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getReviewDatesDashboardData(
			SessionFactory connectionSpace, String moduleName)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query =new StringBuilder();
			query.append("select gi.groupName AS fromOg,dept.deptName As fromplant,cmo.agenda,cmo.title,cmo.brief,cmo.forMonth, ");
			query.append("cmo.onDate,cmo.atTime,cmo.venue,cmo.frequency,cmo.dueDate,cmo.upload1, ");
			query.append("cmo.upload2,cmo.upload3,cmo.otherPlant ");
			query.append(",cmo.userName,cmo.date,cmo.time,cmo.status  ");
			query.append("from cmo_communication_add_data AS cmo ");
			query.append("LEFT JOIN employee_basic AS emp ON emp.id =  cmo.empId  ");
			query.append("LEFT JOIN department AS dept ON dept.id =emp.deptname  LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId    ");
			query.append("LEFT JOIN department AS dept1 ON dept1.id =cmo.otherPlant  LEFT JOIN groupinfo AS gi1 ON gi1.id=dept1.groupId   ");
			query.append("WHERE moduleName = '"+moduleName+"' ");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				data=new ArrayList<ProductivityDashboardPojo>();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				ProductivityDashboardPojo PP=null;
				ComplianceReminderHelper CH=new ComplianceReminderHelper();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object!=null)
					{
						PP=new ProductivityDashboardPojo();
						PP.setFromOG(getValueWithNullCheck(object[0]));
						PP.setFromPlant(getValueWithNullCheck(object[1]));
						PP.setAgenda(getValueWithNullCheck(object[2]));
						PP.setTitle(getValueWithNullCheck(object[3]));
						PP.setBrief(getValueWithNullCheck(object[4]));
						PP.setImplementedIn(getValueWithNullCheck(object[5]).split("-")[1]+"-"+getValueWithNullCheck(object[5]).split("-")[0]);
						PP.setOnDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						PP.setAtTime(getValueWithNullCheck(object[7]));
						PP.setVenue(getValueWithNullCheck(object[8]));
						PP.setFrequency(CH.getFrequencyName(getValueWithNullCheck(object[9])));
						PP.setDueDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[10])));
						PP.setToOG(KH.getOtherMultipleOGDetails(connectionSpace, getValueWithNullCheck(object[14])));
						PP.setToPlant(KH.getotherMultiplePlantNames(connectionSpace, getValueWithNullCheck(object[14])));
						PP.setUploadedBy(getValueWithNullCheck(object[15]));
						PP.setUploadedOn(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[16])));
						PP.setStatus(getValueWithNullCheck(object[18]));
					}
					data.add(PP);
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> oGTaskData(SessionFactory connectionSpace)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			data=new ArrayList<ProductivityDashboardPojo>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			ProductivityDashboardPojo pp=null;
			List<String> OgList=new ArrayList<String>();
			OgList.add("Kaizen");
			OgList.add("Best Practices");
			OgList.add("Improvement Dashboard");
			OgList.add("Development Project");
			OgList.add("Capacity Confirmation");
			for (String moduleName : OgList)
			{
				StringBuilder query=new StringBuilder();
				pp=new ProductivityDashboardPojo();
				pp.setStatus(moduleName);
				if (moduleName!=null && moduleName.equalsIgnoreCase("Kaizen") || moduleName.equalsIgnoreCase("Best Practices"))
				{
					query.append("SELECT status FROM kaizen_add_data WHERE moduleName ='"+moduleName+"'");
				}
				else
				{
					query.append("SELECT status FROM improved_add_data WHERE moduleName ='"+moduleName+"'");
				}
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						int totalCount=0,implementCount=0,notImplementCount=0,runningCount=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							totalCount=1;
							if (object.toString().equalsIgnoreCase("Already Running"))
							{
								runningCount=1;
							}
							else if (object.toString().equalsIgnoreCase("Can Not Be Implemented"))
							{
								notImplementCount=1;
							}
							else if (object.toString().equalsIgnoreCase("Implemented"))
							{
								implementCount=1;
							}
						}
						if (totalCount>0)
						{
							pp.setTotalCountToday(String.valueOf(totalCount+Integer.parseInt(pp.getTotalCountToday())));
						}
						if (runningCount>0)
						{
							pp.setRunningToday(String.valueOf(runningCount+Integer.parseInt(pp.getRunningToday())));
						}
						if (notImplementCount>0)
						{
							pp.setNotImplementToday(String.valueOf(notImplementCount+Integer.parseInt(pp.getNotImplementToday())));
						}
						if (implementCount>0)
						{
							pp.setImplementToday(String.valueOf(implementCount+Integer.parseInt(pp.getImplementToday())));
						}
					}
				}
				query=new StringBuilder();
				if (moduleName!=null && moduleName.equalsIgnoreCase("Kaizen") || moduleName.equalsIgnoreCase("Best Practices"))
				{
					query.append("SELECT status FROM kaizen_add_data WHERE moduleName ='"+moduleName+"' AND implementedIn LIKE '%"+DateUtil.getCurrentMonth()+"'");
				}
				else
				{
					query.append("SELECT status FROM improved_add_data WHERE moduleName ='"+moduleName+"' AND implementedIn LIKE '%"+DateUtil.getCurrentMonth()+"'");
				}
				if (dataList!=null && dataList.size()>0)
				{
					dataList.clear();
				}
				dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						int totalCount=0,implementCount=0,notImplementCount=0,runningCount=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							totalCount=1;
							if (object.toString().equalsIgnoreCase("Already Running"))
							{
								runningCount=1;
							}
							else if (object.toString().equalsIgnoreCase("Can Not Be Implemented"))
							{
								notImplementCount=1;
							}
							else if (object.toString().equalsIgnoreCase("Implemented"))
							{
								implementCount=1;
							}
						}
						if (totalCount>0)
						{
							pp.setTotalCountCurrent(String.valueOf(totalCount+Integer.parseInt(pp.getTotalCountCurrent())));
						}
						if (runningCount>0)
						{
							pp.setRunningCurrent(String.valueOf(runningCount+Integer.parseInt(pp.getRunningCurrent())));
						}
						if (notImplementCount>0)
						{
							pp.setNotImplementCurrent(String.valueOf(notImplementCount+Integer.parseInt(pp.getNotImplementCurrent())));
						}
						if (implementCount>0)
						{
							pp.setImplementCurrent(String.valueOf(implementCount+Integer.parseInt(pp.getImplementCurrent())));
						}
					}
				}
				data.add(pp);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getCMOCommData(
			SessionFactory connectionSpace)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			data=new ArrayList<ProductivityDashboardPojo>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			ProductivityDashboardPojo pp=null;
			List<String> OgList=new ArrayList<String>();
			OgList.add("Review Dates");
			OgList.add("Notes");
			OgList.add("Operational Excellence");
			OgList.add("CMD Comm");
			OgList.add("BSC Comm");
			for (String moduleName : OgList)
			{
				StringBuilder query=new StringBuilder();
				pp=new ProductivityDashboardPojo();
				pp.setStatus(moduleName);
				query.append("SELECT COUNT(*) FROM cmo_communication_add_data WHERE moduleName ='"+moduleName+"'");
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList!=null && dataList.size()>0)
				{
					pp.setTotalCountCurrent(dataList.get(0).toString());
					dataList.clear();
				}
				query.setLength(0);
				query.append("SELECT COUNT(*) FROM cmo_communication_add_data WHERE moduleName ='"+moduleName+"' AND forMonth LIKE '%"+DateUtil.getCurrentMonth()+"'");
				dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList!=null && dataList.size()>0)
				{
					pp.setTotalCountToday(dataList.get(0).toString());
				}
				data.add(pp);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getProjectsData(SessionFactory connectionSpace)
	{
		List<ProductivityDashboardPojo> data=null;
		try
		{
			data=new ArrayList<ProductivityDashboardPojo>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			ProductivityDashboardPojo pp=null;
			List<String> OgList=new ArrayList<String>();
			OgList.add("Special Project");
			OgList.add("Specific Assignments");
			OgList.add("Monitoring Assignments");
			for (String moduleName : OgList)
			{
				StringBuilder query=new StringBuilder();
				pp=new ProductivityDashboardPojo();
				pp.setStatus(moduleName);
				
				query.append("SELECT dueDate,dueTime,status FROM cmo_special_add_details WHERE moduleName ='"+moduleName+"'");
				System.out.println("query is as :::  "+query.toString());
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						int totalCount=0,pending=0,done=0,missed=0;
						Object[] object = (Object[]) iterator.next();
						if (object!=null)
						{
							totalCount=1;
							if (object[2].toString().equalsIgnoreCase("Pending"))
							{
								String strDue = object[0].toString() + " 00:00";
								String strCurrent = DateUtil.getCurrentDateUSFormat() + " 00:00";
								boolean status = DateUtil.compareDateTime(strDue, strCurrent);
								if (status)
								{
									pending = 1;
								}
								else
								{
									missed = 1;
								}
							}
							else if (object[2].toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (totalCount>0)
						{
							pp.setTotalCountToday(String.valueOf(totalCount+Integer.parseInt(pp.getTotalCountToday())));
						}
						if (missed>0)
						{
							pp.setMissed(String.valueOf(missed+Integer.parseInt(pp.getMissed())));
						}
						if (pending>0)
						{
							pp.setPending(String.valueOf(pending+Integer.parseInt(pp.getPending())));
						}
						if (done>0)
						{
							pp.setDone(String.valueOf(done+Integer.parseInt(pp.getDone())));
						}
					}
				}
				data.add(pp);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
}
