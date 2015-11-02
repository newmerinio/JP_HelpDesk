<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pie Chart</title>
</head>
<body>
<div align="center">
<sjc:chart
        id="pieOfdeptVistorrecord"
        cssStyle="width: 200px; height: 200px;"
        legendShow="false"
        pie="true"
        pieLabel="true"
        onHoverTopics="true"
    >
        <s:iterator value="%{graphMap}">
            <sjc:chartData
                label="%{key}"
                data="%{value}"
            />
        </s:iterator>
    </sjc:chart>
    </div>
</body>
</html>