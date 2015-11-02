package com.Over2Cloud.ctrl.wfpm.common;

import java.util.Map;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anoop, This is to be extended in all action classes so as to remove
 *         repetition of this code
 */
public abstract class SessionProviderClass extends ActionSupport
{
	protected Map									session					= ActionContext.getContext().getSession();
	protected String							userName				= (String) session.get("uName");
	protected SessionFactory			connectionSpace	= (SessionFactory) session.get("connectionSpace");
	protected String							accountID				= (String) session.get("accountid");
	protected String							empIdofuser			= session.get("empIdofuser").toString().split("-")[1];
	protected String							cId							= new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName, connectionSpace)[0];
	protected CommonOperInterface	coi							= new CommonConFactory().createInterface();
	protected String								empId						= session.get("empIdofuser").toString().split("-")[1];
}
