<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
function changeAccounts(val)
{
	if(val=='Advance')
	{
		advance();
	}
	else
	{
		 advance_settlement();
	}
}
changeAccounts('Advance');
</SCRIPT>
</head>
<body>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Accounts</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View </div>  
	           <s:select 
	                   cssClass="select"
					   cssStyle="width:10%"
	                   id="account"
	                   list="{'Advance','Settlement'}" 
	                   onchange="changeAccounts(this.value);"
                 		>
			  </s:select>
</div>
<div class="clear"></div>
<br></br>
<div id="result"></div>         
</div>
</body>
</html>