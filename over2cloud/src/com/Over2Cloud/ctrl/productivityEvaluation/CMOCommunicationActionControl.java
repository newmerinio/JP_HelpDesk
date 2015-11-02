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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
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
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

@SuppressWarnings("serial")
public class CMOCommunicationActionControl extends GridPropertyBean implements ServletRequestAware
{
	private List<ConfigurationUtilBean> productivityTxtBox;
	private List<ConfigurationUtilBean> productivityDate;
	private List<ConfigurationUtilBean> productivityFile;
	private List<ConfigurationUtilBean> productivityDropDown;
	private List<ConfigurationUtilBean> productivityTime;
	private Map<String,String> OGList;
	private String moduleName;
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
	private List<Object> 	masterViewList;
	private String otherPlant;
	
	public String getCMOCommunicationFiels() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				List<GridDataPropertyView>  fieldsName=null;
				fieldsName=Configuration.getConfigurationData("mapped_cmo_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_communication_add_configuration");
				if(fieldsName!=null)
				{
					productivityTxtBox=new ArrayList<ConfigurationUtilBean>();
					productivityDate=new ArrayList<ConfigurationUtilBean>();
					productivityFile=new ArrayList<ConfigurationUtilBean>();
					productivityDropDown=new ArrayList<ConfigurationUtilBean>();
					productivityTime=new ArrayList<ConfigurationUtilBean>();
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
						else if(gdp.getColType().trim().equalsIgnoreCase("Time") )
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
							productivityTime.add(conUtilBean);
						}
					}
				}
				OGList=new LinkedHashMap<String, String>();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				OGList=KH.getOGList(connectionSpace);
				
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
	public String getCMOCommunicationFielsSpecial() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				List<GridDataPropertyView>  fieldsName=null;
				fieldsName=Configuration.getConfigurationData("mapped_cmo_special_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_special_communication_add_configuration");
				if(fieldsName!=null)
				{
					productivityTxtBox=new ArrayList<ConfigurationUtilBean>();
					productivityDate=new ArrayList<ConfigurationUtilBean>();
					productivityFile=new ArrayList<ConfigurationUtilBean>();
					productivityDropDown=new ArrayList<ConfigurationUtilBean>();
					productivityTime=new ArrayList<ConfigurationUtilBean>();
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
						else if(gdp.getColType().trim().equalsIgnoreCase("Time") )
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
							productivityTime.add(conUtilBean);
						}
					}
				}
				OGList=new LinkedHashMap<String, String>();
				ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
				OGList=KH.getOGList(connectionSpace);
				
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
	public String cmoAddActionData() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			  CommonOperInterface cbt=new CommonConFactory().createInterface();
			  List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_cmo_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_communication_add_configuration");
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
			    
				cbt.createTable22("cmo_communication_add_data",Tablecolumesaaa,connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
				  String Parmname = it.next().toString();
				  String paramValue=request.getParameter(Parmname);
				  if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("moduleName")&& !Parmname.equalsIgnoreCase("otherPlant"))
				  {
					  if (Parmname.equalsIgnoreCase("forMonth")) 
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue.split("-")[1]+"-"+paramValue.split("-")[0]);
						 insertData.add(ob);
					  }
					  else if(Parmname.equalsIgnoreCase("onDate") || Parmname.equalsIgnoreCase("dueDate"))
					  {
						  ob=new InsertDataTable();
						  ob.setColName(Parmname);
						  ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
						  insertData.add(ob);
					  }
					  else 
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue);
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
								e.printStackTrace();
							}
							if (theFile1 != null)
							{
								ob=new InsertDataTable();
							    ob.setColName("upload1");
							    ob.setDataName(renameFilePath);
							    insertData.add(ob);
							    mailFilenames.append(renameFilePath+",");
							    fileNames.append(renameFilePath.substring(48, renameFilePath.length())+", ");
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
							    fileNames.append(renameFilePath.substring(48, renameFilePath.length())+", ");
							
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
							    fileNames.append(renameFilePath.substring(48, renameFilePath.length())+", ");
							}
						}
					}
				}
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
				
			    status=cbt.insertIntoTable("cmo_communication_add_data",insertData,connectionSpace); 
			    if(status)
				{
					addActionMessage("Data added successfully!!!");
					ComplianceUniversalAction CA=new ComplianceUniversalAction();
					ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
					MsgMailCommunication MMC=new MsgMailCommunication();
					ComplianceReminderHelper CR=new ComplianceReminderHelper();
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
					String mailContent=getMailTextForCMO(loggedUserDetail[0],true,request.getParameter("agenda"),request.getParameter("title"),request.getParameter("brief"),request.getParameter("forMonth"),request.getParameter("onDate"),request.getParameter("atTime"),request.getParameter("venue"),CR.getFrequencyName(request.getParameter("frequency")),request.getParameter("dueDate"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
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
								mailContent=getMailTextForCMO(object[0].toString(),false,request.getParameter("agenda"),request.getParameter("title"),request.getParameter("brief"),request.getParameter("forMonth"),request.getParameter("onDate"),request.getParameter("atTime"),request.getParameter("venue"),CR.getFrequencyName(request.getParameter("frequency")),request.getParameter("dueDate"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
								MMC.addMail(object[0].toString(),"",object[2].toString(),subject, mailContent,"","Pending", "0",sendFile,"PROEVA");
								msgContent="Dear "+object[0].toString()+", a new "+moduleName+" has been uploaded for you by "+loggedUserDetail[6]+", "+loggedUserDetail[4]+". Request you to please take requisite action on same. -CMO Team.";
								MMC.addMessage(object[0].toString(),"", object[1].toString(), msgContent, "", "Pending", "0", "PROEVA");
							}
						}
					}
					else
					{
						addActionMessage("No Employee is Mapped in Plant !!!!");
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
	 
	private String getMailTextForCMO(String loggedname, boolean flag,String... arr)
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
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b><center>"+arr[14]+"</center></b></font></div><br>");
			 mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
			 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Agenda:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[0] + "</td></tr>");
			 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Title:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[1] + "</td></tr>");    	
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[2]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>For Month:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+DateUtil.getDayFromMonth(arr[3].split("-")[0])+"-"+arr[3].split("-")[1]+ "</td></tr>");
		     if (!flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[13]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[4]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>At:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[5]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Venue:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[6]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Frequency:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[7]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[8]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (OG):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[9]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (Plant):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[10]+ "</td></tr>");
		     if (flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Uploaded On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[11]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Attachments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[12]+ "</td></tr>");
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
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String cmoSpecialAddActionData() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			  CommonOperInterface cbt=new CommonConFactory().createInterface();
			  List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_cmo_special_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_special_communication_add_configuration");
			  if(statusColName!=null && statusColName.size()>0)
			  {
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				InsertDataTable ob=null;
				boolean status=false;
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				TableColumes ob1=null;
				for(GridDataPropertyView gdp:statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("reminder1") && !gdp.getColomnName().equalsIgnoreCase("reminder2") && !gdp.getColomnName().equalsIgnoreCase("reminder3"))
					{
					  ob1=new TableColumes();
					  ob1.setColumnname(gdp.getColomnName());
					  ob1.setDatatype("varchar(255)");
					  ob1.setConstraint("default NULL");
					  Tablecolumesaaa.add(ob1);
					}
				}
			    ob1=new TableColumes();
			    ob1.setColumnname("moduleName");
			    ob1.setDatatype("varchar(255)");
			    ob1.setConstraint("default NULL");
			    Tablecolumesaaa.add(ob1);
			    
				cbt.createTable22("cmo_special_add_details",Tablecolumesaaa,connectionSpace);
				
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String dueDate=null;
				while (it.hasNext())
				{
				  String Parmname = it.next().toString();
				  String paramValue=request.getParameter(Parmname);
				  if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("moduleName") && !Parmname.equalsIgnoreCase("otherPlant"))
				  {
					  if (Parmname.equalsIgnoreCase("forMonth")) 
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue.split("-")[1]+"-"+paramValue.split("-")[0]);
						 insertData.add(ob);
					  }
					  else if(Parmname.equalsIgnoreCase("dueDate"))
					  {
						  dueDate = DateUtil.convertDateToUSFormat(paramValue);
						if (dueDate != null)
						{
							status = false;
							status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), dueDate + " " + request.getParameter("dueTime"));
							if (!status)
							{
								dueDate=getUpdateDueDate(request.getParameter("frequency"),dueDate);
							}
							 ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 ob.setDataName(dueDate);
							 insertData.add(ob);
						 }
					  }
					  else if(!Parmname.equalsIgnoreCase("reminder1") && !Parmname.equalsIgnoreCase("reminder2") && !Parmname.equalsIgnoreCase("reminder3"))
					  {
					     ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue);
						 insertData.add(ob);
					  }
				   }
				}
				String renameFilePath=null;
				StringBuilder mailFilenames=new StringBuilder();
				StringBuilder fileNames =new StringBuilder();
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
							    fileNames .append(renameFilePath.substring(48, renameFilePath.length())+", ");
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
							    fileNames.append(renameFilePath.substring(48, renameFilePath.length())+", ");
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
							    fileNames.append(renameFilePath.substring(48, renameFilePath.length())+", ");
							}
						}
					}
				}
				ob=new InsertDataTable();
			    ob.setColName("otherPlant");
			    ob.setDataName(otherPlant);
			    insertData.add(ob);
			    
				ob=new InsertDataTable();
			    ob.setColName("status");
			    ob.setDataName("Pending");
			    insertData.add(ob);
			    
			    ob=new InsertDataTable();
			    ob.setColName("actionTaken");
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
				
			    status=cbt.insertIntoTable("cmo_special_add_details",insertData,connectionSpace); 
				if (status)
				{
					ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
			    	int maxcmoId = KH.getMaximumComSpecialId(request.getParameter("moduleName"),connectionSpace);
			    	
					List<String> dateList = new ArrayList<String>();
					List<String> dayCounterList = new ArrayList<String>();
					dateList.add(dueDate);
					dayCounterList.add("0");
					String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
					String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
					String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));

					if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
					{
						status = false;
						int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
						dayCounterList.add(String.valueOf(dateDiff));
						status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
						if (status)
						{
							String newDate=getUpdateDueDate(request.getParameter("frequency"), reminDate1);
							if(newDate!=null)
								dateList.add(newDate);
						}
						else
						{
							dateList.add(reminDate1);
						}
					}
					if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
					{
						status = false;
						int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
						dayCounterList.add(String.valueOf(dateDiff));
						status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
						if (status)
						{
							String newDate=getUpdateDueDate(request.getParameter("frequency"), reminDate2);
							if(newDate!=null)
								dateList.add(newDate);
						}
						else
						{
							dateList.add(reminDate2);
						}
					}
					if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
					{
						status = false;
						int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
						dayCounterList.add(String.valueOf(dateDiff));
						status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
						if (status)
						{
							String newDate=getUpdateDueDate(request.getParameter("frequency"), reminDate3);
							if(newDate!=null)
								dateList.add(newDate);
						}
						else
						{
							dateList.add(reminDate3);
						}
					}
					if (dateList != null && dateList.size() > 0)
					{
						for (int i = 0; i < dateList.size(); i++)
						{
							Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
							dataWithColumnName.put("due_date", dueDate);
							dataWithColumnName.put("due_time", request.getParameter("dueTime"));
							dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
							dataWithColumnName.put("reminder_status", "0");
							dataWithColumnName.put("status", "Pending");
							dataWithColumnName.put("cmo_special_id", String.valueOf(maxcmoId));
							if (i == 0)
							{
								dataWithColumnName.put("reminder_name", "Due Reminder");
								dataWithColumnName.put("reminder_code", "D");
								dataWithColumnName.put("remind_date", dueDate);
							}
							else
							{
								dataWithColumnName.put("reminder_name", "Reminder " + i);
								dataWithColumnName.put("reminder_code", "R");
								dataWithColumnName.put("remind_date", dateList.get(i));
								dataWithColumnName.put("remind_interval", dayCounterList.get(i));
							}
							KH.saveCmoSpecialReminder(dataWithColumnName,connectionSpace);
						}
					}
					statusColName.clear();
					statusColName=Configuration.getConfigurationData("mapped_cmo_special_report_config",accountID,connectionSpace,"",0,"table_name","cmo_special_report_config");
					if (statusColName!=null && statusColName.size()>0) 
					{
						List <TableColumes> Tablecolume=new ArrayList<TableColumes>();
						TableColumes ob2=null;
						for(GridDataPropertyView gdp:statusColName)
						{
							ob2=new TableColumes();
							ob2.setColumnname(gdp.getColomnName());
							ob2.setDatatype("varchar(255)");
							ob2.setConstraint("default NULL");
							Tablecolume.add(ob2);
						}
						cbt.createTable22("cmo_special_report",Tablecolume,connectionSpace);
						insertData = new ArrayList<InsertDataTable>();
						if (dateList != null && dateList.size() > 1)
						{
							for (int i = 1; i < dateList.size(); i++)
							{
								insertData=setParameterInObj("reminder" + i,dateList.get(i).toString(),insertData);
							}

						}
						
						insertData=setParameterInObj("dueDate",dueDate,insertData);
						insertData=setParameterInObj("dueTime",request.getParameter("dueTime"),insertData);
						insertData=setParameterInObj("userName",new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY),insertData);
						insertData=setParameterInObj("actionTaken","Pending",insertData);
						insertData=setParameterInObj("cmoSpecialId",String.valueOf(maxcmoId),insertData);
						insertData=setParameterInObj("actionTakenDate",DateUtil.getCurrentDateUSFormat(),insertData);
						insertData=setParameterInObj("actionTakenTime",DateUtil.getCurrentTimeHourMin(),insertData);

						cbt.insertIntoTable("cmo_special_report", insertData, connectionSpace);
					}
					addActionMessage("Data added successfully!!!");
					ComplianceUniversalAction CA=new ComplianceUniversalAction();
					MsgMailCommunication MMC=new MsgMailCommunication();
					ComplianceReminderHelper CR=new ComplianceReminderHelper();
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
					String mailContent=getMailTextForCMOSpecial(loggedUserDetail[0],true,request.getParameter("title"),request.getParameter("brief"),CR.getFrequencyName(request.getParameter("frequency")),request.getParameter("forMonth"),request.getParameter("dueDate"),request.getParameter("dueTime"),request.getParameter("reminder1"),request.getParameter("reminder2"),request.getParameter("reminder3"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
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
								mailContent=getMailTextForCMOSpecial(object[0].toString(),false,request.getParameter("title"),request.getParameter("brief"),CR.getFrequencyName(request.getParameter("frequency")),request.getParameter("forMonth"),request.getParameter("dueDate"),request.getParameter("dueTime"),request.getParameter("reminder1"),request.getParameter("reminder2"),request.getParameter("reminder3"),KH.getOtherMultipleOGDetails(connectionSpace, otherPlant),KH.getotherMultiplePlantNames(connectionSpace, otherPlant),DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin(),mailFile,loggedUserDetail[0],moduleName);
								MMC.addMail(object[0].toString(),"",object[2].toString(),subject, mailContent,"","Pending", "0",sendFile,"PROEVA");
								msgContent="Dear "+object[0].toString()+", a new "+moduleName+" has been uploaded for you by "+loggedUserDetail[6]+", "+loggedUserDetail[4]+". Request you to please take requisite action on same. -CMO Team.";
								MMC.addMessage(object[0].toString(),"", object[1].toString(), msgContent, "", "Pending", "0", "PROEVA");
							}
						}
					}
					else
					{
						addActionMessage("No Employee is Mapped in Plant !!!!");
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
	private String getMailTextForCMOSpecial(String loggedname, boolean flag,String... arr)
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
			 mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b><center>"+arr[14]+"</center></b></font></div><br>");
			 mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
			 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Title:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[0] + "</td></tr>");    	
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[1]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>For Month:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+DateUtil.getDayFromMonth(arr[3].split("-")[0])+"-"+arr[3].split("-")[1]+ "</td></tr>");
		     if (!flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Implemented By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[13]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Frequency:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[2]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Due Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[4]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Due Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[5]+ "</td></tr>");
		     if (arr[6]!=null && !arr[6].equalsIgnoreCase(""))
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Reminder 1:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[6]+ "</td></tr>");
			 }
		     if (arr[7]!=null && !arr[7].equalsIgnoreCase(""))
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Reminder 2:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[7]+ "</td></tr>");
			 }
		     if (arr[8]!=null && !arr[8].equalsIgnoreCase(""))
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Reminder 3:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[8]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (OG):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[9]+ "</td></tr>");
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Shared With (Plant):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[10]+ "</td></tr>");
		     if (flag)
			 {
		    	 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Uploaded On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[11]+ "</td></tr>");
			 }
		     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Attachments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>"+arr[12]+ "</td></tr>");
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
	public String getUpdateDueDate(String frequency,String dueDate)
	{
		if (frequency.equalsIgnoreCase("W"))
			dueDate = DateUtil.getNewDate("day", 7, dueDate);
		else if (frequency.equalsIgnoreCase("M"))
			dueDate = DateUtil.getNewDate("month", 1, dueDate);
		else if (frequency.equalsIgnoreCase("BM"))
			dueDate = DateUtil.getNewDate("day", 15, dueDate);
		else if (frequency.equalsIgnoreCase("Q"))
			dueDate = DateUtil.getNewDate("month", 3, dueDate);
		else if (frequency.equalsIgnoreCase("HY"))
			dueDate = DateUtil.getNewDate("month", 6, dueDate);
		else if (frequency.equalsIgnoreCase("Y"))
			dueDate = DateUtil.getNewDate("year", 1, dueDate);
		else if (frequency.equalsIgnoreCase("D"))
			dueDate = DateUtil.getNewDate("day", 1, dueDate);
		
		return dueDate;
	}
	private List<InsertDataTable> setParameterInObj(String paramName,String paramValue,List<InsertDataTable> insertData)
	{
		InsertDataTable object=new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}
	public String getCMOCommunicationFielsView() 
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
		
		List<GridDataPropertyView>returnResult=null;
		if (moduleName!=null && moduleName.equalsIgnoreCase("Special Project") || moduleName.equalsIgnoreCase("Specific Assignments")  || moduleName.equalsIgnoreCase("Monitoring Assignments") ) 
		{
			returnResult=Configuration.getConfigurationData("mapped_cmo_special_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_special_communication_add_configuration");
		}
		else
		{
			returnResult=Configuration.getConfigurationData("mapped_cmo_communication_add_configuration",accountID,connectionSpace,"",0,"table_name","cmo_communication_add_configuration");
		}
		for(GridDataPropertyView gdp:returnResult)
		{
			if (!gdp.getColomnName().equalsIgnoreCase("empId") && !gdp.getColomnName().equalsIgnoreCase("reminder1") && !gdp.getColomnName().equalsIgnoreCase("reminder2") && !gdp.getColomnName().equalsIgnoreCase("reminder3")) 
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
	public String viewCMOCommunicationData() 
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
				query1.append("select count(*) from cmo_communication_add_data");
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
								else
								{
									query.append("cmo."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("cmo."+gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" from cmo_communication_add_data AS cmo");
						query.append(" LEFT JOIN employee_basic AS emp ON emp.id =  cmo.empId ");
						query.append(" LEFT JOIN department AS dept ON dept.id =emp.deptname ");
						query.append(" LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId ");
						query.append(" WHERE moduleName = '"+moduleName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						System.out.println("QUERY IS AS imp :::  "+query.toString());
						if(data!=null && data.size()>0)
						{
							ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
							ComplianceReminderHelper CH=new ComplianceReminderHelper();
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
										else if(gpv.getColomnName().equalsIgnoreCase("forMonth"))
										{
											one.put(gpv.getColomnName(),obdata[k].toString().split("-")[1]+"-"+obdata[k].toString().split("-")[0]);
										}
										else if(gpv.getColomnName().equalsIgnoreCase("frequency"))
										{
											one.put(gpv.getColomnName(),CH.getFrequencyName(obdata[k].toString()));
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String  viewCMOCommunicationDataSpecial() 
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
				query1.append("select count(*) from cmo_special_add_details");
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
								else
								{
									query.append("cmoSpecial."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("cmoSpecial."+gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" from cmo_special_add_details AS cmoSpecial");
						query.append(" LEFT JOIN employee_basic AS emp ON emp.id =  cmoSpecial.empId ");
						query.append(" LEFT JOIN department AS dept ON dept.id =emp.deptname ");
						query.append(" LEFT JOIN groupinfo AS gi ON gi.id=dept.groupId ");
						query.append(" WHERE moduleName = '"+moduleName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						System.out.println("QUERY IS AS imp :::  "+query.toString());
						if(data!=null && data.size()>0)
						{
							ProductivityEvaluationKaizenHelper KH=new ProductivityEvaluationKaizenHelper();
							ComplianceReminderHelper CH=new ComplianceReminderHelper();
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
										else if(gpv.getColomnName().equalsIgnoreCase("forMonth"))
										{
											one.put(gpv.getColomnName(),obdata[k].toString().split("-")[1]+"-"+obdata[k].toString().split("-")[0]);
										}
										else if(gpv.getColomnName().equalsIgnoreCase("frequency"))
										{
											one.put(gpv.getColomnName(),CH.getFrequencyName(obdata[k].toString()));
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
	public void setProductivityDropDown(List<ConfigurationUtilBean> productivityDropDown) {
		this.productivityDropDown = productivityDropDown;
	}
	public List<ConfigurationUtilBean> getProductivityTime() {
		return productivityTime;
	}
	public void setProductivityTime(List<ConfigurationUtilBean> productivityTime) {
		this.productivityTime = productivityTime;
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
	public String getOtherPlant()
	{
		return otherPlant;
	}
	public void setOtherPlant(String otherPlant)
	{
		this.otherPlant = otherPlant;
	}
}
