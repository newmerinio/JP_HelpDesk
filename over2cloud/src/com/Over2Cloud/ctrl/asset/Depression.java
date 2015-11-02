package com.Over2Cloud.ctrl.asset;

public class Depression
{

	public void calculateDep(double closeingCost, int qty, double addThisPeriod, int noOfDays)
	{
		double effectiveDepRate = 0.0f;
		double unitCost = closeingCost / qty;
		long depChargeThisPeriod = 0;
		if (unitCost > 5000)
		{
			effectiveDepRate = 0.0707;
		}
		else
		{
			effectiveDepRate = 1.0000;
		}
		if (effectiveDepRate >= 1)
		{
			depChargeThisPeriod = Math.round(addThisPeriod);
		}
		else
		{
			depChargeThisPeriod = Math.round(((((closeingCost * effectiveDepRate) + (addThisPeriod * effectiveDepRate)) * noOfDays) / 365));
		}
		System.out.println("Depression Charge for this period " + depChargeThisPeriod);
	}

	public static void main(String[] args)
	{
		new Depression().calculateDep(1158797, 42, 0.0, 183);
	}

}
