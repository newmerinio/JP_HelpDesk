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
		<th width="20%" bgcolor="" >
			<font color="black">Status</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;1</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;2</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;3</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;4</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;5</font>
		</th>
	</tr>
	<s:iterator value="dashPojoList">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="20%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{pending}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level1','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL1}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level2','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL2}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level3','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL3}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level4','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL4}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level5','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL5}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="20%" height="20%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel1"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel2"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel3"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel4"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel5"/></b></font></td>
	
    </tr>
</table>
</body>
</html>