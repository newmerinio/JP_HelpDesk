<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
   <%@taglib prefix="sj" uri="/struts-jquery-tags"%>
   <%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

</head>
<body>
	<div id='grid' style="margin-top: 50px;">
		<sjg:grid
			id="gridedittable"
			caption="SMS Template >> View"
			dataType="json"
			href="%{templateView}" 
			editurl="%{smsTemplate}"
			pager="true" 
			navigator="true"
			navigatorSearch="false"
			navigatorView="false" 
			navigatorAdd="false"
			navigatorDelete="true"
			viewrecords="true"       
			navigatorEdit="false"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="templateGridModelList"
			rowList="10,20,30,40,50" 
			rowNum="10" 
			multiselect="true"
			loadingText="Please wait..." 
			width="850"
			rownumbers="true"
			>
		
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true" />
		<sjg:gridColumn name="generatedTemplate" index="generateTemp" title="Generate Template" align="left" sortable="true" search="true" width="200"/>
		<sjg:gridColumn name="keyword" index="keyword" title="Keyword" align="left" sortable="true" search="true" width="150"/>
		<sjg:gridColumn name="shortCodes" index="shortCode" title="Short Code" align="left" sortable="true" search="true" width="150"/>
		<sjg:gridColumn name="revertMessage" index="replySMS" title="Reply SMS" align="left" sortable="true" search="true" width="100"/>
		<sjg:gridColumn name="length" index="length" title="Length" align="center" sortable="true" search="true" frozen="true" width="100"/>
		<sjg:gridColumn name="paramName" index="paramName" title="Param Name" align="left" sortable="true" search="true" width="100" frozen="true"/>
		<sjg:gridColumn name="date" index="date" title="Date" align="center" sortable="true" search="true" width="100"/>
		
	</sjg:grid>
	</div>
</body>
</html>