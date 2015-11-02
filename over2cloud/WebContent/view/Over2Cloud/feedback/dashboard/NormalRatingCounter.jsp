<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.StyleScheme1{
background: #fbf9ee url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x;


}

</style>
</head>
<body>
<table width="100%" align="center" border="1">
<%-- <s:property value="%{userType}"/>jkjhkjkj
<s:if test="%{userType=='N'}">
<tr>Feedback For:
		<s:select id="patType"
				name="patType"
				list="{'IPD','OPD'}"
				headerKey="-1"
				headerValue="Patient Type"
				theme="simple" 
				cssStyle="width:120px;height:25px;margin-bottom:5px;"
				cssClass="button"
				onchange="getRatingDataForDept('',this.value);">
		</s:select> 
</tr> 
</s:if>--%>
<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		
		<th width="" bgcolor="" >
			<font color="black">Mode</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Category</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;1</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;2</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;3</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;4</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;5</font>
		</th>
		
	</tr>
	<s:iterator id="first" status="status" value="%{Ratinginfo}">
	<tr>
	<s:if test="%{mode=='IPD'}">
	<th style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"><b>IPD</b></th>
	<td align="center" bgcolor="" class="StyleScheme1" width=""  >
			<font color="black"><b><s:property value="%{catName}"/></b></font>
		</td>
		<td  align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','1');">
		<a href="#"  >	<font color="black"><b><s:property value="%{rat1}"/></b></font></a>
		</td>
		
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','2');">
				<a href="#"  >	<font color="black"><b><s:property value="%{rat2}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','3');">
				<a href="#"  >	<font color="black"><b><s:property value="%{rat3}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','4');">
				<a href="#"  >	<font color="black"><b><s:property value="%{rat4}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','5');">
				<a href="#"  >	<font color="black"><b><s:property value="%{rat5}"/></b></font></a>
		</td>
	</s:if>
	<s:else >
	<th style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"><b>OPD</b></th>
	<td align="center" bgcolor="" class="StyleScheme1" width="" >
			<font color="black"><b><s:property value="%{catName}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','1');">
			<a href="#"  >	<font color="black"><b><s:property value="%{rat1}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','2');">
			<a href="#"  >	<font color="black"><b><s:property value="%{rat2}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','3');">
			<a href="#"  >	<font color="black"><b><s:property value="%{rat3}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','4');">
			<a href="#"  >	<font color="black"><b><s:property value="%{rat4}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="StyleScheme1" width="" onclick="getCategRatingData('<s:property value="%{catColName}"/>','<s:property value="%{mode}"/>','5');">
			<a href="#"  >	<font color="black"><b><s:property value="%{rat5}"/></b></font></a>
		</td>
	</s:else>
	
		
		
		
	</tr>
	</s:iterator>

</table>

</body>
</html>