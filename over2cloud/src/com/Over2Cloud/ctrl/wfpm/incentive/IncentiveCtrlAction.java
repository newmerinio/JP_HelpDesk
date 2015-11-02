package com.Over2Cloud.ctrl.wfpm.incentive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.target.TargetHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;

public class IncentiveCtrlAction extends SessionProviderClass implements ServletRequestAware
{
	private HttpServletRequest	request;
	private IncentiveType				incentiveType;
	private IncentiveValueType	incentive_in;
	private Map<String, String>	empMap;
	private String							empId;
	private String							empName;
	private String							empMobile;
	private Map<String, String>	incentiveMap;
	private String							applicableFrom;
	private String							slabFrom;
	private String							slabTo;
	private String							inctvId;
	private Map<String, String>	incOfferingMap;
	private String							incoffering;
	private String							incentive;
	private List<Object>				viewIncentiveKpiGridOuter	= null;
	private List<Object>				viewIncentiveKpiGridInner	= null;
	private String							id;
	private ArrayList<Object>		viewIncentiveOfferingGridInner;

	public String beforeIncentiveMainView()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		return returnString;

	}

	public String beforeIncentiveView()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			if (incentiveType == IncentiveType.KPI)
			{
				returnString = IncentiveType.KPI.toString();
			}
			else if (incentiveType == IncentiveType.OFFERING)
			{
				returnString = IncentiveType.OFFERING.toString();
			}
		}
		return returnString;
	}

	public String beforeIncentiveAdd()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			// Creating add page for alloting incentive based on KPI
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			IncentiveHelper ih = new IncentiveHelper();
			//empMap = eh.fetchEmpIdAndEmpName();
			TargetType targetType = null;
			if (incentiveType == IncentiveType.KPI) targetType = TargetType.KPI;
			else if (incentiveType == IncentiveType.OFFERING) targetType = TargetType.OFFERING;
			empMap = eh.fetchEmployee(EmployeeReturnType.MAP, targetType);
			if (incentiveType == IncentiveType.KPI)
			{
				incentiveMap = ih.fetchKpiIdAndName();
				returnString = IncentiveType.KPI.toString();
			}
			// Creating add page for alloting incentive based on Offering
			else if (incentiveType == IncentiveType.OFFERING)
			{
				incOfferingMap = ih.fetchOfferingIdAndName();
				returnString = IncentiveType.OFFERING.toString();
			}
		}
		return returnString;
	}

	public String addKpiIncentive()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				IncentiveHelper ih = new IncentiveHelper();
				//System.out.println("empId:" + empId);
				//System.out.println("applicableFrom:" + applicableFrom);
				//System.out.println("inctvId:" + inctvId);
				//System.out.println("incentive_in:" + incentive_in);
				//System.out.println("incentive:" + incentive);
				//System.out.println("slabFrom:" + slabFrom);
				//System.out.println("slabTo:" + slabTo);
				String[] tempSlabFrom = CommonHelper.getCommaSeparatedToArray(slabFrom);
				String[] tempSlabTo = CommonHelper.getCommaSeparatedToArray(slabTo);
				String[] tempIncentive = CommonHelper.getCommaSeparatedToArray(incentive);
				int count = ih.addIncentiveForKpi(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), inctvId, incentive_in, tempIncentive, cId,
						tempSlabFrom, tempSlabTo);
				addActionMessage(count + " KPI Incentive alloted successfully.");
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;
	}

	public String addOfferingIncentive()
	{

		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				IncentiveHelper ih = new IncentiveHelper();
				//System.out.println("empId:" + empId);
				//System.out.println("applicableFrom:" + applicableFrom);
				//System.out.println("incoffering iD:" + incoffering);
				//System.out.println("incentive_in:" + incentive_in);
				//System.out.println("incentive:" + incentive);
				//System.out.println("slabFrom:" + slabFrom);
				//System.out.println("slabTo:" + slabTo);
				String[] tempSlabFrom = CommonHelper.getCommaSeparatedToArray(slabFrom);
				String[] tempSlabTo = CommonHelper.getCommaSeparatedToArray(slabTo);
				String[] tempIncentive = CommonHelper.getCommaSeparatedToArray(incentive);

				int count = ih.addIncentiveForOffering(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), incoffering, incentive_in, tempIncentive, cId,
						tempSlabFrom, tempSlabTo);
				// int count = ih.addIncentiveForKpi(empId,
				// DateUtil.convertDateMonthYearToYearMonth(applicableFrom), inctvId,
				// incentive_in, tempIncentive, cId,
				// tempSlabFrom, tempSlabTo);

				addActionMessage(count + " Offering incentive alloted successfully.");
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;

	}

	public String viewIncentiveForKpiOuterGrid()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("cId" + cId);
				TargetHelper th = new TargetHelper();
				String empIdofuser = session.get("empIdofuser").toString().split("-")[1].trim();
				TargetType targetType = null;
				if (incentiveType == IncentiveType.KPI) targetType = TargetType.KPI;
				else if (incentiveType == IncentiveType.OFFERING) targetType = TargetType.OFFERING;

				ArrayList<ArrayList<String>> incentiveEmpList = th.fetchEmpAndMobile(empIdofuser, cId, targetType);
				if (incentiveEmpList != null && incentiveEmpList.size() > 0)
				{
					viewIncentiveKpiGridOuter = new ArrayList<Object>();
					Map<String, String> map = null;

					for (ArrayList<String> data : incentiveEmpList)
					{
						map = new HashMap<String, String>();
						map.put("id", CommonHelper.getFieldValue(data.get(0)));
						map.put("empName", CommonHelper.getFieldValue(data.get(1)));
						map.put("mobile", CommonHelper.getFieldValue(data.get(2)));
						viewIncentiveKpiGridOuter.add(map);
					}
				}
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;
	}

	public String viewIncentiveForKpiInnerGrid()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				IncentiveHelper ih = new IncentiveHelper();
				ArrayList<ArrayList<String>> list = ih.fetchIncentiveForKpiByEmpId(id);
				// //System.out.println("list:" + list.size());
				if (list != null && !list.isEmpty())
				{
					Map<String, Object> map = null;
					viewIncentiveKpiGridInner = new ArrayList<Object>();
					SimpleDateFormat sdf = null;
					Date date = null;
					SimpleDateFormat sdf1 = null;
					String currentMonthValue = null;
					for (ArrayList<String> data : list)
					{
						map = new LinkedHashMap<String, Object>();
						map.put("id", CommonHelper.getFieldValue(data.get(0)));
						map.put("kra", CommonHelper.getFieldValue(data.get(1)));
						map.put("kpi", CommonHelper.getFieldValue(data.get(2)));
						map.put("slab_from", CommonHelper.getFieldValue(data.get(3)));
						map.put("slab_to", CommonHelper.getFieldValue(data.get(4)));
						map.put("incentive_value", CommonHelper.getFieldValue(data.get(5)));
						String val = (CommonHelper.getFieldValue(data.get(6)).equalsIgnoreCase("0")) ? "Value" : "Percent";
						map.put("incentive_in", val);
						sdf = new SimpleDateFormat("yyyy-MM");
						date = sdf.parse(CommonHelper.getFieldValue(data.get(7)));
						sdf1 = new SimpleDateFormat("MMM-yyyy");
						currentMonthValue = sdf1.format(date);
						map.put("applicable_from", currentMonthValue);
						viewIncentiveKpiGridInner.add(map);
					}
				}
				// //System.out.println("viewIncentiveKpiGridInner:" +
				// viewIncentiveKpiGridInner.size());
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String viewIncentiveForOfferingInnerGrid()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				IncentiveHelper ih = new IncentiveHelper();
				ArrayList<ArrayList<String>> list = ih.fetchIncentiveForOfferingByEmpId(id);
				// //System.out.println("list:" + list.size());
				if (list != null && !list.isEmpty())
				{
					Map<String, Object> map = null;
					viewIncentiveOfferingGridInner = new ArrayList<Object>();
					SimpleDateFormat sdf = null;
					Date date = null;
					SimpleDateFormat sdf1 = null;
					String currentMonthValue = null;
					for (ArrayList<String> data : list)
					{
						map = new LinkedHashMap<String, Object>();
						map.put("id", CommonHelper.getFieldValue(data.get(0)));
						map.put("offering", CommonHelper.getFieldValue(data.get(1)));
						map.put("slab_from", CommonHelper.getFieldValue(data.get(2)));
						map.put("slab_to", CommonHelper.getFieldValue(data.get(3)));
						map.put("incentive_value", CommonHelper.getFieldValue(data.get(4)));
						String val = (CommonHelper.getFieldValue(data.get(5)).equalsIgnoreCase("0")) ? "Value" : "Percent";
						map.put("incentive_in", val);
						sdf = new SimpleDateFormat("yyyy-MM");
						date = sdf.parse(CommonHelper.getFieldValue(data.get(6)));
						sdf1 = new SimpleDateFormat("MMM-yyyy");
						currentMonthValue = sdf1.format(date);
						map.put("applicable_from", currentMonthValue);
						viewIncentiveOfferingGridInner.add(map);
					}
				}
				// //System.out.println("viewIncentiveOfferingGridInner:" +
				// viewIncentiveOfferingGridInner.size());
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public int getIncentiveType()
	{
		return incentiveType == null ? 0 : incentiveType.ordinal();

	}

	public void setIncentiveType(int incentiveType)
	{
		this.incentiveType = incentiveType == 0 ? IncentiveType.KPI : IncentiveType.OFFERING;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public Map<String, String> getIncentiveMap()
	{
		return incentiveMap;
	}

	public void setIncentiveMap(Map<String, String> incentiveMap)
	{
		this.incentiveMap = incentiveMap;
	}

	public String getApplicableFrom()
	{
		return applicableFrom;
	}

	public void setApplicableFrom(String applicableFrom)
	{
		this.applicableFrom = applicableFrom;
	}

	public String getSlabFrom()
	{
		return slabFrom;
	}

	public void setSlabFrom(String slabFrom)
	{
		this.slabFrom = slabFrom;
	}

	public String getSlabTo()
	{
		return slabTo;
	}

	public void setSlabTo(String slabTo)
	{
		this.slabTo = slabTo;
	}

	public String getInctvId()
	{
		return inctvId;
	}

	public void setInctvId(String inctvId)
	{
		this.inctvId = inctvId;
	}

	public Map<String, String> getIncOfferingMap()
	{
		return incOfferingMap;
	}

	public void setIncOfferingMap(Map<String, String> incOfferingMap)
	{
		this.incOfferingMap = incOfferingMap;
	}

	public String getIncoffering()
	{
		return incoffering;
	}

	public void setIncoffering(String incoffering)
	{
		this.incoffering = incoffering;
	}

	public String getIncentive()
	{
		return incentive;
	}

	public void setIncentive(String incentive)
	{
		this.incentive = incentive;
	}

	public int getIncentive_in()
	{
		return incentive_in == null ? 0 : incentive_in.ordinal();
	}

	public void setIncentive_in(int incentive_in)
	{
		this.incentive_in = incentive_in == 0 ? IncentiveValueType.IncentiveInValue : IncentiveValueType.IncentiveInPercent;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public List<Object> getViewIncentiveKpiGridOuter()
	{
		return viewIncentiveKpiGridOuter;
	}

	public void setViewIncentiveKpiGridOuter(List<Object> viewIncentiveKpiGridOuter)
	{
		this.viewIncentiveKpiGridOuter = viewIncentiveKpiGridOuter;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getEmpMobile()
	{
		return empMobile;
	}

	public void setEmpMobile(String empMobile)
	{
		this.empMobile = empMobile;
	}

	public List<Object> getViewIncentiveKpiGridInner()
	{
		return viewIncentiveKpiGridInner;
	}

	public void setViewIncentiveKpiGridInner(List<Object> viewIncentiveKpiGridInner)
	{
		this.viewIncentiveKpiGridInner = viewIncentiveKpiGridInner;
	}

	public ArrayList<Object> getViewIncentiveOfferingGridInner()
	{
		return viewIncentiveOfferingGridInner;
	}

	public void setViewIncentiveOfferingGridInner(ArrayList<Object> viewIncentiveOfferingGridInner)
	{
		this.viewIncentiveOfferingGridInner = viewIncentiveOfferingGridInner;
	}

}
