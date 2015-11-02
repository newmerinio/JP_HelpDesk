package com.Over2Cloud.ctrl.VAM.common;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.VAM.BeanUtil.SummaryReportPojo;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import java.io.PrintStream;
import java.util.*;
import org.hibernate.*;

public class SummaryReportHelperUniversal
{

    public SummaryReportHelperUniversal()
    {
    }

    
    public List getSummaryReportToData(SessionFactory connection)
	{
		List  summaryreportdatalist = new ArrayList();
		StringBuffer query= new StringBuffer();
		query.append("select srd.id, loc.name, gld.gate, dpt.deptName, srd.report_for, empb.empName, empb.mobOne, empb.emailIdOne, srd.report_at from summary_report_details as srd");
		query.append(" left join location as loc on srd.location = loc.id ");
		query.append(" left join department as dpt on srd.deptName = dpt.id ");
		query.append(" left join employee_basic as empb on srd.empName = empb.id ");
		query.append(" left join gate_location_details as gld on srd.gate = gld.id ");
		System.out.println("QQQQ :::: "+query.toString());
		Session session = null;  
	   Transaction transaction = null;  
		try 
		  {  
			 session=connection.openSession(); 
			
			 transaction = session.beginTransaction(); 
			 summaryreportdatalist=session.createSQLQuery(query.toString()).list();
			 System.out.println("size of list"+summaryreportdatalist.size());
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
	    	ex.printStackTrace();
			 transaction.rollback();
		  } 
		finally{
			try {
				session.flush();
				session.close();
			} catch (Exception e) {
				
			}
		}
		return summaryreportdatalist;
	}


