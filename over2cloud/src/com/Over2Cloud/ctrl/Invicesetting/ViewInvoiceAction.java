/*package com.Over2Cloud.ctrl.Invicesetting;

import hibernate.common.HibernateSessionFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.ProductIteam;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ViewInvoiceAction extends ActionSupport{
	static final Log log = LogFactory.getLog(ViewInvoiceAction.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<GridDataPropertyView>contactGridColomns=new ArrayList<GridDataPropertyView>();
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
	private int id=0;
	private String headerName;
	private List<Object>  contactDetail;
	private List<ConfigurationUtilBean> basicDetail;
	private List<ConfigurationUtilBean> addressDetail;
	private String mappedtablele=null;
	private String basicdetails_table=null;
	private String addressdetails_table=null;
	private String customdetails_table=null;
	private String descriptive_table=null;
	private String createtbasicdetails_table;
	private String createaddressdetails_table;
	boolean basicflag;
	boolean adressflag;
	boolean customflag;
	boolean descirpflag;
	private List<ProductIteam> productlistdata=null;
	private List<ProductIteam> taxlistdata=null;
    private int  balance=0;
	private int percentagSum=0;
	private int grandtotal=0;
	public String beforecontactmasterView(){
		String returnString =ERROR;
		 List<GridDataPropertyView>returnResult=null;
		 List<GridDataPropertyView>returnAdrrResult=null;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
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
				headerName=objectCal[1].toString()+">>"+" "+"View";
				if (objectCal[2] != null
						&& !objectCal[2].toString().equalsIgnoreCase("")) {
					 setBasicdetails_table(objectCal[2].toString());
				returnResult=Configuration.getConfigurationData(objectCal[5].toString(),accountID,connectionSpace,"",0,"table_name",objectCal[2].toString());
				}
				if (objectCal[3] != null
						&& !objectCal[3].toString().equalsIgnoreCase("")) {
					setAddressdetails_table(objectCal[3].toString());
					returnAdrrResult=Configuration.getConfigurationData(objectCal[5].toString(),accountID,connectionSpace,"",0,"table_name",objectCal[3].toString());
				}
				if(objectCal[4] != null
						&& !objectCal[4].toString().equalsIgnoreCase("")) {
					setCustomdetails_table(objectCal[4].toString());
				}
				if (objectCal[10] != null
						&& !objectCal[10].toString().equalsIgnoreCase("")) {
					setDescriptive_table(objectCal[10].toString());
				}
					
					 setMappedtablele(objectCal[5].toString());
					 if(returnResult.size()>0){
					 for(GridDataPropertyView gdpv:returnResult){
							GridDataPropertyView gpv=new GridDataPropertyView();
							gpv.setColomnName(gdpv.getColomnName());
							gpv.setHeadingName(gdpv.getHeadingName());
							gpv.setEditable(gdpv.getEditable());
							gpv.setSearch(gdpv.getSearch());
							gpv.setHideOrShow(gdpv.getHideOrShow());
							gpv.setWidth(gdpv.getWidth());
							gpv.setFormatter(gdpv.getFormatter());
							contactGridColomns.add(gpv);
						}
					 }
					 if(returnAdrrResult!=null){
					 for(GridDataPropertyView gdpv:returnAdrrResult){
							GridDataPropertyView gpv=new GridDataPropertyView();
							gpv.setColomnName(gdpv.getColomnName());
							gpv.setHeadingName(gdpv.getHeadingName());
							gpv.setEditable(gdpv.getEditable());
							gpv.setSearch(gdpv.getSearch());
							gpv.setHideOrShow(gdpv.getHideOrShow());
							gpv.setWidth(gdpv.getWidth());
							gpv.setFormatter(gdpv.getFormatter());
							contactGridColomns.add(gpv);
						  }
				
				
				}
				returnString=SUCCESS;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return returnString;
	}

	public String viewcontactData(){
		String returnString =ERROR;
		try{
			    List<String> ObjList=new ArrayList<String>();
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				query.append("select count(*) from"+" "+getCreatetbasicdetails_table());
				List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null){
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();){
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					//getting the list of colmuns
					StringBuilder ids=new StringBuilder("");
					List<Object> Listhb=new ArrayList<Object>();
					ids.append("select ");
					if(mappedtablele != null
							&& !mappedtablele.equalsIgnoreCase("") && basicdetails_table != null && !basicdetails_table.equalsIgnoreCase("")) {
					List fieldNames=Configuration.getColomnList(mappedtablele, accountID, connectionSpace,basicdetails_table);
					int l=0;
				    basicflag=true;
					for(Iterator it=fieldNames.iterator(); it.hasNext();){
						    Object obdata=(Object)it.next();
						    if(obdata!=null) {
							    if(l<fieldNames.size()-1){
							    	if(obdata.toString().equals("customer_type")){
							    	ids.append("customertype."+obdata.toString()+",");
							    	ObjList.add(obdata.toString());
							    	 }
							    	else if(obdata.toString().equals("customer_name")){
							    		ids.append("customername."+obdata.toString()+",");
							    		ObjList.add(obdata.toString());
							    	}
							    	else if(obdata.toString().equals("invoice_status")){
							    		ids.append("statusname."+obdata.toString()+",");
							    		ObjList.add(obdata.toString());
							    	}
							    	else if(obdata.toString().equals("contact_name")){
							    		ids.append("contactname."+"sirname"+",");
							    		ids.append("contactname."+"first_name"+",");
							    		ids.append("contactname."+"last_name"+",");
							    		ObjList.add("contact_name");
							    	}
							    	else{
							    		ids.append("contactms."+obdata.toString()+",");
							    	ObjList.add(obdata.toString());}}
							    else{
							    	ids.append("contactms."+obdata.toString());
							    	ObjList.add(obdata.toString());	 }}
						    l++;
					}}
				
					if(mappedtablele != null
							&& !mappedtablele.equalsIgnoreCase("") && addressdetails_table != null && !addressdetails_table.equalsIgnoreCase("")) {
					int j=0;
				    adressflag=true;
					List bascfieldNames=Configuration.getColomnList(mappedtablele, accountID, connectionSpace, addressdetails_table);
					for(Iterator it=bascfieldNames.iterator(); it.hasNext();){
						    Object obdata=(Object)it.next();
						    if(obdata!=null) {
							    if(j<bascfieldNames.size()-1){
							    	if(!obdata.toString().trim().equals("id")){
							    	ids.append("contacadrss."+obdata.toString()+",");
							    	ObjList.add(obdata.toString());}
							    	else{
							    		ids.append(",");
							    	}}
							    else{
							    	ids.append("contacadrss."+obdata.toString());
								   ObjList.add(obdata.toString());}}
						    j++;
					}}
					if(mappedtablele != null
							&& !mappedtablele.equalsIgnoreCase("") && customdetails_table != null && !customdetails_table.equalsIgnoreCase("")) {
						int j=0;
				     customflag=true;
					List bascfieldNames=Configuration.getColomnList(mappedtablele, accountID, connectionSpace, customdetails_table);
					for(Iterator it=bascfieldNames.iterator(); it.hasNext();){
						    Object obdata=(Object)it.next();
						    if(obdata!=null) {
							    if(j<bascfieldNames.size()-1){
							    	if(!obdata.toString().trim().equals("id")){
							    	ids.append("custmdtabl."+obdata.toString()+",");
							    	ObjList.add(obdata.toString());}
							    	else{}}
							    else{
							    	ids.append("custmdtabl."+obdata.toString());
								   ObjList.add(obdata.toString());}}
						    j++;
					}}
					if(mappedtablele != null
							&& !mappedtablele.equalsIgnoreCase("") && descriptive_table != null && !descriptive_table.equalsIgnoreCase("")) {
						int j=0;
					descirpflag=true;
					List bascfieldNames=Configuration.getColomnList(mappedtablele, accountID, connectionSpace, descriptive_table);
					for(Iterator it=bascfieldNames.iterator(); it.hasNext();){
						    Object obdata=(Object)it.next();
						    if(obdata!=null) {
							    if(j<bascfieldNames.size()-1){
							    	if(!obdata.toString().trim().equals("id")){
							    	ids.append("descrpdtabl."+obdata.toString()+",");
							    	ObjList.add(obdata.toString());}
							    	else{}}
							    else{
							    	ids.append("descrpdtabl."+obdata.toString());
								   ObjList.add(obdata.toString());}}
						    j++;
					}}
			
				 if(basicflag && adressflag){
				 ids.append(" "+" from"+ " "+getCreatetbasicdetails_table()+" as contactms "+" "+"left join "+" "+getCreateaddressdetails_table()+" "+"as contacadrss on "+" "+"contactms"+"."+"id="+"contacadrss"+".mappedid" ); }
				 else if((getSearchField() != null && !getSearchField().equalsIgnoreCase(""))||(getSearchString() != null && !getSearchString().equalsIgnoreCase(""))){
					 ids.append(" "+" from"+ " "+getCreatetbasicdetails_table()+" as contactms "
							 +" "+" inner join"+" "+"createcustomertype_table"+" "+"as customertype on "+" "+"contactms.customer_type="+" "+"customertype.id"
							 +" "+" inner join"+" "+"createtcustomerdetails_table"+" "+"as customername on "+" "+"contactms.customer_name="+" "+"customername.id"
							 +" "+" inner join"+" "+"createinvoicestatus_table"+" "+"as statusname on "+" "+"contactms.invoice_status="+" "+"statusname.id"
							 +" "+" inner join"+" "+"contactbasicdetails"+" "+"as contactname on "+" "+"contactms.contact_name="+" "+"contactname.id"
							 +" "+" where"+" "+"contactms."+getSearchField()+"="+" "+getSearchString()
							 );
				 }
				 else{
					 ids.append(" "+" from"+ " "+getCreatetbasicdetails_table()+" as contactms"
					 +" "+" inner join"+" "+"createcustomertype_table"+" "+"as customertype on "+" "+"contactms.customer_type="+" "+"customertype.id"
					 +" "+" inner join"+" "+"createtcustomerdetails_table"+" "+"as customername on "+" "+"contactms.customer_name="+" "+"customername.id"
					 +" "+" inner join"+" "+"createinvoicestatus_table"+" "+"as statusname on "+" "+"contactms.invoice_status="+" "+"statusname.id"
					 +" "+" inner join"+" "+"contactbasicdetails"+" "+"as contactname on "+" "+"contactms.contact_name="+" "+"contactname.id"
					 );
				  }
			
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")){
				
					   if(getSearchOper().equalsIgnoreCase("cn")){
						ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");}
					else if(getSearchOper().equalsIgnoreCase("bw")){
						ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");}
					else if(getSearchOper().equalsIgnoreCase("ne")){
						ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");}
					else if(getSearchOper().equalsIgnoreCase("ew")){
						ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
					}}
				ids.append(" limit "+from+","+to);
	
				List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
				
				String [] fieldsArray = ObjList.toArray(new String[ObjList.size()]);
					if(data!=null){
						if(data.size()>0){
							for(Iterator it=data.iterator(); it.hasNext();){
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldsArray.length; k++) {
									if(obdata[k]!=null){
									if(k==0)
										one.put(fieldsArray[k], (Integer)obdata[k]);
									else{
										if(fieldsArray[k].toString().equalsIgnoreCase("contact_name")){//5
											String contact_name="";
											for(int i=k; i<(k+3); i++){
												contact_name+= obdata[i].toString()+" ";}
											one.put(fieldsArray[k], contact_name);}
										 else if(fieldsArray[k].toString().equalsIgnoreCase("product_name")){
											String[] productid= obdata[k].toString().split("#");
											List<String>productcolname=new ArrayList<String>();
											productcolname.add("product_name");
											for (int i = 0; i < productid.length; i++) {
												   Map<String, Object>wherClause= new HashMap<String, Object>();
											       wherClause.put("id",productid[i]);
											List customertypeData=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname, wherClause, connectionSpace);
											if(customertypeData!=null){
												for (Object c : customertypeData) {
													Object dataC = (Object) c;
													one.put(fieldsArray[k], dataC.toString());
											  }
										   }
											}
										 }
										    else{
											one.put(fieldsArray[k], obdata[k].toString());
											}
										}
									}
								}
								Listhb.add(one);
							}
							setContactDetail(Listhb);
						}
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
			}
			returnString=SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return returnString;
	}

    public String getinvoicefullview(){
    	String returnString =ERROR;
		try{
			  List<String> ObjList=new ArrayList<String>();
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
			//getting the list of colmuns
			StringBuilder ids=new StringBuilder("");
			List<ConfigurationUtilBean> Listhb=new ArrayList<ConfigurationUtilBean>();
			List<ConfigurationUtilBean> addressList=new ArrayList<ConfigurationUtilBean>();
			ids.append("select ");
			List<GridDataPropertyView> fieldNames=Configuration.getColomnfieldList("mapped_invoices_configuration", accountID, connectionSpace,"invoices_configuration");
			int l=0;
			for (GridDataPropertyView obdata : fieldNames) {
				    if(obdata!=null) {
					    	if(obdata.getColomnName().equals("product_name")){
						    	ids.append("contactms."+obdata.getColomnName());
						    	ObjList.add("product_name");}
					    	if(obdata.getColomnName().equals("customer_type")){
					    	ids.append("customertype."+obdata.getColomnName()+",");
					    	ObjList.add(obdata.getHeadingName());
					    	 }
					    	else if(obdata.getColomnName().equals("customer_name")){
					    		ids.append("customername."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("invoice_status")){
					    		ids.append("statusname."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("contact_name")){
					    		ids.append("contactname."+"sirname"+",");
					    		ids.append("contactname."+"first_name"+",");
					    		ids.append("contactname."+"last_name"+",");
					    		ObjList.add("contact_name");
					    	}
					    	else if(obdata.getColomnName().equals("invoice_date")){
					    		ids.append(obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("invoice_duedate")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("subject")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("salesorder")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("purchase_order")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("description")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getHeadingName());
					    	}
					    	else if(obdata.getColomnName().equals("address")){
					    		ids.append(obdata.getColomnName()+",");
					    		ObjList.add(obdata.getColomnName());
					    	}
					    	else if(obdata.getColomnName().equals("city")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getColomnName());
					    	}
					    	else if(obdata.getColomnName().equals("state")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getColomnName());
					    	}
					    	else if(obdata.getColomnName().equals("postal_code")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getColomnName());
					    	}
					    	else if(obdata.getColomnName().equals("country")){
					    		ids.append("contactms."+obdata.getColomnName()+",");
					    		ObjList.add(obdata.getColomnName());
					    	}
					    	else{
					    		
					    	}}
			}
			 ids.append(" "+" from"+ " "+"createtinvovcedetails_table"+" as contactms"
			 +" "+" inner join"+" "+"createcustomertype_table"+" "+"as customertype on "+" "+"contactms.customer_type="+" "+"customertype.id"
			 +" "+" inner join"+" "+"createtcustomerdetails_table"+" "+"as customername on "+" "+"contactms.customer_name="+" "+"customername.id"
			 +" "+" inner join"+" "+"createinvoicestatus_table"+" "+"as statusname on "+" "+"contactms.invoice_status="+" "+"statusname.id"
			 +" "+" inner join"+" "+"contactbasicdetails"+" "+"as contactname on "+" "+"contactms.contact_name="+" "+"contactname.id"
			 +" "+" where contactms.id="+" "+getId()
			 );
			 System.out.println("ids"+ids);
			 List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
				String [] fieldsArray = ObjList.toArray(new String[ObjList.size()]);
					if(data!=null){
						if(data.size()>0){
							for(Iterator it=data.iterator(); it.hasNext();){
								Object[] obdata=(Object[])it.next();
							
								for (int k = 0; k < fieldsArray.length; k++) {
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									if(obdata[k]!=null){
									if(k==0){
										objEjb.setField_name(fieldsArray[k]);
									    objEjb.setField_value(obdata[k].toString()); 
									    Listhb.add(objEjb);    }
									 else{
										if(fieldsArray[k].toString().equalsIgnoreCase("contact_name")){//5
											String contact_name="";
											for(int i=k; i<(k+3); i++){
											contact_name+= obdata[i].toString()+" ";}
											objEjb.setField_name("Contact Name");
											objEjb.setField_value( contact_name);
											 Listhb.add(objEjb);	}
										 else{
											   if(fieldsArray[k].toString().equalsIgnoreCase("address")){
											    	objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													addressList.add(objEjb);
												 }
											 else if(fieldsArray[k].toString().equalsIgnoreCase("city")){
											    	objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													addressList.add(objEjb);
												 }
											 else if(fieldsArray[k].toString().equalsIgnoreCase("state")){
											    	objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													addressList.add(objEjb);
												 }
											 else if(fieldsArray[k].toString().equalsIgnoreCase("postal_code")){
											    	objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													addressList.add(objEjb);}
											 else if(fieldsArray[k].toString().equalsIgnoreCase("country")){
											    	objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													addressList.add(objEjb);}
											   
											 else{
												 if(!fieldsArray[k].toString().equalsIgnoreCase("product_name")){
													objEjb.setField_name(fieldsArray[k]);
													objEjb.setField_value(obdata[k].toString());
													Listhb.add(objEjb);
												   }
												 }
											}
										if(fieldsArray[k].toString().equalsIgnoreCase("product_name")){
											String[] productid= obdata[k+2].toString().split("#");
											List<String>productcolname=new ArrayList<String>();
											List<ProductIteam>productlsitdata=new ArrayList<ProductIteam>();
											productcolname.add("id");
											productcolname.add("product_name");
											productcolname.add("product_category");
											productcolname.add("qtyt");
											productcolname.add("unit_price");
											for (int i = 0; i < productid.length; i++) {
												   Map<String, Object>wherClause= new HashMap<String, Object>();
											       wherClause.put("id",productid[i]);
											List customertypeData=cbt.viewAllDataEitherSelectOrAll("createtproductdetails_table",productcolname, wherClause, connectionSpace);
											if(customertypeData!=null){
												for (Object c : customertypeData) {
													Object[] dataC = (Object[]) c;
													ProductIteam listdata=new ProductIteam();
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
											    setProductlistdata(productlsitdata);	
												setBalance(total);
										   }
											//add services taxes
											List<String> objlistdata=new ArrayList<String>();
											objlistdata.add("tax_name");
											objlistdata.add("tax_price");
											List taxlistdata=cbt.viewAllDataEitherSelectOrAll("createtax_table",objlistdata,connectionSpace);
											List<ProductIteam> lstdataobhj=new ArrayList<ProductIteam>();
											for (Iterator iterator2 = taxlistdata.iterator(); iterator2.hasNext();) {
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
											}
										 }else{
											    
										 }
										   }
									 
									}
								setBasicDetail(Listhb);
						        setAddressDetail(addressList);
								}
						
							}
						}
							
						
		         	returnString=SUCCESS;
						
					}
		    
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	return returnString;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<GridDataPropertyView> getContactGridColomns() {
		return contactGridColomns;
	}

	public void setContactGridColomns(List<GridDataPropertyView> contactGridColomns) {
		this.contactGridColomns = contactGridColomns;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public List<Object> getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(List<Object> contactDetail) {
		this.contactDetail = contactDetail;
	}

	public String getMappedtablele() {
		return mappedtablele;
	}

	public void setMappedtablele(String mappedtablele) {
		this.mappedtablele = mappedtablele;
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

	public List<ConfigurationUtilBean> getBasicDetail() {
		return basicDetail;
	}

	public void setBasicDetail(List<ConfigurationUtilBean> basicDetail) {
		this.basicDetail = basicDetail;
	}

	public List<ProductIteam> getProductlistdata() {
		return productlistdata;
	}

	public void setProductlistdata(List<ProductIteam> productlistdata) {
		this.productlistdata = productlistdata;
	}

	public List<ProductIteam> getTaxlistdata() {
		return taxlistdata;
	}

	public void setTaxlistdata(List<ProductIteam> taxlistdata) {
		this.taxlistdata = taxlistdata;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getPercentagSum() {
		return percentagSum;
	}

	public void setPercentagSum(int percentagSum) {
		this.percentagSum = percentagSum;
	}

	public int getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(int grandtotal) {
		this.grandtotal = grandtotal;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ConfigurationUtilBean> getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(List<ConfigurationUtilBean> addressDetail) {
		this.addressDetail = addressDetail;
	}



}
*/