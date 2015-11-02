<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function createRCA(module)
{
	var moduleName=$("#"+module).val();
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/RCAOver2Cloud/RCAMaster/beforeRCAMasterPage.action?moduleName="+moduleName,
	    success : function(confirmation) {
      $("#"+"data_part").html(confirmation);
	   },
	   error: function() {
          alert("error");
      }
	 });
}

</script>
</head>

<body>
<div class="list-icon">
	 <div class="head">RCA</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
 <div class="clear"></div>
  
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
		<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td>
		 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	 		 <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
	   		 <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
			 <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
									<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
		</td></tr></tbody></table>
		</td>
		<td class="alignright printTitle">
			<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="createRCA('moduleName');">Add</sj:a>
		</td>   
		</tr></tbody></table>
		</div>
	</div>
	<s:hidden id="moduleName" value="%{moduleName}"/>
<div style="overflow: scroll; height: 430px;">
<s:url id="modifyClientData" action="modifyClientGrid" />
<s:url id="deleteClientData" action="deleteClientGrid" />
<s:url id="rcaView" action="rcaMasterViewData" escapeAmp="false">
<s:param name="moduleName" value="%{moduleName}"/>
</s:url>
<sjg:grid 
		 id="gridedittabletttt"
          href="%{rcaView}"
          gridModel="viewList"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          editurl="%{modifyClientData}"
          editurl="%{deleteClientData}"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="rcaMasterViewProp" id="rcaMasterViewProp" >  
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
</div>
</div>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridedittabletttt").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	$("#gridedittabletttt").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}

function searchRow()
{
	 $("#gridedittabletttt").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittabletttt");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
</html>