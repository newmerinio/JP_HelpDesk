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
timePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}

var datePick = function(elem) {
    $(elem).datepicker({dateFormat: 'dd-mm-yy'});
    $('#ui-datepicker-div').css("z-index", 2000);
}
</script>
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
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn'],caption:'Padam'} );
}
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/ViewMorningReport.jsp",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}

function addNewEntryReport()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/beforeCreateMorningReport.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
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
	 <div class="head">
	 Morning Report </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
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
					     	
							 <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
							 <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							 <sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
					    <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewEntryReport();">Add</sj:a>
 				</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="report_URL" action="viewMorningReportData">
</s:url>
<s:url id="modifyReport_URL" action="modifyMorningReportData" /> 
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="yyyy-mm-dd"/>
</div>

        <sjg:grid 
		  id="gridedittable"
          href="%{report_URL}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,150,200"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="50"
          autowidth="true"
          editurl="%{modifyReport_URL}" 
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
               navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadPage();
	  			  }}"
		          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reloadPage();
	    }}"
          navigatorViewOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
		           <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						width="100"
		      						align="center"
		      						editable="true"
		      						hidden="true"
		      						key="true"
		 							/>
		 							
		 							
		 							
		 		   <sjg:gridColumn 
		      						name="moduleName"
		      						index="moduleName"
		      						title="For Module"
		      						width="400"
		      						editable="false"
		      						align="center"
		 							/>
		 							
		 							
		 							
		 			<sjg:gridColumn 
		      						name="date"
		      						index="date"
		      						title="Start From"
		      						width="300"
		      						align="center"
		      						editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'd-m-Y', srcformat : 'd-m-Y'}"
			                        editoptions="{dataInit:datePick}"
		 							/>
		 							
		 							
		 			<sjg:gridColumn 
		      						name="time"
		      						index="time"
		      						title="Schedule At"
		      						width="300"
		      						align="center"
		      						editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'H:i', srcformat : 'H:i'}"
		                    		editoptions="{dataInit:timePick}"
		 							/>		
		 																		
	</sjg:grid>
	</div>
	</div>
	</div>
	</div>
	</body>
</html>