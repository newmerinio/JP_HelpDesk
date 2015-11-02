<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<SCRIPT type="text/javascript">
		function addKRA()
		{
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/hr/beforeKRAKPICreate.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}	
	</SCRIPT>
	
	<script type="text/javascript">
		var row=0;
		$.subscribe('rowselect', function(event, data) {
			row = event.originalEvent.id;
		});	
		/* function deleteRow(){
			$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
		}
		function editRow(){
			jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:-150,left:350,modal:true});
		} */
		function editRow()
		{
			jQuery("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
		}
		
		function deleteRow()
		{
			row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
			$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
		}
		function searchRow()
		{
			jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
		}
		function refreshRow(rowid, result) {
			  $("#gridedittable").trigger("reloadGrid"); 
			}
		
	</script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{mainHeaderName}"/> >> View</div> --%>
		<div class="head">KRA-KPI</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:20%; float: left;"></div><div class="head">View</div>
	</div>
	
	<div style=" float:left; padding:10px 1%; width:98%;">
		<s:url id="viewClient" action="viewClientGrid?isExistingClient=%{isExistingClient}&lostFlag=%{lostFlag}&clientStatus=%{clientStatus}" />
		<s:url id="modifyClient" action="viewModifyClient" />
		<s:url id="viewClientContact" action="viewClientContactGrid" />
		<s:url id="modifyClientContact" action="viewModifyClientContact" />
			
			
		<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
		<s:url id="viewKRAKPI" action="viewKRAKPI" />
		<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->
		
		<s:url id="viewKRAKPIOperation" action="viewKRAKPIOperation" />
					
		<div style=" float:left; padding:10px 1%; width:98%;">
		 	<div class="clear"></div>
		 	<div class="listviewBorder">
		 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
					<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
					
					
					<!-- <input id="edit" class="button"  value="Edit" type="button" onclick="editRow()">
					<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="deleteRow()"> -->		
					 
					</td></tr></tbody></table>
					</td>
					<td class="alignright printTitle"><!-- <a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a> -->  
					 <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="addKRA();"
							>Add</sj:a>   </td>
					</tr></tbody></table></div>
				</div>
		 	</div>
		 	<div style="overflow: scroll; height: 450px;">
		 	<sjg:grid 
			  id="gridedittable"
	          href="%{viewKRAKPI}"
	          gridModel="masterViewList"
	           groupField="['mappeddeptid']"
	    	  groupColumnShow="[false]"
	    	  groupCollapse="true"
	    	  groupText="['<b>{0}: {1}</b>']"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorEdit="true"
	          navigatorDelete="true"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorSearch="true"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="false"  
	          loadingText="Requesting Data..."  
	          rowNum="10"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{viewKRAKPIOperation}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          >
			<s:iterator value="masterViewProp" id="masterViewProp" >  
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
			searchoptions="{sopt:['eq','cn']}"
			/>
			</s:iterator>  
		</sjg:grid>
		</div>
		</div>
	</div>
</body>
</html>