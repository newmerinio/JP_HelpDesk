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
function supportAction()
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	var module=$("#moduleName").val();
	if(module=='Support')
	{
		$("#takeActionGrid").dialog({title:'Support Action Dialog'});
	}
	else
	{
		$("#takeActionGrid").dialog({title:'Preventive Action Dialog'});
	}
	
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/beforeTakeAction?assetReminderID="+valuepassed);
	$("#takeActionGrid").dialog('open');
	
	   return false;
}

function downloadDoc(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='docDownloadAction("+rowObject.id+")'><img src='"+ context_path +"/images/docDownlaod.jpg' alt='Download'></a>";
}

function docDownloadAction(remiderReportId) 
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/docDownloadAction.action?remiderReportId="+remiderReportId,
	    success : function(data) 
	    {
			$("#downloadLoc").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}

function testAbc(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View Esc Matrix' onClick='viewEscMatrixAction("+rowObject.id+")'><u>View Esc Matix</u></a>";
}

function viewEscMatrixAction(rowIds) 
{
	$("#downloadColumnAllModDialog").dialog({title: 'Escalation Matrix'});
	$("#downloadColumnAllModDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/viewEscMatrixAction.action?id="+rowIds,
	    success : function(data) 
	    {
			$("#downloadAllModColumnDiv").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}


function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function getActionData(freq,dueD,moduleName)
{
	var frequency=$("#"+freq).val();
	var dueDate=$("#"+dueD).val();
	var module=$("#"+moduleName).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName="+module+"&frequency="+frequency+"&dueDate="+dueDate,
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



function searchResult(searchField, searchString, searchOper)
{
	//alert(searchField+" >>> "+searchString+" >>>>> "+searchOper);
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName=Support",
	    data : "searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(data) 
	    {
	  //  alert(data);
       		$("#data_part").html(data);
		},
	    error: function() 
	    {
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
		val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'assetname\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
}
fillAlphabeticalLinks();


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<s:if test="moduleName=='Support'">
<div class="list-icon">
	 <div class="head">
	 Support</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Activity</div> 
</div>
</s:if>
<s:if test="moduleName=='Preventive'">
<div class="list-icon">
	 <div class="head">
	 Preventive Maintenance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Activity </div> 
</div>
</s:if>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<center>
<div id="downloadLoc"></div>
</center>
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
								<sj:a id="action" cssClass="button" button="true" title="Action" onclick="supportAction();">Action</sj:a>
								  <s:select  
	                              	id					=		"frequency"
	                              	name				=		"frequency"
	                              	list				=		"#{'OT':'One-Time','D':'Daily','W':'Weekly','M':'Monthly','BM':'Bi-Monthly','Q':'Quaterly','HY':'Half Yearly','Y':'Yearly'}"
	                              	headerKey			=		"-1"
	                              	headerValue			=		"Frequency" 
									cssClass			=		"button"
	                             	theme				=		"simple"
	                             	cssStyle			=		"height: 28PX;width: 111px;"
	                              >
			                     </s:select>
	                    	   <sj:datepicker id="dueDate" name="dueDate" cssClass="button" cssStyle="width: 111px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 24px;margin-top: -16px;text-align: center;" readonly="true"  size="20" value=""  changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
						       <sj:a  button="true" cssClass="button" onclick="getActionData('frequency','dueDate','moduleName');">OK</sj:a>
						      
	                          <div id="assetDetailsDiv" style="display:none;"></div></td>
					      </tr>
					     </tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  <% if(userRights.contains("surt_ADD") || true || userRights.contains("prve_ADD")) {%>
				  	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntry('moduleName');">Add</sj:a>
				  <%} %>
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>
<s:url id="viewSupport" action="viewSupportData" escapeAmp="false">
<s:param name="moduleName"  value="%{moduleName}" />
<s:param name="frequency"  value="%{frequency}" />
<s:param name="dueDate"  value="%{dueDate}" />
<s:param name="searchField" value="%{searchField}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="searchOper" value="%{searchOper}"></s:param>
</s:url>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewSupport}"
          gridModel="masterViewList"
          groupField="['actionStatus']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0}-{1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
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
			width="165"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator> 
</sjg:grid> 
</div>

<div id="datapart">

		<!-- support data will come dynamically *******************************************************************-->
</div>

</div>
</div>
</div>
<sj:dialog
          id="takeActionGrid"
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
								
			
		
</body>
</html>