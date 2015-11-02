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
		jQuery("#vehicleViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#vehicleViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#vehicleViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#vehicleViewid").trigger("reloadGrid"); 
	}
</script>

 <script type="text/javascript">
 function exitVehicle1(){
		var rowid = $("#vehicleViewid").jqGrid('getGridParam','selarrrow');
      var VehicleNumber = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'vehicle_number');
	 
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/vehicleExitAdd.action?modifyFlag=0&deleteFlag=0&vehicleExitStatus=1&vehiclestatus=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
   }
 function reprintVehiclePass(){
 
   var rowid = $("#vehicleViewid").jqGrid('getGridParam','selarrrow');
      if(rowid.length==0)
      {  
       alert("please select atleast one row....");
       return false;
      }
      else if(rowid.length > 1)
      {
         alert("please select only one row....");
         return false;
     }
		var srnumber = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'sr_number');
		var drivername = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'driver_name');
		var drivermobile = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'driver_mobile');
		var vehiclemodel = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'vehicle_model');
		var vehiclenumber = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'vehicle_number');
		var entrydate = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'entry_date');
		var entrytime = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'entry_time');
		var deptName = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'deptName');
		var location = jQuery("#vehicleViewid").jqGrid('getCell',rowid, 'location');
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/rePrintVehiclePass.action?sr_number="+srnumber+"&driver_name="+drivername+"&driver_mobile="+drivermobile+"&vehicle_model="+vehiclemodel+"&vehicle_number="+vehiclenumber+"&entry_date="+entrydate+"&entry_time="+entrytime+"&deptName="+deptName+"&location="+location,
		    success : function(subdeptdata) {
		    	$("#"+"reprintvehiclepass").html(subdeptdata);
		    	$("#"+"reprintvehiclepass").dialog('open');
		        
		},
		   error: function() {
	            alert("error");
	        }
		 }); 
	}
 </script>
 <script type="text/javascript">
			function searchDateWiseVehicleMIS(){
					var from_date = $("#from_date").val();
					var to_date = $("#to_date").val();
	 				$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?from_date="+from_date+"&to_date="+to_date,
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });			
			}

