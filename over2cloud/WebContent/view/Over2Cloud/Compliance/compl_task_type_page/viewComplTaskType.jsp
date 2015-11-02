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
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/highLighter.js"/>"></script>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	var rows= $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	if(rows==0)
	{
		alert("Please select atleast one row.");
	}
	if(rows.length>1)
	{
		alert("Please select only one row.");
	}	
	else
	{	
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
}
function deleteRow()
{
	var ids = $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	if(ids==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{	
		$("#gridedittable").jqGrid('delGridRow',ids, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}	
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskTypeView.action?modifyFlag=1&deleteFlag=0",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}	

function getSeacrhData()
{
	var status=$("#status").val();
	var searchParameter=$("#searchParameter").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskTypeView.action?modifyFlag=1&deleteFlag=0&status="+status+"&searchParameter"+searchParameter,
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

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Task Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	<%if(userRights.contains("taty_MODIFY")) {%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
							<%} %>
							<%if(userRights.contains("taty_DELETE") ) {%>
							<s:if test="%{status=='Active'}">
								<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							</s:if>
							<%} %>	
							    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
  								<s:select list="#{'Active':'Active','Inactive':'Inactive','All':'All'}" id="status" name="status" cssClass="button" theme="simple" cssStyle="height:25px;" onchange="getSeacrhData()"></s:select>
					   			<s:textfield placeHolder="Enter Data" name="searchParameter" id="searchParameter" cssClass="textField" theme="simple" cssStyle="height:22px; width:100px" onblur="getSeacrhData()"></s:textfield>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				     <%if(userRights.contains("taty_ADD")) {%>
							<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewTaskType();">Add</sj:a>
	      			<%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 440px;">
<s:url id="veiwTaskType" action="viewTaskType" >
<s:param name="status" value="%{status}"/>
<s:param name="searchParameter" value="%{searchParameter}"/>
</s:url>
<s:url id="viewModifyTaskType" action="viewModifyTaskTypeAction" />
<sjg:grid 
		 
          id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="masterViewList"
          groupField="['sub_type_id']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          editinline="false"
          rowList="100,200,500"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="true"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          filter="true"
          filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
          
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					/>
		</s:if>
		<s:else>
			<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="400"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
			/>
		</s:else>
		</s:iterator>  
</sjg:grid>
</div>
</div>
</div>
</body>

</html>