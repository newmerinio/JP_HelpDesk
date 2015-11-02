package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import KPIReportHelper.OfferingReportHelper;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.user.UserMappingHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OfferingReportAction extends ActionSupport
{
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session
	    .get("connectionSpace");
	List<KPIReportTotalPojo> objectOfferingReportTotalPojoLISTS = null;
	String userName = null;
	String id = null;
	List<KPIReportKPIPojo> objectOfferingReportOfferingPojoLISTS = null;
	private String offeringLevel = session.get("offeringLevel").toString();

    private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	
	public String viewOfferingAchievement()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("111111111111111111111111111111111111111111111");
				String targetMonth = DateUtil.getCurrentDateMonthYear();
				if (getSearchField() != null && getSearchString() != null
						&& !getSearchField().equalsIgnoreCase("")
						&& !getSearchString().equalsIgnoreCase(""))
				{
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						targetMonth=getSearchString().trim();
					}
				}
				objectOfferingReportTotalPojoLISTS = new ArrayList<KPIReportTotalPojo>();
				if (userName.equalsIgnoreCase("ALL_USER"))
				{
					userName = session.get("uName").toString();
					List<String> childUserList = new UserMappingHelper().getAllUsers(
					    userName, true, connectionSpace);
					if (childUserList != null && childUserList.size() > 0)
					{
						for (String user : childUserList)
						{
							KPIReportTotalPojo objectKPIReportTotalPojo = new OfferingReportHelper()
							    .getOfferingReportTotalPojoData(user, connectionSpace,
							        targetMonth);
							objectOfferingReportTotalPojoLISTS.add(objectKPIReportTotalPojo);
						}
					}
				} else
				{
					KPIReportTotalPojo objectKPIReportTotalPojo = new OfferingReportHelper()
					    .getOfferingReportTotalPojoData(userName, connectionSpace, targetMonth);
					objectOfferingReportTotalPojoLISTS.add(objectKPIReportTotalPojo);
				}
				
				//System.out.println("objectOfferingReportTotalPojoLISTS:"+objectOfferingReportTotalPojoLISTS.size());

			} catch (Exception ex)
			{
				ex.printStackTrace();
				returnValue = ERROR;
			}

			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String viewOfferingAchievementDetails()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("0000000000000000000000000000000000000");
				String targetMonth = DateUtil.getCurrentDateMonthYear();
				//System.out.println(userName + "<<<>>>" + id + "<<<>>>" + targetMonth);

				String uName = new CommonWork().getEmpUserIdByEmployeeId(id,
				    connectionSpace);
				//System.out.println("uName:" + uName + "              id:" + id);
				objectOfferingReportOfferingPojoLISTS = new OfferingReportHelper()
				    .getOfferingReportOfferingPojoData(uName, id, connectionSpace, targetMonth, offeringLevel);

			} catch (Exception ex)
			{
				ex.printStackTrace();
			}

			returnValue = SUCCESS;
		} else
		{
			returnValue = LOGIN;
		}

		return returnValue;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<KPIReportTotalPojo> getObjectOfferingReportTotalPojoLISTS()
	{
		return objectOfferingReportTotalPojoLISTS;
	}

	public void setObjectOfferingReportTotalPojoLISTS(
	    List<KPIReportTotalPojo> objectOfferingReportTotalPojoLISTS)
	{
		this.objectOfferingReportTotalPojoLISTS = objectOfferingReportTotalPojoLISTS;
	}

	public List<KPIReportKPIPojo> getObjectOfferingReportOfferingPojoLISTS()
	{
		return objectOfferingReportOfferingPojoLISTS;
	}

	public void setObjectOfferingReportOfferingPojoLISTS(
	    List<KPIReportKPIPojo> objectOfferingReportOfferingPojoLISTS)
	{
		this.objectOfferingReportOfferingPojoLISTS = objectOfferingReportOfferingPojoLISTS;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

}
