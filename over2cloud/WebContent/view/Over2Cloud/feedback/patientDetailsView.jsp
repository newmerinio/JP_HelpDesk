<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadGrid()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}
</SCRIPT>
</head>
<body>
<s:url id="viewPatients" action="viewPatientsInGrid" escapeAmp="false">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
</s:url>
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewPatients}"
		          gridModel="masterViewList"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          rowList="15,30,45,60"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{viewModifyFeedback}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
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
				/>
				</s:iterator>  
		</sjg:grid>
</body>
</html>