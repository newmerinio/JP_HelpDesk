/**
 * @author Anoop, 12-2-2014
 */
package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.lead.LeadActionControlDao;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class DashboardTakeActionHelper extends SessionProviderClass
{
	public JSONObject fetchTakeActionPageData(SessionFactory factory, int id, ActivityType type)
	{
		JSONObject jsonObject = null;
		try
		{
			// System.out.println("111111111111111111111111111111nnnnnnnnn");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (type == ActivityType.client)
			{
				query
						.append("select cbd.id 'clientId', cbd.clientName, cbd.starRating, off.id 'offeringId', off.subofferingname, ccd.id 'contactId', ccd.personName, cs.statusName, ");
				query
						.append("cta.statusId, cta.maxDateTime,cbd.address from client_basic_data as cbd, offeringlevel3 as off, client_contact_data as ccd, client_status as cs, client_takeaction as cta ");
				query.append("where cta.contacId = ccd.id and ccd.clientName = cbd.id and cta.offeringId = off.id and cta.statusId = cs.id ");
				query.append("and cta.id = ");
				query.append(id);
			}
			else if (type == ActivityType.associate)
			{
				query
						.append("select cbd.id 'clientId', cbd.associateName, cbd.associateRating, off.id 'offeringId', off.subofferingname, ccd.id 'contactId', ccd.name, cs.statusname, ");
				query
						.append("ata.statusId, ata.maxDateTime,abd.address from associate_basic_data as abd, offeringlevel3 as off, associate_contact_data as ccd, associatestatus as cs, associate_takeaction as cta ");
				query.append("where cta.contacId = ccd.id and ccd.associateName = cbd.id and cta.offeringId = off.id and cta.statusId = cs.id ");
				query.append("and cta.id = ");
				query.append(id);
			}

			// System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				Object[] object = (Object[]) list.get(0);
				jsonObject = new JSONObject();
				jsonObject.put("CLIENT_ID", object[0].toString());
				
				jsonObject.put("CLIENT_NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

				jsonObject.put("CLIENT_RATING", (object[2] == null || object[2].toString().equals("")) ? "NA" : object[2].toString());

				jsonObject.put("OFFERING_ID", object[3].toString());

				jsonObject.put("OFFERING_NAME", (object[4] == null || object[4].toString().equals("")) ? "NA" : object[4].toString());

				jsonObject.put("CONTACT_ID", object[5].toString());

				jsonObject.put("CONTACT_NAME", (object[6] == null || object[6].toString().equals("")) ? "NA" : object[6].toString());

				jsonObject.put("CURRENT_STATUS", (object[7] == null || object[7].toString().equals("")) ? "NA" : object[7].toString());
				jsonObject.put("CURRENT_STATUS_ID", (object[8] == null || object[8].toString().equals("")) ? "NA" : object[8].toString());
				jsonObject.put("MAX_DATE_TIME", (object[9] == null || object[9].toString().equals("")) ? "NA" : object[9].toString());
				jsonObject.put("CLIENT_ADDRESS", (object[10] == null || object[10].toString().equals("")) ? "NA" : object[10].toString());

				// System.out.println("jsonObject.size():" + jsonObject.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jsonObject;
	}

	public String fetchIdData(SessionFactory factory, int id, ActivityType type)
	{
		String idData = null;
		try
		{
			// System.out.println("111111111111111111111111111111nnnnnnnnn");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (type == ActivityType.client)
			{
				query.append("select cbd.id 'clientId', cbd.clientName ");
				query.append("from client_basic_data as cbd, offeringlevel3 as off, client_contact_data as ccd, client_status as cs, client_takeaction as cta ");
				query.append("where cta.contacId = ccd.id and ccd.clientName = cbd.id and cta.offeringId = off.id and cta.statusId = cs.id ");
				query.append("and cta.id = ");
				query.append(id);
			}
			else if (type == ActivityType.associate)
			{
				query.append("select cbd.id 'clientId', cbd.associateName ");
				query.append("from associate_basic_data as cbd, offeringlevel3 as off, associate_contact_data as ccd, associatestatus as cs, associate_takeaction as cta ");
				query.append("where cta.contacId = ccd.id and ccd.associateName = cbd.id and cta.offeringId = off.id and cta.statusId = cs.id ");
				query.append("and cta.id = ");
				query.append(id);
			}

			System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				for (Object c : list)
				{
						Object[] dataC = (Object[]) c;
						if(dataC[0] != null)
						{
							idData = dataC[0].toString();
						}
						
					//idData.put(dataC[0].toString(), dataC[1].toString());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return idData;
	}
	
	public boolean saveTakeActionData(boolean isRescheduled, String organizationId, SessionFactory factory, ActivityType activityType, String... arg)
	{
		boolean statusFlag = false;
		try
		{
			InsertDataTable ob = null;
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			String[] dateVal = arg[3].split(" ");// dd-mm-yyyy hh:mm
			arg[3] = DateUtil.convertDateToUSFormat(dateVal[0].trim()) + " " + dateVal[1];

			ob = new InsertDataTable();
			ob.setColName("contacId");
			ob.setDataName(arg[0]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("offeringId");
			ob.setDataName(arg[1]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("statusId");
			ob.setDataName(arg[2]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("maxDateTime");
			ob.setDataName(arg[3]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("comment");
			ob.setDataName(arg[4]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("isFinished");
			ob.setDataName(EntityTakeActionIsFinishedType.OPEN.ordinal());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(arg[5]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			if (activityType == ActivityType.client)
			{
				if (arg.length - 1 >= 6)
				{
					ob = new InsertDataTable();
					ob.setColName("client_takeaction_id");
					ob.setDataName(arg[6]);
					insertData.add(ob);
				}

				if (arg.length - 1 >= 7)
				{
					ob = new InsertDataTable();
					ob.setColName("closingComment");
					ob.setDataName(arg[7]);
					insertData.add(ob);
				}

				
				statusFlag = coi.insertIntoTable("client_takeaction", insertData, factory);
				if (statusFlag)
				{
					if (!isRescheduled)
					{
						// Fill auto KPI for 'Total Calls'
						LeadActionControlDao lacd = new LeadActionControlDao();
						lacd.insertInToKPIAutofillTable(empIdofuser, "2", userName, connectionSpace, organizationId, "Prospective Client");
						String statusName = fetchStatusNameById(factory, arg[2], activityType);
						if (statusName.contains("Intro Mail"))
						{
							// Fill auto KPI for 'Intro Mail'
							lacd.insertInToKPIAutofillTable(empIdofuser, "4", userName, connectionSpace, organizationId, "Prospective Client");
						}
						else if (statusName.contains("Meeting"))
						{
							// Fill auto KPI for 'Meeting Generation'
							lacd.insertInToKPIAutofillTable(empIdofuser, "5", userName, connectionSpace, organizationId, "Prospective Client");
						}

						// Fill auto KPI for 'Productive Calls'
						lacd.insertInToKPIAutofillTable(empIdofuser, "3", userName, connectionSpace, organizationId, "Prospective Client");
					}
				}
			}
			else if (activityType == ActivityType.associate)
			{
				if (arg.length - 1 >= 6)
				{
					ob = new InsertDataTable();
					ob.setColName("associate_takeaction_id");
					ob.setDataName(arg[6]);
					insertData.add(ob);
				}

				if (arg.length - 1 >= 7)
				{
					ob = new InsertDataTable();
					ob.setColName("associateComment");
					ob.setDataName(arg[7]);
					insertData.add(ob);
				}

				statusFlag = coi.insertIntoTable("associate_takeaction", insertData, factory);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusFlag;
	}

	/**
	 * Convert client to existing for one offering
	 */
	public boolean convertToExisting(SessionFactory factory, ActivityType type, String... arg)
	{

		boolean flag = false;
		try
		{
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			wherClause.put("isConverted", "1");
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());
			wherClause.put("poNumber", arg[0]);
			wherClause.put("poDate", arg[1]);

			condtnBlock.put("offeringId", arg[3]);

			if (type == ActivityType.client)
			{
				wherClause.put("offering_price", arg[4]);
				wherClause.put("po_attachment", arg[5]);
				wherClause.put("comment", arg[6]);
				condtnBlock.put("clientName", arg[2]);
				flag = coi.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, factory);
			}
			else if (type == ActivityType.associate)
			{
				condtnBlock.put("associateName", arg[2]);
				flag = coi.updateTableColomn("associate_offering_mapping", wherClause, condtnBlock, factory);
			}

			// close activity while converting to existing
			closeActivity(factory, type, Integer.parseInt(arg[7]), "Converted to existing", EntityTakeActionIsFinishedType.CLOSE, "contacId");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Close activity
	 */
	public boolean closeActivity(SessionFactory factory, ActivityType type, int id, String comment, EntityTakeActionIsFinishedType finishedType,
			String conditionField)
	{
		boolean flag = false;
		try
		{
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			wherClause.put("isFinished", finishedType.ordinal());
			wherClause.put("finishedDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("finishedTime", DateUtil.getCurrentTime());
			wherClause.put("closingComment", comment);

			condtnBlock.put(conditionField, id);
			if (type == ActivityType.client)
			{
				flag = coi.updateTableColomn("client_takeaction", wherClause, condtnBlock, factory);
			}
			else if (type == ActivityType.associate)
			{
				flag = coi.updateTableColomn("associate_takeaction", wherClause, condtnBlock, factory);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean rescheduleActivity(String organizationId, SessionFactory factory, ActivityType type, int id, String... arg)
	{

		boolean flag = false;
		try
		{

			boolean closeFlag = false;
			boolean statusFlag = false;
			closeFlag = closeActivity(factory, type, id, "Rescheduled", EntityTakeActionIsFinishedType.RESCHEDULE, "id");
			if (closeFlag)
			{
				statusFlag = saveTakeActionData(true, organizationId, factory, type, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5], String.valueOf(id));
			}
			if (closeFlag && statusFlag)
			{
				flag = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Convert client to lost for one offering
	 */
	public boolean convertToLost(SessionFactory factory, ActivityType type, String... arg)
	{
		boolean flag = false;
		try
		{
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			wherClause.put("isConverted", EntityForOffering.lost.ordinal());
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());
			wherClause.put("RCA", arg[0]);
			wherClause.put("CAPA", arg[1]);
			wherClause.put("lostId", arg[4]);

			condtnBlock.put("offeringId", arg[3]);

			if (type == ActivityType.client)
			{
				condtnBlock.put("clientName", arg[2]);
				flag = coi.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, factory);
			}
			else if (type == ActivityType.associate)
			{
				condtnBlock.put("associateName", arg[2]);
				flag = coi.updateTableColomn("associate_offering_mapping", wherClause, condtnBlock, factory);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean reassignActivity(String organizationId, SessionFactory factory, ActivityType type, int id, String... arg)
	{

		boolean flag = false;
		try
		{
			boolean closeFlag = false;
			boolean statusFlag = false;
			closeFlag = closeActivity(factory, type, id, "Reassigned", EntityTakeActionIsFinishedType.REASSIGN, "id");
			if (closeFlag)
			{
				StringBuilder query1 = new StringBuilder();
				query1.append("select ua.userID from useraccount as ua, employee_basic as eb " + "where eb.useraccountid = ua.id and eb.id IN (");
				query1.append(arg[5]);
				query1.append(")");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				Object userIDEncripted = coi.executeAllSelectQuery(query1.toString(), factory).get(0).toString();
				String mapByEmp = "";
				if (userIDEncripted != null)
				{
					final String DES_ENCRYPTION_KEY = "ankitsin";
					mapByEmp = Cryptography.decrypt(userIDEncripted.toString(), DES_ENCRYPTION_KEY);
					mapByEmp = new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, mapByEmp, factory)[0];
					statusFlag = saveTakeActionData(true, organizationId, factory, type, arg[0], arg[1], arg[2], DateUtil.convertDateToIndianFormat(arg[3].split(" ")[0])
							+ " " + arg[3].split(" ")[1], arg[4], mapByEmp, String.valueOf(id));
				}
			}
			if (closeFlag && statusFlag)
			{
				flag = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public JSONObject fetchEntityNameAndAddress(SessionFactory factory, int id, ActivityType type)
	{
		JSONObject jsonObject = null;
		try
		{
			// System.out.println("111111111111111111111111111111nnnnnnnnn");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (type == ActivityType.client)
			{
				query.append("select cbd.clientName, cbd.address ");
				query.append("from client_basic_data as cbd, client_contact_data as ccd, client_takeaction as cta ");
				query.append("where cbd.id = ccd.clientName and ccd.id = cta.contacId ");
				query.append("and cta.id = ");
				query.append(id);
			}
			else if (type == ActivityType.associate)
			{
				query.append("select cbd.associateName, cbd.address ");
				query.append("from associate_basic_data as cbd, associate_contact_data as ccd, associate_takeaction as cta ");
				query.append("where cbd.id = ccd.associateName and ccd.id = cta.contacId ");
				query.append("and cta.id = ");
				query.append(id);
			}

			// System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				Object[] object = (Object[]) list.get(0);
				jsonObject = new JSONObject();
				jsonObject.put("NAME", (object[0] == null || object[0].toString().equals("")) ? "NA" : object[0].toString());

				jsonObject.put("ADDRESS", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

				// System.out.println("jsonObject.size():" + jsonObject.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jsonObject;
	}

	public Map<String, String> fetchEntityStatus(SessionFactory factory, ActivityType type)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query = null;
			if (type == ActivityType.client)
			{
				query = "select id, statusName from client_status order by statusName asc ";
			}
			else if (type == ActivityType.associate)
			{
				query = "select id, statusname from associatestatus order by statusname asc ";
			}
			List list = coi.executeAllSelectQuery(query, factory);
			if (list != null && list.size() > 0)
			{
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					map.put(data[0].toString(), (data[1] == null || data[1].toString().equals("")) ? "NA" : data[1].toString());
				}
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}

	public Map<String, String> fetchOfferingForActivity(SessionFactory factory, String offeringId)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, subofferingname from offeringlevel3 where id not in (");
			query.append(offeringId);
			query.append(") order by subofferingname asc ");
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					map.put(data[0].toString(), (data[1] == null || data[1].toString().equals("")) ? "NA" : data[1].toString());
				}
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}

	// More actions
	public int moreAction(String organizationId, SessionFactory factory, ActivityType activityType, String... arg)
	{
		int value = 0;
		try
		{
			String[] chkOtherValues = arg[0].split(",");
			String[] statusIdOther = (arg[1] != null && !arg[1].equals("")) ? arg[1].split(",") : new String[chkOtherValues.length];
			String[] offeringIdOther = (arg[2] != null && !arg[2].equals("")) ? arg[2].split(",") : new String[chkOtherValues.length];
			String[] maxDateTimeOther = (arg[3] != null && !arg[3].equals("")) ? arg[3].split(",") : new String[chkOtherValues.length];
			String[] commentOther = (arg[4] != null && !arg[4].equals("")) ? arg[4].split(",") : new String[chkOtherValues.length];

			for (int i = 0; i < chkOtherValues.length; i++)
			{
				if (chkOtherValues[i] != null && !chkOtherValues.equals("") && statusIdOther[i] != null && !statusIdOther.equals("") && offeringIdOther[i] != null
						&& !offeringIdOther.equals("") && maxDateTimeOther[i] != null && !maxDateTimeOther.equals("") && commentOther[i] != null
						&& !commentOther.equals(""))
				{

					boolean statusFlag = saveTakeActionData(false, organizationId, factory, activityType, arg[5], offeringIdOther[i].trim(), statusIdOther[i].trim(),
							maxDateTimeOther[i].trim(), commentOther[i].trim(), arg[6]);

					if (statusFlag) value++;

					// System.out.println("statusFlag:" + statusFlag);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

	public String saveMOM(SessionFactory factory, ActivityType activityType, String... arg)
	{
		String val = null;
		try
		{
			if (new MOMHelper().creteTableMOM(factory))
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				InsertDataTable ob = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				ob = new InsertDataTable();
				ob.setColName("momDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(arg[0]));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("fromTime");
				ob.setDataName(arg[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("toTime");
				ob.setDataName(arg[2]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("agenda");
				ob.setDataName(arg[3]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("entityType");
				ob.setDataName(String.valueOf(activityType.ordinal()));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("entity_takeaction_id");
				ob.setDataName(arg[4]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(arg[5]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				boolean statusFlag = coi.insertIntoTable("entity_takeaction_mom", insertData, factory);
				if (statusFlag)
				{
					StringBuilder query = new StringBuilder();
					query.append("select id from entity_takeaction_mom where entity_takeaction_id = ");
					query.append(arg[4]);
					List list = coi.executeAllSelectQuery(query.toString(), factory);
					if (list != null && list.size() > 0)
					{
						val = list.get(0).toString();
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return val;
	}

	public int saveMOMEntityContact(SessionFactory factory, String... arg)
	{
		int count = 0;
		try
		{
			if (new MOMHelper().creteTableMOMEntityContact(factory))
			{
				// System.out.println("Table created#saveMOMEntityContact");
				String[] ids = arg[0].split(",");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("clientContactId");
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_mom_id");
				ob1.setDataName(arg[1]);
				insertData.add(ob1);

				for (int i = 0; i < ids.length; i++)
				{
					if (!ids[i].trim().equals(""))
					{
						ob.setDataName(ids[i].trim());// setting dynamic values
						boolean statusFlag = coi.insertIntoTable("entity_takeaction_mom_clientcontact", insertData, factory);
						if (statusFlag) count++;

					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public int saveMOMEmployee(SessionFactory factory, String... arg)
	{
		int count = 0;
		try
		{
			if (new MOMHelper().creteTableMOMEmployee(factory))
			{
				// System.out.println("Table created#saveMOMEmployee");
				String[] ids = arg[0].split(",");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("empId");
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_mom_id");
				ob1.setDataName(arg[1]);
				insertData.add(ob1);

				for (int i = 0; i < ids.length; i++)
				{
					if (!ids[i].trim().equals(""))
					{
						ob.setDataName(ids[i].trim());
						boolean statusFlag = coi.insertIntoTable("entity_takeaction_mom_employee", insertData, factory);
						if (statusFlag) count++;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public int saveMOMDiscussion(SessionFactory factory, String... arg)
	{
		int count = 0;
		try
		{
			if (new MOMHelper().creteTableMOMDiscussion(factory))
			{
				String[] ids = arg[0].split(",");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("discussion");
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_mom_id");
				ob1.setDataName(arg[1]);
				insertData.add(ob1);

				for (int i = 0; i < ids.length; i++)
				{
					if (!ids[i].trim().equals(""))
					{
						ob.setDataName(ids[i].trim());
						boolean statusFlag = coi.insertIntoTable("entity_takeaction_mom_discussion", insertData, factory);
						if (statusFlag) count++;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public int saveMOMFutureAction(SessionFactory factory, String... arg)
	{
		int count = 0;
		try
		{
			if (new MOMHelper().creteTableMOMFutureAction(factory))
			{
				String[] ids = arg[0].split(",");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("futureAction");
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_mom_id");
				ob1.setDataName(arg[1]);
				insertData.add(ob1);

				for (int i = 0; i < ids.length; i++)
				{
					if (!ids[i].trim().equals(""))
					{
						ob.setDataName(ids[i].trim());
						boolean statusFlag = coi.insertIntoTable("entity_takeaction_mom_futureaction", insertData, factory);
						if (statusFlag) count++;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public int saveAttachDoc(SessionFactory factory, String... arg)
	{
		int count = 0;
		try
		{
			if (new MOMHelper().creteTableEntityTakeActionAttachedDoc(factory))
			{
				String[] ids = arg[0].split(",");
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("docPath");
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_id");
				ob1.setDataName(arg[1]);
				insertData.add(ob1);

				for (int i = 0; i < ids.length; i++)
				{
					if (!ids[i].trim().equals(""))
					{
						ob.setDataName(ids[i].trim());
						boolean statusFlag = coi.insertIntoTable("entity_takeaction_attacheddoc", insertData, factory);
						if (statusFlag) count++;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public boolean saveMOMFilePathToDb(SessionFactory factory, String path, String clientTakeActionId)
	{
		boolean statusFlag = false;
		try
		{
			if (new MOMHelper().creteTableEntityTakeActionAttachedDoc(factory))
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("docPath");
				ob.setDataName(path);
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("client_takeaction_id");
				ob1.setDataName(clientTakeActionId);
				insertData.add(ob1);

				statusFlag = coi.insertIntoTable("entity_takeaction_attacheddoc", insertData, factory);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return statusFlag;
	}

	@SuppressWarnings("unchecked")
	public String fetchOrganizationIdByContactId(SessionFactory factory, String contactId, ActivityType activityType)
	{
		String id = null;
		String query = null;
		if (activityType == ActivityType.client)
		{
			query = "select clientName from client_contact_data where id = " + contactId;
		}
		else if (activityType == ActivityType.associate)
		{
			query = "select associateName from associate_contact_data where id = " + contactId;
		}
		List list = coi.executeAllSelectQuery(query, factory);
		if (list != null && !list.isEmpty())
		{
			id = CommonHelper.getFieldValue(list.get(0));
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public String fetchStatusNameById(SessionFactory factory, String id, ActivityType activityType)
	{
		String status = null;
		String query = null;
		if (activityType == ActivityType.client)
		{
			query = "select statusName from client_status where id = " + id;
		}
		else if (activityType == ActivityType.associate)
		{
			query = "select statusname from associatestatus where id = " + id;
		}
		List list = coi.executeAllSelectQuery(query, factory);
		if (list != null && !list.isEmpty())
		{
			status = CommonHelper.getFieldValue(list.get(0));
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> fetchAllRefContact(String referedBy, String referedbyID)
	{
		Map<String, String> refcontact = new LinkedHashMap<String, String>();
		try
		{
			String query = null;
			if(referedBy.equalsIgnoreCase("Client") || referedBy.equalsIgnoreCase("Existing_Client"))
			{
				query = "select id, personName from client_contact_data where clientName = '"+referedbyID+"' and personName is not null order by personName";
				System.out.println(query); 
			}
			else if(referedBy.equalsIgnoreCase("Associate") || referedBy.equalsIgnoreCase("Existing_Associate"))
			{
				query = "select id, name from associate_contact_data where associateName = '"+referedbyID+"' and name is not null order by name";
				System.out.println(query); 
			}
			
			List sourceMasterData = coi.executeAllSelectQuery(query, connectionSpace);
			
			if (sourceMasterData != null && sourceMasterData.size()>0)
			{
				for (Object c : sourceMasterData)
				{
					Object[] dataC = (Object[]) c;
					if(dataC[0] != null && dataC[1] != null)
					{
						if(dataC[1] !=null)
						{
							refcontact.put(dataC[1].toString(), dataC[1].toString());
						}
						
					}
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return refcontact;
	}
	// all details of reference contact.
	
	@SuppressWarnings("unchecked")
	public String fetchAllRefContactDetails(String referedBy, String referedbyID, String contactName)
	{
		String refcontact = null;
		try
		{
			String query = null;
			if(referedBy.equalsIgnoreCase("Client") || referedBy.equalsIgnoreCase("Existing_Client"))
			{
				query = "select id, personName, emailOfficial, department, contactNo from client_contact_data where clientName = '"+referedbyID+"' and personName = '"+contactName+"'";
				System.out.println(query); 
			}
			else if(referedBy.equalsIgnoreCase("Associate") || referedBy.equalsIgnoreCase("Existing_Associate"))
			{
				query = "select id, name, emailOfficial, contactNum,department from associate_contact_data where associateName = '"+referedbyID+"' and name = '"+contactName+"'";
				System.out.println(query); 
			}
			
			List sourceMasterData = coi.executeAllSelectQuery(query, connectionSpace);
			
			if (sourceMasterData != null && !sourceMasterData.isEmpty())
			{
				for (Object c : sourceMasterData)
				{
					Object[] dataC = (Object[]) c;
					if(dataC[0] != null && dataC[1] != null)
					{
						refcontact = dataC[1].toString()+"#"+dataC[2].toString();
					}
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return refcontact;
	}
	@SuppressWarnings("unchecked")
	public String fetchRefById(String refIdVal)
	{
		String refId = null;
		try
		{
			List sourceMasterData = coi.executeAllSelectQuery("select referedby, refName from client_basic_data where id = '"+refIdVal+"'", connectionSpace);
			System.out.println("select referedby, refName from client_basic_data where id = '"+refIdVal+"'"); 
			if (sourceMasterData != null && !sourceMasterData.isEmpty())
			{
				for (Object c : sourceMasterData)
				{
					Object[] dataC = (Object[]) c;
					
					if(dataC[0] == null){dataC[0] = "NA";}
					if(dataC[1] == null){dataC[1] = "NA";}
					refId = dataC[0].toString()+"#"+dataC[1].toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return refId;
	}
	
	public String fetchClientStatusName(String statusIdNew)
	{
		String statusname = null;
		try
		{
			List statusnameData = coi.executeAllSelectQuery("select id, statusName from client_status where id = '"+statusIdNew+"'", connectionSpace);
			if (statusnameData != null && !statusnameData.isEmpty())
			{
				for (Object c : statusnameData)
				{
					Object[] dataC = (Object[]) c;
					if(dataC[1] == null){dataC[1] = "NA";}
					statusname = dataC[1].toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusname;
	
	}
	
}
