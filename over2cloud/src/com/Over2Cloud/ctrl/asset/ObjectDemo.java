package com.Over2Cloud.ctrl.asset;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class ObjectDemo
{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
	public void updateSpare(String complainId,String assetId,String spareId,String totalSpare,SessionFactory connection)
	{
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		boolean status = false;
		List<TableColumes> tableColume = new ArrayList<TableColumes>();
		TableColumes ob1 = new TableColumes();
		ob1.setColumnname("complain_id");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("asset_id");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("spare_id");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("total_spare");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("total_cost");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("time");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		
		
		
		
		cbt.createTable22("asset_extra_expenses_detail", tableColume, connection);
	}
	
	/*public String calculateTotalAmount(String spareId,int totalSpare,SessionFactory connection)
	{
		String totalAmount=null;
		String query="SELECT unitcost,remaining FROM receive_spare_detail WHERE remaining>0 AND spare_name="+spareId;
		List dataList=cbt.executeAllSelectQuery(query, connection);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null)
				{
					//totalSpare
				}
			}
		}
		
		//return totalSpare;
		
	}*/
	
	public static void main(String args[])
	{
		ObjectDemo ob=new ObjectDemo();
		String complainId="1";
		String assetId="1";
		String spareId="1";
		String totalSpare="3";
		SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-9");
		ob.updateSpare(complainId, assetId, spareId, totalSpare,connection);
			System.out.println("After Calling Method");
			
		
		System.out.println("test");
	}

	
}
