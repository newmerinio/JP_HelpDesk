//Anoop 30-1-2014

//Variable 'monthCounter' will be used for calculating date for which records have to be fetched
//var monthCounter = 0;

function openContactDetails(id, activityType, name)
{
	// alert(id+"=================="+activityType);
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchContactDetails.action",
		data : "id="+id+"&temp="+activityType,
		success : function(data){
			// alert("============"+data.jsonObject.DOI);
			var tableDiv = '<table style="margin:0px;padding:0px; width:98%;">';
			if(data.jsonObject != null && data.jsonObject.DOI > 0)
			{
				tableDiv += '<tr><td style="margin:1px;text-align:center; width: 18%;" colspan="4">';
				for(var i=0; i<data.jsonObject.DOI; i++)
				{
					tableDiv += '<img alt="star" src="images/WFPM/commonDashboard/star.jpg">&nbsp;';
				}
				tableDiv += '</td></tr>';
			}
			tableDiv += '<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Designation</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Department</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Mobile No.</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Email ID</b></td></tr>'+
			'<tr><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;" >'+data.jsonObject.DESIGNATION+'</td><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;">'+data.jsonObject.DEPARTMENT+'</td><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;">'+data.jsonObject.CONTACT_NO+'</td><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;">'+data.jsonObject.EMAIL+'</td></tr>'+
			'</table>';
			$("#contactPersonDetailsDialog").html(tableDiv);
			$("#contactPersonDetailsDialog").dialog({title: name, width:'600', height:'180'});
			$("#contactPersonDetailsDialog").dialog("open");
		},
		error   : function(){
			alert("error");
		}
	});
}

function openOrgDetails(id, activityType, name)
{
	//alert(id+"=================="+activityType);
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchOrgnizationDetails.action",
		data : "id="+id+"&temp="+activityType,
		success : function(data){
			//alert("============"+data.jsonObject.STAR_RATING);
			var tableDiv = '<table style="margin:0px;padding:0px; width:98%;">';
			if(data.jsonObject != null && data.jsonObject.STAR_RATING > 0)
			{
				tableDiv += '<tr><td style="text-align:center; width: 18%;" colspan="4">';
				for(var i=0; i<data.jsonObject.STAR_RATING; i++)
				{
					tableDiv += '<img alt="star" src="images/WFPM/commonDashboard/star.jpg">&nbsp;';
				}
				tableDiv += '</td></tr>';
			}
			tableDiv += '<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Industry/Sub-Industry</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Address</b></td> <td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Website</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Contact No.</b></td></tr>'+
			'<tr><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;" >'+data.jsonObject.INDUSTRY+'/'+data.jsonObject.SUB_INDUSTRY+'</td>  <td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;">'+data.jsonObject.ADDRESS+'</td><td style="cursor:pointer; background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><a href="http://'+data.jsonObject.WEB_ADDRESS+'">'+data.jsonObject.WEB_ADDRESS+'</a></td><td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;">'+data.jsonObject.CONTACT_NO+'</td></tr>'+
			'</table>';
			$("#contactPersonDetailsDialog").html(tableDiv);
			$("#contactPersonDetailsDialog").dialog({title: name, width:'700', height:'180'});
			$("#contactPersonDetailsDialog").dialog("open");
		},
		error   : function(){
			alert("error");
		}
	});
}

function activityHistory(id)
{
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchActivityHistory.action",
		data : "id="+id,
		success : function(data){
			var mytable = $('<table style="margin:0px;padding:0px; width:98%;"></table>').attr({ id: "basicTable1" });
			$('<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Activity</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Compelling Reasons</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Comment</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td></tr>').appendTo(mytable);
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
			$("#contactPersonDetailsDialog").dialog({title: 'Activity History', width:'900', height:'500'});
			$("#contactPersonDetailsDialog").dialog("open");
		},
		error   : function(){
			alert("error");
		}
	});
	
}

var offeringList = null;
function openFullHistory(id, type, orgnization)
{
	$("#data_part").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/beforeFullHistory.action",
		data : "id="+id+"&temp="+type+"&currDate="+monthCounter+"&orgnization="+orgnization,
		success : function(data){
			$("#data_part").html(data);
		},
		error   : function(){
			alert("error");
		}
	});
}

function takeAction(id,type)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/beforeDashboardTakeActon.action",
		data : "id="+id+"&currDate="+monthCounter+"&temp="+type,
		success : function(data){
			$("#data_part").html(data);
		},
		error   : function(){
			alert("error");
		}
	});
}

function cancel()
{
	$("#MOMDialog").dialog('close');
}

