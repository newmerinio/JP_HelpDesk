package com.Over2Cloud.ctrl.ExcelDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadProductAction extends ActionSupport{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");

	 private String downloadType;
	 private String downloadFile;
	 private InputStream excelStream;
	 private String titles;

	 
	    public String downloadProductreport(){
			   CommonOperInterface cbt=new CommonConFactory().createInterface();
			   List  productdata=null;
			   String[] titelheader=null;
			   String returnResult=ERROR;
	    	try{
	    	if(titles!=null){	
	    	titelheader= titles.split(",");
	    	StringBuilder StringObj=new StringBuilder();
	    	StringObj.append("select ");
	    	StringObj.append(titles);
	    	StringObj.append(" "+" from"+ " "+"createtproductdetails_table"+" as product" ); 
	    	try{
	    		productdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	           if (downloadType != null && downloadType.equals("downloadExcel")) {
				  try {
					  DownldeExcelProduct writerexcel = new DownldeExcelProduct();
					  String storeFilePath = new CreateFolderOs().createUserDir("excelreport");
						downloadFile = writerexcel.createExcel(getText("Product Details"), getText("downloadSheet"), getText("Prdouct Excel Report"), productdata, titelheader, "productdetails", getText(storeFilePath));	
						 excelStream = new FileInputStream(downloadFile);
						 if (downloadFile != null && !downloadFile.equals("")) {
								downloadFile = downloadFile.substring(downloadFile.lastIndexOf(File.separator));
								returnResult = SUCCESS;
							}else{
								addActionError("There IS no Result Find");
							}
			         }catch (Exception e){
			              System.out.println("Error" + e );  //print the error
			          }
	    	}
	           if (downloadType != null && downloadType.equals("downloadPDF")) {
					  try {
						    DownldeExcelProduct writerPdf = new DownldeExcelProduct();
						    String storeFilePath = new CreateFolderOs().createUserDir("pdfreport");
						    downloadFile = writerPdf.createPdf(productdata,titelheader, "productdetails", getText(storeFilePath));
						    excelStream=new FileInputStream(new File(downloadFile));
						    returnResult = SUCCESS;
				         }catch (Exception e){
				              System.out.println("Error" + e );  //print the error
				          }
		    	} 
	           
	           else{addActionMessage("No Result Found! due to Fields selection");}
	    	}else{
	    		addActionMessage("No Result Found! due to Fields selection");
	    	  }
	    	
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
			}
  		return returnResult;
	    }
	    
	    public String downloadPrdouctformate(){
			   String[] titelheader=null;
			   String returnResult=ERROR;
	    	try{
	    	if(titles!=null){	
	    	titelheader= titles.split(",");
	  
	    	  DownldeExcelProduct excelformate = new DownldeExcelProduct();
	    	  String storeFilePath = new CreateFolderOs().createUserDir("excelreport");
						downloadFile = excelformate.createExcelformate(getText("Product Details"), getText("downloadSheet"), titelheader, "productdetails", getText(storeFilePath));	
						 excelStream = new FileInputStream(downloadFile);
						 if (downloadFile != null && !downloadFile.equals("")) {
								downloadFile = downloadFile.substring(downloadFile.lastIndexOf(File.separator));
								returnResult = SUCCESS;
							}else{
								addActionError("No Result Found! due to Fields selection");
							}
						 }
	    	     }
	    	catch (Exception e) {
				// TODO: handle exception
			}
 		return returnResult;
	    }
	    	
	    
		public String getTitles() {
			return titles;
		}

		public void setTitles(String titles) {
			this.titles = titles;
		}
		public String getDownloadType() {
			return downloadType;
		}
		public void setDownloadType(String downloadType) {
			this.downloadType = downloadType;
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
	
	    
}
