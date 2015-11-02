package com.Over2Cloud.ctrl.VAM.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VisitorExit extends ActionSupport{

	/**
	 * Class to help exiting visitor. 
	 */
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(VisitorExit.class);
	private String sr_number;
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private LinkedList<ConfigurationUtilBean>visitorfields = null;
	private LinkedList<ConfigurationUtilBean>vehicleentryfields = null;
	private List<ConfigurationUtilBean> visitorFields = null;
	private String vehicledatalist=null;
	Map<Integer, String> departmentlist = new HashMap<Integer, String>();
	private String cardnumber;
	private String barcode;
	private String srnumberfrommail;
	private String visitorstatusAction;
	private String comments;
	private String VehicleNumber;
	private String deptNameExist;
	private String deptName;
	private String drivername;
	private String drivermobile;
	private String srnumber;
	Map<String, Object> oneList = new HashMap<String, Object>();
	//method to get fields on visitor exit view page.
	public String addVisitorExit()
	{
		String resString = ERROR;
		if(userName == null || userName.equalsIgnoreCase(""))
		{
			return  LOGIN;
		}
		try
    {
			visitorfields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_visitor_configuration",
					accountID, connectionSpace, "", 0, "table_name",
					"visitor_configuration");
			if(group != null)
			{
				for (GridDataPropertyView gdp : group) 
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("location")
							&& !gdp.getColomnName().equalsIgnoreCase("gate")
							&& !gdp.getColomnName().equalsIgnoreCase("deptName")
							&& !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
					}
						if (gdp.getColType().trim().equalsIgnoreCase("Date") || gdp.getColType().trim().equalsIgnoreCase("Time")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							//visitordatetimelist.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("ip")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_time")
								&& !gdp.getColomnName().equalsIgnoreCase("accept")
								&& !gdp.getColomnName().equalsIgnoreCase("reject")
								&& !gdp.getColomnName().equalsIgnoreCase("status")
								&& !gdp.getColomnName().equalsIgnoreCase("image")
								&& !gdp.getColomnName().equalsIgnoreCase("db_transfer")
								&& !gdp.getColomnName().equalsIgnoreCase("update_date")
								&& !gdp.getColomnName().equalsIgnoreCase("update_time")
								&& !gdp.getColomnName().equalsIgnoreCase("time_out")
								&& !gdp.getColomnName().equalsIgnoreCase("update_time")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_name")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_mobile")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_company")
								&& !gdp.getColomnName().equalsIgnoreCase("address")
								&& !gdp.getColomnName().equalsIgnoreCase("visited_person")
								&& !gdp.getColomnName().equalsIgnoreCase("visited_mobile")
								&& !gdp.getColomnName().equalsIgnoreCase("possession")
								&& !gdp.getColomnName().equalsIgnoreCase("one_time_password")
								&& !gdp.getColomnName().equalsIgnoreCase("comments")
								&& !gdp.getColomnName().equalsIgnoreCase("car_number")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_after"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
							} else {
							obj.setMandatory(false);
							}
							visitorfields.add(obj);
						}
					//}
					
				}
					resString = SUCCESS;
			}
    } catch (Exception e)
    {
    	addActionError("There Is Problem to Exit Visitor!");
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <addVisitorExit> of class "+getClass(), e);
			e.printStackTrace();
    }
		return resString;
	
	}
	public String ExitVisitor(){
		String resString = ERROR;
		String query = null;
		try 
		{
		String updatetimeout = DateUtil.getCurrentTime();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		if(getSr_number() != null && !getSr_number().equalsIgnoreCase(""))
		{
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where sr_number = '"+getSr_number()+"'";
		}
		if(getBarcode() != null && !getBarcode().equalsIgnoreCase(""))
		{
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where barcode = '"+getBarcode()+"'";
		}
		//update on local.
		int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
		PreRequestserviceStub updateTable=new PreRequestserviceStub();
		//update on Server.	
		UpdateTable obj=new UpdateTable();
		obj.setTableQuery(query);
			
		if(localstatus>0)
		{
			addActionMessage("Visitor Exited Successfully.");
			resString = SUCCESS;
		}else
		{
			addActionMessage("There is Some Problem in Visitor Exit.");
			resString = ERROR;
		}
		boolean status = updateTable.updateTable(obj).get_return();
		
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <ExitVisitor()> of class "+getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	
	public String addDashboardVisitorExit()
	{
		System.out.println(">>>>>>>>>>>dashboard Exit>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String resString = ERROR;
		if(userName == null || userName.equalsIgnoreCase(""))
		{
			return  LOGIN;
		}
		try
    {
			visitorfields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_visitor_configuration",
					accountID, connectionSpace, "", 0, "table_name",
					"visitor_configuration");
			if(group != null)
			{
				for (GridDataPropertyView gdp : group) 
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("location")
							&& !gdp.getColomnName().equalsIgnoreCase("gate")
							&& !gdp.getColomnName().equalsIgnoreCase("deptName")
							&& !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
					}
						if (gdp.getColType().trim().equalsIgnoreCase("Date") || gdp.getColType().trim().equalsIgnoreCase("Time")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							//visitordatetimelist.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("ip")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_time")
								&& !gdp.getColomnName().equalsIgnoreCase("accept")
								&& !gdp.getColomnName().equalsIgnoreCase("reject")
								&& !gdp.getColomnName().equalsIgnoreCase("status")
								&& !gdp.getColomnName().equalsIgnoreCase("image")
								&& !gdp.getColomnName().equalsIgnoreCase("db_transfer")
								&& !gdp.getColomnName().equalsIgnoreCase("update_date")
								&& !gdp.getColomnName().equalsIgnoreCase("update_time")
								&& !gdp.getColomnName().equalsIgnoreCase("time_out")
								&& !gdp.getColomnName().equalsIgnoreCase("update_time")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_name")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_mobile")
								&& !gdp.getColomnName().equalsIgnoreCase("visitor_company")
								&& !gdp.getColomnName().equalsIgnoreCase("address")
								&& !gdp.getColomnName().equalsIgnoreCase("visited_person")
								&& !gdp.getColomnName().equalsIgnoreCase("visited_mobile")
								&& !gdp.getColomnName().equalsIgnoreCase("possession")
								&& !gdp.getColomnName().equalsIgnoreCase("one_time_password")
								&& !gdp.getColomnName().equalsIgnoreCase("comments")
								&& !gdp.getColomnName().equalsIgnoreCase("car_number")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_after"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
							} else {
							obj.setMandatory(false);
							}
							visitorfields.add(obj);
						}
					//}
					
				}
					resString = SUCCESS;
				
			}
    } catch (Exception e)
    {
    	addActionError("There Is Problem to Exit Visitor!");
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <addDashboardVisitorExit> of class "+getClass(), e);
			e.printStackTrace();
    }
    return resString;
		
	}
	public String ExitDashVisitor()
	{
		String resString = ERROR;
		String query = null;
		try 
		{
		String updatetimeout = DateUtil.getCurrentTime();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		if(getSr_number() != null && !getSr_number().equalsIgnoreCase(""))
		{
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where sr_number = '"+getSr_number()+"'";
		}
		if(getBarcode() != null && !getBarcode().equalsIgnoreCase(""))
		{
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where barcode = '"+getBarcode()+"'";
		}
		//update on local.
		int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
		System.out.println("localstatus>>"+localstatus);
		PreRequestserviceStub updateTable=new PreRequestserviceStub();
		UpdateTable obj=new UpdateTable();
		obj.setTableQuery(query);
		//update on Server.
		
		if(localstatus>0)
		{
			addActionMessage("Visitor Exited Successfully.");
			resString = SUCCESS;
		}else
		{
			addActionMessage("There is Some Problem in Visitor Exit.");
			resString = ERROR;
		}
		boolean status = updateTable.updateTable(obj).get_return();
		
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <ExitDashVisitor()> of class "+getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
  public String	addVehicleExit()
  {
  	String resString = ERROR;
		if(userName == null || userName.equalsIgnoreCase(""))
		{
			return  LOGIN;
		}
		try
    {
			vehicleentryfields = new LinkedList<ConfigurationUtilBean>();
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_vehicle_entry_configuration",
					accountID, connectionSpace, "", 0, "table_name",
					"vehicle_entry_configuration");
			if(group != null)
			{
				for (GridDataPropertyView gdp : group) 
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("location")
							&& !gdp.getColomnName().equalsIgnoreCase("gate")
							&& !gdp.getColomnName().equalsIgnoreCase("Date")
							&& !gdp.getColomnName().equalsIgnoreCase("Time")
							&& !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
					}
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("ip")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_time")
								&& !gdp.getColomnName().equalsIgnoreCase("status")
								&& !gdp.getColomnName().equalsIgnoreCase("image")
								&& !gdp.getColomnName().equalsIgnoreCase("db_transfer")
								&& !gdp.getColomnName().equalsIgnoreCase("update_date")
								&& !gdp.getColomnName().equalsIgnoreCase("update_time")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("vehicle_model")
								&& !gdp.getColomnName().equalsIgnoreCase("out_time")
								&& !gdp.getColomnName().equalsIgnoreCase("trip_no")
								&& !gdp.getColomnName().equalsIgnoreCase("no_of_bill")
								&& !gdp.getColomnName().equalsIgnoreCase("barcode")
								&& !gdp.getColomnName().equalsIgnoreCase("invoce_no")
								&& !gdp.getColomnName().equalsIgnoreCase("material_details")
								&& !gdp.getColomnName().equalsIgnoreCase("quantity")
								&& !gdp.getColomnName().equalsIgnoreCase("destination")
								&& !gdp.getColomnName().equalsIgnoreCase("vehicle_owner")
								&& !gdp.getColomnName().equalsIgnoreCase("vh_owner_mob")
								&& !gdp.getColomnName().equalsIgnoreCase("alert_after"))
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								if(gdp.getColomnName().equalsIgnoreCase("sr_number") || gdp.getColomnName().equalsIgnoreCase("driver_name") || gdp.getColomnName().equalsIgnoreCase("driver_mobile"))
								{
									obj.setMandatory(false);
								}else
								{
									obj.setMandatory(true);
								}
								
							} else {
							obj.setMandatory(false);
							}
							vehicleentryfields.add(obj);
						}
						if (gdp.getColomnName().equalsIgnoreCase("deptName"))
						{
							deptName = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								deptNameExist = "true";
							else
								deptNameExist = "false";
						}
					//}
					
				}
				resString = SUCCESS;
			}
    } catch (Exception e)
    {
    			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <addVehicleExit()> of class "+getClass(), e);
    			e.printStackTrace();
    }
		return resString;
  }
  
  public String searchVehicleDataForExit()
  {
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getVehicleNumber()!=null && !getVehicleNumber().equals("")){
				vehicledatalist = CommonMethod.getPreviousVehicleData(connectionSpace, getVehicleNumber());
				resString = SUCCESS;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	
  }
  
  public String ExitVehicle()
  {
		String resString = ERROR;
		String query = null;
		try 
		{
		String updatetimeout = DateUtil.getCurrentTime();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
			if(getVehicleNumber()!=null && !getVehicleNumber().equalsIgnoreCase(""))
			{
				 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where vehicle_number = '"+getVehicleNumber()+"'";
			}
			if(getDrivername()!=null && !getDrivername().equalsIgnoreCase(""))
			{
				 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where driver_name = '"+getDrivername()+"'";
			}
			if(getDrivermobile()!=null && !getDrivermobile().equalsIgnoreCase(""))
			{
				 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where driver_mobile = '"+getDrivermobile().trim()+"'";
			}
			//update on local.
			int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
			//cbt.executeAllSelectQuery(query, connectionSpace);
			PreRequestserviceStub updateTable=new PreRequestserviceStub();
			UpdateTable obj=new UpdateTable();
			obj.setTableQuery(query);
			
		if(localstatus>0)
		{
		addActionMessage("Vehicle Exited Successfully.");
		resString = SUCCESS;
		}else
		{
			addActionMessage("Some Problem In Exit.");
			resString = ERROR;
		}
		//update on Server.
		boolean status = updateTable.updateTable(obj).get_return();
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <ExitVehicle()> of class "+getClass(), e);
			e.printStackTrace();
		}
		
		return resString;
  }
  /** 
	 * Method for Accept or Reject Visitor Request from Mail URL.
	 * */
	
	public String meetRequestAcceptRejectAdd()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			/*if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}*/
			SessionFactory dbconection =DBDynamicConnection.getSessionFactory();
			visitorFields = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_visitor_configuration", accountID, dbconection, "", 0,
			    "table_name", "visitor_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("sr_number")
					    && !gdp.getColomnName().equalsIgnoreCase("address")
					    && !gdp.getColomnName().equalsIgnoreCase("visited_person")
					    && !gdp.getColomnName().equalsIgnoreCase("visited_mobile")
					    && !gdp.getColomnName().equalsIgnoreCase("time_out")
					    && !gdp.getColomnName().equalsIgnoreCase("image")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_time")
					    && !gdp.getColomnName().equalsIgnoreCase("accept")
					    && !gdp.getColomnName().equalsIgnoreCase("reject")
					    && !gdp.getColomnName().equalsIgnoreCase("status")
					    && !gdp.getColomnName().equalsIgnoreCase("db_transfer")
					    && !gdp.getColomnName().equalsIgnoreCase("ip")
					    && !gdp.getColomnName().equalsIgnoreCase("update_date")
					    && !gdp.getColomnName().equalsIgnoreCase("update_time")
					    && !gdp.getColomnName().equalsIgnoreCase("one_time_password")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_after")
					    && !gdp.getColomnName().equalsIgnoreCase("barcode")
					    && !gdp.getColomnName().equalsIgnoreCase("car_number")
					    && !gdp.getColomnName().equalsIgnoreCase("userName"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						visitorFields.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("empName")
					    && !gdp.getColomnName().equalsIgnoreCase("deptName")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						visitorFields.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("Date")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
						  && !gdp.getColomnName().equalsIgnoreCase("time")
					    || gdp.getColType().trim().equalsIgnoreCase("Time")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						visitorFields.add(obj);
					}
					
				}
			}
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from visitor_entry_details as ved ");
			int i = 0;
			for (ConfigurationUtilBean fields : visitorFields)
      {
				if (fields != null)
				{
					if (i < visitorFields.size() - 1)
					{
						if (fields.getKey().equalsIgnoreCase("purpose"))
						{
							query.append(" pur.purpose , ");
							queryEnd
							    .append(" left join purpose_admin as pur on pur.id = ved.purpose ");
						}
						else if (fields.getKey().equalsIgnoreCase("location"))
						{
							query.append(" loc.name , ");
							queryEnd
							    .append(" left join location as loc on loc.id = ved.location ");
						}
						else
						{
						query.append("ved."+fields.getKey() + ",");
						}
					}
					else
					{
						query.append("ved."+fields.getKey());
					}
				}
				i++;
      }
			query.append(" " + queryEnd.toString());
			query.append(" where ved.sr_number = '" +getSrnumberfrommail()+"'");
			List data = cbt.executeAllSelectQuery(query.toString(),
					dbconection);
			if (data != null && !data.isEmpty())
			{
				Object[] obj = (Object[]) data.get(0);
				for(int j=0; j<obj.length; j++)
				{
					if(obj[j]!= null && visitorFields.get(j).getValue()!=null){
					oneList.put(visitorFields.get(j).getValue(), obj[j].toString());
					}
				}
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to add instantVisitActionAdd.");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <VisitorActionAdd> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
public String visitorActionSubmit()
{
	String resstring = ERROR;
	boolean status = false;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	try
  {
		SessionFactory dbconection =DBDynamicConnection.getSessionFactory();
		if(getVisitorstatusAction() != null && !getVisitorstatusAction().equals("") && getVisitorstatusAction().equalsIgnoreCase("Approved"))
		{
			/*wherClause.put("accept", "yes");
			wherClause.put("comments", getComments());
			condtnBlock.put("sr_number",getSrnumberfrommail());*/
			String query = "update visitor_entry_details set accept = '"+"yes"+"', comments = '"+getComments()+"' where sr_number = '"+getSrnumberfrommail()+"'";
			//update on local.
			int localstatus = cbt.executeAllUpdateQuery(query, dbconection);
			PreRequestserviceStub updateTable=new PreRequestserviceStub();
			UpdateTable obj=new UpdateTable();
			obj.setTableQuery(query);
			//update on Server.
			status = updateTable.updateTable(obj).get_return();
			
			if(localstatus>0){
				addActionMessage("Approved Done Successfully!!!");
				resstring = SUCCESS;
			}
		}
		if(getVisitorstatusAction() != null && !getVisitorstatusAction().equals("") && getVisitorstatusAction().equalsIgnoreCase("Rejected"))
		{
			/*wherClause.put("reject", "no");
			wherClause.put("comments", getComments());
			wherClause.put("status", "1");
			wherClause.put("time_out", DateUtil.getCurrentTime());
			condtnBlock.put("sr_number",getSrnumberfrommail());*/
			String query = "update visitor_entry_details set reject = '"+"no"+"', comments = '"+getComments()+"',"+" status = 1, time_out ='"+DateUtil.getCurrentTime()+"' where sr_number = '"+getSrnumberfrommail()+"'";
			//update on local.
			int localstatus = cbt.executeAllUpdateQuery(query, dbconection);
			PreRequestserviceStub updateTable=new PreRequestserviceStub();
			UpdateTable obj=new UpdateTable();
			obj.setTableQuery(query);
			//update on Server.
			 status = updateTable.updateTable(obj).get_return();
			if(localstatus>0){
				addActionMessage("Rejected Successfully.");
				resstring = SUCCESS;
			}
		}
		else
		{
			addActionMessage("There is some error in Action.");
			resstring = ERROR;
		}
  } catch (Exception e)
  {
  	addActionError("There Is Problem To Accept and Reject Visitor Request!");
		log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
		    + DateUtil.getCurrentTime() + " "
		    + "Problem in Over2Cloud  method <visitorActionSubmit> of class "
		    + getClass(), e);
		e.printStackTrace();
  }
	return resstring;
}
	
	public String getSr_number() {
		return sr_number;
	}
	public void setSr_number(String sr_number) {
		this.sr_number = sr_number;
	}
	public LinkedList<ConfigurationUtilBean> getVisitorfields() {
		return visitorfields;
	}
	public void setVisitorfields(LinkedList<ConfigurationUtilBean> visitorfields) {
		this.visitorfields = visitorfields;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public LinkedList<ConfigurationUtilBean> getVehicleentryfields()
	{
		return vehicleentryfields;
	}
	public void setVehicleentryfields(
	    LinkedList<ConfigurationUtilBean> vehicleentryfields)
	{
		this.vehicleentryfields = vehicleentryfields;
	}
	public String getSrnumberfrommail()
	{
		return srnumberfrommail;
	}

	public void setSrnumberfrommail(String srnumberfrommail)
	{
		this.srnumberfrommail = srnumberfrommail;
	}
	public List<ConfigurationUtilBean> getVisitorFields()
	{
		return visitorFields;
	}
	public void setVisitorFields(List<ConfigurationUtilBean> visitorFields)
	{
		this.visitorFields = visitorFields;
	}
	public Map<String, Object> getOneList()
	{
		return oneList;
	}
	public void setOneList(Map<String, Object> oneList)
	{
		this.oneList = oneList;
	}
	public String getVisitorstatusAction()
	{
		return visitorstatusAction;
	}
	public void setVisitorstatusAction(String visitorstatusAction)
	{
		this.visitorstatusAction = visitorstatusAction;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public String getVehicleNumber()
	{
		return VehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber)
	{
		VehicleNumber = vehicleNumber;
	}
	public String getDeptNameExist()
	{
		return deptNameExist;
	}
	public void setDeptNameExist(String deptNameExist)
	{
		this.deptNameExist = deptNameExist;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public Map<Integer, String> getDepartmentlist()
	{
		return departmentlist;
	}
	public void setDepartmentlist(Map<Integer, String> departmentlist)
	{
		this.departmentlist = departmentlist;
	}
	public String getVehicledatalist()
	{
		return vehicledatalist;
	}
	public void setVehicledatalist(String vehicledatalist)
	{
		this.vehicledatalist = vehicledatalist;
	}
	public String getDrivername()
	{
		return drivername;
	}
	public void setDrivername(String drivername)
	{
		this.drivername = drivername;
	}
	public String getDrivermobile()
	{
		return drivermobile;
	}
	public void setDrivermobile(String drivermobile)
	{
		this.drivermobile = drivermobile;
	}
	public String getSrnumber() {
		return srnumber;
	}
	public void setSrnumber(String srnumber) {
		this.srnumber = srnumber;
	}   
     
}