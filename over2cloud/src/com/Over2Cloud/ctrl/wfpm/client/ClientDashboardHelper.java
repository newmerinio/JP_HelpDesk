package com.Over2Cloud.ctrl.wfpm.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class ClientDashboardHelper{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<ClientSupportBean> getClientTypeDataRatingWise(String cid,SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder ids = new StringBuilder();
	List<ClientSupportBean> finalDataList = new ArrayList<ClientSupportBean>();
	try
	{
		for(int i=0;i<3;i++){
			int rating=0;
			StringBuilder builder=new StringBuilder(" select starRating from client_basic_data as cbd, client_offering_mapping as com  ");
			builder.append(" where com.clientName = cbd.id and cbd.userName  IN ("+cid+")");
			builder.append(" and isConverted='"+i+"' and starRating is not NULL and starRating not IN('NA')");
			List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			System.out.println("star query>>>"+builder.toString());
			if(dataList!=null && dataList.size()>0)
			{
				ClientSupportBean pojo = new ClientSupportBean();
				int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0;
				if(i==0){
					pojo.setClientname("Prospective");
				}
				if(i==1){
					pojo.setClientname("Existing");
				}
				if(i==2){
					pojo.setClientname("Lost");
				}
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					
					Object ob = (Object) iterator.next();
					if(ob!=null && !ob.toString().equalsIgnoreCase("NA"))
					{
						try
						{
							rating=Integer.parseInt(ob.toString());
						}
						catch (Exception e)
						{
							rating=0;
							e.printStackTrace();
						}
						
						if(rating==1)
						{
							rat1+=1;
							pojo.setRat1(String.valueOf(rat1));
						}
						else if(rating==2)
						{
							rat2+=1;
							pojo.setRat2(String.valueOf(rat2));
						}
						else if(rating==3)
						{
							rat3+=1;
							pojo.setRat3(String.valueOf(rat3));
						}
						else if(rating==4)
						{
							rat4+=1;
							pojo.setRat4(String.valueOf(rat4));
						}
						else if(rating==5)
						{
							rat5+=1;
							pojo.setRat5(String.valueOf(rat5));
						}
					}
				}
				pojo.setTotalRat1(rat1);
				pojo.setTotalRat2(rat2);
				pojo.setTotalRat3(rat3);
				pojo.setTotalRat4(rat4);
				pojo.setTotalRat5(rat5);
				finalDataList.add((pojo));
			}
		}
		
			
				} catch (Exception e)
	{
		e.printStackTrace();
	}
	return finalDataList;
	}
	
	
	public List<ClientSupportBean> getClientTypeDataRatingWiseSource(String cid,SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder ids = new StringBuilder();
	List<ClientSupportBean> finalDataList = new ArrayList<ClientSupportBean>();
	try
	{
		for(int i=0;i<3;i++){
			int rating=0;
			StringBuilder builder=new StringBuilder(" select sm.sourceName , count(sm.sourceName) from client_basic_data as cbd, client_offering_mapping as com ,sourcemaster as sm  ");
			builder.append(" where com.clientName = cbd.id and cbd.userName  IN ("+cid+")");
			builder.append(" and cbd.sourceMaster is not null group by sm.sourceName");
			List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			System.out.println("star query>>>)))))>>>>>>"+builder.toString());
			if(dataList!=null && dataList.size()>0)
			{
				ClientSupportBean pojo = new ClientSupportBean();
				int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0;
				if(i==0){
					pojo.setClientname("Prospective");
				}
				if(i==1){
					pojo.setClientname("Existing");
				}
				if(i==2){
					pojo.setClientname("Lost");
				}
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object ob1 = (Object) iterator.next();
					Object[] ob = (Object[]) ob1;
					//Object ob = (Object) iterator.next();
					pojo.setClientname(ob[0].toString());
					System.out.println(ob[1].toString());
					pojo.setRat1(ob[1].toString());
					
				}
				pojo.setTotalRat1(rat1);
				pojo.setTotalRat2(rat2);
				pojo.setTotalRat3(rat3);
				pojo.setTotalRat4(rat4);
				pojo.setTotalRat5(rat5);
				
				System.out.println(rat1);
				
			}
		}
		
			
				} catch (Exception e)
	{
		e.printStackTrace();
	}
	return finalDataList;
	}
	//
	public ClientSupportBean getRatingDetails(ClientSupportBean pojo,SessionFactory connection)
	{ 
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		/*String pojoval=pojo.getClientname();
		if(pojoval.toString().equalsIgnoreCase("Prospective")){
			pojoval=String.valueOf(0);
		}else if(pojoval.toString().equalsIgnoreCase("Existing")){
			pojoval=String.valueOf(1);
		}else if(pojoval.toString().equalsIgnoreCase("Lost")){
			pojoval=String.valueOf(2);
		}*/
		
		return pojo;
	}
	
	
}
