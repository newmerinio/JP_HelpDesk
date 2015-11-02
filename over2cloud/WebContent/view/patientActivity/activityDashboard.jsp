<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"><!--
	var monthCounter=0;
	function nextDayData()
	{
		monthCounter++;
		fetchActivities();
	}

	function previousDayData()
	{
		monthCounter--;
		fetchActivities();
	}

	// set column headers
	function profile(id)
	{
		$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/fetchActivityHistory.action",
			data : "id="+id,
			success : function(data){
				var mytable = $('<table style="margin:0px;padding:0px; width:98%;"></table>').attr({ id: "basicTable1" });
				$('<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Question</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Answers</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td></tr>').appendTo(mytable);
				$(data.jsonArray).each(function(index)
				{
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					for (var j = 0; j < tdValues.length; j++) 
					{
						$('<td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
					}
				});
				
				$("#contactPersonDetailsDialog").html("");
				mytable.appendTo("#contactPersonDetailsDialog");
				$("#contactPersonDetailsDialog").dialog({title: 'Patient Profile', width:'900', height:'500'});
				$("#contactPersonDetailsDialog").dialog("open");
			},
			error   : function(){
				alert("error");
			}
		});
		
	}
	
	// column headers
	function fetchPatientActivities()
	{
		var searchondate = "";
		currDate = monthCounter;
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchDashboardActivityList.action",
			success : function(data){
				
				$("#activityDate").html(data.currDate);
				$("#activityDataList").html("");
				//var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
				var mytable=$("#table1").val();
				var temp = "";
				var row = $('<tr style="background-color: grey; color: white; font-weight: bold;" ></tr>').appendTo("#table1");
				temp = '<td style="padding:2px;margin:1px;text-align:center; width: 3%;">Sr.</td>';
				$(temp).appendTo(row);
				temp='<td style="padding:2px;margin:1px;text-align:center; width: 8%;">Action</td>';
				$(temp).appendTo(row);
				
				$(data.dataArray).each(function(index)
				{
					var tdValues = this.headValue.split(",");
					for (var j = 0; j < (tdValues.length); j++) 
					{
						tdValues[j] = tdValues[j].trim();
						$('<td style="padding:2px;margin:1px;text-align:center; width: 10%;">').text(tdValues[j]).appendTo(row);
					}
				});
			},
			error   : function(){
				alert("error");
			}
		});
	}
	
	// getting data  
	function fetchActivities()
	{
		//alert("fetchActivities()");
		var counter = monthCounter;
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchDataActivity.action",
			data : "counter="+counter,
			success : function(data){
				//alert(JSON.stringify(data));
				$("#activityDate").html(data.currDate);
				$("#activityDataList").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
				$(data.dataArray).each(function(index)
				{
				//	alert(this.VALUE);
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length); j++) 
					{
						tdValues[j] = tdValues[j].trim();
						if(j == 0)
						{
							temp = '<td style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;">'+
							'<center><img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
	     						  '<img onclick="viewPatientFeedback('+tdValues[j]+');" title="Patient Profile" alt="Patient Profile" src="images/profile_pic.png" style="width: 15px; height: 18px;"></center></td>';
							$(temp).appendTo(row);
						}
						/* else if(j == 1)
							$('<td class="example" onclick="openContactDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9; text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
						else if(j == 2)
							$('<td onclick="openOrgDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
						else if(j == 3)
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;"></td>').text(tdValues[j] == "0" ? "Client" : "Associate" ).appendTo(row);
						else if(j == (tdValues.length - 2))
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 25%;"></td>').text(tdValues[j]).appendTo(row);
						 */else
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
					}
				});
				//alert(mytable.toString());
				mytable.appendTo("#activityDataList");
			},
			error   : function(){
				alert("error");
			}
		});
	}

	function searchActivity(date)
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchDataActivity.action",
			data : "&searchondate="+date,
			success :function(data){
//				alert(JSON.stringify(data));
				$("#activityDate").html(data.currDate);
				$("#activityDataList").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
				$(data.dataArray).each(function(index)
				{
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length); j++) 
					{
						tdValues[j] = tdValues[j].trim();
						if(j == 0)
						{
							temp = '<td style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;">'+
							       
							       
	     						   '<center><img onclick="viewPatientFeedback('+tdValues[j]+');" title="Patient Profile" alt="Patient Profile" src="images/profile_pic.png" style="width: 15px; height: 18px;"></center></td>';
							$(temp).appendTo(row);
						}
						else
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
					}
				});
				mytable.appendTo("#activityDataList");
				$("#date").val('');
			},
			error   : function(){
				alert("error");
			}

		});
	}
	
	function fetchPatientMissedActivities()
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchMissedDataActivity.action",
			success : function(data){
			//	alert(JSON.stringify(data));
				$("#activityDataList").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable2" });
				$(data.dataArray).each(function(index)
				{
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length); j++) 
					{
						tdValues[j] = tdValues[j].trim();
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
					}
				});
				mytable.appendTo("#missedActivityDataList");
			},
			error   : function(){
				alert("error");
			}
		});
	}	


	function viewPatientFeedback(id)
	{
		var counter = monthCounter;
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchFeedbackData.action",
			data : "id="+id,
			success : function(data){
				//alert(JSON.stringify(data));
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable3" });
				$(data.dataArray).each(function(index)
				{		
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length); j++) 
					{
						//alert("data"+index);
						if(j===0){		
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
							}else if(index===(data.dataArray.length-1) &&  tdValues[j].trim()!="NA" && tdValues[j].trim()!="" ){ 
								//alert(tdValues[j].trim());
						$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').html("<a href='view/Over2Cloud/patientActivity/downloadReport.action?reportFileName="+tdValues[j].trim()+"'> <img src='images/document.jpg' style='height: 31px;' ></a>").appendTo(row);	
							} 
							else{
								$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
							}}
				});
				$("#activityfeedbackData").html("");
				mytable.appendTo("#activityfeedbackData");
				$("#patientFeedbackDataView").dialog({title: 'Submited Feedback Details ', width:'900', height:'500'});
				$("#patientFeedbackDataView").dialog("open");
			},
			error   : function(){
				alert("error");
			}
		});
	}
	
	function activityHistory(id)
	{
		//alert(id);
		//alert($("#activityDate").text());
		$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/patientActivity/fetchHistoryData.action",
			data : "id="+id+"&date="+$("#activityDate").text(),
			success : function(data){
				var mytable = $('<table style="margin:0px;padding:0px; width:98%;"></table>').attr({ id: "basicTable4" });
				$('<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Activity</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Doctor</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Offering</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td></tr>').appendTo(mytable);
				$(data.dataArray).each(function(index)
				{
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					for (var j = 0; j < tdValues.length; j++) 
					{
						if(index===(data.dataArray.length-1)){ 
					$('<td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 10%;"></td>').text(tdValues[j].trim()).appendTo(row);	
						}else
						$('<td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 10%;"></td>').html(""+tdValues[j]+"").appendTo(row);
					}
				});
				
				$("#activityHistoryData").html("");
				mytable.appendTo("#activityHistoryData");
				$("#patientActivityHistory").dialog({title: 'Patient Activity History', width:'900', height:'500'});
				$("#patientActivityHistory").dialog("open");
			},
			error   : function(){
				alert("error");
			}
		});
		
	}
	
	
