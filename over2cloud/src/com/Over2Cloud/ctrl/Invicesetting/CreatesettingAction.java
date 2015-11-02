package com.Over2Cloud.ctrl.Invicesetting;

import hibernate.common.HibernateSessionFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreatesettingAction extends ActionSupport implements ServletRequestAware{
	static final Log log = LogFactory.getLog(CreatesettingAction.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	private String mappedtablele=null;
	private String mapedtable=null;
	private String headerName=null;
	private String gridwidth=null;
	private String id=null;
	private List<GridDataPropertyView>settingGridColomns=new ArrayList<GridDataPropertyView>();

	

	@SuppressWarnings("unchecked")
	public String addDetails()
	{
		String resultString=ERROR;
		List<GridDataPropertyView>mappeddetails=null;
		String createtable =null;
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		boolean userTrue=false;
		boolean dateTrue=false;
		boolean timeTrue=false;
		try{
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			 Collections.sort(requestParameterNames);
			 
			 Iterator listit = requestParameterNames.iterator();
			 while (listit.hasNext()) {
					String Parmname = listit.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(Parmname.equals("mappedtablele")&& paramValue.equals(request.getParameter("mappedtablele")))
					{
						mappedtablele=paramValue;
						}
						else
						{
							InsertDataTable ob = new InsertDataTable();
						if(!request.getParameter("createtable").equals(paramValue))
						{
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
						else{
							createtable=request.getParameter("createtable");
						}
						}
			 }
				
				if(mappedtablele!=null){
				 mappeddetails=Configuration.getSelectConfigurationData(mappedtablele,accountID,connectionSpace); 
				 }
				if(mappeddetails!=null){
					//	boolean status=false;
					 for(GridDataPropertyView col:mappeddetails){
						 TableColumes obJ=new TableColumes();
						 obJ.setColumnname(col.getColomnName());
						 obJ.setDatatype("varchar(255)");
						 obJ.setConstraint("default NULL");
						 if(col.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(col.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(col.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
						 Tablecolumesaaa.add(obJ);
						}
						CommonforClassdata obhj = new CommonOperAtion();
						InsertDataTable ob = new InsertDataTable();
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
							 ob.setDataName(DateUtil.getCurrentTimeHourMin());
							 insertData.add(ob);
						 }
					    boolean status = obhj.Createtabledata(createtable, Tablecolumesaaa, connectionSpace);
						status = obhj.insertIntoTable(createtable, insertData,connectionSpace);
						if (status) {
							addActionMessage("Data added SuccessfUlly");
						} else {
							addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
						}
						
				resultString=SUCCESS;
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		 return resultString;
	}
			public String beforecontactmasterView(){
				String returnString =ERROR;
				 List<GridDataPropertyView>returnAdrrResult=null;
				 //System.out.println(id+">>>"+mappedtablele+">>>"+mapedtable);
				try{
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("mapped_table", mapedtable);
					setMapedtable(mapedtable);
				    setMappedtablele(mappedtablele);
					List<String> listdata=new ArrayList<String>();
					listdata.add("id");
					listdata.add("master_name");
					listdata.add("configuration_table");
					List resultdata = new CommonOperAtion().getSelectdataFromSelectedFields(mappedtablele,listdata,paramMap,connectionSpace);
					if(resultdata!=null){
						int sumofColumnWidth=0;
					for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
						Object[] objectCal = (Object[]) iterator.next();
						setHeaderName(objectCal[1].toString());
						if (objectCal[2].toString() != null && !objectCal[2].toString().equalsIgnoreCase("")) {
							setMappedtablele(objectCal[2].toString());
							returnAdrrResult=Configuration.getSelectConfigurationData(objectCal[2].toString(), accountID, connectionSpace);
						
							 if(returnAdrrResult.size()>0){
							 for(GridDataPropertyView gdpv:returnAdrrResult){
									GridDataPropertyView gpv=new GridDataPropertyView();
							
									gpv.setColomnName(gdpv.getColomnName());
									gpv.setHeadingName(gdpv.getHeadingName());
							
									gpv.setEditable(gdpv.getEditable());
									gpv.setSearch(gdpv.getSearch());
									gpv.setHideOrShow(gdpv.getHideOrShow());
									gpv.setWidth(gdpv.getWidth());
									gpv.setFormatter(gdpv.getFormatter());
									settingGridColomns.add(gpv);
									sumofColumnWidth+= gdpv.getWidth();
								}
							  }
						   }
						 }
					    setGridwidth(String.valueOf(sumofColumnWidth));
				        returnString=SUCCESS;
					  }
				 }
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
		       return returnString;
	        }
			
	        public HttpServletRequest getRequest() {
				return request;
			}
			public void setRequest(HttpServletRequest request) {
				this.request = request;
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
	        public String getHeaderName() {
				return headerName;
			}
			public void setHeaderName(String headerName) {
				this.headerName = headerName;
			}
			
	       public String getGridwidth() {
				return gridwidth;
			}
			public void setGridwidth(String gridwidth) {
				this.gridwidth = gridwidth;
			}
		@Override
	       public void setServletRequest(HttpServletRequest request) {
		  // TODO Auto-generated method stub
		   this.request=request;
	      }
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	

}
