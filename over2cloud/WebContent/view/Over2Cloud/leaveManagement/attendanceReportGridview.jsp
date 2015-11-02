<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<body>
<div class="list-icon">
	 <div class="head">
	 Attendance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>


 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	
	  <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
	 
	<!--<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>	
	--><sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<!--<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','dailyReport','downloadColumnDailyReport','downloadDailyReportColumnDiv','%{empname}','%{fdate}','%{tdate}')">Download</sj:a>
-->
<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				         onclick="getCurrentColumnss('downloadExcel','viewAttendanceMark','downloadColumnViewAttandance','downloadDailyReportColumnDiv111','%{modifyFlag}')"/>
				    
				    <sj:a  button="true" 
				           cssClass="button" 
			               cssStyle="height:25px; width:32px"
			               title="Upload"
				           buttonIcon="ui-icon-arrowstop-1-n" 
				           onclick="importAttendance();" />

<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addAttendance();">Add</sj:a>

	</td>   
	</tr></tbody></table></div></div>
    </div>
    
    
	 
    <div style="overflow: scroll; height: 430px;">
<s:url id="attendanceGridDataViewsss" action="attendanceGridDataView1111" escapeAmp="false">
<s:url id="viewModifyGrid1" action="modifyattendanceGridData"/>
<s:param name="empname" value="%{empname}" />
<s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:param name="modifyFlag" value="%{modifyFlag}" />
<s:param name="id" value="%{id}" />
</s:url>


<sjg:grid 
		  id="gridedittable111111111"
          href="%{attendanceGridDataViewsss}"
          gridModel="viewReportAttendance"
          navigator="true"
          loadonce="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rowNum="15"
          rownumbers="-1"
          viewrecords="true" 
          editurl="%{viewModifyGrid1}"      
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:600}"
          multiselect="true"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="reportGridColoumnNames" id="reportGridColoumnNames" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		frozen="%{frozenValue}"
		/>
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
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable111111111").jqGrid('editGridRow', row ,{height:350, width:600,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable111111111").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable111111111").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/viewreportAddAction.action?modifyFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}


</script>

<sj:dialog 
		id="downloadColumnViewAttandance" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Holiday Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadDailyReportColumnDiv111"></div>
</sj:dialog>
</html>