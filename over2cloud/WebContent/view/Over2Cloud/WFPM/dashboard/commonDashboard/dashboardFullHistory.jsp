<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/WFPM/commonDashboard/tableStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
// These variables will be used in dashboard contacts fetching, so don't delete these variables and do not even put them in 
// fullHistory.js file of javascript
	var id = ${id};	
	var temp = ${temp};
	var obj = ${jsonArray};
	var count = -1;
	var currDate = ${currDate};
</script>
<script type="text/javascript" src="js/WFPM/dashboard/fullHistory.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<br>
<div class="CSSTableGenerator">
<table>
	<tr>
		<td colspan="7">
		<div style="font-size: large; margin-left: 5%;">&nbsp;<s:property value="#parameters.orgnization"/>&nbsp;</div>
		<div onclick="backToActivityPage();" style="text-decoration: underline; float: left;">Back</div>
		<div style="margin-left: 45%; float: left;">
			<div style="float: left;" id="prevDivID">
				<img alt="Previous" onclick="previousOffering();" style="cursor: pointer;" src="images/backward.png" title="Previous">
				&nbsp;&nbsp;&nbsp;
			</div>
			<div id="offeringValue" style="float: left;"></div>
			<div style="float: left;" id="nextDivID">
				&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="nextOffering();" style="cursor: pointer;" src="images/forward.png" title="Next">
			</div>
		</div>
		</td>
	</tr>
</table>
</div>

<div id="offeringContactDivID">
</div>
</center>	
</body>
<script type="text/javascript">
</script>
</html>