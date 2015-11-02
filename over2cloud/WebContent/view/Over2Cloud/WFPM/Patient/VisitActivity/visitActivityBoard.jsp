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
 <%-- <script src="bootstrap-3.3.4-dist/js/jquery.min.js"></script>
  <script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css"> --%>
<title>Insert title here</title>
<style type="text/css">

	.dropdown profile_dropdown{
		width: 160px !important;
	}
</style>
<script type="text/javascript">
function takeActionOnClick(row,first_name,id,mobile) 
{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforePatientActivity.action?",
		    data: "rowid="+row+"&first_name="+first_name+"&laststatus=1&id="+id+"&mobile="+mobile,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });	
}



	var monthCounter=0;
	function nextDayData()
	{
		monthCounter++;
		fetchActivities();
		getOfferingList();
	}

	function previousDayData()
	{
		monthCounter--;
		fetchActivities();
		getOfferingList();
	}



	function patientProfile(id)
	{
		$("#patientActivityHistory4").dialog({title: 'Patient Profile', width:'302', height:'400'});
		$("#activityHistoryData3").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$("#patientActivityHistory4").dialog("open");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/patientBasicDetails.action",
			data : "id="+id,
			success : function(data){
				$("#activityHistoryData3").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	
	// set column headers
	function profile(id)
	{
		$("#contactPersonDetailsDialog7").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
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
				
				$("#contactPersonDetailsDialog7").html("");
				mytable.appendTo("#contactPersonDetailsDialog7");
				$("#contactPersonDetailsDialog7").dialog({title: 'Patient Profile', width:'900', height:'500'});
				$("#contactPersonDetailsDialog7").dialog("open");
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
			url  : "view/Over2Cloud/WFPM/Patient/fetchPatientActivities.action",
			success : function(data){
				
				$("#activityDate7").html(data.currDate);
				$("#activityDataList6").html("");
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
		function getOfferingList()
		{
			var counter = monthCounter;
			 $.ajax({
				    type : "post",
				    url  : "view/Over2Cloud/WFPM/Patient/fetchOfferingList.action",
				    data : "counter="+counter,		
				    success : function(data) 
				    {
				    	console.log(data);
				    	$('#subofferingname option').remove();
						$('#subofferingname').append($("<option></option>").attr("value",-1).text("--Select Offering--"));
				    	$(data).each(function(index)
						{
						   $('#subofferingname').append($("<option></option>").attr("value",this.ID).text(this.Name));
						});
				    },
				   error: function() {
				        alert("error");
				    }
				 });
		}
	
		function fetchActivities()
		{
			//alert("fetchActivities()");
			var counter = monthCounter;
			$.ajax({
				type : "post",
				url  : "view/Over2Cloud/WFPM/Patient/fetchDataVisitActivity.action",
				data : "counter="+counter+"&offeringId="+$("#subofferingname").val()+"&searchondate="+$("#dateID").val(),
				success : function(data){
					console.log(data);
					//alert(JSON.stringify(data));
					$("#activityDate7").html(data.currDate);
					$("#activityDataList6").html("");
				  		
					//var mytable=$("#table1").val();
					var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
					$(data.dataArray).each(function(index)
					{
					//	alert(this.VALUE);
						var temp = "";
						var tdValues = this.VALUE.split(",");
						var row = $('<tr></tr>').appendTo(mytable);
						
						temp = '<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
						$(temp).appendTo(row);
						for (var j = 0; j < (tdValues.length-1); j++) 
						{
							tdValues[j] = tdValues[j].trim();
							if(j == 0)
							{
								//
								temp = '<td style="cursor:pointer; padding:2px;border-right: 1px solid #e7e9e9; margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;">'+
							'<center><img onclick="takeActionOnClick('+tdValues[tdValues.length-1]+','+tdValues[1]+','+tdValues[0]+','+tdValues[tdValues.length-2]+');" title="Take Action" alt="Take Action" src="images/action.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;<img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
								'<img onclick="patientProfile('+tdValues[tdValues.length-1]+');" title="Patient Profile" alt="Patient Profile" src="images/profile_pic.png" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
		     						  '<img onclick="viewPatientFeedback('+tdValues[tdValues.length-1]+');" title="Patient Questionaire" alt="Patient Questionaire" src="images/WFPM/commonDashboard/fullHistory.jpg" style="width: 15px; height: 18px;"></center></td>';
								$(temp).appendTo(row);
							}
							else if(j == 1)
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j].replace("'","").replace("'","")).appendTo(row);
							else if(j == 2)
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[8]).appendTo(row);
							
							else if(j == 3)
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j-1]).appendTo(row);
							else if(j == 4)
								$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j+1]).appendTo(row);
							else if(j == 5)
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j+1]).appendTo(row);
							else if(j == 6)
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 14%;"></td>').text(tdValues[j+1]).appendTo(row);
							else if(j == 7)
								$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);
							else if(j == 8)
								$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);
							

							
							}
					});
					mytable.appendTo("#activityDataList6");
					//$("#date").val('');
				},
				error   : function(){
					alert("error");
				}
			});
		}

	getOfferingList();
	/* function searchActivity(date)
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchDataVisitActivity.action",
			data : "&searchondate="+date,
			success :function(data){
//				alert(JSON.stringify(data));
				$("#activityDate7").html(data.currDate);
				$("#activityDataList6").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
				$(data.dataArray).each(function(index)
				{
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length-1); j++) 
					{
						tdValues[j] = tdValues[j].trim();
						if(j == 0)
						{
							temp = '<td style="cursor:pointer; padding:2px;border-right: 1px solid #e7e9e9; margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;">'+
							'<center><img onclick="takeActionOnClick('+tdValues[tdValues.length-1]+','+tdValues[1]+');" title="Take Action" alt="Take Action" src="images/action.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;<img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
							'<img onclick="patientProfile('+tdValues[tdValues.length-1]+');" title="Patient Profile" alt="Patient Profile" src="images/profile_pic.png" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
	     						  '<img onclick="viewPatientFeedback('+tdValues[tdValues.length-1]+');" title="Patient Questionaire" alt="Patient Questionaire" src="images/WFPM/commonDashboard/fullHistory.jpg" style="width: 15px; height: 18px;"></center></td>';
							$(temp).appendTo(row);
						}
						else if(j == 1)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j].replace("'","").replace("'","")).appendTo(row);
						else if(j == 2)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[8]).appendTo(row);
						
						else if(j == 3)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j-1]).appendTo(row);
						else if(j == 4)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 5)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 6)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 14%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 7)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);
						else if(j == 8)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);


						
						}
				});
				mytable.appendTo("#activityDataList6");
				$("#date").val('');
			},
			error   : function(){
				alert("error");
			}

		});
	}
	 */

	function fetchPatientMissedActivities()
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchMissedVisitActivity.action",
			data : "offeringId="+$("#subofferingname").val(),
			success : function(data){
			//	alert(JSON.stringify(data));
				$("#missedActivityDataList5").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:101%;"></table>').attr({ id: "basicTable2" });
				$(data.dataArray).each(function(index)
				{
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length-1); j++) 
					{
						tdValues[j] = tdValues[j].trim();
						if(j == 0)
						{
							temp = '<td style="cursor:pointer; padding:2px;border-right: 1px solid #e7e9e9; margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;">'+
							'<center><img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
							'<img onclick="patientProfile('+tdValues[tdValues.length-1]+');" title="Patient Profile" alt="Patient Profile" src="images/profile_pic.png" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
	     						  '<img onclick="viewPatientFeedback('+tdValues[tdValues.length-1]+');" title="Patient Questionaire" alt="Patient Questionaire" src="images/WFPM/commonDashboard/fullHistory.jpg" style="width: 15px; height: 18px;"></center></td>';
							$(temp).appendTo(row);
						}
						else if(j == 1)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j].replace("'","").replace("'","")).appendTo(row);
						else if(j == 2)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[8]).appendTo(row);
						
						else if(j == 3)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j-1]).appendTo(row);
						else if(j == 4)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 5)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 6)
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 14%;"></td>').text(tdValues[j+1]).appendTo(row);
						else if(j == 7)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);
						else if(j == 8)
							$('<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 5%;"></td>').text(tdValues[j+2]).appendTo(row);


						
						}
				});
				mytable.appendTo("#missedActivityDataList5");
			},
			error   : function(){
				alert("error");
			}
		});
			


	}
	

	function viewPatientFeedback(id)
	{
		 	// alert("Answers ::::::::::::: ");
			$("#patientFeedbackDataView1").dialog({title: 'Patient Activity History', width:'900', height:'500'});	
		 	$("#patientFeedbackDataView1").dialog("open");
		 	$("#activityfeedbackData2").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/viewAllFeedbackSubmitted41.action",
				    data: "id="+id,
				    success : function(subdeptdata) {
			       		$("#activityfeedbackData2").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
	}
	
	function viewOfferingDetails(offering,id)
	{
		//$('#id'+offering).css('color', 'red');
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchAllDetailsOffering.action?offering="+offering+"&id="+id,
			success : function(data){
				 var mytable ='<table style="margin:0px;padding:0px; width:98%;">';
				mytable+='<tr><td style=" background-color: #EFB8A4; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Activity</b></td><td style=" background-color: #EFB8A4; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Owner</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Status</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Outcome</b></td></tr>';
				var i=0;
				$(data).each(function(index)
				{
					if(i%2==0)
					{
						mytable+='<tr><td style="text-align:center; width: 10%;">'+data[index].activity+'</td><td style="text-align:center; width: 10%;">'+data[index].name+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].scheduledate+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].status+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].outcome+'</td></tr>';
					}	
					else
					{
						mytable+='<tr style=" background-color: #D8DCDE;"><td style="text-align:center; width: 10%;">'+data[index].activity+'</td><td style="text-align:center; width: 10%;">'+data[index].name+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].scheduledate+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].status+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].outcome+'</td></tr>';
					}
					i++;
				});
				mytable+='</table>';
				$("#offeringDetails").html("");
				$("#offeringDetails").html(mytable);
			},
			error   : function(){
				alert("error");
			}
		});
		
	}
	
	
	
	function viewOffering(offering,id)
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchDetailsOffering.action?offering="+offering+"&id="+id,
			success : function(dataAll){
				
				var pdata=dataAll[0];
				var mytable ='<table style="margin:0px;padding:0px; width:98%;"><br><br>';
				var row1='<tr>';
				var row2='<tr>';
				var row3='<tr style="background-color: #D8DCDE;padding:4px;border:1px solid #ccc;height:20px">';
				console.log(pdata);
				$(pdata).each(function(index)
				{
					row3+='<td style="text-align:center; width: 10%;"  >'+pdata[index].stage+'</td>';
					row1+='<td style="text-align:center; width: 10%;"  ><img  src="images/maleicon.jpg" style="width: 13px; height: 13px;"></td>';
					row2+='<td style="text-align:center; width: 10%;">'+pdata[index].visitDate+'</td>';
				});
				var i=0;
				var image=null;
				var data=dataAll[1];
				$(data).each(function(index)
				{
					if(i==0)
					{
						if(data[index].status=='Completed')
						{
							image='<img  src="images/gren.png" style="width: 15px; height: 15px;">';
						}	
						else
						{
							image='<img  src="images/rd.png" style="width: 13px; height: 13px;">';
						}	
					}	
					else
					{
						if(data[index].status=='Completed')
						{
							image='<img  src="images/gren.png" style="width: 15px; height: 15px;">';
						}	
						else
						{
							image='<img  src="images/rd.png" style="width: 13px; height: 13px;">';
						}	
					}
					var hover='<td align="center"><ul class="nav_links" >';
					hover+='<li style="float:none !important;"><a href="#">';
					hover+='<span class="threeLiner" style="margin:0px;" >'+image+'</span>';
					hover+='</a> ';
					hover+='<div class="dropdown profile_dropdown" style="widht:160px !important">';
					hover+='<div class="arrow_dropdown">&nbsp;</div>';
					hover+='<div class="one_column" >';
					hover+='<ul>';
					hover+='<li><a href="#" >';
					hover+='<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span><b>Owner:</b> '+data[index].owner+'</a></li>';
					hover+='<li id="threelinerDataGraph1"><a href="#" >';
					hover+='<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span><b>Remark:</b> '+data[index].comment+'</a></li>';
					hover+='<li id="threelinerDataGraph1"><a href="#" >';
					hover+='<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span><b>Status:</b> '+data[index].status+'</a></li>';
					hover+='<li id="threelinerDataGraph1"><a href="#" >';
					hover+='<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span><b>Completion Date:</b> '+data[index].actionDate+'</a></li>';
					hover+='<li id="threelinerDataGraph1"><a href="#" >';
					hover+='<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span><b>Outcome:</b> '+data[index].outcome+'</a></li>';
					hover+='<li id="threelinerDataGraph1"><a href="#" >';
					hover+='</ul>';
					hover+='</div>';
					hover+='<div class="clear"></div';
					hover+='</div>';
					hover+='</li>';
					hover+='</ul></td>';
					
					row3+='<td style="text-align:center; width: 10%;"  >'+data[index].activity+'</td>';
					row2+='<td style="text-align:center; width: 10%;">'+data[index].date+'</td>';
					console.log(hover);
					row1+=hover;
					hover=null;
					i++;
				});
				row3+='</tr>';
				row2+='</tr>';
				row1+='</tr>';
				mytable+=row3;
				mytable+=row1;
				mytable+=row2;
				mytable+='</table>';
				$("#offeringDetails").html("");
				$("#offeringDetails").html(mytable);
			},
			error   : function(){
				alert("error");
			}
		});
		
	}
	
	
	function activityHistory(id)
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchVisitHistoryOffering.action",
			data : "id="+id,
			success : function(data){
				var temp = '<table style="margin:0px;padding:0px; width:98%;">';
				temp+='<tr><td style=" background-color: #EFB8A4; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Offering</b></td></tr>';
				var oid=null;
				var i=0;
				$(data).each(function(index)
				{
					if(i=='0')
					{
						oid=data[index].ID;
						i++;
					}	
					temp+='<tr><td style=" padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 10%;"><a onclick="viewOffering('+data[index].ID+','+id+')"><u style="color:blueviolet" id="id'+data[index].ID+'">'+data[index].name+'</u></a></td></tr>';
				});
				temp+='</table>';
				$("#offeringNames").html(temp);
				viewOffering(oid,id);
				$("#activityHistory").dialog({title: 'Patient Activity History', width:'1100', height:'500'});
				$("#activityHistory").dialog("open"); 
			},
			error   : function(){
				alert("error");
			}
		}); 
	}
	
	
