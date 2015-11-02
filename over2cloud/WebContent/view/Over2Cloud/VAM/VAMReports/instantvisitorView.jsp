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
<title>Instant Visitor View</title>
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
		jQuery("#instantvisitorViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#instantvisitorViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#instantvisitorViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#instantvisitorViewid").trigger("reloadGrid"); 
	}
</script>
<style type="text/css">
a:link {
COLOR: #FF3300;
}
a:visited {
COLOR: #800080;
}
a:hover {
COLOR: #FF0000;
}
a:active {
COLOR: #00FF00;
}
</style>
<script type="text/javascript">
		/* * Format a Column as Image */
		function formatImage(cellvalue, options, row) {
			 var context_path = '<%=request.getContextPath()%>';
			 cellvalue="Search";
			return "<img title='Print Visitor Pass' src='"+ context_path +"/images/printer.png' onClick='reprintVisitorPass("+row.id+");' />"+
			"&nbsp&nbsp<img title='Exit Visitor'  src='"+ context_path +"/images/exit1.png' onClick='exitVisitor("+row.id+");' />"
			;}
		
		function formatLink(cellvalue, options, row) {
			if(cellvalue == "Pending")
			{
				return "<a href='#' onclick='instantvisitoradd("+row.id+");'>"+cellvalue+"</a>";
				//return "<a href='instantvisitaction.action?id="+row.id +"'>"+cellvalue+"</a>";
			}else
			{
				return cellvalue;
			}
			
			   }
 </script>
 <script type="text/javascript">
	 function instantvisitoradd(rowid)
	 {
			/* var visitor_name = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'visitor_name');
			var visitor_mobile = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'visitor_mobile');
			var visitor_company = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'visitor_company');
			var visit_date = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'visit_date');
			var time_in = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'time_in');
			var purpose = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'purpose');
			var location = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'location');
			var gate = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'gate');
			var comments = jQuery("#instantvisitorViewid").jqGrid('getCell',rowid, 'comments');
			alert(visitor_name+visitor_mobile+visitor_company+visit_date+time_in+purpose+location+gate+comments); */
			$.ajax({
			    type : "post",
			   // url : "view/Over2Cloud/VAM/reports/instantvisitaction.action?purpose="+purpose+"&visitor_name="+visitor_name+"&visitor_mobile="+visitor_mobile+"&visitor_company="+visitor_company+"&location="+location+"&gate="+gate+"&comments="+comments+"&visit_date="+visit_date+"&time_in="+time_in,
				  url : "view/Over2Cloud/VAM/reports/instantvisitaction.action?rowid="+rowid,	   
			    success : function(subdeptdata) {
			    	$("#"+"data_part").html(subdeptdata);
			    	//$("#"+"instantvisitoradd").dialog('open');
			        
			},
			   error: function() {
		            alert("error");
		        }
			 });
			
	 }
 </script>
<script type="text/javascript">
	function searchDateWisePreReqData(){
	var from_date = $("#from_date").val();
	var to_date = $("#to_date").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?from_date="+from_date+"&to_date="+to_date,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		}
	function searchPurposeWisePreReq()
	{
		var purpose = $("#purpose").val();
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?purpose="+purpose,
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	function searchLocationWisePreReq()
	{
		var location = $("#location").val();
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?location="+location,
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	function searchStatusWisePreReq()
	{
		var visitorstatus = $("#visitorstatusid").val();
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?visitorstatus="+visitorstatus,
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
	<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Status View</div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
	<div class="rightHeaderButton">
	<span class="normalDropDown"> 
	 </span>
	</div>
	<div class="listviewBorder">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
			 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
					Status: <s:select id="visitorstatusid" name="visitorstatus" list="#{'Pending':'Pending', 'Rejected':'Rejected', 'Accepted':'Accepted'}" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchStatusWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 11%;" />
					From Date:<sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy" value="%{datebeforenday}" yearRange="2014:2020" readonly="true" cssClass="textField" style="margin: 0px 6px 10px; width: 9%;"/>
					To Date:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy" value="today" yearRange="2014:2020" readonly="true"   cssClass="textField"  onchange="searchDateWisePreReqData();" style="margin: 0px 6px 10px; width: 9%;"/>
				 	Purpose: <s:select id="purpose" name="purpose" list="purposeList" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchPurposeWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 11%;" />
					Location:<s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="All" theme="simple" cssClass="select" onchange="searchLocationWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 11%;" />
			<span class="normalDropDown">
			</span> 
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
						 <%-- <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadExcel');" />
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadPDF');" /> 
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" id="addPreRequestId">Pre-Request</sj:a> --%>
			</td>   
			</tr></tbody></table></div>	</div>
			<div class="clear"></div>
		<s:url id="instantvisitorView" action="instantvisitorView" escapeAmp="false">
		<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
		<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
		<s:param name="purpose" value="%{#parameters.purpose}"></s:param>
		<s:param name="location" value="%{#parameters.location}"></s:param>
		<s:param name="visitorstatus" value="%{#parameters.visitorstatus}"></s:param>
		</s:url>
		<s:url id="modifyinstantvisitor" action="modifyinstantvisitor" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
			  id="instantvisitorViewid"
	          href="%{instantvisitorView}"
	          gridModel="instantvisitorData"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          rowList="15,20,25"
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
	          editurl="%{modifyinstantvisitor}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          >
	          <sjg:gridColumn 
				name="visitorStatuscol"
				index="visitorStatuscol"
				title="Status"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				formatter="formatLink"
				searchoptions="{sopt:['eq','cn']}"
			/> 
		<s:iterator value="viewInstantVisitorDetail" id="viewInstantVisitorDetail" >  
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
	<sj:dialog 
    	id="instantvisitoradd" 
    	autoOpen="false" 
    	modal="true" 
    	title="Instant Visitor Action"
    	openTopics="openRemoteDialog"
    	width="690"height="400"
    	/> 
</body>
</html>