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

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.subdepartmen.SubDepartmentAction;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper2;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.offering.OfferingHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;

public class LeadActionControl extends SessionProviderClass implements ServletRequestAware
{

	static final Log											log										= LogFactory.getLog(SubDepartmentAction.class);
	// -
	// 100000
	// String cId = new
	// CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName,
	// connectionSpace)[0];
	private HttpServletRequest						request;
	private List<ConfigurationUtilBean>		leadBasicControls			= null;
	private String												mainHeaderName;
	private String												addFlag;
	private String												userselect;
	private String												fromDashboard;
	private String												historyLead;
    private String                                              weightage_targetsegment;
	/*
	 * private boolean starRatingMandatory; private boolean sourceTrueMandatory;
	 */

	private List<GridDataPropertyView>		masterViewProp				= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>		masterViewPropForLead	= new ArrayList<GridDataPropertyView>();
	private Integer												rows									= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer												page									= 0;
	// sorting order - asc or desc
	private String												sord									= "";
	// get index row - i.e. user click to sort.
	private String												sidx									= "";
	// Search Field
	private String												searchField						= "";
	// The Search String
	private String												searchString					= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String												searchOper						= "";
	// Your Total Pages
	private Integer												total									= 0;
	// All Record
	private Integer												records								= 0;
	private boolean												loadonce							= false;
	// Grid colomn view
	private String												oper;
	private String												id;
	private List<Object>									masterViewList;
	private List<Object>									masterViewListForLeadContact;
	private String												ddTrue;
	private String												targetTrue;
	private String												sourceTrue;
	private boolean												locationTrue;

	private String												ddValue;
	private String												starRating;
	private String												targetAchieved;
	private Map<String, String>						sourceList						= null;
	private Map<String, String>						locationList					= null;
	private Map<Integer, String>					offeringList2					= null;
	private List<ConfigurationUtilBean>		subDept_DeptLevelName	= null;

	private String												leadName;
	private String												mobileNo;
	private String												leadAddress;
	private String												phoneNo;
	private String												email;
	private String												statusName;
	private String												offeringLevel;

	private String												clientName;
	private String												associateName;
	private String												employee;
	private String												convertTo;
	private String												status								= "0";
	private String												designation;
	private String												date_and_time;
	private String												address;
	private String												EmailOfficial;
	private String												offering;
	private String												productselect;
	private String												location;
	private String												comment;
	private String												idLost;
	private String												meetingTime;
	private String												history;
	private String												statusselect;
	private String												time;
	public static final String						DES_ENCRYPTION_KEY		= "ankitsin";
	HttpServletRequest										req										= (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse										response							= (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream										inputStream;
	private List<Parent>									parentTakeaction			= null;
	private Map<String, String>						leadAActionTextBox		= null;
	private Map<Integer, String>					targetKpiLiist				= null;
	private String												clienttypeName;

	private int														newLead;
	private int														snoozeLead;
	/* private int completedLead; */
	private int														lstLead;
	public int														convertedToCliet;
	public int														convertedToAssociate;
	private String												sendEmail;
	private String												sendSms;
	private String												showAction;
	private int														leadBasicDetails;
	private int														leadContacDetails;
	private String												leadNameValue;

	private List<ConfigurationUtilBean>		leadContactControls		= null;
	private Map<String, String>						nameMap								= null;
	private JSONArray											jsonArray							= null;
	private Map<String, String>						industryList;
	private Map<String, String>						subIndustryList;
	private Map<String, String>						leadBasicFullViewMap;
	private Map<String, String>						leadContactFullViewMap;
	private Map<String, String>                     weightageMasterMap;
	private int														contactStartingNum;
	private String												industry;
	private String												subIndustry;
	private String												sourceName;
	private ArrayList<ArrayList<String>>	leadBasicForAction;
	private ArrayList<ArrayList<String>>	leadContactForAction;
	private LeadActionType								leadActionType;
	private String												faxNo;
	private String												webAddress;
	private String												name;
	private String												personName;
	private String												department;
	private String												contactNo;
	private String												anniversary;
	private String												emailOfficialContact;
	private String												degreeOfInfluence;
	private String												communicationName;
	private String												birthday;
	private String industryExit;
	private String sourceExit;
	private String locationExit;
	private String starRatingExit;
	private String refNameExit;
	private String refName;
	private String referedByExit;
	private String referedBy;
	private String subindustryExit;

	
	private String timestatuslead;
	private String scheduletime;
	private String offeringId;
	private String lead_name_wild;
	private int count1;
	private int counter;
	private int count3;
	private LinkedHashMap<String, String>	deptMap;
	CommonHelper													ch										= new CommonHelper();

	public String execute()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method execute of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeleadAdd()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			setLeadAddPageDataFields();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method beforeleadAdd of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 6-5-2014
	 */
	public String beforeLeadEdit()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			LeadHelper2 lh = new LeadHelper2();
			ClientHelper3 ch3 = new ClientHelper3();
			masterViewProp = lh.fetchLeadDataForEdition(id);

			// Source
			sourceList = ch3.fetchAllSource();

			// Location
			locationList = ch3.fetchAllLocation();

			// Industry
			industryList = ch3.fetchAllIndustry();

			// subIndustry
			subIndustryList = ch3.fetchSubIndustryByIndustryId(LeadHelper2.industryId);

			leadContactEditList = lh.fetchLeadContactDataForEdition(id);
			// Department

			deptlist = new LinkedHashMap<String, String>();
			deptlist = ch3.fetchAllDepartment();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 6-5-2014
	 */
	public String editLead()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
				{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					if (paramValue.equalsIgnoreCase("-1"))
					{
						paramValue = "NA";
					}
					ob.setDataName(paramValue);
					insertData.add(ob);
				}
			}
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(cId);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			CommonOperInterface coi = new CommonConFactory().createInterface();
			status = coi.updateIntoTable("leadgeneration", insertData, id, connectionSpace);
			if (status)
			{
				addActionMessage("Lead Edited Successfully.");
			}
			else
			{
				addActionError("Oops There is some error in data!");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Sanjiv 12-05-2014. For editing Lead contacts.
	 */
	public String editLeadContact()
	{
		String idval = null;
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> leadColName = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
					"lead_contact_configuration");

			if (leadColName != null)
			{
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				boolean status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();

				for (GridDataPropertyView gdp : leadColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("lead_contact_data", tableColumn, connectionSpace);

				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				int paramValueSize = 0;
				boolean statusTemp = true;
				String associateName = null;
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && Parmname != null)
					{
						paramList.add(Parmname);
						/*
						 * if (Parmname.equalsIgnoreCase("clientName")) { associateName =
						 * paramValue; continue; }
						 */
						// adding the parameters list.
						if (statusTemp)
						{
							String tempParamValueSize[] = request.getParameterValues(Parmname);
							for (String H : tempParamValueSize)
							{
								// counting one time size of the parameter value
								if (H != null) paramValueSize++;
							}
							statusTemp = false;
						}
					}
				}
				String parmValuew[][] = new String[paramList.size()][paramValueSize];
				int m = 0;
				for (Object c : paramList)
				{
					Object Parmname = (Object) c;
					String paramValue[] = request.getParameterValues(Parmname.toString());

					for (int j = 0; j < paramValue.length; j++)
					{

						if (!paramValue[j].equalsIgnoreCase("") && paramValue[j] != null && !paramValue[j].equalsIgnoreCase("-1"))
						{
							// //System.out.println("paramValue[j]" + paramValue[j]);
							parmValuew[m][j] = paramValue[j];
						}
					}
					m++;
				}
				boolean valueSelect = false;
				for (int i = 0; i < paramValueSize; i++)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (int k = 0; k < paramList.size(); k++)
					{

						if (parmValuew[k][i] != null)
						{
							//System.out.println("parmValuew[k][i]>>" + parmValuew[k][i]);
							valueSelect = true;

						}
						else
						{
							valueSelect = false;
						}

						if (valueSelect)
						{
							if (!(paramList.get(k).toString().equalsIgnoreCase("leadName")))
							{
								ob = new InsertDataTable();
								//System.out.println("Column name " + paramList.get(k).toString());
								if (paramList.get(k).toString().equals("id"))
								{
									idval = parmValuew[k][i];
								}
								//System.out.println("Data name " + parmValuew[k][i]);

								if (paramList.get(k).toString().equalsIgnoreCase("birthday") || paramList.get(k).toString().equalsIgnoreCase("anniversary"))
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(DateUtil.convertDateToUSFormat(parmValuew[k][i]));
								}
								else
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(parmValuew[k][i]);
								}
								insertData.add(ob);
							}
						}
					}

