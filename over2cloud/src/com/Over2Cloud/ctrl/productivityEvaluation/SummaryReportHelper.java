package com.Over2Cloud.ctrl.productivityEvaluation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.productivityEvaluation.dashboard.ProductivityDashboardHelper;
import com.Over2Cloud.ctrl.productivityEvaluation.dashboard.ProductivityDashboardPojo;

public class SummaryReportHelper
{

	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getAllSharedSummary(SessionFactory connection)
	{
		List<ProductivityDashboardPojo> sharedSummary=null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			sharedSummary=new ArrayList<ProductivityDashboardPojo>();
			StringBuilder query=new StringBuilder();
			query.append("SELECT kad.moduleName,gi.groupName,dept.deptName,kad.title,kad.brief,kad.upload1, ");
			query.append("kad.upload2,kad.upload3,kad.otherPlant FROM kaizen_add_data AS kad ");
			query.append("LEFT JOIN employee_basic AS emp ON kad.empId=emp.id ");
			query.append("LEFT JOIN department AS dept ON emp.deptname=dept.id ");
			query.append("LEFT JOIN groupinfo AS gi ON emp.groupId=gi.id ");
			query.append("WHERE kad.date='"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY kad.moduleName DESC");
		    
			List data=cbt.executeAllSelectQuery(query.toString(), connection);
		    if (data!=null && data.size()>0)
			{
		    	ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
		    	for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					ProductivityDashboardPojo PD=new ProductivityDashboardPojo();
					Object[] object = (Object[]) iterator.next();
					PD.setModuleName(PDH.getValueWithNullCheck(object[0]));
					PD.setFromOG(PDH.getValueWithNullCheck(object[1]));
					PD.setFromPlant(PDH.getValueWithNullCheck(object[2]));
					PD.setTitle(PDH.getValueWithNullCheck(object[3]));
					PD.setBrief(PDH.getValueWithNullCheck(object[4]));
					PD.setDocuments(getDocumentName(object[5])+", "+getDocumentName(object[6])+", "+getDocumentName(object[7]));
				    if (KH.getOtherMultipleOGDetails(connection, object[8].toString())!=null)
					{
				    	PD.setToOG(KH.getOtherMultipleOGDetails(connection, object[8].toString()));
					} 
				    else
					{
				    	PD.setToOG("NA");
					}
				    if (KH.getotherMultiplePlantNames(connection, object[8].toString())!=null)
					{
						PD.setToPlant(KH.getotherMultiplePlantNames(connection, object[8].toString()));
					}
				    else
				    {
				    	PD.setToPlant("NA");
				    }
				    sharedSummary.add(PD); 
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sharedSummary;
	}

	@SuppressWarnings("rawtypes")
	public List<ProductivityDashboardPojo> getAllActionTakenData(
			SessionFactory connection)
	{
		List<ProductivityDashboardPojo> actionData=null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			actionData=new ArrayList<ProductivityDashboardPojo>();
			StringBuilder query=new StringBuilder();
			query.append("SELECT kad.moduleName,gi.groupName,dept.deptName,kad.status,kad.comment,kad.upload1, ");
			query.append("kad.upload2,kad.upload3,kad.otherPlant FROM kaizen_add_data AS kad ");
			query.append("LEFT JOIN employee_basic AS emp ON kad.empId=emp.id ");
			query.append("LEFT JOIN department AS dept ON emp.deptname=dept.id ");
			query.append("LEFT JOIN groupinfo AS gi ON emp.groupId=gi.id ");
			query.append("WHERE kad.actionDate='"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY kad.moduleName DESC");
			List data=cbt.executeAllSelectQuery(query.toString(), connection);
		    if (data!=null && data.size()>0)
			{
		    	ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
		    	for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					ProductivityDashboardPojo PD=new ProductivityDashboardPojo();
					Object[] object = (Object[]) iterator.next();
					PD.setModuleName(PDH.getValueWithNullCheck(object[0]));
					PD.setFromOG(PDH.getValueWithNullCheck(object[1]));
					PD.setFromPlant(PDH.getValueWithNullCheck(object[2]));
					PD.setStatus(PDH.getValueWithNullCheck(object[3]));
					PD.setComment(PDH.getValueWithNullCheck(object[4]));
					PD.setDocuments(getDocumentName(object[5])+", "+getDocumentName(object[6])+", "+getDocumentName(object[7]));
				    if (KH.getOtherMultipleOGDetails(connection, object[8].toString())!=null)
					{
				    	PD.setToOG(KH.getOtherMultipleOGDetails(connection, object[8].toString()));
					} 
				    else
					{
				    	PD.setToOG("NA");
					}
				    if (KH.getotherMultiplePlantNames(connection, object[8].toString())!=null)
					{
						PD.setToPlant(KH.getotherMultiplePlantNames(connection, object[8].toString()));
					}
				    else
				    {
				    	PD.setToPlant("NA");
				    }
				    actionData.add(PD); 
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return actionData;
	}

	public String getConfigMailForReport(String name,List<ProductivityDashboardPojo> sharedSummaryData,List<ProductivityDashboardPojo> actionTakenData)
	{
		String URL="<a href=http://www.over2cloud.co.in>Login</a>";
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<div align='justify'><font face ='ARIAL' size='2'><h3>Dear "+name+", </h3></FONT></div>");
		//mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>JBM Industries Pvt. Ltd.</b></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Your kind attention is required for following summary activities mapped for your analysis. We request, you to access complete information including the documents & take relevant actions (if any) by "+URL+" with your respective credentials.</td></tr></tbody></table>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Shared Summary Activities as on "+DateUtil.getCurrentDateIndianFormat()+"</b></td></tr></tbody></table>");
       
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Activity Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Uploaded&nbsp;By&nbsp;OG</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Uploaded&nbsp;By&nbsp;Plant</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Title</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td> <td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>&nbsp;&nbsp;&nbsp;Documents&nbsp;&nbsp;&nbsp;</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared&nbsp;with&nbsp;OG</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared&nbsp;with&nbsp;Plant</strong></td> </tr>");
       
        if (sharedSummaryData!=null && sharedSummaryData.size()>0)
        {
       	 int i=0;
     	 for(ProductivityDashboardPojo FBP:sharedSummaryData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getModuleName()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromOG()+"</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromPlant()+"</td> <td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTitle()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getBrief()+"</td> <td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDocuments()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToOG()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToPlant()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getModuleName()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromOG()+"</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromPlant()+"</td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTitle()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getBrief()+"</td><td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDocuments()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToOG()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToPlant()+"</td></tr>");
				}
            }
       	 }
        mailtext.append("</tbody></table>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Action Taken Summary Activities as on "+DateUtil.getCurrentDateIndianFormat()+"</b></td></tr></tbody></table>");
       
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Activity Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Taken&nbsp;By&nbsp;OG</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Taken&nbsp;By&nbsp;Plant</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Status</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Comments</strong></td> <td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>&nbsp;&nbsp;&nbsp;Documents&nbsp;&nbsp;&nbsp;</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared&nbsp;with&nbsp;OG</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared&nbsp;with&nbsp;Plant</strong></td> </tr>");
       
        if (actionTakenData!=null && actionTakenData.size()>0)
        {
       	 int i=0;
     	 for(ProductivityDashboardPojo FBP:actionTakenData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getModuleName()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromOG()+"</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromPlant()+"</td> <td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td> <td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDocuments()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToOG()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToPlant()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getModuleName()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromOG()+"</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFromPlant()+"</td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td><td align='center' width='17%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDocuments()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToOG()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getToPlant()+"</td></tr>");
				}
            }
       	 }
        mailtext.append("</tbody></table>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><b>Thanks !!!</b></FONT>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><b>CMO Team</b></FONT>");
        mailtext.append("<br>-----------------------------------------------------------------------------------<br>");
        mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
        return mailtext.toString() ; 
}

	public String writeDataInExcel(List<ProductivityDashboardPojo> sharedSummaryData,List<ProductivityDashboardPojo> actionTakenData)
	{
	    String excelFileName =null;
	    @SuppressWarnings("static-access")
		String mergeDateTime=new DateUtil().mergeDateTime();
	    String totalFeedback = "Shared Summary Activities as on "+DateUtil.getCurrentDateIndianFormat() ;
	
	  
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
		titleCell.setCellValue("JBM Industries");
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,7));
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("productivity_evaluation_report")+ "/" + mergeDateTime+".xls";
		int header_first=3;
		int index=4;
 		int header_1=header_first+(actionTakenData.size())+3;
 		int index1=header_1+1;
 		
