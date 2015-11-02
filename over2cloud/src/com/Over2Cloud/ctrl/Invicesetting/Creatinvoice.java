package com.Over2Cloud.ctrl.Invicesetting;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.ProductIteam;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Creatinvoice  extends ActionSupport  implements ServletRequestAware{
	static final Log log = LogFactory.getLog(Creatinvoice.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int id=0;
	private String createtbasicdetails_table;
	private String createaddressdetails_table;
	private String customfieldsheader=null;
	private String descriptionheader=null;
	private String mappedtablele=null;
	private String basicdetails_table=null;
	private String addressdetails_table=null;
	private String customdetails_table=null;
	private String descriptive_table=null;
	private String basicfieldsheader=null;
	private String addressfieldsheader=null;
    String[] headtername=null;
	private Map<Integer, String> customertype=null;
	private Map<Integer, String> customername=null;
	private Map<Integer, String> statusname=null;
	private Map<Integer, String> contactname=null;
	private Map<Integer, String> productname=null;
	private String customer_type=null;
	private String customer_name=null;
	private String invoice_status=null;
	private String contact_name=null;
	private String product_name=null;
	private List<ProductIteam> productlistdata=null;
	private List<ProductIteam> taxlistdata=null;
    private int  balance=0;
    private List<ConfigurationUtilBean>conatcbasicPerCalendr=null;
	private List<ConfigurationUtilBean>conatcbasicPerdropdown=null;
	private List<ConfigurationUtilBean>conatctextPerdropdown=null;
	private List<ConfigurationUtilBean>conatcaddressPerCalendr=null;
	
	private List<ConfigurationUtilBean> Listbasicsconfiguration=null;
	private List<ConfigurationUtilBean> Listadressconfiguration=null;
	private List<ConfigurationUtilBean> Listcustomconfiguration=null;
	private List<ConfigurationUtilBean> Listdescriptconfiguration=null;
	private List<ConfigurationUtilBean> Listcheckboxfiguration=null;
	private int total=0;
	private int percentagSum=0;
	private int grandtotal=0;
	private String myArray=null;
	private HttpServletRequest request;
	private String productid=null; 
	private Map<Integer,String> liststatus=null;
	private String checkbox_conditions=null;
	private String checkbox_invoice_preference=null;
	@SuppressWarnings("unchecked")
	public String addinvoicedatasssss(){
			String resultString=ERROR;
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			List<GridDataPropertyView>mappeddetails=null;
		  	StringBuilder StrnObj=new StringBuilder();
			try{
				  if(getProductid()!=null){
					  	String[] array=getProductid().toString().split(",");
					
					  	for (int i = 0; i < array.length; i++) {
					  		StrnObj.append(array[i].toString().trim());
					  		StrnObj.append("#");
						}
					   }
				 mappeddetails=Configuration.getConfigurationData(mappedtablele,accountID,connectionSpace,"",0,"table_name",basicdetails_table);
				 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				 Collections.sort(requestParameterNames);
				 Iterator listit = requestParameterNames.iterator();
				 while (listit.hasNext()) {
						String Parmname = listit.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(Parmname.equals("mappedtablele")&& paramValue.equals(request.getParameter("mappedtablele"))){
							mappedtablele=paramValue;}
						else if(paramValue.equals(request.getParameter("basicdetails_table"))){
							basicdetails_table=paramValue;}
						else if(paramValue.equals(request.getParameter("__checkbox_conditions"))){
							checkbox_conditions=paramValue;}
						else if(paramValue.equals(request.getParameter("__checkbox_invoice_preference"))){
							checkbox_invoice_preference=paramValue;}
						else if(paramValue.equals(request.getParameter("grandtotal"))){
							 InsertDataTable ob = new InsertDataTable();
							 ob.setColName("grandtotal");
 							ob.setDataName(paramValue);	 
						}	
						else{
						 if(mappeddetails!=null){
							 for(GridDataPropertyView col:mappeddetails){
							 if(col.getColomnName().equals(Parmname)){
							InsertDataTable ob = new InsertDataTable();
							if(Parmname.equals("conditions")){
							ob.setColName(Parmname);
							ob.setDataName(checkbox_conditions);}
							else if(Parmname.equals("invoice_preference")){
                            	ob.setColName(Parmname);
    							ob.setDataName(checkbox_invoice_preference);}
							else if(Parmname.equals("product_name")){
                            	ob.setColName(Parmname);
    							ob.setDataName(StrnObj.toString());}
							else{
                            	ob.setColName(Parmname);
    							ob.setDataName(paramValue);
                            }
							insertData.add(ob);
						  }
						 }
					   }
					 }
				   }
	
					//	boolean status=false;
					if(mappeddetails!=null){
					 for(GridDataPropertyView col:mappeddetails){
						 TableColumes obJ=new TableColumes();
						 obJ.setColumnname(col.getColomnName());
						 obJ.setDatatype("varchar(255)");
						 obJ.setConstraint("default NULL");
						 Tablecolumesaaa.add(obJ);
						}
						CommonforClassdata obhj = new CommonOperAtion();
					    boolean status = obhj.Createtabledata("createtinvovcedetails_table", Tablecolumesaaa, connectionSpace);
				
						status = obhj.insertIntoTable("createtinvovcedetails_table", insertData,connectionSpace);
						if (status) {
							addActionMessage("Data added SuccessfUlly");
						} else {
							addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
					}
				}
				resultString=SUCCESS;
			}
		     
			catch (Exception e) {
				// TODO: handle exception
			}
		return resultString;
		}
	
	public String getproductwithtax(){
		String resultString=ERROR;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		try{
			List<String>productcolname=new ArrayList<String>();
			List<ProductIteam>productlsitdata=new ArrayList<ProductIteam>();
	 		productcolname.add("id");
			productcolname.add("product_name");
			productcolname.add("product_category");
			productcolname.add("qtyt");
			productcolname.add("unit_price");
			
		   if(getMyArray()!=null){
				String[] ArrayId= getMyArray().split(",");
				List<String> listProductIds = null;
				if(ArrayId != null && ArrayId.length > 0){
					listProductIds = new ArrayList<String>();
					for(String s:ArrayId){
						if(!listProductIds.contains(s)){
							listProductIds.add(s);
						}
					}
				}
				
			    Map<String, Object>wherClause= new HashMap<String, Object>();
				for (String s:listProductIds) {
			       wherClause.put("id",s);
			       
			List customertypeData=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname, wherClause, connectionSpace);
			if(customertypeData!=null){
				for (Object c : customertypeData) {
					ProductIteam listdata=new ProductIteam();
					Object[] dataC = (Object[]) c;
					 if(dataC[0]!=null && dataC[0]!=null){
						 listdata.setId(Integer.parseInt(dataC[0].toString()));}
					 if(dataC[1]!=null && dataC[1]!=null){
						 listdata.setProduct_name(dataC[1].toString());}
					 if(dataC[2]!=null && dataC[2]!=null){
						 listdata.setProduct_category(dataC[2].toString());}
					 if(dataC[3]!=null && dataC[3]!=null){
						 listdata.setQtyt(dataC[3].toString());}
					 if(dataC[4]!=null && dataC[4]!=null){
						 listdata.setUnit_price(dataC[4].toString());
						 total+= Integer.parseInt(dataC[4].toString());
						} 
					   productlsitdata.add(listdata);
				     }
			   }
			    setProductlistdata(productlsitdata);	
				setBalance(total);
		         
		         }
		   }
			//add services taxes
			List<String> objlistdata=new ArrayList<String>();
			objlistdata.add("tax_name");
			objlistdata.add("tax_price");
			List taxlistdata=cbt.viewAllDataEitherSelectOrAll("createtax_table",objlistdata,connectionSpace);
			List<ProductIteam> lstdataobhj=new ArrayList<ProductIteam>();
			for (Iterator iterator2 = taxlistdata
					.iterator(); iterator2.hasNext();) {
				Object[] object = (Object[]) iterator2.next();
				ProductIteam strObjt=new ProductIteam();
			   if(object[0].toString()!=null){
				strObjt.setTaxname(object[0].toString()+" "+"@"+" "+object[1].toString()+""+"%");
				try{
		         Double percentage=Double.valueOf(object[1].toString())*total/100;
		         percentagSum+= percentage;
				 strObjt.setCaltax(String.valueOf((percentage)));
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				
				lstdataobhj.add(strObjt);
			   }
			}
			    int xyzz= percentagSum+total;
			    setGrandtotal(xyzz);
			   setTaxlistdata(lstdataobhj);
			resultString=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}
	
	
	public String getcancelproductwithtax(){
		String resultString=ERROR;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		try{
			List<String>productcolname=new ArrayList<String>();
			List<ProductIteam>productlsitdata=new ArrayList<ProductIteam>();
	 		productcolname.add("id");
			productcolname.add("product_name");
			productcolname.add("product_category");
			productcolname.add("qtyt");
			productcolname.add("unit_price");
		   if(getMyArray()!=null){
				String[] ArrayId= getMyArray().split(",");
				List<String> listProductIds = null;
				if(ArrayId != null && ArrayId.length > 0){
					listProductIds = new ArrayList<String>();
					for(String s:ArrayId){
						if(!listProductIds.contains(s)){
							listProductIds.add(s);
						}else{
							listProductIds.remove(s);
						}
					}
				}
			    Map<String, Object>wherClause= new HashMap<String, Object>();
				for (String s:listProductIds) {
			       wherClause.put("id",s);
			List customertypeData=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname, wherClause, connectionSpace);
			if(customertypeData!=null){
				for (Object c : customertypeData) {
					ProductIteam listdata=new ProductIteam();
					Object[] dataC = (Object[]) c;
					 if(dataC[0]!=null && dataC[0]!=null){
						 listdata.setId(Integer.parseInt(dataC[0].toString()));}
					 if(dataC[1]!=null && dataC[1]!=null){
						 listdata.setProduct_name(dataC[1].toString());}
					 if(dataC[2]!=null && dataC[2]!=null){
						 listdata.setProduct_category(dataC[2].toString());}
					 if(dataC[3]!=null && dataC[3]!=null){
						 listdata.setQtyt(dataC[3].toString());}
					 if(dataC[4]!=null && dataC[4]!=null){
						 listdata.setUnit_price(dataC[4].toString());
						 total+= Integer.parseInt(dataC[4].toString());
						} 
					   productlsitdata.add(listdata);
				     }
			   }
			    setProductlistdata(productlsitdata);	
				setBalance(total);
		          }
		   }
			//add services taxes
			List<String> objlistdata=new ArrayList<String>();
			objlistdata.add("tax_name");
			objlistdata.add("tax_price");
			List taxlistdata=cbt.viewAllDataEitherSelectOrAll("createtax_table",objlistdata,connectionSpace);
			List<ProductIteam> lstdataobhj=new ArrayList<ProductIteam>();
			for (Iterator iterator2 = taxlistdata
					.iterator(); iterator2.hasNext();) {
				Object[] object = (Object[]) iterator2.next();
				ProductIteam strObjt=new ProductIteam();
			   if(object[0].toString()!=null){
				strObjt.setTaxname(object[0].toString()+" "+"@"+" "+object[1].toString()+""+"%");
				try{
		         Double percentage=Double.valueOf(object[1].toString())*total/100;
		         percentagSum+= percentage;
				 strObjt.setCaltax(String.valueOf((percentage)));
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				
				lstdataobhj.add(strObjt);
			   }
			}
			    int xyzz= percentagSum+total;
			    setGrandtotal(xyzz);
			   setTaxlistdata(lstdataobhj);
		   
			resultString=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}
		
	
	public String getcustomerdetails(){
		String resultString=ERROR;
		   CommonforClassdata obhj = new CommonOperAtion();
		   List<String> listdata=new ArrayList<String>();
		   List<String> data=new ArrayList<String>();
		   Map<String,Object> whereClose=new HashMap<String, Object>();
		   Map<String, Object>mapObject=new HashMap<String,Object>();
		 try{
			   mapObject.put("table_name","customer_configuration");
				data.add("mappedid");
				data.add("table_name");
				List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
			   List selectresultdata = Configuration.getConfigurationIdss("mapped_customer_configuration",data, accountID, mapObject, connectionSpace);
			   if(selectresultdata.size()>0){
					for (Object c1 : selectresultdata) {
						Object[] dataC1 = (Object[]) c1;
						if(dataC1[1].toString().trim().equals("customer_Name")){}
						else if(dataC1[1].toString().trim().equals("description")){}
						else if(dataC1[1].toString().trim().equals("street_address2")){}
						else{
						listdata.add(dataC1[1].toString().trim());
						}
					}
			    whereClose.put("id",getId());
			    List datatemp= obhj.getSelectdataFromSelectedFields("createtcustomerdetails_table",listdata, whereClose, connectionSpace);
			   Object[] objsArray=null;
				if(datatemp.size()>0){
				for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
					objsArray = (Object[]) iterator.next();}
					int tepmid=0;
					for (Object c1 : selectresultdata) {
						Object[] dataC1 = (Object[]) c1;
						ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
						if(dataC1[1].toString().trim().equals("customer_Name")){}
						else if(dataC1[1].toString().trim().equals("description")){}
						else if(dataC1[1].toString().trim().equals("street_address2")){}
						else{
						objEjb.setField_name(dataC1[0].toString().trim());
						objEjb.setField_value(dataC1[1].toString().trim());
						objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
						objEjb.setDefault_value(objsArray[tepmid].toString());
						ListObj.add(objEjb);
						tepmid++;
					}
				 }
				}
				setListbasicsconfiguration(ListObj);
			   }
			resultString=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return resultString;
	}
	
	
	
	
	public String beforecreateinvoice(){
		String resultString=ERROR;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		try{
	
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			List<String> data=new ArrayList<String>();
			data.add("mappedid");
			data.add("table_name");
	
			if(getCreatetbasicdetails_table()!=null && getCreateaddressdetails_table().equalsIgnoreCase("")){
			setCreatetbasicdetails_table(getCreatetbasicdetails_table());
			}
			if(getCreateaddressdetails_table()!=null && getCreateaddressdetails_table().equalsIgnoreCase("")){
				setCreateaddressdetails_table(getCreateaddressdetails_table());
			}
			List resultdata = new CommonOperAtion().getSelectTabledata(
					"allcommontabtable", paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				if (objectCal[5] != null
						&& !objectCal[5].toString().equalsIgnoreCase("")) {
					setMappedtablele(objectCal[5].toString());
					 if(objectCal[11]!=null){
						 headtername=objectCal[11].toString().split("#");
					 }
					 if(headtername.length>=1){
							setBasicfieldsheader(headtername[0].toString());
							}
					if(objectCal[2]!=null && !objectCal[2].toString().equalsIgnoreCase("")){
						Map<String, Object>mapObject=new HashMap<String,Object>();
					mapObject.put("table_name",objectCal[2].toString());
					setBasicdetails_table(objectCal[2].toString());
					List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> ListtextObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> dropdownListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> checkboxListObj=new ArrayList<ConfigurationUtilBean>();
				   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
				   if(selectresultdata.size()>0){
						for (Object c1 : selectresultdata) {
							Object[] dataC1 = (Object[]) c1;
								if(dataC1[3].toString().equalsIgnoreCase("T")){
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							
								if(dataC1[1].toString().equalsIgnoreCase("product_iteam")){}				
								else{					
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									ListtextObj.add(objEjb); }
								     }
								    if(dataC1[3].toString().equalsIgnoreCase("D")){
									    ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
										List<String>colname=new ArrayList<String>();
										if(dataC1[1].toString().equalsIgnoreCase("customer_type")){
										setCustomer_type(dataC1[0].toString());
										colname.add("id");
										colname.add("customer_type");
										List customertypeData=cbt.viewAllDataEitherSelectOrAll("createcustomertype_table",colname,connectionSpace);
										if(customertypeData!=null){
											customertype=new LinkedHashMap<Integer, String>();
											for (Object c : customertypeData) {
												Object[] dataC = (Object[]) c;
												if(dataC[0]!=null && dataC[1]!=null)
												customertype.put((Integer)dataC[0],dataC[1].toString());
											}
										  }
										 }	
										if(dataC1[1].toString().equalsIgnoreCase("customer_name")){
										List<String>customercolname=new ArrayList<String>();
										setCustomer_name(dataC1[0].toString());
										customercolname.add("id");
										customercolname.add("sirname");
										customercolname.add("customer_Name");
										List customerData=cbt.viewAllDataEitherSelectOrAll("createtcustomerdetails_table",customercolname,connectionSpace);
										if(customerData!=null)
										{
											customername=new LinkedHashMap<Integer, String>();
											for (Object c : customerData) {
												Object[] dataC = (Object[]) c;
												if(dataC[0]!=null && dataC[1]!=null)
													customername.put((Integer)dataC[0],dataC[1].toString()+""+dataC[2].toString());
											}
										}
		                         }
									if(dataC1[1].toString().equalsIgnoreCase("invoice_status")){
										List<String>statuscolname=new ArrayList<String>();
										setInvoice_status(dataC1[0].toString());
										statuscolname.add("id");
										statuscolname.add("invoice_status");
										List statusData=cbt.viewAllDataEitherSelectOrAll("createinvoicestatus_table",statuscolname,connectionSpace);
										if(statusData!=null)
										{
											statusname=new LinkedHashMap<Integer, String>();
											for (Object c : statusData) {
												Object[] dataC = (Object[]) c;
												if(dataC[0]!=null && dataC[1]!=null)
													statusname.put((Integer)dataC[0],dataC[1].toString());
											}
										}	
									}
									if(dataC1[1].toString().equalsIgnoreCase("contact_name")){
										List<String>contactcolname=new ArrayList<String>();
										setContact_name(dataC1[0].toString());
										contactcolname.add("id");
										contactcolname.add("sirname");
										contactcolname.add("first_name");
										contactcolname.add("last_name");
										List contactData=cbt.viewAllDataEitherSelectOrAll("contactbasicdetails",contactcolname,connectionSpace);
										if(contactData!=null)
										{
											contactname=new LinkedHashMap<Integer, String>();
											for (Object c : contactData) {
												Object[] dataC = (Object[]) c;
												if(dataC[0]!=null && dataC[1]!=null)
													contactname.put((Integer)dataC[0],dataC[1].toString()+" "+dataC[2].toString()+" "+dataC[3].toString());
											}
										}	
									}
									
									if(dataC1[1].toString().equalsIgnoreCase("product_name")){
								    setProduct_name(dataC1[0].toString());
									List<String> productcolname=new ArrayList<String>();
									productcolname.add("id");
									productcolname.add("product_name");
									List productData=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname,connectionSpace);
									if(productData!=null)
									{
										productname=new LinkedHashMap<Integer, String>();
										for (Object c : productData) {
											Object[] dataC = (Object[]) c;
											if(dataC[0]!=null && dataC[1]!=null)
												productname.put((Integer)dataC[0],dataC[1].toString());
										}
									 }	
									}
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dropdownListObj.add(objEjb);
								}
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dateListObj.add(objEjb);
								}
								if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									ListObj.add(objEjb);
								}
								if(dataC1[3].toString().equalsIgnoreCase("check")){
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									checkboxListObj.add(objEjb);
								}
								
						}
			            setListcheckboxfiguration(checkboxListObj);
						setConatcbasicPerCalendr(dateListObj);
						setConatcbasicPerdropdown(dropdownListObj);
						setListbasicsconfiguration(ListObj);	
						setConatctextPerdropdown(ListtextObj);
					  }
					}		
				}
			}
		
		
			resultString=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}

	  public String getinvoicestatus(){
	    	String returnString =ERROR;
	    	CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	try{
	    		List<String>statuscolname=new ArrayList<String>();
				statuscolname.add("id");
				statuscolname.add("invoice_status");
				List statusData=cbt.viewAllDataEitherSelectOrAll("createinvoicestatus_table",statuscolname,connectionSpace);
				if(statusData!=null)
				{
					statusname=new LinkedHashMap<Integer, String>();
					for (Object c : statusData) {
						Object[] dataC = (Object[]) c;
						if(dataC[0]!=null && dataC[1]!=null)
							statusname.put((Integer)dataC[0],dataC[1].toString());
					}
					setListstatus(statusname);
				}	
	    		
	    		returnString=SUCCESS;
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
			}
	    	return returnString;
	    }
		
		
	
	
	
	public List<ConfigurationUtilBean> getConatcbasicPerCalendr() {
		return conatcbasicPerCalendr;
	}


	public void setConatcbasicPerCalendr(
			List<ConfigurationUtilBean> conatcbasicPerCalendr) {
		this.conatcbasicPerCalendr = conatcbasicPerCalendr;
	}


	public List<ConfigurationUtilBean> getConatcbasicPerdropdown() {
		return conatcbasicPerdropdown;
	}


	public void setConatcbasicPerdropdown(
			List<ConfigurationUtilBean> conatcbasicPerdropdown) {
		this.conatcbasicPerdropdown = conatcbasicPerdropdown;
	}


	public List<ConfigurationUtilBean> getConatcaddressPerCalendr() {
		return conatcaddressPerCalendr;
	}


	public void setConatcaddressPerCalendr(
			List<ConfigurationUtilBean> conatcaddressPerCalendr) {
		this.conatcaddressPerCalendr = conatcaddressPerCalendr;
	}



	public String getCreatetbasicdetails_table() {
		return createtbasicdetails_table;
	}


	public void setCreatetbasicdetails_table(String createtbasicdetails_table) {
		this.createtbasicdetails_table = createtbasicdetails_table;
	}


	public String getCreateaddressdetails_table() {
		return createaddressdetails_table;
	}


	public void setCreateaddressdetails_table(String createaddressdetails_table) {
		this.createaddressdetails_table = createaddressdetails_table;
	}


	public String getMappedtablele() {
		return mappedtablele;
	}


	public void setMappedtablele(String mappedtablele) {
		this.mappedtablele = mappedtablele;
	}


	public String getCustomfieldsheader() {
		return customfieldsheader;
	}


	public void setCustomfieldsheader(String customfieldsheader) {
		this.customfieldsheader = customfieldsheader;
	}


	public String getDescriptionheader() {
		return descriptionheader;
	}


	public void setDescriptionheader(String descriptionheader) {
		this.descriptionheader = descriptionheader;
	}


	public List<ConfigurationUtilBean> getListbasicsconfiguration() {
		return Listbasicsconfiguration;
	}


	public void setListbasicsconfiguration(
			List<ConfigurationUtilBean> listbasicsconfiguration) {
		Listbasicsconfiguration = listbasicsconfiguration;
	}


	public List<ConfigurationUtilBean> getListadressconfiguration() {
		return Listadressconfiguration;
	}


	public void setListadressconfiguration(
			List<ConfigurationUtilBean> listadressconfiguration) {
		Listadressconfiguration = listadressconfiguration;
	}


	public List<ConfigurationUtilBean> getListcustomconfiguration() {
		return Listcustomconfiguration;
	}


	public void setListcustomconfiguration(
			List<ConfigurationUtilBean> listcustomconfiguration) {
		Listcustomconfiguration = listcustomconfiguration;
	}


	public List<ConfigurationUtilBean> getListdescriptconfiguration() {
		return Listdescriptconfiguration;
	}


	public void setListdescriptconfiguration(
			List<ConfigurationUtilBean> listdescriptconfiguration) {
		Listdescriptconfiguration = listdescriptconfiguration;
	}


	public String getBasicdetails_table() {
		return basicdetails_table;
	}


	public void setBasicdetails_table(String basicdetails_table) {
		this.basicdetails_table = basicdetails_table;
	}


	public String getAddressdetails_table() {
		return addressdetails_table;
	}


	public void setAddressdetails_table(String addressdetails_table) {
		this.addressdetails_table = addressdetails_table;
	}


	public String getCustomdetails_table() {
		return customdetails_table;
	}


	public void setCustomdetails_table(String customdetails_table) {
		this.customdetails_table = customdetails_table;
	}

	public String getDescriptive_table() {
		return descriptive_table;
	}

	public void setDescriptive_table(String descriptive_table) {
		this.descriptive_table = descriptive_table;
	}

	public String getBasicfieldsheader() {
		return basicfieldsheader;
	}

	public void setBasicfieldsheader(String basicfieldsheader) {
		this.basicfieldsheader = basicfieldsheader;
	}
	public String getAddressfieldsheader() {
		return addressfieldsheader;
	}
	public void setAddressfieldsheader(String addressfieldsheader) {
		this.addressfieldsheader = addressfieldsheader;
	}
	public List<ConfigurationUtilBean> getConatctextPerdropdown() {
		return conatctextPerdropdown;
	}
	public void setConatctextPerdropdown(
			List<ConfigurationUtilBean> conatctextPerdropdown) {
		this.conatctextPerdropdown = conatctextPerdropdown;
	}
	public Map<Integer, String> getCustomertype() {
		return customertype;
	}
	public void setCustomertype(Map<Integer, String> customertype) {
		this.customertype = customertype;
	}
	
	public Map<Integer, String> getCustomername() {
		return customername;
	}


	public void setCustomername(Map<Integer, String> customername) {
		this.customername = customername;
	}


	public Map<Integer, String> getStatusname() {
		return statusname;
	}


	public void setStatusname(Map<Integer, String> statusname) {
		this.statusname = statusname;
	}

	

	public String getCustomer_type() {
		return customer_type;
	}


	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}


	public String getCustomer_name() {
		return customer_name;
	}


	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getInvoice_status() {
		return invoice_status;
	}

	public String getMyArray() {
		return myArray;
	}


	public void setMyArray(String myArray) {
		this.myArray = myArray;
	}


	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public List<ProductIteam> getProductlistdata() {
		return productlistdata;
	}
	public void setProductlistdata(List<ProductIteam> productlistdata) {
		this.productlistdata = productlistdata;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public List<ProductIteam> getTaxlistdata() {
		return taxlistdata;
	}
	public void setTaxlistdata(List<ProductIteam> taxlistdata) {
		this.taxlistdata = taxlistdata;
	}


	public List<ConfigurationUtilBean> getListcheckboxfiguration() {
		return Listcheckboxfiguration;
	}


	public void setListcheckboxfiguration(
			List<ConfigurationUtilBean> listcheckboxfiguration) {
		Listcheckboxfiguration = listcheckboxfiguration;
	}


	public int getGrandtotal() {
		return grandtotal;
	}


	public void setGrandtotal(int grandtotal) {
		this.grandtotal = grandtotal;
	}


	public Map<Integer, String> getContactname() {
		return contactname;
	}


	public void setContactname(Map<Integer, String> contactname) {
		this.contactname = contactname;
	}


	public String getContact_name() {
		return contact_name;
	}


	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}


	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Map<Integer, String> getProductname() {
		return productname;
	}

	public void setProductname(Map<Integer, String> productname) {
		this.productname = productname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Map<Integer, String> getListstatus() {
		return liststatus;
	}

	public void setListstatus(Map<Integer, String> liststatus) {
		this.liststatus = liststatus;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	
}
