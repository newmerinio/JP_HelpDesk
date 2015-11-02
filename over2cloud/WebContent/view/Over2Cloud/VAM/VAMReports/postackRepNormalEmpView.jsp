<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     	<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<title>Post Acknowledge Report For Normal Employee</title>
	<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
	function editRow()
	{
		jQuery("#acknowledgepostviewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		$("#acknowledgepostviewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#acknowledgepostviewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow()
	{
		$("#acknowledgepostviewid").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
</script>
<script type="text/javascript">
function searchLocationWisePreReq()
{	
	var location = $("#location :selected").text();
	//var location = $("#location:options  selected").val();
	alert(location);
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforepostAckreportView.action?location="+location,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchDateWiseVisitorData(){
	var from_date = $("#from_date").val();
	var to_date = $("#to_date").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforepostAckreportView.action?from_date="+from_date+"&to_date="+to_date,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function loadEmp(val,divId){
		var purposeaddition = "purposeaddition";
		var id = val;
		if(id == null || id == '-1')
		return;
		$.ajax({
		type : "post",
		url : "view/Over2Cloud/VAM/master/loadEmployeee.action?purposeaddition="+purposeaddition,
		data: "id="+id, 
		success : function(data) {
			$('#empName option').remove();
			$('#empName').append($("<option></option>").attr("value",-1).text('Select Employee'));
			$(data).each(function(index)
			{
			   $('#empName').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		},
		error: function() {
			alert("error");
		}
		});
	}
	function searchEmpWiseVisitor(){
	 var empName = $("#empName option:selected").val();
	 var deptName = $("#deptNameid option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforepostAckreportView.action?empName="+empName+"&deptName="+deptName,
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
	<div class="list-icon">
	<div class="clear"></div>
	<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
	<div class="rightHeaderButton">
	<span class="normalDropDown"> 
	 </span>
	</div>
	<div class="clear"></div>
	<div class="listviewBorder">
	<div class="listviewButtonLayer" id="listviewButtonLayerDiv" style="height: 40px;">
			<div class="pwie">
			 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> 
			 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10" style="width: 75%;"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
					From Date:<sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true" cssClass="button" onchange="searchDateWiseVisitorData();" style="margin: 0px 6px 10px; width: 8%;"/>
					To Date:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy"  yearRange="2014:2020" readonly="true"   cssClass="button"  onchange="searchDateWiseVisitorData();" style="margin: 0px 6px 10px; width: 8%;"/>
					Dept:<s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select"  onchange="loadEmp(this.value,'empName');" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					For Emp:<s:select  id="empName"  name="empName" list="#{'-1':'All'}" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchEmpWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					Location:<s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchLocationWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 11%;" />
			 <span class="normalDropDown">
			</span>  
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
						 <%-- <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadExcel');" />
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadPDF');" />
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" id="addacknowledgepostId">Add</sj:a> --%>
			</td>   
			</tr></tbody></table></div>	</div>
			<div class="clear"></div>
			<s:url id="postacknowledgeNView" action="postacknowledgeNView" escapeAmp="false">
			<s:param name="location" value="%{#parameters.location}"></s:param>
			<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
			<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
			<s:param name="empName" value="%{#parameters.empName}"></s:param>
			<s:param name="deptName" value="%{#parameters.deptName}"></s:param>
			</s:url>
			<s:url id="modifypostacknowledgeGrid" action="modifypostacknowledgeGrid" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
			  id="acknowledgepostviewid"
	          href="%{postacknowledgeNView}"
	          gridModel="postacknowledgedata"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="%{modifyFlag}"
	          navigatorSearch="true"
	          rowList="15,20,25,50"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="false"  
	          loadingText="Requesting Data..."  
	          rowNum="15"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{modifypostacknowledgeGrid}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="false"
	          onSelectRowTopics="rowselect"
	          >
	        
		<s:iterator value="viewpostacknowledgeDetail" id="viewpostacknowledgeDetail" >  
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
			searchoptions="{sopt:['eq','cn']}"
		/>
		</s:iterator>  
		</sjg:grid>
		</div>
	</div>
	</div>
</body>
</html>