package com.Over2Cloud.ctrl.helpdesk.common;

import hibernate.common.HibernateSessionFactory;
 




import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;

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
 

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
 
import org.apache.poi.ss.usermodel.Workbook;
 
 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.CommonClasses.Configuration;
 
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.CommonClasses.IPAddress;

import org.hibernate.exception.SQLGrammarException;
import org.hibernate.internal.SessionFactoryImpl;

import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.CommonClasses.Cryptography;

@SuppressWarnings("serial")
public class HelpdeskUniversalHelper extends ActionSupport{
	//private AtomicInteger  AN=new AtomicInteger();
	private static int  AN=0;
	
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,SessionFactory connection)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		
		// Set the columns name of a table
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
					i++;
			 }
		 }
		else
		 {
				selectTableData.append("* from " +tableName);
		 }
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+"='"+me.getValue()+"' and ");
					else
						selectTableData.append((String)me.getKey()+"='"+me.getValue()+"'");
					size++;
				} 
			}
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if(wherClauseList.size()>0)
				{
					selectTableData.append(" and ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue().toString().replace("[", "(").replace("]", ")")+"'");
					size++;
				} 
			}
		
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
		//	System.out.println("Querry is as "+selectTableData);
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.getCurrentSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept, String deptLevel,String module, String level,String floor_status,String floor, SessionFactory connectionSpace) {
		List empList = new ArrayList();
		//String qry = null;
		StringBuilder query = new StringBuilder();
		try {
			query.append("select distinct emp.id, emp.emp_name, emp.mobile_no, emp.email_id,dept.contact_subtype_name from primary_contact as emp");
			query.append(" inner join manage_contact contacts on contacts.emp_id = emp.id");
			if (deptLevel.equalsIgnoreCase("SD")) {
				String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
				query.append(" inner join roaster_conf roaster on contacts.id = roaster.contact_id");
				query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
				query.append(" inner join contact_sub_type dept on sub_dept.contact_sub_id = dept.id ");
				query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
				query.append(" where shift.shift_name="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.module_name='"+module+"'");
				query.append(" and shift.shift_from<='"+ DateUtil.getCurrentTime()+ "' and shift.shift_to >'"+ DateUtil.getCurrentTime() + "'");
				if (floor_status.equalsIgnoreCase("Y")) {
					query.append(" and roaster.floor='"+floor+"' and dept.id=(select contact_sub_id from subdepartment where id='"+ dept_subDept+ "') ");
				}
				else {
					query.append(" and sub_dept.id='"+ dept_subDept+ "'");
				}
			}
			else {
				query.append(" inner join contact_sub_type dept on contacts.for_contact_sub_type_id = dept.id ");
				query.append(" where contacts.level='"+level+"' and contacts.module_name='"+module+"'");
				query.append(" and dept.id='"+ dept_subDept+ "'");
			}
			empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,SessionFactory connection)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
					i++;
			 }
		 }
		// Here we get the whole data of a table
		else
		 {
				selectTableData.append("* from " +tableName);
		 }
	    
		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"' and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		// Set the values for where clause List
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if (wherClause!=null) {
				 selectTableData.append(" and ");
			 }
			 else
			    {
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClauseList.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
					{
						if(size<wherClauseList.size())
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
						else
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
						size++;
					}
				} 
			}
		
		 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
			 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
			{
     		 selectTableData.append(" "+searchField+" = '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("cn"))
			{
			 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("bw"))
			{
			 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("ne"))
			{
			selectTableData.append(" "+searchField+" <> '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("ew"))
			{
			selectTableData.append(" "+searchField+" like '%"+searchString+"'");
			}
			}
		
		 // Set the value for type of order for getting data in specific order
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.getCurrentSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	

	@SuppressWarnings("unchecked")
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);
		
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(orgName);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue(excelName);
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
		
		// Creating the folder for holding the Excel files with the defined name
		/*if(needToStore)
			excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
		else*/
			excelFileName="Feedback"+ mergeDateTime;
		
		int header_first=2;
		int index=3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		for(int i=0;i<titleList.size();i++)
		{
			rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
		}
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				HSSFRow rowData=sheet.createRow((int)index);
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					if(object[cellIndex]!=null)
					{
						if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
						{
							rowData.createCell((int) cellIndex).setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
						}
						else
						{
							 
						 
								rowData.createCell((int) cellIndex).setCellValue(object[cellIndex].toString());
							 
						}
					}
					else
					{
						rowData.createCell((int) cellIndex).setCellValue("NA");
					}
				}
				sheet.autoSizeColumn(index);
				index++;	
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
 
	
	@SuppressWarnings("unused")
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
 
	
	@SuppressWarnings("unchecked")
	public List getDataForRoasterApply(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,String viewtype,SessionFactory connection)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName+"  as roaster");
					i++;
			 }
		 }
		// Here we get the whole data of a table
		else
		 {
				selectTableData.append("* from "+tableName+" as roaster");
		 }
		selectTableData.append(" inner join manage_contact as comp on comp.id=roaster.contact_id ");
		if (viewtype!=null && !viewtype.equals("") && viewtype.equals("SD")) {
			selectTableData.append(" inner join subdepartment on roaster.dept_subdept=subdepartment.id ");
			selectTableData.append(" inner join contact_sub_type on subdepartment.contact_sub_id=contact_sub_type.id ");
		}
		else if (viewtype!=null && !viewtype.equals("") && viewtype.equals("D")) {
			selectTableData.append(" inner join contact_sub_type on roaster.dept_subdept=contact_sub_type.id ");
		}
		selectTableData.append(" inner join primary_contact on primary_contact.id=comp.emp_id "); 

		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" ='"+me.getValue()+"' and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		// Set the values for where clause List
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if (wherClause!=null) {
				 selectTableData.append(" and ");
			 }
			 else
			    {
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClauseList.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
					{
						if(size<wherClauseList.size())
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
						else
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
						size++;
					}
				} 
			}
		
		 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
			 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
			{
     		 selectTableData.append(" "+searchField+" = '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("cn"))
			{
			 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("bw"))
			{
			 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("ne"))
			{
			selectTableData.append(" "+searchField+" <> '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("ew"))
			{
			selectTableData.append(" "+searchField+" like '%"+searchString+"'");
			}
			}
		
		 // Set the value for type of order for getting data in specific order
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.getCurrentSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	 
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,Session session)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		
		// Set the columns name of a table
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
					i++;
			 }
		 }
		
		// Here we get the whole data of a table
		else
		 {
				selectTableData.append("* from " +tableName);
		 }
	    
		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		// Set the values for where clause List
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if (wherClause!=null) {
				 selectTableData.append(" and ");
			 }
			 else
			    {
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClauseList.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
					{
						if(size<wherClauseList.size())
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
						else
							selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
						size++;
					}
				} 
			}
		
		 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
			 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
			{
     		 selectTableData.append(" "+searchField+" = '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("cn"))
			{
			 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("bw"))
			{
			 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
			}
			else if(searchOper.equalsIgnoreCase("ne"))
			{
			selectTableData.append(" "+searchField+" <> '"+searchString+"'");
			}
			else if(searchOper.equalsIgnoreCase("ew"))
			{
			selectTableData.append(" "+searchField+" like '%"+searchString+"'");
			}
			}
		
		 // Set the value for type of order for getting data in specific order
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
			Transaction transaction = null;  
			try {  
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	@SuppressWarnings("unchecked")
	public List getData(String query, SessionFactory connection)
	  {
		List Data=null;
		Session session=null;
		Transaction transaction = null;  
			try {  
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				Data=session.createSQLQuery(query).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				 {
				    ex.printStackTrace();
					transaction.rollback();
				 } 
			if(Data!=null)
			{
			}
			else
			{
			}
			return Data;
		}
	
	public boolean updateData(String query, SessionFactory connection)
	  {
		boolean flag=false;
		int count;
		Session session=null;
		Transaction transaction = null;  
			try {  
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				count=session.createSQLQuery(query).executeUpdate();  
				if (count>0) {
					flag=true;
				}
				transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return flag;
		}
	
	@SuppressWarnings("unchecked")
	public List getEscalationData(Session session)
	  {
		List Data=null;
		String str = "select id ,ticket_no ,feed_by ,deptHierarchy ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time from feedback_status where status='Snooze' and sn_upto_date<='"+DateUtil.getCurrentDateUSFormat()+"'";
		Transaction transaction = null;  
			try {  
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(str).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	@SuppressWarnings("unchecked")
	public List getTransferTicketData(String id, SessionFactory connection)
	  {
		List Data=null;
		String str = "select feed_by ,feed_by_mobno ,feed_by_emailid ,by_dept_subdept ,feed_brief,location,ticket_no,to_dept_subdept from feedback_status where id='"+id+"'";
		Session session=null;
		Transaction transaction = null;  
			try {  
				    session=connection.getCurrentSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(str).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	@SuppressWarnings("unchecked")
	public int getDataCountFromTable(String tableName,Map<String, Object>wherClause, Map<String, List> wherClauseList,SessionFactory connection)
	  {
		int countSize =0;
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  

		selectTableData.append("select count(*) from " +tableName);
	    
		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		// Set the values for where clause
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if (wherClause!=null) {
				 selectTableData.append(" and ");
			 }
			 else
			    {
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClauseList.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClauseList.size())
						selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
					else
						selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
					size++;
				} 
			}
			selectTableData.append(";");
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.getCurrentSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			if (Data!=null && Data.size()>0) {
				countSize  = Integer.parseInt(Data.get(0).toString());
			}
			return countSize;
		}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColomn(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock,SessionFactory connection)
	 {
		boolean Data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		
		selectTableData.append("update " + tableName+" set ");
		int size=1;
		Set set =parameterClause.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<parameterClause.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		
		if(condtnBlock.size()>0)
		{
			selectTableData.append(" where ");
		}
		size=1;
		set =condtnBlock.entrySet(); 
		i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<condtnBlock.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' ");
			size++;
		} 
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.getCurrentSession();
				transaction = session.beginTransaction();  
				int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
				if(count>0)
					Data=true;
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		return Data;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColomnWithSession(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock)
	 {
		boolean Data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		
		selectTableData.append("update " + tableName+" set ");
		int size=1;
		Set set =parameterClause.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<parameterClause.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		
		if(condtnBlock.size()>0)
		{
			selectTableData.append(" where ");
		}
		size=1;
		set =condtnBlock.entrySet(); 
		i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<condtnBlock.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		selectTableData.append(";");
		
		Transaction transaction = null;  
		try {  
			    Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();  
				int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
				if(count>0)
					Data=true;
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		return Data;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackValues(List feedValue, String deptLevel, String feedStatus)
	  {
		

		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if(feedValue!=null && feedValue.size()>0) 
		 {
			for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
					Object[] obdata = (Object[]) iterator.next();
					 FeedbackPojo  fbp = new FeedbackPojo();
					 fbp.setId((Integer)obdata[0]);
					 fbp.setTicket_no(obdata[1].toString());
					 fbp.setFeedback_by_dept(obdata[2].toString());
					
					if (obdata[3]!=null && !obdata[3].toString().equals("")) {
						fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
					}
					else {
						 fbp.setFeed_by("NA");
					}
					 
					if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
							fbp.setFeedback_by_mobno(obdata[4].toString());
				    }
					else {
							 fbp.setFeedback_by_mobno("NA");
					}
					
					if (obdata[5]!=null && !obdata[5].toString().equals("")) {
						fbp.setFeedback_by_emailid(obdata[5].toString());
			        }
				    else {
				    	fbp.setFeedback_by_emailid("NA");
				    }
				 
					fbp.setFeedback_to_dept(obdata[6].toString());
					fbp.setFeedback_to_subdept(obdata[7].toString());
					fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[8].toString()));
					fbp.setFeedtype(obdata[9].toString());
					fbp.setFeedback_catg(obdata[10].toString());
					fbp.setFeedback_subcatg(obdata[11].toString());
					if (obdata[12]!=null && !obdata[12].toString().equals("")) {
						fbp.setFeed_brief(obdata[12].toString());
			        }
				    else {
				    	fbp.setFeed_brief("NA");
				    }
					
					
					
					if (obdata[13]!=null && !obdata[13].toString().equals("")) {
						fbp.setLocation(obdata[13].toString());
			        }
				    else {
				    	fbp.setLocation("NA");
				    }
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
					fbp.setOpen_time(obdata[15].toString().substring(0, 5));
					
					fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
				  
					fbp.setLevel(obdata[18].toString());
					fbp.setStatus(obdata[19].toString());
					if (obdata[20]!=null && !obdata[20].toString().equals("")) {
						fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
					}
					else {
						fbp.setVia_from("NA");
					}
					
					fbp.setFeed_registerby(DateUtil.makeTitle(obdata[21].toString()));
					
					 if (feedStatus.equalsIgnoreCase("High Priority")) {
						 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[22].toString()));
						 fbp.setHp_time(obdata[23].toString().substring(0, 5));
						 fbp.setHp_reason(obdata[24].toString());
						}
					
					 if (feedStatus.equalsIgnoreCase("Snooze")) {
						 if (obdata[25]!=null && !obdata[25].toString().equals("")) {
							 fbp.setSn_reason(obdata[25].toString());
						}
						 else {
							 fbp.setSn_reason("NA");
						}
						 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
							 fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(obdata[26].toString()));
						}
						 else {
							 fbp.setSn_on_date("NA");
						}
						 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
							 fbp.setSn_on_time(obdata[27].toString().toString().substring(0, 5));
						}
						 else {
							 fbp.setSn_on_time("NA");
						}
						 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
							 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[28].toString()));
						}
						 else {
							 fbp.setSn_upto_date("NA");
						}
						 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
							 fbp.setSn_upto_time(obdata[29].toString().substring(0, 5));
						}
						 else {
							 fbp.setSn_upto_time("NA");
						} 
						 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
							 fbp.setSn_duration(obdata[30].toString());
						}
						 else {
							 fbp.setSn_duration("NA");
						}  
						}
					 
						 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
							 fbp.setIg_date(obdata[31].toString());
						 }
						 else {
							 fbp.setIg_date("NA");
						 } 
						 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
							 fbp.setIg_time(obdata[32].toString());
						 }
						 else {
							 fbp.setIg_time("NA");
						 } 
						 
						 if (obdata[33]!=null && !obdata[33].toString().equals("")) {
							 fbp.setIg_reason(obdata[33].toString());
						 }
						 else {
							 fbp.setIg_reason("NA");
						 } 
						 
						 if (obdata[34]!=null && !obdata[34].toString().equals("")) {
							 fbp.setTransfer_date(obdata[34].toString());
						 }
						 else {
							 fbp.setTransfer_date("NA");
						 } 
						 
						 if (obdata[35]!=null && !obdata[35].toString().equals("")) {
							 fbp.setTransfer_time(obdata[35].toString());
						 }
						 else {
							 fbp.setTransfer_time("NA");
						 } 
						
						 if (obdata[36]!=null && !obdata[36].toString().equals("")) {
							 fbp.setTransfer_reason(obdata[36].toString());
						 }
						 else {
							 fbp.setTransfer_reason("NA");
						 } 
						
						 if (obdata[37]!=null && !obdata[37].toString().equals("")) {
							 fbp.setAction_by(DateUtil.makeTitle(obdata[37].toString()));
						 }
						 else {
							 fbp.setAction_by("NA");
						 } 
						
						 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
							 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 10))+" / "+ obdata[38].toString().substring(11, 16));
						 }
						 else {
							 fbp.setAction_by("NA");
						 } 
						 
					 if (feedStatus.equalsIgnoreCase("Resolved")) {
						 if (obdata[39]!=null && !obdata[39].toString().equals("")) {
							 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[39].toString()));
						 }
						 else {
							 fbp.setResolve_date("NA");
						}
						if (obdata[40]!=null && !obdata[40].toString().equals("")) {
							 fbp.setResolve_time(obdata[40].toString().substring(0, 5));
							}
						else {
								 fbp.setResolve_time("NA");
							 }
						
						 if (obdata[41]!=null && !obdata[41].equals("")) {
							 fbp.setResolve_duration(obdata[41].toString());
							  }
						 else {
								   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[40]!=null && !obdata[40].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("")) {
									   String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[41].toString(), obdata[42].toString());
									   fbp.setResolve_duration(dura1);
								   }
								   else {
									   fbp.setResolve_duration("NA");
								   }
								   fbp.setResolve_duration("NA");
							 }
						 
						 if (obdata[41]!=null && !obdata[42].toString().equals("")) {
							 fbp.setResolve_remark(obdata[42].toString());
						}
						 else {
							 fbp.setResolve_remark("NA");
						}
						 fbp.setResolve_by(DateUtil.makeTitle(obdata[43].toString()));
						 fbp.setSpare_used(obdata[44].toString());
						}
					feedList.add(fbp);
				}
		}
		return feedList;
	 
		/*
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if(feedValue!=null && feedValue.size()>0) 
		 {
			for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
					Object[] obdata = (Object[]) iterator.next();
					 FeedbackPojo  fbp = new FeedbackPojo();
					 fbp.setId((Integer)obdata[0]);
					 fbp.setTicket_no(obdata[1].toString());
					 fbp.setFeedback_by_dept(obdata[2].toString());
					
					if (obdata[3]!=null && !obdata[3].toString().equals("")) {
						fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
					}
					else {
						 fbp.setFeed_by("NA");
					}
					 
					if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
							fbp.setFeedback_by_mobno(obdata[4].toString());
				    }
					else {
							 fbp.setFeedback_by_mobno("NA");
					}
					
					if (obdata[5]!=null && !obdata[5].toString().equals("")) {
						fbp.setFeedback_by_emailid(obdata[5].toString());
			        }
				    else {
				    	fbp.setFeedback_by_emailid("NA");
				    }
				 
					fbp.setFeedback_to_dept(obdata[6].toString());
					fbp.setFeedback_to_subdept(obdata[7].toString());
					fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[8].toString()));
					fbp.setFeedtype(obdata[9].toString());
					fbp.setFeedback_catg(obdata[10].toString());
					fbp.setFeedback_subcatg(obdata[11].toString());
					if (obdata[12]!=null && !obdata[12].toString().equals("")) {
						fbp.setFeed_brief(obdata[12].toString());
			        }
				    else {
				    	fbp.setFeed_brief("NA");
				    }
					
					
					
					if (obdata[13]!=null && !obdata[13].toString().equals("")) {
						fbp.setLocation(obdata[13].toString());
			        }
				    else {
				    	fbp.setLocation("NA");
				    }
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
					fbp.setOpen_time(obdata[15].toString().substring(0, 5));
					
					
					if (feedStatus.equalsIgnoreCase("Pending") || feedStatus.equalsIgnoreCase("High Priority")) {
						fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
					}
					
				  
					fbp.setLevel(obdata[18].toString());
					fbp.setStatus(obdata[19].toString());
					if (obdata[20]!=null && !obdata[20].toString().equals("")) {
						fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
					}
					else {
						fbp.setVia_from("NA");
					}
					
					fbp.setFeed_registerby(DateUtil.makeTitle(obdata[21].toString()));
					
					 if (feedStatus.equalsIgnoreCase("High Priority")) {
						 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[22].toString()));
						 fbp.setHp_time(obdata[23].toString().substring(0, 5));
						 fbp.setHp_reason(obdata[24].toString());
						}
					
					 if (feedStatus.equalsIgnoreCase("Snooze")) {
						 if (obdata[25]!=null && !obdata[25].toString().equals("")) {
							 fbp.setSn_reason(obdata[25].toString());
						}
						 else {
							 fbp.setSn_reason("NA");
						}
						 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
							 fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(obdata[26].toString()));
						}
						 else {
							 fbp.setSn_on_date("NA");
						}
						 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
							 fbp.setSn_on_time(obdata[27].toString().toString().substring(0, 5));
						}
						 else {
							 fbp.setSn_on_time("NA");
						}
						 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
							 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[28].toString()));
						}
						 else {
							 fbp.setSn_upto_date("NA");
						}
						 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
							 fbp.setSn_upto_time(obdata[29].toString().substring(0, 5));
						}
						 else {
							 fbp.setSn_upto_time("NA");
						} 
						 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
							 fbp.setSn_duration(obdata[30].toString());
						}
						 else {
							 fbp.setSn_duration("NA");
						}  
						}
					 
						 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
							 fbp.setIg_date(obdata[31].toString());
						 }
						 else {
							 fbp.setIg_date("NA");
						 } 
						 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
							 fbp.setIg_time(obdata[32].toString());
						 }
						 else {
							 fbp.setIg_time("NA");
						 } 
						 
						 if (obdata[33]!=null && !obdata[33].toString().equals("")) {
							 fbp.setIg_reason(obdata[33].toString());
						 }
						 else {
							 fbp.setIg_reason("NA");
						 } 
						 
						 if (obdata[34]!=null && !obdata[34].toString().equals("")) {
							 fbp.setTransfer_date(obdata[34].toString());
						 }
						 else {
							 fbp.setTransfer_date("NA");
						 } 
						 
						 if (obdata[35]!=null && !obdata[35].toString().equals("")) {
							 fbp.setTransfer_time(obdata[35].toString());
						 }
						 else {
							 fbp.setTransfer_time("NA");
						 } 
						
						 if (obdata[36]!=null && !obdata[36].toString().equals("")) {
							 fbp.setTransfer_reason(obdata[36].toString());
						 }
						 else {
							 fbp.setTransfer_reason("NA");
						 } 
						
						 if (obdata[37]!=null && !obdata[37].toString().equals("")) {
							 fbp.setAction_by(DateUtil.makeTitle(obdata[37].toString()));
						 }
						 else {
							 fbp.setAction_by("NA");
						 } 
						
						 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
							 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 10))+" / "+ obdata[38].toString().substring(11, 16));
						 }
						 else {
							 fbp.setAction_by("NA");
						 } 
						 
					
					 if (feedStatus.equalsIgnoreCase("Resolved")) {
						 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
							 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[38].toString()));
						 }
						 else {
							 fbp.setResolve_date("NA");
						}
						if (obdata[39]!=null && !obdata[39].toString().equals("")) {
							 fbp.setResolve_time(obdata[39].toString().substring(0, 5));
							}
						else {
								 fbp.setResolve_time("NA");
							 }
						
						 if (obdata[40]!=null && !obdata[40].equals("")) {
							 fbp.setResolve_duration(obdata[40].toString());
							  }
						 else {
								   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("") && obdata[42]!=null && !obdata[42].toString().equals("")) {
									   String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[41].toString(), obdata[42].toString());
									   fbp.setResolve_duration(dura1);
								   }
								   else {
									   fbp.setResolve_duration("NA");
								   }
								   fbp.setResolve_duration("NA");
							 }
						 
						 if (obdata[41]!=null && !obdata[42].toString().equals("")) {
							 fbp.setResolve_remark(obdata[42].toString());
						}
						 else {
							 fbp.setResolve_remark("NA");
						}
						 fbp.setResolve_by(DateUtil.makeTitle(obdata[43].toString()));
						 fbp.setSpare_used(obdata[44].toString());
						}
					feedList.add(fbp);
				}
		}
		return feedList;
	 */}
	
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setCategoryDetail(List feedValue)
	 {
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if (feedValue!=null && feedValue.size()>0) {
				for (Iterator iterator = feedValue.iterator(); iterator
						.hasNext();) {
					Object[] obdata = (Object[]) iterator.next();
					FeedbackPojo  fbp = new FeedbackPojo();
					 fbp.setId((Integer)obdata[0]);
					 fbp.setTicket_no(obdata[1].toString());
					 fbp.setFeedback_by_dept(obdata[2].toString());
					 if (obdata[3]!=null && !obdata[3].toString().equals("")) {
						fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
					 }
					 else {
						 fbp.setFeed_by("NA");
					 }
					 
					if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
							fbp.setFeedback_by_mobno(obdata[4].toString());
				    }
					else {
							 fbp.setFeedback_by_mobno("NA");
					}
					
					if (obdata[5]!=null && !obdata[5].toString().equals("")) {
						fbp.setFeedback_by_emailid(obdata[5].toString());
			        }
				    else {
				    	fbp.setFeedback_by_emailid("NA");
				    }
				 
					fbp.setFeedback_to_dept(obdata[6].toString());
					fbp.setFeedback_to_subdept(obdata[7].toString());
					fbp.setFeedback_allot_to(obdata[8].toString());
					fbp.setFeedtype(obdata[9].toString());
					fbp.setFeedback_catg(obdata[10].toString());
					fbp.setFeedback_subcatg(obdata[11].toString());
					if (obdata[12]!=null && !obdata[12].toString().equals("")) {
						fbp.setFeed_brief(obdata[12].toString());
			        }
				    else {
				    	fbp.setFeed_brief("NA");
				    }
					
					if (obdata[13]!=null && !obdata[13].toString().equals("")) {
						fbp.setLocation(obdata[13].toString());
			        }
				    else {
				    	fbp.setLocation("NA");
				    }
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
					fbp.setOpen_time(obdata[15].toString().substring(0, 5));
						fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString()));
						if (obdata[17].toString()!=null && !obdata[17].toString().equals("") && !obdata[17].toString().equals("NA")) {
							fbp.setEscalation_time(obdata[17].toString().substring(0, 5));
						}
						else {
							fbp.setEscalation_time("NA");
						}
				  
					fbp.setLevel(obdata[18].toString());
					fbp.setStatus(obdata[19].toString());
					if (obdata[20]!=null && !obdata[20].toString().equals("")) {
						fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
					}
					else {
						fbp.setVia_from("NA");
					}
					
					if (obdata[38]!=null && !obdata[38].toString().equals("")) {
						fbp.setFeedaddressing_time(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 11)+" "+obdata[38].toString().substring(11, 16)));
			        }
				    else {
				    	fbp.setFeedaddressing_time("NA");
				    }
					
					fbp.setFeed_registerby(DateUtil.makeTitle(obdata[21].toString()));
					
					feedList.add(fbp);
				}
		}
		return feedList;
	 }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setAnalyticalReportValues(List TotalTicket,List onTime,List offTime,List snooze,List missed,List ignore, String reportFor)
	 {
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if (TotalTicket!=null && TotalTicket.size()>0) 
		{
			Object[] obdata=null;
			for (Iterator iterator = TotalTicket.iterator(); iterator.hasNext();) 
			{
				 obdata = (Object[]) iterator.next();
				 FeedbackPojo  fbp = new FeedbackPojo();
				 if (reportFor.equalsIgnoreCase("Employee"))
				 {
					 fbp.setEmpId(obdata[0].toString());
					 fbp.setEmpName(obdata[1].toString());
					 fbp.setFeedback_by_subdept(obdata[3].toString());
				 }
				 else if(reportFor.equalsIgnoreCase("Category"))
				 {
					 fbp.setFeedId(obdata[0].toString());
					 fbp.setFeedback_catg(obdata[1].toString());
					 fbp.setFeedback_subcatg(obdata[3].toString());
				 }
				 fbp.setCounter(obdata[2].toString());
				 if (onTime!=null && onTime.size()>0)
				 {
					 Object[]  obdata1=null;
					 for (Iterator iterator2 = onTime.iterator(); iterator2.hasNext();)
					 {
						 obdata1 = (Object[]) iterator2.next();
						if (obdata1!=null && !obdata.equals(""))
						{
							 if (obdata[0].toString().equalsIgnoreCase(obdata1[0].toString()))
							 {
								fbp.setOnTime(obdata1[2].toString());
								int perOnTime = (Integer.parseInt(obdata1[2].toString())*100)/Integer.parseInt(obdata[2].toString());
								fbp.setPerOnTime(String.valueOf(perOnTime));
							 }
						 }
					 }
				 }
				 else
				 {
					fbp.setOnTime("0");
				 }
				 if (offTime!=null && offTime.size()>0)
				 {
					 Object[] obdata2=null;
					 for (Iterator iterator3 = offTime.iterator(); iterator3.hasNext();)
					 {
						obdata2 = (Object[]) iterator3.next();
						if (obdata2!=null && !obdata.equals(""))
						{
							 if (obdata[0].toString().equalsIgnoreCase(obdata2[0].toString()))
							 {
								fbp.setOffTime(obdata2[2].toString());
								int perOffTime = (Integer.parseInt(obdata2[2].toString())*100)/Integer.parseInt(obdata[2].toString());
								fbp.setPerOffTime(String.valueOf(perOffTime));
							 }
						}
					 }
				 }
				 else
				 {
					fbp.setOffTime("0");
				 }
				 if (missed!=null && missed.size()>0)
				 {
					 Object[] obdata3=null;
					 for (Iterator iterator4 = missed.iterator(); iterator4.hasNext();)
					 {
						 obdata3= (Object[]) iterator4.next();
						if (obdata3!=null && !obdata.equals(""))
						{
							 if (obdata[0].toString().equalsIgnoreCase(obdata3[0].toString()))
							 {
								fbp.setMissed(obdata3[2].toString());
								int perMissed = (Integer.parseInt(obdata3[2].toString())*100)/Integer.parseInt(obdata[2].toString());
								fbp.setPerMissed(String.valueOf(perMissed));
							 }
						}
					 }
				 }
				 else
				 {
					fbp.setMissed("0");
				 }
				 if (snooze!=null && snooze.size()>0)
				 {
					 Object[] obdata4=null;
					 for (Iterator iterator5 = snooze.iterator(); iterator5.hasNext();)
					 {
						 obdata4 = (Object[]) iterator5.next();
						if (obdata4!=null && !obdata.equals(""))
						{
							 if (obdata[0].toString().equalsIgnoreCase(obdata4[0].toString()))
							 {
							    fbp.setSnooze(obdata4[2].toString());
							 }
						}
					 }
				 }
				 else
				 {
					fbp.setSnooze("0");
				 }
				 if (ignore!=null && ignore.size()>0)
				 {
					 Object[] ob=null;
					for (Iterator iterator2 = ignore.iterator(); iterator2.hasNext();)
					{
						ob = (Object[]) iterator2.next();
						if (ob!=null && !obdata.equals(""))
						{
							 if (obdata[0].toString().equalsIgnoreCase(ob[0].toString()))
							 {
							    fbp.setIgnore(ob[2].toString());
							 }
						}
					}
				 }
				 else
				 {
					 fbp.setIgnore("0");
				 }
				 feedList.add(fbp);
			}
		}
		return feedList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String ticketno, String deptLevel,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	if (deptLevel.equals("SD")) {
            		query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            		query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
            		query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            		query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            		query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
            		query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
            		query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            		query.append("emp.mobOne as allotto_mobno,emp.emailIdOne  as allotto_emailid,dept1.deptName as bydept,dept2.deptName as todept,subdept2.subdeptname as tosubdept,feedback.id as feedid");
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark,feedback.spare_used, emp1.empName as resolve_by, emp1.mobOne as resolve_by_mobno, emp1.emailIdOne as resolve_by_emailid");
					}
            		query.append(" ,feedback.Addr_date_time");
            		query.append(" from feedback_status as feedback");
            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id ");
            		query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
					}
            		if (id!=null && !id.equals("") && status!=null && !status.equals("") && !status.equals("Re-Assign")) {
            			query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
					}
            		else if (id!=null && !id.equals("") && status!=null && !status.equals("") && status.equals("Re-Assign")) {
            			query.append(" where feedback.id='"+id+"' ");	
					}
            		else {
            		query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
					}
            	}
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getFeedbackDetail4ReportDownload(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel,String pageType, SessionFactory connectionSpace)
     {
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
        		query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
        		query.append("subcatg.subCategoryName,feedback.feed_brief,feedback.Addr_date_time,feedback.location,feedback.open_date,");
        		query.append("feedback.open_time,feedback.escalation_date,feedback.escalation_time,feedback.level,feedback.status,feedback.via_from,feedback.hp_date,");
        		query.append("feedback.hp_time,feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,");
        		query.append("feedback.sn_duration,feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,");
        		query.append("feedback.action_by,emp.mobOne,emp.emailIdOne,dept1.deptName as bydept,dept2.deptName as todept,subdept2.subdeptname as tosubdept,feedback.non_working_time");
        		if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("All")) {
        		    query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
	        	if (pageType!=null && !pageType.equalsIgnoreCase("") && pageType.equalsIgnoreCase("H")) {
	        		query.append(" from feedback_status_15072014 as feedback");
				}
	        	else {
	        		query.append(" from feedback_status as feedback");		
				}
	        
        		if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Snooze") || status.equalsIgnoreCase("High Priority") || status.equalsIgnoreCase("Ignore")) {
	        		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	        		query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
	        		query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
	        		query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
	        		query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
	        		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
	        		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
	        		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		if (status.equalsIgnoreCase("Resolved")) {
            		    query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
					}
        		}
        		else if (status.equalsIgnoreCase("All")) {
        			query.append(" left join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" left join department dept1 on feedback.by_dept_subdept= dept1.id");
            		query.append(" left join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            		query.append(" left join department dept2 on subdept2.deptid= dept2.id");
            		query.append(" left join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            		query.append(" left join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" left join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		query.append(" left join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
        		}
        		
        		query.append(" where ");
        		
        		
        		if (by_dept!=null && !by_dept.equals("") && !by_dept.equals("-1")) {
        			query.append(" dept1.id='"+by_dept+"'");	
				}
        		
        		if (to_dept!=null && !to_dept.equals("") && !to_dept.equals("-1") && !by_dept.equals("-1")) {
        			query.append(" and dept2.id='"+to_dept+"'");	
				}
        		else if (to_dept!=null && !to_dept.equals("-1") && by_dept.equals("-1")) {
        			query.append(" dept2.id='"+to_dept+"'");	
				}
        		
        		if (tosubdept!=null && !tosubdept.equals("-1")) {
        			query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
				}
        		
        		if (feedType!=null  && !feedType.equals("-1")) {
        			query.append(" and feedtype.id='"+feedType+"'");	
				}
        		
        		if (categoryId!=null && !categoryId.equals("-1")) {
        			query.append(" and catg.id='"+categoryId+"'");	
				}
        		
        		if (subCategory!=null && !subCategory.equals("-1")) {
        			query.append(" and subcatg.id='"+subCategory+"'");	
				}
        		
        		if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
        			query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
				}
        		else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
        			query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
				}
        		
        		/*if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("")) {
        			query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
				}
        		else if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && (from_date==null || from_date.equals("") || to_date==null || to_date.equals(""))) {
        			query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
				}*/
        		
        		if (status!=null && !status.equals("") && (status.equals("Pending") || status.equals("Snooze") || status.equals("High Priority") || status.equals("Resolved")  || status.equals("Ignore"))) {
        			query.append(" and feedback.status='"+status+"' ");	
				}
        		
        		query.append(" and feedback.moduleName='HDM' ");	
            	
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
                
                if (list!=null && list.size()>0) {
            		int i=1;
            		if (list!=null && list.size()>0) {
            			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            				Object[] object = (Object[]) iterator.next();
            				FeedbackPojo fp =new FeedbackPojo();
            				fp.setId(i);
            				if (object[0]==null || object[0].toString().equals("")) {
            					fp.setTicket_no("NA");
            				}
            				else {
            					fp.setTicket_no(object[0].toString());
            				}
            				
            				if (object[1]==null || object[1].toString().equals("")) {
            					fp.setFeed_by("NA");
            				}
            				else {
            					fp.setFeed_by(DateUtil.makeTitle(object[1].toString()));
            				}
            				
            				if (object[2]==null || object[2].toString().equals("")) {
            					fp.setFeedback_by_mobno("NA");
            				}
            				else {
            					fp.setFeedback_by_mobno(object[2].toString());
            				}
            				
            				if (object[3]==null || object[3].toString().equals("")) {
            					fp.setFeedback_by_emailid("NA");
            				}
            				else {
            					fp.setFeedback_by_emailid(object[3].toString());
            				}
            				
            				if (object[4]==null || object[4].toString().equals("")) {
            					fp.setFeedback_allot_to("NA");
            				}
            				else {
            					fp.setFeedback_allot_to(DateUtil.makeTitle(object[4].toString()));
            				}
            				
            				if (object[5]==null || object[5].toString().equals("")) {
            					fp.setFeedtype("NA");
            				}
            				else {
            					fp.setFeedtype(DateUtil.makeTitle(object[5].toString()));
            				}
            				
            				if (object[6]==null || object[6].toString().equals("")) {
            					fp.setFeedback_catg("NA");
            				}
            				else {
            					fp.setFeedback_catg(object[6].toString());
            				}
            				
            				if (object[7]==null || object[7].toString().equals("")) {
            					fp.setFeedback_subcatg("NA");
            				}
            				else {
            					fp.setFeedback_subcatg(object[7].toString());
            				}
            				
            				if (object[8]==null || object[8].toString().equals("")) {
            					fp.setFeed_brief("NA");
            				}
            				else {
            					fp.setFeed_brief(object[8].toString());
            				}
            				
            				if (object[9]!=null && !object[9].toString().equals("")) {
            					fp.setAddressingTime(DateUtil.convertDateToIndianFormat(object[9].toString().substring(0, 10))+"/"+object[9].toString().substring(11, 16));
            				}
            				else {
            					fp.setAddressingTime("NA");
            				}
            				
            				if (object[10]==null || object[10].toString().equals("")) {
            					fp.setLocation("NA");
            				}
            				else {
            					fp.setLocation(object[10].toString());
            				}
            				
            				if (object[11]==null || object[11].toString().equals("")) {
            					fp.setOpen_date("NA");
            				}
            				else {
            					fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[11].toString()));
            				}
            				
            				if (object[12]==null || object[12].toString().equals("")) {
            					fp.setOpen_time("NA");
            				}
            				else {
            					fp.setOpen_time(object[12].toString().substring(0, 5));
            				}
            				
            				if (object[13]==null || object[13].toString().equals("")) {
            					fp.setEscalation_date("NA");
            				}
            				else {
            					fp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[13].toString()));
            				}
            				
            				
            				if (object[14]==null || object[14].toString().equals("")) {
            					fp.setEscalation_time("NA");
            				}
            				else if(object[14].toString().length()>5){
            					fp.setEscalation_time(object[14].toString().substring(0, 5));
            				}else {
            					object[14].toString();
            				}
            				
            				if (object[15]==null || object[15].toString().equals("")) {
            					fp.setLevel("NA");
            				}
            				else {
            					fp.setLevel(object[15].toString());
            				}
            				
            				if (object[16]==null || object[16].toString().equals("")) {
            					fp.setStatus("NA");
            				}
            				else {
            					fp.setStatus(object[16].toString());
            				}
            				
            				if (object[17]==null || object[17].toString().equals("")) {
            					fp.setVia_from("NA");
            				}
            				else {
            					fp.setVia_from(DateUtil.makeTitle(object[17].toString()));
            				}
            				
            				if (object[18]==null || object[18].toString().equals("")) {
            					fp.setHp_date("NA");
            				}
            				else {
            					fp.setHp_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
            				}
            				
            				if (object[19]==null || object[19].toString().equals("")) {
            					fp.setHp_time("NA");
            				}
            				else {
            					fp.setHp_time(object[19].toString().substring(0, 5));
            				}
            				
            				if (object[20]==null || object[20].toString().equals("")) {
            					fp.setHp_reason("NA");
            				}
            				else {
            					fp.setHp_reason(object[20].toString());
            				}
            				
            				if (object[21]==null || object[21].toString().equals("")) {
            					fp.setSn_reason("NA");
            				}
            				else {
            					fp.setSn_reason(object[21].toString());
            				}
            				
            				if (object[22]==null || object[22].toString().equals("")) {
            					fp.setSn_on_date("NA");
            				}
            				else {
            					fp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[22].toString()));
            				}
            				
            				if (object[23]==null || object[23].toString().equals("")) {
            					fp.setSn_on_time("NA");
            				}
            				else {
            					fp.setSn_on_time(object[23].toString().substring(0, 5));
            				}
            				
            				if (object[24]==null || object[24].toString().equals("")) {
            					fp.setSn_date("NA");
            				}
            				else {
            					fp.setSn_date(DateUtil.convertDateToIndianFormat(object[24].toString()));
            				}
            				
            				if (object[25]==null || object[25].toString().equals("")) {
            					fp.setSn_time("NA");
            				}
            				else {
            					fp.setSn_time(object[25].toString().substring(0,5));
            				}
            				
            				if (object[26]==null || object[26].toString().equals("")) {
            					fp.setSn_duration("NA");
            				}
            				else {
            					fp.setSn_duration(object[26].toString());
            				}
            				
            				if (object[27]==null || object[27].toString().equals("")) {
            					fp.setIg_date("NA");
            				}
            				else {
            					fp.setIg_date(DateUtil.convertDateToIndianFormat(object[27].toString()));
            				}
            				
            				if (object[28]==null || object[28].toString().equals("")) {
            					fp.setIg_time("NA");
            				}
            				else {
            					fp.setIg_time(object[28].toString().substring(0, 5));
            				}
            				
            				if (object[29]==null || object[29].toString().equals("")) {
            					fp.setIg_reason("NA");
            				}
            				else {
            					fp.setIg_reason(object[29].toString());
            				}
            				
            				if (object[30]==null || object[30].toString().equals("")) {
            					fp.setTransfer_date("NA");
            				}
            				else {
            					fp.setTransfer_date(DateUtil.convertDateToIndianFormat(object[30].toString()));
            				}
            				
            				if (object[31]==null || object[31].toString().equals("")) {
            					fp.setTransfer_time("NA");
            				}
            				else {
            					fp.setTransfer_time(object[31].toString().substring(0, 5));
            				}
            				
            				if (object[32]==null || object[32].toString().equals("")) {
            					fp.setTransfer_reason("NA");
            				}
            				else {
            					fp.setTransfer_reason(object[32].toString());
            				}
            				
            				if (object[33]==null || object[33].toString().equals("")) {
            					fp.setAction_by("NA");
            				}
            				else {
            					fp.setAction_by(DateUtil.makeTitle(object[33].toString()));
            				}
            				
            				if (object[36]==null || object[36].toString().equals("")) {
            					fp.setFeedback_by_dept("NA");
            				}
            				else {
            					fp.setFeedback_by_dept(object[36].toString());
            				}
            				
            				if (object[37]==null || object[37].toString().equals("")) {
            					fp.setFeedback_to_dept("NA");
            				}
            				else {
            					fp.setFeedback_to_dept(object[37].toString());
            				}
            				
            				if (object[38]==null || object[38].toString().equals("")) {
            					fp.setFeedback_to_subdept("NA");
            				}
            				else {
            					fp.setFeedback_to_subdept(object[38].toString());
            				}
            				if (object[39]==null || object[39].toString().equals("")) {
            					fp.setNon_working_time("NA");
            				}
            				else {
            					fp.setNon_working_time(object[39].toString());
            				}
            				
            				// Add By Padam In 13 Nov
            				if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("All")) {
            					if (object[40]==null || object[40].toString().equals("")) {
                					fp.setResolve_date("NA");
                				}
                				else {
                					fp.setResolve_date(DateUtil.convertDateToIndianFormat(object[40].toString()));
                				}
            					
            					if (object[41]==null || object[41].toString().equals("")) {
                					fp.setResolve_time("NA");
                				}
                				else {
                					fp.setResolve_time(object[41].toString());
                				}
            					
            					if (object[42]==null || object[42].toString().equals("")) {
                					fp.setResolve_duration("NA");
                				}
                				else {
                					fp.setResolve_duration(object[42].toString());
                				}
            					
            					if (object[43]==null || object[43].toString().equals("")) {
                					fp.setResolve_remark("NA");
                				}
                				else {
                					fp.setResolve_remark(object[43].toString());
                				}
            					
            					if (object[44]==null || object[44].toString().equals("")) {
                					fp.setResolve_by("NA");
                				}
                				else {
                					fp.setResolve_by(DateUtil.makeTitle(object[44].toString()));
                				}
            					
            					if (object[45]==null || object[45].toString().equals("")) {
                					fp.setSpare_used("NA");
                				}
                				else {
                					fp.setSpare_used(object[45].toString());
                				}
            				}
            				
            				feedList.add(fp);
            				i++;
            			}
            		}
				}
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return feedList;
    }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackStatusCount(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel,String pageType, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            		query.append("select feedback.status,count(*)");
            		if (pageType!=null && !pageType.equals("") && pageType.equals("H")) {
            			query.append(" from feedback_status_15072014 as feedback");
					}
            		else {
            			query.append(" from feedback_status as feedback");
					}
            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
            		query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		query.append(" where ");
            		if (by_dept!=null  && !by_dept.equals("-1")) {
            			query.append(" dept1.id='"+by_dept+"'");	
					}
            		
            		if (to_dept!=null  && !to_dept.equals("-1") && !by_dept.equals("-1")) {
            			query.append(" and dept2.id='"+to_dept+"'");	
					}
            		
            		if (to_dept!=null  && !to_dept.equals("-1") && by_dept.equals("-1")) {
            			query.append(" dept2.id='"+to_dept+"'");	
					}
            		
            		if (tosubdept!=null  && !tosubdept.equals("-1")) {
            			query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
					}
            		
            		if (feedType!=null  && !feedType.equals("-1")) {
            			query.append(" and feedtype.id='"+feedType+"'");	
					}
            		
            		if (categoryId!=null && !categoryId.equals("-1")) {
            			query.append(" and catg.id='"+categoryId+"'");	
					}
            		
            		if (subCategory!=null  && !subCategory.equals("-1")) {
            			query.append(" and subcatg.id='"+subCategory+"'");	
					}
            		
            		if (status!=null && !status.equals("") && (status.equals("Pending") || status.equals("Snooze") || status.equals("High Priority") || status.equals("Resolved"))) {
            			query.append(" and feedback.status='"+status+"'");	
					}
            		
            		if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            			query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
					}
            		
            		else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
            			query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
					}
            		
            		if (status!=null && !status.equals("") && !status.equals("All")) {
            			query.append(" and feedback.status='"+status+"'");
					}
            		
            		query.append(" and feedback.moduleName='HDM' ");	
            		
            		query.append(" group by feedback.status");
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public boolean checkTable(String table, SessionFactory connection)
	 {
		boolean flag = false;
		List list = null;
		String value = null;
		try {
			SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
			Properties props = sessionFactoryImpl.getProperties();
			String url = props.get("hibernate.connection.url").toString();
			String[] urlArray = url.split(":");
			String db_name = urlArray[urlArray.length - 1];
			String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
			Session session = null;  
			Transaction transaction = null;  
		    session=connection.getCurrentSession(); 
		    transaction = session.beginTransaction(); 
		    list=session.createSQLQuery(query).list();  
			transaction.commit();
			if (list!=null && list.size()>0) {
				value = list.get(0).toString();
				if (value.equalsIgnoreCase("1")) {
					flag=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	 }
	
	@SuppressWarnings("unchecked")
	public boolean check_createTable(String table, SessionFactory connection)
	 {
		boolean flag = false;
		List list = null;
		String value = null;
		Session session = null;  
		Transaction transaction = null;  
		try {
			SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
			Properties props = sessionFactoryImpl.getProperties();
			String url = props.get("hibernate.connection.url").toString();
			String[] urlArray = url.split(":");
			String db_name = urlArray[urlArray.length - 1];
			String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
			
		    session=connection.getCurrentSession(); 
		    transaction = session.beginTransaction(); 
		    list=session.createSQLQuery(query).list();  
			transaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
			if (list!=null && list.size()>0) {
				value = list.get(0).toString();
				if (value.equalsIgnoreCase("1")) {
					flag=true;
				}
				else { 
					CommonOperInterface cot = new CommonConFactory().createInterface();
					 if (table.equalsIgnoreCase("feedback_type")) {
					      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_type_configuration", "", connection, "", 0, "table_name", "feedback_type_configuration");
					      if (colName!= null && colName.size()>0) {
					    	  //Create Table Query Based On Configuration
							  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
							  for (GridDataPropertyView tableColumes : colName) {
								TableColumes tc = new TableColumes();
								tc.setColumnname(tableColumes.getColomnName());
								tc.setDatatype("varchar(255)");
								tc.setConstraint("default NULL");
								tableColumn.add(tc);
							   }
							  TableColumes tc = new TableColumes();
							  
							
							  tc.setColumnname("dept_subdept");
							  tc.setDatatype("varchar(5)");
							  tc.setConstraint("default NULL");
							  tableColumn.add(tc);
							  
							  tc = new TableColumes();
							  tc.setColumnname("hide_show");
							  tc.setDatatype("varchar(10)");
							  tc.setConstraint("default NULL");
							  tableColumn.add(tc);
							  
							  tc = new TableColumes();
							  tc.setColumnname("user_name");
							  tc.setDatatype("varchar(30)");
							  tc.setConstraint("default NULL");
							  tableColumn.add(tc);
							  
							  tc = new TableColumes();
							  tc.setColumnname("date");
							  tc.setDatatype("varchar(10)");
							  tc.setConstraint("default NULL");
							  tableColumn.add(tc);
							  
							  tc = new TableColumes();
							  tc.setColumnname("time");
							  tc.setDatatype("varchar(10)");
							  tc.setConstraint("default NULL");
							  tableColumn.add(tc);
							  cot.createTable22("feedback_type", tableColumn, connection);
					}
				}
			   else if (table.equalsIgnoreCase("feedback_category")) {
				      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_category_configuration", "", connection, "", 0, "table_name", "feedback_category_configuration");
				      if (colName!= null && colName.size()>0) {
				    	  
						  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
						  for (GridDataPropertyView tableColumes : colName) {
							TableColumes tc = new TableColumes();
							tc.setColumnname(tableColumes.getColomnName());
							tc.setDatatype("varchar(255)");
							tc.setConstraint("default NULL");
							tableColumn.add(tc);
						   }
						  TableColumes tc = new TableColumes();
						  
						  tc.setColumnname("hide_show");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  tc = new TableColumes();
						  tc.setColumnname("user_name");
						  tc.setDatatype("varchar(30)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  tc = new TableColumes();
						  tc.setColumnname("date");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  tc = new TableColumes();
						  tc.setColumnname("time");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						 cot.createTable22("feedback_category", tableColumn, connection);
			}
			} 
			   else if (table.equalsIgnoreCase("feedback_subcategory")) {
				      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", "", connection, "", 0, "table_name", "feedback_subcategory_configuration");
				      if (colName!= null && colName.size()>0) {
						
						  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
						  for (GridDataPropertyView tableColumes : colName) {
							  if (!tableColumes.getColomnName().equals("fbType")) {
								  TableColumes tc = new TableColumes();
									tc.setColumnname(tableColumes.getColomnName());
									tc.setDatatype("varchar(255)");
									tc.setConstraint("default NULL");
									tableColumn.add(tc);
							}
							
						   }
						  TableColumes tc = new TableColumes();
						  
						  
						  tc.setColumnname("hide_show");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						 
						  tc = new TableColumes();
						  tc.setColumnname("user_name");
						  tc.setDatatype("varchar(30)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  tc = new TableColumes();
						  tc.setColumnname("date");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  tc = new TableColumes();
						  tc.setColumnname("time");
						  tc.setDatatype("varchar(10)");
						  tc.setConstraint("default NULL");
						  tableColumn.add(tc);
						  
						  cot.createTable22("feedback_subcategory", tableColumn, connection);
			   }
			}
					flag=true;
			}
			}
			
		return flag;
	 }
	
	@SuppressWarnings("unchecked")
	public List getClientConf(SessionFactory connection)
	 {
		List list = null;
		try {
			 String query = "SELECT * FROM client_conf";
			 Session session = null;  
			 Transaction transaction = null;  
		     session=connection.getCurrentSession(); 
		     transaction = session.beginTransaction(); 
		     list=session.createSQLQuery(query).list();  
			 transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	 }
	
	@SuppressWarnings("unchecked")
	public FeedbackPojo setFeedbackDataValues(List data, String status,String deptLevel)
	{
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				fbp.setTicket_no(object[0].toString());
				if (object[1]!=null && !object[1].toString().equals("")) {
					fbp.setFeed_registerby(DateUtil.makeTitle(object[1].toString()));
				} else {
					fbp.setFeed_registerby("NA");
				}
				if (object[2]!=null && !object[2].toString().equals("")) {
					fbp.setFeedback_by_mobno(object[2].toString());
				} else {
					fbp.setFeedback_by_mobno("NA");
				}
				if (object[3]!=null && !object[3].toString().equals("")) {
					fbp.setFeedback_by_emailid(object[3].toString());
				} else {
					fbp.setFeedback_by_emailid("NA");
				}
				
				
				fbp.setFeedback_allot_to(object[4].toString());
				fbp.setFeedtype(object[5].toString());
				fbp.setFeedback_catg(object[6].toString());
				fbp.setFeedback_subcatg(object[7].toString());
				fbp.setFeed_brief(object[8].toString());
				fbp.setFeedaddressing_time(object[9].toString());
				fbp.setLocation(object[11].toString());
				fbp.setOpen_date(object[12].toString());
				fbp.setOpen_time(object[13].toString());
				fbp.setEscalation_date(object[14].toString());
				fbp.setEscalation_time(object[15].toString());
				fbp.setLevel(object[16].toString());
				fbp.setStatus(object[17].toString());
				fbp.setVia_from(object[18].toString());
				if (status.equalsIgnoreCase("High Priority")) {
					fbp.setHp_date(object[19].toString());	
					fbp.setHp_time(object[20].toString());	
					fbp.setHp_reason(object[21].toString());
				}
				else if (status.equalsIgnoreCase("Snooze")) {
				    fbp.setSn_reason(object[22].toString());
				    fbp.setSn_on_date(object[23].toString());
				    fbp.setSn_on_time(object[24].toString());
				    fbp.setSn_date(object[25].toString());
				    fbp.setSn_time(object[26].toString());
				    fbp.setSn_duration(object[27].toString());
				}
				else if (status.equalsIgnoreCase("Ignore")) {
	                fbp.setIg_date(object[28].toString());
	     			fbp.setIg_time(object[29].toString());
	     			fbp.setIg_reason(object[30].toString());
				}
				else if (status.equalsIgnoreCase("Transfer")) {
	                fbp.setTransfer_date(object[31].toString());
	         		fbp.setTransfer_time(object[32].toString());
	         		fbp.setTransfer_reason(object[33].toString());	
				}
         		if (object[34]!=null && !object[34].toString().equals("")) {
					fbp.setAction_by(object[34].toString());	
				}
				else {
					fbp.setAction_by("NA");
				}
				
				if (object[35]!=null && !object[35].toString().equals("")) {
					fbp.setMobOne(object[35].toString());	
				}
				else {
					fbp.setMobOne("NA");
				}
				if (object[36]!=null && !object[36].toString().equals("")) {
					fbp.setEmailIdOne(object[36].toString());
				} else {
					fbp.setEmailIdOne("NA");
				}
				
				fbp.setFeedback_by_dept(object[37].toString());
             	fbp.setFeedback_to_dept(object[38].toString());	
             	fbp.setFeedback_to_subdept(object[39].toString());
             	fbp.setId(Integer.parseInt(object[40].toString()));
               
         		if (status.equals("Resolved")) {
	                fbp.setResolve_date(object[41].toString());
	                fbp.setResolve_time(object[42].toString());
	                fbp.setResolve_duration(object[43].toString());
	                fbp.setResolve_remark(object[44].toString());
	                fbp.setSpare_used(object[45].toString());
	                fbp.setResolve_by(object[46].toString());
	                if (object[47]!=null && !object[47].toString().equals("")) {
						fbp.setResolve_by_mobno(object[47].toString());
					} else {
						fbp.setResolve_by_mobno("NA");
					}
	                if (object[48]!=null && !object[48].toString().equals("")) {
						fbp.setResolve_by_emailid(object[48].toString());
					} else {
						fbp.setResolve_by_emailid("NA");
					}
				}
         		
         		if (!status.equals("Resolved")) {
					if (object[41]!=null && !object[41].toString().equals("")) {
						fbp.setAddr_date_time(DateUtil.convertDateToIndianFormat(object[41].toString().substring(0, 10))+" "+object[41].toString().substring(11, 16));
					}
				}
			}
		}
		return fbp;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackDataforReport(List data,String status)
	{
		List<FeedbackPojo> fbpList = new ArrayList<FeedbackPojo>();
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				
				if (object[0]!=null) {
					fbp.setTicket_no(object[0].toString());
				}
				else {
					fbp.setTicket_no("NA");
				}
				
				if (object[1]!=null) {
					fbp.setFeedback_by_dept(object[1].toString());
				}
				else {
					fbp.setFeedback_by_dept("NA");
				}
				
				if (object[2]!=null) {
					fbp.setFeed_by(object[2].toString());
				}
				else {
					fbp.setFeed_by("NA");
				}
				
				if (object[3]!=null) {
					fbp.setFeedback_by_mobno(object[3].toString());
				}
				else {
					fbp.setFeedback_by_mobno("NA");
				}
				
				if (object[4]!=null) {
					fbp.setFeedback_by_emailid(object[4].toString());
				}
				else {
					fbp.setFeedback_by_emailid("NA");
				}
				
				if (object[5]!=null) {
					fbp.setFeedback_to_dept(object[5].toString());
				}
				else {
					fbp.setFeedback_to_dept("NA");
				}
				
				if (object[6]!=null) {
					fbp.setFeedback_allot_to(object[6].toString());
				}
				else {
					fbp.setFeedback_allot_to("NA");
				}
				
				if (object[7]!=null) {
					fbp.setFeedtype(object[7].toString());
				}
				else {
					fbp.setFeedtype("NA");
				}
				
				if (object[8]!=null) {
					fbp.setFeedback_catg(object[8].toString());
				}
				else {
					fbp.setFeedback_catg("NA");
				}
				
				if (object[9]!=null) {
					fbp.setFeedback_subcatg(object[9].toString());
				}
				else {
					fbp.setFeedback_subcatg("NA");
				}
				
				if (object[10]!=null) {
					fbp.setFeed_brief(object[10].toString());
				}
				else {
					fbp.setFeed_brief("NA");
				}
				
				if (object[11]!=null) {
					fbp.setLocation(object[11].toString());
				}
				else {
					fbp.setLocation("NA");
				}
				
				if (object[12]!=null) {
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));
				}
				else {
					fbp.setOpen_date("NA");
				}
				
				if (object[13]!=null) {
					fbp.setOpen_time(object[13].toString().substring(0, 5));
				}
				else {
					fbp.setOpen_time("NA");
				}
				
				if (object[14]!=null) {
					fbp.setLevel(object[14].toString());
				}
				else {
					fbp.setLevel("NA");
				}
				
				if (object[15]!=null) {
					fbp.setStatus(object[15].toString());
				}
				else {
					fbp.setStatus("NA");
				}
				
				if (object[16]!=null) {
					fbp.setVia_from(object[16].toString());
				}
				else {
					fbp.setVia_from("NA");
				}
				
				if (object[17]!=null) {
					fbp.setFeed_registerby(object[17].toString());
				}
				else {
					fbp.setFeed_registerby("NA");
				}
				
		//		System.out.println("Status is as  "+fbp.getStatus());
				
				if (status.equalsIgnoreCase("Pending")) {
					if (object[18]!=null) {
						fbp.setSn_reason(object[18].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[19]!=null && !object[19].toString().equals("") && !object[19].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}
					
					if (object[20]!=null && !object[20].toString().equals("") && !object[20].toString().equals("NA")) {
						fbp.setSn_on_time(object[20].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
					if (object[21]!=null && !object[21].toString().equals("") && !object[1].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
						fbp.setSn_time(object[22].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[23]!=null) {
						fbp.setSn_duration(object[23].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
					

    				if (object[24]!=null && !object[24].toString().equals("")) {
    					
    					fbp.setAddressingTime(object[24].toString());
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[25]!=null) {
						fbp.setEscalation_date(object[25].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[26]!=null) {
						fbp.setEscalation_time(object[26].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
					
					if (object[27]!=null) {
						fbp.setFeedback_to_subdept(object[27].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				else if (status.equalsIgnoreCase("Resolved")) {
					if (object[18]!=null) {
						fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setResolve_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setResolve_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setResolve_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setResolve_duration(object[20].toString());
					}
					else {
						fbp.setResolve_duration("NA");
					}
					
					if (object[21]!=null) {
						fbp.setResolve_remark(object[21].toString());
					}
					else {
						fbp.setResolve_remark("NA");
					}
					
					if (object[22]!=null) {
						fbp.setResolve_by(object[22].toString());
					}
					else {
						fbp.setResolve_by("NA");
					}
					
					if (object[23]!=null) {
						fbp.setSpare_used(object[23].toString());
					}
					else {
						fbp.setSpare_used("NA");
					}
					

					if (object[24]!=null) {
						fbp.setSn_reason(object[24].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[25]!=null && !object[25].toString().equals("") && !object[25].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[25].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}
					
					if (object[26]!=null && !object[26].toString().equals("") && !object[26].toString().equals("NA")) {
						fbp.setSn_on_time(object[26].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
					if (object[27]!=null && !object[27].toString().equals("") && !object[27].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[27].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[28]!=null && !object[28].toString().equals("") && !object[28].toString().equals("NA")) {
						fbp.setSn_time(object[28].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[29]!=null) {
						fbp.setSn_duration(object[29].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
					

    				if (object[30]!=null && !object[30].toString().equals("")) {
    					
    					fbp.setAddressingTime(object[30].toString());
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[31]!=null) {
						fbp.setEscalation_date(object[31].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[32]!=null) {
						fbp.setEscalation_time(object[32].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
					
					if (object[33]!=null) {
						fbp.setFeedback_to_subdept(object[33].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				else if (fbp.getStatus().equalsIgnoreCase("Snooze")) {
					
			//		System.out.println("Inside for Snooze ???"+object.length);
					if (object[18]!=null) {
						fbp.setSn_reason(object[18].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[19]!=null && !object[19].toString().equals("") && !object[19].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}

					if (object[20]!=null && !object[20].toString().equals("") && !object[20].toString().equals("NA")) {
						fbp.setSn_on_time(object[20].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
			//		System.out.println("21 is as "+object[21]);
					if(object[21]!=null )
					{
						fbp.setFeedback_to_subdept(object[21].toString() );
					}

					/*
					 * Commented by Avinash on 03 Sept for Reporting
					 * if (object[21]!=null && !object[21].toString().equals("") && !object[21].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
						fbp.setSn_time(object[22].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[23]!=null) {
						fbp.setSn_duration(object[23].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
					
                    if (object[24]!=null && !object[24].toString().equals("")) {
    					
    					fbp.setAddressingTime(object[24].toString());
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[25]!=null) {
						fbp.setEscalation_date(object[25].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[26]!=null) {
						fbp.setEscalation_time(object[26].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
					
					if (object[27]!=null) {
						fbp.setFeedback_to_subdept(object[27].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}*/
				}
				else if (status.equalsIgnoreCase("High Priority")) {
					if (object[18]!=null) {
						fbp.setHp_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setHp_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setHp_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setHp_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setHp_reason(object[20].toString());
					}
					else {
						fbp.setHp_reason("NA");
					}
				}
				else if (status.equalsIgnoreCase("Ignore")) {
					if (object[18]!=null) {
						fbp.setIg_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setIg_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setIg_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setIg_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setIg_reason(object[20].toString());
					}
					else {
						fbp.setIg_reason("NA");
					}
				}
				
				if (status.equalsIgnoreCase("High Priority") || status.equalsIgnoreCase("Ignore")) {
                    if (object[21]!=null && !object[21].toString().equals("")) {
    					
    					fbp.setAddressingTime(object[21].toString());
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[22]!=null) {
						fbp.setEscalation_date(object[22].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[23]!=null) {
						fbp.setEscalation_time(object[23].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
					
					if (object[24]!=null) {
						fbp.setFeedback_to_subdept(object[24].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				fbpList.add(fbp);	
			}
		}
		return fbpList;
	}
	
	@SuppressWarnings("unchecked")
	public List getReportToData( String date, String time,SessionFactory connection)
	 {

		List  reportdatalist = new ArrayList();
		StringBuffer query= new StringBuffer();
		query.append("select report_conf.id,report_conf.report_level,report_conf.dept,report_conf.sms,report_conf.mail,report_conf.report_type,report_conf.module,emp.empName,emp.mobOne,emp.emailIdOne,report_conf.email_subject,dept.deptName from report_configuration as report_conf ");
		query.append(" left join employee_basic as emp on report_conf.empId=emp.id ");
		query.append(" left join department as dept on report_conf.dept=dept.id ");
		query.append(" where report_conf.report_date='"+date+"' and report_conf.report_time<='"+time+"'");
	//	System.out.println(""+query);
		Session session = null;  
	    Transaction transaction = null;  
		try 
		  {  
			 session=connection.openSession(); 
			 transaction = session.beginTransaction(); 
			 reportdatalist=session.createSQLQuery(query.toString()).list();  
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
			 transaction.rollback();
		  } 
		finally{
			try {
				session.flush();
				session.close();
			} catch (Exception e) {
				
			}
		}
		return reportdatalist;
	 }
	
	@SuppressWarnings("unchecked")
	public String getFloorStatus( String deptid,SessionFactory connection)
	 {
		String  flrStatus = "";
		StringBuffer query= new StringBuffer();
		List flrList = new ArrayList();
		query.append("select floor_status from dept_ticket_series_conf where deptName='"+deptid+"'");
		Session session = null;  
	    Transaction transaction = null;  
		try 
		  {  
			 session=connection.openSession(); 
			 transaction = session.beginTransaction(); 
			 flrList=session.createSQLQuery(query.toString()).list();  
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
			 transaction.rollback();
		  } 
		finally{
			try {
				session.flush();
				session.close();
			} catch (Exception e) {
				
			}
		}
		if(flrList!=null && flrList.size()>0)
        {
			flrStatus=flrList.get(0).toString();
        }
		return flrStatus;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getDeptId( String subdept,SessionFactory connection)
	 {
		String  dept = "";
		StringBuffer query= new StringBuffer();
		List flrList = new ArrayList();
		query.append("select deptid from subdepartment where id='"+subdept+"'");
		Session session = null;  
	    Transaction transaction = null;  
		try 
		  {  
			 session=connection.openSession(); 
			 transaction = session.beginTransaction(); 
			 flrList=session.createSQLQuery(query.toString()).list();  
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
			 transaction.rollback();
		  } 
		finally{
			try {
				session.flush();
				session.close();
			} catch (Exception e) {
				
			}
		}
		if(flrList!=null && flrList.size()>0)
        {
			dept=flrList.get(0).toString();
        }
		return dept;
	 }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackList(String deptId,String subDeptId, String feedTypeId, String categoryId,String viewtype,SessionFactory connection)
	 {
		List  categorylist = new ArrayList();
		StringBuffer str  =new StringBuffer();
		str.append("select feedtype.fbType,catg.categoryName,subcatg.subCategoryName,subcatg.feedBreif,subcatg.addressingTime,subcatg.resolutionTime,subcatg.escalationDuration,subcatg.escalationLevel,subcatg.needEsc,subcatg.severity from feedback_type as feedtype");
		if (viewtype.equals("SD")) {
			str.append(" inner join subdepartment as subdept on feedtype.dept_subdept=subdept.id ");
		}
		else if (viewtype.equals("D")) {
			str.append(" inner join department as dept on feedtype.dept_subdept=dept.id ");
		}
		str.append(" inner join feedback_category as catg on feedtype.id=catg.fbType");
		str.append(" inner join feedback_subcategory as subcatg on catg.id=subcatg.categoryName");
		
		str.append(" where ");
		
		if (viewtype.equalsIgnoreCase("D")) {
			if (feedTypeId!=null && !feedTypeId.equals("") && !feedTypeId.equals("-1")) {
				str.append(" feedtype.id ="+feedTypeId+" ");
			}
			if (categoryId!=null && !categoryId.equals("") && !categoryId.equals("-1")) {
				str.append(" and catg.id ="+categoryId+"");
			}
		}
		else if (viewtype.equalsIgnoreCase("SD")) {
			if (subDeptId!=null && !subDeptId.equals("") && !subDeptId.equals("-1")) {
				str.append(" subdept.id ="+subDeptId+"");
			}
			if (feedTypeId!=null && !feedTypeId.equals("") && !feedTypeId.equals("-1")) {
				str.append(" and feedtype.id ="+feedTypeId+" ");
			}
			if (categoryId!=null && !categoryId.equals("") && !categoryId.equals("-1")) {
				str.append(" and catg.id ="+categoryId+"");
			}
		}
		Session session = null;  
	    Transaction transaction = null;  
		try 
		  {  
			 session=connection.getCurrentSession(); 
			 transaction = session.beginTransaction(); 
			 categorylist=session.createSQLQuery(str.toString()).list();  
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
			 transaction.rollback();
		  } 
		
	    
		return categorylist;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEscalation( String deptLevel, String dept, String levelname,SessionFactory connection)
	 {
		String shiftname =DateUtil.getCurrentDateUSFormat().substring(8, 10)+"_date";
		List  escalation = new ArrayList();
		String query="select emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName from employee_basic as emp"
					+" inner join designation desg on emp.designation = desg.id"
					+" inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
					+" inner join department dept on sub_dept.deptid = dept.id"
                    +" inner join roaster_conf roaster on emp.empId = roaster.empId"
                    +" inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
		            +" where shift.shiftName="+shiftname+" and emp.status='0' and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='"+levelname+"' and dept.id='"+dept+"' and roaster.deptHierarchy='"+deptLevel+"'";
		            Session session = null;  
				    Transaction transaction = null;  
				    try 
				     {  
			            session=connection.getCurrentSession(); 
						transaction = session.beginTransaction(); 
						escalation=session.createSQLQuery(query).list();  
						transaction.commit(); 
					 }
				    catch (Exception ex)
					 {
						transaction.rollback();
					 } 
		
			return escalation;
	    }
	
	public String getNewDate(String newDateType)
	 {
		String data="";
		 try 
			{
			 if (newDateType.equals("D")) {
				 data = DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat());
				}
				else if (newDateType.equals("W")) {
					data = DateUtil.getNewDate("day", 7, DateUtil.getCurrentDateUSFormat());
				}
				else if (newDateType.equals("M")) {
					data = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
				}
				else if (newDateType.equals("Q")) {
					data = DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
				}
				else if (newDateType.equals("H")) {
					data = DateUtil.getNewDate("month", 6, DateUtil.getCurrentDateUSFormat());
				}
             else if (newDateType.equals("Y")) {
            	 data = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return data;
	 }
	
	@SuppressWarnings("unchecked")
	public String getDeptName(String subdept_dept, String deptLevel, SessionFactory connection)
	 {
		 String data="",query="";
		 List list =null;
		
			 if (deptLevel.equals("2")) {
				 query = "select deptName from department where id=(select deptid from subdepartment where id="+subdept_dept+")";
				}
			
            else if (deptLevel.equals("1")) {
            	query = "select deptName from department where id="+subdept_dept+"";
				}
		    Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(query).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{
				   transaction.rollback();
				} 
						
			 if(list!=null && list.size()>0)
	            {
				 data=list.get(0).toString();
	            }
			return data;
	 }
	
	@SuppressWarnings("unchecked")
	public List getTicketCounters( String reportLevel,String reportType, boolean cd_pd,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
		List  counterList = new ArrayList();
		StringBuilder qry = new StringBuilder();
			qry.append("select count(*),feedback.status from feedback_status as feedback");
			if (reportLevel.equals("2")) {
				if (cd_pd) {
					if (reportType!=null && !reportType.equals("") && reportType.equals("D")) {
						qry.append(" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("W")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("M")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");				
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("Q")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("H")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");
					}
				}
				else {
					qry.append(" where feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status");
				}
			}
			else if (reportLevel.equals("1")) {
				qry.append(" inner join subdepartment as subdept on feedback.to_dept_subdept=subdept.id");
				qry.append(" inner join department as dept on subdept.deptid=dept.id");
				if (cd_pd) {
					if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("D")) {
						qry.append(" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("W")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("M")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");				
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("Q")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("H")) {
						qry.append(" where feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");
					}
				}
				else {
					qry.append(" where feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and dept.id="+subdept_dept+" and feedback.moduleName='HDM' group by feedback.status");
				}
			}
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			counterList=session.createSQLQuery(qry.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{
			   transaction.rollback();
			} 
		
		return counterList;
	 }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData( String reportLevel,String reportType, boolean cd_pd, String  status,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
		  List  dataList = new ArrayList();
		  List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
		  StringBuilder query = new StringBuilder("");
						query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        		
        		        // Block for get resolved data
        		        if (status.equalsIgnoreCase("Resolved")) {
        		            query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				        }
        		        // Block for get Snooze data
        		        else if (status.equalsIgnoreCase("Snooze")) {
        		        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
						}
        		        // Block for get High Priority data
        		        else if (status.equalsIgnoreCase("High Priority")) {
        		        	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
						}
        		        // Block for get ignore data
        		        else if (status.equalsIgnoreCase("Ignore")) {
        		        	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
						}
		        		
		        		if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending")) {
	        		        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
							}
		        		query.append(",feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,subdept2.subdeptname as tosubdept");
        		        query.append(" from feedback_status as feedback");
        		
        		        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
        		        // Applying inner join at sub department level
	            		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	            		query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	            		query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
	            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
	            		query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id ");
        		
        		        // Apply inner join for getting the data for Resolved Ticket
		        		if (status.equalsIgnoreCase("Resolved")) {
		        			query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
						}
				
		        		// getting data of current day
						if (cd_pd) {
							query.append(" where feedback.status='"+status+"' ");
							if (reportType!=null && !reportType.equals("") && reportType.equals("D")) {
								query.append(" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("W")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("M")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");				
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("Q")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("H")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							
							if (reportLevel.equals("1")) {
								 query.append(" and dept2.id="+subdept_dept+""); 
							}
						}
				        // End of If Block for getting the data for only the current date in both case for Resolved OR All
				
						// Else Block for getting the data for only the previous date
						else {
								if (reportLevel.equals("2")) {
									  query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"'");
								}
								else if (reportLevel.equals("1")) {
									 query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and dept2.id="+subdept_dept+""); 
								}
						}
						query.append(" and feedback.moduleName='HDM'");
						
					//	System.out.println("CFD Querry >>> "+query);
						
					Session session = null;  
					Transaction transaction = null;  
					try 
					 {  
						session=connection.getCurrentSession(); 
						transaction = session.beginTransaction(); 
						dataList=session.createSQLQuery(query.toString()).list();  
						transaction.commit(); 
					 }
					catch (Exception ex)
						{
						   transaction.rollback();
						   ex.printStackTrace();
						} 
										
					if (dataList!=null && dataList.size()>0) {
						report = new HelpdeskUniversalHelper().setFeedbackDataforReport(dataList, status);
					}
					return report;
	 }
	/*
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData4Report( String reportLevel,String reportType, boolean cd_pd, String  status,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
		  List  dataList = new ArrayList();
		  List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
		  StringBuilder query = new StringBuilder("");
						query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        		
        		        // Block for get resolved data
        		        if (status.equalsIgnoreCase("Resolved")) {
        		            query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				        }
        		        // Block for get Snooze data
        		        else if (status.equalsIgnoreCase("Snooze")) {
        		        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
						}
        		        // Block for get High Priority data
        		        else if (status.equalsIgnoreCase("High Priority")) {
        		        	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
						}
        		        // Block for get ignore data
        		        else if (status.equalsIgnoreCase("Ignore")) {
        		        	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
						}
        		        // getting the data regarding Sub Department at Level 2
		        		if (deptLevel.equals("2")) {
        		            query.append(",subdept1.subdeptname as bysubdept,subdept2.subdeptname as tosubdept");
        		        }
		        		
        		        query.append(" from feedback_status as feedback");
        		
        		        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
        		        // Applying inner join at sub department level
        		        if (deptLevel.equals("2")) {
		            		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
		            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		            		query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
		            		query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
		            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
		            		query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id ");
		            		query.append(" inner join department dept1 on subdept1.deptid= dept1.id  "); 
            	        }
        		        // Applying inner join at Department level
        		       else if (deptLevel.equals("1")) {
		            		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
		            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		            		query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
		            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
		            		query.append(" inner join department dept1 on subdept1.deptid= dept1.id  "); 
				          }
        		
        		        // Apply inner join for getting the data for Resolved Ticket
		        		if (status.equalsIgnoreCase("Resolved")) {
		        			query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
						}
				
		        		// getting data of current day
						if (cd_pd) {
							query.append(" where feedback.status='"+status+"' ");
							if (reportType!=null && !reportType.equals("") && reportType.equals("D")) {
								query.append(" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("W")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("M")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");				
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("Q")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if (reportType!=null && !reportType.equals("") && reportType.equals("H")) {
								query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							
							if (reportLevel.equals("1")) {
								 query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdept_dept+")) and feedback.deptHierarchy="+deptLevel+""); 
							}
						}
				        // End of If Block for getting the data for only the current date in both case for Resolved OR All
				
						// Else Block for getting the data for only the previous date
						else {
								if (reportLevel.equals("2")) {
									  query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"'");
								}
								else if (reportLevel.equals("1")) {
									 query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdept_dept+")) and feedback.deptHierarchy="+deptLevel+""); 
								}
						}
					Session session = null;  
					Transaction transaction = null;  
					try 
					 {  
						session=connection.getCurrentSession(); 
						transaction = session.beginTransaction(); 
						dataList=session.createSQLQuery(query.toString()).list();  
						transaction.commit(); 
					 }
					catch (Exception ex)
						{
						   transaction.rollback();
						} 
										
					if (dataList!=null && dataList.size()>0) {
						report = new HelpdeskUniversalHelper().setFeedbackDataforReport(dataList, status, deptLevel);
					}
					return report;
	 }
	
	
	
	
	
	*/
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getTicketData( String id,SessionFactory connection)
	 {
	    List  dataList = new ArrayList();
	    StringBuilder query = new StringBuilder("");
		query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
        query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
        query.append(",subdept2.subdeptname as tosubdept,feedback.escalation_date,feedback.escalation_time,feedback.Addr_date_time");
        query.append(" from feedback_status as feedback");
        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
		query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
		query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
		query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id ");
		query.append(" where  feedback.id="+id+""); 
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dataList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{
			   transaction.rollback();
			} 
		
		return dataList;
	 }
	
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field,String value,SessionFactory ConnectionName) {
			List  existList = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select * from "+table+" where "+field+"='"+value+"'");
					existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					if(existList.size()>0)
					{
						flag=true;
					}
			}catch (Exception e) {
				flag=false;
			} 
			 return flag;
		}
	 
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,SessionFactory ConnectionName) {
			List  existList = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
		    		query1.append("select * from "+table+" where "+field1+"='"+value1+"'");
		    		if (value2!=null && !value2.equals("")) {
		    			query1.append(" and "+field2+"='"+value2+"'");
					}
		    		if (value3!=null && !value3.equals("")) {
		    			query1.append(" and "+field3+"='"+value3+"'");
					}
		    	//	System.out.println(""+query1);
					existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					if(existList.size()>0)
					{
						flag=true;
					}
			}catch (Exception e) {
				flag=false;
			} 
			 return flag;
		}
	 
	@SuppressWarnings("unchecked")
	public boolean isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,String field4,String value4,SessionFactory ConnectionName) {
				List  existList = null;
				boolean flag=false;
			    try 
				{
			    		StringBuilder query1=new StringBuilder("");
			    		query1.append("select * from "+table+" where "+field1+"='"+value1+"'");
			    		if (value2!=null && !value2.equals("")) {
			    			query1.append(" and "+field2+"='"+value2+"'");
						}
			    		if (value3!=null && !value3.equals("")) {
			    			query1.append(" and "+field3+"='"+value3+"'");
						}
			    		if (value4!=null && !value4.equals("")) {
			    			query1.append(" and "+field4+"='"+value4+"'");
						}
						existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
						if(existList.size()>0)
						{
							flag=true;
						}
				}catch (Exception e) {
					flag=false;
				} 
				 return flag;
			}
	
	@SuppressWarnings("unchecked")
	public String isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,String field4,String value4,String field5,String value5,String field6,String value6,String field7,String value7,String field8,String value8,SessionFactory ConnectionName) {
				List  existList = null;
				//boolean flag=false;
				String ticket="NA";
			    try 
				{
			    		StringBuilder query1=new StringBuilder("");
			    		query1.append("select ticket_no from "+table+" where "+field1+"='"+value1+"'");
			    		if (value2!=null && !value2.equals("")) {
			    			query1.append(" and "+field2+"='"+value2+"'");
						}
			    		if (value3!=null && !value3.equals("")) {
			    			query1.append(" and "+field3+"='"+value3+"'");
						}
			    		if (value4!=null && !value4.equals("")) {
			    			query1.append(" and "+field4+"='"+value4+"'");
						}
			    		if (value5!=null && !value5.equals("")) {
			    			query1.append(" and "+field5+"='"+value5+"'");
						}
			    		if (value6!=null && !value6.equals("")) {
			    			query1.append(" and "+field6+"='"+value6+"'");
						}
			    		if (value7!=null && !value7.equals("") && field7.equalsIgnoreCase("open_time"))
			    		{
			    			query1.append(" and "+field7+" like '"+value7+"%'");
						}
			    		else if(value7!=null && !value7.equals(""))
			    		{
			    			query1.append(" and "+field7+"='"+value7+"'");
			    		}
			    		if (value8!=null && !value8.equals("")) {
			    			query1.append(" and "+field8+"='"+value8+"'");
						}
						existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
						if(existList.size()>0)
						{
							ticket=existList.get(0).toString();
						}
				}catch (Exception e) {
					System.out.println("Error in checking exist record!!! ");
					//flag=false;
				} 
				 return ticket;
			}
	 
	 @SuppressWarnings("unchecked")
	public boolean checkDept_SubDept(String uid,SessionFactory ConnectionName) {
			List  existList = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select count(*) from feedback_type where dept_subdept=(select subdept from employee_basic where useraccountid="+uid+")");
					existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					if(existList.size()>0)
					{flag=true;}
			}catch (Exception e) {
				flag=false;
			} 
			 return flag;
		}
	
	@SuppressWarnings("unchecked")
	public List getSubDeptList( String subdeptid,SessionFactory connection)
	 {
		List  subdeptList = new ArrayList();
		String query="";
	    query="select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdeptid+"";
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			subdeptList=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUID(String loginType,String dept_id,String empName,SessionFactory connection)
	 {
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
			if (loginType.equalsIgnoreCase("H")) {
				query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
				query.append(" inner join feedback_type fbtype on subdept.id= fbtype.dept_subdept ");
				query.append(" inner join department dept on subdept.deptid= dept.id  ");
				query.append(" where dept.id="+dept_id+" order by subdept.subdeptname ");
			}
			else if (loginType.equalsIgnoreCase("M")) {
				query.append("select distinct dept.id,dept.deptName from department as dept");
				query.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
				query.append(" inner join feedback_type as feedtype on subdept.id=feedtype.dept_subdept");
				query.append(" where feedtype.hide_show='Active' and feedtype.moduleName='HDM' order by dept.deptName ");
			}
			else if (loginType.equalsIgnoreCase("N")) {
				query.append("select distinct subdept.id,subdept.subdeptname from feedback_status as feedstatus");
				query.append(" inner join subdepartment subdept on feedstatus.to_dept_subdept=subdept.id ");
				query.append(" where feedstatus.feed_by='"+empName+"'");
			}
		    Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			subdeptList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getDistinctData(String table,String field,SessionFactory connection)
	 {
		List  distinctDataList = new ArrayList();
		StringBuffer query=new StringBuffer();
			query.append("select distinct "+field+" from "+table+" order by "+field+"");
		
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			distinctDataList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return distinctDataList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeData( String dept_subdeptid,String deptLevel,String searchField, String searchString, String searchOper,SessionFactory connection)
	 {
		List  subdeptList = new ArrayList();
		StringBuffer str = new StringBuffer();
		if (deptLevel.equals("2")) {
			str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy,subdept.subdeptname from employee_basic as emp");
			str.append(" inner join designation as desg on desg.id=emp.designation");
			str.append(" inner join subdepartment as subdept on subdept.id=emp.subdept");
			str.append(" where emp.subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id="+dept_subdeptid+")) and emp.escalationId=0 and emp.status=0");
			  if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
				{
				  str.append(" and");
          	  
				  if(searchOper.equalsIgnoreCase("eq"))
					{
					  str.append(" "+searchField+" = '"+searchString+"'");
					}
				else if(searchOper.equalsIgnoreCase("cn"))
				{
					str.append(" "+searchField+" like '%"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("bw"))
				{
					str.append(" "+searchField+" like '"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("ne"))
				{
					str.append(" "+searchField+" <> '"+searchString+"'");
				}
				else if(searchOper.equalsIgnoreCase("ew"))
				{
					str.append(" "+searchField+" like '%"+searchString+"'");
				}
				}
			  str.append(";");
		}
		else if (deptLevel.equals("1")) {
			str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy from employee_basic as emp");
			str.append(" inner join designation as desg on desg.id=emp.designation");
			str.append(" inner join department as dept on dept.id=emp.dept");
			str.append(" where emp.dept="+dept_subdeptid+" and emp.escalationId=0");
			 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
				{
				  str.append(" and");
       	  
				  if(searchOper.equalsIgnoreCase("eq"))
					{
					  str.append(" "+searchField+" = '"+searchString+"'");
					}
				else if(searchOper.equalsIgnoreCase("cn"))
				{
					str.append(" "+searchField+" like '%"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("bw"))
				{
					str.append(" "+searchField+" like '"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("ne"))
				{
					str.append(" "+searchField+" <> '"+searchString+"'");
				}
				else if(searchOper.equalsIgnoreCase("ew"))
				{
					str.append(" "+searchField+" like '%"+searchString+"'");
				}
				}
			  str.append(";");
		}
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			subdeptList=session.createSQLQuery(str.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getContacts4Roaster( String dept_id,String deptLevel,String searchField, String searchString, String searchOper,String module ,String viewType,SessionFactory connection)
	 {
		List  empList = new ArrayList();
		StringBuffer str = new StringBuffer();
			str.append("select comp.id,comp.level");
			if (viewType!=null && !viewType.equals("") && viewType.equals("SD")) {
				str.append(",subdept.subdept_name");
			}
			else if (viewType!=null && !viewType.equals("") && viewType.equals("D")) {
				str.append(",dept.contact_subtype_name");
			}
			
			str.append(",emp.emp_name,emp.mobile_no,emp.email_id");
			str.append(" from primary_contact as emp");
			
			str.append(" inner join manage_contact as comp on comp.emp_id=emp.id");
			if (viewType!=null && !viewType.equals("") && viewType.equals("SD")) {
				str.append(" inner join subdepartment as subdept on subdept.id=comp.for_contact_sub_type_id");
				str.append(" inner join contact_sub_type as dept on dept.id=subdept.contact_sub_id");
			}
			else if (viewType!=null && !viewType.equals("") && viewType.equals("D")) {
				str.append(" inner join contact_sub_type as dept on comp.for_contact_sub_type_id=dept.id");
			}
			
			
			
			str.append(" where dept.id='"+dept_id+"' and comp.work_status=0 and comp.module_name='"+module+"' order by comp.level");
			  if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
				{
				  str.append(" and");
          	  
				  if(searchOper.equalsIgnoreCase("eq"))
					{
					  str.append(" "+searchField+" = '"+searchString+"'");
					}
				else if(searchOper.equalsIgnoreCase("cn"))
				{
					str.append(" "+searchField+" like '%"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("bw"))
				{
					str.append(" "+searchField+" like '"+searchString+"%'");
				}
				else if(searchOper.equalsIgnoreCase("ne"))
				{
					str.append(" "+searchField+" <> '"+searchString+"'");
				}
				else if(searchOper.equalsIgnoreCase("ew"))
				{
					str.append(" "+searchField+" like '%"+searchString+"'");
				}
				}
			  str.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			empList=session.createSQLQuery(str.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return empList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getRoasterList(boolean flag, String id, String deptLevel, SessionFactory connection)
	 {
		List roasterList = new ArrayList();
		String query="";
		if (flag) {
			query="select * from roaster_conf where dept_hierarchy="+deptLevel+" and dept_subdept in(select id from subdepartment where contact_sub_id="+id+")";
		}
		else {
			query="select * from roaster_conf where dept_hierarchy="+deptLevel+" and dept_subdept="+id+"";
		}
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			roasterList=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
		
		return roasterList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getValueId( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2, String fieldvalue3,String moduleName,SessionFactory connection)
	 {
		boolean flag =false;
		String Id=null,query="";
		List  list = new ArrayList();
		query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'";
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			list=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
		
		
		if (list!=null && list.size()<1) {
			list.clear();
			if (table.equalsIgnoreCase("feedback_type")) {
				query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,user_name,date,time,module_name) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"','"+moduleName+"');";
			}
			else if (table.equalsIgnoreCase("feedback_category")) {
				query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,user_name,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
			}
		    
			Session session1 = null;  
			Transaction transaction1 = null;  
			try 
			 {  
				session1=connection.getCurrentSession(); 
				transaction1 = session1.beginTransaction(); 
				Query qry=session1.createSQLQuery(query);
			    qry.executeUpdate();
				flag = true;
				transaction1.commit(); 
			 }
			catch (Exception ex)
				{transaction1.rollback();} 
		    if (flag) {
		    query="select max(id) from "+table+" where "+fieldName1+"="+fieldvalue1+"";
			Session session2 = null;  
			Transaction transaction2 = null;  
			try 
			 {  
				session2=connection.getCurrentSession(); 
				transaction2 = session2.beginTransaction(); 
				list=session2.createSQLQuery(query).list();  
				transaction2.commit(); 
			 }
			catch (Exception ex)
				{transaction2.rollback();} 
		   }
		}
		if (list!=null && list.size()>0) {
			Id=list.get(0).toString();
		}
		return Id;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getEmpValueId( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2, String fieldvalue3,String moduleName,SessionFactory connection)
	 {
		boolean flag =false;
		String Id=null,query="";
		List  list = new ArrayList();
		query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'";
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			list=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
		
		if (list!=null && list.size()>0) {
			Id=list.get(0).toString();
		}
		return Id;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getValueId4Asset( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2,String fieldName3, String fieldvalue3, String fieldvalue4,String moduleName,SessionFactory connection)
	 {
		boolean flag =false;
		String Id=null,query="";
		List  list = new ArrayList();
		query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'  and "+fieldName3+"='"+fieldvalue3+"'";
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			list=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
		
		
		if (list!=null && list.size()<1) {
			list.clear();
			if (table.equalsIgnoreCase("feedback_type")) {
				query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time,moduleName,asset) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue4+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"','"+moduleName+"','"+fieldvalue3+"');";
			}
			else if (table.equalsIgnoreCase("feedback_category")) {
				query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
			}
		    
			Session session1 = null;  
			Transaction transaction1 = null;  
			try 
			 {  
				session1=connection.getCurrentSession(); 
				transaction1 = session1.beginTransaction(); 
				Query qry=session1.createSQLQuery(query);
			    qry.executeUpdate();
				flag = true;
				transaction1.commit(); 
			 }
			catch (Exception ex)
				{transaction1.rollback();} 
		    if (flag) {
		    query="select max(id) from "+table+" where "+fieldName1+"="+fieldvalue1+"";
			Session session2 = null;  
			Transaction transaction2 = null;  
			try 
			 {  
				session2=connection.getCurrentSession(); 
				transaction2 = session2.beginTransaction(); 
				list=session2.createSQLQuery(query).list();  
				transaction2.commit(); 
			 }
			catch (Exception ex)
				{transaction2.rollback();} 
		   }
		}
		if (list!=null && list.size()>0) {
			Id=list.get(0).toString();
		}
		return Id;
	 }
	
	public SessionFactory session4File()
	{
		SessionFactory connection = null;
		String dbbname = "1_clouddb", ipAddress = null, username = null, paassword = null, port = null;
		try {
			  String dbDetails = new IPAddress().getDBDetails();
			  String db[]=dbDetails.split(",");
			  ipAddress=db[0];
			  username=db[1];
			  paassword=db[2];
			  port=db[3];
			
			  AllConnection Conn = new ConnectionFactory(dbbname,ipAddress, username, paassword, port);
			  AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
			  connection = Ob1.getSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	@SuppressWarnings("unchecked")
	public List getNormalDashboardData(String status,String fromDate, String toDate,SessionFactory connection)
	 {
		List  dashList = new ArrayList();
		StringBuffer query= new StringBuffer();
		query.append("select status,count(*) from feedback_status group by status order by status");
		if (status.equals("CL")) {
			query.append("inner join employee_basic as emp on emp.mobOne=feedback.feed_by_mobno"); 
		}
		else if (status.equals("CR")) {
			query.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
		}
			query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
			query.append("where feedback.open_date between '"+fromDate+"' and '"+toDate+"'");
			query.append("group by feedback.status order by feedback.status");
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dashList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
		
		return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeData(String uid, String deptLevel,SessionFactory connectionSpace) {
		List empList = new ArrayList();
		String empall = "";
		try {

				empall = " select emp.empName,emp.mobOne,emp.deptname,emp.empId,emp.emailIdOne,dept.deptName from employee_basic as emp"
					+ " inner join department as dept on emp.deptname=dept.id"
				    + " where emp.id='"+ uid + "'";
			empList = new createTable().executeAllSelectQuery(empall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	public List getData4EmailConfig(SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			  query.append("select email_register.emailid,email_register.password,email_register.mode,emp.id as empid,emp.empName,emp.mobOne,emp.emailIdOne,subdept.id as subdeptid,subdept.subdeptname,dept.id as deptid,dept.deptName,subcatg.id as subcatgid,subcatg.feedBreif,subcatg.escalateTime,subcatg.escalationLevel,subcatg.needEsc from email_registration as email_register");
			  query.append(" inner join employee_basic as emp on emp.id=email_register.empid");
			  query.append(" inner join subdepartment as subdept on subdept.id=emp.subdept");
			  query.append(" inner join department as dept on dept.id=subdept.deptid");
			  query.append(" inner join feedback_subcategory as subcatg on subcatg.id=email_register.subcatg");
			  list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptDashboardData(String qryfor,SessionFactory connection)
	 {
		List  dashDeptList = new ArrayList();
		String query="";
	    if (qryfor.equalsIgnoreCase("dept")) {
	    	query="select distinct dept.deptName,dept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' order by feedback.status";
		}
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	query="select distinct subdept.subdeptname,subdept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
		}
	    else if (qryfor.equalsIgnoreCase("counter")) {
	    	query="select distinct dept.deptName,dept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
		}
	    else if (qryfor.equalsIgnoreCase("subdept_counter")) {
	    	query="select distinct subdept.subdeptname,subdept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
		}
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dashDeptList=session.createSQLQuery(query).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return dashDeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getSubDeptList4FbType(String deptid,String module,String status, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
              {
            		query.append("select distinct subdept.id,subdept.subdept_name from subdepartment as subdept");
            		query.append(" inner join feedback_type as fbtype on subdept.id=fbtype.dept_subdept");
            		query.append(" inner join contact_sub_type as dept on subdept.contact_sub_id=dept.id");
            		query.append(" where dept.id='"+deptid+"' and fbtype.module_name='"+module+"' order by subdept.subdept_name;");
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
              }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
     }
	
	@SuppressWarnings("unchecked")
	public List getDashboardCounter(String qryfor,String status,String empName,String dept_subdeptid,SessionFactory connection)
	 {
		List  dashDeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		// Query for getting SubDept List
	     if (qryfor.equalsIgnoreCase("dept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status as feedback ");
	    	 query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    	 query.append(" inner join department dept on subdept.deptid= dept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    		 query.append(" where dept.id="+dept_subdeptid+" and feedback.moduleName='HDM' group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("Resolved")) {
	    		 query.append(" where dept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM' group by feedback.status order by feedback.status");
			}
	     }
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status as feedback ");
	    	 query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    		 query.append(" where subdept.id="+dept_subdeptid+" and feedback.moduleName='HDM'  group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("Resolved")) {
	    		 query.append(" where subdept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM'  group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("Normal")) {
	    		 query.append(" where feedback.feed_by='"+empName+"' and subdept.id="+dept_subdeptid+" and feedback.moduleName='HDM'  group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("empResolved")) {
	    		 query.append(" where feedback.feed_by='"+empName+"' and subdept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='HDM'  group by feedback.status order by feedback.status");
			}
	    }
	     
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dashDeptList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return dashDeptList;
	 }
	
	// get counts of tickets on the basis of status
	@SuppressWarnings("unchecked")
	public List getTicketsCounts(String countsfor,String UID,String fromDate,String toDate,SessionFactory connection)
	 {
		List  dashList = new ArrayList();
		StringBuffer query= new StringBuffer();
		// Query for getting SubDept List
		query.append("select feed_stats.status,count(*) from feedback_status as feed_stats");
		if (countsfor.equals("CL")) {
		      query.append(" inner join employee_basic as emp on emp.mobOne=feed_stats.feed_by_mobno");
		      query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
		      query.append(" where userac.id='"+UID+"' and");
		}
		else if (countsfor.equals("CR")) {
			  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
			  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
			  query.append(" where userac.id='"+UID+"' and");
		}
		else if (countsfor.equals("HOD")) {
			  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
			  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
			  query.append(" where userac.id='"+UID+"' and feed_stats.to_dept_subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id= emp.subdept)) and");
		}
		else if (countsfor.equals("MGMT")) {
			 query.append(" where ");
		}
		 query.append("  open_date between '"+fromDate+"' and '"+toDate+"'");
		 query.append(" group by status order by status");
	   
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dashList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket(String seriesType,String deptid,String moduleName, SessionFactory connection)
	 {
		 String ticketno="",query="";
		 List list =null;
		 if (seriesType.equals("N")) {
			 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(id) from feedback_status where moduleName='"+moduleName+"')";
		}
		 else if (seriesType.equals("D")) {
			 if(moduleName.equalsIgnoreCase("FM"))
			 {
				 query = "select feed_status.ticket_no from feedback_status_pdm as feed_status"
	                 +" where feed_status.id=(select max(id) from feedback_status_pdm where to_dept_subdept in (select id from contact_sub_type where id="+deptid+")) and stage='2' ";
			 }
			 else if(moduleName.equalsIgnoreCase("HDM"))
			 {
				 query = "select feed_status.ticket_no from feedback_status as feed_status"
	                 +" where feed_status.id=(select max(id) from feedback_status where moduleName='"+moduleName+"' and to_dept_subdept in (select id from subdepartment where deptid="+deptid+"))";
			
			}
		 }
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(query).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			
			
			 if(list!=null && list.size()>0)
	            {
				  ticketno=list.get(0).toString();
	            }
			return ticketno;
	 }
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket4Email(SessionFactory connection)
	 {
		 String ticketno="",query="";
		 List list =null;
			 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(id) from feedback_status where via_from='email')";
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(query).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			
			
			 if(list!=null && list.size()>0)
	            {
				  ticketno=list.get(0).toString();
	            }
			return ticketno;
	 }
	
	@SuppressWarnings("unchecked")
	public String getTicketLevel(String empid,SessionFactory connection)
	 {
		 String ticketlevel="";
		 StringBuilder sb =new StringBuilder();
		 List list =null;
		 sb.append("select contact.level from employee_basic as emp");
		 sb.append(" inner join compliance_contacts as contact on emp.id=contact.emp_id");
		 sb.append(" where emp.id='"+empid+"'");
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(sb.toString()).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			
			
			 if(list!=null && list.size()>0)
	            {
				 ticketlevel="Level"+list.get(0).toString();
	            }
			return ticketlevel;
	 }
	
	@SuppressWarnings("unchecked")
	public String getComplTicketSeries(String seriesType,String deptId,SessionFactory connectionSpace)
	{
		String finalString=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder builder=new StringBuilder();
			if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
			{
				builder.append("SELECT MAX(compid_suffix) FROM compliance_details AS cd ");		
				builder.append("INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE ct.departName="+deptId);
			}
			else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
			{
				builder.append("SELECT MAX(compid_suffix) FROM compliance_details");		
			}
			
			List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
			if(maxSuffixList!=null && maxSuffixList.size()>0)
			{
				String suffix=null;
				if (maxSuffixList.get(0)!=null && !maxSuffixList.get(0).toString().equals("")) {
					 suffix=maxSuffixList.get(0).toString();
				}
				if(suffix!=null && !suffix.equalsIgnoreCase("0"))
				{
					String prefix=getComplPrefix(deptId,seriesType,connectionSpace);
					if(prefix!=null)
					{
						finalString=prefix+suffix;
					}
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return finalString;
	}
	
	@SuppressWarnings("unchecked")
	public String getAssetTicketSeries(String seriesType,String deptid,SessionFactory connectionSpace)
	{
		String finalString=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder builder=new StringBuilder();
			if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
			{
				builder.append("select feed_status.ticket_no from asset_complaint_status as feed_status");		
				builder.append(" where feed_status.id=(select max(id) from asset_complaint_status  where to_dept="+deptid+")");
			}
			else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
			{
				builder.append("select feed_status.ticket_no from asset_complaint_status as feed_status");
				builder.append(" where feed_status.id=(select max(id) from asset_complaint_status)");		
			}
			List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
			if(maxSuffixList!=null && maxSuffixList.size()>0)
			{
					finalString=maxSuffixList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return finalString;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAssetSNOSeries(String seriesType,String deptid,SessionFactory connectionSpace)
	{
		String finalString=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder builder=new StringBuilder();
			if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
			{
				builder.append("select asset.serialno from asset_detail as asset");		
				builder.append(" where asset.id=(select max(id) from asset_detail  where dept_subdept="+deptid+")");
			}
			else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
			{
				builder.append("select asset.serialno from asset_detail as asset");		
				builder.append(" where asset.id=(select max(id) from asset_detail)");
			}
			List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
			if(maxSuffixList!=null && maxSuffixList.size()>0)
			{
					finalString=maxSuffixList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return finalString;
	}
	
	@SuppressWarnings("unchecked")
	public String getComplPrefix(String deptId,String seriesType,SessionFactory connectionSpace)
	{
		String prefix=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder builder=new StringBuilder();
			if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
			{
				builder.append("SELECT compid_prefix FROM compliance_details AS cd ");		
				builder.append("INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE ct.departName="+deptId+" LIMIT 1");
			}
			else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
			{
				builder.append("SELECT compid_prefix FROM compliance_details LIMIT 1");		
			}
			List maxPrefixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
			if(maxPrefixList!=null && maxPrefixList.size()>0)
			{
				prefix=maxPrefixList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return prefix;
	}
	
	@SuppressWarnings("unchecked")
	public String getMailConfig(String name,String title,String status,String deptLevel,boolean flag)
	 {
		List orgData= new LoginImp().getUserInfomation("1", "IN");
		String orgName="";
		if (orgData!=null && orgData.size()>0) {
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				orgName=object[0].toString();
			}
		}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	  /*  if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(name)+ ",</b>");
	    }
	    else if (!flag) {*/
	    mailtext.append("<b>Dear " +DateUtil.makeTitle(name)+ ",</b>");	
		//}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
		}
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getConfigMessage(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag) {
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
		}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
		}
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_date()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
		if (deptLevel.equals("SD")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Sub-Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_subdept() + "</td></tr>");
		}
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		
		
		if (flag) {
		 if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
		     mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+feedbackObj.getEscalation_date()+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		 }
		}
		else
		 {
		   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
		      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve&nbsp;Up&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+feedbackObj.getEscalation_date()+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		   }
		  }
		
		if (status.equalsIgnoreCase("High Priority")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
		}
		else if (status.equalsIgnoreCase("Snooze")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + " Hrs</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Ignore")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Resolved")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> TAT:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_duration() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
		}
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String configMessage4Escalation(FeedbackPojo feedbackObj,String title,String escalateEmp,String deptLevel,boolean flag) {
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<b>Dear " +DateUtil.makeTitle(escalateEmp)+ ",</b>");
	   
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	   
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_date()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
		if (deptLevel.equals("2")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Sub-Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_subdept() + "</td></tr>");
		}
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;Allot&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.makeTitle(feedbackObj.getFeedback_allot_to()) + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+feedbackObj.getEscalation_date()+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getConfigMailForReport(int pc,int hc,int sc,int ic,int rc,int total,int cfpc,int cfsc,int cfhc,int cftotal,String empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo> currentDayPendingData,List<FeedbackPojo> currentDaySnoozeData,List<FeedbackPojo> currentDayHPData,List<FeedbackPojo> currentDayIgData,List<FeedbackPojo> CFData)
	  {
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
        mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Summary Status For All Department On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime()+"</b></td></tr></tbody></table>");
		 
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+hc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+sc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ic+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+rc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfpc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfsc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfhc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+total+"</td></tr></tbody></table>");
	     
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'>"
        		+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>"
        		+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
        		+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>"
        		+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
        		+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>"
        		+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
        		+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
        if (currentDayResolvedData!=null && currentDayResolvedData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayResolvedData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayPendingData!=null && currentDayPendingData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayPendingData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDaySnoozeData!=null && currentDaySnoozeData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDaySnoozeData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayHPData!=null && currentDayHPData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayHPData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayIgData!=null && currentDayIgData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayIgData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (CFData!=null && CFData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:CFData)
       	  {
     		 //System.out.println("To Sub Department>>"+FBP.getFeedback_to_subdept());
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'>"
							+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td>"
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+" "+FBP.getFeedback_by_dept()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+" "+FBP.getOpen_time()+"</td>"
							+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_subcatg()+"</td> "
							+ "<td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_subdept()+" "+FBP.getFeedback_allot_to()+"</td>"
							+ "<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td>"
							+ "<td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
        return mailtext.toString() ; 
	  }
	
	@SuppressWarnings("unchecked")
	public synchronized static String getTicketNo(String deptid,String moduleName,SessionFactory connectionSpace)
	 {
		String ticketno="NA",ticket_type="",ticket_series="",prefix="",sufix="",ticket_no="";
		List ticketSeries = new ArrayList();
		List deptTicketSeries =new ArrayList();
		try {
			   // Code for getting the Ticket Type from table
			  // ticketSeries = new HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
			   ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf","moduleName",moduleName, "", "",connectionSpace);
			   if (ticketSeries!=null && ticketSeries.size()==1) {
				for (Iterator iterator = ticketSeries.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					ticket_type=object[1].toString();
					ticket_series=object[2].toString();
				}
			  // Code for getting the first time counters of Feedback Status table, If get ticket counts greater than Zero than go in If block  and if get 0 than go in else block 	
				if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
					ticket_no= new HelpdeskUniversalHelper().getComplTicketSeries(ticket_type,deptid,connectionSpace);
				}
				else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
					ticket_no= new HelpdeskUniversalHelper().getAssetTicketSeries(ticket_type,deptid,connectionSpace);
				}
				else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
					ticket_no= new HelpdeskUniversalHelper().getAssetSNOSeries(ticket_type,deptid,connectionSpace);
				}
				else {
					ticket_no= new HelpdeskUniversalHelper().getLatestTicket(ticket_type,deptid,moduleName, connectionSpace);
				}
			  
			  if (ticket_no!=null && !ticket_no.equals("")) {
				  if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N")) {
					  if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
							 prefix=ticket_no.substring(0, 4);
							 sufix=ticket_no.substring(4, ticket_no.length());
						}
					  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) {
							 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
							 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
						}
					  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
						     prefix=ticket_no.substring(0, 3);
							 sufix=ticket_no.substring(3, ticket_no.length());
						}
					  else {
							 prefix=ticket_no.substring(0, 2);
							 sufix=ticket_no.substring(2, ticket_no.length());
						}
					  setAN(Integer.parseInt(sufix));
					  ticketno=prefix+ ++AN;
				 }
				 else if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D")) {
					 if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
						 prefix=ticket_no.substring(0, 4);
						 sufix=ticket_no.substring(4, ticket_no.length());
					}
					 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) {
						 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
						 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
					}
					 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
					     prefix=ticket_no.substring(0, 3);
						 sufix=ticket_no.substring(3, ticket_no.length());
					}
					 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
					     prefix=ticket_no.substring(0,11);
						 sufix=ticket_no.substring(11, ticket_no.length());
					}
					 else {
						 prefix=ticket_no.substring(0, 2);
						 sufix=ticket_no.substring(2, ticket_no.length());
					}
					  setAN(Integer.parseInt(sufix));
					  ticketno=prefix+ ++AN;
				 }
			  }
			  else {
						if (ticket_type.equalsIgnoreCase("N")) {
							if (ticket_series!=null && !ticket_series.equals("") && !ticket_series.equals("NA")) {
								ticketno=ticket_series;
							}
						}
						else if (ticket_type.equalsIgnoreCase("D")) {
							deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid,"moduleName",moduleName, "prefix", "ASC",connectionSpace);
	                        if (deptTicketSeries!=null && deptTicketSeries.size()==1) {
							for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();) {
								Object[] object1 = (Object[]) iterator2.next();
								if (object1[2]!=null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA")) {
									ticketno=object1[2].toString()+object1[3].toString();
								}
								else {
									ticketno="NA";
								}
							}
	                      }
						}
					 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketno;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield,String searchfieldvalue, String orderfield, String order,SessionFactory connectionSpace) {
		List SubdeptallList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			query.append("select * from " + table + " where "+ searchfield + "='" + searchfieldvalue + "'");
			if (orderfield!=null && !orderfield.equals("") && order!=null && !order.equals("")) {
				query.append(" ORDER BY "+ orderfield + " " + order + "");
			}
			SubdeptallList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SubdeptallList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield,String searchfieldvalue,String searchfield1,String searchfieldvalue1, String orderfield, String order,SessionFactory connectionSpace) {
		List SubdeptallList = new ArrayList();
		try {
			String query = "select * from " + table + " where "+ searchfield + "='" + searchfieldvalue + "' and "+ searchfield1 + "='" + searchfieldvalue1 + "' ORDER BY "+ orderfield + " " + order + "";
			SubdeptallList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SubdeptallList;
	}
	
	@SuppressWarnings("unchecked")
	public String getDesignationLevel(String userName,SessionFactory connectionSpace)
	{
		String level=null;
		try
		{ 
			String encptUid=Cryptography.encrypt(userName,DES_ENCRYPTION_KEY);
			StringBuilder query1=new StringBuilder("");
			query1.append("SELECT design.levelofhierarchy FROM designation AS design INNER JOIN employee_basic AS emp ON emp.designation=design.id " +
					"INNER JOIN useraccount AS ac ON ac.id=emp.useraccountid WHERE ac.userID='"+encptUid+"'");
			List dataList=new createTable().executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				level=dataList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return level;
	}
	
	
	
	
	// Get Service Department List
	@SuppressWarnings("unchecked")
	public List getServiceDept_SubDept(String orgLevel,String orgId,String module, SessionFactory connection) {

		List dept_subdeptList = null;
		StringBuilder qry= new StringBuilder();
		try {
			if (module.equalsIgnoreCase("COMPL")) {
				qry.append("select distinct dept.id,dept.contact_subtype_name from otm_task as compl");
				qry.append(" inner join contact_sub_type as dept on compl.sub_type_id=dept.id");
				qry.append(" ");
			}
			else if (module.equalsIgnoreCase("HDM")) {
				    qry.append(" select distinct dept.id,dept.contact_subtype_name from contact_sub_type as dept");
				    qry.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
				    qry.append(" inner join feedback_type as feed on subdept.id=feed.dept_subdept");
			        qry.append(" where  feed.hide_show='Active' and feed.module_name='"+module+"' ORDER BY dept.contact_subtype_name ASC");
			}
			else if (module.equalsIgnoreCase("HDM") || module.equalsIgnoreCase("CASTM")) {
			    qry.append(" select distinct dept.id,dept.contact_subtype_name from feedback_type as feed");
 				qry.append(" inner join contact_sub_type as dept on feed.dept_subdept=dept.id");
		        qry.append(" where  feed.hide_show='Active' and feed.module_name='"+module+"' ORDER BY dept.contact_subtype_name ASC");
		    }
			else if(module.equalsIgnoreCase("FM") )
			{
				// Added by Avinash for FM 
			    qry.append(" select distinct dept.id,dept.contact_subtype_name from feedback_type as feed");
				qry.append(" inner join contact_sub_type as dept on feed.dept_subdept=dept.id");
		        qry.append(" where feed.hide_show='Active' and feed.module_name='"+module+"' ORDER BY dept.contact_subtype_name ASC");
			}
			else if(module.equalsIgnoreCase("ASTM") )
			{}
			else {
			    qry.append(" select distinct dept.id,dept.contact_subtype_name from contact_sub_type as dept");
		        qry.append("  ORDER BY dept.contact_subtype_name ASC");
		}
				Session session = null;
				Transaction transaction = null;
				session = connection.getCurrentSession();
				transaction = session.beginTransaction();
				//System.out.println("qry::::"+qry);
				dept_subdeptList = session.createSQLQuery(qry.toString()).list();
				transaction.commit();

		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
		return dept_subdeptList;
	
	}
	
	@SuppressWarnings("unchecked")
	public boolean check_Table(String table, SessionFactory connection)
	 {
		boolean flag = false;
		List list = null;
		String value = null;
		Session session = null;  
		Transaction transaction = null;  
		try {
			SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
			Properties props = sessionFactoryImpl.getProperties();
			String url = props.get("hibernate.connection.url").toString();
			String[] urlArray = url.split(":");
			String db_name = urlArray[urlArray.length - 1];
			String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
		    session=connection.getCurrentSession(); 
		    transaction = session.beginTransaction(); 
		    list=session.createSQLQuery(query).list();  
			transaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
			if (list!=null && list.size()>0) {
				value = list.get(0).toString();
				if (value.equalsIgnoreCase("1")) {
					flag=true;
				}
			}
		return flag;
	 }
	
	
	
	public synchronized static String getReAssignedTicketNo(String ticket_no)
	 {
		String ticketno="NA",prefix="",sufix="",start="";
		try {
			   // Code for getting the Ticket Type from table
					if (ticket_no!=null && !ticket_no.equals("") && !ticket_no.equals("NA")) {
						 
						 start= ticket_no.substring(0, 5);
						 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
						 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
					
					  setAN(Integer.parseInt(sufix));
					  ticketno=start+prefix+ ++AN;
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketno;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept,String module, String level,String floor_status,String floor,SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		//String qry = null;
		StringBuilder query =new StringBuilder();
		try {
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
			query.append("select distinct emp.id from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
			if (module!=null && !module.equals("") && module.equals("HDM")) {
				query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
				query.append(" inner join department dept on sub_dept.deptid = dept.id ");
				query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
			}
			else if (module!=null && !module.equals("") && module.equals("FM")) {
				query.append(" inner join department dept on roaster.dept_subdept = dept.id ");
				query.append(" inner join shift_conf shift on dept.id = shift.dept_subdept ");
			}
				query.append(" where shift.shiftName="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
				query.append(" and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
			if (module!=null && !module.equals("") && module.equals("HDM")) {
				if (floor_status.equalsIgnoreCase("Y")) {
					query.append(" and roaster.floor='"+floor+"'  and dept.id=(select deptid from subdepartment where id='"+ dept_subDept+ "')");
				}
				else if (floor_status.equalsIgnoreCase("N")) {
					query.append(" and sub_dept.id='"+ dept_subDept+ "'");
				}
			}
			else if (module!=null && !module.equals("") && module.equals("FM")) {
				query.append(" and dept.id='"+ dept_subDept+ "'");
			}
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept,
			String deptLevel, String toLevel,List empId,String module, SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		String arr =empId.toString().replace("[", "(").replace("]", ")");
		try {
			query.append("select distinct allot_to from feedback_status as feed_status");
			if (module!=null && !module.equals("") && module.equalsIgnoreCase("HDM")) {
				query.append(" inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id ");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
				query.append(" where sub_dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
			}
			else if (module!=null && !module.equals("") && module.equalsIgnoreCase("FM")) {
				query.append(" inner join department dept on feed_status.to_dept_subdept = dept.id ");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
				query.append(" where dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
			}
			query.append(" and feed_status.moduleName='"+ module + "'");
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String subdept,String module,SessionFactory connectionSpace) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String qry = null;
		try {
			session = HibernateSessionFactory.getSession();
			qry = "select distinct allot_to from feedback_status where open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' and to_dept_subdept="+subdept+" and status='Pending' and moduleName='"+module+"'";
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String getRandomEmployee(String module, List empId,SessionFactory connectionSpace) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String allotmentId = "";
		StringBuilder sb = new StringBuilder();
		try {
			session = HibernateSessionFactory.getSession();
			sb.append("select allot_to from feedback_status where open_date='"+ DateUtil.getCurrentDateUSFormat()+ "' and moduleName='"+module+"'");
			sb.append(" group by allot_to having allot_to in "+ empId.toString().replace("[", "(").replace("]", ")")+ " order by count(allot_to) limit 1 ");
			
			list = new createTable()
					.executeAllSelectQuery(sb.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				allotmentId = object.toString();
			}
		}
		return allotmentId;
	}
	@SuppressWarnings("unchecked")
	public String getConfigMessage4Asset(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag) {
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
		}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
		}
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getOpen_date())+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		
		
		if (flag) {
		 if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
		     mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		 }
		}
		else
		 {
		   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
		      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve&nbsp;Up&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		   }
		  }
		
		if (status.equalsIgnoreCase("High Priority")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
		}
		else if (status.equalsIgnoreCase("Snooze")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + " Hrs</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Ignore")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Resolved")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> TAT:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_duration() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
		}
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}

	public static int getAN()
	{
		return AN;
	}

	public static void setAN(int an)
	{
		AN = an;
	}
	
	@SuppressWarnings("unchecked")
	public String getConfigMailForMorningReport(List morninglist,String empName)
	  {
		  List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
        mailtext.append("<b>Dear "+DateUtil.makeTitle(empName)+",</b>");
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Open Ticket Detail</b></td></tr></tbody></table>");
		
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket No</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Lodge By</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Open At</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>For</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Loaction</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Status</b></td></tr>");
      //System.out.println("******Sized : "+morninglist.size());
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
		{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Pending"))
			 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",       "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",       "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getEmpName()+",       "+object1.getFeedback_to_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
        	}catch(Exception e){e.printStackTrace();}
		}  
       // mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
		{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("High Priority"))
        		 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
                	}catch(Exception e){e.printStackTrace();}
		}  
        
     //   mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
		{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Snooze"))
        		 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
                 	}catch(Exception e){e.printStackTrace();}
		}  
       // mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
		{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Hold"))
        		 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
              	}catch(Exception e){e.printStackTrace();}
		}  
        
         mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
        	return mailtext.toString() ; 
	  }
	
	/*public AtomicInteger getAN() {
		return AN;
	}
	public void setAN(AtomicInteger an) {
		AN = an;
	}*/
	
	
}
