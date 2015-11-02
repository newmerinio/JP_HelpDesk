package com.Over2Cloud.ctrl.wfpm.lead;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.subdepartmen.SubDepartmentAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DashActionControl extends ActionSupport implements ServletRequestAware{
	
	
	static final Log log = LogFactory.getLog(SubDepartmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String empIdofuser=(String)session.get("empIdofuser");//o-100000
	private HttpServletRequest request;
	private Map<String, String> leadGenerationTextBox=null;
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
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
	private List<Object> masterViewList;
	private boolean ddTrue;
	private boolean targetTrue;
	private boolean sourceTrue;
	private boolean locationTrue;
	
	private String ddValue;
	private String SourceName;
	private String starRating;
	private String targetAchieved;
	private Map<Integer, String> sourceList=null;
	private Map<Integer, String> locationList=null;
	private Map<Integer, String> offeringList2=null;
	
	private String leadName;
	private String mobileNo;
	private String leadAddress;
	private String phoneNo;
	private String email;
	private String statusName;
	private String offeringLevel;
	
	private String clientName;
	private String employee;
	private String convertTo;
	private String status;
	private String designation;
	private String address;
	private String EmailOfficial;
	private String offering;
	private String productselect;
	private String location;
	private String comment;
	private String idLost;
	private String meetingTime;
	private String formater;
	private String lostLead;
	private String history;
	private String statusselect;
	private String time;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream inputStream;
	private List<Parent> parentTakeaction = null;
	private Map<String, String>leadAActionTextBox=null;
	private Map<Integer,String>targetKpiLiist=null;
	private String clienttypeName;
	
	private int newLead;
	private int snoozeLead;
	private int completedLead;
	private int lstLead;
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String beforeleadView1()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getModifyFlag().equalsIgnoreCase("0"))
				setModifyFlag("false");
			else
				setModifyFlag("true");
			
			if(getDeleteFlag().equalsIgnoreCase("0"))
				setDeleteFlag("false");
			else
				setDeleteFlag("true");
			
			
			
			if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false") && getFormater().equalsIgnoreCase("0") && getLostLead().equalsIgnoreCase("0"))
				setMainHeaderName("Lead >> View");
			else if(getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false") && getFormater().equalsIgnoreCase("0") && getLostLead().equalsIgnoreCase("0"))
				setMainHeaderName("Lead >> Modify");
			else if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true") && getFormater().equalsIgnoreCase("0") && getLostLead().equalsIgnoreCase("0"))
				setMainHeaderName("Lead >> Delete");
			else if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false") && getFormater().equalsIgnoreCase("1")&& getLostLead().equalsIgnoreCase("0"))
				setMainHeaderName("Lead >> Action");
			else if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false") && getFormater().equalsIgnoreCase("0")&& getLostLead().equalsIgnoreCase("1"))
				setMainHeaderName("Lead >> LostLead");
			setLeadViewProp1();
					
					
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeleadView of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	public void setLeadViewProp1()
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_lead_generation",accountID,connectionSpace,"",0,"table_name","lead_generation");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			if(getFormater().equalsIgnoreCase("1")){
			  gpv.setFormatter(gdp.getFormatter());
			}
			else if (getLostLead().equalsIgnoreCase("1"))
			{
				gpv.setFormatter(gdp.getFormatter());
			}	
			 
			masterViewProp.add(gpv);
		}
	}
	
	public String viewLeadGrid1()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from leadgeneration");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
			{
				BigInteger count=new BigInteger("3");
				for(Iterator it=dataCount.iterator(); it.hasNext();)
				{
					 Object obdata=(Object)it.next();
					 count=(BigInteger)obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				//getting the list of colmuns
			    StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_lead_generation", accountID, connectionSpace, "lead_generation");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				boolean source=false;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if(obdata.toString().equalsIgnoreCase("sourceName"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("source.sourceName,");
							    else
							    	query.append("source.sourceName");
							    source=true;
					    	}
					    	else
					    	{
					    		if(i<fieldNames.size()-1)
							    	query.append("a."+obdata.toString()+",");
							    else
							    	query.append("a."+obdata.toString());
					    	}
					    }
					    i++;
					
				}
				
				
				query.append(" from ");
				
				
				if(source)
				{
				//System.out.println("inside the before1 method");
					//if source is taken as field
					if(getLostLead().equalsIgnoreCase("1"))
					{
						query.append("leadgeneration as a inner join sourcemaster as source on a.sourceName=source.id where status='4'  ");
						//.append(" where status='4' ");
					}
					else
					{
						query.append("leadgeneration as a inner join sourcemaster as source on a.sourceName=source.id where status='0'  ");
						//query.append(" where status='0' or status='3' ");
					}
				
				}
				else
				{
					//if source is not taken
					if(getLostLead().equalsIgnoreCase("1"))
					{
						query.append("leadgeneration as a  where status='4'  ");
						//.append(" where status='4' ");
					}
					else
					{
						query.append("leadgeneration as a where status='0'  ");
						//query.append(" where status='0' or status='3' ");
					}
				}
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and ");

					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" a."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" a."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" a."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" a."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" a."+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
                {
                    if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
                    {
                            query.append(" order by a."+getSidx());
                    }
                    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
                    {
                            query.append(" order by a."+getSidx()+" DESC");
                    }
                }
				
				query.append(" limit "+from+","+to);
				
				
				/**
				 * **************************checking for colomon change due to configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames,"leadgeneration",connectionSpace);
				
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						Map<String, Object> one=new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if(obdata[k]!=null)
							{
									if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewLeadGrid of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String deleteLeadGridOper()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
			}
			else if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String tempIds[]=getId().split(",");
				StringBuilder condtIds=new StringBuilder();
					int i=1;
					for(String H:tempIds)
					{
						if(i<tempIds.length)
							condtIds.append(H+" ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("leadgeneration", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteLeadGridOper of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	public String returnLeadAction()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			
			/**
			 * getting mapped target of employee
			 */
			targetKpiLiist=new HashMap<Integer, String>();
			targetKpiLiist=CommonWork.getTargetOfEmployee(userName, connectionSpace);
			
			 List <String>leadActionDialog=new ArrayList<String>();
			 
			 /*
			  * checking wheather the user have not fields which are shown in the list
			  */
			 
			 
			    List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_lead_generation",accountID,connectionSpace,"",0,"table_name","lead_generation");
				if(statusColName!=null)
				{
					leadAActionTextBox=new LinkedHashMap<String, String>();
					//create table query based on configuration
					for(GridDataPropertyView gdp:statusColName)
					{
						 if(gdp.getColomnName().equalsIgnoreCase("leadName") || gdp.getColomnName().equalsIgnoreCase("mobileNo") 
								 ||gdp.getColomnName().equalsIgnoreCase("leadAddress")|| gdp.getColomnName().equalsIgnoreCase("phoneNo")
								 ||gdp.getColomnName().equalsIgnoreCase("email"))
						 {
							 leadActionDialog.add(gdp.getColomnName());
							 leadAActionTextBox.put(gdp.getColomnName(), gdp.getHeadingName());
						 }
					}
				}
			 
				
	 	    Map<String, Object> temp=new HashMap<String, Object>();
	 	    temp.put("id",getId());
	 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	 	    leadActionDialog=cbt.viewAllDataEitherSelectOrAll("leadgeneration",leadActionDialog,temp,connectionSpace);
	 	   if(leadActionDialog!=null)
	 	   {
	 		   for(Object c:leadActionDialog)
	 		   {
	 			     Object[] data=(Object[])c;
	 			     int length=data.length;
	 			     if(length>0 && data[0]!=null)
	 			    	 leadName=data[0].toString();
	 			     if(length>1 && data[3]!=null)
	 			    	mobileNo=data[3].toString();
	 			     if(length>2 && data[1]!=null)
	 				   leadAddress=data[1].toString();
	 			     if(length>3 && data[2]!=null)
	 			    	 phoneNo=data[2].toString();
	 			     if(length>4 && data[4]!=null)
	 			    	 email=data[4].toString();
	 			     if(length>5 && data[5]!=null)
	 			    	 designation=data[5].toString();
	 				   
	 		   }
	 	   }
		
	 	    List<String>colname=new ArrayList<String>();
			colname.add("id");
			colname.add("empName");
			List employeList=cbt.viewAllDataEitherSelectOrAll("employee_basic",colname,connectionSpace);
			if(employeList!=null)
			{
				sourceList=new LinkedHashMap<Integer, String>();
				for (Object c : employeList) {
					Object[] dataC = (Object[]) c;
					//System.out.println("dataC[0]"+dataC[0]);
					sourceList.put((Integer)dataC[0],dataC[1].toString());
				}
				
			}
			
			
			int level = 0;
			//5#Vertical#Offering#Sub-Offering#Variant#Sub-variant#"
			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;
			
			if(offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level=Integer.parseInt(oLevels[0]);
				List<String>colname2=new ArrayList<String>();
				if(level==1)
				{
					
					colname2.add("id");
					colname2.add("verticalname");
					List offeringList=cbt.viewAllDataEitherSelectOrAll("offeringlevel1",colname2,connectionSpace);
					if(offeringList!=null)
					{
						offeringList2=new LinkedHashMap<Integer, String>();
						for (Object c : offeringList) {
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer)dataC[0],dataC[1].toString());
						}
						
					}
					
				}	
				else if(level==2)
				{
	
					colname2.add("id");
					colname2.add("offeringname");
					List offeringList=cbt.viewAllDataEitherSelectOrAll("offeringlevel2",colname2,connectionSpace);
					if(offeringList!=null)
					{
						offeringList2=new LinkedHashMap<Integer, String>();
						for (Object c : offeringList) {
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer)dataC[0],dataC[1].toString());
						}
						
					}
					
				}	
				else if(level==3)
				{
					colname2.add("id");
					colname2.add("subofferingname");
					List offeringList=cbt.viewAllDataEitherSelectOrAll("offeringlevel3",colname2,connectionSpace);
					if(offeringList!=null)
					{
						offeringList2=new LinkedHashMap<Integer, String>();
						for (Object c : offeringList) {
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer)dataC[0],dataC[1].toString());
						}
						
					}
					
					
				}	
				else if(level==4)
				{
					colname2.add("id");
					colname2.add("variantname");
					List offeringList=cbt.viewAllDataEitherSelectOrAll("offeringlevel4",colname2,connectionSpace);
					if(offeringList!=null)
					{
						offeringList2=new LinkedHashMap<Integer, String>();
						for (Object c : offeringList) {
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer)dataC[0],dataC[1].toString());
						}
						
					}
					
				}	
				else if(level==5)
				{ 
					colname2.add("id");
					colname2.add("subvariantsize");
					List offeringList=cbt.viewAllDataEitherSelectOrAll("offeringlevel5",colname2,connectionSpace);
					if(offeringList!=null)
					{
						offeringList2=new LinkedHashMap<Integer, String>();
						for (Object c : offeringList) {
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer)dataC[0],dataC[1].toString());
						}
						
					}
					
				}	
			
				
			}
		}
		catch(Exception e)
		{
			  log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method returnLeadAction of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public String fillType(){
		String returnResult=ERROR;
		try{
			       StringBuilder string=new StringBuilder();
			       List<String> leadActionDialog=new ArrayList<String>();
			       List TempData=null;
				   String sid=req.getParameter("sid");
				   if(sid.equalsIgnoreCase("1"))
				   {
					   
					    leadActionDialog.add("id");
						leadActionDialog.add("statusName");
				 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
				 	   TempData=cbt.viewAllDataEitherSelectOrAll("client_status",leadActionDialog,connectionSpace);
					   
				   }
				   else
				   {
					    leadActionDialog.add("id");
					    leadActionDialog.add("statusName");
				 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
				 	   TempData=cbt.viewAllDataEitherSelectOrAll("associatestatus",leadActionDialog,connectionSpace);
				   }
				   if(TempData!=null)
				   {
						for(Iterator it=TempData.iterator();it.hasNext();)
						{
							Object onj[]=(Object[])it.next();
							string.append(onj[1].toString()+","+(Integer)onj[0]+"#");
						}
						inputStream = new StringBufferInputStream(string.toString());
				   }
			returnResult=SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
	    	addActionError("Oopss there is some problem!!!");
	    	return ERROR;
		}
		
		return returnResult;
	}
	private String targetselect;
	public String updateLeadAction()
	{
		offeringList2=new LinkedHashMap<Integer, String>();
		sourceList=new LinkedHashMap<Integer, String>();
		targetKpiLiist=new HashMap<Integer, String>();
		convertTo=clienttypeName;
		EmailOfficial=email;
		clientName=leadName;
		address=leadAddress;
		offering=productselect;
		meetingTime=time;

		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
        int level = 0;
		offeringLevel = session.get("offeringLevel").toString();
		String[] oLevels = null;
		
		if(offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
		{
			oLevels = offeringLevel.split("#");
			level=Integer.parseInt(oLevels[0]);
		}
		
		try {
			
			/**
			 * Target achieving merging
			 */
			if(getTargetselect()!=null && !getTargetselect().equalsIgnoreCase("-1") && !getTargetselect().equalsIgnoreCase("") && getTargetselect()!=null)
			{
				DSRgerneration ta = new DSRgerneration();
				String tempempIdofuser[]=empIdofuser.split("-");
				ta.setDSRRecords(getTargetselect(), Integer.parseInt(tempempIdofuser[1]), DSRMode.KPI,DSRType.ONLINE);
			}
			
			
			if(!getId().equalsIgnoreCase("") && getId()!=null && clienttypeName.equalsIgnoreCase("1"))
			{
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
		 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
		 	    wherClause.put("status", 1);
		 	    condtnBlock.put("id",getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
				//create table if the table dose not exist and alter table if it dose not same as configuration
                StringBuilder offeringData=new StringBuilder();
				List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_client_configuration",accountID,connectionSpace,"",0,"table_name","client_basic_configuration");
                if(statusColName!=null)
                {
                        //create table query based on configuration
                        List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                        for(GridDataPropertyView gdp:statusColName)
                        {
                                 TableColumes ob1=new TableColumes();
                                 ob1=new TableColumes();
                                 ob1.setColumnname(gdp.getColomnName());
                                 ob1.setDatatype("varchar(255)");
                                 ob1.setConstraint("default NULL");
                                 Tablecolumesaaa.add(ob1);
                        }
                         cbt.createTable22("client_basic_data",Tablecolumesaaa,connectionSpace);
                
                /**
                 * *******************************CLIENT BASIC DETAILS DATA ENTRY FROM LEAD BASIC DATA*****************************
                 */
                
				 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				 InsertDataTable ob=null;
				 if(clientName!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("clientName");
					 ob.setDataName(clientName);
					 insertData.add(ob);
				 }
				 if(phoneNo!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("phoneNo");
					 ob.setDataName(phoneNo);
					 insertData.add(ob);
				 }
				 if(mobileNo!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("mobileNo");
					 ob.setDataName(mobileNo);
					 insertData.add(ob);
				 }
				 if(EmailOfficial!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("companyEmail");
					 ob.setDataName(EmailOfficial);
					 insertData.add(ob);
				 }
				 if(status!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
				 }
				 if(location!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("location");
					 ob.setDataName(location);
					 insertData.add(ob);
				 }
				 if(comment!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("comment");
					 ob.setDataName(comment);
					 insertData.add(ob);
				 }
				 //getting the employee user name with mapped employee user id
				 String tempUSerName=null;
				 ob=new InsertDataTable();
				 ob.setColName("userName");
				 StringBuilder query1=new StringBuilder("");
				 query1.append("select userTable.userID from useraccount userTable inner join employee_basic as eb " +
				 		"on userTable.id=eb.useraccountid where eb.id="+employee);
				 List  emplUser=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				 for(Iterator it=emplUser.iterator();it.hasNext();)
				 {
					 Object temp=(Object)it.next();
					 //System.out.println("size of temp>>>>>>>>"+temp.toString().length());
					 if(temp!=null)
					 {
						 ob.setDataName(Cryptography.decrypt(temp.toString(),DES_ENCRYPTION_KEY));
						 tempUSerName=ob.getDataName().toString();
						 //System.out.println("tempUserName>>>>>>>>>>>>"+tempUSerName);
					 }
					 else
					 {
						 ob.setDataName(userName);
						 tempUSerName=userName;
						 //System.out.println("inside else>>>>>>>>>>>"+tempUSerName);
					 }
				 }
				 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
				 cbt.insertIntoTable("client_basic_data",insertData,connectionSpace);
				 int idMAx=cbt.getMaxId("client_basic_data",connectionSpace);
				
				 /**
				  * *********************CREATE CLIENT OFFERING MAPPING TABLE IF NOT EXIST ELSE INSERT THE DATA IN THE MAPPING TABLE*****
				  */
				 if(offering!=null)
				 {
					 String tempOffering[]=offering.split(",");
					 for(String H:tempOffering)
					 {
						 if(H!=null && !H.equalsIgnoreCase(""))
						 {
							 List <InsertDataTable> insertOffering=new ArrayList<InsertDataTable>();
							 InsertDataTable ob1=null;
							 ob1=new InsertDataTable();
							 ob1.setColName("clientName");
							 ob1.setDataName(idMAx);
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("offeringId");
							 ob1.setDataName(H);
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("offeringLevelId");
							 ob1.setDataName(level);
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("date");
							 ob1.setDataName(DateUtil.getCurrentDateIndianFormat());
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("time");
							 ob1.setDataName(DateUtil.getCurrentTime());
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("isConverted");
							 ob1.setDataName("0");
							 insertOffering.add(ob1);
							 ob1=new InsertDataTable();
							 ob1.setColName("userName");
							 ob1.setDataName(userName);
							 insertOffering.add(ob1);
							 cbt.insertIntoTable("client_offering_mapping",insertOffering,connectionSpace);
							 int offeringID=cbt.getMaxId("client_offering_mapping",connectionSpace);
							 offeringData.append(offeringID+"#");
						 }
					 }
				 } 
				/**
				 * ***********************GETTING CLIENT CONTACT MASTER TABLE DETAILS AND CREATING OR ALTERING THE TABLE STRUCTURE IF NOT MATCH************
				 */
				 
				 
				 
				 statusColName=Configuration.getConfigurationData("mapped_client_configuration",accountID,connectionSpace,"",0,"table_name","client_contact_configuration");
	                if(statusColName!=null)
	                {
	                        //create table query based on configuration
	                        List <TableColumes> Tablecolumesaaa1=new ArrayList<TableColumes>();
	                        for(GridDataPropertyView gdp:statusColName)
	                        {
	                                 TableColumes ob1=new TableColumes();
	                                 ob1=new TableColumes();
	                                 ob1.setColumnname(gdp.getColomnName());
	                                 ob1.setDatatype("varchar(255)");
	                                 ob1.setConstraint("default NULL");
	                                 Tablecolumesaaa1.add(ob1);
	                        }
	                         cbt.createTable22("client_contact_data",Tablecolumesaaa1,connectionSpace);
	               
	                
		                /**
		                 * ****************** INSERT BASIC DETAILS IN CLIENT CONTACT MASTER***************************
		                 */
					 List <InsertDataTable> insertContact=new ArrayList<InsertDataTable>();
					 InsertDataTable ob2=null;
					 ob2=new InsertDataTable();
					 ob2.setColName("clientName");
					 ob2.setDataName(idMAx);
					 insertContact.add(ob2);
					 if(mobileNo!=null)
					 {
						 ob2=new InsertDataTable();
						 ob2.setColName("contactNo");
						 ob2.setDataName(mobileNo);
						 insertContact.add(ob2);
					 }
					 if(EmailOfficial!=null)
					 {
						 ob2=new InsertDataTable();
						 ob2.setColName("emailOfficial");
						 ob2.setDataName(EmailOfficial);
						 insertContact.add(ob2);
					 }
					ob2=new InsertDataTable();
					ob2.setColName("userName");
					ob2.setDataName(userName);
					insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("date");
					 ob2.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("time");
					 ob2.setDataName(DateUtil.getCurrentTime());
					 insertContact.add(ob2);
					 cbt.insertIntoTable("client_contact_data",insertContact,connectionSpace);
					 int contactId=cbt.getMaxId("client_contact_data",connectionSpace);
	              
					/**
					 * *********INSERT DATA IN CLIENT ACTIVITY HISTORY TABLE FOR MAKING ACTIVITY AS PER LEAD ACTION******************
					 */
					 
					 if(status!=null && !status.equalsIgnoreCase("") && !status.equalsIgnoreCase("-1"))
					 {
						 String offer[]=offeringData.toString().split("#");
						 for(String H:offer)
						 {
							 if(H!=null && !H.equalsIgnoreCase(""))
							 {
								 List <InsertDataTable> insertTakeAction=new ArrayList<InsertDataTable>();
								 InsertDataTable ob3=null;
								 ob3=new InsertDataTable();
								 ob3.setColName("contacId");
								 ob3.setDataName(contactId);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("offeringId");
								 ob3.setDataName(offering);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("statusId");
								 ob3.setDataName(status);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("maxDateTime");
								 ob3.setDataName(meetingTime);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("comment");
								 ob3.setDataName(comment);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("userName");
								 ob3.setDataName(userName);
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("date");
								 ob3.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertTakeAction.add(ob3);
								 ob3=new InsertDataTable();
								 ob3.setColName("time");
								 ob3.setDataName(DateUtil.getCurrentTime());
								 insertTakeAction.add(ob3);
								 cbt.insertIntoTable("client_takeaction",insertTakeAction,connectionSpace);
							 }
						 }
					 }
	                }
				 
					 /**
					  * ***********MAKING AN ENTRY IN LEAD HISTORY TABLE WITH PER LEAD ACTION***************
					  */
					 List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
					 InsertDataTable ob4=null;
					 ob4=new InsertDataTable();
					 ob4.setColName("leadId");
					 ob4.setDataName(getId());
					 insertHistory.add(ob4);
					 ob4=new InsertDataTable();
					 ob4.setColName("comment");
					 ob4.setDataName("prospective client");
					 insertHistory.add(ob4);
					 ob4=new InsertDataTable();
					 ob4.setColName("date");
					 ob4.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertHistory.add(ob4);
					 ob4=new InsertDataTable();
					 ob4.setColName("time");
					 ob4.setDataName(DateUtil.getCurrentTime());
					 insertHistory.add(ob4);
					 ob4=new InsertDataTable();
					 ob4.setColName("actionBy");
					 ob4.setDataName(userName);
					 insertHistory.add(ob4);
					 cbt.insertIntoTable("lead_history",insertHistory,connectionSpace);
                }
                else
                {
                	addActionError("Oops there is some problem!");
                }
			}
			else if(!getId().equalsIgnoreCase("") && getId()!=null && convertTo.equalsIgnoreCase("2"))
			{
				//System.out.println("Prospective Associate");
				String tempUSerName=null;
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
		 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
		 	    wherClause.put("status", 2);
		 	    condtnBlock.put("id",getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
				
				/**
				 * *************CREATE TABLE OF ASSOCIATE BASIC DETAILS AS PER CONFIGURATION **********************
				 */
				
				List<GridDataPropertyView> pAssociateColName = Configuration.getConfigurationData("mapped_prospective_associate_info",accountID,connectionSpace,"",0,"table_name","prospective_associate_info");
				
				if(pAssociateColName != null)
				{
					List <TableColumes> tableColumn=new ArrayList<TableColumes>();
					
					for(GridDataPropertyView gdp:pAssociateColName)
					{
						    TableColumes ob1=new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColumn.add(ob1);
					}
					
					cbt.createTable22("prospectiveAssociate",tableColumn,connectionSpace);
				
				/**
				 * *************INSERT DATA IN ASSOCIATE BASIC DETAILS AS PER LEAD BASIC DATA **********************
				 */
				
				 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				 InsertDataTable ob=null;
				 ob=new InsertDataTable();
				 ob.setColName("associateName");
				 ob.setDataName(clientName);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("phoneNo");
				 ob.setDataName(phoneNo);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("contactNo");
				 ob.setDataName(mobileNo);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("associateEmail");
				 ob.setDataName(EmailOfficial);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("location");
				 ob.setDataName(location);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("address");
				 ob.setDataName(address);
				 insertData.add(ob);
				 ob=new InsertDataTable();
				 ob.setColName("userName");
				 StringBuilder query1=new StringBuilder("");
				 query1.append("select userTable.userID from useraccount userTable inner join employee_basic as eb " +
				 		"on userTable.id=eb.useraccountid where eb.id="+employee);
				 List  emplUser=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				 for(Iterator it=emplUser.iterator();it.hasNext();)
				 {
					 Object temp=(Object)it.next();
					 if(temp!=null)
					 {
						 ob.setDataName(Cryptography.decrypt(temp.toString(),DES_ENCRYPTION_KEY));
						 tempUSerName=ob.getDataName().toString();
					 }
					 else
					 {
						 ob.setDataName(userName);
						 tempUSerName=userName;
					 }
				 }
				     insertData.add(ob);
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
				 
				 cbt.insertIntoTable("prospectiveassociate",insertData,connectionSpace);
				 int idMAx=cbt.getMaxId("prospectiveassociate",connectionSpace);
				 
				/**
				 * *****************CREATE TABLE OF ASSOCIATE CONTACT MASTER AS PER CONFIGURATION DONE BYT CLIENT
				 */
				 
				 List<GridDataPropertyView> pAssociateColName1 = Configuration.getConfigurationData("mapped_prospective_associate_info",accountID,connectionSpace,"",0,"table_name","prospective_associate_contactinfo");
				 if(pAssociateColName1 != null)
					{
						//List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						List <TableColumes> tableColumn1=new ArrayList<TableColumes>();
						for(GridDataPropertyView gdp:pAssociateColName1)
						{
							    TableColumes ob1=new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default NULL");
								tableColumn1.add(ob1);
						}
						
						cbt.createTable22("prospectiveAssociateContact",tableColumn1,connectionSpace);
					
					 
					    /**
						 * *****************INSERT DATA IN ASSOCIATE CONTACT MASTER AS PER LEAD BASIC DETAILS FOR ACTIVITY
						 */
					 List <InsertDataTable> insertContact=new ArrayList<InsertDataTable>();
					 InsertDataTable ob2=null;
					 ob2=new InsertDataTable();
					 ob2.setColName("associateName");
					 ob2.setDataName(idMAx);
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("contactNo");
					 ob2.setDataName(mobileNo);
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("emailOfficial");
					 ob2.setDataName(EmailOfficial);
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("designation");
					 ob2.setDataName(designation);
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
				     ob2.setColName("date");
					 ob2.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("time");
					 ob2.setDataName(DateUtil.getCurrentTime());
					 insertContact.add(ob2);
					 ob2=new InsertDataTable();
					 ob2.setColName("userName");
					 ob2.setDataName(userName);
					 insertContact.add(ob2);
					 
					 cbt.insertIntoTable("prospectiveassociatecontact",insertContact,connectionSpace);
					 int contactID=cbt.getMaxId("prospectiveassociatecontact",connectionSpace);
				
					 /**
					  * 
					  * ****************************INSERT DATA IN ASSOCIATE ACTIVITY TABLE**************************
					  */
					 if(status!=null && !status.equalsIgnoreCase("") && !status.equalsIgnoreCase("-1"))
					 {
						 List <InsertDataTable> insertTakeAction=new ArrayList<InsertDataTable>();
						 InsertDataTable ob3=null;
						 ob3=new InsertDataTable();
						 ob3.setColName("associateId");
						 ob3.setDataName(contactID);
						 insertTakeAction.add(ob3);
						 ob3=new InsertDataTable();
						 ob3.setColName("nextAction");
						 ob3.setDataName(status);
						 insertTakeAction.add(ob3);
						 ob3=new InsertDataTable();
						 ob3.setColName("whatAction");
						 ob3.setDataName(comment);
						 insertTakeAction.add(ob3);
						 ob3=new InsertDataTable();
						 ob3.setColName("maxDate");
						 ob3.setDataName(meetingTime);
						 insertTakeAction.add(ob3);
						 ob3=new InsertDataTable();
						 ob3.setColName("madeOn");
						 ob3.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertTakeAction.add(ob3);
						 ob3=new InsertDataTable();
						 ob3.setColName("user");
						 ob3.setDataName(tempUSerName);
						 insertTakeAction.add(ob3);
						 cbt.insertIntoTable("takeaction",insertTakeAction,connectionSpace);
					 }
					}
				 /**
				  * **********ADDING DATA IN LEAD HISTORY TABLE FOR LEAD HISTORY******************
				  */
				 List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
				 InsertDataTable ob4=null;
				 ob4=new InsertDataTable();
				 ob4.setColName("leadId");
				 ob4.setDataName(getId());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("comment");
				 ob4.setDataName("prospective associate");
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("date");
				 ob4.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("time");
				 ob4.setDataName(DateUtil.getCurrentTime());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("actionBy");
				 ob4.setDataName(userName);
				 insertHistory.add(ob4);
				 cbt.insertIntoTable("lead_history",insertHistory,connectionSpace);
				}
			}	
			else if(!getId().equalsIgnoreCase("") && getId()!=null && convertTo.equalsIgnoreCase("3"))
			{
				
				//System.out.println("Lead Awaited");
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
		 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
		 	    wherClause.put("status", 3);
		 	    condtnBlock.put("id",getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
				
				 /**
				  * **********************ADDING LEAD HISTORY DATA IN LEAD HISTORY TABLE*******************************
				  */
				 List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
				 InsertDataTable ob4=null;
				 ob4=new InsertDataTable();
				 ob4.setColName("leadId");
				 ob4.setDataName(getId());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("comment");
				 ob4.setDataName("lead awaited");
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("date");
				 ob4.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("time");
				 ob4.setDataName(DateUtil.getCurrentTime());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("actionBy");
				 ob4.setDataName(userName);
				 insertHistory.add(ob4);
				 cbt.insertIntoTable("lead_history",insertHistory,connectionSpace);
			}	
			else if(!getId().equalsIgnoreCase("") && getId()!=null && convertTo.equalsIgnoreCase("4"))
			{
				
			    //System.out.println("No Response Finished Lead");	
			    Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
		 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
		 	    wherClause.put("status", 4);
		 	    condtnBlock.put("id",getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
				
				/**
				  * **********************ADDING LEAD HISTORY DATA IN LEAD HISTORY TABLE****************************
				  */
				 List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
				 InsertDataTable ob4=null;
				 ob4=new InsertDataTable();
				 ob4.setColName("leadId");
				 ob4.setDataName(getId());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("comment");
				 ob4.setDataName("Lead Lost");
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("date");
				 ob4.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("time");
				 ob4.setDataName(DateUtil.getCurrentTime());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("actionBy");
				 ob4.setDataName(userName);
				 insertHistory.add(ob4);
				 cbt.insertIntoTable("lead_history",insertHistory,connectionSpace);
			}	
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method updateLeadAction of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		
		
		return SUCCESS;
	}
	
	public String  updateLostLead()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		Map<String, Object>wherClause=new HashMap<String, Object>();
		Map<String, Object>condtnBlock=new HashMap<String, Object>();
 	    CommonOperInterface cbt=new CommonConFactory().createInterface();
 	    wherClause.put("status", 0);
 	    condtnBlock.put("id",getIdLost());
		cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method updateLostLead of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	public String leadHistoryAction()
	{
		
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		try{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query1=new StringBuilder("");
		query1.append("select lead.leadName, lh.comment, lh.date, lh.time, lh.actionBy from lead_history as lh, leadgeneration as lead where lh.leadId = lead.id");
		
		 List  data3=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		 parentTakeaction = new ArrayList<Parent>();
         
         if(data3!=null)
         { 
                 int i=0;
                 for(Iterator it=data3.iterator(); it.hasNext();)
                 {
                         Object[] obdata=(Object[])it.next();
                         Parent p1 = new Parent();
                         if(obdata!=null)
                         {
                                 List<Child> child = new ArrayList<Child>();
                                 for(int j=0; j<obdata.length; j++)
                                 {
                                         Child c1 = new Child();
                                         if(obdata[j]!=null)
                                         {
                                                 c1.setName(obdata[j].toString());
                                                 //list.add(obdata[j].toString());
                                                 //System.out.println(obdata[j].toString());
                                         }
                                         else
                                         {
                                                 c1.setName("NA");
                                                 //list.add("NA");
                                         }
                                         //System.out.println("###"+c1.getName());
                                         child.add(c1);
                                 }
                                 p1.setChildList(child);
                         }
                         parentTakeaction.add(p1);
                 }
                 //System.out.println("parentTakeaction:"+parentTakeaction.size());
         }
	
		}catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method leadHistoryAction of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		
		
		return SUCCESS;
}
	
	public String beforeDashboardView()
	{
		newLead=new LeadActionControlDao().dashboardCounter(connectionSpace,"leadgeneration","status","0", userName);
		snoozeLead=new LeadActionControlDao().dashboardCounter(connectionSpace,"leadgeneration","status","3", userName);
		completedLead=new LeadActionControlDao().dashboardCounter1(connectionSpace,"leadgeneration","status", userName);
		lstLead=new LeadActionControlDao().dashboardCounter(connectionSpace,"leadgeneration","status","4", userName);
		//System.out.println("newLead>>>>>>>"+newLead);
		//System.out.println("snoozeLead>>>>>>>"+snoozeLead);
		//System.out.println("completedLead>>>>>>>"+completedLead);
		//System.out.println("lstLead>>>>>>>"+lstLead);
		return SUCCESS;
	}
	
	
	
	
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	public Map<String, String> getLeadGenerationTextBox() {
		return leadGenerationTextBox;
	}
	public void setLeadGenerationTextBox(Map<String, String> leadGenerationTextBox) {
		this.leadGenerationTextBox = leadGenerationTextBox;
	}

	public boolean isTargetTrue() {
		return targetTrue;
	}
	public void setTargetTrue(boolean targetTrue) {
		this.targetTrue = targetTrue;
	}

	public boolean isSourceTrue() {
		return sourceTrue;
	}

	public void setSourceTrue(boolean sourceTrue) {
		this.sourceTrue = sourceTrue;
	}



	public String getSourceName() {
		return SourceName;
	}

	public void setSourceName(String sourceName) {
		SourceName = sourceName;
	}


	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	public String getTargetAchieved() {
		return targetAchieved;
	}
	public void setTargetAchieved(String targetAchieved) {
		this.targetAchieved = targetAchieved;
	}

	public Map<Integer, String> getSourceList() {
		return sourceList;
	}

	public void setSourceList(Map<Integer, String> sourceList) {
		this.sourceList = sourceList;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
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

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public boolean isDdTrue() {
		return ddTrue;
	}

	public void setDdTrue(boolean ddTrue) {
		this.ddTrue = ddTrue;
	}

	public String getDdValue() {
		return ddValue;
	}

	public void setDdValue(String ddValue) {
		this.ddValue = ddValue;
	}
	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getLeadAddress() {
		return leadAddress;
	}
	public void setLeadAddress(String leadAddress) {
		this.leadAddress = leadAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOfferingLevel() {
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel) {
		this.offeringLevel = offeringLevel;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Map<Integer, String> getOfferingList2() {
		return offeringList2;
	}
	public void setOfferingList2(Map<Integer, String> offeringList2) {
		this.offeringList2 = offeringList2;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getConvertTo() {
		return convertTo;
	}
	public void setConvertTo(String convertTo) {
		this.convertTo = convertTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmailOfficial() {
		return EmailOfficial;
	}

	public void setEmailOfficial(String emailOfficial) {
		EmailOfficial = emailOfficial;
	}

	public String getOffering() {
		return offering;
	}

	public void setOffering(String offering) {
		this.offering = offering;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getIdLost() {
		return idLost;
	}

	public void setIdLost(String idLost) {
		this.idLost = idLost;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getFormater() {
		return formater;
	}
	public void setFormater(String formater) {
		this.formater = formater;
	}
	public String getLostLead() {
		return lostLead;
	}

	public void setLostLead(String lostLead) {
		this.lostLead = lostLead;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public List<Parent> getParentTakeaction() {
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction) {
		this.parentTakeaction = parentTakeaction;
	}

	public Map<String, String> getLeadAActionTextBox() {
		return leadAActionTextBox;
	}

	public void setLeadAActionTextBox(Map<String, String> leadAActionTextBox) {
		this.leadAActionTextBox = leadAActionTextBox;
	}

	public Map<Integer, String> getTargetKpiLiist() {
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist) {
		this.targetKpiLiist = targetKpiLiist;
	}

	public String getTargetselect() {
		return targetselect;
	}

	public void setTargetselect(String targetselect) {
		this.targetselect = targetselect;
	}

	public boolean isLocationTrue() {
		return locationTrue;
	}

	public void setLocationTrue(boolean locationTrue) {
		this.locationTrue = locationTrue;
	}

	public Map<Integer, String> getLocationList() {
		return locationList;
	}

	public void setLocationList(Map<Integer, String> locationList) {
		this.locationList = locationList;
	}

	public String getClienttypeName() {
		return clienttypeName;
	}

	public void setClienttypeName(String clienttypeName) {
		this.clienttypeName = clienttypeName;
	}

	public String getProductselect() {
		return productselect;
	}

	public void setProductselect(String productselect) {
		this.productselect = productselect;
	}

	public String getStatusselect() {
		return statusselect;
	}

	public void setStatusselect(String statusselect) {
		this.statusselect = statusselect;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNewLead() {
		return newLead;
	}

	public void setNewLead(int newLead) {
		this.newLead = newLead;
	}

	public int getSnoozeLead() {
		return snoozeLead;
	}

	public void setSnoozeLead(int snoozeLead) {
		this.snoozeLead = snoozeLead;
	}

	public int getCompletedLead() {
		return completedLead;
	}

	public void setCompletedLead(int completedLead) {
		this.completedLead = completedLead;
	}

	public int getLstLead() {
		return lstLead;
	}

	public void setLstLead(int lstLead) {
		this.lstLead = lstLead;
	}
	
	
}
