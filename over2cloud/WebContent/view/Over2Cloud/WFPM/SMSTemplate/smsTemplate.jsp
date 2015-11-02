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

<SCRIPT type="text/javascript">
	function addNewTemplate()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeAddSMSTemplate.action",
		    success : function(subdeptdata) {
	      		$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	/* * Format a Column as Image */
	 function formatImage(cellvalue, options, row) {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "<img title='Delete' style='cursor: pointer; margin-left: 11px;' src='"+ context_path +"/images/delete1.png' onClick='deleteMe("+row.id+");' />";}

	 function formatLink(cellvalue, options, rowObject) {
		 alert(cellvalue);
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }

	 function editTemplate(id)
	 {
		 $('#editDialog').dialog('open');
	}
	  function deleteMe(id)
	  {
		  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		  $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/SMSTemplate/deleteSMSTemplate.action",
			    data : "id="+ id,
			    success : function(subdeptdata) {
			  $("#gridedittable").trigger("reloadGrid"); 
			},
			   error: function() {
		            alert("Record not deleted");
		        }
			 });
	  };

	  /* Ajax for show none register user */
	  
	  function showNoneRegisterUser()
	  {
		  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		  $.ajax({
				type : "post",
				url : "view/Over2Cloud/WFPM/SMSTemplate/beforeNoneRegister.action",
			    success : function(subdeptdata) {
					$("#grid").html(subdeptdata); 
				},
				error: function() {
		            alert("Error");
		        }
			  });
		}

</SCRIPT>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">SMS Template</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">View</div>
	</div>
	<s:url id="templateView" action="smsTemplateView?type=kpi"></s:url>
	<s:url id="smsTemplate" action="deleteSMSTemplate">
	<s:param name="deleteFlage" value="1"></s:param>
	</s:url>
	<div style=" float:left; padding:10px 1%; width:98%;">
	<s:if test="true || #session['userRights'].contains('kpte_ADD')">	
		<div class="rightHeaderButton">
			<span class="normalDropDown"> </span>
		</div>
	</s:if>	
		
	 	<div class="clear"></div>
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<s:if test="true || #session['userRights'].contains('kpte_DELETE')">
					<%-- <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a> --%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>	
	</s:if>			
	<s:if test="true || #session['userRights'].contains('kpte_VIEW') 
				|| #session['userRights'].contains('kpte_MODIFY') 
				|| #session['userRights'].contains('kpte_DELETE')">
				</td></tr></tbody></table>
				</td><td class="alignright printTitle">
				 		 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
					     <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Upload" buttonIcon="ui-icon-arrowstop-1-n" onclick="excelUploadd();" />
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewTemplate();">Add</sj:a>
				
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
	 	<div style="overflow: scroll; height:450px;">
	 	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{templateView}" 
			editurl="%{smsTemplate}"
			pager="true"
			navigator="true"
			navigatorSearch="true"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="true"
			viewrecords="true"       
			navigatorEdit="false"
			navigatorSearchOptions="{sopt:['eq','cn']}"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="templateGridModelList"
			rowList="15,20,25,30" 
			rowNum="15" 
			multiselect="false"
			loadingText="Please wait..." 
			rownumbers="true"
			autowidth="true"
			shrinkToFit="false"
			onSelectRowTopics="rowselect"
			>
		
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true"  search="false"/>
		<sjg:gridColumn name="generatedTemplate" index="generateTemp" title="Generate Template" align="left" editable="" sortable="true" search="true" width="180" search="false"/>
		<sjg:gridColumn name="keyword" index="keyword" title="Keyword" align="left" sortable="true" search="true" width="79" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="shortCodes" index="shortCode" title="Short Code" align="left" sortable="true" search="true" width="110" search="false"/>
		<sjg:gridColumn name="revertMessage" index="replySMS" title="Reply SMS" align="left" sortable="true" search="true" width="440" search="false"/>
		<sjg:gridColumn name="length" index="length" title="Length" align="center" sortable="true" search="true" frozen="true" width="100" search="false"/>
		<sjg:gridColumn name="paramName" index="paramName" title="Param Name" align="left" sortable="true" search="true" width="130" frozen="true" search="false"/>
		<sjg:gridColumn name="userName" index="userName" title="Created By" align="center" sortable="true" search="true" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="date" index="date" title="Created On" align="center" sortable="true" search="true" width="100" searchoptions="{sopt:['eq','cn']}"/>
	</sjg:grid>
	</div>
	</s:if>
	
	</div>
</body>
<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
	function editRow()
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		$("#gridedittable").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	/* function refreshRow()
	{
		$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	} */
	
	function refreshRow(rowid, result) {
		  $("#gridedittable").trigger("reloadGrid"); 
		}
</script>
</html>