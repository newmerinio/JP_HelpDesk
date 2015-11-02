package com.Over2Cloud.ctrl.Invicesetting;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.Over2Cloud.BeanUtil.ProductIteam;
import com.Over2Cloud.CommonClasses.ConvertInwords;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;


import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.rowset.internal.InsertRow;

public class DownloadInvoicepdf extends ActionSupport{
	@SuppressWarnings("unchecked")
ServletContext context = ServletActionContext.getServletContext();
	Map session = ActionContext.getContext().getSession();
	ServletConfig servletConf = null;;
	private int percentagSum=0;
	private int grandtotal=0;
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	@SuppressWarnings("static-access")
	public String createPdf(List dataList,String[] headerTitles,
			String titleKey, String filePath,int id)
	{
		 int col = 0;
		 String fileName=null;
		 PdfPCell cell = null;
		 List productDataList=null;
			String[] productid=null;
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		try{
			   //special font sizes
			   Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0)); 
			   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
			
			   DecimalFormat df = new DecimalFormat("0.00");
		Document document=new Document(PageSize.A4,15,15,40,40);
		 fileName = filePath + File.separator + "Invoice PDF"+ DateUtil.getCurrentDateIndianFormat()+ (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".pdf";
		 PdfWriter.getInstance(document,new FileOutputStream(fileName));
		 document.open();
		   Image image =Image.getInstance(context.getRealPath("images/dreamsol.png"));
	        image.scaleToFit(100,50);
	        image.setAlignment(Element.ALIGN_LEFT);
	        document.add(image);
	        com.itextpdf.text.Font myContentStyle=new com.itextpdf.text.Font(); /* Define a new Font Object */
	        myContentStyle.setStyle("bold");
            myContentStyle.setColor(255,0,0);
            myContentStyle.setSize(12);
	        document.add(new Paragraph("DreamSol TeleSolution Pvt.ltd.",myContentStyle));
	        com.itextpdf.text.Font myContentStyleS=new com.itextpdf.text.Font(); /* Define a new Font Object */
	        myContentStyleS.setStyle("bold");
	        myContentStyleS.setSize(8);
	        document.add(new Paragraph("An ISO 9001:2000 Certified Company",myContentStyleS));
	        com.itextpdf.text.Font fonsts=new com.itextpdf.text.Font(); /* Define a new Font Object */
	        fonsts.setSize(8);
	        fonsts.setColor(0, 0, 0);
	        fonsts.isBold();
	        document.add(new Paragraph("C-52 Sector-2 Noida India Ph: +91-120-4316422/2506414.Fax: +91-120-4316414",fonsts));
	        
	        
	        //specify column widths
	        float[] columnWidthsss = {10f};
	        PdfPTable ttS = new PdfPTable(columnWidthsss);
	        ttS.setWidthPercentage(80f);
	        ttS.setSpacingBefore(20);
	        insertCell(ttS, "INVOICE", Element.ALIGN_CENTER, 1, bfBold12);
	        ttS.setHeaderRows(1);
	        insertCell(ttS, "", Element.ALIGN_LEFT, 4, bfBold12);
	        document.add(ttS);
	        
	        //specify column widths
	        float[] columnWidthss = {5f, 5f};
	        PdfPTable t = new PdfPTable(columnWidthss);
	        t.setWidthPercentage(80f);
	      //insert column headings
	        insertCell(t, "Invoice 	No:"+" "+"DS/12-13/LSMS/"+id, Element.ALIGN_LEFT, 1, bfBold12);
	        insertCell(t, "Date: "+" "+DateUtil.getCurrentDateIndianFormat(), Element.ALIGN_CENTER, 1, bfBold12);
	 
	        t.setHeaderRows(1);
	        
	        //insert an empty row
	        StringBuilder strinObj=new StringBuilder();
	        insertCell(t, "", Element.ALIGN_LEFT, 4, bfBold12);
	    	strinObj.append("Issued To: ");
	        StringBuilder strngObjRef=new StringBuilder();
	        strngObjRef.append("References: ");
	    	for(Iterator it=dataList.iterator(); it.hasNext();){
				Object[] obdata=(Object[])it.next();
	        for (int k = 0; k < headerTitles.length; k++) {
	        	if(obdata[k]!=null){
	        	if(headerTitles[k].toString().equalsIgnoreCase("customer_Name")){
	        	strinObj.append("\n"+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("street_address")){
				 strinObj.append("\n"+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("city")){
				 strinObj.append("\n"+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("state")){
				 strinObj.append("\n"+obdata[k].toString()+" ");}
			 else if(headerTitles[k].toString().equalsIgnoreCase("postal_code")){
				 strinObj.append(obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("country")){
				 strinObj.append("\n"+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("customer_Contact")){
				 strngObjRef.append("\n"+"Our Ref:"+" "+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("contact_name")){
				 String contact_name="";
					for(int i=k; i<(k+3); i++){
					contact_name+= obdata[i].toString()+" ";}
					strngObjRef.append("\n"+"Your Ref:"+" "+contact_name);}
			 else if(headerTitles[k].toString().equalsIgnoreCase("purchase_order")){
				 strngObjRef.append("\n"+"P.O.No :"+" "+obdata[k].toString());}
			 else if(headerTitles[k].toString().equalsIgnoreCase("invoice_duedate")){
				 strngObjRef.append("\n"+"Due Dated:"+obdata[k].toString());}
	        	
			 else if(headerTitles[k].toString().equalsIgnoreCase("product_name")){
					productid= obdata[k+2].toString().split("#");
					}
			 else{}
	        	}}}
	    	
	 		//insertCell(t, " Issued To: "+"\n"+headerTitles[k].toString()+"\n"+"Indl plot No-22 Udyog Vihar,"+"\n"+"Greater Noida Indl.Dev Area"+"\n"+"Dist. Gautam Buddha Nagar, UP", Element.ALIGN_LEFT, 1, bf12);
	        insertCell(t, strinObj.toString(), Element.ALIGN_LEFT, 1, bf12);
	        insertCell(t, strngObjRef.toString(), Element.ALIGN_LEFT, 1, bf12);
	
	        document.add(t);
	        
	      
	        //specify column widths
	        float[] columnWidths = {5f, 1.5f,2f , 2f};
	        //create PDF table with the given widths
	        PdfPTable table = new PdfPTable(columnWidths);
	        table.setWidthPercentage(80f);
	     
	        
	      //insert column headings
	        insertCell(table, "Description of Product", Element.ALIGN_LEFT, 1, bfBold12);
	        insertCell(table, "Category:", Element.ALIGN_LEFT, 1, bfBold12);
	        insertCell(table, "Quantity", Element.ALIGN_CENTER, 1, bfBold12);
	        insertCell(table, "Amount(INR)", Element.ALIGN_RIGHT, 1, bfBold12);
	        table.setHeaderRows(1);
	      
	      //insert an empty row
	        insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
	        //create section heading by cell merging
	        insertCell(table, "DreamSol Product Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
	        double  total = 0;
	        List<String>productcolname=new ArrayList<String>();
			productcolname.add("id");
			productcolname.add("product_name");
			productcolname.add("product_category");
			productcolname.add("qtyt");
			productcolname.add("unit_price");
			for (int i = 0; i < productid.length; i++) {
				   Map<String, Object>wherClause= new HashMap<String, Object>();
			       wherClause.put("id",productid[i]);
			productDataList=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname, wherClause, connectionSpace);	
	        if(productDataList!=null){
				for (Object c : productDataList) {
					Object[] dataC = (Object[]) c;
					 if(dataC[0]!=null && dataC[0]!=null){}
					 if(dataC[1]!=null && dataC[1]!=null){
						 insertCell(table,dataC[1].toString(),Element.ALIGN_CENTER, 1, bf12);
					 }
					 if(dataC[2]!=null && dataC[2]!=null){
						 insertCell(table,dataC[2].toString(),Element.ALIGN_CENTER, 1, bf12);
					 }
					 if(dataC[3]!=null && dataC[3]!=null){
						  insertCell(table, dataC[3].toString() , Element.ALIGN_CENTER, 1, bf12);
						 }
					 if(dataC[4]!=null && dataC[4]!=null){
						 insertCell(table, dataC[4].toString(), Element.ALIGN_RIGHT, 1, bf12);
						 total+= Integer.parseInt(dataC[4].toString());
					 }
				  } 
	        }
				}
	      //merge the cells to create a footer for that section
	        insertCell(table, "Balance: ", Element.ALIGN_RIGHT, 3, bfBold12);
	        insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);
	    	List<String> objlistdata=new ArrayList<String>();
	        objlistdata.add("tax_name");
			objlistdata.add("tax_price");
	        List taxlistdata=cbt.viewAllDataEitherSelectOrAll("createtax_table",objlistdata,connectionSpace);
	    	for (Iterator iterator2 = taxlistdata.iterator(); iterator2.hasNext();) {
			Object[] object = (Object[]) iterator2.next();
			ProductIteam strObjt=new ProductIteam();
		   if(object[0].toString()!=null){
			 insertCell(table, object[0].toString()+" "+"@"+" "+object[1].toString()+""+"%", Element.ALIGN_RIGHT, 3, bfBold12);
			try{
	         Double percentage=Double.valueOf(object[1].toString())*total/100;
	         percentagSum+= percentage;
	         insertCell(table, df.format(percentage), Element.ALIGN_RIGHT, 1, bfBold12);
			}
			catch (Exception e) {
				// TODO: handle exception
			} }
	    	}
	        int xyzz= (int) (percentagSum+total);
	      //merge the cells to create a footer for that section
	        insertCell(table, "Grand Total: ", Element.ALIGN_RIGHT, 3, bfBold12);
	        insertCell(table, df.format(xyzz), Element.ALIGN_RIGHT, 1, bfBold12);
	        ConvertInwords amointinwords=new ConvertInwords();
	        String wordString= amointinwords.convertInwords(xyzz);
	        //repeat the same as above to display another location
	        insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
	        insertCell(table, "Amount Chargeable(in words): "+"\n"+wordString, Element.ALIGN_LEFT, 4, bfBold12);
	    
	         
	    
	        insertCell(table, "Services Tax No:"+"\n"+"DL-II/ST/R-17/BAS/DTS/1224/05", Element.ALIGN_CENTER, 2, bfBold12);
	        insertCell(table, "Permanent Account No:"+"\n"+"AA-CCD-2037-R", Element.ALIGN_CENTER, 2, bfBold12);
	       
	        //repeat the same as above to display another location
	        insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
	        insertCell(table, "Online Bank Transfer Details", Element.ALIGN_CENTER, 4, bfBold12);
	        
	        insertCell(table, "HDFC Bank,Sector-18,Noida"+"\n"+"Account NO:00882000018761"+"\n"+"RTGS/NEFT IFC Code: HDFC000088", Element.ALIGN_CENTER, 2, bf12);
	        insertCell(table, "IDBI Bank,Sector-18,Noida "+"\n"+"Account No::0109102000028291"+"\n"+"IFC Code:IBKL0000109", Element.ALIGN_CENTER, 2, bf12);
	       
	        insertCell(table, "Important"+"\n"+
	        		
	        		"1. Payment to be made in favor of DreamSol TeleSolution Pvt.Ltd payable at Noida"+"\n"+
	        		"2. Kindly mention the invoice number & TDS details(if any) with the payments"+"\n"+
	        		"3. Service Tax Category : Business Auxiliary Services."+"\n"+
	        		"4. Intrest @ 18% will be charged by us on all payments that remain unpaid after 30 days."+"\n"+
	        	    "5. No Claims of damages or any other expenses will allowed unless authorized in written. "+"\n"+
	        	    "6. Company shall not be liable for any indirect or any consequential damages what so ever. "+"\n"+
	        	    "7. subject to jurisdiction of Delhi Courts only."
	        	    , Element.ALIGN_LEFT, 4, bf12);
	        insertCell(table, "This Invoice is raised after obtaining a prior consent "+"\n"+"Kindly pay or before due date(with in 20 days of invoice date)"+"\n"+"to avoid discontinuation of services.", Element.ALIGN_CENTER, 4, bfBold12);
	        
	     
	        document.add(table);
	        //create a paragraph
	        com.itextpdf.text.Font fonst=new com.itextpdf.text.Font();
	        fonst.setSize(8);
	        fonst.setColor(0, 0, 0);
	        fonst.isBold();
	        Paragraph paragraph = new Paragraph("This is Computer-Generated Invoice.hence dosn't require Signatures",fonst);
	        paragraph.setAlignment(Element.ALIGN_CENTER);
	        document.add(paragraph);
	        Paragraph pragrap= new Paragraph("DreamSol TeleSolution Pvt.ltd.",myContentStyle);
	        pragrap.setAlignment(Element.ALIGN_CENTER);
	        document.add(pragrap);
	        com.itextpdf.text.Font ssss=new com.itextpdf.text.Font(); /* Define a new Font Object */
	        ssss.setStyle("bold");
	        ssss.setSize(8);
	        Paragraph pragraps= new  Paragraph("An ISO 9001:2000 Certified Company",ssss);
	        pragraps.setAlignment(Element.ALIGN_CENTER);
	        document.add(pragraps);
	        com.itextpdf.text.Font fonstsss=new com.itextpdf.text.Font(); /* Define a new Font Object */
	        fonstsss.setSize(8);
	        fonstsss.setColor(0, 0, 0);
	        fonstsss.isBold();
	        Paragraph pragrapss= new  Paragraph("C-52 Sector-2 Noida India Ph: +91-120-4316422/2506414.Fax: +91-120-4316414",fonstsss);
	        pragrapss.setAlignment(Element.ALIGN_CENTER);
	        document.add(pragrapss);
	        
	        document.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return fileName;
	
}
	
	 private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
		   
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
		  cell.setColspan(colspan);
		  //in case there is no text and you wan to create an empty row
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  //add the call to the table
		  table.addCell(cell);
		   
		 }
	public ServletConfig getServletConf() {
		return servletConf;
	}
	public void setServletConf(ServletConfig servletConf) {
		this.servletConf = servletConf;
	}
	
	
	
	}
