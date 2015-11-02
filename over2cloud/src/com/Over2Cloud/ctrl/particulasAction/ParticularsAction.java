package com.Over2Cloud.ctrl.particulasAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.FilesUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ParticularsAction extends ActionSupport implements ServletRequestAware{
	
	Map session = ActionContext.getContext().getSession();
    String userName=(String)session.get("uName");
    String accountID=(String)session.get("accountid");
    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    private List<ConfigurationUtilBean> reibursementTextBox=null;
    private List<ConfigurationUtilBean> reibursementDropDown=null; 
    private List<ConfigurationUtilBean> reibursementFile=null;
    private List<ConfigurationUtilBean> particularsTextBox=null;
    private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
    
 //table field in particulars_data
    private String particulars;
    private String userName1=userName;
    private String created_date;
    private String created_time;
    private String purpose;
    private String refDocName;
    private String other;
    private String amount;
    private String totalamount;
    int sum_totalaoumt=0;
   // private String refWithRec;
    private String purpose_id;
    private String deptName="";
    //File Upload Variables
    private File[] upload;
    private String[] uploadFileName;
    private String[] uploadContentType;
    
    private String formatImage;
    /*private File upload;
    private String uploadFileName;
    private String uploadContentType;*/
    
    //Grid View
    List<Object> gridModel;
	private Map<Integer, String> purposename=null;
	Map<String,String> attachFileMap;
	
int detected_amount=0;
    
private Integer rows = 0;
// Get the requested page. By default grid sets this to 1.
private Integer page = 0;
// sorting order - asc or desc
private String sord = "";
// get index row - i.e. user click to sort.
private String sidx = "";
// Search Field
private String searchField = "";
// The Search String
private String searchString = "";
// The Search Operation
// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
private String searchOper = "";
// Your Total Pages
private Integer total = 0;
// All Record
private Integer records = 0;
private boolean loadonce = false;
//Grid colomn view
private String oper;
private String id;
 // Header name------
 private String mainHeaderName; 
 private String flage;
 private HttpServletRequest request;
 Map<String, Object>userdata = new HashMap<String, Object>();
	// private String id;
		private String fileName;
	    private InputStream fileInputStream;
	 
 public String getReimbDetail4Print123()
		{
	    	System.out.println("hello getReimbDetail4Print123() method jiiiiiiii>>>");
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					setReimbusementViewProp();
					reimbursementViewDataInGrid();
					
				 return	SUCCESS;
				}
				catch (Exception ex) 
				{
					ex.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				
			}
			return LOGIN;
     }

