package com.Over2Cloud.ctrl.asset;

import java.util.List;

public class AssetDashboardBean implements Comparable<AssetDashboardBean>
{
	private String departName;
	private String departId;
	private String totalAllot = "0";
	private String totalFree = "0";
	private String next7Days = "0";
	private String next30Days = "0";
	private String next90Days = "0";
	private String next180Days = "0";
	private String assetName = "NA";
	private String assetserial;
	private String model;
	private String brand;
	private String assetid;
	private String supportCunter;
	private String supportFor;
	private String totalAsset = "0";
	private String type;
	private String onService = "0";
	private int assetScore=0;
	private String open_date;
	private String open_time;
	private String status;
	private String feed_by;
	private String ticket_no;
	private int id;
	private String assetType;
	private String empName;
	private String frequency;
	private String assetcode;
	private String dueDate="0";
	private String dueTime;
	private String remindTo;
	private String escL1;
	private String escL1Duration;
	private String mappedTeam;
    private String empId;
    private String actionStatus;
    private String actionType;
    private String nodeName;
    private String softwareName;
    private String version;
	private List<AssetDashboardBean> assetList;
	private List<AssetDashboardBean> spareList;
	private String onTime="0";
	private String offTime="0";
	private String missed="0";
	private String snooze="0";
	private String perOnTime="0";
	private String perOffTime="0";
	private String perMissed="0";
	private String feedId="0";
	private String ignore="0"; 
	private String pending="0";
	private String level1="0";
	private String level2="0";
	private String level3="0";
	private String level4="0";
	private String level5="0";
	private String highpriority="0";
	private String resolve="0";
	private String feedback_by_subdept;
	private String feedback_catg;
	private String feedback_subcatg;
	private String counter;
	private String outletName;
	private String totalPM="0";
	private String totalSupport="0";
	private String missedPM="0";
	private String missedSupport="0";
	private String next7DaysPM = "0";
	private String next30DaysPM = "0";
	private String next90DaysPM = "0";
	private String reminderIdPM = "0";
	private String reminderIdSupport = "0";
	private String actionTakenDate;
	private String comments;
	private String other;
	private String totalBreakdown;
	private String escFlag;
	private String escLevel;
	private String RCA;
	private String amc="No";

	
	@Override
	public int compareTo(AssetDashboardBean ADB)
	{
		return ADB.getAssetScore()-this.assetScore;
	}
	
	public String getNext90Days()
	{
		return next90Days;
		
	}

	public void setNext90Days(String next90Days)
	{
		this.next90Days = next90Days;
	}

	public String getNext180Days()
	{
		return next180Days;
	}

	public void setNext180Days(String next180Days)
	{
		this.next180Days = next180Days;
	}

	public int getAssetScore()
	{
		return assetScore;
	}

	public void setAssetScore(int assetScore)
	{
		this.assetScore = assetScore;
	}

	public List<AssetDashboardBean> getSpareList()
	{
		return spareList;
	}

	public void setSpareList(List<AssetDashboardBean> spareList)
	{
		this.spareList = spareList;
	}

	public String getOnService()
	{
		return onService;
	}

