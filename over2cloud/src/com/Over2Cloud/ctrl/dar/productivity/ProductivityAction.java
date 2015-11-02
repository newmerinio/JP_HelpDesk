package com.Over2Cloud.ctrl.dar.productivity;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.opensymphony.xwork2.ActionSupport;

public class ProductivityAction extends ActionSupport
{
	private static final long serialVersionUID = 8464835431835887478L;

	public String getProductivityDAR() 
	{
		if (ValidateSession.checkSession()) 
		{
			return SUCCESS;
		} 
		else 
		{
			return ERROR;
		}
	}

	
}
