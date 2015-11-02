package com.Over2Cloud.ctrl.wfpm.services;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class ActivityGeneratorHelper
{

	public void createActivity(SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder();
		List dataList=null;
		
		query.append("SELECT ");
		
		
		
	}
}
