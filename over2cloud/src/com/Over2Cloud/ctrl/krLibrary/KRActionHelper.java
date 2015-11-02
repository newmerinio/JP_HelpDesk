package com.Over2Cloud.ctrl.krLibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

public class KRActionHelper 
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	public String getFileExtenstion(String contentType) {
		if (contentType.equals("image/pjpeg")) {
		return ".jpg";
		} else if (contentType.equals("image/jpeg")) {
		return ".jpg";
		} 
		else if (contentType.equals("image/bmp")) {
			return ".bmp";
			} 
		else if (contentType.equals("image/jpg")) {
			return ".jpg";
			} 
		else if (contentType.equals("image/gif")) {
		return ".gif";
		} else if (contentType.equals("image/png")) {
		return ".png";
		}
		else if (contentType.equals("text/css")) {
			return ".css";
			} 
		else if (contentType.equals("text/html")) {
			return ".html";
			} 
		else if (contentType.equals("text/plain")) {
			return ".txt";
			} 
		else if (contentType.equals("text/xml")) {
			return ".xml";
			} 
		else if (contentType.equals("video/mpeg")) {
			return ".mpeg";
			} 
		else if (contentType.equals("video/mp4")) {
			return ".mp4";
			} 
		else if (contentType.equals("video/ogg")) {
			return ".ogg";
			} 
		else if (contentType.equals("application/pdf")) {
			return ".pdf";
			} 
		else if (contentType.equals("application/zip")) {
			return ".zip";
			} 
		else if (contentType.equals("image/tiff")) {
			return ".tiff";
			} 
		else if (contentType.equals("application/vnd.oasis.opendocument.text")) {
			return ".txt";
			} 
		else if (contentType.equals("application/vnd.ms-excel")) {
			return ".xls";
			} 
		else if (contentType.equals("application/vnd.ms-powerpoint")) {
			return ".ppt";
			} 
		else if (contentType.equals("application/msword")) {
			return ".docx";
			} 
		
		else if (contentType.equals("audio/basic")) {
			return ".mp3";
			} 
		else if (contentType.equals("audio/mpeg")) {
			return ".mpeg";
			} 
		else if (contentType.equals("audio/x-ms-wma")) {
			return ".wma";
			} 
		else if (contentType.equals("audio/x-wav")) {
			return ".wav";
			} 
		else if (contentType.equals("audio/mp3")) {
			return ".mp3";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return ".docx";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
			return ".pptx";
			}
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return ".xlsx";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
			return ".pptx";
			} 
		else if (contentType.equals("video/x-msvideo")) {
			return ".avi";
			} 
		else if (contentType.equals("application/x-msclip")) {
			return ".clp";
			} 
		else if (contentType.equals("text/css")) {
			return ".css";
			} 
		else if (contentType.equals("application/x-msdownload")) {
			return ".dll";
			} 
		else if (contentType.equals("application/x-dvi")) {
			return ".dvi";
			}
		else if (contentType.equals("application/x-gtar")) {
			return ".gtar";
			}
		else if (contentType.equals("image/x-icon")) {
			return ".ico";
			}
		else if (contentType.equals("image/pipeg")) {
			return ".jfif";
			}
		else if (contentType.equals("application/x-javascript")) {
			return ".js";
			}
		else if (contentType.equals("audio/mid")) {
			return ".mid";
			}
		else if (contentType.equals("video/quicktime")) {
			return ".mov";
			}
		else if (contentType.equals("application/pdf")) {
			return ".pdf";
			}
		else if (contentType.equals("video/quicktime")) {
			return ".qt";
			}
		else if (contentType.equals("audio/x-wav")) {
			return ".wav";
			}
		else if (contentType.equals("application/zip")) {
			return ".zip";
			}
		else {
		return null;
		}
		}

	@SuppressWarnings("unchecked")
	public List getGroupName(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id, group_name from kr_group_data WHERE status='Active'", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAssignedDepartment(SessionFactory connectionSpace,String depid) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type as dept  ");
			query.append("INNER JOIN manage_contact as cc ON cc.from_contact_sub_type_id=dept.id ");
			query.append(" WHERE cc.for_contact_sub_type_id='"+depid+"' AND cc.module_name='KR' AND dept.status='Active' GROUP BY dept.contact_subtype_name ORDER BY dept.contact_subtype_name");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public String[] getEmpDetailsByUserName(String moduleName,String userName,SessionFactory connectionSpace)
	{
		String[] values =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			StringBuilder query=new StringBuilder();
			query.append("select contact.id,emp.emp_name,emp.mobile_no,emp.email_id,emp.sub_type_id as deptid, dept.contact_subtype_name,emp.id as empid from primary_contact as emp ");
			query.append(" inner join manage_contact as contact on contact.emp_id=emp.id");
			query.append(" inner join contact_sub_type as dept on emp.sub_type_id=dept.id");
			query.append(" inner join user_account as uac on emp.user_account_id=uac.id where contact.module_name='"+moduleName+"' and uac.user_name='");
			query.append(userName + "' and contact.for_contact_sub_type_id=dept.id AND emp.status=0 AND contact.work_status=0 ");
			//System.out.println("Common Helper Class "+query.toString());
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[7];
				Object[] object=(Object[])dataList.get(0);
				values[0]=getValueWithNullCheck(object[0]);
				values[1]=getValueWithNullCheck(object[1]);
				values[2]=getValueWithNullCheck(object[2]);
				values[3]=getValueWithNullCheck(object[3]);
				values[4]=getValueWithNullCheck(object[4]);
				values[5]=getValueWithNullCheck(object[5]);
				values[6]=getValueWithNullCheck(object[6]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	@SuppressWarnings("unchecked")
	public List getCreatedByDetails(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select user_name from kr_upload_data ORDER BY user_name", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("unchecked")
	public List getDeptName(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id, contact_subtype_name from contact_sub_type ORDER BY contact_subtype_name", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getContactType(SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id,contact_name from contact_type WHERE mapped_level='1'", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getContactFromDept(String toDept, String fromDeptId,
			SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT cc.id,emp.emp_name FROM manage_contact as cc  ");
			query.append("INNER JOIN contact_sub_type as dept ON cc.for_contact_sub_type_id=dept.id ");
			query.append("INNER JOIN primary_contact as emp ON cc.emp_id=emp.id ");
			query.append(" WHERE cc.for_contact_sub_type_id='"+toDept+"' AND cc.from_contact_sub_type_id IN ("+fromDeptId+") AND cc.module_name='KR' AND emp.status=0 AND cc.work_status=0 GROUP BY emp.emp_name ORDER BY emp.emp_name");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Object getMultipleDeptName(String empId,
			SessionFactory connectionSpace)
	{
		String deptName=null;
		StringBuilder dept=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			
			query.append("SELECT dept.contact_subtype_name FROM contact_sub_type AS dept  ");
			query.append("INNER JOIN primary_contact AS emp ON emp.sub_type_id =dept.id ");
			query.append("INNER JOIN manage_contact AS cc ON cc.emp_id =emp.id ");
			query.append(" WHERE cc.id IN("+empId+") GROUP BY dept.contact_subtype_name");
			//System.out.println("QQQQQQQQQ   "+query.toString());
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						dept.append(object.toString()+", ");
					}
				}
				deptName=dept.toString().substring(0, dept.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return deptName;
	}

	@SuppressWarnings("unchecked")
	public Object getMultipleEmpName(String empId,
			SessionFactory connectionSpace)
	{
		String employeeName=null;
		StringBuilder emp=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT emp.emp_name FROM  contact_sub_type  AS dept   ");
			query.append("INNER JOIN primary_contact AS emp ON emp.sub_type_id =dept.id ");
			query.append("INNER JOIN manage_contact AS cc ON cc.emp_id =emp.id ");
			query.append(" WHERE cc.id IN("+empId+") ");
			//System.out.println("QQQQQQQQQ   "+query.toString());
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						emp.append(object.toString()+", ");
					}
				}
				employeeName=emp.toString().substring(0, emp.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return employeeName;
	}
	@SuppressWarnings("unchecked")
	public String  contactIdLoggenedMulti(String empId,String moduleName,SessionFactory connectionSpace) 
	{
		String employeeName=null;
		StringBuilder emp=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT cc.id FROM manage_contact   ");
			query.append("AS cc LEFT JOIN primary_contact As emp ON emp.id=cc.emp_id ");
			query.append(" WHERE cc.emp_id ="+empId+" AND module_name='"+moduleName+"'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						emp.append(object.toString()+", ");
					}
				}
				employeeName=emp.toString().substring(0, emp.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return employeeName;
	}
	@SuppressWarnings("unchecked")
	public List getGroupAssignedDepartment(SessionFactory connectionSpace,String forDept) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type as dept  ");
			query.append("INNER JOIN manage_contact as cc ON cc.from_contact_sub_type_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.sub_type_id=dept.id ");
			query.append(" WHERE cc.for_contact_sub_type_id='"+forDept+"' AND cc.module_name='KR' GROUP BY dept.contact_subtype_name ORDER BY dept.contact_subtype_name");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getLibraryAssignedDepartment(SessionFactory connectionSpace,String forDept) {
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type as dept  ");
			query.append("INNER JOIN manage_contact as cc ON cc.from_contact_sub_type_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.sub_type_id=dept.id ");
			query.append("INNER JOIN kr_sub_group_data as krsubgroup ON krsubgroup.group_name=krgroup.id ");
			query.append("INNER JOIN kr_upload_data as upload ON upload.sub_group_name=krsubgroup.id ");
			query.append(" WHERE cc.for_contact_sub_type_id='"+forDept+"' AND cc.module_name='KR' GROUP BY dept.contact_subtype_name ORDER BY dept.contact_subtype_name");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getSharedAssignedDepartment(SessionFactory connectionSpace,
			String fromDept) {
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type as dept  ");
			query.append("INNER JOIN manage_contact as cc ON cc.from_contact_sub_type_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.sub_type_id=dept.id ");
			query.append("INNER JOIN kr_sub_group_data as krsubgroup ON krsubgroup.group_name=krgroup.id ");
			query.append("INNER JOIN kr_upload_data as upload ON upload.sub_group_name=krsubgroup.id ");
			query.append("INNER JOIN kr_share_data as share ON share.doc_name=upload.id ");
			query.append(" WHERE cc.for_contact_sub_type_id='"+fromDept+"' AND cc.module_name='KR' GROUP BY dept.contact_subtype_name ORDER BY dept.contact_subtype_name");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List fetchAllKRDetails(int maxId,SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.contact_subtype_name,krGroup.group_name,subGroup.sub_group_name,  ");
			query.append("krshare.emp_name,krshare.access_type,krshare.action_req,krshare.due_share_date,krshare.due_date,krshare.frequency,upload.user_name,krshare.created_date,krshare.created_time,krshare.rating_required,krshare.comments_required FROM kr_share_data AS krshare ");
			query.append("LEFT JOIN  kr_upload_data AS upload ON krshare.doc_name=upload.id ");
			query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
			query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
			query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
			query.append(" WHERE krshare.id='"+maxId+"'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public KRPojo krDetailsSet(List data) 
	{
		KRPojo fbp =null;
		if (data!=null && data.size()>0) 
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new KRPojo();
				fbp.setKrId(getValueWithNullCheck(object[0]));
				fbp.setKrName(getValueWithNullCheck(object[1]));
				fbp.setKrBrief(getValueWithNullCheck(object[2]));
				fbp.setKrTags(getValueWithNullCheck(object[3]));
				fbp.setKrAttach(getValueWithNullCheck(object[4]));
				fbp.setDepartment(getValueWithNullCheck(object[5]));
				fbp.setGroup(getValueWithNullCheck(object[6]));
				fbp.setSubGroup(getValueWithNullCheck(object[7]));
				fbp.setKrShareTo(getValueWithNullCheck(object[8]));
				fbp.setKrAccessType(getValueWithNullCheck(object[9]));
				fbp.setKrActionReq(getValueWithNullCheck(object[10]));
				if (object[11]!=null) 
				{
					fbp.setKrDueDateReq(DateUtil.convertDateToIndianFormat(object[11].toString()));
				}
				else
				{
					fbp.setKrDueDateReq("NA");
				}
				if (object[12]!=null) 
				{
					fbp.setKrDueDate(DateUtil.convertDateToIndianFormat(object[12].toString()));
				} 
				else 
				{
					fbp.setKrDueDate("NA");
				}
			    if (object[13]!=null && !object[13].toString().equalsIgnoreCase("-1"))
			    {
			    	fbp.setKrFreq(new ComplianceReminderHelper().getFrequencyName(object[13].toString()));
				} 
			    else 
			    {
					fbp.setKrFreq("NA");
				}
				
				fbp.setKrRating(getValueWithNullCheck(object[17]));
				fbp.setKrComments(getValueWithNullCheck(object[18]));
			}
		}
		return fbp;
	}

	@SuppressWarnings("unchecked")
	public String fetchCountValue(String tableName, SessionFactory connectionSpace) 
	{
		String count=null;
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT COUNT(*) FROM ");
			query.append(tableName);
			query.append(" WHERE status='Active'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list!=null && !list.isEmpty()) 
			{
				count=list.get(0).toString();
			}		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public String fetchDocumentHistory(String shareId,String dataFor, SessionFactory connectionSpace) 
	{
		String shareID=null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			if (dataFor!=null && dataFor.equalsIgnoreCase("download")) 
			{
				query.append("SELECT COUNT(*) FROM kr_share_download_history AS download LEFT JOIN kr_share_data AS krShare ON krShare.id=download.shareId WHERE krShare.doc_name="+shareId);
			}
			else if(dataFor!=null && dataFor.equalsIgnoreCase("edit"))
			{
				query.append("SELECT COUNT(*) FROM kr_share_report_data AS report LEFT JOIN kr_share_data AS krshare on krshare.id=report.kr_sharing_id WHERE krshare.doc_name="+shareId);
			}
			else if(dataFor!=null && dataFor.equalsIgnoreCase("share"))
			{
				query.append("SELECT COUNT(*) FROM kr_share_data WHERE doc_name="+shareId);
			}
			//System.out.println("query.toString()    "+query.toString());
			List data=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data!=null && data.size()>0) 
			{
				shareID=data.get(0).toString();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return shareID;
	}

	@SuppressWarnings("unchecked")
	public List fetchAllKRUploadDetails(int docId,SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.contact_subtype_name,krGroup.group_name,subGroup.sub_group_name, ");
			query.append(" upload.access_type,upload.user_name,upload.created_date,upload.created_time FROM kr_upload_data AS upload ");
			query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
			query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
			query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
			query.append(" WHERE upload.id='"+docId+"'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	
}
