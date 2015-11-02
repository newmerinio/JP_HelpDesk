package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class ActivityPlannerHelper extends GridPropertyBean
{
	private JSONArray commonJsonArr;
	private String city;
	private String type;
	private String subType;
	private String territory;
	private String 	  schedule;

	private Map<String,String> commonMap;
	
	@SuppressWarnings("unchecked")
	public String fetchTerritory()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				List list = new createTable().executeAllSelectQuery("select id,trr_name from territory where trr_city='"+city+"' and status='Active' order by trr_name  ", connectionSpace);
				commonJsonArr = new JSONArray();
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							obj = new JSONObject();
							obj.put("id", object[0].toString());
							obj.put("name", object[1].toString());
							commonJsonArr.add(obj);
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
	public String fetchRelationSubType()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				List list = new createTable().executeAllSelectQuery("select id,rel_subtype from relationship_sub_type where rel_type='"+type+"' and status='Active' order by rel_subtype  ", connectionSpace);
				commonJsonArr = new JSONArray();
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							obj = new JSONObject();
							obj.put("id", object[0].toString());
							obj.put("name", object[1].toString());
							commonJsonArr.add(obj);
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
	public String fetchActivityFor()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				commonMap = new LinkedHashMap<String,String>();
				String query=null;
				if(subType!=null && subType.equalsIgnoreCase("Individual")&& !subType.equalsIgnoreCase("") &&  !subType.equalsIgnoreCase("-1"))
				{
					query ="SELECT con.id,emp.emp_name FROM referral_contact_data as con LEFT JOIN primary_contact as emp on emp.id=con.map_emp_id WHERE territory="+territory;
				}
				else if(subType!=null && subType.equalsIgnoreCase("Institutional")&& !subType.equalsIgnoreCase("") &&  !subType.equalsIgnoreCase("-1"))
				{
					query ="SELECT id,org_name FROM referral_institutional_data WHERE territory="+territory;
				}
				if(query!=null)
				{
					List list = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (list != null && !list.isEmpty())
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null)
							{
								commonMap.put(object[0].toString(), object[1].toString());
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
	public String fetchActivityTypeEvent()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				commonJsonArr = new JSONArray();
				String query=null;
				if(schedule!=null && schedule.equalsIgnoreCase("Field Activity"))
				{
					query="SELECT escData.id,at.act_name FROM activity_type as at LEFT JOIN patientcrm_status_data as escData on escData.status=at.id LEFT JOIN relationship_sub_type as subtype on subtype.id=escData.rh_sub_type WHERE subtype.id="+subType+" AND act_type='Routine';";
				}
				else
				{
					query="SELECT at.id,at.act_name FROM activity_type as at LEFT JOIN relationship_sub_type as subtype on subtype.id=act_rel_subtype WHERE subtype.id="+subType+" AND act_type='Event';";
				}
				if(query!=null)
				{
					List list = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (list != null && !list.isEmpty())
					{
						JSONObject obj = null;
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{

							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								obj = new JSONObject();
								obj.put("id", object[0].toString());
								obj.put("name", object[1].toString());
								commonJsonArr.add(obj);
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
	public Map<String,String> mappedEmployee(String deptId,CommonOperInterface coi)
	{
		Map<String,String> data =null;
		try 
		{
			data =new LinkedHashMap<String, String>();
			String query= "SELECT mc.id,emp.emp_name FROM primary_contact as emp LEFT JOIN manage_contact as mc on mc.emp_id=emp.id WHERE mc.for_contact_sub_type_id="+deptId+" AND mc.module_name='WFPM'";
			List dataList =coi.executeAllSelectQuery(query, connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null)
					{
						data. put(object[0].toString(),object[1].toString());
					}
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("unchecked")
	public String fetchEmpList()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				String query= "SELECT mc.id,emp.emp_name FROM primary_contact as emp LEFT JOIN manage_contact as mc on mc.emp_id=emp.id WHERE mc.for_contact_sub_type_id="+loggedDetails[4]+" AND mc.module_name='WFPM'";
				List list = new createTable().executeAllSelectQuery(query, connectionSpace);
				commonJsonArr = new JSONArray();
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							obj = new JSONObject();
							obj.put("id", object[0].toString());
							obj.put("name", object[1].toString());
							commonJsonArr.add(obj);
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
	public String searchAllContactId(String empId,CommonOperInterface coi)
	{
		String data =null;
		try 
		{
			
			String query= "SELECT id FROM manage_contact WHERE module_name='WFPM' AND emp_id="+empId;
			List dataList =coi.executeAllSelectQuery(query, connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
			    if(dataList.get(0)!=null)
			    {
			    	data = dataList.get(0).toString().replace("[", "").replace("]", "");
			    }
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public void setCommonJsonArr(JSONArray commonJsonArr)
	{
		this.commonJsonArr = commonJsonArr;
	}

	public JSONArray getCommonJsonArr()
	{
		return commonJsonArr;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCity()
	{
		return city;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return type;
	}
	public void setSubType(String subType)
	{
		this.subType = subType;
	}
	public String getSubType()
	{
		return subType;
	}
	public void setTerritory(String territory)
	{
		this.territory = territory;
	}
	public String getTerritory()
	{
		return territory;
	}

	public void setCommonMap(Map<String,String> commonMap)
	{
		this.commonMap = commonMap;
	}

	public Map<String,String> getCommonMap()
	{
		return commonMap;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getSchedule() {
		return schedule;
	}

}
