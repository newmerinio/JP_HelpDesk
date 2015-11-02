package com.Over2Cloud.ctrl.compliance.complContacts;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

@SuppressWarnings("serial")
public class ComplianceCountryAction extends GridPropertyBean  implements ServletRequestAware {

	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	
	private List<Object> masterViewList;
	static final Log log = LogFactory.getLog(ComplianceCountryAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> bandDropdown;
	private List<ConfigurationUtilBean> bandTextBox;
	private Map<String, String> deptNameMap;

	public String beforeMapBand()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String query = "SELECT id,deptName FROM department WHERE flag='Active' ORDER BY deptName";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					deptNameMap = new LinkedHashMap<String, String>();
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptNameMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
				/*setMapPageDataFields();*/
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in KRA KPI  method beforeBandMap of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	
	public String beforeEmployeeBandView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewGridColumns();
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Band  method beforeBandHeaderView of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} 
		else
		{
			return LOGIN;
		}
	}
	
	public void setViewGridColumns()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_country_master", accountID, connectionSpace, "", 0, "table_name", "country_office_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setWidth(gdp.getWidth());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}

	public String viewCountry()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from country_office");
				List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("SELECT ");
					List<?> fieldNames = Configuration.getColomnList("mapped_country_master", accountID, connectionSpace, "country_office_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator<?> it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields

						obdata = (Object) it.next();
						if (obdata != null )
						{
							/*if (obdata.equals("departName"))
							{
								query1.append("dept.deptName,");
							} 
							else if (i < fieldNames.size() - 1)
							{
								query1.append("band." + obdata.toString() + ",");
							} 
							else
							{*/
								query1.append( obdata.toString() + ",");
							/*}*/
						}
						i++;
					}
					query.append(query1.substring(0, query1.toString().length() - 1));
					query.append(" FROM country_office");
					query.append(" WHERE id!=0");
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					List<?> data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata11 = null;
						for (Iterator<?> it = data.iterator(); it.hasNext();)
						{
							obdata11 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase(""))
								{
									if (fieldNames.get(k).toString().equals("created_date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
									} else if (fieldNames.get(k).toString().equals("user_name"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata11[k].toString()));
									} else
									{
										one.put(fieldNames.get(k).toString(), obdata11[k].toString());
									}
								} else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewBandData of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeBandHeaderView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Band  method beforeBandHeaderView of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	/**
	 * View Grid Column Name
	 */
	public void setViewProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_band_configuration", accountID, connectionSpace, "", 0, "table_name", "band_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setWidth(gdp.getWidth());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}

	public String beforeCountryAdd()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				setAddPageDataFields();
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in KRA KPI  method beforeBandAdd of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	/**
	 * Showing Fields of Add Page
	 */
	public void setAddPageDataFields()
	{

		
			try
			{
				List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_country_master", accountID, connectionSpace, "", 0, "table_name", "country_office_configuration");
				if (gridFields != null)
				{
				/*	bandDropdown = new ArrayList<ConfigurationUtilBean>();*/
					bandTextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : gridFields)
					{
						ConfigurationUtilBean objdata = new ConfigurationUtilBean();
						 if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().trim().equalsIgnoreCase("user_name") && !gdp.getColomnName().trim().equalsIgnoreCase("created_date") && !gdp.getColomnName().trim().equalsIgnoreCase("created_time"))
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							objdata.setData_type(gdp.getData_type());
							objdata.setField_length(gdp.getLength());
							bandTextBox.add(objdata);
						}
					}
				}
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
	
	}

	@SuppressWarnings("unchecked")
	public String addCountry()
	{
		String result = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				result = LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			int count=0;
			String query = " select country_name from country_office where country_name='" + request.getParameter("country_name") + "'";
			List<?> list = cbt.executeAllSelectQuery(query, connectionSpace);
			if (list != null && list.size() > 0)
			{
				addActionError(" Oops " + request.getParameter("country_name") + " Already Exists !!!");
				result = SUCCESS;
			} 
			else
			{
				TableColumes ob1 = null;
				List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_country_master", accountID, connectionSpace, "", 0, "table_name", "country_office_configuration");
				if (org2 != null && org2.size()>0)
				{
					// create table query based on configuration
					for (GridDataPropertyView gdp : org2)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						if(gdp.getColomnName().equalsIgnoreCase("status"))
						{
							ob1.setConstraint("default 'Active'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("country_office", Tablecolumesaaa, connectionSpace);

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator<String> it = requestParameterNames.iterator();
					InsertDataTable ob = null;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();// control Name
						if (Parmname != null && !Parmname.trim().equals(""))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
							insertData.add(ob);
						}
					}

					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("created_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("created_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				
					cbt.insertIntoTable("country_office", insertData, connectionSpace);
					count++;
					

					if (count > 0)
					{
						addActionMessage(count + "Country Saved Successfully!!!");
						result = SUCCESS;
					} else
					{
						addActionMessage("Oops!!! There is some error in data.");
						result = SUCCESS;
					}
				}}
		} catch (Exception e)
		{
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public String modifyCountryAction()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
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
						if (paramValue != null && Parmname != null&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							wherClause.put(Parmname, paramValue);
							if (Parmname.equalsIgnoreCase("status"))
							{
								wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("country_office", wherClause,condtnBlock, connectionSpace);

            		StringBuilder fieldsNames=new StringBuilder();
            		StringBuilder dataStore=new StringBuilder();
            		StringBuilder dataField=new StringBuilder();
            		if (wherClause!=null && !wherClause.isEmpty())
					{
						int i=1;
						for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
						{
						    if (i < wherClause.size())
								fieldsNames.append("'"+entry.getKey() + "', ");
							else
								fieldsNames.append("'"+entry.getKey() + "' ");
							i++;
						}
					}
            		UserHistoryAction UA=new UserHistoryAction();
            		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"country_office_configuration");
            		if (fieldValue!=null && fieldValue.size()>0)
					{
            			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (wherClause!=null && !wherClause.isEmpty())
							{
								int i=1;
								for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
								{
									if (object[1].toString().equalsIgnoreCase(entry.getKey()))
									{
										  if (i < fieldValue.size())
										  {
											  dataStore.append(entry.getValue() + ", ");
										      dataField.append(object[0].toString() +", ");
										  }
										  else
										  {
											  dataStore.append(entry.getValue());
										      dataField.append(object[0].toString());
										  }
									}
									i++;
								}
							}
						}
            			
            			
            			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
            			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Country Office",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
            		
				
				}
			    else if (getOper().equalsIgnoreCase("del"))
			   {
					 CommonOperInterface cbt = new CommonConFactory().createInterface(); 
					 String tempIds[] = getId().split(",");
					 StringBuilder condtIds = new StringBuilder(); 
					 Map<String, Object> wherClause = new HashMap<String, Object>();
					 Map<String, Object> condtnBlock = new HashMap<String, Object>();
					 int i = 1; 
					 for(String H : tempIds) 
					 { 
						 if (i < tempIds.length)
					          condtIds.append(H + " ,"); 
						 else 
							  condtIds.append(H); 
						 i++; 
					 }
					 wherClause.put("status","Inactive");
					 wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
					 condtnBlock.put("id", condtIds.toString());
					 cbt.updateTableColomn("country_office", wherClause,condtnBlock, connectionSpace);
					 StringBuilder fieldsNames=new StringBuilder();
					 StringBuilder dataStore=new StringBuilder();
                		StringBuilder dataField=new StringBuilder();
                		if (wherClause!=null && !wherClause.isEmpty())
						{
							 i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i++;
							}
						}
                		UserHistoryAction UA=new UserHistoryAction();
                		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"country_office_configuration");
                		if (fieldValue!=null && fieldValue.size()>0)
						{
                			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (wherClause!=null && !wherClause.isEmpty())
								{
									 i=1;
									for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
									{
										if (object[1].toString().equalsIgnoreCase(entry.getKey()))
										{
											  if (i < fieldValue.size())
											  {
												  dataStore.append(entry.getValue() + ", ");
											      dataField.append(object[0].toString() +", ");
											  }
											  else
											  {
												  dataStore.append(entry.getValue());
											      dataField.append(object[0].toString());
											  }
										}
										i++;
									}
								}
							}
                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Country Office", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
			   }
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}


	public List<ConfigurationUtilBean> getBandDropdown()
	{
		return bandDropdown;
	}

	public void setBandDropdown(List<ConfigurationUtilBean> bandDropdown)
	{
		this.bandDropdown = bandDropdown;
	}

	public List<ConfigurationUtilBean> getBandTextBox()
	{
		return bandTextBox;
	}

	public void setBandTextBox(List<ConfigurationUtilBean> bandTextBox)
	{
		this.bandTextBox = bandTextBox;
	}

	public Map<String, String> getDeptNameMap()
	{
		return deptNameMap;
	}

	public void setDeptNameMap(Map<String, String> deptNameMap)
	{
		this.deptNameMap = deptNameMap;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}
	


}
