package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.Rnd.SMSTest;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.wfpm.template.SMSTemplateBean;
import com.Over2Cloud.ctrl.wfpm.template.SMSTemplateDao;
import com.Over2Cloud.ctrl.wfpm.template.SMSValidation;

public class GetRecordViaSMS {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	/**
	 * Fill DSR by this method
	 * */
	public void fillDSR(String keyword, String[] message, String mobileNo) {
		
		try {
			List smsTemplate = new ArrayList();
			ReportDao reportDao = new ReportDao();
			smsTemplate = reportDao.getAllRecordByKeyword(keyword);
			SMSTest smsTest = new SMSTest();
			String paramName = "";
			String paramNames[] = null;
			String shortCode = "";
			String shortCodes[] = null;
			String validation = "";
			String validations[] = null;
			String revertSMS = "";
			//SMSTemplateDao templateDao = new SMSTemplateDao();
			for (Iterator iterator = smsTemplate.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				paramName = object[2].toString();
				shortCode = object[3].toString();
				validation = object[4].toString();
				revertSMS = object[5].toString();
			}
			/*try {
				//System.out.println(">>>>>>>>>>revertSMS : "+ revertSMS);
				smsTest.sendSMS(revertSMS, mobileNo);
				//System.out.println(">>>>>>>>> SMS send at "+ mobileNo);
			} catch (Exception e) {
				//System.out.println(">>>>>>>>>>>"+revertSMS);
				e.printStackTrace();
			}*/
			
			if(paramName != null && !paramName.equalsIgnoreCase("")){
				paramNames = paramName.split(",");
			}
			
			if (shortCode != null && !shortCode.equalsIgnoreCase("")) {
				shortCodes = shortCode.split(",");
			}
			
			if(validation != null && !validation.equalsIgnoreCase(""))
			{
				validations = validation.split(",");
			}
			
			int value = 0;
			String productId = "";
			boolean status = false;
			smsTemplate.clear();
			smsTemplate = reportDao.getEmpInfoByMobNo(mobileNo);
			boolean recordCorrect=false;
			List<SMSTemplateBean> list = new ArrayList<SMSTemplateBean>();
			SMSTemplateBean bean = null;
			int empId=0;
			String accId = "";
			String comment="";
			
			if(smsTemplate != null && smsTemplate.size()>0)
			{
				for (Iterator iterator = smsTemplate.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empId = Integer.parseInt(object[0].toString().trim());
					accId = object[1].toString();
				}
				accId = reportDao.getUserId(accId);
				
				try {
					accId = Cryptography.decrypt(accId,DES_ENCRYPTION_KEY);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				String date = "";
				
				String val="";
				
				//System.out.println(">>>>>>>>>paramNames : "+paramNames);
				if(paramNames != null)
				for (int i = 0; i < paramNames.length; i++) {
					bean = new SMSTemplateBean();
					if(paramNames[i].trim().equalsIgnoreCase("Date"))
					{
						date = message[i].substring(shortCodes[i].length(), message[i].length()).trim();
						//date = message[i].substring(2, message[i].length()).trim();
						
						//date = "";
						
						if(!SMSValidation.validateDateFormat(date))
						{
							recordCorrect = false;
							try {
								smsTest.sendSMS("Please insert correct date format (dd-mm-yyyy)", mobileNo);
								//System.out.println(">>>>>>>>> SMS send at "+ mobileNo);
							} catch (Exception e) {
								//System.out.println(">>>>>> Please insert correct date format (dd-mm-yyyy)");
								e.printStackTrace();
							}
							return;
						}
						else
						{
							bean.setDate(date);
							recordCorrect = true;
						}
					}
					if(paramNames[paramNames.length-1].trim().equalsIgnoreCase("#"))
					{
						comment = message[message.length-1].substring(1, message[message.length-1].length()).trim();
						bean.setGeneratedTemplate(comment);
					}
					if(!paramNames[i].trim().equalsIgnoreCase("Date") && !paramNames[i].trim().equalsIgnoreCase("#"))
					{
						val = message[i].trim().substring(shortCodes[i].trim().length(), message[i].trim().length()).trim();
						
						if(!val.equalsIgnoreCase(""))
						{
							if(SMSValidation.isNumeric(val)){
								value = Integer.parseInt(val);
								bean.setId(value);
								recordCorrect = true;
							}
							else
							{
								recordCorrect = false;
								try {
									smsTest.sendSMS("Please insert numeric value in your KPI", mobileNo);
									//System.out.println(">>>>>>>>> SMS send at "+ mobileNo);
								} catch (Exception e) {
									//System.out.println(">>>>>>>>>>>>. Please insert integer value");
									e.printStackTrace();
								}
								
								
								return;
							}
						}
						else
						{
							recordCorrect = false;
							value=0;
							try {
								smsTest.sendSMS("Please insert correct keyword", mobileNo);
								//System.out.println(">>>>>>>>> SMS send at "+ mobileNo);
							} catch (Exception e) {
								//System.out.println("Please insert correct keyword : "+ mobileNo);
								e.printStackTrace();
							}
							return;
						}
						productId = paramNames[i].trim();
						bean.setParamName(productId);
						status = reportDao.filDSRByEmp(date, empId, DSRMode.KPI, productId, value, accId, comment, DSRType.SMS);
						list.add(bean);
						//recordCorrect = true;
						if(status)
						{
							//System.out.println(">>>>>KPI>>"+productId+">>>>> Record Saved");
						}
						else
						{
							//System.out.println(">>>>>KPI>>"+productId+">>>>> Record not saved");
						}
					}
				}
			}
			else
			{
				recordCorrect = false;
				smsTest.sendSMS("Your mobile no is not register", mobileNo);
				//System.out.println(">>>>>>>>>Your mobile no is not register : "+ mobileNo);
				return;
				//templateDao.setNoneRegisterUserDetail(keyword, message, mobileNo);
			}
			
			/*if(recordCorrect)
			{
				for (SMSTemplateBean templateBean : list) {
					//System.out.println(">>>>>>>>>>templateBean.getDate() : "+ templateBean.getDate());
					status = reportDao.filDSRByEmp(templateBean.getDate(), empId, DSRMode.KPI, templateBean.getParamName(), templateBean.getId(), accId, comment);
					
					if(status)
					{
						//System.out.println(">>>>>KPI>>"+productId+">>>>> Record Saved");
					}
					else
					{
						//System.out.println(">>>>>KPI>>"+productId+">>>>> Record not saved");
					}
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receive Message & Mobile No from virtual server.
	 * */
	
	public void setSMSReport(String mobileNo, String msg)
	{
		String keyword[] = msg.split(":");
		
		String message[] = keyword[1].split(",");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int day = cal.get(Calendar.DAY_OF_MONTH);
		new GetRecordViaSMS().fillDSR(keyword[0], message, mobileNo);
	}
	public static void main(String[] args) {
		/*String mobileNo = "9250400314";
		String msg = "Test:DT 06-09-2013,D 2,C4,T 2,DY 7,DP 4,# This is test";
		String keyword[] = msg.split(":");
		
		String message[] = keyword[1].split(",");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int day = cal.get(Calendar.DAY_OF_MONTH);
		new GetRecordViaSMS().fillDSR(keyword[0], message, mobileNo);*/
		
		//System.out.println(">>>> DSRType.SMS : "+ DSRType.SMS +", DSRType.ONLINE : "+ DSRType.ONLINE);
	}
}
