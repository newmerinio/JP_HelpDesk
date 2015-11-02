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
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
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
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotViewPage.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}

function searchResult(searchField, searchString, searchOper)
{
	
	//alert( searchOper);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotViewPage.action",
	    data : "&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(data) {
	   		$("#data_part").html(data);
		},
	    error: function() {
	        alert("error");
	    }
	 });
	   
	    
}
   
function fillAlphabeticalLinks()
{
	
	var val = "";
	val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'\',\'\',\'\')">All</a>&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'assetname\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
}
fillAlphabeticalLinks();



</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Allotment</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					    		<%if(userRights.contains("alnt_MODIFY")) {%> 
					    			<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
					    		<%} %>
								<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		                     	<%if(userRights.contains("alnt_ADD")) {%> 
		                     		<sj:a id="groupAddButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('groupAllotment');">Add</sj:a> 
								<%} %>
						     <div id="assetDetailsDiv" style="display:none;"></div><!--
						     
						       Search:<s:textfield
			                   name="search" 
			                   id="search" 
			                   onchange="searchResult('Search',this.value,'eq');getDocu('assetDetailsDiv');" 
			                   maxlength="50"  
			                   placeholder="Enter Data"  
			                   cssClass="button"
			                   cssStyle="margin-top: -28px;margin-right: -179px;margin-left: 549px;width: 60px;"
			                   />
						      
					      --></td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <!--<td class="alignright printTitle">
				  		<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download"  buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','totalAllotDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
						<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('allotment');">Add</sj:a>
						<sj:a id="groupAddButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('groupAllotment');">Add</sj:a>
						<sj:a id="freeReallotButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('Free Reallot');">Free Re-allot</sj:a>
	      		  </td>
			--></tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewAllotment" action="viewAllotmentData" escapeAmp="false">
<s:param name="searchField" value="%{searchField}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="searchOper" value="%{searchOper}"></s:param>
</s:url>
<s:url id="modifyAllotment" action="modifyAllotmentData"/>
<sjg:grid 
          id="gridedittable"
          href="%{viewAllotment}"
          gridModel="masterViewList"
          groupField="['outletid']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0} Total Asset: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="true"
          navigatorSearch="true"
          editinline="false"
          rowList="500,1000,1500"
          rowNum="500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifyAllotment}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp"  status="test"> 
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="235"
			align="%{align}"
			editable="%{editable}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator> 
</sjg:grid> 
</div>
</div>
</div>
</div>
<sj:dialog 
		              			id="downloadColumnAllModDialog" 
		              			modal="true" 
		              			effect="slide" 
		              			autoOpen="false" 
		              			width="300" 
		              			height="500" 
		              			title="Asset " 
		              			loadingText="Please Wait" 
		              			overlayColor="#fff" 
		              			overlayOpacity="1.0" 
		              			position="['center','center']" >
								<div id="downloadAllModColumnDiv"></div>
								</sj:dialog>
		
		</body>
</body>

</html>