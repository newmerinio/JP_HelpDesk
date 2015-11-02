package com.Over2Cloud.ctrl.wfpm.associate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Parent;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProspectiveAssociateView extends ActionSupport implements ServletRequestAware
{

	static final Log																										log											= LogFactory.getLog(ProspectiveAssociateView.class);
	private Map																													session									= ActionContext.getContext().getSession();
	private String																											userName								= (String) session.get("uName");
	private String																											accountID								= (String) session.get("accountid");
	private SessionFactory																							connectionSpace					= (SessionFactory) session.get("connectionSpace");

	private HttpServletRequest																					request;
	private String																											id;
	private String																											name;
	private String																											phoneNo;
	private String																											contactNo;
	private String																											emailID;
	private String																											faxNo;
	private String																											associateType;
	private String																											associateSubType;
	private String																											address;
	private String																											webAddress;

	private Map<String, Object>																					contactData;
	private List<Object>																								listContact;
	private List<Object>																								prospectiveAssociateContact;
	private List<Object>																								historyDetail;
	private List<GridDataPropertyView>																	viewProspectiveContact	= new ArrayList<GridDataPropertyView>();

	private Map<String, Object>																					associateDetail;
	private Map<List<GridDataPropertyView>, List<GridDataPropertyView>>	associateContact;
	Map<String, String>																									associateBasicDetail;
	private Map<List<String>, List<GridDataPropertyView>>								associateMap;
	List<String>																												headerName							= new ArrayList<String>();
	List<Parent>																												parant;
	List<ConfigurationUtilBean>																					child;

	List<Parent>																												parantHistory;
	List<ConfigurationUtilBean>																					childHistory;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			associateDetail = new HashMap<String, Object>();
			setAssociateContactViewProp("mapped_prospective_associate_info", "prospective_associate_contactinfo");
			setAssociateBasicDetail();
			getAssociateContactInfo();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method execute() of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getAssociateContactDeatil()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			associateDetail = new HashMap<String, Object>();

			setAssociateContactViewProp("mapped_prospective_associate_info", "prospective_associate_contactinfo");
			getAssociateContactInfo();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method getAssociateContactDeatil() of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getActionHistory()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			associateDetail = new HashMap<String, Object>();

			setAssociateContactViewProp("mapped_prospective_associate_info", "prospective_associate_contactinfo");
			getAssociateActionHistory();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method getActionHistory() of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAssociateBasicDetail()
	{
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_prospective_associate_info", accountID, connectionSpace, "", 0,
				"table_name", "prospective_associate_info");
		StringBuilder query = new StringBuilder("");
		List<String> names = new ArrayList<String>();
		boolean aType = false;
		boolean aCategory = false;
		boolean sourceName = false;
		query.append("select ");
		int i = 0;
		for (GridDataPropertyView gdp : returnResult)
		{
			if (gdp.getColomnName().equalsIgnoreCase("associateType"))
			{
				aType = true;
				query.append("aType.associateType,");

			}
			else if (gdp.getColomnName().equalsIgnoreCase("associateSubType"))
			{
				aType = true;
				query.append("aType.associateSubType,");
			}
			else if (gdp.getColomnName().equalsIgnoreCase("associateCategory"))
			{
				aCategory = true;
				query.append("acategory.associate_category,");

			}
			else if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
			{
				sourceName = true;
				query.append("source.sourceName,");
			}
			else
			{
				if (i < returnResult.size() - 1) query.append("pAssociate." + gdp.getColomnName() + ",");
				else query.append("pAssociate." + gdp.getColomnName());
			}
			i++;
			names.add(gdp.getHeadingName());
		}

		query.append(" from prospectiveAssociate as pAssociate ");

		if (aType)
		{
			query.append(" left join associateType as aType on pAssociate.associateType=aType.id");
		}
		if (aCategory)
		{
			query.append(" left join associateCategory as acategory on pAssociate.associateCategory=acategory.id");
		}
		if (sourceName)
		{
			query.append(" left join sourceMaster as source on pAssociate.sourceName=source.id");
		}

		query.append(" where pAssociate.id=" + getId() + "");

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		Map<String, String> one = null;

		if (data != null)
		{
			for (Iterator it = data.iterator(); it.hasNext();)
			{
				Object[] obdata = (Object[]) it.next();
				one = new HashMap<String, String>();

				if (obdata != null)
				{
					for (int j = 0; j < returnResult.size(); j++)
					{
						if (obdata[j] != null)
						{
							one.put(names.get(j), obdata[j].toString());
						}
					}
				}
			}

		}

		if (data != null)
		{
			for (Iterator it = data.iterator(); it.hasNext();)
			{
				Object[] obdata = (Object[]) it.next();
				associateBasicDetail = new HashMap<String, String>();

				if (obdata != null)
				{
					for (int j = 0; j < returnResult.size(); j++)
					{
						if (obdata[j] != null)
						{
							associateBasicDetail.put(names.get(j), obdata[j].toString());
						}
					}
				}
			}
		}
	}

	public void getAssociateContactInfo()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List temp = new ArrayList();

		StringBuilder query = new StringBuilder("");
		query.append("select ");
		List<GridDataPropertyView> list = setAssociateContactViewProp("mapped_prospective_associate_info", "prospective_associate_contactinfo");
		for (GridDataPropertyView gridDataPropertyView : list)
		{
			if (!gridDataPropertyView.getColomnName().equalsIgnoreCase("associateName"))
			{
				query.append(gridDataPropertyView.getColomnName() + ",");
				headerName.add(gridDataPropertyView.getHeadingName());
			}
		}
		String queryNew = query.toString().substring(0, query.toString().lastIndexOf(","));
		queryNew += " from prospectiveAssociateContact where associateName=" + id;
		temp = cbt.executeAllSelectQuery(queryNew.toString(), connectionSpace);
		ConfigurationUtilBean configurationUtilBean = null;

		parant = new ArrayList<Parent>();
		for (Iterator it = temp.iterator(); it.hasNext();)
		{
			Parent parentBean = new Parent();
			child = new ArrayList<ConfigurationUtilBean>();
			Object obdata[] = (Object[]) it.next();
			for (int i = 0; i < obdata.length; i++)
			{
				configurationUtilBean = new ConfigurationUtilBean();
				if (obdata[i] != null)
				{
					configurationUtilBean.setField_value(obdata[i].toString());
				}
				else
				{
					configurationUtilBean.setField_value("NA");
				}
				child.add(configurationUtilBean);
			}
			parentBean.setChildList1(child);
			parant.add(parentBean);
		}

	}

	List<GridDataPropertyView>	historyHeaderNames	= new ArrayList<GridDataPropertyView>();
	private boolean							checkBoxDesabled;

	public void getAssociateActionHistory()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List temp = new ArrayList();
		GridDataPropertyView propertyView = new GridDataPropertyView();

		propertyView.setColomnName("flag");
		propertyView.setHeadingName("Flag");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("id");
		propertyView.setHeadingName("ID");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("nextAction");
		propertyView.setHeadingName("Next Action");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("whatAction");
		propertyView.setHeadingName("What Action");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("maxDate");
		propertyView.setHeadingName("Due Date");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("description");
		propertyView.setHeadingName("Description");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("madeOn");
		propertyView.setHeadingName("Made On");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setColomnName("actionBy");
		propertyView.setHeadingName("Action By");
		historyHeaderNames.add(propertyView);

		propertyView = new GridDataPropertyView();
		propertyView.setHeadingName("Contact Name");
		historyHeaderNames.add(propertyView);

		StringBuilder query = new StringBuilder("");
		query.append("select ");

		for (GridDataPropertyView view : historyHeaderNames)
		{
			if (view.getColomnName() != null)
			{
				query.append("tAction." + view.getColomnName() + ",");
			}
		}
		query.append("contact.name,");
		String queryNew = query.toString().substring(0, query.toString().lastIndexOf(","));

		queryNew += " from takeaction as tAction, prospectiveAssociateContact as contact , prospectiveAssociate as associate where contact.id = tAction.associateId and contact.associateName = associate.id and associate.id="
				+ getId();
		temp = cbt.executeAllSelectQuery(queryNew.toString(), connectionSpace);
		ConfigurationUtilBean configurationUtilBean = null;
		parantHistory = new ArrayList<Parent>();
		for (Iterator it = temp.iterator(); it.hasNext();)
		{
			Parent parentBean = new Parent();
			childHistory = new ArrayList<ConfigurationUtilBean>();
			Object obdata[] = (Object[]) it.next();
			for (int j = 0; j < obdata.length; j++)
			{
				configurationUtilBean = new ConfigurationUtilBean();
				if (obdata[j] != null)
				{
					configurationUtilBean.setField_value(obdata[j].toString());
				}
				else
				{
					configurationUtilBean.setField_value("NA");
				}
				childHistory.add(configurationUtilBean);
			}
			parentBean.setChildList1(childHistory);
			parantHistory.add(parentBean);

		}
	}

	public String viewAssociateContactGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from prospectiveAssociateContact where associateName='" + getId() + "'");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_prospective_associate_info", accountID, connectionSpace, "prospective_associate_contactinfo");
				listContact = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1) query.append(obdata.toString() + ",");
						else query.append(obdata.toString());
					}
					i++;

				}

				query.append(" from prospectiveAssociateContact  where associateName='" + getId() + "'");

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						contactData = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0) contactData.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								else contactData.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						listContact.add(contactData);
					}
					setProspectiveAssociateContact(listContact);
				}

			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <Method Name> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<GridDataPropertyView> setAssociateContactViewProp(String table1, String table2)
	{
		List<GridDataPropertyView> list = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewProspectiveContact.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(table1, accountID, connectionSpace, "", 0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			viewProspectiveContact.add(gpv);
			list.add(gpv);
		}

		return list;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhoneNo()
	{
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

	public String getEmailID()
	{
		return emailID;
	}

	public void setEmailID(String emailID)
	{
		this.emailID = emailID;
	}

	public String getFaxNo()
	{
		return faxNo;
	}

	public void setFaxNo(String faxNo)
	{
		this.faxNo = faxNo;
	}

	public String getAssociateType()
	{
		return associateType;
	}

	public void setAssociateType(String associateType)
	{
		this.associateType = associateType;
	}

	public String getAssociateSubType()
	{
		return associateSubType;
	}

	public void setAssociateSubType(String associateSubType)
	{
		this.associateSubType = associateSubType;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getWebAddress()
	{
		return webAddress;
	}

	public void setWebAddress(String webAddress)
	{
		this.webAddress = webAddress;
	}

	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<Object> getProspectiveAssociateContact()
	{
		return prospectiveAssociateContact;
	}

	public void setProspectiveAssociateContact(List<Object> prospectiveAssociateContact)
	{
		this.prospectiveAssociateContact = prospectiveAssociateContact;
	}

	public List<GridDataPropertyView> getViewProspectiveContact()
	{
		return viewProspectiveContact;
	}

	public void setViewProspectiveContact(List<GridDataPropertyView> viewProspectiveContact)
	{
		this.viewProspectiveContact = viewProspectiveContact;
	}

	public List<Object> getHistoryDetail()
	{
		return historyDetail;
	}

	public void setHistoryDetail(List<Object> historyDetail)
	{
		this.historyDetail = historyDetail;
	}

	public List<Object> getListContact()
	{
		return listContact;
	}

	public void setListContact(List<Object> listContact)
	{
		this.listContact = listContact;
	}

	public Map<String, Object> getContactData()
	{
		return contactData;
	}

	public void setContactData(Map<String, Object> contactData)
	{
		this.contactData = contactData;
	}

	public Map<String, Object> getAssociateDetail()
	{
		return associateDetail;
	}

	public void setAssociateDetail(Map<String, Object> associateDetail)
	{
		this.associateDetail = associateDetail;
	}

	public Map<List<GridDataPropertyView>, List<GridDataPropertyView>> getAssociateContact()
	{
		return associateContact;
	}

	public void setAssociateContact(Map<List<GridDataPropertyView>, List<GridDataPropertyView>> associateContact)
	{
		this.associateContact = associateContact;
	}

	public Map<String, String> getAssociateBasicDetail()
	{
		return associateBasicDetail;
	}

	public void setAssociateBasicDetail(Map<String, String> associateBasicDetail)
	{
		this.associateBasicDetail = associateBasicDetail;
	}

	public Map<List<String>, List<GridDataPropertyView>> getAssociateMap()
	{
		return associateMap;
	}

	public void setAssociateMap(Map<List<String>, List<GridDataPropertyView>> associateMap)
	{
		this.associateMap = associateMap;
	}

	public List<String> getHeaderName()
	{
		return headerName;
	}

	public void setHeaderName(List<String> headerName)
	{
		this.headerName = headerName;
	}

	public List<Parent> getParant()
	{
		return parant;
	}

	public void setParant(List<Parent> parant)
	{
		this.parant = parant;
	}

	public List<ConfigurationUtilBean> getChild()
	{
		return child;
	}

	public void setChild(List<ConfigurationUtilBean> child)
	{
		this.child = child;
	}

	public List<Parent> getParantHistory()
	{
		return parantHistory;
	}

	public void setParantHistory(List<Parent> parantHistory)
	{
		this.parantHistory = parantHistory;
	}

	public List<ConfigurationUtilBean> getChildHistory()
	{
		return childHistory;
	}

	public void setChildHistory(List<ConfigurationUtilBean> childHistory)
	{
		this.childHistory = childHistory;
	}

	public List<GridDataPropertyView> getHistoryHeaderNames()
	{
		return historyHeaderNames;
	}

	public void setHistoryHeaderNames(List<GridDataPropertyView> historyHeaderNames)
	{
		this.historyHeaderNames = historyHeaderNames;
	}

	public boolean isCheckBoxDesabled()
	{
		return checkBoxDesabled;
	}

	public void setCheckBoxDesabled(boolean checkBoxDesabled)
	{
		this.checkBoxDesabled = checkBoxDesabled;
	}
}
