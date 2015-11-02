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


<s:url id="attendanceGridDataView" action="attendanceGridDataView" escapeAmp="false">
<s:param name="empname" value="%{empname}" />
<s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:param name="modifyFlag" value="%{modifyFlag}" />
</s:url>

<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{attendanceGridDataView}"
          gridModel="viewReportAttendance"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="25,50,75"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          rowNum="25"
           autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          >
          
		<s:iterator value="reportGridColoumnNames" id="reportGridColoumnNames" >  
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
</sjg:grid>
</div>


</body>

</html>