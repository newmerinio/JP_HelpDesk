<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DAR</title>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

</script>
</head>
<div class="list-icon"><div class="clear"></div><div class="head"><s:property value="%{mainHeaderName}"/></div></div>
<body topmargin='0'>
<div style=display: align="center" ><br><br>
<div id="graphDiv" style="float: left;" >
    <sjc:chart
    	id="chartPie2"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader}"  /></h3></CENTER>
    </div>
<div id="graphDiv1" style="float: left;">
    <sjc:chart
    	id="chartPie3"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap1}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader1}"  /></h3></CENTER>
    </div>
<div id="graphDiv2" style="float: left;">
    <sjc:chart
    	id="chartPie4"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap2}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader2}"  /></h3></CENTER>
    </div>
<div id="graphDiv3" style="float: left;">
    <sjc:chart
    	id="chartPie5"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap3}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader3}"  /></h3></CENTER>
    </div>
<div id="graphDiv4" style="float: left;">
    <sjc:chart
    	id="chartPie6"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap4}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader4}"  /></h3></CENTER>
    </div>
<div id="graphDiv5" style="float: left;">
    <sjc:chart
    	id="chartPie7"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap5}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader5}"  /></h3></CENTER>
    </div>
<div id="graphDiv6" style="float: left;">
   
    <sjc:chart
    	id="chartPie8"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap6}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3><s:property value="%{graphHeader6}"  /></h3></CENTER>
    </div>
<div id="graphDiv7" style="float: left;">
   
    <sjc:chart
    	id="chartPie9"
    	cssStyle="width: 400px; height: 300px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap7}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
<CENTER><h3>Pie Chart For Yes or No</h3></CENTER>
    </div>
</div>
</body>
</html>