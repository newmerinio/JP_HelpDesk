package com.Over2Cloud.ctrl.wfpm.associate;

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

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.wfpm.report.ReportGeneration;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TakeActionOnAssociate extends ActionSupport implements ServletRequestAware
{

	static final Log							log							= LogFactory.getLog(TakeActionOnAssociate.class);
	private Map										session					= ActionContext.getContext().getSession();
	private String								userName				= (String) session.get("uName");
	private SessionFactory				connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private String								empIdofuser			= (String) session.get("empIdofuser");							// o
																																																		// -
																																																		// 100000
	private String								id;
	private String								associateName;
	private String								currentStatus;
	private List									actionStatus;
	private String								selectRows;
	private HttpServletRequest		request;
	private Map<Integer, String>	targetKpiLiist	= null;
	private String								targetAchived;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			query.append("select * from prospectiveAssociateContact where id='" + getId() + "'");
			List associateList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (associateList != null)
			{
				for (Iterator it = associateList.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					associateName = (String) obdata[2];

				}
			}
			String query1 = "select * from associatestatus";
			List associate = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			actionStatus = new ArrayList();
			if (associate != null)
			{
				for (Iterator it = associate.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					actionStatus.add(obdata[1].toString());
				}
			}

			String query2 = "select * from takeaction where associateId=" + getId();
			associate.removeAll(associate);
			associate = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			if (associate != null)
			{
				for (Iterator it = associate.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					currentStatus = (obdata[3].toString());
				}
			}

			/**
			 * getting mapped target of employee
			 */
			targetKpiLiist = new HashMap<Integer, String>();
			targetKpiLiist = CommonWork.getTargetOfEmployee(userName, connectionSpace);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method execute() of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String takeActionOnAssociate()
	{
		String returnString = ERROR;

		try
		{

			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			boolean targetSelected = false;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;

			boolean status = false;
			while (it.hasNext())
			{
				String parmName = it.next().toString();
				String paramValue = request.getParameter(parmName);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null)
				{

					if (parmName.equalsIgnoreCase("maxDate"))
					{
						String date[] = paramValue.split(" ");
						ob = new InsertDataTable();
						ob.setColName(parmName);
						ob.setDataName(DateUtil.convertDateToUSFormat(date[0]) + " " + date[1]);

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
			ob = new InsertDataTable();
			ob.setColName("user");
			ob.setDataName(userName);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("madeOn");
			ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("actionDate");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("actionTime");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("flag");
			ob.setDataName("0".trim());
			insertData.add(ob);

			status = cbt.insertIntoTable("takeaction", insertData, connectionSpace);

			if (!getTargetAchived().equalsIgnoreCase("-1") && !getTargetAchived().equalsIgnoreCase("") && getTargetAchived() != null)
			{
				DSRgerneration ta = new DSRgerneration();
				String tempempIdofuser[] = empIdofuser.split("-");
				ta.setDSRRecords(getTargetAchived(), Integer.parseInt(tempempIdofuser[1]), DSRMode.KPI, DSRType.ONLINE);
			}

			if (status)
			{
				addActionMessage("Action Taken!!!");
				returnString = SUCCESS;
			}
			else
			{
				addActionMessage("Oops There is some error in data!");
				returnString = SUCCESS;
			}
			returnString = SUCCESS;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method takeActionOnAssociate() of class " + getClass(), e);
			e.printStackTrace();
			returnString = ERROR;
		}
		return returnString;
	}

	public String setTakeActionOnAssociate()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();

			wherClause.put("flag", "1");
			wherClause.put("actionBy", userName);
			condtnBlock.put("id", getId());
			boolean status = cbt.updateTableColomn("takeaction", wherClause, condtnBlock, connectionSpace);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method setTakeActionOnAssociate() of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String convertToLost()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			if (selectRows != null && !getSelectRows().equalsIgnoreCase(""))
			{
				String rows[] = selectRows.split(",");

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();

				wherClause.put("existAssociate", "2");

				for (int i = 0; i < rows.length; i++)
				{
					condtnBlock.put("id", rows[i]);
					cbt.updateTableColomn("prospectiveAssociate", wherClause, condtnBlock, connectionSpace);
				}

			}
			addActionMessage("Peospective Associate Converted Into Existing!!!");
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <convertToExist> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String convertToAsso()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			if (selectRows != null)
			{
				String rows[] = selectRows.split(",");

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();

				wherClause.put("existAssociate", "1");

				for (int i = 0; i < rows.length; i++)
				{
					condtnBlock.put("id", rows[i]);
					cbt.updateTableColomn("prospectiveAssociate", wherClause, condtnBlock, connectionSpace);
				}

			}
			addActionMessage("Peospective Associate Converted Into Existing!!!");
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <convertToExist> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAssociateName()
	{
		return associateName;
	}

	public void setAssociateName(String associateName)
	{
		this.associateName = associateName;
	}

	public String getCurrentStatus()
	{
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}

	public List getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(List actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getSelectRows()
	{
		return selectRows;
	}

	public void setSelectRows(String selectRows)
	{
		this.selectRows = selectRows;
	}

	public Map<Integer, String> getTargetKpiLiist()
	{
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist)
	{
		this.targetKpiLiist = targetKpiLiist;
	}

	public String getTargetAchived()
	{
		return targetAchived;
	}

	public void setTargetAchived(String targetAchived)
	{
		this.targetAchived = targetAchived;
	}

}
