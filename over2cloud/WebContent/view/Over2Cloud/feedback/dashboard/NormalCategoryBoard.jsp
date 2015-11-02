<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">

#ratingBlock{
display: none;
}
</style>
</head>
<body>
	<table height="40%" width="100%" align="center" border="1" style="margin-bottom: 10px;">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		<th width="70%" bgcolor="" >
			<font color="black">Category&nbsp;Name</font>
		</th>
		<th width="30%" bgcolor="" >
			<font color="black">Counter</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="70%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{actionName}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="30%" onclick="getOnClickDataForFeedbackType('<s:property value="%{id}"/>','Category');">
			<a href="#"><font color="black"><b><s:property value="%{actionCounter}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalPending"/></b></font></td>
    </tr>
</table>
</body>
</html>