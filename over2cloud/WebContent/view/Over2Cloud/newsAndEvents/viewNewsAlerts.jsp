<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/feedback/feedback.js"/>"></script>
<SCRIPT type="text/javascript">
function addNewSubDept() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/newsAlertsConfig/beforeNewsAdd.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
	
}
function searchData()
{
	jQuery("#gridmultitable1").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
</SCRIPT>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridmultitable1").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	$("#gridmultitable1").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function reload(rowid, result) {
	  $("#gridmultitable1").trigger("reloadGrid"); 
	}
</script>
</head>
<body>
<div class="list-icon">
<div class="head">News & Alerts</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View  </div>
</div>

<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    <td class="pL10">
						 <%if(userRights.contains("desi_MODIFY")) {%>       
                   <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
			<%} %>
			<!-- <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a> -->
					
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
				
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				 <%--   <%if(userRights.contains("desi_ADD")) {%> --%>
				     <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				             buttonIcon="ui-icon-plus"
							onclick="addNewSubDept();"
							>Add</sj:a> 
							<%-- <%} %> --%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<s:url id="viewNews" action="showAllNewsAlerts"/>
<s:url id="editNewsndevents" action="editNewsndevents"/>
		<sjg:grid
    	id="gridmultitable1"
    	dataType="json"
    	href="%{viewNews}"
    	pager="true"
    	navigator="true"
    	navigatorSearch="true"
    	navigatorSearchOptions="{sopt:['eq','cn']}"
    	navigatorAddOptions="{height:280,reloadAfterSubmit:true}"
    	navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
    	navigatorView="true"
    	navigatorDelete="true"
    	navigatorAdd="false"
    	navigatorEdit="true"
    	gridModel="viewNewsAlerts"
    	multiselect="true"
    	rowList="15,20,30"
    	rowNum="15"
    	autowidth="true"
    	rownumbers="true"
    	editurl="%{editNewsndevents}"
    	onSelectRowTopics="rowselect"
    	shrinkToFit="false"
    >
    	        
		<s:iterator value="gridColomns" id="level1ColmnNames" > 
		<s:if test="%{colomnName=='start_date' || colomnName=='end_date'">  
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
			editoptions="{dataInit:datePicker}"
					/>
				
				</s:if>
				<s:else>
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
				</s:else>
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>