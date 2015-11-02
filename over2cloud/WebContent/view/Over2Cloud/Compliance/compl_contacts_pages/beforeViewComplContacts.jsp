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
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/common/topclient.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	


$.subscribe('editRow', function(event,data)
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		var id   = jQuery("#gridedittableContact").jqGrid('getGridParam', 'selrow');
		var levelName = jQuery("#gridedittableContact").jqGrid('getCell',id,'level');
		var test=levelName.split("-");
		var currentLevel=parseInt(test[1]);
		var targetid='level';
		$('#'+targetid+' option').remove();
		for (var i=currentLevel; i<=5; i++)
		{
			$('#'+targetid).append($("<option></option>").attr("value",i).text("Level "+i));
		}
		
		jQuery("#gridedittableContact").jqGrid('editGridRow', row ,{height:200, width:300,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:100,modal:true});
		row=0;
		var targetid='level';
	}
	
});

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		$("#gridedittableContact").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
	}
}
function searchRow()
{
	 $("#gridedittableContact").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittableContact");
	grid.trigger("reloadGrid",[{current:true}]);
}


function getSearchData()
{
	var moduleName=$("#moduleName").val();
	var subType=$("#subType").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactSearchView.action",
		data: "modifyFlag=0&deleteFlag=1&moduleName="+moduleName+"&subType="+subType,
	    success : function(data) 
	    {
			$("#"+"contactDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	
}

getSearchData();
</script>
</head>
<body>
<%-- <s:hidden id="dataFor" value="%{moduleName}"/> --%>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Edit Compliance Contact"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
          <div id="dataPart"></div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View For <s:property value="%{mainHeaderName}"/> Department</div> 
	<div id="test"  class="alignright printTitle">
         <sj:a id="mapping" cssClass="button" button="true" cssStyle="margin-top: 4px;" buttonIcon="ui-icon-plus" title="Map Contact" onClick="getContactMapping();">Map</sj:a>
   		 <sj:a id="sharing" cssClass="button" button="true" cssStyle="margin-top: 4px;" buttonIcon="ui-icon-plus" title="Share Contact" onClick="getContactSharing();">Share</sj:a>
    </div> 
	 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
						<%if(true || userRights.contains("coma_MODIFY")) {%>
						<sj:a id="editButton111" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onClickTopics="editRow"></sj:a>
					<%} %>
					<%if(userRights.contains("coma_DELETE")) {%>
						<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
						<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>	
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
				    
				    <s:select 
				       id="moduleName"
				       name="moduleName"
				       list="moduleList"
				       headerKey="-1"
				       headerValue="Select Module Name"
				       cssClass="button"
				       cssClass="button"
			           cssStyle="margin-top: -29px;margin-left:3px"		
					   theme="simple"
					   onchange="getSearchData();"
					   
					 />
						
				
					 <s:select 
                      id="subType" 
			          name="subType" 
			          list="subTypeMap"
			          headerKey="-1"
			          headerValue="Select Department"
			          cssClass="button"
			          cssStyle="margin-top: -29px;margin-left:3px;width:157px;"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 
         			 <!--<sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getSearchData();">OK</sj:a>
					      --></td></tr></tbody>
					  </table>
				  </td>
				 <td class="alignright printTitle">
			 		<%if(true || userRights.contains("coma_ADD")) {%> <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewContact();">Add</sj:a><%} %>
	       		</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="contactDiv">
</div>
</div>
</div>
</div>
</body>
</html>