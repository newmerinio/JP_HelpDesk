<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<html>
<head>

<script type="text/javascript">
	
var pendingColor=[];
var closedColor=[];
var escColor=[];

$.subscribe('escalationColor',function(event,data)
{
	for(var i=0;i<pendingColor.length;i++){
		$("#"+pendingColor[i]).css('background','rgb(255, 216, 242)');
	}
	
	for(var i=0;i<closedColor.length;i++){
		$("#"+closedColor[i]).css('background','rgb(191, 255, 169)');
	}
	
	for(var i=0;i<escColor.length;i++){
		$("#"+escColor[i]).css('background','rgb(255, 255, 128)');
	}
	pendingColor=[];
	escColor=[];
	closedColor=[];
}); 

function viewStatus(cellvalue, options, row)
{
	 	if(row.fstatus=='Pending')
		{
	 		pendingColor.push(row.id);
		}
		else if(row.fstatus=='Resolved' && row.level=='Level1')
		{
			closedColor.push(row.id);
		}
		if(row.level=='Level1')
		{
		}
		else 
		{
			escColor.push(row.id);
		}	
    	return ''+cellvalue+'';
}


function getGraphData(fdate,tdate,dept,searchFor)
{
	var fromdate=$("#"+fdate).val();
	var todate=$("#"+tdate).val();
	var dept=$("#"+dept).val();
	var searchFor=$("#"+searchFor).val();
	
		$("#chartDialog").dialog({title: 'Department-wise Distribution of Issues',width: 900,height: 600 });
		$("#chartDialog").dialog('open');
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/report/getReportChartData.action?fromDate="+fromdate+"&toDate="+todate+"&dept="+dept+"&searchFor="+searchFor,
		    success : function(data) {
	       $("#chartDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}	
	
function viewGridSearchdData()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var searchFor=$("#searchFor").val();
	var dept=$("#dept").val();
	var conP = "<%=request.getContextPath()%>";
	$("#resultDiv111").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/report/getReportDataPage.action?fromDate="+fromDate+"&toDate="+toDate+"&searchFor="+searchFor.split(" ").join("%20")+"&dept="+dept,
        success : function(subdeptdata) {
      		$("#"+"resultDiv111").html(subdeptdata);
   		 },
       error: function() {
            alert("error");
        }
     });
}

viewGridSearchdData();


	function viewActivityPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
		    success : function(subdeptdata) 
		    {
		    	$("#"+"data_part").html(subdeptdata);
		    },
		   error: function() 
		   {
			   alert("error");
		   }
		 });
	}
	
	// for Excel Download Starts
	function downloadReportData()
	{
		var fromdate=$("#fromDate").val();
		var todate=$("#toDate").val();
		var dept=$("#dept").val();
		var searchFor=$("#searchFor").val();

		$("#downloadExcelReport").dialog({
			title : 'Check Column',
			width : 350,
			height : 600
		});
		$("#downloadExcelReport").dialog('open');
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/report/downloadReportData.action?fromDate="+fromdate+"&toDate="+todate+"&dept="+dept+"&searchFor="+searchFor,
			success : function(data)
			{
				$("#downloadExcelReport").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}
	
	
</script>

</head>
<body>

	<sj:dialog id="chartDialog" autoOpen="false" modal="true"
		width="400" height="200" resizable="false">
		<div id="chartDiv"></div>
	</sj:dialog>
	
	<sj:dialog id="downloadExcelReport" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Select Fields" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" modal="true" width="390" height="300" />
	
<div class="list-icon">
	 <div class="head">Admin & Ward Round Analytical</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">View</div>
	 <div align="right"><sj:a  buttonIcon="ui-icon-person" button="true" onclick="viewActivityPage();" title="Activity Board" cssClass="button" cssStyle = "width: 140px;margin-right: 3px;margin-top:3px;height:20px">Activity Board</sj:a></div>
</div>
<div class="clear"></div>
    <div class="border" style="height: 50%" align="center">
     <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    <td class="pL10"> 
					    
					     <s:select 
	                      id="searchFor" 
	                      name="searchFor" 
	                      list="{'Admin Round','Ward Rounds'}"
	                      cssClass="button"
	                      theme="simple"
	                      cssStyle = "height:28px;margin-top:-2px;;margin-left: 3px"
                     	 />
                     	 
                     	<s:select 
	                      id="dept" 
	                      name="dept" 
	                      list="%{deptMap}"
	                      headerKey="-1"
	                      headerValue="Department"
	                      cssClass="button"
	                      theme="simple"
	                      cssStyle = "height:28px; width: 120px;;margin-top:-2px;margin-left: 3px"
                     	 />
                     	 
                     	 <sj:datepicker cssStyle="height: 16px; width: 60px;"
							cssClass="button" id="fromDate" name="fromDate" size="20"
							maxDate="0" value="%{fromDate}" readonly="true"
							yearRange="0" showOn="focus"
							displayFormat="dd-mm-yy" Placeholder="From Date" 
						/>
						<sj:datepicker
							cssStyle="height: 16px; width: 60px;" cssClass="button"
							id="toDate" name="toDate" onblur="viewSearchdData();"
							onchange="viewSearchdData();" size="20" value="%{toDate}"
							readonly="true" yearRange="0" showOn="focus"
							displayFormat="dd-mm-yy" Placeholder="To Date" 
						/> 
						
						<sj:a id="search" button="true" cssClass="button" theme="simple"
									title="Search" cssStyle="height:17px; width:60px" onclick="viewGridSearchdData();">Search</sj:a>	
                     	 
					     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  	<sj:a id="downloadData" button="true"buttonIcon="ui-icon-arrowstop-1-s" cssClass="button"
									title="Download" cssStyle="height:18px; width:0px" onclick="downloadReportData()"></sj:a>	
				  	<sj:a id="graphButton" href="#"  cssStyle="height:25px; width:32px" ><img src='images/pie_chart_icon.jpg' style="margin-bottom: -8px;margin-right: 3px;" alt='Graph' width='32' height='25' title="Pie Chart" onclick="getGraphData('fromDate','toDate','dept','searchFor');"></sj:a>
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	<div style="overflow: hidden;">
			<div id="resultDiv111"></div>
		</div>
  </div>
  
</body>
</html>