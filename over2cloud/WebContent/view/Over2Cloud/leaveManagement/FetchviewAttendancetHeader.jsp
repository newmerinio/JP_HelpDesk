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
<script>

function onChangeDate()
{

	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	var modifyFlag=$("#modifyFlag").val();
    var actionStatus =$("#actionStatus").val();
    var actionStatus1 =$("#actionStatus1").val();
    

$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/viewreportAddForMyself.action?actionStatus1="+actionStatus1+"&actionStatus="+actionStatus+"&fdate="+fdate+"&tdate="+tdate+"&modifyFlag="+modifyFlag,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function loadGrid()
{
	
	$.ajax({
	    type : "post",
	  //  url : "view/Over2Cloud/leaveManagement/viewreportAddForMyself.action?modifyFlag=show",
	   	   url : "view/Over2Cloud/leaveManagement/viewreportAddForMyself.action?modifyFlag=show",

			  success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function forCounter()
{

	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/headercounter.action?f1date="+fdate+"&t1date="+tdate,

	    success : function(feeddraft) {
	        $("#abc").html(feeddraft);

	},
	   error: function() {
            alert("error");
        }
	 });
}



onChangeDate();
forCounter();


</script>


</head>
<sj:dialog
          id="holidayDialog11"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Holiday Calendar For 2014" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
   <div id="holidayDialogDataDiv11"></div>        
</sj:dialog>

<body>


  <div class="list-icon">
<div id="abc"></div>
	</div>
		
 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10">
	
	<s:if test="%{modifyFlag=='show'}">
      
          From <sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="fdate" name="fdate" size="20" maxDate="0" onchange="onChangeDate();forCounter(); " value="%{firstDay}"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/> 
	      To   <sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="tdate" name="tdate" size="20" maxDate="0" onchange="onChangeDate();forCounter(); " value="today" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/> 
		       <s:select id="actionStatus" name="actionStatus"  list="#{'All time':'All time','Ontime':'Ontime','Late Coming':'Late Coming','Early Going':'Early going'}"  
					cssClass="button" onchange="onChangeDate();" cssStyle="margin-top: -29px;margin-left: 2px;"/>
    <s:select id="actionStatus1" name="actionStatus1"  list="#{'Absent Type':'Absent Type','Intimated':'Intimated','Nonintimated':'Nonintimated'}"  
		 cssClass="button" onchange="onChangeDate();" cssStyle="margin-top: -29px;margin-left: 180px;" theme="simple"/>
	
	
  </s:if>
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<!--<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','dailyReport','downloadColumnDailyReport','downloadDailyReportColumnDiv','%{empname}','%{fdate}','%{tdate}')">Download</sj:a>
-->
<!--<sj:a  button="true" 
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

-->
<!--<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addAttendance();">Add</sj:a>

-->




<sj:a id="addButtonss"  button="true"   cssClass="button" onclick="fetchLeavePolicy();">Leave Policy</sj:a>
<sj:a id="addButton1"  button="true"   cssClass="button" onclick="fetchholidayCalendar();">Holiday Calendar</sj:a>

<sj:a id="addButton2"  button="true"   cssClass="button" onclick="fetchLeaveRequest();">Leave Request</sj:a>
	</td> 
	</tr></tbody></table></div></div>
    </div>
    
<s:hidden id="modifyFlag" value="%{modifyFlag}"/>
<div id="datapart"></div>

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



</html>