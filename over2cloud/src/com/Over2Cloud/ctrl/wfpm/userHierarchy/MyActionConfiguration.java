package com.Over2Cloud.ctrl.wfpm.userHierarchy;

import java.util.Map;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.ActionContext;

public interface MyActionConfiguration{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
}
