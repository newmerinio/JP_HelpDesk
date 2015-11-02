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
	<title>Visitor MIS View</title>
	<script type="text/javascript">
	/* function pageisNotAvailable(){
	$("#facilityisnotavilable").dialog('open');
	} */
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
			alert("Please, Select At Least One Row.");
			return;
		}
		jQuery("#vistormisViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#vistormisViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#vistormisViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#vistormisViewid").trigger("reloadGrid"); 
	}
</script>
<script type="text/javascript">	 
function downloadEXECL(rowidval){
	alert("rowExcel"+rowidval);
    var downloadType="downloadEXCEL";
    //var app_name = 'VAM';
     
     var jsVar = '<s:property value="#app_name"/>'; 
     alert("app_name"+jsVar);
    var downloadurlaction="getvisitorexceldownload";
	$("#downloadpdfid").load("view/Over2Cloud/VAM/download/getvisitorexcelpdfpdfconfirmation.action?id="+rowidval+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction+"&app_name="+app_name);
    $("#downloadpdfid").dialog('open');
}
function dowloadinvoice(){
 $("#download").submit();
}

function searchDeptWiseVisitor(){
	 var deptname = $("#deptNameid option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?deptName="+deptname,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchEmpWiseVisitor(){
	 var empName = $("#empName option:selected").text();
	 var deptName = $("#deptNameid option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?empName="+empName+"&deptName="+deptName,
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
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?from_date="+from_date+"&to_date="+to_date,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function searchPurposeWiseVisitor()
{
	var purpose = $("#purpose").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?purpose="+purpose,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

</script>
<script type="text/javascript">
				$.subscribe('oncompleteresult', function(event,data)
		       {
		         setTimeout(function(){ $("#resultexit").fadeIn(); }, 10);
		         setTimeout(function(){ $("#resultexit").fadeOut(); }, 4000);
		       });
</script>
<script type="text/javascript">
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

function loadGateName(val,divId){
	var id = val;
	if(id == null || id == '-1')
	return;
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/VAM/master/loadGateName.action",
	data: "id="+id, 
	success : function(data) {
		$('#gate option').remove();
		$('#gate').append($("<option></option>").attr("value",-1).text('Select Gate'));
		$(data).each(function(index)
		{
		   $('#gate').append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
	},
	error: function() {
		alert("error");
	}
	});
}

function searchLocationAndGateWiseVisitor(){
	 var location = $("#location option:selected").val();
	 var gate = $("#gate option:selected").text();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?location="+location+"&gatename="+gate,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchRequestedVisitor(){
	 var visit_mode = $("#visit_mode option:selected").text();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?visit_mode="+visit_mode,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function wildSearchVisitor(){
	var visitorNameVal = $("#visitor_nameId").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?visitorNameVal="+visitorNameVal,
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
	<div class="list-icon">
	<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
	<div class="rightHeaderButton">
	<span class="normalDropDown"> 
	 </span>
	</div>
	<div class="listviewBorder">
			<div class="listviewButtonLayer" id="listviewButtonLayerDiv" style="height: 36px;">
			<div class="pwie">
			 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> 
			 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10" style="width: 75%;"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
				<%--	Dept:<s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchDeptWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
						Location:<s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchLocationWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 7%;" />
				 --%>
					Dept:<s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select"  onchange="loadEmp(this.value,'empName');" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 6%;" />
				<%-- 	Emp:<s:select  id="empName"  name="empName" list="#{'-1':'All'}" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchEmpWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 6%;" /> --%>
					Location:<s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="loadGateName(this.value,'gate');" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 6%;" />
					Gate:<s:select  id="gate"  name="gate" list="#{'-1':''}" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchLocationAndGateWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 6%;" />
					From:<sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy"  yearRange="2014:2020" readonly="true" cssClass="textField" style="margin: 0px 6px 10px; width: 6%;"/>
					To:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy"  yearRange="2014:2020" readonly="true"   cssClass="textField"  onchange="searchDateWiseVisitorData();" style="margin: 0px 6px 10px; width: 6%;"/>
				 	Mode: <s:select id="visit_mode" name="visit_mode"  list="#{'Instant Visitor':'Instant Visitor', 'Pre-Request':'Pre-Request'}" headerKey="All" headerValue="All" theme="simple" cssClass="select" onchange="searchRequestedVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 6%;" />
				    Purpose: <s:select id="purpose" name="purpose" list="purposeListPreReqest" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchPurposeWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					Visitor: <s:textfield name="visitor_name"  id="visitor_nameId" theme="simple" onkeyup="wildSearchVisitor();" cssClass="textField" placeholder="Enter Data" style="margin: 0px 0px 9px; width: 6%;"/>
			<%--  <span class="normalDropDown">
			</span>  --%>
			</td></tr>
			</tbody></table>
			</td><td class="alignright printTitle" style="width: 9%;">
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadExcel');" />
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadPDF');" /><!--
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" id="addVisitorId">Add</sj:a>
			--></td>   
			</tr></tbody></table></div>	</div>
			<div class="clear"></div>
		<s:url var="app_name" id="visitorMisRecord" action="visitorMisdetail" escapeAmp="false">
		<s:param name="app_name" value="%{#parameters.app_name}"></s:param>
		<s:param name="deptName" value="%{#parameters.deptName}"></s:param>
		<s:param name="empName" value="%{#parameters.empName}"></s:param>
		<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
		<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
		<s:param name="purpose" value="%{#parameters.purpose}"></s:param>
		<s:param name="location" value="%{#parameters.location}"></s:param>
		<s:param name="gatename" value="%{#parameters.gatename}"></s:param>
		<s:param name="visit_mode" value="%{#parameters.visit_mode}"></s:param>
		<s:param name="visitorNameVal" value="%{visitorNameVal}"></s:param>
		</s:url>
		<s:url id="modifyVisitormisGrid" action="modifyVisitormisGrid" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
			  id="vistormisViewid"
	          href="%{visitorMisRecord}"
	          gridModel="visitorData"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
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
	          editurl="%{modifyVisitormisGrid}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          >
	        
		<s:iterator value="viewVisitorDetail" id="viewVisitorDetail" >  
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
		
		<sjg:gridColumn 
				name="totalstaytime"
				index="totalstaytime"
				title="Total Time"
				width="70"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/>   
		
		
		<sjg:gridColumn 
					name="visitormodecol"
					index="visitormodecol"
					title="Mode"
					width="80"
					align="center"
					editable="false"
					search="false"
					hidden="false"
					searchoptions="{sopt:['eq','cn']}"
				/>
				
		
		
		</sjg:grid>
		</div>
		</div>
		</div>
		 <sj:dialog id="downloaddaildetails" buttons="{'Download':function() { okdownload(); }, 'Cancel':function() {},}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Select Fields" openTopics="openEffectDialog"closeTopics="closeEffectDialog"  width="390"height="300" />	
		
	</body>
	</html>