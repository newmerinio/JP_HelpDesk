<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<B>Asset Preventive Due Matrix Graph</B>
<center>
    <sjc:chart
        id="chartPie1PreventiveGraph%{randomNum}"
       	cssStyle="width: %{gridWidth}px; height: %{gridHeight}px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        
    >

    <s:iterator value="%{preventiveData}">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		
		    		/>	
		    		
    	</s:iterator>
    </sjc:chart>
    </center>
</body>
</html>