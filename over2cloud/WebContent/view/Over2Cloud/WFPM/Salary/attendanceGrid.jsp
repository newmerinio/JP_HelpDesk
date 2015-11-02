<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
function refreshRow(rowid, result) {
	  $("#gridedittable").trigger("reloadGrid"); 
	}
</script>
<script type="text/javascript">
	$.subscribe('complete', function(event,data)
    {
    	setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });


	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
	function editRow()
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchData()
	{
		jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
</script>
</head>

<body>
<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{mainHeaderName}"/> >> View</div> --%>
		<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>

<div class="container_block">

<div style=" float:left; padding:10px 1%; width:98%;">

<s:url id="empInfo" action="empInformation" />
<s:url id="editEmpInfo" action="editEmpInformation" />
<div class="clear"></div>	 	
	 	
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 

<s:if test="#session['userRights'].contains('atte_MODIFY')">
	<%-- <sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow();">Edit</sj:a> --%>	
<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
</s:if>
<sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
<s:if test="#session['userRights'].contains('atte_VIEW') || #session['userRights'].contains('atte_MODIFY')">
<!-- <input name="cvDelete" id="cvDelete" class="button" value="Search" type="button" onclick="searchData();"> -->
<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
	</td> 
</tr></tbody></table></div></div></div>
<div style="overflow: scroll; height: 480px;">
	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{empInfo}" 
			pager="true" 
		     navigator="true"
	         navigatorAdd="false"
	         navigatorView="true"
	         navigatorEdit="true"
	         navigatorDelete="true"
	         navigatorSearch="true"
	         navigatorRefresh="true"
	         navigatorSearch="true"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			 navigatorSearchOptions="{sopt:['eq','cn']}"
			editinline="false" 
			gridModel="empDetail"
			rowList="15,20,25,30" 
			rowNum="10" 
			multiselect="true"
			draggable="true" 
			loadingText="Requesting Data..." 
			shrinkToFit="true"
			width="800"
			rownumbers="true"
			loadonce="true"
		    autowidth="true"
		    onSelectRowTopics="rowselect"
			>
	
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true" />
		<sjg:gridColumn name="empName" index="empName" title="Emp Name" align="center" editable="true" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="mobileNo" index="mobileNo" title="Mobile No" align="center" sortable="true" editable="true" search="true"  searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="emailId" index="emailId" title="Email Id" align="center" sortable="true" search="true"/>
		<sjg:gridColumn name="attandance" index="attandance" title="Attandance" align="center" sortable="true" editable="true" search="true" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="month" index="month" title="Month" align="center" sortable="true" editable="true" search="true" searchoptions="{sopt:['eq','cn']}"/>
	</sjg:grid>
	</div>
</s:if>
</div> 
</div>
</body>
</html>