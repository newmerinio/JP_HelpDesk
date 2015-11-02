package com.Over2Cloud.ctrl.leaveManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LeaveDashboardAction extends ActionSupport 
{
  static final Log log = LogFactory.getLog(LeaveDashboardAction.class);
  @SuppressWarnings("rawtypes")
  Map session = ActionContext.getContext().getSession();
  public static final String DES_ENCRYPTION_KEY = "ankitsin";
  AttendancePojo attendancepojo;
  AttendancePojo attendancepojo1,attendancepojo2,attendancepojo3;
  private Integer             rows             = 0;

  // Get the requested page. By default grid sets this to 1.
  private Integer             page             = 0;

  // sorting order - asc or desc
  private String              sord;

  // get index row - i.e. user click to sort.
  private String              sidx;

  // Search Field
  private String              searchField;

  // The Search String
  private String              searchString;

  // Limit the result when using local data, value form attribute rowTotal
  private Integer             totalrows;

  // he Search Operation
  // ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
  private String              searchOper;

  // Your Total Pages
  private Integer             total            = 0;

  // All Records
  private Integer             records          = 0;
  private boolean             loadonce         = false;
  private List<AttendancePojo> gridModel;
  private List<NewsAlertsPojo> newsList;
  
  
  @SuppressWarnings("rawtypes")
public String getData4LeaveDashboard()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			    LeaveHelper leaveHelper=new LeaveHelper();
				String deptHierarchy = (String) session.get("userDeptLevel");
				String username=(String) session.get("uName");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		        //Code for Employee details for particular department....
				
				String user=Cryptography.encrypt(username,DES_ENCRYPTION_KEY);
				List empid=leaveHelper.getEmpId(user,deptHierarchy,connectionSpace);

				if (empid!=null && empid.size()>0)
				{
					List<AttendancePojo> finalDataList = new ArrayList<AttendancePojo>();
					attendancepojo=new AttendancePojo();
					finalDataList=leaveHelper.getAllDashDetails(empid.get(0).toString(),connectionSpace);
					attendancepojo.setDetailList(finalDataList);
					
					attendancepojo1=new AttendancePojo();
					List<AttendancePojo> datalist = new ArrayList<AttendancePojo>();
					datalist=leaveHelper.getAllUpdateData(empid.get(0).toString(),connectionSpace);
					attendancepojo1.setDetailList(datalist);
					
					// For event part HOliday************ 
					attendancepojo2=new AttendancePojo();
					List<AttendancePojo> datalist1 = new ArrayList<AttendancePojo>();
					datalist1=leaveHelper.getAllEventDetails(empid.get(0).toString(),connectionSpace);
					attendancepojo2.setDetailList(datalist1);
					
				// For event part Birthday************ 
					attendancepojo3=new AttendancePojo();
					List<AttendancePojo> datalist2 = new ArrayList<AttendancePojo>();
					datalist2=leaveHelper.getAllBirthDay(empid.get(0).toString(),connectionSpace);
					attendancepojo3.setDetailList(datalist2);
					System.out.println("attendancepojo3 si as========"+attendancepojo3.getDetailList().size());
					
					
					/*finalDataList=leaveHelper.getAllData(empid.get(0).toString(),connectionSpace,deptHierarchy);	
					attendancepojo.setDetailList(finalDataList);
					List<AttendancePojo> datalist = new ArrayList<AttendancePojo>();
					attendancepojo1=new AttendancePojo();
					datalist=leaveHelper.getAllholiday(connectionSpace);
					attendancepojo1.setDetailList(datalist);*/
					
				}
				
				newsList=new ArrayList<NewsAlertsPojo>();
				newsList=new FeedbackDashboardDao().getDashboardAlertsList("News", connectionSpace);
				
				returnResult=SUCCESS;
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else {
			returnResult=LOGIN;
		}
   return returnResult;
	}

  public String getAllDataAbsent(){
	  return SUCCESS;
  }
  
  @SuppressWarnings("rawtypes")
public String absentData()
  {
	  boolean valid=ValidateSession.checkSession();
	  if(valid)
	  {
		  try
		  {
			     /*String fromDate=DateUtil.getCurrentDateUSFormat();
				   String[] datearr=fromDate.split("-");
				   String toDate=datearr[0]+"-"+datearr[1]+"-"+"01";
				  */
			      Map session = ActionContext.getContext().getSession();
				  String fromDate="2013-10-12";
				  String toDate="2013-10-01";
				  String deptHierarchy = (String) session.get("userDeptLevel");
				  String username=(String) session.get("uName");
				  SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				  LeaveHelper leaveHelper=new LeaveHelper();
				  String user=Cryptography.encrypt(username,DES_ENCRYPTION_KEY);
				  List empid=leaveHelper.getEmpId(user,deptHierarchy,connectionSpace);
			      gridModel=new ArrayList<AttendancePojo>();
			      StringBuffer buffer=null;
				  if (empid!=null && empid.size()>0) {
					  buffer =new StringBuffer("SELECT date1,day,lupdate FROM attendence_record WHERE date1 between '"+toDate+"' AND '"+fromDate+"' AND empname='"+empid.get(0).toString()+"' AND status='0' AND attendence='Absent' GROUP BY date1");
				  }
				  List data=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
					  if(data!=null && data.size()>0)
					  {
						  Object[] object=null;
						  for (Iterator iterator = data.iterator(); iterator.hasNext();)
						  {
							object = (Object[]) iterator.next();
							AttendancePojo ap=new AttendancePojo();
							if(object!=null)
							{
								if(object[0]!=null)
								{
									ap.setDate(DateUtil.convertDateToIndianFormat(object[0].toString()));
								}
								
								if(object[1]!=null)
								{
									ap.setDay(object[1].toString());
								}
								
								if(object[2]!=null)
								{
									ap.setLupdate(object[2].toString());
								}
								gridModel.add(ap);
							}
						}
					  }
					  setRecords(gridModel.size());
						int to = (getRows() * getPage());
						@SuppressWarnings("unused")
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
					  setGridModel(gridModel);
					  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					  return SUCCESS;
				 
			
		  }
		  catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	  }
	  else
	  {
		  return LOGIN;
	  }
  
  }
public AttendancePojo getAttendancepojo() {
	return attendancepojo;
}

public void setAttendancepojo(AttendancePojo attendancepojo) {
	this.attendancepojo = attendancepojo;
}

public AttendancePojo getAttendancepojo1() {
	return attendancepojo1;
}

public void setAttendancepojo1(AttendancePojo attendancepojo1) {
	this.attendancepojo1 = attendancepojo1;
}

public AttendancePojo getAttendancepojo2() {
	return attendancepojo2;
}

public void setAttendancepojo2(AttendancePojo attendancepojo2) {
	this.attendancepojo2 = attendancepojo2;
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

public Integer getTotalrows() {
	return totalrows;
}

public void setTotalrows(Integer totalrows) {
	this.totalrows = totalrows;
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

public List<AttendancePojo> getGridModel() {
	return gridModel;
}

public void setGridModel(List<AttendancePojo> gridModel) {
	this.gridModel = gridModel;
}

public List<NewsAlertsPojo> getNewsList() {
	return newsList;
}

public void setNewsList(List<NewsAlertsPojo> newsList) {
	this.newsList = newsList;
}

public AttendancePojo getAttendancepojo3() {
	return attendancepojo3;
}

public void setAttendancepojo3(AttendancePojo attendancepojo3) {
	this.attendancepojo3 = attendancepojo3;
}


}
