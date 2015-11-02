package com.Over2Cloud.ctrl.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.stax2.ri.typed.StringBase64Decoder;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.FeedbackDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackOverallSummaryBean;
import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojoDashboard;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.pojo.DeptActionPojo;
import com.Over2Cloud.ctrl.feedback.pojo.FeedbackAnalysis;
import com.Over2Cloud.ctrl.feedback.pojo.NegativeFeedbackStagePojo;
import com.Over2Cloud.ctrl.feedback.pojo.QualityPojo;
import com.Over2Cloud.ctrl.feedback.pojo.RatingPojo;
import com.Over2Cloud.ctrl.feedback.pojo.TicketAllotmentPojo;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackDashboard extends GridPropertyBean
{
	/**
* 
*/
	private static final long serialVersionUID = 326895984327492755L;
	private List<FeedbackPojoDashboard> dashPojoList;
	private List<FeedbackOverallSummaryBean> summaryList;
	private List<FeedbackDataPojo> feedDataDashboardList;
	private List<FeedbackDataPojo> feedDataDashboardType;
	private List<TicketAllotmentPojo> allotPojo;
	private List<FeedbackTicketPojo> feedTicketList;
	private JSONArray jsonArray;
	private String graphHeader;
	private String graphDescription;
	private String yAxisHeader;
	private String xAxisHeader;
	private Map<String, Integer> pieYesNoMap;
	private Map<Integer, String> pieFeedbackCounter;
	private Map<String, Integer> pieNegFeedStatus;
	private Map<String, String> feedbackPieCounters;
	private String totalPending;
	private String totalLevel1;
	private String totalLevel2;
	private String totalLevel3;
	private String totalLevel4;
	private String totalLevel5;
	private String totalSnooze;
	private String totalHighPriority;
	private String totalIgnore;
	private String totalReAssign;
	private String totalResolve;
	private String totalCounter;
	private String totalData;
	private String today;
	private String block;
	private List<GridDataPropertyView> masterViewProp = null;
	private String status;
	private String deptId;
	private String type;
	private List<Object> masterViewList;
	private String feedId;
	private Map<String, String> docMap;
	private String documentName;
	private List<FeedbackAnalysis> analysisPojo;
	private List<DeptActionPojo> deptActionPojo;
	private String subTotalIssue;
	private String SubTotalAction;
	private String SubTotalCapa;
	private String fromDate = null;
	private String toDate = null;
	private String totalNoted;
	private String totalTO;
	private String totalNFA;
	private List<NegativeFeedbackStagePojo> negativeFeedList;
	private List<NegativeFeedbackStagePojo> chooseList;
	private String totalRating1, totalRating2, totalRating3, totalRating4, totalRating5;
	private List<QualityPojo> Ratinginfo = new ArrayList<QualityPojo>();
	private static final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private InputStream fileInputStream;
	private String fileName;
	private String userType = (String) session.get("userTpe");
	private TreeMap<String, String> fromDept;
	

	public String getDeptFeedAllotDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				allotPojo = new ArrayList<TicketAllotmentPojo>();
				allotPojo = new FeedbackDashboardDao().getDeptWiseTicketAllotmentDetails(getDeptId(), connectionSpace);
				return SUCCESS;
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

	@SuppressWarnings("rawtypes")
	public String beforePositiveNegativeGraph()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				pieYesNoMap = new HashMap<String, Integer>();
				pieYesNoMap = new FeedbackDashboardDao().getYesNoMap(connectionSpace);
				return SUCCESS;
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

	@SuppressWarnings("rawtypes")
	public String beforeNegativeFeedbackStatusGraph()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				pieNegFeedStatus = new HashMap<String, Integer>();
				pieNegFeedStatus = new FeedbackDashboardDao().getYesNoMap(connectionSpace);
				return SUCCESS;
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

	@SuppressWarnings("rawtypes")
	public String showFeedbackCountersPie()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				feedbackPieCounters = new TreeMap<String, String>();
				List dataList = cbt.executeAllSelectQuery(" select mode,count(*) from feedbackdata group by mode ", connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();

						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								feedbackPieCounters.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getNegativeFeedCounter()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				negativeFeedList = new ArrayList<NegativeFeedbackStagePojo>();
				chooseList=new ArrayList<NegativeFeedbackStagePojo>();
				NegativeFeedbackStagePojo pojo=null ;
				Map<String, Object> session = ActionContext.getContext().getSession();
				EscalationActionControlDao EAC = new EscalationActionControlDao();
				List list = new ArrayList();
				List dataList = null;
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				StringBuffer buffer = null;
				int pending = 0, others = 0, tktOpened = 0, actionTaken = 0, negative = 0,noToBLK=0,positiveRemarks=0,negativeRemarks=0;
				int physian=0,tpa=0,friends=0,corp=0,advt=0,totOthers=0;
				int totalCount=0;

				jsonArray = new JSONArray();
				/*if ((fromDate == null || "null".equalsIgnoreCase("fromdate")) && (toDate == null || "null".equalsIgnoreCase("todate")))
				{
					fromDate=(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					toDate=(DateUtil.getCurrentDateIndianFormat());
					System.out.println(fromDate+":::getNegativeFeedCounter::::"+toDate);
				}
				System.out.println(fromDate+":::::::"+toDate);*/
				int count = 0;
				List<String> modesList = new ArrayList<String>();
				// if(tableExists)
				{
					modesList.add("Paper-IPD");
					modesList.add("Paper-OPD");
					modesList.add("SMS-IPD");
					modesList.add("SMS-OPD");
					modesList.add("Admin Round");
					modesList.add("Ward Rounds");
					modesList.add("By Hand");
					modesList.add("Email");
					modesList.add("Verbal");
				}
				if (status != null && !status.equalsIgnoreCase(""))
				{
					modesList.clear();
					modesList.add(status);
				}
				if (fromDate != null && toDate != null)
				{

					String str1 = " AND feedbck.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'";
					for (String str : modesList)
					{
						String[] temp = str.split("-");
						pojo= new NegativeFeedbackStagePojo();
						pojo.setMode(str);
						if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("Paper-OPD") || str.equalsIgnoreCase("SMS-OPD"))
						{
							//Total counter
							buffer = new StringBuffer("select count(DISTINCT feedbck.id) from feedbackdata as feedbck  ");
							buffer.append(" where feedbck.mode ='" + temp[0] + "'");
							buffer.append(" and feedbck.compType='" + temp[1] + "'");
							buffer.append(str1);
							//System.out.println("total count query>>>>"+buffer);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setTotalSeek(String.valueOf(count));
							if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("Paper-OPD"))
							{
								totalCount+=count;
							}
							count = 0;
							buffer.delete(0, buffer.length());
							
								if (temp[1].equalsIgnoreCase("IPD"))
								{
									buffer.append("SELECT field_value FROM feedback_paperform_rating_configuration AS fprc WHERE field_value NOT IN('max_id_feeddbackdata','choseHospital','recommend')");
								} 
								else
								{
									buffer.append("SELECT field_value FROM feedback_paperform_rating_configuration_opd AS fprc WHERE field_value NOT IN('max_id_feeddbackdata','choseHospital','recommend','targetOn')");
								}
								list=cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
								//System.out.println(list.toString().replace("[", "(").replace("]", ")"));
								buffer.delete(0, buffer.length());
								if(list!=null && list.size()>0)
								{
									buffer.append("SELECT count(DISTINCT feedbck.id) FROM feedbackdata AS feedbck ");
									if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("Paper-OPD"))
									{
										buffer.append(" INNER JOIN feedback_paper_rating AS fpr ON fpr.max_id_feeddbackdata=feedbck.id ");
										buffer.append(" WHERE ( 1 IN"+list.toString().replace("[", "(").replace("]", ")"));
										buffer.append(" OR 2 IN"+list.toString().replace("[", "(").replace("]", ")")+" OR feedbck.targetOn='No') ");
									}
									else
									{
										buffer.append(" WHERE feedbck.targetOn='No'");
									}
									buffer.append(" AND feedbck.mode='"+temp[0]+"' AND feedbck.compType='"+temp[1]+"' "+str1);
									//System.out.println("query>>>>>>>>"+buffer);
									list.clear();
									list=cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
									pojo.setNegTicket(String.valueOf(list.get(0)));
									count=(Integer.parseInt(pojo.getTotalSeek()))-(Integer.parseInt(pojo.getNegTicket()));
									if(pojo.getNegTicket().equalsIgnoreCase("0"))
									{
										
									}
									else
									{	
										pojo.setNegTicket(pojo.getNegTicket()+" ("+(Math.round(((double)Integer.parseInt(pojo.getNegTicket())/Integer.parseInt(pojo.getTotalSeek()))*100.0))+"%)");
									}
									//System.out.println("count>>>>"+count);
									if(count>0)
									{
										pojo.setPosTicket(String.valueOf(count)+" ("+(Math.round(((double)count/Integer.parseInt(pojo.getTotalSeek()))*100.0))+"%)");
									}
									count=0;
								}
								buffer.delete(0, buffer.length());
								list.clear();
							
							//For positive,negative remarks and no to BLK
							if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("Paper-OPD"))
							{
								buffer.append("select overAll,recommend,choseHospital from feedbackdata as feedbck ");
								buffer.append(" where feedbck.mode ='" + temp[0] + "' and feedbck.compType='" + temp[1] + "'");
								buffer.append(str1);
								list.clear();
								//System.out.println("remarks query>>>>>"+buffer);
								list=cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
								if(list!=null && list.size()>0)
								{
									for (Iterator iterator = list.iterator(); iterator.hasNext();)
									{
										Object object[] = (Object[]) iterator.next();
										if(object[0]!=null)
										{
											if(object[0].equals("Yes"))
											{
												positiveRemarks++;
											}
											else
											{
												negativeRemarks++;
											}
										}
										if(object[1]!=null && object[1].equals("No"))
										{
											noToBLK++;
										}
										if(object[2]!=null)
										{
											if(object[2].equals("Physician"))
											{
												physian++;
											}
											else if(object[2].equals("Friends & Relatives"))
											{
												friends++;
											}
											else if(object[2].equals("TPA"))
											{
												tpa++;
											}
											else if(object[2].equals("Corp-Tie Ups"))
											{
												corp++;
											}
											else if(object[2].equals("Adv./Website"))
											{
												advt++;
											}
											else if(object[2].equals("Others"))
											{
												totOthers++;
											}
											
										}
									}
								}
								if(positiveRemarks==0)
								{
									pojo.setTotalPosRemarks(String.valueOf(positiveRemarks));
								}
								else
								{
									pojo.setTotalPosRemarks(String.valueOf(positiveRemarks)+" ("+(Math.round(((double)positiveRemarks/Integer.parseInt(pojo.getTotalSeek()))*100.0))+"%)");
								}
								if(negativeRemarks==0)
								{
									pojo.setTotalNegRemarks(String.valueOf(negativeRemarks));
								}
								else
								{
									pojo.setTotalNegRemarks(String.valueOf(negativeRemarks)+" ("+(Math.round(((double)negativeRemarks/Integer.parseInt(pojo.getTotalSeek()))*100.0))+"%)");
								}
								if(noToBLK==0)
								{
									pojo.setTotalNoToBLK(String.valueOf(noToBLK));
								}
								else
								{	
									pojo.setTotalNoToBLK(String.valueOf(noToBLK)+" ("+(Math.round(((double)noToBLK/Integer.parseInt(pojo.getTotalSeek()))*100.0))+"%)");
								}	
								//Chose BLK because of
								//pojo= new NegativeFeedbackStagePojo();
								if(physian==0)
								{
									pojo.setTotalPhysian(String.valueOf(physian));
								}
								else
								{
									pojo.setTotalPhysian(String.valueOf(physian)+" ("+(Math.round(((double)physian/totalCount)*100.0))+"%)");
								}
								if(friends==0)
								{
									pojo.setTotalFriends(String.valueOf(friends));
								}
								else
								{
									pojo.setTotalFriends(String.valueOf(friends)+" ("+(Math.round(((double)friends/totalCount)*100.0))+"%)");
								}
								if(tpa==0)
								{
									pojo.setTotalTPA(String.valueOf(tpa));
								}
								else
								{
									pojo.setTotalTPA(String.valueOf(tpa)+" ("+(Math.round(((double)tpa/totalCount)*100.0))+"%)");
								}
								if(corp==0)
								{
									pojo.setTotalCorp(String.valueOf(corp));
								}
								else
								{
									pojo.setTotalCorp(String.valueOf(corp)+" ("+(Math.round(((double)corp/totalCount)*100.0))+"%)");
								}
								if(advt==0)
								{
									pojo.setTotalAdvt(String.valueOf(advt));
								}
								else
								{
									pojo.setTotalAdvt(String.valueOf(advt)+" ("+(Math.round(((double)advt/totalCount)*100.0))+"%)");
								}
								if(totOthers==0)
								{
									pojo.setTotalOthers(String.valueOf(totOthers));
								}
								else
								{
									pojo.setTotalOthers(String.valueOf(totOthers)+" ("+(Math.round(((double)totOthers/totalCount)*100.0))+"%)");
								}
								pojo.setCompType(temp[1]);
								chooseList.add(pojo);
								//System.out.println(pojo.getTotalPositive()+">>>>>>><<<<<<<<<<"+pojo.getTotalNegative());
								physian=0;tpa=0;friends=0;corp=0;advt=0;totOthers=0;totalCount=0;
								positiveRemarks=0;
								negativeRemarks=0;
								noToBLK=0;
								buffer.delete(0, buffer.length());
								list.clear();
							}
							
							
							pojo.setTotalNegative("" + negative);

							// pojo.setStage1NoFurtherAction("" + nfa);
							// pojo.setStage1Total("" + (pending + tktOpened +
							// nfa));
							/*if (block != null && block.equalsIgnoreCase("Stage 2nd"))
							{
								JSONObject jsonObject1 = new JSONObject();
								jsonObject1.put("mode", "Pending");
								jsonObject1.put("counter", pojo.getStage1Pending());
								jsonArray.add(jsonObject1);
								JSONObject jsonObject2 = new JSONObject();
								jsonObject2.put("mode", "ActionTaken");
								jsonObject2.put("counter", pojo.getStage1ActionTaken());
								jsonArray.add(jsonObject2);
								JSONObject jsonObject3 = new JSONObject();
								jsonObject3.put("mode", "NoFurtherAction");
								jsonObject3.put("counter", pojo.getStage1NoFurtherAction());
								jsonArray.add(jsonObject3);
							}*/
							pending = 0;
							//nfa = 0;
							tktOpened = 0;
							// dataList.clear();
							// list.clear();
							buffer.delete(0, buffer.length());
						}
						// Stage-II Pending
						buffer = new StringBuffer("select count(DISTINCT feedbck.id),feedbck.status from feedback_status as feedbck ");
						buffer.append(getExtraQuery(str));
						buffer.append(" where feedbck.via_from='" + temp[0] + "' and feedbck.escalation_date!='NA'  and feedbck.stage='2' and feedbck.status NOT IN ('Ticket Opened','No Further Action Required','Ignore')");
						if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
						{
							buffer.append(" and feed.compType='" + temp[1] + "'");
						}
						buffer.append(" and feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and feedbck.moduleName='FM' group by feedbck.status");
						//System.out.println("stageII pending"+buffer);
						list = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
						if (list != null && list.size() > 0)
						{
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									if (object[1].equals("Pending"))
									{
										pending += Integer.parseInt(object[0].toString());
									}
									else if (object[1].equals("Resolved") || object[1].equals("Ignore") || object[1].equals("Acknowledged"))
									{
										actionTaken += Integer.parseInt(object[0].toString());
									}
									else
									{
										others+=Integer.parseInt(object[0].toString());
									}
								}
							}
						}
						pojo.setStage2Pending("" + pending);
						pojo.setStage2ActionTaken("" + actionTaken);
						pojo.setStage2TicketOpened("" + (pending + others + actionTaken));
						//For Pie Chart
						if (block != null && block.equalsIgnoreCase("Stakeholder"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Closed");
							if(pojo.getStage2ActionTaken().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getStage2ActionTaken());
							}
							else
							{
								jsonObject.put("counter", pojo.getStage2ActionTaken().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Pending");
							if(pojo.getStage2Pending().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getStage2Pending());
							}
							else
							{
								jsonObject.put("counter", pojo.getStage2Pending().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
						}
						else if ((block != null && block.equalsIgnoreCase("Rating")))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "3 & Above/Y");
							if(pojo.getPosTicket().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getPosTicket());
							}
							else
							{
								jsonObject.put("counter", pojo.getPosTicket().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "1 & 2/N");
							if(pojo.getNegTicket().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getNegTicket());
							}
							else
							{
								jsonObject.put("counter", pojo.getNegTicket().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							
						}
						else if (block != null && block.equalsIgnoreCase("Remarks") )
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Positive");
							if(pojo.getTotalPosRemarks().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalPosRemarks());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalPosRemarks().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Negative");
							if(pojo.getTotalNegRemarks().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalNegRemarks());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalNegRemarks().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
						}
						//For choosen Pie chart
						else if ((block != null && block.equalsIgnoreCase("choosen")))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Physician");
							if(pojo.getTotalPhysian().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalPhysian());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalPhysian().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "TPA");
							if(pojo.getTotalTPA().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalTPA());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalTPA().split(" ")[0]);
							}
							
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Friends & Relative");
							if(pojo.getTotalFriends().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalFriends());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalFriends().split(" ")[0]);
							}
							
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Corp-Tie Up");
							if(pojo.getTotalCorp().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalCorp());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalCorp().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Advt./ Website");
							if(pojo.getTotalAdvt().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalAdvt());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalAdvt().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							jsonObject.clear();
							jsonObject.put("mode", "Others");
							if(pojo.getTotalOthers().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", pojo.getTotalOthers());
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalOthers().split(" ")[0]);
							}
							jsonArray.add(jsonObject);
							
						}
						else if( (block != null && block.equalsIgnoreCase("RatingRem")))
						{
							
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "3 & Above/Y");
							if(pojo.getPosTicket().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", "0(0%)");
							}
							else
							{
								jsonObject.put("counter", pojo.getPosTicket());
							}
							
							jsonArray.add(jsonObject);
							jsonObject.clear();
							
							if(status.equalsIgnoreCase("SMS-IPD"))
							jsonObject.put("mode", "Positive");
							if( pojo.getTotalPosRemarks().equalsIgnoreCase("0"))
							{
								jsonObject.put("counter", "0(0%)");
							}
							else
							{
								jsonObject.put("counter", pojo.getTotalPosRemarks());
							}
							jsonArray.add(jsonObject);
						}
						pending = 0;
						actionTaken = 0;
						others=0;
						list.clear();
						//For Bar Chart
						if (block != null && block.equalsIgnoreCase("DepartmentBar"))
						{
							if(!pojo.getStage2TicketOpened().equalsIgnoreCase("0"))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", str);
								jsonObject.put("Closed", pojo.getStage2ActionTaken());
								jsonObject.put("Pending", pojo.getStage2Pending());
								jsonArray.add(jsonObject);
							}
						}
						else if (block != null && block.equalsIgnoreCase("RemarksBar"))
						{
							if(!(pojo.getTotalPosRemarks().equalsIgnoreCase("0")&& pojo.getTotalNegRemarks().equalsIgnoreCase("0")))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", str);
								if(pojo.getTotalPosRemarks().equalsIgnoreCase("0"))
								{
									jsonObject.put("Positive", pojo.getTotalPosRemarks());
								}
								else
								{
									jsonObject.put("Positive",pojo.getTotalPosRemarks().split(" ")[0]);
								}
								if(pojo.getTotalNegRemarks().equalsIgnoreCase("0"))
								{
									jsonObject.put("Negative", pojo.getTotalNegRemarks());
								}
								else
								{
									jsonObject.put("Negative", pojo.getTotalNegRemarks().split(" ")[0]);
								}
								jsonArray.add(jsonObject);
							}
							
						}
						else if (block != null && block.equalsIgnoreCase("RatingBar"))
						{
							if(!(pojo.getPosTicket().equalsIgnoreCase("0")&& pojo.getNegTicket().equalsIgnoreCase("0")))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", str);
								if(pojo.getPosTicket().equalsIgnoreCase("0"))
								{
									jsonObject.put("3 & above/Yes", pojo.getPosTicket());
								}
								else
								{
									jsonObject.put("3 & above/Yes", pojo.getPosTicket().split(" ")[0]);
								}
								if(pojo.getNegTicket().equalsIgnoreCase("0"))
								{
									jsonObject.put("1 & 2/No", pojo.getNegTicket());
								}
								else
								{
									jsonObject.put("1 & 2/No", pojo.getNegTicket().split(" ")[0]);
								}
								
								jsonArray.add(jsonObject);
							}
						}
						negativeFeedList.add(pojo);
						count = 0;
						negative = 0;
					}
				//	System.out.println(physian+"<<<<<<<<<<<<"+totalCount+">>>>>>>>>>"+(((double)physian/totalCount)*100));
					
				}
				return SUCCESS;
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

	public String showFeedbackCounters()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (block != null && block.equalsIgnoreCase("block2"))
				{
					summaryList = new ArrayList<FeedbackOverallSummaryBean>();
					int i = 1;
					int counter = 0;
					List<String> modesList = new ArrayList<String>();
					// if(tableExists)
					{
						// modesList=new
						// EscalationActionControlDao().getGraphData
						// (connectionSpace,"feedbackdata","mode","select");
						modesList.add("Paper");
						modesList.add("SMS");
						modesList.add("Mob Tab");
						modesList.add("Kiosk");
						modesList.add("IVRS");
						modesList.add("Online");
						// modesList.add("Voice");
						// modesList.add("Call");

					}
					// System.out.println(
					// "Modes List is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."
					// +modesList.size());
					StringBuffer bufer = null;
					for (String str : modesList)
					{
						FeedbackOverallSummaryBean summary = new FeedbackOverallSummaryBean();
						summary.setId(i++);
						// For Yesterdays Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getNextDateAfter(-1) + "' and mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getNextDateAfter(-1) + "' and mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayNo(String.valueOf(counter));

						// For Todays Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getCurrentDateUSFormat() + "' and mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getCurrentDateUSFormat() + "' and mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayNo(String.valueOf(counter));

						// For Total Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalNo(String.valueOf(counter));
						if (str != null && str.equalsIgnoreCase("Mob Tab"))
						{
							str = "Mobile App";
						}
						summary.setMode(str);
						summaryList.add(summary);
					}
				} else if (block != null && block.equalsIgnoreCase("block5"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					FeedbackDashboardDao FDD = new FeedbackDashboardDao();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
					if (deptId != null && deptId.size() > 0)
					{
						feedDataDashboardList = FDD.getFeedbackCategoryCounters(connectionSpace, deptId, "", "");
						if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
						{
							int temp = 0;
							for (FeedbackDataPojo fp : feedDataDashboardList)
							{
								temp = temp + fp.getActionCounter();
							}
							totalPending = String.valueOf(temp);
						}
					}
				} else if (block != null && block.equalsIgnoreCase("block6"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					dashPojoList = new ArrayList<FeedbackPojoDashboard>();
					if (deptId != null && deptId.size() > 0)
					{
						dashPojoList = new FeedbackDashboardDao().getDashboardLevelStatus(connectionSpace, deptId, fromDate, toDate);
						if (dashPojoList != null && dashPojoList.size() > 0)
						{
							int temp5 = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0;
							for (FeedbackPojoDashboard fpd : dashPojoList)
							{
								temp1 = temp1 + Integer.parseInt(fpd.getPendingL1());
								temp2 = temp2 + Integer.parseInt(fpd.getPendingL2());
								temp3 = temp3 + Integer.parseInt(fpd.getPendingL3());
								temp4 = temp4 + Integer.parseInt(fpd.getPendingL4());
								temp5 = temp5 + Integer.parseInt(fpd.getPendingL5());
							}
							totalLevel1 = String.valueOf(temp1);
							totalLevel2 = String.valueOf(temp2);
							totalLevel3 = String.valueOf(temp3);
							totalLevel4 = String.valueOf(temp4);
							totalLevel5 = String.valueOf(temp5);
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getDashboardTicketCountersShow()
	{

		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{

				String empIdofuser = (String) session.get("empIdofuser");
				setUserType((String) session.get("userTpe"));

				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				String empId = empIdofuser.split("-")[1].trim();
				// fromDept =new TreeMap<>();
				dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				// change by kamlesh 29-10-2014
				// System.out.println(getFromDate()+" TO "+getToDate());
				if (fromDate == null || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase(" "))
				{
					setFromDate(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					setToDate(DateUtil.getCurrentDateIndianFormat());
				}// change end
				else if(getFromDate() != null && getToDate() != null && !"".equalsIgnoreCase(getFromDate()) && !"".equalsIgnoreCase(getToDate()))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						fromDate=DateUtil.convertDateToIndianFormat(getFromDate());
						toDate=DateUtil.convertDateToIndianFormat(getToDate());
					}
				}
				
				setGraphHeader("Department wise Ticket Status From " + fromDate + " to " + toDate);
				dashPojoList = new FeedbackDashboardDao().getDashboardTicketsDataShow(connectionSpace, "", true, true, getFromDate(), getToDate());
				if (dashPojoList != null && dashPojoList.size() > 0)
				{
					int temp = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0, temp5 = 0, temp6 = 0, temp7 = 0, temp8 = 0, temp9 = 0, temp10 = 0, temp11 = 0, temp12 = 0, temp13 = 0, temp14 = 0;
					for (FeedbackPojoDashboard fpd : dashPojoList)
					{
						temp = temp + Integer.parseInt(fpd.getPending());
						temp6 = temp6 + Integer.parseInt(fpd.getHp());
						temp7 = temp7 + Integer.parseInt(fpd.getSz());
						temp8 = temp8 + Integer.parseInt(fpd.getIg());
						temp9 = temp9 + Integer.parseInt(fpd.getRAs());
						temp10 = temp10 + Integer.parseInt(fpd.getResolved());
						temp11 = temp11 + Integer.parseInt(fpd.getTotalCounter());
						temp12 = temp12 + Integer.parseInt(fpd.getNoted());
						temp13 += Integer.parseInt(fpd.getTicketOpened());
						temp14 += Integer.parseInt(fpd.getNfa());
					}
					totalPending = String.valueOf(temp);
					totalSnooze = String.valueOf(temp7);
					totalHighPriority = String.valueOf(temp6);
					totalIgnore = String.valueOf(temp8);
					totalReAssign = String.valueOf(temp9);
					totalResolve = String.valueOf(temp10);
					totalCounter = String.valueOf(temp11);
					totalNoted = String.valueOf(temp12);
					totalTO = String.valueOf(temp13);
					totalNFA = String.valueOf(temp14);
					//System.out.println("total nfa"+totalNFA);

					if (dashPojoList != null && dashPojoList.size() > 0)
					{
						int tempp = 0, tempp1 = 0;
						DecimalFormat df = new DecimalFormat();
						df.setMaximumFractionDigits(2);
						for (FeedbackPojoDashboard fpd : dashPojoList)
						{
							tempp = tempp + Integer.parseInt(fpd.getTotalAction());
							tempp1 = tempp1 + Integer.parseInt(fpd.getTotalCapa());
							fpd.setPercent(String.valueOf(df.format((Float.parseFloat(fpd.getTotalCounter()) / Float.parseFloat(totalCounter) * 100))));
						}
						setSubTotalAction(String.valueOf(tempp));
						setSubTotalCapa(String.valueOf(tempp1));
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return ERROR;
		}
	}

	// to be merged 31-10-2014
	@SuppressWarnings("rawtypes")
	public String getDashboardShow()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				// For Bar Graph
				// change by kamlesh 29-10-2014
				if (fromDate == null && toDate == null)
				{

					setFromDate(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					setToDate(DateUtil.getCurrentDateIndianFormat());

				}// change end

				// System.out.println(getFromDate()+" TO "+getToDate());
				// setGraphHeader("Department wise Ticket Status From " +
				// fromDate + " to " + toDate);
				setYAxisHeader("Ticket Counters");
				setXAxisHeader("Ticket Counters");
				List<FeedbackPojoDashboard> dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				FeedbackDashboardDao fd = new FeedbackDashboardDao();
				// change by kamlesh 29-10-2014

				fromDate=DateUtil.convertDateToUSFormat(fromDate);
				toDate=DateUtil.convertDateToUSFormat(toDate);
				return SUCCESS;
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

	// merged on 3-11-2014 by kamlesh
	@SuppressWarnings("unchecked")
	public String getblock1counter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				// for pie chart
				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				// Code for Second Quadrant Starts From Here
				summaryList = new ArrayList<FeedbackOverallSummaryBean>();
				int i = 1;
				int counter = 0;
				int count = 0;
				List<String> modesList = new ArrayList<String>();
				{
					modesList.add("Paper-IPD");
					modesList.add("Paper-OPD");
					modesList.add("SMS-IPD");
					modesList.add("SMS-OPD");
					modesList.add("Admin Round");
					modesList.add("Ward Rounds");
				}

				StringBuffer bufer = null;
				List list = null;
				List dataList = new ArrayList();
				int pending = 0, tktOpened = 0, nfa = 0;
				for (String str : modesList)
				{
					FeedbackOverallSummaryBean summary = new FeedbackOverallSummaryBean();
					EscalationActionControlDao EAC = new EscalationActionControlDao();
					summary.setId(i++);
					String[] temp = str.split("-");
					// For Pending,Ticket Opened,No Further Action Required
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("Paper-OPD"))
					{

						bufer = new StringBuffer("select count(DISTINCT feedbck.id),feedbck.status from feedback_status_feed_paperRating as feedbck ");
						bufer.append(getExtraQuery(str));
						bufer.append(" where feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' " + "and feedbck.via_from='" + temp[0] + "' and feedbck.status IN('Pending','Ticket Opened','No Further Action Required') and feedbck.moduleName='FM' ");
						bufer.append(" and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average') and feed.compType='" + temp[1] + "' group by feedbck.status");

						// System.out.println("from new table>>>>>"+bufer);
						dataList.add(cbt.executeAllSelectQuery(bufer.toString(), connectionSpace));
						bufer.delete(0, bufer.length());
					}
					bufer = new StringBuffer("select count(DISTINCT feedbck.id),feedbck.status from feedback_status as feedbck");
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer.append(getExtraQuery(str));
					} else
					{
						bufer.append(getExtraQueryFromPatinfo());
					}
					bufer.append(" where feedbck.escalation_date!='NA' and feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' " + "and feedbck.via_from='" + temp[0] + "' and feedbck.status IN('Pending','Ticket Opened','No Further Action Required') and feedbck.moduleName='FM' ");
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer.append(" and feed.compType='" + temp[1] + "' ");
					}
					bufer.append(" group by feedbck.status ");
					// System.out.println("from status table>>>>>>>>>>" +
					// bufer);
					dataList.add(cbt.executeAllSelectQuery(bufer.toString(), connectionSpace));
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list = (List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{

								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									if (object[1].equals("Pending"))
									{
										pending += Integer.parseInt(object[0].toString());
										nfa += pending;

									} else if (object[1].equals("Ticket Opened"))
									{
										tktOpened += Integer.parseInt(object[0].toString());
										nfa += tktOpened;
									} else if (object[1].equals("No Further Action Required"))
									{

									}

								}
							}
						}
					} else
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", "0");
					}
					summary.setPending("" + pending);
					summary.setTicketOpened("" + tktOpened);
					// summary.setNoFurtherAction("" + nfa);
					summary.setTotalTickets("" + nfa);
					pending = 0;
					nfa = 0;
					tktOpened = 0;
					dataList.clear();
					bufer.setLength(0);
					// For Positive
					bufer.append("select count(DISTINCT feedbck.id) from feedback_status as feedbck ");
					bufer.append(getExtraQuery(str));
					bufer.append(" where feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and feedbck.via_from='" + temp[0] + "' and (feedbck.escalation_date='NA' || feedbck.escalation_date IS NULL) and feedtype.moduleName='FM' and feedtype.fbType='Appreciation' ");
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer.append(" and feed.compType='" + temp[1] + "' ");
					}
					// System.out.println("paper positive>>>>>>> " + bufer);
					counter = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
					summary.setPositive(String.valueOf(counter));
					count = 0;
					if (status != null && status.equalsIgnoreCase("Positive"))
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", String.valueOf(counter));
					}

					// total+=Integer.parseInt(summary.getPositive());
					bufer.setLength(0);
					// For Negative
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer = new StringBuffer("select count(DISTINCT feedbck.id) from feedback_status_feed_paperRating as feedbck  ");
						bufer.append(getExtraQuery(str));
						bufer.append(" where feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and feedbck.via_from='" + temp[0] + "' and feedbck.escalation_date!='NA' and feedbck.moduleName='FM' and feed.compType='" + temp[1] + "' and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average')");
						// System.out.println("paper negative>>>>>>> " + bufer);
						count = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
						bufer.delete(0, bufer.length());
					}
					bufer.append("select count(DISTINCT feedbck.id) from feedback_status as feedbck ");
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer.append(getExtraQuery(str));
					} else
					{
						bufer.append(getExtraQueryFromPatinfo());
					}
					bufer.append(" where feedbck.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and feedbck.via_from='" + temp[0] + "' and feedbck.escalation_date!='NA' and feedbck.moduleName='FM' ");
					if (str.equalsIgnoreCase("Paper-IPD") || str.equalsIgnoreCase("SMS-IPD") || str.equalsIgnoreCase("SMS-OPD") || str.equalsIgnoreCase("Paper-OPD"))
					{
						bufer.append(" and feed.compType='" + temp[1] + "' ");
					}
					// System.out.println("paper negative from status>>>>>>> "+bufer);
					counter = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
					summary.setNegative(String.valueOf(counter + count));
					count = 0;
					if (status != null && status.equalsIgnoreCase("Negative"))
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", String.valueOf(counter));
					}
					// total+=Integer.parseInt(summary.getNegative());
					bufer.setLength(0);

					if (str.equalsIgnoreCase("Mob Tab"))
					{
						str = "Mobile App";
					}
					summary.setMode(str);
					// summary.setTotal(String.valueOf(total));
					total = 0;
					count = 0;
					summaryList.add(summary);
					jsonArray.add(jsonObject);
				}

			}

			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		} else
		{
			return ERROR;
		}

	}

	public StringBuilder getExtraQuery(String mode)
	{
		StringBuilder query = new StringBuilder();
		/*
		 * query.append(
		 * " inner join feedbackdata as feed on feed.patientId=feedbck.patientId "
		 * );
		 */
		query.append(" inner join department dept on feedbck.to_dept_subdept= dept.id ");
		query.append(" inner join feedback_subcategory subcatg on feedbck.subcatg = subcatg.id ");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id ");
		query.append(" LEFT JOIN employee_basic as emp on emp.id=feedbck.allot_to ");
		if (mode.equalsIgnoreCase("Paper-IPD") || mode.equalsIgnoreCase("SMS-IPD") || mode.equalsIgnoreCase("SMS-OPD") || mode.equalsIgnoreCase("Paper-OPD"))
		{
			query.append(" LEFT JOIN feedbackdata as feed on feed.id=feedbck.feedDataId ");
		}

		return query;
	}

	public StringBuilder getExtraQueryFromPatinfo()
	{
		StringBuilder query = new StringBuilder();
		/*
		 * query.append(
		 * " inner join patientinfo as feed on feed.patientId=feedbck.patientId "
		 * );
		 */
		query.append(" inner join department dept on feedbck.to_dept_subdept= dept.id ");
		query.append(" inner join feedback_subcategory subcatg on feedbck.subcatg = subcatg.id ");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id ");
		query.append(" LEFT JOIN employee_basic as emp on emp.id=feedbck.allot_to ");
		return query;
	}

	// to be merged 27-10-2014
	@SuppressWarnings("unchecked")
	public String getblock2counter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				/*System.out.println("????"+fromDate+":::getblockb444442counter::::"+toDate+"????");
				if ((fromDate == null || " ".equalsIgnoreCase("fromdate")) && (toDate == null || "".equalsIgnoreCase("todate")))
				{
					fromDate=(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					toDate=(DateUtil.getCurrentDateIndianFormat());
					System.out.println(fromDate+":::getblock2counter::::"+toDate);
				}*/
				//System.out.println("???"+fromDate+":::getblock2counter::::"+toDate+"????");
				if (getFromDate() != null && getToDate() != null && !"".equalsIgnoreCase(getFromDate()) && !"".equalsIgnoreCase(getToDate()))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						fromDate=DateUtil.convertDateToIndianFormat(getFromDate());
						toDate=DateUtil.convertDateToIndianFormat(getToDate());
					}
				}
				
				String empIdofuser = (String) session.get("empIdofuser");
				setUserType((String) session.get("userTpe"));

				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				String empId = empIdofuser.split("-")[1].trim();
				fromDept = new TreeMap<String, String>();

				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				fromDept = new ActivityBoardHelper().getAllServiceDepts(connectionSpace);
				
				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);
				setDeptActionPojo(deptActionPojo);
				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					int temp = 0, temp1 = 0, temp2 = 0;
					for (DeptActionPojo fpd : deptActionPojo)
					{
						temp = temp + Integer.parseInt(fpd.getTotalAction());
						temp1 = temp1 + Integer.parseInt(fpd.getTotalIsues());
						temp2 = temp2 + Integer.parseInt(fpd.getTotalCapa());

					}
					setSubTotalAction(String.valueOf(temp));
					setSubTotalIssue(String.valueOf(temp1));
					setSubTotalCapa(String.valueOf(temp2));
					float total = (temp + temp1) + temp2;
					int temp3, temp4, temp5;

					for (DeptActionPojo fpd : deptActionPojo)
					{
						float temp6;
						float temp7;
						temp3 = Integer.parseInt(fpd.getTotalAction());
						temp4 = Integer.parseInt(fpd.getTotalIsues());
						temp5 = Integer.parseInt(fpd.getTotalCapa());
						temp6 = (temp3 + temp4) + temp5;
						temp7 = Math.round(((float) (temp6 / total) * 100));
						fpd.setPercent(String.valueOf((int) temp7));
						// System.out.println(temp6+"/"+total+"="+fpd.getPercent(
						// ));
					}
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					getRatingCounter(deptId);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		} else
		{
			return ERROR;
		}
	}

	// to be merged 2nd block pie 27-10-2014

	public String getJsonDataPieBlock2()
	{
		// System.out.println("FeedbackDashboard.getJsonData()");
		if (ValidateSession.checkSession())
		{
			try
			{
				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);
				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					JSONObject jsonObject = new JSONObject();
					jsonArray = new JSONArray();
					float temp1 = 0f;
					for (DeptActionPojo fpd : deptActionPojo)
					{
						temp1 = temp1 + Integer.parseInt(fpd.getTotalIsues());
					}

					float temp6 = 0f;
					float temp7 = 0f;

					for (DeptActionPojo fpd : deptActionPojo)
					{

						temp6 = Integer.parseInt(fpd.getTotalIsues());

						temp7 = ((float) (temp6 / temp1) * 100);
						fpd.setPercent(String.valueOf(temp7));
						// System.out.println(temp6+"/"+total+"="+fpd.getPercent(
						// ));
					}
					for (DeptActionPojo pojo : deptActionPojo)
					{
						jsonObject.put("department", pojo.getDeptName());
						jsonObject.put("percent", pojo.getPercent());
						jsonObject.put("issue", (int) temp1);
						jsonArray.add(jsonObject);
					}
				}

				return SUCCESS;
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

	public String getFeedbackAction()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				FeedbackDashboardDao fd = new FeedbackDashboardDao();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (block != null && block.equalsIgnoreCase("2ndBlock"))
				{
					feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardList = fd.getFeedbackCounters(connectionSpace);
					if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardList)
						{
							temp = temp + fp.getActionCounter();
							temp1 = temp1 + Integer.parseInt(fp.getActionToday());
						}
						totalPending = String.valueOf(temp);
						totalCounter = String.valueOf(temp1);
					}
				} else if (block != null && block.equalsIgnoreCase("7rdBlock"))
				{
					feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardType = fd.getfeedbackTypelist(connectionSpace, null, "", "");
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				} else if (block != null && block.equalsIgnoreCase("4rdBlock"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardType = fd.getfeedbackTypelist(connectionSpace, deptId, "", "");
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				}
				return SUCCESS;
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

	@SuppressWarnings("rawtypes")
	public String getDashboardShowNormal()
	{
		// System.out.println("FeedbackDashboard.getDashboardShowNormal()");
		if (ValidateSession.checkSession())
		{
			try
			{
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackDashboardDao FDD = new FeedbackDashboardDao();
				String empIdLoggedUSer = (String) session.get("empIdofuser");

				List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
				if (fromDate == null || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase(" "))
				{

					setFromDate(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					setToDate(DateUtil.getCurrentDateIndianFormat());

				}

				// First Quadrant ....
				feedTicketList = new ArrayList<FeedbackTicketPojo>();
				feedTicketList = FDD.getFeedTicketDashboardNormal(connectionSpace, deptId, fromDate, toDate);
				/*
				 * if (deptId!=null && deptId.size()>0) {
				 * feedTicketList=FDD.getFeedTicketDashboardNormal
				 * (connectionSpace,deptId); }
				 */

				// Second Quadrant....
				feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
				if (deptId != null && deptId.size() > 0)
				{
					feedDataDashboardType = FDD.getfeedbackTypelist(connectionSpace, deptId, fromDate, toDate);
					// System.out.println("<< feedDataDashboardType >>>> "+
					// feedDataDashboardType.size());
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				}

				// System.out.println("feedDataDashboardType size is as ...."+
				// feedDataDashboardType.size());

				// Third Quadrant....
				feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
				if (deptId != null && deptId.size() > 0)
				{
					feedDataDashboardList = FDD.getFeedbackCategoryCounters(connectionSpace, deptId, fromDate, toDate);
					if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
					{
						int temp = 0;
						for (FeedbackDataPojo fp : feedDataDashboardList)
						{
							temp = temp + fp.getActionCounter();
						}
						totalPending = String.valueOf(temp);
					}
				}

				/*
				 * // for second QuardrantPie List
				 * dataList=cbt.executeAllSelectQuery(
				 * " SELECT COUNT(*),ft.fbType FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND fs.to_dept_subdept IN "
				 * +deptId.toString().replace("[",
				 * "(").replace("]",")")+" GROUP BY fbType", connectionSpace);
				 * if (dataList!=null && dataList.size()>0) {
				 * pieFeedbackCounter=new LinkedHashMap<Integer, String>(); for
				 * (Iterator iterator = dataList.iterator();
				 * iterator.hasNext();) { Object[] object = (Object[])
				 * iterator.next(); if (object[0]!=null && object[1]!=null) {
				 * pieFeedbackCounter
				 * .put(Integer.parseInt(object[0].toString()),
				 * object[1].toString()); } } }
				 */

				// Fourth Quadrant....
				dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				if (deptId != null && deptId.size() > 0)
				{
					dashPojoList = new FeedbackDashboardDao().getDashboardLevelStatus(connectionSpace, deptId, fromDate, toDate);
					if (dashPojoList != null && dashPojoList.size() > 0)
					{
						int temp5 = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0;
						for (FeedbackPojoDashboard fpd : dashPojoList)
						{
							temp1 = temp1 + Integer.parseInt(fpd.getPendingL1());
							temp2 = temp2 + Integer.parseInt(fpd.getPendingL2());
							temp3 = temp3 + Integer.parseInt(fpd.getPendingL3());
							temp4 = temp4 + Integer.parseInt(fpd.getPendingL4());
							temp5 = temp5 + Integer.parseInt(fpd.getPendingL5());
						}
						totalLevel1 = String.valueOf(temp1);
						totalLevel2 = String.valueOf(temp2);
						totalLevel3 = String.valueOf(temp3);
						totalLevel4 = String.valueOf(temp4);
						totalLevel5 = String.valueOf(temp5);
					}
				}
				getRatingCounter(deptId);
				return SUCCESS;
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

	@SuppressWarnings({ "unused", "unchecked" })
	public String getRatingCounter(List<String> deptId)
	{
		String returnResult = ERROR;
		String userType = (String) session.get("userTpe");

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String loggedEmpId = "";
				String dept_subdept_id = "";
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpId = object[5].toString();
						dept_subdept_id = object[3].toString();
					}
				}
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuffer query = new StringBuffer();
				StringBuilder colName = new StringBuilder("");
				List modeList = new ArrayList<String>();
				if(type!=null && type.equalsIgnoreCase("IPD"))
				{
					modeList.add("IPD");
				}
				else if(type!=null && type.equalsIgnoreCase("OPD"))
				{
					modeList.add("OPD");
				}
				else
				{	
					modeList.add("IPD");
				//	modeList.add("OPD");
				}	
				//System.out.println(type+"mode list"+modeList.size());
				for (Iterator iterator = modeList.iterator(); iterator.hasNext();)
				{
					String object = (String) iterator.next();

					if (object.equalsIgnoreCase("IPD"))
					{
						query.append("select fprc.field_name,fprc.field_value from feedback_paperform_rating_configuration as fprc");
					} 
					else
					{
						query.append("select fprc.field_name,fprc.field_value from feedback_paperform_rating_configuration_opd as fprc");
					}

					query.append(" INNER JOIN feedback_category AS fc ON fprc.field_name=fc.categoryName ");
					query.append(" INNER JOIN feedback_type AS ft ON ft.id=fc.fbType");
					if (userType != null && ("M".equalsIgnoreCase(userType)))
					{
						if (this.deptId != null && !"-1".equalsIgnoreCase(this.deptId))
						{
							query.append(" where ft.dept_subdept='" + this.deptId + "' ");
						}
					}
					if (userType != null && ("H".equalsIgnoreCase(userType) || "N".equalsIgnoreCase(userType)))
					{
						query.append(" where ft.dept_subdept='" + dept_subdept_id + "' ");
					}
					query.append(" GROUP BY fprc.field_name ORDER BY fc.id ");
					//System.out.println("quert::::::::::"+query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						int count = 1;
						for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0] != null && object2[1] != null)
							{
								QualityPojo pojo = new QualityPojo();
								pojo.setId(count += 1);
								pojo.setCatName(object2[0].toString());
								pojo.setCatColName(object2[1].toString());
								pojo.setMode(object);
								pojo = getRatingDetails(pojo, connectionSpace);
								Ratinginfo.add(pojo);
							}
						}
					}
					query.delete(0, query.length());
					data.clear();
				}// for end

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}

		return returnResult;
	}

	public QualityPojo getRatingDetails(QualityPojo pojo, SessionFactory connection)
	{
		int rating = 0,total=0;
		
		StringBuilder builder = new StringBuilder("");
		builder.append(" select " + pojo.getCatColName() + " from feedback_paper_rating AS fpr INNER JOIN feedbackdata AS feed ON feed.id=fpr.max_id_feeddbackdata ");
		builder.append(" where feed.compType='" + pojo.getMode() + "' and feed.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		//System.out.println("##" + pojo.getCatColName() + " QRY:::" + builder.toString());
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			int rat1 = 0, rat2 = 0, rat3 = 0, rat4 = 0, rat5 = 0;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object ob = (Object) iterator.next();
				if (ob != null && !ob.toString().equalsIgnoreCase("NA"))
				{
					try
					{
						rating = Integer.parseInt(ob.toString());
					} catch (Exception e)
					{
						rating = 0;
						e.printStackTrace();
					}

					if (rating == 1)
					{
						rat1 += 1;
					} else if (rating == 2)
					{
						rat2 += 1;
					} else if (rating == 3)
					{
						rat3 += 1;
					} else if (rating == 4)
					{
						rat4 += 1;
					} else if (rating == 5)
					{
						rat5 += 1;
					}
				}
			}
			total=rat1+rat2+rat3+rat4+rat5;
			//System.out.println("tatal>>>>"+total);
			if(rat1==0)
			{	
				pojo.setRat1(String.valueOf(rat1));
			}
			else
			{
				pojo.setRat1(String.valueOf(rat1)+" ("+(Math.round(((double)rat1/total)*100.0))+"%)");
			}
			if(rat2==0)
			{	
				pojo.setRat2(String.valueOf(rat2));
			}
			else
			{
				pojo.setRat2(String.valueOf(rat2)+" ("+(Math.round(((double)rat2/total)*100.0))+"%)");
			}
			if(rat3==0)
			{	
				pojo.setRat3(String.valueOf(rat3));
			}
			else
			{
				pojo.setRat3(String.valueOf(rat3)+" ("+(Math.round(((double)rat3/total)*100.0))+"%)");
			}
			if(rat4==0)
			{	
				pojo.setRat4(String.valueOf(rat4));
			}
			else
			{
				pojo.setRat4(String.valueOf(rat4)+" ("+(Math.round(((double)rat4/total)*100.0))+"%)");
			}
			if(rat5==0)
			{	
				pojo.setRat5(String.valueOf(rat5));
			}
			else
			{
				pojo.setRat5(String.valueOf(rat5)+" ("+(Math.round(((double)rat5/total)*100.0))+"%)");
			}
			total=0;
			/*pojo.setRat2(String.valueOf(rat2));
			pojo.setRat3(String.valueOf(rat3));
			pojo.setRat4(String.valueOf(rat4));
			pojo.setRat5(String.valueOf(rat5));*/
		}
		return pojo;
	}

	public String beforeDocumentresolveDownload()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (feedId != null && !feedId.equalsIgnoreCase(""))
				{
					String query = null;
					if (type != null && type.equalsIgnoreCase("1"))
					{
						query = "SELECT resolveDoc FROM feedback_status WHERE id=" + feedId;
					} else
					{
						query = "SELECT resolveDoc1 FROM feedback_status WHERE id=" + feedId;
					}
					// System.out.println("QUERY :::  "+query);
					List dataCount = cbt.executeAllSelectQuery(query, connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						docMap = new LinkedHashMap<String, String>();
						String str = null;
						Object object = null;
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							object = (Object) iterator.next();
							if (object != null)
							{
								str = object.toString().substring(object.toString().lastIndexOf("//") + 2, object.toString().length());
								documentName = str.substring(14, str.length());
								docMap.put(object.toString(), documentName);
							}
						}
						returnResult = SUCCESS;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String docDownload()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				File file = new File(fileName);
				String str = fileName.substring(fileName.lastIndexOf("//") + 2, fileName.length());
				documentName = str.substring(14, str.length());
				fileName = documentName;
				if (file.exists())
				{
					fileInputStream = new FileInputStream(file);
					return SUCCESS;
				} else
				{
					addActionError("File dose not exist");
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

	// for bar char 2ndblock to be merged 22-10-2014
	public String getJsonData()
	{
		// System.out.println("FeedbackDashboard.getJsonData()");
		if (ValidateSession.checkSession())
		{
			try
			{

				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);

				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					for (DeptActionPojo pojo : deptActionPojo)
					{
						jsonObject.put("department", pojo.getDeptName());
						// System.out.println("DEpart:  "+pojo.getDeptName());
						jsonObject.put("Action", pojo.getTotalAction());
						jsonObject.put("CAPA", pojo.getTotalCapa());
						jsonObject.put("Issue", pojo.getTotalIsues());
						jsonArray.add(jsonObject);
					}
				}

				return SUCCESS;
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

	public List<FeedbackPojoDashboard> getDashPojoList()
	{
		return dashPojoList;
	}

	public void setDashPojoList(List<FeedbackPojoDashboard> dashPojoList)
	{
		this.dashPojoList = dashPojoList;
	}

	public List<FeedbackOverallSummaryBean> getSummaryList()
	{
		return summaryList;
	}

	public void setSummaryList(List<FeedbackOverallSummaryBean> summaryList)
	{
		this.summaryList = summaryList;
	}

	public List<FeedbackDataPojo> getFeedDataDashboardList()
	{
		return feedDataDashboardList;
	}

	public void setFeedDataDashboardList(List<FeedbackDataPojo> feedDataDashboardList)
	{
		this.feedDataDashboardList = feedDataDashboardList;
	}

	public List<FeedbackTicketPojo> getFeedTicketList()
	{
		return feedTicketList;
	}

	public void setFeedTicketList(List<FeedbackTicketPojo> feedTicketList)
	{
		this.feedTicketList = feedTicketList;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getGraphHeader()
	{
		return graphHeader;
	}

	public void setGraphHeader(String graphHeader)
	{
		this.graphHeader = graphHeader;
	}

	public String getGraphDescription()
	{
		return graphDescription;
	}

	public void setGraphDescription(String graphDescription)
	{
		this.graphDescription = graphDescription;
	}

	public String getYAxisHeader()
	{
		return yAxisHeader;
	}

	public void setYAxisHeader(String axisHeader)
	{
		yAxisHeader = axisHeader;
	}

	public String getXAxisHeader()
	{
		return xAxisHeader;
	}

	public void setXAxisHeader(String axisHeader)
	{
		xAxisHeader = axisHeader;
	}

	public Map<Integer, String> getPieFeedbackCounter()
	{
		return pieFeedbackCounter;
	}

	public void setPieFeedbackCounter(Map<Integer, String> pieFeedbackCounter)
	{
		this.pieFeedbackCounter = pieFeedbackCounter;
	}

	public Map<String, Integer> getPieYesNoMap()
	{
		return pieYesNoMap;
	}

	public void setPieYesNoMap(Map<String, Integer> pieYesNoMap)
	{
		this.pieYesNoMap = pieYesNoMap;
	}

	public Map<String, Integer> getPieNegFeedStatus()
	{
		return pieNegFeedStatus;
	}

	public void setPieNegFeedStatus(Map<String, Integer> pieNegFeedStatus)
	{
		this.pieNegFeedStatus = pieNegFeedStatus;
	}

	public Map<String, String> getFeedbackPieCounters()
	{
		return feedbackPieCounters;
	}

	public void setFeedbackPieCounters(Map<String, String> feedbackPieCounters)
	{
		this.feedbackPieCounters = feedbackPieCounters;
	}

	public String getTotalPending()
	{
		return totalPending;
	}

	public void setTotalPending(String totalPending)
	{
		this.totalPending = totalPending;
	}

	public String getTotalLevel1()
	{
		return totalLevel1;
	}

	public void setTotalLevel1(String totalLevel1)
	{
		this.totalLevel1 = totalLevel1;
	}

	public String getTotalLevel2()
	{
		return totalLevel2;
	}

	public void setTotalLevel2(String totalLevel2)
	{
		this.totalLevel2 = totalLevel2;
	}

	public String getTotalLevel3()
	{
		return totalLevel3;
	}

	public void setTotalLevel3(String totalLevel3)
	{
		this.totalLevel3 = totalLevel3;
	}

	public String getTotalLevel4()
	{
		return totalLevel4;
	}

	public void setTotalLevel4(String totalLevel4)
	{
		this.totalLevel4 = totalLevel4;
	}

	public String getTotalLevel5()
	{
		return totalLevel5;
	}

	public void setTotalLevel5(String totalLevel5)
	{
		this.totalLevel5 = totalLevel5;
	}

	public String getTotalSnooze()
	{
		return totalSnooze;
	}

	public void setTotalSnooze(String totalSnooze)
	{
		this.totalSnooze = totalSnooze;
	}

	public String getTotalHighPriority()
	{
		return totalHighPriority;
	}

	public void setTotalHighPriority(String totalHighPriority)
	{
		this.totalHighPriority = totalHighPriority;
	}

	public String getTotalIgnore()
	{
		return totalIgnore;
	}

	public void setTotalIgnore(String totalIgnore)
	{
		this.totalIgnore = totalIgnore;
	}

	public String getTotalReAssign()
	{
		return totalReAssign;
	}

	public void setTotalReAssign(String totalReAssign)
	{
		this.totalReAssign = totalReAssign;
	}

	public String getTotalResolve()
	{
		return totalResolve;
	}

	public void setTotalResolve(String totalResolve)
	{
		this.totalResolve = totalResolve;
	}

	public String getTotalCounter()
	{
		return totalCounter;
	}

	public void setTotalCounter(String totalCounter)
	{
		this.totalCounter = totalCounter;
	}

	public List<FeedbackDataPojo> getFeedDataDashboardType()
	{
		return feedDataDashboardType;
	}

	public void setFeedDataDashboardType(List<FeedbackDataPojo> feedDataDashboardType)
	{
		this.feedDataDashboardType = feedDataDashboardType;
	}

	public String getTotalData()
	{
		return totalData;
	}

	public void setTotalData(String totalData)
	{
		this.totalData = totalData;
	}

	public String getToday()
	{
		return today;
	}

	public void setToday(String today)
	{
		this.today = today;
	}

	public String getBlock()
	{
		return block;
	}

	public void setBlock(String block)
	{
		this.block = block;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public Map<String, String> getDocMap()
	{
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap)
	{
		this.docMap = docMap;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public List<TicketAllotmentPojo> getAllotPojo()
	{
		return allotPojo;
	}

	public void setAllotPojo(List<TicketAllotmentPojo> allotPojo)
	{
		this.allotPojo = allotPojo;
	}

	public List<FeedbackAnalysis> getAnalysisPojo()
	{
		return analysisPojo;
	}

	public void setAnalysisPojo(List<FeedbackAnalysis> analysisPojo)
	{
		this.analysisPojo = analysisPojo;
	}

	public List<DeptActionPojo> getDeptActionPojo()
	{
		return deptActionPojo;
	}

	public void setDeptActionPojo(List<DeptActionPojo> deptActionPojo)
	{
		this.deptActionPojo = deptActionPojo;
	}

	public String getSubTotalIssue()
	{
		return subTotalIssue;
	}

	public void setSubTotalIssue(String subTotalIssue)
	{
		this.subTotalIssue = subTotalIssue;
	}

	public String getSubTotalAction()
	{
		return SubTotalAction;
	}

	public void setSubTotalAction(String subTotalAction)
	{
		SubTotalAction = subTotalAction;
	}

	public String getSubTotalCapa()
	{
		return SubTotalCapa;
	}

	public void setSubTotalCapa(String subTotalCapa)
	{
		SubTotalCapa = subTotalCapa;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getTotalNoted()
	{
		return totalNoted;
	}

	public void setTotalNoted(String totalNoted)
	{
		this.totalNoted = totalNoted;
	}

	public List<NegativeFeedbackStagePojo> getNegativeFeedList()
	{
		return negativeFeedList;
	}

	public void setNegativeFeedList(List<NegativeFeedbackStagePojo> negativeFeedList)
	{
		this.negativeFeedList = negativeFeedList;
	}

	public String getTotalTO()
	{
		return totalTO;
	}

	public void setTotalTO(String totalTO)
	{
		this.totalTO = totalTO;
	}

	public String getTotalNFA()
	{
		return totalNFA;
	}

	public void setTotalNFA(String totalNFA)
	{
		this.totalNFA = totalNFA;
	}

	public String getTotalRating1()
	{
		return totalRating1;
	}

	public void setTotalRating1(String totalRating1)
	{
		this.totalRating1 = totalRating1;
	}

	public String getTotalRating2()
	{
		return totalRating2;
	}

	public void setTotalRating2(String totalRating2)
	{
		this.totalRating2 = totalRating2;
	}

	public String getTotalRating3()
	{
		return totalRating3;
	}

	public void setTotalRating3(String totalRating3)
	{
		this.totalRating3 = totalRating3;
	}

	public String getTotalRating4()
	{
		return totalRating4;
	}

	public void setTotalRating4(String totalRating4)
	{
		this.totalRating4 = totalRating4;
	}

	public String getTotalRating5()
	{
		return totalRating5;
	}

	public void setTotalRating5(String totalRating5)
	{
		this.totalRating5 = totalRating5;
	}

	public List<QualityPojo> getRatinginfo()
	{
		return Ratinginfo;
	}

	public void setRatinginfo(List<QualityPojo> ratinginfo)
	{
		Ratinginfo = ratinginfo;
	}

	public TreeMap<String, String> getFromDept()
	{
		return fromDept;
	}

	public void setFromDept(TreeMap<String, String> fromDept)
	{
		this.fromDept = fromDept;
	}
	public List<NegativeFeedbackStagePojo> getChooseList()
	{
		return chooseList;
	}

	public void setChooseList(List<NegativeFeedbackStagePojo> chooseList)
	{
		this.chooseList = chooseList;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	

}