					if (valueSelect)
					{
						/*
						 * ob = new InsertDataTable(); ob.setColName("clientName"); //
						 * //System.out.println("associateName  " + associateName);
						 * ob.setDataName(associateName); insertData.add(ob);
						 */

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						status = cbt.updateIntoTable("lead_contact_data", insertData, idval, connectionSpace);
					}
				}

				if (status)
				{
					addActionMessage("Lead contact edited successfully.");
				}
				else
				{
					addActionMessage("Oops There is some error in data.");
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: editLeadContact of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setLeadAddPageDataFields()
	{
		// mapped_lead_generation
		// lead_contact_configuration

		// Create accordion for lead details, begins
		CommonOperInterface coi = new CommonConFactory().createInterface();
		StringBuilder empuery = new StringBuilder("");
		empuery.append("select table_name from mapped_lead_generation");
		List empData = coi.executeAllSelectQuery(empuery.toString(), connectionSpace);
		for (Iterator it = empData.iterator(); it.hasNext();)
		{
			Object obdata = (Object) it.next();
			if (obdata != null && !obdata.toString().equals(""))
			{
				if (obdata.toString().equalsIgnoreCase("lead_generation"))
				{
					leadBasicDetails = 1;
				}
				else if (obdata.toString().equalsIgnoreCase("lead_contact_configuration"))
				{
					leadContacDetails = 1;
				}
			}
		}

		if (leadBasicDetails == 1)
		{
			leadBasicControls = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
					"lead_generation");
			if (offeringLevel1 != null)
			{
				for (GridDataPropertyView gdp : offeringLevel1)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time"))
					{
					            //condition for only selected fields for add 	
						if(gdp.getColType().equalsIgnoreCase("T"))
						{
					      if(gdp.getColomnName().equalsIgnoreCase("leadName")
					    	   || gdp.getColomnName().equalsIgnoreCase("leadAddress")
					    	   || gdp.getColomnName().equalsIgnoreCase("phoneNo")
					    	   || gdp.getColomnName().equalsIgnoreCase("webAddress"))
					    	
					       {
						      obj.setValue(gdp.getHeadingName());
						      obj.setKey(gdp.getColomnName());
						      obj.setValidationType(gdp.getValidationType());
						      obj.setColType(gdp.getColType());
						      if (gdp.getMandatroy().toString().equals("1"))
						         {
							      obj.setMandatory(true);
						         }
						      else
						         {
							      obj.setMandatory(false);
						         }

						         leadBasicControls.add(obj);
					       }
						
						
					}	
						
						System.out.println("colomn name========="+gdp.getColomnName());
				    if (gdp.getColType().equalsIgnoreCase("D"))
						{
				    	      if(gdp.getColomnName().equalsIgnoreCase("industry"))
				    	        {
				    	    	  industry=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
								        industryExit="true";
				    	    	  else
				    	    	        industryExit="false";	
				    	        }
				    	      else if(gdp.getColomnName().equalsIgnoreCase("subIndustry"))
				    	      {
				    	    	  subIndustry=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
								        subindustryExit="true";
				    	    	  else
				    	    	        subindustryExit="false";	
				    	      }
				    	      else  if(gdp.getColomnName().equalsIgnoreCase("sourceName"))
				    	      {
				    	    	  sourceName=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
								        sourceExit="true";
				    	    	  else
				    	    	        sourceExit="false";	
				    	      }
				    	      else  if(gdp.getColomnName().equalsIgnoreCase("referedBy"))
				    	      {   
				    	    	  referedBy=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
				    	    		  referedByExit="true";
				    	    	  else
				    	    		  referedByExit="false";	
				    	      }else  if(gdp.getColomnName().equalsIgnoreCase("refName"))
				    	      {
				    	    	  refName=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
				    	    	  {  refNameExit="true";   System.out.println("in true"); }
				    	    	  else
				    	    	  {  refNameExit="false";	System.out.println("in false");  }
				    	      }
				    	      else  if(gdp.getColomnName().equalsIgnoreCase("starRating"))
				    	      {
				    	    	  starRating=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
				    	    		  starRatingExit="true";
				    	    	  else
				    	    		  starRatingExit="false";	
				    	      }else  if(gdp.getColomnName().equalsIgnoreCase("name"))
				    	      {
				    	    	  name=gdp.getHeadingName();
				    	    	  if(gdp.getMandatroy().equalsIgnoreCase("1"))
				    	    		  locationExit="true";
				    	    	  else
				    	    		  locationExit="false";	
				    	      }
				    			
				    	      
				    	 }
					}
					
					
				}
				 if (industryExit != null)
				    {
					 industryList = new LeadHelper().fetchIndustryList(connectionSpace);
				    }
		        if (sourceExit != null)
			        {   
				
				    sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
			        }

	           if (locationExit != null)
			       {
				   locationList = new LeadHelper().fetchLocationList(connectionSpace);
			       }
		  }

			
			// ****** Lead Contact ******************************************
			if (leadContacDetails == 1)
			{
				List<GridDataPropertyView> configData = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
						"lead_contact_configuration");
				if (configData != null)
				{
					leadContactControls = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : configData)
					{
						if (gdp.getColomnName().equalsIgnoreCase("leadName"))
						{
							leadNameValue = gdp.getHeadingName();
							continue;
						}
						if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{   
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
                            
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
						  // System.out.println("column name===*************"+gdp.getColomnName());
							// ////System.out.println("obj.getColType()>>" + obj.getColType());
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							leadContactControls.add(obj);
							
						}
						// Department
						if (gdp.getColomnName().equalsIgnoreCase("department"))
						{
							CommonHelper ch = new CommonHelper();
							deptMap = new LinkedHashMap<String, String>();
							List tempList = coi.executeAllSelectQuery("select id,deptName from department order by deptName", connectionSpace);
							if (tempList != null && tempList.size() > 0)
							{
								for (Object dept : tempList)
								{
									Object[] dataI = (Object[]) dept;
									deptMap.put(dataI[0].toString(), ch.getFieldValue(dataI[1]));
								}
							}
						}
						
						
						
						

						if (gdp.getColType().equalsIgnoreCase("D"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
							{
								sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
							}

							if (gdp.getColomnName().equalsIgnoreCase("location"))
							{
								locationList = new LeadHelper().fetchLocationList(connectionSpace);
							}
						}
						
					}
				}

			}
		}
		// ////System.out.println("leadContactControls.size():" +
		// leadContactControls.size());
	}

	public String leadGenerationAdd()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// create table for lead
			LeadActionControlDao lacd = new LeadActionControlDao();
			lacd.createLeadGenerationTable(accountID, connectionSpace);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
				{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					if (paramValue.equalsIgnoreCase("-1"))
					{
						paramValue = "NA";
					}
					ob.setDataName(paramValue);
					insertData.add(ob);
				}
			}
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(cId);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			// store lead data to table 'leadgeneration'
			status = cbt.insertIntoTable("leadgeneration", insertData, connectionSpace);
			/*
			 * if (!getTargetAchieved().equalsIgnoreCase("-1") &&
			 * !getTargetAchieved().equalsIgnoreCase("") && getTargetAchieved() !=
			 * null) { DSRgerneration ta = new DSRgerneration(); String
			 * tempempIdofuser[] = empIdofuser.split("-");
			 * ta.setDSRRecords(getTargetAchieved(),
			 * Integer.parseInt(tempempIdofuser[1]), DSRMode.KPI, DSRType.ONLINE); }
			 */
			if (status)
			{
				int leadId = cbt.getMaxId("leadgeneration", connectionSpace);
				lacd.createKPIAutofillTable(accountID, connectionSpace);
				lacd.insertInToKPIAutofillTable(empIdofuser, "1", userName, connectionSpace, String.valueOf(leadId), "Lead");
				addActionMessage("Lead Registered Successfully!!!");
				return SUCCESS;
			}
			else
			{
				addActionError("Oops There is some error in data!");
				return ERROR;
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method leadGenerationAdd of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
	}

	public String beforeleadView()
	{
		
		try
        {
            if (!ValidateSession.checkSession()) return LOGIN;
            // lead
            setLeadViewProp();
            industryList = new LeadHelper().fetchIndustryList(connectionSpace);
            sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
            locationList = new LeadHelper().fetchLocationList(connectionSpace);
            // counter for 1st call
            CommonOperInterface cbt = new CommonConFactory().createInterface();
            StringBuilder query11 = new StringBuilder("");
            query11.append("select count(*) from leadgeneration where status='0'");
            count1=countRecord(query11.toString());
            setCount1(count1);
            
        }
        catch (Exception e)
        {
            log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
                    + "Problem in Over2Cloud  method View of class " + getClass(), e);
            e.printStackTrace();
            addActionError("Oops There is some error in data!");
            return ERROR;
        }
        return SUCCESS;
		/*try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			// lead
			setLeadViewProp();
			industryList = new LeadHelper().fetchIndustryList(connectionSpace);
			sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
			locationList = new LeadHelper().fetchLocationList(connectionSpace);
			// lead contact
			// for multiple contacts
			// setLeadViewPropForContact();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method View of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;*/
        
	}
	 // for counter in change lead type
    public String counter(){
        {
            try
            {
                if (!ValidateSession.checkSession()) return LOGIN;
                
                CommonOperInterface cbt = new CommonConFactory().createInterface();
                StringBuilder query11 = new StringBuilder("");
                query11.append("select count(*) from leadgeneration ");
                if(getCounter()!=-1){
                    if(getCounter()==0){
                        query11.append("where status='0'");
                    }
                    if(getCounter()==1){
                        query11.append("where status='1'");
                    }
                    if(getCounter()==2){
                        query11.append("where status='2'");
                    }
                    if(getCounter()==3){
                        query11.append("where status='3'");
                    } 
                    if(getCounter()==4){
                        query11.append("where status='4'");
                    }
                    if(getCounter()==5){
                        query11.append("where status='5'");
                    }
                }
                count3=countRecord(query11.toString());
                setCount3(count3);
            }
            catch (Exception e)
            {
                log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
                        + "Problem in Over2Cloud  method View of class " + getClass(), e);
                e.printStackTrace();
                addActionError("Oops There is some error in data!");
                return ERROR;
            }
            return SUCCESS;
    }
    }
     // common function for count
        public Integer countRecord(String query)
        {       BigInteger totalRecord=new BigInteger("3");
            List listData=null;
            CommonOperInterface cbt = new CommonConFactory().createInterface();
            listData=cbt.executeAllSelectQuery(query,connectionSpace);
                  if(listData!=null)
                  {
                      for(Iterator iterator=listData.iterator(); iterator.hasNext();)
                      {
                    
                          totalRecord=(BigInteger) iterator.next();
                      }
                  }
            
                return totalRecord.intValue();
        }
	public void setLeadViewProp()
	{
		// Lead view page
		         
		      new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, connectionSpace, "lead_contact_configuration", masterViewProp, true);
		if (masterViewProp != null && masterViewProp.size() > 0)
		{   	
		      new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, connectionSpace, "lead_generation", masterViewProp, false);

			// list data which has to be shown in lead view
			List<String> listDataToShow = new LeadHelper().getLeadBasicToShow();
			// Lead contact data which has to be shown
			List<String> listContactDataToShow = new LeadHelper().getLeadContactToShow();
			// Merg list
			listDataToShow.addAll(listContactDataToShow);

			// Remove extra fields from list 'masterViewProp' which are not needed on
			// view page of lead
			for (Iterator iterator = masterViewProp.iterator(); iterator.hasNext();)
			{
				if (!listDataToShow.contains(((GridDataPropertyView) iterator.next()).getColomnName()))
				{
					iterator.remove();
				}
			}

			// //////////////
			/*
			 * for (Iterator iterator = masterViewProp.iterator();
			 * iterator.hasNext();) { //System.out.println(((GridDataPropertyView)
			 * iterator.next()).getColomnName()); }
			 */

		}
	}

	public void setLeadViewPropForContact()
	{
		// Fetch configuration for contact view
		new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, connectionSpace, "lead_contact_configuration", masterViewPropForLead, true);
	}

	public String viewLeadGrid()
	{
		
		// changes by manab
		 try
	        {
	            if (!ValidateSession.checkSession()) return LOGIN;

	            masterViewList = new ArrayList<Object>();
	            // Edit lead data
	            // *******************************************************************
	            if (getOper() != null && getOper().equalsIgnoreCase("edit"))
	            {
	                CommonOperInterface cbt = new CommonConFactory().createInterface();
	                Map<String, Object> wherClause = new HashMap<String, Object>();
	                Map<String, Object> condtnBlock = new HashMap<String, Object>();
	                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	                Collections.sort(requestParameterNames);
	                Iterator it = requestParameterNames.iterator();
	                while (it.hasNext())
	                {
	                    String Parmname = it.next().toString();

	                    String paramValue = request.getParameter(Parmname);
	                    // lead name and star rating is editable only
	                    if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("")
	                            && Parmname.equalsIgnoreCase("leadName")) wherClause.put(Parmname, paramValue);
	                }
	                condtnBlock.put("id", getId());
	                cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock, connectionSpace);

	            }
	            // Delete lead data
	            // *********************************************************
	            else if (getOper() != null && getOper().equalsIgnoreCase("del"))
	            {
	                CommonOperInterface cbt = new CommonConFactory().createInterface();
	                String tempIds[] = getId().split(",");
	                StringBuilder condtIds = new StringBuilder();
	                int i = 1;
	                for (String H : tempIds)
	                {
	                    if (i < tempIds.length) condtIds.append(H + " ,");
	                    else condtIds.append(H);
	                    i++;
	                }
	                cbt.deleteAllRecordForId("leadgeneration", "id", condtIds.toString(), connectionSpace);
	            }

	            // Lead data to view
	            // ******************************************************
	            CommonOperInterface cbt = new CommonConFactory().createInterface();
	            StringBuilder query1 = new StringBuilder("");

	            CommonHelper ch = new CommonHelper();
	            String cIdAll = ch.getHierarchyContactIdByEmpId(empIdofuser);

	            if (getStatus() != null)
	            {
	                query1.append("select count(*) from leadgeneration where userName IN(" + cIdAll + ") and status ='" + getStatus() + "'");
	            }
	            else
	            {
	                query1.append("select count(*) from leadgeneration where userName IN(" + cIdAll + ") and status = '0'");
	            }

	            List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
	                if (to > getRecords()) to = getRecords();

	                // getting the list of colmuns

	                List<String> listDataToShow = new LeadHelper().getLeadBasicToShow();
	                List<String> listContactDataToShow = new LeadHelper().getLeadContactToShow();
	                if (listDataToShow != null && listDataToShow.size() > 0 && listContactDataToShow != null && listContactDataToShow.size() > 0)
	                {
	                    StringBuilder query = new StringBuilder("");
	                    StringBuilder query2 = new StringBuilder("");

	                    query.append("select ld.id, ");
	                    for (String data : listDataToShow)
	                    {
	                        if (data.equalsIgnoreCase("sourceName"))
	                        {
	                            query.append("sm.");
	                            query.append(data);
	                            query.append(", ");
	                        }
	                        else if (data.equalsIgnoreCase("industry"))
	                        {
	                            query.append("ind.");
	                            query.append(data);
	                            query.append(", ");
	                        }
	                        else if (data.equalsIgnoreCase("subIndustry"))
	                        {
	                            query.append("sind.");
	                            query.append(data);
	                            query.append(", ");
	                        }
	                        else if (data.equalsIgnoreCase("name"))
	                        {
	                            query.append("loc.");
	                            query.append(data);
	                            query.append(", ");
	                        }
	                        else
	                        {
	                            query.append("ld.");
	                            query.append(data);
	                            query.append(", ");
	                        }
	                    }

	                    for (String data : listContactDataToShow)
	                    {
	                        query.append("lc.");
	                        query.append(data);
	                        query.append(", ");
	                    }

	                    query2.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
	                    query2.append(" ");
	                    System.out.println("query2:" + query2);
	                    
	                    query2.append("from leadgeneration as ld left join lead_contact_data as lc on lc.leadName = ld.id ");
	                    if (listDataToShow.contains("sourceName")) query2.append("left join sourcemaster as sm on sm.id = ld.sourceName ");
	                    if (listDataToShow.contains("industry")) query2.append("left join industrydatalevel1 as ind on ind.id = ld.industry ");
	                    if (listDataToShow.contains("subIndustry")) query2.append("left join subindustrydatalevel2 as sind on sind.id = ld.subIndustry ");
	                    if (listDataToShow.contains("name")) query2.append("left join location as loc on loc.id = ld.name ");
	                    query2.append("where ld.status = '");
	                    System.out.println("status>>>"+status);
	                    //System.out.println("status>>>"+query2);
	                    query2.append(status);
	                    query2.append("' and ld.userName IN(");
	                    query2.append(cIdAll);
	                    query2.append(") ");
	                    query2.append("");
	                    System.out.println("status>>>"+query2);
	                    if (industry != null && !industry.equals("-1"))
	                    {
	                        System.out.println("indrustry");
	                        query2.append("and ld.industry = '");
	                        query2.append(industry);
	                        query2.append("' ");
	                    }
	                    if (subIndustry != null && !subIndustry.equals("-1"))
	                    {
	                        System.out.println("subindrustry");
	                        query2.append("and ld.subIndustry = '");
	                        query2.append(subIndustry);
	                        query2.append("' ");
	                    }
	                    if (starRating != null && !starRating.equals("-1"))
	                    {
	                        System.out.println("rating");
	                        query2.append("and ld.starRating = '");
	                        query2.append(starRating);
	                        query2.append("' ");
	                    }
	                    if (sourceName != null && !sourceName.equals("-1"))
	                    {
	                        System.out.println("source name");
	                        query2.append("and ld.sourceName = '");
	                        query2.append(sourceName);
	                        query2.append("' ");
	                    }
	                    if (location != null && !location.equals("-1"))
	                    {
	                        query2.append("and ld.name = '");
	                        query2.append(location);
	                        query2.append("' ");
	                        System.out.println("---jjjj--"+query2);
	                    }
	                    if (lead_name_wild != null && !lead_name_wild.equals(""))
	                	{
	                	query2.append("and ld.leadName like '");
	                	query2.append(lead_name_wild);
	                	query2.append("%' ");
	                	
	                	}

	                    String app = "ld.";
	                    if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")
	                            && getSearchOper() != null && !getSearchOper().equals(""))
	                    {
	                        if (listContactDataToShow.contains(getSearchField())) app = "lc.";
	                        else if (getSearchString().equalsIgnoreCase("sourceName")) app = "sm.";

	                        // add search query based on the search operation
	                        query2.append(" and ");
	                        if (getSearchOper().equalsIgnoreCase("eq"))
	                        {
	                            query2.append(app + getSearchField() + " = '" + getSearchString() + "'");
	                        }
	                        else if (getSearchOper().equalsIgnoreCase("cn"))
	                        {
	                            query2.append(app + getSearchField() + " like '%" + getSearchString() + "%'");
	                        }
	                        else if (getSearchOper().equalsIgnoreCase("bw"))
	                        {
	                            query2.append(app + getSearchField() + " like '" + getSearchString() + "%'");
	                        }
	                        else if (getSearchOper().equalsIgnoreCase("ne"))
	                        {
	                            query2.append(app + getSearchField() + " <> '" + getSearchString() + "'");
	                        }
	                        else if (getSearchOper().equalsIgnoreCase("ew"))
	                        {
	                            query2.append(app + getSearchField() + " like '%" + getSearchString() + "'");
	                        }
	                    }

	                    if (getSord() != null && !getSord().equalsIgnoreCase(""))
	                    {
	                        if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
	                        {
	                            query2.append(" order by " + app + getSidx());
	                        }
	                        if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
	                        {
	                            query2.append(" order by " + app + getSidx() + " DESC");
	                        }
	                    }
	                    /*if (lead_name_wild != null && !lead_name_wild.equals(""))
	                    {
	                    query2.append("and ld.leadName like '");
	                    query2.append(lead_name_wild);
	                    query2.append("%' ");
	                    System.out.println("--query2--"+query2);
	                    System.out.println(" >>>>> "+query2.toString()+"  "+getLead_name_wild());
	                    }*/
	                    query2.append(" ORDER BY ld.leadName ");
	                    query2.append(" limit " + from + "," + getRows());
	                    List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);

	                    //********************************************************************
	                    if (query2 == null || query2.equals("")) return SUCCESS;

	                    List data = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);

	                    if (data != null)
	                    {
	                        listDataToShow.addAll(listContactDataToShow);
	                        listDataToShow.add(0, "id");

	                        for (Iterator it = data.iterator(); it.hasNext();)
	                        {
	                            Object[] obdata = (Object[]) it.next();
	                            Map<String, Object> one = new HashMap<String, Object>();
	                            for (int k = 0; k < listDataToShow.size(); k++)
	                            {
	                                if (obdata[k] != null)
	                                {
	                                    if (k == 0) one.put(listDataToShow.get(k).toString(), (Integer) obdata[k]);
	                                    else
	                                    {
	                                        if (listDataToShow.get(k).toString().equalsIgnoreCase("date"))
	                                        {
	                                            one.put(listDataToShow.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	                                        }
	                                        else one.put(listDataToShow.get(k).toString(), obdata[k].toString());
	                                    }
	                                }
	                                else
	                                {
	                                	one.put(listDataToShow.get(k).toString(), "Not Mapped");
	                                }
	                            }
	                            masterViewList.add(one);
	                        }
	                        setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	                    }
	                }
	            }
	        }
	        catch (Exception e)
	        {
	            log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
	                    + "Problem in Over2Cloud  method viewLeadGrid of class " + getClass(), e);
	            e.printStackTrace();
	            addActionError("Oops There is some error in data!");
	            return ERROR;
	        }

	        return SUCCESS;
		
		
	        
	}

	public String viewLeadContact()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			if (getOper() != null && getOper().equalsIgnoreCase("edit"))
			{
				// No need to do now
			}
			else if (getOper() != null && getOper().equalsIgnoreCase("del"))
			{
				// No need to do now
			}

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			if (getStatus() != null)
			{
				query1.append("select count(*) from lead_contact_data where userName ='");
				query1.append(cId);
				query1.append("' and leadName ='");
				query1.append(id);
				query1.append("' ");
			}
			else
			{
				query1.append("select count(*) from lead_contact_data where userName ='");
				query1.append(cId);
				query1.append("' and leadName ='");
				query1.append(id);
				query1.append("' ");
			}

			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				query.append("select ");

				List fieldNames = Configuration.getColomnList("mapped_lead_generation", accountID, connectionSpace, "lead_contact_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();

					if (obdata != null)
					{
						if (!obdata.toString().equalsIgnoreCase("leadName"))
						{
							queryTemp.append(obdata.toString());
							queryTemp.append(", ");
						}
					}
				}

				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" from lead_contact_data where leadName='");
				query.append(id);
				query.append("' ");

				// //System.out.println("query:::::::::::::" + query);
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and ");

					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" a." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" a." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" a." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" a." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" a." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by a." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by a." + getSidx() + " DESC");
					}
				}

				// //System.out.println("getRows():" + getRows());
				query.append(" limit " + from + "," + getRows());
				// //System.out.println("LEAD VIEW:" + fieldNames);

				// No need this field in view
				fieldNames.remove("leadName");

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("userName") || fieldNames.get(k).toString().equalsIgnoreCase("date")
										|| fieldNames.get(k).toString().equalsIgnoreCase("time")) continue;

								if (k == 0) one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								else one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						Listhb.add(one);
					}
					// Collections.reverse(Listhb);
					setMasterViewListForLeadContact(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method viewLeadGrid of class " + getClass(), e);
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
			if (!ValidateSession.checkSession()) return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("leadgeneration", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method deleteLeadGridOper of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;
	}

	public String returnLeadAction()
	{
		// //System.out.println("returnLeadAction>>>>>>>>>");
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			// Basic data
			StringBuilder subIndId = new StringBuilder();
			leadBasicForAction = new LeadHelper().fetchLeadBasicForTakeAction(connectionSpace, accountID, id, subIndId);
			// //System.out.println("leadBasicForAction.size():" +
			// leadBasicForAction.size());
			leadContactForAction = new LeadHelper().fetchLeadContactForTakeAction(connectionSpace, accountID, id, subIndId);
			// //System.out.println("leadContactForAction.size():" +
			// leadContactForAction.size());
			industryList = new LeadHelper().fetchIndustryList(connectionSpace);
			weightageMasterMap = new LinkedHashMap<String, String>();
			weightageMasterMap = new LeadHelper().fetchAllweightageTargetSegment(connectionSpace);
			//System.out.println("size of weightage map************"+weightageMasterMap.size());
			
			
			
			// //System.out.println("industryList.size():" + industryList.size());
			jsonArray = new LeadHelper().fetchSubIndustry(connectionSpace, subIndId.toString(), LeadData.All);
			// //System.out.println("jsonArray.size():" + jsonArray.size());

			/**
			 * getting mapped target of employee
			 */
			/*
			 * targetKpiLiist = new HashMap<Integer, String>(); targetKpiLiist =
			 * CommonWork.getTargetOfEmployee(userName, connectionSpace);
			 */

			List<String> leadActionDialog = new ArrayList<String>();

			/*
			 * checking wheather the user have not fields which are shown in the list
			 */

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
					"lead_generation");
			if (statusColName != null)
			{
				leadAActionTextBox = new LinkedHashMap<String, String>();
				// create table query based on configuration
				for (GridDataPropertyView gdp : statusColName)
				{
					if (gdp.getColomnName().equalsIgnoreCase("leadName") || gdp.getColomnName().equalsIgnoreCase("mobileNo")
							|| gdp.getColomnName().equalsIgnoreCase("leadAddress") || gdp.getColomnName().equalsIgnoreCase("phoneNo")
							|| gdp.getColomnName().equalsIgnoreCase("email") || gdp.getColomnName().equalsIgnoreCase("date_and_time"))
					{
						leadActionDialog.add(gdp.getColomnName());
						leadAActionTextBox.put(gdp.getColomnName(), gdp.getHeadingName());
						// //System.out.println("leadAActionTextBox " + leadAActionTextBox);
					}
				}
			}

			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("id", getId());
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			leadActionDialog = cbt.viewAllDataEitherSelectOrAll("leadgeneration", leadActionDialog, temp, connectionSpace);
			if (leadActionDialog != null)
			{
				// //System.out.println("leadActionDialog  " + leadActionDialog);
				for (Object c : leadActionDialog)
				{
					Object[] data = (Object[]) c;
					int length = data.length;
					// //System.out.println("length   " + length);
					if (length > 0 && data[0] != null) leadName = data[0].toString();
					if (length > 1 && data[3] != null) mobileNo = data[3].toString();
					if (length > 2 && data[1] != null) leadAddress = data[1].toString();
					if (length > 3 && data[2] != null) phoneNo = data[2].toString();
					if (length > 4 && data[4] != null) email = data[4].toString();
					if (length > 5 && data[5] != null) date_and_time = data[5].toString();

				}
			}

			/*
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("empName");
			List employeList = cbt.viewAllDataEitherSelectOrAll("employee_basic", colname, connectionSpace);
			sourceList = new LinkedHashMap<String, String>();
			if (employeList != null)
			{
				for (Object c : employeList)
				{
					Object[] dataC = (Object[]) c;
					sourceList.put(dataC[0].toString(), dataC[1].toString());
				}
			}
			 */
			
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			sourceList = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);

			int level = 0;
			// 5#Vertical#Offering#Sub-Offering#Variant#Sub-variant#"
			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
				List<String> colname2 = new ArrayList<String>();

				if (level == 1)
				{

					colname2.add("id");
					colname2.add("verticalname");
					List offeringList = cbt.viewAllDataEitherSelectOrAll("offeringlevel1", colname2, connectionSpace);
					offeringList2 = new LinkedHashMap<Integer, String>();
					if (offeringList != null)
					{
						for (Object c : offeringList)
						{
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer) dataC[0], dataC[1].toString());
						}

					}

				}
				else if (level == 2)
				{

					colname2.add("id");
					colname2.add("offeringname");
					List offeringList = cbt.viewAllDataEitherSelectOrAll("offeringlevel2", colname2, connectionSpace);
					offeringList2 = new LinkedHashMap<Integer, String>();
					if (offeringList != null)
					{
						for (Object c : offeringList)
						{
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer) dataC[0], dataC[1].toString());
						}

					}

				}
				else if (level == 3)
				{
					colname2.add("id");
					colname2.add("subofferingname");
					List offeringList = cbt.viewAllDataEitherSelectOrAll("offeringlevel3", colname2, connectionSpace);
					offeringList2 = new LinkedHashMap<Integer, String>();
					if (offeringList != null)
					{
						for (Object c : offeringList)
						{
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer) dataC[0], dataC[1].toString());
						}

					}

				}
				else if (level == 4)
				{
					colname2.add("id");
					colname2.add("variantname");
					List offeringList = cbt.viewAllDataEitherSelectOrAll("offeringlevel4", colname2, connectionSpace);
					offeringList2 = new LinkedHashMap<Integer, String>();
					if (offeringList != null)
					{
						for (Object c : offeringList)
						{
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer) dataC[0], dataC[1].toString());
						}

					}

				}
				else if (level == 5)
				{
					colname2.add("id");
					colname2.add("subvariantname");

					List offeringList = cbt.viewAllDataEitherSelectOrAll("offeringlevel5", colname2, connectionSpace);
					offeringList2 = new LinkedHashMap<Integer, String>();

					if (offeringList != null)
					{
						for (Object c : offeringList)
						{
							Object[] dataC = (Object[]) c;
							offeringList2.put((Integer) dataC[0], dataC[1].toString());

						}

					}

				}

			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method returnLeadAction of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}

		return SUCCESS;
	}

	public String fillType()
	{
		String returnResult = ERROR;
		try
		{
			StringBuilder string = new StringBuilder();
			List<String> leadActionDialog = new ArrayList<String>();
			List TempData = null;
			String sid = req.getParameter("sid");
			if (sid.equalsIgnoreCase("1"))
			{

				leadActionDialog.add("id");
				leadActionDialog.add("statusName");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				TempData = cbt.viewAllDataEitherSelectOrAll("client_status", leadActionDialog, connectionSpace);

			}
			else if (sid.equalsIgnoreCase("2"))
			{
				leadActionDialog.add("id");
				leadActionDialog.add("statusName");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				TempData = cbt.viewAllDataEitherSelectOrAll("associatestatus", leadActionDialog, connectionSpace);
			}
			else if (sid.equalsIgnoreCase("4"))
			{
				leadActionDialog.add("id");
				leadActionDialog.add("lostReason");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				TempData = cbt.viewAllDataEitherSelectOrAll("lostoportunity", leadActionDialog, connectionSpace);
				// //System.out.println("size of the TempData " + TempData);
			}
			if (TempData != null)
			{
				for (Iterator it = TempData.iterator(); it.hasNext();)
				{
					Object onj[] = (Object[]) it.next();
					string.append(onj[1].toString() + "," + (Integer) onj[0] + "#");
				}
				inputStream = new StringBufferInputStream(string.toString());
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}

		return returnResult;
	}

	private String																			targetselect;
	private ArrayList<ArrayList<GridDataPropertyView>>	leadContactEditList;
	private Map<String, String>													deptlist;

	@SuppressWarnings("unchecked")
  public String updateLeadAction()
	   {
		       if (!ValidateSession.checkSession()) return LOGIN;

		           offeringList2 = new LinkedHashMap<Integer, String>();
		           sourceList = new LinkedHashMap<String, String>();
		           targetKpiLiist = new HashMap<Integer, String>();
		           convertTo = clienttypeName;
		           EmailOfficial = email;
		           clientName = leadName;
		           associateName = leadName;
		           address = leadAddress;
		           offering = productselect;
		           meetingTime = time;

	  int level = new OfferingHelper().getOfferingLevel(session);
		  try
		    {
			if (!getId().equalsIgnoreCase("")
					             && getId() != null
					             && (leadActionType == LeadActionType.PROSPECTIVE_CLIENT || leadActionType == LeadActionType.PROSPECTIVE_ASSOCIATE
							                           || leadActionType == LeadActionType.SNOOZE || leadActionType == LeadActionType.LOST))
			{
			 CommonOperInterface cbt = new CommonConFactory().createInterface();
				 // update lead flag
			 new LeadHelper2().updateLeadStatus(connectionSpace, leadActionType, id);
				 LeadActionControlDao lacd = new LeadActionControlDao();
				 lacd.createKPIAutofillTable(accountID, connectionSpace);
				 lacd.insertInToKPIAutofillTable(empIdofuser, "2", userName, connectionSpace, id, "Lead");

			 if (time != null && !time.equalsIgnoreCase(""))
				{
					// time -> dd-mm-yyyy hh:mm
					String[] dateVal = time.split(" ");
					time = DateUtil.convertDateToUSFormat(dateVal[0].trim()) + " " + dateVal[1];
				}

				/*******************************************************************************
				 * Prospective client
				 *******************************************************************************/
				if (leadActionType == LeadActionType.PROSPECTIVE_CLIENT)
				{
					// Send email
					if (sendEmail.equalsIgnoreCase("true") && email != null && !email.equals(""))
					{
						new LeadHelper2().sendMailOnLeadAction(email, leadName, LeadActionType.PROSPECTIVE_CLIENT);
					}

					// Send SMS
					if (sendSms.equalsIgnoreCase("true") && mobileNo != null && !mobileNo.equals(""))
					{
						new LeadHelper2().sendSMSOnLeadAction(mobileNo, leadName, LeadActionType.PROSPECTIVE_CLIENT);
					}

					// create table if the table dose not exist and alter table if
					new ClientHelper().createTableClientBasicData(connectionSpace, accountID);

					// Fetch lead data and put them variables for insert record into client basic data
					Map<String, String> leadDataMap = new LeadHelper().fetchLeadBasicColumnAndValueMap(connectionSpace, accountID, id);
					String entryKey, entryValue;
					for (Map.Entry<String, String> entry : leadDataMap.entrySet())
					{
						entryKey = entry.getKey();
						entryValue = entry.getValue();
						if (entryKey.equalsIgnoreCase("starRating"))
						{
							starRating = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("faxNo"))
						{
							faxNo = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("mobileNo"))
						{
							mobileNo = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("email"))
						{
							email = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("comment"))
						{
							comment = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("sourceName"))
						{
							sourceName = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("name"))
						{
							name = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
					}

					/****************************************************************
					 * CLIENT BASIC DETAILS DATA ENTRY FROM LEAD BASIC DATA
					 **************************************************************/

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					String currentUser = userName;

					if (leadName != null)
					{
						ob = new InsertDataTable();
						ob.setColName("clientName");
						ob.setDataName(leadName);
						insertData.add(ob);
					}
					if (leadAddress != null)
					{
						ob = new InsertDataTable();
						ob.setColName("address");
						ob.setDataName(leadAddress);
						insertData.add(ob);
					}
					if (starRating != null)
					{
						ob = new InsertDataTable();
						ob.setColName("starRating");
						ob.setDataName(starRating);
						insertData.add(ob);
					}
					if (phoneNo != null)
					{
						ob = new InsertDataTable();
						ob.setColName("phoneNo");
						ob.setDataName(phoneNo);
						insertData.add(ob);
					}
					if (faxNo != null)
					{
						ob = new InsertDataTable();
						ob.setColName("faxNo");
						ob.setDataName(faxNo);
						insertData.add(ob);
					}
					if (webAddress != null)
					{
						ob = new InsertDataTable();
						ob.setColName("webAddress");
						ob.setDataName(webAddress);
						insertData.add(ob);
					}
					if (mobileNo != null)
					{
						ob = new InsertDataTable();
						ob.setColName("mobileNo");
						ob.setDataName(mobileNo);
						insertData.add(ob);
					}
					if (email != null)
					{
						ob = new InsertDataTable();
						ob.setColName("companyEmail");
						ob.setDataName(email);
						insertData.add(ob);
					}
					if (comment != null)
					{
						ob = new InsertDataTable();
						ob.setColName("comment");
						ob.setDataName(comment);
						insertData.add(ob);
					}
					if (sourceName != null)
					{
						ob = new InsertDataTable();
						ob.setColName("sourceMaster");
						ob.setDataName(sourceName);
						insertData.add(ob);
					}
					if (industry != null)
					{
						ob = new InsertDataTable();
						ob.setColName("industry");
						ob.setDataName(industry);
						insertData.add(ob);
					}
					if (subIndustry != null)
					{
						ob = new InsertDataTable();
						ob.setColName("subIndustry");
						ob.setDataName(subIndustry);
						insertData.add(ob);
					}
					if (name != null)
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(name);
						insertData.add(ob);
					}
					if (weightage_targetsegment != null && !weightage_targetsegment.equalsIgnoreCase("-1"))
					{
						ob = new InsertDataTable();
						ob.setColName("weightage_targetsegment");
						ob.setDataName(weightage_targetsegment);
						insertData.add(ob);
						//System.out.println("value of weightage in********"+weightage_targetsegment);
					}
					   // System.out.println("value of weightage out********"+weightage_targetsegment);
					

					// getting the employee user name with mapped employee user id
					//change by azad 5sep				
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(cId);
					insertData.add(ob);
					    
					LeadHelper2 lh2 = new LeadHelper2();
					currentUser = lh2.getContactIdOfEmpByEmpId(userselect);
					ob = new InsertDataTable();
                    ob.setColName("acManager");
                  
					if (userselect != null)
					{  System.out.println("acc mgr 1  "+userselect);
						ob.setDataName(userselect);
					}
					else
					{  System.out.println("acc mgr2   "+userselect);
						currentUser = cId;
						ob.setDataName(currentUser);
					}

					insertData.add(ob);
					
					                      
                      
	  //set the current status and with date(Active inactive)
					/*ob = new InsertDataTable();
					ob.setColName("currentStatus");
					ob.setDataName("1");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("currentStatusDate");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					*/
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
					cbt.insertIntoTable("client_basic_data", insertData, connectionSpace);
					
					
					int idMAx = cbt.getMaxId("client_basic_data", connectionSpace);
					
					lacd.insertInToKPIAutofillTable(empIdofuser, "3", userName, connectionSpace, String.valueOf(idMAx), "Prospective Client");
					lacd.insertInToKPIAutofillTable(empIdofuser, "6", userName, connectionSpace, String.valueOf(idMAx), "Prospective Client");
					
					List statusName = lacd.statusName("statusName", "client_status", statusselect, connectionSpace);
					
					if (statusName != null && statusName.size() > 0)
					{
						if (statusName.get(0).toString().equalsIgnoreCase("Intro Meeting"))
						{
							lacd.insertInToKPIAutofillTable(empIdofuser, "5", userName, connectionSpace, String.valueOf(idMAx), "Prospective Client");
						}
					}
					
					// CREATE CLIENT OFFERING MAPPING TABLE
					new ClientHelper().createTableClientOfferingMapping(connectionSpace);

					
					// SAVE DATA IN THE MAPPING TABLE
					if (offering != null)
					{
						String mappedOfferingCount = new LeadHelper2().mapClientWithOffering(connectionSpace, offering, idMAx, currentUser, level);
					}

					// Create table 'client_contact_data'
					new ClientHelper().createTableClientContactData(connectionSpace, accountID);

					int contactId = -1;
					// if (contactNo != null && !contactNo.equals(""))
					if (true)
					{
						// INSERT BASIC DETAILS IN CLIENT CONTACT MASTER
						// //System.out.println("userSelect " + userselect);
						List<InsertDataTable> insertContact = new ArrayList<InsertDataTable>();
						InsertDataTable ob2 = null;

						ob2 = new InsertDataTable();
						ob2.setColName("clientName");
						ob2.setDataName(idMAx);
						insertContact.add(ob2);

						if (personName != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("personName");
							ob2.setDataName(personName);
							insertContact.add(ob2);
						}
						if (department != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("department");
							ob2.setDataName(new LeadHelper().fetchDeptIdForName(department,connectionSpace));
							insertContact.add(ob2);
						}
						if (contactNo != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("contactNo");
							ob2.setDataName(contactNo);
							insertContact.add(ob2);
						}
						if (anniversary != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("anniversary");
							ob2.setDataName(anniversary);
							insertContact.add(ob2);
						}
						if (emailOfficialContact != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("emailOfficial");
							ob2.setDataName(emailOfficialContact);
							insertContact.add(ob2);
						}
						if (degreeOfInfluence != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("degreeOfInfluence");
							ob2.setDataName(degreeOfInfluence);
							insertContact.add(ob2);
						}
						if (communicationName != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("communicationName");
							ob2.setDataName(communicationName);
							insertContact.add(ob2);
						}
						if (birthday != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("birthday");
							ob2.setDataName(birthday);
							insertContact.add(ob2);
						}

						if (designation != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("designation");
							ob2.setDataName(designation);
							insertContact.add(ob2);
						}

						ob2 = new InsertDataTable();
						ob2.setColName("userName");
						ob2.setDataName(currentUser);
						insertContact.add(ob2);

						ob2 = new InsertDataTable();
						ob2.setColName("date");
						ob2.setDataName(DateUtil.getCurrentDateUSFormat());
						insertContact.add(ob2);

						ob2 = new InsertDataTable();
						ob2.setColName("time");
						ob2.setDataName(DateUtil.getCurrentTime());
						insertContact.add(ob2);

						cbt.insertIntoTable("client_contact_data", insertContact, connectionSpace);
						contactId = cbt.getMaxId("client_contact_data", connectionSpace);
					}
					// INSERT DATA IN CLIENT ACTIVITY HISTORY TABLE FOR MAKING ACTIVITY AS
					// PER LEAD ACTION
					if (statusselect != null && !statusselect.equalsIgnoreCase("") && !statusselect.equalsIgnoreCase("-1"))
					{
						ClientHelper ch = new ClientHelper();
						ch.createTableClientTakeAction(connectionSpace);

						if (offering != null)
						{
							String offer[] = offering.split(",");
							for (String H : offer)
							{
								if (H != null && !H.equalsIgnoreCase(""))
								{
									List<InsertDataTable> insertTakeAction = new ArrayList<InsertDataTable>();
									InsertDataTable ob3 = null;

									ob3 = new InsertDataTable();
									ob3.setColName("contacId");
									ob3.setDataName(contactId);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("offeringId");
									ob3.setDataName(H);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("statusId");
									ob3.setDataName(statusselect);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("maxDateTime");
									ob3.setDataName(time);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("comment");
									ob3.setDataName(comment);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("isFinished");
									ob3.setDataName("0");
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("userName");
									ob3.setDataName(currentUser);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("date");
									ob3.setDataName(DateUtil.getCurrentDateUSFormat());
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("time");
									ob3.setDataName(DateUtil.getCurrentTime());
									insertTakeAction.add(ob3);
									cbt.insertIntoTable("client_takeaction", insertTakeAction, connectionSpace);
								}
							}
						}
					}

					// Create table 'lead_history'
					new LeadHelper2().createTableLeadHistory(connectionSpace);

					// MAKING AN ENTRY IN LEAD HISTORY TABLE WITH PER LEAD ACTION
					List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
					InsertDataTable ob4 = null;

					ob4 = new InsertDataTable();
					ob4.setColName("leadId");
					ob4.setDataName(getId());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("convertedTo");
					ob4.setDataName(LeadActionType.PROSPECTIVE_CLIENT.ordinal());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("comment");
					ob4.setDataName(comment);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("date");
					ob4.setDataName(DateUtil.getCurrentDateUSFormat());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("time");
					ob4.setDataName(DateUtil.getCurrentTimeHourMin());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("actionBy");
					ob4.setDataName(currentUser);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("status");
					ob4.setDataName(statusselect);
					insertHistory.add(ob4);

					cbt.insertIntoTable("lead_history", insertHistory, connectionSpace);
				}

				/*******************************************************************************
				 * Prospective associate
				 *******************************************************************************/
				else if (leadActionType == LeadActionType.PROSPECTIVE_ASSOCIATE)
				{

					if (sendEmail.equalsIgnoreCase("true") && email != null)
					{
						new LeadHelper2().sendMailOnLeadAction(email, leadName, LeadActionType.PROSPECTIVE_ASSOCIATE);
					}
					if (sendSms.equalsIgnoreCase("true") && mobileNo != null)
					{
						new LeadHelper2().sendSMSOnLeadAction(mobileNo, leadName, LeadActionType.PROSPECTIVE_ASSOCIATE);
					}

					// CREATE TABLE OF ASSOCIATE BASIC DETAILS AS PER CONFIGURATION
					String currentUser = userName;
					new AssociateHelper().createTableAssociateBasicData(connectionSpace, accountID);

					// Fetch lead data and put them variables
					Map<String, String> leadDataMap = new LeadHelper().fetchLeadBasicColumnAndValueMap(connectionSpace, accountID, id);
					String entryKey, entryValue;
					for (Map.Entry<String, String> entry : leadDataMap.entrySet())
					{
						entryKey = entry.getKey();
						entryValue = entry.getValue();
						if (entryKey.equalsIgnoreCase("starRating"))
						{
							starRating = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("faxNo"))
						{
							faxNo = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("mobileNo"))
						{
							mobileNo = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("email"))
						{
							email = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("comment"))
						{
							comment = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("sourceName"))
						{
							sourceName = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
						if (entryKey.equalsIgnoreCase("name"))
						{
							name = (entryValue == null || entryValue.equals("")) ? "NA" : entryValue;
						}
					}

					// INSERT DATA IN ASSOCIATE BASIC DETAILS AS PER LEAD
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;

					ob = new InsertDataTable();
					ob.setColName("location");
					ob.setDataName(name);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("associateName");
					ob.setDataName(leadName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("address");
					ob.setDataName(address);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("phoneNo");
					ob.setDataName(phoneNo);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("faxNo");
					ob.setDataName(faxNo);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("webAddress");
					ob.setDataName(webAddress);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("contactNo");
					ob.setDataName(mobileNo);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("associateEmail");
					ob.setDataName(EmailOfficial);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("sourceName");
					ob.setDataName(sourceName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("associateRating");
					ob.setDataName(starRating);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName(status);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");

					LeadHelper2 lh2 = new LeadHelper2();
					currentUser = lh2.getContactIdOfEmpByEmpId(userselect);
					if (currentUser != null)
					{
						ob.setDataName(currentUser);
					}
					else
					{
						currentUser = cId;
						ob.setDataName(currentUser);
					}

					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					cbt.insertIntoTable("associate_basic_data", insertData, connectionSpace);
					int idMAx = cbt.getMaxId("associate_basic_data", connectionSpace);
					lacd.insertInToKPIAutofillTable(empIdofuser, "3", userName, connectionSpace, String.valueOf(idMAx), "Prospective Associate");
					lacd.insertInToKPIAutofillTable(empIdofuser, "7", userName, connectionSpace, String.valueOf(idMAx), "Prospective Associate");
					List statusName = new LeadActionControlDao().statusName("statusname", "associatestatus", statusselect, connectionSpace);
					if (statusName != null && statusName.size() > 0)
					{
						if (statusName.get(0).toString().equalsIgnoreCase("Intro Meeting"))
						{
							lacd.insertInToKPIAutofillTable(empIdofuser, "5", userName, connectionSpace, String.valueOf(idMAx), "Prospective Associate");
						}
					}
					// CREATE Associate OFFERING MAPPING TABLE IF
					new AssociateHelper().createTableAssociateOfferingMapping(connectionSpace);

					// Insert into offering mapping table
					if (offering != null)
					{
						new LeadHelper2().mapClientOfferingMapping(connectionSpace, offering, idMAx, currentUser, level);
					}

					// CREATE TABLE OF ASSOCIATE CONTACT MASTER AS PER
					new AssociateHelper().createTableAssociateContactMaster(connectionSpace, accountID);

					int contactID = -1;
					// if (contactNo != null && !contactNo.equals(""))
					if (true)
					{
						// Insert data to associate contact
						// //System.out.println("userSelect " + userselect);
						List<InsertDataTable> insertContact = new ArrayList<InsertDataTable>();

						InsertDataTable ob2 = null;
						ob2 = new InsertDataTable();
						ob2.setColName("associateName");
						ob2.setDataName(idMAx);
						insertContact.add(ob2);

						if (communicationName != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("communicationName");
							ob2.setDataName(communicationName);
							insertContact.add(ob2);
						}

						if (personName != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("name");
							ob2.setDataName(personName);
							insertContact.add(ob2);
						}
						if (contactNo != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("contactNum");
							ob2.setDataName(contactNo);
							insertContact.add(ob2);
						}
						if (birthday != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("birthday");
							ob2.setDataName(birthday);
							insertContact.add(ob2);
						}
						if (anniversary != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("anniversary");
							ob2.setDataName(anniversary);
							insertContact.add(ob2);
						}
						if (emailOfficialContact != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("emailOfficial");
							ob2.setDataName(emailOfficialContact);
							insertContact.add(ob2);
						}
						if (designation != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("designation");
							ob2.setDataName(designation);
							insertContact.add(ob2);
						}
						if (department != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("department");
							ob2.setDataName(department);
							insertContact.add(ob2);
						}
						if (degreeOfInfluence != null)
						{
							ob2 = new InsertDataTable();
							ob2.setColName("degreeOfInfluence");
							ob2.setDataName(degreeOfInfluence);
							insertContact.add(ob2);
						}

						ob2 = new InsertDataTable();
						ob2.setColName("userName");
						ob2.setDataName(currentUser);
						insertContact.add(ob2);

						ob2 = new InsertDataTable();
						ob2.setColName("date");
						ob2.setDataName(DateUtil.getCurrentDateUSFormat());
						insertContact.add(ob2);

						ob2 = new InsertDataTable();
						ob2.setColName("time");
						ob2.setDataName(DateUtil.getCurrentTime());
						insertContact.add(ob2);

						cbt.insertIntoTable("associate_contact_data", insertContact, connectionSpace);
						contactID = cbt.getMaxId("associate_contact_data", connectionSpace);
					}

					// INSERT DATA IN ASSOCIATE ACTIVITY
					if (statusselect != null && !statusselect.equalsIgnoreCase("") && !statusselect.equalsIgnoreCase("-1"))
					{
						if (offering != null)
						{
							AssociateHelper ah = new AssociateHelper();
							ah.createTableAssociateTakeAction(connectionSpace);

							String offer[] = offering.split(",");
							for (String H : offer)
							{
								if (H != null && !H.equalsIgnoreCase(""))
								{

									List<InsertDataTable> insertTakeAction = new ArrayList<InsertDataTable>();
									InsertDataTable ob3 = null;
									ob3 = new InsertDataTable();
									ob3.setColName("contacId");
									ob3.setDataName(contactID);
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("offeringId");
									ob3.setDataName(H);
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("statusId");
									ob3.setDataName(statusselect);
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("maxDateTime");
									ob3.setDataName(time);
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("comment");
									ob3.setDataName(comment);
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("isFinished");
									ob3.setDataName("0");
									insertTakeAction.add(ob3);

									ob3 = new InsertDataTable();
									ob3.setColName("userName");
									ob3.setDataName(currentUser);
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("date");
									ob3.setDataName(DateUtil.getCurrentDateUSFormat());
									insertTakeAction.add(ob3);
									ob3 = new InsertDataTable();
									ob3.setColName("time");
									ob3.setDataName(DateUtil.getCurrentTime());
									insertTakeAction.add(ob3);

									cbt.insertIntoTable("associate_takeaction", insertTakeAction, connectionSpace);
								}
							}
						}
					}

					// Create table 'lead_history'
					new LeadHelper2().createTableLeadHistory(connectionSpace);

					// ADDING DATA IN LEAD HISTORY TABLE FOR LEAD
					List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
					InsertDataTable ob4 = null;
					ob4 = new InsertDataTable();
					ob4.setColName("leadId");
					ob4.setDataName(getId());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("convertedTo");
					ob4.setDataName(LeadActionType.PROSPECTIVE_ASSOCIATE.ordinal());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("comment");
					ob4.setDataName(comment);
					insertHistory.add(ob4);
					ob4 = new InsertDataTable();

					ob4.setColName("date");
					ob4.setDataName(DateUtil.getCurrentDateUSFormat());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("time");
					ob4.setDataName(DateUtil.getCurrentTimeHourMin());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("actionBy");
					ob4.setDataName(currentUser);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("status");
					ob4.setDataName(statusselect);
					insertHistory.add(ob4);

					cbt.insertIntoTable("lead_history", insertHistory, connectionSpace);
				}

				/*******************************************************************************
				 * Snoozed lead
				 *******************************************************************************/
				else if (leadActionType == LeadActionType.SNOOZE && !getId().equalsIgnoreCase("") && getId() != null)
				{
					String[] datetime = null;
					if (getTime() != null)
					{
						datetime = getTime().split(" ");
					}
					LeadHelper2 lh2 = new LeadHelper2();
					// send mail
					if (sendEmail.equalsIgnoreCase("true") && email != null)
					{
						lh2.sendMailOnLeadAction(email, leadName, LeadActionType.SNOOZE);
					}
					// send SMS
					if (sendSms.equalsIgnoreCase("true") && mobileNo != null)
					{
						lh2.sendSMSOnLeadAction(mobileNo, leadName, LeadActionType.SNOOZE);
					}

					lacd.insertInToKPIAutofillTable(empIdofuser, "3", userName, connectionSpace, id, "Lead");

					String currentUser = lh2.getContactIdOfEmpByEmpId(userselect);
					if (currentUser == null)
					{
						currentUser = cId;
					}

					// Create table 'lead_history'
					new LeadHelper2().createTableLeadHistory(connectionSpace);

					// ADDING LEAD HISTORY DATA IN LEAD HISTORY
					List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
					InsertDataTable ob4 = null;
					ob4 = new InsertDataTable();
					ob4.setColName("leadId");
					ob4.setDataName(getId());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("convertedTo");
					ob4.setDataName(LeadActionType.SNOOZE.ordinal());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("comment");
					ob4.setDataName(comment);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("date");
					ob4.setDataName(DateUtil.getCurrentDateUSFormat());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("time");
					ob4.setDataName(DateUtil.getCurrentTimeHourMin());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("actionBy");
					ob4.setDataName(currentUser);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("snooztime");
					ob4.setDataName(datetime[1]);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("snoozdate");
					ob4.setDataName(datetime[0]);
					insertHistory.add(ob4);

					cbt.insertIntoTable("lead_history", insertHistory, connectionSpace);
				}

				/*******************************************************************************
				 * Lost lead
				 *******************************************************************************/
				else if (leadActionType == LeadActionType.LOST)
				{
					// send mail
					if (sendEmail.equalsIgnoreCase("true") && email != null)
					{
						new LeadHelper2().sendMailOnLeadAction(email, leadName, LeadActionType.LOST);
					}
					// send SMS
					if (sendSms.equalsIgnoreCase("true") && mobileNo != null)
					{
						new LeadHelper2().sendSMSOnLeadAction(mobileNo, leadName, LeadActionType.LOST);
					}

					LeadHelper2 lh2 = new LeadHelper2();
					String currentUser = lh2.getContactIdOfEmpByEmpId(userselect);
					if (currentUser == null)
					{
						currentUser = cId;
					}
					

					// Create table 'lead_history'
					new LeadHelper2().createTableLeadHistory(connectionSpace);

					// ADDING LEAD HISTORY DATA IN LEAD HISTORY
					List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
					InsertDataTable ob4 = null;
					ob4 = new InsertDataTable();
					ob4.setColName("leadId");
					ob4.setDataName(getId());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("convertedTo");
					ob4.setDataName(LeadActionType.LOST.ordinal());
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("comment");
					ob4.setDataName(comment);
					insertHistory.add(ob4);
					
					ob4 = new InsertDataTable();
					ob4.setColName("date");
					ob4.setDataName(DateUtil.getCurrentDateUSFormat());
					insertHistory.add(ob4);
					
					ob4 = new InsertDataTable();
					ob4.setColName("time");
					ob4.setDataName(DateUtil.getCurrentTimeHourMin());
					insertHistory.add(ob4);
					
					ob4 = new InsertDataTable();
					ob4.setColName("actionBy");
					ob4.setDataName(currentUser);
					insertHistory.add(ob4);

					ob4 = new InsertDataTable();
					ob4.setColName("status");
					ob4.setDataName(statusselect);
					insertHistory.add(ob4);
					
					cbt.insertIntoTable("lead_history", insertHistory, connectionSpace);
				}
				addActionMessage("Action Taken Successfully");
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method updateLeadAction of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;
	}

	public String updateLostLead()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("status", 0);
			condtnBlock.put("id", id);
			cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock, connectionSpace);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method updateLostLead of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String leadHistoryAction()
	{

		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1
					.append("select lead.leadName, lh.convertedTo, lh.comment, lh.date, lh.time, lh.actionBy from lead_history as lh, leadgeneration as lead where lh.leadId = lead.id and lh.actionBy='"
							+ userName + "' ");
			List data3 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			parentTakeaction = new ArrayList<Parent>();

			if (data3 != null)
			{
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								if (j == 3)
								{
									c1.setName(DateUtil.convertDateToIndianFormat(obdata[j].toString().trim()));
								}
								else
								{
									c1.setName(obdata[j].toString());
								}
								// //System.out.println(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}
				// //System.out.println("parentTakeaction:" + parentTakeaction.size());
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method leadHistoryAction of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}

		return SUCCESS;
	}

	public String beforeDashboardView()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	public String fetchAllLead()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			jsonArray = new LeadHelper().getAllLead(connectionSpace, cId);
			ret = SUCCESS;
		}
		return ret;
	}

	public String addLeadContacts()
	{
		String returnString = ERROR;
        try {
            if (!ValidateSession.checkSession())
                return LOGIN;

            CommonOperInterface cbt = new CommonConFactory().createInterface();
            List<GridDataPropertyView> clientColName = Configuration
                    .getConfigurationData("mapped_lead_generation", accountID,
                            connectionSpace, "", 0, "table_name",
                            "lead_contact_configuration");

            if (clientColName != null) {
                // List <InsertDataTable> insertData=new
                // ArrayList<InsertDataTable>();
                boolean status = false;
                boolean empbasic_status = false;
                InsertDataTable ob = null;
                List<TableColumes> tableColumn = new ArrayList<TableColumes>();
                List<InsertDataTable> insertInEmpBasic = new ArrayList<InsertDataTable>();
                InsertDataTable empBasicObj = null;
                for (GridDataPropertyView gdp : clientColName) {
                    TableColumes ob1 = new TableColumes();
                    ob1.setColumnname(gdp.getColomnName());
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default NULL");
                    tableColumn.add(ob1);

                }

                cbt.createTable22("lead_contact_data", tableColumn,
                        connectionSpace);

                List<String> requestParameterNames = Collections
                            .list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();

                List paramList = new ArrayList<String>();
                int paramValueSize = 0;
                boolean statusTemp = true;
                String associateName = null;
                String leadTypeValue12= null;
                while (it.hasNext()) 
                {
                    String Parmname = it.next().toString();
                    String paramValue = request.getParameter(Parmname);
                    if (paramValue != null && Parmname != null) {
                        
                               paramList.add(Parmname);
                                 
                                //assign client name into variable
                                if (Parmname.equalsIgnoreCase("leadName")) {
                                       associateName = paramValue;
                                       continue;
                                    }
                                
                                // for leadtypevalue
                                if (Parmname.equalsIgnoreCase("leadTypeValue")) {
                                    leadTypeValue12 = paramValue;
                                       continue;
                                    }
                                
                              // adding the parameters list.
                        if (statusTemp) {
                              String tempParamValueSize[] = request
                                                    .getParameterValues(Parmname);
                                for (String H : tempParamValueSize) {
                                      // counting one time size of the parameter value
                                        if (H != null)
                                            paramValueSize++;
                                     }
                              statusTemp = false;
                            }
                    }
                }
                String parmValuew[][] = new String[paramList.size()][paramValueSize];
                int m = 0;
                for (Object c : paramList) 
                {
                    Object Parmname = (Object) c;
                    String paramValue[] = request.getParameterValues(Parmname.toString());

                    for (int j = 0; j < paramValue.length; j++) {

                        if (!paramValue[j].equalsIgnoreCase("")
                                && paramValue[j] != null
                                && !paramValue[j].equalsIgnoreCase("-1")) {
                            parmValuew[m][j] = paramValue[j];
                        }
                    }
                    m++;
                }
                boolean valueSelect = false;
                for (int i = 0; i < paramValueSize; i++) 
                {
                    List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                    for (int k = 0; k < paramList.size(); k++) {

                        if (parmValuew[k][i] != null) {
                            // //System.out.println("parmValuew[k][i]>>" +
                            // parmValuew[k][i]);
                            valueSelect = true;

                        } else {
                            valueSelect = false;
                        }

                        if (valueSelect) {
                            if (!paramList.get(k).toString().equalsIgnoreCase(
                                        "leadName") && !paramList.get(k).toString().equalsIgnoreCase(
                                        "leadTypeValue")) 
                            {
                                ob = new InsertDataTable();
                                empBasicObj = new InsertDataTable();
                                

                                if (paramList.get(k).toString()
                                        .equalsIgnoreCase("birthday")
                                        || paramList
                                                .get(k)
                                                .toString()
                                                .equalsIgnoreCase("anniversary")) {
                                    ob.setColName(paramList.get(k).toString());
                                    ob
                                            .setDataName(DateUtil
                                                    .convertDateToUSFormat(parmValuew[k][i]));
                                    empBasicObj.setColName(paramList.get(k)
                                            .toString());
                                    empBasicObj
                                            .setDataName(DateUtil
                                                    .convertDateToUSFormat(parmValuew[k][i]));

                                }
                                
                                else {
                                             ob.setColName(paramList.get(k).toString());
                                              ob.setDataName(parmValuew[k][i]);
                                      
                                          if (paramList.get(k).toString()
                                                 .equalsIgnoreCase(
                                                    "communicationName")) {
                                               empBasicObj.setColName("comName");
                                               empBasicObj
                                                .setDataName(parmValuew[k][i]);
                                                insertInEmpBasic.add(empBasicObj);
                                        } else if (paramList.get(k).toString()
                                            .equalsIgnoreCase("emailOfficial")) {
                                          empBasicObj.setColName("emailIdOne");
                                            empBasicObj
                                                .setDataName(parmValuew[k][i]);
                                            insertInEmpBasic.add(empBasicObj);
                                        }  

                                         else if (paramList.get(k).toString()
                                               .equalsIgnoreCase("contactNo")) {
                                              empBasicObj.setColName("mobOne");
                                               empBasicObj
                                                .setDataName(parmValuew[k][i]);
                                            insertInEmpBasic.add(empBasicObj);
                                        }

                                        else if (paramList.get(k).toString()
                                                .equalsIgnoreCase("department")) {
                                              empBasicObj.setColName("deptname");
                                              empBasicObj
                                                    .setDataName(new ClientHelper3().fetchClientDept());
                                        
                                                 insertInEmpBasic.add(empBasicObj);
                                          }

                                          else if (paramList.get(k).toString()
                                                      .equalsIgnoreCase("personName")) {
                                                     empBasicObj.setColName("empName");
                                                empBasicObj
                                                         .setDataName(parmValuew[k][i]);
                                                      insertInEmpBasic.add(empBasicObj);
                                           } else {
                                                      empBasicObj.setColName(paramList.get(k)
                                                          .toString());
                                                      empBasicObj
                                                         .setDataName(parmValuew[k][i]);
                                                           insertInEmpBasic.add(empBasicObj);
                                           }

                                }
                                insertData.add(ob);

                            }

                        }
                    }
    //add lead into contact object                
                    if (valueSelect) {
                        ob = new InsertDataTable();
                        ob.setColName("leadName");
                        ob.setDataName(associateName);
                        insertData.add(ob);
                        
                        ob = new InsertDataTable();
                        ob.setColName("leadTypeValue");
                        ob.setDataName(leadTypeValue12);
                        insertData.add(ob);
   
                        /*ob = new InsertDataTable();
                        ob.setColName("currentStatus");
                        ob.setDataName("1");
                        insertData.add(ob);*/
                        
                      
                        
                        /*ob = new InsertDataTable();
                        ob.setColName("currentStatusDate");
                        ob.setDataName(DateUtil.getCurrentDateUSFormat());
                        insertData.add(ob);*/

                        ob = new InsertDataTable();
                        ob.setColName("userName");
                        ob.setDataName(cId);
                        insertData.add(ob);

                        ob = new InsertDataTable();
                        ob.setColName("date");
                        ob.setDataName(DateUtil.getCurrentDateUSFormat());
                        insertData.add(ob);

                        ob = new InsertDataTable();
                        ob.setColName("time");
                        ob.setDataName(DateUtil.getCurrentTime());
                        insertData.add(ob);

                        // for empBasic
                        
                        
                        status = cbt.insertIntoTable("lead_contact_data",
                                insertData, connectionSpace);
                        /*empbasic_status = cbt.insertIntoTable("employee_basic",
                                insertInEmpBasic, connectionSpace);*/
                    }
                }

                if (status) {
                    addActionMessage("Client contact saved successfully");
                    returnString = SUCCESS;
                } else {
                    addActionMessage("Oops There is some error in data!");
                    returnString = SUCCESS;
                }
            }
        } catch (Exception e) {
            log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
                    + " Time: " + DateUtil.getCurrentTime() + " "
                    + "Problem in Over2Cloud  method: addContacts of class "
                    + getClass(), e);
            e.printStackTrace();
        }
        return returnString;
        
		/*//System.out.println("111111111111111111111111111111111");
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> leadColName = Configuration
						.getConfigurationData("mapped_lead_generation",
								accountID, connectionSpace, "", 0, "table_name",
								"lead_contact_configuration");

				if (leadColName != null) 
				   {
					                                  // List <InsertDataTable> insertData=new
					                                 // ArrayList<InsertDataTable>();
					InsertDataTable ob = null;

					boolean status = false;
					List<TableColumes> tableColumn = new ArrayList<TableColumes>();

					for (GridDataPropertyView gdp : leadColName) {
						        TableColumes ob1 = new TableColumes();
						        ob1.setColumnname(gdp.getColomnName());
						        ob1.setDatatype("varchar(255)");
						        ob1.setConstraint("default NULL");
						        tableColumn.add(ob1);

					}

					cbt.createTable22("lead_contact_data", tableColumn,
				  	                                                   connectionSpace);
			   }			

				int count = new LeadHelper().addDataToLeadContact(connectionSpace, accountID, request, cId, leadName);
				addActionMessage(count + " Contacts saved successfully.");
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;*/
	}

	public String fetchSubIndustry()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{System.out.println(">>>>>>>>>>>>>>>>>fetchSubIndustry>>>>>");
				jsonArray = new LeadHelper().fetchSubIndustry(connectionSpace, id, LeadData.ById);
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String leadFullView()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				leadBasicFullViewMap = new LeadHelper().leadBasicFullView(connectionSpace, id, accountID);
				if (leadBasicFullViewMap != null && leadBasicFullViewMap.size() > 0)
				{
					leadContactFullViewMap = new LeadHelper().leadContactFullView(connectionSpace, id, accountID);
				}

				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String fetchLeadEmail()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				email = new LeadHelper2().fetchLeadEmailById(connectionSpace, id);
				//System.out.println("email:" + email);
				ret = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}

	/*
	 * Sanjiv 20-10-2014 Fetch match time
	 */
	public String validateLeadTimeForOffering() {

		try {
			
			if (!ValidateSession.checkSession())
				return LOGIN;
			String contactId = new LeadHelper2().getContactIdOfEmpByEmpId(userselect);
			System.out.println(">><>>>>>>>>>userName lead>"+userName+"   contactId>>"+contactId);
			String dateUS = scheduletime.substring(0, 10);
			String timeval = scheduletime.substring(10, 16);
			scheduletime = DateUtil.convertDateToUSFormat(dateUS)+timeval;
			timestatuslead = new LeadHelper2().fetchLeadTimeStatusForOffering(
					EntityType.CLIENT, contactId, id, scheduletime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getStarRating()
	{
		return starRating;
	}

	public void setStarRating(String starRating)
	{
		this.starRating = starRating;
	}

	public String getTargetAchieved()
	{
		return targetAchieved;
	}

	public void setTargetAchieved(String targetAchieved)
	{
		this.targetAchieved = targetAchieved;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getDdValue()
	{
		return ddValue;
	}

	public void setDdValue(String ddValue)
	{
		this.ddValue = ddValue;
	}

	public String getLeadName()
	{
		return leadName;
	}

	public void setLeadName(String leadName)
	{
		this.leadName = leadName;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getLeadAddress()
	{
		return leadAddress;
	}

	public void setLeadAddress(String leadAddress)
	{
		this.leadAddress = leadAddress;
	}

	public String getPhoneNo()
	{
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public String getOfferingLevel()
	{
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel)
	{
		this.offeringLevel = offeringLevel;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public Map<Integer, String> getOfferingList2()
	{
		return offeringList2;
	}

	public void setOfferingList2(Map<Integer, String> offeringList2)
	{
		this.offeringList2 = offeringList2;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getConvertTo()
	{
		return convertTo;
	}

	public void setConvertTo(String convertTo)
	{
		this.convertTo = convertTo;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDesignation()
	{
		return designation;
	}

	public void setDesignation(String designation)
	{
		this.designation = designation;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getEmailOfficial()
	{
		return EmailOfficial;
	}

	public void setEmailOfficial(String emailOfficial)
	{
		EmailOfficial = emailOfficial;
	}

	public String getOffering()
	{
		return offering;
	}

	public void setOffering(String offering)
	{
		this.offering = offering;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getEmployee()
	{
		return employee;
	}

	public void setEmployee(String employee)
	{
		this.employee = employee;
	}

	public String getIdLost()
	{
		return idLost;
	}

	public void setIdLost(String idLost)
	{
		this.idLost = idLost;
	}

	public String getMeetingTime()
	{
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime)
	{
		this.meetingTime = meetingTime;
	}

	public String getHistory()
	{
		return history;
	}

	public void setHistory(String history)
	{
		this.history = history;
	}

	public List<Parent> getParentTakeaction()
	{
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction)
	{
		this.parentTakeaction = parentTakeaction;
	}

	public Map<String, String> getLeadAActionTextBox()
	{
		return leadAActionTextBox;
	}

	public void setLeadAActionTextBox(Map<String, String> leadAActionTextBox)
	{
		this.leadAActionTextBox = leadAActionTextBox;
	}

	public Map<Integer, String> getTargetKpiLiist()
	{
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist)
	{
		this.targetKpiLiist = targetKpiLiist;
	}

	public String getTargetselect()
	{
		return targetselect;
	}

	public void setTargetselect(String targetselect)
	{
		this.targetselect = targetselect;
	}

	public boolean isLocationTrue()
	{
		return locationTrue;
	}

	public void setLocationTrue(boolean locationTrue)
	{
		this.locationTrue = locationTrue;
	}

	public String getClienttypeName()
	{
		return clienttypeName;
	}

	public void setClienttypeName(String clienttypeName)
	{
		this.clienttypeName = clienttypeName;
		if (clienttypeName.equals("1")) leadActionType = LeadActionType.PROSPECTIVE_CLIENT;
		else if (clienttypeName.equals("2")) leadActionType = LeadActionType.PROSPECTIVE_ASSOCIATE;
		else if (clienttypeName.equals("3")) leadActionType = LeadActionType.SNOOZE;
		else if (clienttypeName.equals("4")) leadActionType = LeadActionType.LOST;
	}

	public String getProductselect()
	{
		return productselect;
	}

	public void setProductselect(String productselect)
	{
		this.productselect = productselect;
	}

	public String getStatusselect()
	{
		return statusselect;
	}

	public void setStatusselect(String statusselect)
	{
		this.statusselect = statusselect;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public int getNewLead()
	{
		return newLead;
	}

	public void setNewLead(int newLead)
	{
		this.newLead = newLead;
	}

	public int getSnoozeLead()
	{
		return snoozeLead;
	}

	public void setSnoozeLead(int snoozeLead)
	{
		this.snoozeLead = snoozeLead;
	}

	public int getLstLead()
	{
		return lstLead;
	}

	public void setLstLead(int lstLead)
	{
		this.lstLead = lstLead;
	}

	public String getDate_and_time()
	{
		return date_and_time;
	}

	public void setDate_and_time(String date_and_time)
	{
		this.date_and_time = date_and_time;
	}

	public String getSendEmail()
	{
		return sendEmail;
	}

	public void setSendEmail(String sendEmail)
	{
		this.sendEmail = sendEmail;
	}

	public String getSendSms()
	{
		return sendSms;
	}

	public void setSendSms(String sendSms)
	{
		this.sendSms = sendSms;
	}

	public String getAddFlag()
	{
		return addFlag;
	}

	public void setAddFlag(String addFlag)
	{
		this.addFlag = addFlag;
	}

	public String getAssociateName()
	{
		return associateName;
	}

	public void setAssociateName(String associateName)
	{
		this.associateName = associateName;
	}

	public String getUserselect()
	{
		return userselect;
	}

	public void setUserselect(String userselect)
	{
		this.userselect = userselect;
	}

	public String getShowAction()
	{
		return showAction;
	}

	public void setShowAction(String showAction)
	{
		this.showAction = showAction;
	}

	public String getFromDashboard()
	{
		return fromDashboard;
	}

	public void setFromDashboard(String fromDashboard)
	{
		this.fromDashboard = fromDashboard;
	}

	public String getHistoryLead()
	{
		return historyLead;
	}

	public void setHistoryLead(String historyLead)
	{
		this.historyLead = historyLead;
	}

	public String getDdTrue()
	{
		return ddTrue;
	}

	public void setDdTrue(String ddTrue)
	{
		this.ddTrue = ddTrue;
	}

	public String getTargetTrue()
	{
		return targetTrue;
	}

	public void setTargetTrue(String targetTrue)
	{
		this.targetTrue = targetTrue;
	}

	public String getSourceTrue()
	{
		return sourceTrue;
	}

	public void setSourceTrue(String sourceTrue)
	{
		this.sourceTrue = sourceTrue;
	}

	public int getConvertedToCliet()
	{
		return convertedToCliet;
	}

	public void setConvertedToCliet(int convertedToCliet)
	{
		this.convertedToCliet = convertedToCliet;
	}

	public int getConvertedToAssociate()
	{
		return convertedToAssociate;
	}

	public void setConvertedToAssociate(int convertedToAssociate)
	{
		this.convertedToAssociate = convertedToAssociate;
	}

	public int getLeadBasicDetails()
	{
		return leadBasicDetails;
	}

	public void setLeadBasicDetails(int leadBasicDetails)
	{
		this.leadBasicDetails = leadBasicDetails;
	}

	public int getLeadContacDetails()
	{
		return leadContacDetails;
	}

	public void setLeadContacDetails(int leadContacDetails)
	{
		this.leadContacDetails = leadContacDetails;
	}

	public Map<String, String> getNameMap()
	{
		return nameMap;
	}

	public void setNameMap(Map<String, String> nameMap)
	{
		this.nameMap = nameMap;
	}

	public List<ConfigurationUtilBean> getLeadContactControls()
	{
		return leadContactControls;
	}

	public void setLeadContactControls(List<ConfigurationUtilBean> leadContactControls)
	{
		this.leadContactControls = leadContactControls;
	}

	public String getLeadNameValue()
	{
		return leadNameValue;
	}

	public void setLeadNameValue(String leadNameValue)
	{
		this.leadNameValue = leadNameValue;
	}

	public List<GridDataPropertyView> getMasterViewPropForLead()
	{
		return masterViewPropForLead;
	}

	public void setMasterViewPropForLead(List<GridDataPropertyView> masterViewPropForLead)
	{
		this.masterViewPropForLead = masterViewPropForLead;
	}

	public List<Object> getMasterViewListForLeadContact()
	{
		return masterViewListForLeadContact;
	}

	public void setMasterViewListForLeadContact(List<Object> masterViewListForLeadContact)
	{
		this.masterViewListForLeadContact = masterViewListForLeadContact;
	}

	public List<ConfigurationUtilBean> getLeadBasicControls()
	{
		return leadBasicControls;
	}

	public void setLeadBasicControls(List<ConfigurationUtilBean> leadBasicControls)
	{
		this.leadBasicControls = leadBasicControls;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public Integer getContactStartingNum()
	{
		return contactStartingNum;
	}

	public void setContactStartingNum(Integer contactStartingNum)
	{
		this.contactStartingNum = contactStartingNum;
	}

	public Map<String, String> getLeadBasicFullViewMap()
	{
		return leadBasicFullViewMap;
	}

	public void setLeadBasicFullViewMap(Map<String, String> leadBasicFullViewMap)
	{
		this.leadBasicFullViewMap = leadBasicFullViewMap;
	}

	public Map<String, String> getLeadContactFullViewMap()
	{
		return leadContactFullViewMap;
	}

	public void setLeadContactFullViewMap(Map<String, String> leadContactFullViewMap)
	{
		this.leadContactFullViewMap = leadContactFullViewMap;
	}

	public Map<String, String> getIndustryList()
	{
		return industryList;
	}

	public void setIndustryList(Map<String, String> industryList)
	{
		this.industryList = industryList;
	}

	public Map<String, String> getSourceList()
	{
		return sourceList;
	}

	public void setSourceList(Map<String, String> sourceList)
	{
		this.sourceList = sourceList;
	}

	public Map<String, String> getLocationList()
	{
		return locationList;
	}

	public void setLocationList(Map<String, String> locationList)
	{
		this.locationList = locationList;
	}

	public String getIndustry()
	{
		return industry;
	}

	public void setIndustry(String industry)
	{
		this.industry = industry;
	}

	public String getSubIndustry()
	{
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry)
	{
		this.subIndustry = subIndustry;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public ArrayList<ArrayList<String>> getLeadBasicForAction()
	{
		return leadBasicForAction;
	}

	public void setLeadBasicForAction(ArrayList<ArrayList<String>> leadBasicForAction)
	{
		this.leadBasicForAction = leadBasicForAction;
	}

	public ArrayList<ArrayList<String>> getLeadContactForAction()
	{
		return leadContactForAction;
	}

	public void setLeadContactForAction(ArrayList<ArrayList<String>> leadContactForAction)
	{
		this.leadContactForAction = leadContactForAction;
	}

	public LeadActionType getLeadActionType()
	{
		return leadActionType;
	}

	public void setLeadActionType(LeadActionType leadActionType)
	{
		this.leadActionType = leadActionType;
	}

	public String getFaxNo()
	{
		return faxNo;
	}

	public void setFaxNo(String faxNo)
	{
		this.faxNo = faxNo;
	}

	public String getWebAddress()
	{
		return webAddress;
	}

	public void setWebAddress(String webAddress)
	{
		this.webAddress = webAddress;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmailOfficialContact()
	{
		return emailOfficialContact;
	}

	public void setEmailOfficialContact(String emailOfficialContact)
	{
		this.emailOfficialContact = emailOfficialContact;
	}

	public String getAnniversary()
	{
		return anniversary;
	}

	public void setAnniversary(String anniversary)
	{
		this.anniversary = anniversary;
	}

	public String getDegreeOfInfluence()
	{
		return degreeOfInfluence;
	}

	public void setDegreeOfInfluence(String degreeOfInfluence)
	{
		this.degreeOfInfluence = degreeOfInfluence;
	}

	public String getCommunicationName()
	{
		return communicationName;
	}

	public void setCommunicationName(String communicationName)
	{
		this.communicationName = communicationName;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getPersonName()
	{
		return personName;
	}

	public void setPersonName(String personName)
	{
		this.personName = personName;
	}

	public void setDeptMap(LinkedHashMap<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public LinkedHashMap<String, String> getDeptMap()
	{
		return deptMap;
	}

	public void setSubIndustryList(Map<String, String> subIndustryList)
	{
		this.subIndustryList = subIndustryList;
	}

	public Map<String, String> getSubIndustryList()
	{
		return subIndustryList;
	}

	public void setLeadContactEditList(ArrayList<ArrayList<GridDataPropertyView>> leadContactEditList)
	{
		this.leadContactEditList = leadContactEditList;
	}

	public ArrayList<ArrayList<GridDataPropertyView>> getLeadContactEditList()
	{
		return leadContactEditList;
	}

	public void setDeptlist(Map<String, String> deptlist)
	{
		this.deptlist = deptlist;
	}

	public Map<String, String> getDeptlist()
	{
		return deptlist;
	}

	public Map<String, String> getWeightageMasterMap() {
		return weightageMasterMap;
	}

	public void setWeightageMasterMap(Map<String, String> weightageMasterMap) {
		this.weightageMasterMap = weightageMasterMap;
	}

	public String getWeightage_targetsegment() {
		return weightage_targetsegment;
	}

	public void setWeightage_targetsegment(String weightage_targetsegment) {
		this.weightage_targetsegment = weightage_targetsegment;
	}

	public String getTimestatuslead() {
		return timestatuslead;
	}

	public void setTimestatuslead(String timestatuslead) {
		this.timestatuslead = timestatuslead;
	}

	public String getScheduletime() {
		return scheduletime;
	}

	public void setScheduletime(String scheduletime) {
		this.scheduletime = scheduletime;
	}

	public String getLead_name_wild() {
		return lead_name_wild;
	}

	public void setLead_name_wild(String lead_name_wild) {
		this.lead_name_wild = lead_name_wild;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return counter;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public int getCount3() {
		return count3;
	}

	public void setCount3(int count3) {
		this.count3 = count3;
	}

	public String getIndustryExit() {
		return industryExit;
	}

	public void setIndustryExit(String industryExit) {
		this.industryExit = industryExit;
	}

	public String getSourceExit() {
		return sourceExit;
	}

	public void setSourceExit(String sourceExit) {
		this.sourceExit = sourceExit;
	}

	public String getLocationExit() {
		return locationExit;
	}

	public void setLocationExit(String locationExit) {
		this.locationExit = locationExit;
	}

	public String getStarRatingExit() {
		return starRatingExit;
	}

	public void setStarRatingExit(String starRatingExit) {
		this.starRatingExit = starRatingExit;
	}

	public String getRefNameExit() {
		return refNameExit;
	}

	public void setRefNameExit(String refNameExit) {
		this.refNameExit = refNameExit;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getReferedByExit() {
		return referedByExit;
	}

	public void setReferedByExit(String referedByExit) {
		this.referedByExit = referedByExit;
	}

	public String getReferedBy() {
		return referedBy;
	}

	public void setReferedBy(String referedBy) {
		this.referedBy = referedBy;
	}

	public String getSubindustryExit() {
		return subindustryExit;
	}

	public void setSubindustryExit(String subindustryExit) {
		this.subindustryExit = subindustryExit;
	}
	

}