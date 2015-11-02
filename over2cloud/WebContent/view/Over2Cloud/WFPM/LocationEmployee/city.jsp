<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>

<script type="text/javascript">
$(document).ready(function()
	{
	$("#city").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
</SCRIPT>
</head>
<body>
 <s:select 
            id="city"
            name="city" 
            list="empMap" 
            multiple="true"
            cssStyle="width:5%"
            onchange="fetchTerritoryMultiple('city','terrDiv')"
      >
</s:select>
</body>
</html>