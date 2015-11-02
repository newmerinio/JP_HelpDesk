package com.Over2Cloud.ctrl.wfpm.target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.lead.GenericWriteExcel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OBDTargetActionControl extends ActionSupport implements ServletRequestAware{

	private Map session	= ActionContext.getContext().getSession();
	private String userName	= (String) session.get("uName");
	private String accountID = (String) session.get("accountid");
	private SessionFactory	connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private List<GridDataPropertyView> obdTextBox;
	private Integer	rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer	page = 0;
	// sorting order - asc or desc
	private String sord	= "";
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
	private Integer	total = 0;
	// All Record
	private Integer	records	= 0;
	private boolean	loadonce = false;
	// Grid colomn view
	private String oper;
	private int id;
	private List<Object> gridModel;
	private String headerName;
	private String offeringLevel = session.get("offeringLevel").toString();
	private List<ConfigurationUtilBean>		obdBoxForConfig		= null;
	private Map<Integer, String>				empDataList			= null;
	private Map<Integer, String>				mapKPIList			= null;
	private Map<Integer, String>				mapOfferingList	= null;
	private String											empID;
	private String											empName;
	private String											mobileno;
	private String											empDesg;
	private String											header;
	private String											targetvalue;
	private String											targetInPercent;
	private String											offeringTargetValues;
	private String											kpiIds;
	private String											targetMonth;
	private String											offeringId;
	private String											targetOn;
	static final Log log = LogFactory.getLog(TargetActionControl.class);
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private boolean isEmpNameExist;
	private JSONObject jsonData;
	private String downloadType=null;
	private String download4=null;
	private String tableAllis=null;
	private String tableName=null;
	private String excelName=null;
	private Map<String, String> columnMap=null;
	private String columnNames;
	private CommonOperInterface cbt=new CommonConFactory().createInterface();
	private String columns;
	public String beforeOBDTargetView()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setOBDViewProp("mapped_obd_report_configuration", "obd_report_configuration");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setOBDViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		obdTextBox = new ArrayList<GridDataPropertyView>();

			List<GridDataPropertyView> returnResult = Configuration
						.getConfigurationData(table1, accountID, connectionSpace, "", 0,
						"table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setAlign(gdp.getAlign());
			obdTextBox.add(gpv);
		}
	}
	
	public String getOBDTargetView()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setHeaderName(headerName);
			
			List<Object> list = new ArrayList<Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			List fieldNames = Configuration.getColomnList(
					"mapped_obd_report_configuration", accountID, connectionSpace,
					"obd_report_configuration");
			
			String countQuery = "SELECT emp.empName, emp.mobOne, obd.totalCall, obd.productiveCall, " +
					" obd.totalSale, obd.newOutlet, obd.userName " +
					" FROM employee_basic as emp INNER JOIN obdtargetdata as obd " +
					" ON emp.mobOne = obd.mobile " +
					" where obd.userName='"+userName+"'";
			
			List list2 = cbt.executeAllSelectQuery(countQuery, connectionSpace);
			setRecords(list2 != null ? list2.size() : 0);
			int to = (getRows() * getPage());
			int from = to - getRows();
			
			query.append("SELECT emp.empName, emp.mobOne, obd.totalCall, obd.productiveCall, ");
			query.append("obd.totalSale, obd.newOutlet, obd.currentMonth");
			query.append(" FROM employee_basic as emp INNER JOIN obdtargetdata as obd");
			query.append(" ON emp.mobOne = obd.mobile");
			query.append(" where obd.userName='"+userName+"' limit  "+from+", "+to);
			List dataCount = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			
			if (dataCount != null)
			{
				int counter = 0;
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					counter = 0;
					Object[] obdata = (Object[]) it.next();
					Map<String, Object> one = new HashMap<String, Object>();
					
					one.put("empName",  obdata[0]);
					for (int k = 1, x=1; x < fieldNames.size(); k++,x++)
					{
						if(
							 !fieldNames.get(x).toString().equalsIgnoreCase("userName") 
								&& !fieldNames.get(x).toString().equalsIgnoreCase("time")
								&& !fieldNames.get(x).toString().equalsIgnoreCase("date"))
						{
							one.put(fieldNames.get(x).toString(),  obdata[k]);
						
							if(!fieldNames.get(x).toString().equalsIgnoreCase("mobile")
									&& !fieldNames.get(x).toString().equalsIgnoreCase("currentMonth")) 
							{
								if(obdata[k] != null)
								{
									counter += Integer.parseInt(obdata[k].toString().trim());
								}
							}
							one.put("total",  counter);
						}
						
					}
					
					list.add(one);
				}
			}
			setGridModel(list);
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeAddConfigOBDTarget()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setHeaderName(headerName);
			List<GridDataPropertyView> list = Configuration.getConfigurationData(
					"mapped_obd_report_configuration", accountID, connectionSpace, "",
					0, "table_name", "obd_report_configuration");
			
			obdBoxForConfig = new ArrayList<ConfigurationUtilBean>();
			empDataList = new HashMap<Integer, String>();
			
			
			for (GridDataPropertyView gdp : list) {
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T")
						&& !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}

					obdBoxForConfig.add(obj);
				}
				
				if (gdp.getColType().trim().equalsIgnoreCase("D")
						&& !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					empDataList = CommonWork.allEmpList(connectionSpace);
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					if (gdp.getColomnName().equalsIgnoreCase("mobile"))
					{
						empName = "Name";
						if (gdp.getMandatroy().equalsIgnoreCase("1")) isEmpNameExist = true;
						else isEmpNameExist = false;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
					+ DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: TargetActionControl of class "
					+ getClass(), e);
		}
		
		return SUCCESS;
	}
	
	public String addConfigOBDTarget()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> obdConfig = Configuration.getConfigurationData(
					"mapped_obd_report_configuration", accountID, connectionSpace, "",
					0, "table_name", "obd_report_configuration");
			
			if(empName != null)
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();
				TableColumes ob1 = new TableColumes();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				String query = "select totalCall,productiveCall,totalSale,newOutlet from obdtargetdata where mobile='"+CommonWork.getMobileNoByEmpID(empName, connectionSpace)+"' "+
				"and currentMonth='"+DateUtil.getCurrentMonth()+"-"+ DateUtil.getCurretnYear()+"'";
				for (GridDataPropertyView gdp : obdConfig)
				{
					
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);
					/**
					 * if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue =
					 * true; else if (gdp.getColomnName().equalsIgnoreCase("date"))
					 * dateTrue = true; else if
					 * (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
					 */
				}
				/*ob1 = new TableColumes();
				ob1.setColumnname("currentMonth");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);*/
				cbt.createTable22("obdTargetData", tableColumn,connectionSpace);
				
				List list =  cbt.executeAllSelectQuery(query, connectionSpace);
				
				
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Iterator it = requestParameterNames.iterator();
				
				if(list != null && !list.isEmpty())
				{
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						
						if (paramValue != null && !paramValue.equalsIgnoreCase("")
								&& !paramValue.equalsIgnoreCase("-1") && parmName != null)
						{
							if(!parmName.equalsIgnoreCase("empName".trim()))
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("mobile", CommonWork.getMobileNoByEmpID(getEmpName(), connectionSpace));
					condtnBlock.put("currentMonth", "'"+DateUtil.getCurrentMonth()+"-"+ DateUtil.getCurretnYear()+"'");
					boolean status = cbt.updateTableColomn("obdTargetData", wherClause, condtnBlock,
							connectionSpace);
					if (status)
					{
						addActionMessage("OBD target updated successfully");
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
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						
						if (paramValue != null && !paramValue.equalsIgnoreCase("")
								&& !paramValue.equalsIgnoreCase("-1") && parmName != null)
						{
							ob = new InsertDataTable();
							if(parmName.equalsIgnoreCase("empName".trim()))
							{
								ob.setColName("mobile");
								ob.setDataName(CommonWork.getMobileNoByEmpID(paramValue, connectionSpace));
							}
							else
							{
								ob.setColName(parmName);
								ob.setDataName(paramValue);
							}
							insertData.add(ob);
						}
					}
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("currentMonth");
					ob.setDataName(DateUtil.getCurrentMonth()+"-"+ DateUtil.getCurretnYear());
					insertData.add(ob);
					boolean status = cbt.insertIntoTable("obdtargetdata", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("OBD target saved successfully");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
					+ DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: TargetActionControl of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String getTargetByEmp()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String mob = CommonWork.getMobileNoByEmpID(empID, connectionSpace);
			//String query = "select totalCall,productiveCall,totalSale,newOutlet from obdtargetdata where mobile='"+mob+"' and currentMonth='"+DateUtil.getCurrentMonth()+"-"+ DateUtil.getCurretnYear()+"'";
			
			String query = "select totalCall,productiveCall,totalSale,newOutlet from obdtargetdata where mobile='"+mob+"'";
			//System.out.println(">>>>>Query : "+ query);
			List list =  cbt.executeAllSelectQuery(query, connectionSpace);
			jsonData = new JSONObject();
			
			if(list != null && !list.isEmpty())
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					jsonData.put("TotalCall", object[0].toString());
					jsonData.put("ProductiveCall", object[1].toString());
					jsonData.put("TotalSale", object[2].toString());
					jsonData.put("NewOutlet", object[3].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getColumn4Download()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(download4!=null && download4.equals("obdTarget"))
				{
					tableAllis="ast";
					returnResult=getColumnName("mapped_obd_report_configuration","obd_report_configuration",tableAllis);
					tableName="obdtargetdata";
					excelName="OBD Target";
				}
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
	public String getColumnForDownload()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(download4!=null && download4.equals("obdTarget"))
				{
					tableAllis="ast";
					returnResult=getColumnName("mapped_obd_report_configuration","obd_report_configuration",tableAllis);
					tableName="obdtargetdata";
					excelName="OBD Target";
				}
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
	
	public String getColumnName(String mappedTableName,String basicTableName,String allias)
	{
			String returnResult=ERROR;
			try
			{
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List<GridDataPropertyView> columnList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
				columnMap=new LinkedHashMap<String, String>();
				if(columnList!=null&&columnList.size()>0)
				{
					//if(downloadType!=null && downloadType.equals("downloadExcel"))
					{
						for(GridDataPropertyView  gdp1:columnList)
						{
							if(!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time"))
							{
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
						}
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			
			String query = "SELECT emp.empName, emp.mobOne, obd.totalCall, obd.productiveCall, obd.totalSale, " +
					"obd.newOutlet, obd.userName " +
					"FROM employee_basic as emp " +
					"INNER JOIN obdtargetdata as obd ON emp.mobOne = obd.mobile " +
					"where obd.userName='prabhatk' limit  0, 10";
			List dataList = new ArrayList<String>();
			List titleList = Arrays.asList(columns.split(","));
			
			dataList = cbt.executeAllSelectQuery(query, connectionSpace);
			new GenericWriteExcel().createExcel("WFPM",excelName, dataList, titleList, null, excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
		
	public String downloadExcelAction()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			String query = "";			
			List dataList = new ArrayList<String>();
			List titleList = Arrays.asList(columns.split(","));
			
			dataList = cbt.executeAllSelectQuery(query, connectionSpace);
			new GenericWriteExcel().createExcel("WFPM",excelName, dataList, titleList, null, excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<GridDataPropertyView> getObdTextBox() {
		return obdTextBox;
	}

	public void setObdTextBox(List<GridDataPropertyView> obdTextBox) {
		this.obdTextBox = obdTextBox;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Object> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<Object> gridModel) {
		this.gridModel = gridModel;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getOfferingLevel() {
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel) {
		this.offeringLevel = offeringLevel;
	}

	public List<ConfigurationUtilBean> getObdBoxForConfig() {
		return obdBoxForConfig;
	}

	public void setObdBoxForConfig(List<ConfigurationUtilBean> obdBoxForConfig) {
		this.obdBoxForConfig = obdBoxForConfig;
	}

	public Map<Integer, String> getEmpDataList() {
		return empDataList;
	}

	public void setEmpDataList(Map<Integer, String> empDataList) {
		this.empDataList = empDataList;
	}

	public Map<Integer, String> getMapKPIList() {
		return mapKPIList;
	}

	public void setMapKPIList(Map<Integer, String> mapKPIList) {
		this.mapKPIList = mapKPIList;
	}

	public Map<Integer, String> getMapOfferingList() {
		return mapOfferingList;
	}

	public void setMapOfferingList(Map<Integer, String> mapOfferingList) {
		this.mapOfferingList = mapOfferingList;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmpDesg() {
		return empDesg;
	}

	public void setEmpDesg(String empDesg) {
		this.empDesg = empDesg;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTargetvalue() {
		return targetvalue;
	}

	public void setTargetvalue(String targetvalue) {
		this.targetvalue = targetvalue;
	}

	public String getTargetInPercent() {
		return targetInPercent;
	}

	public void setTargetInPercent(String targetInPercent) {
		this.targetInPercent = targetInPercent;
	}

	public String getOfferingTargetValues() {
		return offeringTargetValues;
	}

	public void setOfferingTargetValues(String offeringTargetValues) {
		this.offeringTargetValues = offeringTargetValues;
	}

	public String getKpiIds() {
		return kpiIds;
	}

	public void setKpiIds(String kpiIds) {
		this.kpiIds = kpiIds;
	}

	public String getTargetMonth() {
		return targetMonth;
	}

	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getTargetOn() {
		return targetOn;
	}

	public void setTargetOn(String targetOn) {
		this.targetOn = targetOn;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public boolean isEmpNameExist() {
		return isEmpNameExist;
	}

	public void setEmpNameExist(boolean isEmpNameExist) {
		this.isEmpNameExist = isEmpNameExist;
	}

	public JSONObject getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
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

	public String getTableAllis() {
		return tableAllis;
	}

	public void setTableAllis(String tableAllis) {
		this.tableAllis = tableAllis;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
