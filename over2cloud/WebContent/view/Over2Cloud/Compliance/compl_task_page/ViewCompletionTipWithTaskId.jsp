<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var row=0;
$.subscribe('rowselectoncomptip', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable11111").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable11111").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable11111").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action?modifyFlag=1&deleteFlag=0",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	var grid = $("#gridedittable11111");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
</head>
<body>
<s:url id="completionTip_URL" action="completionTip_URL" escapeAmp="false">
<s:param name="dataId" value="%{#parameters.dataId}"></s:param>
</s:url>
<s:url id="completionTip_URL_Edit" action="completionTip_URL_Edit" escapeAmp="false"/>
<div id="escalationMapResult" align="center">
<div align="center">
<sjg:grid 
		  id="gridedittable11111"
          href="%{completionTip_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
          editinline="true"
          editurl="%{completionTip_URL_Edit}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          onSelectRowTopics="rowselectoncomptip"
          >
		 		   <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						align="center"
		      						width="75"
		      						hidden="true"
		 							/>
		       
		 		 <sjg:gridColumn 
		      						name="completion_tip"
		      						index="completion_tip"
		      						title="Completion Tip"
		      						width="538"
		      						align="left"
		      						editable="true"
		 							/>	
		  </sjg:grid>
		  </div>
		  <br>
		  <br>
		  <%-- <sj:a id="editButton" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onclick="editRow()">Edit</sj:a>
		  <sj:a id="delButton"  title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
   </div>
</body>
</html>