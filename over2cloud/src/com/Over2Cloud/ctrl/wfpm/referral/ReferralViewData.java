package com.Over2Cloud.ctrl.wfpm.referral;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

public class ReferralViewData extends GridPropertyBean
{

	/**
	 * 
	 */
	private Map<String, String> orgmap,indmap,contactmap,statusmap,statusmap1;
	private static final long serialVersionUID = 1L;
	private List<GridDataPropertyView> materViewProp=null;
	private List<GridDataPropertyView> masterViewListForContact=null;
	private String referralStatus,tabel;
	private String fromdate,fromdate1,todate,todate1,from_source,from_source1,searchParameter,searchParameter1,data_status;
	private JSONArray dataArray = new JSONArray();
	
	@SuppressWarnings("unchecked")
	public String viewReferralGridData(){
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataCount = cbt.executeAllSelectQuery("Select count(*) from referral_contact_data", connectionSpace);
			
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				Object obdata=null;
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();

				StringBuilder queryTemp = new StringBuilder("");
				
				queryTemp.append("SELECT ");
				List fieldNames = Configuration.getColomnList(
						"mapped_referral_individual_data",
						accountID, connectionSpace,
						"referral_individual_data_configuration");
				List Listhb = new ArrayList();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1)
						{
						
							if(obdata.toString().equalsIgnoreCase("emp_name") || obdata.toString().equalsIgnoreCase("comm_name") || obdata.toString().equalsIgnoreCase("mobile_no") || obdata.toString().equalsIgnoreCase("email_id") || obdata.toString().equalsIgnoreCase("dob") || obdata.toString().equalsIgnoreCase("doa") || obdata.toString().equalsIgnoreCase("address") || obdata.toString().equalsIgnoreCase("degreeOfInfluence"))
							{
								queryTemp.append("emp."+obdata.toString() + ",");
							}
							else if(obdata.toString().equalsIgnoreCase("country"))
							{
								queryTemp.append(" ctry.country_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("state"))
							{
								queryTemp.append(" st.state_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("city"))
							{
								queryTemp.append(" cty.city_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("acc_mgr"))
							{
								queryTemp.append(" emp1.emp_name as acc_mgr, ");
							}
							else if(obdata.toString().equalsIgnoreCase("from_source"))
							{
								queryTemp.append(" src.sourceName , ");
							}
							else if(obdata.toString().equalsIgnoreCase("territory"))
							{
								queryTemp.append(" terr.trr_name , ");
							}
							
							else{
								queryTemp.append("rcd."+obdata.toString() + ",");
							}
						}
						else
							queryTemp.append("rcd."+obdata.toString());
					}
					i++;
				}
				queryTemp.append(" FROM referral_contact_data as rcd ");
				queryTemp.append(" LEFT JOIN primary_contact AS emp ON emp.id=rcd.map_emp_id ");
				queryTemp.append(" LEFT JOIN contact_sub_type AS dept ON dept.id=emp.sub_type_id ");
				queryTemp.append(" LEFT JOIN city AS cty ON cty.id=rcd.city");
				queryTemp.append("  LEFT JOIN territory AS terr ON terr.id=rcd.territory ");
				queryTemp.append(" LEFT JOIN state AS st ON st.id=cty.name_state");
				queryTemp.append(" INNER JOIN sourcemaster as src on src.id=rcd.from_source");  
				queryTemp.append(" LEFT JOIN country AS ctry ON ctry.id=cty.code_country");
				queryTemp.append(" LEFT JOIN rel_mgr_data AS rmd ON rmd.id=rcd.acc_mgr" +
						" INNER JOIN manage_contact as cc on cc.id=rmd.name " +
						" INNER JOIN primary_contact as emp1 on emp1.id=cc.emp_id where ");
				
				
				if (this.getReferralStatus() != null && !this.getReferralStatus().equals("-1")	&& !this.getReferralStatus().equalsIgnoreCase("undefined"))
				{
					if (this.getReferralStatus().equalsIgnoreCase("All Status"))
					{
						queryTemp.append(" status IS Not Null ");
					}
					else
					{
						queryTemp.append(" rcd.status='" + this.getReferralStatus() + "'");
					}
				}
				if (this.getData_status() != null && !this.getData_status().equals("-1")	&& !this.getData_status().equalsIgnoreCase("undefined"))
				{
					if (this.getData_status().equalsIgnoreCase("All Status"))
					{
						queryTemp.append(" and rcd.data_status IS Not Null ");
					}
					else
					{
						queryTemp.append(" and rcd.data_status='" + this.getData_status() + "'");
					}
				}
				
				if (this.getFrom_source() != null && !this.getFrom_source().equals("-1")	&& !this.getFrom_source().equalsIgnoreCase("undefined"))
				{
						queryTemp.append(" and rcd.from_source='" + this.getFrom_source() + "'");
				}
				
				else if (getFromdate() != null && !getFromdate().equals("")
					    && getTodate() != null && !getTodate().equals(""))
				{	setFromdate(DateUtil.convertDateToUSFormat(getFromdate()));
					setTodate(DateUtil.convertDateToUSFormat(getTodate()));
					queryTemp.append("and rcd.date >= '" + DateUtil.convertDateToUSFormat(getFromdate())
					    + "' and rcd.date <= '" + DateUtil.convertDateToUSFormat(getTodate()) + "'");
				}
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					if(getSearchField().equalsIgnoreCase("emp_name") || getSearchField().equalsIgnoreCase("comm_name") || getSearchField().equalsIgnoreCase("mobile_no") || getSearchField().equalsIgnoreCase("email_id") || getSearchField().equalsIgnoreCase("dob") || getSearchField().equalsIgnoreCase("doa") || getSearchField().equalsIgnoreCase("address") || getSearchField().equalsIgnoreCase("degreeOfInfluence"))
					{
						setSearchField("emp."+getSearchField());
					}
					// add search query based on the search operation
					queryTemp.append(" and ");
					if (getSearchOper().substring(2,getSearchOper().length()).equalsIgnoreCase("eq"))
					{
						queryTemp.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						queryTemp.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						queryTemp.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if(searchParameter != null && !searchParameter.equalsIgnoreCase(""))
				{      
					queryTemp.append(" and ");
				    if (fieldNames != null && !fieldNames.isEmpty()) 
				       {  
				    	  int k=0;
				      for (Iterator iterator = fieldNames.iterator(); iterator
				                                                  .hasNext();) 
				           {  
				                 String object = iterator.next().toString();
				 	         if (object != null)
				 	          {
				 	        	if(object.toString().equalsIgnoreCase("emp_name") || object.toString().equalsIgnoreCase("comm_name") || object.toString().equalsIgnoreCase("mobile_no") || object.toString().equalsIgnoreCase("email_id") || object.toString().equalsIgnoreCase("dob") || object.toString().equalsIgnoreCase("doa") || object.toString().equalsIgnoreCase("address") || object.toString().equalsIgnoreCase("degreeOfInfluence"))
								{
				 	        		queryTemp.append(" emp."+object.toString()+" LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("country"))
								{
									queryTemp.append(" ctry.country_name LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("state"))
								{
									queryTemp.append(" st.state_name LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("city"))
								{
									queryTemp.append(" cty.city_name LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("acc_mgr"))
								{
									queryTemp.append(" emp1.emp_name LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("from_source"))
								{
									queryTemp.append(" src.sourceName LIKE "+"'%"+searchParameter+"%"+"'");
								}
								else{
									queryTemp.append(" rcd."+object.toString()+" LIKE "+"'%"+searchParameter+"%"+"'");
								}
				 	          }
				 	        if(k < fieldNames.size()-1)
				 	         {
				 	        	/*if (!object.toString().equalsIgnoreCase("clientName"))
				 	        	{ */
				 	        	queryTemp.append(" OR ");
				 	            /*}*/
				 	         }	
				 	        	else 
				 	        		queryTemp.append(" ");
				                 k++;
				           }
				    }
				}
				
				
	          System.out.println("query:::" + queryTemp);
		      List data = cbt.executeAllSelectQuery(queryTemp.toString(),
						connectionSpace);
				if (data != null)
				{
					Object[] obdata11 = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						obdata11 = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase("")){
								if (fieldNames.get(k).toString().equals("degreeOfInfluence"))
								{
									System.out.println("STATUS TRUE::::::::::::::::::");
									if(obdata11[k].toString().equalsIgnoreCase("1")){
										one.put(fieldNames.get(k).toString(),"1-Low");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("2")){
										one.put(fieldNames.get(k).toString(),"2-Normal");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("3")){
										one.put(fieldNames.get(k).toString(),"3-Medium");	
									}
									else if(obdata11[k].toString().equalsIgnoreCase("4")){
										one.put(fieldNames.get(k).toString(),"4-High");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("5")){
										one.put(fieldNames.get(k).toString(),"5-Very High");
									}
								}
								else if (fieldNames.get(k).toString()
										.equals("date"))
								{
									one.put(fieldNames.get(k).toString(),
											DateUtil.convertDateToIndianFormat(obdata11[k]
													.toString()));
								}
								else if (fieldNames.get(k).toString()
										.equals("userName"))
								{
									one.put(fieldNames.get(k).toString(),
											DateUtil.makeTitle(obdata11[k]
													.toString()));
								}
								else if (fieldNames.get(k).toString()
										.equals("data_status"))
								{
									if(obdata11[k].toString().equalsIgnoreCase("0")){
										one.put(fieldNames.get(k).toString(),"Active");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("2")){
										one.put(fieldNames.get(k).toString(),"Inactive");
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(),
											obdata11[k].toString());
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMaterViewProp(Listhb);
					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
				}
				 return SUCCESS;
						
					}

		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		 return ERROR;
	}
	
	
	public String viewReferralCorGridData(){
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataCount = cbt.executeAllSelectQuery("Select count(*) from referral_institutional_data", connectionSpace);
			
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				Object obdata=null;
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				
				StringBuilder queryTemp = new StringBuilder("");
				
				queryTemp.append("SELECT ");
				List fieldNames = Configuration.getColomnList(
						"mapped_referral_institutional_data",
						accountID, connectionSpace,
						"referral_institutional_data_configuration");
				List Listhb = new ArrayList();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1)
						{
						
							if(obdata.toString().equalsIgnoreCase("emp_name") || obdata.toString().equalsIgnoreCase("comm_name") || obdata.toString().equalsIgnoreCase("mobile_no") || obdata.toString().equalsIgnoreCase("email_id") || obdata.toString().equalsIgnoreCase("dob") || obdata.toString().equalsIgnoreCase("doa") || obdata.toString().equalsIgnoreCase("degreeOfInfluence"))
							{
								queryTemp.append("emp."+obdata.toString() + ",");
							}
							else if(obdata.toString().equalsIgnoreCase("country"))
							{
								queryTemp.append(" ctry.country_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("state"))
							{
								queryTemp.append(" st.state_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("city"))
							{
								queryTemp.append(" cty.city_name , ");
							}
							else if(obdata.toString().equalsIgnoreCase("acc_mgr"))
							{
								queryTemp.append(" emp1.emp_name as acc_mgr, ");
							}
							else if(obdata.toString().equalsIgnoreCase("from_source"))
							{
								queryTemp.append(" sm.sourceName , ");
							}
							else if(obdata.toString().equalsIgnoreCase("territory"))
							{
								queryTemp.append(" terr.trr_name , ");
							}
							else{
								queryTemp.append("rid."+obdata.toString() + ",");
							}
						}
						else
							queryTemp.append("rid."+obdata.toString());
					}
					i++;
				}
				queryTemp.append(" FROM referral_institutional_data as rid ");
			
				queryTemp.append(" LEFT JOIN city AS cty ON cty.id=rid.city");
				queryTemp.append("  LEFT JOIN territory AS terr ON terr.id=rid.territory ");
				queryTemp.append(" LEFT JOIN state AS st ON st.id=cty.name_state");
				queryTemp.append(" LEFT JOIN country AS ctry ON ctry.id=cty.code_country");
				queryTemp.append(" LEFT JOIN rel_mgr_data AS rmd ON rmd.id=rid.acc_mgr");
				queryTemp.append(" inner join sourcemaster as sm on sm.id=rid.from_source ");
				queryTemp.append(" INNER JOIN manage_contact as cc on cc.id=rmd.name "); 
				queryTemp.append(" INNER JOIN primary_contact as emp1 on emp1.id=cc.emp_id where ");
				
				if (this.getReferralStatus() != null && !this.getReferralStatus().equals("-1")	&& !this.getReferralStatus().equalsIgnoreCase("undefined"))
				{
					if (this.getReferralStatus().equalsIgnoreCase("All Status"))
					{
						queryTemp.append(" status IS Not Null ");
					}
					else
					{
						queryTemp.append(" rid.status='" + this.getReferralStatus() + "'");
					}
				}
				if (this.getFrom_source1() != null && !this.getFrom_source1().equals("-1")	&& !this.getFrom_source1().equalsIgnoreCase("undefined"))
				{
						queryTemp.append(" and rid.from_source='" + this.getFrom_source1() + "'");
				}
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					queryTemp.append(" and ");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						queryTemp.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						queryTemp.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						queryTemp.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getFromdate1() != null && !getFromdate1().equals("")
					    && getTodate1() != null && !getTodate1().equals(""))
				{	setFromdate1(DateUtil.convertDateToUSFormat(getFromdate1()));
					setTodate1(DateUtil.convertDateToUSFormat(getTodate1()));
					queryTemp.append("and rid.date >= '" + DateUtil.convertDateToUSFormat(getFromdate1())
					    + "' and rid.date <= '" + DateUtil.convertDateToUSFormat(getTodate1()) + "'");
				}
				
				if (this.getData_status() != null && !this.getData_status().equals("-1")	&& !this.getData_status().equalsIgnoreCase("undefined"))
				{
					if (this.getData_status().equalsIgnoreCase("All Status"))
					{
						queryTemp.append(" and rid.data_status IS Not Null ");
					}
					else
					{
						queryTemp.append(" and rid.data_status='" + this.getData_status() + "'");
					}
				}
				
				if(searchParameter1 != null && !searchParameter1.equalsIgnoreCase(""))
				{      
					queryTemp.append(" and ");
				    if (fieldNames != null && !fieldNames.isEmpty()) 
				       {  
				    	  int k=0;
				      for (Iterator iterator = fieldNames.iterator(); iterator
				                                                  .hasNext();) 
				           {  
				                 String object = iterator.next().toString();
				 	         if (object != null)
				 	          {
				 	        	if(object.toString().equalsIgnoreCase("emp_name") || object.toString().equalsIgnoreCase("comm_name") || object.toString().equalsIgnoreCase("mobile_no") || object.toString().equalsIgnoreCase("email_id") || object.toString().equalsIgnoreCase("dob") || object.toString().equalsIgnoreCase("doa") || object.toString().equalsIgnoreCase("degreeOfInfluence"))
								{
				 	        		queryTemp.append(" emp."+object.toString()+" LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("country"))
								{
									queryTemp.append(" ctry.country_name LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("state"))
								{
									queryTemp.append(" st.state_name LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("city"))
								{
									queryTemp.append(" cty.city_name LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("acc_mgr"))
								{
									queryTemp.append(" emp1.emp_name LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else if(object.toString().equalsIgnoreCase("from_source"))
								{
									queryTemp.append(" sm.sourceName LIKE "+"'%"+searchParameter1+"%"+"'");
								}
								else{
									queryTemp.append(" rid."+object.toString()+" LIKE "+"'%"+searchParameter1+"%"+"'");
								}
				 	          }
				 	        if(k < fieldNames.size()-1)
				 	         {
				 	        	/*if (!object.toString().equalsIgnoreCase("clientName"))
				 	        	{ */
				 	        	queryTemp.append(" OR ");
				 	            /*}*/
				 	         }	
				 	        	else 
				 	        		queryTemp.append(" ");
				                 k++;
				           }
				    }
				}
				
				
	          //System.out.println("query:::" + queryTemp);
              List data = cbt.executeAllSelectQuery(queryTemp.toString(),
						connectionSpace);
				if (data != null)
				{
					Object[] obdata11 = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						obdata11 = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata11[k] != null
									&& !obdata11[k].toString()
											.equalsIgnoreCase(""))
							{
								if (fieldNames.get(k).toString()
										.equals("date"))
								{
									one.put(fieldNames.get(k).toString(),
											DateUtil.convertDateToIndianFormat(obdata11[k]
													.toString()));
								}
								else if (fieldNames.get(k).toString()
										.equals("rating"))
								{
									if(obdata11[k].toString().equalsIgnoreCase("1")){
										one.put(fieldNames.get(k).toString(),"1-Low Potential");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("2")){
										one.put(fieldNames.get(k).toString(),"2-Normal Potential");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("3")){
										one.put(fieldNames.get(k).toString(),"3-Medium Potential");	
									}
									else if(obdata11[k].toString().equalsIgnoreCase("4")){
										one.put(fieldNames.get(k).toString(),"4-High Potential");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("5")){
										one.put(fieldNames.get(k).toString(),"5-Very High Potential");
									}
								}
								else if (fieldNames.get(k).toString()
										.equals("userName"))
								{
									one.put(fieldNames.get(k).toString(),
											DateUtil.makeTitle(obdata11[k]
													.toString()));
								}
								else if (fieldNames.get(k).toString()
										.equals("data_status"))
								{
									if(obdata11[k].toString().equalsIgnoreCase("0")){
										one.put(fieldNames.get(k).toString(),"Active");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("2")){
										one.put(fieldNames.get(k).toString(),"Inactive");
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(),
											obdata11[k].toString());
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMaterViewProp(Listhb);
					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
				}

	          
	          return SUCCESS;
						
					}

		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		 return ERROR;
	}
	
	
	public String viewReferralCorContact(){
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataCount = cbt.executeAllSelectQuery("Select count(*) from referral_institutional_mapcontact where org_name='"+id+"'", connectionSpace);
			
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();

				// getting the list of colmuns
				 
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select rim.id,emp.emp_name,emp.comm_name,emp.mobile_no,emp.email_id,emp.dob ");
				queryTemp.append(" ,emp.degreeOfInfluence from referral_institutional_mapcontact as rim ");
				queryTemp.append(" inner join referral_institutional_data as rid on rid.id=rim.org_name ");
				queryTemp.append(" inner join primary_contact as emp on rim.map_emp_id=emp.id where rim.org_name='"+id+"'");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					queryTemp.append(" and ");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						queryTemp.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						queryTemp.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						queryTemp.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						queryTemp.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}
				

				//System.out.println("query:::" + queryTemp);
				List data = cbt.executeAllSelectQuery(queryTemp.toString(), connectionSpace);
				List tempList = new ArrayList();
				materViewProp = new ArrayList<GridDataPropertyView>();
				if (data != null)
				{ 
					for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
					{
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						Object[] object = (Object[]) iterator2.next();
						
						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("emp_name", getValueWithNullCheck(object[1]));
						tempMap.put("comm_name", getValueWithNullCheck(object[2]));
						tempMap.put("mobile_no", getValueWithNullCheck(object[3]));
						tempMap.put("email_id", getValueWithNullCheck(object[4]));
						tempMap.put("dob",  getValueWithNullCheck(object[5]));
				if(object[6]!=null){
						if(object[6].toString().equalsIgnoreCase("1")){
							tempMap.put("degreeOfInfluence","1-Low");
						}
						else if(object[6].toString().equalsIgnoreCase("2")){
							tempMap.put("degreeOfInfluence","2-Normal");
						}
						else if(object[6].toString().equalsIgnoreCase("3")){
							tempMap.put("degreeOfInfluence","3-Medium");	
						}
						else if(object[6].toString().equalsIgnoreCase("4")){
							tempMap.put("degreeOfInfluence","4-High");
						}
						else if(object[6].toString().equalsIgnoreCase("5")){
							tempMap.put("degreeOfInfluence","5-Very High");
						}
					}
			 		  	tempList.add(tempMap);
			 		}
				}
				this.setMasterViewListForContact(tempList);
				
				
				 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				 return SUCCESS;
						
					}

		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		 return ERROR;
	}
	
	
	public String OrganisationBasicDetails()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			orgmap = new LinkedHashMap<String, String>();

			sb.append("select rid.contact,rid.website,rid.landmark,rid.fax,sm.sourceName,emp1.emp_name as acc_mgr,rid.address,city.city_name,state.state_name,country.country_name,rid.user_name,rid.date from referral_institutional_data as rid ");
			sb.append(" inner join sourcemaster as sm on sm.id=rid.from_source "); 
			sb.append(" inner join city as city on city.id=rid.city "); 
			sb.append(" inner join country as country on city.code_country=country.id "); 
			sb.append(" inner join state as state on city.name_state=state.id "); 
			sb.append(" LEFT JOIN rel_mgr_data AS rmd ON rmd.id=rid.acc_mgr "); 
			sb.append(" INNER JOIN manage_contact as cc on cc.id=rmd.name "); 
			sb.append(" INNER JOIN primary_contact as emp1 on emp1.id=cc.emp_id ");
			sb.append(" where rid.id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				orgmap.put("Contact No", getValueWithNullCheck(data[0]));
				orgmap.put("Website", getValueWithNullCheck(data[1]));
				orgmap.put("Landmark", getValueWithNullCheck(data[2]));
				orgmap.put("Fax", getValueWithNullCheck(data[3]));
				orgmap.put("Source", getValueWithNullCheck(data[4]));
				orgmap.put("Account Manager", getValueWithNullCheck(data[5]));
				orgmap.put("Address", getValueWithNullCheck(data[6]));
				orgmap.put("City", getValueWithNullCheck(data[7]));
				orgmap.put("State", getValueWithNullCheck(data[8]));
				orgmap.put("Country", getValueWithNullCheck(data[9]));
				orgmap.put("Created By", getValueWithNullCheck(data[10]));
				orgmap.put("Created On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[11].toString())));
		    }
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String IndividualBasicDetails()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			indmap = new LinkedHashMap<String, String>();

			sb.append(" SELECT emp.emp_name,emp.comm_name,emp.mobile_no,emp.email_id,emp.dob,emp.doa,emp.address,cty.city_name,st.state_name,ctry.country_name,rcd.user_name,rcd.date ");
			sb.append(" FROM referral_contact_data as rcd ");
			sb.append(" LEFT JOIN primary_contact AS emp ON emp.id=rcd.map_emp_id ");
			sb.append(" LEFT JOIN contact_sub_type AS dept ON dept.id=emp.sub_type_id ");
			sb.append(" LEFT JOIN city AS cty ON cty.id=rcd.city ");
			sb.append(" LEFT JOIN state AS st ON st.id=cty.name_state ");
			sb.append(" INNER JOIN sourcemaster as src on src.id=rcd.from_source ");  
			sb.append(" LEFT JOIN country AS ctry ON ctry.id=cty.code_country");
			sb.append(" LEFT JOIN rel_mgr_data AS rmd ON rmd.id=rcd.acc_mgr" +
					" INNER JOIN manage_contact as cc on cc.id=rmd.name " +
					" INNER JOIN primary_contact as emp1 on emp1.id=cc.emp_id ");
			sb.append(" where rcd.id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				indmap.put("Name", getValueWithNullCheck(data[0]));
				indmap.put("Communication Name", getValueWithNullCheck(data[1]));
				indmap.put("Mobile No", getValueWithNullCheck(data[2]));
				indmap.put("Email Id", getValueWithNullCheck(data[3]));
				indmap.put("DOB", getValueWithNullCheck(data[4]));
				indmap.put("DOA", getValueWithNullCheck(data[5]));
				indmap.put("Address", getValueWithNullCheck(data[6]));
				indmap.put("City", getValueWithNullCheck(data[7]));
				indmap.put("State", getValueWithNullCheck(data[8]));
				indmap.put("Country", getValueWithNullCheck(data[9]));
				indmap.put("Created By", getValueWithNullCheck(data[10]));
				indmap.put("Created On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[11].toString())));
		    }
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	
	public String mapContactDetails()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			contactmap = new LinkedHashMap<String, String>();

			sb.append(" select emp1.emp_name,emp1.comm_name,emp1.mobile_no, emp1.email_id,emp1.dob,emp1.doa,rim.designation,cst.contact_subtype_name,rim.gender from referral_institutional_mapcontact as rim ");
			sb.append(" INNER JOIN primary_contact as emp1 on emp1.id=rim.map_emp_id ");
			sb.append(" INNER JOIN contact_sub_type as cst on emp1.sub_type_id=cst.id ");
			sb.append(" where rim.id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				contactmap.put("Name", getValueWithNullCheck(data[0]));
				contactmap.put("Communication Name", getValueWithNullCheck(data[1]));
				contactmap.put("Mobile No", getValueWithNullCheck(data[2]));
				contactmap.put("Email Id", getValueWithNullCheck(data[3]));
				contactmap.put("DOB", getValueWithNullCheck(data[4]));
				contactmap.put("Anniversary Date", getValueWithNullCheck(data[5]));
				contactmap.put("Designation", getValueWithNullCheck(data[6]));
				contactmap.put("Department", getValueWithNullCheck(data[7]));
				contactmap.put("Gender", getValueWithNullCheck(data[8]));
		    }
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String inststatus()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			statusmap = new LinkedHashMap<String, String>();

			sb.append(" select user_name,date,action_by,action_on,status,comments from "+tabel+"");
			sb.append(" where id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				statusmap.put("Requested By", getValueWithNullCheck(data[0]));
				statusmap.put("Requested On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[1].toString())));
				statusmap.put("Requested To", getValueWithNullCheck(data[2]));
				if(data[3]!=null)
				{
				statusmap.put("Action On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[3].toString())));
				}
				else{
					statusmap.put("Action On", getValueWithNullCheck(data[3]));	
				}
				statusmap.put("Current Status", getValueWithNullCheck(data[4]));
				statusmap.put("Comments", getValueWithNullCheck(data[5]));
		    }
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String indistatus()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			statusmap1 = new LinkedHashMap<String, String>();

			sb.append(" select user_name,date,action_by,action_on,status,comments from referral_individual_data");
			sb.append(" where id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				statusmap1.put("Requested By", getValueWithNullCheck(data[0]));
				statusmap1.put("Requested On", getValueWithNullCheck(data[1]));
				statusmap1.put("Requested To", getValueWithNullCheck(data[2]));
				statusmap1.put("Action On", getValueWithNullCheck(data[3]));
				statusmap1.put("Current Status", getValueWithNullCheck(data[4]));
				statusmap1.put("Comments", getValueWithNullCheck(data[5]));
		    }
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	private String getValueWithNullCheck(Object obj)
	{
		return( obj!=null && !obj.toString().equalsIgnoreCase(""))?obj.toString():"NA";
	}



	public void setMaterViewProp(List<GridDataPropertyView> materViewProp)
	{
		this.materViewProp = materViewProp;
	}



	public List<GridDataPropertyView> getMaterViewProp()
	{
		return materViewProp;
	}


	public void setMasterViewListForContact(List<GridDataPropertyView> masterViewListForContact) {
		this.masterViewListForContact = masterViewListForContact;
	}


	public List<GridDataPropertyView> getMasterViewListForContact() {
		return masterViewListForContact;
	}


	public void setReferralStatus(String referralStatus) {
		this.referralStatus = referralStatus;
	}


	public String getReferralStatus() {
		return referralStatus;
	}

	public String getFromdate() {
		return fromdate;
	}


	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}


	public String getFromdate1() {
		return fromdate1;
	}


	public void setFromdate1(String fromdate1) {
		this.fromdate1 = fromdate1;
	}


	public String getTodate() {
		return todate;
	}


	public void setTodate(String todate) {
		this.todate = todate;
	}


	public String getTodate1() {
		return todate1;
	}


	public void setTodate1(String todate1) {
		this.todate1 = todate1;
	}


	public String getFrom_source() {
		return from_source;
	}


	public void setFrom_source(String from_source) {
		this.from_source = from_source;
	}


	public String getFrom_source1() {
		return from_source1;
	}


	public void setFrom_source1(String from_source1) {
		this.from_source1 = from_source1;
	}


	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}


	public String getSearchParameter() {
		return searchParameter;
	}


	public void setSearchParameter1(String searchParameter1) {
		this.searchParameter1 = searchParameter1;
	}


	public String getSearchParameter1() {
		return searchParameter1;
	}


	public void setOrgmap(Map<String, String> orgmap) {
		this.orgmap = orgmap;
	}


	public Map<String, String> getOrgmap() {
		return orgmap;
	}


	public Map<String, String> getContactmap() {
		return contactmap;
	}


	public void setContactmap(Map<String, String> contactmap) {
		this.contactmap = contactmap;
	}


	public void setStatusmap(Map<String, String> statusmap) {
		this.statusmap = statusmap;
	}


	public Map<String, String> getStatusmap() {
		return statusmap;
	}


	public void setStatusmap1(Map<String, String> statusmap1) {
		this.statusmap1 = statusmap1;
	}


	public Map<String, String> getStatusmap1() {
		return statusmap1;
	}


	public void setIndmap(Map<String, String> indmap) {
		this.indmap = indmap;
	}


	public Map<String, String> getIndmap() {
		return indmap;
	}


	public void setTabel(String tabel) {
		this.tabel = tabel;
	}


	public String getTabel() {
		return tabel;
	}


	public void setData_status(String data_status) {
		this.data_status = data_status;
	}


	public String getData_status() {
		return data_status;
	}

}