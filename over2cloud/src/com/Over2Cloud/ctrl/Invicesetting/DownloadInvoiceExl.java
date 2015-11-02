package com.Over2Cloud.ctrl.Invicesetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.Over2Cloud.CommonClasses.DateUtil;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadInvoiceExl extends ActionSupport{
	ServletContext context = ServletActionContext.getServletContext();
	ServletConfig servletConf = null;;
	private CellStyle cs = null;
	 private CellStyle csBold = null;
	 private CellStyle csBoldd = null;
	 private CellStyle csBolddd = null;
	 
	 private CellStyle csTop = null;
	 private CellStyle csRight = null;
	 private CellStyle csBottom = null;
	 private CellStyle csLeft = null;
	 private CellStyle csTopLeft = null;
	 private CellStyle csTopRight = null;
	 private CellStyle csBottomLeft = null;
	 private CellStyle csBottomRight = null;
	@SuppressWarnings("deprecation")
	public String createExcel(List data,String[] headerTitles,
			String titleKey, String filePath)
	{
		String fileName=null;
		 try {
			 
			  XSSFWorkbook wb = new XSSFWorkbook();
			   XSSFSheet  sheet = wb.createSheet("My Sample Excel");

			   //Setup some styles that we need for the Cells
			   setCellStyles(wb);
			   fileName = filePath + File.separator + "Invoice Excel"+ DateUtil.getCurrentDateIndianFormat()+ (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".xlsx";
			   //FileInputStream obtains input bytes from the image file
			   String image =context.getRealPath("images/dreamsol.png");
			   InputStream inputStream = new FileInputStream(image);
			   //Get the contents of an InputStream as a byte[].
			   byte[] bytes = IOUtils.toByteArray(inputStream);
			   //Adds a picture to the workbook
			   int pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
			   //close the input stream
			   inputStream.close();
			 
			   //Returns an object that handles instantiating concrete classes
			   CreationHelper helper = wb.getCreationHelper();
			 
			   //Creates the top-level drawing patriarch.
			   Drawing drawing = sheet.createDrawingPatriarch();
			 
			   //Create an anchor that is attached to the worksheet
			   ClientAnchor anchor = helper.createClientAnchor();
			   //set top-left corner for the image
			   anchor.setCol1(0);
			   anchor.setRow1(2);
			 
			   //Creates a picture
			   Picture pict = drawing.createPicture(anchor, pictureIdx);
			   //Reset the image to the original size
			   pict.resize();
			 

			 
			   //Get current Date and Time
			   Date date = new Date(System.currentTimeMillis());
			   DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
			 
			   //Set Column Widths
			   sheet.setColumnWidth(0, 2500); 
			   sheet.setColumnWidth(1, 2500);
			   sheet.setColumnWidth(2, 6000);
			   sheet.setColumnWidth(3, 10000);
			   sheet.setColumnWidth(4, 3000);
			 
			   //Setup the Page margins - Left, Right, Top and Bottom
			   sheet.setMargin(XSSFSheet.LeftMargin, 0.25);
			   sheet.setMargin(XSSFSheet.RightMargin, 0.25);
			   sheet.setMargin(XSSFSheet.TopMargin, 0.75);
			   sheet.setMargin(XSSFSheet.BottomMargin, 0.75);
			 
			   //Setup the Header and Footer Margins
			   sheet.setMargin(XSSFSheet.HeaderMargin, 0.25);
			   sheet.setMargin(XSSFSheet.FooterMargin, 0.25);
			    
			   //If you are using HSSFWorkbook() instead of XSSFWorkbook()
			   //HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
			   //ps.setHeaderMargin((double) .25);
			   //ps.setFooterMargin((double) .25);
			 
			   //Set Header Information 
			   Header header = sheet.getHeader();
			   header.setLeft("*** ORIGINAL COPY ***");
			   header.setCenter(HSSFHeader.font("Arial", "Bold") +
			     HSSFHeader.fontSize((short) 14) + "SAMPLE ORDER");
			   header.setRight(df.format(date));
			 
			   //Set Footer Information with Page Numbers
			   Footer footer = sheet.getFooter();
			   footer.setRight( "Page " + HeaderFooter.page() + " of " + 
			     HeaderFooter.numPages() );
			 
			   int rowIndex = 6* 1;
			   rowIndex = insertHeaderInfo(sheet, rowIndex);
			   rowIndex = insertDetailInfo(sheet, rowIndex);
			  
			   //Write the Excel file
			   FileOutputStream fileOut = null;
			   fileOut = new FileOutputStream(fileName);
			   wb.write(fileOut);
			   fileOut.close();
			 
			  }
			  catch (Exception e) {
			   System.out.println(e);
			  }
		     return fileName;
			 
			 
			 }
	
	 private void setCellStyles(XSSFWorkbook wb) {
		 
		  //font size 10
		  Font f = wb.createFont();
		  f.setFontHeightInPoints((short) 10);
		 
		  //Simple style 
		  cs = wb.createCellStyle();
		  cs.setFont(f);
		 
		  //Bold Fond
		  Font bold = wb.createFont();
		  bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		  bold.setFontHeightInPoints((short) 10);
		 
		  
		  //Bold style 
		  csBold = wb.createCellStyle();
		  csBold.setBorderBottom(CellStyle.BORDER_THIN);
		  csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBold.setFont(bold);
		  
		  //Bold style 
		  csBoldd = wb.createCellStyle();
		  csBoldd.setAlignment(CellStyle.ALIGN_RIGHT);
		  csBoldd.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBoldd.setFont(bold);
		 
		  
		  //Bold style 
		  csBolddd = wb.createCellStyle();
		  csBolddd.setAlignment(CellStyle.ALIGN_CENTER);
		  csBolddd.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBolddd.setFont(bold);
		  bold.setFontHeightInPoints((short) 10);
		
		  //Setup style for Top Border Line
		  csTop = wb.createCellStyle();
		  csTop.setBorderTop(CellStyle.BORDER_THIN);
		  csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTop.setFont(f);
		 
		  //Setup style for Right Border Line
		  csRight = wb.createCellStyle();
		  csRight.setBorderRight(CellStyle.BORDER_THIN);
		  csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csRight.setFont(f);
		 
		  //Setup style for Bottom Border Line
		  csBottom = wb.createCellStyle();
		  csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottom.setFont(f);
		 
		  //Setup style for Left Border Line
		  csLeft = wb.createCellStyle();
		  csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csLeft.setFont(f);
		 
		  //Setup style for Top/Left corner cell Border Lines
		  csTopLeft = wb.createCellStyle();
		  csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		  csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setFont(f);
		 
		  //Setup style for Top/Right corner cell Border Lines
		  csTopRight = wb.createCellStyle();
		  csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		  csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		  csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setFont(f);
		 
		  //Setup style for Bottom/Left corner cell Border Lines
		  csBottomLeft = wb.createCellStyle();
		  csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setFont(f);
		 
		  //Setup style for Bottom/Right corner cell Border Lines
		  csBottomRight = wb.createCellStyle();
		  csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		  csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setFont(f);
		 
		 }
	
	private int insertHeaderInfo(XSSFSheet sheet, int index){
		 
		  int rowIndex = index;
		  Row row = null;
		  Cell c = null;
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Reference::");
		  c.setCellStyle(csTopLeft);
		  c.setCellStyle(csTop);
		  c = row.createCell(2);
		  c.setCellStyle(csTopRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Our Ref:");
		  c.setCellStyle(csTopLeft);
		  c = row.createCell(1);
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellValue("Prabhat Kumar");
		  c.setCellStyle(csRight);
		  
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Your Ref:");
		  c.setCellStyle(csTopLeft);
		  c = row.createCell(1);
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellValue("Prabhat Kumar");
		  c.setCellStyle(csRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellStyle(csRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Name:");
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellValue("ABC Customer");
		  c.setCellStyle(csRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Address:");
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellValue("123 Street No.");
		  c.setCellStyle(csRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellStyle(csLeft);
		  c = row.createCell(2);
		  c.setCellValue("Unknown City, State ZIPCODE");
		  c.setCellStyle(csRight);
		 
		  rowIndex++;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellStyle(csBottomLeft);
		  c = row.createCell(1);
		  c.setCellStyle(csBottom);
		  c = row.createCell(2);
		  c.setCellValue("U.S.A.");
		  c.setCellStyle(csBottomRight);
		 
		  rowIndex = rowIndex + 3;
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Line No");
		  c.setCellStyle(csBold);
		  c = row.createCell(1);
		  c.setCellValue("Quantity");
		  c.setCellStyle(csBold);
		  c = row.createCell(2);
		  c.setCellValue("Item No");
		  c.setCellStyle(csBold);
		  c = row.createCell(3);
		  c.setCellValue("Description");
		  c.setCellStyle(csBold);
		  c = row.createCell(4);
		  c.setCellValue("Price");
		  c.setCellStyle(csBold);
		 
		  return rowIndex;
		 
		 }
	
	 @SuppressWarnings("deprecation")
	private int insertDetailInfo(XSSFSheet sheet, int index){
		 
		  int rowIndex = 0;
		  Row row = null;
		  Cell c = null;
		 
		  for(int i = 1; i <20; i++){
		   rowIndex = index + i;
		   row = sheet.createRow(rowIndex);
		   c = row.createCell(0);
		   c.setCellValue(i);
		   c.setCellStyle(cs);
		   c = row.createCell(1);
		   c.setCellValue(10 + i);
		   c.setCellStyle(cs);
		   c = row.createCell(2);
		   c.setCellValue("ITEM" + i);
		   c.setCellStyle(cs);
		   c = row.createCell(3);
		   c.setCellValue("My ITEM" + i + " Decscription");
		   c.setCellStyle(cs);
		   c = row.createCell(4);
		   c.setCellValue(1.11 * i);
		   c.setCellStyle(cs);
		  }
		   row = sheet.createRow(rowIndex);
           c = row.createCell(0);
           c.setCellValue("Balance:");
           c.setCellStyle(csBoldd);
    	   System.out.println("rowIndex"+rowIndex);
           sheet.addMergedRegion(new CellRangeAddress(
        		   
        		   rowIndex, // mention first row here
        		   rowIndex, //mention last row here, it is 1 as we are doing a column wise merging
                   0, //mention first column of merging
                   3  //mention last column to include in merge
                   ));
           

           for(int i=20 ; i < 25; i++){
        	   rowIndex=index+i;
    		   row = sheet.createRow(rowIndex);
        	   c = row.createCell(0);
        	   c.setCellStyle(csBoldd);
        	   c.setCellValue("Primary Education Cess @0.24%:"+">>"+i);          
               sheet.addMergedRegion(new CellRangeAddress(
            		   rowIndex, // mention first row here
            		   rowIndex, //mention last row here, it is 1 as we are doing a column wise merging
                       0, //mention first column of merging
                       3  //mention last column to include in merge
                       ) );
           }
           row = sheet.createRow(rowIndex);
           c = row.createCell(0);
           c.setCellValue("Online Bank Transfer Details:");
           c.setCellStyle(csBolddd);
           sheet.addMergedRegion(new CellRangeAddress(
        		   
        		   rowIndex, // mention first row here
        		   rowIndex, //mention last row here, it is 1 as we are doing a column wise merging
                   0, //mention first column of merging
                   4  //mention last column to include in merge
                   ));
           for(int i=26 ; i < 28; i++){
        	   rowIndex=index+i;
        	   c = sheet.createRow(rowIndex).createCell(0);
        	   c.setCellStyle(csBolddd);
        	   c.setCellValue("HDFC Bank,Sector-18,Noida Account NO:00882000018761 RTGS/NEFT IFC Code: HDFC000088"); 
               sheet.addMergedRegion(new CellRangeAddress(
            		   rowIndex, // mention first row here
            		   rowIndex+2, //mention last row here, it is 1 as we are doing a column wise merging
                       0, //mention first column of merging
                       2  //mention last column to include in merge
                       ) );
               
           }
           for(int i=26 ; i < 28; i++){
        	   rowIndex=index+i;
    		   c = sheet.createRow(rowIndex).createCell(3);
    		   c.setCellStyle(csBolddd);
    		   c.setCellValue("HDFC Bank,Sector-18,Noida Account NO:00882000018761 RTGS/NEFT IFC Code: HDFC000088"+i); 
               sheet.addMergedRegion(new CellRangeAddress(
            		   rowIndex, // mention first row here
            		   rowIndex+2, //mention last row here, it is 1 as we are doing a column wise merging
                       3, //mention first column of merging
                       4  //mention last column to include in merge
                       ) );
               
           }
           

           
		  return rowIndex;
		 
		 }
	
	
	
	
		
	
}
