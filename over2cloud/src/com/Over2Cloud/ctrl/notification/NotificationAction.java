package com.Over2Cloud.ctrl.notification;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.hr.EmployeeHistoryCtrl;
import com.Over2Cloud.modal.db.commom.NotificationPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NotificationAction extends ActionSupport{

	static final Log log = LogFactory.getLog(EmployeeHistoryCtrl.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	/**
	 * var names for showing counter of data
	 */
	private int clientActivity=0;
	private int associateActivity=0;
	private int krActivity=0;
	private int orgNotification=0;
	private int viaCall_Count=0;
	private int viaOnline_Count=0;
	private int sn_Count=0;
	private int hp_Count=0;
	private String moduleFlagVar;
	private List<NotificationPojo>notificationData;
	private String date1;
	private String date2;
	private String date3;
	private String status;
	private String viaFrom;
	private String limitFlag;
	private String serverTime;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase("")){
				return LOGIN;	
			}
			serverTime=DateUtil.getCurrentTime().substring(0, 5);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getCountOfNotification()
	{	return SUCCESS;
	}

	// Method for getting data of feedback to take action
	@SuppressWarnings("unchecked")
	public String selectData4Feedback()
	{	return SUCCESS;
	}
	
	
	public int getClientActivity() {
		return clientActivity;
	}


	public void setClientActivity(int clientActivity) {
		this.clientActivity = clientActivity;
	}


	public int getAssociateActivity() {
		return associateActivity;
	}


	public void setAssociateActivity(int associateActivity) {
		this.associateActivity = associateActivity;
	}


	public int getKrActivity() {
		return krActivity;
	}


	public void setKrActivity(int krActivity) {
		this.krActivity = krActivity;
	}


	public String getModuleFlagVar() {
		return moduleFlagVar;
	}


	public void setModuleFlagVar(String moduleFlagVar) {
		this.moduleFlagVar = moduleFlagVar;
	}


	public List<NotificationPojo> getNotificationData() {
		return notificationData;
	}


	public void setNotificationData(List<NotificationPojo> notificationData) {
		this.notificationData = notificationData;
	}


	public int getOrgNotification() {
		return orgNotification;
	}


	public void setOrgNotification(int orgNotification) {
		this.orgNotification = orgNotification;
	}

	public String getDate1() {
		return date1;
	}


	public void setDate1(String date1) {
		this.date1 = date1;
	}


	public String getDate2() {
		return date2;
	}


	public void setDate2(String date2) {
		this.date2 = date2;
	}


	public String getDate3() {
		return date3;
	}


	public void setDate3(String date3) {
		this.date3 = date3;
	}


	public int getViaCall_Count() {
		return viaCall_Count;
	}


	public void setViaCall_Count(int viaCall_Count) {
		this.viaCall_Count = viaCall_Count;
	}


	public int getViaOnline_Count() {
		return viaOnline_Count;
	}


	public void setViaOnline_Count(int viaOnline_Count) {
		this.viaOnline_Count = viaOnline_Count;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getViaFrom() {
		return viaFrom;
	}


	public void setViaFrom(String viaFrom) {
		this.viaFrom = viaFrom;
	}


	public String getLimitFlag() {
		return limitFlag;
	}


	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}


	public int getSn_Count() {
		return sn_Count;
	}


	public void setSn_Count(int sn_Count) {
		this.sn_Count = sn_Count;
	}


	public int getHp_Count() {
		return hp_Count;
	}


	public void setHp_Count(int hp_Count) {
		this.hp_Count = hp_Count;
	}


	public String getServerTime() {
		return serverTime;
	}


	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
}
