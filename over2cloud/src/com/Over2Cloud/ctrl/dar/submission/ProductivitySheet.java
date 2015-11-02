package com.Over2Cloud.ctrl.dar.submission;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.dar.task.GenericWriteExcel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProductivitySheet extends ActionSupport 
{
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ProductivitySheet.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private Integer rows = 0;
	private String idproduct;
	private String idtodate;
	private String idto;
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
	private String downloadType=null;
	private String download4=null;
	private Map<String, String> columnMap=null;
	private List<ConfigurationUtilBean> columnMap2=null;
	private String[] columnNames;
	private String tableName=null;
	private String excelName=null;
	private String mainHeaderName;
	private boolean loadonce = false;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private List<Object> viewList;

	private List<GridDataPropertyView>taskTypeViewProp=new ArrayList<GridDataPropertyView>();
	
	 @SuppressWarnings("unchecked")
	public void setTaskViewProp()
	 {
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		taskTypeViewProp.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName2=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
			if(statusColName2!=null&&statusColName2.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName2)
				{
					if (gdp.getColomnName().equalsIgnoreCase("tasknamee") || gdp.getColomnName().equalsIgnoreCase("allotedto") || gdp.getColomnName().equalsIgnoreCase("intiation")
							|| gdp.getColomnName().equalsIgnoreCase("completion")) 
					{
							gpv=new GridDataPropertyView();
							gpv.setColomnName("b."+gdp.getColomnName());
				            gpv.setHeadingName(gdp.getHeadingName());
				            gpv.setHideOrShow(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setWidth(gdp.getWidth());
							taskTypeViewProp.add(gpv);
					}
				}
			}
		List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_submission_configuration");
		if(statusColName!=null&&statusColName.size()>0 )
		{
			for(GridDataPropertyView gdp:statusColName)
			{
				if (gdp.getColomnName().equalsIgnoreCase("statuss") || gdp.getColomnName().equalsIgnoreCase("otherworkk") || gdp.getColomnName().equalsIgnoreCase("compercentage") || 
						gdp.getColomnName().equalsIgnoreCase("attachmentt") || gdp.getColomnName().equalsIgnoreCase("actiontaken") || 
						gdp.getColomnName().equalsIgnoreCase("tommorow") || gdp.getColomnName().equalsIgnoreCase("today")) 
					{
							gpv=new GridDataPropertyView();
							gpv.setColomnName("a."+gdp.getColomnName());
							gpv.setHideOrShow(gdp.getHideOrShow());
						    gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setWidth(gdp.getWidth());
							taskTypeViewProp.add(gpv);
					}
			}
		}
		if (taskTypeViewProp!=null && taskTypeViewProp.size()>0) 
		{
			session.put("productivityColumnList", taskTypeViewProp);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	 
	 public String beforeDarViewtask()
		{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
			 		setTaskViewProp();
			 		setMainHeaderName("Productivity Sheet >> View");
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
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String viewProductivityRegisOperation()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					StringBuilder query=new StringBuilder("");
					if(getIdproduct()!=null && getIdtodate()!=null && getIdto()!=null)
					{
					query.append("SELECT COUNT(*) FROM dar_submission WHERE date between '"+DateUtil.convertDateToUSFormat(getIdto())+"' and '"+DateUtil.convertDateToUSFormat(getIdtodate())+"' and tasknameee IN(select id from task_registration where allotedto='"+getIdproduct()+"')");
					}
					List  dataCount1 = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(dataCount1!=null && dataCount1.size()>0)
					{
						BigInteger count = new BigInteger("1");
					for(Iterator it=dataCount1.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count = (BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					
					StringBuilder query1 = new StringBuilder("");
					if(getIdproduct()!= null && getIdtodate()!= null && getIdto()!= null)
					{
					    query1.append("SELECT a.id,b.tasknamee,emp.empName,b.intiation,b.completion, "+
					    		"a.statuss,a.otherworkk,a.compercentage,a.attachmentt,a.actiontaken , "+
					    		"a.tommorow,a.today FROM  dar_submission  a "+ 
					    		"INNER JOIN task_registration as b on b.id= a.tasknameee  "+
					    		"INNER JOIN employee_basic as emp on emp.id=b.allotedto "+
					    		"WHERE  a.date between '"+DateUtil.convertDateToUSFormat(getIdto().toString())+"' and '"+DateUtil.convertDateToUSFormat(getIdtodate().toString())+"' AND b.allotedto='"+getIdproduct()+"'");
					}
					if (getIdproduct()!=null && getIdtodate()==null && getIdto()==null) 
					{
						query1.append("SELECT a.id,b.tasknamee,emp.empName,b.intiation,b.completion, "+
					    		"a.statuss,a.otherworkk,a.compercentage,a.attachmentt,a.actiontaken , "+
					    		"a.tommorow,a.today FROM  dar_submission  a "+ 
					    		"INNER JOIN task_registration as b on b.id= a.tasknameee  "+
					    		"INNER JOIN employee_basic as emp on emp.id=b.allotedto "+
								"WHERE  b.allotedto='"+getIdproduct()+"'");
					}
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query1.append(" and ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query1.append(" a."+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query1.append(" a."+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query1.append(" a."+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query1.append(" a."+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query1.append(" a."+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					List<GridDataPropertyView> fieldNames=null;
					if (session.containsKey("productivityColumnList")) 
					{
						fieldNames=(List<GridDataPropertyView>) session.get("productivityColumnList");
					}
						if(dataCount!=null && dataCount.size()>0)
						  {
							viewList=new ArrayList<Object>();
							List<Object> Listhb=new ArrayList<Object>();
							Object[] obdata=null;
							for(Iterator it=dataCount.iterator(); it.hasNext();)
							{
								obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								int k=0;
								for (GridDataPropertyView gdp:fieldNames)
								{
									if(obdata[k]!=null)
									{
										if(k==0)
										{
											one.put(gdp.getColomnName().toString(), (Integer)obdata[k]);
										}
										else if(gdp.getColomnName().toString().equalsIgnoreCase("b.intiation") || gdp.getColomnName().toString().equalsIgnoreCase("b.completion"))
										{
											one.put(gdp.getColomnName().toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else
										{
											one.put(gdp.getColomnName().toString(), obdata[k].toString());
										}
									}
									else 
									{
									    one.put(gdp.getColomnName().toString(), "NA");
									}
									k++;
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
		
	public String getColumn4Download()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(download4!=null && download4.equals("taskRegistration"))
				{
					returnResult=getColumnName("mapped_dar_configuration","dar_configuration","taskRegistration");
					tableName="dar_submission";
					excelName="Task detail";
				}
				if(download4!=null && download4.equals("darSubmission"))
				{
					returnResult=getColumnName("mapped_dar_configuration","dar_submission_configuration","darSubmission");
					tableName="dar_submission";
					excelName="DAR detail";
				}
				if(download4!=null && download4.equals("productivity"))
				{
					returnResult=getColumnName("mapped_dar_configuration","dar_submission_configuration","productivity");
					tableName="dar_submission";
					excelName="Productivity detail";
				}
		
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getColumn4Download of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName,String basicTableName,String a)
	{
			String returnResult=ERROR;
			try
			{
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List<GridDataPropertyView> columnList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
				columnMap=new LinkedHashMap<String, String>();
				columnMap2=new ArrayList<ConfigurationUtilBean>();
				if(columnList!=null&&columnList.size()>0)
				{
					ConfigurationUtilBean obj=null;
					for(GridDataPropertyView  gdp1:columnList)
					{
						obj=new ConfigurationUtilBean();
						if (a.equalsIgnoreCase("taskRegistration")) 
						{
						   if(!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("time") && !gdp1.getColomnName().equals("date"))
							{
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
						}
						if (a.equalsIgnoreCase("darSubmission")) 
						{
						   if(!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("time") && !gdp1.getColomnName().equals("date")
								 && !gdp1.getColomnName().equals("snooze") && !gdp1.getColomnName().equals("status1") && !gdp1.getColomnName().equals("reason1")
								 && !gdp1.getColomnName().equals("status2")  && !gdp1.getColomnName().equals("reason2"))
							{
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
						}
					}
					if (a.equalsIgnoreCase("productivity")) 
					{
						List<GridDataPropertyView> data=null;
						if (session.containsKey("productivityColumnList")) 
						{
							data = (List<GridDataPropertyView>) session.get("productivityColumnList");
						    session.remove("productivityColumnList");
						}
						for (GridDataPropertyView gdp : data) 
						{
							if (!gdp.getColomnName().equalsIgnoreCase("id")) 
							{
								ConfigurationUtilBean obj1 = new ConfigurationUtilBean(); 
								obj1.setKey(gdp.getColomnName());
								obj1.setValue(gdp.getHeadingName());
								columnMap2.add(obj1);
								columnMap.put(gdp.getColomnName(), gdp.getHeadingName());
							}
						}
					}
					if(columnMap!=null && columnMap.size()>0)
					{
						session.put("columnMap", columnMap);
					}
					returnResult=SUCCESS;
				}
				}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getColumnName of class "+getClass(), e);
				e.printStackTrace();
			}
			return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
	System.out.println("cxncvnnv       fgdfgd");
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				List keyList=new ArrayList();
				List titleList=new ArrayList();
				String userName=(String)session.get("uName");
				if(columnNames!=null && columnNames.length>0)
				{
					keyList=Arrays.asList(columnNames);
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					Map<String, String> tempMap=new LinkedHashMap<String, String>();
					tempMap=(Map<String, String>) session.get("columnMap");
					List dataList=null;
					StringBuilder query = new StringBuilder("");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					for(int index=0;index<keyList.size();index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
				    if(downloadType!=null && downloadType.equals("uploadExcel"))
					{
						returnResult=new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.",excelName, dataList, titleList, null, excelName);
					}
				    else
				    {
				    	if (download4.equalsIgnoreCase("taskRegistration")) 
				    	{
				    		query.append("SELECT ");
				    		System.out.println("querynnnnnnnnnn" +query);
							int i = 0;
							Object obdata=null;
							for (Iterator it = keyList.iterator(); it.hasNext();)
							{
								obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < keyList.size() - 1)
									{
										if (obdata.toString().equals("tasktype")) 
										{
											query.append(" ty.tasktype " + ",");
										}
										else if (obdata.toString().equals("allotedby")) 
										{
											query.append(" emp1.empName as allotedby " + ",");
										}
										else if (obdata.toString().equals("allotedto")) 
										{
											query.append(" emp2.empName as allotedto " + ",");
										}
										else if (obdata.toString().equals("clientfor")) 
										{
											query.append(" ct.companyy " + ",");
										}
										else if (obdata.toString().equals("guidance")) 
										{
											query.append(" emp3.empName as guidance " + ",");
										}
										else if (obdata.toString().equals("project")) 
										{
											query.append(" emp6.name as project1 " + ",");
										}
										else if (obdata.toString().equals("validate_By_1")) 
										{
											query.append(" emp4.name as validateby1 " + ",");
										}
										else if (obdata.toString().equals("validate_By_2")) 
										{
											query.append(" emp5.empName as validateby2 " + ",");
										}
										else 
										{
											query.append(obdata.toString() + ",");
										}
									}
									else
									{
										if (obdata.toString().equals("tasktype")) 
										{
											query.append(" ty.tasktype " );
										}
										else if (obdata.toString().equals("allotedby")) 
										{
											query.append(" emp1.empName as allotedby ");
										}
										else if (obdata.toString().equals("allotedto")) 
										{
											query.append(" emp2.empName as allotedto ");
										}
										else if (obdata.toString().equals("clientfor")) 
										{
											query.append(" ct.companyy ");
										}
										else if (obdata.toString().equals("guidance")) 
										{
											query.append(" emp3.empName as guidance ");
										}
										else if (obdata.toString().equals("project")) 
										{
											query.append(" emp6.name as project1 ");
										}
										else if (obdata.toString().equals("validate_By_1")) 
										{
											query.append(" emp4.name as validateby1 ");
										}
										else if (obdata.toString().equals("validate_By_2")) 
										{
											query.append(" emp5.empName as validateby2 ");
										}
										else 
										{
											query.append(obdata.toString());
										}
									}
								}
								i++;
							}
							query.append(" FROM task_registration a");
							query.append(" INNER JOIN task_type ty on a.tasktype= ty.id ");
							query.append(" INNER JOIN employee_basic emp1 on a.allotedby= emp1.id ");
							query.append(" INNER JOIN employee_basic emp2 on a.allotedto= emp2.id  ");
							query.append(" INNER JOIN client_type ct on a.clientfor= ct.id  ");
							query.append(" INNER JOIN employee_basic emp3 on a.guidance= emp3.id  ");
							query.append(" INNER JOIN project_management emp6 on a.project= emp6.id  ");
							query.append(" INNER JOIN project_management emp4 on a.validate_By_1= emp4.id  ");
							query.append(" INNER JOIN employee_basic emp5 on a.validate_By_2= emp5.id  ");
							query.append("WHERE a.username='"+userName+"'"); 
							dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							System.out.println("dataList"  +dataList);
							if (dataList != null && dataList.size() > 0)
							{
								excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
								if (session.containsKey("columnMap"))
								{
									session.remove("columnMap");
								}
							}
						}
				      	if (download4.equalsIgnoreCase("darSubmission")) 
				    	{
				    		query.append("SELECT ");
							int i = 0;
							Object obdata=null;
							for (Iterator it = keyList.iterator(); it.hasNext();)
							{
								obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < keyList.size() - 1)
									{
										if (obdata.toString().equals("tasknameee")) 
										{
											query.append(" b.tasknamee " + ",");
										}
										else if(obdata.toString().equals("allotedbyy")) 
										{
											query.append(" emp.empName as allotedby " + ",");
										}
										else if(obdata.toString().equals("allotedtoo")) 
										{
											query.append(" emp1.empName as allotedto " + ",");
										}
										else if(obdata.toString().equals("initiondate")) 
										{
											query.append(" b.intiation " + ",");
										}
										else if(obdata.toString().equals("comlpetiondate")) 
										{
											query.append(" b.completion " + ",");
										}
										else if(obdata.toString().equals("guidancee")) 
										{
											query.append(" emp2.empName as guidance " + ",");
										}
										else 
										{
											query.append(obdata.toString() + ",");
										}
									}
									else
									{
										if (obdata.toString().equals("tasknameee")) 
										{
											query.append(" b.tasknamee " );
										}
										else if(obdata.toString().equals("allotedbyy")) 
										{
											query.append(" emp.empName as allotedby " );
										}
										else if(obdata.toString().equals("allotedtoo")) 
										{
											query.append(" emp1.empName as allotedto " );
										}
										else if(obdata.toString().equals("initiondate")) 
										{
											query.append(" b.intiation " );
										}
										else if(obdata.toString().equals("comlpetiondate")) 
										{
											query.append(" b.completion " );
										}
										else if(obdata.toString().equals("guidancee")) 
										{
											query.append(" emp2.empName as guidance " );
										}
										else 
										{
											query.append(obdata.toString());
										}
									}
								}
								i++;
							}
							query.append(" FROM dar_submission a");
							query.append(" INNER JOIN task_registration b on a.tasknameee=b.id  ");
							query.append(" INNER JOIN employee_basic as emp on b.allotedby=emp.id ");
							query.append(" INNER JOIN employee_basic as emp1 on b.allotedto=emp1.id  ");
							query.append(" INNER JOIN employee_basic as emp2 on b.guidance=emp2.id   ");
							query.append("WHERE a.username ='"+userName+"'"); 
							dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
								if (session.containsKey("columnMap"))
								{
									session.remove("columnMap");
								}
							}
						}
				      	if (download4.equalsIgnoreCase("productivity")) 
					    {
					    		query.append("SELECT ");
								int i = 0;
								Object obdata=null;
								for (Iterator it = keyList.iterator(); it.hasNext();)
								{
									obdata = (Object) it.next();
									if (obdata != null)
									{
										if (i < keyList.size() - 1)
										{
											if (obdata.toString().equals("b.allotedto")) 
											{
												query.append(" emp.empName " + ",");
											}
											else 
											{
												query.append(obdata.toString() + ",");
											}
										}
										else
										{
											if (obdata.toString().equals("b.allotedto")) 
											{
												query.append(" emp.empName " );
											}
											else 
											{
												query.append(obdata.toString());
											}
										}
									}
									i++;
								}
								query.append(" FROM dar_submission a");
								query.append(" INNER JOIN task_registration as b on b.id= a.tasknameee  ");
								query.append(" INNER JOIN employee_basic as emp on emp.id=b.allotedto ");
								
								System.out.println("idto" +idto);
								System.out.println("idproduct" +idproduct);
								System.out.println("idtodate" +idtodate);
								
								if(getIdproduct()!= null && getIdtodate()!= null && getIdto()!= null)
								{
									query.append("WHERE  a.date between '"+DateUtil.convertDateToUSFormat(getIdto().toString())+"' and '"+DateUtil.convertDateToUSFormat(getIdtodate().toString())+"' AND b.allotedto='"+getIdproduct()+"'"); 
								}
								if (getIdproduct()!=null && getIdtodate()==null && getIdto()==null) 
								{
									query.append("WHERE  b.allotedto='"+getIdproduct()+"'"); 
								}
								dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if (dataList != null && dataList.size() > 0)
								{
									excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
									if (session.containsKey("columnMap"))
									{
										session.remove("columnMap");
									}
								}
						}
				    }
				    if(excelFileName!=null)
					{
						excelStream=new FileInputStream(excelFileName);
					}
					returnResult=SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method downloadExcel of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
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
	public List<GridDataPropertyView> getTaskTypeViewProp() {
		return taskTypeViewProp;
	}
	public void setTaskTypeViewProp(List<GridDataPropertyView> taskTypeViewProp) {
		this.taskTypeViewProp = taskTypeViewProp;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public List<Object> getViewList() {
		return viewList;
	}
	public void setViewList(List<Object> viewList) {
		this.viewList = viewList;
	}
	public String getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(String idproduct) {
		this.idproduct = idproduct;
	}
	public String getIdtodate() {
		return idtodate;
	}
	public void setIdtodate(String idtodate) {
		this.idtodate = idtodate;
	}
	public String getIdto() {
		return idto;
	}
	public void setIdto(String idto) {
		this.idto = idto;
	}
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public String getDownload4() {
		return download4;
	}
	public void setDownload4(String download4) {
		this.download4 = download4;
	}
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	public List<ConfigurationUtilBean> getColumnMap2() {
		return columnMap2;
	}
	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2) {
		this.columnMap2 = columnMap2;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public FileInputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
