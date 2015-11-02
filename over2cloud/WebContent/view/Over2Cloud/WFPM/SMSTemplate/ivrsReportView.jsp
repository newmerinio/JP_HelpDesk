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
		<div class="head">IVRS Report >> View</div>
	</div>
	<s:url id="templateView" action="smsTemplateView?type=kpi"></s:url>
	<s:url id="smsTemplate" action="deleteSMSTemplate"></s:url>
	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="rightHeaderButton">
			<sj:a
				button="true"
				buttonIcon="ui-icon-plus"
				href="#"
				onclick="addNewTemplate();"
			>
				Add
			</sj:a>
			
			  
			<span class="normalDropDown"> </span>
		</div>
		
		
	 	<div class="clear"></div>
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
				<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow();">Edit</sj:a>	
	<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>	
				</td></tr></tbody></table>
				<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"></td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
	 	
	 	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{templateView}" 
			editurl="%{smsTemplate}"
			pager="true"
			navigator="false"
			navigatorSearch="false"
			navigatorView="false" 
			navigatorAdd="false"
			navigatorDelete="true"
			viewrecords="true"       
			navigatorEdit="false"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="templateGridModelList"
			rowList="10,20,30,40,50" 
			rowNum="10" 
			multiselect="true"
			loadingText="Please wait..." 
			rownumbers="true"
			autowidth="true"
			onSelectRowTopics="rowselect"
			>
		
		<sjg:gridColumn name="id" index="id" title="ID" hidden="true" />
		<sjg:gridColumn name="generatedTemplate" index="generateTemp" title="Generate Template" align="left" editable="" sortable="true" search="true" width="200"/>
		<sjg:gridColumn name="keyword" index="keyword" title="Keyword" align="left" sortable="true" search="true" width="150"/>
		<sjg:gridColumn name="shortCodes" index="shortCode" title="Short Code" align="left" sortable="true" search="true" width="150"/>
		<sjg:gridColumn name="revertMessage" index="replySMS" title="Reply SMS" align="left" sortable="true" search="true" width="100"/>
		<sjg:gridColumn name="length" index="length" title="Length" align="center" sortable="true" search="true" frozen="true" width="100"/>
		<sjg:gridColumn name="paramName" index="paramName" title="Param Name" align="left" sortable="true" search="true" width="100" frozen="true"/>
		<sjg:gridColumn name="date" index="date" title="Date" align="center" sortable="true" search="true" width="100"/>
		
	</sjg:grid>
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
</script>
</html>