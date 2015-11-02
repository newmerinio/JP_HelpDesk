/*package com.Over2Cloud.ctrl.Invicesetting;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class Downloadinvoice extends ActionSupport{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");

	 private String downloadType;
	 private String downloadFile;
	 private int id=0;
	 
	 
    public String downloadinvoicereport(){
    	String returnResult = ERROR;
    	  CommonOperInterface cbt=new CommonConFactory().createInterface();
    	try{
    		System.out.println("iDD"+getId());
    		//getting the list of colmuns
			StringBuilder ids=new StringBuilder("");
    		List<String> ObjList=new ArrayList<String>();
    		List<GridDataPropertyView> fieldNames=Configuration.getColomnfieldList("mapped_invoices_configuration", accountID, connectionSpace,"invoices_configuration");	
    		int l=0;
    		ids.append("select ");
    		for (GridDataPropertyView obdata : fieldNames) {
			    if(obdata!=null) {
			    	if(obdata.getColomnName().equals("product_name")){
				    	ids.append("contactms."+obdata.getColomnName());
				    	ObjList.add("product_name");}
			    	if(obdata.getColomnName().equals("customer_type")){
			    	ids.append("customertype."+obdata.getColomnName()+",");
			    	ObjList.add(obdata.getHeadingName());}
			    	else if(obdata.getColomnName().equals("customer_name")){
			    		ids.append("customername."+obdata.getColomnName()+",");
			    		ids.append("customername."+"customer_Contact"+",");
			    		ObjList.add(obdata.getColomnName());
			    		ObjList.add("customer_Contact");}
			    	else if(obdata.getColomnName().equals("invoice_status")){
			    		ids.append("statusname."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getHeadingName());}
			    	else if(obdata.getColomnName().equals("contact_name")){
			    		ids.append("contactname."+"sirname"+",");
			    		ids.append("contactname."+"first_name"+",");
			    		ids.append("contactname."+"last_name"+",");
			    		ObjList.add("contact_name");
			    	}
			    	else if(obdata.getColomnName().equals("invoice_date")){
			    		ids.append(obdata.getColomnName()+",");
			    		ObjList.add(obdata.getHeadingName());
			    	}
			    	else if(obdata.getColomnName().equals("invoice_duedate")){
			    		ids.append("contactms."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else if(obdata.getColomnName().equals("subject")){
			    		ids.append("contactms."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getHeadingName());
			    	}
			    	else if(obdata.getColomnName().equals("salesorder")){
			    		ids.append("contactms."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else if(obdata.getColomnName().equals("purchase_order")){
			    		ids.append("contactms."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else if(obdata.getColomnName().equals("description")){
			    		ids.append("contactms."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getHeadingName());
			    	}
			    	else if(obdata.getColomnName().equals("address")){
			    		ids.append("customername."+"street_address"+",");
			    		ObjList.add("street_address");
			    	}
			    	else if(obdata.getColomnName().equals("city")){
			    		ids.append("customername."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else if(obdata.getColomnName().equals("state")){
			    		ids.append("customername."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else if(obdata.getColomnName().equals("postal_code")){
			    		ids.append("customername."+"zip_code"+" "+",");
			    		ObjList.add("postal_code");
			    	}
			    	else if(obdata.getColomnName().equals("country")){
			    		ids.append("customername."+obdata.getColomnName()+",");
			    		ObjList.add(obdata.getColomnName());
			    	}
			    	else{
			    		
			    	}}
	}
    		 ids.append(" "+" from"+ " "+"createtinvovcedetails_table"+" as contactms"
    				 +" "+" inner join"+" "+"createcustomertype_table"+" "+"as customertype on "+" "+"contactms.customer_type="+" "+"customertype.id"
    				 +" "+" inner join"+" "+"createtcustomerdetails_table"+" "+"as customername on "+" "+"contactms.customer_name="+" "+"customername.id"
    				 +" "+" inner join"+" "+"createinvoicestatus_table"+" "+"as statusname on "+" "+"contactms.invoice_status="+" "+"statusname.id"
    				 +" "+" inner join"+" "+"contactbasicdetails"+" "+"as contactname on "+" "+"contactms.contact_name="+" "+"contactname.id"
    				 +" "+" where contactms.id="+" "+getId()
    				 );
    		List  downloadAbleData=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
    		String [] fieldsArray = ObjList.toArray(new String[ObjList.size()]);
    		if (downloadType != null && downloadType.equals("downloadPDF")) {
				  try                                      //try statement
			        {
						DownloadInvoicepdf writerPdf = new DownloadInvoicepdf();
						downloadFile = writerPdf.createPdf(downloadAbleData,fieldsArray, "Invoice PDF",  getText("C:/excelreport"),getId());
						System.out.println("downloadFile"+downloadFile);
						
			            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + downloadFile);   //open the file chart.pdf

			        } catch (Exception e)                    //catch any exceptions here
			          {
			              System.out.println("Error" + e );  //print the error
			          }
			}
    		if (downloadType != null && downloadType.equals("downloadDOC")) {
    			try                                      //try statement
		        {
    				DownloadinvoiceDoc writerDOC = new DownloadinvoiceDoc();
					downloadFile = writerDOC.createDOC(downloadAbleData,fieldsArray, "Invoice DOC",  getText("C:/excelreport"));
					System.out.println("downloadFile"+downloadFile);
		            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + downloadFile);   //open the file chart.pdf

		        } catch (Exception e){
		              System.out.println("Error" + e );  //print the error
		          }
    			
    		}
    		if (downloadType != null && downloadType.equals("downloadEXCEL")) {
    			try                                      //try statement
		        {
    				DownloadInvoiceExl writereXCEL = new DownloadInvoiceExl();
					downloadFile = writereXCEL.createExcel(downloadAbleData,fieldsArray, "Invoice EXCEL",  getText("C:/excelreport"));
					System.out.println("downloadFile"+downloadFile);
		            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + downloadFile);   //open the file chart.pdf

		        } catch (Exception e)                    //catch any exceptions here
		          {
		              System.out.println("Error" + e );  //print the error
		          }
    			
    		}
    		returnResult=SUCCESS;
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    return returnResult;
    }
	  
	  
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
*/