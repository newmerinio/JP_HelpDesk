<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


curremonth  : <s:property value="curremonth.size"/>
<s:select 

headerKey="-1"
headerValue="-Month Name:-"
list="curremonth" 
name="currentmonth"
theme="simple"/>