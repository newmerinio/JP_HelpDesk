package com.Over2Cloud.ctrl.VAM.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VisitorPreRequest extends ActionSupport implements ServletRequestAware{

	/**
	 * class for all visitor pre request activity.
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String prerequestdatalist=null;
	private String onetimepassword;
	private String previsitedvisitormobile;
	private String previsitedvistordetaillist = null;  
	private String drivermob;
	private String prevehicleEntrydetaillist = null;
	
	public String getPreRequestData(){
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getOnetimepassword()!=null){
				prerequestdatalist = CommonMethod.getPreRequestData(connectionSpace, getOnetimepassword());
				resString = SUCCESS;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	}
	public String getPreVisitedVisitorDetails(){
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getPrevisitedvisitormobile()!=null){
				previsitedvistordetaillist = CommonMethod.getPreVisitedVisitorData(connectionSpace, getPrevisitedvisitormobile());
				resString = SUCCESS;
			}
			else
			{
				resString = ERROR;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	}
	
	public String getPreVehicleEntryDetails(){
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getDrivermob()!=null){
				prevehicleEntrydetaillist = CommonMethod.getPreVehicleEntryData(connectionSpace, getDrivermob());
				resString = SUCCESS;
			}
			else
			{
				resString = ERROR;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	}

	public String getPrerequestdatalist() {
		return prerequestdatalist;
	}
	public void setPrerequestdatalist(String prerequestdatalist) {
		this.prerequestdatalist = prerequestdatalist;
	}

	public String getOnetimepassword() {
		return onetimepassword;
	}
	public void setOnetimepassword(String onetimepassword) {
		this.onetimepassword = onetimepassword;
	}
	public String getPrevisitedvisitormobile() {
		return previsitedvisitormobile;
	}
	public void setPrevisitedvisitormobile(String previsitedvisitormobile) {
		this.previsitedvisitormobile = previsitedvisitormobile;
	}
	public String getPrevisitedvistordetaillist() {
		return previsitedvistordetaillist;
	}
	public void setPrevisitedvistordetaillist(String previsitedvistordetaillist) {
		this.previsitedvistordetaillist = previsitedvistordetaillist;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getDrivermob()
	{
		return drivermob;
	}
	public void setDrivermob(String drivermob)
	{
		this.drivermob = drivermob;
	}
	public String getPrevehicleEntrydetaillist()
	{
		return prevehicleEntrydetaillist;
	}
	public void setPrevehicleEntrydetaillist(String prevehicleEntrydetaillist)
	{
		this.prevehicleEntrydetaillist = prevehicleEntrydetaillist;
	}
	
}
