package com.Over2Cloud.ctrl.wfpm.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class SMSTemplateDao {
	static final Log log = LogFactory.getLog(SMSTemplateDao.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	
	public List getAllKpiList()
	{
		List kpiList = new ArrayList();
		try {
			String query = "select id, kpi from krakpicollection";
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
			kpiList = cbt.executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kpiList;
	}
	
	public List getAllOfferingList(String offeringLavel)
	{
		List kpiList = new ArrayList();
		try {
			String query = "select id, subofferingname from offeringlevel"+ offeringLavel;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
			kpiList = cbt.executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kpiList;
	}
	
	public List getKPINameByID(String id)
	{
		List kpi = new ArrayList();
		try {
			String query = "select kpi from krakpicollection where id in"+ id;
			//System.out.println(">>>>>>>>>>Query : "+ query);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			kpi = cbt.executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kpi;
	}

	public void setNoneRegisterUserDetail(String keyword, String[] message, String mobileNo) {
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List <TableColumes> tableColumn=new ArrayList<TableColumes>();
		
		TableColumes ob1=new TableColumes();
		List<String> column = new ArrayList<String>();
		
			column.add("mobile");
			column.add("keyword");
			column.add("message");
			column.add("date");
			column.add("time");
			
			
		ob1.setColumnname("keyword");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		
		for (String columnName : column) {
			ob1=new TableColumes();
			ob1.setColumnname("length");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		}
		
		cbt.createTable22("noneUserDetail",tableColumn,connectionSpace);
		
	}
}
