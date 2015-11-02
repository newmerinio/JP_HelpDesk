<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	     <sjc:chart
    	id="chartDate"
    	xaxisMode="time"
    	xaxisTimeformat="%m.%Y"
    	xaxisMin="4"
    	xaxisMax="5"
    	xaxisColor="#666"
    	xaxisTickSize="[3, 'month']"
    	xaxisTickColor="#aaa"
    	xaxisPosition="top"
    	yaxisPosition="right"
    	yaxisTickSize="10"
    	cssStyle="width: 600px; height: 400px;"
    >
    	<sjc:chartData
    		id="chartDateData"
    		label="Map -Date, Integer-"
    		list="jsonArrayKPI"
    		color="#990066"
    		lines="{ show: true }"
    	/>
    </sjc:chart>
</body>
</html>