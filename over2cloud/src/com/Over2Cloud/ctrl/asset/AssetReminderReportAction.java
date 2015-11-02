package com.Over2Cloud.ctrl.asset;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceEditGridAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetReminderReportAction extends ActionSupport
{
	static final Log log = LogFactory.getLog(AssetReminderReportAction.class);
	Map session = ActionContext.getContext().getSession();
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String userName=(String)session.get("uName");
	String accountID = (String) session.get("accountid");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public File uploadDoc;
	private String uploadDocContentType;
	private String uploadDocFileName;
	public File uploadDoc1;
	private String uploadDoc1ContentType;
	private String uploadDoc1FileName;
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
	// private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private Map<String, String> outletName;
	private String fromDate;
	private String toDate;
	private String minCount;
	private String maxCount;
	private String searchPeriodOn;
	private String frequency;
	private InputStream fileInputStream;
	private String fileName;
	private String documentName;
	private String outletId;
	private String actionStatus;
	private String remiderReportId;
	private Map<String, String> docMap;
	
	@SuppressWarnings("unchecked")
	public String beforeViewAssetReminderReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userEmpID=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				outletName=new LinkedHashMap<String, String>();
				outletName.put("All", "All Outlet");
				List outletNameList = getMappedOutletId(userEmpID);
				if(outletNameList!=null && outletNameList.size()>0)
				{
					for (Iterator iterator = outletNameList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						outletName.put(object[0].toString(), object[1].toString());
					}
				}
				
				fromDate=DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
				toDate=DateUtil.getCurrentDateUSFormat();
				minCount="-30";
				maxCount="30";
				
				
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
	
	@SuppressWarnings("unchecked")
	public String beforeViewReminderReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ABD.associateName");
				gpv.setHeadingName("Outlet Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("AD.assetname");
				gpv.setHeadingName("Asset Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.frequency");
				gpv.setHeadingName("Frequency");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.reminder_for");
				gpv.setHeadingName("Allot To");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				List<GridDataPropertyView> complReportColumn=Configuration.getConfigurationData("mapped_asset_report_config",accountID,connectionSpace,"",0,"table_name","asset_report_config");
				if(complReportColumn!=null && complReportColumn.size()>0)
				{
					for(GridDataPropertyView gdp:complReportColumn)
					{
						if(!gdp.getColomnName().equalsIgnoreCase("download"))
						{
							gpv=new GridDataPropertyView();
							gpv.setColomnName("AR."+gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setWidth(gdp.getWidth());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							masterViewProp.add(gpv);
						}
					}
				}
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.remindMode");
				gpv.setHeadingName("Mode");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.escalation_level_1");
				gpv.setHeadingName("Level 2");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.escalation_level_2");
				gpv.setHeadingName("Level 3");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.escalation_level_3");
				gpv.setHeadingName("Level 3");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ARD.escalation_level_4");
				gpv.setHeadingName("Level 4");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("AR.download");
				gpv.setHeadingName("Document");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setFormatter("downloadDoc");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				if(masterViewProp!=null && masterViewProp.size()>0)
				{
					session.put("masterViewProp", masterViewProp);
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
	
	public List getMappedOutletId(String userEmpID)
	{
		List outletNameList=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpID+")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			StringBuilder empIdS = new StringBuilder();
			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					empIdS.append(shareDataCount.get(i).toString()+",");
					String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
					if (mappedEmpId != null)
					{
						empIdS.append(mappedEmpId+",");
					}
				}
			}
			String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", userEmpID);
			if (mappedEmpId != null)
			{
				empIdS.append(mappedEmpId+",");
			}
			empIdS.append(userEmpID);
			
			outletNameList = new AssetUniversalHelper().getOutletNameByEmpId(empIdS.toString(),false,connectionSpace);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return outletNameList;
	}
	
	@SuppressWarnings("unchecked")
	public String viewReminderReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder strBuilder=new StringBuilder();
				String reminderIdSeries=null;
				Object object=null;
				StringBuilder query = new StringBuilder();
				StringBuilder getMappedOutletId = new StringBuilder();
				List  dataCount=null;
				
				String userEmpID=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				
				
				List outletNameList = getMappedOutletId(userEmpID);
				if(outletNameList!=null && outletNameList.size()>0)
				{
					for (Iterator iterator = outletNameList.iterator(); iterator.hasNext();)
					{
						Object[] object2 = (Object[]) iterator.next();
						getMappedOutletId.append(object2[0].toString()+",");
					}
				}
				getMappedOutletId.append("0");
				
				query.append("SELECT ARD.id FROM associate_basic_data AS ABD ");
				query.append(" INNER JOIN asset_allotment_detail AS AAD ON AAD.outletid=ABD.id ");
				query.append(" INNER JOIN asset_detail AS AD ON AAD.assetid=AD.id ");
				query.append(" INNER JOIN asset_reminder_details AS ARD ON ARD.assetid=AD.id");
				if(outletId!=null && !outletId.equalsIgnoreCase("undefined"))
				{
					if(!outletId.equalsIgnoreCase("All"))
					{
						query.append(" WHERE ABD.id="+outletId);
					}
					else
					{
						query.append(" WHERE ABD.id IN("+getMappedOutletId.toString()+")");
					}
				}
				System.out.println("+++++++++++++ "+query.toString());
				dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
					{
						object = (Object) iterator.next();
						strBuilder.append(object.toString()+",");
					}
				}
				if(strBuilder!=null && !strBuilder.toString().equalsIgnoreCase(""))
				{
					reminderIdSeries=strBuilder.toString().substring(0, strBuilder.toString().length()-1);
				}
				if(dataCount!=null && dataCount.size()>0)
				{
					dataCount.clear();
				}
				query.setLength(0);
				//StringBuilder query1=new StringBuilder("");
				query.append("select count(*) from asset_report");
				dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
					query.setLength(0);
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					if(session.containsKey("session"))
					{
						session.remove("masterViewProp");
					}
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								if(gpv.getColomnName().equalsIgnoreCase("id"))
								{
									query.append("AR.id,");
								}
								else
								{
									query.append(gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM asset_report AS AR " +
								"INNER JOIN asset_reminder_details AS ARD ON ARD.id=AR.assetReminderID " +
								"INNER JOIN asset_detail AS AD ON AD.id=ARD.assetid " +
								"INNER JOIN asset_allotment_detail AS AAD ON AAD.assetid=AD.id " +
								"INNER JOIN associate_basic_data AS ABD ON ABD.id=AAD.outletid " +
								"WHERE ARD.id IN("+reminderIdSeries+")");
						if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
						{
							String str[]=fromDate.split("-");
							if(str[0].length()<3)
							{
								fromDate=DateUtil.convertDateToUSFormat(fromDate);
								toDate=DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND AR.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
							}
							else
							{
								query.append(" AND AR.actionTakenDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
							}
						}
						else if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("dueDate"))
						{
							String str[]=fromDate.split("-");
							if(str[0].length()<3)
							{
								fromDate=DateUtil.convertDateToUSFormat(fromDate);
								toDate=DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND AR.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
							}
							else 
							{
								query.append(" AND AR.dueDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
							}
						}
						else if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("All"))
						{
							if (fromDate!=null & !fromDate.equalsIgnoreCase("All") && toDate!=null && !toDate.equalsIgnoreCase("All"))
                            {
								String str[]=fromDate.split("-");
								if(str[0].length()<3)
								{
									fromDate=DateUtil.convertDateToUSFormat(fromDate);
									toDate=DateUtil.convertDateToUSFormat(toDate);
								}
								System.out.println("RRRRRRR "+fromDate+" >>> "+toDate);
								query.append(" AND (AR.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
								query.append(" OR AR.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"')");
							
                            }
						}
						System.out.println("Frequency "+frequency);
						if (frequency!=null && !frequency.equalsIgnoreCase("All")) 
						{
							query.append(" AND ARD.frequency = '"+frequency+"'");
						}
						System.out.println("Action Status "+actionStatus);
						if(actionStatus!=null && !actionStatus.equalsIgnoreCase("ALL"))
						{
							query.append(" AND AR.actionTaken='"+actionStatus+"'");
						}
						query.append(" ORDER BY AR.actionTakenDate ASC");
						System.out.println("@@@@@ "+query.toString());
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						
						if(data!=null)
						{
							Object[] obdata=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								int k=0;
								obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
									if(obdata[k]!=null)
									{
										if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d") && !gpv.getColomnName().equalsIgnoreCase("AR.dueDate") && !gpv.getColomnName().equalsIgnoreCase("AR.actionTakenDate"))
										{
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else if(gpv.getColomnName().equalsIgnoreCase("AR.document_takeaction") || gpv.getColomnName().equalsIgnoreCase("AR.document_config_1") || gpv.getColomnName().equalsIgnoreCase("AR.document_config_2"))
										{
											String str=obdata[k].toString().substring(obdata[k].toString().lastIndexOf("//")+2, obdata[k].toString().length());
											String docName=str.substring(14,str.length());
											one.put(gpv.getColomnName(),docName);
										}
										else if(gpv.getColomnName().equalsIgnoreCase("ARD.reminder_for")  || gpv.getColomnName().equalsIgnoreCase("ARD.escalation_level_1") || gpv.getColomnName().equalsIgnoreCase("ARD.escalation_level_2") || gpv.getColomnName().equalsIgnoreCase("ARD.escalation_level_3") || gpv.getColomnName().equalsIgnoreCase("ARD.escalation_level_4")  )
										{
											StringBuilder empName= new StringBuilder();
											String empId=obdata[k].toString().replace("#", ",").substring(0,(obdata[k].toString().length()-1));
											String query2="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
											List data1=cbt.executeAllSelectQuery(query2,connectionSpace );
											for (Iterator iterator = data1.iterator(); iterator.hasNext();) 
											{
												object = (Object) iterator.next();
												empName.append(object.toString()+", ");
											}
											one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
										}
										else if(gpv.getColomnName().equalsIgnoreCase("ARD.frequency"))
										{
											one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
										}
										else if(gpv.getColomnName().equalsIgnoreCase("AR.dueDate") || gpv.getColomnName().equalsIgnoreCase("AR.actionTakenDate"))
										{
											one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+" ("+obdata[k+1].toString()+")");
										}
										else if(gpv.getColomnName().equalsIgnoreCase("AR.userName"))
										{
											one.put(gpv.getColomnName(),DateUtil.makeTitle(Cryptography.decrypt(obdata[k].toString(), DES_ENCRYPTION_KEY)));
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
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
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
	
	@SuppressWarnings("unchecked")
	public String getDocDownload()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				if(remiderReportId!=null && !remiderReportId.equalsIgnoreCase(""))
				{
					List  dataCount=cbt.executeAllSelectQuery("SELECT document_takeaction,document_config_1,document_config_2 FROM asset_report WHERE id="+remiderReportId,connectionSpace);
					if(dataCount!=null && dataCount.size()>0)
					{
						docMap=new LinkedHashMap<String, String>();
						String str=null;
						Object[] object=null;
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if(object[0]!=null)
							{
								str=object[0].toString().substring(object[0].toString().lastIndexOf("//")+2, object[0].toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object[0].toString(),documentName);
							}
							if(object[1]!=null)
							{
								str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object[1].toString(),documentName);
							}
							if(object[2]!=null)
							{
								str=object[2].toString().substring(object[2].toString().lastIndexOf("//")+2, object[2].toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object[2].toString(),documentName);
							}
							
						}
						returnResult=SUCCESS;
					}
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getDocDownload of class "+getClass(), e);
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
	public String getReminderDocDownload()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				if(remiderReportId!=null && !remiderReportId.equalsIgnoreCase(""))
				{
					List  dataCount=cbt.executeAllSelectQuery("SELECT uploadDoc,uploadDoc1 FROM asset_reminder_details WHERE id="+remiderReportId,connectionSpace);
					if(dataCount!=null && dataCount.size()>0)
					{
						docMap=new LinkedHashMap<String, String>();
						String str=null;
						Object[] object=null;
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if(object[0]!=null)
							{
								str=object[0].toString().substring(object[0].toString().lastIndexOf("//")+2, object[0].toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object[0].toString(),documentName);
							}
							if(object[1]!=null)
							{
								str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object[1].toString(),documentName);
							}
							
						}
						returnResult=SUCCESS;
					}
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getDocDownload of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	public String docDownload()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				File file=new File(fileName);
				String str = fileName.substring(fileName.lastIndexOf("//")+2, fileName.length());
				documentName=str.substring(14,str.length());
				fileName=documentName;
	            if(file.exists())
	            {
	            	fileInputStream = new FileInputStream(file);
	            	return SUCCESS;
	            }
	            else
	            {
	                 addActionError("File dose not exist");
	                 return ERROR;
	            }
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method docDownload of class "+getClass(), e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	
	public Map<String, String> getOutletName()
	{
		return outletName;
	}

	public void setOutletName(Map<String, String> outletName)
	{
		this.outletName = outletName;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getMinCount()
	{
		return minCount;
	}

	public void setMinCount(String minCount)
	{
		this.minCount = minCount;
	}

	public String getMaxCount()
	{
		return maxCount;
	}

	public void setMaxCount(String maxCount)
	{
		this.maxCount = maxCount;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}
	public File getUploadDoc()
	{
		return uploadDoc;
	}
	public void setUploadDoc(File uploadDoc)
	{
		this.uploadDoc = uploadDoc;
	}
	public String getUploadDocContentType()
	{
		return uploadDocContentType;
	}
	public void setUploadDocContentType(String uploadDocContentType)
	{
		this.uploadDocContentType = uploadDocContentType;
	}
	public String getUploadDocFileName()
	{
		return uploadDocFileName;
	}
	public void setUploadDocFileName(String uploadDocFileName)
	{
		this.uploadDocFileName = uploadDocFileName;
	}
	public File getUploadDoc1()
	{
		return uploadDoc1;
	}
	public void setUploadDoc1(File uploadDoc1)
	{
		this.uploadDoc1 = uploadDoc1;
	}
	public String getUploadDoc1ContentType()
	{
		return uploadDoc1ContentType;
	}
	public void setUploadDoc1ContentType(String uploadDoc1ContentType)
	{
		this.uploadDoc1ContentType = uploadDoc1ContentType;
	}
	public String getUploadDoc1FileName()
	{
		return uploadDoc1FileName;
	}
	public void setUploadDoc1FileName(String uploadDoc1FileName)
	{
		this.uploadDoc1FileName = uploadDoc1FileName;
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

	public String getSearchPeriodOn()
	{
		return searchPeriodOn;
	}

	public void setSearchPeriodOn(String searchPeriodOn)
	{
		this.searchPeriodOn = searchPeriodOn;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public String getOutletId()
	{
		return outletId;
	}

	public void setOutletId(String outletId)
	{
		this.outletId = outletId;
	}

	public String getRemiderReportId()
	{
		return remiderReportId;
	}

	public void setRemiderReportId(String remiderReportId)
	{
		this.remiderReportId = remiderReportId;
	}

	public Map<String, String> getDocMap()
	{
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap)
	{
		this.docMap = docMap;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}
	
	
	
	
	
	
}
