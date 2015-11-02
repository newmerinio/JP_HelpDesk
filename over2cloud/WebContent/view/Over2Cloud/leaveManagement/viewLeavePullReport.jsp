<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<body>





 
   
<s:url id="pullreportGridDataView" action="viewLeavePullReport" escapeAmp="false">
 <s:param name="fieldser" value="%{fieldser}"></s:param>
 <s:param name="fieldval" value="%{fieldval}"></s:param>
 <s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:url id="editemployeeTypeData" action="employeeTypeGridDataModify" />
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
</s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{pullreportGridDataView}"
          gridModel="viewLeavePullData"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          editurl="%{editemployeeTypeData}" 
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          onSelectRowTopics="rowselect"
          multiselect="false"
          >
          
		<s:iterator value="pullreportGridColomns" id="pullreportGridColomns" >  
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


</body>

</html>