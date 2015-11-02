package com.Over2Cloud.ctrl.wfpm.report;

public class KPIReportKPIPojo extends KPIReportMainPojo
{
	private String kpi;
	private String offering;
	private String targetAlloted;

	public String getKpi()
	{
		return kpi;
	}

	public void setKpi(String kpi)
	{
		this.kpi = kpi;
	}

	public String getTargetAlloted()
	{
		return targetAlloted;
	}

	public void setTargetAlloted(String targetAlloted)
	{
		this.targetAlloted = targetAlloted;
	}

	public String getOffering()
	{
		return offering;
	}

	public void setOffering(String offering)
	{
		this.offering = offering;
	}

}
