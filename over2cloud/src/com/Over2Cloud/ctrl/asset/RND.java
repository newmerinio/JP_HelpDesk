package com.Over2Cloud.ctrl.asset;

import java.util.ArrayList;
import java.util.ListIterator;

public class RND
{
	public static void main(String args[])
	{
		    //create an object of ArrayList
		    ArrayList aList = new ArrayList();
		   
		    //Add elements to ArrayList object
		    aList.add("1");
		    aList.add("2");
		    aList.add("3");
		    aList.add("4");
		    aList.add("5");
		   
		    //Get an object of ListIterator using listIterator() method
		    ListIterator listIterator = aList.listIterator();
		 
		    /*
		      Use nextIndex and previousIndex methods of ListIterator to get next and
		      previous index from the current position in the list.
		    */
		   
		    System.out.println("Previous Index is : " + listIterator.previousIndex());  
		    System.out.println("Next Index is : " + listIterator.nextIndex());
		   
		    //advance current position by one using next method
		    listIterator.next();
		   
		    System.out.println("After increasing current position by one element : ");
		    System.out.println("Previous Index is : " + listIterator.previousIndex());  
		    System.out.println("Next Index is : " + listIterator.nextIndex());
	}
}
