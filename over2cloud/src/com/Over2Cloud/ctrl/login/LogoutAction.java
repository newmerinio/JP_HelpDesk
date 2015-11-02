package com.Over2Cloud.ctrl.login;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;


import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware {
	
	static final Log log = LogFactory.getLog(LogoutAction.class);
	private Map<String,Object> session;
	@SuppressWarnings("unchecked")
	public String execute(){
		session.clear();
		if(!session.isEmpty())
		{
		session.remove("uName");
		session.remove("accountid");
		}
		if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
		    try {
		        ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
		    } catch (IllegalStateException e) {
		    	log.error("@ERP::>> Problem in LogoutAction", e);
		    }
		}
		return SUCCESS;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}
}
