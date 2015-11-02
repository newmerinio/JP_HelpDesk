package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.user.UserMappingHelper;
import com.Over2Cloud.ctrl.wfpm.userHierarchy.UserHierarchyAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class KPIReportAction extends ActionSupport
{
	private static String			userName;
	Map												session												= ActionContext.getContext().getSession();
	SessionFactory						connectionSpace								= (SessionFactory) session.get("connectionSpace");

	List<KPIReportTotalPojo>	objectKPIReportTotalPojoLISTS	= null;
	List<KPIReportKPIPojo>		objectKPIReportKPIPojoLISTS		= null;
	String										id														= null;
	String										month													= null;
	private String						searchField										= "";
	private String						searchString									= "";
	private String						searchOper										= "";

	public String viewAchievement()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				String targetMonth = DateUtil.getCurrentDateMonthYear();
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						targetMonth = getSearchString().trim();
					}
				}
				objectKPIReportTotalPojoLISTS = new ArrayList<KPIReportTotalPojo>();
				if (userName.equalsIgnoreCase("ALL_USER"))
				{
					userName = session.get("uName").toString();
					List<String> childUserList = new UserMappingHelper().getAllUsers(userName, true, connectionSpace);
					if (childUserList != null && childUserList.size() > 0)
					{
						for (String user : childUserList)
						{
							KPIReportTotalPojo objectKPIReportTotalPojo = new KPIReportHelper().getKPIReportTotalPojoData(user, connectionSpace, targetMonth);
							objectKPIReportTotalPojoLISTS.add(objectKPIReportTotalPojo);
						}
					}
				}
				else
				{
					KPIReportTotalPojo objectKPIReportTotalPojo = new KPIReportHelper().getKPIReportTotalPojoData(userName, connectionSpace, targetMonth);
					objectKPIReportTotalPojoLISTS.add(objectKPIReportTotalPojo);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			returnType = SUCCESS;
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	public String viewKPIAchievement()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("0000000000000000000000000000000000000");
				String targetMonth = DateUtil.getCurrentDateMonthYear();
				//System.out.println(userName + "<<<>>>" + id + "<<<>>>" + targetMonth);

				String uName = new CommonWork().getEmpUserIdByEmployeeId(id, connectionSpace);
				//System.out.println("uName:" + uName + "              id:" + id);
				objectKPIReportKPIPojoLISTS = new KPIReportHelper().getKPIReportKPIPojoData(uName, id, connectionSpace, targetMonth);

				//System.out.println("objectKPIReportKPIPojoLISTS.size():" + objectKPIReportKPIPojoLISTS.size());

			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			returnValue = SUCCESS;
		}
		else
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

	public List<KPIReportTotalPojo> getObjectKPIReportTotalPojoLISTS()
	{
		return objectKPIReportTotalPojoLISTS;
	}

	public void setObjectKPIReportTotalPojoLISTS(List<KPIReportTotalPojo> objectKPIReportTotalPojoLISTS)
	{
		this.objectKPIReportTotalPojoLISTS = objectKPIReportTotalPojoLISTS;
	}

	public List<KPIReportKPIPojo> getObjectKPIReportKPIPojoLISTS()
	{
		return objectKPIReportKPIPojoLISTS;
	}

	public void setObjectKPIReportKPIPojoLISTS(List<KPIReportKPIPojo> objectKPIReportKPIPojoLISTS)
	{
		this.objectKPIReportKPIPojoLISTS = objectKPIReportKPIPojoLISTS;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

}
