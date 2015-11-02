<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>

<script type="text/javascript">



var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/beforeViewdailyGrid.action",
		    success : function(data) {
	       		$("#data_part").html(data);
	       		
			},
		    error: function() {
	            alert("error");
	        }
		 });
	
}


function addNewGroup()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : "/view/Over2Cloud/WFPM/Communication/beforeGroupAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}



function addDailyConfigure()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/dailyUtilityAdd.action",
	    success : function(data) {
       		$("#data_part").html(data);
       		
		},
	    error: function() {
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
	<div class="head">Daily</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>

<center>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>

</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!--<a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a>
<a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a>
--> 

<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addDailyConfigure();">Add</sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="viewdailyConfigGrid" action="viewDailyConfigureDataGrid" ></s:url>
<s:url id="viewModifyGrid" action="modifyDailyUtility" >
<s:param name="id" value="%{id}" />
</s:url>
<center>
<div style="overflow: scroll; height: 415px;">
<sjg:grid 
		  id="gridedittable"
          href="%{viewdailyConfigGrid}"
          gridModel="viewDailyConfigureData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
          <s:iterator value="viewDailyConfigureGrid" id="viewDailyConfigureGrid" >  
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
		</s:iterator> 
	          
          
		
</sjg:grid>
</div>
</center>
</div></center>

</div>

</body>
</html>
</html>