package com.Over2Cloud.ctrl.asset.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.asset.SpareAction;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceEditGridAction;
import com.opensymphony.xwork2.ActionContext;

public class AssetUniversalHelper
{
	static final Log log = LogFactory.getLog(AssetUniversalHelper.class);
	Map session = ActionContext.getContext().getSession();

	@SuppressWarnings("unchecked")
	public boolean updateTableColNull(String tableName, Map<String, Object> parameterClause, Map<String, Object> condtnBlock, List<String> nullCondtnLIst, SessionFactory connection)
	{
		boolean Data = false;
		StringBuilder selectTableData = new StringBuilder("");

		selectTableData.append("update " + tableName + " set ");
		int size = 1;
		Set set = parameterClause.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			if (size < parameterClause.size())
				selectTableData.append(me.getKey() + " = '" + me.getValue() + "' , ");
			else
				selectTableData.append(me.getKey() + " = '" + me.getValue() + "'");
			size++;
		}

		if (condtnBlock.size() > 0 || nullCondtnLIst.size() > 0)
		{
			selectTableData.append(" where ");
		}
		size = 1;
		if (condtnBlock.size() > 0 && condtnBlock != null)
		{
			set = condtnBlock.entrySet();
			i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < condtnBlock.size())
					selectTableData.append(me.getKey() + " = " + me.getValue() + " and ");
				else
					selectTableData.append(me.getKey() + " = " + me.getValue() + "");
				size++;
			}
		}
		if (nullCondtnLIst != null && nullCondtnLIst.size() > 0)
		{
			size = 1;
			for (int j = 0; j < nullCondtnLIst.size(); j++)
			{
				if (condtnBlock.size() > 0)
				{
					if (nullCondtnLIst.size() > size)
					{
						selectTableData.append(" and " + nullCondtnLIst.get(j) + " IS NULL and ");
					}
					else
					{
						selectTableData.append(" and " + nullCondtnLIst.get(j) + " IS NULL");
					}
				}
				else
				{
					selectTableData.append(nullCondtnLIst.get(j) + " IS NULL");
				}
				size++;
			}

		}
		selectTableData.append(" ;");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			int count = session.createSQLQuery(selectTableData.toString()).executeUpdate();
			if (count > 0)
				Data = true;
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		return Data;
	}

	public boolean insertOrUpdate(int nonConsumeSpare, String spareNameId, SessionFactory connection)
	{
		boolean flag = false;
		Session hSession = null;
		Transaction hTransaction = null;
		int result = 0;
		try
		{
			hSession = connection.openSession();
			hTransaction = hSession.beginTransaction();
			String sql = "INSERT INTO nonconsume_spare_detail (spare_name,total_nonconsume_spare,sms_flag) VALUES (" + spareNameId + ",'" + nonConsumeSpare + "',0)" + "ON DUPLICATE KEY UPDATE total_nonconsume_spare='" + nonConsumeSpare
					+ "',sms_flag=0";
			result = hSession.createSQLQuery(sql).executeUpdate();
			hTransaction.commit();
		}
		catch (Exception e)
		{
			hTransaction.rollback();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method insertOrUpdate of class " + getClass(), e);
		}
		finally
		{
			try
			{
				hSession.flush();
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method insertOrUpdate of class " + getClass(), e);
			}

		}
		if (result > 0)
		{
			flag = true;
		}
		return flag;
	}

	public List getTitleByKey(String tableName, List keyList, String filedName, String condFiled, SessionFactory connection)
	{
		Session hSession = null;
		Transaction hTransaction = null;
		List dataList = null;
		try
		{
			hSession = connection.openSession();
			hTransaction = hSession.beginTransaction();
			String sql = "select " + filedName + " from " + tableName + " where " + condFiled + " IN(:keyList)";
			dataList = hSession.createSQLQuery(sql).setParameterList("keyList", keyList).list();
			hTransaction.commit();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getTitleByKey of class " + getClass(), e);
			hTransaction.rollback();
		}
		finally
		{
			try
			{
				hSession.flush();
			}
			catch (Exception e)
			{
				hTransaction.rollback();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getTitleByKey of class " + getClass(), e);
			}

		}
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public int altTabSetUnique(String tablename, String columnName, SessionFactory connection)
	{
		int size = 0;
		Session session = null;
		Transaction transaction = null;
		try
		{
			String query = "ALTER TABLE " + tablename + " ADD UNIQUE (" + columnName + ")";
			session = connection.openSession();
			transaction = session.beginTransaction();
			size = session.createSQLQuery(query).executeUpdate();
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method altTabSetUnique() of class " + getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return size;
	}

	public boolean insertIntoTable(String tableName, List<InsertDataTable> Tablecolumesaaa, SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder("INSERT IGNORE INTO " + tableName + " (");
		int i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(createTableQuery.toString());
			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
			query.executeUpdate();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method insertIntoTable of class " + getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}
		return flage;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName, Map<String, Object> wherClause, Map<String, Object> order, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select ");

		// Set the columns name of a table
		if (colmName != null && !colmName.equals("") && !colmName.isEmpty())
		{
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}

		// Here we get the whole data of a table
		else
		{
			selectTableData.append(" * from " + tableName);
		}

		// Set the values for where clause
		if (wherClause != null && !wherClause.isEmpty())
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = " + me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '" + me.getValue() + "'");
				size++;
			}
		}

		// Set the value for type of order for getting data in specific order
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " " + me.getValue() + "");
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getDataFromTable of class " + getClass(), ex);
			transaction.rollback();
		}
		finally
		{
		}
		return Data;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName, Map<String, Object> wherClause, Map<String, List> wherClauseList, Map<String, Object> order, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select ");

		// Set the columns name of a table
		if (colmName != null && !colmName.equals("") && !colmName.isEmpty())
		{
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}

		// Here we get the whole data of a table
		else
		{
			selectTableData.append("* from " + tableName);
		}

		// Set the values for where clause
		if (wherClause != null && !wherClause.isEmpty())
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = " + me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '" + me.getValue() + "'");
				size++;
			}
		}

		// Set the values for where clause List
		if (wherClauseList != null && !wherClauseList.isEmpty())
		{
			if (wherClauseList.size() > 0)
			{
				selectTableData.append(" and ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = " + me.getValue().toString().replace("[", "(").replace("]", ")") + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '" + me.getValue().toString().replace("[", "(").replace("]", ")") + "'");
				size++;
			}
		}

		// Set the value for type of order for getting data in specific order
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " " + me.getValue() + "");
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		finally
		{
		}
		return Data;
	}

	@SuppressWarnings("unchecked")
	public List getEmpDataByUserName(String uName, String deptLevel, SessionFactory connection)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id, dept.deptName from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id"
						+ " inner join department as dept on sdept.deptid=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id"
						+ " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			empList = new createTable().executeAllSelectQuery(empall, connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	/*
	 * public List<AssetDashboardBean> getDataForDashboard(SessionFactory
	 * connection,String dataFor) {
	 * 
	 * CommonOperInterface cbt=new CommonConFactory().createInterface(); String
	 * deptHierarchy = (String) session.get("userDeptLevel");
	 * List<AssetDashboardBean> finalDataList = new
	 * ArrayList<AssetDashboardBean>(); try { AssetDashboardBean adb=null;
	 * String query="SELECT dept.deptName,subdept.id FROM department AS dept "+
	 * "INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id "+
	 * "INNER JOIN asset_allotment_log AS alotlog ON alotlog.dept_subdept=subdept.id GROUP BY dept.deptName"
	 * ;
	 * 
	 * List dataList=cbt.executeAllSelectQuery(query, connection);
	 * if(dataList!=null && dataList.size()>0){
	 * if(dataFor.equalsIgnoreCase("totalAllot")){ for (Iterator iterator =
	 * dataList.iterator(); iterator.hasNext();) {
	 * 
	 * Object[] object = (Object[]) iterator.next(); adb=new
	 * AssetDashboardBean(); adb.setDepartName(object[0].toString());
	 * 
	 * List data1=new ArrayList();
	 * 
	 * if(deptHierarchy!=null && deptHierarchy.equals("1")) { String
	 * query1="select count(alg.dept_subdept)from " +
	 * "asset_allotment_log as alg inner join department as dept on " +
	 * "dept.id=alg.dept_subdept where alg.dept_subdept='"
	 * +object[1].toString()+"' group by alg.dept_subdept";
	 * data1=cbt.executeAllSelectQuery(query1.toString(),connection); } else
	 * if(deptHierarchy!=null && deptHierarchy.equals("2")) { String
	 * query2="select count(alg.dept_subdept) from " +
	 * "asset_allotment_log as alg inner join subdepartment as sdept on " +
	 * "sdept.id=alg.dept_subdept inner join department as dept on dept.id=sdept.deptid "
	 * +
	 * "where alg.dept_subdept='"+object[1].toString()+"' group by alg.dept_subdept"
	 * ; data1=cbt.executeAllSelectQuery(query2.toString(),connection); }
	 * System.out.println(data1.size()); for (Iterator iterator2 =
	 * data1.iterator(); iterator2.hasNext();) { Object object1 = (Object)
	 * iterator2.next(); adb.setTotalAllot(object1.toString()); }
	 * 
	 * int totalfree=gettotalFreeData(connection,object[1].toString()); if
	 * (totalfree>0) {
	 * adb.setTotalFree(String.valueOf(Integer.parseInt(adb.getTotalFree
	 * ())+totalfree));
	 * 
	 * } int totalNext7Days=getTotal7DaysData(connection,object[1].toString());
	 * if(totalNext7Days>0){
	 * adb.setNext7Days(String.valueOf(Integer.parseInt(adb
	 * .getNext7Days())+totalNext7Days)); } int
	 * totalNext10Days=getTotal10DaysData(connection,object[1].toString());
	 * if(totalNext10Days>0){
	 * adb.setNext10Days(String.valueOf(Integer.parseInt(adb
	 * .getNext10Days())+totalNext10Days)); } finalDataList.add(adb);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return finalDataList;
	 * 
	 * }
	 */
	public int getTotalFreeAsset(SessionFactory connection, String deptId, String assetType)
	{
		int counter = 0;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT COUNT(id) FROM asset_detail WHERE id NOT IN(SELECT assetid FROM asset_allotment_detail WHERE flag=1) AND status='Active' AND " + "dept_subdept=" + deptId + " AND assettype=" + assetType;
			List freeData = cbt.executeAllSelectQuery(query, connection);
			if (freeData != null && freeData.size() > 0)
			{
				counter = Integer.parseInt(freeData.get(0).toString());
			}

		}
		catch (Exception e)
		{
		}
		return counter;
	}

	public int getTotalSupportDataByDuration(SessionFactory connection, String deptId, String dataFor, String finalDate)
	{
		int counter = 0;
		StringBuilder query = new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();

		query.append("SELECT COUNT(ARD.id) FROM asset_reminder_details AS ARD ");
		query.append(" INNER JOIN asset_detail AS ASD ON ASD.id=ARD.assetid ");
		query.append(" WHERE ASD.status='Active' AND ARD.actionTaken='Pending' AND ASD.dept_subdept=" + deptId);
		query.append(" AND ARD.dueDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + finalDate + "'  AND moduleName='" + dataFor + "'");
		System.out.println(" >>>>>>>>>>>> " + query.toString());
		List totalData = cbt.executeAllSelectQuery(query.toString(), connection);
		if (totalData != null && totalData.size() > 0)
		{
			counter = Integer.parseInt(totalData.get(0).toString());
		}
		else
		{
			counter = 0;
		}
		return counter;
	}

	/*
	 * public int getTotal10DaysData(SessionFactory connection,String id) { int
	 * counter=0; CommonOperInterface cbt=new
	 * CommonConFactory().createInterface(); String
	 * query="SELECT count(*) FROM asset_support_detail as asd "+
	 * "inner join subdepartment as sdept on sdept.id=asd.dept_subdept "+
	 * "inner join department as dept on dept.id=sdept.deptid where asd.dept_subdept='"
	 * +id+"' "+
	 * "AND supportto BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"
	 * +DateUtil.getNewDate("month",1,DateUtil.getCurrentDateUSFormat())+"'";
	 * System.out.println("query is as 10 daysss====="+query); List
	 * totalData=cbt.executeAllSelectQuery(query, connection);
	 * if(totalData!=null && totalData.size()>0){
	 * counter=Integer.parseInt(totalData.get(0).toString()); } return counter;
	 * }
	 */

	public Map<String, Integer> getTotalAllotDtaa(SessionFactory connection, String dataFor)
	{
		Map<String, Integer> listData = new LinkedHashMap<String, Integer>();
		try
		{
			String deptHierarchy = (String) session.get("userDeptLevel");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT dept.deptName,subdept.id FROM department AS dept " + "INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id "
					+ "INNER JOIN asset_allotment_log AS alotlog ON alotlog.dept_subdept=subdept.id GROUP BY dept.deptName";

			List dataList = cbt.executeAllSelectQuery(query, connection);
			if (dataList != null && dataList.size() > 0)
			{
				if (dataFor.equalsIgnoreCase("totalAllot"))
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						List data1 = new ArrayList();

						if (deptHierarchy != null && deptHierarchy.equals("1"))
						{
							String query1 = "select count(alg.dept_subdept)from " + "asset_allotment_log as alg inner join department as dept on " + "dept.id=alg.dept_subdept where alg.dept_subdept='" + object[1].toString()
									+ "' group by alg.dept_subdept";
							data1 = cbt.executeAllSelectQuery(query1.toString(), connection);
						}
						else if (deptHierarchy != null && deptHierarchy.equals("2"))
						{
							String query2 = "select count(alg.dept_subdept) from " + "asset_allotment_log as alg inner join subdepartment as sdept on " + "sdept.id=alg.dept_subdept inner join department as dept on dept.id=sdept.deptid "
									+ "where alg.dept_subdept='" + object[1].toString() + "' group by alg.dept_subdept";
							data1 = cbt.executeAllSelectQuery(query2.toString(), connection);
						}
						for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
						{
							Object object1 = (Object) iterator2.next();
							if (object1 != null && object[0] != null)
							{
								listData.put(object[0].toString(), Integer.parseInt(object1.toString()));
							}
						}
					}
				}
				if (dataFor.equalsIgnoreCase("totalFree"))
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						// int data = gettotalFreeData(connection,
						// object[1].toString());
						// listData.put(object[0].toString(), data);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return listData;

	}

	public List<AssetDashboardBean> getAllAssetDetails(SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		AssetDashboardBean adb;
		List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
		try
		{
			String query = "SELECT dept.deptName,ad.assetname,ad.serialno,ad.assetbrand,ad.assetmodel FROM asset_detail as ad " + "INNER JOIN subdepartment as sdept on sdept.id=ad.dept_subdept "
					+ "INNER JOIN department as dept on dept.id=sdept.deptid ";
			List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				adb = new AssetDashboardBean();
				Object[] object = (Object[]) iterator.next();
				if (object != null && object.length > 0)
				{
					if (object[0] != null)
					{
						adb.setDepartName(object[0].toString());
					}
					else
					{
						adb.setDepartName("NA");
					}
					if (object[1] != null)
					{
						adb.setAssetName(object[1].toString());
					}
					else
					{
						adb.setAssetName("NA");
					}
					if (object[2] != null)
					{
						adb.setAssetserial(object[2].toString());
					}
					else
					{
						adb.setAssetserial("NA");
					}
					if (object[3] != null)
					{
						adb.setModel(object[3].toString());
					}
					else
					{
						adb.setModel("NA");
					}
					if (object[4] != null)
					{
						adb.setBrand(object[4].toString());
					}
					else
					{
						adb.setBrand("NA");
					}
				}
				finalDataList.add(adb);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return finalDataList;
	}

	/*
	 * public Map<String, Integer> getAllSupportDetails(SessionFactory
	 * connectionSpace) { CommonOperInterface cbt=new
	 * CommonConFactory().createInterface(); Map<String ,Integer > total=new
	 * LinkedHashMap<String, Integer>(); try { Map<String ,Integer >
	 * months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"3"); if(months3List!=null
	 * && months3List.size()>0){} for (Entry<String, Integer> entry :
	 * months3List.entrySet()) { total.put(entry.getKey(), entry.getValue()); }
	 * months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"6"); for (Entry<String,
	 * Integer> entry : months3List.entrySet()) { total.put(entry.getKey(),
	 * entry.getValue()); } months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"9"); for (Entry<String,
	 * Integer> entry : months3List.entrySet()) { total.put(entry.getKey(),
	 * entry.getValue()); } months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"12"); for (Entry<String,
	 * Integer> entry : months3List.entrySet()) { total.put(entry.getKey(),
	 * entry.getValue()); } months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"18"); for (Entry<String,
	 * Integer> entry : months3List.entrySet()) { total.put(entry.getKey(),
	 * entry.getValue()); } months3List=new LinkedHashMap<String, Integer>();
	 * months3List=getSupportDetails(connectionSpace,"24"); for (Entry<String,
	 * Integer> entry : months3List.entrySet()) { total.put(entry.getKey(),
	 * entry.getValue()); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return total; }
	 */

	private Map<String, Integer> getSupportDetails(String deptId, SessionFactory connectionSpace, String s, String dataFor)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		Map<String, Integer> listData = new LinkedHashMap<String, Integer>();
		try
		{
			String date = DateUtil.getNewDate("month", Integer.parseInt(s), DateUtil.getCurrentDateUSFormat());
			String datearr[] = date.split("-");
			int temp = Integer.parseInt(datearr[2]) - 1;
			String finalDate;
			if (temp < 10)
			{
				finalDate = datearr[0] + "-" + datearr[1] + "-0" + temp;
			}
			else
			{
				finalDate = datearr[0] + "-" + datearr[1] + "-" + temp;
			}
			if (dataFor.equalsIgnoreCase("Support"))
			{
				query.append("SELECT COUNT(ARD.id) FROM asset_reminder_details AS ARD ");
				query.append(" INNER JOIN asset_detail AS ASD ON ASD.id=ARD.assetid ");
				query.append(" WHERE ASD.status='Active' AND ARD.actionTaken='Pending' AND ASD.dept_subdept=" + deptId);
				query.append(" AND ARD.dueDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '2014-03-20'");
				// query = "SELECT count(*) FROM asset_support_detail as asd " +
				// "inner join subdepartment as sdept on sdept.id=asd.dept_subdept "
				// +
				// "inner join department as dept on dept.id=sdept.deptid where  "
				// + "dept_subdept='" + id + "' AND " + "supportto BETWEEN '" +
				// DateUtil.getCurrentDateUSFormat() + "' AND '" + finalDate +
				// "'";
			}
			if (dataFor.equalsIgnoreCase("AssetExpire"))
			{
				/*
				 * query = "SELECT count(*) FROM procurement_detail as pd " +
				 * "INNER JOIN asset_detail as ad on ad.id=pd.assetid " +
				 * "INNER JOIN createvendor_master as cv on cv.id=pd.vendorname "
				 * +
				 * "INNER JOIN asset_allotment_detail AS asd ON asd.assetid=ad.id "
				 * +
				 * "INNER JOIN employee_basic AS emp ON emp.id=asd.employeeid "
				 * +
				 * "WHERE pd.assetid IN(SELECT id FROM asset_detail WHERE dept_subdept='"
				 * + deptId + "') " + "AND expectedlife BETWEEN '" +
				 * DateUtil.getCurrentDateUSFormat() + "' AND '" + finalDate +
				 * "'";
				 */
			}
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object != null)
					{
						int month3Counter = Integer.parseInt(object.toString());
						listData.put("" + s + " Months Support", month3Counter);
					}
					else
					{
						listData.put("" + s + " Months Support", 0);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return listData;
	}

	public Map<String, String> getSpareGraph(SessionFactory connectionSpace)
	{
		Map<String, String> consumeNonConsumeSpareMap = null;
		Map<String, String> resultMAp = new LinkedHashMap<String, String>();
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			consumeNonConsumeSpareMap = new LinkedHashMap<String, String>();
			query1.append("select SUM(total_nonconsume_spare) from nonconsume_spare_detail");
			List totalRemainIng = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

			String deptHierarchy = (String) session.get("userDeptLevel");
			if (deptHierarchy.equals("2"))
			{
				query1 = new StringBuilder("");
				query1.append("SELECT SUM(sid.no_issue),dept.deptName FROM spare_issue_detail as sid " + "INNER JOIN subdepartment sdept on sdept.id=sid.dept_subdept " + "INNER JOIN department dept on dept.id=sdept.deptid GROUP BY dept.deptName");
			}
			else if (deptHierarchy.equals("1"))
			{
				query1 = new StringBuilder("");
				query1.append("SELECT SUM(sid.no_issue),dept.deptName FROM spare_issue_detail as sid " + "INNER JOIN department dept on dept.id=sid.dept_subdept GROUP BY dept.deptName");
			}
			if (query1 != null)
			{
				List totalConsume = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (totalConsume != null && totalConsume.size() > 0)
				{
					for (Iterator iterator = totalConsume.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						consumeNonConsumeSpareMap.put(object[1].toString(), object[0].toString());
					}
				}
				if (totalRemainIng != null && totalRemainIng.size() > 0)
					;
				{
					consumeNonConsumeSpareMap.put("Remaining Spare", totalRemainIng.get(0).toString());

					if (consumeNonConsumeSpareMap != null && consumeNonConsumeSpareMap.size() > 0)
					{
						resultMAp = consumeNonConsumeSpareMap;
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultMAp;

	}

	public List getEmpSubdept(String username, String deptHierarchy, SessionFactory connectionSpace)
	{
		String query = null;
		List subDept = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			if (deptHierarchy.equalsIgnoreCase("2"))
			{
				query = "select sdept.id as sdeptid from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id" + " inner join department as dept on sdept.deptid=dept.id"
						+ " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + username + "'";

			}
			else
			{
				query = "select emp.dept as deptid from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + username + "'";

			}

			subDept = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return subDept;
	}

/*	public List<AssetDashboardBean> getTotalAsset(String deptid, SessionFactory connectionSpace, String dataFor)
	{
		String query = null;
		String query2 = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<AssetDashboardBean> list = new ArrayList<AssetDashboardBean>();
		AssetDashboardBean ADB;
		try
		{
			if (dataFor.equalsIgnoreCase("asset"))
			{
				String assetTypeQuery = "SELECT id,assetSubCat FROM createasset_type_master WHERE status='Active'";
				List dataList = cbt.executeAllSelectQuery(assetTypeQuery, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						ADB = new AssetDashboardBean();
						ADB.setAssetName(object[1].toString());

						query = "select count(*) from asset_detail where status='Active' and dept_subdept='" + deptid + "' and assettype=" + object[0].toString();
						List totalAsset = cbt.executeAllSelectQuery(query, connectionSpace);
						if (totalAsset != null && totalAsset.size() > 0)
						{
							ADB.setTotalAsset(totalAsset.get(0).toString());
							ADB.setAssetType(object[0].toString());
							ADB.setDepartId(deptid);
							query2 = "SELECT COUNT(allot.id) FROM asset_allotment_detail AS allot " + "INNER JOIN asset_detail AS asd ON asd.id=allot.assetid WHERE asd.status='Active' AND allot.flag=1 " + "AND asd.dept_subdept=" + deptid
									+ "  AND asd.assettype=" + object[0].toString();
							List totalAllotData = cbt.executeAllSelectQuery(query2, connectionSpace);
							if (totalAllotData != null && totalAllotData.size() > 0)
							{
								ADB.setTotalAllot(totalAllotData.get(0).toString());
								int totalfree = getTotalFreeAsset(connectionSpace, deptid, object[0].toString());
								if (totalfree > 0)
								{
									ADB.setTotalFree(String.valueOf(Integer.parseInt(ADB.getTotalFree()) + totalfree));
									String onServiceQuery = "select count(assetid) from asset_repair_detail " + "where assetid IN(select assetid from asset_allotment_log where dept_subdept='9')";
									List data = cbt.executeAllSelectQuery(onServiceQuery, connectionSpace);
									if (data != null && data.size() > 0)
									{
										ADB.setOnService(data.get(0).toString());
										ADB.setDepartId(deptid);
									}
									else
									{
										ADB.setOnService("0");
									}
								}
								else
								{
									ADB.setTotalFree("0");
								}
							}
							else
							{
								ADB.setTotalAllot("0");
							}
						}
						if (totalAsset == null)
						{
							ADB.setTotalAsset("0");
						}
						list.add(ADB);
					}
				}
			}

			
			 * if (dataFor.equalsIgnoreCase("Spare")) { ADB = new
			 * AssetDashboardBean(); String spareFree = null; query =
			 * "select count(*) from spare_detail"; List totalList =
			 * cbt.executeAllSelectQuery(query, connectionSpace); if (totalList
			 * != null && totalList.size() > 0) {
			 * ADB.setTotalAsset(totalList.get(0).toString()); query2 =
			 * "select count(*) from spare_issue_detail where dept_subdept='" +
			 * subdeptid + "'"; List spareData =
			 * cbt.executeAllSelectQuery(query2, connectionSpace); if (spareData
			 * != null && spareData.size() > 0) {
			 * ADB.setTotalAllot(spareData.get(0).toString()); spareFree =
			 * String.valueOf(Integer.parseInt(totalList.get(0).toString()) -
			 * Integer.parseInt(spareData.get(0).toString()));
			 * ADB.setTotalFree(spareFree); ADB.setDepartId(subdeptid);
			 * ADB.setAssetName("Spare"); } else { ADB.setTotalAllot("0");
			 * ADB.setTotalFree("0"); ADB.setAssetName("Spare"); }
			 * 
			 * } else { ADB.setTotalAsset("0"); } list.add(ADB); }
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}*/

	@SuppressWarnings("unchecked")
	public List<AssetDashboardBean> getRankForAsset(String deptId, SessionFactory connectionSpace, String dataFor)
	{
		List<AssetDashboardBean> list = new ArrayList<AssetDashboardBean>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = null;
		AssetDashboardBean ADB;
		try
		{
			if (dataFor != null && dataFor.equalsIgnoreCase("AssetRank"))
			{
				String assetTypeQry = "SELECT id,assetSubCat FROM createasset_type_master WHERE status='Active'";
				List dataList = cbt.executeAllSelectQuery(assetTypeQry, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						query = new StringBuilder();
						Object[] object = (Object[]) iterator.next();
						query.append("SELECT COUNT(*) FROM asset_detail AS ast ");
						query.append(" INNER JOIN asset_complaint_status AS acs ON acs.asset_id=ast.id");
						query.append(" WHERE ast.dept_subdept=" + deptId + " AND ast.status='Active' AND ast.assettype=" + object[0].toString());
						List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							ADB = new AssetDashboardBean();
							ADB.setAssetScore(Integer.valueOf(data.get(0).toString()));
							ADB.setAssetType(object[1].toString());
							ADB.setAssetid(object[0].toString());
							ADB.setDepartId(deptId);
							list.add(ADB);
						}
					}
				}
			}
			else if (dataFor != null && dataFor.equalsIgnoreCase("UserRank"))
			{
				query = new StringBuilder();
				query.append("SELECT employee.empName,COUNT(employee.id),employee.id AS counts FROM asset_complaint_status AS asset_complaint ");
				query.append(" INNER JOIN asset_allotment_log AS asset_allot ON asset_complaint.asset_id=asset_allot.assetid");
				query.append(" INNER JOIN compliance_contacts AS contacts ON asset_allot.employeeid=contacts.id");
				query.append(" INNER JOIN employee_basic AS employee ON contacts.emp_id=employee.id");
				query.append(" INNER JOIN asset_detail AS asset ON asset.id=asset_allot.assetid");
				query.append(" WHERE asset.dept_subdept=" + deptId + " GROUP BY employee.id");
				// System.out.println("User Rank "+query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						query = new StringBuilder();
						Object[] object = (Object[]) iterator.next();

						ADB = new AssetDashboardBean();
						if (object != null && object[0] != null)
						{
							ADB.setEmpName(object[0].toString());
							ADB.setAssetScore(Integer.valueOf(object[1].toString()));
							ADB.setAssetid(object[2].toString());
							ADB.setDepartId(deptId);
							list.add(ADB);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}

	private String getAssetName(String assetid, SessionFactory connectionSpace, String dataFor)
	{
		String asetName = null;
		String query = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();

		try
		{
			if (dataFor.equalsIgnoreCase("AssetRank"))
			{
				query = "select catm.assetSubCat from createasset_type_master as catm " + "INNER JOIN asset_detail as ad on  ad.astcategory=catm.id where ad.id='" + assetid + "'";
			}
			if (dataFor.equalsIgnoreCase("userRAnk"))
			{
				query = "SELECT empName FROM employee_basic WHERE id='" + assetid + "'";
			}
			List assetData = cbt.executeAllSelectQuery(query, connectionSpace);
			if (assetData != null && assetData.size() > 0)
			{
				asetName = assetData.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return asetName;
	}

	public List<AssetDashboardBean> supportDetails(String deptId, SessionFactory connectionSpace, String dataFor)
	{
		List<AssetDashboardBean> list = new ArrayList<AssetDashboardBean>();
		AssetDashboardBean adb;
		try
		{
			adb = new AssetDashboardBean();
			String finalDate = DateUtil.getNewDate("day", 7, DateUtil.getCurrentDateUSFormat());
			int support7Days = getTotalSupportDataByDuration(connectionSpace, deptId, dataFor, finalDate);
			adb.setNext7Days(String.valueOf(support7Days));

			finalDate = DateUtil.getNewDate("day", 30, DateUtil.getCurrentDateUSFormat());
			int support30Days = getTotalSupportDataByDuration(connectionSpace, deptId, dataFor, finalDate);
			adb.setNext30Days(String.valueOf(support30Days));

			finalDate = DateUtil.getNewDate("day", 90, DateUtil.getCurrentDateUSFormat());
			int support90Days = getTotalSupportDataByDuration(connectionSpace, deptId, dataFor, finalDate);
			adb.setNext90Days(String.valueOf(support90Days));

			finalDate = DateUtil.getNewDate("day", 180, DateUtil.getCurrentDateUSFormat());
			int support180Days = getTotalSupportDataByDuration(connectionSpace, deptId, dataFor, finalDate);
			adb.setNext180Days(String.valueOf(support180Days));

			if (dataFor.equalsIgnoreCase("Support"))
			{
				adb.setAssetName("Support");
			}
			else if (dataFor.equalsIgnoreCase("Preventive"))
			{
				adb.setAssetName("Preventive");
			}
			adb.setDepartId(deptId);
			list.add(adb);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getLatestSeries(String seriesType, String deptid, SessionFactory connection)
	{
		String ticketno = "", query = "";
		List list = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (seriesType.equals("N"))
		{
			query = "select ast.serialno from asset_detail as ast where ast.id=(select max(ast.id) from asset_detail as ast)";
		}
		else if (seriesType.equals("D"))
		{
			query = "select ast.serialno from asset_detail as ast where ast.id=(select max(ast.id) from asset_detail as ast where " + "ast.dept_subdept in (select id from subdepartment where deptid=" + deptid + "))";
		}
		list = cbt.executeAllSelectQuery(query, connection);
		if (list != null && list.size() > 0)
		{
			ticketno = list.get(0).toString();
		}
		return ticketno;
	}

	public String getFrequencyName(String freqAbrivation)
	{
		String frequency = null;

		if (freqAbrivation.equalsIgnoreCase("OT"))
			frequency = "One Time";
		else if (freqAbrivation.equalsIgnoreCase("D"))
			frequency = "Daily";
		else if (freqAbrivation.equalsIgnoreCase("W"))
			frequency = "Weekly";
		else if (freqAbrivation.equalsIgnoreCase("M"))
			frequency = "Monthly";
		else if (freqAbrivation.equalsIgnoreCase("BM"))
			frequency = "Bi-Monthly";
		else if (freqAbrivation.equalsIgnoreCase("Q"))
			frequency = "Quaterly";
		else if (freqAbrivation.equalsIgnoreCase("HY"))
			frequency = "Half Yearly";
		else if (freqAbrivation.equalsIgnoreCase("Y"))
			frequency = "Yearly";

		return frequency;
	}

	public String getMailBodyAsset(List<AssetDashboardBean> assetData, String alias, String name, String remindFor, String logedName)
	{
		StringBuffer mailText = new StringBuffer("");
		mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Dear " + name
				+ ",</b></td></tr></tbody></table>");
		mailText.append("<br>");
		if (alias.equalsIgnoreCase("allot"))
		{
			mailText
					.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Following Asset Support Task has been allotted to you:</b></tr></tbody></table>");
			mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		}
		if (alias.equalsIgnoreCase("confi"))
		{
			mailText
					.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Following Asset Support Task has been Registered By you:</b></tr></tbody></table>");
			mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		}
		if (assetData != null && assetData.size() > 0)
		{
			for (AssetDashboardBean ist : assetData)
			{
				if (remindFor.equalsIgnoreCase("remToSelf"))
				{
					mailText
							.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>You</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToOther") && alias.equals("allot"))
				{
					mailText
							.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
									+ logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToOther") && alias.equals("confi"))
				{
					mailText
							.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
									+ logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToBoth") && alias.equals("allot"))
				{
					mailText
							.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
									+ logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToBoth") && alias.equals("confi"))
				{
					mailText
							.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
									+ logedName + "</td></tr>");
				}
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Asset Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getType() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Asset Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getAssetName() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Asset Code:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getAssetcode() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Asset Serial No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getAssetserial() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Due Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getDueDate() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Due Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getDueTime() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Frequency:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getFrequency() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Escalation 1 Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getEscL1() + "</td></tr>");
				mailText
						.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Escalation 1 Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ ist.getEscL1Duration() + "</td></tr>");

			}
		}
		mailText.append("</table>");
		mailText.append("<br>");
		mailText.append("<br>");
		mailText
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailText.toString();
	}

	public String getMsgBodyAsset(String allotedBy, String assetName, String dueDate, String escTime)
	{
		StringBuilder msgBody = new StringBuilder();
		try
		{
			msgBody.append("Asset Support Allotment Alert: Asset Name: " + assetName + " Allotted By: " + allotedBy + ", Due Date: " + dueDate + " Escalation Time: " + escTime + ".");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return msgBody.toString();
	}

	public String getMsgBodyAssetReg(String allotedTo, String assetName, String dueDate, String escTime)
	{
		StringBuilder msgBody = new StringBuilder();
		try
		{
			msgBody.append("Asset Support Registration Alert: Asset Name: " + assetName + " is Allotted To: " + allotedTo + ", Due Date: " + dueDate + " Escalation Time: " + escTime + ".");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return msgBody.toString();
	}

	@SuppressWarnings("unchecked")
	public List<AssetDashboardBean> setAnalyticalReportValues(List TotalTicket, List onTime, List offTime, List snooze, List missed, List ignore, String reportFor)
	{
		List<AssetDashboardBean> feedList = new ArrayList<AssetDashboardBean>();
		if (TotalTicket != null && TotalTicket.size() > 0)
		{
			Object[] obdata = null;
			for (Iterator iterator = TotalTicket.iterator(); iterator.hasNext();)
			{
				obdata = (Object[]) iterator.next();
				AssetDashboardBean fbp = new AssetDashboardBean();
				if (reportFor.equalsIgnoreCase("Employee"))
				{
					fbp.setEmpId(obdata[0].toString());
					fbp.setEmpName(obdata[1].toString());
					fbp.setFeedback_by_subdept(obdata[3].toString());
				}
				else if (reportFor.equalsIgnoreCase("Category"))
				{
					fbp.setFeedId(obdata[0].toString());
					fbp.setFeedback_catg(obdata[1].toString());
					fbp.setFeedback_subcatg(obdata[3].toString());
				}
				fbp.setCounter(obdata[2].toString());
				if (onTime != null && onTime.size() > 0)
				{
					Object[] obdata1 = null;
					for (Iterator iterator2 = onTime.iterator(); iterator2.hasNext();)
					{
						obdata1 = (Object[]) iterator2.next();
						if (obdata1 != null && !obdata.equals(""))
						{
							if (obdata[0].toString().equalsIgnoreCase(obdata1[0].toString()))
							{
								fbp.setOnTime(obdata1[2].toString());
								int perOnTime = (Integer.parseInt(obdata1[2].toString()) * 100) / Integer.parseInt(obdata[2].toString());
								fbp.setPerOnTime(String.valueOf(perOnTime));
							}
						}
					}
				}
				else
				{
					fbp.setOnTime("0");
				}
				if (offTime != null && offTime.size() > 0)
				{
					Object[] obdata2 = null;
					for (Iterator iterator3 = offTime.iterator(); iterator3.hasNext();)
					{
						obdata2 = (Object[]) iterator3.next();
						if (obdata2 != null && !obdata.equals(""))
						{
							if (obdata[0].toString().equalsIgnoreCase(obdata2[0].toString()))
							{
								fbp.setOffTime(obdata2[2].toString());
								int perOffTime = (Integer.parseInt(obdata2[2].toString()) * 100) / Integer.parseInt(obdata[2].toString());
								fbp.setPerOffTime(String.valueOf(perOffTime));
							}
						}
					}
				}
				else
				{
					fbp.setOffTime("0");
				}
				if (missed != null && missed.size() > 0)
				{
					Object[] obdata3 = null;
					for (Iterator iterator4 = missed.iterator(); iterator4.hasNext();)
					{
						obdata3 = (Object[]) iterator4.next();
						if (obdata3 != null && !obdata.equals(""))
						{
							if (obdata[0].toString().equalsIgnoreCase(obdata3[0].toString()))
							{
								fbp.setMissed(obdata3[2].toString());
								int perMissed = (Integer.parseInt(obdata3[2].toString()) * 100) / Integer.parseInt(obdata[2].toString());
								fbp.setPerMissed(String.valueOf(perMissed));
							}
						}
					}
				}
				else
				{
					fbp.setMissed("0");
				}
				if (snooze != null && snooze.size() > 0)
				{
					Object[] obdata4 = null;
					for (Iterator iterator5 = snooze.iterator(); iterator5.hasNext();)
					{
						obdata4 = (Object[]) iterator5.next();
						if (obdata4 != null && !obdata.equals(""))
						{
							if (obdata[0].toString().equalsIgnoreCase(obdata4[0].toString()))
							{
								fbp.setSnooze(obdata4[2].toString());
							}
						}
					}
				}
				else
				{
					fbp.setSnooze("0");
				}
				if (ignore != null && ignore.size() > 0)
				{
					Object[] ob = null;
					for (Iterator iterator2 = ignore.iterator(); iterator2.hasNext();)
					{
						ob = (Object[]) iterator2.next();
						if (ob != null && !obdata.equals(""))
						{
							if (obdata[0].toString().equalsIgnoreCase(ob[0].toString()))
							{
								fbp.setIgnore(ob[2].toString());
							}
						}
					}
				}
				else
				{
					fbp.setIgnore("0");
				}
				feedList.add(fbp);
			}
		}
		return feedList;
	}
	
	@SuppressWarnings("unchecked")
	public List<AssetDashboardBean> getAllReminderOfAsset(String logedDeptId, String userEmpID, String userContID,SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder ids = new StringBuilder();
		List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
		AssetDashboardBean dashBean = null;
		String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpID+")";
		List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
		StringBuilder empId = new StringBuilder();
		if (shareDataCount != null && shareDataCount.size() > 0)
		{
			for (int i = 0; i < shareDataCount.size(); i++)
			{
				empId.append(shareDataCount.get(i).toString()+",");
				String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
				if (mappedEmpId != null)
				{
					empId.append(mappedEmpId+",");
				}
			}
		}
		String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", userEmpID);
		if (mappedEmpId != null)
		{
			empId.append(mappedEmpId+",");
		}
		empId.append(userEmpID);
		
		List outletNameList = getOutletNameByEmpId(empId.toString(),false,connectionSpace);
		if(outletNameList!=null && outletNameList.size()>0)
		{
			for (Iterator iterator = outletNameList.iterator(); iterator.hasNext();)
			{
				dashBean = new AssetDashboardBean();
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					List reminderList = new ArrayList();
					dashBean.setOutletName(object[1].toString());
					reminderList=getRemrinderByOutletId(object[0].toString(),"Preventive",connectionSpace);
					System.out.println("++++++++++++++++++ "+reminderList.size());
					if(reminderList!=null && reminderList.size()>0)
					{
						dashBean.setTotalPM(String.valueOf(reminderList.size()));
						for (Iterator iterator2 = reminderList.iterator(); iterator2.hasNext();)
						{
							Object[] reminderObject = (Object[]) iterator2.next();
							System.out.println(">>>>>>>>>>> "+reminderObject[0].toString());
							
							ids.append(reminderObject[0].toString()+",");
							int missedCount=0;
							int next7Count=0;
							int next30Count=0;
							int next90Count=0;
							if(reminderObject[1]!=null && reminderObject[2]!=null)
							{
								String startDate=null;
								String endDate=null;
								String middleDate=null;
								if(reminderObject[3]!=null && reminderObject[3].toString().equalsIgnoreCase("Pending"))
								{
									boolean dateMissedStatus = DateUtil.time_update(reminderObject[1].toString(), reminderObject[2].toString());
									if(dateMissedStatus)
									{
										missedCount=1;
									}
								}
								if(reminderObject[3]!=null && !reminderObject[3].toString().equalsIgnoreCase("Done") && !reminderObject[3].toString().equalsIgnoreCase("Recurring"))
								{
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("day", 8, DateUtil.getCurrentDateUSFormat());
									
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next7Count=1;
									}
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("day", 1, endDate);
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next30Count=1;
									}
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("day", 1, endDate);
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next90Count=1;
									}
								}
							}
							if(missedCount>0)
							{
								dashBean.setMissedPM(String.valueOf(Integer.valueOf(dashBean.getMissedPM())+missedCount));
							}
							if(next7Count>0)
							{
								dashBean.setNext7DaysPM(String.valueOf(Integer.valueOf(dashBean.getNext7DaysPM())+next7Count));
							}
							if(next30Count>0)
							{
								dashBean.setNext30DaysPM(String.valueOf(Integer.valueOf(dashBean.getNext30DaysPM())+next30Count));
							}
							if(next90Count>0)
							{
								dashBean.setNext90DaysPM(String.valueOf(Integer.valueOf(dashBean.getNext90DaysPM())+next90Count));
							}
						}
						if(ids.length()>0)
						{
							dashBean.setReminderIdPM(ids.toString()+"0");
							ids.setLength(0);
						}
							
					}
					if(reminderList!=null && reminderList.size()>0)
						reminderList.clear();
					
					reminderList=getRemrinderByOutletId(object[0].toString(),"Support",connectionSpace);
					if(reminderList!=null && reminderList.size()>0)
					{
						dashBean.setTotalSupport(String.valueOf(reminderList.size()));
						for (Iterator iterator2 = reminderList.iterator(); iterator2.hasNext();)
						{
							Object[] reminderObject = (Object[]) iterator2.next();
							System.out.println("Support Id s "+reminderObject[0].toString());
							ids.append(reminderObject[0].toString()+",");
							int missedCount=0;
							int next7Count=0;
							int next30Count=0;
							int next90Count=0;
							if(reminderObject[1]!=null && reminderObject[2]!=null)
							{
								String startDate=null;
								String endDate=null;
								String middleDate=null;
								
								if(reminderObject[3]!=null && reminderObject[3].toString().equalsIgnoreCase("Pending"))
								{
									boolean dateMissedStatus = DateUtil.time_update(reminderObject[1].toString(), reminderObject[2].toString());
									if(dateMissedStatus)
									{
										missedCount=1;
									}
								}
								
								if(reminderObject[3]!=null && !reminderObject[3].toString().equalsIgnoreCase("Done") && !reminderObject[3].toString().equalsIgnoreCase("Recurring"))
								{
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("day", 8, DateUtil.getCurrentDateUSFormat());
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next7Count=1;
									}
									
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next30Count=1;
									}
									
									
									startDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
									endDate=DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
									middleDate=reminderObject[1].toString();
									if(DateUtil.checkDateBetween(startDate,middleDate,endDate))
									{
										next90Count=1;
									}
								}
							}
							if(missedCount>0)
							{
								dashBean.setMissedSupport(String.valueOf(Integer.valueOf(dashBean.getMissedSupport())+missedCount));
							}
							if(next7Count>0)
							{
								dashBean.setNext7Days(String.valueOf(Integer.valueOf(dashBean.getNext7Days())+next7Count));
							}
							if(next30Count>0)
							{
								dashBean.setNext30Days(String.valueOf(Integer.valueOf(dashBean.getNext30Days())+next30Count));
							}
							if(next90Count>0)
							{
								dashBean.setNext90Days(String.valueOf(Integer.valueOf(dashBean.getNext90Days())+next90Count));
							}
						}
						if(ids.length()>0)
						{
							dashBean.setReminderIdSupport(ids.toString()+"0");
							ids.setLength(0);
						}
					}
				}
				finalDataList.add(dashBean);
			}
		}
		return finalDataList;
	}
	
	public List getOutletNameByEmpId(String empId,boolean flag,SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ABD.id,ABD.associateName FROM associate_basic_data AS ABD ");
		query.append(" INNER JOIN associate_contact_data AS ACD ON ACD.associateName=ABD.id ");
		query.append(" INNER JOIN employee_basic AS EMP ON EMP.mobOne=ACD.contactNum ");
		if (flag)
		{
			query.append(" INNER JOIN asset_complaint_status AS acs ON acs.location=ABD.id ");
		}
		query.append(" WHERE EMP.id IN("+empId+") ");
		query.append(" GROUP BY ABD.associateName ");
		System.out.println("OUTLET QUERY ::  "+query.toString());
		List outletNameList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return outletNameList;
	}
	
	public List getRemrinderByOutletId(String outletId,String moduleName,SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ARD.id,ARD.dueDate,ARD.dueTime,ARD.actionTaken ");
		query.append(" FROM asset_reminder_details AS ARD  ");
		query.append(" INNER JOIN asset_detail AS AD ON AD.id=ARD.assetid ");
		query.append(" INNER JOIN asset_allotment_detail AS AAD ON AAD.assetid=AD.id ");
		query.append(" WHERE AAD.outletid="+outletId+" ");
		if (moduleName!=null && !moduleName.equalsIgnoreCase("")) 
		{
			query.append(" AND ARD.moduleName='"+moduleName+"'");
		}
		List outletNameList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return outletNameList;
	}

	public List<AssetDashboardBean> getDataForTicketStatus(String userEmpId,String userContactId,SessionFactory connectionSpace) 
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder ids = new StringBuilder();
		List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
		try 
		{
			ComplianceEditGridAction CEA=new ComplianceEditGridAction();
			AssetDashboardBean dashBean = null;
			String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpId+")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			StringBuilder empId = new StringBuilder();
			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					empId.append(shareDataCount.get(i).toString()+",");
					String mappedEmpId = CEA.getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
					if (mappedEmpId != null)
					{
						empId.append(mappedEmpId+",");
					}
				}
			}
			String mappedEmpId = CEA.getLevelHierarchy("ASTM", userEmpId);
			if (mappedEmpId != null)
			{
				empId.append(mappedEmpId+",");
			}
			empId.append(userEmpId);
			
			List outletNameList = getOutletNameByEmpId(empId.toString(),true,connectionSpace);
			if(outletNameList!=null && outletNameList.size()>0)
			{
				for (Iterator iterator = outletNameList.iterator(); iterator.hasNext();)
				{
					dashBean = new AssetDashboardBean();
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						dashBean.setId(Integer.parseInt(object[0].toString()));
						dashBean.setOutletName(object[1].toString());
						String query ="SELECT id,status,level FROM asset_complaint_status WHERE location ='"+object[0].toString()+"' ";
						System.out.println(">>>>>   "+query);
						List compliantData=cbt.executeAllSelectQuery(query, connectionSpace);
						if (compliantData!=null && compliantData.size()>0) 
						{
							for (Iterator iterator2 = compliantData.iterator(); iterator2.hasNext();)
							{
								int pending=0,level1=0,level2=0,level3=0,level4=0,level5=0,snooze=0,ignore=0,highPriority=0,resolve=0;
								Object[] object2 = (Object[]) iterator2.next();
								if(object2[0]!=null && object2[1]!=null)
								{
									if (object2[1].toString().equalsIgnoreCase("Pending")) 
									{
										pending=1;
									}
									if (object2[2].toString().equalsIgnoreCase("Level1") && !object2[1].toString().equalsIgnoreCase("Ignore") && !object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										level1=1;
									}
									if (object2[2].toString().equalsIgnoreCase("Level2") && !object2[1].toString().equalsIgnoreCase("Ignore") && !object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										level2=1;
									}
									if (object2[2].toString().equalsIgnoreCase("Level3") && !object2[1].toString().equalsIgnoreCase("Ignore") && !object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										level3=1;
									}
									if (object2[2].toString().equalsIgnoreCase("Level4") && !object2[1].toString().equalsIgnoreCase("Ignore") && !object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										level4=1;
									}
									if (object2[2].toString().equalsIgnoreCase("Level5") && !object2[1].toString().equalsIgnoreCase("Ignore") && !object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										level5=1;
									}
									if (object2[1].toString().equalsIgnoreCase("Snooze")) 
									{
										snooze=1;
									}
									if (object2[1].toString().equalsIgnoreCase("High Priority")) 
									{
										highPriority=1;
									}
									if (object2[1].toString().equalsIgnoreCase("Ignore")) 
									{
										ignore=1;
									}
									if (object2[1].toString().equalsIgnoreCase("Resolved")) 
									{
										resolve=1;
									}
								}
								if (pending>0) 
								{
									dashBean.setPending(String.valueOf(Integer.parseInt(dashBean.getPending())+pending));
								}
								if (level1>0) 
								{
									dashBean.setLevel1(String.valueOf(Integer.parseInt(dashBean.getLevel1())+level1));
								}
								if (level2>0) 
								{
									dashBean.setLevel2(String.valueOf(Integer.parseInt(dashBean.getLevel2())+level2));
								}
								if (level3>0) 
								{
									dashBean.setLevel3(String.valueOf(Integer.parseInt(dashBean.getLevel3())+level3));
								}
								if (level4>0) 
								{
									dashBean.setLevel1(String.valueOf(Integer.parseInt(dashBean.getLevel4())+level4));
								}
								if (level5>0) 
								{
									dashBean.setLevel1(String.valueOf(Integer.parseInt(dashBean.getLevel5())+level5));
								}
								if (snooze>0) 
								{
									dashBean.setSnooze(String.valueOf(Integer.parseInt(dashBean.getSnooze())+snooze));
								}
								if (ignore>0) 
								{
									dashBean.setIgnore(String.valueOf(Integer.parseInt(dashBean.getIgnore())+ignore));
								}
								if (highPriority>0) 
								{
									dashBean.setHighpriority(String.valueOf(Integer.parseInt(dashBean.getHighpriority())+highPriority));
								}
								if (resolve>0) 
								{
									dashBean.setResolve(String.valueOf(Integer.parseInt(dashBean.getResolve())+resolve));
								}
							}
						}
						dashBean.setCounter(String.valueOf(Integer.parseInt(dashBean.getPending())+Integer.parseInt(dashBean.getLevel1())+Integer.parseInt(dashBean.getLevel2())+Integer.parseInt(dashBean.getLevel3())+Integer.parseInt(dashBean.getLevel4())+Integer.parseInt(dashBean.getLevel5())+Integer.parseInt(dashBean.getSnooze())+Integer.parseInt(dashBean.getHighpriority())+Integer.parseInt(dashBean.getIgnore())+Integer.parseInt(dashBean.getResolve())));
						finalDataList.add(dashBean);
					}
					
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return finalDataList;
	}

	public List<AssetDashboardBean> getDataForPendingTicketStatus(String userEmpId, SessionFactory connectionSpace) 
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder ids = new StringBuilder();
		List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
		try 
		{
			ComplianceEditGridAction CEA=new ComplianceEditGridAction();
			AssetDashboardBean dashBean = null;
			String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpId+")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			StringBuilder empId = new StringBuilder();
			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					empId.append(shareDataCount.get(i).toString()+",");
					String mappedEmpId = CEA.getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
					if (mappedEmpId != null)
					{
						empId.append(mappedEmpId+",");
					}
				}
			}
			String mappedEmpId = CEA.getLevelHierarchy("ASTM", userEmpId);
			if (mappedEmpId != null)
			{
				empId.append(mappedEmpId+",");
			}
			empId.append(userEmpId);
			
			List pendingDetailsList = getPendingStatusByEmpId(empId.toString(),connectionSpace);
			if(pendingDetailsList!=null && pendingDetailsList.size()>0)
			{
				for (Iterator iterator = pendingDetailsList.iterator(); iterator.hasNext();)
				{
					dashBean = new AssetDashboardBean();
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						dashBean.setTicket_no(object[0].toString());
						dashBean.setOpen_date(DateUtil.convertDateToIndianFormat(object[1].toString())+" "+object[2].toString().substring(0, 5));
						if (object[3]!=null) 
						{
							dashBean.setOutletName(object[3].toString());
						}
						else 
						{
							dashBean.setOutletName("NA");
						}
						if (object[4]!=null) 
						{
							dashBean.setAssetName(object[4].toString());
						}
						else 
						{
							dashBean.setAssetName("NA");
						}
						if (object[5]!=null) 
						{
							dashBean.setFeedback_catg(object[5].toString());
						}
						else 
						{
							dashBean.setFeedback_catg("NA");
						}
						if (object[6]!=null) 
						{
							dashBean.setBrand(object[6].toString());
						}
						else 
						{
							dashBean.setBrand("NA");
						}
						if (object[7]!=null) 
						{
							dashBean.setEmpName(object[7].toString());
						}
						else 
						{
							dashBean.setEmpName("NA");
						}
						if (object[8]!=null) 
						{
							dashBean.setStatus(object[8].toString());
						}
						else 
						{
							dashBean.setStatus("NA");
						}
						if (object[9]!=null) 
						{
							String qry ="SELECT * FROM asset_reminder_details WHERE moduleName='Support' AND dueDate > '"+DateUtil.getCurrentDateUSFormat()+"' AND assetid='"+object[9].toString()+"' ";
							List amcList=cbt.executeAllSelectQuery(qry, connectionSpace);
							if (amcList!=null && amcList.size()>0) 
							{
								dashBean.setAmc("Yes");
							}
						}
						if (object[10]!=null) 
						{
							dashBean.setId(Integer.parseInt(object[10].toString()));
						}
						finalDataList.add(dashBean);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return finalDataList;
	}

	private List getPendingStatusByEmpId(String empId,SessionFactory connectionSpace) 
	{
		List data=null;
		try 
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder qry=new StringBuilder();
			qry.append(" SELECT ticket_no,open_date,open_time,abd.associateName,ad.assetname,cat.categoryName,acs.feed_brief,EMP1.empName,acs.status,acs.asset_id,acs.id AS compliantID ");
			qry.append(" FROM asset_complaint_status AS acs ");
			qry.append(" LEFT JOIN associate_basic_data AS abd ON acs.location=abd.id ");
			qry.append(" LEFT JOIN associate_contact_data AS ACD ON ACD.associateName=abd.id ");
			qry.append(" LEFT JOIN employee_basic AS EMP ON EMP.mobOne=ACD.contactNum ");
			qry.append(" LEFT JOIN asset_detail AS ad ON acs.asset_id=ad.id ");
			qry.append(" LEFT JOIN feedback_subcategory AS scat ON acs.subCatg=scat.id ");
			qry.append(" LEFT JOIN feedback_category AS cat ON scat.categoryName=cat.id ");
			qry.append(" LEFT JOIN employee_basic AS EMP1 ON acs.allot_to=EMP1.id ");
			qry.append(" WHERE EMP.id IN("+empId+") AND (acs.status='Pending' OR acs.status='Snooze' OR acs.status='High Priority') GROUP BY acs.ticket_no ");
			data=cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public List<AssetDashboardBean> getPendingPreventiveSupportData(String userEmpID, SessionFactory connectionSpace) 
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
		AssetDashboardBean dashBean = null;
		String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpID+")";
		List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
		StringBuilder empId = new StringBuilder();
		if (shareDataCount != null && shareDataCount.size() > 0)
		{
			for (int i = 0; i < shareDataCount.size(); i++)
			{
				empId.append(shareDataCount.get(i).toString()+",");
				String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
				if (mappedEmpId != null)
				{
					empId.append(mappedEmpId+",");
				}
			}
		}
		String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", userEmpID);
		if (mappedEmpId != null)
		{
			empId.append(mappedEmpId+",");
		}
		empId.append(userEmpID);
		ComplianceReminderHelper CRH=new ComplianceReminderHelper();
		List assetDetails = getOutletAssetDetailByEmpId(empId.toString(),connectionSpace);
		if(assetDetails!=null && assetDetails.size()>0)
		{
			for (Iterator iterator = assetDetails.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					dashBean = new AssetDashboardBean();
					dashBean.setOutletName(object[0].toString());
					if (object[4]!=null) 
					{
						dashBean.setAssetName(object[4].toString());
					}
					else 
					{
						dashBean.setAssetName("NA");
					}
					if (object[5]!=null) 
					{
						dashBean.setAssetserial(object[5].toString());
					}
					else 
					{
						dashBean.setAssetserial("NA");
					}
					if (object[6]!=null) 
					{
						dashBean.setBrand(object[6].toString());
					}
					else 
					{
						dashBean.setBrand("NA");
					}
					if (object[7]!=null) 
					{
						dashBean.setFrequency(CRH.getFrequencyName(object[7].toString()));
					}
					else 
					{
						dashBean.setFrequency("NA");
					}
					if (object[8]!=null && object[8].toString().equalsIgnoreCase("Support")) 
					{
						boolean flag=false;
						flag=DateUtil.comparetwoDates(object[1].toString(), DateUtil.getCurrentDateUSFormat());
						if (!flag) 
						{
							dashBean.setAmc("Yes");
						}
						if (object[7]!=null && object[7].toString().equalsIgnoreCase("D")) 
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("W"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("week", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("M"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("BM"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("Q"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("HY"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("Y"))
						{
							dashBean.setTotalSupport(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, object[1].toString())));
						}
						else
						{
							dashBean.setTotalSupport("NA");
						}
					}
					else
					{
						dashBean.setTotalSupport("NA");
					}
					if (object[8]!=null && object[8].toString().equalsIgnoreCase("Preventive")) 
					{
						if (object[7]!=null && object[7].toString().equalsIgnoreCase("D")) 
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("W"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("week", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("M"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("BM"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("Q"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("HY"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, object[1].toString())));
						}
						else if(object[7]!=null && object[7].toString().equalsIgnoreCase("Y"))
						{
							dashBean.setTotalPM(DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, object[1].toString())));
						}
						else
						{
							dashBean.setTotalPM("NA");
						}
					}
					else
					{
						dashBean.setTotalPM("NA");
					}
					if (object[9]!=null) 
					{
						String qry="SELECT ar.dueDate FROM asset_report AS ar INNER JOIN asset_reminder_details AS ard ON ar.assetReminderID=ard.id "
								+ "  WHERE ar.assetReminderID='"+object[9].toString()+"'  AND ard.moduleName='Preventive' AND (ar.actionTaken ='Done' OR ar.actionTaken ='Recurring') "
								+ " ORDER BY ar.id DESC LIMIT 0,1 ";
						List lastPm=cbt.executeAllSelectQuery(qry, connectionSpace);
						if (lastPm!=null && lastPm.size()>0) 
						{
							dashBean.setMissedPM(DateUtil.convertDateToIndianFormat(lastPm.get(0).toString()));
						}
						else
						{
							dashBean.setMissedPM("Not Available");
						}
						dashBean.setId(Integer.parseInt(object[9].toString()));
					}
					else
					{
						dashBean.setMissedPM("Not Available");
					}
				}
				finalDataList.add(dashBean);
			}
			
		}
		return finalDataList;
	}

	public List getOutletAssetDetailByEmpId(String empId,SessionFactory connectionSpace) 
	{
		List data=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder qry=new StringBuilder();
			qry.append(" SELECT outlet.associateName,ARD.dueDate,ARD.actionTaken,AD.id AS assetId,AD.assetname, ");
			qry.append(" AD.serialno,AD.assetbrand,  ARD.frequency,ARD.moduleName,ARD.id AS reminder ");
			qry.append(" FROM asset_reminder_details AS ARD ");
			qry.append(" LEFT JOIN asset_detail AS AD ON AD.id=ARD.assetid ");
			qry.append(" LEFT JOIN asset_allotment_detail AS AAD ON AAD.assetid=AD.id ");
			qry.append(" LEFT JOIN associate_basic_data AS outlet ON outlet.id=AAD.outletid ");
			qry.append(" INNER JOIN associate_contact_data AS ACD ON ACD.associateName=outlet.id ");
			qry.append(" INNER JOIN employee_basic AS EMP ON EMP.mobOne=ACD.contactNum ");
			qry.append(" WHERE ARD.actionTaken!='Done' AND ARD.actionTaken!='Recurring' AND EMP.id IN("+empId+") GROUP BY ARD.id ");
			System.out.println(" qry     "+qry.toString());
			data=cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	
}
