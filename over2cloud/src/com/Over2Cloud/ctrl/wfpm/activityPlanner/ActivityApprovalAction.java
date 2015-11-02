package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;


@SuppressWarnings("serial")
public class ActivityApprovalAction extends GridPropertyBean
{
	private List<ConfigurationUtilBean> textBox=null;
	private List<ConfigurationUtilBean> dropDown=null;
	private List<ConfigurationUtilBean> dateField=null;
	private List<GridDataPropertyView> masterViewProp = null;
	private List<Object> masterViewList;
	private String activityId;
	private String activity_for;
	private String activityType;
	private String amount;
	private String date;
	private String sch_date;
	private String comment;
	
	public String beforeApproveActivity()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				setAddPageDataFields();
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
	@SuppressWarnings("unchecked")
	private void setAddPageDataFields()
	{
    	try
    	{
    	    List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_activity_approve_configuration",accountID,connectionSpace,"",0,"table_name","activity_approve_configuration");
    	    textBox = new ArrayList<ConfigurationUtilBean>();
    	    dropDown = new ArrayList<ConfigurationUtilBean>();
    	    dateField = new ArrayList<ConfigurationUtilBean>();
     	    CommonOperInterface coi = new CommonConFactory().createInterface();
			if(date.split("-")[0].length()>3)
			{
				date=DateUtil.convertDateToIndianFormat(date);
			}
			String tableMapped=null;
			String query= "SELECT rel_sub_type  FROM activity_schedule_plan WHERE id= "+activityId;
			List xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
			if (xepenseList != null && xepenseList.size() > 0)
			{
				tableMapped =xepenseList.get(0).toString();
			}
			if(tableMapped!=null)
			{
				if(tableMapped.equalsIgnoreCase("referral_contact_data"))
				{
					query= "SELECT pc.emp_name,at.act_name,at.id,sp.amount,sp.sch_from,sp.sch_to,sp.comments  FROM activity_schedule_plan as sp LEFT JOIN referral_contact_data as ca on ca.id=sp.activity_for " +
							"LEFT JOIN primary_contact as pc on pc.id=ca.map_emp_id LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type " +
							"LEFT JOIN activity_type as at on ps.status = at.id WHERE sp.id= "+activityId;
				}
				else
				{
					query="SELECT ca.org_name,at.act_name,at.id,sp.amount,sp.sch_from,sp.sch_to,sp.comments  FROM activity_schedule_plan as sp LEFT JOIN referral_institutional_data as ca on ca.id=sp.activity_for " +
							"LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type LEFT JOIN activity_type as at on ps.status = at.id " +
							"WHERE sp.id= "+activityId;
				}
				xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							activity_for = object[0].toString();
							activityType = object[1].toString();
							if(object[3]!=null)
							{
								amount = object[3].toString();
							}
							else
							{
								amount = "No";
							}
							if(object[4]!=null && object[5]!=null)
							{
								sch_date = object[4].toString()+" - "+object[5].toString();
							}
							if(object[6]!=null)
							{
								comment = object[6].toString();
							}
						}
					}
					xepenseList.clear();
				}
			}
    	    if(fieldList!=null && fieldList.size()>0)
            {
                for(GridDataPropertyView  gdp : fieldList)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("created_date") )
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        textBox.add(objdata);
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("D"))
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        dropDown.add(objdata);
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("date"))
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        dateField.add(objdata);
                    }
                }
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	@SuppressWarnings("unchecked")
	public String addActivityApproval()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue=request.getParameter(parmName);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
							&& !parmName.equalsIgnoreCase("activityId"))
					{
						 wherClause.put(parmName, paramValue);
					}
				}
				 wherClause.put("activity_action_by", loggedDetails[0]);
				 wherClause.put("activity_flag", "2");
				condtnBlock.put("id",activityId);
				cbt.updateTableColomn("activity_schedule_plan", wherClause, condtnBlock,connectionSpace);
                addActionMessage("Action Taken Successfully !!!");
                
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				addActionError("Oop's there is some problem in adding Group ");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public String beforeActivityTotalGrid()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				viewProperties();
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
	@SuppressWarnings("unchecked")
	private void viewProperties()
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_activity_planner_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_planner_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if(!gdp.getColomnName().equalsIgnoreCase("country") && !gdp.getColomnName().equalsIgnoreCase("state")&&!gdp.getColomnName().equalsIgnoreCase("city")
						&&	!gdp.getColomnName().equalsIgnoreCase("rel_type"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
			session.put("masterViewProp",masterViewProp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String viewTotalTask()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("sc.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("territory"))
							{
								query.append("tt.trr_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else
							{
								query.append("sc."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append(gpv.getColomnName().toString());
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_plan as sc");
				query.append(" LEFT JOIN territory as tt on tt.id=sc.territory ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status ");
				query.append(" WHERE  sc.for_month='"+date+"'");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					ActivityPlannerAction AA=new ActivityPlannerAction();
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("sch_from"))
								{
									one.put(gpv.getColomnName(),  obdata[k].toString()+" To "+ obdata[k+1].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							}
							else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	public List<ConfigurationUtilBean> getTextBox() {
		return textBox;
	}
	public void setTextBox(List<ConfigurationUtilBean> textBox) {
		this.textBox = textBox;
	}
	public List<ConfigurationUtilBean> getDropDown() {
		return dropDown;
	}
	public void setDropDown(List<ConfigurationUtilBean> dropDown) {
		this.dropDown = dropDown;
	}
	public List<ConfigurationUtilBean> getDateField() {
		return dateField;
	}
	public void setDateField(List<ConfigurationUtilBean> dateField) {
		this.dateField = dateField;
	}
	public String getActivityId()
	{
		return activityId;
	}
	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}
	public String getActivity_for()
	{
		return activity_for;
	}
	public void setActivity_for(String activityFor)
	{
		activity_for = activityFor;
	}
	public String getActivityType()
	{
		return activityType;
	}
	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}
	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}
	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}
	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}
	public void setAmount(String amount)
	{
		this.amount = amount;
	}
	public String getAmount()
	{
		return amount;
	}
	public void setSch_date(String sch_date)
	{
		this.sch_date = sch_date;
	}
	public String getSch_date()
	{
		return sch_date;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String getComment()
	{
		return comment;
	}
}
