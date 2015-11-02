package com.Over2Cloud.ctrl.krLibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class KRDashboardHelper
{

	@SuppressWarnings("rawtypes")
	public List<KRDashPojo> getKRStatusData(SessionFactory connectionSpace,String shareID, CommonOperInterface cbt)
	{
		List<KRDashPojo> data=null;
		try
		{
			StringBuilder query =new StringBuilder();
			query.append("SELECT upload.kr_name,grp.groupName,subGroup.subGroupName,sh.userName,dept.deptName,sh.dueShareDate, ");
			query.append(" sh.accessType,sh.actionReq,sh.id FROM kr_share_data AS sh ");
			query.append(" LEFT JOIN kr_upload_data AS upload ON sh.doc_name=upload.id ");
			query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
			query.append(" LEFT JOIN kr_group_data AS grp ON subGroup.groupName=grp.id ");
			query.append(" LEFT JOIN department AS dept ON grp.dept=dept.id  ");
			query.append(" WHERE sh.id IN ("+shareID+")");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				data=new ArrayList<KRDashPojo>();
				KRDashPojo PP=null;
				Object[] object =null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					object= (Object[]) iterator.next();
					if (object!=null)
					{
						PP=new KRDashPojo();
						PP.setKrName(getValueWithNullCheck(object[0]));
						PP.setGroup(getValueWithNullCheck(object[1]));
						PP.setSubGroup(getValueWithNullCheck(object[2]));
						PP.setShareBy(getValueWithNullCheck(object[3]));
						PP.setShareDept(getValueWithNullCheck(object[4]));
						PP.setShareDate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[5])));
						PP.setAccessRights(getValueWithNullCheck(object[6]));
						PP.setActionable(getValueWithNullCheck(object[7]));
						PP.setShareId(getValueWithNullCheck(object[8]));
					}
					data.add(PP);
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("rawtypes")
	public List<KRDashPojo> getKRScoreData(SessionFactory connectionSpace,String shareId, CommonOperInterface cbt,String userName )
	{
		List<KRDashPojo> data=null;
		try
		{
			data = new ArrayList<KRDashPojo>();
			StringBuilder query =null;
			List<String> shareStatus=new ArrayList<String>();
			shareStatus.add("Share By Me");
			shareStatus.add("Share With Me");
			KRDashPojo PP=null;
			String qryString=null;
			
			for (String status : shareStatus)
			{
				query = new StringBuilder();
				PP=new KRDashPojo();
				PP.setShareStatus(status);
				if (status.equalsIgnoreCase("Share By Me"))
				{
					qryString=" userName='"+userName+"'";
				}
				else if(status.equalsIgnoreCase("Share With Me"))
				{
					qryString=" id IN ("+shareId+")";
				}
				query.append("SELECT actionStatus FROM kr_share_data WHERE "+qryString+" AND actionReq='Yes' ");
				List shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (shareData!=null && shareData.size()>0)
				{
			    	for (Iterator iterator = shareData.iterator(); iterator.hasNext();)
					{
			    		int pending=0,done=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							if (object.toString().equalsIgnoreCase("Pending"))
							{
								pending=1;
							}
							else if (object.toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (pending>0)
						{
							PP.setPending(String.valueOf(pending+Integer.parseInt(PP.getPending())));
						}
						if (done>0)
						{
							PP.setActionTaken(String.valueOf(done+Integer.parseInt(PP.getActionTaken())));
						}
					}
			    	shareData.clear();
				}
			    query.setLength(0);
		    	query.append("SELECT count(*) FROM kr_share_data WHERE "+qryString+" AND actionReq='No' ");
		    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    	if (shareData!=null && !shareData.isEmpty())
				{
		    		PP.setActionable(String.valueOf(shareData.get(0).toString()));
				}
		    	shareData.clear();
			    query.setLength(0);
			    query.append("SELECT actionStatus FROM kr_share_data WHERE "+qryString+" AND actionReq='Yes'  AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			    shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (shareData!=null && shareData.size()>0)
				{
			    	for (Iterator iterator = shareData.iterator(); iterator.hasNext();)
					{
			    		int pending=0,done=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							if (object.toString().equalsIgnoreCase("Pending"))
							{
								pending=1;
							}
							else if (object.toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (pending>0)
						{
							PP.setLastMonthPending(String.valueOf(pending+Integer.parseInt(PP.getLastMonthPending())));
						}
						if (done>0)
						{
							PP.setLastMonthActionTaken(String.valueOf(done+Integer.parseInt(PP.getLastMonthActionTaken())));
						}
					}
			    	shareData.clear();
				}
			    query.setLength(0);
		    	query.append("SELECT count(*) FROM kr_share_data WHERE "+qryString+" AND actionReq='No' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
		    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    	if (shareData!=null && !shareData.isEmpty())
				{
		    		PP.setLastMonthActionable(String.valueOf(shareData.get(0).toString()));
				}
		    	shareData.clear();
			    query.setLength(0);
			    query.append("SELECT actionStatus FROM kr_share_data WHERE "+qryString+" AND actionReq='Yes'  AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
			    shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (shareData!=null && shareData.size()>0)
				{
			    	for (Iterator iterator = shareData.iterator(); iterator.hasNext();)
					{
			    		int pending=0,done=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							if (object.toString().equalsIgnoreCase("Pending"))
							{
								pending=1;
							}
							else if (object.toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (pending>0)
						{
							PP.setLastWeekPending(String.valueOf(pending+Integer.parseInt(PP.getLastWeekPending())));
						}
						if (done>0)
						{
							PP.setLastWeekActionTaken(String.valueOf(done+Integer.parseInt(PP.getLastWeekActionTaken())));
						}
					}
			    	shareData.clear();
				}
				query.setLength(0);
		    	query.append("SELECT count(*) FROM kr_share_data WHERE "+qryString+" AND actionReq='No' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
		    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    	if (shareData!=null && !shareData.isEmpty())
				{
		    		PP.setLastWeekActionable(String.valueOf(shareData.get(0).toString()));
				}
		    	shareData.clear();
			    query.setLength(0);
			    query.append("SELECT actionStatus FROM kr_share_data WHERE "+qryString+" AND actionReq='Yes'  AND dueShareDate = '"+DateUtil.getNextDateAfter(-1)+"'");
			    shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (shareData!=null && shareData.size()>0)
				{
			    	for (Iterator iterator = shareData.iterator(); iterator.hasNext();)
					{
			    		int pending=0,done=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							if (object.toString().equalsIgnoreCase("Pending"))
							{
								pending=1;
							}
							else if (object.toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (pending>0)
						{
							PP.setYesterPending(String.valueOf(pending+Integer.parseInt(PP.getYesterPending())));
						}
						if (done>0)
						{
							PP.setYesterActionTaken(String.valueOf(done+Integer.parseInt(PP.getYesterActionTaken())));
						}
					}
			    	shareData.clear();
				}
			    query.setLength(0);
		    	query.append("SELECT count(*) FROM kr_share_data WHERE "+qryString+" AND actionReq='No' AND dueShareDate = '"+DateUtil.getNextDateAfter(-1)+"'");
		    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    	if (shareData!=null && !shareData.isEmpty())
				{
		    		PP.setYesterActionable(String.valueOf(shareData.get(0).toString()));
				}
		    	shareData.clear();
			    query.setLength(0);
			    query.append("SELECT actionStatus FROM kr_share_data WHERE "+qryString+" AND actionReq='Yes'  AND dueShareDate = '"+DateUtil.getCurrentDateUSFormat()+"'");
			    shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (shareData!=null && shareData.size()>0)
				{
			    	for (Iterator iterator = shareData.iterator(); iterator.hasNext();)
					{
			    		int pending=0,done=0;
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							if (object.toString().equalsIgnoreCase("Pending"))
							{
								pending=1;
							}
							else if (object.toString().equalsIgnoreCase("Done"))
							{
								done=1;
							}
						}
						if (pending>0)
						{
							PP.setTodayPending(String.valueOf(pending+Integer.parseInt(PP.getTodayPending())));
						}
						if (done>0)
						{
							PP.setTodayActionTaken(String.valueOf(done+Integer.parseInt(PP.getTodayActionTaken())));
						}
					}
			    	shareData.clear();
				}
			    query.setLength(0);
		    	query.append("SELECT count(*) FROM kr_share_data WHERE "+qryString+" AND actionReq='No' AND dueShareDate = '"+DateUtil.getCurrentDateUSFormat()+"'");
		    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    	if (shareData!=null && !shareData.isEmpty())
				{
		    		PP.setTodayActionable(String.valueOf(shareData.get(0).toString()));
				}
		    	shareData.clear();
				data.add(PP);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public List<KRDashPojo> getKRAgeingData(SessionFactory connectionSpace,String shareId, CommonOperInterface cbt, String userName)
	{
		List<KRDashPojo> data=null;
		try
		{
			data = new ArrayList<KRDashPojo>();
			StringBuilder query  = new StringBuilder();
			KRDashPojo PP=null;
			
			PP=new KRDashPojo();
			
		    query.append("SELECT COUNT(*) FROM kr_share_data WHERE id IN("+shareId+")  AND dueShareDate BETWEEN   '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNextDateAfter(30)+"' ");
		    List shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    if (shareData!=null && shareData.size()>0)
			{
		    	PP.setThisMonth(shareData.get(0).toString());
		    	shareData.clear();
			}
		    query.setLength(0);
	    	query.append("SELECT count(*) FROM kr_share_data WHERE id IN("+shareId+") AND dueShareDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNextDateAfter(7)+"' ");
	    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	    	if (shareData!=null && !shareData.isEmpty())
			{
	    		PP.setThisWeek(shareData.get(0).toString());
			}
	    	shareData.clear();
		    query.setLength(0);
	    	query.append("SELECT count(*) FROM kr_share_data WHERE id IN("+shareId+") AND dueShareDate BETWEEN   '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat())+"' ");
	    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	    	if (shareData!=null && !shareData.isEmpty())
			{
	    		PP.setThisYear(String.valueOf(shareData.get(0).toString()));
			}
	    	shareData.clear();
		    
			data.add(PP);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public List<KRDashPojo> fetchTotalScoreDetails(SessionFactory connectionSpace, String shareId,CommonOperInterface cbt, String userName)
	{
		List<KRDashPojo> data=null;
		try
		{
			data = new ArrayList<KRDashPojo>();
			StringBuilder query  = new StringBuilder();
			KRDashPojo PP=null;
			
			PP=new KRDashPojo();
			
		    query.append("SELECT COUNT(*) FROM kr_upload_data WHERE userName ='"+userName+"' AND flag='0'");
		    List shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		    if (shareData!=null && shareData.size()>0)
			{
		    	PP.setTotalKR(shareData.get(0).toString());
		    	shareData.clear();
			}
		    query.setLength(0);
	    	query.append("SELECT count(*) FROM kr_share_data WHERE id IN("+shareId+") ");
	    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	    	if (shareData!=null && !shareData.isEmpty())
			{
	    		PP.setShareKR(shareData.get(0).toString());
			}
	    	shareData.clear();
		    query.setLength(0);
	    	query.append("SELECT count(*) FROM kr_share_download_history AS history LEFT JOIN kr_share_data As sh ON sh.id=history.shareId WHERE sh.id IN("+shareId+")");
	    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	    	if (shareData!=null && !shareData.isEmpty())
			{
	    		PP.setDownloadKR(String.valueOf(shareData.get(0).toString()));
			}
	    	shareData.clear();
	    	query.setLength(0);
	    	query.append("SELECT count(*) FROM kr_share_report_data AS report LEFT JOIN kr_share_data As sh ON sh.id=report.krSharingId WHERE sh.id IN("+shareId+")");
	    	shareData=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	    	if (shareData!=null && !shareData.isEmpty())
			{
	    		PP.setEditKR(String.valueOf(shareData.get(0).toString()));
			}
			data.add(PP);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
}
