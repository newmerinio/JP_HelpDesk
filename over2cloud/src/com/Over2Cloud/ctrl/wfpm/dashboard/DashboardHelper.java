package com.Over2Cloud.ctrl.wfpm.dashboard;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import java.io.PrintStream;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;

// Referenced classes of package com.Over2Cloud.ctrl.wfpm.dashboard:
//            ActivityType

public class DashboardHelper extends SessionProviderClass
{

    public DashboardHelper()
    {
    }

    public List fetchCommonDashboardActivitiesForClient(SessionFactory factory, String month, String userName, String cIdUserType)
    {
        List list = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            query.append("select cta.id, ccd.personName, cbd.clientName, com.isConverted, off.subofferingn" +
"ame, cs.statusName, SUBSTRING_INDEX(cta.maxDateTime,' ',-1) 'TIME', empb.empName" +
",cta.compelling_reason, cbd.id 'cbdId' "
);
            query.append("from client_takeaction as cta,compliance_contacts as cct,employee_basic as empb," +
" client_contact_data as ccd, "
);
            query.append("offeringlevel3 as off, client_status as cs, client_basic_data as cbd, client_off" +
"ering_mapping as com "
);
            query.append("where ");
            query.append("com.clientName = cbd.id ");
            query.append("and ccd.clientName = cbd.id ");
            query.append("and cta.contacId = ccd.id ");
            query.append("and off.id = cta.offeringId ");
            query.append("and cct.id = cta.userName ");
            query.append("and cct.emp_id = empb.id ");
            query.append("and cs.id = cta.statusId ");
            query.append("and cta.offeringId = com.offeringId ");
            query.append("and com.isConverted = '0' ");
            query.append("and cta.isFinished = '0' ");
            query.append("and ((cbd.userName IN(");
            query.append(userName);
            if("M".equalsIgnoreCase(cIdUserType))
            {
                query.append("))");
            } else
            {
                query.append(") and cta.userName = '");
                query.append(cId);
                query.append("' and client_takeaction_id IS NULL) OR (cta.userName = '");
                query.append(cId);
                query.append("' and client_takeaction_id IS NOT NULL) ");
            }
            query.append(") and cta.maxDateTime like '");
            query.append(month);
            query.append("%' order by maxDateTime");
            System.out.println((new StringBuilder("query:><><>")).append(query).toString());
            list = coi.executeAllSelectQuery(query.toString(), factory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public List fetchCommonDashboardActivitiesForAssociate(SessionFactory factory, String month, String userName,String cIdUserType)
    {
        List list = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            query.append("select cta.id, ccd.name, cbd.associateName, com.isConverted, off.subofferingname" +
            		", cs.statusname, SUBSTRING_INDEX(cta.maxDateTime,' ',-1) 'TIME',empb.empName, cbd.id 'cbdId' ");
            query.append("from associate_takeaction as cta,compliance_contacts as cct, employee_basic as empb, associate_contact_data as ccd, ");
            query.append("offeringlevel3 as off, associatestatus as cs, associate_basic_data as cbd, assoc" +"iate_offering_mapping as com ");
            query.append("where ");
            query.append("com.associateName = cbd.id ");
            query.append("and ccd.associateName = cbd.id ");
            query.append("and cta.contacId = ccd.id ");
            query.append("and off.id = cta.offeringId ");
            query.append("and cct.id = cta.userName ");
            query.append("and cct.emp_id=empb.id ");
            query.append("and cta.offeringId = com.offeringId ");//and cs.id =  cta.statusId
            query.append("and cs.id =  cta.statusId ");
            query.append("and com.isConverted = '0' ");
            query.append("and cta.isFinished = '0' ");
            query.append("and ((cbd.userName IN(");
            query.append(userName);
            /*query.append(") and cta.userName = '");
            query.append(cId);
            query.append("' and associate_takeaction_id IS NULL) OR (cta.userName = '");
            query.append(cId);
            query.append("' and associate_takeaction_id IS NOT NULL) ");
            query.append(") and cta.maxDateTime like '");
            query.append(month);
            query.append("%' order by maxDateTime");*/
            if("M".equalsIgnoreCase(cIdUserType))
            {
                query.append("))");
            } else
            {
                query.append(") and cta.userName = '");
                query.append(cId);
                query.append("' and associate_takeaction_id IS NULL) OR (cta.userName = '");
                query.append(cId);
                query.append("' and associate_takeaction_id IS NOT NULL) ");
            }
            query.append(") and cta.maxDateTime like '");
            query.append(month);
            query.append("%' order by maxDateTime");
            System.out.println("query  associate "+query);
            list = coi.executeAllSelectQuery(query.toString(), factory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public void buildJSONArrayForActivity(JSONArray jsonArray, List dataList, ActivityType type)
    {
        try
        {
            JSONObject jsonObject = null;
            for(Iterator iterator = dataList.iterator(); iterator.hasNext(); jsonArray.add(jsonObject))
            {
                Object obj = iterator.next();
                Object data[] = (Object[])obj;
                jsonObject = new JSONObject();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < data.length; i++)
                {
                    if(data[i] != null && !data[i].toString().equals(""))
                    {
                        if(i == 3 && type == ActivityType.client)
                        {
                            sb.append(String.valueOf(ActivityType.client.ordinal()));
                        } else
                        if(i == 3 && type == ActivityType.associate)
                        {
                            sb.append(String.valueOf(ActivityType.associate.ordinal()));
                        } else
                        if(i == 2 && type == ActivityType.activityHistory)
                        {
                            sb.append(DateUtil.convertDateToIndianFormat(data[i].toString().split(" ")[0]));
                        } else
                        {
                            sb.append(data[i].toString());
                        }
                        sb.append(",");
                    } else
                    {
                        sb.append("NA,");
                    }
                }

                jsonObject.put("VALUE", sb.substring(0, sb.lastIndexOf(",")));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public List fetchRecentActivitiesForClient(SessionFactory factory, int id)
    {
        List list = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            query.append("select cs.statusName, cta.compelling_reason, cta.comment, cta.maxDateTime ");
            query.append("from client_takeaction as cta ");
            query.append("left join client_status as cs on cs.id = cta.statusId ");
            query.append("where cta.contacId in (select id from client_contact_data where clientName ");
            query.append("in(select clientName from client_contact_data where id ");
            query.append("in (select contacId from client_takeaction where id = ");
            query.append(id);
            query.append("))) ");
            query.append("and cta.offeringId in (select offeringId from client_takeaction where id = ");
            query.append(id);
            query.append(") ");
            query.append("order by cta.maxDateTime desc ");
            list = coi.executeAllSelectQuery(query.toString(), factory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public List fetchRecentActivitiesForAssociate(SessionFactory factory, int id)
    {
        List list = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            query.append("select cs.statusname, cta.comment, cta.maxDateTime ");
            query.append("from associate_takeaction as cta ");
            query.append("left join associatestatus as cs on cs.id = cta.statusId ");
            query.append("where cta.contacId in (select id from associate_contact_data where associateName" +
" "
);
            query.append("in(select associateName from associate_contact_data where id ");
            query.append("in (select contacId from associate_takeaction where id = ");
            query.append(id);
            query.append("))) ");
            query.append("and cta.offeringId in (select offeringId from associate_takeaction where id = ");
            query.append(id);
            query.append(") ");
            query.append("order by cta.maxDateTime desc ");
            list = coi.executeAllSelectQuery(query.toString(), factory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public JSONObject fetchContantDetailsByType(SessionFactory factory, int id, ActivityType activityType)
    {
        JSONObject jsonObject = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            if(activityType == ActivityType.client)
            {
                query.append("select ccd.id, ccd.designation, ccd.contactNo, dept.deptName, ccd.emailOfficial," +
" ccd.degreeOfInfluence "
);
                query.append("from client_contact_data as ccd ");
                query.append("left join client_takeaction as cta on cta.contacId = ccd.id ");
                query.append("left join department as dept on ccd.department = dept.id ");
                query.append("where cta.id = ");
                query.append(id);
            } else
            if(activityType == ActivityType.associate)
            {
                query.append("select ccd.id, ccd.designation, ccd.contactNum, dept.deptName, ccd.emailOfficial" +
", ccd.degreeOfInfluence "
);
                query.append("from associate_contact_data as ccd ");
                query.append("left join associate_takeaction as cta on cta.contacId = ccd.id ");
                query.append("left join department as dept on ccd.department = dept.id ");
                query.append("where cta.id =");
                query.append(id);
            }
            List list = coi.executeAllSelectQuery(query.toString(), factory);
            if(list != null && list.size() > 0)
            {
                Object object[] = (Object[])list.get(0);
                jsonObject = new JSONObject();
                jsonObject.put("ID", object[0].toString());
                jsonObject.put("DESIGNATION", object[1] != null && !object[1].toString().equals("") ? ((Object) (object[1].toString())) : "NA");
                jsonObject.put("CONTACT_NO", object[2] != null && !object[2].toString().equals("") ? ((Object) (object[2].toString())) : "NA");
                jsonObject.put("DEPARTMENT", object[3] != null && !object[3].toString().equals("") ? ((Object) (object[3].toString())) : "NA");
                jsonObject.put("EMAIL", object[4] != null && !object[4].toString().equals("") ? ((Object) (object[4].toString())) : "NA");
                jsonObject.put("DOI", object[5] != null && !object[5].toString().equals("") ? ((Object) (object[5].toString())) : "NA");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject fetchOrgnizationDetailsByType(SessionFactory factory, int id, ActivityType activityType)
    {
        JSONObject jsonObject = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            if(activityType == ActivityType.client)
            {
                query.append("select cbd.id, cbd.webAddress, cbd.address, cbd.phoneNo, cbd.starRating, ind.ind" +
"ustry, sind.subIndustry "
);
                query.append("from client_basic_data as cbd, client_contact_data ccd, client_takeaction as cta" +
", industrydatalevel1 as ind, subindustrydatalevel2 as sind "
);
                query.append("where cta.contacId = ccd.id and ccd.clientName = cbd.id and cbd.industry = ind.i" +
"d and cbd.subIndustry = sind.id "
);
                query.append("and cta.id = ");
                query.append(id);
            } else
            if(activityType == ActivityType.associate)
            {
                query.append("select cbd.id, cbd.webAddress, cbd.address, cbd.phoneNo, cbd.associateRating, in" +
"d.industry, sind.subIndustry "
);
                query.append("from associate_basic_data as cbd, associate_contact_data ccd, associate_takeacti" +
"on as cta, industrydatalevel1 as ind, subindustrydatalevel2 as sind "
);
                query.append("where cta.contacId = ccd.id and ccd.associateName = cbd.id  and cbd.industry = i" +
"nd.id and cbd.subIndustry = sind.id "
);
                query.append("and cta.id = ");
                query.append(id);
            }
            List list = coi.executeAllSelectQuery(query.toString(), factory);
            if(list != null && list.size() > 0)
            {
                Object object[] = (Object[])list.get(0);
                jsonObject = new JSONObject();
                jsonObject.put("ID", object[0].toString());
                jsonObject.put("WEB_ADDRESS", object[1] != null && !object[1].toString().equals("") ? ((Object) (object[1].toString())) : "NA");
                jsonObject.put("ADDRESS", object[2] != null && !object[2].toString().equals("") ? ((Object) (object[2].toString())) : "NA");
                jsonObject.put("CONTACT_NO", object[3] != null && !object[3].toString().equals("") ? ((Object) (object[3].toString())) : "NA");
                jsonObject.put("STAR_RATING", object[4] != null && !object[4].toString().equals("") ? ((Object) (object[4].toString())) : "NA");
                jsonObject.put("INDUSTRY", object[5] != null && !object[5].toString().equals("") ? ((Object) (object[5].toString())) : "NA");
                jsonObject.put("SUB_INDUSTRY", object[6] != null && !object[6].toString().equals("") ? ((Object) (object[6].toString())) : "NA");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray fetchContactPersonByOfferingAndOrganization(SessionFactory factory, int id, ActivityType activityType, int offeringId)
    {
        JSONArray jsonArray = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            if(activityType == ActivityType.client)
            {
                query.append("select distinct(ccd.id), ccd.personName, ccd.contactNo, ccd.emailOfficial, ccd.d" +
"esignation, ccd.department, ccd.degreeOfInfluence "
);
                query.append("from client_takeaction as cta, client_contact_data as ccd ");
                query.append("where cta.contacId = ccd.id ");
                query.append("and cta.contacId in (select id from client_contact_data where clientName ");
                query.append("in(select clientName from client_contact_data where id ");
                query.append("in (select contacId from client_takeaction where id = ");
                query.append(id);
                query.append("))) ");
                query.append("and cta.offeringId ='");
                query.append(offeringId);
                query.append("' ");
            } else
            if(activityType == ActivityType.associate)
            {
                query.append("select distinct(ccd.id), ccd.name, ccd.contactNum, ccd.emailOfficial, ccd.design" +
"ation, ccd.department, ccd.degreeOfInfluence "
);
                query.append("from associate_takeaction as cta, associate_contact_data as ccd ");
                query.append("where cta.contacId = ccd.id ");
                query.append("and cta.contacId in (select id from associate_contact_data where associateName ");
                query.append("in(select associateName from associate_contact_data where id ");
                query.append("in (select contacId from associate_takeaction where id = ");
                query.append(id);
                query.append("))) ");
                query.append("and cta.offeringId ='");
                query.append(offeringId);
                query.append("' ");
            }
            List list = coi.executeAllSelectQuery(query.toString(), factory);
            if(list != null && list.size() > 0)
            {
                jsonArray = new JSONArray();
                JSONObject jsonObject = null;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); jsonArray.add(jsonObject))
                {
                    Object obj = iterator.next();
                    Object object[] = (Object[])obj;
                    jsonObject = new JSONObject();
                    jsonObject.put("ID", object[0].toString());
                    jsonObject.put("NAME", object[1] != null && !object[1].toString().equals("") ? ((Object) (object[1].toString())) : "NA");
                    jsonObject.put("CONTACT_NO", object[2] != null && !object[2].toString().equals("") ? ((Object) (object[2].toString())) : "NA");
                    jsonObject.put("EMAIL", object[3] != null && !object[3].toString().equals("") ? ((Object) (object[3].toString())) : "NA");
                    jsonObject.put("DESIGNATION", object[4] != null && !object[4].toString().equals("") ? ((Object) (object[4].toString())) : "NA");
                    jsonObject.put("DEPARTMENT", object[5] != null && !object[5].toString().equals("") ? ((Object) (object[5].toString())) : "NA");
                    jsonObject.put("DOI", object[6] != null && !object[6].toString().equals("") ? ((Object) (object[6].toString())) : "NA");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray fetchActivityDetailsByOfferingAndContact(SessionFactory factory, int contacId, int offeringId, ActivityType activityType)
    {
        JSONArray jsonArray = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            if(activityType == ActivityType.client)
            {
                query.append("select cta.id, cs.statusName, cta.comment, cta.maxDateTime ");
                query.append("from client_takeaction as cta ");
                query.append("left join client_status as cs on cs.id = cta.statusId ");
                query.append("where cta.contacId = '");
                query.append(contacId);
                query.append("' ");
                query.append("and cta.offeringId = '");
                query.append(offeringId);
                query.append("' ");
                query.append("order by cta.maxDateTime desc ");
            } else
            if(activityType == ActivityType.associate)
            {
                query.append("select cta.id, cs.statusname, cta.comment, cta.maxDateTime ");
                query.append("from associate_takeaction as cta ");
                query.append("left join associatestatus as cs on cs.id = cta.statusId ");
                query.append("where cta.contacId = '");
                query.append(contacId);
                query.append("' ");
                query.append("and cta.offeringId = '");
                query.append(offeringId);
                query.append("' ");
                query.append("order by cta.maxDateTime desc ");
            }
            List list = coi.executeAllSelectQuery(query.toString(), factory);
            if(list != null && list.size() > 0)
            {
                jsonArray = new JSONArray();
                JSONObject jsonObject = null;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); jsonArray.add(jsonObject))
                {
                    Object obj = iterator.next();
                    Object object[] = (Object[])obj;
                    jsonObject = new JSONObject();
                    jsonObject.put("ID", object[0].toString());
                    jsonObject.put("STATUS", object[1] != null && !object[1].toString().equals("") ? ((Object) (object[1].toString())) : "NA");
                    jsonObject.put("COMMENT", object[2] != null && !object[2].toString().equals("") ? ((Object) (object[2].toString())) : "NA");
                    jsonObject.put("DATE", object[3] != null && !object[3].toString().equals("") ? ((Object) ((new StringBuilder(String.valueOf(DateUtil.convertDateToIndianFormat(object[3].toString().split(" ")[0].trim())))).append(" ").append(object[3].toString().split(" ")[1]).toString())) : "NA");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray fetchOfferingMappedWithOrganization(SessionFactory factory, int id, ActivityType activityType)
    {
        JSONArray jsonArray = null;
        try
        {
            CommonOperInterface coi = (new CommonConFactory()).createInterface();
            StringBuilder query = new StringBuilder();
            CommonHelper ch = new CommonHelper();
            String tempOffering[] = ch.getOfferingName();
            if(activityType == ActivityType.client)
            {
                query.append("select off.id, off.");
                query.append(tempOffering[0]);
                query.append(" from ");
                query.append(tempOffering[1]);
                query.append(" as off, client_offering_mapping as com, client_contact_data as ccd, client_take" +
"action as cta "
);
                query.append("where off.id = com.offeringId and com.clientName = ccd.clientName and ccd.id = c" +
"ta.contacId "
);
                query.append("and com.isConverted = '0' ");
                query.append("and cta.id = ");
                query.append(id);
            } else
            if(activityType == ActivityType.associate)
            {
                query.append("select off.id, off.");
                query.append(tempOffering[0]);
                query.append(" from ");
                query.append(tempOffering[1]);
                query.append(" as off, associate_offering_mapping as com, associate_contact_data as ccd, assoc" +
"iate_takeaction as cta "
);
                query.append("where off.id = com.offeringId and com.associateName = ccd.associateName and ccd." +
"id = cta.contacId "
);
                query.append("and com.isConverted = '0' ");
                query.append("and cta.id = ");
                query.append(id);
            }
            List list = coi.executeAllSelectQuery(query.toString(), factory);
            if(list != null && list.size() > 0)
            {
                jsonArray = new JSONArray();
                JSONObject jsonObject = null;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); jsonArray.add(jsonObject))
                {
                    Object obj = iterator.next();
                    Object object[] = (Object[])obj;
                    jsonObject = new JSONObject();
                    jsonObject.put("ID", object[0].toString());
                    jsonObject.put("VALUE", object[1] != null && !object[1].toString().equals("") ? ((Object) (object[1].toString())) : "NA");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public ArrayList fetchMissedActivities()
    {
        ArrayList mainList = new ArrayList();
        try
        {
            CommonHelper ch = new CommonHelper();
            String cIdAll = ch.getHierarchyContactIdByEmpId(empIdofuser);
            StringBuilder queryClient = new StringBuilder();
            queryClient.append("select cta.id, cs.statusName, cbd.clientName, 'Client', cta.maxDateTime, empb.em" +
"pName from client_takeaction as cta inner join client_status as cs on cs.id = ct" +
"a.statusId inner join client_contact_data as ccd on ccd.id = cta.contacId inner " +
"join client_basic_data as cbd on cbd.id = ccd.clientName inner join compliance_c" +
"ontacts as cct on cct.id = cta.userName inner join employee_basic as empb on cct" +
".emp_id = empb.id where cta.maxDateTime < '"
);
            queryClient.append(DateUtil.getCurrentDateUSFormat());
            queryClient.append(" 00:00");
            queryClient.append("' and cbd.userName in (");
            queryClient.append(cIdAll);
            queryClient.append(") and cta.isFinished = '0' order by cta.maxDateTime desc");
            List list = coi.executeAllSelectQuery(queryClient.toString(), connectionSpace);
            mainList = CommonHelper.convertDBRecordsToNestedArrayList(list, 4, true);
            StringBuilder queryAssociate = new StringBuilder();
            queryAssociate.append("select cta.id, cs.statusname, cbd.associateName, 'Associate', cta.maxDateTime fr" +
"om associate_takeaction as cta inner join associatestatus as cs on cs.id = cta.s" +
"tatusId inner join associate_contact_data as ccd on ccd.id = cta.contacId inner " +
"join associate_basic_data as cbd on cbd.id = ccd.associateName where cta.maxDate" +
"Time < '"
);
            queryAssociate.append(DateUtil.getCurrentDateUSFormat());
            queryClient.append(" 00:00");
            queryAssociate.append("' and cbd.userName in (");
            queryAssociate.append(cIdAll);
            queryAssociate.append(") and cta.isFinished = '0' order by cta.maxDateTime desc");
            list = null;
            list = coi.executeAllSelectQuery(queryAssociate.toString(), connectionSpace);
            mainList.addAll(CommonHelper.convertDBRecordsToNestedArrayList(list, 4, true));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return mainList;
    }
}