</script> 
<%-- <script type="text/javascript" src="js/patientActivityjs/patientActivity.js"></script> --%>

<style type="text/css">
	.wrap {
		width: 100%;
	}

	.floatleft {
    	float:left;
    	width: 20%;
    	height: 480px;
	}

	.floatright {
    	width: 80%;
    	float:left;
    	height: 480px;
	}
/* 	td:hover{
 
  -webkit-box-shadow: 0.5px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 0.5px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
} */

</style>
</head>
<body>
<sj:dialog
	id="activityHistory"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
>
	<div class="wrap">
    	<div  class="floatleft" id="offeringNames" style="border: 1"></div>
    	<div class="floatright" id="offeringDetails"></div>
	</div>

</sj:dialog>
<sj:dialog
	id="contactPersonDetailsDialog7"
	autoOpen="false"
	modal="true"
></sj:dialog>

<center>
<br>
<div style="width: 99%;">
	<table width="97%" style="margin:0px;padding:0px; " id="table1">
		<tr style="font-weight: bolder;" >
			<td colspan="12"  style="margin:1px;text-align:center;">
			<div style="float:left;margin-left:25%">
					<s:select 
		                 id="subofferingname"
		                 name="subofferingname" 
		                 list="{'No Data'}"
		                 cssClass="button"
		                 onchange="fetchActivities()"
		                 headerKey="-1"
		                 cssStyle="height:26px;"
		                 headerValue="Select Offering"
		                 theme="simple"
		              >
		          </s:select>
			</div>
			<div style="margin-left: 3%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/backward.jpg" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;  color: black;">	
					Activities As On&nbsp;&nbsp; 
				</div>
				<div id="activityDate7" style="color: black; float: left;"></div>
				<div style="float: left;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="nextDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/forward.jpg" title="Next">
				</div>
			</div>
			<br>
			<div style="margin-top: -1%;margin-left:3%;float:left;">
				<sj:datepicker name="date" id="dateID" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="button" 
						placeholder="Activity On Date" timepicker="false" onchange="fetchActivities();"/>
			</div>
			</td>
		</tr>
		
		<tr style="background-color: grey; color: white; font-weight: bold;">
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 3%;">Sr.</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 7%;">Action.</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Patient Name</td>
						<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Owner Name</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Offering</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Activity</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Next Schedule Date</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 14%;">Comment</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 5%;">TAT</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 5%;">Level</td>
		</tr>
		
		
		
 	</table>

