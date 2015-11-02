package com.Over2Cloud.ctrl.wfpm.industry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class IndustryMapHelper
{
	private int			clientForOfferingDetails	= 0;
	private String	verticalname;
	private String	offeringname;
	private String	subofferingname;
	private String	variantname;
	private String	subvariantsize;
	private String	subIndustry;

	public boolean setAddPageDataForOffering(SessionFactory connectionSpace, HttpServletRequest request, String userName, String accountID)
	{
		boolean flag = false;
		String industryDDHeadingName;
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_industry_configuration", accountID, connectionSpace, "", 0,
				"table_name", "industry_configuration");
		for (GridDataPropertyView gdp : offeringLevel1)
		{
			if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("industry"))
			{
				industryDDHeadingName = gdp.getHeadingName();
			}
		}

		return flag;
	}

	public String getVerticalname()
	{
		return verticalname;
	}

	public void setVerticalname(String verticalname)
	{
		this.verticalname = verticalname;
	}

	public String getOfferingname()
	{
		return offeringname;
	}

	public void setOfferingname(String offeringname)
	{
		this.offeringname = offeringname;
	}

	public String getSubofferingname()
	{
		return subofferingname;
	}

	public void setSubofferingname(String subofferingname)
	{
		this.subofferingname = subofferingname;
	}

	public String getVariantname()
	{
		return variantname;
	}

	public void setVariantname(String variantname)
	{
		this.variantname = variantname;
	}

	public String getSubvariantsize()
	{
		return subvariantsize;
	}

	public void setSubvariantsize(String subvariantsize)
	{
		this.subvariantsize = subvariantsize;
	}

	public String getSubIndustry()
	{
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry)
	{
		this.subIndustry = subIndustry;
	}

	public int getClientForOfferingDetails()
	{
		return clientForOfferingDetails;
	}

	public void setClientForOfferingDetails(int clientForOfferingDetails)
	{
		this.clientForOfferingDetails = clientForOfferingDetails;
	}

}