public String beforeReimbursementdetails()
	 {
		 boolean valid=ValidateSession.checkSession();
		 if(valid)
		 {
			 try
			 {
				 return SUCCESS;
			 }catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			 
		 }return LOGIN;
	 
	 }
	 
	 public String beforeReimbursementview()
	 {
		 
		 boolean valid=ValidateSession.checkSession();
		 if(valid)
		 {
			 try
			 {
				 reimbursementViewDataInGrid();
				 setReimbusementViewProp();
				 return SUCCESS;
			 }catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			 
		 }return LOGIN;
	 }
	 public void setReimbusementViewProp()
	 {String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("SNo.");
		gpv.setHideOrShow("true");
		gpv.setSearch("true");
		masterViewProp.add(gpv);
		try
		{
		List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_reimbursement_configuration",accountID,connectionSpace,"",0,"table_name","reimbursement_configuration");
		if(statusColName!=null&&statusColName.size()>0)
		{
			for(GridDataPropertyView gdp:statusColName)
				if(!gdp.getColomnName().equalsIgnoreCase("purpose"))
			{
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				//gpv.setFormatter(gdp.getFormatter());//for fine link
				masterViewProp.add(gpv);
				//System.out.println("masterViewProp>>>>>>>>"+masterViewProp);
				//System.out.println("gpv.getFormatter()>gdp.getColomnName()>>>>>>>>>>>>>>"+gpv.getFormatter());
				//System.out.println("gdp.getColomnName()>?>>>>>>."+gdp.getColomnName());
			}
	             
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	 } 
	 
	 public String downloadFile() 
		{
		// System.out.println("download()>>>>>>>>>>>>>>>>>>>>>>>> ");
		       boolean sessionFlag=ValidateSession.checkSession();
		       if (sessionFlag) 
		       {
			        try
			        {
				          if(getId()!=null)
				          {
				            fileName=getId();
			            	File file=new File("C:/rebursmentdocument/"+fileName);
			            	//System.out.println("file>>>>>>>>>>>>>>>>>>>"+file);
				            if(file.exists())
				             {
				                 fileInputStream = new FileInputStream(file);
				                 return "download";
				             }
				             else
				             {
				                  addActionError("File does not exist");
				                  return ERROR;
				             }
				          }
			        }
			        catch(Exception e)
			        {
			        	e.printStackTrace();
			        	return ERROR;
			        }
		     }
		     else 
		     {
		    	 return LOGIN;
		     }
		  return SUCCESS;
		 }	
	 
	 public String beforeAddReimbursement()
	 {
		// System.out.println("beforeReimbursement()>>>>>>>>>>>>>>>>");
		 boolean valid=ValidateSession.checkSession();
		 if(valid)
		 {
			 try
			 {
				 setReimbursementAddPageDataFields();
				// addReimbursement();
				 return SUCCESS;
			 }catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			 
		 }return LOGIN;
	 }
	 public void setReimbursementAddPageDataFields()
	 {

	     List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_reimbursement_configuration",accountID,connectionSpace,"",0,"table_name","reimbursement_configuration");
	 	CommonforClassdata cbt = new CommonOperAtion();
	     if(offeringLevel1!=null)
	     {
	         reibursementTextBox=new ArrayList<ConfigurationUtilBean>();
	         reibursementDropDown = new ArrayList<ConfigurationUtilBean>();
	         reibursementFile = new ArrayList<ConfigurationUtilBean>();
	         for(GridDataPropertyView  gdp:offeringLevel1)
	         {
	             ConfigurationUtilBean objdata= new ConfigurationUtilBean();
	             if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("created_date")
	             		&& !gdp.getColomnName().equalsIgnoreCase("userName")
	                     && !gdp.getColomnName().equalsIgnoreCase("created_time")
	                     && !gdp.getColomnName().equalsIgnoreCase("other")
	                     && !gdp.getColomnName().equalsIgnoreCase("purpose_id"))
	                     
	                {
	                 objdata.setKey(gdp.getColomnName());
	                 objdata.setValue(gdp.getHeadingName());
	                 objdata.setColType(gdp.getColType());
	                 objdata.setValidationType(gdp.getValidationType());
	                 if(gdp.getMandatroy()==null)
	                 objdata.setMandatory(false);
	                 else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	                     objdata.setMandatory(false);
	                 else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	                     objdata.setMandatory(true);
	                 reibursementTextBox.add(objdata);
	             }
	             
	             else if(gdp.getColType().trim().equalsIgnoreCase("D"))
	             {
	            		if(gdp.getColomnName().equalsIgnoreCase("purpose"))
	            		{
	            			System.out.println("column anme===="+gdp.getColomnName());
	            			System.out.println("heading name========"+gdp.getHeadingName());
	            			 objdata.setKey(gdp.getColomnName());
	    	                 objdata.setValue(gdp.getHeadingName());
	    	                 objdata.setColType(gdp.getColType());
	    	                 objdata.setValidationType(gdp.getValidationType());
	    	                 if(gdp.getMandatroy()==null)
	    	                 objdata.setMandatory(false);
	    	                 else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	    	                     objdata.setMandatory(false);
	    	                 else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	    	                     objdata.setMandatory(true);
	    	                 reibursementDropDown.add(objdata);
						    	
						}
	            	 
	             }
	             else if(gdp.getColType().trim().equalsIgnoreCase("F"))
	             {
	            	 System.out.println("column anme===="+gdp.getColomnName());
	            	 objdata.setKey(gdp.getColomnName());
	                 objdata.setValue(gdp.getHeadingName());
	                 objdata.setColType(gdp.getColType());
	                 objdata.setValidationType(gdp.getValidationType());
	                 if(gdp.getMandatroy()==null)
	                 objdata.setMandatory(false);
	                 else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	                     objdata.setMandatory(false);
	                 else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	                     objdata.setMandatory(true);
	                 reibursementFile.add(objdata);
	                 System.out.println("reibursementFile size>>>>>>>"+reibursementFile.size());
				    	
	             }
	         }
	         List<String> particularcolname=new ArrayList<String>();
				particularcolname.add("id");
				particularcolname.add("purpose");
				List productData=cbt.viewAllDataEitherSelectOrAll("purpose_data",particularcolname,connectionSpace);
				if(productData!=null)
				{
					purposename=new LinkedHashMap<Integer, String>();
					for (Object c : productData) {
						Object[] dataC = (Object[]) c;
						if(dataC[0]!=null && dataC[1]!=null)
							purposename.put((Integer)dataC[0],dataC[1].toString());
						
					}
					Map<Integer, String> sortedMap = cbt.sortByComparator(purposename);
					 setPurposename(sortedMap);
				 }
	     }
	     
	 }
	 public String editReimbursement()
	 {
		 boolean valid=ValidateSession.checkSession();
		 if(valid)
		 {
			 try
			 {
				 return SUCCESS;
			 }catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			 
		 }return LOGIN;
	 }
	 
    public String addReimbursement()
    {
    	
    	boolean valid=ValidateSession.checkSession();
    	List<TableColumes> tableColumn = new ArrayList<TableColumes>();
    	if(valid)
    	{
    		
    	try{
    		 CommonOperInterface cbt=new CommonConFactory().createInterface();
    		 
    		// System.out.println(">>>>"+getParticulars());
    		 //System.out.println(">>>>"+getAmount());
    		 
    		 
    		 String[] arrayParticulars= getParticulars().trim().split(",");
    		 String[] arrayAmount= getAmount().trim().split(",");
    		 
    		 for (int i = 0; i < uploadFileName.length; i++) {
    			 //System.out.println("uploadFileName[i]>>>>>>>>>>>>>"+uploadFileName[i]);
			 }
    		 // Creating dynamic table particulars_data
    		 
    		 TableColumes ob1=new TableColumes();
    		 ob1=new TableColumes();
             ob1.setColumnname("created_date");
             ob1.setDatatype("varchar(255)");
             ob1.setConstraint("default NULL");
             tableColumn.add(ob1);
             
             TableColumes ob8=new TableColumes();
    		 ob8=new TableColumes();
             ob8.setColumnname("created_time");
             ob8.setDatatype("varchar(255)");
             ob8.setConstraint("default NULL");
             tableColumn.add(ob8);

             
             TableColumes ob2=new TableColumes();
             ob2=new TableColumes();
             ob2.setColumnname("particulars");
             ob2.setDatatype("varchar(255)");
             ob2.setConstraint("default NULL");
             tableColumn.add(ob2);
             
             TableColumes ob3=new TableColumes();
             ob3.setColumnname("purpose_id");
             ob3.setDatatype("varchar(255)");
             ob3.setConstraint("default NULL");
             tableColumn.add(ob3);
             
             
             TableColumes ob6=new TableColumes();
             ob6.setColumnname("amount");
             ob6.setDatatype("varchar(255)");
             ob6.setConstraint("default NULL");
             tableColumn.add(ob6);
             
             TableColumes ob7=new TableColumes();
             ob7.setColumnname("uploadsFile");
             ob7.setDatatype("varchar(255)");
             ob7.setConstraint("default NULL");
             tableColumn.add(ob7);
             
       //      System.out.println("particulars_data table creation:line ->128"+tableColumn);
             
            boolean tableCreated=cbt.createTable22("particulars_data",tableColumn,connectionSpace);
     	//	System.out.println("particulars_data table created successwfully>>>>>>>>>>"+tableCreated);
     		
     		boolean status=false;
     		if(tableCreated)
     		{
     			List <InsertDataTable> insertHistory=null;
                 InsertDataTable Idt=null;
                 for (int i = 0; i < arrayParticulars.length; i++) {
                	  
                	 if(!arrayParticulars[i].equalsIgnoreCase(null) && !arrayParticulars[i].equalsIgnoreCase(" ")){
                		 
                		 
                	 insertHistory=new ArrayList<InsertDataTable>();	
                     Idt=new InsertDataTable(); 
                     Idt.setColName("particulars");
                     Idt.setDataName(arrayParticulars[i]);
                     insertHistory.add(Idt);
                     
                     Idt=new InsertDataTable(); 
                     Idt.setColName("purpose_id");
                     Idt.setDataName(getPurpose());
                     insertHistory.add(Idt);
                     
                	 if(arrayAmount[i]!=null && !arrayAmount[i].equalsIgnoreCase("")){
                         Idt=new InsertDataTable();
                         Idt.setColName("amount");
                         Idt.setDataName(arrayAmount[i]);
                         insertHistory.add(Idt);
                    	 }
                	 
                	  // find document attachment file,image or any scan copy
          			String storeFilePath = new CreateFolderOs().createUserDir("rebursmentdocument");
                	  File f = new File(storeFilePath+ File.separator+ uploadFileName[i]);
					  if(f.exists()){
						  Idt=new InsertDataTable();
                          Idt.setColName("uploadsFile");
                          Idt.setDataName(uploadFileName[i]);
                          insertHistory.add(Idt);
							 FilesUtil.saveFile(upload[i], uploadFileName[i], storeFilePath+ File.separator ); 
					  }else{
						  Idt=new InsertDataTable();
                          Idt.setColName("uploadsFile");
                          Idt.setDataName(uploadFileName[i]);
                          insertHistory.add(Idt);
						  FilesUtil.saveFile(upload[i], uploadFileName[i], storeFilePath+ File.separator );
			           }
         
						
                	  Idt=new InsertDataTable();
                      Idt.setColName("created_date");
                      Idt.setDataName(DateUtil.getCurrentDateUSFormat());
                      insertHistory.add(Idt);
                      
                      Idt=new InsertDataTable();
                      Idt.setColName("created_time");
                      Idt.setDataName(DateUtil.getCurrentTimeHourMin());
                      insertHistory.add(Idt);
                      
                	 status=cbt.insertIntoTable("particulars_data",insertHistory,connectionSpace);
                	 }
				  }
                 
                 if(status)
                 {
                 	addActionMessage("Particulars Data Added Successfully!!!!!");
                 }else{
                 	addActionError("OOP's some Error in Data!!!!!!!!!");
                 }
     			
     		}
    		
    			return SUCCESS;
    			
    	   }catch (Exception e) {
			e.printStackTrace();
			return ERROR;
			}
		}
    		return LOGIN;
    }
    
	private String uploadsFile() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Object>  contactDetail;
    List<Object> Listhb=new ArrayList<Object>();
    public String veiwGridData()
    { 
    	//System.out.println("<<<<<<<<<<<<<{viewReimbursmentInGrid}>>>>>>>>>>>>>>>>>>>");
    	try
    	{
    	//	System.out.println(">>>>>>>> Request : "+ request);
    		/*Enumeration enumeration = request.getParameterNames();
    		while (enumeration.hasMoreElements()) {
				System.out.println(">>>>>>>>>"+ enumeration.nextElement());
			}*/
    		
    		/*
    		 GridDataPropertyView gpv=new GridDataPropertyView();

    		 List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_particulars_configuration",accountID,connectionSpace,"",0,"table_name","particulars_configuration");
    	     if(returnResult!=null)
    	     {
    	    	 for(GridDataPropertyView gdp:returnResult)
    	         {
    	    		 ConfigurationUtilBean objdata= new ConfigurationUtilBean();
    	    		 if(gdp.getColType().trim().equalsIgnoreCase("T") 
    	                     && !gdp.getColomnName().equalsIgnoreCase("sNo"))
    	                    
    	    		 {
    	    			 gpv=new GridDataPropertyView();
    	                 gpv.setColomnName(gdp.getColomnName());
    	                 gpv.setHeadingName(gdp.getHeadingName());
    	                 gpv.setEditable(gdp.getEditable());
    	                 gpv.setSearch(gdp.getSearch());
    	                 gpv.setHideOrShow(gdp.getHideOrShow());
    	                 gpv.setFormatter(gdp.getFormatter());
    	                 gpv.setWidth(100);
    	                 masterViewProp.add(gpv);
    	    		 }
    	    		 else
    	    		 {
    	    			 objdata.setKey(gdp.getColomnName());
    	                 objdata.setValue(gdp.getHeadingName());
    	                 objdata.setValidationType(gdp.getValidationType());
    	                 if(gdp.getMandatroy()==null)
    	                 objdata.setMandatory(false);
    	    		 }
    	             
    	          
    	         }
    	     }
    		
    		
    	*/}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
	 public String createReimbursementView()
	    {
	        boolean valid=ValidateSession.checkSession();
	        if(!valid)
	        {
	            try
	            {
	            	  
	            	  
	            	  return LOGIN;
	            }
	            catch(Exception e)
	            {
	                 e.printStackTrace();
	                 return ERROR;
	            }
	        }
	        setReimbursementViewProp();
	      
	            return SUCCESS;
	    }	 
    public String beforeParticularsView()
	    {
	        boolean valid=ValidateSession.checkSession();
	        if(valid)
	        {
	            try
	            {
	            	  
	            	 setParticularsAddPageDataFields();
	            	  return SUCCESS;
	            }
	            catch(Exception e)
	            {
	                 e.printStackTrace();
	                 return ERROR;
	            }
	        }
	       
	       //   setMainHeaderName("Particulars >> Details");
             //  setMainHeaderName(getMainHeaderName());
	            return LOGIN;
	    }
    public String particularsViewColum()
    {
        boolean valid=ValidateSession.checkSession();
        if(!valid)
        {
            try
            {
            	  
            	  
            	  return LOGIN;
            }
            catch(Exception e)
            {
                 e.printStackTrace();
                 return ERROR;
            }
        }
        	
              setPaticularsViewProp();
        //	  System.out.println(""+getMasterViewProp().size());
        	  return SUCCESS;
        
        //setMainHeaderName(getMainHeaderName());
           
    }
public void setParticularsAddPageDataFields()
{

    List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_particulars_configuration",accountID,connectionSpace,"",0,"table_name","particulars_configuration");
    if(offeringLevel1!=null)
    {
        particularsTextBox=new ArrayList<ConfigurationUtilBean>();
        for(GridDataPropertyView  gdp:offeringLevel1)
        {
            ConfigurationUtilBean objdata= new ConfigurationUtilBean();
            if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("created_date")
            		&& !gdp.getColomnName().equalsIgnoreCase("userName")
                    && !gdp.getColomnName().equalsIgnoreCase("created_time")&& !gdp.getColomnName().equalsIgnoreCase("refDocName")
                    && !gdp.getColomnName().equalsIgnoreCase("particulars"))
            {
                objdata.setKey(gdp.getColomnName());
                objdata.setValue(gdp.getHeadingName());
                objdata.setColType(gdp.getColType());
                objdata.setValidationType(gdp.getValidationType());
                if(gdp.getMandatroy()==null)
                objdata.setMandatory(false);
                else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                    objdata.setMandatory(false);
                else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                    objdata.setMandatory(true);
                particularsTextBox.add(objdata);
         //       System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+particularsTextBox);
            }
            
            else if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
                    && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
            {
                objdata.setKey(gdp.getColomnName());
                objdata.setValue(gdp.getHeadingName());
                objdata.setValidationType(gdp.getValidationType());
                if(gdp.getMandatroy()==null)
                objdata.setMandatory(false);
            }
        }
    }
}

public void setPaticularsViewProp()
{
	//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>view Grid Column");
	 GridDataPropertyView gpv=new GridDataPropertyView();
     /*gpv.setColomnName("id");
     gpv.setHeadingName("Id");
     gpv.setHideOrShow("true");
     masterViewProp.add(gpv);*/
     
     List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_particulars_configuration",accountID,connectionSpace,"",0,"table_name","particulars_configuration");
     if(returnResult!=null)
     {
    	 for(GridDataPropertyView gdp:returnResult)
         {
    		 ConfigurationUtilBean objdata= new ConfigurationUtilBean();
    		 if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("refDocName")
                     && !gdp.getColomnName().equalsIgnoreCase("particulars")
                     && !gdp.getColomnName().equalsIgnoreCase("sNo"))
    		 {
    			 gpv=new GridDataPropertyView();
                 gpv.setColomnName(gdp.getColomnName());
                 gpv.setHeadingName(gdp.getHeadingName());
                 gpv.setEditable(gdp.getEditable());
                 gpv.setSearch(gdp.getSearch());
                 gpv.setHideOrShow(gdp.getHideOrShow());
                 gpv.setFormatter(gdp.getFormatter());
                 gpv.setWidth(100);
                 masterViewProp.add(gpv);
    		 }
    		 else
    		 {
    			 objdata.setKey(gdp.getColomnName());
                 objdata.setValue(gdp.getHeadingName());
                 objdata.setValidationType(gdp.getValidationType());
                 if(gdp.getMandatroy()==null)
                 objdata.setMandatory(false);
    		 }
             
          
         }
     }
   
}

