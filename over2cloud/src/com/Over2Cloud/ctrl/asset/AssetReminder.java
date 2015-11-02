package com.Over2Cloud.ctrl.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssetReminder extends ActionSupport
{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private List<ConfigurationUtilBean> supportColumnMap;
	private List<ConfigurationUtilBean> commonDDMap = null;
	List<ConfigurationUtilBean> assetDropMap=null;
	private Map<Integer, String>  vendorList, supportTypeList,assetTypeList;
	private Map<String,String> viaFrom;
	private Map<String,String> remindToMap;
	private Map<String,String> frequencyMap;
	private Map<String,String> actionType;
	private Map<String, String> escalationMap=null;
	private List<ConfigurationUtilBean> assetTextBox=null;
	private List<ConfigurationUtilBean> assetCheckBox=null;
	private List<ConfigurationUtilBean> assetFile=null;
	private List<ConfigurationUtilBean> assetCalender=null;
	private List<ConfigurationUtilBean> assetRadio=null;
	private List<ConfigurationUtilBean> assetDropDown=null;
	private Map<String, String> emplMap=null;
	private Map<String, String> escL1Emp=null;
	private String moduleName;
	private String outletId;
	
	public String firstActionMethod()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String empId=(String) session.get("empIdofuser").toString().split("-")[1];
				ComplianceCommonOperation cmnOper=new ComplianceCommonOperation();
				AssetUniversalAction AUA=new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				emplMap=new LinkedHashMap<String, String>();
				escL1Emp=new LinkedHashMap<String, String>();
				
				addPageDataFieldsForAssetSupport();
				outletId=AUA.getOutletDetailsByEmpId(empId)[0];
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeListByEmpId(empId);
				
				viaFrom=new LinkedHashMap<String, String>();
				viaFrom.put("SMS","SMS ");
				viaFrom.put("Mail","Mail ");
				viaFrom.put("Both","Both ");
				viaFrom.put("None","None ");
				setViaFrom(viaFrom);
				//Remind To Map
				remindToMap=new HashMap<String,String>();
				remindToMap.put("remToSelf","Self ");
				remindToMap.put("remToOther","Other ");
				remindToMap.put("remToBoth","Both ");
				setRemindToMap(remindToMap);
				//Frequency Map
				frequencyMap=new LinkedHashMap<String, String>();
				frequencyMap.put("OT","One-Time");
				frequencyMap.put("D","Daily");
				frequencyMap.put("W","Weekly");
				frequencyMap.put("M","Monthly");
				frequencyMap.put("BM","Bi-Monthly");
				frequencyMap.put("Q","Quaterly");
				frequencyMap.put("HY","Half Yearly");
				frequencyMap.put("Y","Yearly");
				setFrequencyMap(frequencyMap);
				//Escalation Map
				escalationMap=new LinkedHashMap<String, String>();
				escalationMap.put("Y","Yes");
				escalationMap.put("N","No");
				
				//Action Type Map
				actionType=new LinkedHashMap<String, String>();
				actionType.put("individual", "Individual ");
				actionType.put("group", "Group ");
				
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				String userEmpID = empIdofuser.split("-")[1].trim();
				String deptId=null;
				//String user = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
				deptId=new ComplianceUniversalAction().getEmpDetailsByUserName("ASTM",userName)[4];
				if(userEmpID!=null)
				{
					emplMap=cmnOper.getComplianceContacts(userEmpID,deptId,"ASTM");
					escL1Emp=cmnOper.getComplianceAllContacts(deptId,"ASTM");
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	@SuppressWarnings("unchecked")
	public void addPageDataFieldsForAssetSupport()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> supportColumnList = Configuration.getConfigurationData("mapped_asset_reminder", accountID, connectionSpace, "", 0, "table_name", "asset_reminder");
		supportColumnMap = new ArrayList<ConfigurationUtilBean>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		assetTextBox=new ArrayList<ConfigurationUtilBean>();
		assetFile=new ArrayList<ConfigurationUtilBean>();
		assetRadio=new ArrayList<ConfigurationUtilBean>();
		assetDropDown=new ArrayList<ConfigurationUtilBean>();
		assetCalender=new ArrayList<ConfigurationUtilBean>();
		assetCheckBox=new ArrayList<ConfigurationUtilBean>();
		
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		ConfigurationUtilBean obj;
		
		if (supportColumnList != null && supportColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : supportColumnList)
			{
				obj = new ConfigurationUtilBean();
				if (gdp.getColomnName().trim().equalsIgnoreCase("dueDate") || gdp.getColomnName().trim().equalsIgnoreCase("dueTime"))
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					commonDDMap.add(obj);
				}
				if (gdp.getColomnName().equalsIgnoreCase("assetid"))
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel")|| gdp1.getColomnName().equalsIgnoreCase("assetname")|| gdp1.getColomnName().equalsIgnoreCase("supportfrom"))
						{
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							obj.setValidationType(gdp1.getValidationType());
							obj.setColType(gdp1.getColType());
							obj.setEditable(true);
							if (gdp1.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							commonDDMap.add(obj);
						}
					}
				}
				else if (moduleName!=null && moduleName.equalsIgnoreCase("Support") && gdp.getColomnName().equalsIgnoreCase("vendorid"))
				{
					try
					{
						obj.setKey("vendor_for");
						obj.setValue("Vendor For");
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						supportColumnMap.add(obj);
						
						obj = new ConfigurationUtilBean();
						
						vendorList = new LinkedHashMap<Integer, String>();
						List temp = new ArrayList<String>();
						String query = "SELECT id,vendor_for FROM vendor_type_detail WHERE status='Active' ORDER BY vendor_for";
						temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (temp != null && temp.size() > 0)
						{
							for (Object obj1 : temp)
							{
								Object[] object = (Object[]) obj1;
								vendorList.put((Integer) object[0], object[1].toString());
							}
						}
						obj.setKey(gdp.getColomnName());
						obj.setValue(new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname"));
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						supportColumnMap.add(obj);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else if (moduleName!=null && moduleName.equalsIgnoreCase("Support") && gdp.getColomnName().equalsIgnoreCase("supporttype"))
				{
					try
					{
						supportTypeList = new LinkedHashMap<Integer, String>();
						List temp = new ArrayList<String>();
						String query = "SELECT id,category FROM createasset_support_catg_master WHERE status='Active' ORDER BY category ASC";
						temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (temp != null && temp.size() > 0)
						{
							Object[] object=null;
							for (Object obj1 : temp)
							{
								object = (Object[]) obj1;
								if (object[1]!=null)
                                {
									supportTypeList.put((Integer) object[0], object[1].toString());
                                }
							}
						}
						obj.setKey(gdp.getColomnName());
						obj.setValue(new AssetCommonAction().getMasterFieldName("asset_support_catg", "category"));
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						supportColumnMap.add(obj);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else if (gdp.getColomnName().equalsIgnoreCase("detail"))
				{
					if(moduleName!=null && moduleName.equalsIgnoreCase("Support"))
					{
						obj.setValue(gdp.getHeadingName());
					}
					else
					{
						obj.setValue("Preventive Details");
					}
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					supportColumnMap.add(obj);
				}
				else if (gdp.getColType().equalsIgnoreCase("F")) 
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					assetFile.add(obj);
				}
				else if (gdp.getColType().equalsIgnoreCase("D") && (gdp.getColomnName().equalsIgnoreCase("frequency") || gdp.getColomnName().equalsIgnoreCase("escalation"))) 
				{
					System.out.println(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					assetDropDown.add(obj);
				}
				else if (gdp.getColType().equalsIgnoreCase("date")) 
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					assetCalender.add(obj);
				}
				else if (gdp.getColType().equalsIgnoreCase("R")) 
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					assetRadio.add(obj);
				}
				else if (gdp.getColType().equalsIgnoreCase("C")) 
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					assetCheckBox.add(obj);
				}
				
			}
		}
	}
	
	
	public List<ConfigurationUtilBean> getSupportColumnMap()
	{
		return supportColumnMap;
	}
	public void setSupportColumnMap(List<ConfigurationUtilBean> supportColumnMap)
	{
		this.supportColumnMap = supportColumnMap;
	}
	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}
	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}
	public Map<Integer, String> getVendorList()
	{
		return vendorList;
	}
	public void setVendorList(Map<Integer, String> vendorList)
	{
		this.vendorList = vendorList;
	}
	public Map<Integer, String> getSupportTypeList()
	{
		return supportTypeList;
	}
	public void setSupportTypeList(Map<Integer, String> supportTypeList)
	{
		this.supportTypeList = supportTypeList;
	}
	public List<ConfigurationUtilBean> getAssetDropMap()
	{
		return assetDropMap;
	}
	public void setAssetDropMap(List<ConfigurationUtilBean> assetDropMap)
	{
		this.assetDropMap = assetDropMap;
	}
	public Map<Integer, String> getAssetTypeList()
	{
		return assetTypeList;
	}
	public void setAssetTypeList(Map<Integer, String> assetTypeList)
	{
		this.assetTypeList = assetTypeList;
	}
	public Map<String, String> getViaFrom()
	{
		return viaFrom;
	}
	public void setViaFrom(Map<String, String> viaFrom)
	{
		this.viaFrom = viaFrom;
	}
	public Map<String, String> getRemindToMap()
	{
		return remindToMap;
	}
	public void setRemindToMap(Map<String, String> remindToMap)
	{
		this.remindToMap = remindToMap;
	}
	public Map<String, String> getFrequencyMap()
	{
		return frequencyMap;
	}
	public void setFrequencyMap(Map<String, String> frequencyMap)
	{
		this.frequencyMap = frequencyMap;
	}
	public Map<String, String> getActionType()
	{
		return actionType;
	}
	public void setActionType(Map<String, String> actionType)
	{
		this.actionType = actionType;
	}
	public Map<String, String> getEscalationMap()
	{
		return escalationMap;
	}
	public void setEscalationMap(Map<String, String> escalationMap)
	{
		this.escalationMap = escalationMap;
	}
	public List<ConfigurationUtilBean> getAssetTextBox()
	{
		return assetTextBox;
	}
	public void setAssetTextBox(List<ConfigurationUtilBean> assetTextBox)
	{
		this.assetTextBox = assetTextBox;
	}
	public List<ConfigurationUtilBean> getAssetCheckBox()
	{
		return assetCheckBox;
	}
	public void setAssetCheckBox(List<ConfigurationUtilBean> assetCheckBox)
	{
		this.assetCheckBox = assetCheckBox;
	}
	public List<ConfigurationUtilBean> getAssetFile()
	{
		return assetFile;
	}
	public void setAssetFile(List<ConfigurationUtilBean> assetFile)
	{
		this.assetFile = assetFile;
	}
	
	public List<ConfigurationUtilBean> getAssetCalender()
	{
		return assetCalender;
	}
	public void setAssetCalender(List<ConfigurationUtilBean> assetCalender)
	{
		this.assetCalender = assetCalender;
	}
	
	public List<ConfigurationUtilBean> getAssetRadio()
	{
		return assetRadio;
	}
	public void setAssetRadio(List<ConfigurationUtilBean> assetRadio)
	{
		this.assetRadio = assetRadio;
	}
	public List<ConfigurationUtilBean> getAssetDropDown()
	{
		return assetDropDown;
	}
	public void setAssetDropDown(List<ConfigurationUtilBean> assetDropDown)
	{
		this.assetDropDown = assetDropDown;
	}

	public Map<String, String> getEmplMap()
	{
		return emplMap;
	}
	public void setEmplMap(Map<String, String> emplMap)
	{
		this.emplMap = emplMap;
	}
	public Map<String, String> getEscL1Emp()
	{
		return escL1Emp;
	}
	public void setEscL1Emp(Map<String, String> escL1Emp)
	{
		this.escL1Emp = escL1Emp;
	}
	public String getModuleName()
	{
		return moduleName;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	public String getOutletId()
	{
		return outletId;
	}
	public void setOutletId(String outletId)
	{
		this.outletId = outletId;
	}
	
}
