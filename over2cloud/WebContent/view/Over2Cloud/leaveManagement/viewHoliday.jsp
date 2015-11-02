<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'dd-mm-yy'});
    $('#date_picker_div').css("z-index", 2000);
}
</SCRIPT>
</head>
<body>
<div id="time_picker_div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" readonly="true" />
</div>
<div class="list-icon">
	 <div class="head">
	 Holiday List</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<!--<%if(userRights.contains("holi_ADD")){ %>
<div class="rightHeaderButton">
<sj:a  button="true" onclick="addHoliday();" buttonIcon="ui-icon-plus">Add Holiday List</sj:a>
</div>
<%} %>
--><%if(userRights.contains("holi_VIEW") || true){ %>
     <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
	<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRows()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<!--<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importHoliday();">Import</sj:a>
	<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-s" onclick="getCurrentColumn('downloadAllData','holidayList','downloadColumnAllModDialog','downloadAllModColumnDiv')">Download</sj:a>
	
	-->
	<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="getCurrentColumn('downloadAllData','holidayList','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
				    
				    <sj:a  button="true" 
				           cssClass="button" 
			               cssStyle="height:25px; width:32px"
			               title="Upload"
				           buttonIcon="ui-icon-arrowstop-1-n" 
				           onclick="importHoliday();" />
	
	
	
	<%if(userRights.contains("holi_ADD") || true){ %>
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addHoliday();">Add</sj:a>

<%} %>
	
	</td>   
	</tr></tbody></table></div></div>
    </div>
<s:url id="holidayGridDataView" action="holidayGridDataView" escapeAmp="false">
<s:url id="editHolidayData" action="holidayGridDataModify" />
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
</s:url>
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{holidayGridDataView}"
          gridModel="viewHoliday"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..." 
          editurl="%{editHolidayData}" 
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          onSelectRowTopics="rowselect"
          multiselect="true"
          >
          
		<s:iterator value="holidayGridColomns" id="holidayGridColomns" status="test">  
			<s:if test="colomnName=='start_date' || colomnName=='end_date'">  
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
			editoptions="{dataInit:datePicker}"
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
		/></s:else>
		</s:iterator>  
</sjg:grid>
</div></div>
<sj:dialog 
		              			id="downloadColumnAllModDialog" 
		              			modal="true" 
		              			effect="slide" 
		              			autoOpen="false" 
		              			width="300" 
		              			height="500" 
		              			title="Holiday Detail Column Name" 
		              			loadingText="Please Wait" 
		              			overlayColor="#fff" 
		              			overlayOpacity="1.0" 
		              			position="['center','center']" >
								<div id="downloadAllModColumnDiv"></div>
								</sj:dialog>
<%} %>
</body>
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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:170, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}


function searchRows()
{
	
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadRow()
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeHolidayListView.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


</script>
</html>