    public List getVisitorDataList(SessionFactory connection, String todaydate, String deptname, String location)
    {
        List visitordatalist = new ArrayList();
        List report = new ArrayList();
        StringBuilder query = new StringBuilder("");
        query.append("select vid.sr_number, vid.visitor_name,vid.visitor_mobile, vid.visitor_company,v" +
"id.address,pa.purpose, vid.visited_person,vid.visited_mobile,dept.deptName, vid." +
"time_in,vid.time_out,loc.name, vid.status from visitor_entry_details as vid"
);
        query.append(" left join department as dept on vid.deptName = dept.id");
        query.append(" left join purpose_admin as pa on vid.purpose = pa.id");
        query.append(" left join location as loc on vid.location = loc.id");
        if(!CommonMethod.getDeptForId(connection, deptname).equals("Admin"))
        {
            query.append((new StringBuilder(" where visit_date = '")).append(todaydate).append("'").toString());
            query.append((new StringBuilder(" and vid.deptName = '")).append(deptname).append("'").toString());
            query.append((new StringBuilder(" and vid.location = '")).append(location).append("'").toString());
            query.append(" order by vid.sr_number");
            System.out.println((new StringBuilder("for Dept>>")).append(query.toString()).toString());
        } else
        {
            query.append((new StringBuilder(" where visit_date = '")).append(todaydate).append("'").toString());
            query.append(" order by vid.sr_number");
            System.out.println((new StringBuilder("for all>>>")).append(query.toString()).toString());
        }
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = connection.openSession();
            transaction = session.beginTransaction();
            visitordatalist = session.createSQLQuery(query.toString()).list();
            transaction.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            transaction.rollback();
        }
        if(visitordatalist != null && visitordatalist.size() > 0)
        {
            report = (new SummaryReportHelperUniversal()).setvisitorDataforReport(visitordatalist);
        }
        return report;
    }

    public List setvisitorDataforReport(List visitordatalist)
    {
        String timein = null;
        String timeout = null;
        String totalstay = null;
        List srpList = new ArrayList();
        SummaryReportPojo srp = null;
        if(visitordatalist != null && visitordatalist.size() > 0)
        {
            for(Iterator iterator = visitordatalist.iterator(); iterator.hasNext(); srpList.add(srp))
            {
                Object object[] = (Object[])iterator.next();
                srp = new SummaryReportPojo();
                if(object[0] != null)
                {
                    srp.setSr_number(object[0].toString());
                } else
                {
                    srp.setSr_number("NA");
                }
                if(object[1] != null)
                {
                    srp.setVisitor_name(object[1].toString());
                } else
                {
                    srp.setVisitor_name("NA");
                }
                if(object[2] != null)
                {
                    srp.setVisitor_mobile(object[2].toString());
                } else
                {
                    srp.setVisitor_mobile("NA");
                }
                if(object[3] != null)
                {
                    srp.setVisitor_company(object[3].toString());
                } else
                {
                    srp.setVisitor_company("NA");
                }
                if(object[4] != null)
                {
                    srp.setAddress(object[4].toString());
                } else
                {
                    srp.setAddress("NA");
                }
                if(object[5] != null)
                {
                    srp.setPurpose(object[5].toString());
                } else
                {
                    srp.setPurpose("NA");
                }
                if(object[6] != null)
                {
                    srp.setVisited_person(object[6].toString());
                } else
                {
                    srp.setVisited_person("NA");
                }
                if(object[7] != null)
                {
                    srp.setVisited_mobile(object[7].toString());
                } else
                {
                    srp.setVisited_mobile("NA");
                }
                if(object[8] != null)
                {
                    srp.setDeptName(object[8].toString());
                } else
                {
                    srp.setDeptName("NA");
                }
                if(object[9] != null)
                {
                    srp.setTime_in(object[9].toString().substring(0, 5));
                    timein = object[9].toString();
                } else
                {
                    srp.setTime_in("NA");
                }
                if(object[10] != null)
                {
                    srp.setTime_out(object[10].toString().substring(0, 5));
                    timeout = object[10].toString();
                } else
                {
                    srp.setTime_out("NA");
                }
                if(object[11] != null)
                {
                    srp.setLocation(object[11].toString());
                } else
                {
                    srp.setLocation("NA");
                }
                if(object[12] != null)
                {
                    srp.setStatus(object[12].toString());
                } else
                {
                    srp.setStatus("NA");
                }
                String totaltime = CommonMethod.subtractTime(timein, timeout);
                if(totaltime != null)
                {
                    srp.setTotaltimestay(totaltime);
                } else
                {
                    srp.setTotaltimestay("NA");
                }
            }

        }
        return srpList;
    }

    public List getVehicleDataList(SessionFactory connection, String todaydate)
    {
        List vehicledatalist = new ArrayList();
        List report = new ArrayList();
        StringBuilder query = new StringBuilder("");
        query.append("select vhd.sr_number, vhd.driver_name,vhd.driver_mobile, vhd.vehicle_number,vhd." +
"entry_time,vhd.out_time, vhd.material_details,vhd.quantity,dept.deptName,loc.nam" +
"e, vhd.status from vehicle_entry_details as vhd"
);
        query.append(" left join department as dept on vhd.deptName = dept.id");
        query.append(" left join location as loc on vhd.location = loc.id");
        query.append((new StringBuilder(" where vhd.entry_date  = '")).append(todaydate).append("'").toString());
        query.append(" order by vhd.sr_number");
        System.out.println((new StringBuilder("queryvehcle>>")).append(query.toString()).toString());
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = connection.openSession();
            transaction = session.beginTransaction();
            vehicledatalist = session.createSQLQuery(query.toString()).list();
            transaction.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            transaction.rollback();
        }
        if(vehicledatalist != null && vehicledatalist.size() > 0)
        {
            report = (new SummaryReportHelperUniversal()).setvehicleDataforReport(vehicledatalist);
        }
        return report;
    }

    public List setvehicleDataforReport(List vehicledatalist)
    {
        List srpVehList = new ArrayList();
        SummaryReportPojo srpveh = null;
        if(vehicledatalist != null && vehicledatalist.size() > 0)
        {
            for(Iterator iterator = vehicledatalist.iterator(); iterator.hasNext(); srpVehList.add(srpveh))
            {
                Object object[] = (Object[])iterator.next();
                srpveh = new SummaryReportPojo();
                if(object[0] != null)
                {
                    srpveh.setSr_number(object[0].toString());
                } else
                {
                    srpveh.setSr_number("NA");
                }
                if(object[1] != null)
                {
                    srpveh.setDriver_name(object[1].toString());
                } else
                {
                    srpveh.setDriver_name("NA");
                }
                if(object[2] != null)
                {
                    srpveh.setDriver_mobile(object[2].toString());
                } else
                {
                    srpveh.setDriver_mobile("NA");
                }
                if(object[3] != null)
                {
                    srpveh.setVehicle_number(object[3].toString());
                } else
                {
                    srpveh.setVehicle_number("NA");
                }
                if(object[4] != null)
                {
                    srpveh.setEntry_time(object[4].toString());
                } else
                {
                    srpveh.setEntry_time("NA");
                }
                if(object[5] != null)
                {
                    srpveh.setOut_time(object[5].toString());
                } else
                {
                    srpveh.setOut_time("NA");
                }
                if(object[6] != null)
                {
                    srpveh.setMaterial_details(object[6].toString());
                } else
                {
                    srpveh.setMaterial_details("NA");
                }
                if(object[7] != null)
                {
                    srpveh.setQuantity(object[7].toString());
                } else
                {
                    srpveh.setQuantity("NA");
                }
                if(object[8] != null)
                {
                    srpveh.setDeptName(object[8].toString());
                } else
                {
                    srpveh.setDeptName("NA");
                }
                if(object[9] != null)
                {
                    srpveh.setLocation(object[9].toString());
                } else
                {
                    srpveh.setLocation("NA");
                }
                if(object[10] != null)
                {
                    srpveh.setStatus(object[10].toString());
                } else
                {
                    srpveh.setStatus("NA");
                }
            }

        }
        return srpVehList;
    }

    public List getPostAckDataList(SessionFactory connection, String todaydate)
    {
        List ackpostdatalist = new ArrayList();
        List report = new ArrayList();
        StringBuilder query = new StringBuilder("");
        query.append("select apd.id, vad.vender_type,apd.vender_name, apd.act_mangr,apd.mob_number,dpt" +
".deptName,empb.empName,apd.location,apd.gate from acknowledge_post_details as ap" +
"d"
);
        query.append(" left join department as dpt on apd.dept = dpt.id");
        query.append(" left join employee_basic as empb on apd.empName = empb.id");
        query.append(" left join vendor_admin_details vad on apd.vender_type = vad.id");
        query.append(" left join location as loc on apd.location = loc.id");
        query.append((new StringBuilder(" where apd.date  = '")).append(todaydate).append("'").toString());
        query.append(" order by apd.id");
        System.out.println((new StringBuilder("queryackpostrep>>")).append(query.toString()).toString());
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = connection.openSession();
            transaction = session.beginTransaction();
            ackpostdatalist = session.createSQLQuery(query.toString()).list();
            transaction.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            transaction.rollback();
        }
        if(ackpostdatalist != null && ackpostdatalist.size() > 0)
        {
            report = (new SummaryReportHelperUniversal()).setAcknoledgeDataforReport(ackpostdatalist);
        }
        return report;
    }

    public List setAcknoledgeDataforReport(List ackpostdatalist)
    {
        List srpackpostList = new ArrayList();
        SummaryReportPojo srpackpost = null;
        if(ackpostdatalist != null && ackpostdatalist.size() > 0)
        {
            for(Iterator iterator = ackpostdatalist.iterator(); iterator.hasNext(); srpackpostList.add(srpackpost))
            {
                Object object[] = (Object[])iterator.next();
                srpackpost = new SummaryReportPojo();
                if(object[0] != null)
                {
                    srpackpost.setId(Integer.parseInt(object[0].toString()));
                } else
                {
                    srpackpost.setId(Integer.parseInt("0"));
                }
                if(object[1] != null)
                {
                    srpackpost.setVender_type(object[1].toString());
                } else
                {
                    srpackpost.setVender_type("NA");
                }
                if(object[2] != null)
                {
                    srpackpost.setVender_name(object[2].toString());
                } else
                {
                    srpackpost.setVender_name("NA");
                }
                if(object[3] != null)
                {
                    srpackpost.setAct_mangr(object[3].toString());
                } else
                {
                    srpackpost.setAct_mangr("NA");
                }
                if(object[4] != null)
                {
                    srpackpost.setMob_number(object[4].toString());
                } else
                {
                    srpackpost.setMob_number("NA");
                }
                if(object[5] != null)
                {
                    srpackpost.setDept(object[5].toString());
                } else
                {
                    srpackpost.setDept("NA");
                }
                if(object[6] != null)
                {
                    srpackpost.setEmpName(object[6].toString());
                } else
                {
                    srpackpost.setEmpName("NA");
                }
                if(object[7] != null)
                {
                    srpackpost.setLocation(object[7].toString());
                } else
                {
                    srpackpost.setLocation("NA");
                }
                if(object[8] != null)
                {
                    srpackpost.setGate(object[8].toString());
                } else
                {
                    srpackpost.setGate("NA");
                }
            }

        }
        return srpackpostList;
    }

    public String getConfigMailForSummaryReport(SessionFactory connection, String empname, List visitordatalist, String empmob, String location)
    {
        long j = 0L;
        long l = 0L;
        int visitorin = 0;
        int visitorout = 0;
        String summarysms = null;
        List orgData = (new LoginImp()).getUserInfomation("8", "IN");
        String orgName = "";
        if(orgData != null && orgData.size() > 0)
        {
            for(Iterator iterator = orgData.iterator(); iterator.hasNext();)
            {
                Object object[] = (Object[])iterator.next();
                orgName = object[0].toString();
            }

        }
        for(Iterator iterator1 = visitordatalist.iterator(); iterator1.hasNext();)
        {
            SummaryReportPojo SRP = (SummaryReportPojo)iterator1.next();
            if(!SRP.getStatus().equals("0"))
            {
                j++;
            } else
            {
                l++;
            }
        }

        StringBuilder mailtext = new StringBuilder("");
        mailtext.append((new StringBuilder("<b>Dear ")).append(DateUtil.makeTitle(empname)).append(",</b>").toString());
        mailtext.append("<br><br><b>Hello!!!</b><br>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br>Your kind atten" +
"tion is required for following Visitor movement summary mapped for your analysis" +
". We request, you to access complete information including & take relevant actio" +
"ns (if any) by login with your respective credentials.</FONT><br>"
);
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append((new StringBuilder("<tr bgcolor='#8db7f9'><td align='center' colspan='3' width='10%' style=' color:#" +
"111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> " +
"Current Visitor Count Status for  "
)).append(location).append(" as On ").append(DateUtil.getCurrentDateIndianFormat()).append(" At ").append(DateUtil.getCurrentTimeHourMin()).append(" For All Department </b></td></td></tr>").toString());
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Visit" +
"or for Today</b></td></td><td align='center' width='10%' style=' color:#111111; " +
"font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Vis" +
"itor Out</b></td><td align='center' width='10%' style=' color:#111111; font-size" +
":11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Visitor Stil" +
"l In</b></td></tr>"
);
        mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(visitordatalist.size()).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(j).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(l).append("</td></tr></tbody></table>").toString());
    //table start    
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append((new StringBuilder("<tr bgcolor='#8db7f9'><td align='center' colspan='9' width='10%' style=' color:#" +
"111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> " +
"Visitor Summary Report for "
)).append(location).append(" as On ").append(DateUtil.getCurrentDateIndianFormat()).append(" At ").append(DateUtil.getCurrentTimeHourMin()).append(" For All Department </b></td></td></tr>").toString());
    //column table row start   
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visitor" +
"&nbsp;ID</strong></td><td align='center' width='12%' style=' color:#111111; font" +
"-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visitor&" +
"nbsp;Name</strong></td><td align='center' width='12%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Purpose" +
"</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11" +
"px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visited&nbsp;Per" +
"son</strong></td> <td align='center' width='10%' style=' color:#111111; font-siz" +
"e:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visited&nbsp" +
";Mobile</strong></td> <td align='center' width='12%' style=' color:#111111; font" +
"-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Departme" +
"nt</strong></td><td align='center' width='12%' style=' color:#111111; font-size:" +
"11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;IN</" +
"strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px" +
"; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;Out</str" +
"ong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; f" +
"ont-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Stay</stro" +
"ng></td></tr>"
);
        if(visitordatalist != null && visitordatalist.size() > 0)
        {
            int i = 0;
            for(Iterator iterator2 = visitordatalist.iterator(); iterator2.hasNext();)
            {
                SummaryReportPojo SRP = (SummaryReportPojo)iterator2.next();
                int k = ++i;
                if(k % 2 != 0)
                {
                    if(!SRP.getStatus().equals("0"))
                    {
                        mailtext.append((new StringBuilder("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisitor_name())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getPurpose())).append("</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font" +
"-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisited_person())).append("</td>  <td align='center' width='10%' style=' color:#111111; font-size:11px; fon" +
"t-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVisited_mobile()).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getDeptName())).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_in()).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_out()).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTotaltimestay()).append("</td></tr>").toString());
                        visitorout++;
                    } else
                    {
                        mailtext.append((new StringBuilder("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisitor_name())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getPurpose())).append("</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font" +
"-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisited_person())).append("</td>  <td align='center' width='10%' style=' color:#111111; font-size:11px; fon" +
"t-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVisited_mobile()).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getDeptName())).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_in()).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_out()).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTotaltimestay()).append("</td></tr>").toString());
                        visitorin++;
                    }
                } else
                if(!SRP.getStatus().equals("0"))
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisitor_name())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getPurpose())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisited_person())).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVisited_mobile()).append("</td><td align='center' width='12%' bgcolor='#53c156' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getDeptName())).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_in()).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_out()).append("</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTotaltimestay()).append("</td></tr>").toString());
                    visitorout++;
                } else
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisitor_name())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getPurpose())).append("</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getVisited_person())).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVisited_mobile()).append("</td><td align='center' width='12%' bgcolor='#C24641' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(converCapFirstChar(SRP.getDeptName())).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_in()).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTime_out()).append("</td><td align='center' width='12%'  bgcolor='#C24641'  style=' color:#111111; f" +
"ont-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getTotaltimestay()).append("</td></tr>").toString());
                    visitorin++;
                }
            }

        }
        mailtext.append("</tbody></table>");
 //table end here with column data       
        
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br><b>Thanks &" +
" Regards,</b></FONT>"
);
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><b>Admin Team.<br><" +
"/b></FONT>"
);
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='1'><b>----------------" +
"--------------------------------------------------------------------------------" +
"--------------------------------------------------------------------------------" +
"--</b><br></FONT>"
);
        mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your ema" +
