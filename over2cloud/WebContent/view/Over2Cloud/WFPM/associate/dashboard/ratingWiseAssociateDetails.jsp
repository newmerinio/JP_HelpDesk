<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>

</head>
<body>
	<s:url id="viewDataId" action="viewRatingDataGrid.action" escapeAmp="false">
		<s:param name="starRating"  value="%{starRating}" />
		<s:param name="clientType"  value="%{clientType}" />
	</s:url>
	<div style=" float:left; padding:10px 1%; width:98%;">
		
	 	<div style="overflow: scroll; height:250px;">
		<sjg:grid
			id="gridedittable"
			href="%{viewDataId}" 
			pager="true" 
			navigator="true"
			navigatorSearch="true"
			navigatorView="false" 
			navigatorAdd="false"
			navigatorDelete="true"
			viewrecords="true"       
			navigatorEdit="false"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="ratingfDataList"
			rowList="15,30,150" 
			rowNum="15" 
			multiselect="false"
			loadingText="Please wait..." 
			rownumbers="true"
			autowidth="true"
			shrinkToFit="false"
			onSelectRowTopics="rowselect"
			>
		
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true" />
		<sjg:gridColumn name="clientname" index="clientname" title="Associate Name" align="left" sortable="true" search="false" width="280" />
		<sjg:gridColumn name="address" index="address" title="Address" align="left" sortable="true" search="true" width="200" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="webAddress" index="webAddress" title="Website" align="left" sortable="true" search="false" width="150" />
		<sjg:gridColumn name="location" index="name" title="Location" align="left" sortable="true" search="false" width="100" />
		<sjg:gridColumn name="offeringName" index="offeringName" title="Offering" align="left" sortable="true" search="false" width="100" />
	</sjg:grid>
	</div>
</div>
</body>
</html>