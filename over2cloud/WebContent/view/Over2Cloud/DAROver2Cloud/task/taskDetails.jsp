<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <s:iterator value="taskDetails">
       		  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{key}"/>:</div>
								<div class="rightColumn1">
	                       		<s:property value="%{value}"/>
               </div>
               </div>
   </s:iterator>
</body>
</html>