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
function viewSupportDetails(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getSupportDetails("+rowObject.astid+")'>View</a>";
}

function getSupportDetails(rowId) 
{
	$('#assetReminderDialog').dialog('option', 'title', 'Support Details');
	$("#assetReminderDialog").dialog('open');
	$("#assetReminderDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeGetReminderDetails.action?moduleName=Support&assetId="+rowId,
	    success : function(data) {
       		$("#assetReminderDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function viewPMDetails(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getPMDetails("+rowObject.astid+")'>View</a>";
}

function getPMDetails(rowId) 
{
	$('#assetReminderDialog').dialog('option', 'title', 'Preventive Maintenance Details');
	$("#assetReminderDialog").dialog('open');
	$("#assetReminderDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeGetReminderDetails.action?moduleName=Preventive&assetId="+rowId,
	    success : function(data) {
       		$("#assetReminderDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function totalAllotment(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getTotalAllotment("+rowObject.astid+")'>"+rowObject.totalAllotment+"</a>";
}

function getTotalAllotment(rowId) 
{
	$('#assetReminderDialog').dialog('option', 'title', 'Total Allotment Details');
	$("#assetReminderDialog").dialog('open');
	$("#assetReminderDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeGetTotalAllot.action?assetId="+rowId,
	    success : function(data) {
       		$("#assetReminderDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function totalBreakdown(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getTotalBreakdown("+rowObject.astid+")'>"+rowObject.totalBreakdown+"</a>";
}

function getTotalBreakdown(rowId) 
{
	$('#assetReminderDialog').dialog('option', 'title', 'Total Breakdown Details');
	$("#assetReminderDialog").dialog('open');
	$("#assetReminderDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeGetBreakdownDetails.action?assetId="+rowId,
	    success : function(data) {
       		$("#assetReminderDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function searchResult(searchField, searchString, searchOper)
{
	
	//alert( searchOper);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createModuleViewPage.action",
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
		val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'ast.assetname\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}
fillAlphabeticalLinks();

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Activity Dashboard </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
	<s:hidden id="assetColCount" value="%{assetColCount}" />
	<s:hidden id="PMColCount" value="%{PMColCount}" />
	<s:hidden id="suprtColCount" value="%{suprtColCount}" />
	<s:hidden id="allotColCount" value="%{allotColCount}" />
	
<div class="rightHeaderButton"></div>
<div class="clear" ></div>
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
					     	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="getViewData('activitydashboard');;"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   <!--<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download"  buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
				   --><sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('AssetRegister');">Register</sj:a>
				   <sj:a id="addButton1"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('Support');">Support</sj:a>
				   <sj:a id="addButton2"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('Preventive');">Preventive</sj:a>
				   <sj:a id="addButton3"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('allotment');">Allotment</sj:a>
				   <sj:a id="addButton4"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="getPage('call');">Lodge Complaint</sj:a>
				   <sj:a id="addButton5"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="getViewData('MachineDetails');">Monitor Network</sj:a>
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	<div style="overflow: scroll; height: 400px;">
	<s:url id="viewAllData" action="viewAllMoudleGridData" escapeAmp="false">
	<s:param name="searchField" value="%{searchField}"></s:param>
    <s:param name="searchString" value="%{searchString}"></s:param>
	<s:param name="searchOper" value="%{searchOper}"></s:param> 
	</s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewAllData}"
	          gridModel="masterViewList"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="false"
	          navigatorDelete="false"
	          navigatorEdit="false"
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
				/>
			</s:iterator>    
		</sjg:grid>
		</div>
		</div>
		</div>
		</div>
		<sj:dialog
          id="assetReminderDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
    	  onCloseTopics="closeDialog"
          autoOpen="false"
          title="Asset Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
          <div id="assetReminderDiv"></div>
</sj:dialog>
		</body>
		<script type="text/javascript">
	$(document).ready( function() {
		var astColCount=$('#assetColCount').val();
		var procColCount=$('#procColCount').val();
		//var suprtColCount=$('#suprtColCount').val();
		var allotColCount=$('#allotColCount').val();
		
		jQuery("#gridedittable").jqGrid('setGroupHeaders', {
			useColSpanStyle :false,
			groupHeaders : [ {
				startColumnName :'${ast1stCol}',
				numberOfColumns :astColCount,
				titleText :'<b><font size="3">Asset</font></b>'
			}, 
			{
				startColumnName :'supportDate',
				numberOfColumns :2,
				titleText :'<b><font size="3">Support</font></b>'
			}, 			
			{
				startColumnName :'PMDate',
				numberOfColumns :2,
				titleText :'<b><font size="3">Preventive Maintenance</font></b>'
			}, 	
			{
				startColumnName :'${allot1stCol}',
				numberOfColumns :allotColCount,
				titleText :'<b><font size="3">Allotment</font></b>'
			}, 
			]
		});
	});

</script>
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
		
</html>