package com.Over2Cloud.ctrl.rcaMaster;

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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;

public class RCAMasterAction extends GridPropertyBean implements ServletRequestAware
{
    private static final long serialVersionUID = 1865884663589330860L;
	private LinkedHashMap<Integer, String> listDept=null;
	private LinkedHashMap<Integer, String> listCategory;
	private String moduleName;
	private List<ConfigurationUtilBean> rcaDropDown;
	private List<ConfigurationUtilBean> rcaTextField;
	private HttpServletRequest request;
	private List<GridDataPropertyView>rcaMasterViewProp=null;
	private List<Object> viewList;
	private String category;
	private JSONArray jsonArr;
    
	@SuppressWarnings("rawtypes")
    public String beforeRcaPage()
    {
		if (ValidateSession.checkSession())
        {
	        try
            {
				listDept = new LinkedHashMap<Integer, String>();
				listCategory=new LinkedHashMap<Integer, String>();
				RCAMasterHelper RCA=new RCAMasterHelper();
				List deptList=RCA.getDeptList(connectionSpace);
				Object[] object=null;
				if (deptList!=null && deptList.size()>0) 
				{
					for (Iterator iterator = deptList.iterator(); iterator.hasNext();) 
					{
					    object = (Object[]) iterator.next();
					    listDept.put(Integer.parseInt(object[0].toString()), object[1].toString());
					}
				}
				List categoryList=RCA.getCategory(moduleName,connectionSpace);
				if (categoryList!=null && categoryList.size()>0)
                {
	                for (Iterator iterator = categoryList.iterator(); iterator.hasNext();)
                    {
	                	 object = (Object[]) iterator.next();
	                	 listCategory.put(Integer.parseInt(object[0].toString()), object[1].toString());
                    }
                }
				List<GridDataPropertyView> columnList=Configuration.getConfigurationData("mapped_rca_master_configuration",accountID,connectionSpace,"",0,"table_name","rca_master_configuration");
				rcaDropDown=new ArrayList<ConfigurationUtilBean>();
				rcaTextField=new ArrayList<ConfigurationUtilBean>();
				if(columnList!=null && columnList.size()>0)
				{
					ConfigurationUtilBean obj=null;
					for(GridDataPropertyView  gdp : columnList)
					{
						obj=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time")&& !gdp.getColomnName().equalsIgnoreCase("moduleName"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if(gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							rcaTextField.add(obj);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if(gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							rcaDropDown.add(obj);
						}
					}
				}
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String rcaMasterAdd()
    {
	    if (ValidateSession.checkSession())
        {
	        try
            {
	        	boolean status=false;
			    if(userName==null || userName.equalsIgnoreCase(""))
				   return LOGIN;
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
			    List<GridDataPropertyView> org2=Configuration.getConfigurationData("mapped_rca_master_configuration",accountID,connectionSpace,"",0,"table_name","rca_master_configuration");
				if(org2!=null)
				{
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:org2)
					{
						 ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 Tablecolumesaaa.add(ob1);
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					}
					 cbt.createTable22("rca_master",Tablecolumesaaa,connectionSpace);
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator();
					 while (it.hasNext()) 
					 {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
						{
							 ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 ob.setDataName(paramValue);
							 insertData.add(ob);
						}
					 }
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(userName);
						 insertData.add(ob);
					 }
					 if(dateTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("date");
						 ob.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertData.add(ob);
					 }
					 if(timeTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("time");
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 if(insertData.size()>0)
						 status=cbt.insertIntoTable("rca_master",insertData,connectionSpace);
					 
				}
				if(status)
				{
					 addActionMessage("RCA Registered Successfully!!!");
					 return SUCCESS;
				}
				else
				{
					 addActionMessage("Oops There is some error in data!");
					 return SUCCESS;
				}
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
	public String beforeRCAMasterView()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
		
				setRCAViewProp();
				returnResult=SUCCESS;		
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	private void setRCAViewProp()
    {
		rcaMasterViewProp=new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		rcaMasterViewProp.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_rca_master_configuration",accountID,connectionSpace,"",0,"table_name","rca_master_configuration");
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					rcaMasterViewProp.add(gpv);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	public String viewRcaMaster()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from rca_master");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					Object obdata=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					DARReportHelper DAR=new DARReportHelper();
	            	String str[]=DAR.getOfferingName(connectionSpace);
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_rca_master_configuration", accountID, connectionSpace, "rca_master_configuration");
					int i=0;
					if(fieldNames!=null&&fieldNames.size()>0)
					{
						Object obdata1=null;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
						    obdata1=(Object)it.next();
						    if(obdata1!=null)
						    {
							    if(i<fieldNames.size()-1)
							    	if (obdata1.toString().equalsIgnoreCase("deptName")) 
							    	{
							    		query.append("dept.deptName,"); 
									}
							    	else if(obdata1.toString().equalsIgnoreCase("category"))
							    	{
							    		if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
							            {
								            query.append("at.assetSubCat, ");
							            }
							            else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM") || moduleName!=null && moduleName.equalsIgnoreCase("FM"))
							            {
											query.append(" fc.categoryName, ");
										}
							            else if(moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM"))
							            {
											query.append(" aa."+str[0]+",  ");
										}
							    	}
							    	else
                                    {
                                    	query.append("rca."+obdata1.toString()+",");
                                    }
							    else
							    	if (obdata1.toString().equalsIgnoreCase("subCategory"))
                                    {
							    		if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM") || moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM"))
							            {
							            	query.append(" cat.categoryName  ");
							            }
							            else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM") || moduleName!=null && moduleName.equalsIgnoreCase("FM"))
							            {
											query.append(" subcat.subCategoryName  ");
										}
                                    } 
							    	else
                                    {
							    		query.append("rca."+obdata1.toString());
                                    }
							    	
						    	}
						    i++;
						}
					}
					query.append(" FROM rca_master AS rca ");
					query.append(" INNER JOIN department AS dept ON dept.id=rca.deptName ");
					if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
                    {
						query.append(" INNER JOIN createasset_type_master AS at ON at.id=rca.category ");
                    }
					else if (moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM")) 
					{
						query.append(" INNER JOIN "+str[1]+" AS aa ON aa.id=rca.category ");
					}
					else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM") || moduleName!=null && moduleName.equalsIgnoreCase("FM"))
		            {
						query.append(" INNER JOIN feedback_category AS fc ON fc.id=rca.category ");
						query.append(" INNER JOIN feedback_subcategory AS scat ON scat.id=rca.subCategory ");
						query.append(" INNER JOIN feedback_category AS cat ON cat.id=scat.categoryName ");
						query.append(" INNER JOIN feedback_type AS ft ON ft.id=cat.fbType ");
		            }
					if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM") || moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM"))
		            {
						query.append(" INNER JOIN feedback_type AS ft ON ft.dept_subdept=rca.category ");
		            	query.append(" INNER JOIN feedback_category AS cat ON cat.fbType=ft.id ");
		            }
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					else if(getSord()!=null && getSidx()!=null && !getSord().equalsIgnoreCase("") && !getSidx().equalsIgnoreCase(""))
					{
						query.append(" ORDER BY "+getSidx()+" "+getSord()+"");
					}
					System.out.println("  QUERY IS AS ::::    "+query.toString());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			
					if(data!=null && data.size()>0)
					{
						viewList=new ArrayList<Object>();
						List<Object> Listhb=new ArrayList<Object>();
						Object[] obdata1=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata1[k]!=null)
								{
										if(k==0)
											one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
										else
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	
	}
	@SuppressWarnings("rawtypes")
    public String subCategoryData()
    {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				RCAMasterHelper RCA=new RCAMasterHelper();
				List data2 = RCA.getSubCategory(moduleName,category, connectionSpace);
				if (data2 != null && data2.size() > 0)
				{
					jsonArr=new JSONArray();
					JSONObject formDetailsJson = new JSONObject();
					Object[] object = null;
					for (Iterator iterator = data2.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", object[0].toString());
							formDetailsJson.put("NAME", object[1].toString());
							jsonArr.add(formDetailsJson);
						}
					}
				}
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
	public LinkedHashMap<Integer, String> getListDept()
	{
		return listDept;
	}

	public void setListDept(LinkedHashMap<Integer, String> listDept)
	{
		this.listDept = listDept;
	}

	public LinkedHashMap<Integer, String> getListCategory()
	{
		return listCategory;
	}

	public void setListCategory(LinkedHashMap<Integer, String> listCategory)
	{
		this.listCategory = listCategory;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public List<ConfigurationUtilBean> getRcaDropDown()
	{
		return rcaDropDown;
	}

	public void setRcaDropDown(List<ConfigurationUtilBean> rcaDropDown)
	{
		this.rcaDropDown = rcaDropDown;
	}

	public List<ConfigurationUtilBean> getRcaTextField()
	{
		return rcaTextField;
	}

	public void setRcaTextField(List<ConfigurationUtilBean> rcaTextField)
	{
		this.rcaTextField = rcaTextField;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public List<GridDataPropertyView> getRcaMasterViewProp()
	{
		return rcaMasterViewProp;
	}
	public void setRcaMasterViewProp(List<GridDataPropertyView> rcaMasterViewProp)
	{
		this.rcaMasterViewProp = rcaMasterViewProp;
	}
	public List<Object> getViewList()
	{
		return viewList;
	}
	public void setViewList(List<Object> viewList)
	{
		this.viewList = viewList;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public JSONArray getJsonArr()
	{
		return jsonArr;
	}
	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}
	
}
