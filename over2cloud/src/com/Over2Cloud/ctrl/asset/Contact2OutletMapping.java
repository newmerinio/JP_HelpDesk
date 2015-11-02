package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class Contact2OutletMapping extends GridPropertyBean implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(Contact2OutletMapping.class);
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private List<Object> masterViewList;
	private HttpServletRequest request;
	private Map<String,String> outletName;
	private String outletId;
	private String moduleName;
	private String contactId;
	private String sendFlag;
	private String dataFor;
	
	
	public String beforeContactMap()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				outletName = new LinkedHashMap<String, String>();
				String query="SELECT id,associateName FROM associate_basic_data ORDER BY associateName";
				List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						outletName.put(object[0].toString(), object[1].toString());
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public String getUnmappedContact()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("employee");
				gpv.setHeadingName("Employee Name");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("level");
				gpv.setHeadingName("Level");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				return SUCCESS;
			}
			catch(Exception e)
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
	
	@SuppressWarnings("unchecked")
	public String viewUnmappedContact()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				query.append("select distinct contacts.id,CONCAT('Level ', contacts.level),emp.empname ");
				query.append(" from compliance_contacts as contacts ");
				query.append(" inner join employee_basic as emp on emp.id=contacts.emp_id ");
				query.append(" inner join department as dept on contacts.forDept_id=dept.id ");
				query.append(" inner join associate_contact_data as associate on dept.id=associate.department ");
				query.append(" where associate.associateName="+outletId+" AND contacts.work_status!=1 AND moduleName='ASTM' AND contacts.emp_id not in (select  cont.emp_id from compliance_contacts as cont ");
				query.append(" inner join outlet_to_contact_mapping as outt on cont.id=outt.contactid");
				query.append(" where outt.outletid="+outletId+")");
				System.out.println(">>>>> "+query.toString());
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					List<Object> Listhb=new ArrayList<Object>();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("level", CUA.getValueWithNullCheck(object[1]));
						one.put("employee", CUA.getValueWithNullCheck(object[2]));
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setRecords(dataList.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
				return SUCCESS;
			}
			catch(Exception e)
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
	
	
	public String mapContact2Outlet()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false, flag = false;
				TableColumes ob1 =null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				
				ob1 = new TableColumes();
				ob1.setColumnname("outletid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("contactid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("sendflag");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				cbt.createTable22("outlet_to_contact_mapping", Tablecolumesaaa, connectionSpace);
				
				ob = new InsertDataTable();
				ob.setColName("outletid");
				ob.setDataName(outletId);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("sendflag");
				ob.setDataName(sendFlag);
				insertData.add(ob);
				
				if(contactId!=null)
				{
					contactId=contactId+",";
					String contArr[]=contactId.split(",");
					for (int i = 0; i < contArr.length; i++)
					{
						ob = new InsertDataTable();
						ob.setColName("contactid");
						ob.setDataName(contArr[i]);
						insertData.add(ob);
						
						status = cbt.insertIntoTable("outlet_to_contact_mapping", insertData, connectionSpace);
						insertData.remove(insertData.size()-1);
					}
					
				}
				if(status)
				{
					addActionMessage("Contact Mapped Successfully");
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Error In Data Add");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String BeforeViewCont2OutletMapping()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				gpv.setWidth(1);
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("employee");
				gpv.setHeadingName("Employee Name");
				gpv.setHideOrShow("false");
				gpv.setWidth(900);
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("level");
				gpv.setHeadingName("Level");
				gpv.setHideOrShow("false");
				gpv.setWidth(900);
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("outlet");
				gpv.setHeadingName("Outlet Name");
				gpv.setHideOrShow("true");
				gpv.setWidth(1);
				masterViewProp.add(gpv);
				
				if(dataFor!=null && dataFor.equalsIgnoreCase("OEH"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("dept");
					gpv.setHeadingName("Department Name");
					gpv.setHideOrShow("false");
					gpv.setWidth(900);
					masterViewProp.add(gpv);
				}
				
				return SUCCESS;
			}
			catch(Exception e)
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
	
	@SuppressWarnings("unchecked")
	public String viewMappedContact()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println(">>>> "+dataFor);
				StringBuilder query = new StringBuilder();
				if(dataFor!=null && dataFor.equalsIgnoreCase("TAH"))
				{
					query.append("SELECT outlet.id,emp.empname,CONCAT('Level ',contact.level),associate.associateName FROM outlet_to_contact_mapping AS outlet ");
					query.append(" INNER JOIN compliance_contacts AS contact ON contact.id=outlet.contactid ");
					query.append(" INNER JOIN employee_basic AS emp ON emp.id=contact.emp_id ");
					query.append(" INNER JOIN associate_basic_data AS associate ON associate.id=outlet.outletid ");
				}
				else if(dataFor!=null && dataFor.equalsIgnoreCase("OEH"))
				{
					query.append("SELECT distinct contact.id,emp.empName,CONCAT('Level ',contact.level),associate.associateName,dept.deptName FROM compliance_contacts AS contact ");
					query.append(" INNER JOIN employee_basic AS emp ON emp.id=contact.emp_id ");
					query.append(" INNER JOIN department AS dept ON dept.id=contact.forDept_id ");
					query.append(" INNER JOIN associate_contact_data AS assocontact ON assocontact.department=contact.forDept_id ");
					query.append(" INNER JOIN associate_basic_data AS associate ON associate.id=assocontact.associateName ");
					query.append(" WHERE contact.forDept_id IN(SELECT DISTINCT department FROM associate_contact_data) AND contact.moduleName='ASTM' AND contact.work_status!=1 ORDER BY associate.associateName ");
				}
				System.out.println(">>>>> "+query.toString());
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					List<Object> Listhb=new ArrayList<Object>();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						if(dataFor!=null && dataFor.equalsIgnoreCase("OEH"))
						{
							one.put("id", CUA.getValueWithNullCheck(object[0]));
							one.put("employee", CUA.getValueWithNullCheck(object[1]));
							one.put("level", CUA.getValueWithNullCheck(object[2]));
							one.put("outlet", CUA.getValueWithNullCheck(object[3]));
							one.put("dept", CUA.getValueWithNullCheck(object[4]));
						}
						else if(dataFor!=null && dataFor.equalsIgnoreCase("TAH"))
						{
							one.put("id", CUA.getValueWithNullCheck(object[0]));
							one.put("employee", CUA.getValueWithNullCheck(object[1]));
							one.put("level", CUA.getValueWithNullCheck(object[2]));
							one.put("outlet", CUA.getValueWithNullCheck(object[3]));
						}
						
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setRecords(dataList.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
				return SUCCESS;
			}
			catch(Exception e)
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
	
	
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}
	
	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public Map<String, String> getOutletName()
	{
		return outletName;
	}

	public void setOutletName(Map<String, String> outletName)
	{
		this.outletName = outletName;
	}

	public String getOutletId()
	{
		return outletId;
	}

	public void setOutletId(String outletId)
	{
		this.outletId = outletId;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getSendFlag()
	{
		return sendFlag;
	}

	public void setSendFlag(String sendFlag)
	{
		this.sendFlag = sendFlag;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}
	
}
