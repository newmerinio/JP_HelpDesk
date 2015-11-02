<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

</script>

</head>
<body>
<div style="width:100%;">
    
<s:url id="requestGridDataView" action="pmsoutcomeGridDataView" escapeAmp="false">
	<s:param name="fromDate" value="%{fromDate}"></s:param>
	<s:param name="toDate" value="%{toDate}"></s:param>
	<s:param name="status" value="%{status}"></s:param>
	<s:param name="empType" value="%{empType}"></s:param>
	<s:param name="deptId" value="%{deptId}"></s:param>
	<s:param name="emp" value="%{emp}"></s:param>
	
</s:url>
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{requestGridDataView}"
          gridModel="viewResponse"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          rowTotal="10"
          autowidth="true"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          filter="true"
          
          >
          
		<s:iterator value="requestGridColomns" id="requestGridColomns" >  
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


</div>
</body>
</html>