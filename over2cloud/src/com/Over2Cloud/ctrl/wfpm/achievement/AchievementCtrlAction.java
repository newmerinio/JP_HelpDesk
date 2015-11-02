package com.Over2Cloud.ctrl.wfpm.achievement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class AchievementCtrlAction extends SessionProviderClass implements ServletRequestAware
{

	private HttpServletRequest	request;
	private CommonOperInterface	coi	= new CommonConFactory().createInterface();

	// Getter & Setters
	//****************************************************************************
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
}
