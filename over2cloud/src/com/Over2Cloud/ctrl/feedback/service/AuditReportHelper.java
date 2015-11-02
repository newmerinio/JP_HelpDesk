package com.Over2Cloud.ctrl.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;

public class AuditReportHelper {

	@SuppressWarnings("rawtypes")
	public void auditReportForFeedback(SessionFactory connection) 
	{
		try {

			StringBuilder query = new StringBuilder();
			query.append("SELECT id FROM feedbackdata WHERE mode='Paper' AND openDate='"+DateUtil.getCurrentDateUSFormat()+"'");
			List list = new createTable().executeAllSelectQuery(query.toString(), connection);
			query.delete(0, query.length());
			Random rand = new Random();
			//int length = list.size() > 5 ? 5 : list.size();
			
			//To get 5% of total feedback filled on particular day.
			int length = (list.size() * 5)/100;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			//To get different random no.  
			for (int i = 0; i < length;)
			{
				int no = rand.nextInt(list.size());
				if (query.indexOf(no + ", ") == -1) {
					query.append(no + ", ");
					i++;
				}
			}
			if (query.length() > 0)
			{
				query = query.delete(query.length() - 3, query.length() - 1);
				System.out.println(query+"::::"+length);
				String len[] = query.toString().split(", ");
				for (int i = 0; i < len.length; i++)
				{
					Object object = (Object) list.get(Integer.parseInt(len[i].trim()));
					if (object != null)
					{
						ob = new InsertDataTable();
						ob.setColName("feedDataId");
						ob.setDataName(object);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("insert_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("insert_time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						new createTable().insertIntoTable("feedback_audit_report",insertData, connection);
						insertData.clear();
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
