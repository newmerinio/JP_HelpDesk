<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
                                                                   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@page import="com.Over2Cloud.CommonClasses.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
function refresh(div)
{
	if(div=='1stBlock')
	{
		var type=document.getElementById("tabDataType").value;
	
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=1",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
	else if (div=='2stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
			 $("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlock')
	{
		var type=document.getElementById("tabDataType1").value;
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
			    success : function(subdeptdata) {
				 $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=1",
			    success : function(subdeptdata) {
			
			    $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart1')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=1",
			    success : function(subdeptdata) {
			
			    $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
}
function showDetails(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		var type=document.getElementById("tabDataType").value;
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
			    success : function(subdeptdata) {
				 $("#dashboardDialog").html(subdeptdata);
			     $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=0",
			    success : function(subdeptdata) {
			   
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
	else if (div=='2stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#dashboardDialog").html(subdeptdata);
		     $("#dashboardDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
			 $("#dashboardDialog").html(subdeptdata);
		     $("#dashboardDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlock')
	{
		var type=document.getElementById("tabDataType1").value;
		
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
			    success : function(subdeptdata) {
				 $("#dashboardDialog").html(subdeptdata);
			     $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=0",
			    success : function(subdeptdata) {
			
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart1')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=0",
			    success : function(subdeptdata) {
			   
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
}
function showDetailsPie(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=1",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='4stBlock') 
		{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=1",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function showDetailsData(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='4stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function stillInVehicle()
{   
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/beforedashboardvehicleview.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"visitorshowid").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
}

stillInVehicle();

</script>
<script type="text/javascript">
	function showPieChart(id)
	{
		if(id=='secondblockId')
		{
		$("#"+id).html("<center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/VAM/master/pieChartVehicleDeptwiseReport.action",
			success : function(visitordata) {
			
			$("#"+id).html(visitordata);
			},
			error : function() {
				alert("error");
			}
		});
	}
	else if(id=='fourthblockId')
	{
		$("#"+id).html("<center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/VAM/master/pieChartVehiclePurposewiseReport.action",
			success : function(visitordata) {
			
			$("#"+id).html(visitordata);
			},
			error : function() {
				alert("error");
			}
		});
	}
}

function showCounterDataDashboardTable(id)
{
	if(id=='secondblockId')
	{
	$("#"+id).html("<center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/VAM/master/deptwiseVehicleReport.action",
		success : function(deptdashdata) {
		$("#"+id).html(deptdashdata);
		},
		error : function() {
			alert("error");
		}
	});
}
else if(id=='fourthblockId')
	{
		$("#"+id).html("<center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/VAM/master/purposewiseVehicleReport.action",
			success : function(deptdashdata) {
			
			$("#"+id).html(deptdashdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
}

</script>
</head>
<body>
<sj:dialog 
    	id="visitorexitDialog" 
    	autoOpen="false" 
    	modal="true" 
    	width="600"
		height="500"
    	resizable="false"
    	title="Selected View"
    >
</sj:dialog>
<sj:dialog 
    	id="dataDialog1" 
    	autoOpen="false" 
    	modal="true" 
    	width="870"
		height="300"
    	resizable="false"
    	title="View"
    >
</sj:dialog>

<div class="middle-content">

<!-- Current vehicle start here -->
<div class="contentdiv-small" style="overflow: hidden; width: 95%;">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;">
<sj:dialog 
    	id="visitorshowid" 
    	autoOpen="false" 
    	modal="true" 
    	width="750"
		height="180"
    	resizable="false"
    	title="View"
    >
 </sj:dialog>
</div>
</div>
<!-- Current vehicle end here -->

<!-- Departmentwise start here -->
<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieChart('secondblockId')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart"  /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showCounterDataDashboardTable('secondblockId')"><img src="images/data.jpg" width="15" height="15" alt="Show Counters" title="Show Counters"  /></a></div>
<div style="height:auto; margin-bottom:10px;" id='secondblockId'>
<div style="height:auto; margin-bottom:10px;" ><B>Departmentwise Vehicle Status</B></div>

<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#780000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Department</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle In</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle Out</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                 
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="listOlists"  value="listOlists" var = "childlist"> 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[0]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[1]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[2]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[3]}"/> </b></a></font></td>
                                </tr>
                        	   </s:iterator>
                        </table>
                   
                   </td>
               </tr>
</table>
              
</div>
</div>
<!-- Departmentwise end here -->

<!-- Visitor start here -->
<div class="contentdiv-small" style="overflow: hidden;">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Vehicle Count</B></div>
<table  align="center" border="0" width="100%" style="border-style:dotted solid;">
<tbody>
 <tr bgcolor="#669966" bordercolor="black" >
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Date</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle In</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle Out</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
 </tr>
  <s:iterator id="sevendayslistoflist"  value="sevendayslistoflist" var = "childlistofdate"> 
<tr>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlistofdate[0]}" /> </b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="%{#childlistofdate[1]}" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="%{#childlistofdate[3]}" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="%{#childlistofdate[2]}" /></a></font></td>
</tr>
</s:iterator>
</tbody>
</table>
</div>
</div>
<!--End here  -->

<!-- Purpose start here -->
<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieChart('fourthblockId')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart"  /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showCounterDataDashboardTable('fourthblockId')"><img src="images/data.jpg" width="15" height="15" alt="Show Counters" title="Show Counters"  /></a></div>
<div style="height:auto; margin-bottom:10px;" id='fourthblockId'>
<div style="height:auto; margin-bottom:10px;" ><B>Purposewise Vehicle Status</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr>
                   <td valign="bottom">
                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#000000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Purpose</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle In</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle Out</b></font></td>
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
</div>
<!-- Purpose end here -->

</div>
</body>
<script type="text/javascript">
</script>
</html>