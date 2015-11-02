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
<script type="text/javascript">
$.subscribe('getStatusForm', function(event,data)
{
	
          var empIds;
          empIds = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		  var connection=document.getElementById("conString").value;
		  var tdate=$('#tdate').val();
		  
		 alert("empIds" +empIds);
		 alert("connection" +connection);
		 alert("tdate" +tdate);
		 
		  $("#send_mail").load(connection+"/view/Over2Cloud/leaveManagement/sendMailReport.action?empIds="+empIds+"&tdate="+tdate.split(" ").join("%20"));
		  $("#send_mail").dialog('open');
	  
});

$.subscribe('closeDialog', function(event,data)
{
		  $("#send_mail").dialog('close');
	  
});
</script>

<script>



function getOnChangeData()
{
	
	var tdate=$("#tdate").val();
	var deptId=$("#deptId").val();
	var emp=$("#emp").val();
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/leaveManagement/totalReportAction.action?tdate="+tdate+"&deptId="+deptId+"&emp="+emp,
	    success : function(subdeptdata) {
       $("#datapart").html(subdeptdata);
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
	    url : "view/Over2Cloud/leaveManagement/totalReportAction.action",
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

loadGrid();

</script>


</head>
<body>

<sj:dialog 
	id="send_mail" 
	title="Send Mail" 
	modal="true" 
	showEffect="slide" 
	hideEffect="explode"
	autoOpen="false" 
	width="200" 
	height="200"
	openTopics="openEffectDialog"
	closeTopics="closeEffectDialog"
	position="['center','center']">
</sj:dialog>

<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="list-icon">
	 <div class="head">
	Total</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Report</div> 
</div>

 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10">
	
	
	<sj:datepicker name="tdate" id="tdate" readonly="true" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;" cssClass="button" placeholder="Select From Date"/>
	                   
	                          <s:select  
							    	id=	"deptId"
							    	name="deptId"
							    	list="deptName"
							    	headerKey="-1"
							    	headerValue="Select"
							    	cssClass="button"
									cssStyle="margin-top: -29px;margin-left:3px"
									onchange=" getEmployeeReport(this.value,'emp');"
							       theme="simple"
							       >
							      </s:select>
							      <s:select  
							    	id=	"emp"
							    	name="emp"
							    	list="#{'-1':'Select'}"
							    	cssClass="button"
									cssStyle="margin-top: -29px;margin-left:3px"
							       theme="simple"
							       onchange="getOnChangeData()"
							       >
							      </s:select>
	
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
				           onclick="getCurrentColumn('downloadExcel','totalReport','downloadColumnTotalReport','downloadTotalReportColumnDiv','%{emp}','tdate');" />
				   
<sj:submit value=" Send Mail " button="true" onClickTopics="getStatusForm" onCompleteTopics="closeDialog"/>
	</td> 
	</tr></tbody></table></div></div>
    </div>
    
<s:url id="reportTotalGridDataView" action="reportTotalGridDataView" escapeAmp="false">
<s:param name="tdate" value="%{tdate}" />
<s:param name="deptId" value="%{deptId}" />
<s:param name="subdeptId" value="%{subdeptId}" />
<s:param name="emp" value="%{emp}" />
</s:url>
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

<sj:dialog 
		id="downloadColumnTotalReport" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Total Report Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadTotalReportColumnDiv"></div>
</sj:dialog>
</html>