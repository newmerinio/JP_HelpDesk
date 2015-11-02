package com.Over2Cloud.ctrl.VAM.download;
import java.io.File; 
import java.io.FileOutputStream; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map; 
 
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.CellStyle; 
import org.apache.poi.ss.usermodel.Font; 
import org.apache.poi.ss.usermodel.Footer; 
import org.apache.poi.ss.usermodel.Header; 
import org.apache.poi.ss.usermodel.IndexedColors; 
import org.apache.poi.ss.usermodel.PrintSetup; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.ss.util.CellRangeAddress; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
 
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean; 
import com.Over2Cloud.CommonClasses.DateUtil; 
import com.Over2Cloud.ctrl.AllCommonModule.ContactCommonPropertyMap; 
import com.Over2Cloud.ctrl.AllCommonModule.CustomerCommonPropertyMap; 
import com.itextpdf.text.BaseColor; 
import com.itextpdf.text.Document; 
import com.itextpdf.text.Element; 
import com.itextpdf.text.PageSize; 
import com.itextpdf.text.Paragraph; 
import com.itextpdf.text.pdf.PdfPCell; 
import com.itextpdf.text.pdf.PdfPTable; 
import com.itextpdf.text.pdf.PdfWriter; 
public class DownloadExcel { 
    /** 
     *  Define Constructure 
     */ 
    public DownloadExcel() { 
    } 
     
    // Define A method for Creating Excel File 
    public String createExcel(String titleString, String sheetString,String subTitleString, List data, String[] headerTitles,String titleKey, String filePath){ 
 
        String file = null; 
        Workbook wb; 
        int col = 0; 
        try{ 
            // check Header Title   
            if (headerTitles != null && headerTitles.length > 0) 
                col = headerTitles.length; 
 
            wb =  (Workbook) new HSSFWorkbook(); 
            // Hear we are getting whole property  
            List<ConfigurationUtilBean> titleMap = new CustomerCommonPropertyMap().getTitles(titleKey); 
            Map<String, CellStyle> styles = createStyles(wb); 
            Sheet sheet = wb.createSheet(sheetString); 
            PrintSetup printSetup = sheet.getPrintSetup(); 
            printSetup.setLandscape(true); 
            sheet.setFitToPage(true); 
            sheet.setHorizontallyCenter(true); 
 
            Header header = sheet.getHeader(); 
            header.setCenter("Center Header"); 
            header.setLeft("Left Header"); 
            header.setRight("Right Footer"); 
            Footer footer = sheet.getFooter(); 
            footer.setCenter("center footer"); 
            footer.setLeft("left footer"); 
            footer.setRight("right footer"); 
 
            // Title Row.... 
            Row titleRow = sheet.createRow(0); 
            titleRow.setHeightInPoints(20); 
            Cell titleCell = titleRow.createCell(0); 
            titleCell.setCellValue(titleString); 
            titleCell.setCellStyle(styles.get("title")); 
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col - 1)); 
 