public void setReimbursementViewProp()
{
	//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>view Grid Column");
	 GridDataPropertyView gpv=new GridDataPropertyView();
     gpv.setColomnName("id");
     gpv.setHeadingName("Id");
     gpv.setHideOrShow("true");
     masterViewProp.add(gpv);
     
     List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_particulars_configuration",accountID,connectionSpace,"",0,"table_name","particulars_configuration");
     for(GridDataPropertyView gdp:returnResult)
     {
         gpv=new GridDataPropertyView();
         gpv.setColomnName(gdp.getColomnName());
         gpv.setHeadingName(gdp.getHeadingName());
         gpv.setEditable(gdp.getEditable());
         gpv.setSearch(gdp.getSearch());
         gpv.setHideOrShow(gdp.getHideOrShow());
         gpv.setFormatter(gdp.getFormatter());
         gpv.setWidth(100);
         masterViewProp.add(gpv);
     // System.out.println("gdp.getForma?>>>>>>>>>>>>>"+gdp.getFormatter());
     }
   
}

public String addPurposeMaster()
{ 
	//System.out.println(">>>>>>>>>>>>>>addParticulars()>>>>>>>");
	List<TableColumes> tableColumn = new ArrayList<TableColumes>();
	boolean valid=ValidateSession.checkSession();
	if(valid)
	{
	 try
	  {
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 
		 // Creating dynamic table particulars_data
		 /*TableColumes ob1=new TableColumes();
		 ob1=new TableColumes();
         ob1.setColumnname("particulars");
         ob1.setDatatype("varchar(255)");
         ob1.setConstraint("default NULL");
         tableColumn.add(ob1);*/
         
         TableColumes ob2=new TableColumes();
         ob2=new TableColumes();
         ob2.setColumnname("purpose");
         ob2.setDatatype("varchar(255)");
         ob2.setConstraint("default NULL");
         tableColumn.add(ob2);
         
         TableColumes ob3=new TableColumes();
         ob3.setColumnname("userName");
         ob3.setDatatype("varchar(255)");
         ob3.setConstraint("default NULL");
         tableColumn.add(ob3);
         
         TableColumes ob4=new TableColumes();
         ob4.setColumnname("created_date");
         ob4.setDatatype("varchar(255)");
         ob4.setConstraint("default NULL");
         tableColumn.add(ob4);
         
         TableColumes ob5=new TableColumes();
         ob5.setColumnname("created_time");
         ob5.setDatatype("varchar(255)");
         ob5.setConstraint("default NULL");
         tableColumn.add(ob5);
         
         TableColumes ob6=new TableColumes();
         ob6.setColumnname("totalamount");
         ob6.setDatatype("varchar(255)");
         ob6.setConstraint("default NULL");
         tableColumn.add(ob6);
         
     //    System.out.println("records>>>>>>>>>>>>>>>"+tableColumn.size());
         
        boolean tableCreated=cbt.createTable22("purpose_data",tableColumn,connectionSpace);
		//System.out.println("purpose_data table created successwfully>>>>>>>>>>"+tableCreated);
		boolean status=false;
		if(tableCreated)
		{
			List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();	 
            InsertDataTable Idt=null;
            Idt=new InsertDataTable();
            
            /*Idt.setColName("particulars");
            Idt.setDataName(particulars);
            insertHistory.add(Idt);*/
            
            Idt=new InsertDataTable(); 
            Idt.setColName("purpose");
            Idt.setDataName(purpose);
            insertHistory.add(Idt);
            
            
            Idt=new InsertDataTable(); 
            Idt.setColName("userName");
            Idt.setDataName(userName1);
            insertHistory.add(Idt);
            
            Idt=new InsertDataTable();
            Idt.setColName("created_date");
            Idt.setDataName(DateUtil.getCurrentDateUSFormat());
            insertHistory.add(Idt);
            
            Idt=new InsertDataTable();
            Idt.setColName("created_time");
            Idt.setDataName(DateUtil.getCurrentTimeHourMin());
            insertHistory.add(Idt);
            
            Idt=new InsertDataTable(); 
            Idt.setColName("totalamount");
            Idt.setDataName(totalamount);
            insertHistory.add(Idt);
            
            status=cbt.insertIntoTable("purpose_data",insertHistory,connectionSpace);
            //System.out.println("Purpose Data  inserted successfully>>>>>"+status);
            if(status)
            {
            	addActionMessage("Purpose Added Successfully!!!!!");
            }else{
            	addActionError("OOP's some Error in Data!!!!!!!!!");
            }
			
		}
	  }catch (Exception e)
	  {
		e.printStackTrace();
		return ERROR;
	  }
	}
	  else {
			return LOGIN;
	 }
	return SUCCESS;
}
public String addReimbursementData()
{
  boolean valid=ValidateSession.checkSession();
  if(valid)
  {
	try
	{
		addReimbursement();
		return SUCCESS;
	}catch (Exception e) {
		e.printStackTrace();
		return ERROR;
	}
  }
  return LOGIN;
}

