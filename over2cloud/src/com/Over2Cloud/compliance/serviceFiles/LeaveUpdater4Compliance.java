package com.Over2Cloud.compliance.serviceFiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class LeaveUpdater4Compliance
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();

	@SuppressWarnings({ "unused", "unused" })
	public void getLeaveUpdater(SessionFactory connection)
	{
		CommonOperInterface cot = new CommonConFactory().createInterface();
		try
		{

			boolean tableCreated1 = createLeaveUpdater4compliance(connection);
			if (tableCreated1)
			{
				// collect data if there is leave request for today
				boolean chkDate;
				String query1 = "select fdate, tdate, mode,  approveBy, leaveunit, date1 from leave_request where  fdate BETWEEN '" + DateUtil.getNextDateAfter(-30) + "' AND '" + DateUtil.getCurrentDateUSFormat() + "' ";
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connection);
				if (dataList1 != null && dataList1.size() > 0)
				{
					// Object[] object = null;
					for (Iterator iterator1 = dataList1.iterator(); iterator1.hasNext();)
					{
						Object[] object1 = (Object[]) iterator1.next();
						if (object1[1] != null && object1[0] != null)
						{
							String chkduplicateEntry = "select * from compliance_owner_leave_status where date = '" + DateUtil.getCurrentDateUSFormat() + "' and contactid='" + object1[4].toString() + "' ";
							List chkList1 = cbt.executeAllSelectQuery(chkduplicateEntry.toString(), connection);
							if (chkList1.size() == 0)
							{

								// check for time more or less than workint
								// time
								int i = object1[0].toString().compareTo(DateUtil.getCurrentDateUSFormat());
								if (i != 0)
								{
									int j = object1[1].toString().compareTo(DateUtil.getCurrentDateUSFormat());
									if (j != 0)
									{
										chkDate = DateUtil.checkDateBetween(object1[0].toString(), DateUtil.getCurrentDateUSFormat(), object1[1].toString());

									} else
									{
										chkDate = true;
									}
								} else
								{
									chkDate = true;
								}
								if (chkDate == true)
								{
									// to get email id
									String emailGet = "select emp.emailIdOne, emp.id from employee_basic as emp inner join compliance_contacts as cc on cc.emp_id=emp.id where cc.id='" + object1[4].toString() + "'";
									List dataListemail = cbt.executeAllSelectQuery(emailGet.toString(), connection);
									if (dataListemail != null && dataListemail.size() > 0)
									{
										for (Iterator iteratorEmail = dataListemail.iterator(); iteratorEmail.hasNext();)
										{
											Object[] objectE = (Object[]) iteratorEmail.next();
											if (objectE[0] != null)
											{
												String emailid = objectE[0].toString();
												// for those who has not COMPL
												// as module name in
												// working_timing
												String ftime = "00:01";
												String ttime = "23:59";

												String workingHr = "select fromTime, toTime from working_timing where moduleName='COMPL' group by moduleName";
												List dataListworkinghr = cbt.executeAllSelectQuery(workingHr.toString(), connection);
												if (dataListworkinghr != null && dataListworkinghr.size() > 0)
												{
													for (Iterator iteratorworkinghr = dataListworkinghr.iterator(); iteratorworkinghr.hasNext();)
													{
														Object[] objectWH = (Object[]) iteratorworkinghr.next();
														ftime = objectWH[0].toString();
														ttime = objectWH[1].toString();
													}
												}
												// entry for data in table
												if (object1[4] != null)
												{
													List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
													InsertDataTable ob = null;
													ob = new InsertDataTable();
													ob.setColName("contactid");
													ob.setDataName(object1[4].toString());
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("emailid");
													ob.setDataName(emailid);
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("date");
													ob.setDataName(DateUtil.getCurrentDateUSFormat());
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("time");
													boolean TimeLchk = DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), ftime);
													boolean TimeRchk = DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), ttime);
													int chk4NoWrkTM = ftime.compareToIgnoreCase("00:01");
													if (TimeLchk == false && TimeRchk == true)
													{
														ob.setDataName(DateUtil.getCurrentTimeHourMin());
													}
													else if (chk4NoWrkTM == 0)
													{
														ob.setDataName(ftime);
													} 
													else
													{
														ob.setDataName(ftime);
													}
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("moduleName");
													ob.setDataName("COMPL");
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("working_hrs_from");
													ob.setDataName(ftime);
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("working_hrs_to");
													ob.setDataName(ttime);
													insertData.add(ob);

													ob = new InsertDataTable();
													ob.setColName("status");
													ob.setDataName("unsend");
													insertData.add(ob);
													if (TimeRchk == true)
													{
														boolean status = cot.insertIntoTable("compliance_owner_leave_status", insertData, connection);
													}
												}
											}
										}
									}
								}
							}
							else
							{
							}
						}
					}
				}

			}

		} catch (Exception E)
		{
			E.printStackTrace();
		}
	}

	// for create compliance_owner_leave_status Table
	public boolean createLeaveUpdater4compliance(SessionFactory connection)
	{
		boolean tableCreated = false;

		TableColumes ob1 = null;
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

		ob1 = new TableColumes();
		ob1.setColumnname("contactid");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("emailid");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("date");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("time");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("moduleName");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("working_hrs_from");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("working_hrs_to");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("status");
		ob1.setDatatype("varchar(255)");
		Tablecolumesaaa.add(ob1);

		if (Tablecolumesaaa != null && Tablecolumesaaa.size() > 0)
		{
			tableCreated = cbt.createTable22("compliance_owner_leave_status", Tablecolumesaaa, connection);
		}
		return tableCreated;
	}

}