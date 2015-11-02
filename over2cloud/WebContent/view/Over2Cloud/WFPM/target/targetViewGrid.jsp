<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;});	
	function editRow(){
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:-20,left:350,modal:true});
	}
	function deleteRow(){
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
	}
	function refreshRow(rowid, result) {
		  $("#gridedittable").trigger("reloadGrid"); 
		}
</script>
</head>
<body>
<center>
<s:url id="targetGridView" action="targetGridView?targetOn=%{targetOn}&empID=%{empID}" >
</s:url>
<div class="listviewBorder">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
		<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
			
	<s:if test="#session['userRights'].contains('targ_MODIFY') ">
			<%-- <sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a> --%>
			<%-- <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px;" cssClass="button" button="true" onclick="editRow()"></sj:a> --%>
	</s:if>
	<s:if test="#session['userRights'].contains('targ_DELETE') ">
			<%-- <sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
			<%-- <sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a> --%>
	</s:if>
		<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
		</td></tr></tbody></table>
		</td><td class="alignright printTitle"></td>   
	</tr></tbody></table></div>
	</div>
	
	<s:if test="#session['userRights'].contains('targ_VIEW') 
				|| #session['userRights'].contains('targ_MODIFY') 
				|| #session['userRights'].contains('targ_DELETE') ">
	<div style="overflow: scroll; height: 480px;">
	<sjg:grid 
			  id="gridedittable"
	          href="%{targetGridView}"
	          gridModel="gridModel"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorEdit="false"
	          navigatorDelete="false"
	          navigatorSearch="false"
	          navigatorRefresh="true"
	          navigatorAdd="false"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rowNum="5"
	          navigatorSearchOptions="{sopt:['eq','ne']}"
	          shrinkToFit="true"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          
	          >
	          
			<s:iterator value="mappedFields" id="mappedFields" >  
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
</s:if>

</div>
</center>
</body>
</html>
