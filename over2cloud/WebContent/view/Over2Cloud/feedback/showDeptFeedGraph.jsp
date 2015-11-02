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
<s:hidden id='deptDataType' name="deptDataType" value="chart"></s:hidden>
<div class="headding" align="center" style="margin-left: 0px;"><CENTER><h3><s:property value="%{feedStatHeader}"  /></h3></CENTER></div>
<div class="clear"></div>
 <sjc:chart
    	id="chartPie20"
    	cssStyle="width: 250px; height: 200px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    	cssClass="chartDataCss"
    >
    	<s:iterator value="%{pieFeedStatMap}">
	    	<s:if test="key=='Pending'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#088A29"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='Resolved'">
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
</body>
</html>