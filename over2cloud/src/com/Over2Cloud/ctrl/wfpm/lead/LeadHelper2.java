package com.Over2Cloud.ctrl.wfpm.lead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.SMSTest;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class LeadHelper2 extends SessionProviderClass
{
	CommonOperInterface					coi									= new CommonConFactory().createInterface();
	public static final String	DES_ENCRYPTION_KEY	= "ankitsin";
	public static String				industryId;
	int													idval								= 1;

	public boolean sendMailOnLeadAction(String email, String leadName, LeadActionType leadActionType)
	{
		boolean send = false;
		Mailtest mailtest = new Mailtest();
		StringBuilder mailtext = new StringBuilder("");
		if (leadActionType == LeadActionType.PROSPECTIVE_CLIENT)
		{
			mailtext
					.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><B>Dear " + leadName + ", thanks for showing your interest.</B></FONT></div>");
		}
		else if (leadActionType == LeadActionType.PROSPECTIVE_ASSOCIATE)
		{
			mailtext
					.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><B>Dear " + leadName + ", thanks for showing your interest.</B></FONT></div>");
		}
		else if (leadActionType == LeadActionType.SNOOZE)
		{
			mailtext
					.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><B>Dear " + leadName + ", thanks for showing your interest.</B></FONT></div>");
		}
		if (leadActionType == LeadActionType.LOST)
		{
			mailtext
					.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><B>Dear " + leadName + ", thanks for showing your interest.</B></FONT></div>");
		}

		send = mailtest.confMail("smtp.gmail.com", "465", "services@dreamsol.biz", "dreamsol", email, "Lead Action  For " + leadName + "", mailtext.toString());
		if (send)
		{
			// //System.out.println("Mail Sent Successfully to: " + leadName +
			// ", at: "
			// + email);
		}
		else
		{
			// //System.out.println("ERROR: Mail did not sent Successfully to: " +
			// leadName + ", at: " + email);
		}

		return send;
	}

	public boolean sendSMSOnLeadAction(String mobileNo, String leadName, LeadActionType leadActionType)
	{
		SMSTest fAct = new SMSTest();
		StringBuilder msg = new StringBuilder("");
		boolean send = false;
		if (leadActionType == LeadActionType.PROSPECTIVE_CLIENT)
		{
			msg.append("Dear ");
			msg.append(leadName);
			msg.append(", thanks for showing your interest.");
		}
		else if (leadActionType == LeadActionType.PROSPECTIVE_ASSOCIATE)
		{
			msg.append("Dear ");
			msg.append(leadName);
			msg.append(", thanks for showing your interest.");
		}
		else if (leadActionType == LeadActionType.SNOOZE)
		{
			msg.append("Dear ");
			msg.append(leadName);
			msg.append(", thanks for showing your interest.");
		}
		else if (leadActionType == LeadActionType.LOST)
		{
			msg.append("Dear ");
			msg.append(leadName);
			msg.append(", thanks for showing your interest.");
		}

		send = fAct.sendSMS(msg.toString(), mobileNo);
		if (send)
		{
			// //System.out.println("SMS Sent Successfully to: " + leadName + ", at: "
			// +
			// mobileNo);
		}
		else
		{
			// //System.out.println("ERROR: SMS did not Sent Successfully to: " +
			// leadName + ", at: " + mobileNo);
		}
		return send;
	}

	public boolean updateLeadStatus(SessionFactory factory, LeadActionType leadActionType, String id)
	{
		Map<String, Object> wherClause = new HashMap<String, Object>();
		Map<String, Object> condtnBlock = new HashMap<String, Object>();
		wherClause.put("status", leadActionType.ordinal());
		condtnBlock.put("id", id);
		return coi.updateTableColomn("leadgeneration", wherClause, condtnBlock, factory);
	}

	public String mapClientWithOffering(SessionFactory factory, String offering, int idMAx, String userName, int level)
	{
		int flag = 0;
		String tempOffering[] = offering.split(",");
		for (String H : tempOffering)
		{
			try
			{
				if (H != null && !H.equalsIgnoreCase(""))
				{
					List<InsertDataTable> insertOffering = new ArrayList<InsertDataTable>();
					InsertDataTable ob1 = null;
					ob1 = new InsertDataTable();
					ob1.setColName("clientName");
					ob1.setDataName(idMAx);
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("offeringId");
					ob1.setDataName(H);
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("offeringLevelId");
					ob1.setDataName(level);
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("date");
					ob1.setDataName(DateUtil.getCurrentDateUSFormat());
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("time");
					ob1.setDataName(DateUtil.getCurrentTimeHourMin());
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("isConverted");
					ob1.setDataName("0");
					insertOffering.add(ob1);
					ob1 = new InsertDataTable();
					ob1.setColName("userName");
					ob1.setDataName(userName);
					insertOffering.add(ob1);
					boolean b = coi.insertIntoTable("client_offering_mapping", insertOffering, factory);
					if (b) flag++;
					// int offeringID = coi.getMaxId("client_offering_mapping", factory);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return String.valueOf(flag);
	}

	public boolean createTableLeadHistory(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();

			ob1 = new TableColumes();
			ob1.setColumnname("leadId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("comment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("actionBy");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("convertedTo");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("status");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("snoozdate");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("snooztime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = coi.createTable22("lead_history", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public String mapClientOfferingMapping(SessionFactory factory, String offering, int idMAx, String userName, int level)
	{
		String tempOffering[] = offering.split(",");
		int count = 0;
		for (String H : tempOffering)
		{
			if (H != null && !H.equalsIgnoreCase(""))
			{
				List<InsertDataTable> insertOffering = new ArrayList<InsertDataTable>();
				InsertDataTable ob1 = null;
				ob1 = new InsertDataTable();
				ob1.setColName("associateName");
				ob1.setDataName(idMAx);
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("offeringId");
				ob1.setDataName(H);
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("offeringLevelId");
				ob1.setDataName(level);
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("date");
				ob1.setDataName(DateUtil.getCurrentDateUSFormat());
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("time");
				ob1.setDataName(DateUtil.getCurrentTimeHourMin());
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("isConverted");
				ob1.setDataName("0");
				insertOffering.add(ob1);
				ob1 = new InsertDataTable();
				ob1.setColName("userName");
				ob1.setDataName(userName);
				insertOffering.add(ob1);
				if (coi.insertIntoTable("associate_offering_mapping", insertOffering, factory)) count++;
			}
		}
		return String.valueOf(count);
	}

	public String fetchLeadEmailById(SessionFactory factory, String id)
	{
		String email = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select email from leadgeneration where id = ");
			query.append(id);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				email = list.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return email;
	}

	// returns contactId of employee by empId for department it belongs to
	public String getContactIdOfEmpByEmpId(String empId)
	{
		String cId = null;
		try
		{
			StringBuilder query1 = new StringBuilder("");
			query1.append("select userTable.userID from useraccount userTable inner join employee_basic as eb on userTable.id=eb.useraccountid where eb.id=" + empId);
			List emplUser = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
			for (Iterator it = emplUser.iterator(); it.hasNext();)
			{
				CommonHelper ch = new CommonHelper();
				Object temp = (Object) it.next();
				if (temp != null)
				{
					cId = Cryptography.decrypt(temp.toString(), DES_ENCRYPTION_KEY);
					cId = ch.getEmpDetailsByUserName(CommonHelper.moduleName, cId, connectionSpace)[0];
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return cId;
	}

	@SuppressWarnings("unchecked")
	public List<GridDataPropertyView> fetchLeadDataForEdition(String LeadId)
	{
		List<GridDataPropertyView> mainList = null;
		try
		{
			List<GridDataPropertyView> configData = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
					"lead_generation");
			if (configData == null || configData.isEmpty()) return null;
			// clientDropDown = new ArrayList<ConfigurationUtilBean>();
			StringBuilder queryStart = new StringBuilder();
			StringBuilder queryEnd = new StringBuilder();
			queryStart.append("SELECT ");
			String itrTemp = "";
			for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
			{
				itrTemp = itr.next().getColomnName();
				if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time"))
				{
					itr.remove();
					continue;
				}
				queryStart.append(itrTemp);
				queryStart.append(", ");
			}

			queryEnd.append(queryStart.toString().substring(0, queryStart.toString().lastIndexOf(",")));
			queryEnd.append(" FROM leadgeneration WHERE id = ");
			queryEnd.append(LeadId);

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
	public ArrayList<ArrayList<GridDataPropertyView>> fetchLeadContactDataForEdition(String LeadId)
	{
		ArrayList<ArrayList<GridDataPropertyView>> containerMainList = new ArrayList<ArrayList<GridDataPropertyView>>();
		try
		{
			ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_lead_generation", accountID,
					connectionSpace, "", 0, "table_name", "lead_contact_configuration");
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
			queryEnd.append(" FROM lead_contact_data WHERE leadName = '");
			queryEnd.append(LeadId);
			queryEnd.append("' ");

			List list = coi.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				for (Object obj : list)
				{
					Object[] listArray = (Object[]) obj;
					String listVal = "";
					ArrayList<GridDataPropertyView> configDataTemp = getLeadConfigDataForContactTemp();
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

	public ArrayList<GridDataPropertyView> getLeadConfigDataForContactTemp()
	{
		ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_lead_generation", accountID,
				connectionSpace, "", 0, "table_name", "lead_contact_configuration");
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
	public Map<String, String> fetchAllDesignationOfLeadContact()
	{
		Map<String, String> map = null;
		try
		{
			List tempList = coi.executeAllSelectQuery("select id, designation from lead_contact_data group by designation order by designation", connectionSpace);
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
	
	public String fetchLeadTimeStatusForOffering(EntityType entityType, String userName, String id, String scheduletime)
	{
		String timestatus = "You can schedule";
		StringBuilder query = new StringBuilder();
		if (entityType == EntityType.CLIENT)
		{
			query
					.append("select maxDateTime from client_takeaction where userName = '");
			query.append(userName);
			query.append("' and id = ");
			query.append("(select max(id) from client_takeaction where userName = '");
			query.append(userName);
			query.append("')");
		}
		else if (entityType == EntityType.ASSOCIATE)
		{
				query
				.append("select maxDateTime from associate_takeaction where userName = '");
				query.append(userName);
				query.append("' and id = ");
				query.append("(select max(id) from associate_takeaction where userName = '");
				query.append(userName);
				query.append("')");
		}
		//System.out.println("query.toString()>>"+query.toString());
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			timestatus = CommonHelper.getFieldValue(list.get(0));
			if(timestatus.equalsIgnoreCase(scheduletime))
			{
				timestatus = "This time is already scheduled.";
			}else{
				timestatus = "You can schedule.";
			}
		}
		return timestatus;
	}

}
