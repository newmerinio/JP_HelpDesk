package com.Over2Cloud.ctrl.asset;

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

@SuppressWarnings("serial")
public class DepreciationAction extends GridPropertyBean
{
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	private List<Object> masterViewList=new ArrayList<Object>();
	
	@SuppressWarnings("unchecked")
	public String beforeViewDepreciation()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if(gdp.getColomnName().equalsIgnoreCase("assetname") || gdp.getColomnName().equalsIgnoreCase("totalamount")
						|| gdp.getColomnName().equalsIgnoreCase("serialno"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
				}
			}
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("depreciation_rate");
			gpv.setHeadingName("Depreciation Charge");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("amount_deducted");
			gpv.setHeadingName("Deducted Amount");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("today_amount");
			gpv.setHeadingName("Current Amount");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);
			
			if(masterViewProp!=null && masterViewProp.size()>0)
			{
				session.put("deprColumnList", masterViewProp);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String ViewDepreciation()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				StringBuilder query1 = new StringBuilder("");
				query.append("select ");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("deprColumnList");
				System.out.println("Field Size is "+fieldNames.size());
				if (session.containsKey("deprColumnList"))
				{
					session.remove("deprColumnList");
				}
				
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView GPV : fieldNames)
					{
						if(GPV.getColomnName().toString().equals("depreciation_rate") || GPV.getColomnName().toString().equals("today_amount") || GPV.getColomnName().toString().equals("amount_deducted"))
						{
							query1.append("ADR."+GPV.getColomnName().toString()+",");
						}
						else
						{
							query1.append("AST."+GPV.getColomnName()+",");
						}
					}
					query.append(query1.substring(0, query1.length()-1));
					query.append(" FROM asset_depreciation_detail AS ADR INNER JOIN asset_detail AS AST ON ADR.assetid=AST.id");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" where ");
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}

					}
					
					List data1 = new ArrayList();
					data1=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data1 != null && data1.size() > 0)
					{
						setRecords(data1.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data1.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							int k = 0;
							for (GridDataPropertyView GPV : fieldNames)
							{
								if (obdata[k] != null)
								{
									if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										tempMap.put(GPV.getColomnName().toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else
									{
										tempMap.put(GPV.getColomnName().toString(), obdata[k].toString());
									}
								}
								else
								{
									tempMap.put(GPV.getColomnName().toString(), "NA");
								}
								k++;
							}
							tempList.add(tempMap);
							
						}
						setMasterViewList(tempList);
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

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}
	
	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}
}
