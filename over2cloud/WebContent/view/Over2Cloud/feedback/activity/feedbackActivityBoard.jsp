<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">

</SCRIPT>
</head>
<body>
<s:url id="feedbackActivityGrid"  escapeAmp="false" action="viewActivityData">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="status" value="%{status}"></s:param>
<s:param name="feedBack" value="%{feedBack}"></s:param>
<s:param name="mode" value="%{mode}"></s:param>
<s:param name="ticket_no" value="%{ticket_no}"></s:param>
<s:param name="feedBy" value="%{feedBy}"></s:param>
<s:param name="dept" value="%{dept}"></s:param>
<s:param name="catg" value="%{catg}"></s:param>
<s:param name="subCat" value="%{subCat}"></s:param>
<s:param name="wildsearch" value="%{wildsearch}"></s:param>
<s:param name="clientId" value="%{clientId}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
<s:param name="dataFor" value="%{dataFor}"></s:param>

</s:url>
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{feedbackActivityGrid}"
		          dataType="json"
		          gridModel="masterViewList"
		          pager="true"
		          groupField="['targetOn']"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          groupText="['<b> {0}: {1}</b>']"
		          groupCollapse="true"
		          groupColumnShow="false"
		          rowList="1000,2000"
		          rownumbers="-1"
		          multiselect="true"  
		          loadingText="Requesting Data..."  
		          rowNum="1000"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          rownumbers="true"
		          onSelectRowTopics="rowselect"
		          multiboxonly="true"
		          viewrecords="true"
		          onCompleteTopics="colorEscalation"
		          sortable="true"
		          
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
				<sjg:gridColumn 
				name="%{colomnName}"
				index="%{index}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				search="true"
				hidden="%{hideOrShow}"
				formatter="%{formatter}"
				/>
				</s:iterator>  
				
		</sjg:grid>
</body>
</html>