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

public class DownloadContactAction extends ActionSupport  {
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");

	private InputStream fileInputStream;
	
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	 private String downloadType;
	 private String downloadFile;
	 private String[] titles;
	
	 
	 public String downloadContactreport() throws Exception {
			   CommonOperInterface cbt=new CommonConFactory().createInterface();
			   List  Contactdata=null;
	    	try{
	    	if(titles!=null){	
	    	StringBuilder StringObj=new StringBuilder();
	    	StringObj.append("select ");
	    	int sizes=1;
	    	for (int i = 0; i < titles.length; i++) {
		    	 if(sizes<titles.length){
		    		 StringObj.append(" "+titles[i]+" "+",");
		    	 }else{
		    		 StringObj.append(" "+titles[i]+" ");
		    	 }
		    	 sizes++;
			}
	    	System.out.println("StringObj"+StringObj);
	    	StringObj.append(" "+" from"+ " "+"contactbasicdetails"+" as contactms "+" "+"left join "+" "+"contactadressdetails"+" "+"as contacadrss on "+" "+"contactms"+"."+"id="+"contacadrss"+".mappedid" ); 
	    	
	    	System.out.println("StringObj>>>>>>"+StringObj);
	    	try{
				 Contactdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	    	}
	           if (downloadType != null && downloadType.equals("downloadExcel")) {
				  try {
					    DownldeExcelContact writerexcel = new DownldeExcelContact();
					    String storeFilePath = new CreateFolderOs().createUserDir("excelreport");
						downloadFile = writerexcel.createExcel(getText("Conatct Details"), getText("downloadSheet"), getText("Conatct Excel Report"), Contactdata, titles, "contactdetails", getText(storeFilePath));	
						fileInputStream=new FileInputStream(new File(downloadFile));
						
			         }catch (Exception e){
			              System.out.println("Error" + e );  //print the error
			          }
	    	}
	           if (downloadType != null && downloadType.equals("downloadPDF")) {
					  try {
						    DownldeExcelContact writerPdf = new DownldeExcelContact();
						    String storeFilePath = new CreateFolderOs().createUserDir("pdfreport");
						    downloadFile = writerPdf.createPdf(Contactdata,titles, "contactdetails", getText(storeFilePath));
							fileInputStream=new FileInputStream(new File(downloadFile));
							
				         }catch (Exception e){
				              System.out.println("Error" + e );  //print the error
				          }
		    	}
	           
	           else{addActionMessage("No Result Found! due to Fields selection");}}
	    	catch (Exception e) {
				// TODO: handle exception
			}
         return SUCCESS;
	    }
	    
	    public String downloadContactformate(){
			   String[] titelheader=null;
			   String returnResult=ERROR;
	    	try{
	    	if(titles!=null){	
	   
	    	DownldeExcelContact excelformate = new DownldeExcelContact();
	    	   String storeFilePath = new CreateFolderOs().createUserDir("excelreport");
						downloadFile = excelformate.createExcelformate(getText("Conatct Details"), getText("downloadSheet"), titles, "contactdetails", getText(storeFilePath));	
						fileInputStream = new FileInputStream(downloadFile);
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
	    	
	    
		public String[] getTitles() {
			return titles;
		}

		public void setTitles(String[] titles) {
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
	
	
	    
}
