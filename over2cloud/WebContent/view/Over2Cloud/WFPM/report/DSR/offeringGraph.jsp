<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
    <%@taglib prefix="sjc"  uri="/struts-jquery-chart-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Target vs. Achievement Report</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div class="newColumn" align="center" style="font: bold;">
	<div class="leftColumn"><b>Total Target : </b></div>
		<div class="rightColumn">
			<b><s:property value="userdata['targetvalue']"/></b>
		</div>
	</div>
	<div class="clear"></div>
	<div align="center">
	<sjc:chart
    	id="chartPie"
    	cssStyle="width: 500px; height: 350px;"
    	pie="true"
    	pieLabel="true"
    >
    	<sjc:chartData
    		id="pieSerie1"
    		label="Achived"
    		data="%{userdata['totalsale']}"
    		color="#33CC99"
    	/>
    	<sjc:chartData
    		id="pieSerie2"
    		label="Remainning"
    		data="%{userdata['remainng_target']}"
    		color="#6633FF"
    	/>
    </sjc:chart>
    </div>
	</div>
</body>
</html>