 		if (actionTakenData.size()<=0) 
 		{
			 header_1=3;
	 		 index1=4;
		}
 		else 
 		{
 			 header_1=header_first+(actionTakenData.size())+3;
	 		 index1=header_1+1;
		}
 		
	
 		if(sharedSummaryData!=null && sharedSummaryData.size()>0)
		{
	 	    // Header Row
			Row headerRow10 = sheet.createRow(header_first-1);
			headerRow10.setHeightInPoints(18);
			Cell headerTitleCell10 = headerRow10.createCell(0);
			headerTitleCell10.setCellValue("Shared Summary Activities i.e. "+DateUtil.getCurrentDateIndianFormat());
			headerTitleCell10.setCellStyle(styles.get("subTitle_RS"));
			sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,7));
			
			HSSFRow rowHead = sheet.createRow((int) header_first);
			rowHead.createCell((int) 0).setCellValue(" Activity Name ");
			rowHead.createCell((int) 1).setCellValue(" Uploaded by OG ");
			rowHead.createCell((int) 2).setCellValue(" Uploaded by Plant ");
			rowHead.createCell((int) 3).setCellValue(" Title ");
			rowHead.createCell((int) 4).setCellValue(" Brief ");
			rowHead.createCell((int) 5).setCellValue(" Document ");
			rowHead.createCell((int) 6).setCellValue(" Shared with OG ");
			rowHead.createCell((int) 7).setCellValue(" Shared with Plant ");
			
			  for (ProductivityDashboardPojo CDRD:sharedSummaryData)
	 		  {
	 				HSSFRow rowData=sheet.createRow((int)index);
					rowData.createCell((int) 0).setCellValue(CDRD.getModuleName());
					rowData.createCell((int) 1).setCellValue(CDRD.getFromOG());
					rowData.createCell((int) 2).setCellValue(CDRD.getFromPlant());
					rowData.createCell((int) 3).setCellValue(CDRD.getTitle());
					rowData.createCell((int) 4).setCellValue(CDRD.getBrief());
					rowData.createCell((int) 5).setCellValue(CDRD.getDocuments());
					rowData.createCell((int) 6).setCellValue(CDRD.getToOG());
					rowData.createCell((int) 7).setCellValue(CDRD.getToPlant());
					index++;
	 				sheet.autoSizeColumn(index);
			  }
		 }
		  if (actionTakenData!=null && actionTakenData.size()>0) 
		  {
			    Row headerRow14 = sheet.createRow(header_1-1);
				headerRow14.setHeightInPoints(18);
				Cell headerTitleCell14 = headerRow14.createCell(0);
				headerTitleCell14.setCellValue("Action Taken Summary Activities i.e. "+DateUtil.getCurrentDateIndianFormat());
				headerTitleCell14.setCellStyle(styles.get("subTitle_SN"));
				sheet.addMergedRegion(new CellRangeAddress(header_1-1, header_1-1, 0,7));
		 	
				HSSFRow rowHead = sheet.createRow((int) header_1);
				rowHead.createCell((int) 0).setCellValue(" Activity Name ");
				rowHead.createCell((int) 1).setCellValue(" Uploaded by OG ");
				rowHead.createCell((int) 2).setCellValue(" Uploaded by Plant ");
				rowHead.createCell((int) 3).setCellValue(" Action Status ");
				rowHead.createCell((int) 4).setCellValue(" Comment ");
				rowHead.createCell((int) 5).setCellValue(" Document ");
				rowHead.createCell((int) 6).setCellValue(" Shared with OG ");
				rowHead.createCell((int) 7).setCellValue(" Shared with Plant ");
				
			  for (ProductivityDashboardPojo CDRD:actionTakenData)
	 		   {
	 				HSSFRow rowData=sheet.createRow((int)index1);
	 				rowData.createCell((int) 0).setCellValue(CDRD.getModuleName());
					rowData.createCell((int) 1).setCellValue(CDRD.getFromOG());
					rowData.createCell((int) 2).setCellValue(CDRD.getFromPlant());
					rowData.createCell((int) 3).setCellValue(CDRD.getStatus());
					rowData.createCell((int) 4).setCellValue(CDRD.getComment());
					rowData.createCell((int) 5).setCellValue(CDRD.getDocuments());
					rowData.createCell((int) 6).setCellValue(CDRD.getToOG());
					rowData.createCell((int) 7).setCellValue(CDRD.getToPlant());
					index1++;
	 				sheet.autoSizeColumn(index1);
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
		style.setFillForegroundColor(IndexedColors.GREEN.index);
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
	
	public String getDocumentName(Object doc)
	{
		System.out.println("doc    :::   "+doc);
		String docName = doc!=null ? doc.toString().substring(48, doc.toString().length()) : "NA" ;
		return docName;
	}
}
