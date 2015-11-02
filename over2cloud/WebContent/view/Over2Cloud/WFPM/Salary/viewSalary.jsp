<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
	//alert(">>>>>>>>>>>>>>calender");
    $(elem).datepicker({dateFormat:'mm-yy'});
    $('#date_picker_dob').css("z-index", 2000);
    
};
</SCRIPT>
<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
	});	
	function editRow()
	{
		alert(">>>>>>>>>>>>>>>>");
		$("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	function deleteRow(){
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:50,modal:true});
	}
	function searchRow()
	{
		$("#gridedittable").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	/* function refreshRow()
	{
		$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	} */
	
	function refreshRow(rowid, result) {
		  $("#gridedittable").trigger("reloadGrid"); 
		}
</script>
</head>
<body>
<div id="date_picker_dob" style="display:none">
  <sj:datepicker name="dob" id="doba" value="dob" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
</div> 

<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Salary</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
	<div class="rightHeaderButton">
	</div>

<div style=" float:left; padding:10px 1%; width:98%;">
<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<%-- <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a> --%>
					<%-- <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a> --%>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
				</td>   
				</tr></tbody></table></div></div> </div>
				
<s:url id="empInfo" action="empInformation" />
<s:url id="editEmpInfo" action="editEmpInformation" />
<s:url id="calculate" action="calculateSalary" >
</s:url>
<div style="overflow: scroll; height:480px;">
	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{calculate}" 
			pager="true" 
			navigator="true"
			navigatorSearch="true"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="false"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			gridModel="salaryGridModelList"
			rowList="15,20,30,40,50" 
			rowNum="15" 
			multiselect="true"
			draggable="true" 
			loadingText="Please wait..." 
			shrinkToFit="false"
			width="850"
			rownumbers="true"
			loadonce="true"
			autowidth="true"
			>
	
		<sjg:gridColumn name="emailId" editable="true" index="emailId" title="ID" hidden="true" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="empName" editable="true" index="empName" title="Emp Name" align="center" sortable="true" search="false" width="200" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="mobileNo" editable="true" index="mobileNo" title="Mobile No" align="center" sortable="true" search="false" width="200" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="emailId" editable="true" index="emailId" title="Email Id" align="center" sortable="true" search="false" width="200" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="monthDays" editable="true" index="monthDays" title="Month Days" align="center" sortable="true" search="false" width="100"/>
		<sjg:gridColumn name="holidays" editable="true" index="holidays" title="Holidays" align="center" sortable="true" search="false" frozen="true" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="workingDays" editable="true" index="workingDays" title="Working Days" align="center" sortable="true" search="false" width="100" frozen="true" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="attandance" editable="true" index="attandance" title="Attandance" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="onLeave" editable="true" index="onLeave" title="On Leave" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="allowLeave" editable="true" index="allowLeave" title="Allow Leave" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="dudctionDays" editable="true" index="dudctionDays" title="Dudction Days" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="deduction" editable="true" index="deduction" title="Deduction" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="basicSalary" editable="true" index="basicSalary" title="Basic Salary" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="payDay" editable="true" index="payDay" title="Pay Day" align="center" sortable="true" search="false" width="100"/>
		<sjg:gridColumn name="salaryPayble" editable="true" index="salaryPayble" title="Salary Payble" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="extraDaysSalary" editable="true" index="extraDaysSalary" title="Extra Days Salary" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="extraSalary" editable="true" index="extraSalary" title="Extra Salary" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="incentive" editable="true" index="incentive" title="incentive" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn name="netSalary" editable="true" index="netSalary" title="Net Salary" align="center" sortable="true" search="false" width="100" searchoptions="{sopt:['eq','cn']}"/>
		<sjg:gridColumn 
		name="month" 
		editable="false" 
		index="month" 
		title="Month" 
		align="center" 
		sortable="true" 
        searchoptions="{sopt:['eq','cn'], dataInit:datePicker}" 
        width="100" 
        />
	</sjg:grid>
	</div>
</div>
</body>
</html>