<div id="activityDataList6" style="height: 200px; overflow-y: scroll;"></div>

<br><br>

<!-- Missed Activities -->

<table style="margin:0px;padding:0px; width:97%;">
<tr style="background-color: white; color: black; font-weight: bold;">
	 <td style="padding:2px;margin:1px; text-align:center; width: 4%;" colspan="8">Missed Activities</td>
</tr>
	<tr style="background-color: grey; color: white; font-weight: bold;">
		<%--	 <td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 3%;">Sr.</td>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Patient Name</td>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Relationship Manager</td>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Offering</td>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Activity</td>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Date</td>
	--%>
			<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 3%;">Sr.</td>
				 <td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 7%;">Action.</td> 
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Patient Name</td>
						<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Owner Name</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Offering</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Activity</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Schedule Date & Time</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 14%;">Comment</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 5%;">TAT</td>
					<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 5%;">Level</td>
		</tr>
</table>

<div id="missedActivityDataList5" style="height: 150px; overflow-y: scroll;  overflow-x: hidden;">
</div>
</div>
</center>

<center>
<br>
<sj:dialog
	id="patientFeedbackDataView1"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
<div id="activityfeedbackData2"  style="   margin-left: 4px;
  padding: 0px;
  overflow-y: auto;
  width: 99%;
  position: absolute;"></div>
</sj:dialog>

<sj:dialog
	id="patientActivityHistory4"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
	
<div style="width: 104%;">
	<div id="activityHistoryData3"  style="margin-left: 4px;padding: 0px;overflow-y: auto;width: 97%;"></div>
</div> 
</sj:dialog>

<br><br>

</center>
</body>
<script type="text/javascript">
//fetchPatientActivities();
//fetch today activities
fetchActivities();
//fetch missed activities
fetchPatientMissedActivities();
</script>
</html>
