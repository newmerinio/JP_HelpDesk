package com.Over2Cloud.ctrl.asset.complaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ActivityOnAsset extends ActionSupport
{
	 Map session = ActionContext.getContext().getSession();
	    public static final String DES_ENCRYPTION_KEY = "ankitsin";
		String userName = (String) session.get("uName");
		String accountID = (String) session.get("accountid");
		String deptLevel = (String) session.get("userDeptLevel");
		SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
		
	// Grid Variables Declaration
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
	private boolean loadonce = true;
	// Grid column view
	private String oper;
	private int id;
	
	private String fromDate;
	private String toDate;
	private String newDept_id;
	private String feedStatus;
	
	
	
	List<AssetComplaintPojo> feedbackList = null;
	
	@SuppressWarnings("unchecked")
	public String getMoreFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				AssetComplaintHelper ACH = new AssetComplaintHelper();
				feedbackList = new ArrayList<AssetComplaintPojo>();

				// int count = 0 ;
				List data = null;
				String dept_id = "";

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_id = object[4].toString();
					}
				}
				if (dept_id != null && !dept_id.equals(""))
				{
					
					// System.out.println("Inside Else");
					fromDate=DateUtil.convertDateToUSFormat(fromDate);
					toDate=DateUtil.convertDateToUSFormat(toDate);

					/*		if (fromDate!=null && !fromDate.equals("") && !fromDate.equals("undefined")) {
							String []frmDateArr = fromDate.split("-");
							if (frmDateArr[0].length()<3) {
								fromDate=DateUtil.convertDateToUSFormat(fromDate);
								toDate=DateUtil.convertDateToUSFormat(toDate);
							}
						}*/
						data = ACH.getFeedbackDetail(feedStatus, fromDate, toDate, dept_id, newDept_id,searchField, searchString, searchOper, connectionSpace);
					}

					
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					feedbackList = ACH.setFeedbackValues(data, deptLevel, getFeedStatus());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}


	public Integer getRows()
	{
		return rows;
	}


	public void setRows(Integer rows)
	{
		this.rows = rows;
	}


	public Integer getPage()
	{
		return page;
	}


	public void setPage(Integer page)
	{
		this.page = page;
	}


	public String getSord()
	{
		return sord;
	}


	public void setSord(String sord)
	{
		this.sord = sord;
	}


	public String getSidx()
	{
		return sidx;
	}


	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}


	public String getSearchField()
	{
		return searchField;
	}


	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}


	public String getSearchString()
	{
		return searchString;
	}


	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}


	public String getSearchOper()
	{
		return searchOper;
	}


	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}


	public Integer getTotal()
	{
		return total;
	}


	public void setTotal(Integer total)
	{
		this.total = total;
	}


	public Integer getRecords()
	{
		return records;
	}


	public void setRecords(Integer records)
	{
		this.records = records;
	}


	public boolean isLoadonce()
	{
		return loadonce;
	}


	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}


	public String getOper()
	{
		return oper;
	}


	public void setOper(String oper)
	{
		this.oper = oper;
	}


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public List<AssetComplaintPojo> getFeedbackList()
	{
		return feedbackList;
	}


	public void setFeedbackList(List<AssetComplaintPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
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


	public String getNewDept_id()
	{
		return newDept_id;
	}


	public void setNewDept_id(String newDept_id)
	{
		this.newDept_id = newDept_id;
	}


	public String getFeedStatus()
	{
		return feedStatus;
	}


	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	
	
}