"il id has been mapped by admin user of the automated software \n provided by Dre" +
"amSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the" +
" system as required \n by the client. \nIn case, if you find the data mentioned " +
"in the mail is incorrect or if you prefer not to receive the further emails then" +
" <BR>please do not reply to this mail instead contact to your administrator or f" +
"or any support related to the software service \n provided, visit contact detail" +
"s over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your fee" +
"dback to <br>support@dreamsol.biz</FONT>"
);
        return mailtext.toString();
    }

    public String getConfigVehicleSumarryRep(String empname, List vehicledatalist, String location)
    {
        long j = 0L;
        long l = 0L;
        long m = 0L;
        long n = 0L;
        List orgData = (new LoginImp()).getUserInfomation("8", "IN");
        String orgName = "";
        if(orgData != null && orgData.size() > 0)
        {
            for(Iterator iterator = orgData.iterator(); iterator.hasNext();)
            {
                Object object[] = (Object[])iterator.next();
                orgName = object[0].toString();
            }

        }
        for(Iterator iterator1 = vehicledatalist.iterator(); iterator1.hasNext();)
        {
            SummaryReportPojo SRP = (SummaryReportPojo)iterator1.next();
            if(!SRP.getStatus().equals("0"))
            {
                j++;
            } else
            {
                l++;
            }
        }

        StringBuilder mailtext = new StringBuilder("");
        mailtext.append((new StringBuilder("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cell" +
"spacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px;" +
" font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
)).append(orgName).append("</b></td></tr></tbody></table>").toString());
        mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='cente" +
"r' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica," +
" sans-serif;'><b>Vehicle Summary Report</b></td></tr></tbody></table>"
);
        mailtext.append((new StringBuilder("<b>Dear ")).append(DateUtil.makeTitle(empname)).append(",</b>").toString());
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append((new StringBuilder("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cell" +
"spacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px;" +
" font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Daily Vehicle Summary Re" +
"port For "
)).append(location).append(" as On ").append(DateUtil.getCurrentDateIndianFormat()).append(" At ").append(DateUtil.getCurrentTimeHourMin()).append("</b></td></tr></tbody></table>").toString());
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Vehic" +
"le Enter</b></td></td><td align='center' width='10%' style=' color:#111111; font" +
"-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Vehicle Out</" +
"b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; fon" +
"t-family:Verdana, Arial, Helvetica, sans-serif;'><b>Vehicle Still In Campus</b><" +
"/td></tr>"
);
        mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(vehicledatalist.size()).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(j).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(l).append("</td></tr></tbody></table>").toString());
 //table column start here       
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sr.&nbs" +
"p;No.</strong></td><td align='center' width='12%' style=' color:#111111; font-si" +
"ze:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Driver&nbsp" +
";Name</strong></td><td align='center' width='12%' style=' color:#111111; font-si" +
"ze:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Driver&nbsp" +
";Mobile</strong></td> <td align='center' width='9%' style=' color:#111111; font-" +
"size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Vehicle&n" +
"bsp;Number</strong></td><td align='center' width='9%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Entry T" +
"ime</strong></td> <td align='center' width='12%' style=' color:#111111; font-siz" +
"e:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Out Time</st" +
"rong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; " +
"font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Material &nbsp;Detai" +
"l</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:" +
"11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Quantity</stro" +
"ng></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; f" +
"ont-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department Name</stro" +
"ng></td><td align='center' width='12%' style=' color:#111111; font-size:11px; fo" +
"nt-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td>" +
"</tr>"
);     //comment by Azad 14oct
       /* mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);*/
        if(vehicledatalist != null && vehicledatalist.size() > 0)
        {
            int i = 0;
            for(Iterator iterator2 = vehicledatalist.iterator(); iterator2.hasNext();)
            {
                SummaryReportPojo SRP = (SummaryReportPojo)iterator2.next();
                int k = ++i;
                if(k % 2 != 0)
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDriver_name()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDriver_mobile()).append("</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVehicle_number()).append("</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getEntry_time()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getOut_time()).append("</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getMaterial_details()).append("</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getQuantity()).append("</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDeptName()).append("</td><td align='left' width='12%' style='  color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getLocation()).append("</td></tr>").toString());
                } else
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getSr_number()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDriver_name()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDriver_mobile()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVehicle_number()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getEntry_time()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getOut_time()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getMaterial_details()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getQuantity()).append("</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDeptName()).append("</td><td align='left' width='12%'  style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getLocation()).append("</td></tr>").toString());
                }
            }

        }
        mailtext.append("</tbody></table>");
        
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<b" +
"r><br></FONT>"
);
        mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Ema" +
