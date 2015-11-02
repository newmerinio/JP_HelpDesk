package com.Over2Cloud.ctrl.ExcelDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadProductExcel extends ActionSupport{
	
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private File cAExcel;
    private String cAExcelContentType;
    private String cAExcelFileName;
    public Map<String, String> productdetails=null;
    public List<Object> gridmodulelistObject;
    public Map<String,Object> mappclumObject;
    private Integer rows=0;
    private Integer total=0;
    private Integer records=0;
    private Integer page=0;
    private String selectedId=null;
	@SuppressWarnings({ "unchecked", "unchecked" })
	public String productUplodeExcel() throws Exception
	{
	
		String returnResult = ERROR;
		List<String> columnlistObjt=new ArrayList<String>();
		Map<String, Object> Mappobject=new LinkedHashMap<String, Object>();
		List<String> gridlistObject  = new ArrayList<String>();
		productdetails = new LinkedHashMap<String, String>();
		try {
			if (cAExcel != null) {
				InputStream inputStream = new FileInputStream(cAExcel);
				try{
					/** 
					 * read parameter from the table mapped_contactdetails_configuration and add HashMap
					 *
					 */	
					List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mappedproductsetting_configuration", accountID, connectionSpace,"configuration_productmgmt");
				int l=0;
				for (GridDataPropertyView col : basicfieldNames) {
				    if(col!=null) {
					    if(l<basicfieldNames.size()-1){
					    	productdetails.put(col.getColomnName(), col.getHeadingName());}
						    else{
						    	productdetails.put(col.getColomnName(), col.getHeadingName());
					    	 }}
				    l++;
			      }}catch (Exception e) {
					// TODO: handle exception
				   }
			
				if(cAExcelFileName.contains(".xlsx")){
					GenericReadExcel7 GRE7 = new GenericReadExcel7();
					XSSFSheet excelSheet = GRE7.readExcel(inputStream);
					for (int rowIndex = 1; rowIndex < excelSheet
					.getPhysicalNumberOfRows(); rowIndex++) {
						XSSFRow row = excelSheet.getRow(rowIndex);
				        int cellNo = row.getLastCellNum();
				         if (cellNo > 0) {
				        		/** 
			 					 *Here Read Header Parameter and put in Map oBject and List Object
			 					 *
			 					 */	
				        		 if(rowIndex==1){
				        			  Mappobject.put("id", "Id");
					    			  columnlistObjt.add("id");
				        				/** 
					 					 *check the header field matches that of the Map Object 
					 					 *
					 					 */	
				        	           try{
					        	             for(int cellIndex = 0; cellIndex < cellNo; cellIndex++) {
					        					    Iterator<Entry<String, String>> hiterator=productdetails.entrySet().iterator();
					        					    while (hiterator.hasNext()) {
					        						Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
					        						if(GRE7.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim())){
					        							/** 
									 					 *header field matches from header Put Into List and Map Object 
									 					 *
									 					 */	    
					        							System.out
																.println("Keyy"+paramPair.getKey());
					        							     columnlistObjt.add(paramPair.getKey());
					        							     Mappobject.put(paramPair.getKey(), paramPair.getValue().toString());
					        							    }
					        						
					        						}}setMappclumObject(Mappobject);}
					        	            	      catch (Exception e) {
					        	            		  e.printStackTrace();
													// TODO: handle exception
												}
					        		     }else{
					        		    	     /** 
							 					 *Read Row data from Excel and put into List Object
							 					 *
							 					 */	
					        			    try{
				        			          for(int cellIndex = 0; cellIndex < cellNo; cellIndex++) {
				        			             if(GRE7.readExcelSheet(row, cellIndex)!=null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase("")){
				        			             /** 
							 					 * add source code at here and check what column value you have get
							 					 * 
							 					 * if you have drop type value just check it and pick id of that value and 
							 					 *  and add in List Object ! 
							 					 *
							 					 */	
				        			        	  gridlistObject.add(GRE7.readExcelSheet(row, cellIndex).toString()); 
				        			            }  else{
						        			      gridlistObject.add("NA");}
				        			        }
				        			      }catch (Exception e) {
				        			    	e.printStackTrace();
											// TODO: handle exception
										}
				        			 
					        		 }
				        		  }
				        		}
				        	
					    }else{
					    
					    	GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
					    	HSSFSheet excelShee = GRBE.readExcel(inputStream);
							for (int rowIndex = 1; rowIndex < excelShee
							.getPhysicalNumberOfRows(); rowIndex++) {
								HSSFRow row = excelShee.getRow(rowIndex);
						        int cellNo = row.getLastCellNum();
						         if (cellNo > 0) {
						    		 if(rowIndex==1){
						    			  Mappobject.put("id", "Id");
						    			  columnlistObjt.add("id");
						        	          try{
						        	        	
						        	             for(int cellIndex = 0; cellIndex < cellNo; cellIndex++) {
						        					   Iterator<Entry<String, String>> hiterator=productdetails.entrySet().iterator();
						        					    while (hiterator.hasNext()) {
						        						Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        						if(GRBE.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim())){
						        					   
						        							columnlistObjt.add(paramPair.getKey());
						        							Mappobject.put(paramPair.getKey(), paramPair.getValue().toString());
						        							     
						        							     
						        							     
						        							    }
						        						}}setMappclumObject(Mappobject);}
						        			    
						        	            	  catch (Exception e) {
						        	            		  e.printStackTrace();
														// TODO: handle exception
													}
						        		      } else{
						        		    	 try{
						        		    	   gridlistObject.add(String.valueOf(rowIndex));
						        			       for(int cellIndex = 0; cellIndex < cellNo; cellIndex++) {
						        			    	   
						        			    	   if(GRBE.readExcelSheet(row, cellIndex)!=null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase("")){
						        			        	gridlistObject.add(GRBE.readExcelSheet(row, cellIndex).toString()); }
						        			        else{
						        			    	   gridlistObject.add("NA");}
						        			    }
						        			 } catch (Exception e) {
						        				 e.printStackTrace();
						        			 }
												// TODO: handle exception
											}
						    		 
						                 }
	        						 }
							
             					 if (gridlistObject.size() > 0) {
        							returnResult=SUCCESS;
        							session.put("gridlistObject", gridlistObject);
        							session.put("columnlistObjt", columnlistObjt);
							      }
					    }}}
						        
		   catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
		
		return returnResult;
	}

	
	@SuppressWarnings("unchecked")
	public String showconfirmationdata(){
		String returnResult = ERROR;
		try{
			List<String> ObjectListdata = (List<String>) session.get("gridlistObject");
			List<String> listdataa = (List<String>) session.get("columnlistObjt");
			List<Object> Listhb=new ArrayList<Object>();
			if (ObjectListdata != null && ObjectListdata.size() > 0) {
				setRecords(ObjectListdata.size());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				if(ObjectListdata.size()>0){
							int i=0;
							Map<String, Object> one=new HashMap<String, Object>();
							String [] fieldsArray = listdataa.toArray(new String[listdataa.size()]);
							String [] fieldsArrayss = ObjectListdata.toArray(new String[ObjectListdata.size()]);
							if(fieldsArrayss!=null && fieldsArray!=null){
								 
							for (int k = 0; k < fieldsArrayss.length; k++) {
								one.put(fieldsArray[i].toString().trim(), fieldsArrayss[k].toString());
								i++;
								if(i==fieldsArray.length){
									i=0;
									Listhb.add(one);
									one=new HashMap<String, Object>();}
							    }
							setGridmodulelistObject(Listhb);
						  }
						}	
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			
			returnResult = SUCCESS;
			
			}catch (Exception e) {
				e.printStackTrace();
			// TODO: handle exception
		}
		return returnResult;
		
	}
	  
	
	
	@SuppressWarnings("unchecked")
	public String saveExcelData(){
		System.out.println("saveExcelDataURL_data>>>");
		String returnResult = ERROR;
		boolean status =false;
		CommonforClassdata obhj = new CommonOperAtion();
		try {	
		List<String> ObjectListdata = (List<String>) session.get("gridlistObject");
		List<String> listdataa = (List<String>) session.get("columnlistObjt");
	    session.remove("gridlistObject");
	    session.remove("columnlistObjt");
		String [] fieldsArray = listdataa.toArray(new String[listdataa.size()]);
		String [] dataArrayss = ObjectListdata.toArray(new String[ObjectListdata.size()]);
		   	  List<String> basicfeildsList=new ArrayList<String>();
		   	  List<String>  addressfeildsList=new ArrayList<String>();
		    		try{
		    			productdetails = new LinkedHashMap<String, String>();
						List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mappedproductsetting_configuration", accountID, connectionSpace,"configuration_productmgmt ");
						int l=0;
						for (GridDataPropertyView col : basicfieldNames) {
						    if(col!=null) {
							    if(l<basicfieldNames.size()-1){
							    	productdetails.put(col.getColomnName(), col.getHeadingName());}
								    else{
								    	productdetails.put(col.getColomnName(), col.getHeadingName());
							    	 }}
						    l++;
					      }}catch (Exception e) {
							// TODO: handle exception
						   }
					      basicfeildsList.add("id");
					    for(int cellIndex = 0; cellIndex < fieldsArray.length; cellIndex++) {
					    Iterator<Entry<String, String>> hiterator=productdetails.entrySet().iterator();
  					    while (hiterator.hasNext()) {
  						Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
  						        if(fieldsArray[cellIndex].toString().trim().equals(paramPair.getKey().toString().trim())){
  								  basicfeildsList.add(paramPair.getKey());
  					            	}
  							     }
  				           }   
		             int k=0;
		       	     List <InsertDataTable> insertData= new ArrayList<InsertDataTable>();
		             for(int i=0; i<dataArrayss.length; i++){
		                  InsertDataTable ob = new InsertDataTable();
		            	  if(!basicfeildsList.get(k).equals("id")){
							ob.setColName(basicfeildsList.get(k));
							ob.setDataName(dataArrayss[i]);
							insertData.add(ob);
		            	  }
		            	k++;
		            	if(k == basicfeildsList.size()){
		            		status = obhj.insertIntoTable("createtproductdetails_table", insertData,connectionSpace);
		            		i +=   addressfeildsList.size();
		            		k=0;
		            		  insertData= new ArrayList<InsertDataTable>();
		            	}
		             }
		             if(status){
		            	 System.out.println("hhhhhhhhhhh");
			        	 addActionMessage("DataUplod Successfully");
			         }else{
			        	 addActionError("There Is Some Problem In Uploading data");
			         }
			           
		            
		        }
		   
		    catch (Exception e) {
		    	  e.printStackTrace();
		      }
				// TODO: handle exception
			 
		    returnResult = SUCCESS;

            System.out.println("returnResult>>>>>>"+returnResult);
			return returnResult;
		}	
	
	public List<Object> getGridmodulelistObject() {
		return gridmodulelistObject;
	}


	public void setGridmodulelistObject(List<Object> gridmodulelistObject) {
		this.gridmodulelistObject = gridmodulelistObject;
	}



	public File getCAExcel() {
		return cAExcel;
	}

	public void setCAExcel(File excel) {
		cAExcel = excel;
	}

	public String getCAExcelContentType() {
		return cAExcelContentType;
	}

	public void setCAExcelContentType(String excelContentType) {
		cAExcelContentType = excelContentType;
	}

	public String getCAExcelFileName() {
		return cAExcelFileName;
	}

	public void setCAExcelFileName(String excelFileName) {
		cAExcelFileName = excelFileName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}


	public Map<String, Object> getMappclumObject() {
		return mappclumObject;
	}

	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	public void setMappclumObject(Map<String, Object> mappclumObject) {
		this.mappclumObject = mappclumObject;
	}
	
}
