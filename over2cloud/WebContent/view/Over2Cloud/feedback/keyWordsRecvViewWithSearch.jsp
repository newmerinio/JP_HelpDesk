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
function getSearchData()
{

	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var keyType=$("#keyType").val();
	
	$.ajax({
 		type :"post",
 		url : "view/Over2Cloud/feedback/beforeKeyView.action?fromDate="+fromDate+"&toDate="+toDate+"&keyType="+keyType.split(" ").join("%20"),
 		success : function(data) 
	    {
			$("#keyWordDiv").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
	
}

getSearchData();
</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">Keywords Details</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
					        <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" onchange="resetToDate(this.value)" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
		     				<sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date" />  
					         <s:select 
                      id="keyType" 
			          name="keyType" 
			          list="keyTypeMap"
			          headerKey="-1"
			          headerValue="Keyword"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;width: 120px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
					         
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="keyWordDiv">
</div>
</div>		
</div>
</div>
</body>
</html>