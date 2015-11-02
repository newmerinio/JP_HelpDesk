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
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#viewMappedContactId").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#viewMappedContactId").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#viewMappedContactId").jqGrid('searchGrid', {sopt:['eq','cn'],caption:'Padam'} );
}
function reloadRow()
{
	var dataFor=$("#outletEmpLevel").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/BeforeViewCont2OutletMapping.action?moduleName=ASTM&dataFor="+dataFor,
	    success : function(data) 
	    {
			$("#"+"dataDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	var grid = $("#viewMappedContactId");
	grid.trigger("reloadGrid",[{current:true}]);
}

function mapNewContact()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createCont2OutletMapping.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function getOnloadData()
{
	var dataFor=$("#outletEmpLevel").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/BeforeViewCont2OutletMapping.action?moduleName=ASTM&dataFor="+dataFor,
	    success : function(data) 
	    {
			$("#"+"dataDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
getOnloadData();
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Map Contact In Outlet </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
							    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
					      </td>
					      	<s:select 
				                  id="outletEmpLevel" 
						          name="outletEmpLevel" 
						          list="#{'TAH':'Ticket Allot Level','OEH':'Outlet Emp Level'}"
						          cssClass="button"
						          theme="simple"
						          onchange="getOnloadData();"
         			 />
					      </tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(userRights.contains("alma_ADD")) {%>
	      	         <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="mapNewContact();">Add</sj:a>
	      	       <%} %>
 				</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	<div style="overflow: scroll; height: 400px;">
		<div id="dataDiv"></div>
	</div>	
		</body>
</html>