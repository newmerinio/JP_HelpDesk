<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>

<title>Opportunity History View</title>
</head>
<body>

	<div style=" float:left; padding:10px 1%; width:98%;">
	 	<div class="clear"></div>
		<div class="listviewBorder" style="height: 43px;">
	 	<s:url id="historyDataView" action="historyDataView">
	 	<s:param name="id" value="%{id}"></s:param>	
	 	</s:url>
	 	
	 	<div style="overflow: scroll; height:400px;">
	 	<sjg:grid
			id="historygridId"
			href="%{historyDataView}" 
			dataType="json"
			pager="true"
			navigator="true"
			navigatorSearch="true"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="true"
			viewrecords="true"       
			navigatorEdit="false"
			navigatorSearchOptions="{sopt:['eq','cn']}"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="historyGridModelList"
			rowList="15,20,250" 
			rowNum="15" 
			multiselect="false"
			loadingText="Please wait..." 
			rownumbers="true"
			autowidth="true"
			shrinkToFit="false"
			onSelectRowTopics="rowselect"
			>
		
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true"  search="false"/>
		<sjg:gridColumn name="activity" index="activity" title="Activity" align="left" editable="false" sortable="true" search="true" width="200" search="false"/>
		<sjg:gridColumn name="date" index="date" title="Date & Time" align="left" sortable="true" search="true" width="110" searchoptions="false"/>
		<sjg:gridColumn name="contactperson" index="contactperson" title="Contact Person" align="left" sortable="true" search="true" width="160" search="false"/>
		<sjg:gridColumn name="comment" index="comment" title="Comment" align="left" sortable="true" search="true" width="310" search="false"/>
		<sjg:gridColumn name="accountmanager" index="accountmanager" title="Account Manager" align="left" sortable="true" search="true" width="100" search="false"/>
	</sjg:grid>
	</div>
		</div>
	</div>
</body>
</html>