package com.Over2Cloud.ctrl.feedback.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class SMSSenderForFeedbackHelper
{

	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static Map<String, Object> parameterClause = new HashMap<String, Object>();
	private static final MsgMailCommunication MMC = new MsgMailCommunication();
	private static String hosName = "BLK Hospital";
	private static String posKeyWrd = "BLKY";
	private static String negKeyWrd = "BLKN";
	private static List<FeedbackPojo> smsSendList = new ArrayList<FeedbackPojo>();
	static final Log log = LogFactory.getLog(SMSSenderForFeedbackHelper.class);

	public void sendSMSForFeedback(SessionFactory connection)
	{
		try
		{
			if (true)
			{
				Object[] object = null;
				StringBuilder buffer = new StringBuilder("select startTime,endTime,afterDays from feedback_sms_config");
				List data = cbt.executeAllSelectQuery(buffer.toString(), connection);
				String startTime = null;
				String endTime = null;
				String afterDays = null;

				String smsDate = null;
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();

						if (object != null)
						{
							if (object[0] != null)
							{
								startTime = object[0].toString();
							}

							if (object[1] != null)
							{
								endTime = object[1].toString();
							}

							if (object[2] != null)
							{
								afterDays = object[2].toString();
							}
						}
					}
				}
				boolean validHrs = validDateTime2SendSMS(startTime, endTime);
				System.out.println("validHrs or not :" + validHrs);
				if (validHrs && afterDays != null && !afterDays.equalsIgnoreCase(""))
				{
					smsSendList.clear();
					smsDate = DateUtil.getNextDateAfter(Integer.parseInt(afterDays));
					smsSendList = getAllClientsToSendSMS(connection, smsDate);
					System.out.println("smsSendList ki size >>>" + smsSendList.size());
					if (smsDate != null && !smsDate.equalsIgnoreCase(""))
					{
						for (FeedbackPojo pojo : smsSendList)
						{
							// Dear MONU, Thanks for availing services at BLK
							// Hospital. Please SMS 'Y' if U R satisfied else
							// SMS 'N' if not satisfied to 9015189269
							String msg = "Dear " + pojo.getName() + ",  Thanks for availing services at " + hosName + ". Please SMS '" + posKeyWrd + "' if U R satisfied else SMS '" + negKeyWrd + "' if not satisfied to 9266605050";
							@SuppressWarnings("unused")
							String smsStamp = getSMSStamp(smsDate, startTime, endTime);

							// sendSMS2T2MTable(msg,pojo.getMobileNo(),smsStamp);
							// http://IP:8080/T2MBLK/urlHlp?type=1&text=HI&to=9250400311&smsstamp=&keyword=
							// For Feedback:

							// Commented on 19 April for BLK
							System.out.println("Added for Sending");
							MMC.addScheduledMessage(pojo.getName(), "", pojo.getMobileNo(), msg, "", "Pending", "11", "FM", smsDate, startTime + ":00", connection);
						}
					}
				}
			}
			smsSendList.clear();
			Runtime rt = Runtime.getRuntime();
			rt.gc();
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in method sendSMSForFeedback of class " + getClass(), e);
			e.printStackTrace();
		}
	}

	private boolean validDateTime2SendSMS(String startTime, String endTime)
	{
		boolean validTime = false;
		if (startTime != null && endTime != null)
		{
			String _currTime = DateUtil.getCurrentTimeHourMin().replace(":", "");
			String _endTime = endTime.replace(":", "");
			String _startTime = startTime.replace(":", "");

			if (Integer.parseInt(_currTime) >= Integer.parseInt(_startTime) && Integer.parseInt(_currTime) <= Integer.parseInt(_endTime))
			{
				validTime = true;
				return validTime;
			} else
			{
				validTime = false;
				return validTime;
			}
		} else
		{
			validTime = false;
			return validTime;
		}
	}

	public String getSMSStamp(String msgDate, String startTime, String endTime)
	{
		String arr[] = msgDate.split("-");
		String finalString = "";
		for (int i = 0; i < arr.length; i++)
		{
			finalString += arr[i];
		}

		String arr1[] = startTime.split(":");

		for (int i = 0; i < arr1.length; i++)
		{
			finalString += arr1[i];
		}

		String arr2[] = endTime.split(":");
		for (int i = 0; i < arr2.length; i++)
		{
			finalString += arr2[i];
		}
		return finalString;
	}

	@SuppressWarnings("deprecation")
	public boolean sendSMS2T2MTable(String msg, String mobNo, String smsStamp)
	{
		boolean sendFlag = true;
		String URL = null;

		// URL =
		// "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=blkhdm&password=blk31";
		URL = "http://172.17.17.126:8080/T2MBLK/urlhdm?type=1&smsstamp=" + smsStamp + "&keyword=blky";

		StringBuilder url = new StringBuilder(URL);
		String encodedMsg = URLEncoder.encode(msg);

		url.append("&to=");
		url.append(mobNo);
		url.append("&text=");
		url.append(encodedMsg);

		if (url != null)
		{
			try
			{
				URL codedURL = new URL(url.toString());
				HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
				HURLC.connect();
				@SuppressWarnings("unused")
				int returnCode = HURLC.getResponseCode();
				HURLC.disconnect();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return sendFlag;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackPojo> getAllClientsToSendSMS(SessionFactory connection, String smsDate)
	{
		List<FeedbackPojo> pojoList = new ArrayList<FeedbackPojo>();
		try
		{
			List dataList = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder buffer = new StringBuilder("select id,cr_number,patient_name,admit_consultant,visit_type,mobile_no,email,insert_date,smsFlag,sms_date,patient_type from patientinfo where smsFlag='0' and patient_type='OPD' and insert_date='" + smsDate + "' " + " and (mobile_no LIKE '9_________' || mobile_no LIKE '8_________' || mobile_no LIKE '7_________' || mobile_no LIKE '6_________') GROUP BY mobile_no ");
			// System.out.println("For Sending SMS >>>"+buffer);
			dataList = cbt.executeAllSelectQuery(buffer.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				addData(dataList, pojoList, connection);
				dataList.clear();
			}
			buffer.setLength(0);
			buffer.append("select id,cr_number,patient_name,admit_consultant,visit_type,mobile_no,email,insert_date,smsFlag,sms_date,patient_type from patientinfo where smsFlag='0' and patient_type='IPD' and insert_date='" + smsDate + "' and discharge_datetime!='NA' GROUP BY mobile_no ");
			// System.out.println("IPD query"+buffer);
			dataList = cbt.executeAllSelectQuery(buffer.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				addData(dataList, pojoList, connection);
				dataList.clear();
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in method getAllClientsToSendSMS of class " + getClass(), e);
			e.printStackTrace();
		}
		System.out.println("PAt size for sending SMS >> " + pojoList.size());
		return pojoList;
	}

	@SuppressWarnings("rawtypes")
	public void addData(List dataList, List<FeedbackPojo> pojoList, SessionFactory connection)
	{
		try
		{
			Object[] object = null;
			FeedbackPojo pojo = null;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				pojo = new FeedbackPojo();
				object = (Object[]) iterator.next();
				// System.out.println("Id is as "+object[0]);
				if (object != null)
				{
					if (object[0] != null)
					{
						pojo.setId(Integer.parseInt(object[0].toString()));
					}

					if (object[1] != null)
					{
						pojo.setPatId(object[1].toString());
					}

					if (object[2] != null)
					{
						pojo.setName(object[2].toString());
					}

					if (object[3] != null)
					{
						pojo.setDocterName(object[3].toString());
					}

					if (object[4] != null)
					{
						pojo.setPurposeOfVisit(object[4].toString());
					}

					if (object[5] != null)
					{
						pojo.setMobileNo(object[5].toString());
					}

					if (object[6] != null)
					{
						pojo.setEmailId(object[6].toString());
					}

					if (object[7] != null)
					{
						pojo.setInsertDate(object[7].toString());
					}
					if (object[10] != null)
					{
						pojo.setPatType(object[10].toString());
					}
					boolean updated = updatePatInfoTable(connection, Integer.parseInt(object[0].toString()));
					System.out.println("Column Updated or not>>>" + updated);
					if (updated)
					{
						pojoList.add(pojo);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public boolean updatePatInfoTable(SessionFactory connection, int id)
	{
		boolean update = false;
		try
		{
			parameterClause.put("smsFlag", "3");
			parameterClause.put("sms_date", DateUtil.getCurrentDateUSFormat());

			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			condtnBlock.put("id", id);
			update = new HelpdeskUniversalHelper().updateTableColomn("patientinfo", parameterClause, condtnBlock, connection);
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in method updatePatInfoTable of class " + getClass(), e);
			e.printStackTrace();
		}
		return update;
	}
}