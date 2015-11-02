package com.Over2Cloud.ctrl.wfpm.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSValidation {

	/**
	 * Validation for Date in dd-mm-yyyy format
	 * */
	public static boolean validateDateFormat(String userName){
		Pattern dateFrmtPtrn = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)");
	    Matcher mtch = dateFrmtPtrn.matcher(userName);
	    if(mtch.matches()){
	        return true;
	    }
	    return false;
	}
	
	/**
	 *  Validation for numeric value
	 * */
	public static boolean isNumeric(String val)
	{
		Pattern numericVal = Pattern.compile("[0-9]+");
		Matcher match = numericVal.matcher(val);
		
		if(match.matches())
		{
			return true;
		}
		return false;
	}

	/**
	 *  Validation for alphabets value
	 * */
	public static boolean isAlphabets(String val)
	{
		Pattern numericVal = Pattern.compile("[A-Za-z ]+");
		Matcher match = numericVal.matcher(val);
		
		if(match.matches())
		{
			return true;
		}
		return false;
	}
	
	/**
	 *  Validation for alphanumeric value
	 * */
	public static boolean isAlphaNumeric(String val)
	{
		Pattern numericVal = Pattern.compile("[A-Za-z0-9 ]+");
		Matcher match = numericVal.matcher(val);
		
		if(match.matches())
		{
			return true;
		}
		return false;
	}
	
	public static void main(String a[]){
        //System.out.println("Is '13-12-2013' a valid date format >> "+validateDateFormat("13-12-2013"));
        //System.out.println(">>>>>>>>>> isNumeric('1123') : "+ isNumeric("123456788767867"));
        //System.out.println(">>>>>>>>>> isNumeric('sadg1123') : "+ isNumeric("sadg1123"));
        //System.out.println(">>>>>>>>>> isAlphabets('anurag') : "+ isAlphabets("anurag"));
        //System.out.println(">>>>>>>>>> isAlphabets('anurag123') : "+ isAlphabets("anurag123"));
        //System.out.println(">>>>>>>>>> isAlphaNumeric('anurag123') : "+ isAlphaNumeric("anurag123"));
        //System.out.println(">>>>>>>>>> isAlphaNumeric('gsahgd') : "+ isAlphaNumeric("gsahgd"));
    }
}
