package com.Over2Cloud.ctrl.wfpm.associate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class AssociateHelper1 extends SessionProviderClass
{
	public static String	industryId;
	public static String	locationId;
	public static String	associateTypeId;
	int										idval	= 1;

	@SuppressWarnings("unchecked")
	public List<GridDataPropertyView> fetchAssociateBasicDataForEdition(String associateId)
	{
		List<GridDataPropertyView> mainList = null;
		try
		{
			List<GridDataPropertyView> configData = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_basic_configuration");
			if (configData == null || configData.isEmpty()) return null;
			StringBuilder queryStart = new StringBuilder();
			StringBuilder queryEnd = new StringBuilder();
			queryStart.append("SELECT ");
			String itrTemp = "";
			for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
			{
				itrTemp = itr.next().getColomnName();
				if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time") || itrTemp.equalsIgnoreCase("status")
						|| itrTemp.equalsIgnoreCase("targetAchieved"))
				{
					itr.remove();
					continue;
				}
				queryStart.append(itrTemp);
				queryStart.append(", ");
			}

			queryEnd.append(queryStart.toString().substring(0, queryStart.toString().lastIndexOf(",")));
			queryEnd.append(" FROM associate_basic_data WHERE id = ");
			queryEnd.append(associateId);

			List list = coi.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				Object[] listArray = (Object[]) list.get(0);
				String listVal = "";
				for (int i = 0; i < configData.size(); i++)
				{
					// System.out.println("columnName:" +
					// configData.get(i).getColomnName());
					// System.out.println("getColType:" + configData.get(i).getColType());
					// System.out.println("value:" +
					// CommonHelper.getFieldValue(listArray[i]));
					listVal = CommonHelper.getFieldValue(listArray[i]);

					if (configData.get(i).getColomnName().equalsIgnoreCase("industry"))
					{
						industryId = listVal;
					}
					if (configData.get(i).getColomnName().equalsIgnoreCase("location"))
					{
						locationId = listVal;
					}
					if (configData.get(i).getColomnName().equalsIgnoreCase("associateType"))
					{
						associateTypeId = listVal;
					}

					configData.get(i).setValue(listVal);
				}
			}

			mainList = configData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mainList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<GridDataPropertyView>> fetchAssociateContactDataForEdition(String associateId)
	{
		ArrayList<ArrayList<GridDataPropertyView>> containerMainList = new ArrayList<ArrayList<GridDataPropertyView>>();
		try
		{
			ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_associate_configuration",
					accountID, connectionSpace, "", 0, "table_name", "associate_contact_configuration");
			if (configData == null || configData.isEmpty()) return null;
			// clientDropDown = new ArrayList<ConfigurationUtilBean>();
			StringBuilder queryStart = new StringBuilder();
			StringBuilder queryEnd = new StringBuilder();
			queryStart.append("SELECT ");
			String itrTemp = "";
			// add extra id field to list 'configData' to be used for contact edition
			GridDataPropertyView gdp = new GridDataPropertyView();
			gdp.setColomnName("id");
			gdp.setColType("T");
			configData.add(0, gdp);

			for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
			{
				itrTemp = itr.next().getColomnName();
				if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time") || itrTemp.equalsIgnoreCase("status")
						|| itrTemp.equalsIgnoreCase("targetAchieved"))
				{
					itr.remove();
					continue;
				}
				queryStart.append(itrTemp);
				queryStart.append(", ");
			}

			queryEnd.append(queryStart.toString().substring(0, queryStart.toString().lastIndexOf(",")));
			queryEnd.append(" FROM associate_contact_data WHERE associateName = '");
			queryEnd.append(associateId);
			queryEnd.append("' ");

			List list = coi.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				for (Object obj : list)
				{
					Object[] listArray = (Object[]) obj;
					String listVal = "";
					ArrayList<GridDataPropertyView> configDataTemp = getAssociateConfigDataForContactTemp();
					for (int i = 0; i < configDataTemp.size(); i++)
					{
						// System.out.println("columnName:" +
						// configDataTemp.get(i).getColomnName());
						// System.out.println("getColType:" +
						// configDataTemp.get(i).getColType());
						// System.out.println("value:" +
						// CommonHelper.getFieldValue(listArray[i]));
						listVal = CommonHelper.getFieldValue(listArray[i]);
						configDataTemp.get(i).setValue(listVal);
					}
					containerMainList.add(configDataTemp);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return containerMainList;
	}

	public ArrayList<GridDataPropertyView> getAssociateConfigDataForContactTemp()
	{
		ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_associate_configuration",
				accountID, connectionSpace, "", 0, "table_name", "associate_contact_configuration");
		if (configData == null || configData.isEmpty()) return null;
		// add extra id field to list 'configData' to be used for contact
		// edition
		GridDataPropertyView gdp = new GridDataPropertyView();
		gdp.setColomnName("id");
		gdp.setColType("T");
		configData.add(0, gdp);
		String itrTemp = "";

		for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
		{
			itrTemp = itr.next().getColomnName();
			if (itrTemp.equalsIgnoreCase("anniversary"))
			{
				itr.next().setAnniversaryId("anniversary" + String.valueOf(idval));

			}
			if (itrTemp.equalsIgnoreCase("birthday"))
			{
				itr.next().setBirthdayId("birthday" + String.valueOf(idval));
			}
			if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time")
					|| itrTemp.equalsIgnoreCase("clientName"))
			{
				itr.remove();
			}
		}
		return configData;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> fetchAllDesignationOfAssociateContact()
	{
		Map<String, String> map = null;
		try
		{
			List tempList = coi
					.executeAllSelectQuery("select id, designation from associate_contact_data group by designation order by designation", connectionSpace);
			if (tempList != null && tempList.size() > 0)
			{
				map = CommonHelper.convertListToMap(tempList, false);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	public int fetchAsociateDept()
	{
		int clientDeptId = 0;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select id from department where deptName = '");
			query.append("Prospective Associate");
			query.append("'");

			List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (subIndustryData != null && !subIndustryData.isEmpty())
			{
				clientDeptId = Integer.parseInt(subIndustryData.get(0).toString());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clientDeptId;
	}

}
