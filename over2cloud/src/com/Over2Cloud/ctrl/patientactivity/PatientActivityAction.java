package com.Over2Cloud.ctrl.patientactivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.RandomStringGenerator;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PatientActivityAction extends ActionSupport implements ServletRequestAware
{
	// Ankit 26-05-2015
	Map session = ActionContext.getContext().getSession();
	// String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	// String empId = session.get("empIdofuser").toString().split("-")[1];
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private JSONArray dataArray = new JSONArray();
	String currDate = null;
	String searchondate = null;
	String counter;
	private String traceid;

	// feedbackform work merged
	private File report;
	private String reportFileName;
	private String reportContentType;
	private FileInputStream fis;
	private String pname, mobile, mobno, ot, cotp;
	private String page, type;
	private String psex, main;
	private String poccupation;
	private Map<String, String> profilemap;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	HttpServletRequest request;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String id;
	private String date;
	private String offeringId;
	private String setNo;
	private List<PatientActivityPojo> questionList;

	private String pageNo;
	private String done;
	private String positionPage;
	private Map<String, String> answerMap;
	private String form_name;

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String backFormView()
	{
		// System.out.println("backFormView >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		if (this.getTraceid() != null && !this.getTraceid().equals("-1") && !this.getTraceid().equalsIgnoreCase("undefined"))
		{
			try
			{
				String email = null;
				Random r = new Random();
				String token = Long.toString(Math.abs(r.nextLong()), 36);

				String ch1 = token.substring(0, 6);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				CommunicationHelper COH = new CommunicationHelper();
				AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory connection = Ob1.getSession();
				// System.out.println(connection.openSession().isConnected());
				StringBuilder query = new StringBuilder(" select ffa.questionId,ffa.answer from feedback_form_answers as ffa ");
				query.append("where ffa.empId='" + traceid + "'");
				List data = cbt.executeAllSelectQuery(query.toString(), connection);
				//System.out.println("Query  :::" + query);
				if (data != null && data.size() > 0)
				{
					answerMap = new HashMap<String, String>();
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obj = (Object[]) it.next();
						// answerMap.put(obj[0].toString(),obj[1].toString().replace(",",""));
						answerMap.put(obj[0].toString(), obj[1].toString());
					}
				}
				// System.out.println(answerMap);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return SUCCESS;

	}

	// Review only questionnaire forms
	// Review only questionnaire forms
	public String viewQuestionnaireForms()
	{
		String returnValue = ERROR;
		if (true)
		{
			try
			{
				// System.out.println(type+" reviewQuestionnaireForms>>>>>>>>>>>>>>>> "+pageNo);
				// System.out.println("done "+ done);
				// System.out.println("pname "+ pname);
				// System.out.println("positionPage "+positionPage);
				if (pageNo.equalsIgnoreCase("1"))
				{
					return "page1";
				} else if (pageNo.trim().equalsIgnoreCase("2"))
				{
					return "page2";
				} else if (pageNo.equalsIgnoreCase("3"))
				{
					return "page3";
				} else if (pageNo.equalsIgnoreCase("4"))
				{
					return "page4";
				} else if (pageNo.equalsIgnoreCase("5"))
				{
					return "page5";
				} else if (pageNo.equalsIgnoreCase("6"))
				{
					return "page6";
				} else if (pageNo.equalsIgnoreCase("7"))
				{
					return "page7";
				} else if (pageNo.equalsIgnoreCase("8"))
				{
					return "page8";
				} else if (pageNo.equalsIgnoreCase("9"))
				{
					return "page9";
				} else if (pageNo.equalsIgnoreCase("10"))
				{
					return "page10";
				} else if (pageNo.equalsIgnoreCase("11"))
				{
					return "page11";
				} else if (pageNo.equalsIgnoreCase("12"))
				{

					if (traceid != null && !traceid.equalsIgnoreCase("undefined"))
					{
						String gender = "Male";
						if (type.equalsIgnoreCase("F"))
						{
							gender = "Female";
						}
						SessionFactory connection = DBDynamicConnection.getSession("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
						List data = coi.executeAllSelectQuery("select form_count from patient_basic_data where id='" + traceid + "'  and gender='" + gender + "'", connection);
						if (data != null)
						{
							String count = (String) data.get(0);
							Map<String, String> pageMap = new LinkedHashMap<String, String>();
							pageMap.put("p1,", "1");
							pageMap.put("p2,", "2");
							pageMap.put("p21,", "3");
							pageMap.put("p3,", "4");
							pageMap.put("p4,", "5");
							pageMap.put("p51,", "6");
							pageMap.put("p52,", "7");
							pageMap.put("p9,", "9");
							String[] mandatory = { "p1,", "p2,", "p21,", "p3,", "p4,", "p51,", "p52,", "p9," };
							for (int i = 0; i < mandatory.length; i++)
							{
								if (count.contains(mandatory[i]))
								{
									page = "9";
									// System.out.println( count +
									// " ok "+mandatory[i]);
								} else
								{
									if (type.equalsIgnoreCase("F") && mandatory[i].equalsIgnoreCase("p9,"))
									{
										form_name = "10";
									} else
									{
										form_name = pageMap.get(mandatory[i]);
									}
									page = "8";
									// System.out.println( count +
									// " Break "+mandatory[i]);
									break;
								}
							}

							// System.out.println(pageNo+">>>>>>>>>>>>>  sataus>>>>>>>>>>>  "+count);
						}
					}
					return "page12";
				} else if (pageNo.equalsIgnoreCase("main"))
				{
					return "main";
				} else
				{
					return SUCCESS;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnValue = LOGIN;
		}

		return SUCCESS;
	}

	public String mainportal()
	{

		if (this.getTraceid() != null && !this.getTraceid().equals("-1") && !this.getTraceid().equalsIgnoreCase("undefined"))
		{
			//System.out.println("Method Invoked...........");
			try
			{
				//System.out.println(ot);
				String email = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				CommunicationHelper COH = new CommunicationHelper();
				AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory connection = Ob1.getSession();
				// System.out.println(connection.openSession().isConnected());
				String query = "SELECT first_name,last_name,mobile,email,gender FROM patient_basic_data  where id='" + traceid + "' and first_name !='NA' and first_name is not null";
				List data = cbt.executeAllSelectQuery(query, connection);

				if (data != null && data.size() > 0)
				{
					Object[] ob = (Object[]) data.get(0);

					if (ob[0] != null && !ob[0].toString().equals(""))
					{
						pname = ob[0].toString();
						if (ob[0 + 1].toString() != null && !ob[0 + 1].toString().equals(""))
						{
							pname = pname + " " + ob[0 + 1].toString();
						}
					} else
					{
						pname = "NA";
					}
					if (ob[2] == null)
					{
						mobile = "NA";
						mobno = "NA";
					} else
					{
						mobile = Cryptography.encrypt(ob[1].toString(), DES_ENCRYPTION_KEY);
						mobno = ob[2].toString();
					}
					if (ob[3] == null)
					{
						email = "NA";
					} else
					{
						email = ob[3].toString();
					}
					if (ob[4] == null)
					{
						type = "NA";
					} else
					{
						if (ob[4].toString().equalsIgnoreCase("male"))
						{
							type = "M";
						} else
						{
							type = "F";
						}

					}

				}
				boolean status = false;
				// ot=Cryptography.encrypt(ch1,DES_ENCRYPTION_KEY);

				return SUCCESS;

			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return ERROR;
		}
	}

	public String beforePatientFeedback()
	{

		if (this.getTraceid() != null && !this.getTraceid().equals("-1") && !this.getTraceid().equalsIgnoreCase("undefined"))
		{
			//System.out.println("Method Invoked...........");
			try
			{
				String email = null;
				Random r = new Random();
				String token = Long.toString(Math.abs(r.nextLong()), 36);

				String ch1 = token.substring(0, 6);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				CommunicationHelper COH = new CommunicationHelper();
				AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory connection = Ob1.getSession();
				// System.out.println(connection.openSession().isConnected());
				String query = "SELECT first_name,last_name,mobile,email,gender FROM patient_basic_data  where id='" + traceid + "' and first_name !='NA' and first_name is not null";
				List data = cbt.executeAllSelectQuery(query, connection);

				if (data != null && data.size() > 0)
				{
					Object[] ob = (Object[]) data.get(0);

					if (ob[0] != null && !ob[0].toString().equals(""))
					{
						pname = ob[0].toString();
						if (ob[0 + 1].toString() != null && !ob[0 + 1].toString().equals(""))
						{
							pname = pname + " " + ob[0 + 1].toString();
						}
					} else
					{
						pname = "NA";
					}
					if (ob[2] == null)
					{
						mobile = "NA";
						mobno = "NA";
					} else
					{
						mobile = Cryptography.encrypt(ob[1].toString(), DES_ENCRYPTION_KEY);
						mobno = ob[2].toString();
					}
					if (ob[3] == null)
					{
						email = "NA";
					} else
					{
						email = ob[3].toString();
					}
					if (ob[4] == null)
					{
						type = "NA";
					} else
					{
						if (ob[4].toString().equalsIgnoreCase("male"))
						{
							type = "M";
						} else
						{
							type = "F";
						}

					}

				}
				boolean status = false;
				// ot=Cryptography.encrypt(ch1,DES_ENCRYPTION_KEY);
				ot = ch1;

				String report_sms = "Dear " + pname + ", your OTP is: " + ch1 + ". Please use the same to fill your Wellness Questionnaire. Thanks.";
				String text = this.mailtext(pname, ch1);
				if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
				{
					status = COH.addMessage(pname, " ", mobno, report_sms, "OTP Generation for Wellness Questionnaire", "Pending", "0", "COM", connection);
				}

				if (email != null && email != "" && email != "NA")
				{
					status = COH.addMail(pname, "", email, "OTP Generation for Wellness Questionnaire", text, "Report", "Pending", "0", "", "WFPM", connection);
				}
				if (status)
				{
					return SUCCESS;
				} else
				{
					return ERROR;
				}

			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return ERROR;
		}
	}

	public String resendOTP()
	{
		if (this.getTraceid() != null && !this.getTraceid().equals("-1") && !this.getTraceid().equalsIgnoreCase("undefined"))
		{
			try
			{
				String email = null;
				Random r = new Random();
				String token = Long.toString(Math.abs(r.nextLong()), 36);

				String ch1 = token.substring(0, 6);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				CommunicationHelper COH = new CommunicationHelper();
				AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory connection = Ob1.getSession();
				// System.out.println(connection.openSession().isConnected());
				String query = "SELECT first_name,last_name,mobile,email,gender FROM patient_basic_data  where id='" + traceid + "' and first_name !='NA' and first_name is not null";
				List data = cbt.executeAllSelectQuery(query, connection);

				if (data != null && data.size() > 0)
				{
					Object[] ob = (Object[]) data.get(0);

					if (ob[0] != null && !ob[0].toString().equals(""))
					{
						pname = ob[0].toString();
						if (ob[0 + 1].toString() != null && !ob[0 + 1].toString().equals(""))
						{
							pname = pname + " " + ob[0 + 1].toString();
						}
					} else
					{
						pname = "NA";
					}
					if (ob[2] == null)
					{
						mobile = "NA";
						mobno = "NA";
					} else
					{
						mobile = Cryptography.encrypt(ob[1].toString(), DES_ENCRYPTION_KEY);
						mobno = ob[2].toString();
					}
					if (ob[3] == null)
					{
						email = "NA";
					} else
					{
						email = ob[3].toString();
					}
					if (ob[4] == null)
					{
						type = "NA";
					} else
					{
						if (ob[4].toString().equalsIgnoreCase("male"))
						{
							type = "M";
						} else
						{
							type = "F";
						}

					}

				}
				boolean status = false;
				// ot=Cryptography.encrypt(ch1,DES_ENCRYPTION_KEY);
				ot = ch1;
				String report_sms = "Dear " + pname + ", your OTP is: " + ch1 + ". Please use the same to fill your Wellness Questionnaire. Thanks.";
				String text = this.mailtext(pname, ch1);
				if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
				{
					status = COH.addMessage(pname, " ", mobno, report_sms, "OTP Generation for Wellness Questionnaire", "Pending", "0", "COM", connection);
				}

				if (email != null && email != "" && email != "NA")
				{
					status = COH.addMail(pname, "", email, "OTP Generation for Wellness Questionnaire", text, "Report", "Pending", "0", "", "WFPM", connection);
				}
				if (status)
				{
					return SUCCESS;
				} else
				{
					return ERROR;
				}
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return ERROR;
		}
	}

	public String mailtext(String empname, String password)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(empname) + ",</b>");
		mailtext.append("<br><br>Your OTP is: " + password + ". Please use the same to fill your Wellness Questionnaire.");
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>--------------<br>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public String mailtext1(String empname)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(empname) + ",</b><br><br>Thanks for giving your valuable time to fill your Wellness Questionnaire. We will get back to you shortly.");
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>--------------<br>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public String mailtext2(String doc, String empname, String gender, String mobno, List<PatientDashboardPojo> dataList, SessionFactory connection)
	{
		StringBuilder mailtext = new StringBuilder("");
		try
		{
			mailtext.append("<b>Dear " + DateUtil.makeTitle(doc) + ",</b><br><br>Patient Name: " + empname + ", Gender: " + gender + ", Mobile No.: " + mobno + " has successfully filled the Wellness Questionnaire.");
			mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b style='margin-left: -102px;' >Wellness Questionnaire Status For " + empname + " On " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTimeHourMin() + "</b></td></tr></tbody></table>");
			mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='70%' align='center'><tbody>");

			if (dataList != null && dataList.size() > 0)
			{
				List<PatientDashboardPojo> data = null;
				for (PatientDashboardPojo pojoObject : dataList)
				{
					mailtext.append("<th bgcolor='#8db7d6' colspan='3'>");
					mailtext.append("<b style='margin-left: -112px;'>" + pojoObject.getSections() + "</b>");
					mailtext.append("</th>");

					mailtext.append("<tr  bgcolor='#ffffff'>");
					mailtext.append("<td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Sr No.</b></td>");
					mailtext.append("<td align='center' width='40%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Questions</b></td>");
					mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Answers</b></td>");
					mailtext.append("</tr>");
					if (pojoObject.getSections() != null && pojoObject.getId() != null && pojoObject.getQuesset() != null)
					{
						data = this.setQuestionaireSection(connection, pojoObject.getSections(), pojoObject.getId(), pojoObject.getQuesset());
					}
					if (data != null && data.size() > 0)
					{
						int i = 0;
						for (PatientDashboardPojo pojo : data)
						{
							int k = ++i;
							if (k % 2 != 0)
							{
								mailtext.append("<tr  bgcolor='#e8e7e8'>");
								// data1=this.getSubQuestionaire(connection,pojoObject.getQuesid());

								/*
								 * if(data1!=null && data1.size()>1) {
								 */
								if (!pojo.getQuestion().equalsIgnoreCase("Relation") && !pojo.getQuestion().equalsIgnoreCase("Duration") && !pojo.getQuestion().equalsIgnoreCase("Mention Value if any"))
								{
									mailtext.append("<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getSno() + "</td>");
									mailtext.append("<td align='left' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getQuestion() + "</td>");
									/* } */
									if (pojo.getSubanswers() != null && !pojo.getSubanswers().equalsIgnoreCase("") && pojo.getAnswers() != null && !pojo.getAnswers().equalsIgnoreCase("") && !pojo.getAnswers().equalsIgnoreCase("No") && !pojo.getAnswers().equalsIgnoreCase("Do not Know"))
									{
										/*
										 * if(pojo.getSubquestion()!=null &&
										 * !pojo
										 * .getSubquestion().equalsIgnoreCase
										 * ("") &&
										 * pojo.getSubquestion().equalsIgnoreCase
										 * ("Mention Value if any")) {
										 */
										mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getAnswers() + ": " + pojo.getSubanswers() + "</td></tr>");
										/*
										 * } else {
										 * if(pojo.getSubanswers()!=null &&
										 * !pojo
										 * .getSubanswers().equalsIgnoreCase("")
										 * && pojo.getAnswers()!=null &&
										 * !pojo.getAnswers
										 * ().equalsIgnoreCase("") &&
										 * !pojo.getAnswers
										 * ().equalsIgnoreCase("No") &&
										 * !pojo.getAnswers
										 * ().equalsIgnoreCase("Donot Know")) {
										 * mailtext.append(
										 * "<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
										 * +
										 * pojo.getAnswers()+": "+pojo.getSubanswers
										 * ()+"</td></tr>"); } }
										 */
									} else
									{
										mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getAnswers() + "</td></tr>");
									}
								}
							} else
							{
								mailtext.append("<tr  bgcolor='#ffffff'>");
								if (!pojo.getQuestion().equalsIgnoreCase("Relation") && !pojo.getQuestion().equalsIgnoreCase("Duration") && !pojo.getQuestion().equalsIgnoreCase("Mention Value if any"))
								{
									mailtext.append("<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getSno() + "</td>");
									mailtext.append("<td align='left' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getQuestion() + "</td>");
									if (pojo.getSubanswers() != null && !pojo.getSubanswers().equalsIgnoreCase("") && pojo.getAnswers() != null && !pojo.getAnswers().equalsIgnoreCase("") && !pojo.getAnswers().equalsIgnoreCase("No") && !pojo.getAnswers().equalsIgnoreCase("Donot Know"))
									{
										mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getAnswers() + ": " + pojo.getSubanswers() + "</td></tr>");
									} else
									{
										mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojo.getAnswers() + "</td></tr>");
									}
								}

							}
						}
					}
					mailtext.append("<tr  bgcolor='#ffffff'style='HEIGHT: 27PX;'></tr>");

				}
				mailtext.append("</tbody></table>");
			}

			/*
			 * if(dataList!=null && dataList.size()>0) { int i=0;
			 * for(PatientDashboardPojo pojoObject : dataList) { int k = ++i; if
			 * (k % 2 != 0) { mailtext.append("<tr  bgcolor='#e8e7e8'>");
			 * mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getSno()+"</td>"); mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getQuestion()+"</td>"); mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getAnswers()+"</td></tr>"); } else {
			 * mailtext.append("<tr  bgcolor='#ffffff'>"); mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getSno()+"</td>"); mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getQuestion()+"</td>"); mailtext.append(
			 * "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			 * + pojoObject.getAnswers()+"</td></tr>"); }
			 * 
			 * } mailtext.append("</tbody></table>"); }
			 */
			mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>--------------<br>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailtext.toString();
	}

	/*
	 * mailtext.append(
	 * "<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>"
	 * ); return mailtext.toString(); }
	 */public String patientFeedback()
	{
		// String cotp=null;
		done = "0";
		positionPage = "1";
		// System.out.println("type "+type);
		// System.out.println("traceid "+traceid);
		// System.out.println("done "+done);
		// System.out.println("positionPage "+positionPage);
		// System.out.println("Method Invoked     ........");
		if (this.getTraceid() != null && !this.getTraceid().equals("-1") && !this.getTraceid().equalsIgnoreCase("undefined"))
		{
			// System.out.println("Test 1>><<<>>");
			try
			{
				// System.out.println(request.getParameter("otp1"));
				// cotp=Cryptography.decrypt(request.getParameter("otp1"),DES_ENCRYPTION_KEY);
				cotp = request.getParameter("otp1");
				// System.out.println(cotp);
				ot = request.getParameter("otp");
				// System.out.println(ot);
				// System.out.println(traceid);
				pname = request.getParameter("pname");

				if (cotp.equalsIgnoreCase(ot))
				// if(true)
				{

					// System.out.println("Test 2>>>>");
					questionList = new ArrayList<PatientActivityPojo>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory connection = Ob1.getSession();
					String query = "SELECT id,first_name,last_name,age,gender FROM patient_basic_data  where id='" + traceid + "' and first_name!='NA' and first_name is not null";
					List data = cbt.executeAllSelectQuery(query, connection);

					if (data != null && data.size() > 0)
					{
						Object[] ob = (Object[]) data.get(0);
						if (ob[1] != null && !ob[1].toString().equals(""))
						{
							pname = ob[1].toString();
							if (ob[1 + 1].toString() != null && !ob[1 + 1].toString().equals(""))
							{
								pname = pname + " " + ob[1 + 1].toString();
							}
						} else
						{
							pname = "NA";
						}
						if (ob[3] == null)
						{
							page = "NA";
						} else
						{
							page = ob[3].toString();
						}
						if (ob[4] == null)
						{
							psex = "NA";
						} else
						{
							psex = ob[4].toString();
						}
						/*
						 * if (ob[4] == null) { poccupation = "NA"; } else {
						 * poccupation = ob[4].toString(); }
						 */
					}
					String queryConf = "SELECT * FROM questionsconfig WHERE questionSet='" + setNo + "'";

					List confData = cbt.executeAllSelectQuery(queryConf, connection);
					if (confData != null && confData.size() > 0)
					{
						PatientActivityPojo pj = null;
						PatientActivityPojo pj1 = null;
						for (Iterator iterator = confData.iterator(); iterator.hasNext();)
						{
							List<String> optionList = new ArrayList<String>();
							pj = new PatientActivityPojo();
							Object[] object = (Object[]) iterator.next();
							pj.setQuestionId(object[0].toString());
							pj.setQuestionSet(object[1].toString());
							pj.setQuestions(object[2].toString());
							pj.setAnswers(object[3].toString());
							if (object[4] != null)
							{
								if (object[4].toString().equalsIgnoreCase("Yes"))
								{
									pj.setUpload(true);
								} else
								{
									pj.setUpload(false);
								}
							} else
							{
								pj.setUpload(false);
							}
							if (object[5] != null)
							{
								if (object[5].toString().equalsIgnoreCase("Yes"))
								{
									pj.setMandatory(true);
								} else
								{
									pj.setMandatory(false);
								}
							} else
							{
								pj.setMandatory(false);
							}
							if (object[6] != null)
							{
								if (object[6].toString().equalsIgnoreCase("Yes"))
								{
									List<PatientActivityPojo> test = new ArrayList<PatientActivityPojo>();
									pj.setSubquestion(true);
									String subQuery = "SELECT * FROM questionssubconfig WHERE questionId =" + object[0].toString();
									//System.out.println("Sub  QUery ::::: " + subQuery);
									List subQuestion = cbt.executeAllSelectQuery(subQuery, connection);
									if (subQuestion != null && subQuestion.size() > 0)
									{
										for (Iterator iterator2 = subQuestion.iterator(); iterator2.hasNext();)
										{
											List<String> suboptionList = new ArrayList<String>();
											pj1 = new PatientActivityPojo();
											Object[] object2 = (Object[]) iterator2.next();
											pj1.setSubquestionId(object2[0].toString());
											pj1.setSubquestions(object2[2].toString());
											pj1.setSubanswers(object2[3].toString());
											if (object2[4] != null)
											{
												if (object2[4].toString().equalsIgnoreCase("Yes"))
												{
													pj1.setSubupload(true);
												} else
												{
													pj1.setSubupload(false);
												}
											} else
											{
												pj1.setSubupload(false);
											}
											if (object2[5] != null)
											{
												if (object2[5].toString().equalsIgnoreCase("Yes"))
												{
													pj1.setSubmandatory(true);
												} else
												{
													pj1.setSubmandatory(false);
												}
											} else
											{
												pj1.setSubmandatory(false);
											}
											if (object2[6] != null)
											{
												String[] str = object2[6].toString().split(",");
												if (str != null)
												{
													for (int i = 0; i < str.length; i++)
													{
														if (!str[i].equalsIgnoreCase(" "))
														{
															suboptionList.add(str[i]);
														}
													}
												}
												pj1.setSuboptionsList(suboptionList);
											}
											test.add(pj1);
											pj.setSubQuestion(test);
										}
									}
								} else
								{
									pj.setSubquestion(false);
								}
							} else
							{
								pj.setSubquestion(false);
							}
							if (object[7] != null)
							{
								String[] str = object[7].toString().split(",");
								if (str != null)
								{
									for (int i = 0; i < str.length; i++)
									{
										if (!str[i].equalsIgnoreCase(" "))
										{
											optionList.add(str[i]);
										}
									}
								}
								pj.setOptionsList(optionList);
							}
							questionList.add(pj);
						}
					}
					return SUCCESS;
				} else
				{
					addActionError("Please Enter Valid OTP !!!");
					return ERROR;
				}
			} catch (Exception e)
			{
				addActionError("Please Enter Valid OTP !!!");
				return ERROR;
			}
		} else
		{
			addActionError("Please Enter Valid OTP !!!");
			return ERROR;
		}
	}

	public String viewHeader()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				// getClientPieChartData();
				returnValue = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String fetchPatientColList()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();

		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_take_action_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_take_action_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				StringBuilder sb = new StringBuilder("");
				JSONObject colHead = new JSONObject();
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("comment") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("isFinished") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("readFlag") && !gdp.getColomnName().equalsIgnoreCase("closing_comment") && !gdp.getColomnName().equalsIgnoreCase("clientName"))
					{
						sb.append(gdp.getHeadingName() + ",");
					}
				}
				// System.out.println("sb>>>"+sb.toString());
				colHead.put("headValue", sb.substring(0, sb.lastIndexOf(",")));
				dataArray.add(colHead);
				currDate = DateUtil.getCurrentDateIndianFormat();
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}

	public String fetchDataActivity()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");
		String month = null;
		boolean check = false;
		if (searchondate != null && !searchondate.equalsIgnoreCase("undefined") && !searchondate.equalsIgnoreCase("null"))
		{
			month = DateUtil.convertDateToUSFormat(searchondate);
			check = true;
		} else if (counter != null && !counter.equalsIgnoreCase("undefined") && !counter.equalsIgnoreCase("null") && !counter.equalsIgnoreCase("0"))
		{
			month = DateUtil.getDateAfterDays(Integer.parseInt(counter.trim()));
			check = true;
		}

		sb.append(" select pta.id,ctd.personName,emp.empName,off.subofferingname,ptd.status,SUBSTRING_INDEX(pta.maxDateTime,' ',-1) " + " from patient_take_action as pta ");
		sb.append(" left join patientcrm_status_data as psd on psd.id = pta.offeringId");
		sb.append(" left join offeringlevel3 as off on off.id = psd.offering_name");
		sb.append(" left join patientcrm_status_data as ptd on ptd.id = pta.statusId ");
		sb.append(" left join client_contact_data as ctd on ctd.id = pta.contactId ");
		sb.append(" left join employee_basic as emp on emp.id = pta.client_contact_id ");
		if (check)
		{
			sb.append(" where pta.maxDateTime like '" + month + "%' order by pta.maxDateTime ");
		} else
			sb.append(" where pta.maxDateTime like '" + DateUtil.getCurrentDateUSFormat() + "%' order by pta.maxDateTime ");

		//System.out.println(">>> " + sb.toString());
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
		{
			Object obj = iterator.next();
			Object data[] = (Object[]) obj;
			jsonObject = new JSONObject();
			StringBuilder sb2 = new StringBuilder();

			for (int i = 0; i < data.length; i++)
			{
				if (data[i] != null && !data[i].toString().equals(""))
				{
					sb2.append(data[i].toString());
					sb2.append(",");
				} else
				{
					sb2.append("NA,");
				}
			}
			jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
		}
		this.currDate = DateUtil.convertDateToIndianFormat(month != null ? month : DateUtil.getCurrentDateUSFormat());
		return SUCCESS;
	}

	public String submitFeedback()
	{
		try
		{
			AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
			AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
			SessionFactory connection = Ob1.getSession();
			boolean status = false;

			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());

			// int
			// ir=coi.executeAllUpdateQuery("insert into history_feedback_form_answers select * from feedback_form_answers as f1 where f1.empId='"+
			// request.getParameter("traceid").toString() +"'", connection);
			// System.out.println("record addeded into hsitory >>>>>> ");

			// coi.executeAllUpdateQuery("  delete from feedback_form_answers where empId='"+
			// request.getParameter("traceid").toString() +"'", connection);

			// System.out.println("<delete from feedback_form_answers where empId='"+
			// request.getParameter("traceid").toString()
			// +"'>deleting record... at empid : "+
			// request.getParameter("traceid").toString());
			String flag = new RandomStringGenerator().generateRandomString((short) 'R', 6);

			if (requestParameterNames != null && requestParameterNames.size() > 0)
			{
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					// System.out.println("parmName " + parmName +
					// "     paramValue  " + paramValue);

					if (!parmName.contains("__multiselect") && !parmName.contains("traceid"))
					{
						ob = new InsertDataTable();
						ob.setColName(parmName);
						String[] values = request.getParameterValues(parmName);
						// System.out.println("values::: "+values.toString());
						if (values != null && !values.toString().equalsIgnoreCase(""))
						{
							String vals = new String("");
							for (String s : values)
							{
								vals += s + ",";
							}
							ob.setDataName(vals);
						}
						insertData.add(ob);
					}
				}
				List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
				InsertDataTable ob1 = null;

				ob1 = new InsertDataTable();
				ob1.setColName("date");
				ob1.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData1.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("time");
				ob1.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData1.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("empId");
				ob1.setDataName(request.getParameter("traceid").toString());
				insertData1.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("flag");
				ob1.setDataName(flag);
				insertData1.add(ob1);

				if (reportFileName != null)
				{
					String renameFilePath = new CreateFolderOs().createUserDir("feedbackReport") + "//" + DateUtil.mergeDateTime() + getReportFileName();
					String storeFilePath = new CreateFolderOs().createUserDir("feedbackReports") + "//" + getReportFileName();
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath != null)
					{
						ob1 = new InsertDataTable();
						File theFile = new File(storeFilePath);
						File newFileName = new File(str);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(report, theFile);
								if (theFile.exists())
									theFile.renameTo(newFileName);
							} catch (IOException e)
							{
								e.printStackTrace();
							}
							ob1.setColName("report");
							ob1.setDataName(renameFilePath);
							insertData1.add(ob1);
						}
					}
				}

				for (InsertDataTable insertDataTable : insertData)
				{
					// System.out.println("----------------------------------------------------------------------------------");
					// System.out.println("colname ::: "+insertDataTable.getColName());
					// System.out.println("Dataname ::: "+insertDataTable.getDataName());
					if (!insertDataTable.getColName().equalsIgnoreCase("questionId") && !insertDataTable.getColName().equalsIgnoreCase("report") && !insertDataTable.getColName().equalsIgnoreCase("subquestionId"))
					{
						if (insertDataTable.getColName().toString().indexOf("#") > 0)
						{
							ob1 = new InsertDataTable();
							ob1.setColName("questionId");
							ob1.setDataName(insertDataTable.getColName().toString().split("#")[0].trim());
							insertData1.add(ob1);

							ob1 = new InsertDataTable();
							ob1.setColName("subQuestionId");
							ob1.setDataName(insertDataTable.getColName().toString().split("#")[1].trim());
							insertData1.add(ob1);

							ob1 = new InsertDataTable();
							ob1.setColName("answer");
							ob1.setDataName(insertDataTable.getDataName().toString().trim());
							insertData1.add(ob1);
						} else
						{
							ob1 = new InsertDataTable();
							ob1.setColName("questionId");
							ob1.setDataName(insertDataTable.getColName().toString().trim());
							insertData1.add(ob1);

							ob1 = new InsertDataTable();
							ob1.setColName("subQuestionId");
							ob1.setDataName("NA");
							insertData1.add(ob1);

							ob1 = new InsertDataTable();
							ob1.setColName("answer");
							ob1.setDataName(insertDataTable.getDataName().toString().trim());
							insertData1.add(ob1);

						}
						status = coi.insertIntoTable("feedback_form_answers", insertData1, connection);
						insertData1.remove(insertData1.size() - 1);
						insertData1.remove(insertData1.size() - 1);
						insertData1.remove(insertData1.size() - 1);
					}
				}
			}
			if (status)
			{
				addActionMessage("Successfully Saved.");
				Conn = null;
				Ob1 = null;
				connection = null;
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String submitFeedbackNew()
	{
		try
		{
			boolean status = false;
			int maxId = 0;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			boolean insertBool = false;
			// AllConnection Conn = new ConnectionFactory("2_clouddb",
			// "127.0.0.1", "dreamsol", "dreamsol", "3306");
			// AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
			// SessionFactory connection = Ob1.getSession();

			if (request.getParameter("traceid") != null && !request.getParameter("traceid").trim().equalsIgnoreCase(""))
				insertBool = true;
			SessionFactory connection = DBDynamicConnection.getSession("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
			String flag = new RandomStringGenerator().generateRandomString((short) 'R', 6);

			if (requestParameterNames != null && requestParameterNames.size() > 0)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					String parmName = it.next().toString();
					// System.out.println("parmNameparmNameparmName "+
					// parmName);
					if (!parmName.contains("__multiselect") && !parmName.contains("checkbox") && !request.getParameter("traceid").trim().equalsIgnoreCase("") && !parmName.contains("traceid") && !parmName.contains("setNo") && !parmName.contains("nop") && !parmName.contains("update") && !parmName.contains("type") && !parmName.contains("doneStatus") && !parmName.contains("positionPage") && !parmName.contains("pageNo") && !parmName.contains("done") && !parmName.contains("pname")
							&& !parmName.contains("page")

					)
					{
						Map<String, Object> insertData1 = new HashMap<String, Object>();
						Map<String, Object> whereData1 = new HashMap<String, Object>();
						String questionId = "";

						String paramValue = request.getParameter(parmName);
						// System.out.println("parmName " + parmName +
						// "     paramValue  " + paramValue);

						ob = new InsertDataTable();
						ob.setColName("questionId");
						ob.setDataName(parmName.trim());
						insertData1.put("questionId", parmName.trim());
						whereData1.put("questionId", parmName.trim());
						questionId = parmName.trim();
						insertData.add(ob);

						// ob = new InsertDataTable();
						// ob.setColName("answer");
						//

						String[] values = request.getParameterValues(parmName);
						if (values != null && !values.toString().equalsIgnoreCase(""))
						{
							// System.out.println("parmName " + parmName +
							// "paramValue  " +request.getParameter(parmName));
							ob = new InsertDataTable();
							ob.setColName("answer");
							String vals = new String("");
							for (String s : values)
							{
								vals += s + ", ";
							}

							ob.setDataName(vals);
							insertData.add(ob);
							insertData1.put("answer", vals);
						}

						// ob.setDataName(paramValue);
						// insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("question_status");
						ob.setDataName("1");
						insertData.add(ob);
						insertData1.put("question_status", "1");

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
						insertData1.put("date", DateUtil.getCurrentDateUSFormat());

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
						insertData1.put("time", DateUtil.getCurrentTimeHourMin());

						ob = new InsertDataTable();
						ob.setColName("empId");
						ob.setDataName(request.getParameter("traceid").toString().trim());
						insertData.add(ob);
						insertData1.put("empId", request.getParameter("traceid").toString().trim());
						whereData1.put("empId", request.getParameter("traceid").toString().trim());

						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName(flag);
						insertData.add(ob);
						insertData1.put("flag", flag);
						if (reportFileName != null)
						{
							String renameFilePath = new CreateFolderOs().createUserDir("feedbackReport") + "//" + DateUtil.mergeDateTime() + getReportFileName();
							String storeFilePath = new CreateFolderOs().createUserDir("feedbackReports") + "//" + getReportFileName();
							String str = renameFilePath.replace("//", "/");
							if (storeFilePath != null)
							{
								ob = new InsertDataTable();
								File theFile = new File(storeFilePath);
								File newFileName = new File(str);
								if (theFile != null)
								{
									try
									{
										FileUtils.copyFile(report, theFile);
										if (theFile.exists())
											theFile.renameTo(newFileName);
									} catch (IOException e)
									{
										e.printStackTrace();
									}
									ob.setColName("report");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
									insertData1.put("report", renameFilePath);
								}
							}
						}
						if (insertBool)
						{
							if ( request.getParameter("update") != null && request.getParameter("update").toString().equalsIgnoreCase("update"))
							{
								// System.out.println("update>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+
								// request.getParameter("update").toString());
								String querydel = "DELETE FROM feedback_form_answers WHERE empId=" + request.getParameter("traceid").toString().trim() + " AND questionId IN ('" + questionId.trim() + "')";
								cbt.executeAllUpdateQuery(querydel, connection);
								status = cbt.insertIntoTable("feedback_form_answers", insertData, connection);
								// System.out.println("ID1    "+ insertData1);
								// System.out.println("wD1     "+whereData1);
								// cbt.updateTableColomn("feedback_form_answers",
								// insertData1, whereData1, connection);

								// status=cbt.updateIntoTable("feedback_form_answers",insertData
								// ,
								// request.getParameter("traceid").toString().trim(),
								// connection);
							} else
							{
								String querydel = "DELETE FROM feedback_form_answers WHERE empId=" + request.getParameter("traceid").toString().trim() + " AND questionId IN ('" + questionId.trim() + "')";
								cbt.executeAllUpdateQuery(querydel, connection);
								status = cbt.insertIntoTable("feedback_form_answers", insertData, connection);
								
								//status = cbt.insertIntoTable("feedback_form_answers", insertData, connection);
								// System.out.println("sataus>>>>>>>>>>>"+status);
							}
						} else
						{
							// No data Insert for view only Feedback forms.
						}
					}
				}

				String gender = "Male";
				if (type.equalsIgnoreCase("F"))
				{
					gender = "Female";
				}
				List data1 = cbt.executeAllSelectQuery("select form_count from patient_basic_data where id='" + traceid + "' and gender='" + gender + "' ", connection);
				if (data1 != null)
				{
					String count = (String) data1.get(0);
					// /int num=Integer.parseInt(pageNo);
					// num+=1;
					// System.out.println(count+">>>>>>>>>>>>>>>>>>>>");
					if (page != null)
					{
						if (count.equalsIgnoreCase("0"))
						{
							count = page;
							cbt.executeAllUpdateQuery("update patient_basic_data set form_count='" + count + "' where id='" + traceid + "' and gender='" + gender + "' ", connection);

							// System.out.println("sataus>>>>>>>>>>>"+count);
						} else
						{
							if (count.contains(page))
							{

							} else
							{
								count = count + page;
								cbt.executeAllUpdateQuery("update patient_basic_data set form_count='" + count + "' where id='" + traceid + "' and gender='" + gender + "' ", connection);
								// System.out.println("sataus>>>>>>>>>>>"+count);
							}

						}
					}

				}

			}
			if (status)
			{
				// System.out.println(status);
				if (request.getParameter("nop") != null && request.getParameter("nop").equalsIgnoreCase("last"))
				{

					// System.out.println("Test");
					CommunicationHelper COH = new CommunicationHelper();
					CommonOperInterface cbt = new CommonConFactory().createInterface();

					StringBuilder query1 = new StringBuilder();
					query1.append("UPDATE feedback_form_answers SET question_status='2' WHERE empId IN(" + request.getParameter("traceid").toString() + ") and date= '" + DateUtil.getCurrentDateUSFormat() + "'");
					cbt.updateTableColomn(connection, query1);

					// System.out.println("Test 1111");
					// mobno="9999174430";
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					wherClause.put("question_status", "2");
					condtnBlock.put("id", maxId);
					cbt.updateTableColomn("feedback_form_answers", wherClause, condtnBlock, connection);
					String pemail = null, pmob = null;
					String query = "SELECT first_name,last_name,mobile,email,gender FROM patient_basic_data  where id='" + traceid + "' and first_name!='NA' and first_name is not null";
					// System.out.println("Test 2222222");
					List data = cbt.executeAllSelectQuery(query, connection);

					if (data != null && data.size() > 0)
					{
						Object[] ob = (Object[]) data.get(0);
						if (ob[0] != null && !ob[0].toString().equals(""))
						{
							pname = ob[0].toString();
							if (ob[0 + 1].toString() != null && !ob[0 + 1].toString().equals(""))
							{
								pname = pname + " " + ob[0 + 1].toString();
							}
						} else
						{
							pname = "NA";
						}
						if (ob[2] == null)
						{
							pmob = "NA";
						} else
						{
							pmob = ob[2].toString();
						}
						if (ob[3] == null)
						{
							pemail = "NA";
						} else
						{
							pemail = ob[3].toString();
						}
						if (ob[4] == null)
						{
							type = "NA";
						} else
						{
							type = ob[4].toString();
						}
					}

					// Patient Conformation Alert
					String text = this.mailtext1(pname);
					// System.out.println("Test 333333");
					String report_sms = "Dear " + pname + ", thanks for giving your valuable time to fill your Wellness Questionnaire. We will get back to you shortly. Thanks.";
					// System.out.println("Test 444444");
					if (pmob != null && pmob != "" && pmob != "NA" && pmob.length() == 10 && (pmob.startsWith("9") || pmob.startsWith("8") || pmob.startsWith("7")))
					{
						status = COH.addMessage(pname, " ", pmob, report_sms, "Questionnaire Completion Alert", "Pending", "0", "COM", connection);
						// System.out.println("Test 55555");
					}
					if (pemail != null && pemail != "" && pemail != "NA")
					{
						status = COH.addMail(pname, "", pemail, "Questionnaire Completion Alert", text, "Report", "Pending", "0", "", "WFPM", connection);
						// System.out.println("Test 666666");
					}
					// #####################################

					// Doctor Conformation Alert
					String email1 = "prabhatk@dreamsol.biz", mobno1 = "9250400315", email2 = "Rajiv.Sikka@medanta.org", email3 = "Vrinda.Sharma@medanta.org", email4 = "Saumit.Sehgal@medanta.org", mobno2 = "9871432975", mobno3 = "9711724603", mobno4 = "9811107347", drname1 = "Mr. Prabhat", drname2 = "Mr. Rajiv Sikka", drname3 = "Dr Vrinda Sharma", drname4 = "Mr Saumit Sehgal";
					String report_sms1 = "Dear  " + drname1 + " Patient Name:" + pname + ", Gender: " + type + ", Mobile No.: " + pmob + " has successfully filled the Wellness Questionnaire.";
					String report_sms2 = "Dear  " + drname2 + " Patient Name:" + pname + ", Gender: " + type + ", Mobile No.: " + pmob + " has successfully filled the Wellness Questionnaire.";
					String report_sms3 = "Dear  " + drname3 + " Patient Name:" + pname + ", Gender: " + type + ", Mobile No.: " + pmob + " has successfully filled the Wellness Questionnaire.";
					String report_sms4 = "Dear  " + drname4 + " Patient Name:" + pname + ", Gender: " + type + ", Mobile No.: " + pmob + " has successfully filled the Wellness Questionnaire.";
					List<PatientDashboardPojo> dataList = getData4PatientQuestionnaire(connection, traceid);
					// System.out.println("Test 777777777");
					String text0 = this.mailtext2(drname1, pname, type, pmob, dataList, connection);
					String text1 = this.mailtext2(drname2, pname, type, pmob, dataList, connection);
					String text2 = this.mailtext2(drname3, pname, type, pmob, dataList, connection);
					String text3 = this.mailtext2(drname4, pname, type, pmob, dataList, connection);
					// System.out.println("Test 888888888");
					if (mobno1 != null && mobno1 != "" && mobno1 != "NA" && mobno1.length() == 10 && (mobno1.startsWith("9") || mobno1.startsWith("8") || mobno1.startsWith("7")) && mobno2 != null && mobno2 != "" && mobno2 != "NA" && mobno2.length() == 10 && (mobno2.startsWith("9") || mobno2.startsWith("8") || mobno2.startsWith("7")) && mobno3 != null && mobno3 != "" && mobno3 != "NA" && mobno3.length() == 10
							&& (mobno3.startsWith("9") || mobno3.startsWith("8") || mobno3.startsWith("7")) && mobno4 != null && mobno4 != "" && mobno4 != "NA" && mobno4.length() == 10 && (mobno4.startsWith("9") || mobno4.startsWith("8") || mobno4.startsWith("7")))
					{
						status = COH.addMessage(drname1, " ", mobno1, report_sms1, "Questionnaire Completion Notification Alert", "Pending", "0", "COM", connection);
						status = COH.addMessage(drname2, " ", mobno2, report_sms2, "Questionnaire Completion Notification Alert", "Pending", "0", "COM", connection);
						status = COH.addMessage(drname3, " ", mobno3, report_sms3, "Questionnaire Completion Notification Alert", "Pending", "0", "COM", connection);
						status = COH.addMessage(drname4, " ", mobno4, report_sms4, "Questionnaire Completion Notification Alert", "Pending", "0", "COM", connection);
						// System.out.println("Test 99999999");
					}
					if (email1 != null && email1 != "" && email1 != "NA" && email2 != null && email2 != "" && email2 != "NA" && email3 != null && email3 != "" && email3 != "NA" && email4 != null && email4 != "" && email4 != "NA")
					{
						status = COH.addMail(drname1, "", email1, "Questionnaire Completion Notification Alert", text0, "Report", "Pending", "0", "", "WFPM", connection);
						status = COH.addMail(drname2, "", email2, "Questionnaire Completion Notification Alert", text1, "Report", "Pending", "0", "", "WFPM", connection);
						status = COH.addMail(drname3, "", email3, "Questionnaire Completion Notification Alert", text2, "Report", "Pending", "0", "", "WFPM", connection);
						status = COH.addMail(drname4, "", email4, "Questionnaire Completion Notification Alert", text3, "Report", "Pending", "0", "", "WFPM", connection);
						// System.out.println("Test 99999999");
					}
					COH = null;
					cbt = null;
				}
				// ########################################
				addActionMessage("Successfully Saved.");

				connection = null;
			}
			return viewQuestionnaireForms();
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

	}

	public String fetchMissedDataActivity()
	{

		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");
		sb.append(" select ctd.personName,emp.empName,off.subofferingname,ptd.status,SUBSTRING_INDEX(pta.maxDateTime,' ',1) as missedDateTime" + " from patient_take_action as pta ");
		sb.append(" left join patientcrm_status_data as psd on psd.id = pta.offeringId");
		sb.append(" left join offeringlevel3 as off on off.id = psd.offering_name");
		sb.append(" left join patientcrm_status_data as ptd on ptd.id = pta.statusId ");
		sb.append(" left join client_contact_data as ctd on ctd.id = pta.contactId ");
		sb.append(" left join employee_basic as emp on emp.id = pta.client_contact_id ");
		/*
		 * sb.append(
		 * " select contactId,client_contact_id,offeringId,statusId,SUBSTRING_INDEX(maxDateTime,' ',1) as missedDateTime"
		 * + " from patient_take_action ");
		 */
		sb.append(" where SUBSTRING_INDEX(pta.maxDateTime,' ',1) < '" + DateUtil.getCurrentDateUSFormat() + "' order by pta.maxDateTime ");
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
		{
			Object obj = iterator.next();
			Object data[] = (Object[]) obj;
			jsonObject = new JSONObject();
			StringBuilder sb2 = new StringBuilder();

			for (int i = 0; i < data.length; i++)
			{
				if (data[i] != null && !data[i].toString().equals(""))
				{
					if (i == 4)
					{
						sb2.append(DateUtil.convertDateToIndianFormat(data[i].toString().trim()));
						sb2.append(",");
					} else
					{
						sb2.append(data[i].toString());
						sb2.append(",");
					}
				} else
				{
					sb2.append("NA,");
				}
			}
			jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
		}
		return SUCCESS;
	}

	public String fetchHistoryData()
	{
		try
		{
			// System.out.println("CHKKKKKKKKKKKKKKKKKKKKKKKK "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			sb.append(" select ptd.status,emp.empName,off.subofferingname,SUBSTRING_INDEX(pta.maxDateTime,' ',1) as missedDateTime" + " from patient_take_action as pta ");
			sb.append(" left join patientcrm_status_data as psd on psd.id = pta.offeringId");
			sb.append(" left join offeringlevel3 as off on off.id = psd.offering_name");
			sb.append(" left join patientcrm_status_data as ptd on ptd.id = pta.statusId ");
			sb.append(" left join client_contact_data as ctd on ctd.id = pta.contactId ");
			sb.append(" left join employee_basic as emp on emp.id = pta.client_contact_id ");
			sb.append(" where pta.contactId=(select distinct contactId from patient_take_action where id=" + id + ") and  pta.maxDateTime <='" + DateUtil.convertDateToUSFormat(date) + "' order BY pta.id DESC ");
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				StringBuilder sb2 = new StringBuilder();

				for (int i = 0; i < data.length; i++)
				{
					if (data[i] != null && !data[i].toString().equals(""))
					{
						if (i == 3)
						{
							sb2.append(DateUtil.convertDateToIndianFormat(data[i].toString().trim()));
							sb2.append(",");
						} else
						{
							sb2.append(data[i].toString());
							sb2.append(",");
						}
					} else
					{
						sb2.append("NA,");
					}
				}
				jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchFeedbackData()
	{

		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		try
		{
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			sb.append(" select id,question " + " from feedback_form_questions ");
			/* System.out.println(">>>>>>>>>>>>>>>>> "+sb.toString()); */
			List qdataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			Map<String, String> mapdata = new LinkedHashMap<String, String>();

			for (Iterator iterator = qdataList.iterator(); iterator.hasNext();)
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				mapdata.put(data[0] != null ? data[0].toString() : "NA", data[1] != null ? data[1].toString() : "NA");
			}
			/* System.out.println(mapdata); */

			Set<String> qidSet = mapdata.keySet();
			StringBuilder sb2 = new StringBuilder("select ");
			for (Iterator iterator = qidSet.iterator(); iterator.hasNext();)
			{
				sb2.append(iterator.next().toString() + ",");
			}
			StringBuilder query = new StringBuilder("");
			query.append(sb2.substring(0, sb2.lastIndexOf(",")));
			query.append(" from feedback_form_answers where contactId=(select contactId from patient_basic_data where id=" + id + ") order by id desc limit 0,1 ");
		//	System.out.println("query.toString()" + query.toString());
			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);

			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				Object[] questions = (Object[]) mapdata.values().toArray();

				for (int i = 0; i < questions.length; i++)
				{
					StringBuilder sb3 = new StringBuilder();

					sb3.append(questions[i].toString() + "," + (data[i] == null ? "NA" : data[i].toString()));
					jsonObject.put("VALUE", sb3.toString());
					dataArray.add(jsonObject);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String downloadReport()
	{
		boolean b = false;
		try
		{
			// System.out.println("Checking path from requested client "+
			// reportFileName);
			setReportFileName(new File(reportFileName).toString());
			this.setFis(new FileInputStream(new File(reportFileName)));
		} catch (Exception e)
		{
			b = true;
			// System.out.println("Exception in Downloading");
			e.printStackTrace();
		}
		if (b)
		{
			addActionMessage("Error !!! File not found ");
			return "error";
		}
		return "success";
	}

	// ************** Patient Visit Acitivity starts************//

	public String viewVisitHeader()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				returnValue = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String fetchVisitColList()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();

		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_visit_action_config", accountID, connectionSpace, "", 0, "table_name", "patient_visit_action_config");
			if (statusColName != null && statusColName.size() > 0)
			{
				StringBuilder sb = new StringBuilder("");
				JSONObject colHead = new JSONObject();
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("comment") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("isFinished") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("readFlag") && !gdp.getColomnName().equalsIgnoreCase("closing_comment") && !gdp.getColomnName().equalsIgnoreCase("clientName")
							&& !gdp.getColomnName().equalsIgnoreCase("curr_schedule_date") && !gdp.getColomnName().equalsIgnoreCase("comment1") && !gdp.getColomnName().equalsIgnoreCase("sent_questionair"))
					{
						sb.append(gdp.getHeadingName() + ",");
					}
				}
				// System.out.println("sb>>>"+sb.toString());
				colHead.put("headValue", sb.substring(0, sb.lastIndexOf(",")));
				dataArray.add(colHead);
				currDate = DateUtil.getCurrentDateIndianFormat();
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}

	public String fetchDataVisitActivity()
	{
		try
		{
			
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");
		String month = null;
		boolean check = false;
		System.out.println(">>>>>>>>"+searchondate+"::::::::::::;");
		if (searchondate != null && !searchondate.equalsIgnoreCase("undefined") && !searchondate.equalsIgnoreCase("null")&& !searchondate.equalsIgnoreCase(""))
		{
			month = DateUtil.convertDateToUSFormat(searchondate);
			check = true;
		} else if (counter != null && !counter.equalsIgnoreCase("undefined") && !counter.equalsIgnoreCase("null") && !counter.equalsIgnoreCase("0"))
		{
			month = DateUtil.getDateAfterDays(Integer.parseInt(counter.trim()));
			check = true;
		}

		sb.append(" select pva.id,concat(pbd.first_name,' ',pbd.last_name),off.subofferingname,emp.emp_name as doctor,act1.act_name as current_activity,act1.act_name as next_activity," + " CONCAT((SUBSTRING_INDEX(pva.maxDateTime,' ',-1)),' ',pva.time)  ,pva.comment2,emp.emp_name " + " ,pva.escalate_date,pva.escalate_time,pva.escalation,pbd.mobile, pva.patient_name  from patient_visit_action as pva ");
		sb.append(" left join offeringlevel3 as off on off.id = pva.offeringlevel3");
		sb.append(" left join patient_basic_data as pbd on pbd.id = pva.patient_name");
		sb.append(" left join patientcrm_status_data as pcs2 on pcs2.id = pva.next_activity");
		sb.append(" left join activity_type as act1 on act1.id=pcs2.status ");
		sb.append(" left join manage_contact as cc on cc.id = pva.relationship_mgr ");
		sb.append(" left join primary_contact as emp on emp.id = cc.emp_id ");
		sb.append(" where pva.id!=0 ");
		if(session.get("userTpe").toString().equalsIgnoreCase("N"))
		{
			sb.append(" and cc.emp_id='"+session.get("empIdofuser").toString().split("-")[1]+"'");
		}
		if(offeringId!=null && !offeringId.equalsIgnoreCase("-1"))
		{
			sb.append(" and pva.offeringlevel3='"+offeringId+"' ");
		}
		if (check)
		{
			sb.append(" and pva.maxDateTime like '" + month + "%' and pva.status NOT IN('Closed','Completed') order by pva.maxDateTime ");
		} else
			sb.append(" and pva.maxDateTime like '" + DateUtil.getCurrentDateUSFormat() + "%' and pva.status NOT IN('Closed','Completed') order by pva.maxDateTime ");

		 System.out.println(">query>>::::::::: "+sb.toString());
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
		{
			Object obj = iterator.next();
			Object data[] = (Object[]) obj;
			jsonObject = new JSONObject();
			StringBuilder sb2 = new StringBuilder();

			for (int i = 0; i < data.length; i++)
			{
				if (data[i] != null && !data[i].toString().equals(""))
				{
					// System.out.println(i+"  "+ data[i].toString());
					if (i == 1)
					{
						sb2.append("'" + data[i].toString() + "'");
						sb2.append(",");
					} else if (i == 6)
					{
						sb2.append(DateUtil.convertDateToIndianFormat(data[i].toString().trim().split(" ")[0]) + " " + data[i].toString().trim().split(" ")[1]);
						sb2.append(",");
					}
					 else if (i == 9)
						{
						 	//System.out.println(data[i]+"::::"+data[i+1]);
							sb2.append(DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), data[i].toString(), data[i+1].toString()));
							sb2.append(",");
						//	System.out.println(DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), data[i].toString(), data[i+1].toString()));
							i++;
						}
					else
					{
						sb2.append(data[i].toString());
						sb2.append(",");
					}
				} else
				{
					sb2.append("NA,");
				}
			}
			System.out.println(sb2.substring(0, sb2.lastIndexOf(",")));
			jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
		}
		
		this.currDate = DateUtil.convertDateToIndianFormat(month != null ? month : DateUtil.getCurrentDateUSFormat());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String fetchOfferingList()
	{
		try
		{
			String month = null;
			boolean check = false;
			if (searchondate != null && !searchondate.equalsIgnoreCase("undefined") && !searchondate.equalsIgnoreCase("null"))
			{
				month = DateUtil.convertDateToUSFormat(searchondate);
				check = true;
			} else if (counter != null && !counter.equalsIgnoreCase("undefined") && !counter.equalsIgnoreCase("null") && !counter.equalsIgnoreCase("0"))
			{
				month = DateUtil.getDateAfterDays(Integer.parseInt(counter.trim()));
				check = true;
			}
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct off.id,off.subofferingname  " + " FROM patient_visit_action AS pva  ");
			query.append("INNER JOIN offeringlevel3 AS off ON off.id=pva.offeringlevel3");
			query.append(" left join manage_contact as cc on cc.id = pva.relationship_mgr ");
			query.append(" left join primary_contact as emp on emp.id = cc.emp_id ");
			query.append(" where pva.id!=0 ");
			if(session.get("userTpe").toString().equalsIgnoreCase("N"))
			{
				query.append(" and cc.emp_id='"+session.get("empIdofuser").toString().split("-")[1]+"'");
			}
			if (check)
			{
				query.append(" and pva.maxDateTime like '" + month + "%' and pva.status NOT IN('Closed','Completed') order by pva.maxDateTime ");
			} else
			{
				query.append(" and pva.maxDateTime like '" + DateUtil.getCurrentDateUSFormat() + "%' and pva.status NOT IN('Closed','Completed') order by pva.maxDateTime ");
			}
			System.out.println("offering query:::"+query);
			//dataList.clear();
			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			jsonArray=new JSONArray();
			if (dataList != null)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] dataC = (Object[]) iterator.next();
					jsonObject=new JSONObject();
					jsonObject.put("ID", dataC[0].toString());
					jsonObject.put("Name", dataC[1].toString());
					jsonArray.add(jsonObject);
				}
			}
			System.out.println(jsonArray.size());
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String fetchMissedVisitActivity()
	{

		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");
		
		sb.append(" select pva.id,concat(pbd.first_name,' ',pbd.last_name),off.subofferingname,emp.emp_name as doctor,act1.act_name as current_activity,act1.act_name as next_activity," + " CONCAT((SUBSTRING_INDEX(pva.maxDateTime,' ',-1)),' ',pva.time)  ,pva.comment2,emp.emp_name " + " ,pva.escalate_date,pva.escalate_time,pva.escalation, pva.patient_name  from patient_visit_action as pva ");
		sb.append(" left join offeringlevel3 as off on off.id = pva.offeringlevel3");
		sb.append(" left join patient_basic_data as pbd on pbd.id = pva.patient_name");
		sb.append(" left join patientcrm_status_data as pcs2 on pcs2.id = pva.next_activity");
		sb.append(" LEFT JOIN activity_type as act1 on act1.id=pcs2.status ");
		sb.append(" left join manage_contact as cc on cc.id = pva.relationship_mgr ");
		sb.append(" left join primary_contact as emp on emp.id = cc.emp_id ");
		sb.append(" where pva.id!=0 ");
		if(session.get("userTpe").toString().equalsIgnoreCase("N"))
		{
			sb.append(" and cc.emp_id='"+session.get("empIdofuser").toString().split("-")[1]+"'");
		}
		if(offeringId!=null && !offeringId.equalsIgnoreCase("-1"))
		{
			sb.append(" and pva.offeringlevel3='"+offeringId+"' ");
		}
		sb.append(" and SUBSTRING_INDEX(pva.maxDateTime,' ',1) < '" + DateUtil.getCurrentDateUSFormat() + "' and pva.status NOT IN('Closed','Completed') order by pva.maxDateTime desc ");
		System.out.println("missed  >>> "+sb.toString());
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
		{
			Object obj = iterator.next();
			Object data[] = (Object[]) obj;
			jsonObject = new JSONObject();
			StringBuilder sb2 = new StringBuilder();

			for (int i = 0; i < data.length; i++)
			{

				if (data[i] != null && !data[i].toString().equals(""))
				{
					if (i == 6)
					{
						sb2.append(DateUtil.convertDateToIndianFormat(data[i].toString().trim().split(" ")[0]) + " " + data[i].toString().trim().split(" ")[1]);

						sb2.append(",");
					} 
					else if (i == 9)
					{
					 	//System.out.println(data[i]+"::::"+data[i+1]);
						sb2.append(DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), data[i].toString(), data[i+1].toString()));
						sb2.append(",");
						//System.out.println(DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), data[i].toString(), data[i+1].toString()));
						i++;
					}
					else
					{
						sb2.append(data[i].toString());
						sb2.append(",");
					}
				} else
				{
					sb2.append("NA,");
				}
			}
			jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
		}
		return SUCCESS;
	}

	public String fetchVisitFeedbackData()
	{

		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		try
		{
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			sb.append(" select id,question " + " from feedback_form_questions ");
			/* System.out.println(">>>>>>>>>>>>>>>>> "+sb.toString()); */
			List qdataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			Map<String, String> mapdata = new LinkedHashMap<String, String>();

			for (Iterator iterator = qdataList.iterator(); iterator.hasNext();)
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				mapdata.put(data[0] != null ? data[0].toString() : "NA", data[1] != null ? data[1].toString() : "NA");
			}

			Set<String> qidSet = mapdata.keySet();
			StringBuilder sb2 = new StringBuilder("select ");
			for (Iterator iterator = qidSet.iterator(); iterator.hasNext();)
			{
				sb2.append(iterator.next().toString() + ",");
			}
			StringBuilder query = new StringBuilder("");
			query.append(sb2.substring(0, sb2.lastIndexOf(",")));
			query.append(" from feedback_form_answers where contactId=(select patient_name from patient_visit_action where id=" + id + ") order by id desc limit 0,1 ");
			//System.out.println("query.toString()" + query.toString());
			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);

			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				Object[] questions = (Object[]) mapdata.values().toArray();

				for (int i = 0; i < questions.length; i++)
				{
					StringBuilder sb3 = new StringBuilder();

					sb3.append(questions[i].toString() + "," + (data[i] == null ? "NA" : data[i].toString()));
					jsonObject.put("VALUE", sb3.toString());
					dataArray.add(jsonObject);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchVisitHistoryData()
	{
		try
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			
			sb.append(" select psd.status, emp.empName,off.subofferingname,SUBSTRING_INDEX(pva.maxDateTime,' ',1) as missedDateTime" + " from patient_visit_action as pva ");
			sb.append(" left join patientcrm_status_data as psd on psd.id = pva.next_activity ");
			sb.append(" left join compliance_contacts as cc on cc.id = pva.relationship_mgr");
			sb.append(" left join employee_basic as emp on emp.id = cc.emp_id ");
			sb.append(" left join offeringlevel3 as off on off.id = pva.offeringlevel3 ");

			sb.append(" where pva.patient_name=(select patient_name from patient_visit_action where id=" + id + ") and  pva.maxDateTime <='" + DateUtil.convertDateToUSFormat(date) + "' order BY pva.id DESC ");
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				StringBuilder sb2 = new StringBuilder();

				for (int i = 0; i < data.length; i++)
				{
					if (data[i] != null && !data[i].toString().equals(""))
					{
						if (i == 3)
						{
							sb2.append(DateUtil.convertDateToIndianFormat(data[i].toString().trim()));
							sb2.append(",");
						} else
						{
							sb2.append(data[i].toString());
							sb2.append(",");
						}
					} else
					{
						sb2.append("NA,");
					}
				}
				jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String patientProfileData()
	{
		try
		{
			//System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			profilemap = new LinkedHashMap<String, String>();
			
			sb.append("select concat(pbd.first_name,' ',pbd.last_name),cd.name,pbd.mobile,pbd.email,pbd.address,pbd.patient_type,pbd.patient_category,pbd.blood_group,pbd.allergic_to ");
			sb.append(" from patient_basic_data as pbd ");
			sb.append(" inner join country_data as cd on cd.id= pbd.nationality");
			sb.append(" where pbd.id= (select patient_name from patient_visit_action where id='" + id + "')");

			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				profilemap.put("Patient Name", getValueWithNullCheck(data[0].toString()));
				profilemap.put("Nationality", getValueWithNullCheck(data[1].toString()));
				profilemap.put("Moblie No", getValueWithNullCheck(data[2].toString()));
				profilemap.put("Email", getValueWithNullCheck(data[3].toString()));
				profilemap.put("Address", getValueWithNullCheck(data[4].toString()));
				profilemap.put("Patient Type", getValueWithNullCheck(data[5].toString()));
				profilemap.put("Patient Category", getValueWithNullCheck(data[6].toString()));
				profilemap.put("Blood Group", getValueWithNullCheck(data[7].toString()));
				profilemap.put("Allergic To", getValueWithNullCheck(data[8].toString()));
				
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String getDoctorList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				jsonArray = new JSONArray();
				// System.out.println(departId);
				if (offeringId != null && !offeringId.equalsIgnoreCase(""))
				{
						StringBuilder query = new StringBuilder();
						query.append("SELECT cc.id,emp.emp_name FROM doctor_off_map_data AS dmd ");
						query.append(" INNER JOIN manage_contact AS cc ON cc.id=dmd.doctor_name");
						query.append(" INNER JOIN primary_contact AS emp ON cc.emp_id=emp.id");
						query.append(" WHERE offlevel3=(SELECT offeringname FROM offeringlevel3 WHERE id="+offeringId+")");
						//System.out.println("doc:::"+query.toString());
						List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data2 != null && data2.size() > 0)
						{
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object != null)
								{
									jsonObject = new JSONObject();
									jsonObject.put("ID", object[0].toString());
									jsonObject.put("NAME", object[1].toString());
									jsonArray.add(jsonObject);
								}
							}
						}
					
					return SUCCESS;
				} else
				{
					return ERROR;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PatientDashboardPojo> getData4PatientQuestionnaire(SessionFactory connection, String id)
	{
		List dataList = new ArrayList();
		List<PatientDashboardPojo> report = new ArrayList<PatientDashboardPojo>();
		StringBuilder query = new StringBuilder("");
		// End of If Block for getting the data for only the current date in
		// both case for Resolved OR All
		/*
		 * query.append(
		 * " SELECT ques.questions,form.answer,ques.sections FROM feedback_form_answers AS form"
		 * ); query.append(
		 * " INNER join questionsconfig AS ques ON ques.id=SUBSTRING_INDEX(form.questionId,'q',-1) "
		 * );
		 * query.append(" where subQuestionId = 'NA' and form.date='"+DateUtil
		 * .getCurrentDateUSFormat()+"'and form.empId='"+id+"'");
		 */// System.out.println(query);

		query.append(" SELECT distinct form.empId,ques.sections,ques.questionSet FROM feedback_form_answers AS form");
		query.append(" INNER join questionsconfig AS ques ON ques.id=form.questionId ");
		query.append(" where subQuestionId = 'NA' and form.date='" + DateUtil.getCurrentDateUSFormat() + "'and form.empId='" + id + "'");

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			// System.out.println(query.toString());
			dataList = session.createSQLQuery(query.toString()).list();

			transaction.commit();
		} catch (Exception ex)
		{
			transaction.rollback();
		}

		if (dataList != null && dataList.size() > 0)
		{
			report = this.setQuestionaireData(dataList, id);
		}
		return report;
	}

	@SuppressWarnings("unchecked")
	public List<PatientDashboardPojo> setQuestionaireData(List data, String id)
	{
		List<PatientDashboardPojo> fbpList = new ArrayList<PatientDashboardPojo>();
		StringBuilder query = new StringBuilder("");
		PatientDashboardPojo fbp = null;
		if (data != null && data.size() > 0 && data.get(0) != null)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new PatientDashboardPojo();
				fbp.setId(getValueWithNullCheck(object[0]));
				fbp.setSections(getValueWithNullCheck(object[1]));
				fbp.setQuesset(getValueWithNullCheck(object[2]));
				fbpList.add(fbp);
			}
		}
		return fbpList;
	}

	@SuppressWarnings("unchecked")
	public List<PatientDashboardPojo> setQuestionaireSection(SessionFactory connection, String section, String id, String setno)
	{
		List<PatientDashboardPojo> fbpList = new ArrayList<PatientDashboardPojo>();
		StringBuilder query = new StringBuilder("");
		PatientDashboardPojo fbp = null;
		query.append(" SELECT ques.subQuestion,ques.questions,form.answer FROM feedback_form_answers AS form");
		query.append(" INNER join questionsconfig AS ques ON ques.id=SUBSTRING_INDEX(form.questionId,'q',-1) ");
		query.append(" where subQuestionId = 'NA' and form.date='" + DateUtil.getCurrentDateUSFormat() + "'and form.empId='" + id + "' and ques.sections='" + section + "' and ques.questionSet='" + setno + "'");
		List dataList = coi.executeAllSelectQuery(query.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			int i = 1;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new PatientDashboardPojo();
				if (!getValueWithNullCheck(object[1]).equalsIgnoreCase("Relation") && !getValueWithNullCheck(object[1]).equalsIgnoreCase("Duration") && !getValueWithNullCheck(object[1]).equalsIgnoreCase("Mention Value if any"))
				{
					fbp.setSno(Integer.toString(i));
					/*
					 * if(object[0]!=null &&
					 * object[0].toString().equalsIgnoreCase("Relation")) {
					 */
					// fbp.setSubquestion(getValueWithNullCheck(object[0]));
					// fbp.setSubanswers(getValueWithNullCheck(object[1].toString().subSequence(0,
					// object[1].toString().length()-1)));
					/*
					 * } else{
					 */
					fbp.setQuesid(getValueWithNullCheck(object[0]));
					List datt = getSubQuestionaire(connection, getValueWithNullCheck(object[0]));
					if (datt != null && datt.size() > 0)
					{
						String sub = datt.get(0).toString();
						if (sub != null && sub != "")
						{
							query = null;
							query = new StringBuilder("");
							query.append(" SELECT form.answer FROM feedback_form_answers AS form");
							query.append(" INNER join questionsconfig AS ques ON ques.id=form.questionId ");
							query.append(" where ques.id='" + sub + "' and form.date='" + DateUtil.getCurrentDateUSFormat() + "'and form.empId='" + id + "' and ques.sections='" + section + "'");
							List ist = coi.executeAllSelectQuery(query.toString(), connection);
							/*
							 * if(ist!=null && ist.size()>0 && ist.get(0)!=null)
							 * { for (Iterator itr = ist.iterator();
							 * iterator.hasNext();) { Object[] ob = (Object[])
							 * iterator.next(); if(ob[0]!=null && ob[0]!="") {
							 * fbp.setSubquestion(ob[0].toString()); } else
							 * if(ob[1]!=null && ob[1]!="") {
							 * fbp.setSubanswers(ob
							 * [1].toString().trim().subSequence
							 * (0,ob[1].toString
							 * ().trim().length()-1).toString()); } } }
							 */
							if (ist != null && ist.size() > 0 && ist.get(0) != null)
							{
								fbp.setSubanswers(ist.get(0).toString().trim().subSequence(0, ist.get(0).toString().trim().length() - 1).toString());
							}
						}
					}
					fbp.setQuestion(getValueWithNullCheck(object[1]));
					if (object[2] != null)
					{
						fbp.setAnswers(object[2].toString().trim().subSequence(0, object[2].toString().trim().length() - 1).toString());
					} else
					{
						fbp.setAnswers("NA");
					}
					/* } */
					i = i + 1;
					// System.out.println(fbp.getSubquestion());
					fbpList.add(fbp);
				}
			}
		}

		return fbpList;
	}

	@SuppressWarnings("unchecked")
	public List getSubQuestionaire(SessionFactory connection, String queid)
	{
		List<PatientDashboardPojo> fbpList = new ArrayList<PatientDashboardPojo>();
		StringBuilder query = new StringBuilder("");
		PatientDashboardPojo fbp = null;
		query.append(" SELECT questionId FROM questionssubconfig ");
		query.append(" where id='" + queid + "'");
		List dataList = coi.executeAllSelectQuery(query.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			fbpList = dataList;
		}
		return fbpList;
	}

	// ************** Patient Visit Acitivity ends************//

	public JSONArray getDataArray()
	{
		return dataArray;
	}

	public void setDataArray(JSONArray dataArray)
	{
		this.dataArray = dataArray;
	}

	public String getCurrDate()
	{
		return currDate;
	}

	public void setCurrDate(String currDate)
	{
		this.currDate = currDate;
	}

	public String getSearchondate()
	{
		return searchondate;
	}

	public void setSearchondate(String searchondate)
	{
		this.searchondate = searchondate;
	}

	public String getCounter()
	{
		return counter;
	}

	public void setCounter(String counter)
	{
		this.counter = counter;
	}

	public File getReport()
	{
		return report;
	}

	public void setReport(File report)
	{
		this.report = report;
	}

	public String getReportFileName()
	{
		return reportFileName;
	}

	public void setReportFileName(String reportFileName)
	{
		this.reportFileName = reportFileName;
	}

	public String getReportContentType()
	{
		return reportContentType;
	}

	public void setReportContentType(String reportContentType)
	{
		this.reportContentType = reportContentType;
	}

	public void setTraceid(String traceid)
	{
		this.traceid = traceid;
	}

	public String getTraceid()
	{
		return traceid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public FileInputStream getFis()
	{
		return fis;
	}

	public void setFis(FileInputStream fis)
	{
		this.fis = fis;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getPsex()
	{
		return psex;
	}

	public void setPsex(String psex)
	{
		this.psex = psex;
	}

	public String getPoccupation()
	{
		return poccupation;
	}

	public void setPoccupation(String poccupation)
	{
		this.poccupation = poccupation;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public void setProfilemap(Map<String, String> profilemap)
	{
		this.profilemap = profilemap;
	}

	public Map<String, String> getProfilemap()
	{
		return profilemap;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public String getSetNo()
	{
		return setNo;
	}

	public void setSetNo(String setNo)
	{
		this.setNo = setNo;
	}

	public List<PatientActivityPojo> getQuestionList()
	{
		return questionList;
	}

	public void setQuestionList(List<PatientActivityPojo> questionList)
	{
		this.questionList = questionList;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobno(String mobno)
	{
		this.mobno = mobno;
	}

	public String getMobno()
	{
		return mobno;
	}

	public void setOt(String ot)
	{
		this.ot = ot;
	}

	public String getOt()
	{
		return ot;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public String getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(String pageNo)
	{
		this.pageNo = pageNo;
	}

	public Map<String, String> getAnswerMap()
	{
		return answerMap;
	}

	public void setAnswerMap(Map<String, String> answerMap)
	{
		this.answerMap = answerMap;
	}

	public String getDone()
	{
		return done;
	}

	public void setDone(String done)
	{
		this.done = done;
	}

	public String getPositionPage()
	{
		return positionPage;
	}

	public void setPositionPage(String positionPage)
	{
		this.positionPage = positionPage;
	}

	public void setMain(String main)
	{
		this.main = main;
	}

	public String getMain()
	{
		return main;
	}

	public void setCotp(String cotp)
	{
		this.cotp = cotp;
	}

	public String getCotp()
	{
		return cotp;
	}

	public String getForm_name()
	{
		return form_name;
	}

	public void setForm_name(String form_name)
	{
		this.form_name = form_name;
	}

}