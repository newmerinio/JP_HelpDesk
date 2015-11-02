package com.Over2Cloud.ctrl.wfpm.industry;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndustryMapActionControl extends ActionSupport implements
		ServletRequestAware {
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private HttpServletRequest request;
	private String offeringLevel = session.get("offeringLevel").toString();


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
	private String oper;
	private String id,status;
	private String subindustryID;
	private String targetSegmentID;
	private int lastOffering;

	private String verticalname;
	private String offeringname;
	private String subofferingname;
	private String targetSegment;
	private String variantname;
	private String subvariantsize;
	private String subIndustry;
	private String offeringExit;
	
	private String deptID;
	private String weightage;

	private boolean OLevel1;
	private boolean OLevel2;
	private boolean OLevel3;
	private boolean OLevel4;
	private boolean OLevel5;

	private String OLevel1LevelName;
	private String OLevel2LevelName;
	private String OLevel3LevelName;
	private String OLevel4LevelName;
	private String OLevel5LevelName;

	private LinkedHashMap<String, String> verticalMap;
	private LinkedHashMap<String, String> industryMasterMap;
	private LinkedHashMap<String, String> deptMasterMap;
	private LinkedHashMap<String, String> offeMasterMap;
	private LinkedHashMap<String, String> targetSegmentMap;
	private LinkedHashMap<String, String> offeringMap;
	private LinkedHashMap<String, String> subOfferingMap;
	private Map<String, String> listtest;
	private JSONArray jsonArray = null;
	// private Map<Integer, String> mapOfferingList = null;
	private List<WeightageSort> mapOfferingList;
	private String param = "";
	private String refval = "";
	private String offeringId;
	private String Offeringlevel;
	private String industryDDHeadingName;
	private String subIndustryHeadingName;
	private String targetDDHeadingName;
	private String deptHeadingName;
	private String subDeptHeadingName;
	private List<Object>								weightageDataList;
	private Integer countTargetSegment=0;

	public String beforeIndustryMap() {
		String returnValue = LOGIN;
		if (ValidateSession.checkSession()) {
			beforMapWeightage();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			   // For Industry Dropdown dynamic name start here
			  /*List<GridDataPropertyView> offeringLevel1 = Configuration
					          .getConfigurationData("mapped_industry_configuration",
							             accountID, connectionSpace, "", 0, "table_name",
							                               "industry_configuration");
			  for (GridDataPropertyView gdp : offeringLevel1) {
				     if (gdp.getColType().trim().equalsIgnoreCase("T")
						          && gdp.getColomnName().equalsIgnoreCase("industry")) {
					                industryDDHeadingName = gdp.getHeadingName();
					                    // subIndustryHeadingName=gdp.getHeadingName();
				   }
			   }*/
			 // Fill values in dropdown
			 // Get all industry list
 /*	 industryMasterMap = new LinkedHashMap<String, String>();
			List industryMasterData = cbt.executeAllSelectQuery("select id, "    //commented on 29aug2014
					   + "industry from industrydatalevel1 order by industry",
					           connectionSpace);
			if (industryMasterData != null) {
				 for (Object c : industryMasterData) {
					  Object[] dataC = (Object[]) c;
					  industryMasterMap.put(dataC[0].toString(), dataC[1]
							     .toString());
				   }
			   }
			
*/			 
			
			
		 List<GridDataPropertyView> offeringLevel1 = Configuration
			                         .getConfigurationData("mapped_weightage_master",
					                                accountID, connectionSpace, "", 0, "table_name",
					                                            "weightage_master");
	          for (GridDataPropertyView gdp : offeringLevel1) 
	              {
		            if (gdp.getColType().trim().equalsIgnoreCase("T")
				                    && gdp.getColomnName().equalsIgnoreCase("weightageName")) 
		               {
		        	     targetDDHeadingName = gdp.getHeadingName();
			             // subIndustryHeadingName=gdp.getHeadingName();
		               }
	              }
			
			targetSegmentMap = new LinkedHashMap<String, String>();
			List targetMasterData = cbt.executeAllSelectQuery("select id, "
					      + "weightageName from weightagemaster order by weightageName",
					              connectionSpace);
			if (targetMasterData != null) {
				for (Object c : targetMasterData) {
					Object[] dataC = (Object[]) c;
					targetSegmentMap.put(dataC[0].toString(), dataC[1]
							.toString());
				  }
			  }
			// Fill values in dropdown
			// Get all dept list
			deptMasterMap = new LinkedHashMap<String, String>();
			List deptMasterData = cbt.executeAllSelectQuery("select id, "
					+ "deptName from department order by deptName",
					connectionSpace);
			if (deptMasterData != null) {
				for (Object c : deptMasterData) {
					Object[] dataC = (Object[]) c;
					deptMasterMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}
			// End Here                               //commented on 1sep2014
            /*		//Sub industry map
			List<GridDataPropertyView> offeringLevel2 = Configuration
					    .getConfigurationData("mapped_sub_industry_configuration",
							         accountID, connectionSpace, "", 0, "table_name",
							          "sub_industry_configuration");
			for (GridDataPropertyView gdp : offeringLevel2) {
				if (gdp.getColType().trim().equalsIgnoreCase("T")
						&& gdp.getColomnName().equalsIgnoreCase("subIndustry")) {
					subIndustryHeadingName = gdp.getHeadingName();
				}
			}
			//End sub-industry    */
			List<GridDataPropertyView> deptheadName = Configuration
					.getConfigurationData("mapped_dept_config_param",
							accountID, connectionSpace, "", 0, "table_name",
							"dept_config_param");
			for (GridDataPropertyView gdp : deptheadName) {
				if (gdp.getColType().trim().equalsIgnoreCase("T")
						&& gdp.getColomnName().equalsIgnoreCase("deptName")) {
					deptHeadingName = gdp.getHeadingName();
				    }
			}
			List<GridDataPropertyView> subdeptheadName = Configuration
					.getConfigurationData("mapped_subdeprtmentconf", accountID,
							connectionSpace, "", 0, "table_name",
							"subdeprtmentconf");
			for (GridDataPropertyView gdp : subdeptheadName) {
				if (gdp.getColType().trim().equalsIgnoreCase("T")
						&& gdp.getColomnName().equalsIgnoreCase("subdeptname")) {
					subDeptHeadingName = gdp.getHeadingName();
				}
			}
			// oferring taken by client in configuration
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) {
				oLevels = offeringLevel.split("#");
				//////System.out.println("oLevels>>>>>>>>>>>"+oLevels[0].toString(
				// ));//3
				int level = Integer.parseInt(oLevels[0]);
				if (level > 0) {
					OLevel1 = true;
					OLevel1LevelName = oLevels[1];
				}
				if (level > 1) {
					OLevel2 = true;
					OLevel2LevelName = oLevels[2];
				}
				if (level > 2) {
					OLevel3 = true;
					OLevel3LevelName = oLevels[3];
				}
				if (level > 3) {
					OLevel4 = true;
					OLevel4LevelName = oLevels[4];
				}
				if (level > 4) {
					OLevel5 = true;
					OLevel5LevelName = oLevels[5];
				}
				verticalMap = new LinkedHashMap<String, String>();
				List verticalData = cbt.executeAllSelectQuery(
						      "select id, verticalname from "
								       + "offeringlevel1 order by verticalname",
						     connectionSpace);
				if (verticalData != null) {
					  for (Object c : verticalData) {
						         Object[] dataC = (Object[]) c;
						         verticalMap.put(dataC[0].toString(), dataC[1]
								         .toString());
					}
				}
			}
			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String fetchsubIndustryName() {
		String returnValue = LOGIN;
		if (ValidateSession.checkSession()) {

			try {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				// //System.out.println("param>>>>>>>" + param);
				if (param.equalsIgnoreCase("1")) {
					query1
							.append("select distinct(subi.id),subi.subIndustry from subindustrydatalevel2 "
									+ "as subi inner join off_indust_subindust_dept_mapping as oisd on oisd.subIndustry=subi.id "
									+ "where offeringId='" + offeringId + "'");
				} else {
					query1
							.append("select id,subIndustry from subindustrydatalevel2 where industry='");
					query1.append(id);
					query1.append("' order by subIndustry ");
				}
				// //System.out.println("query1>>>>>>>>>>>>>" +
				// query1.toString());
				List data2 = cbt.executeAllSelectQuery(query1.toString(),
						                                                 connectionSpace);
				// //System.out.println("data2>>>>>>>>>>>>>>>>>>>" + data2);
				if (data2 != null) {
					jsonArray = new JSONArray();
					for (Iterator it = data2.iterator(); it.hasNext();) {
						Object[] obdata = (Object[]) it.next();
						if (obdata != null) {
							JSONObject formDetailsJson = new JSONObject();

							formDetailsJson.put("ID", obdata[0].toString());

							formDetailsJson.put("NAME", obdata[1].toString());

							jsonArray.add(formDetailsJson);
						}
					}
				}
				returnValue = SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	/*
	 * Gudiya 25-02-2014 Fetch data of one level based on other level
	 */
 public String fetchOfferingLevelData() {
	 try {

			
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			   
			if (Offeringlevel.equalsIgnoreCase("1")
					                               && Integer.parseInt(offeringLevel) > 1) 
			   {
			  	 CommonOperInterface cbt = new CommonConFactory()
						                                         .createInterface();
				 List empSelectParam = new ArrayList<String>();
				                                                         /*empSelectParam.add("id");
				                                                         empSelectParam.add("offeringname");
				                                                         Map<String, Object> temp = new HashMap<String, Object>();
				        
				                                                         temp.put("verticalname", offeringId);
				                                                         empSelectParam = cbt
						                                                 .viewAllDataEitherSelectOrAll("offeringlevel2",
								                                                             empSelectParam, temp, connectionSpace);*/
				 String query="select id,offeringname from offeringlevel2 where verticalname in("+offeringId+")";
				 System.out.println("query*******************"+query+"*********Offeringid"+offeringId);
				 empSelectParam=cbt.executeAllSelectQuery(query, connectionSpace);
				 offeringMap = new LinkedHashMap<String, String>(); 
				 if (empSelectParam != null && empSelectParam.size() > 0) 
				    {
				                                     //	jsonArray = new JSONArray();
					  for (Object c : empSelectParam) 
					      {
						    Object[] dataC = (Object[]) c;
						    offeringMap.put(dataC[0].toString() , dataC[1].toString());
					     
						    System.out.println("size of offering map"+offeringMap.size());
						
					                                                       //	JSONObject formDetailsJson = new JSONObject();
					                                                         /*	formDetailsJson.put("ID", Integer
							                                               	.toString((Integer) dataC[0]));
						                                                    formDetailsJson.put("NAME", dataC[1].toString());

						                                                     jsonArray.add(formDetailsJson);   */	
						 }
					}

			 } else if (Offeringlevel.equalsIgnoreCase("2")	&& Integer.parseInt(offeringLevel) > 2) 
			  {
				CommonOperInterface cbt = new CommonConFactory()
						                                       .createInterface();
				List empSelectParam = new ArrayList<String>();
				                                                 /*empSelectParam.add("id");
				                                                   empSelectParam.add("subofferingname");
				                                                   Map<String, Object> temp = new HashMap<String, Object>();
				                                                   temp.put("offeringname", offeringId);
				                                                   System.out.println("offering level2************* "+offeringId);
				                                                   empSelectParam = cbt
						                                                               .viewAllDataEitherSelectOrAll("offeringlevel3",
								                                   empSelectParam, temp, connectionSpace);*/
				String query="select id,subofferingname from offeringlevel3 where offeringname in("+offeringId+")";
				
				empSelectParam = cbt.executeAllSelectQuery(query, connectionSpace);

				subOfferingMap=new LinkedHashMap<String, String>();
				     
				if (empSelectParam != null && empSelectParam.size() > 0) 
				   {
				                    //	jsonArray = new JSONArray();
					for (Object c : empSelectParam) 
					    {
						  Object[] dataC = (Object[]) c;

						  subOfferingMap.put(dataC[0].toString(), dataC[1].toString());
						  System.out.println("size of suoffering"+subOfferingMap.size());
						 
						                                                 // JSONObject formDetailsJson = new JSONObject();
						                                                 /* formDetailsJson.put("ID", Integer
								                                        .toString((Integer) dataC[0]));
						                                               formDetailsJson.put("NAME", dataC[1].toString());
 
						                                           jsonArray.add(formDetailsJson);*/
						}

				  }
			} else if (Offeringlevel.equalsIgnoreCase("3")
					                && Integer.parseInt(offeringLevel) > 3) 
			 {
				 CommonOperInterface cbt = new CommonConFactory()
						                                         .createInterface();
				 List empSelectParam = new ArrayList<String>();
				 empSelectParam.add("id");
				 empSelectParam.add("variantname");
				 Map<String, Object> temp = new HashMap<String, Object>();
				 temp.put("subofferingname", offeringId);
				 empSelectParam = cbt
						            .viewAllDataEitherSelectOrAll("offeringLevel4",
								                           empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0) 
				   {
					 jsonArray = new JSONArray();
					  for (Object c : empSelectParam) {
						  Object[] dataC = (Object[]) c;

						  JSONObject formDetailsJson = new JSONObject();
						  formDetailsJson.put("ID", Integer
								.toString((Integer) dataC[0]));
						  formDetailsJson.put("NAME", dataC[1].toString());

						  jsonArray.add(formDetailsJson);
					}
				}
			} else if (Offeringlevel.equalsIgnoreCase("4")
					                                    && Integer.parseInt(offeringLevel) > 4)
			{
				CommonOperInterface cbt = new CommonConFactory()
						                                        .createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("subvariantname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("variantname", offeringId);
				empSelectParam = cbt
						            .viewAllDataEitherSelectOrAll("offeringlevel5",
								                                           empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0) 
				   {
					 jsonArray = new JSONArray();
					 for (Object c : empSelectParam) 
					     {
						  Object[] dataC = (Object[]) c;

						  JSONObject formDetailsJson = new JSONObject();
						  formDetailsJson.put("ID", Integer
								.toString((Integer) dataC[0]));
						  formDetailsJson.put("NAME", dataC[1].toString());

						  jsonArray.add(formDetailsJson);
					     }
				  }
			}
      
		} catch (Exception e) {
			LOG
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method: fetchOfferingLevelData of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Gudiya 25-02-2014 Store client offerings map with industry
	 */
	public String addMapIndustryOffering() {
		  

		String returnString = "SUCCESS";
		boolean flag = false;
		try {
			 
			
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			InsertDataTable ob = null;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			int level = 0;

			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) {
				 oLevels = offeringLevel.split("#");
				 level = Integer.parseInt(oLevels[0]);
			}

			List<String> requestParameterNames = Collections
					                                      .list((Enumeration<String>) request.getParameterNames());
			Iterator it = requestParameterNames.iterator();

			String industry = null;
			String subIndustry = null;
			String deptName = null;
			String offeringId = "";
			List<String> paramList = new ArrayList<String>();
			while (it.hasNext()) 
			{
				   String ParmName = it.next().toString();

			     /*	 if (ParmName.equalsIgnoreCase("targetSegment"))
				     {
					    targetSegment = request.getParameter(ParmName);
				     } 
				    */ if (ParmName.equalsIgnoreCase("subIndustry")) {
					          subIndustry = request.getParameter(ParmName);
				     } else if (ParmName.equalsIgnoreCase("deptName")) {
					    deptName = request.getParameter(ParmName);
				     }
                    else 
				     {
					    // get all offering level in a list
					    //paramList.add(ParmName);
					if(level == 1)
					{
						if (ParmName.equalsIgnoreCase("subvariantsize")) {
							offeringId = request.getParameter(ParmName);
						}
					}
					if(level == 2)
					{
						if (ParmName.equalsIgnoreCase("variantname")) {
							offeringId = request.getParameter(ParmName);
						}
					}
					if(level == 3)
					{
						if (ParmName.equalsIgnoreCase("subofferingname")) {
							offeringId = request.getParameter(ParmName);
						}
					}
					if(level == 4)
					{
						if (ParmName.equalsIgnoreCase("offeringname")) {
							offeringId = request.getParameter(ParmName);
						}
					}
					if(level == 5)
					{
						if (ParmName.equalsIgnoreCase("verticalname")) {
							offeringId = request.getParameter(ParmName);
						}
					}
				}

			}
			//System.out.println("offeringId======== :"+offeringId);
			/*String tempParamValues[] = null;
			if (paramList.contains("subvariantsize")) {
				tempParamValues = request.getParameterValues("subvariantsize");
			} else if (paramList.contains("variantname")) {
				tempParamValues = request.getParameterValues("variantname");
			} else if (paramList.contains("subofferingname")) {
				tempParamValues = request.getParameterValues("subofferingname");
			} else if (paramList.contains("offeringname")) {
				tempParamValues = request.getParameterValues("offeringname");
			} else if (paramList.contains("verticalname")) {
				tempParamValues = request.getParameterValues("verticalname");
			}*/

			// count: it is to check no. of offering mapped with client
			// successfully
			int count = 0;
			
			//if (tempParamValues.length > 0) {
				// create off_indust_subindust_dept_mapping table
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("offeringId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				// overwriting same tableColumes reference again and again
				ob1 = new TableColumes();
				ob1.setColumnname("offeringLevelId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				/*
				 * ob1 = new TableColumes(); ob1.setColumnname("industry");
				 * ob1.setDatatype("varchar(255)");
				 * ob1.setConstraint("default NULL"); Tablecolumesaaa.add(ob1);
				 */

				ob1 = new TableColumes();
				ob1.setColumnname("targetSegment");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("deptName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("weightage");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				cbt.createTable22("off_indust_subindust_dept_mapping",
						                                             Tablecolumesaaa, connectionSpace);
				// end creating off_indust_subindust_dept_mapping table

				/*
				 * set one record value for client
				 */
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				// //System.out.println("offeringId??????????????" + offeringId);
                
				String[] targetIdArray= CommonHelper.getCommaSeparatedToArray(targetSegment);
				String[] subofferingIdArray=CommonHelper.getCommaSeparatedToArray(subofferingname);
				
	//add the sub-offering and target segment for weightage			
	if(subofferingIdArray != null)
	  {
		for(String suboffering : subofferingIdArray)
		  {
			 if(targetIdArray != null)
			   {
				 
			     for(String targetsegment : targetIdArray)
			        { //offeringId is sub-offering data    
			    	   ob = new InsertDataTable();
			    	   ob.setColName("offeringId");
			    	   ob.setDataName(suboffering);
			    	   insertData.add(ob);
			        
				       ob = new InsertDataTable();
				       ob.setColName("offeringLevelId");
				       ob.setDataName(String.valueOf(level));
				       insertData.add(ob);
				
					   ob = new InsertDataTable();
				       ob.setColName("targetSegment");
				       ob.setDataName(targetsegment);
				       insertData.add(ob);

				       ob = new InsertDataTable();
				       ob.setColName("userName");
				       ob.setDataName(userName);
				       insertData.add(ob);

				       ob = new InsertDataTable();
				       ob.setColName("date");
				       ob.setDataName(DateUtil.getCurrentDateUSFormat());
				       insertData.add(ob);

				       ob = new InsertDataTable();
				       ob.setColName("time");
				       ob.setDataName(DateUtil.getCurrentTime());
				       insertData.add(ob);
				       
				       ob = new InsertDataTable();
				       ob.setColName("status");
				       ob.setDataName("Active");
				       insertData.add(ob);
				       
				      /*
				      * Each record value will be same for client except offeringID
				      * for multiple mapped offerings save record one by one
				      */

				     InsertDataTable ob2 = new InsertDataTable();
				     InsertDataTable ob3 = new InsertDataTable();
				     ob2.setColName("deptName");
				     ob3.setColName("weightage");
				     insertData.add(ob2);
				     insertData.add(ob3);

				     if (deptID != null && !deptID.equalsIgnoreCase("")
						            && weightage != null && !weightage.equalsIgnoreCase(""))
				      {
					          String[] splitWeightages = weightage.split(",");
					          String[] splitDeptID = deptID.split(",");
					          IndustryHelper ih = new IndustryHelper();
					                                       /*
					                                          * for (int i = 0; i < splitWeightages.length; i++) { if
					                                          * (splitWeightages[i] != null &&
					                                          * !splitWeightages[i].trim().equalsIgnoreCase("")) {
					                                          * ob3.setDataName(splitWeightages[i].trim());
					                                          * //System.out.println("ob3 :"+ob3); } }
					                                          */
					         for (int i = 0; i < splitWeightages.length; i++) 
					             {
						               if (splitWeightages[i] != null
								                                    && !splitWeightages[i].trim().equalsIgnoreCase(""))
						                   {
							
							                   boolean isExist = ih.isRecordExists(cbt,
							                		                       suboffering, String.valueOf(level),
							                		                       targetsegment, splitDeptID[i].trim());
							                   if(isExist)
							                      {
								                   assignWeightage(suboffering,targetsegment);//update, splitWeightages[i].trim() 
							                      }
							                      else
							                      {
									                ob2.setDataName(splitDeptID[i].trim());
									                ob3.setDataName(splitWeightages[i].trim());
									                flag = cbt.insertIntoTable("off_indust_subindust_dept_mapping", insertData, connectionSpace);
									                if (flag) count++;
								                    //insert, splitWeightages[i].trim() 
							                       }
						                    }
					               }
				           }//department and weightage block end here
				     insertData.clear();
			        }
			   }
		  }
	 }	
			if (count > 0) {
				addActionMessage(count
						+ " Weightage with Department Mapping saved successfully !");
				returnString = SUCCESS;
			} else {
				//addActionMessage("Oops There is some error in data!");
				returnString = SUCCESS;
			}

		} catch (Exception e) {
			LOG.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: addOffering of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return returnString;
	}

	// fetch data in dropdown offering ,before assign weightage to dept
	public String beforeAssignWeightage() {
		String returnValue = LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (ValidateSession.checkSession()) {
			try {

				beforeIndustryMap();
				int level = 0;

				String[] oLevels = null;

				if (offeringLevel != null
						&& !offeringLevel.equalsIgnoreCase("")) {
					oLevels = offeringLevel.split("#");
					level = Integer.parseInt(oLevels[0]);
					lastOffering = level;
					// //System.out.println("lastOffering?????????????????" +
					// lastOffering);
				}

				String tableName = "", colName = "";
				if (level == 1) {
					tableName = "offeringlevel1";
					colName = "verticalname";
				}
				if (level == 2) {
					tableName = "offeringlevel2";
					colName = "offeringname";
				}
				if (level == 3) {
					tableName = "offeringlevel3";
					colName = "subofferingname";
				}
				if (level == 4) {
					tableName = "offeringlevel4";
					colName = "variantname";
				}
				if (level == 5) {
					tableName = "offeringlevel5";
					colName = "subvariantname";
				}
				offeMasterMap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder();
				query.append("select off.id,off.");
				query.append(colName);
				query.append(" from ");
				query.append(tableName);// select id,subofferingname from
				// offeringlevel3;
				query
						.append(" as off inner join off_indust_subindust_dept_mapping as oisd on oisd.offeringId=off.id");
				//System.out.println("query??????????" + query);
				List offeMasterMapData = cbt.executeAllSelectQuery(query
						.toString(), connectionSpace);

				if (offeMasterMapData != null) {
					for (Object c : offeMasterMapData) {
						Object[] dataC = (Object[]) c;
						offeMasterMap.put(dataC[0].toString(), dataC[1]
								.toString());
					}
				}

				returnValue = SUCCESS;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforMapWeightage() {
		String returnValue = LOGIN;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				int level = 0;

				String[] oLevels = null;

				if (offeringLevel != null
						&& !offeringLevel.equalsIgnoreCase("")) {
					oLevels = offeringLevel.split("#");
					level = Integer.parseInt(oLevels[0]);
					lastOffering = level;
				}

				Map<String, String> deptMap = new IndustryHelper()
						.fetchAllDept(connectionSpace);

				mapOfferingList = new ArrayList<WeightageSort>();
				StringBuilder query = new StringBuilder();
				query
						.append("select oisdm.deptName as 'deptId', dept.deptName, oisdm.weightage from off_indust_subindust_dept_mapping oisdm ");
				query
						.append("inner join department as dept on dept.id = oisdm.deptName where oisdm.offeringId = '");
				query.append(offeringId);
				query.append("' and oisdm.offeringLevelId = '");
				query.append(lastOffering);
				query.append("' and oisdm.targetSegment = '");
				query.append(targetSegmentID);
				query.append("' order by dept.deptName");
				System.out.println("Target segment id************"+targetSegmentID);

				//System.out.println("Query1111111: " + query);
				List data = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (data != null && data.size() > 0) {
					ArrayList<String> val;
					for (Object iterator : data) {
						Object[] object = (Object[]) iterator;

						for (Iterator<Map.Entry<String, String>> entry = deptMap
								.entrySet().iterator(); entry.hasNext();) {
							WeightageSort ob = new WeightageSort();
							val = new ArrayList<String>();
							if (entry.next().getKey().equals(
									object[0].toString().trim())) {
								entry.remove();
								ob.setId(object[0].toString());
								ob.setDeptname((object[1] == null || object[1].toString().equals("")) ? "0" : object[1].toString());
								ob.setWeightage((object[2] == null || object[2].toString().equals("")) ? "0" : object[2].toString());
								//val.add((object[1] == null || object[1].toString().equals("")) ? "0" : object[1].toString());
								//val.add((object[2] == null || object[2].toString().equals("")) ? "0" : object[2].toString());
								mapOfferingList.add(ob);
								
							}
						}
					}
				}

				//List<WeightageSort> val = new ArrayList<WeightageSort>();
				for (Map.Entry<String, String> entry : deptMap.entrySet()) {
					//val = new ArrayList<WeightageSort>();
					WeightageSort ob = new WeightageSort();
					ob.setDeptname(entry.getValue());
					ob.setId(entry.getKey());
					ob.setWeightage("0");
					mapOfferingList.add(ob);
				}

				if(mapOfferingList!=null && mapOfferingList.size()>0)
					Collections.sort(mapOfferingList);
				returnValue = SUCCESS;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String assignWeightage(String sub_offering, String target_segmentId) {
		String returnValue = LOGIN;
		if (ValidateSession.checkSession()) {
			// //System.out.println("subIndustry:" + subIndustry);
			// //System.out.println("subofferingname:" + subofferingname);
			try {
				int level = 0;
				String[] oLevels = null;

				if (offeringLevel != null
						&& !offeringLevel.equalsIgnoreCase("")) {
					oLevels = offeringLevel.split("#");
					level = Integer.parseInt(oLevels[0]);
				}

				if (level == 1) {
					offeringId = verticalname;
				}
				if (level == 2) {
					offeringId = offeringname;
				}
				if (level == 3) {
					offeringId = sub_offering;
				}
				if (level == 4) {
					offeringId = variantname;
				}
				if (level == 5) {
					offeringId = subvariantsize;
				}
				//target_segmentId = target_segment;
				//System.out.println("deptID==================" + deptID);
				//System.out.println("weightage================" + weightage);
				//System.out.println("offeringId===============" + offeringId);
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				if (deptID != null && !deptID.equalsIgnoreCase("")
						&& weightage != null && !weightage.equalsIgnoreCase("")) {
					boolean stauts = false;
					String[] splitWeightages = weightage.split(",");
					String[] splitDeptID = deptID.split(",");

					for (int i = 0; i < splitWeightages.length; i++) {
						StringBuilder query = new StringBuilder();
						if (splitWeightages[i] != null
								&& !splitWeightages[i].trim().equalsIgnoreCase(
										"") && splitDeptID[i] != null
								&& !splitDeptID[i].trim().equalsIgnoreCase("")) {
							query
									.append(" update off_indust_subindust_dept_mapping ");
							query.append(" set weightage='");
							query.append(splitWeightages[i].trim());
							query.append("' where offeringId='");
							query.append(offeringId);
							query.append("' and targetSegment='");
							query.append(target_segmentId);
							query.append("' and deptName='");
							query.append(splitDeptID[i].trim());
							query.append("'");
							//System.out.println("query: " + query);
							stauts = cbt.updateTableColomn(connectionSpace,
									query);
							// //System.out.println("splitWeightages[i]==" +
							// splitWeightages[i].trim());
							// //System.out.println("splitDeptID[i]==" +
							// splitDeptID[i].trim());
							//System.out.println("stauts :" + stauts);
							
						}
					}
					if (stauts) {
						addActionMessage("Weightages updated with Department Successfully!");
					}
					else
					{
						addActionMessage("OOP's some errors in Data");
					}

				}
				return SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeTargetSegmentView() {
		String retType = LOGIN;
		if (ValidateSession.checkSession()) {
			try {

				retType = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//count Target Segment (by Azad 7July 2014)
		String query1="select count(*) from off_indust_subindust_dept_mapping";
		countTargetSegment=countRecord(query1);
		
		
		
		
		
		
		return retType;
	}

	
	//function of count Target Segment (by Azad 7july 2014)
	public Integer countRecord(String query)
	{ 	  BigInteger totalRecord=new BigInteger("3");
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
	
	
	
	
	@SuppressWarnings("unchecked")
	public String viewWeightage() {
		String retType = LOGIN;
		if (ValidateSession.checkSession()) {
			try 
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from off_indust_subindust_dept_mapping");
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

					CommonHelper ch = new CommonHelper();
					String[] off =  ch.getOfferingName();
					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select co.id, co.");
					query.append(off[0]);
					query.append(",sbi.weightageName,dept.deptName,oisd.weightage, oisd.userName, oisd.date from ");
					query.append(off[1]);
					query.append(" as co, off_indust_subindust_dept_mapping as oisd, ");
					query.append("weightagemaster as sbi, ");
					query.append("department as dept ");
					query.append("where oisd.offeringId=co.id  ");
					query.append("and oisd.targetSegment=sbi.id ");
					query.append("and oisd.deptName=dept.id ");
					query.append("and oisd.weightage <> '0' ");
					if(this.getStatus()!=null && !this.getStatus().equals("-1") &&  !this.getStatus().equalsIgnoreCase("undefined"))
					{
						query.append("and oisd.status='"+this.getStatus()+"'");
					}
					System.out.println("query for view viewWeightage :"+query);
					// for Searching start here
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						}
					}
					// End Here
					
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						for(Object ob: data)
						{
							Map<String, Object>	map= new HashMap<String, Object>();
							Object[] obdata = (Object[]) ob ;
							map.put("id", obdata[0]);
							System.out.println("id>>"+obdata[0].toString());
							//System.out.println("offeringId>"+CommonHelper.getFieldValue(obdata[1]));
							//System.out.println("subIndustry>"+CommonHelper.getFieldValue(obdata[2]));
							//System.out.println("deptName>"+CommonHelper.getFieldValue(obdata[3]));
							//System.out.println("weightage>"+CommonHelper.getFieldValue(obdata[4]));
							//System.out.println("userName>"+CommonHelper.getFieldValue(obdata[5]));
							//System.out.println("date>"+CommonHelper.getFieldValue(obdata[6]));
							
							map.put("offeringId" , CommonHelper.getFieldValue(obdata[1]));
							map.put("targetSegment" , CommonHelper.getFieldValue(obdata[2]));
							map.put("deptName" , CommonHelper.getFieldValue(obdata[3]));
							map.put("weightage" , CommonHelper.getFieldValue(obdata[4]));
							map.put("userName" , CommonHelper.getFieldValue(obdata[5]));
							map.put("date" , DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[6])));
							
							tempList.add(map);
						}
						setWeightageDataList(tempList);
						//System.out.println("tempList>>"+tempList.size()+"WeightageDataList>>>"+weightageDataList.size());
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				retType = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		return retType;
	}

	public LinkedHashMap<String, String> getIndustryMasterMap() {
		return industryMasterMap;
	}

	public void setIndustryMasterMap(
			LinkedHashMap<String, String> industryMasterMap) {
		this.industryMasterMap = industryMasterMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getOfferingLevel() {
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel) {
		this.offeringLevel = offeringLevel;
	}

	public boolean isOLevel1() {
		return OLevel1;
	}

	public void setOLevel1(boolean oLevel1) {
		OLevel1 = oLevel1;
	}

	public boolean isOLevel2() {
		return OLevel2;
	}

	public void setOLevel2(boolean oLevel2) {
		OLevel2 = oLevel2;
	}

	public boolean isOLevel3() {
		return OLevel3;
	}

	public void setOLevel3(boolean oLevel3) {
		OLevel3 = oLevel3;
	}

	public boolean isOLevel4() {
		return OLevel4;
	}

	public void setOLevel4(boolean oLevel4) {
		OLevel4 = oLevel4;
	}

	public boolean isOLevel5() {
		return OLevel5;
	}

	public void setOLevel5(boolean oLevel5) {
		OLevel5 = oLevel5;
	}

	public String getOLevel1LevelName() {
		return OLevel1LevelName;
	}

	public void setOLevel1LevelName(String oLevel1LevelName) {
		OLevel1LevelName = oLevel1LevelName;
	}

	public String getOLevel2LevelName() {
		return OLevel2LevelName;
	}

	public void setOLevel2LevelName(String oLevel2LevelName) {
		OLevel2LevelName = oLevel2LevelName;
	}

	public String getOLevel3LevelName() {
		return OLevel3LevelName;
	}

	public void setOLevel3LevelName(String oLevel3LevelName) {
		OLevel3LevelName = oLevel3LevelName;
	}

	public String getOLevel4LevelName() {
		return OLevel4LevelName;
	}

	public void setOLevel4LevelName(String oLevel4LevelName) {
		OLevel4LevelName = oLevel4LevelName;
	}

	public String getOLevel5LevelName() {
		return OLevel5LevelName;
	}

	public void setOLevel5LevelName(String oLevel5LevelName) {
		OLevel5LevelName = oLevel5LevelName;
	}

	public LinkedHashMap<String, String> getVerticalMap() {
		return verticalMap;
	}

	public void setVerticalMap(LinkedHashMap<String, String> verticalMap) {
		this.verticalMap = verticalMap;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getOfferinglevel() {
		return Offeringlevel;
	}

	public void setOfferinglevel(String offeringlevel) {
		Offeringlevel = offeringlevel;
	}

	public String getIndustryDDHeadingName() {
		return industryDDHeadingName;
	}

	public void setIndustryDDHeadingName(String industryDDHeadingName) {
		this.industryDDHeadingName = industryDDHeadingName;
	}

	public String getSubIndustryHeadingName() {
		return subIndustryHeadingName;
	}

	public void setSubIndustryHeadingName(String subIndustryHeadingName) {
		this.subIndustryHeadingName = subIndustryHeadingName;
	}

	public String getDeptHeadingName() {
		return deptHeadingName;
	}

	public void setDeptHeadingName(String deptHeadingName) {
		this.deptHeadingName = deptHeadingName;
	}

	public String getSubDeptHeadingName() {
		return subDeptHeadingName;
	}

	public void setSubDeptHeadingName(String subDeptHeadingName) {
		this.subDeptHeadingName = subDeptHeadingName;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public LinkedHashMap<String, String> getDeptMasterMap() {
		return deptMasterMap;
	}

	public void setDeptMasterMap(LinkedHashMap<String, String> deptMasterMap) {
		this.deptMasterMap = deptMasterMap;
	}

	public String getRefval() {
		return refval;
	}

	public void setRefval(String refval) {
		this.refval = refval;
	}

	public int getLastOffering() {
		return lastOffering;
	}

	public void setLastOffering(int lastOffering) {
		this.lastOffering = lastOffering;
	}

	public LinkedHashMap<String, String> getOffeMasterMap() {
		return offeMasterMap;
	}

	public void setOffeMasterMap(LinkedHashMap<String, String> offeMasterMap) {
		this.offeMasterMap = offeMasterMap;
	}

	public String getSubindustryID() {
		return subindustryID;
	}

	public void setSubindustryID(String subindustryID) {
		this.subindustryID = subindustryID;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getWeightage() {
		return weightage;
	}

	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}

	public String getVerticalname() {
		return verticalname;
	}

	public void setVerticalname(String verticalname) {
		this.verticalname = verticalname;
	}

	public String getOfferingname() {
		return offeringname;
	}

	public void setOfferingname(String offeringname) {
		this.offeringname = offeringname;
	}

	public String getSubofferingname() {
		return subofferingname;
	}

	public void setSubofferingname(String subofferingname) {
		this.subofferingname = subofferingname;
	}

	public String getVariantname() {
		return variantname;
	}

	public void setVariantname(String variantname) {
		this.variantname = variantname;
	}

	public String getSubvariantsize() {
		return subvariantsize;
	}

	public void setSubvariantsize(String subvariantsize) {
		this.subvariantsize = subvariantsize;
	}

	public String getSubIndustry() {
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry) {
		this.subIndustry = subIndustry;
	}
	public List<WeightageSort> getMapOfferingList() {
		return mapOfferingList;
	}

	public void setMapOfferingList(List<WeightageSort> mapOfferingList) {
		this.mapOfferingList = mapOfferingList;
	}

	public List<Object> getWeightageDataList() {
		return weightageDataList;
	}

	public void setWeightageDataList(List<Object> weightageDataList) {
		this.weightageDataList = weightageDataList;
	}

	public Integer getCountTargetSegment() {
		return countTargetSegment;
	}

	public void setCountTargetSegment(Integer countTargetSegment) {
		this.countTargetSegment = countTargetSegment;
	}

	public LinkedHashMap<String, String> getTargetSegmentMap() {
		return targetSegmentMap;
	}

	public void setTargetSegmentMap(LinkedHashMap<String, String> targetSegmentMap) {
		this.targetSegmentMap = targetSegmentMap;
	}

	public String getTargetDDHeadingName() {
		return targetDDHeadingName;
	}

	public void setTargetDDHeadingName(String targetDDHeadingName) {
		this.targetDDHeadingName = targetDDHeadingName;
	}

	public String getTargetSegmentID() {
		return targetSegmentID;
	}

	public void setTargetSegmentID(String targetSegmentID) {
		this.targetSegmentID = targetSegmentID;
	}

	public LinkedHashMap<String, String> getSubOfferingMap() {
		return subOfferingMap;
	}

	public void setSubOfferingMap(LinkedHashMap<String, String> subOfferingMap) {
		this.subOfferingMap = subOfferingMap;
	}

	public Map<String, String> getListtest() {
		return listtest;
	}

	public void setListtest(Map<String, String> listtest) {
		this.listtest = listtest;
	}

	public String getTargetSegment() {
		return targetSegment;
	}

	public void setTargetSegment(String targetSegment) {
		this.targetSegment = targetSegment;
	}

	public String getOfferingExit() {
		return offeringExit;
	}

	public void setOfferingExit(String offeringExit) {
		this.offeringExit = offeringExit;
	}

	public LinkedHashMap<String, String> getOfferingMap() {
		return offeringMap;
	}

	public void setOfferingMap(LinkedHashMap<String, String> offeringMap) {
		this.offeringMap = offeringMap;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	
	

}
