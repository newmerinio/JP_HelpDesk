package com.Over2Cloud.ctrl.wfpm.report;

public class IVRSDataBean
{

	public IVRSDataBean()
	{
	}

	private long		id;
	private String	name;
	private String	mobileNo;
	private String	availability;
	private String	totalCall				= "0";
	private String	productiveCall	= "0";
	private String	totalSale				= "0";
	private String	newOutlet				= "0";
	private String	date;
	private String	time;
	private String	talkTime;
	private String	pulse;
	private String	target;
	private String	achive;
	private String	achivePerc;

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getAvailability()
	{
		return availability;
	}

	public void setAvailability(String availability)
	{
		this.availability = availability;
	}

	public String getTotalCall()
	{
		return totalCall;
	}

	public void setTotalCall(String totalCall)
	{
		this.totalCall = totalCall;
	}

	public String getProductiveCall()
	{
		return productiveCall;
	}

	public void setProductiveCall(String productiveCall)
	{
		this.productiveCall = productiveCall;
	}

	public String getTotalSale()
	{
		return totalSale;
	}

	public void setTotalSale(String totalSale)
	{
		this.totalSale = totalSale;
	}

	public String getNewOutlet()
	{
		return newOutlet;
	}

	public void setNewOutlet(String newOutlet)
	{
		this.newOutlet = newOutlet;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getTalkTime()
	{
		return talkTime;
	}

	public void setTalkTime(String talkTime)
	{
		this.talkTime = talkTime;
	}

	public String getPulse()
	{
		return pulse;
	}

	public void setPulse(String pulse)
	{
		this.pulse = pulse;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public String getAchive()
	{
		return achive;
	}

	public void setAchive(String achive)
	{
		this.achive = achive;
	}

	public String getAchivePerc()
	{
		return achivePerc;
	}

	public void setAchivePerc(String achivePerc)
	{
		this.achivePerc = achivePerc;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
