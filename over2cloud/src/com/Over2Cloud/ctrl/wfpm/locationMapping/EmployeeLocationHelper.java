package com.Over2Cloud.ctrl.wfpm.locationMapping;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class EmployeeLocationHelper extends GridPropertyBean
{
	private Map<String, String> empMap;
    private String deptId;
    private String country;
    private String state;
    private String city;
    
	public String fetchEmployee()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				ComplianceUniversalAction CUA= new ComplianceUniversalAction();
            	String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
            	CommonOperInterface cbt = new CommonConFactory().createInterface();
        		StringBuilder qryString = new StringBuilder();
        		List dataList = null;
            	qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
    			qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
    			qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " AND from_contact_sub_type_id='"+deptId+"' AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by emp_name asc");
    			empMap = new LinkedHashMap<String, String>();
    			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
    			if (dataList != null && dataList.size() > 0)
    			{
    				Object[] objectCol=null;
    				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
    				{
    					objectCol = (Object[]) iterator.next();
    					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
    					{
    						empMap.put(objectCol[0].toString(), objectCol[1].toString());
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
	 public String fetchStateMulti()
	{
			if (ValidateSession.checkSession()) 
			{
				try
				{
					
	            	CommonOperInterface cbt = new CommonConFactory().createInterface();
	        		StringBuilder qryString = new StringBuilder();
	        		List dataList = null;
	            	qryString.append("SELECT id,state_name FROM state WHERE country_code IN(" +country + " )");
	    			empMap = new LinkedHashMap<String, String>();
	    			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
	    			if (dataList != null && dataList.size() > 0)
	    			{
	    				Object[] objectCol=null;
	    				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	    				{
	    					objectCol = (Object[]) iterator.next();
	    					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
	    					{
	    						empMap.put(objectCol[0].toString(), objectCol[1].toString());
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
	 public String fetchCityMulti()
		{
				if (ValidateSession.checkSession()) 
				{
					try
					{
						
		            	CommonOperInterface cbt = new CommonConFactory().createInterface();
		        		StringBuilder qryString = new StringBuilder();
		        		List dataList = null;
		            	qryString.append("SELECT id,city_name FROM city WHERE name_state IN(" +state + " )");
		    			empMap = new LinkedHashMap<String, String>();
		    			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		    			if (dataList != null && dataList.size() > 0)
		    			{
		    				Object[] objectCol=null;
		    				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
		    				{
		    					objectCol = (Object[]) iterator.next();
		    					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
		    					{
		    						empMap.put(objectCol[0].toString(), objectCol[1].toString());
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
	 public String fetchTerritoryMulti()
		{
				if (ValidateSession.checkSession()) 
				{
					try
					{
						
		            	CommonOperInterface cbt = new CommonConFactory().createInterface();
		        		StringBuilder qryString = new StringBuilder();
		        		List dataList = null;
		            	qryString.append("SELECT id,trr_name FROM territory WHERE trr_city IN(" +city + " )");
		    			empMap = new LinkedHashMap<String, String>();
		    			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		    			if (dataList != null && dataList.size() > 0)
		    			{
		    				Object[] objectCol=null;
		    				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
		    				{
		    					objectCol = (Object[]) iterator.next();
		    					if (objectCol[0] != null || !objectCol[1].toString().equals(""))
		    					{
		    						empMap.put(objectCol[0].toString(), objectCol[1].toString());
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
	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getDeptId()
	{
		return deptId;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getCountry()
	{
		return country;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	
}
