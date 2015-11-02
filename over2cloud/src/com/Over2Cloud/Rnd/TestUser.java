package com.Over2Cloud.Rnd;

import hibernate.common.HibernateSessionFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;

import com.Over2Cloud.CommonClasses.Cryptography;

public class TestUser {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		System.out.println("User Name===="+Cryptography.encrypt("Varsha", DES_ENCRYPTION_KEY));
		
		
		System.out.println(" ikhdhasgjkdsgj   ");
		/*System.out.println("User Name   "+Cryptography.decrypt("Y0oeOY38MdI=", DES_ENCRYPTION_KEY));
		System.out.println("Password  "+Cryptography.decrypt("T5xI6PkLM9k=", DES_ENCRYPTION_KEY));
		
		//System.out.println("User Name===="+Cryptography.decrypt("4XdfScmRYjM=", DES_ENCRYPTION_KEY));
		//System.out.println("Password  "+Cryptography.decrypt("1JlbypE5Jbc=", DES_ENCRYPTION_KEY));
		
		
		
		Session session=HibernateSessionFactory.getSession();
		List tempStr=session.createSQLQuery("select uid,accountid from client_info where id='1'").list();
		if(tempStr!=null && tempStr.size()>0)
		{
			System.out.println(""+tempStr.get(0).toString());
			
			Object[] obj=(Object[]) tempStr.get(0);
			
			System.out.println(""+obj[0]);
			System.out.println(""+obj[1]);
			
		}
		
		float f=1f;
		
		
		
			int i=1;
			switch(i)
			{
				case 1:
				System.out.println("one");
				case 2:
				System.out.println("two");
				case 3:
				System.out.println("three");
			}
		
		
		
		*/
		
		
		String ticketNo="P1000";
		String pre=ticketNo.substring(0,1);
		String post=ticketNo.substring(1,ticketNo.length());
		post=String.valueOf(Integer.parseInt(post)+1);
		String newTicketNo=pre+post;
		
		System.out.println("Ticket No is as >>> "+newTicketNo);
		
		
		
		
		
		
		
		
		
	}
}
