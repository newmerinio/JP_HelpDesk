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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:500, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
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
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn'],caption:'Padam'} );
}
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetViewPage.action?modifyFlag=0&deleteFlag=0",
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

	function printBarCode(cellvalue, options, rowObject) 
	{
		return "<a href='#' title='View' onClick='getPrintData("+rowObject.id+")'>Print</a>";
	}
	
	function getPrintData(rowId) 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/generateBarcode.action?assetId="+rowId,
		    success : function(data) {
		    	$("#UploadDialog").dialog({title:'Print Barcode',height:200,width:300,dialogClass:'transparent'});
				$("#UploadDialog").dialog('open');
	       		$("#dataDiv").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}


	function searchResult(searchField, searchString, searchOper)
	{
		
		//alert( searchOper);
		$("#mailandsmsdiv").html("");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetViewPage.action?modifyFlag=0&deleteFlag=0",
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
			val += '&nbsp;&nbsp;&nbsp<a href="#" onclick="searchResult(\'assetname\',\''
					+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		$("#alphabeticalLinks").html(val);
	}

	function viewAssetHistory(cellvalue, options, rowObject) 
	{
		return "<a href='#' title='View' onClick='getAssetHistoryData("+rowObject.id+")'><u>"+rowObject.assetname+"</u></a>";
	}

	function getAssetHistoryData(id)
	{
		$("#HistoryDialog").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getAssetById.action",
			data: "dataId="+id+"&idFor=AssetDetail",
		    success : function(data) {
			$("#HistoryDialog").dialog({title:'View Asset History',height:550,width:950,dialogClass:'transparent'});
	       		$("#HistoryDiv").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}

	$.subscribe('removeSessionValue', function(event,data)
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/removeSessionValue.action",
		    data: "removeSession4=Asset",
		    success : function(data) {
		    	
		    },
		   error: function() {
		        alert("error");
		    }
		 });
	});


	function getNextOrPreviousData(dataFor)
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getAssetById.action",
			data: "idFor=AssetDetail&nextOrPrevious="+dataFor,
		    success : function(data) {
	       		$("#overlapAll").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}


	function getTicketInfo(complaintId)
	{
		$("#ticketDetails").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getCompliantDetailById.action",
			data: "complaintId="+complaintId,
		    success : function(data) {
			$("#ticketDetails").dialog({height:350,width:950,dialogClass:'transparent'});
	       		$("#ticketDiv").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}

	
	fillAlphabeticalLinks();
	
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Registration & Procurement </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
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
					     	<%if(userRights.contains("reon_MODIFY")) {%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
							<%} %>
							<%if(userRights.contains("reon_DELETE")) {%>
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<%} %>	
							    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
	      	        <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download"  buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadAllData','assetDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
				    <%if(userRights.contains("reon_ADD") || true) {%>
					    <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('AssetRegister');">Add</sj:a>
					    <sj:a id="uploadButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="uploadData('assetDetail');">Upload</sj:a>
				    <%} %>
 				</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	<div style="overflow: scroll; height: 400px;">
	<s:url id="modifyAsset" action="modifyAsset" />
	<s:url id="viewAsset" action="viewAssetGridData" escapeAmp="false">
	<s:param name="searchField" value="%{searchField}"></s:param>
    <s:param name="searchString" value="%{searchString}"></s:param>
    <s:param name="searchOper" value="%{searchOper}"></s:param></s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewAsset}"
	          gridModel="masterViewList"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="%{deleteFlag}"
	          navigatorEdit="%{modifyFlag}"
	          navigatorSearch="true"
	          editinline="false"
	          rowList="14,100,200"
	          rowNum="14"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          rownumbers="-1"
	          pagerInput="false"   
	          multiselect="false"
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyAsset}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
          	>
          	<sjg:gridColumn 
					name="barCode"
					index="barCode"
					title="Bar Code"
					width="100"
					editable="false"
					search="false"
					align="center"
					formatter="printBarCode"
					hidden="true"
				/>
			<s:iterator value="masterViewProp" id="masterViewProp" >
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
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
                  id="UploadDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Upload Data Via Excel"
                  width="900"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="dataDiv"></div>
        	</sj:dialog>
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
				
				
				
	<sj:dialog
                  id="HistoryDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Upload Data Via Excel"
                  width="900"
                  height="350"
                  showEffect="slide"
                  onCloseTopics="removeSessionValue"
                  position="['top','top']"
                  hideEffect="explode">
                  <div id="HistoryDiv"></div>
        	</sj:dialog>
        	
     <sj:dialog
                  id="ticketDetails"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="View Complaint Detail"
                  width="400"
                  height="250"
                  showEffect="slide"
                  position="['top','top']"
                  hideEffect="explode">
                  <div id="ticketDiv"></div>
        	</sj:dialog>
		
		</body>
</html>