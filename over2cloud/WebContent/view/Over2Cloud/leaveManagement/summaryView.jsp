<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

</head>
<body>
<s:url id="summaryViewGrid" action="summaryViewGrid" escapeAmp="false">
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
<s:param name="empname" value="%{empname}"></s:param>
</s:url>
<div style="overflow: scroll; height: 230px;">
<sjg:grid 
		  id="gridedittable"
          href="%{summaryViewGrid}"
          gridModel="summaryView"
          loadonce="true"
          navigatorEdit="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="false"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true" 
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          >
          
		<s:iterator value="summaryColomnsNames" id="summaryColomnsNames" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		frozen="%{frozenValue}"
		/>
		</s:iterator>  
</sjg:grid></div>


</body>
<sj:dialog 
		id="downloadColumnSummaryReport" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Summary Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadSummaryReportColumnDiv"></div>
</sj:dialog>
</html>