public String reimbursementDataInGrid()
{
 boolean valid=ValidateSession.checkSession();
 if(valid)
 {
	 try
	 {
		 reimbursementViewDataInGrid();
		 return SUCCESS;
	 }catch (Exception e) {
		e.printStackTrace();
		return ERROR;
	}
 }
 return LOGIN;
}
public String  reimbursementViewDataInGrid()
{

	try
	{

		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 {
		 StringBuilder query=new StringBuilder("");
		 query.append("select count(*) from particulars_data");
		 List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		 if(dataCount!=null&&dataCount.size()>0)
		 {
			BigInteger count = new BigInteger("3");
			for (Iterator it = dataCount.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();
				count = (BigInteger) obdata;
			}
			setRecords(count.intValue());
			int to = (getRows() * getPage());
			int from = to - getRows();
			if (to > getRecords()) to = getRecords();
			 
			 StringBuilder query1=new StringBuilder("");
			 query1.append("select ");
			 List<String> fieldName=new ArrayList<String>();
			 
			 fieldName.add("id");
			 fieldName.add("created_time");
			 fieldName.add("created_date");
			 fieldName.add("purpose_id");
			 fieldName.add("totalamont");//not in column view
			 fieldName.add("particulars");
			 fieldName.add("amount");
			 fieldName.add("uploadsFile");
			 
			 query1.append("partdata.id"+",");
			 query1.append("partdata.created_time"+",");
			 query1.append("partdata.created_date"+",");
			 query1.append("purpose"+",");
			
			 query1.append("prpsdata.totalamount"+",");//not in column view
			 query1.append("partdata.particulars"+",");
			 query1.append("partdata.amount"+",");
			 query1.append("partdata.uploadsFile");
			 
					 query1.append(" from particulars_data as partdata "+
							 "inner join purpose_data as prpsdata on partdata.purpose_id="+"prpsdata.id ");
					
					 System.out.println(">>>>"+query1);
					 List  data=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					 
					String [] arrayfieldName = fieldName.toArray(new String[fieldName.size()]);
					System.out.println(">>>>>>>>>>"+arrayfieldName.length);
					System.out.println(">>>>>>>>>>"+data.size());
					int purpose_totalaoumt=0;
					//int sum_totalaoumt=0;
					 if(data!=null && data.size()>0)
						{
						List<Object> tempList=new ArrayList<Object>();
					
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> tempMap=new HashMap<String, Object>();
							for (int k = 0; k < arrayfieldName.length; k++) {
								if(obdata[k]!=null)
								{
										if(k==0)
										{
											tempMap.put(arrayfieldName[k].toString(),(Integer)obdata[k]);
										}
										else {
											 if(arrayfieldName[k].toString().equalsIgnoreCase("totalamont")){
												 
												  purpose_totalaoumt= Integer.parseInt(obdata[k].toString());
											  }
											  if(arrayfieldName[k].toString().equalsIgnoreCase("amount")){
												  
												  sum_totalaoumt+= Integer.parseInt(obdata[k].toString());
											  }
											  
											  if(! arrayfieldName[k].toString().equalsIgnoreCase("created_date"))
													 
											  {
												  tempMap.put(arrayfieldName[k].toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												  
											  }
											  if(arrayfieldName[k].toString().equalsIgnoreCase("created_date"))
												 {
													 tempMap.put(arrayfieldName[k].toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												 }else{
													  tempMap.put(arrayfieldName[k].toString(),obdata[k].toString());
												 }
										}
										detected_amount=(purpose_totalaoumt-sum_totalaoumt);
								}
							}
							
							tempList.add(tempMap);
						}
						   userdata.put("amount","(A)Tatal Amount = "+sum_totalaoumt);
						  // userdata.put("imagename","Total :");
						   userdata.put("purpose_id","(B)Advance Amount = "+purpose_totalaoumt);
						//   userdata.put("created_date","Total Claim Balance :"+detected_amount);
						  setGridModel(tempList);
						 
						  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			if(purpose_totalaoumt!=0){
				
			}
					 
   	 }
		 return SUCCESS; 
}
	
		
	}catch (Exception e) {
		e.printStackTrace();
		return ERROR;
	}
}

@SuppressWarnings("unchecked")
public String getAttachmentdownloadForTask() 
{
	try
	{
	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		 CommonOperInterface cbt = new CommonConFactory().createInterface();
		 String query = "select id,uploadsFile from particulars_data where id='"+getId()+"'";
		 attachFileMap=new LinkedHashMap<String,String>();
		 //System.out.println("query>>>>>>>>>>>>>>>>>>"+query);
		 List attach=cbt.executeAllSelectQuery(query, connectionSpace);
		 if (attach!=null && attach.size()>0) 
		 {
				for (Iterator iterator = attach.iterator(); iterator.hasNext();) 
				{
					 Object[] object = (Object[]) iterator.next();
					 if (object!=null) 
					 {
						 if (object[0]!=null && object[1]!=null) 
						 {
							 attachFileMap.put("id", object[0].toString());
							 attachFileMap.put("FilePath", object[1].toString());
							 System.out.println("attachFileMap>>>>>>>>>"+object[1].toString());
						 }
						 else
						 {
							 addActionMessage("FILE NOT FOUND");
							 return "notfound";
						 }
					 }
				}
		}
		else 
		{
			 addActionMessage("FILE NOT FOUND");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return ERROR;
	}
	return SUCCESS;
}
public String particularsViewDataInGrid()
{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 {
				 StringBuilder query=new StringBuilder("");
				 query.append("select count(*) from purpose_data");
				 List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				 if(dataCount!=null&&dataCount.size()>0)
				 {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords()) to = getRecords();
					 
					 
					 
					 StringBuilder query1=new StringBuilder("");
					 query1.append("select ");
					 List fieldName=Configuration.getColomnList("mapped_particulars_configuration", accountID, connectionSpace, "particulars_configuration");
					 int i=0;
						if(fieldName!=null && fieldName.size()>0)
						{
							for(Iterator it=fieldName.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(i<fieldName.size()-1)
									    	query1.append(obdata.toString()+",");
									    else
									    	query1.append(obdata.toString());
								    }
								    i++;
								
							}
						}
						
							 query1.append(" from purpose_data ");
							 List  data=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
							 if(data!=null && data.size()>0)
								{
								List<Object> tempList=new ArrayList<Object>();
								for(Iterator it=data.iterator(); it.hasNext();)
								{
									Object[] obdata=(Object[])it.next();
									Map<String, Object> tempMap=new HashMap<String, Object>();
									for (int k = 0; k < fieldName.size(); k++) {
										if(obdata[k]!=null)
										{
												if(k==0)
												{
													
													tempMap.put(fieldName.get(k).toString(),(Integer)obdata[k]);
												}
												else {
													
													  if(! fieldName.get(k).toString().equalsIgnoreCase("refDocName") 
															  && ! fieldName.get(k).toString().equalsIgnoreCase("sNo")
															  )
															 if(fieldName.get(k).toString().equalsIgnoreCase("created_date"))
															 {
																 tempMap.put(fieldName.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
															 }else
													  {
														  tempMap.put(fieldName.get(k).toString(),obdata[k].toString());
													  }
												}
										}
									}
									
									tempList.add(tempMap);
								}
								  setGridModel(tempList);
								  System.out.println("gridModel.size():"+gridModel.size());
								  
								  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
		    	 }
				 return SUCCESS; 
		 }
			}catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			
		}else{ return LOGIN;}
}

	public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPurpose() {
	return purpose;
}
public void setPurpose(String purpose) {
	this.purpose = purpose;
}
	public String getParticulars() {
	return particulars;
}
public void setParticulars(String particulars) {
	this.particulars = particulars;
}
public String getUserName1() {
	return userName1;
}
public void setUserName1(String userName1) {
	this.userName1 = userName1;
}
public String getCreated_date() {
	return created_date;
}
public void setCreated_date(String created_date) {
	this.created_date = created_date;
}
public String getCreated_time() {
	return created_time;
}
public void setCreated_time(String created_time) {
	this.created_time = created_time;
}
	public List<Object> getContactDetail() {
	return contactDetail;
}
public void setContactDetail(List<Object> contactDetail) {
	this.contactDetail = contactDetail;
}
public List<Object> getListhb() {
	return Listhb;
}
public void setListhb(List<Object> listhb) {
	Listhb = listhb;
}
	public String getFlage() {
	return flage;
}
public void setFlage(String flage) {
	this.flage = flage;
}
	public List<GridDataPropertyView> getMasterViewProp() {
	return masterViewProp;
}
public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
	this.masterViewProp = masterViewProp;
}
	public List<ConfigurationUtilBean> getReibursementTextBox() {
	return reibursementTextBox;
}
public void setReibursementTextBox(
		List<ConfigurationUtilBean> reibursementTextBox) {
	this.reibursementTextBox = reibursementTextBox;
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


	public String getSord() {
		return sord;
	}


	public void setSord(String sord) {
		this.sord = sord;
	}


	public String getSidx() {
		return sidx;
	}


	public void setSidx(String sidx) {
		this.sidx = sidx;
	}


	public String getSearchField() {
		return searchField;
	}


	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}


	public String getSearchString() {
		return searchString;
	}


	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	public String getSearchOper() {
		return searchOper;
	}


	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
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


	public boolean isLoadonce() {
		return loadonce;
	}


	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}


	public String getOper() {
		return oper;
	}


	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}


	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	
	public List<Object> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<Object> gridModel) {
		this.gridModel = gridModel;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		  this.request=request;
	}

	public List<ConfigurationUtilBean> getParticularsTextBox() {
		return particularsTextBox;
	}

	public void setParticularsTextBox(List<ConfigurationUtilBean> particularsTextBox) {
		this.particularsTextBox = particularsTextBox;
	}

	public String getRefDocName() {
		return refDocName;
	}

	public void setRefDocName(String refDocName) {
		this.refDocName = refDocName;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPurpose_id() {
		return purpose_id;
	}
	public void setPurpose_id(String purpose_id) {
		this.purpose_id = purpose_id;
	}
	public Map<Integer, String> getPurposename() {
		return purposename;
	}
	public void setPurposename(Map<Integer, String> purposename) {
		this.purposename = purposename;
	}
	public List<ConfigurationUtilBean> getReibursementDropDown() {
		return reibursementDropDown;
	}
	public void setReibursementDropDown(
			List<ConfigurationUtilBean> reibursementDropDown) {
		this.reibursementDropDown = reibursementDropDown;
	}
	public List<ConfigurationUtilBean> getReibursementFile() {
		return reibursementFile;
	}
	public File[] getUpload() {
		return upload;
	}
	public void setUpload(File[] upload) {
		this.upload = upload;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getAttachFileMap() {
		return attachFileMap;
	}
	public void setAttachFileMap(Map<String, String> attachFileMap) {
		this.attachFileMap = attachFileMap;
	}

	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}

	public String getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}

	public int getDetected_amount() {
		return detected_amount;
	}

	public void setDetected_amount(int detected_amount) {
		this.detected_amount = detected_amount;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
