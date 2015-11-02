package com.Over2Cloud.ctrl.productivityEvaluation.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.productivityEvaluation.ProductivityEvaluationKaizenHelper;

public class ProductivityDashboardAction extends GridPropertyBean
{
	
	private static final long serialVersionUID = -4081531049143607394L;
	private List<ProductivityDashboardPojo> kaizenList;
	private List<ProductivityDashboardPojo> bestPracticesList;
	private List<ProductivityDashboardPojo> improvedList;
	private List<ProductivityDashboardPojo> developementList;
	private List<ProductivityDashboardPojo> capacityList;
	private List<ProductivityDashboardPojo> reviewDateList;
	private List<ProductivityDashboardPojo> notesList;
	private List<ProductivityDashboardPojo> operationalList;
	private List<ProductivityDashboardPojo> cmdCommList;
	private List<ProductivityDashboardPojo> bscCommList;
	private List<ProductivityDashboardPojo> OGTaskList;
	private List<ProductivityDashboardPojo> cmoCommList;
	private List<ProductivityDashboardPojo> projectsList;
	private String moduleName;
	private String dataFor;
	private List<GridDataPropertyView> masterViewProp=null;
	private 	List<Object> 	masterViewList;
	
	public String beforeDashboardKaizen()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				// First Quadrant ....
				kaizenList=new ArrayList<ProductivityDashboardPojo>();
				ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				kaizenList=PDH.getKaizenDashboardData(connectionSpace,"Kaizen");
				
				// Second Quadrant ....
				bestPracticesList=new ArrayList<ProductivityDashboardPojo>();
				bestPracticesList=PDH.getKaizenDashboardData(connectionSpace,"Best Practices");
				
