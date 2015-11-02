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
	
	alert("fdate" +fdate+ "tdate" +tdate);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeLeavePullReport.action?fdate="+fdate+"&tdate="+tdate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function seachData(fieldser,fieldval)
{
	var searchMob=$("#searchMob").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeLeavePullReport.action",
	   data:"fieldser="+fieldser+"&fieldval="+fieldval,
	    success : function(data) 
	    {
		 $("#datapart").html(data);
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
	    url : "view/Over2Cloud/leaveManagement/beforeLeavePullReport.action",
	    success : function(subdeptdata) {
       $("#datapart").html(subdeptdata);
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

<div class="list-icon">
	 <div class="head">
	 Pull Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>

<!--<div class="rightHeaderButton">
<sj:a  button="true" onclick="addEmployeeType();" buttonIcon="ui-icon-plus">Add</sj:a>
</div>


 --><div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="fdate" name="fdate" size="20"  onchange="onChangeDate()"  value="%{fdate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
	<sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="tdate" name="tdate" size="20" maxDate="0" onchange="onChangeDate()" value="today"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/>
	Mobile No.:<s:textfield name="searchMob" id="searchMob" onkeyup="seachData('mobno',this.value);" cssStyle="margin-top: -27px"  placeholder="Enter Mobile No."  cssClass="button" theme="simple"/>
	 Keyword:<s:textfield name="searchKey" id="searchKey" onkeyup="seachData('IncomingSms',this.value);" cssStyle="margin-top: -27px; margin-left: 2px"  placeholder="Enter Keyword"  cssClass="button" theme="simple"/>
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	
	</td>   
	</tr></tbody></table></div></div>
    </div>
<s:url id="pullreportGridDataView" action="viewLeavePullReport" escapeAmp="false">
 <s:param name="fieldser" value="%{fieldser}"></s:param>
 <s:param name="fieldval" value="%{fieldval}"></s:param>
<s:url id="editemployeeTypeData" action="employeeTypeGridDataModify" />
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
</s:url>
<div style="overflow: scroll; height: 430px;">
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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:170, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}

function reloadRow()
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeEmployeeTypeView.action",
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