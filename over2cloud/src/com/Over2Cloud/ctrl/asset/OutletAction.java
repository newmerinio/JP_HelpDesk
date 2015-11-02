package com.Over2Cloud.ctrl.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class OutletAction extends GridPropertyBean
{
	static final Log log = LogFactory.getLog(OutletAction.class);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private List<Object> masterViewList;
	private List<Object> masterViewList1;
	
	public String viewOutletAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from associate_basic_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					
					query.append(" SELECT id,associateName,address FROM associate_basic_data ORDER BY associateName");
					
					System.out.println("Query string "+query.toString());
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Map<String, Object> tempMap=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							tempMap.put("id",CUA.getValueWithNullCheck(obdata[0]));
							tempMap.put("outletName",CUA.getValueWithNullCheck(obdata[1]));
							tempMap.put("address",CUA.getValueWithNullCheck(obdata[2]));
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllotment of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getMappedEmp2Outlet()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList1 = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from associate_contact_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					query.append(" SELECT id,name,contactNum,emailOfficial,designation FROM associate_contact_data WHERE associateName="+id+" ORDER BY name");
					System.out.println("Query string "+query.toString());
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Map<String, Object> tempMap=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							tempMap.put("id",CUA.getValueWithNullCheck(obdata[0]));
							tempMap.put("gridEmpName",CUA.getValueWithNullCheck(obdata[1]));
							tempMap.put("gridEmpContact",CUA.getValueWithNullCheck(obdata[2]));
							tempMap.put("gridEmpEmail",CUA.getValueWithNullCheck(obdata[3]));
							tempMap.put("gridEmpDesignation",CUA.getValueWithNullCheck(obdata[4]));
							tempList.add(tempMap);
						}
						setMasterViewList1(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllotment of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	
	
	

	public List<Object> getMasterViewList1()
	{
		return masterViewList1;
	}

	public void setMasterViewList1(List<Object> masterViewList1)
	{
		this.masterViewList1 = masterViewList1;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}
	
}
