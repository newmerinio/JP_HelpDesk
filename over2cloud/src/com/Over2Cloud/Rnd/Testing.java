package com.Over2Cloud.Rnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Testing
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		   try {
			   
			   String str="azaa, asd, , , , , , , , , , , , , , , , , , , , ";
			   String desg[]=str.trim().split(",");
				for (int i = 0; i < desg.length; i++) 
				{
					System.out.println("::"+desg[i].trim()+"::");
				}
			   
			   /*



               Process p = Runtime.getRuntime().exec(
                               System.getenv("windir") + "\\system32\\"
                                               + "tasklist.exe");

               String line;
               BufferedReader input = new BufferedReader(
                               new InputStreamReader(p.getInputStream()));
               while ((line = input.readLine()) != null) {
                       System.out.println(line); // <-- Parse data here.
               }
       */} catch (Exception E) {
               E.printStackTrace();
       }

	}

}
