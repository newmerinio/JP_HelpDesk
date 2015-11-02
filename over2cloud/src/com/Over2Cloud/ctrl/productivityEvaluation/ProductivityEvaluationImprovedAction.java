package com.Over2Cloud.ctrl.productivityEvaluation;

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

public class ProductivityEvaluationImprovedAction extends GridPropertyBean
{
	private static final long serialVersionUID = 8806681043682330928L;
	private List<GridDataPropertyView> masterViewProp=null;
	private 	List<Object> 	masterViewList;
	private String moduleName;
	
	public String  beforeImprovedPageView() 
	{
	 	  boolean sessionFlag=ValidateSession.checkSession();
	 	  if(sessionFlag)
	 	  {
	 		 try
	 		 {
	 			  setViewProp();
	 			  return SUCCESS;
	 		 }
	 		 catch(Exception exp)
	 		 {
	 			exp.printStackTrace();
	 			return ERROR;
	 		 }
	 	  }
	 	  else
	 	  {
	 		 return LOGIN;
	 	  }
	  }

	@SuppressWarnings("unchecked")
	public void setViewProp() 
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
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_improved_dash_add_configuration",accountID,connectionSpace,"",0,"table_name","improved_dash_add_configuration");
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
	public String viewImprovedData() 
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
				query1.append("select count(*) from improved_add_data");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
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
								else if(gpv.getColomnName().equalsIgnoreCase("byReview"))
								{
									query.append("dept2.deptName As byReview,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("reviewName"))
								{
									query.append("emp1.empName As reviewName,");
								}
								else
								{
									query.append("imp."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("imp."+gpv.getColomnName().toString());
							}
							i++;
							
						}
						query.append(" from improved_add_data AS imp");
						query.append(" LEFT JOIN employee_basic AS emp ON emp.id =  imp.empId ");
						query.append(" LEFT JOIN department AS dept ON dept.id =emp.deptname ");
						query.append(" LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId ");
						query.append(" LEFT JOIN department AS dept2 ON dept2.id =imp.byReview ");
						query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id =  imp.reviewName ");
						query.append(" WHERE moduleName = '"+moduleName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						System.out.println("QUERY IS AS imp :::  "+query.toString());
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
}
