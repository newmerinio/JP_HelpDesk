package com.Over2Cloud.ctrl.VAM.VisitorReports;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.VAM.BeanUtil.SummaryReportPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class SummaryReportExcelToAttache
{
	
 public String writeDataToExcel(List<SummaryReportPojo> visitordatalist, String location)
 {
   String excelFileName =null;
   String mergeDateTime=new DateUtil().mergeDateTime();
   String totalFeedback = "Visitor Summary On "+DateUtil.getCurrentDateIndianFormat() ;
   List orgData= new LoginImp().getUserInfomation("8", "IN");
	String orgName="";
	
	if (orgData!=null && orgData.size()>0) {
		for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			orgName=object[0].toString();
		}
	}
   String reportType="";
   
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(totalFeedback);
	Map<String, CellStyle> styles = createStyles(wb);
	
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);
	

	// Title Row
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue(orgName);
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,14));
	
	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue(reportType);
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,14));
	
	excelFileName = new CreateFolderOs().createUserDir("Visitor Summary")+ "_"+location+"_"+DateUtil.getCurrentDateIndianFormat()+".xls";
	int header_first=3;
	int index=4;
	int header_1=header_first+(visitordatalist.size())+3;
	int index1=header_1+1;
	//int header_2=header_1+(vehicledatalist.size())+3;
	//int index2=header_2+1;
	//int header_3=header_2+(postacknowledgelist.size())+3;
	//int index3=header_3+1;
	
	
	if (visitordatalist.size()<=0) {
		 header_1=3;
		 index1=4;
	}
	else {
		 header_1=header_first+(visitordatalist.size())+3;
		 index1=header_1+1;
	}
	
	/*if (vehicledatalist.size()<=0) {
		 header_2=header_1;
		 index2=index1;
	}
	else {
		 header_2=header_1+(vehicledatalist.size())+3;
		 index2=header_2+1;
	}
	
	if (postacknowledgelist.size()<=0) {
		 header_3=header_2;
		 index3=index2;
	}
	else {
		 header_3=header_2+(postacknowledgelist.size())+3;
		 index3=header_3+1;
	}*/
	

	if(visitordatalist!=null && visitordatalist.size()>0)
	 {
    // Header Row
	Row headerRow10 = sheet.createRow(header_first-1);
	headerRow10.setHeightInPoints(18);
	Cell headerTitleCell10 = headerRow10.createCell(0);
	headerTitleCell10.setCellValue("Visitor Summary");
	headerTitleCell10.setCellStyle(styles.get("subTitle_RS"));
	sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,14));
	
	// Code Start for showing the data for current day Visitor.
	HSSFRow rowHead = sheet.createRow((int) header_first);
	rowHead.createCell((int) 0).setCellValue(" Visitor ID ");
	rowHead.createCell((int) 1).setCellValue(" Visitor Name ");
	rowHead.createCell((int) 2).setCellValue(" Visitor Mobile ");
	rowHead.createCell((int) 3).setCellValue(" Visitor Company ");
	rowHead.createCell((int) 4).setCellValue(" Address ");
	rowHead.createCell((int) 5).setCellValue(" Purpose ");
	rowHead.createCell((int) 6).setCellValue(" Visited Person ");
	rowHead.createCell((int) 7).setCellValue(" Visited Mobile ");
	rowHead.createCell((int) 8).setCellValue(" Department Name ");
	rowHead.createCell((int) 9).setCellValue(" Location ");
	rowHead.createCell((int) 10).setCellValue(" Time In ");
	rowHead.createCell((int) 11).setCellValue(" Time Out ");
	rowHead.createCell((int) 12).setCellValue(" Total Time IN");
	
	  for (SummaryReportPojo CDVD:visitordatalist)
	   {
			HSSFRow rowData=sheet.createRow((int)index);
			//Data Insertion as Ticket Lodged By Department Name
			rowData.createCell((int) 0).setCellValue(CDVD.getSr_number());
			rowData.createCell((int) 1).setCellValue(CDVD.getVisitor_name());
			rowData.createCell((int) 2).setCellValue(CDVD.getVisitor_mobile());
			rowData.createCell((int) 3).setCellValue(CDVD.getVisitor_company());
			rowData.createCell((int) 4).setCellValue(CDVD.getAddress());
			rowData.createCell((int) 5).setCellValue(CDVD.getPurpose());
			rowData.createCell((int) 6).setCellValue(CDVD.getVisited_person());
			rowData.createCell((int) 7).setCellValue(CDVD.getVisited_mobile());
			rowData.createCell((int) 8).setCellValue(CDVD.getDeptName());
			rowData.createCell((int) 9).setCellValue(CDVD.getLocation());
			rowData.createCell((int) 10).setCellValue(CDVD.getTime_in());
			rowData.createCell((int) 11).setCellValue(CDVD.getTime_out());
			rowData.createCell((int) 12).setCellValue(CDVD.getTotaltimestay());
			index++;
			sheet.autoSizeColumn(index);
		}
	}
   // Code End for showing the data for current day Resolved Tickets


	/*if(vehicledatalist!=null && vehicledatalist.size()>0)
	 {
	Row headerRow14 = sheet.createRow(header_1-1);
	headerRow14.setHeightInPoints(18);
	Cell headerTitleCell14 = headerRow14.createCell(0);
	headerTitleCell14.setCellValue("Vehicle Report Data");
	headerTitleCell14.setCellStyle(styles.get("subTitle_PN"));
	sheet.addMergedRegion(new CellRangeAddress(header_1-1, header_1-1, 0,14));
	  // Code Start for showing the data for current day Snooze Tickets
	HSSFRow rowHead4 = sheet.createRow(header_1);
	rowHead4.createCell((int) 0).setCellValue(" Sr. No. ");
	rowHead4.createCell((int) 1).setCellValue(" Driver Name ");
	rowHead4.createCell((int) 2).setCellValue(" Driver Mobile ");
	rowHead4.createCell((int) 3).setCellValue(" Vehicle Number ");
	rowHead4.createCell((int) 4).setCellValue(" Entry Time ");
	rowHead4.createCell((int) 5).setCellValue(" Out Time ");
	rowHead4.createCell((int) 6).setCellValue(" Material Detail ");
	rowHead4.createCell((int) 7).setCellValue(" Quantity ");
	rowHead4.createCell((int) 8).setCellValue(" Department Name ");
	rowHead4.createCell((int) 9).setCellValue(" Location ");

	for (SummaryReportPojo CDVHD:vehicledatalist) {
		HSSFRow rowData=sheet.createRow((int)index1);
		//Data Insertion as Ticket Lodged By Department Name
		rowData.createCell((int) 0).setCellValue(CDVHD.getSr_number());
		rowData.createCell((int) 1).setCellValue(CDVHD.getDriver_name());
		rowData.createCell((int) 2).setCellValue(CDVHD.getDriver_mobile());
		rowData.createCell((int) 3).setCellValue(CDVHD.getVehicle_number());
		rowData.createCell((int) 4).setCellValue(CDVHD.getEntry_time());
		rowData.createCell((int) 5).setCellValue(CDVHD.getOut_time());
		rowData.createCell((int) 6).setCellValue(CDVHD.getMaterial_details());
		rowData.createCell((int) 7).setCellValue(CDVHD.getQuantity());
		rowData.createCell((int) 8).setCellValue(CDVHD.getDeptName());
		rowData.createCell((int) 9).setCellValue(CDVHD.getLocation());
		index1++;
		sheet.autoSizeColumn(index1);
	}
}*/
	/*if(postacknowledgelist!=null && postacknowledgelist.size()>0)
	 {
		Row headerRow11 = sheet.createRow(header_2-1);
		headerRow11.setHeightInPoints(18);
		Cell headerTitleCell11 = headerRow11.createCell(0);
		headerTitleCell11.setCellValue("Post Acknowledge Report Data");
		headerTitleCell11.setCellStyle(styles.get("subTitle_SN"));
		sheet.addMergedRegion(new CellRangeAddress(header_2-1, header_2-1, 0,14));
	    // Code Start for showing the data for current day Snooze Tickets
		HSSFRow rowHead1 = sheet.createRow(header_2);
		rowHead1.createCell((int) 0).setCellValue(" Id ");
		rowHead1.createCell((int) 1).setCellValue(" Vendor Type ");
		rowHead1.createCell((int) 2).setCellValue(" Vendor Name ");
		rowHead1.createCell((int) 3).setCellValue(" Account Manager ");
		rowHead1.createCell((int) 4).setCellValue(" Mobile Number ");
		rowHead1.createCell((int) 5).setCellValue(" Department ");
		rowHead1.createCell((int) 6).setCellValue(" Employee Name ");
		rowHead1.createCell((int) 7).setCellValue(" Location ");
		rowHead1.createCell((int) 8).setCellValue(" Gate ");
	
		for (SummaryReportPojo CDACD:postacknowledgelist) {
			HSSFRow rowData5=sheet.createRow((int)index2);
			//Data Insertion as Ticket Lodged By Department Name
			rowData5.createCell((int) 0).setCellValue(CDACD.getId());
			rowData5.createCell((int) 1).setCellValue(CDACD.getVender_type());
			rowData5.createCell((int) 2).setCellValue(CDACD.getVender_name());
			rowData5.createCell((int) 3).setCellValue(CDACD.getAct_mangr());
			rowData5.createCell((int) 4).setCellValue(CDACD.getMob_number());
			rowData5.createCell((int) 5).setCellValue(CDACD.getDept());
			rowData5.createCell((int) 6).setCellValue(CDACD.getEmpName());
			rowData5.createCell((int) 7).setCellValue(CDACD.getLocation());
			rowData5.createCell((int) 8).setCellValue(CDACD.getGate());
			index2++;
			sheet.autoSizeColumn(index2);
		}
	  }*/
	
	try
	 {
		FileOutputStream out = new FileOutputStream(new File(excelFileName));
		
       wb.write(out);
       out.close();
	 }
	catch(Exception e)
	 {
			e.printStackTrace();
	 }
	return excelFileName;

 }
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;

		// Title Style
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(titleFont);
		style.setWrapText(true);
		styles.put("title", style);

		// Sub Title Style
		Font subTitleFont = wb.createFont();
		subTitleFont.setFontHeightInPoints((short) 14);
		subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont);
		style.setWrapText(true);
		styles.put("subTitle", style);
		
		
		// Sub Title Style for Pending Tickets
		Font subTitleFont_PN = wb.createFont();
		subTitleFont_PN.setFontHeightInPoints((short) 14);
		subTitleFont_PN.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.RED.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_PN);
		style.setWrapText(true);
		styles.put("subTitle_PN", style);
		
		
		// Sub Title Style for Resolved Tickets
		Font subTitleFont_RS = wb.createFont();
		subTitleFont_RS.setFontHeightInPoints((short) 14);
		subTitleFont_RS.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREEN.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_RS);
		style.setWrapText(true);
		styles.put("subTitle_RS", style);
		
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_HP = wb.createFont();
		subTitleFont_HP.setFontHeightInPoints((short) 14);
		subTitleFont_HP.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.ORANGE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_HP);
		style.setWrapText(true);
		styles.put("subTitle_HP", style);
		
		
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_SN = wb.createFont();
		subTitleFont_SN.setFontHeightInPoints((short) 14);
		subTitleFont_SN.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.YELLOW.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_SN);
		style.setWrapText(true);
		styles.put("subTitle_SN", style);
		
		
		
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_IG = wb.createFont();
		subTitleFont_IG.setFontHeightInPoints((short) 14);
		subTitleFont_IG.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.LAVENDER.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_IG);
		style.setWrapText(true);
		styles.put("subTitle_IG", style);
		
		
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_SH = wb.createFont();
		subTitleFont_SH.setFontHeightInPoints((short) 10);
		subTitleFont_SH.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(subTitleFont_SH);
		style.setWrapText(true);
		styles.put("subTitle_SH", style);
		
		

		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);
		return styles;
	}
}
