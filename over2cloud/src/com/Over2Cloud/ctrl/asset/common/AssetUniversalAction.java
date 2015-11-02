package com.Over2Cloud.ctrl.asset.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.AssetCommonAction;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetUniversalAction extends ActionSupport
{

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private Map<Integer, String> deptList = null;
	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
	boolean checkdept = false;
	private String destination;
	private String locationId;
	private Map<Integer, String> employeeList = new HashMap<Integer, String>();
	private HashMap<String, String> employeeDataList = null;
	private String supportAdd, assetAdd, allotOnRepairAdd, headingSerialNo, headingVendorName, vendorValue, supprtValue, headingAssettype, assetValue = null;
	private String headingSupportName, allotmentAdd, headingUsagesType, spareCatgHeadName, reallotPage, issueSpare = null;
	private List<ConfigurationUtilBean> assetColumnMap, dateColumnMap, spareIssueColumnMap, assetDropMap, assetDropMap1;
	private List<ConfigurationUtilBean> supportColumnMap;
	private List<ConfigurationUtilBean> allotmentColumnMap;
	private List<ConfigurationUtilBean> commonDDMap = null;
	private List<ConfigurationUtilBean> locationMap = null;
	private boolean serialNoActive, vendorActive, supportTypeActive, usagesTypeActive, deptLevel, assettype = false;
	private Map<Integer, String> serialNoList, vendorList, supportTypeList, usagesTypeList, spareCatgNoList, assetTypeList, floorList, assetDetail = null;
	private String empId;
	private Map<Integer, String> spareNameMap = null;
	private String deptOrSubDeptId;
	private String dept;
	private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private String mandatroyDept = null;
	private Map<Integer, String> subfloorList = new LinkedHashMap<Integer, String>();
	private String subLocId;
	private Map<String, String> locationDetail = null;
	private String empMobileNo = null;
	private AtomicInteger AN = new AtomicInteger();
	private Map<String, String> supportFrom;
	private Map<String, String> supportFor;
	private Map<String, String> contMappedDeptList;
	private String deptId;
	private JSONArray empData;
	private JSONArray jsonArr = null;
	private JSONArray jsonArr1 = null;
	private JSONArray jsonArr2 = null;
	private Map<String, JSONArray> obj;
	private String assetType;
	private String escL1SelectValue;
	private String escL2SelectValue;
	private String escL3SelectValue;
	private Map<String, String> supportDetail = null;
	private String value;
	private String dataFor;
	private String dueDate;
	private String minDateValue;
	private Map<String, String> remndDate = null;
	private String frequency;
	private String outletId;

	@SuppressWarnings("unchecked")
	public String firstActionMethod()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				assetDropMap = new ArrayList<ConfigurationUtilBean>();
				assetDropMap1 = new ArrayList<ConfigurationUtilBean>();
				AssetUniversalAction AUA = new AssetUniversalAction();

				// Asset
				if (getSupportAdd() != null)
				{
					getContactMappedDept(connectionSpace);
					addPageDataFieldsForAssetSupport();
				}
				if (getAssetAdd() != null)
				{
					addPageDataFieldsForAsset();
				}
				if (getAllotmentAdd() != null)
				{
					usagesTypeList = new LinkedHashMap<Integer, String>();
					floorList = new LinkedHashMap<Integer, String>();
					getContactMappedDept(connectionSpace);
					createAssetAllotPage();
				}
				if (getAllotOnRepairAdd() != null)
				{
					createAllotOnRepairPage();
				}
				if (getReallotPage() != null)
				{
					floorList = new LinkedHashMap<Integer, String>();
					getContactMappedDept(connectionSpace);
					createReAllotPage();
				}
				if (getIssueSpare() != null)
				{
					createIssueSpare();
				}

				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();

				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");

				/* Get Department Data */
				String deptHierarchy = (String) session.get("userDeptLevel");
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
				}

				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");

				// Getting Id, Dept Name for Non Service Department
				departmentlist = new AssetUniversalHelper().getDataFromTable("department", colmName, wherClause, order, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
				}
				assetDropMap = AUA.getAssetConfig();
				if (departmentlist != null)
					departmentlist.removeAll(departmentlist);

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
	public Map<String, String> getContactMappedDept(SessionFactory connectionSpace)
	{
		contMappedDeptList = new LinkedHashMap<String, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List datalist = cbt.executeAllSelectQuery(
				"SELECT dept.id,dept.deptName FROM compliance_contacts AS cc INNER JOIN department AS dept ON cc.forDept_id=dept.id WHERE cc.moduleName='ASTM' ORDER BY deptName", connectionSpace);
		if (datalist != null && datalist.size() > 0)
		{
			Object[] object = null;
			for (Iterator iterator = datalist.iterator(); iterator.hasNext();)
			{
				object = (Object[]) iterator.next();
				if (object[0] != null && object[1] != null)
				{
					contMappedDeptList.put(object[0].toString(), object[1].toString());
				}
			}
		}
		return contMappedDeptList;
	}

	// Assets Starts
	@SuppressWarnings("unchecked")
	public void addPageDataFieldsForAsset()
	{
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		assetColumnMap = new ArrayList<ConfigurationUtilBean>();
		// assetDropMap = new ArrayList<ConfigurationUtilBean>();
		assetDropMap1 = new ArrayList<ConfigurationUtilBean>();
		dateColumnMap = new ArrayList<ConfigurationUtilBean>();
		supportFrom = new LinkedHashMap<String, String>();
		supportFor = new LinkedHashMap<String, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (assetColumnList != null && assetColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : assetColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("serialno")
						&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy ")
						&& !gdp.getColomnName().equalsIgnoreCase("dept_subdept") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("status"))
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
					assetColumnMap.add(obj);
				}
				else if (gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
				{
					if (gdp.getMandatroy().toString().equals("1"))
					{
						mandatroyDept = gdp.getMandatroy().toString();
					}
					else
					{
						mandatroyDept = gdp.getMandatroy().toString();
					}
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("date"))
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					dateColumnMap.add(obj);
					if (gdp.getMandatroy().toString().equals("1"))
					{
						mandatroyDept = gdp.getMandatroy().toString();
					}
					else
					{
						mandatroyDept = gdp.getMandatroy().toString();
					}
					if (gdp.getColomnName().equalsIgnoreCase("installon") || gdp.getColomnName().equalsIgnoreCase("commssioningon") || gdp.getColomnName().equalsIgnoreCase("receivedOn"))
					{
						supportFrom.put(gdp.getColomnName(), gdp.getHeadingName());
					}
				}
				if (gdp.getColomnName().equalsIgnoreCase("vendorname"))
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
					assetDropMap1.add(obj);

					obj = new ConfigurationUtilBean();
					vendorActive = true;
					headingVendorName = new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname");
					vendorValue = gdp.getColomnName();
					try
					{
						vendorList = new LinkedHashMap<Integer, String>();
						List temp = new ArrayList<String>();
						temp = cbt.executeAllSelectQuery("SELECT id,vendor_for FROM vendor_type_detail WHERE status='Active' ORDER BY vendor_for", connectionSpace);
						if (temp != null && temp.size() > 0)
						{
							for (Object obj1 : temp)
							{
								Object[] object = (Object[]) obj1;
								vendorList.put((Integer) object[0], object[1].toString());
							}
						}
						obj.setKey(gdp.getColomnName());
						obj.setValue(headingVendorName);
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
						assetDropMap1.add(obj);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (gdp.getColomnName().equalsIgnoreCase("assettype"))
				{
					assetTypeList = new LinkedHashMap<Integer, String>();
					String query = "SELECT id,assetSubCat FROM createasset_type_master WHERE status='Active' ORDER BY assetSubCat ASC";
					List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							if (object[1] != null && !object[1].toString().equals(""))
							{
								assetTypeList.put((Integer) object[0], object[1].toString());
							}
						}
					}
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
					assetDropMap1.add(obj);
				}
				if (gdp.getColomnName().equalsIgnoreCase("supportfrom"))
				{
					supportTypeActive = true;
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
					assetDropMap1.add(obj);
				}
				if (gdp.getColomnName().equalsIgnoreCase("supportfor"))
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
					assetDropMap1.add(obj);
					for (int i = 1; i <= 12; i++)
					{
						supportFor.put(String.valueOf(i * 3), (i * 3) + " Months");
					}

				}
			}

		}
		else
		{
			addActionMessage("Asset Detail Table Is Not properly Configured !");
		}
	}

	// set data for create asset support add page

	@SuppressWarnings("unchecked")
	public void addPageDataFieldsForAssetSupport()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> supportColumnList = Configuration.getConfigurationData("mapped_asset_support", accountID, connectionSpace, "", 0, "table_name", "asset_support");
		supportColumnMap = new ArrayList<ConfigurationUtilBean>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		ConfigurationUtilBean obj;
		if (supportColumnList != null && supportColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp1 : assetColumnList)
			{
				obj = new ConfigurationUtilBean();
				if (gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
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
					supportColumnMap.add(obj);
				}
				if (gdp1.getColomnName().equalsIgnoreCase("assetname"))
				{
					headingSerialNo = gdp1.getHeadingName().toString();
				}
			}
		}
		if (supportColumnList != null && supportColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : supportColumnList)
			{
				obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());

					if (gdp.getColomnName().equalsIgnoreCase("supportfrom") || gdp.getColomnName().equalsIgnoreCase("supportto"))
					{
						obj.setEditable(true);
					}
					else
					{
						obj.setEditable(false);
					}
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
				if (gdp.getColType().trim().equalsIgnoreCase("D"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("assetid"))
					{
						serialNoActive = true;
						serialNoList = new LinkedHashMap<Integer, String>();
						try
						{
							String query = "SELECT id,assetname FROM asset_detail where id NOT IN(SELECT assetid FROM asset_support_detail) AND status='Active'  ORDER BY serialno ASC";
							List slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (slnoList == null)
							{
								query = "SELECT id,assetname FROM asset_detail WHERE status='Active' ORDER BY serialno ASC";
								slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							}
							if (slnoList != null && slnoList.size() > 0)
							{
								for (Object obj1 : slnoList)
								{
									Object[] object = (Object[]) obj1;
									serialNoList.put((Integer) object[0], object[1].toString());
								}
							}
							obj.setKey(gdp.getColomnName());
							obj.setValue(headingSerialNo);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}

					}
					else if (gdp.getColomnName().equalsIgnoreCase("vendorid"))
					{
						vendorActive = true;
						headingVendorName = new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname");
						vendorValue = gdp.getColomnName();
						try
						{
							vendorList = new LinkedHashMap<Integer, String>();
							List temp = new ArrayList<String>();
							String query = "SELECT id,vendorname FROM createvendor_master WHERE status='Active' ORDER BY vendorname ASC";
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
							obj.setValue(headingVendorName);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("supporttype"))
					{
						supportTypeActive = true;
						headingSupportName = new AssetCommonAction().getMasterFieldName("asset_support_catg", "category");
						supprtValue = gdp.getColomnName();
						try
						{
							supportTypeList = new LinkedHashMap<Integer, String>();
							List temp = new ArrayList<String>();
							String query = "SELECT id,category FROM createasset_support_catg_master WHERE status='Active' ORDER BY category ASC";
							temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (temp != null && temp.size() > 0)
							{
								for (Object obj1 : temp)
								{
									Object[] object = (Object[]) obj1;
									supportTypeList.put((Integer) object[0], object[1].toString());
								}
							}
							obj.setKey(gdp.getColomnName());
							obj.setValue(headingSupportName);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("employeeid"))
					{
						String empHeading = new AssetCommonAction().getMasterFieldName("employee_basic_configuration", "empName");
						try
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(empHeading);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
					{
						if (gdp.getMandatroy().toString().equals("1"))
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
						else
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
					}
				}
			}
		}
	}

	public void createAssetAllotPage()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> allotColumnList = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
		allotmentColumnMap = new ArrayList<ConfigurationUtilBean>();
		dateColumnMap = new ArrayList<ConfigurationUtilBean>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		locationMap = new ArrayList<ConfigurationUtilBean>();
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		if (allotColumnList != null && allotColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp1 : assetColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
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
					allotmentColumnMap.add(obj);
				}
				if (gdp1.getColomnName().equalsIgnoreCase("assetname"))
				{
					headingSerialNo = gdp1.getHeadingName().toString();
				}
				if (gdp1.getColomnName().equalsIgnoreCase("assettype"))
				{
					headingAssettype = gdp1.getHeadingName().toString();
				}
			}
		}
		if (allotColumnList != null && allotColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : allotColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("maplevel") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept")
						&& !gdp.getColomnName().equalsIgnoreCase("flag"))
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					obj.setEditable(false);
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					allotmentColumnMap.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("D"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("assetid"))
					{
						serialNoActive = true;
						serialNoList = new LinkedHashMap<Integer, String>();
						try
						{
							String query = "SELECT id,assetname From asset_detail where flag=1 and id NOT IN(select allot.assetid from asset_allotment_detail as allot where allot.flag=1) ORDER BY serialno ASC";
							List slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (slnoList == null)
							{
								query = "SELECT id,assetname FROM asset_detail WHERE status='Active' ORDER BY serialno ASC";
								slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							}
							if (slnoList != null && slnoList.size() > 0)
							{
								for (Object obj1 : slnoList)
								{
									Object[] object = (Object[]) obj1;
									serialNoList.put((Integer) object[0], object[1].toString());
								}
							}

							obj.setKey(gdp.getColomnName());
							obj.setValue(headingSerialNo);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("employeeid"))
					{
						String empHeading = new AssetCommonAction().getMasterFieldName("employee_basic_configuration", "empName");
						try
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(empHeading);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
					{
						if (gdp.getMandatroy().toString().equals("1"))
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
						else
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("usagestype"))
					{
						obj = new ConfigurationUtilBean();
						obj.setKey(gdp.getColomnName());
						obj.setValue(gdp.getHeadingName());
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
						usagesTypeList.put(0, "Unique");
						usagesTypeList.put(1, "Common");
					}
					else if (gdp.getColomnName().equals("sublocation"))
					{
						getFloorDetailList();
						List<GridDataPropertyView> locBodyColumnList = Configuration.getConfigurationData("mapped_buddy_setting", accountID, connectionSpace, "", 0, "table_name",
								"buddy_setting_configuration");
						if (locBodyColumnList != null && locBodyColumnList.size() > 0)
						{
							for (GridDataPropertyView gdp1 : locBodyColumnList)
							{
								obj = new ConfigurationUtilBean();
								if (gdp1.getColomnName().equalsIgnoreCase("floorname") || gdp1.getColomnName().equalsIgnoreCase("subfloorname"))
								{

									obj.setKey(gdp1.getColomnName());
									obj.setValue(gdp1.getHeadingName());
									obj.setColType("D");
									obj.setEditable(true);
									if (gdp1.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									locationMap.add(obj);
								}
								else if (gdp1.getColomnName().equalsIgnoreCase("intecom"))
								{
									obj.setKey(gdp1.getColomnName());
									obj.setValue(gdp1.getHeadingName());
									obj.setColType("T");
									obj.setEditable(true);
									if (gdp1.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									locationMap.add(obj);
								}
							}
						}
					}
				}
				if (gdp.getColType().trim().equalsIgnoreCase("Time"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("issueon") || gdp.getColomnName().equalsIgnoreCase("issueat"))
					{
						ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
						timeObj.setKey(gdp.getColomnName());
						timeObj.setValue(gdp.getHeadingName());
						timeObj.setValidationType(gdp.getValidationType());
						timeObj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							timeObj.setMandatory(true);
						}
						else
						{
							timeObj.setMandatory(false);
						}
						dateColumnMap.add(timeObj);
					}
				}
			}
			if (headingAssettype != null && !headingAssettype.equalsIgnoreCase(""))
			{
				assetTypeList = new LinkedHashMap<Integer, String>();
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				obj.setKey("assettype");
				obj.setValue(headingAssettype);
				obj.setColType("D");
				obj.setMandatory(true);
				commonDDMap.add(obj);
				String query = "SELECT id,assetSubCat FROM createasset_type_master ORDER BY assetSubCat ASC";
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						if (object[1] != null && !object[1].toString().equals(""))
						{
							assetTypeList.put((Integer) object[0], object[1].toString());
						}
					}

				}

			}
		}
	}

	public void createReAllotPage()
	{
		List<GridDataPropertyView> allotColumnList = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
		allotmentColumnMap = new ArrayList<ConfigurationUtilBean>();
		dateColumnMap = new ArrayList<ConfigurationUtilBean>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		locationMap = new ArrayList<ConfigurationUtilBean>();
		if (allotColumnList != null && allotColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : allotColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy ") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept")
						&& !gdp.getColomnName().equalsIgnoreCase("flag"))
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
					allotmentColumnMap.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("D"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("employeeid"))
					{
						String empHeading = new AssetCommonAction().getMasterFieldName("employee_basic_configuration", "empName");
						try
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(empHeading);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
					{
						if (gdp.getMandatroy().toString().equals("1"))
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
						else
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
					}
					else if (gdp.getColomnName().equals("sublocation"))
					{
						getFloorDetailList();
						List<GridDataPropertyView> locBodyColumnList = Configuration.getConfigurationData("mapped_buddy_setting", accountID, connectionSpace, "", 0, "table_name",
								"buddy_setting_configuration");
						if (locBodyColumnList != null && locBodyColumnList.size() > 0)
						{
							for (GridDataPropertyView gdp1 : locBodyColumnList)
							{
								obj = new ConfigurationUtilBean();
								if (gdp1.getColomnName().equalsIgnoreCase("floorname") || gdp1.getColomnName().equalsIgnoreCase("subfloorname"))
								{

									obj.setKey(gdp1.getColomnName());
									obj.setValue(gdp1.getHeadingName());
									obj.setColType("D");
									obj.setEditable(true);
									if (gdp1.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									locationMap.add(obj);
								}
								else if (gdp1.getColomnName().equalsIgnoreCase("intecom"))
								{
									obj.setKey(gdp1.getColomnName());
									obj.setValue(gdp1.getHeadingName());
									obj.setColType("T");
									obj.setEditable(true);
									if (gdp1.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									locationMap.add(obj);
								}
							}
						}
					}
				}

				if (gdp.getColType().trim().equalsIgnoreCase("Time"))
				{
					if (!gdp.getColomnName().equalsIgnoreCase("returnon"))
					{
						ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
						timeObj.setKey(gdp.getColomnName());
						timeObj.setValue(gdp.getHeadingName());
						timeObj.setValidationType(gdp.getValidationType());
						timeObj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							timeObj.setMandatory(true);
						}
						else
						{
							timeObj.setMandatory(false);
						}
						dateColumnMap.add(timeObj);
					}
				}
			}
		}
	}

	public void createAllotOnRepairPage()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> allotRepairColumnList = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
		allotmentColumnMap = new ArrayList<ConfigurationUtilBean>();
		dateColumnMap = new ArrayList<ConfigurationUtilBean>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		if (allotRepairColumnList != null && allotRepairColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp1 : assetColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
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
					allotmentColumnMap.add(obj);
				}
				if (gdp1.getColomnName().equalsIgnoreCase("serialno"))
				{
					headingSerialNo = gdp1.getHeadingName().toString();
				}
			}
		}

		if (allotRepairColumnList != null && allotRepairColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : allotRepairColumnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("maplevel") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept")
						&& !gdp.getColomnName().equalsIgnoreCase("flag"))
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					obj.setEditable(false);
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					allotmentColumnMap.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("D"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("assetid"))
					{
						String dept_subdept_id = null;
						try
						{
							List empData = new AssetUniversalHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), (String) session.get("userDeptLevel"), connectionSpace);
							if (empData != null && empData.size() > 0)
							{
								for (Iterator iterator = empData.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									dept_subdept_id = object[3].toString();
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						serialNoActive = true;
						serialNoList = new LinkedHashMap<Integer, String>();
						try
						{
							String tabCheckQuery = "SHOW TABLES LIKE 'asset_allotment_detail'";
							List temp = cbt.executeAllSelectQuery(tabCheckQuery.toString(), connectionSpace);
							List slnoList = null;
							String query;
							if (temp != null && temp.size() > 0)
							{
								// query=
								// "select id,serialno From asset_detail where (assettype=1 and flag=1) and id NOT IN (select allot.assetid from asset_allotment_detail as allot where allot.flag=1) AND dept_subdept="
								// +dept_subdept_id+" ORDER BY serialno ASC";
								query = "select id,serialno From asset_detail where (assettype=1 and flag=1) and id NOT IN (select allot.assetid from asset_allotment_detail as allot where allot.flag=1) ORDER BY serialno ASC";
								slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							}
							else
							{
								// query=
								// "SELECT id,serialno FROM asset_detail WHERE dept_subdept="
								// +dept_subdept_id+" ORDER BY serialno ASC";
								query = "SELECT id,serialno FROM asset_detail ORDER BY serialno ASC";
								slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							}
							if (slnoList != null && slnoList.size() > 0)
							{
								for (Object obj1 : slnoList)
								{
									Object[] object = (Object[]) obj1;
									serialNoList.put((Integer) object[0], object[1].toString());
								}
							}
							obj.setKey(gdp.getColomnName());
							obj.setValue(headingSerialNo);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("employeeid"))
					{
						String empHeading = new AssetCommonAction().getMasterFieldName("employee_basic_configuration", "empName");
						try
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(empHeading);
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
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
					{
						if (gdp.getMandatroy().toString().equals("1"))
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
						else
						{
							mandatroyDept = gdp.getMandatroy().toString();
						}
					}

				}
				if (gdp.getColType().trim().equalsIgnoreCase("Time"))
				{
					ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
					timeObj.setKey(gdp.getColomnName());
					timeObj.setValue(gdp.getHeadingName());
					timeObj.setValidationType(gdp.getValidationType());
					timeObj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						timeObj.setMandatory(true);
					}
					else
					{
						timeObj.setMandatory(false);
					}
					dateColumnMap.add(timeObj);
				}
			}
		}
	}

	public void createIssueSpare()
	{
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_spare_issue_master", accountID, connectionSpace, "", 0, "table_name", "spare_issue_master");
		spareIssueColumnMap = new ArrayList<ConfigurationUtilBean>();
		dateColumnMap = new ArrayList<ConfigurationUtilBean>();
		spareCatgNoList = new LinkedHashMap<Integer, String>();
		commonDDMap = new ArrayList<ConfigurationUtilBean>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		ConfigurationUtilBean obj;
		if (assetColumnList != null && assetColumnList.size() > 0)
		{
			for (GridDataPropertyView gdp : assetColumnList)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy ") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
				{
					obj = new ConfigurationUtilBean();
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
					spareIssueColumnMap.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
					timeObj.setKey(gdp.getColomnName());
					timeObj.setValue(gdp.getHeadingName());
					timeObj.setValidationType(gdp.getValidationType());
					timeObj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						timeObj.setMandatory(true);
					}
					else
					{
						timeObj.setMandatory(false);
					}
					dateColumnMap.add(timeObj);
				}
				// if(gdp.getColomnName().equalsIgnoreCase("spare_name"))
				if (gdp.getColType().trim().equalsIgnoreCase("D"))
				{
					String query = "select scd.id,scd.spare_category from createspare_catg_master " + "as scd where scd.id in(select spare_category from spare_detail where "
							+ "id in( select spare_name from nonconsume_spare_detail " + "where total_nonconsume_spare>0)) order by scd.spare_category asc";
					List temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (temp != null && temp.size() > 0)
					{
						for (Object obj1 : temp)
						{
							Object[] object = (Object[]) obj1;
							spareCatgNoList.put((Integer) object[0], object[1].toString());
						}
					}
					if (spareCatgNoList != null && spareCatgNoList.size() > 0)
					{
						spareCatgHeadName = new AssetCommonAction().getMasterFieldName("spare_catg_master", "spare_category");
						vendorActive = true;
						obj = new ConfigurationUtilBean();
						obj.setKey("spare_category");
						obj.setValue(spareCatgHeadName);
						obj.setValidationType("");
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
					else
					{
						spareNameMap = new LinkedHashMap<Integer, String>();
						String query1 = "Select id,spare_name from spare_detail where id "
								+ "IN(select spare_name from nonconsume_spare_detail where total_nonconsume_spare>0) order by spare_name ASC";
						List temp1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
						if (temp1 != null && temp1.size() > 0)
						{
							for (Object obj1 : temp1)
							{
								Object[] object = (Object[]) obj1;
								spareNameMap.put((Integer) object[0], object[1].toString());
							}
						}
					}
					usagesTypeActive = true;
					obj = new ConfigurationUtilBean();
					obj.setKey("spare_name");
					obj.setValue(new AssetCommonAction().getFieldName("mapped_spare_master", "spare_master", "spare_name"));
					obj.setValidationType("");
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
				if (gdp.getColomnName().equalsIgnoreCase("deptHierarchy"))
				{
					mandatroyDept = gdp.getMandatroy().toString();
					deptLevel = true;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void getFloorDetailList()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query1 = "Select id,floorname from buddy_floor_info order by floorname ASC";
		List data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					floorList.put((Integer) object[0], object[1].toString());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String getEmpDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				String deptLevel = (String) session.get("userDeptLevel");
				List empList = new ArrayList<String>();
				empList.add("id");
				empList.add("empName");
				Map<String, Object> temp = new HashMap<String, Object>();
				if (deptLevel.equals("2"))
				{
					temp.put("subdept", getDeptOrSubDeptId());
				}
				else if (deptLevel.equals("1"))
				{
					temp.put("dept", getDeptOrSubDeptId());
				}

				Map<String, Object> order = new HashMap<String, Object>();
				order.put("empName", "ASC");

				empList = new AssetUniversalHelper().getDataFromTable("employee_basic", empList, temp, null, order, connectionSpace);
				if (empList.size() > 0)
				{
					for (Object c : empList)
					{
						Object[] dataC = (Object[]) c;
						employeeList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("EmpDiv", "");
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getEmpMobDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				employeeDataList = new HashMap<String, String>();
				if(empId!=null)
				{
					StringBuilder query=new StringBuilder();
					query.append("SELECT emp.mobOne FROM employee_basic AS emp ");
					query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id ");
					query.append(" WHERE cc.id="+empId);
					List dataList=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						employeeDataList.put("mobOne", dataList.get(0).toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("EmpMobDiv", "");
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void setsubDept_DeptTags(String level)
	{
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					if (mandatroyDept != null && mandatroyDept.equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					subDept_DeptLevelName.add(obj);
				}

			}
		}

		if (level.equals("2"))
		{
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0)
			{
				for (GridDataPropertyView gdv1 : subdept_deptList)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname"))
					{
						obj.setKey(gdv1.getColomnName());
						obj.setValue(gdv1.getHeadingName());
						obj.setValidationType(gdv1.getValidationType());
						obj.setColType("D");
						if (mandatroyDept != null && mandatroyDept.equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						subDept_DeptLevelName.add(obj);
					}

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String getSubDepartment()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List list = new ArrayList<String>();
				list.add("id");
				list.add("subdeptname");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subdeptname", "ASC");
				temp.put("deptid", getDept());
				list = new HelpdeskUniversalHelper().getDataFromTable("subdepartment", list, temp, null, order, connectionSpace);
				if (list.size() > 0)
				{
					for (Object c : list)
					{
						Object[] dataC = (Object[]) c;
						subDeptList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("subDeptDiv", "");
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// Buddy !!!!!
	@SuppressWarnings("unchecked")
	public String getSubLocation()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List list = new ArrayList<String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query1 = "Select id,subfloorname from buddy_sub_floor_info where floorname1='" + getLocationId() + "' order by subfloorname ASC";
				list = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (list.size() > 0)
				{
					for (Object c : list)
					{
						Object[] dataC = (Object[]) c;
						subfloorList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("subLoc", "");
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getSubLocationDetails()
	{
		String returnResult = ERROR;
		try
		{
			locationDetail = new LinkedHashMap<String, String>();
			List subLocDetail = new ArrayList<String>();
			String query = "SELECT subflr.intecom,subflr.subfloorname,flr.floorcode FROM buddy_sub_floor_info as subflr INNER JOIN buddy_floor_info "
					+ "as flr on flr.id=subflr.floorname1 WHERE subflr.id='" + getSubLocId() + "'";
			subLocDetail = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (subLocDetail != null && subLocDetail.size() > 0)
			{
				for (Iterator iterator = subLocDetail.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();

					if (objectCol[0] == null && objectCol[0].toString().equals(""))
					{
						locationDetail.put("intecom", "NA");
					}
					else
					{
						locationDetail.put("intecom", objectCol[0].toString());
					}
					if (objectCol[1] == null && objectCol[1].toString().equals("") && objectCol[2] == null && objectCol[2].toString().equals(""))
					{
						locationDetail.put("location", "NA");
					}
					else
					{
						locationDetail.put("location", objectCol[2].toString() + "," + objectCol[1].toString());
					}
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getAllotedAssetDetails()
	{
		String returnResult = ERROR;
		try
		{
			assetDetail = new LinkedHashMap<Integer, String>();
			List astDetail = new ArrayList<String>();
			if (empMobileNo != null && !empMobileNo.equals("") && !empMobileNo.equalsIgnoreCase("undefined"))
			{
				String query = "SELECT ast.id,ast.assetname FROM asset_detail AS ast INNER JOIN asset_allotment_detail AS astalt ON astalt.assetid=ast.id "
						+ "INNER JOIN employee_basic AS emp ON emp.id=astalt.employeeid WHERE emp.mobOne='" + empMobileNo + "' ORDER BY ast.assetname";
				astDetail = new createTable().executeAllSelectQuery(query, connectionSpace);
			}
			else if (subLocId != null && !subLocId.equals(""))
			{
				String query = "SELECT ast.id,ast.assetname FROM asset_detail AS ast INNER JOIN asset_allotment_detail AS astalt ON astalt.assetid=ast.id " + "WHERE astalt.sublocation='" + subLocId
						+ "' ORDER BY ast.assetname";
				astDetail = new createTable().executeAllSelectQuery(query, connectionSpace);
			}
			else if (empMobileNo.equalsIgnoreCase("undefined"))
			{
				String deptHierarchy = (String) session.get("userDeptLevel");
				String empId = null;
				String user = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
				List loggedUserData = new HelpdeskUniversalAction().getEmpDataByUserName(user, deptHierarchy);
				if (loggedUserData != null && loggedUserData.size() > 0)
				{
					for (Iterator iterator = loggedUserData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (deptHierarchy.equalsIgnoreCase("2"))
						{
							empId = object[6].toString();
						}
						else if (deptHierarchy.equalsIgnoreCase("1"))
						{
							empId = object[5].toString();
						}
					}
					String query = "SELECT ast.id,ast.assetname FROM asset_detail AS ast INNER JOIN asset_allotment_detail AS astalt ON astalt.assetid=ast.id "
							+ "INNER JOIN employee_basic AS emp ON emp.id=astalt.employeeid WHERE emp.id='" + empId + "' ORDER BY ast.assetname";
					astDetail = new createTable().executeAllSelectQuery(query, connectionSpace);
				}
			}
			if (astDetail != null && astDetail.size() > 0)
			{
				for (Iterator iterator = astDetail.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol != null)
					{
						assetDetail.put(Integer.valueOf(objectCol[0].toString()), objectCol[1].toString());
					}
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getSerialNo(String deptid, SessionFactory connectionSpace)
	{
		String seriesNo = "NA", series_type = "", series_series = "", prefix = "", sufix = "", series_no = "";
		List serialNoSeries = new ArrayList();
		List deptserialNoSeries = new ArrayList();
		try
		{
			// Code for getting the Ticket Type from table
			serialNoSeries = new HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
			if (serialNoSeries != null && serialNoSeries.size() == 1)
			{
				for (Iterator iterator = serialNoSeries.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					series_type = object[1].toString();
					series_series = object[2].toString();
				}
				// Code for getting the first time counters of Feedback Status
				// table, If get ticket counts greater than Zero than go in If
				// block and if get 0 than go in else block
				series_no = new AssetUniversalHelper().getLatestSeries(series_type, deptid, connectionSpace);
				if (series_no != null && !series_no.equals(""))
				{
					if (series_no != null && !series_no.equals("") && series_type.equalsIgnoreCase("N"))
					{
						AN.set(Integer.parseInt(series_no));
						seriesNo = prefix + AN.incrementAndGet();
					}
					else if (series_no != null && !series_no.equals("") && series_type.equalsIgnoreCase("D"))
					{
						int deptSeriesLength = series_no.lastIndexOf("/") + 1;
						int serialLength = series_no.length();
						prefix = series_no.substring(0, deptSeriesLength);
						sufix = series_no.substring(deptSeriesLength, serialLength);
						AN.set(Integer.parseInt(sufix));
						seriesNo = prefix + AN.incrementAndGet();
					}
				}
				else
				{
					if (series_type.equalsIgnoreCase("N"))
					{
						if (series_series != null && !series_series.equals("") && !series_series.equals("NA"))
						{
							seriesNo = series_series;
						}
					}
					else if (series_type.equalsIgnoreCase("D"))
					{
						deptserialNoSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid, "prefix", "ASC", connectionSpace);
						if (deptserialNoSeries != null && deptserialNoSeries.size() == 1)
						{
							for (Iterator iterator2 = deptserialNoSeries.iterator(); iterator2.hasNext();)
							{
								Object[] object1 = (Object[]) iterator2.next();
								if (object1[2] != null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA"))
								{
									seriesNo = object1[2].toString() + object1[3].toString();
								}
								else
								{
									seriesNo = "NA";
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return seriesNo;
	}

	public String abccd()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getEmployeeBydeptId()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				empData = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT cc.id,emp.empName FROM compliance_contacts AS cc ");
				query.append(" INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE cc.moduleName='ASTM' AND cc.forDept_id=" + deptId);
				query.append(" ORDER BY emp.empName");
				System.out.println("@@@@@@11 "+query.toString());
				List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] object = null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject jsonMap = new JSONObject();
							jsonMap.put("id", object[0].toString());
							jsonMap.put("name", object[1].toString());
							empData.add(jsonMap);
						}
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getAssetSearch()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				getAssetConfig();
				assetTypeList();
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public List<ConfigurationUtilBean> getAssetConfig()
	{
		try
		{
			List<GridDataPropertyView> supportColumnList = Configuration.getConfigurationData("mapped_asset_reminder", accountID, connectionSpace, "", 0, "table_name", "asset_reminder");
			assetDropMap = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
			if (supportColumnList != null && supportColumnList.size() > 0)
			{
				ConfigurationUtilBean obj;
				for (GridDataPropertyView gdp1 : supportColumnList)
				{
					if (gdp1.getColomnName().equalsIgnoreCase("assetid"))
					{
						if (assetColumnList != null && assetColumnList.size() > 0)
						{
							for (GridDataPropertyView gdp : assetColumnList)
							{
								obj = new ConfigurationUtilBean();
								if (gdp.getColomnName().equalsIgnoreCase("assettype") || gdp.getColomnName().equalsIgnoreCase("serialno") || gdp.getColomnName().equalsIgnoreCase("assetname")
										|| gdp.getColomnName().equalsIgnoreCase("mfgserialno"))
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
									assetDropMap.add(obj);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return assetDropMap;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> assetTypeList()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			assetTypeList = new LinkedHashMap<Integer, String>();
			String query = "SELECT id,assetSubCat FROM createasset_type_master WHERE status='Active' ORDER BY assetSubCat ASC";
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] object = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[1] != null && !object[1].toString().equals(""))
					{
						assetTypeList.put((Integer) object[0], object[1].toString());
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return assetTypeList;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> assetTypeListByEmpId(String empId)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			assetTypeList = new LinkedHashMap<Integer, String>();
			StringBuilder query = new StringBuilder();
			query.append("SELECT astCatg.id,astCatg.assetSubCat FROM asset_allotment_detail AS allot ");
			query.append(" INNER JOIN asset_detail AS asset ON asset.id=allot.assetid ");
			query.append(" INNER JOIN createasset_type_master AS astCatg ON astCatg.id=asset.assettype ");
			query.append(" WHERE allot.outletid=(SELECT distinct outlet.id FROM employee_basic AS emp ");
			query.append(" INNER JOIN associate_contact_data AS outletCont ON outletCont.department=emp.deptname ");
			query.append(" INNER JOIN associate_basic_data AS outlet ON outlet.id=outletCont.associateName WHERE emp.id="+empId+") ORDER BY astCatg.assetSubCat ");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] object = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[1] != null && !object[1].toString().equals(""))
					{
						assetTypeList.put((Integer) object[0], object[1].toString());
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return assetTypeList;
	}
	
	public String[] getOutletDetailsByEmpId(String empId)
	{
		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		String[] values =null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct outlet.id,outlet.associateName FROM employee_basic AS emp ");
			query.append(" INNER JOIN associate_contact_data AS outletCont ON outletCont.department=emp.deptname ");
			query.append(" INNER JOIN associate_basic_data AS outlet ON outlet.id=outletCont.associateName WHERE emp.id="+empId);
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[2];
				Object[] object=(Object[])dataList.get(0);
				values[0]=CUA.getValueWithNullCheck(object[0]);
				values[1]=CUA.getValueWithNullCheck(object[1]);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public String getAssetDetailSearch()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Object object[] = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				jsonArr = new JSONArray();
				jsonArr1 = new JSONArray();
				jsonArr2 = new JSONArray();
				StringBuilder query = new StringBuilder();
				//query.append("SELECT id,serialno as code,assetname,mfgserialno FROM asset_detail where assettype='" + assetType + "'");
				query.append("SELECT asset.id,asset.serialno AS code,asset.assetname,asset.mfgserialno FROM asset_allotment_detail AS allot ");
				query.append("INNER JOIN asset_detail AS asset ON asset.id=allot.assetid WHERE allot.outletid="+outletId+" AND asset.assettype="+ assetType);
				System.out.println("Query Str4ing is "+query.toString());
				List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data2 != null && data2.size() > 0)
				{
					JSONObject formDetailsJson = new JSONObject();
					obj = new LinkedHashMap<String, JSONArray>();
					for (Iterator iterator = data2.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", object[0].toString());
							formDetailsJson.put("NAME", object[1].toString());
							jsonArr.add(formDetailsJson);
						}
						if (object[2] != null)
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", object[0].toString());
							formDetailsJson.put("NAME", object[2].toString());
							jsonArr1.add(formDetailsJson);
						}
						if (object[3] != null)
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", object[0].toString());
							formDetailsJson.put("NAME", object[3].toString());
							jsonArr2.add(formDetailsJson);
						}
					}
					obj.put("code", jsonArr);
					obj.put("assetName", jsonArr1);
					obj.put("mfgSerialNo", jsonArr2);
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getNextEscMap()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String deptId = null;
				deptId=new ComplianceUniversalAction().getEmpDetailsByUserName("ASTM",userName)[4];
				if (deptId != null && !deptId.equalsIgnoreCase(""))
				{
					jsonArr = new JSONArray();
					if (escL2SelectValue != null && !escL2SelectValue.equalsIgnoreCase(""))
					{
						escL1SelectValue = escL1SelectValue + "," + escL2SelectValue;
					}
					if (escL3SelectValue != null && !escL3SelectValue.equalsIgnoreCase(""))
					{
						escL1SelectValue = escL1SelectValue + "," + escL3SelectValue;
					}
					StringBuilder query = new StringBuilder();
					query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
					query.append(" WHERE cc.id NOT IN(" + escL1SelectValue + ") AND forDept_id=" + deptId + " AND moduleName='ASTM' AND work_status!=1 order by empName asc");
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
						JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArr.add(formDetailsJson);
							}
						}
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getAssetSupportDetail()
	{
		String returnResult = ERROR;
		try
		{
			supportDetail = new LinkedHashMap<String, String>();
			String supportTo = null;
			String query = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			if (dataFor != null && dataFor.equalsIgnoreCase("normal"))
			{
				query = "SELECT assetname,serialno,assetbrand,assetmodel,supportfrom,supportfor,receivedOn,installon,commssioningon,id FROM asset_detail WHERE id='" + value + "'";
			}
			else if (dataFor != null && dataFor.equalsIgnoreCase("search"))
			{
				query = "SELECT assetname,serialno,assetbrand,assetmodel,supportfrom,supportfor,receivedOn,installon,commssioningon,id FROM asset_detail WHERE assetname='" + value + "' OR serialno='"
						+ value + "' OR mfgserialno='" + value + "'";
			}

			List assetDetails = cbt.executeAllSelectQuery(query, connectionSpace);
			if (assetDetails != null && assetDetails.size() > 0)
			{
				Object[] object = null;
				for (Iterator iterator = assetDetails.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						supportDetail.put("assetName", object[0].toString());
					}
					else
					{
						supportDetail.put("assetName", "NA");
					}
					if (object[1] != null)
					{
						supportDetail.put("serialno", object[1].toString());
					}
					else
					{
						supportDetail.put("serialno", "NA");
					}
					if (object[2] != null)
					{
						supportDetail.put("assetbrand", object[2].toString());
					}
					else
					{
						supportDetail.put("assetbrand", "NA");
					}
					if (object[3] != null)
					{
						supportDetail.put("assetmodel", object[3].toString());
					}
					else
					{
						supportDetail.put("assetmodel", "NA");
					}
					if (object[4] != null)
					{
						if (object[4].toString().equalsIgnoreCase("receivedOn"))
						{
							supportDetail.put("supportfrom", DateUtil.convertDateToIndianFormat(object[6].toString()));
							supportTo = object[6].toString();
						}
						else if (object[4].toString().equalsIgnoreCase("installOn"))
						{
							supportDetail.put("supportfrom", DateUtil.convertDateToIndianFormat(object[7].toString()));
							supportTo = object[7].toString();
						}
						else if (object[4].toString().equalsIgnoreCase("commssioningon"))
						{
							supportDetail.put("supportfrom", DateUtil.convertDateToIndianFormat(object[8].toString()));
							supportTo = object[8].toString();
						}
					}
					else
					{
						supportDetail.put("supportfrom", "NA");
					}
					if (object[5] != null)
					{
						supportDetail.put("supportfor", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", Integer.valueOf(object[5].toString()), supportTo)));
					}
					else
					{
						supportDetail.put("supportfor", "NA");
					}
					if (object[9] != null)
					{
						supportDetail.put("assetID", object[9].toString());
					}
					else
					{
						supportDetail.put("assetID", "NA");
					}
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public int getMaximumAssetReminderId()
	{

		List assetList = null;
		int maxAssetReminderId = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		qryString.append("select max(id) from asset_reminder_details");
		assetList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		for (int i = 0; i < assetList.size(); i++)
		{
			maxAssetReminderId = (Integer) assetList.get(0);
		}
		return maxAssetReminderId;
	}

	// saveComplReminder
	public boolean saveAssetReminder(Map<String, String> dataWithColumnName)
	{
		boolean status = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		TableColumes ob2 = new TableColumes();

		ob2 = new TableColumes();
		ob2.setColumnname("reminder_name");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		ob2 = new TableColumes();
		ob2.setColumnname("reminder_code");
		ob2.setDatatype("varchar(10)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openDate
		ob2 = new TableColumes();
		ob2.setColumnname("due_date");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);
		// openDate
		ob2 = new TableColumes();
		ob2.setColumnname("due_time");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("remind_date");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);
		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("remind_time");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		ob2 = new TableColumes();
		ob2.setColumnname("remind_interval");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("asseReminder_id");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// set status
		ob2 = new TableColumes();
		ob2.setColumnname("reminder_status");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// set status
		ob2 = new TableColumes();
		ob2.setColumnname("status");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		status = cbt.createTable22("asset_reminder_data", Tablecolumesaaa, connectionSpace);
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		Iterator<Entry<String, String>> hiterator = dataWithColumnName.entrySet().iterator();
		while (hiterator.hasNext())
		{
			Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
			ob = new InsertDataTable();
			ob.setColName(paramPair.getKey().toString());
			ob.setDataName(paramPair.getValue().toString());
			insertData.add(ob);
		}
		status = cbt.insertIntoTable("asset_reminder_data", insertData, connectionSpace);
		return status;
	}

	public List getEmpDataByUserName(String uName, String deptLevel, String moduleName)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id as deptid, dept.deptName,emp.id as empid,emp.city,uac.userType,cc.id from employee_basic as emp"
						+ " inner join compliance_contacts as cc on cc.emp_id=emp.id" + " inner join subdepartment as sdept on emp.subdept=sdept.id"
						+ " inner join department as dept on sdept.deptid=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where cc.moduleName='" + moduleName
						+ "' and uac.userID='" + uName + "' and cc.forDept_id=dept.id ";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName,emp.id as empid,emp.city,cc.id from employee_basic as emp"
						+ " inner join compliance_contacts as cc on cc.emp_id=emp.id" + " inner join department as dept on emp.dept=dept.id"
						+ " inner join useraccount as uac on emp.useraccountid=uac.id where cc.moduleName='" + moduleName + "' and uac.userID='" + uName + "' and cc.forDept_id=dept.id";
			}
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	public String getReminder()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (frequency != null && dueDate != null && !frequency.equalsIgnoreCase("D"))
				{
					remndDate = new LinkedHashMap<String, String>();
					if (frequency.equalsIgnoreCase("OT"))
					{
						int dateDiff = DateUtil.getNoOfDays(DateUtil.convertDateToUSFormat(dueDate), DateUtil.getCurrentDateUSFormat());
						remndDate.put("maxDate", String.valueOf(dateDiff - 1));
						remndDate.put("minDate", "0");
					}
					else if (frequency.equalsIgnoreCase("W"))
					{
						String newDate = DateUtil.getNewDate("day", -7, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((7 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("BM"))
					{
						String newDate = DateUtil.getNewDate("day", -15, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((15 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("M"))
					{
						String newDate = DateUtil.getNewDate("month", -1, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());

						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((30 + (dateDiff))));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("Q"))
					{
						String newDate = DateUtil.getNewDate("month", -3, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((90 + (dateDiff)) + 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("HY"))
					{
						String newDate = DateUtil.getNewDate("month", -6, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((180 + (dateDiff)) + 3));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("Y"))
					{
						String newDate = DateUtil.getNewDate("year", -1, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((365 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<AssetDashboardBean> getAssetData(int maxId, String logedUser)
	{
		List<AssetDashboardBean> data = new ArrayList<AssetDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			AssetDashboardBean adb = null;
			StringBuilder query = new StringBuilder();
			String mappedTeam = null;
			query.append("SELECT ard.frequency,atm.assetSubCat,ad.assetname,ad.serialno,ad.mfgserialno,ard.reminder_for,ard.dueDate,ard.dueTime,  ");
			query.append(" ard.escalation_level_1,ard.l1EscDuration ");
			query.append(" FROM asset_reminder_details AS ard  ");
			query.append(" INNER JOIN asset_detail AS ad ON ad.id=ard.assetid  ");
			query.append(" INNER JOIN createasset_type_master AS atm ON atm.id=ad.assettype WHERE ard.id = '" + maxId + "'");
			List assetData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (assetData != null && assetData.size() > 0)
			{
				for (Iterator iterator = assetData.iterator(); iterator.hasNext();)
				{
					adb = new AssetDashboardBean();
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						if (object[0] != null)
						{
							adb.setFrequency(new AssetUniversalHelper().getFrequencyName(object[0].toString()));
						}
						else
						{
							adb.setFrequency("NA");
						}
						if (object[1] != null)
						{
							adb.setType(object[1].toString());
						}
						else
						{
							adb.setType("NA");
						}
						if (object[2] != null)
						{
							adb.setAssetName(object[2].toString());
						}
						else
						{
							adb.setAssetName("NA");
						}
						if (object[6] != null)
						{
							adb.setDueDate(DateUtil.convertDateToIndianFormat(object[6].toString()));
						}
						else
						{
							adb.setDueDate("NA");
						}
						if (object[7] != null)
						{
							adb.setDueTime(object[7].toString());
						}
						else
						{
							adb.setDueTime("NA");
						}
						if (object[3] != null)
						{
							adb.setAssetcode(object[3].toString());
						}
						else
						{
							adb.setAssetcode("NA");
						}
						if (object[4] != null)
						{
							adb.setAssetserial(object[4].toString());
						}
						else
						{
							adb.setAssetserial("NA");
						}
						if (object[5] != null)
						{
							adb.setRemindTo(object[5].toString());
						}
						else
						{
							adb.setRemindTo("NA");
						}
						if (object[8] != null)
						{
							String empId1 = object[8].toString().replace("#", ",").substring(0, (object[8].toString().length() - 1));
							List empDataList = getEmployeeInfo(empId1);
							if (empDataList != null && empDataList.size() > 0)
							{
								StringBuilder str = new StringBuilder();
								;
								boolean flag = false;
								for (Iterator iterator2 = empDataList.iterator(); iterator2.hasNext();)
								{
									Object object2[] = (Object[]) iterator2.next();
									if (!object2[5].toString().equalsIgnoreCase(logedUser))
									{
										str.append(object2[0].toString() + ", ");
									}
									else
									{
										flag = true;
									}
								}
								if (str != null && str.toString().length() > 0)
								{
									mappedTeam = str.toString().substring(0, str.toString().length() - 2);
								}
								if (mappedTeam == null)
									mappedTeam = "You";
								else if (flag)
									mappedTeam = mappedTeam + " and You";
							}
							adb.setEscL1(mappedTeam);
						}
						else
						{
							adb.setEscL1("NA");
						}
						if (object[9] != null)
						{
							adb.setEscL1Duration(object[9].toString());
						}
						else
						{
							adb.setEscL1Duration("NA");
						}
					}
					data.add(adb);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public List getEmployeeInfo(String contactId)
	{
		List data = null;
		try
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("SELECT emp.empName,emp.emailIdOne,emp.mobOne,emp.id,dept.deptName,cc.id AS contId ");
			query.append(" FROM compliance_contacts AS cc ");
			query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
			query.append(" INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status!=1 AND cc.id IN(" + contactId + ")");
			data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public List getExistAssetDetails(String reminderId, SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString
					.append("SELECT ad.assetname,ad.serialno,ad.assetbrand,ad.assetmodel,ard.dueDate," +
							"ard.reminder_for,ard.dueTime,ard.frequency,ard.actionTaken,ard.comments," +
							"ard.snooze_date,ard.snooze_time,ad.mfgserialno,ard.detail,ard.uploadDoc," +
							"ard.uploadDoc1,ard.moduleName FROM asset_reminder_details AS ard INNER JOIN asset_detail ad ON ad.id=ard.assetid WHERE ard.id=");
			qryString.append("'");
			qryString.append(reminderId);
			qryString.append("'");
			System.out.println("^^^^^^ "+qryString.toString());
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connection);
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public List getLodgedTickets(String dept, String status_for, String empName, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (status_for.equals("H"))
			{
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from asset_complaint_status where status not in ('Resolved','Ignore') and  to_dept in (" + dept + ")");
			}
			/*
			 * else if (status_for.equals("M")) {query.append(
			 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where status not in ('Resolved','Ignore')"
			 * ); } else if (status_for.equals("N")) {query.append(
			 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where status not in ('Resolved','Ignore') and feed_by='"
			 * +empName+"'"); }
			 */
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalGridReport(String reportFor, String fromDate, String toDate, String dept, String subdept, String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace, String statusFor, String moduleName, String byDept)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			String tosubdept = null;
			if (subdept != null && subdept.equalsIgnoreCase("null"))
			{
				tosubdept = "-1";
			}
			else
			{
				tosubdept = subdept;
			}
			if (reportFor.equalsIgnoreCase("Category"))
			{
				query.append("SELECT subcatg.id ,catg.categoryName,count(*) as counter,subcatg.subCategoryName FROM asset_complaint_status as feedback");
			}
			else if (reportFor.equalsIgnoreCase("Employee"))
			{
				query.append("SELECT  emp.id as employeeId,emp.empName,count(*) as counter,sub.subdeptname FROM asset_complaint_status as feedback");
			}
			query.append(" INNER JOIN subdepartment subdept1 on feedback.by_dept= subdept1.id");
			query.append(" INNER JOIN subdepartment subdept2 on feedback.to_dept= subdept2.id");
			query.append(" INNER JOIN department dept1 on subdept1.deptid= dept1.id");
			query.append(" INNER JOIN department dept2 on subdept2.deptid= dept2.id");
			query.append(" INNER JOIN feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" INNER JOIN feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("Category"))
			{
				query.append(" where open_date between '" + fromDate + "' and '" + toDate + "' ");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" AND  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" AND feedback.to_dept_subdept in (select id from subdepartment where deptid=" + dept + " ");
					if (tosubdept != null && !tosubdept.equalsIgnoreCase(""))
					{
						query.append(" AND id IN (" + tosubdept + ")");
					}
					query.append(")");
				}
			}
			else if (reportFor.equalsIgnoreCase("Employee"))
			{
				query.append(" INNER JOIN employee_basic emp on feedback.allot_to= emp.id");
				query.append(" INNER JOIN subdepartment sub on emp.subdept= sub.id ");

				query.append(" WHERE open_date between '" + fromDate + "' and '" + toDate + "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" AND  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" AND feedback.to_dept_subdept in (SELECT id FROM subdepartment WHERE deptid=" + dept + "  ");
					if (tosubdept != null && !tosubdept.equalsIgnoreCase(""))
					{
						query.append(" AND id IN (" + tosubdept + ")");
					}
					query.append(")");
				}
			}

			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq"))
				{
					query.append(" " + searchField + " = '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("cn"))
				{
					query.append(" " + searchField + " like '%" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("bw"))
				{
					query.append(" " + searchField + " like '" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("ne"))
				{
					query.append(" " + searchField + " <> '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("ew"))
				{
					query.append(" " + searchField + " like '%" + searchString + "'");
				}
			}
			if (statusFor != null && statusFor.equalsIgnoreCase("Ontime"))
			{
				query.append(" AND feedback.level='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' ");
			}
			else if (statusFor != null && statusFor.equalsIgnoreCase("Offtime"))
			{
				query.append(" AND feedback.level!='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' AND allot_to=resolve_by ");
			}
			else if (statusFor != null && statusFor.equalsIgnoreCase("Snooze"))
			{
				query.append(" AND feedback.level='Level1' AND feedback.status='Snooze'  ");
			}
			else if (statusFor != null && statusFor.equalsIgnoreCase("Missed"))
			{
				query.append(" AND feedback.level!='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' AND allot_to!=resolve_by ");
			}
			else if (statusFor != null && statusFor.equalsIgnoreCase("Ignore"))
			{
				query.append(" AND feedback.status='Ignore' ");
			}
			query.append(" AND feedback.moduleName='" + moduleName + "' ");

			if (reportFor.equalsIgnoreCase("Category"))
			{
				if (byDept != null && !byDept.equalsIgnoreCase("-1"))
				{
					query.append(" AND dept1.id='" + byDept + "' ");
				}
				query.append(" GROUP BY feedback.subCatg ORDER BY counter desc");
			}
			else if (reportFor.equalsIgnoreCase("Employee"))
			{
				query.append(" GROUP BY emp.empName ORDER BY counter desc");
			}
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getMappedDeptIdOfAEmployee(String ownDeptId,String empId,String moduleName,SessionFactory connectionSpace,CommonOperInterface cbt)
	{
		String deptIds=null;
		try
		{
			String dataQuery = "SELECT forDept_id FROM compliance_contacts WHERE fromDept_id="+ownDeptId+" AND emp_id="+empId+" AND moduleName='"+moduleName+"' AND work_status!=1";
			List dataList = cbt.executeAllSelectQuery(dataQuery, connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < dataList.size(); i++)
				{
					str.append(dataList.get(i).toString()+",");
				}
				if(str!=null)
				{
					deptIds=str.substring(0, str.length()-1);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return deptIds;
	}
	
	
	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public String getSupportAdd()
	{
		return supportAdd;
	}

	public void setSupportAdd(String supportAdd)
	{
		this.supportAdd = supportAdd;
	}

	public String getAssetAdd()
	{
		return assetAdd;
	}

	public void setAssetAdd(String assetAdd)
	{
		this.assetAdd = assetAdd;
	}

	public void setSerialNoActive(boolean serialNoActive)
	{
		this.serialNoActive = serialNoActive;
	}

	public String getHeadingSerialNo()
	{
		return headingSerialNo;
	}

	public void setHeadingSerialNo(String headingSerialNo)
	{
		this.headingSerialNo = headingSerialNo;
	}

	public boolean isVendorActive()
	{
		return vendorActive;
	}

	public void setVendorActive(boolean vendorActive)
	{
		this.vendorActive = vendorActive;
	}

	public String getHeadingVendorName()
	{
		return headingVendorName;
	}

	public void setHeadingVendorName(String headingVendorName)
	{
		this.headingVendorName = headingVendorName;
	}

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
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

	public String getVendorValue()
	{
		return vendorValue;
	}

	public void setVendorValue(String vendorValue)
	{
		this.vendorValue = vendorValue;
	}

	public String getSupprtValue()
	{
		return supprtValue;
	}

	public void setSupprtValue(String supprtValue)
	{
		this.supprtValue = supprtValue;
	}

	public boolean isSupportTypeActive()
	{
		return supportTypeActive;
	}

	public void setSupportTypeActive(boolean supportTypeActive)
	{
		this.supportTypeActive = supportTypeActive;
	}

	public String getHeadingSupportName()
	{
		return headingSupportName;
	}

	public void setHeadingSupportName(String headingSupportName)
	{
		this.headingSupportName = headingSupportName;
	}

	public boolean isCheckdept()
	{
		return checkdept;
	}

	public void setCheckdept(boolean checkdept)
	{
		this.checkdept = checkdept;
	}

	public Map<Integer, String> getEmployeeList()
	{
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList)
	{
		this.employeeList = employeeList;
	}

	public HashMap<String, String> getEmployeeDataList()
	{
		return employeeDataList;
	}

	public void setEmployeeDataList(HashMap<String, String> employeeDataList)
	{
		this.employeeDataList = employeeDataList;
	}

	public String getAllotOnRepairAdd()
	{
		return allotOnRepairAdd;
	}

	public void setAllotOnRepairAdd(String allotOnRepairAdd)
	{
		this.allotOnRepairAdd = allotOnRepairAdd;
	}

	public String getAllotmentAdd()
	{
		return allotmentAdd;
	}

	public void setAllotmentAdd(String allotmentAdd)
	{
		this.allotmentAdd = allotmentAdd;
	}

	public String getHeadingUsagesType()
	{
		return headingUsagesType;
	}

	public void setHeadingUsagesType(String headingUsagesType)
	{
		this.headingUsagesType = headingUsagesType;
	}

	public String getSpareCatgHeadName()
	{
		return spareCatgHeadName;
	}

	public void setSpareCatgHeadName(String spareCatgHeadName)
	{
		this.spareCatgHeadName = spareCatgHeadName;
	}

	public String getReallotPage()
	{
		return reallotPage;
	}

	public void setReallotPage(String reallotPage)
	{
		this.reallotPage = reallotPage;
	}

	public String getIssueSpare()
	{
		return issueSpare;
	}

	public void setIssueSpare(String issueSpare)
	{
		this.issueSpare = issueSpare;
	}

	public boolean isUsagesTypeActive()
	{
		return usagesTypeActive;
	}

	public void setUsagesTypeActive(boolean usagesTypeActive)
	{
		this.usagesTypeActive = usagesTypeActive;
	}

	public boolean isDeptLevel()
	{
		return deptLevel;
	}

	public void setDeptLevel(boolean deptLevel)
	{
		this.deptLevel = deptLevel;
	}

	public Map<Integer, String> getUsagesTypeList()
	{
		return usagesTypeList;
	}

	public void setUsagesTypeList(Map<Integer, String> usagesTypeList)
	{
		this.usagesTypeList = usagesTypeList;
	}

	public Map<Integer, String> getSpareCatgNoList()
	{
		return spareCatgNoList;
	}

	public void setSpareCatgNoList(Map<Integer, String> spareCatgNoList)
	{
		this.spareCatgNoList = spareCatgNoList;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public Map<Integer, String> getSpareNameMap()
	{
		return spareNameMap;
	}

	public void setSpareNameMap(Map<Integer, String> spareNameMap)
	{
		this.spareNameMap = spareNameMap;
	}

	public String getDeptOrSubDeptId()
	{
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId)
	{
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(List<ConfigurationUtilBean> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public Map<Integer, String> getSubDeptList()
	{
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList)
	{
		this.subDeptList = subDeptList;
	}

	public List<ConfigurationUtilBean> getAssetColumnMap()
	{
		return assetColumnMap;
	}

	public void setAssetColumnMap(List<ConfigurationUtilBean> assetColumnMap)
	{
		this.assetColumnMap = assetColumnMap;
	}

	public List<ConfigurationUtilBean> getDateColumnMap()
	{
		return dateColumnMap;
	}

	public void setDateColumnMap(List<ConfigurationUtilBean> dateColumnMap)
	{
		this.dateColumnMap = dateColumnMap;
	}

	public List<ConfigurationUtilBean> getSpareIssueColumnMap()
	{
		return spareIssueColumnMap;
	}

	public void setSpareIssueColumnMap(List<ConfigurationUtilBean> spareIssueColumnMap)
	{
		this.spareIssueColumnMap = spareIssueColumnMap;
	}

	public List<ConfigurationUtilBean> getSupportColumnMap()
	{
		return supportColumnMap;
	}

	public void setSupportColumnMap(List<ConfigurationUtilBean> supportColumnMap)
	{
		this.supportColumnMap = supportColumnMap;
	}

	public List<ConfigurationUtilBean> getAllotmentColumnMap()
	{
		return allotmentColumnMap;
	}

	public void setAllotmentColumnMap(List<ConfigurationUtilBean> allotmentColumnMap)
	{
		this.allotmentColumnMap = allotmentColumnMap;
	}

	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}

	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}

	public List<ConfigurationUtilBean> getAssetDropMap()
	{
		return assetDropMap;
	}

	public void setAssetDropMap(List<ConfigurationUtilBean> assetDropMap)
	{
		this.assetDropMap = assetDropMap;
	}

	public String getHeadingAssettype()
	{
		return headingAssettype;
	}

	public void setHeadingAssettype(String headingAssettype)
	{
		this.headingAssettype = headingAssettype;
	}

	public String getAssetValue()
	{
		return assetValue;
	}

	public void setAssetValue(String assetValue)
	{
		this.assetValue = assetValue;
	}

	public boolean isAssettype()
	{
		return assettype;
	}

	public void setAssettype(boolean assettype)
	{
		this.assettype = assettype;
	}

	public Map<Integer, String> getAssetTypeList()
	{
		return assetTypeList;
	}

	public void setAssetTypeList(Map<Integer, String> assetTypeList)
	{
		this.assetTypeList = assetTypeList;
	}

	public Map<Integer, String> getFloorList()
	{
		return floorList;
	}

	public void setFloorList(Map<Integer, String> floorList)
	{
		this.floorList = floorList;
	}

	public String getLocationId()
	{
		return locationId;
	}

	public void setLocationId(String locationId)
	{
		this.locationId = locationId;
	}

	public Map<Integer, String> getSubfloorList()
	{
		return subfloorList;
	}

	public void setSubfloorList(Map<Integer, String> subfloorList)
	{
		this.subfloorList = subfloorList;
	}

	public String getSubLocId()
	{
		return subLocId;
	}

	public void setSubLocId(String subLocId)
	{
		this.subLocId = subLocId;
	}

	public Map<String, String> getLocationDetail()
	{
		return locationDetail;
	}

	public void setLocationDetail(Map<String, String> locationDetail)
	{
		this.locationDetail = locationDetail;
	}

	public String getEmpMobileNo()
	{
		return empMobileNo;
	}

	public void setEmpMobileNo(String empMobileNo)
	{
		this.empMobileNo = empMobileNo;
	}

	public Map<Integer, String> getAssetDetail()
	{
		return assetDetail;
	}

	public void setAssetDetail(Map<Integer, String> assetDetail)
	{
		this.assetDetail = assetDetail;
	}

	public Map<String, String> getSupportFrom()
	{
		return supportFrom;
	}

	public void setSupportFrom(Map<String, String> supportFrom)
	{
		this.supportFrom = supportFrom;
	}

	public Map<String, String> getSupportFor()
	{
		return supportFor;
	}

	public void setSupportFor(Map<String, String> supportFor)
	{
		this.supportFor = supportFor;
	}

	public Map<String, String> getContMappedDeptList()
	{
		return contMappedDeptList;
	}

	public void setContMappedDeptList(Map<String, String> contMappedDeptList)
	{
		this.contMappedDeptList = contMappedDeptList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public JSONArray getEmpData()
	{
		return empData;

	}

	public void setEmpData(JSONArray empData)
	{
		this.empData = empData;
	}

	public List<ConfigurationUtilBean> getLocationMap()
	{
		return locationMap;
	}

	public void setLocationMap(List<ConfigurationUtilBean> locationMap)
	{
		this.locationMap = locationMap;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public JSONArray getJsonArr1()
	{
		return jsonArr1;
	}

	public void setJsonArr1(JSONArray jsonArr1)
	{
		this.jsonArr1 = jsonArr1;
	}

	public JSONArray getJsonArr2()
	{
		return jsonArr2;
	}

	public void setJsonArr2(JSONArray jsonArr2)
	{
		this.jsonArr2 = jsonArr2;
	}

	public Map<String, JSONArray> getObj()
	{
		return obj;
	}

	public void setObj(Map<String, JSONArray> obj)
	{
		this.obj = obj;
	}

	public String getAssetType()
	{
		return assetType;
	}

	public void setAssetType(String assetType)
	{
		this.assetType = assetType;
	}

	public String getEscL1SelectValue()
	{
		return escL1SelectValue;
	}

	public void setEscL1SelectValue(String escL1SelectValue)
	{
		this.escL1SelectValue = escL1SelectValue;
	}

	public String getEscL2SelectValue()
	{
		return escL2SelectValue;
	}

	public void setEscL2SelectValue(String escL2SelectValue)
	{
		this.escL2SelectValue = escL2SelectValue;
	}

	public String getEscL3SelectValue()
	{
		return escL3SelectValue;
	}

	public void setEscL3SelectValue(String escL3SelectValue)
	{
		this.escL3SelectValue = escL3SelectValue;
	}

	public Map<String, String> getSupportDetail()
	{
		return supportDetail;
	}

	public void setSupportDetail(Map<String, String> supportDetail)
	{
		this.supportDetail = supportDetail;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public Map<String, String> getRemndDate()
	{
		return remndDate;
	}

	public void setRemndDate(Map<String, String> remndDate)
	{
		this.remndDate = remndDate;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getOutletId()
	{
		return outletId;
	}

	public void setOutletId(String outletId)
	{
		this.outletId = outletId;
	}

	public List<ConfigurationUtilBean> getAssetDropMap1()
	{
		return assetDropMap1;
	}

	public void setAssetDropMap1(List<ConfigurationUtilBean> assetDropMap1)
	{
		this.assetDropMap1 = assetDropMap1;
	}

}
