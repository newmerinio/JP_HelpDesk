<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<s:url id="viewTotalRepair" action="viewTotalAssetRank" escapeAmp="false">
<s:param name="deptId" value="%{deptId}" />
<s:param name="assetid" value="%{assetid}" />
<s:param name="type" value="%{type}" />
</s:url>
<sjg:grid 
		  id="totalRepairGrid"
          caption="%{mainHeaderName}"
          href="%{viewTotalRepair}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          shrinkToFit="false"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" status="test">  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>

</body>
</html>