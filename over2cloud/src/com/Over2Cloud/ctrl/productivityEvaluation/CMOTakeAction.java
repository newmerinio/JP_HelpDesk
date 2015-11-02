package com.Over2Cloud.ctrl.productivityEvaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class CMOTakeAction extends GridPropertyBean implements ServletRequestAware
{
	private String specialId;
	private String dueDate;
	private String dueTime;
	private HttpServletRequest request;
	private File docUpload1;
	private String docUpload1ContentType;
	private String docUpload1FileName;
	private File docUpload2;
	private String docUpload2ContentType;
	private String docUpload2FileName;
	private File docUpload3;
	private String docUpload3ContentType;
	private String docUpload3FileName;
    
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
	
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String takeActionCMO() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				boolean doneRemindFlag = false;
				String renameFilePath = null;
				List remindDateList = null;
				String dueDate = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_cmo_special_report_config", accountID, connectionSpace, "", 0, "table_name",
				        "cmo_special_report_config");
				if (statusColName != null)
				{
					TableColumes ob1;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("cmo_special_report", Tablecolumesaaa, connectionSpace);
				 }
				
			     List data = cbt.executeAllSelectQuery("SELECT dueDate FROM cmo_special_add_details WHERE id=" + specialId, connectionSpace);
			     if (data != null && data.size() > 0)
			     {
			    	 for (Iterator iterator = data.iterator(); iterator.hasNext();)
			    	 {
			    		 Object object = (Object) iterator.next();
			    		 dueDate = object.toString();
			    	 }
			     }
			     List dateList = cbt.executeAllSelectQuery("SELECT id,reminder_code,remind_date FROM cmo_special_reminder WHERE reminder_code='D' OR reminder_code='R' AND cmo_special_id=" + specialId,
			        connectionSpace);
			     if (dateList != null && dateList.size() > 0)
			     {
			    	 remindDateList = new ArrayList();
			    	 for (Iterator iterator2 = dateList.iterator(); iterator2.hasNext();)
			    	 {
			    		 Object object2[] = (Object[]) iterator2.next();
			    		 if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R") && object2[2] != null)
			    		 {
			    			 remindDateList.add(object2[2].toString());
			    		 }
			    	 }
			     }
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					String comments = request.getParameter("comment");
					
					HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
					Map<String, Object> setVariables;
					Map<String, Object> whereCondition;
					
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", specialId);
					setVariables.put("actionTaken", "Recurring");
					setVariables.put("comment", comments);
					setVariables.put("status", "Done");
					boolean doneFlag = helpdeskHelper.updateTableColomn("cmo_special_add_details", setVariables, whereCondition, connectionSpace);
					if (doneFlag)
					{
						setVariables.clear();
						whereCondition.clear();
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Done");
						doneRemindFlag = helpdeskHelper.updateTableColomn("cmo_special_reminder", setVariables, whereCondition, connectionSpace);
					}
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("dueDate") && !parmName.equals("moduleName") )
						{
							if (parmName.equalsIgnoreCase("specialId"))
							{
								ob = new InsertDataTable();
								ob.setColName("cmoSpecialId");
								ob.setDataName(paramValue);
								insertData.add(ob);
							} 
							else
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}
					if (docUpload1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + DateUtil.mergeDateTime() + docUpload1FileName;
						String storeFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + docUpload1FileName;
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(docUpload1, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("docUpload1");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					if (docUpload2FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + DateUtil.mergeDateTime() + docUpload2FileName;
						String storeFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + docUpload2FileName;
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(docUpload2, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("docUpload2");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					if (docUpload3FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + DateUtil.mergeDateTime() + docUpload3FileName;
						String storeFilePath = new CreateFolderOs().createUserDir("productivity_evaluation") + "//" + docUpload3FileName;
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(docUpload3, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("docUpload3");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					if (remindDateList != null && remindDateList.size() > 0)
					{
						for (int i = 0; i < remindDateList.size(); i++)
						{
							ob = new InsertDataTable();
							ob.setColName("reminder" + (i + 1));
							ob.setDataName(remindDateList.get(i));
							insertData.add(ob);
						}
					}
					
	
					ob = new InsertDataTable();
					ob.setColName("dueDate");
					ob.setDataName(dueDate);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("actionTakenDate");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("actionTakenTime");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("actionTaken");
					ob.setDataName("Recurring");
					insertData.add(ob);

					doneRemindFlag = cbt.insertIntoTable("cmo_special_report", insertData, connectionSpace);
					//boolean status = beforeMailConfiguration(complID, renameFilePath, actionTaken, connectionSpace);
					if (doneRemindFlag)
					{
						addActionMessage("Successfully done !!!!");
						return  SUCCESS;
					}
					else
					{
						return  ERROR;
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
	
	
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}
	public String getDueTime()
	{
		return dueTime;
	}
	public void setDueTime(String dueTime)
	{
		this.dueTime = dueTime;
	}

	public File getDocUpload1()
	{
		return docUpload1;
	}

	public void setDocUpload1(File docUpload1)
	{
		this.docUpload1 = docUpload1;
	}

	public String getDocUpload1ContentType()
	{
		return docUpload1ContentType;
	}

	public void setDocUpload1ContentType(String docUpload1ContentType)
	{
		this.docUpload1ContentType = docUpload1ContentType;
	}

	public String getDocUpload1FileName()
	{
		return docUpload1FileName;
	}

	public void setDocUpload1FileName(String docUpload1FileName)
	{
		this.docUpload1FileName = docUpload1FileName;
	}

	public File getDocUpload2()
	{
		return docUpload2;
	}

	public void setDocUpload2(File docUpload2)
	{
		this.docUpload2 = docUpload2;
	}

	public String getDocUpload2ContentType()
	{
		return docUpload2ContentType;
	}

	public void setDocUpload2ContentType(String docUpload2ContentType)
	{
		this.docUpload2ContentType = docUpload2ContentType;
	}

	public String getDocUpload2FileName()
	{
		return docUpload2FileName;
	}

	public void setDocUpload2FileName(String docUpload2FileName)
	{
		this.docUpload2FileName = docUpload2FileName;
	}

	public File getDocUpload3()
	{
		return docUpload3;
	}

	public void setDocUpload3(File docUpload3)
	{
		this.docUpload3 = docUpload3;
	}

	public String getDocUpload3ContentType()
	{
		return docUpload3ContentType;
	}

	public void setDocUpload3ContentType(String docUpload3ContentType)
	{
		this.docUpload3ContentType = docUpload3ContentType;
	}

	public String getDocUpload3FileName()
	{
		return docUpload3FileName;
	}

	public void setDocUpload3FileName(String docUpload3FileName)
	{
		this.docUpload3FileName = docUpload3FileName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) 
	{
		this.request=request;
	}
}
