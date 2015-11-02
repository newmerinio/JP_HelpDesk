package com.Over2Cloud.ctrl.particulasAction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReimbursementActionGrid extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ReimbursementActionGrid.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	
	//table field in particulars_data
    private String particulars;
    private String userName1=userName;
    private String created_date;
    private String created_time;
    private String purpose;
    private String refWithRec;
    private String amount;
    private String sNo;
    
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
	private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String id;
	List<Object> gridModel;
	private boolean ddTrue;
	private String title;
	List<String> fieldNames;
	
public String particularsViewDataInGrid()
 {
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 {
				 StringBuilder query=new StringBuilder("");
				 query.append("select count(*) from particulars_data");
				 List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				 if(dataCount!=null&&dataCount.size()>0)
				 {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords()) to = getRecords();
					 
					 
					 
					 StringBuilder query1=new StringBuilder("");
					 query1.append("select ");
					 List fieldName=Configuration.getColomnList("mapped_particulars_configuration", accountID, connectionSpace, "particulars_configuration");
					 System.out.println(">>>>>>>>>>>.2222222"+fieldName);
					 int i=0;
						if(fieldName!=null && fieldName.size()>0)
						{
							for(Iterator it=fieldName.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(i<fieldName.size()-1)
									    	query1.append(obdata.toString()+",");
									    else
									    	query1.append(obdata.toString());
								    }
								    i++;
								
							}
						}
						
							 query1.append(" from particulars_data ");
							 List  data=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
							 System.out.println(">>>>>>>>>>>>>>>>>>>>>4444444"+data.size());
							 if(data!=null && data.size()>0)
								{
								List<Object> tempList=new ArrayList<Object>();
								for(Iterator it=data.iterator(); it.hasNext();)
								{
									Object[] obdata=(Object[])it.next();
									Map<String, Object> tempMap=new HashMap<String, Object>();
									System.out.println("size of fieldName>>>>>>>>>"+fieldName.size());
									for (int k = 0; k < fieldName.size(); k++) {
										if(obdata[k]!=null)
										{
												if(k==0)
												{
													
													tempMap.put(fieldName.get(k).toString(),(Integer)obdata[k]);
												}
												else {
													
													  if(! fieldName.get(k).toString().equalsIgnoreCase("refWithRec") 
															  && ! fieldName.get(k).toString().equalsIgnoreCase("amount")
															  && ! fieldName.get(k).toString().equalsIgnoreCase("sNo")
															  && ! fieldName.get(k).toString().equalsIgnoreCase("userName"))
															 
													  {
														  tempMap.put(fieldName.get(k).toString(),obdata[k].toString());
														  System.out
															.println(fieldName.get(k).toString()+"====="+obdata[k].toString());
													  }
												}
										}
									}
									
									tempList.add(tempMap);
								}
								  setGridModel(tempList);
								  System.out.println("tempList.size():"+tempList.size());
								  System.out.println("gridModel.size():"+gridModel.size());
								  
								  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
		    	 }
				 return SUCCESS; 
		 }
			}catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			
		}else{ return LOGIN;}
 }
//Remember This point :when implement ServletRequestAware this method need

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public SessionFactory getConnectionSpace() {
		return connectionSpace;
	}

	public void setConnectionSpace(SessionFactory connectionSpace) {
		this.connectionSpace = connectionSpace;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDdTrue() {
		return ddTrue;
	}

	public void setDdTrue(boolean ddTrue) {
		this.ddTrue = ddTrue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static Log getLog() {
		return log;
	}

	public List<Object> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<Object> gridModel) {
		this.gridModel = gridModel;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRefWithRec() {
		return refWithRec;
	}

	public void setRefWithRec(String refWithRec) {
		this.refWithRec = refWithRec;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSNo() {
		return sNo;
	}

	public void setSNo(String no) {
		sNo = no;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
