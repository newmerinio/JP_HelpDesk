<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.StyleScheme1{
background: #E28F8F url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x;

}

</style>
</head>
<body>
<s:if test="%{block=='3rdBlock'}">

             
              <center style="margin-left: -37px;margin-top:5px;"><font color="#000000"><b>Feedback Ratings For</b></font>
              <s:select id="fromDept1"
						name="fromDept1"
						list="fromDept"
						headerKey="-1"
						headerValue="Select Department"
						theme="simple" 
						cssStyle="width:150px;height:25px;margin-bottom:5px;"
						cssClass="button"
						onchange="getRatingDataForDept('fromDept1','patType','ratingBlockmgt');">
						
			</s:select> 
			<font color="#000000"><b>Of</b></font> <s:select id="patType"
				name="patType"
				list="{'IPD','OPD'}"
				headerKey="-1"
				headerValue="Patient Type"
				theme="simple" 
				cssStyle="width:120px;height:25px;margin-bottom:5px;"
				cssClass="button"
				onchange="getRatingDataForDept('fromDept1','patType','ratingBlockmgt');">
		</s:select> 
              </center>
               <div id="ratingBlockmgt">
<table width="100%" align="center" border="1">
<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;" align="center">
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
	<th align="center"><b>IPD</b></th>
	<td align="center" bgcolor="" class="StyleScheme1" width="" >
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
	<th><b>OPD</b></th>
	<td align="center" bgcolor="" class="StyleScheme1" width="" >
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
	</s:else>
	
		
		
		
	</tr>
	</s:iterator>

</table>
</div>
</s:if>
<s:else>
<table height="40%" width="100%" align="center"  border="1" style="border-radius:3px;">
<tr  style="border: 1px solid #d3d3d3;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;" height="10%">
<th width="60%"  >
<font color="black">Department</font>
</th>
<th width="20%"  >
<font color="black">Issues</font>
</th>
<th width="20%" >
<font color="black">Actions</font>
</th>
<th width="20%"  >
<font color="black">CAPA</font>
</th>
<th width="20%" >
<font color="black">&nbsp;&nbsp;%&nbsp;&nbsp;</font>
</th>
</tr>

<s:iterator value="deptActionPojo">
<tr>
<td align="center" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;" class="sortable" width="60%">

<a href="#"><font color="black"><s:property value="%{deptName}"/></font></a>
</td>

<s:if test="%{totalIsues!=0}">
<td align="center" bgcolor="" class="sortable" width="60%" onclick="getFeedbackActionOnClick('<s:property value="%{deptId}"/>','Issue');">
<a href="#"><font color="#275985"><b><s:property value="%{totalIsues}"/></b></font></a>
</td>
</s:if>
<s:else>
	<th align="center"><font color="#275985"><s:property value="%{totalIsues}"/></font></th>
</s:else>

<s:if test="%{totalAction!=0}">
<td align="center" bgcolor="" class="sortable" width="20%" onclick="getFeedbackActionOnClick('<s:property value="%{deptId}"/>','Action');">
<a href="#"><font color="#275985"><b><s:property value="%{totalAction}"/></b></font></a>
</td>
</s:if>
<s:else>
	<th align="center"><font color="#275985"><s:property value="%{totalAction}"/></font></th>
</s:else>

<s:if test="%{totalCapa!=0}">
<td align="center" bgcolor="" class="sortable" width="20%" onclick="getFeedbackActionOnClick('<s:property value="%{deptId}"/>','Capa');">
<a href="#"><font color="#275985"><b><s:property value="%{totalCapa}"/></b></font></a>
</td>
</s:if>
<s:else>
	<th align="center"><font color="#275985"><s:property value="%{totalCapa}"/></font></th>
</s:else>

<td align="center" bgcolor="" class="sortable" width="20%">
<font color="#275985"><b><s:property value="%{percent}"/></b></font>
</td>

</tr>
</s:iterator>
<tr>
<td align="center" bgcolor="#C4DCFB" class="sortable" width="50%">

<font color="black"><b>Total</b></font>
</td>
<td align="center" bgcolor="#C4DCFB" class="sortable" width="20%" onclick="getFeedbackActionOnClick('','Issue');">
<a href="#"><font color="black"><b><s:property value="%{subTotalIssue}"/></b></font></a>
</td>
<td align="center" bgcolor="#C4DCFB" class="sortable" width="20%" onclick="getFeedbackActionOnClick('','Action');">
<a href="#"><font color="black"><b><s:property value="%{SubTotalAction}"/></b></font></a>
</td>
<td align="center" bgcolor="#C4DCFB" class="sortable" width="20%" onclick="getFeedbackActionOnClick('','Capa');">
<a href="#"><font color="black"><b><s:property value="%{SubTotalCapa}"/></b></font></a>
</td>
<th width="20%" bgcolor="#C4DCFB" >
<font color="Black">&nbsp;&nbsp;100&nbsp;&nbsp;</font>
</th>
</tr>

</table>
</s:else>
</body>
</html>