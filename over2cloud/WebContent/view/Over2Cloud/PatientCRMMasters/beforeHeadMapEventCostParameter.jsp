<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function addNew(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/beforeAddEventConfigureMaster.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addCostParameters(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",  
	    url : "view/Over2Cloud/patientCRMMaster/viewEventConfigureHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

/*
function getGridView()
{
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/patientCRMMaster/viewSourceMasterDetails.action",
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}
getGridView();
*/ 


</script>
</head>


<body>
<div class="list-icon">
	 <div class="head">Map Event with ROI Parameters</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>

 <div class="clear"></div>
  
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
   
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	  <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
   	  <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
 	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNew();">Add</sj:a>
    <sj:a id="addButt" title="View Map Event with Cost Parameters" button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addCostParameters();">Map Cost Parameters</sj:a>
    
    </td>   
	</tr></tbody></table>
	</div>
	</div>
   <%-- "#{'All':'All','Active':'Active','DActive':'InActive'}" --%>
  <s:url id="viewModifyEventRoiCost" action="viewModifyEventRoiCost" />
<s:url id="viewEventRoiCostData" action="viewEventRoiCostData" ></s:url>
<div style="overflow: scroll; height: 430px;">
				
<sjg:grid 
								          id="gridEventRoiCost"
								          href="%{viewEventRoiCostData}"
								          gridModel="viewList"
								          navigator="false"
								          navigatorAdd="false"
								          navigatorView="true"
								          navigatorDelete="true"
								          navigatorEdit="true"
								          navigatorSearch="true"
								          editinline="false"
								          rowList="100,200,500"
								          rowNum="100"
								          viewrecords="true"       
								          pager="true"
								          pagerButtons="true"
								          rownumbers="true"
								          pagerInput="false"   
								          multiselect="false"
								          navigatorSearchOptions="{sopt:['eq','cn']}"  
								          loadingText="Requesting Data..."  
								          navigatorEditOptions="{height:230,width:400}"
								          editurl="%{viewModifyEventRoiCost}"
								          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
								          shrinkToFit="false"
								          autowidth="true"
								          loadonce="true"
								          onSelectRowTopics="rowselect"
								          onEditInlineSuccessTopics="oneditsuccess"
								          caption="Configure ROI"
								          >
											<s:iterator value="masterViewProp2" id="masterViewActReason" >  
												<s:if test="%{colomnName=='status'}">
					<sjg:gridColumn 
						name="%{colomnName}"
						index="%{colomnName}"
						title="%{headingName}"
						width="%{width}"
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
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
			</s:iterator>  
				</sjg:grid>
			</div>
		</div>

</body>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

var selRowIds = jQuery('#gridEventRoiCost').jqGrid('rowselect', 'selarrrow');

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		jQuery("#gridEventRoiCost").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}

function deleteRow()
{
	  var status = jQuery("#gridEventRoiCost").jqGrid('getCell',row,'status');
//	  alert(status);
	if(row==0)
	{
		alert("Please select atleast one row to delete.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already delete.");
	}
	else
	{
		$("#gridEventRoiCost").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Delete',msg: "Delete selected record(s)?",bSubmit: "Delete" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}	
}

function searchRow()
{
	 $("#gridEventRoiCost").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadPage()
{
	var grid = $("#gridEventRoiCost");
	grid.trigger("reloadGrid",[{current:true}]);
}


</script>

</html>