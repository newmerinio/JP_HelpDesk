package com.Over2Cloud.ctrl.feedback.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.feedback.FeedbackDashboard;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardCounters extends GridPropertyBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5451825444982480834L;
	private String deptId;
	private String status;
	private String mode;
	private String period;
	private String target;
	private String dataFor;
	private String fromDate;// to be merged 31-10-2014
	private String toDate;// to be merged 31-10-2014
	private String ratingFlag;
	private List<GridDataPropertyView> masterViewProp;
	private List<Object> masterViewList;
	private static final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String feedId;
	private String type;
	private String category, rating;

	@SuppressWarnings("unchecked")
	public String dataInGridMode()
	{
		//System.out.println("b4 data");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				query.append("select count(*) from feedbackdata");
				List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					query.delete(0, query.length());

					query.append(" SELECT ");
					int i = 0;
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							query.append("" + gpv.getColomnName().toString() + ",");
							
						} else
						{
							query.append("" + gpv.getColomnName().toString());
						}
						i++;
					}
					if (searchField!=null && searchField.equalsIgnoreCase("Type") && !(mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("Paper-OPD")))
					{
						query.append(" FROM feedbackdata AS feed");
						query.append(" INNER JOIN feedback_ticket AS tkt ON tkt.feed_data_id=feed.id ");
						query.append(" INNER JOIN feedback_status AS feedback ON feedback.id=tkt.feed_stat_id  ");
					}
					else
					{
						query.append(" FROM feedbackdata AS feed");
					}
					
					if(mode.contains("-"))
					{
						if(searchField!=null && searchField.equalsIgnoreCase("Type"))
						{
							
							String qry="";
							if (mode.split("-")[1].equalsIgnoreCase("IPD"))
							{
								qry=("SELECT field_value FROM feedback_paperform_rating_configuration AS fprc WHERE field_value NOT IN('max_id_feeddbackdata','choseHospital','recommend')");
							} 
							else
							{
								qry=("SELECT field_value FROM feedback_paperform_rating_configuration_opd AS fprc WHERE field_value NOT IN('max_id_feeddbackdata','choseHospital','recommend','targetOn')");
							}
							List list=cbt.executeAllSelectQuery(qry, connectionSpace);
							if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("Paper-OPD"))
							{
								query.append(" INNER JOIN feedback_paper_rating AS fpr ON fpr.max_id_feeddbackdata=feed.id ");
								//System.out.println("searchString"+searchString);
								if(searchString!=null && searchString.equalsIgnoreCase("N"))
								{	
									query.append(" WHERE ( 1 IN"+list.toString().replace("[", "(").replace("]", ")"));
									query.append(" OR 2 IN"+list.toString().replace("[", "(").replace("]", ")")+" OR feed.targetOn='No') ");
								}
								else if(searchString!=null && searchString.equalsIgnoreCase("Y"))
								{
									query.append(" WHERE ( 1 NOT IN"+list.toString().replace("[", "(").replace("]", ")"));
									query.append(" AND 2 NOT IN"+list.toString().replace("[", "(").replace("]", ")")+" AND feed.targetOn='Yes') ");
								}
							}
							else
							{
								if(searchString!=null && searchString.equalsIgnoreCase("N"))
								{
									query.append(" WHERE feed.targetOn='No'");
								}
								else if(searchString!=null && searchString.equalsIgnoreCase("Y"))
								{
									query.append(" WHERE feed.targetOn='Yes'");
								}
							}
							list.clear();
						}
					}
					if (mode != null && !mode.equalsIgnoreCase(""))
					{
						if(searchField!=null && searchField.equalsIgnoreCase("Type"))
						{
							query.append(" AND ");
						}
						else
						{	
							query.append(" WHERE ");
						}
						if(mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("Paper-OPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD"))
						{
							String arr[] = mode.split("-");
							query.append(" feed.mode='" + arr[0] + "' AND feed.compType='" + arr[1] + "'");
						}
						else if (mode.equalsIgnoreCase("IPD") || mode.equalsIgnoreCase("OPD"))
						{
							query.append(" feed.compType='"+mode+"'");
						}
						else
						{
							query.append(" feed.mode='"+mode+"'");
						}
					}
					else
					{
						query.append(" WHERE feed.id!=0 ");
					}
					if (searchField != null && !searchField.equalsIgnoreCase("") && searchString != null && !searchString.equalsIgnoreCase("") && !searchField.equalsIgnoreCase("Type"))
					{
						
						if (mode.equalsIgnoreCase("IPD") || mode.equalsIgnoreCase("OPD"))
						{
							query.append(" AND feed." + searchField + " LIKE '%" + searchString + "%'");
						}
						else
						{
							query.append(" AND feed." + searchField + "='" + searchString + "'");
						}
					}
					query.append(" AND feed.openDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					//System.out.println("jskfgh>>>>>>:::::::::::::::" + query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							int k = 0;
							Object[] obdata = (Object[]) iterator.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (GridDataPropertyView gpv : fieldNames)
							{
								if (obdata[k] != null && !"".equalsIgnoreCase(obdata[k].toString()))
								{
									if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + obdata[k + 1].toString().substring(0, 5));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("feed.admission_time"))
									{
										String date[]=obdata[k].toString().split(" ");
										one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(date[0].toString()) + ", " + date[1].toString().substring(0, 5));
									}
									else
									{
										one.put(gpv.getColomnName(), obdata[k].toString());
									}
								} else
								{
									one.put(gpv.getColomnName().toString(), "NA");
								}
								k++;
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setRecords(masterViewList.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
					returnResult = SUCCESS;
				}
			} catch (Exception e)
			{
				session.remove("masterViewProp");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String getModeData()
	{
		//System.out.println("b4 grid>>>>>>>" + mode);
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				getGridColumnNames();
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void getGridColumnNames()
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.clientId");
			gpv.setHeadingName("MRD No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.patientId");
			gpv.setHeadingName("Patient ID");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.clientName");
			gpv.setHeadingName("Patient Name");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(180);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.compType");
			gpv.setHeadingName("Patient Type");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.openDate");
			gpv.setHeadingName("Date");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.openTime");
			gpv.setHeadingName("Time");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("true");
			gpv.setWidth(120);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.centerCode");
			gpv.setHeadingName("Room/Bed No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.mobNo");
			gpv.setHeadingName("Mobile No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed.admission_time");
			gpv.setHeadingName("Admission Date");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			session.put("masterViewProp", masterViewProp);
			
			if (searchField!=null && searchField.equalsIgnoreCase("Type") && !(mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("Paper-OPD")))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("feedback.status");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(80);
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("feedback.feedback_remarks");
				gpv.setHeadingName("Comments");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(180);
				masterViewProp.add(gpv);
			}
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getDataOnClick()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				getGridColumns(getStatus());
				// System.out.println("Columns are set !!!!");
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String getRatingDataOnClick()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				masterViewProp = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("clientId");
				gpv.setHeadingName("MRD No");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("clientName");
				gpv.setHeadingName("Patient Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(180);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("compType");
				gpv.setHeadingName("Patient Type");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("openDate");
				gpv.setHeadingName("Date");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("openTime");
				gpv.setHeadingName("Time");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(80);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("centerCode");
				gpv.setHeadingName("Room/Bed No");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobNo");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("emailId");
				gpv.setHeadingName("Email ID");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(180);
				masterViewProp.add(gpv);

				session.put("masterViewProp", masterViewProp);
				// System.out.println("Columns are set !!!!");
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void getGridColumns(String status)
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (status != null && status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Level1") || status.equalsIgnoreCase("Level2") || status.equalsIgnoreCase("Level3") || status.equalsIgnoreCase("Level4") || status.equalsIgnoreCase("Level5") || status.equalsIgnoreCase("Re-Assign") || status.equalsIgnoreCase("Total") || status.equalsIgnoreCase("Today") || status.equalsIgnoreCase("Category") || status.equalsIgnoreCase("TotalDept")
						|| status.equalsIgnoreCase("TodayDept"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date")
							&& !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")
							&& !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc")
							&& !gdp.getColomnName().equals("resolveDoc1") && !gdp.getColomnName().equals("pending_alert") && !gdp.getColomnName().equals("resolve_alert"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Snooze"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time")
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason")
							&& !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1") && !gdp.getColomnName().equals("pending_alert") && !gdp.getColomnName().equals("resolve_alert"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("High Priority"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date")
							&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date")
							&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1") && !gdp.getColomnName().equals("pending_alert") && !gdp.getColomnName().equals("resolve_alert"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Ignore"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time")
							&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("transfer_date")
							&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1") && !gdp.getColomnName().equals("pending_alert") && !gdp.getColomnName().equals("resolve_alert"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Resolved"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time")
							&& !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("pending_alert") && !gdp.getColomnName().equals("resolve_alert"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						if (gdp.getColomnName().equalsIgnoreCase("spare_used"))
						{
							gpv.setHeadingName("RCA");
						} else if (gdp.getColomnName().equalsIgnoreCase("resolve_remark"))
						{
							gpv.setHeadingName("CAPA");
						} else
						{
							gpv.setHeadingName(gdp.getHeadingName());
						}

						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						if (gdp.getColomnName().equalsIgnoreCase("resolveDoc"))
						{
							gpv.setFormatter("resolveDoc");
						} else if (gdp.getColomnName().equalsIgnoreCase("resolveDoc1"))
						{
							gpv.setFormatter("resolveDoc1");
						} else if (gdp.getColomnName().equalsIgnoreCase("resolve_remark"))
						{
							gpv.setFormatter("resolveremark");
						} else if (gdp.getColomnName().equalsIgnoreCase("spare_used"))
						{
							gpv.setFormatter("rcaformatter");
						}

						else
						{
							gpv.setFormatter(gdp.getFormatter());
						}
						masterViewProp.add(gpv);
					}
				} else if (status.equalsIgnoreCase("Ticket Opened"))
				{
					if (gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") || gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from")
							|| gdp.getColomnName().equals("location") || gdp.getColomnName().equals("allot_to"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status.equalsIgnoreCase("No Further Action Required"))
				{
					if (gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") || gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from")
							|| gdp.getColomnName().equals("location") || gdp.getColomnName().equals("allot_to"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status.equalsIgnoreCase(""))
				{
					if (gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") || gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from")
							|| gdp.getColomnName().equals("location") || gdp.getColomnName().equals("allot_to"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
			session.put("masterViewProp", masterViewProp);
		} catch (Exception e)
		{
		}
	}

	@SuppressWarnings("unchecked")
	public String dashRatingDataInGrid()
	{
		// System.out.println("inside get data>>>>>>>>>>");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				boolean mFlag = false, hFlag = false, nFlag = false;
				String loggedEmpId = "";
				String userType = (String) session.get("userTpe");
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				ActivityBoardHelper helper = new ActivityBoardHelper();
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpId = object[5].toString();
					}
				}
				List<String> deptIds = new ArrayList<String>();

				if (userType != null && userType.equalsIgnoreCase("M"))
				{
					mFlag = true;
				} else if (userType != null && userType.equalsIgnoreCase("H"))
				{
					deptIds = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);
					hFlag = true;
				} else
				{
					if (userName != null && loggedEmpId != null)
					{
						nFlag = true;
					}
				}
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				// List data = new ArrayList();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				FeedbackDashboard fd = new FeedbackDashboard();
				if (fieldNames != null && fieldNames.size() > 0)
				{
					int i = 0;
					StringBuilder query = new StringBuilder("");
					query1.append(" select ");
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							query.append("feed." + gpv.getColomnName().toString() + ",");
						} else
						{
							query.append("feed." + gpv.getColomnName().toString());
						}
					}
					query1.append("" + query.toString().substring(0, query.length() - 1) + "");
					query1.append(" from feedbackdata as feed");
					query1.append(" inner join feedback_paper_rating as rat on rat.max_id_feeddbackdata=feed.id");
					query1.append(" where rat." + category + "='" + rating + "' and feed.compType='" + status + "' and feed.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' order by feed.openDate desc");
				}

				//System.out.println("Data Rating Query :: " + query1.toString());
				List data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						int k = 0;
						Object[] obdata = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								} else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							} else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// System.out.println("b4 returniign result>>>>>>.......");
		return returnResult;
	}

	// change by kamlesh 29-10-2014 inside line no 1143
	@SuppressWarnings("unchecked")
	public String dashboardDataInGrid()
	{
		// System.out.println("inside get data>>>>>>>>>>");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				boolean mFlag = false, hFlag = false, nFlag = false;
				String loggedEmpId = "";
				String userType = (String) session.get("userTpe");
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				ActivityBoardHelper helper = new ActivityBoardHelper();
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpId = object[5].toString();
					}
				}
				List<String> deptIds = new ArrayList<String>();

				if (userType != null && userType.equalsIgnoreCase("M"))
				{
					mFlag = true;
				} else if (userType != null && userType.equalsIgnoreCase("H"))
				{
					deptIds = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);
					hFlag = true;
				} else
				{
					if (userName != null && loggedEmpId != null)
					{
						nFlag = true;
					}
				}

				List data = new ArrayList();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				FeedbackDashboard fd = new FeedbackDashboard();
				query1.append("select count(*) from feedback_status");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						/*if (getDeptId() != null && (getDeptId().equalsIgnoreCase(String.valueOf(helper.getAdminDeptId(connectionSpace))) || getDeptId().equalsIgnoreCase("")) && (getStatus().equalsIgnoreCase("Pending") || getStatus().equalsIgnoreCase("") || getStatus().equalsIgnoreCase("Ticket Opened") || getStatus().equalsIgnoreCase("No Further Action Required")))
						{
							data.add(getDeptData(fieldNames, mFlag, nFlag, hFlag, loggedEmpId, deptIds, FUA, "feedback_status_feed_paperRating", "1", connectionSpace));
						}*/
						data.add(getDeptData(fieldNames, mFlag, nFlag, hFlag, loggedEmpId, deptIds, FUA, "feedback_status", "0", connectionSpace));
						if (data != null && data.size() > 0)
						{
							for (Iterator it1 = data.iterator(); it1.hasNext();)
							{
								List list = (List) it1.next();
								for (Iterator it = list.iterator(); it.hasNext();)
								{
									int k = 0;
									Object[] obdata = (Object[]) it.next();
									Map<String, Object> one = new HashMap<String, Object>();
									for (GridDataPropertyView gpv : fieldNames)
									{
										if (obdata[k] != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											} else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										} else
										{
											one.put(gpv.getColomnName().toString(), "NA");
										}
										k++;
									}
									Listhb.add(one);
								}
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				session.remove("masterViewProp");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// System.out.println("b4 returniign result>>>>>>.......");
		return returnResult;
	}

	public List getDeptData(List<GridDataPropertyView> fieldNames, boolean mFlag, boolean nFlag, boolean hFlag, String loggedEmpId, List<String> deptIds, FeedbackUniversalAction FUA, String tableName, String flag, SessionFactory connectionSpace)
	{
		int i = 0;
		StringBuilder query = new StringBuilder(" Select ");
		for (GridDataPropertyView gpv : fieldNames)
		{
			if (i < (fieldNames.size() - 1))
			{
				if (gpv.getColomnName().equalsIgnoreCase("feedType"))
				{
					query.append("ft.fbType,");
				} else if (gpv.getColomnName().equalsIgnoreCase("category"))
				{
					query.append("cat.categoryName,");
				} else if (gpv.getColomnName().equalsIgnoreCase("subCatg"))
				{
					query.append("scat.subCategoryName,");
				} else if (gpv.getColomnName().equalsIgnoreCase("by_dept_subdept"))
				{
					query.append("dept1.deptName AS byDept,");
				} else if (gpv.getColomnName().equalsIgnoreCase("to_dept_subdept"))
				{
					query.append("dept2.deptName AS toDept,");
				} else if (gpv.getColomnName().equalsIgnoreCase("allot_to"))
				{
					query.append("emp.empName AS allotTo,");
				} else if (gpv.getColomnName().equalsIgnoreCase("resolve_by"))
				{
					query.append("emp1.empName AS resolveBy,");
				} else
				{
					query.append("feed." + gpv.getColomnName().toString() + ",");
				}
			} else
			{
				query.append("feed." + gpv.getColomnName().toString());
			}
			i++;
		}
		query.append(" FROM " + tableName + " AS feed ");
		query.append(" LEFT JOIN feedback_subcategory As scat ON scat.id=feed.subCatg ");
		query.append(" LEFT JOIN feedback_category As cat ON cat.id=scat.categoryName ");
		query.append(" LEFT JOIN feedback_type As ft ON ft.id=cat.fbType ");
		query.append(" LEFT JOIN department As dept1 ON dept1.id=feed.by_dept_subdept ");
		query.append(" LEFT JOIN department As dept2 ON dept2.id=feed.to_dept_subdept ");
		query.append(" LEFT JOIN employee_basic AS emp ON feed.allot_to= emp.id");
		if (status != null && status.equalsIgnoreCase("Resolved") && !status.equalsIgnoreCase(""))
		{
			query.append(" LEFT JOIN employee_basic AS emp1 ON feed.resolve_by= emp1.id");
		}
		query.append(" WHERE feed.moduleName='FM' and feed.escalation_date!='NA' ");
		if (status != null && !status.equalsIgnoreCase("Level1") && !status.equalsIgnoreCase("Level2") && !status.equalsIgnoreCase("Level3") && !status.equalsIgnoreCase("Level4") && !status.equalsIgnoreCase("Level5") && !status.equalsIgnoreCase("Total") && !status.equalsIgnoreCase("Today") && !status.equalsIgnoreCase("Category") && !status.equalsIgnoreCase("TotalDept") && !status.equalsIgnoreCase("TodayDept") && !status.equalsIgnoreCase(""))
		{
			// query.append(" and feed.escalation_date!='NA' ");
			if (feedId == null)
			{
				query.append(" and feed.status='" + status + "'");
			}
			if (type != null && !type.equalsIgnoreCase(""))
			{
				query.append(" and feed.level='" + type + "'");
			}

			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}

		}
		if (status != null && status.equalsIgnoreCase("Level1") || status.equalsIgnoreCase("Level2") || status.equalsIgnoreCase("Level3") || status.equalsIgnoreCase("Level4") || status.equalsIgnoreCase("Level5") && !status.equalsIgnoreCase(""))
		{
			query.append(" and feed.level='" + status + "' AND status IN('Pending','Snooze','High Priority')");
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.allot_to='" + loggedEmpId + "')");
			}
			query.append(" and subcatg.needEsc='Y' ");
		}
		/*
		 * if (deptId!=null && deptId.equalsIgnoreCase("All")) { query
		 * .append(" AND feed.to_dept_subdept IN ("+deptId+")  " ); }
		 */
		if (status != null && status.equalsIgnoreCase("Total") || status.equalsIgnoreCase("Today"))
		{
			query.append(" and ft.fbType='" + type + "' ");
			if (status.equalsIgnoreCase("Today"))
			{
				query.append(" AND feed.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
			}
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and ( feed.allot_to='" + loggedEmpId + "')");
			}
		}
		if (status != null && status.equalsIgnoreCase("TotalDept") || status.equalsIgnoreCase("TodayDept") && !status.equalsIgnoreCase(""))
		{
			List<String> deptId = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);

			query.append(" and ft.fbType='" + type + "' ");
			// query.append(" and ft.fbType='"+type+
			// "' AND feed.moduleName='FM'  AND feed.to_dept_subdept IN "
			// +deptId.toString().replace("[", "(").replace("]",
			// ")")+" ");
			if (status.equalsIgnoreCase("TodayDept"))
			{
				query.append(" AND feed.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
			}
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and ( feed.allot_to='" + loggedEmpId + "')");
			}
		}
		if (status != null && status.equalsIgnoreCase("Category") && !status.equalsIgnoreCase(""))
		{
			query.append("and cat.id IN (" + type + ")");
			List<String> deptId = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);

			if (hFlag)
			{
				query.append(" and ft.dept_subdept in " + deptId.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.allot_to='" + loggedEmpId + "')");
			}

		}
		/*
		 * if(status.equalsIgnoreCase("") &&
		 * !tableName.equalsIgnoreCase("feedback_status")) {
		 * query.append(" and feed.status='Pending' "); }
		 */
		if (getDeptId() != null && !getDeptId().equalsIgnoreCase(""))
		{
			if (tableName.equalsIgnoreCase("feedback_status"))
			{
				query.append(" and feed.to_dept_subdept='" + getDeptId() + "'");
			} else
			{
				query.append(" and feed.by_dept_subdept='" + getDeptId() + "' and (feed.feed_brief  like '%Poor' or feed.feed_brief  like '%Average') ");
			}
		} else if (getDeptId() != null && getDeptId().equalsIgnoreCase("") && !tableName.equalsIgnoreCase("feedback_status"))
		{
			query.append("  and (feed.feed_brief  like '%Poor' or feed.feed_brief  like '%Average') ");
		}

		if (feedId != null)
		{
			query.append(" and feed.id='" + feedId + "'");
		}
		if (fromDate != null && toDate != null)
		{
			query.append(" and feed.status NOT IN('Ticket Opened','No Further Action Required') and feed.stage='2' and feed.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		}
		// System.out.println("QUERY IS AS :::   "+query);
		List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

		return data;
	}

	public String beforeCountersShow()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}

		} else
		{
			return LOGIN;
		}
	}

	public String getFeedbackModeData()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				getColumnNames();
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void getColumnNames()
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticket_no");
			gpv.setHeadingName("Ticket No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("clientId");
			gpv.setHeadingName("MRD No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("patientId");
			gpv.setHeadingName("Patient ID");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_by");
			gpv.setHeadingName("Patient Name");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("open_date");
			gpv.setHeadingName("Date");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("open_time");
			gpv.setHeadingName("Time");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("true");
			gpv.setWidth(80);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("location");
			gpv.setHeadingName("Room/Bed No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("patMobNo");
			gpv.setHeadingName("Mobile No");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			/*gpv = new GridDataPropertyView();
			gpv.setColomnName("patEmailId");
			gpv.setHeadingName("Email ID");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);*/

			gpv = new GridDataPropertyView();
			gpv.setColomnName("allot_to");
			gpv.setHeadingName("Allot To");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("deptName");
			gpv.setHeadingName("Department");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("categoryName");
			gpv.setHeadingName("Category");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subCategoryName");
			gpv.setHeadingName("Sub Category");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_brief");
			gpv.setHeadingName("Feedback Brief");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedback_remarks");
			gpv.setHeadingName("Comments");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setFormatter("viewStatus");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			masterViewProp.add(gpv);
			
			if (mode != null && mode.equalsIgnoreCase("Appreciation"))
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mode");
				gpv.setHeadingName("Mode");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				masterViewProp.add(gpv);
			}
			if ("Paper-IPD".equalsIgnoreCase(mode) || "SMS-IPD".equalsIgnoreCase(mode) || "SMS-OPD".equalsIgnoreCase(mode) || "Paper-OPD".equalsIgnoreCase(mode))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("openBy");
				gpv.setHeadingName("Open By");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(120);
				masterViewProp.add(gpv);
			}	
			session.put("masterViewProp", masterViewProp);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String dataInGridDashboard()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List data = new ArrayList();
				;
				List list = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from feedback_status");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");

					// System.out.println(mode+"=rating flag>>>"+ratingFlag);
					/*if (((mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("Paper-OPD")) && ratingFlag.equalsIgnoreCase("1")))
					{
						data.add(getDataForGrid("feedback_status_feed_paperRating", fieldNames, cbt, connectionSpace));
					} else*/
					/*if (("109".equalsIgnoreCase(deptId) || "".equalsIgnoreCase(deptId)))
					{
						data.add(getDataForGrid("feedback_status_feed_paperRating", fieldNames, cbt, connectionSpace));
					}*/
					data.add(getDataForGrid("feedback_status", fieldNames, cbt, connectionSpace));
					String time = "";
					if (data != null && data.size() > 0)
					{
						for (Iterator it1 = data.iterator(); it1.hasNext();)
						{
							List dataList = (List) it1.next();
							for (Iterator it = dataList.iterator(); it.hasNext();)
							{
								int k = 0;
								Object[] obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null)
									{
										if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{

											if (obdata[k + 1] != null)
											{
												time = obdata[k + 1].toString().substring(0, 5);
											}
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + time);
										}
										else if(gpv.getColomnName().equalsIgnoreCase("openBy"))
										{
											one.put(gpv.getColomnName(), getEmpName(obdata[k].toString()));
										}
										else
										{
											one.put(gpv.getColomnName(), obdata[k].toString());
										}
									} else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
								}
								Listhb.add(one);
							}
						}
						setMasterViewList(Listhb);
						setRecords(masterViewList.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{

			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private String getEmpName(String openBy) 
	{
		List list=cbt.executeAllSelectQuery("SELECT empName FROM employee_basic WHERE id='"+openBy+"'", connectionSpace);
		if(list!=null && list.size()>0)
		{
			return list.get(0).toString();
		}
		return null;
	}

	public List getDataForGrid(String tableName, List<GridDataPropertyView> fieldNames, CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		List list = null;
		try {
		
		StringBuilder query = new StringBuilder("");
		String[] temp = mode.split("-");
		if (fieldNames != null && fieldNames.size() > 0)
		{
			query.append(" select feedbck.id,feedbck.ticket_no,feedbck.clientId,feedbck.patientId ,feedbck.feed_by,feedbck.open_date,feedbck.open_time,feedbck.location,feedbck.patMobNo,emp.empName,dept.deptName,catg.categoryName,subcatg.subCategoryName,feedbck.feed_brief,feedbck.feedback_remarks,feedbck.status,feedbck.level ");
			/*if (status != null && status.equalsIgnoreCase("escalatedTicket"))
			{
				query.append(",");
			}*/
			if (mode != null && mode.equalsIgnoreCase("Appreciation"))
			{
				query.append(",feedbck.via_from");
			}
			if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD") || mode.equalsIgnoreCase("Paper-OPD"))
			{
				query.append(" ,feed.handledBy AS openBy");
			}
			/*
			 * if (mode.equalsIgnoreCase("Paper") ||
			 * mode.equalsIgnoreCase("SMS")) {
			 * query.append(" ,feed.compType,feed.serviceBy "); } else {
			 * query.append(" ,feed.patient_type,feed.admit_consultant "); }
			 */
			query.append(" from " + tableName + " AS feedbck ");
			/*
			 * if (mode.equalsIgnoreCase("Paper") ||
			 * mode.equalsIgnoreCase("SMS")) { query.append(
			 * " inner join feedbackdata as feed on feed.patientId=feedbck.patientId   "
			 * ); } else { query.append(
			 * " inner join patientinfo as feed on feed.patientId=feedbck.patientId   "
			 * ); }
			 */
			query.append(" inner join department dept on feedbck.to_dept_subdept= dept.id ");
			query.append(" inner join feedback_subcategory subcatg on feedbck.subcatg = subcatg.id ");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
			query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id ");
			query.append(" LEFT JOIN employee_basic as emp on emp.id=feedbck.allot_to ");
			if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD") || mode.equalsIgnoreCase("Paper-OPD"))
			{
				query.append(" inner join feedbackdata as feed on feed.id=feedbck.feedDataId ");
			}
			query.append(" WHERE feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
			if (dataFor != null && dataFor.equalsIgnoreCase("feedbackMode"))
			{
				if (mode != null)
				{

					if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD") || mode.equalsIgnoreCase("Paper-OPD"))
					{
						query.append(" AND feed.compType='" + temp[1] + "' ");
					}
					query.append(" AND feedbck.via_from='" + temp[0] + "' ");
				}
				if (status != null)
				{
					if (status.equalsIgnoreCase("Positive"))
					{
						query.append(" AND (feedbck.escalation_date='NA' || feedbck.escalation_date IS NULL) and feedtype.fbType='Appreciation'");
					} else if (status.equalsIgnoreCase("Negative"))
					{
						query.append(" AND feedbck.escalation_date!='NA' ");
					} else
					{
						query.append(" AND feedbck.status='" + status + "' and feedbck.escalation_date!='NA'");
					}
				}
				if (!tableName.equalsIgnoreCase("feedback_status"))
				{
					query.append(" and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average') ");
				}
			} else if (dataFor != null && dataFor.equalsIgnoreCase("deptAction"))
			{
				if (deptId != null && !deptId.equalsIgnoreCase(""))
				{
					if (tableName.equalsIgnoreCase("feedback_status"))
					{
						query.append(" AND feedbck.to_dept_subdept=" + deptId);
					} else
					{
						query.append(" AND feedbck.by_dept_subdept=" + deptId);
					}

				}
				if (mode != null && mode.equalsIgnoreCase("Action"))
				{
					query.append(" AND feedbck.status NOT IN ('Pending','Ticket Opened','No Further Action Required') AND feedbck.stage='2' ");
				} else if (mode != null && mode.equalsIgnoreCase("Capa"))
				{
					query.append(" AND resolve_remark IS NOT NULL ");
				}
			} else if (dataFor != null && dataFor.equalsIgnoreCase("feedbackNegativeCount"))
			{
				/*
				 * if (mode != null && status.equalsIgnoreCase("Total Seek")) {
				 * query.append(" AND feedbck.via_from ='" + temp[0] +
				 * "' and subCatg IN('469','538')"); } else if (mode != null &&
				 * status.equalsIgnoreCase("Stage1Total")) { if
				 * (tableName.equalsIgnoreCase
				 * ("feedback_status_feed_paperRating")) {
				 * query.append(" and feedbck.via_from ='" + mode +
				 * "'  and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average')"
				 * ); } else { query.append(" and feedbck.via_from ='" + mode +
				 * "' and feedbck.escalation_date!='NA' and (feedbck.stage='1' || (feedbck.stage='2' && feedbck.status IN('Ticket Opened','No Further Action Required'))) "
				 * ); } } else if (mode != null &&
				 * status.equalsIgnoreCase("totalNegative")) { if
				 * (tableName.equalsIgnoreCase
				 * ("feedback_status_feed_paperRating")) {
				 * query.append(" and feedbck.via_from ='" + temp[0] +
				 * "' and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average')"
				 * ); } else { query.append(" and feedbck.via_from ='" + temp[0]
				 * + "' and feedbck.escalation_date!='NA'"); } }
				 *//*
					 * else if (mode != null &&
					 * status.equalsIgnoreCase("totalPositive")) {
					 * 
					 * query.append(" and feedbck.via_from ='" + temp[0] +
					 * "' and feedbck.escalation_date='NA' ");
					 * 
					 * } else if (mode != null &&
					 * status.equalsIgnoreCase("escalatedTicket")) {
					 * query.append(" and feedbck.via_from='" + temp[0] +
					 * "' and feedbck.level='Level2' ");
					 * 
					 * }
					 */
				if (mode != null && status.equalsIgnoreCase("Stage2TicketOpened") && ratingFlag.equalsIgnoreCase("0"))
				{
					query.append(" AND feedbck.via_from ='" + temp[0] + "' and feedbck.status NOT IN('Ticket Opened','No Further Action Required','Ignore')  and feedbck.escalation_date!='NA' and feedbck.stage='2' ");
				}
				else if (mode != null && status.equalsIgnoreCase("Stage2Pending") && ratingFlag.equalsIgnoreCase("0"))
				{
					query.append(" AND feedbck.via_from='" + temp[0] + "' and feedbck.escalation_date!='NA' and feedbck.status='Pending' and stage='2' ");
				}
				else if (mode != null && status.equalsIgnoreCase("Stage2ActionTaken") && ratingFlag.equalsIgnoreCase("0"))
				{
					query.append(" AND feedbck.via_from='" + temp[0] + "' and feedbck.escalation_date!='NA' and feedbck.status IN('Resolved')  and stage='2' ");
				}
			}
			/*
			 * else if (dataFor.equalsIgnoreCase("FeedbackAnalysis")) { if (mode
			 * != null && !mode.equalsIgnoreCase("Suggestion")) {
			 * query.append(" AND feedtype.fbType='" + mode + "'"); } else if
			 * (mode != null) { query.append("  AND feedtype.fbType='" + mode +
			 * "'"); } }
			 */
			if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD") || mode.equalsIgnoreCase("Paper-OPD"))
			{
				query.append(" and feed.compType='" + temp[1] + "'");
			}
			query.append(" AND feedbck.moduleName='FM' group by feedbck.id ");
			//System.out.println("Data query>>>>>>>>>>>" + query);
			list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
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

	public String getRatingFlag()
	{
		return ratingFlag;
	}

	public void setRatingFlag(String ratingFlag)
	{
		this.ratingFlag = ratingFlag;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating(String rating)
	{
		this.rating = rating;
	}

}