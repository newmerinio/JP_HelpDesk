package com.Over2Cloud.ctrl.dar.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class DARReportHelper 
{
	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getAllData(SessionFactory connectionSpace) 
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append("WHERE dr.date='"+DateUtil.getCurrentDateUSFormat()+"' AND tr.intiation<='"+DateUtil.getCurrentDateUSFormat()+"' OR tr.status!='Done' GROUP BY tr.id ORDER BY emp2.empName ASC");
			System.out.println("FIRST QUERY ==========    "+query.toString());
			List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
			
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
					
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("static-access")
	public String writeDataInExcel(List<DarSubmissionPojoBean> reportData) 
	{
	    String excelFileName =null;
	    String mergeDateTime=new DateUtil().mergeDateTime();
	    String totalFeedback = "Consolidated Project Achievement Summary Status AS On "+DateUtil.getCurrentDateIndianFormat() ;
	
	    /*List orgData= new LoginImp().getUserInfomation("8", "IN");
		String orgName="";
		
		if (orgData!=null && orgData.size()>0) 
		{
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				orgName=object[0].toString();
			}
		}
	    */
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
		titleCell.setCellValue("DreamSol");
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,14));
		
/*		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue("Daily Dar Report");
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,14));*/
		
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("dar_report")+ "/" + mergeDateTime+".xls";
		int header_first=3;
		int index=4;
 	
 		if(reportData!=null && reportData.size()>0)
		 {
 	    // Header Row
		Row headerRow10 = sheet.createRow(header_first-1);
		headerRow10.setHeightInPoints(18);
		Cell headerTitleCell10 = headerRow10.createCell(0);
		headerTitleCell10.setCellValue("Dar Submission Detail");
		headerTitleCell10.setCellStyle(styles.get("subTitle_RS"));
		sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,14));
		
 		// Code Start for showing the data for current day Resolved Tickets
		HSSFRow rowHead = sheet.createRow((int) header_first);
		rowHead.createCell((int) 0).setCellValue(" Task Name ");
		rowHead.createCell((int) 1).setCellValue(" Task Brief ");
		rowHead.createCell((int) 2).setCellValue(" Allotted By ");
		rowHead.createCell((int) 3).setCellValue(" Dates ");
		rowHead.createCell((int) 4).setCellValue(" Status ");
		rowHead.createCell((int) 5).setCellValue(" Today Activity ");
		rowHead.createCell((int) 6).setCellValue(" Functional Review ");
		rowHead.createCell((int) 7).setCellValue(" Technical Review ");
		rowHead.createCell((int) 8).setCellValue(" Tomorrow's Task ");
		
		  for (DarSubmissionPojoBean CDRD:reportData)
 		   {
 				HSSFRow rowData=sheet.createRow((int)index);
				rowData.createCell((int) 0).setCellValue(CDRD.getTasknameee());
				rowData.createCell((int) 1).setCellValue(CDRD.getSpecificTask());
				rowData.createCell((int) 2).setCellValue(CDRD.getAllotedbyy());
				rowData.createCell((int) 3).setCellValue(CDRD.getInitiondate() +" to "+CDRD.getComlpetiondate());
				rowData.createCell((int) 4).setCellValue(CDRD.getStatuss()+", "+CDRD.getCompercentage());
				rowData.createCell((int) 5).setCellValue(CDRD.getToday());
				rowData.createCell((int) 6).setCellValue(CDRD.getFunctonalDate()+", "+CDRD.getFunctReviewBy()+", "+CDRD.getReviewStatus());
				rowData.createCell((int) 7).setCellValue(CDRD.getTechnicalDate()+", "+CDRD.getTechReviewBy()+", "+CDRD.getReviewStatus());
				rowData.createCell((int) 8).setCellValue(CDRD.getTommorow());
				index++;
 				sheet.autoSizeColumn(index);
			}
		}
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
		style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
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
	public String getConfigMailForReport(List<DarSubmissionPojoBean> reportData) 
	{
		
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Telesolutions Pvt. Ltd.</b></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Consolidated Project Achievement Summary Status As On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table>");
        mailtext.append("<br>");
        if (reportData!=null && reportData.size()>0)
        {
        	   StringBuilder mainBody=new StringBuilder();
        	   String empId=null;
	     	  for(DarSubmissionPojoBean FBP1:reportData)
	       	  {
	     		 if (empId==null || !empId.equalsIgnoreCase(FBP1.getEmpId()))
				 {
	     			empId=FBP1.getEmpId();
	     			mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Task Status For "+FBP1.getAllotedtoo()+"</b></td></tr></tbody></table>");
	         		mainBody.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	         		mainBody.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
	         		for(DarSubmissionPojoBean FBP:reportData)
	             	{
		   	     		if (empId.equalsIgnoreCase(FBP.getEmpId()))
		   				{
		   	     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
		   	     			if (!status)
							{
		   	     				mainBody.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
							}
							else
							{
								mainBody.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
							}
		   				}
	             	}
	        		mainBody.append("</tbody></table>");
	        		mailtext.append(mainBody);
	        		mailtext.append("<br>");
	        		mainBody.setLength(0);
				 }
	          }
        }
        
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
        return mailtext.toString() ; 
}
	@SuppressWarnings("rawtypes")
	public String clientName(String dataFor,String clientId, SessionFactory connectionSpace)
	{
		String data=null;
		try 
		{
			String query1=null;
			List  data1=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			if (dataFor.equalsIgnoreCase("PA") || dataFor.equalsIgnoreCase("EA") ) 
			{
				query1="SELECT associateName FROM associate_basic_data WHERE id="+clientId;
			}
			else if (dataFor.equalsIgnoreCase("PC") || dataFor.equalsIgnoreCase("EC")) 
			{
				query1="SELECT clientName FROM client_basic_data WHERE id="+clientId;
			}
			else if(dataFor.equalsIgnoreCase("IN"))
			{
				query1="SELECT verticalname FROM offeringlevel1 WHERE id="+clientId;
			}
			else if(dataFor.equalsIgnoreCase("N"))
			{
				query1="SELECT orgName FROM client_type WHERE id="+clientId;
			}
			data1=cbt.executeAllSelectQuery(query1, connectionSpace);
			if (data1!=null && data1.size()>0) 
			{
				data=data1.get(0).toString();
			} 
			else 
			{
				data="NA";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public String offeringName(String dataFor,String contactName,String offeringID, SessionFactory connectionSpace)
	{
		String data=null;
		try 
		{
			List  data1=null;
		
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			String offeringDetails[]=getOfferingName(connectionSpace);
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
		    if (dataFor.equalsIgnoreCase("IN"))
			{
				if (offeringDetails[2].equalsIgnoreCase("2")) 
				{
					query.append(" offeringname FROM offeringlevel2 WHERE verticalname='"+contactName+"'");
				}
				else if (offeringDetails[2].equalsIgnoreCase("3")) 
				{
					query.append(" o3.subofferingname FROM offeringlevel3 as o3 INNER JOIN offeringlevel2 AS o2 On o3.offeringname= o2.id INNER JOIN offeringlevel1 AS o1 ON o1.id=o2.verticalname WHERE   o1.id='"+contactName+"' AND o3.id='"+offeringID+"'");
				}
				else if (offeringDetails[2].equalsIgnoreCase("4")) 
				{
					query.append(" o4.variantname FROM offeringlevel4 as o4 INNER JOIN offeringlevel3 AS o3 ON o4.subofferingname=o3.id WHERE  offeringlevel1 AS o1 ON o1.id='"+contactName+"' AND o4.id='"+offeringID+"'");
				}
				else if (offeringDetails[2].equalsIgnoreCase("5")) 
				{
					query.append(" o5.subvariantname FROM offeringlevel5 as o5 INNER JOIN offeringlevel4 AS o4 ON o5.variantname=o4.id WHERE  offeringlevel1 AS o1 ON o1.id='"+contactName+"' AND o5.id='"+offeringID+"' ");
				}
			}
			else 
			{
				query.append(" "+offeringDetails[0]+" FROM "+offeringDetails[1]+" WHERE id='"+offeringID+"' ");
			}
			//System.out.println("QUERY GEING NAMAE OF OFFERING  ::;   "+query.toString());
			data1=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data1!=null && data1.size()>0) 
			{
				data=data1.get(0).toString();
			} 
			else 
			{
				data="NA";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	public String[] getOfferingName(SessionFactory connectionSpace)
	{
		int level = 0;
		String offeringLevel=null;
		String[] oLevels = null;
		offeringLevel=CommonWork.offeringLevelWithName(connectionSpace);
		if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
		{
			oLevels = offeringLevel.split("#");
			level = Integer.parseInt(oLevels[0]);
		}
		String[] myArray = new String[3];
		String tableName = "", colName = "";
		if (level == 1)
		{
			tableName = "offeringlevel1";
			colName = "verticalname";
		}
		if (level == 2)
		{
			tableName = "offeringlevel2";
			colName = "offeringname";
		}
		if (level == 3)
		{
			tableName = "offeringlevel3";
			colName = "subofferingname";
		}
		if (level == 4)
		{
			tableName = "offeringlevel4";
			colName = "variantname";
		}
		if (level == 5)
		{
			tableName = "offeringlevel5";
			colName = "subvariantname";
		}
		myArray[0] = colName;
		myArray[1] = tableName;
		myArray[2] = String.valueOf(level);

		return myArray;
	}

	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getAllTodayReviewData(SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append("WHERE tr.technical_Date='"+DateUtil.getCurrentDateUSFormat()+"' OR tr.functional_Date='"+DateUtil.getCurrentDateUSFormat()+"' ");
			System.out.println("SECOND QUERY ==========    "+query.toString());
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
			
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
					
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public List<DarSubmissionPojoBean> getAllTommorowReviewData(
			SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append("WHERE tr.technical_Date='"+DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat())+"' OR tr.functional_Date='"+DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat())+"' ");
			
			System.out.println("THIRD QUERY ==========    "+query.toString());
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
			
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
					
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public List<DarSubmissionPojoBean> getAllDayAfterReviewData(
			SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append("WHERE tr.technical_Date='"+DateUtil.getNewDate("day", 2, DateUtil.getCurrentDateUSFormat())+"' OR tr.functional_Date='"+DateUtil.getNewDate("day", 2, DateUtil.getCurrentDateUSFormat())+"' ");
			System.out.println("FOUR QUERY ==========    "+query.toString());
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
			
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
					
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public String getConfigMailForReviewReport(
			List<DarSubmissionPojoBean> todayReview,
			List<DarSubmissionPojoBean> tommorowReview,
			List<DarSubmissionPojoBean> dayAfterReview)
	{
		
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Telesolutions Pvt. Ltd.</b></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Functional & Technical Review Status As On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table>");
        if (todayReview!=null && todayReview.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Today's Review</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:todayReview)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        if (tommorowReview!=null && tommorowReview.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Tomorrow's Review</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:tommorowReview)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        if (dayAfterReview!=null && dayAfterReview.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Day After Tomorrow's Review</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:dayAfterReview)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
        return mailtext.toString() ; 
}

	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getAllAlreadyFreeData(
			SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append(" WHERE tr.allotedto IN(SELECT id FROM compliance_contacts WHERE emp_id IN(SELECT emp.id FROM compliance_contacts AS cc ");
			query.append(" INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE cc.moduleName='DAR' AND emp.flag=0  AND emp.id NOT IN(SELECT emp.id FROM employee_basic AS emp ");
			query.append(" LEFT JOIN compliance_contacts AS cc ON cc.emp_id=emp.id inner join  task_registration as task on task.allotedto = cc.id ");
			query.append(" WHERE task.status!='Done' GROUP BY emp.empName) GROUP BY emp.empName)) AND cc1.work_status=0 AND emp1.flag=0 GROUP BY emp2.id ");
			
			System.out.println("FOUR QUERY ==========    "+query.toString());
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
			
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
					
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getAllTodayFreeData(
			SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append(" WHERE tr.completion ='"+DateUtil.getCurrentDateUSFormat()+"' ");
			
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getAllTommorowFreeData(
			SessionFactory connection)
	{
		List<DarSubmissionPojoBean> dataList=new ArrayList<DarSubmissionPojoBean>();
		DarSubmissionPojoBean dsp=null;
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder(); 

			query .append("SELECT emp2.id AS empID,tr.technical_Date,emp4.empName AS tech ,emp5.empName AS func,tr.functional_Date,dr.wStatus,tr.intiation, ");
			query.append(" tr.completion,tr.specifictask,tr.taskname,emp1.empName as guidance,emp2.empName as allotedTo,emp3.empName as allotedBy, dr.tactionStatus, ");
			query.append(" dr.tommorow,dr.compercentage,tr.status FROM task_registration AS tr ");
			query.append(" LEFT JOIN dar_submission AS dr ON tr.id=dr.taskname LEFT JOIN compliance_contacts AS cc1 ON cc1.id=tr.guidance ");
			query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=cc1.emp_id  LEFT JOIN compliance_contacts AS cc2 ON cc2.id=tr.allotedto ");
			query.append(" LEFT JOIN employee_basic AS emp2 ON emp2.id=cc2.emp_id  LEFT JOIN compliance_contacts AS cc3 ON cc3.id=tr.allotedby LEFT JOIN employee_basic AS emp3 ON emp3.id=cc3.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc4 ON cc4.id=tr.validate_By_2  LEFT JOIN employee_basic AS emp4 ON emp4.id=cc4.emp_id ");
			query.append(" LEFT JOIN compliance_contacts AS cc5 ON cc5.id=tr.validate_By_1  LEFT JOIN employee_basic AS emp5 ON emp5.id=cc5.emp_id ");
			query.append(" WHERE tr.completion ='"+DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat())+"' ");
			
			List list=cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if (list!=null && list.size()>0) 
			{
				Object[] object=null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object!=null) 
					{
						dsp =new DarSubmissionPojoBean();
						dsp.setEmpId(getValueWithNullCheck(object[0]));
						dsp.setTechnicalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[1])));
						dsp.setTechReviewBy(getValueWithNullCheck(object[2]));
						dsp.setFunctReviewBy(getValueWithNullCheck(object[3]));
						dsp.setFunctonalDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[4])));
						if (object[5]!=null)
						{
							dsp.setReviewStatus(getValueWithNullCheck(object[5]));
						}
						else
						{
							dsp.setReviewStatus("Pending");
						}
						if (object[13]!=null && object[14]!=null && object[15]!=null)
						{
							dsp.setToday(getValueWithNullCheck(object[13]));
							dsp.setTommorow(getValueWithNullCheck(object[14]));
						}
						else
						{
							dsp.setToday("DAR Awaited");
							dsp.setTommorow("DAR Awaited");
						}
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[6])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[7])));
						dsp.setSpecificTask(getValueWithNullCheck(object[8]));
						dsp.setTasknameee(getValueWithNullCheck(object[9]));
						dsp.setGuidancee(getValueWithNullCheck(object[10]));
						dsp.setAllotedtoo(getValueWithNullCheck(object[11]));
						dsp.setAllotedbyy(getValueWithNullCheck(object[12]));
						
						dsp.setCompercentage(getValueWithNullCheck(object[15]));
						dsp.setStatuss(getValueWithNullCheck(object[16]));
					}
					dataList.add(dsp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public String getConfigMailForFreeResourceReport(
			List<DarSubmissionPojoBean> alreadyFree,
			List<DarSubmissionPojoBean> todayFree,
			List<DarSubmissionPojoBean> tommorowFree)
	{
		
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Telesolutions Pvt. Ltd.</b></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Consolidated Project Allotment Status As On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table>");
        if (alreadyFree!=null && alreadyFree.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Task Brief of Already Free Resources</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:alreadyFree)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        if (todayFree!=null && todayFree.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resources Getting Free Today</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:todayFree)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        if (tommorowFree!=null && tommorowFree.size()>0)
        {
        	mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resources Getting Free Tomorrow</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Brief</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Dates</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today&nbsp;Activity</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Functional&nbsp;Review</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Technical&nbsp;Review</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tomorrow's&nbsp;Task</strong></td> </tr>");
     		for(DarSubmissionPojoBean FBP:tommorowFree)
         	{
     			boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.convertDateToUSFormat(FBP.getComlpetiondate()), DateUtil.getCurrentDateUSFormat());
     			if (!status)
				{
     				mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", On Time</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#FFCCCC'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedtoo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTasknameee()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSpecificTask()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAllotedbyy()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getInitiondate()+" to "+FBP.getComlpetiondate()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatuss()+", "+FBP.getCompercentage()+", Delay</td> <td align='center' width='12%' bgcolor='#9AFE2E' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToday()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFunctReviewBy()+", "+FBP.getFunctonalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTechReviewBy()+", "+FBP.getTechnicalDate()+", "+FBP.getReviewStatus()+"</td><td align='center' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTommorow()+"</td></tr>");
				}
         	}
     		mailtext.append("</tbody></table>");
    		mailtext.append("<br>");
        }
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
        return mailtext.toString() ; 
}
}
