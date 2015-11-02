package com.Over2Cloud.ctrl.krLibrary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

@SuppressWarnings("serial")
public class KRActionable extends GridPropertyBean
{
	private String fromDate;
	private Map<Integer, String> deptList;
	private String shareStatus;
	private String searchTags;
	private String searchValue;
	private List<Object> masterViewList = null;
	private String toDate;

	@SuppressWarnings("unchecked")
	public String actionableViewHeaderPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fromDate = DateUtil.getNextDateAfter(-30);
				KRActionHelper KRH = new KRActionHelper();
				List data = null;
				deptList = new LinkedHashMap<Integer, String>();
				String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
				if (dept != null && !dept[4].equalsIgnoreCase(""))
				{
					data = KRH.getSharedAssignedDepartment(connectionSpace, dept[4]);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								deptList.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
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

	@SuppressWarnings("unchecked")
	public String viewKRInGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					String dataName = null;
					String employeeName = null;
					StringBuilder shareId = new StringBuilder();
					List<Object> Listhb = new ArrayList<Object>();
					String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
					KRActionHelper KRH = new KRActionHelper();
					String loggedContactId = KRH.contactIdLoggenedMulti(empIdofuser, "KR", connectionSpace);
					if (loggedContactId != null && !loggedContactId.toString().equalsIgnoreCase(""))
					{
						String contactId[] = null;
						String query = "SELECT id,emp_name FROM kr_share_data";
						List empData = cbt.executeAllSelectQuery(query, connectionSpace);
						if (empData != null && empData.size() > 0)
						{
							contactId = loggedContactId.split(",");
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[1] != null)
								{
									employeeName = ", " + object[1].toString() + ", ";
									for (String s : contactId)
									{
										if (employeeName.contains(", " + s.trim() + ","))
										{
											shareId.append(object[0].toString() + ",");
										}
									}
								}
							}
							if (shareId.toString() != null && !shareId.toString().equalsIgnoreCase(""))
							{
								dataName = shareId.toString().substring(0, shareId.toString().length() - 1);
							}
						}
					}
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("fieldsname");
					if (fieldNames == null)
					{
						session.remove("fieldsname");
					}
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("sub_type_id"))
								{
									query.append("dept.contact_subtype_name AS groupdept,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("group_name"))
								{
									query.append("krGroup.group_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("empSubType"))
								{
									query.append("share.emp_name as dept,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("sub_group_name"))
								{
									query.append("subGroup.sub_group_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("doc_name"))
								{
									query.append("upload.upload1,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("tag_search"))
								{
									query.append("upload.tag_search,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("kr_name"))
								{
									query.append("upload.kr_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("kr_id"))
								{
									query.append("upload.kr_starting_id,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("document"))
								{
									query.append("share.doc_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("primary_author"))
								{
									query.append("upload.user_name AS primaryAuthor,");
								}
								else
								{
									query.append("share." + gpv.getColomnName().toString() + ",");
								}
							}
							else
							{
								query.append("share." + gpv.getColomnName().toString());
							}
							i++;
						}
					}
					query.append(" from kr_share_data As share ");
					query.append(" LEFT JOIN kr_upload_data AS upload ON share.doc_name=upload.id ");
					query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
					query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
					query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
					query.append(" WHERE ");
					query.append(" share.status='Active' AND share.id IN (" + dataName + ")");

					if (searchTags != null && !searchTags.equalsIgnoreCase(""))
					{
						query.append(" AND upload.tag_search LIKE '%" + searchTags + "%' OR upload.upload1 LIKE '%" + searchTags + "%'");
					}
					else if (fromDate != null && !fromDate.equalsIgnoreCase("") && toDate != null && !toDate.equalsIgnoreCase(""))
					{
						query.append(" AND share.created_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
					else if (searchField != null && !searchField.equalsIgnoreCase("-1") && searchValue != null && !searchValue.equalsIgnoreCase("-1"))
					{
						if (searchValue.equalsIgnoreCase("Pending"))
						{
							query.append(" AND share.actionStatus = '" + searchValue + "'");
						}
						else
						{
							query.append(" AND " + searchField + " = '" + searchValue + "'");
						}

					}
					else
					{
						query.append(" AND share.created_date BETWEEN '" + DateUtil.getNextDateAfter(-30) + "' AND '" + DateUtil.getCurrentDateUSFormat() + "'");
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}
					query.append(" limit " + from + "," + to);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						KRActionHelper KH = new KRActionHelper();
						ComplianceReminderHelper RH = new ComplianceReminderHelper();
						for (Iterator it = data.iterator(); it.hasNext();)
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
									}
									else if (gpv.getColomnName().equalsIgnoreCase("empSubType"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(KH.getMultipleDeptName(obdata[k].toString(), connectionSpace)));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("emp_name"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(KH.getMultipleEmpName(obdata[k].toString(), connectionSpace)));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("frequency"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(RH.getFrequencyName(obdata[k].toString())));
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
						setRecords(masterViewList.size());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public String getShareStatus()
	{
		return shareStatus;
	}

	public void setShareStatus(String shareStatus)
	{
		this.shareStatus = shareStatus;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getSearchTags()
	{
		return searchTags;
	}

	public void setSearchTags(String searchTags)
	{
		this.searchTags = searchTags;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

}
