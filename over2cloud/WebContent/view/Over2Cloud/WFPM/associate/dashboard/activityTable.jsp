<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<table align="center" style="width: 100%;font-size: 15px;padding: 0px; border:1px solid #000000">
<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font><b>Tomorrow </b> </font>
		</td>
		
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b> Missed</b></font>
		</td>
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b>Today</b></font>
		</td>
		<td align="left" style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b>Seven &nbsp;Days</b></font>
		</td>
		
	</tr>
	<s:iterator id="first" status="status" value="%{finalDataList}">
	<tr>

		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#"><b><s:property value="%{tomorrowAct}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#"><b><s:property value="%{missedActivity}"/></b> </a> </font>
		</td>
	
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#"><b><s:property value="%{todayAct}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#"><b><s:property value="%{sevenDaysAct}"/></b> </a> </font>
		</td>
	</tr>
	</s:iterator>

</table>

</body>
</html>