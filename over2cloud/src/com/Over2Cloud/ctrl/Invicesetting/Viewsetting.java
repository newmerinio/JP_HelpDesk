package com.Over2Cloud.ctrl.Invicesetting;

import hibernate.common.HibernateSessionFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Viewsetting extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String mappedtablele=null;
	private String mapedtable=null;
	private List<GridDataPropertyView>settingGridColomns=new ArrayList<GridDataPropertyView>();
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

	private List<Object>  settingDetail;
	@SuppressWarnings("null")
	
	public String viewsettingData(){
		String returnString =ERROR;
		try{
			    List<String> ObjList=new ArrayList<String>();
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				query.append("select count(*) from"+" "+getMapedtable());
				List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null){
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();){
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					//getting the list of colmuns
					StringBuilder ids=new StringBuilder("");
					List<Object> Listhb=new ArrayList<Object>();
					ids.append("select ");
					if(getMapedtable() != null){
					List<GridDataPropertyView> fieldNames=Configuration.getSelectConfigurationData(mappedtablele, accountID, connectionSpace);
					int l=0;
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
							GridDataPropertyView gridDataPropertyView = (GridDataPropertyView) iterator.next();
							 if(gridDataPropertyView!=null) {
								    if(l<fieldNames.size()-1){
								    	ids.append(gridDataPropertyView.getColomnName()+",");
								    	ObjList.add(gridDataPropertyView.getColomnName());  }
								    else{
								    	ids.append(gridDataPropertyView.getColomnName());
								    	ObjList.add(gridDataPropertyView.getColomnName());	 }}
							    l++;
						}
				
					}
					ids.append(" from"+" "+ getMapedtable());
				    if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")){
					if(getSearchOper().equalsIgnoreCase("eq")){
						ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");}
					else if(getSearchOper().equalsIgnoreCase("cn")){
						ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");}
					else if(getSearchOper().equalsIgnoreCase("bw")){
						ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");}
					else if(getSearchOper().equalsIgnoreCase("ne")){
						ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");}
					else if(getSearchOper().equalsIgnoreCase("ew")){
						ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
					}}
				ids.append(" limit"+" "+from+","+to);
				List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
				String [] fieldsArray = ObjList.toArray(new String[ObjList.size()]);
		
					if(data!=null){
						if(data.size()>0){
							for(Iterator it=data.iterator(); it.hasNext();){
								Object[] obdata=(Object[])it.next();
							
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < obdata.length; k++) {
									if(obdata[k]!=null){
									if (fieldsArray[k].equalsIgnoreCase("date")) {
										one.put(fieldsArray[k],DateUtil.convertDateToIndianFormat( obdata[k].toString()));
									}
									else{
										one.put(fieldsArray[k], obdata[k].toString());
									}
									}
								}
								Listhb.add(one);
								
								
							}
							setSettingDetail(Listhb);
							System.out.println(getSettingDetail().size());
						}
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
			}
			returnString=SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
   public String editviewsettingData(){
	   String returnString =ERROR;
		try{
		
			
			
			
			returnString=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	   return returnString;
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
	public String getMappedtablele() {
		return mappedtablele;
	}
	public void setMappedtablele(String mappedtablele) {
		this.mappedtablele = mappedtablele;
	}
	public String getMapedtable() {
		return mapedtable;
	}
	public void setMapedtable(String mapedtable) {
		this.mapedtable = mapedtable;
	}
	public List<GridDataPropertyView> getSettingGridColomns() {
		return settingGridColomns;
	}
	public void setSettingGridColomns(List<GridDataPropertyView> settingGridColomns) {
		this.settingGridColomns = settingGridColomns;
	}
	public List<Object> getSettingDetail() {
		return settingDetail;
	}
	public void setSettingDetail(List<Object> settingDetail) {
		this.settingDetail = settingDetail;
	}
	
}
