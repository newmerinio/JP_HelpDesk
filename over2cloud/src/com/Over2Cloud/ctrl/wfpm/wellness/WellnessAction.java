package com.Over2Cloud.ctrl.wfpm.wellness;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WellnessAction extends ActionSupport implements ServletRequestAware{
	/**
	 * Patient Wellness Action and add view
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");

	private String empId = session.get("empIdofuser").toString().split("-")[1];
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private String offeringLevel = session.get("offeringLevel").toString();
	String cId = new CommonHelper().getEmpDetailsByUserName(
			CommonHelper.moduleName, userName, connectionSpace)[0];
	static final Log log = LogFactory.getLog(WellnessAction.class);
	
	private List<Object> masterViewList;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private String from_date;
	private String to_date;
	private String uh_id;
	private String mobile;
	private String patient_name;
	
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
	// Grid colomn view
	private String oper,tat="0",pat="0",nat="0",totseek="0",searchParameter;
	private String id;
	private String count,status;
	private String datebeforeday;
	
	public String beforewellnessreport()
	{
		setDatebeforeday(DateUtil.getDateAfterDays(-7));
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "select distinct ffa.question_status,COUNT(*) from patient_basic_data as pbd left join feedback_form_answers as ffa on ffa.empId = pbd.id ";
		if (from_date != null && !from_date.equals("") && to_date != null && !to_date.equals(""))
		{
			query=query+" where pbd.date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"'";
		}
		query=query+" group by ffa.empId ";
		System.out.println(query);
		List data = cbt.executeAllSelectQuery(query, connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] == null)
				{
				 if(object[1]!=null)
				 {
					 nat=object[1].toString();
				 }
			   }
				else if(object[0] != null && object[0].toString().equalsIgnoreCase("0") && !object[0].toString().equals("-1") && !object[0].toString().equalsIgnoreCase("undefined") && object[1] != null && !object[1].toString().equals("-1") && !object[1].toString().equalsIgnoreCase("undefined"))
				{
					nat=Integer.toString(Integer.parseInt(nat)+Integer.parseInt(object[1].toString()));
				}
				else if(object[0] != null && object[0].toString().equalsIgnoreCase("1") && !object[0].toString().equals("-1") && !object[0].toString().equalsIgnoreCase("undefined") && object[1] != null && !object[1].toString().equals("-1") && !object[1].toString().equalsIgnoreCase("undefined"))
				{
					pat=Integer.toString((Integer.parseInt(pat)+Integer.parseInt(object[0].toString())));
				}
				else if(object[0] != null && object[0].toString().equalsIgnoreCase("2") && !object[0].toString().equals("-1") && !object[0].toString().equalsIgnoreCase("undefined") && object[1] != null && !object[1].toString().equals("-1") && !object[1].toString().equalsIgnoreCase("undefined"))
				{
					tat=Integer.toString((Integer.parseInt(tat)+Integer.parseInt("1")));
				}
			}
			totseek=Integer.toString(Integer.parseInt(nat)+Integer.parseInt(pat)+Integer.parseInt(tat));
		}
		
		viewWellnessReportProperties();
		return SUCCESS;
	}
	
	public void viewWellnessReportProperties()
	{
		try {
			masterViewProp = new ArrayList<GridDataPropertyView>();
			
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
		    
		    gpv=new GridDataPropertyView();
			gpv.setColomnName("patient_name");
			gpv.setHeadingName("Patient Name");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(250);
			masterViewProp.add(gpv);
		    
		    gpv=new GridDataPropertyView();
			gpv.setColomnName("uh_id");
			gpv.setHeadingName("UHID");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			masterViewProp.add(gpv);
		    
		    gpv=new GridDataPropertyView();
			gpv.setColomnName("mobile");
			gpv.setHeadingName("Mobile No.");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			masterViewProp.add(gpv);
		    
		    gpv=new GridDataPropertyView();
			gpv.setColomnName("email");
			gpv.setHeadingName("Email");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(200);
			masterViewProp.add(gpv);
		    
		    gpv=new GridDataPropertyView();
			gpv.setColomnName("seek_on");
			gpv.setHeadingName("Seek On");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("revert_on");
			gpv.setHeadingName("Revert On");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("question_status");
			gpv.setHeadingName("Status");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			gpv.setFormatter("viewQuestionnaire");
			masterViewProp.add(gpv);
			
		/*	gpv=new GridDataPropertyView();
			gpv.setColomnName("questionnaire");
			gpv.setHeadingName("Questionnaire");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			gpv.setFormatter("viewQuestionnaire");
			masterViewProp.add(gpv);*/
			//session.put("fieldNames",masterViewProp );
			System.out.println(" masterViewProp "+masterViewProp.size()); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String viewWellnessReportDataGrid()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT COUNT(*) FROM patient_basic_data");
				List  dataCount1=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				query.setLength(0);
				if(dataCount1.size()>0)
				{
					List fieldNames = Configuration.getColomnList("mapped_patient_basic_configuration", accountID,connectionSpace, "patient_basic_configuration");
					BigInteger count=new BigInteger("1");
					Object obdata=null;
					for(Iterator it=dataCount1.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
				
				query.append(" select distinct pbd.id, concat(pbd.first_name,' ', pbd.last_name) as patient_name, pbd.patien_id, pbd.mobile, pbd.email, pbd.sent_questionair, ffa.question_status ");
				query.append(" ,pbd.date as seek_on,ffa.date as revert_on, pbd.uh_id from patient_basic_data as pbd left join feedback_form_answers as ffa on ffa.empId = pbd.id");
				query.append(" where pbd.id <> '0' and concat(pbd.first_name,' ', pbd.last_name) !='NA' and concat(pbd.first_name,' ', pbd.last_name) is not null ");
				if (patient_name != null && !patient_name.equals("") && !patient_name.equals("undefined"))
				{
				query.append("and concat(pbd.first_name,' ', pbd.last_name) ='");
				query.append(patient_name);
				query.append("' ");
				}
				if (mobile != null && !mobile.equals("") && !mobile.equals("undefined"))
				{
				query.append("and pbd.mobile ='");
				query.append(mobile);
				query.append("' ");
				}
				if (uh_id != null && !uh_id.equals("") && !uh_id.equals("undefined"))
				{
				query.append("and pbd.uh_id ='");
				query.append(uh_id);
				query.append("' ");
				}
				if (status != null && !status.equals("") && !status.equals("undefined") && !status.equals("-1"))
				{
					if(status.equalsIgnoreCase("0"))
					{
						query.append("and ffa.question_status ='");
						query.append(status);
						query.append("' ||  ffa.question_status is null ");
					}
					else{
						query.append("and ffa.question_status ='");
						query.append(status);
						query.append("' ");
				     }
				}
				
				if (from_date != null && !from_date.equals("") && to_date != null && !to_date.equals(""))
				{
				query.append(" and pbd.date between '");
				query.append(DateUtil.convertDateToUSFormat(from_date));
				query.append("' and '");
				query.append(DateUtil.convertDateToUSFormat(to_date));
				query.append("'");
				}
				
				
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and ");
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" tr."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" tr."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" tr."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"'");
					}
				}
				
				if(searchParameter != null && !searchParameter.equalsIgnoreCase(""))
				{      
				query.append(" and ");
				    if (fieldNames != null && !fieldNames.isEmpty()) 
				       {  
				    	  int k=0;
				      for (Iterator iterator = fieldNames.iterator(); iterator
				                                                  .hasNext();) 
				           {  
				                 String object = iterator.next().toString();
				 	         if (object != null)
				 	          {
				 	        	//System.out.println("EEE>>>>"+object.toString());
				if(object.toString().equalsIgnoreCase("patient_name"))
				{
				 	        	query.append(" concat(pbd.first_name,' ', pbd.last_name) LIKE "+"'%"+searchParameter+"%"+"'");
				}
				else 
				{
				query.append(" pbd."+object.toString()+" LIKE "+"'%"+searchParameter+"%"+"'");
				}
				 	          }
				 	        if(k < fieldNames.size()-1)
				 	         {
				 	        	/*if (!object.toString().equalsIgnoreCase("clientName"))
				 	        	{ */
				 	        	query.append(" OR ");
				 	            /*}*/
				 	         }	
				 	        	else 
				 	        	query.append(" ");
				                 k++;
				           }
				    }
				}
					query.append(" ORDER BY ffa.date Desc ");
				
				System.out.println("query.toString() "+query.toString()); 
				List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				
				if(dataCount!=null && dataCount.size()>0)
				{
					masterViewList=new ArrayList<Object>();
					List<Object> Listhb=new ArrayList<Object>();
					Object[] obdata1=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata1=(Object[])it.next();
						 Map<String, Object> one=new HashMap<String, Object>();
						 if(obdata1[0] != null)
							{
							 one.put("id", (Integer)obdata1[0]);
							}
						 
						 if(obdata1[1] != null)
							{
							 one.put("patient_name",obdata1[1].toString());
							}
						 else 
							{
								one.put("patient_name","NA");
							}
						 if(obdata1[2] != null)
							{
							 one.put("patien_id",obdata1[2].toString());
							}
						 else 
							{
								one.put("patien_id","NA");
							}
						 if(obdata1[3] != null)
							{
							 one.put("mobile",obdata1[3].toString());
							}
						 else 
							{
								one.put("mobile","NA");
							}
						 if(obdata1[4] != null)
							{
							 one.put("email",obdata1[4].toString());
							}
						 else 
							{
								one.put("email","NA");
							}
						 if(obdata1[5] != null)
							{
							 one.put("sent_questionair",obdata1[5].toString());
							}
						 else 
							{
								one.put("sent_questionair","NA");
							}
						 
						if(obdata1[6] != null)
						{
							if(obdata1[6].toString().equalsIgnoreCase("2"))
							{
								one.put("question_status","Total Attempted");
								
							}else if(obdata1[6].toString().equalsIgnoreCase("1"))
							{
								one.put("question_status","Partial Attempted");
							}
							else
							{
								one.put("question_status","Not Attempted");
							}
							
						}
						else 
						{
							one.put("question_status","Not Attempted");
						}
						if(obdata1[7] != null)
						{
							one.put("seek_on",DateUtil.convertDateToIndianFormat(obdata1[7].toString()));
						}else 
						{
							one.put("seek_on","NA");
						}
						if(obdata1[8] != null)
						{
							one.put("revert_on",DateUtil.convertDateToIndianFormat(obdata1[8].toString()));
						}else 
						{
							one.put("revert_on","NA");
						}
						if(obdata1[9] != null)
						{
							one.put("uh_id",obdata1[9].toString());
						}else 
						{
							one.put("uh_id","NA");
						}
						 
						 Listhb.add(one);
					}
					System.out.println("MasterViewList "+masterViewList.size()); 
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				 }
			   }
				return SUCCESS;
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
	}
	
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setUh_id(String uh_id) {
		this.uh_id = uh_id;
	}

	public String getUh_id() {
		return uh_id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setTat(String tat) {
		this.tat = tat;
	}

	public String getTat() {
		return tat;
	}

	public void setPat(String pat) {
		this.pat = pat;
	}

	public String getPat() {
		return pat;
	}

	public void setNat(String nat) {
		this.nat = nat;
	}

	public String getNat() {
		return nat;
	}

	public void setDatebeforeday(String datebeforeday) {
		this.datebeforeday = datebeforeday;
	}

	public String getDatebeforeday() {
		return datebeforeday;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setTotseek(String totseek) {
		this.totseek = totseek;
	}

	public String getTotseek() {
		return totseek;
	}

	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	public String getSearchParameter() {
		return searchParameter;
	}

}