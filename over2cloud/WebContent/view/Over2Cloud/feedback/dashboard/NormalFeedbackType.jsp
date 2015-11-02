<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table height="40%" width="100%" align="center" border="1">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		<th width="60%" bgcolor="" >
			<font color="black">Type&nbsp;Name</font>
		</th>
		<th width="20%" bgcolor="" >
			<font color="black">Total</font>
		</th>
		<th width="20%" bgcolor="" >
			<font color="black">Today</font>
		</th>
	</tr>
<s:iterator value="feedDataDashboardType">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="60%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{feedType}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','TotalDept');">
			<a href="#"><font color="black"><b><s:property value="%{feedTypeTotal}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','TodayDept');">
			<a href="#"><font color="black"><b><s:property value="%{feedTypeToday}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
 </tr>
</table>

</body>
</html>