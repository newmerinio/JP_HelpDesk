package com.Over2Cloud.ctrl.wfpm.target;

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
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class TargetCtrlAction extends SessionProviderClass implements ServletRequestAware
{

	private HttpServletRequest						request;
	private TargetType										targetType;
	private CommonOperInterface						coi											= new CommonConFactory().createInterface();
	private Map<String, String>						empMap;
	// private String empId;
	private String												contactId;
	private ArrayList<ArrayList<String>>	kpiDetailsList;
	private String												empMobile;
	private String												applicableFrom;
	private String												kpiId;
	private String												weightage;
	private String												targetValue;
	private List<Object>									viewTargetKpiGridOuter	= null;
	private List<Object>									viewTargetKpiGridInner;
	private String												id;
	private String												empId;
	private ArrayList<ArrayList<String>>	offeringDetailsList;
	private String												offeringId;
	private ArrayList<Object>							viewTargetOfferingGridOuter;
	private ArrayList<Object>							viewTargetOfferingGridInner;
	private String												date;
	private String												empName;
	private ArrayList<ArrayList<String>>	targetForAlreadyMappedKpi;
	private ArrayList<ArrayList<String>>	targetForAlreadyMappedOffering;

	public String beforeTargetView()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			if (targetType == TargetType.KPI)
			{
				returnString = TargetType.KPI.toString();
			}
			else if (targetType == TargetType.OFFERING)
			{
				returnString = TargetType.OFFERING.toString();
			}
		}
		return returnString;
	}

	public String beforeTargetMainView()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		return returnString;
	}

	public String beforeTargetAdd()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			// Creating add page for alloting target based on KPI
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			empMap = eh.fetchEmployee(EmployeeReturnType.MAP, targetType);
			if (targetType == TargetType.KPI)
			{
				returnString = TargetType.KPI.toString();
			}
			// Creating add page for alloting target based on Offering
			else if (targetType == TargetType.OFFERING)
			{
				returnString = TargetType.OFFERING.toString();
			}
		}
		return returnString;
	}

	public String fetchEmpMappedKpi()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			TargetHelper th = new TargetHelper();
			kpiDetailsList = th.fetchKpiIdAndNameByEmpId(empId);
			retValue = SUCCESS;
		}
		return retValue;
	}

	public String fetchAllOffering()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			TargetHelper th = new TargetHelper();
			// offering details list
			offeringDetailsList = th.fetchAllOffering();
			retValue = SUCCESS;
		}
		return retValue;
	}

	@SuppressWarnings("unchecked")
	public String fetchEmpMobile()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				EmployeeHelper th = new EmployeeHelper();
				empMobile = th.fetchEmpDetailsById(empId)[2];
				// System.out.println("empMobile:" + empMobile);
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;
	}

	public String addKpiTarget()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				// System.out.println("empId:" + empId);
				// System.out.println("applicableFrom:" + applicableFrom);
				// System.out.println("kpiId:" + kpiId);
				// System.out.println("weightage:" + weightage);
				// System.out.println("targetValue:" + targetValue);

				int count = th.addTargetForKpi(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), kpiId, weightage, targetValue, cId);
				addActionMessage(count + " KPI target alloted successfully.");
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;
	}

	public String addOfferingTarget()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				// System.out.println("empId:" + empId);
				// System.out.println("applicableFrom:" + applicableFrom);
				// System.out.println("offeringId:" + offeringId);
				// System.out.println("weightage:" + weightage);
				// System.out.println("targetValue:" + targetValue);

				int count = th.addTargetForOffering(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), offeringId, weightage, targetValue, cId);
				addActionMessage(count + " Offering target alloted successfully.");
				retValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return retValue;
	}

	public String viewTargetForKpiOuterGrid()
	{
		String retValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				CommonHelper ch = new CommonHelper();
				String empIdofuser = session.get("empIdofuser").toString().split("-")[1].trim();
				ArrayList<ArrayList<String>> targetEmpList = th.fetchEmpAndMobile(empIdofuser, cId, targetType);
				if (targetEmpList != null && targetEmpList.size() > 0)
				{
					viewTargetKpiGridOuter = new ArrayList<Object>();
					Map<String, String> map = null;

					for (ArrayList<String> data : targetEmpList)
					{
						map = new HashMap<String, String>();
						map.put("id", ch.getFieldValue(data.get(0)));
						map.put("empName", ch.getFieldValue(data.get(1)));
						map.put("mobile", ch.getFieldValue(data.get(2)));
						viewTargetKpiGridOuter.add(map);
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

	public String viewTargetForKpiInnerGrid()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				ArrayList<ArrayList<String>> list = th.fetchTargetForKpiByEmpId(id);
				// //System.out.println("list:" + list.size());
				if (list != null && !list.isEmpty())
				{
					Map<String, Object> map = null;
					viewTargetKpiGridInner = new ArrayList<Object>();
					CommonHelper ch = new CommonHelper();
					String currentMonthValue = null;
					for (ArrayList<String> data : list)
					{
						map = new LinkedHashMap<String, Object>();
						map.put("id", CommonHelper.getFieldValue(data.get(0)));
						map.put("kra", CommonHelper.getFieldValue(data.get(1)));
						map.put("kpi", CommonHelper.getFieldValue(data.get(2)));
						map.put("weightage", CommonHelper.getFieldValue(data.get(3)));
						map.put("targetValue", CommonHelper.getFieldValue(data.get(4)));

						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						Date date = sdf1.parse(CommonHelper.getFieldValue(data.get(5)));
						SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
						currentMonthValue = sdf.format(date);
						map.put("applicableFrom", currentMonthValue);
						viewTargetKpiGridInner.add(map);
					}
				}
				// System.out.println("viewTargetKpiGridInner:" +
				// viewTargetKpiGridInner.size());
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String viewTargetForOfferingInnerGrid()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				ArrayList<ArrayList<String>> list = th.fetchTargetForOfferingByEmpId(id);
				// System.out.println("list:" + list.size());
				if (list != null && !list.isEmpty())
				{
					Map<String, Object> map = null;

					viewTargetOfferingGridInner = new ArrayList<Object>();
					CommonHelper ch = new CommonHelper();
					String currentMonthValue = null;
					for (ArrayList<String> data : list)
					{
						map = new LinkedHashMap<String, Object>();
						map.put("id", CommonHelper.getFieldValue(data.get(0)));
						map.put("offering", CommonHelper.getFieldValue(data.get(1)));
						map.put("weightage", CommonHelper.getFieldValue(data.get(2)));
						map.put("targetValue", CommonHelper.getFieldValue(data.get(3)));

						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						Date date = sdf1.parse(CommonHelper.getFieldValue(data.get(4)));
						SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
						currentMonthValue = sdf.format(date);
						map.put("applicableFrom", currentMonthValue);
						viewTargetOfferingGridInner.add(map);
					}
				}
				// System.out.println("viewTargetKpiGridInner:" +
				// viewTargetOfferingGridInner.size());
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public String beforeKpiTargetEdit()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				EmployeeHelper eh = new EmployeeHelper();
				targetForAlreadyMappedKpi = new ArrayList<ArrayList<String>>();
				ArrayList<String> list = null;
				ArrayList<ArrayList<String>> targetList = th.fetchTargetByTableId(id, applicableFrom, TargetType.KPI);
				empId = targetList.get(0).get(10).toString();
				String[] details = eh.fetchEmpDetailsById(empId);
				empId = details[0];
				empName = details[1];
				empMobile = details[2];
				ArrayList<ArrayList<String>> kpiList = th.fetchKpiIdAndNameByEmpId(empId);
				boolean flag;

				for (ArrayList<String> tempKpiList : kpiList)
				{
					flag = true;
					for (ArrayList<String> tempTargetList : targetList)
					{
						if (tempKpiList.get(0).equals(tempTargetList.get(1)))
						{
							list = new ArrayList<String>();
							list.add(tempTargetList.get(1));
							list.add(tempTargetList.get(2));
							list.add(tempTargetList.get(3));
							list.add(tempTargetList.get(4));
							list.add(tempTargetList.get(5));
							targetForAlreadyMappedKpi.add(list);
							flag = false;
							break;
						}
					}
					if (flag)
					{
						list = new ArrayList<String>();
						list.add(tempKpiList.get(0));
						list.add(tempKpiList.get(1));
						list.add(tempKpiList.get(2));
						list.add("");
						list.add("");
						targetForAlreadyMappedKpi.add(list);
					}
				}
				// System.out.println("targetForAlreadyMappedKpi.size():" +
				// targetForAlreadyMappedKpi.size());
				applicableFrom = DateUtil.convertDateYearMonthToMonthYear(applicableFrom);

				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public String beforeOfferingTargetEdit()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				EmployeeHelper eh = new EmployeeHelper();
				targetForAlreadyMappedOffering = new ArrayList<ArrayList<String>>();
				ArrayList<String> list = null;
				ArrayList<ArrayList<String>> targetList = th.fetchTargetByTableId(id, applicableFrom, TargetType.OFFERING);

				empId = targetList.get(0).get(9).toString();
				String[] details = eh.fetchEmpDetailsById(empId);
				empId = details[0];
				empName = details[1];
				empMobile = details[2];

				offeringDetailsList = th.fetchAllOffering();

				boolean flag;
				for (ArrayList<String> tempOfferingList : offeringDetailsList)
				{
					flag = true;
					for (ArrayList<String> tempTargetList : targetList)
					{
						if (tempOfferingList.get(0).equals(tempTargetList.get(1)))
						{
							list = new ArrayList<String>();
							list.add(tempTargetList.get(1));
							list.add(tempTargetList.get(2));
							list.add(tempTargetList.get(3));
							list.add(tempTargetList.get(4));
							targetForAlreadyMappedOffering.add(list);
							flag = false;
							break;
						}
					}
					if (flag)
					{
						list = new ArrayList<String>();
						list.add(tempOfferingList.get(0));
						list.add(tempOfferingList.get(1));
						list.add("");
						list.add("");
						targetForAlreadyMappedOffering.add(list);
					}
				}
				// System.out.println("targetForAlreadyMappedOffering.size():" +
				// targetForAlreadyMappedOffering.size());
				applicableFrom = DateUtil.convertDateYearMonthToMonthYear(applicableFrom);

				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String editKpiTarget()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				// System.out.println("empId:" + empId);
				// System.out.println("kpiId:" + kpiId);
				// System.out.println("weightage:" + weightage);
				// System.out.println("targetValue:" + targetValue);
				// System.out.println("applicableFrom:" + applicableFrom);

				int count = th.editTargetForKpi(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), kpiId, weightage, targetValue, cId);
				addActionMessage(count + " KPIs updated successfully.");

				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String editOfferingTarget()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				TargetHelper th = new TargetHelper();
				// System.out.println("empId:" + empId);
				// System.out.println("kpiId:" + kpiId);
				// System.out.println("weightage:" + weightage);
				// System.out.println("targetValue:" + targetValue);
				// System.out.println("applicableFrom:" + applicableFrom);

				// int count = th.editTargetForKpi(empId,
				// DateUtil.convertDateMonthYearToYearMonth(applicableFrom), kpiId,
				// weightage, targetValue, cId);
				int count = th.editTargetForOffering(empId, DateUtil.convertDateMonthYearToYearMonth(applicableFrom), offeringId, weightage, targetValue, cId);
				addActionMessage(count + " KPIs updated successfully.");

				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	// Getter & Setters
	//****************************************************************************
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public void setTargetType(int targetType)
	{
		this.targetType = targetType == 0 ? TargetType.KPI : TargetType.OFFERING;
	}

	public int getTargetType()
	{
		return targetType == null ? 0 : targetType.ordinal();
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setKpiDetailsList(ArrayList<ArrayList<String>> kpiDetailsList)
	{
		this.kpiDetailsList = kpiDetailsList;
	}

	public ArrayList<ArrayList<String>> getKpiDetailsList()
	{
		return kpiDetailsList;
	}

	public void setEmpMobile(String empMobile)
	{
		this.empMobile = empMobile;
	}

	public String getEmpMobile()
	{
		return empMobile;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setApplicableFrom(String applicableFrom)
	{
		this.applicableFrom = applicableFrom;
	}

	public String getApplicableFrom()
	{
		return applicableFrom;
	}

	public void setKpiId(String kpiId)
	{
		this.kpiId = kpiId;
	}

	public String getKpiId()
	{
		return kpiId;
	}

	public void setWeightage(String weightage)
	{
		this.weightage = weightage;
	}

	public String getWeightage()
	{
		return weightage;
	}

	public void setTargetValue(String targetValue)
	{
		this.targetValue = targetValue;
	}

	public String getTargetValue()
	{
		return targetValue;
	}

	public void setViewTargetKpiGridOuter(List<Object> viewTargetKpiGridOuter)
	{
		this.viewTargetKpiGridOuter = viewTargetKpiGridOuter;
	}

	public List<Object> getViewTargetKpiGridOuter()
	{
		return viewTargetKpiGridOuter;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setViewTargetKpiGridInner(List<Object> viewTargetKpiGridInner)
	{
		this.viewTargetKpiGridInner = viewTargetKpiGridInner;
	}

	public List<Object> getViewTargetKpiGridInner()
	{
		return viewTargetKpiGridInner;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setOfferingDetailsList(ArrayList<ArrayList<String>> offeringDetailsList)
	{
		this.offeringDetailsList = offeringDetailsList;
	}

	public ArrayList<ArrayList<String>> getOfferingDetailsList()
	{
		return offeringDetailsList;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setViewTargetOfferingGridOuter(ArrayList<Object> viewTargetOfferingGridOuter)
	{
		this.viewTargetOfferingGridOuter = viewTargetOfferingGridOuter;
	}

	public ArrayList<Object> getViewTargetOfferingGridOuter()
	{
		return viewTargetOfferingGridOuter;
	}

	public void setViewTargetOfferingGridInner(ArrayList<Object> viewTargetOfferingGridInner)
	{
		this.viewTargetOfferingGridInner = viewTargetOfferingGridInner;
	}

	public ArrayList<Object> getViewTargetOfferingGridInner()
	{
		return viewTargetOfferingGridInner;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setTargetForAlreadyMappedKpi(ArrayList<ArrayList<String>> targetForAlreadyMappedKpi)
	{
		this.targetForAlreadyMappedKpi = targetForAlreadyMappedKpi;
	}

	public ArrayList<ArrayList<String>> getTargetForAlreadyMappedKpi()
	{
		return targetForAlreadyMappedKpi;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}

	public void setTargetForAlreadyMappedOffering(ArrayList<ArrayList<String>> targetForAlreadyMappedOffering)
	{
		this.targetForAlreadyMappedOffering = targetForAlreadyMappedOffering;
	}

	public ArrayList<ArrayList<String>> getTargetForAlreadyMappedOffering()
	{
		return targetForAlreadyMappedOffering;
	}

}