</script> 
<%-- <script type="text/javascript" src="js/patientActivityjs/patientActivity.js"></script> --%>
</head>
<body>
<sj:dialog
	id="contactPersonDetailsDialog"
	autoOpen="false"
	modal="true"
></sj:dialog>

<center>
<br>
<div style="width: 99%;">
	<table width="97%" style="margin:0px;padding:0px; " id="table1">
		<tr style="font-weight: bolder;" >
			<td colspan="8"  style="margin:1px;text-align:center;">
			<div style="margin-left: 40%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/backward.jpg" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;  color: black;">	
					Activities As On&nbsp;&nbsp; 
				</div>
				<div id="activityDate" style="color: black; float: left;"></div>
				<div style="float: left;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="nextDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/forward.jpg" title="Next">
				</div>
			</div>
			<br>
			<div style="margin-top: -2%;">
				Activities On Date:	<sj:datepicker name="date" id="date" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="button" 
						placeholder="Select Search Date" timepicker="false" onchange="searchActivity(this.value);" style="width: 10%;"/>
			</div>
			</td>
		</tr>
 	</table>

<div id="activityDataList"  style="height:200px; overflow-y: scroll;"></div>
</div>
<br><br>

<!-- Missed Activities -->
<table style="margin:0px;padding:0px; width:97%;">
<tr style="background-color: white; color: black; font-weight: bold;">
	<td style="padding:2px;margin:1px; text-align:center; width: 4%;" colspan="6">Missed Activities</td>
</tr>
<tr style="background-color: grey; color: white; font-weight: bold;">
	<tr style="background-color: grey; color: white; font-weight: bold;">
			<td style="padding:2px;margin:1px;text-align:center; width: 3%;">Sr.</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 8%;">Patient Name</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Employee </td>
			<td style="padding:2px;margin:1px;text-align:center; width: 7%;">Offering</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 8%;">Activity</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Date</td>
		</tr>
</tr>
</table>
<div id="missedActivityDataList" style="height: 150px; overflow-y: scroll;">
</div>

</center>
<center>
<br>
<sj:dialog
	id="patientFeedbackDataView"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
<div style="width: 104%;">
	<table width="97%" style="margin:0px;padding:0px; " id="feedbakData">
		<tr></tr>
		<tr style="background-color: grey; color: white; font-weight: bold;">
			<td style="padding:2px;margin:1px;text-align:center; width: 3%;">Sr.</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 18%;">Question</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Answer</td>
		</tr>
 	</table>
<div id="activityfeedbackData"  style="   margin-left: 4px;
  padding: 0px;
  overflow-y: auto;
  width: 99%;
  position: absolute;"></div>
</div>
</sj:dialog>
<sj:dialog
	id="patientActivityHistory"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
<div style="width: 104%;">
<div id="activityHistoryData"  style="   margin-left: 4px;
  padding: 0px;
  overflow-y: auto;
  width: 97%;"></div>
</div>
</sj:dialog>

<br><br>

</center>
</body>
<script type="text/javascript">
fetchPatientActivities();
//fetch today activities
fetchActivities();
		//fetch missed activities
fetchPatientMissedActivities();
</script>
</html>