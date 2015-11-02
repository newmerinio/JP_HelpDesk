<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<s:url var="chartDataUrl" action="json-chart-data"/>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/fusioncharts/fusioncharts.js"></script>
<script type="text/javascript" src="js/fusioncharts/themes/fusioncharts.theme.zune.js"></script>
<script src="<s:url value="js/WFPM/opportunity/viewOpportunityDetails.js"/>"></script>
	<script type="text/javascript">
	FusionCharts.ready(function(){
	var list = ${arr};
	var acManagername=$("#acManagerNameid").val();
	var revenueChart = new FusionCharts({
	type: "pie2d",
	renderAt: "chartContainer",
	width: "1300",
	height: "300",
	dataFormat: "json",
	dataSource: {
	"chart": {
	"caption": "Monthly revenue for last year",
	"subCaption": acManagername,
	"xAxisName": "Month",
	"yAxisName": "Revenues",
	"theme": "zune"
	},
	"data": list
	}
	 
	});
	revenueChart.render("chartContainer");
	});
	
	function Column3D_dashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "Column3d",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for last year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	function Column2D_dashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "Column2d",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for last year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	function pie3Ddashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "pie3d",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for last year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	function pie2Ddashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "pie2d",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for last year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	function line2Ddashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "line",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	function bar2Ddashboard()
	{
		var list = ${arr};
		var acManagername=$("#acManagerNameid").val();
		FusionCharts.ready(function(){
			var revenueChart = new FusionCharts({
			type: "bar2d",
			renderAt: "chartContainer",
			width: "1300",
			height: "300",
			dataFormat: "json",
			dataSource: {
			"chart": {
			"caption": "Monthly revenue for last year",
			"subCaption": acManagername,
			"xAxisName": "Month",
			"yAxisName": "Revenues (In Rupees)",
			"theme": "zune"
			},
			"data":  list
			}
			 
			});
			revenueChart.render("chartContainer");
			});
	}
	</script>
	<script type="text/javascript">
		function graphBySalesPerson()
		{
			var acManager=$("#acManager").val();
			var acManagerName=$("#acManager option:selected").text();
			
			$("#chartContainer").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/report/DSR/beforeopportunityreportView.action?modifyFlag=0&deleteFlag=0&acManager="+acManager+"&acManagerName="+acManagerName,
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
  <div class="newColumn">
	<div class="leftColumn1">Sales Person:</div>
	<div class="rightColumn1">
	<s:select 
	            id="acManager"
	            name="acManager" 
	            list="employee_basicMap"
	            headerKey="-1"
	            headerValue="Select" 
	            cssClass="select"
                cssStyle="width:40%"
                onchange="graphBySalesPerson();"
	            >
	</s:select>
	</div>
	</div>
				<div style="float: left; margin-left: 41%; margin-top: 1%;">
				 	<sj:a
						button="true"
						cssClass="button" 
						cssStyle="height:25px;"
		                title="Back"
			            buttonIcon=""
						onclick="backToOpportunityBoard();"
						
					>Back
					</sj:a> 
					</div>  
	
	<div class="contentdiv-small" style="overflow: hidden; width: 95%; height: 40%;margin-top: -1px;">
	<div id="chartContainer">FusionCharts XT will load here!</div>
	<b>View chart as: </b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column3D_dashboard();">Column 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column2D_dashboard();">Column 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie2Ddashboard();">Pie 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie3Ddashboard();">Pie 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="line2Ddashboard();">Line 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="bar2Ddashboard();">Bar 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<s:hidden name="acManagerName" value="%{acManagerName}" id="acManagerNameid"></s:hidden>
	
<div class="contentdiv-small" style="overflow: hidden;" >
<div id="chartContainer">FusionCharts Offering will load here!</div>
<div style="height:auto; margin-bottom:10px;" id='fourthblockId'>
<div style="height:auto; margin-bottom:10px;" ><B>Offering Details</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr>
                   <td valign="bottom">
                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#000000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Client</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Offering</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Prospect</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Lost</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Existing</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                       <table align="center" border="0" style="border-style:dotted solid;" width="100%">
                        	  <s:iterator id="purposeListOflists"  value="purposeListOflists" var = "childlistpurpose"> 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[0]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[1]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[2]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[3]}"/> </b></a></font></td>
                                </tr>
                        	   </s:iterator>
                       </table>
                   </td>
               </tr>
              </table>
</div>
<b>View chart as: </b>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column3D_dashboard();">Column 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column2D_dashboard();">Column 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie2Ddashboard();">Pie 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie3Ddashboard();">Pie 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="line2Ddashboard();">Line 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="bar2Ddashboard();">Bar 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div class="contentdiv-small" style="overflow: hidden;" >
<div id="chartContainer">FusionCharts Lost Client will load here!</div>
<div style="height:auto; margin-bottom:10px;" id='fourthblockId'>
<div style="height:auto; margin-bottom:10px;" ><B>Lost Client Details</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr>
                   <td valign="bottom">
                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#000000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Purpose</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visitor In</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visitor Out</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                       <table align="center" border="0" style="border-style:dotted solid;" width="100%">
                        	  <s:iterator id="purposeListOflists"  value="purposeListOflists" var = "childlistpurpose"> 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Data </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Data </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Data</b></a></font></td>
                                </tr>
                        	   </s:iterator>
                       </table>
                   </td>
               </tr>
              </table>
</div>
<b>View chart as: </b>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column3D_dashboard();">Column 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column2D_dashboard();">Column 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie2Ddashboard();">Pie 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie3Ddashboard();">Pie 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="line2Ddashboard();">Line 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="bar2Ddashboard();">Bar 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div class="contentdiv-small" style="overflow: hidden;" >
<div id="chartContainer">FusionCharts Unmapped Client Details will load here!</div>
<div style="height:auto; margin-bottom:10px;" id='fourthblockId'>
<div style="height:auto; margin-bottom:10px;" ><B>Unmapped Client Details</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr>
                   <td valign="bottom">
                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#000000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Client Name</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Contact</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Address</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                       <table align="center" border="0" style="border-style:dotted solid;" width="100%">
                        	  <s:iterator id="purposeListOflists"  value="purposeListOflists" var = "childlistpurpose"> 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[0]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[1]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[2]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistpurpose[3]}"/> </b></a></font></td>
                                </tr>
                        	   </s:iterator>
                       </table>
                   </td>
               </tr>
              </table>
</div>
<b>View chart as: </b>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column3D_dashboard();">Column 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="Column2D_dashboard();">Column 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie2Ddashboard();">Pie 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="pie3Ddashboard();">Pie 3D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="line2Ddashboard();">Line 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="bar2Ddashboard();">Bar 2D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</body>
</html>