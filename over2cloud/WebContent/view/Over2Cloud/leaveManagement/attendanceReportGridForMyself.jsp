<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"><s:url  id="contextz" value="/template/theme" /><sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<body>
    <div style="overflow: scroll; height: 430px;">
<s:url id="attendanceGridDataViewMyself" action="attendanceGridDataViewMyself" escapeAmp="false">
<s:url id="viewModifyGrid1" action="modifyattendanceGridData"/>
<s:param name="empname" value="%{empname}" />
<s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:param name="modifyFlag" value="%{modifyFlag}" />
<s:param name="id" value="%{id}" />
<s:param name="actionStatus" value="%{actionStatus}" />
<s:param name="actionStatus1" value="%{actionStatus1}" />
</s:url>


<sjg:grid 
		  id="gridedittables"
          href="%{attendanceGridDataViewMyself}"
          gridModel="viewReportAttendance"
          navigator="true"
          loadonce="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
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
          multiselect="false"
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
	jQuery("#gridedittables").jqGrid('editGridRow', row ,{height:350, width:600,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittables").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittables").jqGrid('searchGrid', {sopt:['eq','cn']} );
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