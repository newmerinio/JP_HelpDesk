<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<CENTER>
	<sjc:chart
    	id="chartPie125"
    	cssStyle="width: 400px; height: 400px;"
    	legendShow="true"
    	pie="true"
    	pieLabel="true"
    	cssClass="chartDataCss"
    >
    	<s:iterator value="%{pieYesNoMap}">
	    	<s:if test="key=='Yes'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#088A29"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='No'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#ff0000"
	    	/>
	    	</s:elseif>
	    	<s:else>
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
	    	</s:else>
    	</s:iterator>
    	<s:iterator value="%{pieDeptMap}">
	    	<s:if test="key=='Excellent'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#556B2F"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='V.Good'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#006400"
	    	/>
	    	</s:elseif>
	    	<s:if test="key=='Good'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#7FFF00"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='Average'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#FF8C00"
	    	/>
	    	</s:elseif>
	    	<s:elseif test="key=='Poor'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#ff0000"
	    	/>
	    	</s:elseif>
	    	<s:else>
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
	    	</s:else>
    	</s:iterator>
    </sjc:chart>
    </CENTER>
</body>
</html>