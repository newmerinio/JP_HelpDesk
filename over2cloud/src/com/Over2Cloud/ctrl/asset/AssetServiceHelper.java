package com.Over2Cloud.ctrl.asset;

import java.util.List;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;

public class AssetServiceHelper 
{
	@SuppressWarnings("unchecked")
	public List getDepressionDetails(SessionFactory connectionSpace)
	{
		List dataList=null;
		try
		{
			String query="SELECT id,today_amount,depreciation_rate,next_update_on,amount_deducted FROM asset_depreciation_detail WHERE today_amount>1 AND next_update_on<='"+DateUtil.getCurrentDateUSFormat()+"'";
			dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
}
