package com.Over2Cloud.ctrl.wfpm.lead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.lead.LeadSupportBean;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientSupportBean;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LeadDashboardAction extends SessionProviderClass
{
	// Anoop, 7-10-2013
	HashMap<String, Integer>	leadMap					= null;
	HashMap<String, Integer>	clientMap				= null;
	HashMap<String, Integer>	associateMap		= null;

	HashMap<String, Integer>	offeringMap			= null;
	HashMap<String, Integer>	employeeMap			= null;
	HashMap<String, Integer>	eventMap				= null;
	private String						legendShow			= "false";
	private String						width						= "210";
	private String						height					= "210",leadtype,starRating,clientType;
	private ArrayList<LeadSupportBean> finalDataList;
	private List<LeadSupportBean> ratingfDataList;
	private ArrayList<ArrayList<String>> birthdayList = null;
	private JSONArray jsonArray;
	private JSONArray jsonArray1;
	CommonHelper ch = new CommonHelper();
	String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
	
	public String beforeLeadPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getLeadPieChartData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	
	public String beforeLeadFactorPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getLeadPieFactorChartData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	private void getLeadPieChartData() throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		jsonArray=new JSONArray();
		CommonHelper ch = new CommonHelper();
		String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
		
		String q1 = "select status, count(*)  from leadgeneration " + "where userName IN (" + cIdAll + ") group by status";
		
		List leadList = coi.executeAllSelectQuery(q1, connectionSpace);
		if (leadList != null)
		{
			leadMap = new HashMap<String, Integer>();
			leadMap.put("New", 0);
			leadMap.put("Pros. Client", 0);
			leadMap.put("Pros. Associate", 0);
			leadMap.put("Snoozed", 0);
			leadMap.put("Lost", 0);

			for (Object ob : leadList)
			{
				Object[] data = (Object[]) ob;
				
				String sts = "";
				
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					//this.setLeadtype("New");
					sts="New";
					leadMap.put("New", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					sts="Pros. Client";
					leadMap.put("Pros. Client", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					sts="Pros. Associate";
					leadMap.put("Pros. Associate", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("3"))
				{
					sts="Snoozed";
					leadMap.put("Snoozed", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("4"))
				{
					sts="Lost";
					leadMap.put("Lost", Integer.parseInt(data[1].toString()));
				}
				jsonObject.put("source", sts);
				jsonObject.put("percent", Integer.parseInt(data[1].toString()));
				jsonArray.add(jsonObject);
			}
			
			
			
		  }
    	}
	
	private void getLeadPieFactorChartData() throws Exception
	{
		JSONObject jsonObject1=new JSONObject();
		jsonArray1=new JSONArray();
		CommonHelper ch = new CommonHelper();
		String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
	
		String q1 = "select sm.sourceName,count(*) from leadgeneration as lg right join sourcemaster as sm on lg.sourceName=sm.id where lg.userName IN (" + cIdAll + ")   group by lg.sourceName";
		
		List leadList = coi.executeAllSelectQuery(q1, connectionSpace);
		if (leadList != null)
		{
			leadMap = new HashMap<String, Integer>();
			/*leadMap.put("Newspaper", 0);
			leadMap.put("Internet", 0);
			leadMap.put("Cold Call Meeting", 0);
			leadMap.put("Conference", 0);
			leadMap.put("Google Add", 0);
			leadMap.put("Other Online", 0);*/
			
			leadMap.put("Direct Mailer", 0);
			leadMap.put("Status Test", 0);
			leadMap.put("Internal Reference", 0);
			leadMap.put("Call Enquiry", 0);
			leadMap.put("Website Enquiry", 0);
			leadMap.put("Associate Reference", 0);
			leadMap.put("Health Camp", 0);
			leadMap.put("Word of Mouth", 0);
			leadMap.put("Other", 0);
			
			
			
			
			
			
			for (Object ob : leadList)
			{
				Object[] data = (Object[]) ob;
				String s = "";
				if (data[0].toString().equalsIgnoreCase("Call Enquiry"))
				{
					s="Call Enquiry";
					leadMap.put("Call Enquiry", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Internal Reference"))
				{
					s="Internal Reference";
					leadMap.put("Internal Reference", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Website Enquiry"))
				{
					s="Website Enquiry";
					leadMap.put("Website Enquiry",Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Associate Reference"))
				{
					s="Associate Reference";
					leadMap.put("Associate Reference", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Health Camp"))
				{
					s="Health Camp";
					leadMap.put("Health Camp", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Word of Mouth"))
				{
					s="Word of Mouth";
					leadMap.put("Word of Mouth", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Direct Mailer"))
				{
					s="Direct Mailer";
					leadMap.put("Direct Mailer", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Other"))
				{
					s="Other";
					leadMap.put("Other", Integer.parseInt(data[1].toString()));
				}
				else if(data[0].toString().equalsIgnoreCase("Status Test"))
				{
					s="Status Test";
					leadMap.put("Status Test", Integer.parseInt(data[1].toString()));
				}
				jsonObject1.put("source", s);
				jsonObject1.put("percent", Integer.parseInt(data[1].toString()));
				jsonArray1.add(jsonObject1);
				
				
				
				
				
				
				
				
				
				
				
				/*if (data[0].toString().equalsIgnoreCase("Newspaper"))
				{
					leadMap.put("Newspaper", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("Internet"))
				{
					leadMap.put("Internet", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("Cold Call Meeting"))
				{
					leadMap.put("Cold Call Meeting", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("Conference"))
				{
					leadMap.put("Conference", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("Google Add"))
				{
					leadMap.put("Google Add", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("Other Online"))
				{
					leadMap.put("Other Online", Integer.parseInt(data[1].toString()));
				}*/
			  }
			
		   }
    	}
	
	
	public String beforeLeadTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getLeadTableData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	
	private void getLeadTableData() throws Exception
	{
		finalDataList = new ArrayList<LeadSupportBean>();
		int rating=0;
		for(int i=0;i<5;i++)
		{
		String q3 ="select starRating from leadgeneration where userName IN (" + cIdAll + ") and status='"+i+"'  group by starRating";
//		String q3 = "select cbd.associateRating from associate_basic_data as cbd, "
	//			+ "associate_offering_mapping as com where com.associateName = cbd.id " + "and cbd.userName IN(" + cIdAll + ") and  com.isConverted='"+i+"' group by com.isConverted";
		
		List associateList = coi.executeAllSelectQuery(q3, connectionSpace);
		int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0;
		
		LeadSupportBean pojo=new LeadSupportBean();
		if(i==0){
			pojo.setLeadtype("New");
		}
		if(i==1){
			pojo.setLeadtype("Pros. Client");
		}
		if(i==2){
			pojo.setLeadtype("Pros. Associate");
		}
		if(i==3){
			pojo.setLeadtype("Snoozed");
		}
		if(i==4){
			pojo.setLeadtype("Lost");
		}
		
		if (associateList != null && associateList.size()>0)
		{
			
			for (Iterator iterator = associateList.iterator(); iterator.hasNext();)
			{	
				Object data = (Object) iterator.next();
				if(data!=null && !data.toString().equalsIgnoreCase("NA"))
				{
					try
					{
						rating=Integer.parseInt(data.toString());
						
					}
					catch (Exception e)
					{
						rating=0;
						e.printStackTrace();
					}
					if(rating==1)
					{
						rat1+=1;
					}
					else if(rating==2)
					{
						rat2+=1;
					}
					else if(rating==3)
					{
						rat3+=1;
					}
					else if(rating==4)
					{
						rat4+=1;
					}
					else if(rating==5)
					{
						rat5+=1;
					}
					pojo.setRat1(String.valueOf(rat1+Integer.parseInt(pojo.getRat1())));
					pojo.setRat2(String.valueOf(rat2+Integer.parseInt(pojo.getRat2())));
					pojo.setRat3(String.valueOf(rat3+Integer.parseInt(pojo.getRat3())));
					pojo.setRat4(String.valueOf(rat4+Integer.parseInt(pojo.getRat4())));
					pojo.setRat5(String.valueOf(rat5+Integer.parseInt(pojo.getRat5())));
				}
				
			   }
			finalDataList.add((pojo));
		    }
		 }
    }
	
	public String beforeLeadFactorTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getLeadFactorTableData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	
	private void getLeadFactorTableData() throws Exception
	{
		finalDataList = new ArrayList<LeadSupportBean>();
		String rating="";
		for(int i=0;i<5;i++)
		{
		String q3 ="select sm.sourceName from leadgeneration as lg right join sourcemaster as sm on lg.sourceName=sm.id where lg.userName IN (" + cIdAll + ") and lg.status='"+i+"'  group by starRating";
//		String q3 = "select cbd.associateRating from associate_basic_data as cbd, "
	//			+ "associate_offering_mapping as com where com.associateName = cbd.id " + "and cbd.userName IN(" + cIdAll + ") and  com.isConverted='"+i+"' group by com.isConverted";
		System.out.println("q3 "+q3);
		List associateList = coi.executeAllSelectQuery(q3, connectionSpace);
		int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0,rat6=0,rat7=0,rat8=0,rat9=0;
		
		LeadSupportBean pojo=new LeadSupportBean();
		if(i==0){
			pojo.setLeadtype("New");
		}
		if(i==1){
			pojo.setLeadtype("Pros. Client");
		}
		if(i==2){
			pojo.setLeadtype("Pros. Associate");
		}
		if(i==3){
			pojo.setLeadtype("Snoozed");
		}
		if(i==4){
			pojo.setLeadtype("Lost");
		}
		
		if (associateList != null && associateList.size()>0)
		{
			
			for (Iterator iterator = associateList.iterator(); iterator.hasNext();)
			{	
				Object data = (Object) iterator.next();
				
				if(data!=null && !data.toString().equalsIgnoreCase("NA"))
				{
					try
					{
						rating=data.toString();
						
					}
					catch (Exception e)
					{
						rating="";
						e.printStackTrace();
					}
					if(rating.equalsIgnoreCase("Call Enquiry"))
					{
						rat1+=1;
					}
					else if(rating.equalsIgnoreCase("Internal Reference"))
					{
						rat2+=1;
					}
					else if(rating.equalsIgnoreCase("Direct Mailer"))
					{
						rat3+=1;
					}
					else if(rating.equalsIgnoreCase("Website Enquiry"))
					{
						rat4+=1;
					}
					else if(rating.equalsIgnoreCase("Associate Reference"))
					{
						rat5+=1;
					}
					else if(rating.equalsIgnoreCase("Health Camp"))
					{
						rat6+=1;
					}
					else if(rating.equalsIgnoreCase("Word of Mouth"))
					{
						rat7+=1;
					}
					else if(rating.equalsIgnoreCase("Other"))
					{
						rat8+=1;
					}
					else if(rating.equalsIgnoreCase("Status Test"))
					{
						rat9+=1;
					}
					
					/*if(rating.equalsIgnoreCase("Newspaper"))
					{
						rat1+=1;
					}
					else if(rating.equalsIgnoreCase("Internet"))
					{
						rat2+=1;
					}
					else if(rating.equalsIgnoreCase("Cold Call Meeting"))
					{
						rat3+=1;
					}
					else if(rating.equalsIgnoreCase("Conference"))
					{
						rat4+=1;
					}
					else if(rating.equalsIgnoreCase("Google Add"))
					{
						rat5+=1;
					}
					else if(rating.equalsIgnoreCase("Other Online"))
					{
						rat6+=1;
					}*/
					pojo.setRat1(String.valueOf(rat1+Integer.parseInt(pojo.getRat1())));
					pojo.setRat2(String.valueOf(rat2+Integer.parseInt(pojo.getRat2())));
					pojo.setRat3(String.valueOf(rat3+Integer.parseInt(pojo.getRat3())));
					pojo.setRat4(String.valueOf(rat4+Integer.parseInt(pojo.getRat4())));
					pojo.setRat5(String.valueOf(rat5+Integer.parseInt(pojo.getRat5())));
					pojo.setRat6(String.valueOf(rat6+Integer.parseInt(pojo.getRat6())));
					pojo.setRat7(String.valueOf(rat7+Integer.parseInt(pojo.getRat7())));
					pojo.setRat8(String.valueOf(rat8+Integer.parseInt(pojo.getRat8())));
					pojo.setRat9(String.valueOf(rat9+Integer.parseInt(pojo.getRat9())));
				}
				
			   }
			finalDataList.add((pojo));
		    }
		 }
    }
	
	public String beforeShowClientBirthdayTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query7 = new StringBuilder("");
				query7.append("select cbd.clientName, ccd.personName, ccd.birthday from client_basic_data as cbd, "
						+ "client_contact_data as ccd where cbd.id = ccd.clientName  " + "and (ccd.birthday >= '" + DateUtil.getCurrentDateUSFormat() + "' and ccd.birthday <= '" +  DateUtil.getDateAfterDays(7)
						+ "') order by ccd.birthday ");
				List dataCount7 = coi.executeAllSelectQuery(query7.toString(), connectionSpace);

				if (dataCount7 != null && dataCount7.size() > 0)
				{
					birthdayList = new ArrayList<ArrayList<String>>();
					for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null && object[2] != null && !object[2].toString().equalsIgnoreCase(""))
						{
							ArrayList<String> data = new ArrayList<String>();
							data.add(object[0].toString());
							data.add(object[1].toString());
							data.add(DateUtil.convertDateToIndianFormat(object[2].toString()));

							birthdayList.add(data);
						}
					}
				}
		
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	
	public String showRatingWiseDetails()
	{
		//setRatingWiseViewProperty();
		System.out.println("In>>>>");
		return SUCCESS;
	}
	
	public String viewRatingDataGrid()
	{
		System.out.println("iii2    ");
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		LeadSupportBean clientBean;
		finalDataList = new ArrayList<LeadSupportBean>();
		
		try {System.out.println("iii3    ");
		if(starRating.equalsIgnoreCase("rat1"))
		{
			starRating = "1";
		}
		else if(starRating.equalsIgnoreCase("rat2"))
		{
			starRating = "2";
		}
		else if(starRating.equalsIgnoreCase("rat3"))
		{
			starRating = "3";
		}
		else if(starRating.equalsIgnoreCase("rat4"))
		{
			starRating = "4";
		}
		else if(starRating.equalsIgnoreCase("rat5"))
		{
			starRating = "5";
		}
		
		if(clientType.equalsIgnoreCase("Prospective"))
		{
			clientType = "0";
		}
		else if(clientType.equalsIgnoreCase("Existing"))
		{
			clientType = "1";
		}
		else if(clientType.equalsIgnoreCase("Lost"))
		{
			clientType = "2";
		}
			StringBuilder query = new StringBuilder("");
			query.append("select cbd.leadName, cbd.leadAddress,cbd.webAddress,loc.name,indus.industry,subindus.subIndustry,cbd.id ");
			query.append("from leadgeneration as cbd ");
			query.append("left join location as loc on cbd.name=loc.id ");
			query.append("left join industrydatalevel1 as indus on cdb.industry = indus.id ");
			query.append("left join subindustrydatalevel2 as subindus on indus.industry = subindus.industry ");
			query.append("where cbd.isConverted = '");
			query.append(clientType);
			query.append("' and cbd.starRating='");
			query.append(starRating);
			query.append("'");
			
			System.out.println("query:::::"+query.toString());
			List template = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			for (Iterator iterator = template.iterator(); iterator.hasNext();) {
				clientBean = new LeadSupportBean();
			Object[] object = (Object[]) iterator.next();
			
			if(object[0] != null)
			{
				clientBean.setLeadname(object[0].toString());
				System.out.println("object[0].toString() "+object[0].toString());
			}
			if(object[1] != null)
			{
				clientBean.setAddress(object[1].toString());
				System.out.println("object[1].toString() "+object[1].toString());
			}
			if(object[2] != null)
			{
				clientBean.setWebaddress(object[2].toString());
				System.out.println("object[2].toString() "+object[2].toString());
			}
			if(object[3] != null)
			{
				clientBean.setLocation(object[3].toString());
				System.out.println("object[3].toString() "+object[3].toString());
			}
			if(object[4] != null)
			{
				clientBean.setIndustry(object[4].toString());
				System.out.println("object[4].toString() "+object[4].toString());
			}
			if(object[5] != null)
			{
				clientBean.setSubindustry(object[5].toString());
				System.out.println("object[5].toString() "+object[5].toString());
			}
			
			if(object[6] != null)
			{
				clientBean.setId(object[6].toString());
				System.out.println("object[6].toString() "+object[6].toString());
			}
			
			
			finalDataList.add(clientBean);
				System.out.println("ratingfDataList  "+finalDataList.size()); 
			
			System.out.println("iii5    ");
		}
		setRatingfDataList(finalDataList);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public HashMap<String, Integer> getLeadMap()
	{
		return leadMap;
	}

	public void setLeadMap(HashMap<String, Integer> leadMap)
	{
		this.leadMap = leadMap;
	}

	public HashMap<String, Integer> getClientMap()
	{
		return clientMap;
	}

	public void setClientMap(HashMap<String, Integer> clientMap)
	{
		this.clientMap = clientMap;
	}

	public HashMap<String, Integer> getOfferingMap()
	{
		return offeringMap;
	}

	public void setOfferingMap(HashMap<String, Integer> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public HashMap<String, Integer> getEmployeeMap()
	{
		return employeeMap;
	}

	public void setEmployeeMap(HashMap<String, Integer> employeeMap)
	{
		this.employeeMap = employeeMap;
	}

	public HashMap<String, Integer> getEventMap()
	{
		return eventMap;
	}

	public void setEventMap(HashMap<String, Integer> eventMap)
	{
		this.eventMap = eventMap;
	}

	public String getLegendShow()
	{
		return legendShow;
	}

	public void setLegendShow(String legendShow)
	{
		this.legendShow = legendShow;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setLeadtype(String leadtype) {
		this.leadtype = leadtype;
	}

	public String getLeadtype() {
		return leadtype;
	}

	public void setFinalDataList(ArrayList<LeadSupportBean> finalDataList) {
		this.finalDataList = finalDataList;
	}

	public ArrayList<LeadSupportBean> getFinalDataList() {
		return finalDataList;
	}

	public void setBirthdayList(ArrayList<ArrayList<String>> birthdayList) {
		this.birthdayList = birthdayList;
	}

	public ArrayList<ArrayList<String>> getBirthdayList() {
		return birthdayList;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setRatingfDataList(List<LeadSupportBean> ratingfDataList) {
		this.ratingfDataList = ratingfDataList;
	}

	public List<LeadSupportBean> getRatingfDataList() {
		return ratingfDataList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONArray getJsonArray1() {
		return jsonArray1;
	}

	public void setJsonArray1(JSONArray jsonArray1) {
		this.jsonArray1 = jsonArray1;
	}
	
	

}