package com.Over2Cloud.ctrl.wfpm.industry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class IndustryHelper {
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");

	public boolean addIndustry(SessionFactory connectionSpace,
			HttpServletRequest request, String userName, String accountID) {
		boolean flag = false;

		try {

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration
					.getConfigurationData("mapped_industry_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"industry_configuration");
			if (org2 != null) {
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2) {
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName"))
						userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date"))
						dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time"))
						timeTrue = true;
				}
				cbt.createTable22("industrydatalevel1", Tablecolumesaaa,
						connectionSpace);
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null) {
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}
				if (userTrue) {
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
				}
				if (dateTrue) {
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
				}
				if (timeTrue) {
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}
				if (insertData.size() > 3) // when their is atleast one data
					flag = cbt.insertIntoTable("industrydatalevel1",
							insertData, connectionSpace);
				System.out.println(" flag   add industrydatalevel1   "+flag);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public boolean addSubIndustry() {
		boolean flag = false;

		return flag;
	}

	public Map<String, String> fetchAllDept(SessionFactory factory) {
		Map<String, String> deptMap = new LinkedHashMap<String, String>();
		try {
			String query = "select id, deptName from department where flag='Active' order by deptName ";
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List list = coi.executeAllSelectQuery(query, factory);
			if (list != null && list.size() > 0) {
				for (Object obj : list) {
					Object[] data = (Object[]) obj;
					deptMap.put(data[0].toString(), (data[1] == null
							|| data[1].equals("") ? "NA" : data[1].toString().trim()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptMap;
	}

	public boolean isRecordExists(CommonOperInterface coi, String offeringId,
			String offeringLevelId, String subIndustry, String deptName) {

		boolean flag = false;
		try {
			StringBuilder query = new StringBuilder();
			query
					.append("select * from off_indust_subindust_dept_mapping  where offeringId = '");
			query.append(offeringId);
			query.append("' and offeringLevelId = '");
			query.append(offeringLevelId);
			query.append("' and targetSegment = '");
			query.append(subIndustry);
			query.append("' and deptName = '");
			query.append(deptName);
			query.append("'");
			//System.out.println("query :"+query);

			List list = coi.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (list != null && list.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
