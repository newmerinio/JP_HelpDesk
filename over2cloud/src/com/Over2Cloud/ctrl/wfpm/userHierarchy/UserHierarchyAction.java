package com.Over2Cloud.ctrl.wfpm.userHierarchy;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.user.UserMappingHelper;
import com.opensymphony.xwork2.ActionSupport;

public class UserHierarchyAction extends ActionSupport implements
		MyActionConfiguration, ServletRequestAware
{
	private ArrayList<String>		userHierarchyList	= null;
	private HttpServletRequest	request;

	public String beforeUserHierarchy()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("userName:"+userName);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				userHierarchyList = new UserMappingHelper().getAllUsers(userName, true,
						connectionSpace);
				/*System.out.println("userHierarchyList.size():"
						+ userHierarchyList.size());*/
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public ArrayList<String> getUserHierarchyList()
	{
		return userHierarchyList;
	}

	public void setUserHierarchyList(ArrayList<String> userHierarchyList)
	{
		this.userHierarchyList = userHierarchyList;
	}
}
