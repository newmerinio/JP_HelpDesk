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
<title>Vehicle Entry View</title>
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
		jQuery("#vehiclemisViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#vehiclemisViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#vehiclemisViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#vehiclemisViewid").trigger("reloadGrid"); 
	}
</script>
<script type="text/javascript">
			function searchDateWiseVehicleMIS(){
					var from_date = $("#from_date").val();
					var to_date = $("#to_date").val();
	 				$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?from_date="+from_date+"&to_date="+to_date,
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });				
			}

function searchDeptWiseVehicleMIS(){
	 var deptname = $("#deptNameid option:selected").val();
	 $.ajax({
			type : "post",
			url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?deptName="+deptname,
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
			},
			error: function() {
			alert("error");
			}
			});		
		}
		function searchLocationWiseVehicleMIS(){
			 var location = $("#location option:selected").val();
			 $.ajax({
					type : "post",
					url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?location="+location,
					success : function(subdeptdata) {
					$("#"+"data_part").html(subdeptdata);
					},
					error: function() {
					alert("error");
					}
					});		
		}
		function searchPurposeWiseVehicleMIS(){
			 var purpose = $("#purpose option:selected").val();
			$.ajax({
					type : "post",
					url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?purpose="+purpose,
					success : function(subdeptdata) {
					$("#"+"data_part").html(subdeptdata);
					},
					error: function() {
					alert("error");
					}
					});		
		}
		function searchStatusWiseVehicleMIS() {
		var status = $("#veh_status option:selected").text();
		alert("status>>>"+status);
		$.ajax({
			type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?veh_status="+status,
		    success : function(data) {
	     	  $("#"+"data_part").html(data);
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
					From:<sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true"  onchange="searchDateWiseVehicleMIS();" cssClass="textField" style="margin: 0px 6px 10px; width: 6%;"/>
					To:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true"   cssClass="textField"  onchange="searchDateWiseVehicleMIS();" style="margin: 0px 6px 10px; width: 6%;"/>
					Vehicle Status:<s:select  id="veh_status"  name="veh_status" list="#{'1':'With Material','2':'Without Material'}" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchStatusWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					Plant:<s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchDeptWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					Location From:<s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchLocationWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
			 		Visit Purpose:<s:select  id="purpose"  name="purpose" list="purposelist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchPurposeWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
			 <span class="normalDropDown">
			</span>  
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
						 <%-- <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType11('downloadExcel');" />
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType11('downloadPDF');" /> 
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" id="addVehicleId">Add</sj:a> --%>
			</td>   
			</tr></tbody></table></div>	</div>
			<div class="clear"></div>
	<s:url var="app_name" id="vehiclemisRecord" action="vehiclemisdetail" escapeAmp="false">
	<s:param name="deptName" value="%{#parameters.deptName}"></s:param>
	<s:param name="location" value="%{#parameters.location}"></s:param>
	<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
	<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
	<s:param name="purpose" value="%{#parameters.purpose}"></s:param>
	<s:param name="veh_status" value="%{#parameters.veh_status}"></s:param>
	</s:url>
	<s:url id="modifyvehiclemisGrid" action="modifyvehiclemisGrid" />
	<div style="overflow: scroll; height: 480px;">
	<sjg:grid 
			  id="vehiclemisViewid"
	          href="%{vehiclemisRecord}"
	          gridModel="vehicledatalist"
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
	          editurl="%{modifyvehiclemisGrid}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          >
	          
    		<s:iterator value="viewVehicleDetail" id="viewVehicleDetail" >  
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
    	<sj:dialog id="downloaddaildetails" buttons="{'Download':function() { okdownload(); }, 'Cancel':function() {},}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Select Fields" openTopics="openEffectDialog"closeTopics="closeEffectDialog"  width="390"height="300" />
</body>
</html>