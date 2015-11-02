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
<s:url var="chartDataUrl" action="json-chart-data"/>
</head>
<body>
masterViewProp.size = <s:property value="pieYesNoMap.size"/>
<div class="clear"></div>
	<sjc:chart
    	id="pieChartDiv"
    	cssStyle="width: 200px; height: 250px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieYesNoMap}">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#088A29"
	    	/>
    	</s:iterator>
    </sjc:chart>
</body>
</html>