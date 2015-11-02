<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<s:url id="reportGrid"  escapeAmp="false" action="viewReportGridData">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="searchFor" value="%{searchFor}"></s:param>
<s:param name="dept" value="%{dept}"></s:param>
</s:url>
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{reportGrid}"
		          dataType="json"
		          gridModel="masterViewList"
		          pager="true"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          rowList="15,50,100"
		          rownumbers="-1"
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          rownumbers="true"
		          viewrecords="true"
		          onCompleteTopics="escalationColor"
		          
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
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
				/>
				</s:iterator>  
		</sjg:grid>
</body>
</html>