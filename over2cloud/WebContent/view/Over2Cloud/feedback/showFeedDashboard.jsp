<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboard.js"/>"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
</head>
<body>
<div class="middle-content">
<div class="contentdiv-small" style="overflow: hidden;" id="div1">
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div class="headding" align="center" style="margin-left: 40px;"><CENTER><h3>Feedback Counters</h3></CENTER></div>
<div class="clear"></div>
<table align="center" width="80%" height="80%"> 
	<tr bgcolor="#8A0886">
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="white">Mode</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" >
				<tr>
					<th align="center" colspan="2">
						<font color="white">Yesterday</font>
					</th>
				</tr>
				<tr>
					<th align="center">
						<font color="white">Y</font>
					</th>
					<th align="center">
						<font color="white">N</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<th align="center" colspan="2">
						<font color="white">Today</font>
					</th>
				</tr>
				<tr>
					<th align="center">
						<font color="white">Y</font>
					</th>
					<th align="center">
						<font color="white">N</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<th align="center" colspan="2">
						<font color="white">Total</font>
					</th>
				</tr>
				<tr>
					<th align="center">
						<font color="white">Y</font>
					</th>
					<th align="center">
						<font color="white">N</font>
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">Mobile&nbsp;Tab</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">SMS</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">Voice</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">Online</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">Not&nbsp;Responded</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table align="center" width="100%" height="100%">
				<tr>
					<th align="center" valign="top">
						<font color="#8000FF">Total</font>
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
		<td>
			<table align="center" width="100%">
				<tr>
					<th align="center">
						0
					</th>
					<th align="center">
						0
					</th>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</div>
<div class="contentdiv-small"  style="overflow: hidden;">
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Tickets Details Via Tab</h3></CENTER></div>
<div class="clear"></div>
<table width="100%" align="center" height="80%" border="1">
	<tr align="center" height="100px">
		<th align="center" valign="top">
			Opened
		</th>
		<th align="center" valign="top">
			Closed
		</th>
		<th align="center" valign="top">
			Level 1
		</th>
		<th align="center" valign="top">
			Level 2
		</th>
		<th align="center" valign="top">
			Level 3
		</th>
		<th align="center" valign="top">
			Level 4
		</th>
	</tr>
	<tr align="center" valign="top">
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
	</tr>
</table>
<div class="clear"></div>
</div>
<div class="contentdiv-small"  style="overflow: hidden;">
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Tickets Details Via SMS</h3></CENTER></div>
<div class="clear"></div>
<table width="100%" align="center" height="80%" border="1">
	<tr align="center" height="100px">
		<th align="center" valign="top">
			Opened
		</th>
		<th align="center" valign="top">
			Closed
		</th>
		<th align="center" valign="top">
			Level 1
		</th>
		<th align="center" valign="top">
			Level 2
		</th>
		<th align="center" valign="top">
			Level 3
		</th>
		<th align="center" valign="top">
			Level 4
		</th>
	</tr>
	<tr align="center" valign="top">
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
	</tr>
</table>  
<div class="clear"></div>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Tickets Details Via OBD</h3></CENTER></div>
<div class="clear"></div>
<table width="100%" align="center" height="80%" border="1">
	<tr align="center" height="100px">
		<th align="center" valign="top">
			Opened
		</th>
		<th align="center" valign="top">
			Closed
		</th>
		<th align="center" valign="top">
			Level 1
		</th>
		<th align="center" valign="top">
			Level 2
		</th>
		<th align="center" valign="top">
			Level 3
		</th>
		<th align="center" valign="top">
			Level 4
		</th>
	</tr>
	<tr align="center" valign="top">
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
	</tr>
</table>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Tickets Details Via Online</h3></CENTER></div>
<div class="clear"></div>
<table width="100%" align="center" height="80%" border="1">
	<tr align="center" height="100px">
		<th align="center" valign="top">
			Opened
		</th>
		<th align="center" valign="top">
			Closed
		</th>
		<th align="center" valign="top">
			Level 1
		</th>
		<th align="center" valign="top">
			Level 2
		</th>
		<th align="center" valign="top">
			Level 3
		</th>
		<th align="center" valign="top">
			Level 4
		</th>
	</tr>
	<tr align="center" valign="top">
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
	</tr>
</table>  
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Tickets Details Via Paper</h3></CENTER></div>
<div class="clear"></div>
<table width="100%" align="center" height="80%" border="1">
	<tr align="center" height="100px">
		<th align="center" valign="top">
			Opened
		</th>
		<th align="center" valign="top">
			Closed
		</th>
		<th align="center" valign="top">
			Level 1
		</th>
		<th align="center" valign="top">
			Level 2
		</th>
		<th align="center" valign="top">
			Level 3
		</th>
		<th align="center" valign="top">
			Level 4
		</th>
	</tr>
	<tr align="center" valign="top">
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
		<td align="center" valign="top">
			count
		</td>
	</tr>
</table>  
</div>
</div>
</body>
</html>