function searchDeptWiseVehicleMIS(){
	 var deptName = $("#deptNameid option:selected").val();
	 $.ajax({
	 type : "post",
	 url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?deptName="+deptName,
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
				 url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?vehlocation="+location,
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
				 url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?purpose="+purpose,
				 success : function(subdeptdata) {
				 $("#"+"data_part").html(subdeptdata);
				 },
				 error: function() {
				 alert("error");
							  }
					 });	
		}
		function searchData(){
			 var vehicle_number = $("#vehicle_number").val();
			
			 var selectedId = $("#vehicleStatus").val();
			 $.ajax({
				 type : "post",
				 url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?vehicle_number="+vehicle_number+"&selectedId="+selectedId,
				 success : function(subdeptdata) {
				 $("#"+"data_part").html(subdeptdata);
				 },
				 error: function() {
				                     alert("error");
							       }
			});	
		}
		function reloadPage()
		{
          	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?modifyFlag=0&deleteFlag=0",
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
	  <div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">&nbsp&nbsp&nbsp Total:&nbsp<s:property value="%{countTotal}"/> &nbsp&nbsp Entry:&nbsp<s:property value="%{countIn}"/>&nbsp&nbsp&nbsp Exit:&nbsp<s:property value="%{countOut}"/>&nbsp&nbsp&nbsp &nbsp<sj:a id="refButtonPage" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 571px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a></div>
	  <div class="head" style="margin-top:4px; float: right;"><sj:a button="true" cssClass="button" cssStyle="margin-top: -2px; height:28px;" title="Add" buttonIcon="ui-icon-plus" id="addVehicleId">Entry</sj:a></div>
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
			 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10" style="width: 90%;"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
				    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
				    From:<sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true" cssClass="textField"  onchange="searchDateWiseVehicleMIS();" style="margin: 0px 6px 10px; width: 7%;"/>
					To:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true"   cssClass="textField"  onchange="searchDateWiseVehicleMIS();" style="margin: 0px 6px 10px; width: 7%;"/>
					    <s:select 
			                       id="vehicleStatus" 
			                       name="vehicleStatus"
			                       list="#{'0':'IN','1':'OUT','2':'ALL'}"
			                       headerKey="-1"
			                       headerValue="Status"
			                       cssStyle="height: 26px; width: 9%;"
                                   theme="simple" 
			                       cssClass="select"
			                       onchange="searchData();"
			              />
					   <s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="Department" theme="simple" cssClass="select" onchange="searchDeptWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					   <%-- <s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="Location" theme="simple" cssClass="select" onchange="searchLocationWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
					 --%>   <s:select  id="purpose"  name="purpose" list="purposelist" headerKey="-1" headerValue="Purpose" theme="simple" cssClass="select" onchange="searchPurposeWiseVehicleMIS();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 8%;" />
			       	   <s:textfield id="vehicle_number" name="vehicle_number" theme="simple" cssClass="textField" onchange="searchData();" style="margin-top: -10px; height: 23px; margin-left: 6px; width: 7%;" placeholder="Enter Data"></s:textfield><!--         
			           <s:textfield id="driver_mobile" name="driver_mobile" theme="simple" cssClass="textField" onchange="searchData();" style="margin-top: -10px; height: 23px; margin-left: 6px; width:7%;" placeholder="Mobile No"></s:textfield>         
		    --><span class="normalDropDown">
			</span>  
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
			          <a><img title="Print" src="images/printer.png" style="height:23px; width:28px;" onclick="reprintVehiclePass();"/></a>&nbsp&nbsp
					  <a><img title="Download Excel" src="images/page_white_excel.png" style="height:23px; width:28px;"  cssStyle="height:25px;" onclick="setVisitorDownloadType('downloadExcel');"></a>&nbsp&nbsp
					  <a><img title="Download Pdf" src="images/page_white_acrobat.png" style="height:23px; width:28px;" cssStyle="height:25px;" onClick="setVisitorDownloadType('downloadPDF');"/></a>&nbsp&nbsp
					  <a><img title="Exit" src="images/exit1.png" style="height:23px; width:28px;" onclick="exitVehicle1();"/></a>
			</td>   
			</tr></tbody></table></div></div>
			<div class="clear"></div>
	<s:url var="app_name" id="vehicleRecord" action="vehicleDetails" escapeAmp="false">
	<s:param name="deptName" value="%{#parameters.deptName}"></s:param>
	<s:param name="vehlocation" value="%{#parameters.vehlocation}"></s:param>
	<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
	<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
	<s:param name="purpose" value="%{#parameters.purpose}"></s:param>
	<s:param name="vehicle_number" value="%{vehicle_number}"></s:param>
	<s:param name="driver_mobile" value="%{driver_mobile}"></s:param>
	<s:param name="sr_number" value="%{sr_number}"></s:param>
	<s:param name="selectedId" value="%{selectedId}"></s:param>
	</s:url>
	<s:url id="modifyVehicleGrid" action="modifyVehicleGrid" />
	<div style="overflow: scroll; height: 480px;">
	<sjg:grid 
			  id="vehicleViewid"
	          href="%{vehicleRecord}"
	          gridModel="vehicledatalist"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          rowList="15,20,25,50"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rowNum="15"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{modifyVehicleGrid}"
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
		<center>
			<sj:div id="vehicleExitID">
			</sj:div>
		</center> 
		</div>
	</div>
	</div>
		<sj:dialog 
    	id="reprintvehiclepass" 
    	autoOpen="false" 
    	modal="true" 
    	title="Vehicle Pass"
    	openTopics="openRemoteDialog"
    	width="690"height="400"
    	/> 
    	<sj:dialog id="downloaddaildetails" buttons="{'Download':function() { okdownload(); }, 'Cancel':function() {},}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Select Fields" openTopics="openEffectDialog"closeTopics="closeEffectDialog"  width="390"height="300" />
</body>
</html>