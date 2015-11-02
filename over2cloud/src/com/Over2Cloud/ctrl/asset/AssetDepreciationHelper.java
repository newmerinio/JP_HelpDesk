package com.Over2Cloud.ctrl.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;

public class AssetDepreciationHelper
{
	@SuppressWarnings("unchecked")
	public void updateDepression(SessionFactory connection)
	{
		try
   	 	{
   		 	AssetServiceHelper srvcHelper=new AssetServiceHelper();
   		 	List dataList=srvcHelper.getDepressionDetails(connection);
   		 	InsertDataTable ob = null;
   		 	List data=new ArrayList();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			
   		 	if(dataList!=null && dataList.size()>0)
   		 	{
   		 		for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
   		 		{
   		 			String id,receivedOn;
   		 			double closeingCost,rate_of_depression,deduction_amount =0.0f;
   		 			int noOfDays = 0;
   		 			
					Object[] object = (Object[]) iterator.next();
					id=object[0].toString();
					
					if(object[1]!=null)
						closeingCost=Double.parseDouble(object[1].toString());
					else
						closeingCost=0.0f;
					
					
					if(object[2]!=null)
						rate_of_depression=Double.parseDouble(object[2].toString());
					else
						rate_of_depression=0.0f;
					
					
					if(object[3]!=null)
					{
						receivedOn=object[3].toString();
						System.out.println(receivedOn+">>>> "+noOfDays);
						noOfDays=DateUtil.getNoOfDays(DateUtil.getCurrentDateUSFormat(),receivedOn);
						
					}
					
					
					if(object[4]!=null)
					{
						deduction_amount=Double.parseDouble(object[4].toString());
					}
					
					do
					{
						List depDetails=calculateDep(closeingCost,1,rate_of_depression,1);
						if(depDetails!=null && depDetails.size()>0)
						{
							for(int i=0;i<depDetails.size();i++)
							{
								closeingCost=closeingCost-Double.valueOf(depDetails.get(0).toString());
								deduction_amount+=Double.parseDouble(depDetails.get(0).toString());
								System.out.println(depDetails.get(0));
							}
						}
						noOfDays--;
					} 
					while (noOfDays>0 && closeingCost>1);
					
					if(closeingCost!=0.0f && deduction_amount!=0.0f)
					{
						System.out.println("Inisde if");
						Map<String, Object> setVariables=new HashMap<String, Object>();
						Map<String, Object> whereCondition=new HashMap<String, Object>();
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						whereCondition.put("id",id);
						setVariables.put("next_update_on", DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()));
						setVariables.put("today_amount",closeingCost);
						setVariables.put("amount_deducted",deduction_amount);
						cbt.updateTableColomn("asset_depreciation_detail", setVariables, whereCondition, connection);
						
						String strCheck=DateUtil.getCurrentDateUSFormat().substring(4, DateUtil.getCurrentDateUSFormat().length());
						if(DateUtil.getCurrentDateUSFormat().substring(4, DateUtil.getCurrentDateUSFormat().length()).equals("-03-31"))
						{
							data.clear();
							insertData.clear();
							data=cbt.executeAllSelectQuery("SELECT IFNULL(SUM(amount_deducted),0) FROM asset_depreciation_yearly_log WHERE asset_depreciation_id="+id, connection);
							
							
							ob = new InsertDataTable();
							ob.setColName("asset_depreciation_id");
							ob.setDataName(id);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("witten_down_value");
							ob.setDataName(closeingCost);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("amount_deducted");
							ob.setDataName(deduction_amount-Double.parseDouble(data.get(0).toString()));
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);
							
							cbt.insertIntoTable("asset_depreciation_yearly_log", insertData, connection);
						}
					}
				}
   		 	}
   	 	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List calculateDep(double closeingCost,int qty,double effectiveDepRate,int noOfDays)
	{
		long depChargeThisPeriod=0;
		List depDetails=new ArrayList();
		
		depChargeThisPeriod=Math.round(((closeingCost*effectiveDepRate)*noOfDays)/365);
		depDetails.add(depChargeThisPeriod);
		System.out.println("Depression Charge for this period "+depChargeThisPeriod);
		return depDetails;
	}
}