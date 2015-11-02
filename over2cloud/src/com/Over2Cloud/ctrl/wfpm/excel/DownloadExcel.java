package com.Over2Cloud.ctrl.wfpm.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
//import com.Over2Cloud.ctrl.ExcelDownload.DownldeExcelCustomer;
import com.Over2Cloud.ctrl.Invicesetting.DownloadInvoiceExl;
import com.Over2Cloud.ctrl.Invicesetting.DownloadInvoicepdf;
import com.Over2Cloud.ctrl.Invicesetting.DownloadinvoiceDoc;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadExcel extends ActionSupport {
	/**
	 * DownloadExcel For Visitor.
	 */
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String titles;
	private String downloadType;
	private String downloadFile;
	private InputStream excelStream;
	private String from_date,to_date,mod; 
	 private String app_name=null;
	 private String id=null;
	 
	public String excelView()
	{
		return SUCCESS;
	}

	
	
	public String downloadExcelDetails(){
		
		   CommonOperInterface cbt=new CommonConFactory().createInterface();
		   List  Customerdata=null;
		   String[] titelheader=null;
		   String returnResult=ERROR;
 	try{
 	if(titles!=null){
 	titelheader= titles.split(",");
 	StringBuilder StringObj=new StringBuilder();
 	StringObj.append("select distinct ");
 	StringObj.append(titles);
 	StringObj.append(" "+" from opportunity_details"+" as customer INNER JOIN  client_basic_data AS cbd ON cbd.id= customer.clientName INNER JOIN client_offering_mapping AS com ON com.clientName = customer.clientName INNER JOIN offeringlevel3 AS off ON off.id = customer.offeringId  LEFT JOIN salesstagemaster AS ssm ON ssm.id =customer.sales_stages  LEFT JOIN forcastcategarymaster AS fcm ON fcm.id = customer.forecast_category  LEFT JOIN lostoportunity AS lop ON lop.id = customer.lost_reason  INNER JOIN employee_basic AS emp ON emp.id = customer.userName  where customer.id IN("+id+")");
 	try
 	{
 		Customerdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
 		System.out.println(Customerdata.size());
		   DownldeExcelCustomer writerexcel = new DownldeExcelCustomer();
		   String storeFilePath = new CreateFolderOs().createUserDir("excelreport");
		   downloadFile = writerexcel.createExcel(getText("Opportunity Review Details"), getText("downloadSheet"),"", Customerdata, titelheader, "customerdetails", getText(storeFilePath));
		 	excelStream = new FileInputStream(downloadFile);
			 if (downloadFile != null && !downloadFile.equals("")) {
					downloadFile = downloadFile.substring(downloadFile.lastIndexOf(File.separator));
					returnResult = SUCCESS;
				}else{
					addActionError("There IS no Result Find");
				}

 	}
			catch (Exception e) {
				e.printStackTrace();
			}
 	
   }}
 	catch (Exception e) {
			// TODO: handle exception
		}
	return returnResult;
	}


	public String getTitles() {
		return titles;
	}
	
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public String getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(String downloadFile) {
		this.downloadFile = downloadFile;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	
	
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMod() {
		return mod;
	}
	public void setMod(String mod) {
		this.mod = mod;
	}
	
}