function fetchActivities()
{
	var searchondate = "";
	currDate = monthCounter;
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchDashboardActivityList.action",
		data : "currDate="+currDate+"&searchondate="+searchondate,
		success : function(data){
		//	alert(JSON.stringify(data));
			$("#activityDate").html(data.currDate);
			$("#activityDataList").html("");
			var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
			$(data.jsonArray).each(function(index)
			{
			//	alert(this.VALUE);
				var temp = "";
				var tdValues = this.VALUE.split(",");
				var row = $('<tr></tr>').appendTo(mytable);
				temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
				$(temp).appendTo(row);
				for (var j = 0; j < (tdValues.length-1); j++) 
				{
					tdValues[j] = tdValues[j].trim();
					if(j == 0)
					{
						temp = '<td style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;">'+
						       '<center><img onclick="openFullHistory('+tdValues[j]+',\''+tdValues[3]+'\',\''+tdValues[2]+'\');" title="Full History" alt="Full History" src="images/WFPM/commonDashboard/fullHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
						       '<img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
						       '<img onclick="takeAction('+tdValues[j]+',\''+tdValues[3]+'\');" title="Take Action" alt="Take Action" src="images/WFPM/commonDashboard/action.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
     						   '<img onclick="fullView(\''+tdValues[j]+'\',\''+tdValues[9]+'\',\''+tdValues[3]+'\');" title="Full View" alt="Full View" src="images/WFPM/fullView.jpg" style="width: 15px; height: 18px;"></center></td>';
						// alert(temp);
						$(temp).appendTo(row);
					}
					else if(j == 1)
						$('<td class="example" onclick="openContactDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9; text-align:center; width: 10%;"></td>').text(tdValues[j]).appendTo(row);
					else if(j == 2)
						$('<td onclick="openOrgDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
					else if(j == 3)
						$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;"></td>').text(tdValues[j] == "0" ? "Client" : "Associate" ).appendTo(row);
					else if(j == (tdValues.length - 2))
						$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 25%;"></td>').text(tdValues[j]).appendTo(row);
					else
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

/****************************************************************************************************/

function fetchMissedActivities()
{
	//missedActivityDataList
	$.ajax({
		type:"post",
		url :"view/Over2Cloud/wfpm/dashboard/fetchMissedActivities.action",
		success:function(data){
		$("#missedActivityDataList").html(data);
	},
		error:function(){
		alert("error!");
	}
	});
}

function fullView(uid,id, type)
{
	//alert("ID IS >>>>>>>>>>"+id+"              Type Is >>>>>>>>>>>>>>"+type);

	//alert("monthCounter:"+monthCounter);
	if(type == '0')
	{
		$("#data_part").html("");
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#data_part").load("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&uid="+uid+"&isExistingClient=0&mainHeaderName=Client&currDate="+monthCounter);
	}
	else if(type == '33')
	{
		$("#data_part").html("");
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&uid="+uid+"&isExistingClient=0"+type+"&mainHeaderName=Associate&currDate="+monthCounter));
	}else{	
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$("#data_part").load("view/Over2Cloud/WFPM/Associate/viewAssociateFullHistory.action?id="+id+"&isExistingAssociate=2&mainHeaderName=Associate&currDate="+monthCounter+"&uid="+uid);
	}
}

function searchActivity(date)
	{
		var searchondate = "ActivityByDate";
		currDate = date
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/fetchDashboardActivityList.action",
			data : "currDate="+currDate+"&searchondate="+searchondate,
			success : function(data){
				$("#activityDate").html(data.currDate);
				$("#activityDataList").html("");
				var mytable = $('<table style="margin-left:16px;padding:0px; width:97.8%;"></table>').attr({ id: "basicTable" });
				$(data.jsonArray).each(function(index)
				{
					//alert(this.VALUE);
					var temp = "";
					var tdValues = this.VALUE.split(",");
					var row = $('<tr></tr>').appendTo(mytable);
					temp = '<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">'+(index+1)+'</td>';
					$(temp).appendTo(row);
					for (var j = 0; j < (tdValues.length - 1); j++) 
					{
						tdValues[j] = tdValues[j].trim();
					//	alert(tdValues[j]);
						if(j == 0)
						{
							//alert("searchactivities tdValues[j] "+tdValues[7]+"  tdValues[3] "+tdValues[3]);
							temp = '<td style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 8%;">'+
							       '<center><img onclick="openFullHistory('+tdValues[j]+',\''+tdValues[3]+'\',\''+tdValues[2]+'\');" title="Full History" alt="Full History" src="images/WFPM/commonDashboard/fullHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
							       '<img onclick="activityHistory('+tdValues[j]+');" title="Activity History" alt="Activity History" src="images/WFPM/commonDashboard/activityHistory.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
							       '<img onclick="takeAction('+tdValues[j]+',\''+tdValues[3]+'\');" title="Take Action" alt="Take Action" src="images/WFPM/commonDashboard/action.jpg" style="width: 15px; height: 18px;">&nbsp;&nbsp;'+
	     						   '<img onclick="fullView(\''+tdValues[j]+'\',\''+tdValues[9]+'\',\''+tdValues[3]+'\');" title="Full View" alt="Take Action" src="images/WFPM/fullView.jpg" style="width: 15px; height: 18px;"></center></td>';
							//alert(temp);
							$(temp).appendTo(row);
						}
						else if(j == 1)
							$('<td class="example" onclick="openContactDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9; text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
						else if(j == 2)
							$('<td onclick="openOrgDetails(\''+tdValues[0]+'\',\''+tdValues[3]+'\',\''+tdValues[j]+'\');" style="cursor:pointer; padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 25%;"></td>').text(tdValues[j]).appendTo(row);
						else if(j == 3)
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 7%;"></td>').text(tdValues[j] == "0" ? "Client" : "Associate" ).appendTo(row);
						else if(j == (tdValues.length - 2))
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;"></td>').text(tdValues[j]).appendTo(row);
						else
							$('<td style="padding:2px;margin:1px;border-bottom:1px solid #e7e9e9;text-align:center; width: 18%;"></td>').text(tdValues[j]).appendTo(row);
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