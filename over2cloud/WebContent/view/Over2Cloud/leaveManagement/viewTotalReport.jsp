<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>

</head>
<body>


<s:hidden id="tdateValue" value="%{tdate}"></s:hidden>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>

<s:url id="reportTotalGridDataView" action="reportTotalGridDataView" escapeAmp="false">
<s:param name="tdate" value="%{tdate}" />
<s:param name="deptId" value="%{deptId}" />
<s:param name="subdeptId" value="%{subdeptId}" />
<s:param name="emp" value="%{emp}" />
</s:url>
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{reportTotalGridDataView}"
          gridModel="reportTotalView"
          navigator="true"
          loadonce="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="true"
          viewrecords="true"  
          editurl="%{editTimeSlotData}"   
          editurl="%{editTimeSlotData1}" 
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          rowNum="5"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
		  multiselect="true"
          >
          
		<s:iterator value="reportGridColoumnNames" id="reportGridColoumnNames" status="test">  
	
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid></div>
</body>


</html>