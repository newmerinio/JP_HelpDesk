package com.Over2Cloud.ctrl.productivityEvaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class ProductivityEvaluationKaizenAction extends GridPropertyBean implements ServletRequestAware
{

	private static final long serialVersionUID = -1544718390569363548L;
	private List<ConfigurationUtilBean> productivityTxtBox;
	private List<ConfigurationUtilBean> productivityDate;
	private List<ConfigurationUtilBean> productivityFile;
	private List<ConfigurationUtilBean> productivityDropDown;
	private String moduleName;
	private Map<String,String> OGList;
	private Map<String,String> reviewBy;
	private String ogValue;
	private JSONArray jsonArr;
	private HttpServletRequest request;
	private File upload1;
	private String upload1ContentType;
	private String upload1FileName;
	private File upload2;
	private String upload2ContentType;
	private String upload2FileName;
	private File upload3;
	private String upload3ContentType;
	private String upload3FileName;
	private List<GridDataPropertyView> masterViewProp=null;
	private 	List<Object> 	masterViewList;
	private String kaizenId;
	private int indexVal;
	private String byReview;
	private String reviewName;
	private String reviewDate;
	private String otherPlant;
	
	public String beforeKaizenPage() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				List<GridDataPropertyView>  fieldsName=null;
				fieldsName=Configuration.getConfigurationData("mapped_kaizen_add_configuration",accountID,connectionSpace,"",0,"table_name","kaizen_add_configuration");
				if(fieldsName!=null)
				{
					productivityTxtBox=new ArrayList<ConfigurationUtilBean>();
					productivityDate=new ArrayList<ConfigurationUtilBean>();
					productivityFile=new ArrayList<ConfigurationUtilBean>();
					productivityDropDown=new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView  gdp:fieldsName)
					{
						ConfigurationUtilBean conUtilBean=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityTxtBox.add(conUtilBean);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("Date") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityDate.add(conUtilBean);
							
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("F") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityFile.add(conUtilBean);
							
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("D") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityDropDown.add(conUtilBean);
						}
					}
				}
				OGList=new LinkedHashMap<String, String>();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				OGList=KH.getOGList(connectionSpace);
				if (moduleName!=null && !moduleName.equalsIgnoreCase("Kaizen") && !moduleName.equalsIgnoreCase("Best Practices")) 
				{
					reviewBy=KH.getReviewBY(connectionSpace);
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
    
	public String beforeImprovedPage()
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				List<GridDataPropertyView>  fieldsName=null;
				fieldsName=Configuration.getConfigurationData("mapped_improved_dash_add_configuration",accountID,connectionSpace,"",0,"table_name","improved_dash_add_configuration");
				if(fieldsName!=null)
				{
					productivityTxtBox=new ArrayList<ConfigurationUtilBean>();
					productivityDate=new ArrayList<ConfigurationUtilBean>();
					productivityFile=new ArrayList<ConfigurationUtilBean>();
					productivityDropDown=new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView  gdp:fieldsName)
					{
						ConfigurationUtilBean conUtilBean=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityTxtBox.add(conUtilBean);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("Date") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityDate.add(conUtilBean);
							
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("F") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityFile.add(conUtilBean);
							
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("D") )
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());	
							conUtilBean.setColType(gdp.getColType());	
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							conUtilBean.setValidationType(gdp.getValidationType());	
							productivityDropDown.add(conUtilBean);
						}
					}
				}
				OGList=new LinkedHashMap<String, String>();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				OGList=KH.getOGList(connectionSpace);
				if (moduleName!=null && !moduleName.equalsIgnoreCase("Kaizen") && !moduleName.equalsIgnoreCase("Best Practices")) 
				{
					reviewBy=KH.getReviewBY(connectionSpace);
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
	@SuppressWarnings("rawtypes")
	public String getPlantName()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (ogValue != null && !ogValue.equalsIgnoreCase(""))
				{
					jsonArr = new JSONArray();
					StringBuilder query = new StringBuilder();
					if (ogValue.equalsIgnoreCase("All")) 
					{
						query.append("SELECT id,deptName FROM department WHERE groupId NOT IN('9','10')");
					}
					else
					{
						query.append("SELECT id,deptName FROM department WHERE groupId NOT IN('9','10') AND groupId IN ("+ogValue+")");
					}
					//System.out.println("query plant :::  "+query.toString());
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
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
				else
				{
					return ERROR;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String kaizenAddData() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			  CommonOperInterface cbt=new CommonConFactory().createInterface();
			  List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_kaizen_add_configuration",accountID,connectionSpace,"",0,"table_name","kaizen_add_configuration");
			  if(statusColName!=null && statusColName.size()>0)
			  {
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				InsertDataTable ob=null;
				boolean status=false;
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				TableColumes ob1=null;
				for(GridDataPropertyView gdp:statusColName)
				{
				  ob1=new TableColumes();
				  ob1.setColumnname(gdp.getColomnName());
				  ob1.setDatatype("varchar(255)");
				  ob1.setConstraint("default NULL");
				  Tablecolumesaaa.add(ob1);
				}
				
			    ob1=new TableColumes();
			    ob1.setColumnname("moduleName");
			    ob1.setDatatype("varchar(255)");
			    ob1.setConstraint("default NULL");
			    Tablecolumesaaa.add(ob1);
			    
				cbt.createTable22("kaizen_add_data",Tablecolumesaaa,connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
				  String Parmname = it.next().toString();
				  String paramValue=request.getParameter(Parmname);
				  if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("moduleName")&& !Parmname.equalsIgnoreCase("otherPlant"))
				  {
					  if (Parmname.equalsIgnoreCase("implementedIn")) 
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue.split("-")[1]+"-"+paramValue.split("-")[0]);
						 insertData.add(ob);
					  } 
					  else 
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(DateUtil.makeTitle(paramValue));
						 insertData.add(ob);
					  }
				  }
				}
				String renameFilePath=null;
				StringBuilder fileNames=new StringBuilder();
				StringBuilder mailFilenames=new StringBuilder();
				if (upload1FileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload1FileName();
					String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload1FileName();
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath1 != null)
					{
						File theFile1 = new File(storeFilePath1);
						File newFileName = new File(str);
						if (theFile1 != null)
						{
							try
							{
								FileUtils.copyFile(upload1, theFile1);
								if (theFile1.exists())
									theFile1.renameTo(newFileName);
							}
							catch (IOException e)
							{
								mailFilenames.append("");
								fileNames.append("");
								e.printStackTrace();
							}
							if (theFile1 != null)
							{
								ob=new InsertDataTable();
							    ob.setColName("upload1");
							    ob.setDataName(renameFilePath);
							    insertData.add(ob);
							    mailFilenames.append(renameFilePath+",");
							    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
							}
						}
					}
				}
				
				if (upload2FileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload2FileName();
					String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload2FileName();
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath1 != null)
					{
						File theFile1 = new File(storeFilePath1);
						File newFileName = new File(str);
						if (theFile1 != null)
						{
							try
							{
								FileUtils.copyFile(upload2, theFile1);
								if (theFile1.exists())
									theFile1.renameTo(newFileName);
							}
							catch (IOException e)
							{
								mailFilenames.append("");
								fileNames.append("");
								e.printStackTrace();
							}
							if (theFile1 != null)
							{
								ob=new InsertDataTable();
							    ob.setColName("upload2");
							    ob.setDataName(renameFilePath);
							    insertData.add(ob);
							    mailFilenames.append(renameFilePath+",");
							    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
							}
						}
					}
				}
				if (upload3FileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload3FileName();
					String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload3FileName();
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath1 != null)
					{
						File theFile1 = new File(storeFilePath1);
						File newFileName = new File(str);
						if (theFile1 != null)
						{
							try
							{
								FileUtils.copyFile(upload3, theFile1);
								if (theFile1.exists())
									theFile1.renameTo(newFileName);
							}
							catch (IOException e)
							{
								mailFilenames.append("");
								fileNames.append("");
								e.printStackTrace();
							}
							if (theFile1 != null)
							{
								ob=new InsertDataTable();
							    ob.setColName("upload3");
							    ob.setDataName(renameFilePath);
							    insertData.add(ob);
							    mailFilenames.append(renameFilePath+",");
							    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
							
							}
						}
					}
				}
				ob=new InsertDataTable();
			    ob.setColName("otherPlant");
			    ob.setDataName(otherPlant.trim());
			    insertData.add(ob);
			    
				ob=new InsertDataTable();
			    ob.setColName("status");
			    ob.setDataName("Pending");
			    insertData.add(ob);
			    
			    ob=new InsertDataTable();
			    ob.setColName("empId");
			    ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
			    insertData.add(ob);
				
			    ob=new InsertDataTable();
			    ob.setColName("moduleName");
			    ob.setDataName(request.getParameter("moduleName"));
			    insertData.add(ob);
				
			    ob=new InsertDataTable();
			    ob.setColName("userName");
			    ob.setDataName(userName);
			    insertData.add(ob);
			   
				ob=new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				
			    ob=new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
			    status=cbt.insertIntoTable("kaizen_add_data",insertData,connectionSpace); 
			    if(status)
				{
					addActionMessage("Data added successfully!!!");
					ComplianceUniversalAction CA=new ComplianceUniversalAction();
					ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
					MsgMailCommunication MMC=new MsgMailCommunication();
					String loggedUserDetail[]=CA.getEmpDataByUserName(userName);
					String subject= moduleName+" Acknowledgement Alert";
					String mailFile=null;
					String sendFile=null;
					if (fileNames!=null && !fileNames.toString().equalsIgnoreCase(""))
					{
						mailFile=fileNames.toString().substring(0, fileNames.toString().length()-2);
					}
					else
					{
						mailFile="NA";
					}
					if (mailFilenames!=null && !mailFilenames.toString().equalsIgnoreCase(""))
					{
						sendFile=mailFilenames.toString().substring(0, mailFilenames.toString().length()-1);
					} 
					else
					{
						sendFile="";
					}
					String mailContent=getMailTextForKaizen(loggedUserDetail[0],true,request.getParameter("title"),request.getParameter("brief"),request.getParameter("implementedIn"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
					MMC.addMail(loggedUserDetail[0],loggedUserDetail[4],loggedUserDetail[2],subject, mailContent,"","Pending", "0",sendFile,"PROEVA"); 
					String msgContent="Dear "+loggedUserDetail[0]+", Thanks your "+moduleName+" has been successfully uploaded. -CMO Team. ";
					MMC.addMessage(loggedUserDetail[0], loggedUserDetail[4], loggedUserDetail[1], msgContent, "", "Pending", "0", "PROEVA");
					
					subject= moduleName+" Notification Alert from "+loggedUserDetail[6]+", "+loggedUserDetail[4];
					List sendPersonList=KH.getSendPersonNames(connectionSpace,otherPlant);
					if (sendPersonList!=null && sendPersonList.size()>0)
					{
						for (Iterator iterator = sendPersonList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null)
							{
								mailContent=getMailTextForKaizen(object[0].toString(),false,request.getParameter("title"),request.getParameter("brief"),request.getParameter("implementedIn"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
								MMC.addMail(object[0].toString(),"",object[2].toString(),subject, mailContent,"","Pending", "0",sendFile,"PROEVA");
								msgContent="Dear "+object[0].toString()+", a new "+moduleName+" has been uploaded for you by "+loggedUserDetail[6]+", "+loggedUserDetail[4]+". Request you to please take requisite action on same. -CMO Team.";
								MMC.addMessage(object[0].toString(),"", object[1].toString(), msgContent, "", "Pending", "0", "PROEVA");
							}
						}
					}
					
					return SUCCESS;
				}
				else
				{
				    addActionMessage("Oops There is some error in data!");
				    return SUCCESS;
				}
			 }
			  else
			  {
				  return ERROR; 
			  }
			 
			}
			catch (Exception exp) 
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

	private String getMailTextForKaizen(String loggedname,boolean flag, String... arr)
	{
		StringBuilder mailText=new StringBuilder();
		try
		{

			 mailText.append("<br><div align='justify'><font face ='ARIAL' size='2'><h3>Dear "+loggedname+", </h3></FONT></div>"); 
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><h3>Hello!!!</h3></FONT></div>");
			 if (flag)
			 {
				 mailText.append("<div align='justify'><font face ='ARIAL' size='2'>This is an acknowledgement alert for following activity done by you. </FONT></div>");
			 }
			 else
			 {
				 String URL="<a href=http://www.over2cloud.co.in>Login</a>";
				 mailText.append("<div align='justify'><font face ='ARIAL' size='2'>Your kind attention is required for following notification mapped for your analysis. You may find all the related documents for this activity attached with the mail. We request, you to take relevant actions (if any) by ");
				 mailText.append(URL);
				 mailText.append(" with your respective credentials. </FONT></div>");
			 }
			 mailText.append("<br>");
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b><center>"+arr[8]+"</center></b></font></div><br>");
			 mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
			 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Title:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[0] + "</td></tr>");    	
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[1]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented In:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+DateUtil.getDayFromMonth(arr[2].split("-")[0])+"-"+arr[2].split("-")[1]+ "</td></tr>");
		     if (!flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[7]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (OG):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[3]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (Plant):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[4]+ "</td></tr>");
		     if (flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Uploaded On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[5]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Attachments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[6]+ "</td></tr>");
		     mailText.append("</table>");
		     mailText.append("<br>");
		     mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> Thanks & Regards </b></font></div><br>");
		     mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> CMO Team </b></font></div>");
		     mailText.append("<br>");
	         mailText.append("-----------------------------------------------------------------------");
			 mailText.append("<br><div align='justify'><font face ='ARIAL' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
			
			
			 return mailText.toString();
		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailText.toString();
	}

	public String viewKaizenPage() 
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
	public String viewKaizenData() 
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
	
	@SuppressWarnings("rawtypes")
	public String getReviewNames()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (ogValue != null && !ogValue.equalsIgnoreCase(""))
				{
					jsonArr = new JSONArray();
					StringBuilder query = new StringBuilder();
					query.append("SELECT id,empName FROM employee_basic WHERE deptname = '"+ogValue+"'");
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
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
				else
				{
					return ERROR;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addImprovedActionData()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			  CommonOperInterface cbt=new CommonConFactory().createInterface();
			  List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_improved_dash_add_configuration",accountID,connectionSpace,"",0,"table_name","improved_dash_add_configuration");
			  if(statusColName!=null && statusColName.size()>0)
			  {
				InsertDataTable ob=null;
				boolean status=false;
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				TableColumes ob1=null;
				for(GridDataPropertyView gdp:statusColName)
				{
				  ob1=new TableColumes();
				  ob1.setColumnname(gdp.getColomnName());
				  ob1.setDatatype("varchar(255)");
				  ob1.setConstraint("default NULL");
				  Tablecolumesaaa.add(ob1);
				}
			    ob1=new TableColumes();
			    ob1.setColumnname("moduleName");
			    ob1.setDatatype("varchar(255)");
			    ob1.setConstraint("default NULL");
			    Tablecolumesaaa.add(ob1);
			    
				cbt.createTable22("improved_add_data",Tablecolumesaaa,connectionSpace);
				
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				
				String title=null,monthUpload=null,selectOG=null;
				ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
				ArrayList<String> fieldNameList = new ArrayList<String>();
				ComplianceUniversalAction CA=new ComplianceUniversalAction();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				MsgMailCommunication MMC=new MsgMailCommunication();
				String loggedUserDetail[]=CA.getEmpDataByUserName(userName);
				while (it.hasNext())
				{
				   String Parmname = it.next().toString();
				   String paramValue=request.getParameter(Parmname);
				   if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("moduleName")&& !Parmname.equalsIgnoreCase("indexVal")&& !Parmname.equalsIgnoreCase("otherPlant"))
				   {
					  if (Parmname.equalsIgnoreCase("title")) 
					  {
						  title=paramValue;
					  }
					  else if(Parmname.equalsIgnoreCase("implementedIn"))
					  {
						  monthUpload=paramValue.split("-")[1]+"-"+paramValue.split("-")[0];
					  }
					  else if(Parmname.equalsIgnoreCase("otherOG"))
					  {
						  selectOG=paramValue;
					  }
					  else
					  {
						fieldNameList.add(Parmname);
						ArrayList<String> list = new ArrayList<String>();
						String tempParamValueSize[] = request.getParameterValues(Parmname);
						for(int i=0; i<indexVal; i++)
						{
							if (Parmname.equalsIgnoreCase("reviewDate")) 
							{
								list.add( DateUtil.convertDateToUSFormat(tempParamValueSize[i].trim()));
							}
							else
							{
								list.add(tempParamValueSize[i].trim());
							}
							
						}
						mainList.add(list);
					  }
				   }
				}
				for (int i = 0; i < mainList.get(0).size(); i++)
				{
					String reviewBy=null;
					String reviewName=null;
					String reviewDate=null;
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					for (int j = 0; j < fieldNameList.size(); j++) 
					{
						//System.out.print(fieldNameList.get(j).toString());
						String s= mainList.get(j).get(i).toString();
						//System.out.println(":"+s);
						if (!fieldNameList.get(j).toString().equalsIgnoreCase("moduleName")&& !fieldNameList.get(j).toString().equalsIgnoreCase("indexVal") && !s.equalsIgnoreCase("")) 
						{
							ob = new InsertDataTable();
							ob.setColName(fieldNameList.get(j).toString());
							ob.setDataName(s);
							insertData.add(ob);
							if (fieldNameList.get(j).toString().equalsIgnoreCase("byReview"))
							{
								reviewBy=s;
							}
							else if (fieldNameList.get(j).toString().equalsIgnoreCase("reviewName"))
							{
								reviewName=s;
							}
							else if (fieldNameList.get(j).toString().equalsIgnoreCase("reviewDate"))
							{
								reviewDate=DateUtil.convertDateToIndianFormat(s);
							}
						}
					}
					String renameFilePath=null;
					StringBuilder fileNames=new StringBuilder();
					StringBuilder mailFilenames=new StringBuilder();
					if (upload1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload1FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(upload1, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									ob=new InsertDataTable();
								    ob.setColName("upload1");
								    ob.setDataName(renameFilePath);
								    insertData.add(ob);
								    mailFilenames.append(renameFilePath+",");
								    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
								
								}
							}
						}
					}
					if (upload2FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload2FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload2FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(upload2, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									ob=new InsertDataTable();
								    ob.setColName("upload2");
								    ob.setDataName(renameFilePath);
								    insertData.add(ob);
								    mailFilenames.append(renameFilePath+",");
								    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
								
								}
							}
						}
					}
					if (upload3FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + DateUtil.mergeDateTime() + getUpload3FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("ProductivityEvaluationUpload") + "//" + getUpload3FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(upload3, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									ob=new InsertDataTable();
								    ob.setColName("upload3");
								    ob.setDataName(renameFilePath);
								    insertData.add(ob);
								    mailFilenames.append(renameFilePath+",");
								    fileNames.append(renameFilePath.substring(50, renameFilePath.length())+", ");
								
								}
							}
						}
					}

					ob=new InsertDataTable();
				    ob.setColName("title");
				    ob.setDataName(title);
				    insertData.add(ob);
				    

					ob=new InsertDataTable();
				    ob.setColName("implementedIn");
				    ob.setDataName(monthUpload);
				    insertData.add(ob);
				    
					ob=new InsertDataTable();
				    ob.setColName("otherOG");
				    ob.setDataName(selectOG);
				    insertData.add(ob);

					ob=new InsertDataTable();
				    ob.setColName("otherPlant");
				    ob.setDataName(otherPlant);
				    insertData.add(ob);
					
					ob=new InsertDataTable();
				    ob.setColName("status");
				    ob.setDataName("Pending");
				    insertData.add(ob);
				    
				    ob=new InsertDataTable();
				    ob.setColName("empId");
				    ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				    insertData.add(ob);
					
				    ob=new InsertDataTable();
				    ob.setColName("moduleName");
				    ob.setDataName(request.getParameter("moduleName"));
				    insertData.add(ob);
					
				    ob=new InsertDataTable();
				    ob.setColName("userName");
				    ob.setDataName(userName);
				    insertData.add(ob);
				   
					ob=new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					
				    ob=new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					status=cbt.insertIntoTable("improved_add_data",insertData,connectionSpace);
				    if (status)
					{
						String subject= moduleName+" Acknowledgement Alert";
						String mailFile=null;
						String sendFile=null;
						if (fileNames!=null && !fileNames.toString().equalsIgnoreCase(""))
						{
							mailFile=fileNames.toString().substring(0, fileNames.toString().length()-2);
						}
						else
						{
							mailFile="NA";
						}
						if (mailFilenames!=null && !mailFilenames.toString().equalsIgnoreCase(""))
						{
							sendFile=mailFilenames.toString().substring(0, mailFilenames.toString().length()-1);
						} 
						else
						{
							sendFile="";
						}
						String mailContent=getMailTextForImproved(loggedUserDetail[0],true,request.getParameter("title"),KH.getotherMultiplePlantNames(connectionSpace, reviewBy),KH.getReviewName(connectionSpace, reviewName),reviewDate,request.getParameter("implementedIn"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
						MMC.addMail(loggedUserDetail[0],loggedUserDetail[4],loggedUserDetail[2],subject, mailContent,"","Pending", "0",sendFile,"PROEVA"); 
						String msgContent="Dear "+loggedUserDetail[0]+", Thanks your "+moduleName+" has been successfully uploaded. -CMO Team. ";
						MMC.addMessage(loggedUserDetail[0], loggedUserDetail[4], loggedUserDetail[1], msgContent, "", "Pending", "0", "PROEVA");
						
						subject= moduleName+" Notification Alert from "+loggedUserDetail[6]+", "+loggedUserDetail[4];
						List sendPersonList=KH.getSendPersonNames(connectionSpace,otherPlant);
						if (sendPersonList!=null && sendPersonList.size()>0)
						{
							for (Iterator iterator = sendPersonList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null)
								{
									mailContent=getMailTextForImproved(loggedUserDetail[0],false,request.getParameter("title"),KH.getotherMultiplePlantNames(connectionSpace, reviewBy),KH.getReviewName(connectionSpace, reviewName),reviewDate,request.getParameter("implementedIn"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
									MMC.addMail(object[0].toString(),"",object[2].toString(),subject, mailContent,"","Pending", "0",sendFile,"PROEVA");
									msgContent="Dear "+object[0].toString()+", a new "+moduleName+" has been uploaded for you by "+loggedUserDetail[6]+", "+loggedUserDetail[4]+". Request you to please take requisite action on same. -CMO Team.";
									MMC.addMessage(object[0].toString(),"", object[1].toString(), msgContent, "", "Pending", "0", "PROEVA");
								}
							}
						}
						 fileNames.setLength(0);
						 mailFilenames.setLength(0);
					}
					//	System.out.println("status :::::  "+status);
					//System.out.println("-----------------------------------------------------------------");
				}
				if(status)
				{
						addActionMessage("Data added successfully!!!");
						return SUCCESS;
				}
				else
				{
					addActionMessage("Oops there is some error in data!!!");
					return ERROR;
				}
			  }
			  else
			  {
				  return ERROR; 
			  }
			}
			catch (Exception exp) 
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
	private String getMailTextForImproved(String loggedname,boolean flag, String... arr)
	{
		StringBuilder mailText=new StringBuilder();
		try
		{
			 mailText.append("<br><div align='justify'><font face ='ARIAL' size='2'><h3>Dear "+loggedname+", </h3></FONT></div>"); 
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><h3>Hello!!!</h3></FONT></div>");
			 if (flag)
			 {
				 mailText.append("<div align='justify'><font face ='ARIAL' size='2'>This is an acknowledgement alert for following activity done by you. </FONT></div>");
			 }
			 else
			 {
				 String URL="<a href=http://www.over2cloud.co.in>Login</a>";
				 mailText.append("<div align='justify'><font face ='ARIAL' size='2'>Your kind attention is required for following notification mapped for your analysis. You may find all the related documents for this activity attached with the mail. We request, you to take relevant actions (if any) by ");
				 mailText.append(URL);
				 mailText.append(" with your respective credentials. </FONT></div>");
			 }
			 mailText.append("<br>");
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b><center>"+arr[10]+"</center></b></font></div><br>");
			 mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
			 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Title:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[0] + "</td></tr>");    	
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented In:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+DateUtil.getDayFromMonth(arr[4].split("-")[0])+"-"+arr[4].split("-")[1]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Review By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[1]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Review Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[2]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Review Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[3]+ "</td></tr>");
		     if (!flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[9]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (OG):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[5]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (Plant):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[6]+ "</td></tr>");
		     if (flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Uploaded On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[7]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Attachments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[8]+ "</td></tr>");
		     mailText.append("</table>");
		     mailText.append("<br>");
		     mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> Thanks & Regards </b></font></div><br>");
		     mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> CMO Team </b></font></div>");
		     mailText.append("<br>");
	         mailText.append("-----------------------------------------------------------------------");
			 mailText.append("<br><div align='justify'><font face ='ARIAL' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
			
			
			 return mailText.toString();
		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailText.toString();
	}
	public String beforeTakeAction()
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String kaizenTakeAction() 
	{
	 	boolean sessionFlag=ValidateSession.checkSession();
	 	if(sessionFlag)
	 	{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) 
				{
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id") )
					{
						if (!Parmname.equalsIgnoreCase("kaizenId") && !Parmname.equalsIgnoreCase("moduleName")) 
						{
							wherClause.put(Parmname, paramValue);
						}
						if (Parmname.equalsIgnoreCase("kaizenId")) 
						{
							condtnBlock.put("id",paramValue);
						}
					}
				}
				wherClause.put("actionDate", DateUtil.getCurrentDateUSFormat());
				if (moduleName!=null && moduleName.equalsIgnoreCase("Kaizen") || moduleName.equalsIgnoreCase("Best Practices")) 
				{
					cbt.updateTableColomn("kaizen_add_data", wherClause, condtnBlock,connectionSpace);
				}
				else
				{
					cbt.updateTableColomn("improved_add_data", wherClause, condtnBlock,connectionSpace);
				}
				
			    addActionMessage("Action Taken Succesfully !!!!!");
				return SUCCESS;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
				addActionMessage("Oopss there is some error!!!!");
				return ERROR;
				
			}
	 	}
	 	else
	 	{
	 		return LOGIN;
	 	}
	}
	@Override
	public void setServletRequest(HttpServletRequest request) 
	{
		this.request=request;
	}
	
	public List<ConfigurationUtilBean> getProductivityTxtBox() {
		return productivityTxtBox;
	}
	
	public void setProductivityTxtBox(List<ConfigurationUtilBean> productivityTxtBox) {
		this.productivityTxtBox = productivityTxtBox;
	}

	public List<ConfigurationUtilBean> getProductivityDate() {
		return productivityDate;
	}

	public void setProductivityDate(List<ConfigurationUtilBean> productivityDate) {
		this.productivityDate = productivityDate;
	}

	public List<ConfigurationUtilBean> getProductivityFile() {
		return productivityFile;
	}

	public void setProductivityFile(List<ConfigurationUtilBean> productivityFile) {
		this.productivityFile = productivityFile;
	}

	public List<ConfigurationUtilBean> getProductivityDropDown() {
		return productivityDropDown;
	}

	public void setProductivityDropDown(
			List<ConfigurationUtilBean> productivityDropDown) {
		this.productivityDropDown = productivityDropDown;
	}

	public Map<String, String> getOGList() {
		return OGList;
	}

	public void setOGList(Map<String, String> oGList) {
		OGList = oGList;
	}
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOgValue() {
		return ogValue;
	}

	public void setOgValue(String ogValue) {
		this.ogValue = ogValue;
	}

	public JSONArray getJsonArr() {
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}

	public String getUpload1ContentType() {
		return upload1ContentType;
	}

	public void setUpload1ContentType(String upload1ContentType) {
		this.upload1ContentType = upload1ContentType;
	}

	public String getUpload1FileName() {
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}

	public File getUpload2() {
		return upload2;
	}

	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}

	public String getUpload2ContentType() {
		return upload2ContentType;
	}

	public void setUpload2ContentType(String upload2ContentType) {
		this.upload2ContentType = upload2ContentType;
	}

	public String getUpload2FileName() {
		return upload2FileName;
	}

	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}

	public File getUpload3() {
		return upload3;
	}

	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}

	public String getUpload3ContentType() {
		return upload3ContentType;
	}

	public void setUpload3ContentType(String upload3ContentType) {
		this.upload3ContentType = upload3ContentType;
	}

	public String getUpload3FileName() {
		return upload3FileName;
	}

	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
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

	public Map<String, String> getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(Map<String, String> reviewBy) {
		this.reviewBy = reviewBy;
	}

	public String getKaizenId() {
		return kaizenId;
	}

	public void setKaizenId(String kaizenId) {
		this.kaizenId = kaizenId;
	}

	public int getIndexVal() {
		return indexVal;
	}

	public void setIndexVal(int indexVal) {
		this.indexVal = indexVal;
	}

	public String getByReview() {
		return byReview;
	}

	public void setByReview(String byReview) {
		this.byReview = byReview;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getOtherPlant()
	{
		return otherPlant;
	}
	public void setOtherPlant(String otherPlant)
	{
		this.otherPlant = otherPlant;
	}
	
}