"il ID has been mapped by Admin User of the Automated Software \n provided by Dre" +
"amSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the" +
" system as required \n by the client. \nIn case, if you find the data mentioned " +
"in the mail is incorrect or if you prefer not to receive the further E-mails the" +
"n <BR>please do not reply to this mail instead contact to your administrator or " +
"for any support related to the software service \n provided, visit contact detai" +
"ls over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your fe" +
"edback to <br>support@dreamsol.biz</FONT>"
);
        System.out.println((new StringBuilder("mailtext>>Vehicle>")).append(mailtext.toString()).toString());
        return mailtext.toString();
    }

    public String getConfigPostSumarryRep(String empname, List postacknowledgelist, String location)
    {
        long j = 0L;
        long l = 0L;
        long m = 0L;
        long n = 0L;
        List orgData = (new LoginImp()).getUserInfomation("8", "IN");
        String orgName = "";
        if(orgData != null && orgData.size() > 0)
        {
            for(Iterator iterator = orgData.iterator(); iterator.hasNext();)
            {
                Object object[] = (Object[])iterator.next();
                orgName = object[0].toString();
            }

        }
        StringBuilder mailtext = new StringBuilder("");
        mailtext.append((new StringBuilder("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cell" +
"spacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px;" +
" font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
)).append(orgName).append("</b></td></tr></tbody></table>").toString());
        mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='cente" +
"r' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica," +
" sans-serif;'><b>Post Acknowledge Summary Report</b></td></tr></tbody></table>"
);
        mailtext.append((new StringBuilder("<b>Dear ")).append(DateUtil.makeTitle(empname)).append(",</b>").toString());
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append((new StringBuilder("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cell" +
"spacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px;" +
" font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Post Acknowledge Summary" +
" Report For "
)).append(location).append(" as On ").append(DateUtil.getCurrentDateIndianFormat()).append(" At ").append(DateUtil.getCurrentTimeHourMin()).append("</b></td></tr></tbody></table>").toString());
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Post<" +
"/b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11p" +
"x; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Post1</b></td><td alig" +
"n='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdan" +
"a, Arial, Helvetica, sans-serif;'><b>Post Not Recieved</b></td></tr>"
);
        mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(postacknowledgelist.size()).append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append("Post1").append("</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append("Un-Recieved Post").append("</td></tr></tbody></table>").toString());
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Id</str" +
"ong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; f" +
"ont-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Vendor&nbsp;Type</str" +
"ong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; f" +
"ont-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Vendor&nbsp;Name</str" +
"ong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; f" +
"ont-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Account&nbsp;Manager<" +
"/strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px" +
"; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile Number</str" +
"ong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; " +
"font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong><" +
"/td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'><strong>Employee &nbsp;Name</stron" +
"g></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; fo" +
"nt-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td>" +
" <td align='center' width='12%' style=' color:#111111; font-size:11px; font-fami" +
"ly:Verdana, Arial, Helvetica, sans-serif;'><strong>Gate</strong></td></tr>"
);
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='bor" +
"der-collapse: collapse' width='100%' align='center'><tbody>"
);
        if(postacknowledgelist != null && postacknowledgelist.size() > 0)
        {
            int i = 0;
            for(Iterator iterator1 = postacknowledgelist.iterator(); iterator1.hasNext();)
            {
                SummaryReportPojo SRP = (SummaryReportPojo)iterator1.next();
                int k = ++i;
                if(k % 2 != 0)
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getId()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVender_type()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVender_name()).append("</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getAct_mangr()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getMob_number()).append("</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDept()).append("</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getEmpName()).append("</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-" +
"family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getLocation()).append("</td><td align='left' width='12%' bgcolor='##b2b2f7' style=' color:#111111; font" +
"-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getGate()).append("</td></tr>").toString());
                } else
                {
                    mailtext.append((new StringBuilder("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; fon" +
"t-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
)).append(SRP.getId()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVender_type()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getVender_name()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getAct_mangr()).append("</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-f" +
"amily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getMob_number()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getDept()).append("</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getEmpName()).append("</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-fa" +
"mily:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getLocation()).append("</td><td align='left' width='12%' bgcolor='##b2b2f7' style=' color:#111111; font" +
"-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
).append(SRP.getGate()).append("</td></tr>").toString());
                }
            }

        }
        mailtext.append("</tbody></table>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<b" +
"r><br></FONT>"
);
        mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Ema" +
"il ID has been mapped by Admin User of the Automated Software \n provided by Dre" +
"amSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the" +
" system as required \n by the client. \nIn case, if you find the data mentioned " +
"in the mail is incorrect or if you prefer not to receive the further E-mails the" +
"n <BR>please do not reply to this mail instead contact to your administrator or " +
"for any support related to the software service \n provided, visit contact detai" +
"ls over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your fe" +
"edback to <br>support@dreamsol.biz</FONT>"
);
        System.out.println((new StringBuilder("mailtext>>PostAck>")).append(mailtext.toString()).toString());
        return mailtext.toString();
    }

    private String converCapFirstChar(String oldChar)
    {
        char stringArray[] = oldChar.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        String myString = new String(stringArray);
        return myString;
    }
}