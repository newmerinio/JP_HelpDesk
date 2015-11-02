package com.Over2Cloud.CommonClasses;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class ValidateSession 
{
	public static boolean checkSession()
	{
		boolean sessionFlag=false;
		Map session = ActionContext.getContext().getSession();
		if(session!=null)
		 {
		  try
			{
				String userName=null;
				String clientId=null;
				if(session.containsKey("uName") && session.containsKey("accountid"))
				 {
					userName=(String)session.get("uName");
					clientId=(String)session.get("accountid");
					if(userName!=null && clientId!=null)
					 {sessionFlag=true;}
					else
					 {sessionFlag=false;}
				 }
			}
			catch (Exception e)
			{
				sessionFlag=false;
			}
		}
		return sessionFlag;
	}
}