				return SUCCESS;
			} 
			catch (Exception e)
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
	
	public String beforeDashboardImproved()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				// First Quadrant ....
				improvedList=new ArrayList<ProductivityDashboardPojo>();
				improvedList=PDH.getImprovedDashboardData(connectionSpace,"Improvement Dashboard");
				
				// Second Quadrant ....
				developementList=new ArrayList<ProductivityDashboardPojo>();
				developementList=PDH.getImprovedDashboardData(connectionSpace,"Development Project");
				
				// Third Quadrant ....
				capacityList=new ArrayList<ProductivityDashboardPojo>();
				capacityList=PDH.getImprovedDashboardData(connectionSpace,"Capacity Confirmation");
				
				return SUCCESS;
			} 
			catch (Exception e)
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
	
	public String beforeDashboardReviewDates()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				// First Quadrant ....
				reviewDateList=new ArrayList<ProductivityDashboardPojo>();
				reviewDateList=PDH.getReviewDatesDashboardData(connectionSpace,"Review Dates");
				
				// Second Quadrant ....
				notesList=new ArrayList<ProductivityDashboardPojo>();
				notesList=PDH.getReviewDatesDashboardData(connectionSpace,"Notes");
				
				
				return SUCCESS;
			} 
			catch (Exception e)
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
	
	public String beforeDashboardOperationalBSC()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				// First Quadrant ....
				operationalList=new ArrayList<ProductivityDashboardPojo>();
				operationalList=PDH.getReviewDatesDashboardData(connectionSpace,"Operational Excellence");
				
				// Second Quadrant ....
				cmdCommList=new ArrayList<ProductivityDashboardPojo>();
				cmdCommList=PDH.getReviewDatesDashboardData(connectionSpace,"CMD Comm");
				
				// Third Quadrant ....
				bscCommList=new ArrayList<ProductivityDashboardPojo>();
				bscCommList=PDH.getReviewDatesDashboardData(connectionSpace,"BSC Comm");
				
				return SUCCESS;
			} 
			catch (Exception e)
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
	
	public String beforeDashboardMain()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				ProductivityDashboardHelper PDH=new ProductivityDashboardHelper();
				// First Quadrant ....
				
				
				OGTaskList=new ArrayList<ProductivityDashboardPojo>();
				OGTaskList = PDH.oGTaskData(connectionSpace);
				
				cmoCommList=new ArrayList<ProductivityDashboardPojo>();
				cmoCommList= PDH.getCMOCommData(connectionSpace);
				
				projectsList=new ArrayList<ProductivityDashboardPojo>();
				projectsList= PDH.getProjectsData(connectionSpace);
		
				return SUCCESS;
			} 
			catch (Exception e)
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
	
	public String viewOGTaskDash() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				viewPageProps(moduleName);
				return SUCCESS;
			} 
			catch (Exception e) 
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
	
	
	@SuppressWarnings("unchecked")
	private void viewPageProps(String module) 
	{
		masterViewProp=new ArrayList<GridDataPropertyView>();
		
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("fromOG");
		gpv.setHeadingName("OG");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("fromPlant");
		gpv.setHeadingName("Plant");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_kaizen_add_configuration",accountID,connectionSpace,"",0,"table_name","kaizen_add_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			if (!gdp.getColomnName().equalsIgnoreCase("empId")) 
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				masterViewProp.add(gpv);
			}
		}
		session.put("fieldsname", masterViewProp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewOGTaskGridData() 
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from kaizen_add_data");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				
				if(dataCount!=null && dataCount.size()>0)
				{
					//getting the list of colmuns
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("fieldsname");
					session.remove("fieldsname");
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("fromOG")) 
								{
									query.append("gi.groupName AS fromOg,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("fromPlant"))
								{
									query.append("dept.deptName As fromplant,");
								}
								else
								{
									query.append("kad."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("kad."+gpv.getColomnName().toString());
							}
							i++;
							
						}
						query.append(" from kaizen_add_data AS kad");
						query.append(" LEFT JOIN employee_basic AS emp ON emp.id =  kad.empId ");
						query.append(" LEFT JOIN department AS dept ON dept.id =emp.deptname ");
						query.append(" LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId ");
						query.append(" WHERE moduleName = '"+moduleName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						System.out.println("QUERY IS AS :::  "+query.toString());
						if(data!=null && data.size()>0)
						{
							ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								int k=0;
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
									if(obdata[k]!=null)
									{
										if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else if(gpv.getColomnName().equalsIgnoreCase("implementedIn"))
										{
											one.put(gpv.getColomnName(),obdata[k].toString().split("-")[1]+"-"+obdata[k].toString().split("-")[0]);
										}
										else if(gpv.getColomnName().equalsIgnoreCase("otherOG"))
										{
											one.put(gpv.getColomnName(),KH.getOtherMultipleOGDetails(connectionSpace,obdata[k+1].toString()));
										}
										else if(gpv.getColomnName().equalsIgnoreCase("otherPlant"))
										{
											one.put(gpv.getColomnName(),KH.getotherMultiplePlantNames(connectionSpace,obdata[k].toString()));
										}
										else
										{
											one.put(gpv.getColomnName(),obdata[k].toString());
										}
									}
									else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
									
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	else
	{
		returnResult=LOGIN;
	}
		return returnResult;
	}
	
	public List<ProductivityDashboardPojo> getKaizenList()
	{
		return kaizenList;
	}
	public void setKaizenList(List<ProductivityDashboardPojo> kaizenList)
	{
		this.kaizenList = kaizenList;
	}

	public List<ProductivityDashboardPojo> getBestPracticesList()
	{
		return bestPracticesList;
	}

	public void setBestPracticesList(
			List<ProductivityDashboardPojo> bestPracticesList)
	{
		this.bestPracticesList = bestPracticesList;
	}

	public List<ProductivityDashboardPojo> getImprovedList()
	{
		return improvedList;
	}

	public void setImprovedList(List<ProductivityDashboardPojo> improvedList)
	{
		this.improvedList = improvedList;
	}

	public List<ProductivityDashboardPojo> getDevelopementList()
	{
		return developementList;
	}

	public void setDevelopementList(List<ProductivityDashboardPojo> developementList)
	{
		this.developementList = developementList;
	}

	public List<ProductivityDashboardPojo> getCapacityList()
	{
		return capacityList;
	}

	public void setCapacityList(List<ProductivityDashboardPojo> capacityList)
	{
		this.capacityList = capacityList;
	}

	public List<ProductivityDashboardPojo> getReviewDateList()
	{
		return reviewDateList;
	}

	public void setReviewDateList(List<ProductivityDashboardPojo> reviewDateList)
	{
		this.reviewDateList = reviewDateList;
	}

	public List<ProductivityDashboardPojo> getNotesList()
	{
		return notesList;
	}

	public void setNotesList(List<ProductivityDashboardPojo> notesList)
	{
		this.notesList = notesList;
	}

	public List<ProductivityDashboardPojo> getOperationalList()
	{
		return operationalList;
	}

	public void setOperationalList(List<ProductivityDashboardPojo> operationalList)
	{
		this.operationalList = operationalList;
	}

	public List<ProductivityDashboardPojo> getCmdCommList()
	{
		return cmdCommList;
	}

	public void setCmdCommList(List<ProductivityDashboardPojo> cmdCommList)
	{
		this.cmdCommList = cmdCommList;
	}

	public List<ProductivityDashboardPojo> getBscCommList()
	{
		return bscCommList;
	}

	public void setBscCommList(List<ProductivityDashboardPojo> bscCommList)
	{
		this.bscCommList = bscCommList;
	}

	public List<ProductivityDashboardPojo> getOGTaskList()
	{
		return OGTaskList;
	}

	public void setOGTaskList(List<ProductivityDashboardPojo> oGTaskList)
	{
		OGTaskList = oGTaskList;
	}

	public List<ProductivityDashboardPojo> getCmoCommList()
	{
		return cmoCommList;
	}

	public void setCmoCommList(List<ProductivityDashboardPojo> cmoCommList)
	{
		this.cmoCommList = cmoCommList;
	}

	public List<ProductivityDashboardPojo> getProjectsList()
	{
		return projectsList;
	}

	public void setProjectsList(List<ProductivityDashboardPojo> projectsList)
	{
		this.projectsList = projectsList;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDataFor() {
		return dataFor;
	}

	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	
}
