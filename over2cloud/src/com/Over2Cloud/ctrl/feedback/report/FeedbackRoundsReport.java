package com.Over2Cloud.ctrl.feedback.report;

import com.Over2Cloud.action.GridPropertyBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.OpenTicketsForDept;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.excelDownloads.DownloadFeedbackExcel;
import com.Over2Cloud.ctrl.feedback.excelDownloads.DownloadFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.feedbackaction.ActionOnFeedback;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.aspose.slides.p883e881b.tr;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class FeedbackRoundsReport extends GridPropertyBean{

		private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
		private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
		private List<ActivityPojo> masterViewList;
		private Map<Integer, String> deptMap = null;
		private Map<String, String> pieChartMap = null;
		private Map<String, String> columnMap;
		private String fromDate;
		private String toDate;
		private String searchFor;
		private String dept;
		private String excelFileName;
		private String selectedId;
		private FileInputStream excelStream;
		private InputStream inputStream;
		private String contentType;
		private String[] columnNames;
		
		@SuppressWarnings("unchecked")
		public String downloadReportData()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					boolean roundType=false;
					if(searchFor.equalsIgnoreCase("Ward Rounds"))
					{
						roundType=true;
					}
					// Add fields Accordingly for new fields
					columnMap = new LinkedHashMap<String, String>();
					columnMap.put("feedback.clientId", "MRD No");
					columnMap.put("feedback.feed_by", "Patient Name");
					columnMap.put("feedback.location", "Location");
					if(roundType)
					{
						columnMap.put("patinfo.admission_time", "Admission Date");
						columnMap.put("patinfo.admit_consultant", "Doctor Name");
						columnMap.put("patinfo.visit_type", "Specialisation Name");
					}
					columnMap.put("dept2.deptName as todept", " To Department");
					columnMap.put("catg.categoryName", "Category");
					columnMap.put("subcatg.subCategoryName", "Sub Category");
					columnMap.put("feedback.feed_brief", "Brief");
					columnMap.put("feedback.status as fstatus", " Status");
					columnMap.put("emp.empName as allot_to", "Alloted To");
					columnMap.put("feedback.resolve_remark", "Action Taken");
					if(roundType)
					{	
						columnMap.put("feedback.feed_registerby", "PCC");
					}
					if (columnMap != null && columnMap.size() > 0)
					{
						session.put("columnMap", columnMap);
					}
					return SUCCESS;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
		
		@SuppressWarnings("unchecked")
		public String downloadReportExcel()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			String excelName=searchFor+" Details";
			excelFileName = "Report"+DateUtil.mergeDateTime()+".xls";
			if (sessionFlag)
			{
				try
				{
					List keyList = new ArrayList();
					List titleList = new ArrayList();
					List data = null;
					boolean roundType=false;
					if(searchFor.equalsIgnoreCase("Ward Rounds"))
					{
						roundType=true;
					}
					if (columnNames != null && columnNames.length > 0)
					{
						keyList = Arrays.asList(columnNames);
						Map<String, String> tempMap = new LinkedHashMap<String, String>();
						tempMap = (Map<String, String>) session.get("columnMap");
						if (session.containsKey("columnMap"))
							session.remove("columnMap");

						for (int index = 0; index < keyList.size(); index++)
						{
							titleList.add(tempMap.get(keyList.get(index)));
						}
						if (keyList != null && keyList.size() > 0)
						{
							data = getQuerryList(keyList,roundType);
						
							if (data != null && data.size() > 0 && titleList != null && keyList != null)
							{
								String orgDetail = (String) session.get("orgDetail");
								String orgName = "";
								if (orgDetail != null && !orgDetail.equals(""))
								{
									orgName = orgDetail.split("#")[0];
								}
								String excelFilePath =new DownloadFeedbackExcel(). writeDataInExcel(data, titleList, keyList, excelName, orgName, true,excelFileName, connectionSpace);
								if (excelFilePath != null)
								{
									excelStream = new FileInputStream(excelFilePath);
								}
							}
						}
					}
					returnResult = SUCCESS;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					session.remove("columnMap");
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		}
		
		private List getQuerryList(List keyList, boolean roundType)
		{

			StringBuilder query = new StringBuilder();
			if (keyList != null && keyList.size() > 0)
			{

				query.append("SELECT ");
				int i = 0;
				for (Iterator it = keyList.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < keyList.size() - 1)
						{
							query.append(obdata.toString() + ",");
						}
						else
						{
							query.append(obdata.toString());
						}
					}
					i++;
				}
			}
			query.append(" FROM feedback_status AS feedback");
			query.append(" INNER JOIN employee_basic emp ON feedback.allot_to= emp.id");
			query.append(" INNER JOIN department dept2 ON feedback.to_dept_subdept= dept2.id");
			query.append(" INNER JOIN feedback_subcategory subcatg ON feedback.subcatg = subcatg.id");
			query.append(" INNER JOIN feedback_category catg ON subcatg.categoryName = catg.id");
			if(roundType)
			{
				query.append(" INNER JOIN patientinfo patinfo ON patinfo.patientId = feedback.patientId");
			}	
			query.append(" WHERE feedback.moduleName='FM' ");
			if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
			{
				String str[] = getFromDate().split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					query.append(" AND feedback.open_date between '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
				}
				else
				{
					query.append(" AND feedback.open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
				}
			}
			if (getSearchFor() != null && !getSearchFor().equalsIgnoreCase("-1"))
			{
				query.append(" AND feedback.via_from= '" + getSearchFor().trim() + " '");
			}
			if (getDept() != null && !getDept().equalsIgnoreCase("-1"))
			{
				query.append(" AND feedback.to_dept_subdept= '" + getDept() + " '");
			}
			query.append(" GROUP BY feedback.id ORDER BY feedback.id desc ");
		//	System.out.println("status QUERRY is as "+query);
			List feedList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			return feedList;
		}

		public String getReportChartData()
		{
			boolean valid = ValidateSession.checkSession();
			if (valid)
			{
				try 
				{
					StringBuilder query=new StringBuilder();
					query.append("SELECT COUNT(feedback.id), dept2.deptName");
					query.append(" FROM feedback_status AS feedback");
					query.append(" INNER JOIN department dept2 ON feedback.to_dept_subdept= dept2.id");
					query.append(" WHERE feedback.moduleName='FM' ");
					if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
					{
						String str[] = getFromDate().split("-");
						if (str[0] != null && str[0].length() > 3)
						{
							query.append(" AND feedback.open_date between '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
						}
						else
						{
							query.append(" AND feedback.open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
						}
					}
					if (getSearchFor() != null && !getSearchFor().equalsIgnoreCase("-1"))
					{
						query.append(" AND feedback.via_from= '" + getSearchFor().trim() + " '");
					}
					if (getDept() != null && !getDept().equalsIgnoreCase("-1"))
					{
						query.append(" AND feedback.to_dept_subdept= '" + getDept() + " '");
					}
					query.append(" GROUP BY feedback.to_dept_subdept ");
					List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						pieChartMap=new LinkedHashMap<String, String>();
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							pieChartMap.put(object[1].toString(),object[0].toString());
						}
						dataList.clear();
					}
					return SUCCESS;
				} catch (Exception e) 
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
		
		public String viewReportHeader()
		{
			boolean valid = ValidateSession.checkSession();
			if (valid)
			{
				try
				{
					deptMap=new LinkedHashMap<Integer, String>();
					List list = cbt.executeAllSelectQuery("SELECT dept.id,dept.deptName FROM department AS dept INNER JOIN feedback_type AS typ ON dept.id=typ.dept_subdept WHERE typ.moduleName='FM' ORDER BY dept.deptName ASC", connectionSpace);
					if (list != null && list.size() > 0)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							Object[] object1 = (Object[]) iterator.next();
							if(object1[0]!=null && object1[1]!=null)
							{	
								deptMap.put((Integer) object1[0], object1[1].toString());
							}	
						}
						list.clear();
					}
					setFromDate(DateUtil.getCurrentDateUSFormat());
					setToDate(DateUtil.getCurrentDateUSFormat());
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

		public String getReportDataPage()
		{
			boolean valid = ValidateSession.checkSession();
			if (valid)
			{
				try
				{
					boolean roundType=false;
					if(searchFor.equalsIgnoreCase("Ward Rounds"))
					{
						roundType=true;
					}
					GridDataPropertyView gpv ;
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("id");
					gpv.setHeadingName("id");
					gpv.setHideOrShow("true");
					gpv.setWidth(10);
					masterViewProp.add(gpv);
					
					if(roundType)
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName("clientId");
						gpv.setHeadingName("MRD No");
						gpv.setHideOrShow("false");
						gpv.setWidth(60);
						masterViewProp.add(gpv);
						
						gpv = new GridDataPropertyView();
						gpv.setColomnName("admission");
						gpv.setHeadingName("Date of Admission");
						gpv.setHideOrShow("false");
						gpv.setWidth(100);
						masterViewProp.add(gpv);
						
						gpv = new GridDataPropertyView();
						gpv.setColomnName("clientName");
						gpv.setHeadingName("Patient Name");
						gpv.setHideOrShow("false");
						gpv.setWidth(120);
						masterViewProp.add(gpv);
						
						gpv = new GridDataPropertyView();
						gpv.setColomnName("docName");
						gpv.setHeadingName("Doctor Name");
						gpv.setHideOrShow("false");
						gpv.setWidth(120);
						masterViewProp.add(gpv);
						
						gpv = new GridDataPropertyView();
						gpv.setColomnName("visitType");
						gpv.setHeadingName("Specialisation Name");
						gpv.setHideOrShow("false");
						gpv.setWidth(120);
						masterViewProp.add(gpv);
						
					}
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("centerCode");
					gpv.setHeadingName("Location");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("dept");
					gpv.setHeadingName("Department");
					gpv.setHideOrShow("false");
					gpv.setWidth(80);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("cat");
					gpv.setHeadingName("Category");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("subCat");
					gpv.setHeadingName("Sub Category");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("brief");
					gpv.setHeadingName("Brief");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					masterViewProp.add(gpv);
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("allot_to");
					gpv.setHeadingName("Allot To");
					gpv.setHideOrShow("false");
					gpv.setWidth(110);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("capa");
					gpv.setHeadingName("Action Taken");
					gpv.setHideOrShow("false");
					gpv.setWidth(240);
					masterViewProp.add(gpv);
					
					if(roundType)
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName("feedRegBy");
						gpv.setHeadingName("PCC");
						gpv.setHideOrShow("false");
						gpv.setWidth(120);
						masterViewProp.add(gpv);
					}
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("fstatus");
					gpv.setHeadingName("Status");
					gpv.setHideOrShow("false");
					gpv.setFormatter("viewStatus");
					gpv.setWidth(50);
					masterViewProp.add(gpv);
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("level");
					gpv.setHeadingName("Level");
					gpv.setHideOrShow("false");
					gpv.setWidth(50);
					masterViewProp.add(gpv);
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("feedStatId");
					gpv.setHeadingName("feedStatId");
					gpv.setHideOrShow("true");
					gpv.setWidth(10);
					masterViewProp.add(gpv);
					
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

		public String viewReportGridData()
		{
			boolean valid = ValidateSession.checkSession();
			if (valid)
			{
				try
				{
					ActivityBoardHelper helper=new ActivityBoardHelper();
					masterViewList = new ArrayList<ActivityPojo>();
					boolean roundType=false;
					if(searchFor.equalsIgnoreCase("Ward Rounds"))
					{
						roundType=true;
					}
					List dataList=getQueryData(roundType,connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						int count=0;
						ActivityPojo pojo=null;
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							pojo = new ActivityPojo();
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								pojo.setId(count++);
								if (object[0] != null && !"".equals(object[0]) )
								{
									pojo.setFeedStatId(object[0].toString());
								}
								else
								{
									pojo.setFeedStatId("NA");
								}
								if (object[1] != null && !"".equals(object[1]) )
								{
									pojo.setClientId(object[1].toString());
								}
								else
								{
									pojo.setClientId("NA");
								}
								if (object[2] != null)
								{
									pojo.setClientName(object[2].toString());
								}
								else
								{
									pojo.setClientName("NA");
								}
								if (object[3] != null && !"".equalsIgnoreCase(object[3].toString()))
								{
									pojo.setCenterCode(object[3].toString());
								}
								else
								{
									pojo.setCenterCode("NA");
								}
								
								if (object[4] != null)
								{
									pojo.setFstatus(object[4].toString());
								}
								else
								{
									pojo.setFstatus("NA");
								}
								
								if (object[5] != null)
								{
									pojo.setDept(object[5].toString());
								}
								else
								{
									pojo.setDept("NA");
								}
								if (object[6] != null)
								{
									pojo.setAllot_to(object[6].toString());
								}
								else
								{
									pojo.setAllot_to("NA");
								}
								
								if (object[7] != null)
								{
									pojo.setCat(object[7].toString());
								}
								else
								{
									pojo.setCat("NA");
								}
								if (object[8] != null)
								{
									pojo.setSubCat(object[8].toString());
								}
								else
								{
									pojo.setSubCat("NA");
								}
								if (object[9] != null)
								{
									pojo.setBrief(object[9].toString());
								}
								else
								{
									pojo.setBrief("NA");
								}
								
								
								if (object[10] != null)
								{
									String name = helper.getEmpNameByUserId(object[10].toString(), connectionSpace);
									if (name != null)
									{
										pojo.setFeedRegBy(name);
									}
									else
									{
										pojo.setFeedRegBy(object[10].toString());
									}
								}
								else
								{
									pojo.setFeedRegBy("NA");
								}
								
								if (object[11] != null && !object[11].equals(""))
								{
									pojo.setCapa(object[11].toString());
								}
								else
								{
									pojo.setCapa("NA");
								}
								if (object[12] != null && !object[12].equals(""))
								{
									pojo.setLevel(object[12].toString());
								}
								else
								{
									pojo.setLevel("NA");
								}
								if(roundType)
								{
									if (object[13] != null && !object[13].equals(""))
									{
										pojo.setVisitType(object[13].toString());
									}
									else
									{
										pojo.setVisitType("NA");
									}
									if (object[14] != null && !object[14].equals(""))
									{
										pojo.setDocName(object[14].toString());
									}
									else
									{
										pojo.setDocName("NA");
									}
									if (object[15] != null && !object[15].equals(""))
									{
										pojo.setAdmission(DateUtil.convertDateToIndianFormat(object[15].toString().split(" ")[0]) + ", " + object[15].toString().split(" ")[1].substring(0, 5));
									}
									else
									{
										pojo.setAdmission("NA");
									}
								}
							}
							masterViewList.add(pojo);
						}
						dataList.clear();
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
		
		private List getQueryData(boolean roundType,SessionFactory connectionSpace)
		{
			List list = null;
			try 
			{
				StringBuilder query = new StringBuilder("");
				query.append("SELECT feedback.id,feedback.clientId,feedback.feed_by,feedback.location,feedback.status, ");
				query.append(" dept2.deptName AS todept,emp.empName AS allot_to,catg.categoryName, ");
				query.append(" subcatg.subCategoryName,feedback.feed_brief,feedback.feed_registerby,feedback.resolve_remark,feedback.level ");
				if(roundType)
				{	
					query.append(" ,patinfo.visit_type,patinfo.admit_consultant,patinfo.admission_time");
				}
				query.append(" FROM feedback_status AS feedback");
				query.append(" INNER JOIN employee_basic emp ON feedback.allot_to= emp.id");
				query.append(" INNER JOIN department dept2 ON feedback.to_dept_subdept= dept2.id");
				query.append(" INNER JOIN feedback_subcategory subcatg ON feedback.subcatg = subcatg.id");
				query.append(" INNER JOIN feedback_category catg ON subcatg.categoryName = catg.id");
				if(roundType)
				{
					query.append(" INNER JOIN patientinfo patinfo ON patinfo.patientId = feedback.patientId");
				}	
				query.append(" WHERE feedback.moduleName='FM' ");
				if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" AND feedback.open_date between '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
					}
					else
					{
						query.append(" AND feedback.open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
					}
				}
				if (getSearchFor() != null && !getSearchFor().equalsIgnoreCase("-1"))
				{
					query.append(" AND feedback.via_from= '" + getSearchFor().trim() + " '");
				}
				if (getDept() != null && !getDept().equalsIgnoreCase("-1"))
				{
					query.append(" AND feedback.to_dept_subdept= '" + getDept() + " '");
				}
				query.append(" GROUP BY feedback.id ORDER BY feedback.id DESC ");
				System.out.println("data query>>>>"+query);
				list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			return list;
		}

		public List<GridDataPropertyView> getMasterViewProp()
		{
			return masterViewProp;
		}

		public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
		{
			this.masterViewProp = masterViewProp;
		}

		public List<ActivityPojo> getMasterViewList()
		{
			return masterViewList;
		}

		public void setMasterViewList(List<ActivityPojo> masterViewList)
		{
			this.masterViewList = masterViewList;
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

		public String getSearchFor() {
			return searchFor;
		}
		public void setSearchFor(String searchFor) {
			this.searchFor = searchFor;
		}

		public Map<Integer, String> getDeptMap() {
			return deptMap;
		}

		public void setDeptMap(Map<Integer, String> deptMap) {
			this.deptMap = deptMap;
		}

		public String getDept() {
			return dept;
		}

		public void setDept(String dept) {
			this.dept = dept;
		}

		public Map<String, String> getPieChartMap() {
			return pieChartMap;
		}

		public void setPieChartMap(Map<String, String> pieChartMap) {
			this.pieChartMap = pieChartMap;
		}

		public Map<String, String> getColumnMap() {
			return columnMap;
		}

		public void setColumnMap(Map<String, String> columnMap) {
			this.columnMap = columnMap;
		}

		public String getExcelFileName() {
			return excelFileName;
		}

		public void setExcelFileName(String excelFileName) {
			this.excelFileName = excelFileName;
		}

		public String getSelectedId() {
			return selectedId;
		}

		public void setSelectedId(String selectedId) {
			this.selectedId = selectedId;
		}

		public FileInputStream getExcelStream() {
			return excelStream;
		}

		public void setExcelStream(FileInputStream excelStream) {
			this.excelStream = excelStream;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String[] getColumnNames() {
			return columnNames;
		}

		public void setColumnNames(String[] columnNames) {
			this.columnNames = columnNames;
		}
		
}	
