<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<style type="text/css">

</style>
<SCRIPT type="text/javascript">
	function addIndustry()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function MapIndustry()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryMap.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	/* function weightageAssinge()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeAssignWeightage.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	} */

	function excelUpload()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingUpload.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function commSearch()
	{
	var status=$("#status").val();
	var searchParameter=$("#searchParameter").val();
//		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryView.action?status="+status+"&searchParameter="+searchParameter,
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
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		
		<div class="head">View</div><div class="head">Industry</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Industries: <s:property value="%{countIndustries}"/>,</div><div class="head">Sub-Industries: <s:property value="%{countSubIndustries}"/></div> 
	</div>
	
	<!-- Level 1 to 2 Data VIEW URL CALLING Part Starts HERE -->
	<s:url id="viewLevel1" action="viewLevel1" escapeAmp="false" namespace="/view/Over2Cloud/wfpm/industry">
	<s:param name="status" value="%{#parameters.status}"></s:param>	
	</s:url>
	
	
	<!-- Level 1 to 2 Data Modification URL CALLING Part Starts HERE -->
	<s:url id="viewModifyLevel1" action="viewModifyLevel1" />
	<!-- Level 1 to 2 Data Modification URL CALLING Part ENDS HERE -->
	
	<div class="rightHeaderButton">
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<s:if test="%{status=='Active'}">
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					</s:if>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
					
					<s:select
									id="status" 
									name="status" 
									list="{'Active','Inactive'}"
									cssStyle="height: 28px; width: 35%;"
									theme="simple"
									cssClass="button"
									onchange="commSearch();"
							  />
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
	 <s:if test="true || #session.userRights.contains('indu_ADD')">
	     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addIndustry();">Add</sj:a>
     </s:if>         
				</td>   
				</tr></tbody></table></div></div> </div>
				<div style="overflow: scroll; height: 450px;">
	<s:if test="true || #session.userRights.contains('indu_ADD') || #session.userRights.contains('indu_MODIFY') 
			|| #session.userRights.contains('indu_DELETE')">		
			<sjg:grid 
			  id="gridedittable"
	          href="%{viewLevel1}"
	          gridModel="level1DataObject"
	          caption="Industry"
	          groupField="['industry']"
	    	  groupColumnShow="[false]"
	    	  groupCollapse="true"
	    	  groupText="['<b>{0}: {1}</b>']"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          rowList="100,500,1000"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rowNum="15"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{viewModifyLevel1}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          loadonce="false"
	          >
			<s:iterator value="level2ColmnNames" id="level2ColmnNames" >  
			<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{250}"
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
					width="%{250}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
			</s:iterator>  
			</sjg:grid>
	</s:if>
			</div>
	</div>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event,data) {
	row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
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
//	alert(row);
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
	function refreshRow()
	{
		$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
</script>
</html>