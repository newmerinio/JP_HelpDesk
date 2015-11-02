package com.Over2Cloud.ctrl.asset.dashboard;

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

public class AssetGridDashboardData extends GridPropertyBean
{
	private static final long serialVersionUID = -2146858401538446397L;
	private String status;
	private List<GridDataPropertyView> masterViewProp=null;
	private 	List<Object> 	masterViewList;
	private String outletId;
	private String feedId;
	
	
	public String  getAssetTicketDetails() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				getGridColumns(status);
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
	private void getGridColumns(String dataFor2) 
	{
		try 
		{
			masterViewProp=new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
		
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_asset_complaint_configuration",accountID,connectionSpace,"",0,"table_name","asset_complaint_configuration");
			for(GridDataPropertyView gdp:returnResult)
			{
				if (status!=null && status.equalsIgnoreCase("Pending")|| status.equalsIgnoreCase("Level1")|| status.equalsIgnoreCase("Level2")|| status.equalsIgnoreCase("Level3")|| status.equalsIgnoreCase("Level4")|| status.equalsIgnoreCase("Level5")) 
				{
					if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("serialno") && !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("category") && !gdp.getColomnName().equals("sub_location")&& !gdp.getColomnName().equals("vendorid")
							&& !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")&& !gdp.getColomnName().equals("ig_time")
							&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time")
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason")&& !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")&& !gdp.getColomnName().equals("ig_reason")
							&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedType")
							&& !gdp.getColomnName().equals("transfer_time")&& !gdp.getColomnName().equals("resolutionTime")&& !gdp.getColomnName().equals("non_working_time")&& !gdp.getColomnName().equals("rca"))
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
				else if (status!=null && status.equalsIgnoreCase("Snooze")) 
				{
					if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("serialno") && !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("category") && !gdp.getColomnName().equals("sub_location")&& !gdp.getColomnName().equals("vendorid")
							&& !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("ig_time")
							&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time") 
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason")
							&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date")  && !gdp.getColomnName().equals("ig_date")&& !gdp.getColomnName().equals("ig_reason")
							&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedType")
							&& !gdp.getColomnName().equals("transfer_time")&& !gdp.getColomnName().equals("resolutionTime")&& !gdp.getColomnName().equals("non_working_time")&& !gdp.getColomnName().equals("rca"))
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
				else if (status!=null && status.equalsIgnoreCase("High Priority")) 
				{
					if(!gdp.getColomnName().equals("uniqueid")&& !gdp.getColomnName().equals("serialno") && !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("category") && !gdp.getColomnName().equals("sub_location")&& !gdp.getColomnName().equals("vendorid")
							&& !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")&& !gdp.getColomnName().equals("ig_time")
							&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date")  && !gdp.getColomnName().equals("sn_upto_time")
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("resolve_by")  && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")&& !gdp.getColomnName().equals("ig_reason")
							&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedType")
							&& !gdp.getColomnName().equals("transfer_time")&& !gdp.getColomnName().equals("resolutionTime")&& !gdp.getColomnName().equals("non_working_time")&& !gdp.getColomnName().equals("rca"))
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
				else if (status!=null && status.equalsIgnoreCase("Ignore")) 
				{
					if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("serialno") && !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("category") && !gdp.getColomnName().equals("sub_location")&& !gdp.getColomnName().equals("vendorid")
							&& !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")
							&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time")
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason")&& !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") 
							&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedType")
							&& !gdp.getColomnName().equals("transfer_time")&& !gdp.getColomnName().equals("resolutionTime")&& !gdp.getColomnName().equals("non_working_time")&& !gdp.getColomnName().equals("rca"))
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
				else if (status!=null && status.equalsIgnoreCase("Resolved")) 
				{
					if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("serialno") && !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("category") && !gdp.getColomnName().equals("sub_location")&& !gdp.getColomnName().equals("vendorid")
							&& !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")&& !gdp.getColomnName().equals("ig_time")
							&& !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time")
							&& !gdp.getColomnName().equals("hp_reason")&& !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")&& !gdp.getColomnName().equals("ig_reason")
							&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedType")
							&& !gdp.getColomnName().equals("transfer_time")&& !gdp.getColomnName().equals("resolutionTime")&& !gdp.getColomnName().equals("non_working_time")&& !gdp.getColomnName().equals("rca"))
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
			}
			session.put("masterViewProp", masterViewProp);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
    
	@SuppressWarnings("rawtypes")
	public String viewGridData() 
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				query1.append("select count(*) from asset_complaint_status");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				
				if(dataCount!=null && dataCount.size()>0)
				{
					@SuppressWarnings("unchecked")
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								if(gpv.getColomnName().equalsIgnoreCase("feedType"))
								{
									query.append("ft.fbType,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("category"))
								{
									query.append("cat.categoryName,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("subCatg"))
								{
									query.append("scat.subCategoryName,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("by_dept"))
								{
									query.append("dept1.deptName AS byDept,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("to_dept"))
								{
									query.append("dept2.deptName AS toDept,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("allot_to"))
								{
									query.append("emp.empName AS allotTo,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("resolve_by"))
								{
									query.append("emp1.empName AS resolveBy,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("asset_id"))
								{
									query.append("ad.assetname ,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("location"))
								{
									query.append("abd.associateName ,");
								}
								else
								{
									query.append("feed."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("feed."+gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM asset_complaint_status AS feed ");
						query.append(" LEFT JOIN feedback_subcategory As scat ON scat.id=feed.subCatg ");
						query.append(" LEFT JOIN feedback_category As cat ON cat.id=scat.categoryName ");
						query.append(" LEFT JOIN feedback_type As ft ON ft.id=cat.fbType ");
						query.append(" LEFT JOIN department As dept1 ON dept1.id=feed.by_dept ");
						query.append(" LEFT JOIN department As dept2 ON dept2.id=feed.to_dept ");
						query.append(" LEFT JOIN employee_basic AS emp ON feed.allot_to= emp.id");
						query.append(" LEFT JOIN asset_detail AS ad ON feed.asset_id= ad.id");
						query.append(" LEFT JOIN associate_basic_data AS abd ON feed.location= abd.id");
						if (status!=null && status.equalsIgnoreCase("Resolved")) 
						{
							query.append(" LEFT JOIN employee_basic AS emp1 ON feed.resolve_by= emp1.id");
						}
						
						query.append(" WHERE ");
						
						if (status!=null && !status.equalsIgnoreCase("Level1") && !status.equalsIgnoreCase("Level2")&& !status.equalsIgnoreCase("Level3")&& !status.equalsIgnoreCase("Level4")&& !status.equalsIgnoreCase("Level5")&& !status.equalsIgnoreCase("Total")&& !status.equalsIgnoreCase("Today")&& !status.equalsIgnoreCase("Category") && !status.equalsIgnoreCase("TotalDept") && !status.equalsIgnoreCase("TodayDept")) 
						{
							query.append("  feed.status='"+status+"'  ");
							
							/*if (type!=null && !type.equalsIgnoreCase("")) 
							{
								query.append(" AND feed.level='"+type+"'");
							}*/
						}
						if (feedId!=null) 
						{
							query.append(" AND feed.id='"+feedId+"'  ");
						}
						if (status!=null && status.equalsIgnoreCase("Level1") || status.equalsIgnoreCase("Level2")|| status.equalsIgnoreCase("Level3")|| status.equalsIgnoreCase("Level4")|| status.equalsIgnoreCase("Level5")) 
						{
							query.append("  feed.level='"+status+"' AND feed.status!='Resolved' AND feed.status!='Ignore'  ");
						}
						if (outletId!=null && !outletId.equalsIgnoreCase("All") && !outletId.equalsIgnoreCase("")) 
						{
							query.append(" AND feed.location ='"+outletId+"'  ");
						}
						
						System.out.println("QUERY IS AS :::   "+query.toString());
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						if(data!=null && data.size()>0)
						{
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	
}
