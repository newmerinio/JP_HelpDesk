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
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
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
		$("#gridedittable").jqGrid('delGridRow',ids, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true});
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
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}

function getSearchData()
{
	var status=$("#status").val();
	var deptName=$("#deptName").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action?modifyFlag=1&deleteFlag=0&status="+status+"&deptName="+deptName,
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

function ViewCompletionTip(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='CompletionTip("+rowObject.id+")'><font color='blue'>View</font></a>";
}
function ViewKR(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='KR("+rowObject.id+")'><font color='blue'>View</font></a>";
}

function CompletionTip(taskId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipWithTaskId.jsp?dataId="+taskId,
	    success : function(data) 
	    {
			$("#krCompletionTip").dialog({title: 'Check List',width: 600,height: 350});
			$("#krCompletionTip").dialog('open');
			$("#krCompletionTip").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
function KR(taskId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=KRName",
	    success : function(data) 
	    {
		$("#krCompletionTip").dialog({title: 'KR Name',width: 500,height: 350});
		$("#krCompletionTip").dialog('open');
		$("#krCompletionTip").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}

function addNewKRName() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewTaskName4AddKR.action",
	    success : function(data) 
	    {
		$("#data_part").html(data);
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
<sj:dialog
          id="krCompletionTip"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Compliance"
          modal="true"
          closeTopics="closeEffectDialog"
          width="700"
          height="350"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Task Name</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	<%if(userRights.contains("tana_MODIFY")) {%>
								<sj:a id="editButton" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onclick="editRow()"></sj:a>
							<%} %>
							<%if(userRights.contains("tana_DELETE")) {%>
							<s:if test="%{status=='Active'}">
								<sj:a id="delButton"  title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
							</s:if>
							<%} %>	
							 <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
						 	 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
						     <s:select id="deptName" name="deptName" list="allDeptName" headerKey="-1" headerValue="Select Department" cssClass="button"
								onchange="getSearchData()" theme="simple" cssStyle="height:28px;">
	       					 </s:select>
						     <s:select list="#{'Active':'Active','Inactive':'Inactive','All':'All'}" id="status" name="status" cssClass="button" theme="simple" cssStyle="height:28px;" onchange="getSearchData()"></s:select>
					   		 <%-- <s:textfield name="searchParameter" id="searchParameter" cssClass="textField" theme="simple" cssStyle="height:22px; width:100px" onblur="getSeacrhData()" placeHolder="Enter Data" cssClass="button"></s:textfield> --%> 
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				    <%if(userRights.contains("tana_ADD")) {%>
						<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewTaskName();">Add</sj:a>
						<sj:a id="addButtonKR"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewKRName();">Add KR</sj:a>
	      			<%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<s:url id="veiwTaskType" action="viewComplTask" escapeAmp="false">
<s:param name="deptName" value="%{deptName}"/>
<s:param name="status" value="%{status}"/>


</s:url>
<s:url id="viewModifyTaskType" action="viewModifyAction" />
<sjg:grid 
		  id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          navigatorDelete="true"
          navigatorEdit="true"
          groupField="['task_type']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          rowList="100,500,1000"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          rownumbers="true"
          loadingText="Requesting Data..."  
          navigatorViewOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          multiselect="true"
          onSelectRowTopics="rowselect"
          filter="true"
          filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="colomnName=='sub_type_id' || colomnName=='completion' || colomnName=='task_type' || colomnName=='task_name'">
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
			/>
		
		</s:if>
		<s:elseif test="colomnName=='accounting' || colomnName=='completion_tip' || colomnName=='budgetary' || colomnName=='kr_name'">
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
			/>
		</s:elseif>
		<s:elseif test="%{colomnName=='status'}">
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
		</s:elseif>
		<s:else>
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
			/>
		</s:else>
		
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</div>
</body>
</html>