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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSuportCatgView.action?modifyFlag=0&deleteFlag=0",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}	

function searchResult(searchField, searchString, searchOper)
{
	
	//alert( searchOper);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareViewPage.action",
	    data : "&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	   
	    
}
function addEscDept()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/Escalation_Conf/BeforeConfigEscalation.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
//fillAlphabeticalLinks();


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Configure Escalation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
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
					     	<%if(userRights.contains("spde_MODIFY")) {%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
							<%} %>
							<%if(userRights.contains("spde_DELETE")) {%>
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<%} %>	
								<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addEscDept();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewEscDept" action="viewEscDept">
</s:url>
<sjg:grid 
		 
          id="sparecGrid"
          href="%{viewEscDept}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="true"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          
          >
        <sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		width="165"
		align="left"
		editable="false"
		hidden="true"
		/>
		
		<sjg:gridColumn 
		name="escFor"
		index="escFor"
		title="Escalation For"
		width="140"
		align="center"
		editable="false"
		/>
		<sjg:gridColumn 
		name="deptName"
		index="deptName"
		title="Department"
		width="140"
		align="center"
		editable="false"
		/>
		
		
		 
		<sjg:gridColumn 
		name="considerRoaster"
		index="considerRoaster"
		title="Consider Roaster"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="addresingTime"
		index="addresingTime"
		title="Addressing Time"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="resolutionTime"
		index="resolutionTime"
		title="Resolution Time"
		width="120"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="escalationTime"
		index="escalationTime"
		title="Escalation Time"
		width="130"
		align="center"
		editable="false"
		/>
		 
		
</sjg:grid>
</div>
</div>
</div>
</div>
</body>

</html>