            // Sub Title Row..... 
            Row subTitleRow = sheet.createRow(1); 
            subTitleRow.setHeightInPoints(18); 
            Cell subTitleCell = subTitleRow.createCell(0); 
            subTitleCell.setCellValue(subTitleString); 
            subTitleCell.setCellStyle(styles.get("subTitle")); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, col - 1)); 
            //  
            Row headerRow = sheet.createRow(2); 
            headerRow.setHeightInPoints(15); 
            Cell headerCell = null; 
            if (headerTitles != null ){ 
                for (ConfigurationUtilBean cell : titleMap) { 
                    int titleIndex=0; 
                    for (int i = 0; i < headerTitles.length; i++) { 
                        if(cell.getKey().equalsIgnoreCase(headerTitles[titleIndex].trim())){ 
                            headerCell = headerRow.createCell(titleIndex);      
                            headerCell.setCellValue(cell.getValue()); 
                            headerCell.setCellStyle(styles.get("header")); 
                                } 
                            titleIndex++; 
                    } 
                } 
                 
            } 
            Row dataRow = null; 
            Cell dataCell = null; 
 
            int rowIndex = 3; 
              /* List Iteration text */ 
              try{ 
               if(data!=null && data.size()>0){ 
                   for(Iterator it=data.iterator(); it.hasNext();){ 
                        Object[] obdata=(Object[])it.next(); 
                         dataRow = sheet.createRow(rowIndex); 
                for (int cellIndex = 0; cellIndex < headerTitles.length; cellIndex++) { 
                    dataCell = dataRow.createCell(cellIndex); 
                 
                    if(obdata[cellIndex]!=null && !obdata[cellIndex].toString().equalsIgnoreCase("")){ 
                        dataCell.setCellValue(obdata[cellIndex].toString()); 
                    } 
                    else{ 
                        dataCell.setCellValue("NA");} 
                   } 
                       
                rowIndex++; 
                   } 
            }}catch (Exception e) { 
                // TODO: handle exception 
            } 
             
 
            for (int titleIndex = 0; titleIndex < headerTitles.length; titleIndex++) 
                sheet.autoSizeColumn(titleIndex); // adjust width of the column 
 
            file = filePath + File.separator + "CustomerReport" 
                    + DateUtil.getCurrentDateIndianFormat() 
                    + (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".xls"; 
 
            if (wb instanceof XSSFWorkbook) 
                file += "x"; 
            FileOutputStream out = new FileOutputStream(file); 
            wb.write(out); 
            out.close(); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
 
         
        } 
        return file; 
 
    } 
     
    // over loaded method for statement Report Doownload. 
    public String createStatementReportExcel(String titleString, String sheetString,String subTitleString, List data, String[] headerTitles,String titleKey, String filePath){ 
 
 
        String file = null; 
        Workbook wb; 
        int col = 0; 
        try{ 
            // check Header Title   
            if (headerTitles != null && headerTitles.length > 0) 
                col = headerTitles.length; 
 
            wb =  (Workbook) new HSSFWorkbook(); 
            // Hear we are getting whole property  
            List<ConfigurationUtilBean> titleMap = new CustomerCommonPropertyMap().getTitles(titleKey); 
            Map<String, CellStyle> styles = createStyles(wb); 
            Sheet sheet = wb.createSheet(sheetString); 
            PrintSetup printSetup = sheet.getPrintSetup(); 
            printSetup.setLandscape(true); 
            sheet.setFitToPage(true); 
            sheet.setHorizontallyCenter(true); 
 
            Header header = sheet.getHeader(); 
            header.setCenter("Center Header"); 
            header.setLeft("Left Header"); 
            header.setRight("Right Footer"); 
            Footer footer = sheet.getFooter(); 
            footer.setCenter("center footer"); 
            footer.setLeft("left footer"); 
            footer.setRight("right footer"); 
 
            // Title Row.... 
            Row titleRow = sheet.createRow(0); 
            titleRow.setHeightInPoints(20); 
            Cell titleCell = titleRow.createCell(0); 
            titleCell.setCellValue(titleString); 
            titleCell.setCellStyle(styles.get("title")); 
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col - 1)); 
 
            // Sub Title Row..... 
            Row subTitleRow = sheet.createRow(1); 
            subTitleRow.setHeightInPoints(18); 
            Cell subTitleCell = subTitleRow.createCell(0); 
            subTitleCell.setCellValue(subTitleString); 
            subTitleCell.setCellStyle(styles.get("subTitle")); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, col - 1)); 
            //  
            Row headerRow = sheet.createRow(2); 
            headerRow.setHeightInPoints(15); 
            Cell headerCell = null; 
            if (headerTitles != null ){ 
                for (ConfigurationUtilBean cell : titleMap) { 
                    int titleIndex=0; 
                    for (int i = 0; i < headerTitles.length; i++) { 
                        if(cell.getKey().equalsIgnoreCase(headerTitles[titleIndex].trim())){ 
                            headerCell = headerRow.createCell(titleIndex);      
                            headerCell.setCellValue(cell.getValue()); 
                            headerCell.setCellStyle(styles.get("header")); 
                                } 
                            titleIndex++; 
                    } 
                } 
                 
            } 
            Row dataRow = null; 
            Cell dataCell = null; 
 
            int rowIndex = 3; 
              /* List Iteration text */ 
              try{ 
               if(data!=null && data.size()>0){ 
                   for(Iterator it=data.iterator(); it.hasNext();){ 
                        Object[] obdata=(Object[])it.next(); 
                         dataRow = sheet.createRow(rowIndex); 
                for (int cellIndex = 0; cellIndex < headerTitles.length; cellIndex++) { 
                    dataCell = dataRow.createCell(cellIndex); 
                 
                    if(obdata[cellIndex]!=null && !obdata[cellIndex].toString().equalsIgnoreCase("")){ 
                        dataCell.setCellValue(obdata[cellIndex].toString()); 
                    } 
                    else{ 
                        dataCell.setCellValue("NA");} 
                   } 
                       
                rowIndex++; 
                   } 
            }}catch (Exception e) { 
                // TODO: handle exception 
            } 
             
 
            for (int titleIndex = 0; titleIndex < headerTitles.length; titleIndex++) 
                sheet.autoSizeColumn(titleIndex); // adjust width of the column 
 
            file = filePath + File.separator + "StatementReport" 
                    + DateUtil.getCurrentDateIndianFormat() 
                    + (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".xls"; 
 
            if (wb instanceof XSSFWorkbook) 
                file += "x"; 
            FileOutputStream out = new FileOutputStream(file); 
            wb.write(out); 
            out.close(); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
 
         
        } 
        return file; 
 
     
         
    } 
    @SuppressWarnings("unchecked") 
    public String createPdf(List data,String[] headerTitles,String titleKey, String filePath) 
    { 
        int col = 0; 
        String fileName=null; 
         PdfPCell cell = null; 
        try { 
             
            if (headerTitles != null && headerTitles.length > 0) 
                col = headerTitles.length; 
         
            List<ConfigurationUtilBean> titleMap = new CustomerCommonPropertyMap().getTitles(titleKey); 
            Document document=new Document(PageSize.A4,20,20,50,50); 
            fileName = filePath + File.separator + "Customer Report" 
            + DateUtil.getCurrentDateIndianFormat() 
            + (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".pdf"; 
             PdfWriter.getInstance(document,new FileOutputStream(fileName)); 
             document.open(); 
             PdfPTable table=new PdfPTable(col); 
             table.setWidthPercentage(100f); 
             float[] widths=new float[col];   
             for(int wx=0;wx<col;wx++) 
                 widths[wx]=100f/col; 
             table.setWidths(widths); 
             table.setHeaderRows(2); 
              
             if (headerTitles != null ){ 
                 for (ConfigurationUtilBean title : titleMap) 
                 {     
                     for (int i = 0; i < headerTitles.length; i++) { 
                          
                        if(title.getKey().equalsIgnoreCase(headerTitles[i].trim())){ 
                         
                        cell=new PdfPCell(new Paragraph(title.getValue())); 
                        cell.setBackgroundColor(new BaseColor(204,204,204)); 
                        cell.setBorderColor(new BaseColor(170,170,170)); 
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
                        table.addCell(cell); 
                        } 
                     
                    } 
                     
                 } 
             } 
                int altr=1; 
                String cellValue="NA"; 
                 try{ 
                   if(data!=null && data.size()>0){ 
                        for(Iterator it=data.iterator(); it.hasNext();){ 
                            Object[] obdata=(Object[])it.next(); 
                            for (int cellIndex = 0; cellIndex < headerTitles.length; cellIndex++) { 
                                if(obdata[cellIndex]!=null && !obdata[cellIndex].toString().equalsIgnoreCase("")){ 
                                    cell=new PdfPCell(new Paragraph(obdata[cellIndex].toString()));} 
                                  else{ 
                                      cell=new PdfPCell(new Paragraph(cellValue)); 
                                    } 
                          
                               if(altr%2==0) 
                               { 
                                   cell.setBackgroundColor(new BaseColor(240,240,240)); 
                                   cell.setBorderColor(new BaseColor(170,170,170));     
                                        
                               }     
                               else 
                               { 
                                   cell.setBackgroundColor(new BaseColor(255,255,255)); 
                                   cell.setBorderColor(new BaseColor(170,170,170));     
                                        
                               } 
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
                             
                             table.addCell(cell); 
                        } 
                           altr++; 
                    } 
                        }}catch (Exception e) { 
                        e.printStackTrace(); 
                        // TODO: handle exception 
                    } 
             document.add(table); 
             document.close(); 
              
             }catch (Exception e) { 
                    e.printStackTrace(); 
                    // TODO: handle exception 
                } 
         
        return fileName; 
} 
     
    // Define A method for Creating Excel File 
    public String createExcelformate(String titleString, String sheetString, String[] headerTitles,String titleKey, String filePath){ 
 
        String file = null; 
        Workbook wb; 
        int col = 0; 
        try{ 
            // check Header Title   
            if (headerTitles != null && headerTitles.length > 0) 
                col = headerTitles.length; 
 
            wb =  (Workbook) new HSSFWorkbook(); 
            // Hear we are getting whole property  
            List<ConfigurationUtilBean> titleMap = new CustomerCommonPropertyMap().getTitles(titleKey); 
            Map<String, CellStyle> styles = createStyles(wb); 
            Sheet sheet = wb.createSheet(sheetString); 
            PrintSetup printSetup = sheet.getPrintSetup(); 
            printSetup.setLandscape(true); 
            sheet.setFitToPage(true); 
            sheet.setHorizontallyCenter(true); 
 
            Header header = sheet.getHeader(); 
            header.setCenter("Center Header"); 
            header.setLeft("Left Header"); 
            header.setRight("Right Footer"); 
            Footer footer = sheet.getFooter(); 
            footer.setCenter("center footer"); 
            footer.setLeft("left footer"); 
            footer.setRight("right footer"); 
 
            // Title Row.... 
            Row titleRow = sheet.createRow(0); 
            titleRow.setHeightInPoints(20); 
            Cell titleCell = titleRow.createCell(0); 
            titleCell.setCellValue(titleString); 
            titleCell.setCellStyle(styles.get("title")); 
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col - 1)); 
 
         
            //  
            Row headerRow = sheet.createRow(1); 
            headerRow.setHeightInPoints(15); 
            Cell headerCell = null; 
            if (headerTitles != null ){ 
                for (ConfigurationUtilBean cell : titleMap) { 
                    int titleIndex=0; 
                    for (int i = 0; i < headerTitles.length; i++) { 
                        if(cell.getKey().equalsIgnoreCase(headerTitles[titleIndex].trim())){ 
                            headerCell = headerRow.createCell(titleIndex);      
                            headerCell.setCellValue(cell.getValue()); 
                            headerCell.setCellStyle(styles.get("header")); 
                                } 
                            titleIndex++; 
                    } 
                } 
                 
            } 
             
            for (int titleIndex = 0; titleIndex < headerTitles.length; titleIndex++) 
                sheet.autoSizeColumn(titleIndex); // adjust width of the column 
 
            file = filePath + File.separator + "ContactReport" 
                    + DateUtil.getCurrentDateIndianFormat() 
                    + (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".xls"; 
 
            if (wb instanceof XSSFWorkbook) 
                file += "x"; 
            FileOutputStream out = new FileOutputStream(file); 
            wb.write(out); 
            out.close(); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
 
         
        } 
        return file; 
 
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
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); 
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
        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index); 
        style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
        style.setFont(subTitleFont); 
        style.setWrapText(true); 
        styles.put("subTitle", style); 
 
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
        style.setAlignment(CellStyle.ALIGN_CENTER); 
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
