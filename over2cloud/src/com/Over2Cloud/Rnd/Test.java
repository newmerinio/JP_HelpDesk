package com.Over2Cloud.Rnd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class Test 
{
	public static void main(String[] arg)
    {
	    String s="fieldValue13";
	    Pattern p = Pattern.compile("(\\d+)");
	    Matcher m = p.matcher(s);
	    while(m.find())
	    {
	        System.out.println(m.group(1));
	       
	        int s1 = s.indexOf(m.group(1));
	        System.out.println("string :: "+s1);
	        
	        String abc = s.substring(0, s.indexOf(m.group(1)));
	        System.out.println("222222222 :: "+abc);
	        
	    }
    }
}
