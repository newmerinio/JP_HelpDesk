package com.Over2Cloud.ctrl.wfpm.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;

public class CommonPropertyMap {

	/**
	 * For Excel property.
	 */
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		private String key;
		public  Map<String,String> valueMap;
		public List<ConfigurationUtilBean> customerdetails = null;
		private String xyzz=null;
		private String basicdetails_table=null;
		private String addressdetails_table=null;
		private String customdetails_table=null;
		private String descriptive_table=null;
		
		public Map<String, List<ConfigurationUtilBean>> commonMap = null;
		{	
			System.out.println("HELLLLLLLLLLLLLLLLLLL......");	
			commonMap = new HashMap<String, List<ConfigurationUtilBean>>();
			customerdetails =  new ArrayList<ConfigurationUtilBean>();
			try{
			List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mapped_opportunity_details_configuration", accountID, connectionSpace,"opportunity_details_configuration");
			int l=0;
			for (GridDataPropertyView col : basicfieldNames) {
				ConfigurationUtilBean objdata=new ConfigurationUtilBean();
			    if(col!=null) {
				    if(l<basicfieldNames.size()-1){
				    	if(col.getMandatroy()!="0"){
				    		if(!col.getColomnName().equalsIgnoreCase("weightage")&&!col.getColomnName().equalsIgnoreCase("expectency")&&!col.getColomnName().equalsIgnoreCase("offeringLevelId")&&!col.getColomnName().equalsIgnoreCase("lost_reason")&&!col.getColomnName().equalsIgnoreCase("lost_offering_flag")&&!col.getColomnName().equalsIgnoreCase("opp_history"))
				    		{
				    		if(col.getColomnName().equalsIgnoreCase("clientName"))
				    		{
				    			objdata.setKey("cbd."+col.getColomnName());
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("offeringId"))
				    		{
				    			objdata.setKey("off.subofferingname");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("sales_stages"))
				    		{
				    			objdata.setKey("ssm.salesstageName");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("forecast_category"))
				    		{
				    			objdata.setKey("fcm.forcastName");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		
				    		else if(col.getColomnName().equalsIgnoreCase("convertDate"))
				    		{
				    			objdata.setKey("com.convertDate");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("lost_reason"))
				    		{
				    			objdata.setKey("lop.lostReason");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("po_number"))
				    		{
				    			objdata.setKey("com.poNumber");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("po_date"))
				    		{
				    			objdata.setKey("com.poDate");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("offering_price"))
				    		{
				    			objdata.setKey("com.offering_price");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else if(col.getColomnName().equalsIgnoreCase("userName"))
				    		{
				    			objdata.setKey("emp.empName");
				    			objdata.setValue(col.getHeadingName());
				    		}
				    		else{
				    		objdata.setKey("customer."+col.getColomnName());
				    		objdata.setValue(col.getHeadingName());
				    		}
				    		if(col.getMandatroy()==null)
				    			objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
				    		   customerdetails.add(objdata);
				    		}
				    		//objdata.setValue(col.getHeadingName());
				    		
				    	      }
					    	else{
					    		if(!col.getColomnName().equalsIgnoreCase("weightage")&&!col.getColomnName().equalsIgnoreCase("expectency")&&!col.getColomnName().equalsIgnoreCase("offeringLevelId")&&!col.getColomnName().equalsIgnoreCase("lost_reason")&&!col.getColomnName().equalsIgnoreCase("lost_offering_flag")&&!col.getColomnName().equalsIgnoreCase("opp_history"))
					    		{
					    		if(col.getColomnName().equalsIgnoreCase("clientName"))
					    		{
					    			objdata.setKey("cbd."+col.getColomnName());
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("offeringId"))
					    		{
					    			objdata.setKey("off.subofferingname");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("sales_stages"))
					    		{
					    			objdata.setKey("ssm.salesstageName");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("forecast_category"))
					    		{
					    			objdata.setKey("fcm.forcastName");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		
					    		else if(col.getColomnName().equalsIgnoreCase("convertDate"))
					    		{
					    			objdata.setKey("com.convertDate");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("lost_reason"))
					    		{
					    			objdata.setKey("lop.lostReason");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("po_number"))
					    		{
					    			objdata.setKey("com.poNumber");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("po_date"))
					    		{
					    			objdata.setKey("com.poDate");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("offering_price"))
					    		{
					    			objdata.setKey("com.offering_price");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else if(col.getColomnName().equalsIgnoreCase("userName"))
					    		{
					    			objdata.setKey("emp.empName");
					    			objdata.setValue(col.getHeadingName());
					    		}
					    		else{
					    		objdata.setKey("customer."+col.getColomnName());
					    		objdata.setValue(col.getHeadingName());
					    		}
					    		if(col.getMandatroy()==null)
					    			objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("0"))
									objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("1"))
									objdata.setMandatory(true);
					    		customerdetails.add(objdata);
					    		}
					    	//	objdata.setKey("customer."+col.getColomnName());
					    	//	objdata.setValue(col.getHeadingName());
					    	}
					        }
				    else{
				    	if(!col.getColomnName().equalsIgnoreCase("weightage")&&!col.getColomnName().equalsIgnoreCase("expectency")&&!col.getColomnName().equalsIgnoreCase("offeringLevelId")&&!col.getColomnName().equalsIgnoreCase("lost_reason")&&!col.getColomnName().equalsIgnoreCase("lost_offering_flag")&&!col.getColomnName().equalsIgnoreCase("opp_history"))
			    		{
				    	if(col.getColomnName().equalsIgnoreCase("clientName"))
			    		{
			    			objdata.setKey("cbd."+col.getColomnName());
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("offeringId"))
			    		{
			    			objdata.setKey("off.subofferingname");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("sales_stages"))
			    		{
			    			objdata.setKey("ssm.salesstageName");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("forecast_category"))
			    		{
			    			objdata.setKey("fcm.forcastName");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		
			    		else if(col.getColomnName().equalsIgnoreCase("convertDate"))
			    		{
			    			objdata.setKey("com.convertDate");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("lost_reason"))
			    		{
			    			objdata.setKey("lop.lostReason");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("po_number"))
			    		{
			    			objdata.setKey("com.poNumber");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("po_date"))
			    		{
			    			objdata.setKey("com.poDate");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("offering_price"))
			    		{
			    			objdata.setKey("com.offering_price");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else if(col.getColomnName().equalsIgnoreCase("userName"))
			    		{
			    			objdata.setKey("emp.empName");
			    			objdata.setValue(col.getHeadingName());
			    		}
			    		else{
			    		objdata.setKey("customer."+col.getColomnName());
			    		objdata.setValue(col.getHeadingName());
			    		}
				    	if(col.getMandatroy()==null)
			    			objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
			    		  customerdetails.add(objdata);
			    		}
				    //	objdata.setKey("customer."+col.getColomnName());
			    	//	objdata.setValue(col.getHeadingName());
			       	 }}
			    l++;
		      }}catch (Exception e) {
				// TODO: handle exception
			}
			commonMap.put("customerdetails", customerdetails);
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

		public String getXyzz() {
			
			return xyzz;
		}

		public void setXyzz(String xyzz) {
			this.xyzz = xyzz;
		}

		   public String getKey() {
			   
	            return key;
	    }

	    public void setKey(String key) {
	            this.key = key;
	    }

	    public List<ConfigurationUtilBean> getValueMap() {
	 
            return commonMap.get(key);
            
         }

         public List<ConfigurationUtilBean> getTitles(String key) {
            return commonMap.get(key);
         }



}
