package com.Over2Cloud.ctrl.wfpm.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SMSTemplate extends ActionSupport{
	static final Log log = LogFactory.getLog(SMSTemplate.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	private List<SMSTemplateBean> kpiList;
	private String paramName;
	private String shortCode;
	private String paramCheck;
	private String paramval;
	private String paramLength;
	private String status1;
	private String status2;
	private String msgStartCode;
	private String autorplysms;
	private String id;
	private String oper;
	private List<SMSTemplateBean> templateGridModelList;
	private String type;
	public String execute() {
		try {
			if(userName == null || userName.equalsIgnoreCase(""))
			{
				return  LOGIN;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <execute> of class "+getClass(), e);
		}
		return SUCCESS;
	}
	
	public String createSMSTemplate()
	{
		try {
			
			if(userName == null || userName.equalsIgnoreCase(""))
			{
				return  LOGIN;
			}
			String pName[] = null;
			String sCode[] = null;
			String pLength[] = null;
			String pCheck[] = null;
			String pVal[] = null;
			
			String name = "";
			String code = "";
			String length = "";
			@SuppressWarnings("unused")
			String check = "";
			String val = "";
			
			List data = new ArrayList();
			InsertDataTable ob=null;
			boolean status=false;
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			String table = "";
			
			if(type.equalsIgnoreCase("kpi"))
			{
				table = "smstemplate";
			}
			else {
				table = "smstemplateoffering";
			}
			data = cbt.executeAllSelectQuery("select * from "+table+" where keyword='"+ getMsgStartCode() +"'", connectionSpace);
			
			if (data.size() == 0) {
				if(getStatus1() != null && !getStatus1().equalsIgnoreCase("false"))
				{
					name += "Date,";
					code += "DT,";
					length += "NA,";
					val += "NA,";
				}
				
				if(getParamName() != null)
				{
					pName = getParamName().split(",");
				}
				if(getShortCode() != null)
				{
					sCode = getShortCode().split(",");
				}
				if (getParamLength() != null) {
					pLength = getParamLength().split(",");
				}
				
				if(getParamval() != null)
				{
					pVal = getParamval().split(",");
				}
				
				for (int i = 0; i < pName.length; i++) {
					if(!pName[i].trim().equalsIgnoreCase("-1") && !pName[i].trim().equalsIgnoreCase(""))
					{
						name+= pName[i].trim()+",";
					}
				}
				for (int i = 0; i < sCode.length; i++) {
					if(!sCode[i].trim().equalsIgnoreCase("-1") && !sCode[i].trim().equalsIgnoreCase(""))
					{
						code+= sCode[i].trim()+",";
					}
				}
				for (int i = 0; i < pLength.length; i++) {
					
					if(!pLength[i].trim().equalsIgnoreCase("-1") && !pLength[i].trim().equalsIgnoreCase(""))
					{
						length+= pLength[i].trim()+",";
					}
				}
				for (int i = 0; i < pVal.length; i++) {
					
					if(!pVal[i].trim().equalsIgnoreCase("-1") && !pVal[i].trim().equalsIgnoreCase(""))
					{
						val+= pVal[i].trim()+",";
					}
				}
				
				if(getStatus2() != null && !getStatus2().equalsIgnoreCase("false"))
				{
					name += "#";
					code += "#";
					length += "NA";
					val += "NA";
				}
				
				ob=new InsertDataTable();
				ob.setColName("keyword");
				ob.setDataName(getMsgStartCode());
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("length");
				ob.setDataName(length);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("paramName");
				ob.setDataName(name);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("revertMessage");
				ob.setDataName(autorplysms);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("shortCode");
				ob.setDataName(code);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("validation");
				ob.setDataName(val);
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
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				
				status=cbt.insertIntoTable(table,insertData,connectionSpace); 
				if(status)
				{
				 addActionMessage("SMS Template added Successfully!!!");
				}
				else
				{
				 addActionMessage("Oops There is some error in data!!!");
				}
			}
			else
			{
				addActionMessage("SMS Template Keyword already exist!!!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String beforeCreateSMSTemplate()
	{
		try {
			if(userName == null || userName.equalsIgnoreCase(""))
			{
				return  LOGIN;
			}
			kpiList = new ArrayList<SMSTemplateBean>();
			SMSTemplateBean templateBean = new SMSTemplateBean();
			SMSTemplateDao templateDao = new SMSTemplateDao();
			List list = templateDao.getAllKpiList(); 
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				templateBean = new SMSTemplateBean();
				templateBean.setId(Integer.parseInt(object[0].toString()));
				templateBean.setKeyword(object[1].toString());
				
				kpiList.add(templateBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String beforeCreateSMSTemplateOnOffering()
	{
		try {
			if(userName == null && userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			kpiList = new ArrayList<SMSTemplateBean>();
			SMSTemplateBean templateBean = new SMSTemplateBean();
			SMSTemplateDao templateDao = new SMSTemplateDao();
			String offeringLavel = session.get("offeringLevel").toString();
			List list = templateDao.getAllOfferingList(offeringLavel); 
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				templateBean = new SMSTemplateBean();
				templateBean.setId(Integer.parseInt(object[0].toString()));
				templateBean.setKeyword(object[1].toString());
				
				kpiList.add(templateBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String viewSMSTeplate()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List <TableColumes> tableColumn=new ArrayList<TableColumes>();
			String table = "";
			String keyword = "";
			String length = "";
			String paramName = "";
			String revertSMS = "";
			String shortCode = "";
			String validation = "";
			String date = "";
			String mainKeyword = "";
			SMSTemplateBean templateBean = null;
			TableColumes ob1=new TableColumes();
			templateGridModelList = new ArrayList<SMSTemplateBean>();
			
			ob1.setColumnname("keyword");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("length");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("paramName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		
			ob1=new TableColumes();
			ob1.setColumnname("revertMessage");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("shortCode");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("validation");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			ob1=new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
			
			if(type.equalsIgnoreCase("kpi"))
			{
				cbt.createTable22("smstemplate",tableColumn,connectionSpace);
				table = "smstemplate";
				mainKeyword = "DSSFA";
			}
			else {
				cbt.createTable22("smstemplateoffering",tableColumn,connectionSpace);
				table = "smstemplateoffering";
				mainKeyword = "DSOFF";
			}
			
			List template = cbt.executeAllSelectQuery("select keyword, length, paramName, revertMessage, shortCode, validation, date, id, userName from "+table+" where userName='"+userName+"'", connectionSpace);
			for (Iterator iterator = template.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				
				templateBean = new SMSTemplateBean();
				keyword = object[0].toString();
				length = object[1].toString();
				paramName = object[2].toString();
				revertSMS = object[3].toString();
				shortCode = object[4].toString();
				validation = object[5].toString();
				date = object[6].toString();
				
				templateBean.setId(Integer.parseInt(object[7].toString()));
				templateBean.setGeneratedTemplate(mainKeyword+" "+keyword +":"+ shortCode);
				templateBean.setKeyword(keyword);
				templateBean.setLength(length);
				templateBean.setParamName(paramName);
				templateBean.setRevertMessage(revertSMS);
				templateBean.setShortCodes(shortCode);
				templateBean.setRevertMessage(revertSMS);
				templateBean.setValidation(validation);
				templateBean.setDate(DateUtil.convertDateToIndianFormat(date));
				templateBean.setUserName(object[8].toString());
				
				templateGridModelList.add(templateBean);
			}
			setTemplateGridModelList(templateGridModelList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String deleteSMSTemplateById()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
			if(getOper() != null)
			{
				if(getOper().equalsIgnoreCase("del"))
				{
					cbt.deleteAllRecordForId("smstemplate", "id", getId(), connectionSpace);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String beforeNoneRegisterUserShow()
	{
		//System.out.println(">>>>>>>>>>>>>");
		return SUCCESS;
	}
	public List<SMSTemplateBean> getKpiList() {
		return kpiList;
	}

	public void setKpiList(List<SMSTemplateBean> kpiList) {
		this.kpiList = kpiList;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamCheck() {
		return paramCheck;
	}

	public void setParamCheck(String paramCheck) {
		this.paramCheck = paramCheck;
	}

	public String getParamval() {
		return paramval;
	}

	public void setParamval(String paramval) {
		this.paramval = paramval;
	}

	public String getParamLength() {
		return paramLength;
	}

	public void setParamLength(String paramLength) {
		this.paramLength = paramLength;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getMsgStartCode() {
		return msgStartCode;
	}

	public void setMsgStartCode(String msgStartCode) {
		this.msgStartCode = msgStartCode;
	}

	public String getAutorplysms() {
		return autorplysms;
	}

	public void setAutorplysms(String autorplysms) {
		this.autorplysms = autorplysms;
	}

	public List<SMSTemplateBean> getTemplateGridModelList() {
		return templateGridModelList;
	}

	public void setTemplateGridModelList(List<SMSTemplateBean> templateGridModelList) {
		this.templateGridModelList = templateGridModelList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
