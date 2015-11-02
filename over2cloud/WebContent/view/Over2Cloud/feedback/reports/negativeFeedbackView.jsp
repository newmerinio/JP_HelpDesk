<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<s:url id="viewFeedbackInGrid" action="viewPositiveCustomerInGrid" escapeAmp="false">
<s:param name="flage" value="%{flage}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="mode" value="%{mode}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
<s:param name="docName" value="%{docName}"></s:param>
<s:param name="spec" value="%{spec}"></s:param>
<s:param name="actionStatus" value="%{actionStatus}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{viewFeedbackInGrid}"
          gridModel="custDataList"
          groupField="['mode']"
          groupText="['<b>{0} {1}</b>']"
		  groupCollapse="true"
          groupColumnShow="[false]"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          navigatorRefresh="true"
          rowList="100,200,300"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="100"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          rownumbers="true"
          >
		<s:iterator value="CRMColumnNames" id="CRMColumnNames" >  
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
		      			   frozen="%{frozenValue}"
		      			   
		 				   />
		   </s:iterator>  		
</sjg:grid>
</body>
</html>