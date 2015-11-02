package com.Over2Cloud.ctrl.Invicesetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.Over2Cloud.CommonClasses.DateUtil;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import com.opensymphony.xwork2.ActionSupport;

public class DownloadinvoiceDoc extends ActionSupport{
	
	
	    @SuppressWarnings("unchecked")
	    ServletContext context = ServletActionContext.getServletContext();
		ServletConfig servletConf = null;;
		 private BaseFont bfBold;
		 private BaseFont bf;
		 private int pageNumber = 0;
		 //special font sizes
		   Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0)); 
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
		   DecimalFormat df = new DecimalFormat("0.00");
		@SuppressWarnings("static-access")
		public String createDOC(List data,String[] headerTitles,
				String titleKey, String filePath)
		{
			DownloadinvoiceDoc downloadinvoiceDoc = new DownloadinvoiceDoc();
		   String fileName=null;
		   
		  if (filePath!=null)
		  {	
				try{
					
				     fileName = filePath + File.separator + "Invoice DOC"+ DateUtil.getCurrentDateIndianFormat()+ (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".pdf" ;
				     downloadinvoiceDoc.createPDF(fileName);
				}
		 catch (Exception e) {
			// TODO: handle exception
		 }
		  }
		  return fileName;
		 }
		 
		 private void createPDF (String pdfFilename){
		 
		  Document doc = new Document();
		  PdfWriter docWriter = null;
		  initializeFonts();
		 
		  try {
		   String path = pdfFilename;
		   docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
		   doc.addAuthor("betterThanZero");
		   doc.addCreationDate();
		   doc.addProducer();
		   doc.addCreator("MySampleCode.com");
		   doc.addTitle("Invoice");
		   doc.setPageSize(PageSize.LETTER);
		 
		   doc.open();
		   PdfContentByte cb = docWriter.getDirectContent();
		    
		   boolean beginPage = true;
		   int y = 0;
		    
		   for(int i=0; i < 10; i++ ){
		    if(beginPage){
		     beginPage = false;
		     generateLayout(doc, cb); 
		     generateHeader(doc, cb);
		     y = 615; 
		    }
		    generateDetail(doc, cb, i, y);
		    y = y - 15;
		    if(y < 50){
		     printPageNumber(cb);
		     doc.newPage();
		     beginPage = true;
		    }
		   }
		   printPageNumber(cb);
		        
		  }
		  catch (DocumentException dex)
		  {
		   dex.printStackTrace();
		  }
		  catch (Exception ex)
		  {
		   ex.printStackTrace();
		  }
		  finally
		  {
		   if (doc != null)
		   {
			   
		    doc.close();
		   }
		   if (docWriter != null)
		   {
		    docWriter.close();
		   }
		  }
		 }
		 
		 private void generateLayout(Document doc, PdfContentByte cb)  {
		 
		  try {
		 
		   cb.setLineWidth(1f);
		 
		   // Invoice Header box layout
		   cb.rectangle(420,700,150,60);
		   cb.moveTo(420,720);
		   cb.lineTo(570,720);
		   cb.moveTo(420,740);
		   cb.lineTo(570,740);
		   cb.moveTo(480,700);
		   cb.lineTo(480,760);
		   cb.stroke();
		 
		   // Invoice Header box Text Headings 
		   createHeadings(cb,422,743,"Account No.");
		   createHeadings(cb,422,723,"Invoice No.");
		   createHeadings(cb,422,703,"Invoice Date");
		 
		   // Invoice Detail box layout 
		   cb.rectangle(20,50,550,600);
		   cb.moveTo(20,630);
		   cb.lineTo(570,630);
		   cb.moveTo(50,50);
		   cb.lineTo(50,650);
		   cb.moveTo(150,50);
		   cb.lineTo(150,650);
		   cb.moveTo(430,50);
		   cb.lineTo(430,650);
		   cb.moveTo(500,50);
		   cb.lineTo(500,650);
		   cb.stroke();
		 
		   // Invoice Detail box Text Headings 
		   createHeadings(cb,22,633,"Qty");
		   createHeadings(cb,52,633,"Item Number");
		   createHeadings(cb,152,633,"Item Description");
		   createHeadings(cb,432,633,"Price");
		   createHeadings(cb,502,633,"Ext Price");
		 
		   //add the images
		   Image companyLogo = Image.getInstance(context.getRealPath("images/dreamsol.png"));
		   companyLogo.scaleToFit(100,50);
		   companyLogo.setAlignment(Element.ALIGN_LEFT);
	      
		   doc.add(companyLogo);
		 
		  }
		 
		  catch (DocumentException dex){
		   dex.printStackTrace();
		  }
		  catch (Exception ex){
		   ex.printStackTrace();
		  }
		 
		 }
		  
		 private void generateHeader(Document doc, PdfContentByte cb)  {
		 
		  try {
		 
		   createHeadings(cb,200,750,"Company Name");
		   createHeadings(cb,200,735,"Address Line 1");
		   createHeadings(cb,200,720,"Address Line 2");
		   createHeadings(cb,200,705,"City, State - ZipCode");
		   createHeadings(cb,200,690,"Country");
		    
		   createHeadings(cb,482,743,"ABC0001");
		   createHeadings(cb,482,723,"123456");
		   createHeadings(cb,482,703,"09/26/2012");
		 
		  }
		 
		  catch (Exception ex){
		   ex.printStackTrace();
		  }
		 
		 }
		  
		 private void generateDetail(Document doc, PdfContentByte cb, int index, int y)  {
		  DecimalFormat df = new DecimalFormat("0.00");
		   
		  try {
		 
		   createContent(cb,48,y,String.valueOf(index+1),PdfContentByte.ALIGN_RIGHT);
		   createContent(cb,52,y, "ITEM" + String.valueOf(index+1),PdfContentByte.ALIGN_LEFT);
		   createContent(cb,152,y, "Product Description - SIZE " + String.valueOf(index+1),PdfContentByte.ALIGN_LEFT);
		    
		   double price = Double.valueOf(df.format(Math.random() * 10));
		   double extPrice = price * (index+1) ;
		   createContent(cb,498,y, df.format(price),PdfContentByte.ALIGN_RIGHT);
		   createContent(cb,568,y, df.format(extPrice),PdfContentByte.ALIGN_RIGHT);
		    
		  }
		 
		  catch (Exception ex){
		   ex.printStackTrace();
		  }
		 
		 }
		 
		 private void createHeadings(PdfContentByte cb, float x, float y, String text){
		 
		 
		  cb.beginText();
		  cb.setFontAndSize(bfBold, 8);
		  cb.setTextMatrix(x,y);
		  cb.showText(text.trim());
		  cb.endText(); 
		 
		 }
		  
		 private void printPageNumber(PdfContentByte cb){
		 
		 
		  cb.beginText();
		  cb.setFontAndSize(bfBold, 8);
		  cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1), 570 , 25, 0);
		  cb.endText(); 
		   
		  pageNumber++;
		   
		 }
		  
		 private void createContent(PdfContentByte cb, float x, float y, String text, int align){
		 
		 
		  cb.beginText();
		  cb.setFontAndSize(bf, 8);
		  cb.showTextAligned(align, text.trim(), x , y, 0);
		  cb.endText(); 
		 
		 }
		 
		 private void initializeFonts(){
		 
		 
		  try {
		   bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		   bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		 
		  } catch (DocumentException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 
		 
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
		 
		}
   