	public void setOnService(String onService)
	{
		this.onService = onService;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTotalAsset()
	{
		return totalAsset;
	}

	public void setTotalAsset(String totalAsset)
	{
		this.totalAsset = totalAsset;
	}

	public String getSupportCunter()
	{
		return supportCunter;
	}

	public void setSupportCunter(String supportCunter)
	{
		this.supportCunter = supportCunter;
	}

	public String getSupportFor()
	{
		return supportFor;
	}

	public void setSupportFor(String supportFor)
	{
		this.supportFor = supportFor;
	}

	public String getAssetid()
	{
		return assetid;
	}

	public void setAssetid(String assetid)
	{
		this.assetid = assetid;
	}

	public String getAssetName()
	{
		return assetName;
	}

	public void setAssetName(String assetName)
	{
		this.assetName = assetName;
	}

	public String getAssetserial()
	{
		return assetserial;
	}

	public void setAssetserial(String assetserial)
	{
		this.assetserial = assetserial;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public List<AssetDashboardBean> getAssetList()
	{
		return assetList;
	}

	public void setAssetList(List<AssetDashboardBean> assetList)
	{
		this.assetList = assetList;
	}

	public String getDepartName()
	{
		return departName;
	}

	public void setDepartName(String departName)
	{
		this.departName = departName;
	}

	public String getDepartId()
	{
		return departId;
	}

	public void setDepartId(String departId)
	{
		this.departId = departId;
	}

	public String getTotalAllot()
	{
		return totalAllot;
	}

	public void setTotalAllot(String totalAllot)
	{
		this.totalAllot = totalAllot;
	}

	public String getTotalFree()
	{
		return totalFree;
	}

	public void setTotalFree(String totalFree)
	{
		this.totalFree = totalFree;
	}

	public String getNext7Days()
	{
		return next7Days;
	}

	public void setNext7Days(String next7Days)
	{
		this.next7Days = next7Days;
	}
	public String getNext30Days()
	{
		return next30Days;
	}

	public void setNext30Days(String next30Days)
	{
		this.next30Days = next30Days;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getFeed_by()
	{
		return feed_by;
	}

	public void setFeed_by(String feed_by)
	{
		this.feed_by = feed_by;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getAssetType()
	{
		return assetType;
	}

	public void setAssetType(String assetType)
	{
		this.assetType = assetType;
	}
	
	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getAssetcode()
	{
		return assetcode;
	}

	public void setAssetcode(String assetcode)
	{
		this.assetcode = assetcode;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getDueTime()
	{
		return dueTime;
	}

	public void setDueTime(String dueTime)
	{
		this.dueTime = dueTime;
	}

	public String getRemindTo()
	{
		return remindTo;
	}

	public void setRemindTo(String remindTo)
	{
		this.remindTo = remindTo;
	}

	public String getEscL1()
	{
		return escL1;
	}

	public void setEscL1(String escL1)
	{
		this.escL1 = escL1;
	}

	public String getEscL1Duration()
	{
		return escL1Duration;
	}

	public void setEscL1Duration(String escL1Duration)
	{
		this.escL1Duration = escL1Duration;
	}
	
	public String getMappedTeam()
	{
		return mappedTeam;
	}

	public void setMappedTeam(String mappedTeam)
	{
		this.mappedTeam = mappedTeam;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public String getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}
	public String getNodeName()
	{
		return nodeName;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	public String getSoftwareName()
	{
		return softwareName;
	}

	public void setSoftwareName(String softwareName)
	{
		this.softwareName = softwareName;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getOnTime()
	{
		return onTime;
	}

	public void setOnTime(String onTime)
	{
		this.onTime = onTime;
	}

	public String getOffTime()
	{
		return offTime;
	}

	public void setOffTime(String offTime)
	{
		this.offTime = offTime;
	}

	public String getMissed()
	{
		return missed;
	}

	public void setMissed(String missed)
	{
		this.missed = missed;
	}

	public String getSnooze()
	{
		return snooze;
	}

	public void setSnooze(String snooze)
	{
		this.snooze = snooze;
	}

	public String getPerOnTime()
	{
		return perOnTime;
	}

	public void setPerOnTime(String perOnTime)
	{
		this.perOnTime = perOnTime;
	}

	public String getPerOffTime()
	{
		return perOffTime;
	}

	public void setPerOffTime(String perOffTime)
	{
		this.perOffTime = perOffTime;
	}

	public String getPerMissed()
	{
		return perMissed;
	}

	public void setPerMissed(String perMissed)
	{
		this.perMissed = perMissed;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public String getIgnore()
	{
		return ignore;
	}

	public void setIgnore(String ignore)
	{
		this.ignore = ignore;
	}

	public String getFeedback_by_subdept()
	{
		return feedback_by_subdept;
	}

	public void setFeedback_by_subdept(String feedback_by_subdept)
	{
		this.feedback_by_subdept = feedback_by_subdept;
	}

	public String getFeedback_catg()
	{
		return feedback_catg;
	}

	public void setFeedback_catg(String feedback_catg)
	{
		this.feedback_catg = feedback_catg;
	}

	public String getFeedback_subcatg()
	{
		return feedback_subcatg;
	}

	public void setFeedback_subcatg(String feedback_subcatg)
	{
		this.feedback_subcatg = feedback_subcatg;
	}

	public String getCounter()
	{
		return counter;
	}

	public void setCounter(String counter)
	{
		this.counter = counter;
	}

	public String getOutletName()
	{
		return outletName;
	}

	public void setOutletName(String outletName)
	{
		this.outletName = outletName;
	}

	public String getTotalPM()
	{
		return totalPM;
	}

	public void setTotalPM(String totalPM)
	{
		this.totalPM = totalPM;
	}

	public String getTotalSupport()
	{
		return totalSupport;
	}

	public void setTotalSupport(String totalSupport)
	{
		this.totalSupport = totalSupport;
	}

	public String getMissedPM()
	{
		return missedPM;
	}

	public void setMissedPM(String missedPM)
	{
		this.missedPM = missedPM;
	}

	public String getMissedSupport()
	{
		return missedSupport;
	}

	public void setMissedSupport(String missedSupport)
	{
		this.missedSupport = missedSupport;
	}

	public String getNext7DaysPM()
	{
		return next7DaysPM;
	}

	public void setNext7DaysPM(String next7DaysPM)
	{
		this.next7DaysPM = next7DaysPM;
	}

	public String getNext30DaysPM()
	{
		return next30DaysPM;
	}

	public void setNext30DaysPM(String next30DaysPM)
	{
		this.next30DaysPM = next30DaysPM;
	}

	public String getNext90DaysPM()
	{
		return next90DaysPM;
	}

	public void setNext90DaysPM(String next90DaysPM)
	{
		this.next90DaysPM = next90DaysPM;
	}

	public String getReminderIdPM()
	{
		return reminderIdPM;
	}

	public void setReminderIdPM(String reminderIdPM)
	{
		this.reminderIdPM = reminderIdPM;
	}

	public String getReminderIdSupport()
	{
		return reminderIdSupport;
	}

	public void setReminderIdSupport(String reminderIdSupport)
	{
		this.reminderIdSupport = reminderIdSupport;
	}

	public String getActionTakenDate()
	{
		return actionTakenDate;
	}

	public void setActionTakenDate(String actionTakenDate)
	{
		this.actionTakenDate = actionTakenDate;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getOther()
	{
		return other;
	}

	public void setOther(String other)
	{
		this.other = other;
	}

	public String getTotalBreakdown()
	{
		return totalBreakdown;
	}

	public void setTotalBreakdown(String totalBreakdown)
	{
		this.totalBreakdown = totalBreakdown;
	}

	public String getEscFlag()
	{
		return escFlag;
	}

	public void setEscFlag(String escFlag)
	{
		this.escFlag = escFlag;
	}

	public String getEscLevel()
	{
		return escLevel;
	}

	public void setEscLevel(String escLevel)
	{
		this.escLevel = escLevel;
	}

	public String getRCA()
	{
		return RCA;
	}

	public void setRCA(String rca)
	{
		RCA = rca;
	}

	public String getPending()
	{
		return pending;
	}

	public void setPending(String pending)
	{
		this.pending = pending;
	}

	public String getLevel1()
	{
		return level1;
	}

	public void setLevel1(String level1)
	{
		this.level1 = level1;
	}

	public String getLevel2()
	{
		return level2;
	}

	public void setLevel2(String level2)
	{
		this.level2 = level2;
	}

	public String getLevel3()
	{
		return level3;
	}

	public void setLevel3(String level3)
	{
		this.level3 = level3;
	}

	public String getLevel4()
	{
		return level4;
	}

	public void setLevel4(String level4)
	{
		this.level4 = level4;
	}

	public String getLevel5()
	{
		return level5;
	}

	public void setLevel5(String level5)
	{
		this.level5 = level5;
	}

	public String getHighpriority()
	{
		return highpriority;
	}

	public void setHighpriority(String highpriority)
	{
		this.highpriority = highpriority;
	}

	public String getResolve()
	{
		return resolve;
	}

	public void setResolve(String resolve)
	{
		this.resolve = resolve;
	}

	public String getAmc()
	{
		return amc;
	}

	public void setAmc(String amc)
	{
		this.amc = amc;
	}

	
	
}
