package com.Over2Cloud.ctrl.wfpm.template;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.ctrl.wfpm.associate.TakeActionOnAssociate;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IVRSReport extends ActionSupport{

	static final Log log = LogFactory.getLog(TakeActionOnAssociate.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	public String execute() {
		try {
			if(userName==null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			//System.out.println(">>>>>>>>>>>>>>>>>>>>Show IVRS Report");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
