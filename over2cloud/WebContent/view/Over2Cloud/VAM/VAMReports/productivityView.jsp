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
<title>Productivity</title>
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
		jQuery("#productivityViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#productivityViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#productivityViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#productivityViewid").trigger("reloadGrid"); 
	}
</script>
<script type="text/javascript">
	 function totalVisitReport(rowid)
	 {
			alert("rowid>>"+rowid);
			$.ajax({
			    type : "post",
			   // url : "view/Over2Cloud/VAM/reports/instantvisitaction.action?purpose="+purpose+"&visitor_name="+visitor_name+"&visitor_mobile="+visitor_mobile+"&visitor_company="+visitor_company+"&location="+location+"&gate="+gate+"&comments="+comments+"&visit_date="+visit_date+"&time_in="+time_in,
				  url : "view/Over2Cloud/VAM/reports/totalvisitaction.action?rowid="+rowid,	   
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
		/* * Format a Column as Image */
		function formatImage(cellvalue, options, row) {
			 var context_path = '<%=request.getContextPath()%>';
			 cellvalue="Search";
			return "<img title='Print Visitor Pass' src='"+ context_path +"/images/printer.png' onClick='reprintVisitorPass("+row.id+");' />"+
			"&nbsp&nbsp<img title='Exit Visitor'  src='"+ context_path +"/images/exit1.png' onClick='exitVisitor("+row.id+");' />"
			;}
		
		function formatLink(cellvalue, options, row) {
		// cellvalue = "Search";
		// alert("cellvalue>>>"+cellvalue+"row.id>>>>"+row.id);
			
				return "<a href='#' onclick='totalVisitReport("+row.id+");'>"+cellvalue+"</a>";
				//return "<a href='instantvisitaction.action?id="+row.id +"'>"+cellvalue+"</a>";
			
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
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
			 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
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
		<s:url id="productivityreportView" action="productivityreportView" escapeAmp="false"></s:url>
		<s:url id="modifyproductivity" action="modifyproductivity" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
			  id="productivityViewid"
	          href="%{productivityreportView}"
	          gridModel="productivityData"
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
	          editurl="%{modifyproductivity}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          >
	         <s:iterator value="viewproductivityDetail" id="viewproductivityDetail" >  
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
				name="instantvisitors"
				index="instantvisitors"
				title="Instant Visitors"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/> 
		  <sjg:gridColumn 
				name="prerequestvisitors"
				index="prerequestvisitors"
				title="Pre-Request Visitors"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/> 
			<sjg:gridColumn 
				name="totalvisits"
				index="totalvisits"
				title="Total Visits"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				formatter="formatLink"
				searchoptions="{sopt:['eq','cn']}"
			/> 
			<sjg:gridColumn 
				name="totalbreeches"
				index="totalbreeches"
				title="Total Breeches"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/> 
			<sjg:gridColumn 
				name="totaltime"
				index="totaltime"
				title="Total Time"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/> 
			<sjg:gridColumn 
				name="totalpost"
				index="totalpost"
				title="Total Posts"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				searchoptions="{sopt:['eq','cn']}"
			/> 
			<sjg:gridColumn 
				name="graphview"
				index="graphview"
				title="Graph"
				width="150"
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
	<sj:dialog 
    	id="totalvisitreport" 
    	autoOpen="false" 
    	modal="true" 
    	title="Instant Visitor Action"
    	openTopics="openRemoteDialog"
    	width="690"height="400"
    	/>
</body>
</html>