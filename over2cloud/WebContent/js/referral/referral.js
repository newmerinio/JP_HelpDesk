searchResult('','','','');
function searchResult(param, searchField, searchString, searchOper)
{
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	var referralSubType=$("#referralSubType").val();
	var referralStatus=$("#referralStatus").val();
	var fromdate=$("#from_date").val();
	var todate=$("#to_date").val();
	var fromdate1=$("#from_date1").val();
	var todate1=$("#to_date1").val();
	var data_status=$("#data_status").val();
	var data_status1=$("#data_status1").val();
	var from_source=$("#from_source").val();
	var from_source1=$("#from_source1").val();
	var searchParameter=$("#searchParameter").val();
	var searchParameter1=$("#searchParameter1").val();
	
	//$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralGridVieW.action?referralSubType="+referralSubType+"&referralStatus="+referralStatus+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper+"&fromdate="+fromdate+"&todate="+todate+"&fromdate1="+fromdate1+"&todate1="+todate1+"&from_source="+from_source+"&from_source1="+from_source1+"&searchParameter="+searchParameter+"&searchParameter1="+searchParameter1+"&data_status="+data_status+"&data_status1="+data_status1,
	    success : function(subdeptdata) {
		if(referralSubType=='Individual')
		{
			$("#individual").show();
			$("#institutional").hide();
			fillAlphabeticalLinks();
			$("#"+"datapart").html(subdeptdata);
		}
		else{
			$("#individual").hide();
			$("#institutional").show();
			fillAlphabeticalLinks1();
			$("#"+"datapart1").html(subdeptdata);
		  }
		},
	   error: function() {
	            alert("error");
	        }
	 });
}
function fillAlphabeticalLinks()
{
	param = $("#selectstatus").val();
	var val = "";
	val += '&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'emp_name\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}


function fillAlphabeticalLinks1()
{
	param = $("#selectstatus").val();
	var val = "";
	val += '&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'org_name\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks1").html(val);
	//alert("val:"+val);
}




function getViewAdd(val)
{
	var referralSubType=$("#referralSubType").val();
	var referralStatus=$("#referralStatus").val();
if(referralSubType=='Individual')
{
	searchResult('','','','');
	$("#individual").show();
	$("#institutional").hide();
}
else{
	searchResult('','','','');
	$("#individual").hide();
	$("#institutional").show();
}	
}

function addRefIndivisual()
{
	var referralSubType=$("#referralSubType").val();
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/createIndivisualRef.action?referralSubType="+referralSubType,
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function fetchState(val,targetid){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/fetchStateInfo.action?CommonAddr="+val,
	    success : function(subdeptdata) {
		console.log(subdeptdata);
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select State"));
    	$(subdeptdata).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.state));
		});
	    
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
}

/*function OrganizationProfile(id)
{
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchOrganisationProfile.action",
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
			$("#contactPersonDetailsDialog").dialog();
			$("#contactPersonDetailsDialog").dialog("open");
		},
		error   : function(){
			alert("error");
		}
	});
	
}
*/
function getOrg(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='OrganizationProfile("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function OrganizationProfile(id) 
{
	var org_name = jQuery("#gridBasicDetails3").jqGrid('getCell',id, 'org_name');
	var rating = jQuery("#gridBasicDetails3").jqGrid('getCell',id, 'rating');
	var path="";
	for(var i=1;i<=rating.split("-")[0];i++)
	{
		path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
	}
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/orgBasicDetails.action",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Basic Details For '+org_name+', '+id+'  '+path  , width:'600', height:'250'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function getIndi(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='IndividualProfile("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function IndividualProfile(id) 
{
	var emp_name = jQuery("#gridBasicDetailsReferral").jqGrid('getCell',id, 'emp_name');
	var rating = jQuery("#gridBasicDetailsReferral").jqGrid('getCell',id, 'degreeOfInfluence');
	var path="";
	for(var i=1;i<=rating.split("-")[0];i++)
	{
		path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
	}
	
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/indiBasicDetails.action",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Basic Details For '+emp_name+', '+id+' '+path , width:'600', height:'250'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function getMapContact(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='MapContact("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function MapContact(id) 
{
	//alert(id);
	var emp_name = $("#gridContactDetails1").jqGrid('getCell',id,'emp_name');
	//alert(emp_name);
	var path="";
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/mapContactDetails.action",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Basic Details For '+emp_name+', '+id, width:'700', height:'300'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function getStatus(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='InstiStatus("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function InstiStatus(id) 
{
	var org_name = jQuery("#gridBasicDetails3").jqGrid('getCell',id, 'org_name');
	var rating = jQuery("#gridBasicDetails3").jqGrid('getCell',id, 'rating');
	var path="";
	for(var i=1;i<=rating.split("-")[0];i++)
	{
		path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
	}
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/commstatus.action?tabel=referral_institutional_data",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Status For '+org_name.trim()+', '+id+'  '+path  , width:'700', height:'250'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function getStatus1(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='IndiStatus("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function IndiStatus(id) 
{
	var emp_name = jQuery("#gridBasicDetailsReferral").jqGrid('getCell',id, 'emp_name');
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/commstatus.action?tabel=referral_contact_data",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Status For '+emp_name.trim()+',  '+id , width:'700', height:'400'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}


function fetchCity(val,targetid)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/fetchCityInfo.action?CommonAddr="+val,
	    success : function(subdeptdata) {
		console.log(subdeptdata);
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select City"));
    	$(subdeptdata).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.city));
		});
	    
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
}

var row=0;
$.subscribe('rowselect', function(event, data) 
{
	//row = event.originalEvent.id;
	row  = $("#gridBasicDetails3").jqGrid('getGridParam','selarrrow');
	
});

$.subscribe('rowselect1', function(event, data) 
		{
			//row = event.originalEvent.id;
			row  = $("#gridBasicDetailsReferral").jqGrid('getGridParam','selarrrow');
			
		});


function adhoc()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/viewAdhocCorporateHeader.action?adhoc=Cor",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function action()
{
	var referralSubType=$("#referralSubType").val();
	var referralStatus=$("#referralStatus").val();
	//alert("Hello");
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(row.length>1)
	{
		alert("Please select only one row to edit.");
	}
	else
	{
	if(referralSubType=='Institutional')
	{
		var action_on = jQuery("#gridBasicDetails3").jqGrid('getCell',row, 'action_on');	
		
		if(action_on=='NA' || action_on.length==0)
		{
		var org_name = jQuery("#gridBasicDetails3").jqGrid('getCell',row, 'org_name');
		var rating = jQuery("#gridBasicDetails3").jqGrid('getCell',row, 'rating');
		var path="";
		for(var i=1;i<=rating.split("-")[0];i++)
		{
			path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
		}
		
		$("#contactPersonDetailsDialog").dialog('open');
		$("#contactPersonDetailsDialog").dialog();
		$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeStatusAction.action?tabel=referral_institutional_data&referralSubType="+referralSubType+"&referralStatus="+referralStatus,
			data: "id="+row, 
			success : function(data) {  
     		    $("#contactPersonDetailsDialog").dialog({title: 'Take Action For '+org_name+', '+row+'    '+path  , width:'700', height:'250'});
				$("#contactPersonDetailsDialog").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else{
			alert("Action Has Been Already Taken !!!");
		}
	}
	else{
		
		var action_on = jQuery("#gridBasicDetailsReferral").jqGrid('getCell',row, 'action_on');	
		
		if(action_on=='NA' || action_on.length==0)
		{
		var emp_name = jQuery("#gridBasicDetailsReferral").jqGrid('getCell',row, 'emp_name');
		
		$("#contactPersonDetailsDialog").dialog('open');
		$("#contactPersonDetailsDialog").dialog();
		$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeStatusAction.action?tabel=referral_contact_data&referralSubType="+referralSubType+"&referralStatus="+referralStatus,
			data: "id="+row, 
			success : function(data) {  
     		    $("#contactPersonDetailsDialog").dialog({title: 'Take Action For '+emp_name+', '+row, width:'700', height:'250'});
				$("#contactPersonDetailsDialog").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
		else{
			alert("Action Has Been Already Taken !!!");
		}
	}
	
		
	}
}



var row=0;

$.subscribe('rowselect', function(event, data) 
{
	var referralSubType=$("#referralSubType").val();
	//row = event.originalEvent.id;
	if(referralSubType=='Institutional')
	{
		row  = $("#gridBasicDetails3").jqGrid('getGridParam','selarrrow');	
	}
	else{
		row  = $("#gridBasicDetailsReferral").jqGrid('getGridParam','selarrrow');
	}
});	
function editRow()
{
	var referralSubType=$("#referralSubType").val();
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(row.length>1)
	{
		alert("Please select only one row to edit.");
	}
	else
	{
		if(referralSubType=='Institutional')
		{
		jQuery("#gridBasicDetails3").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
		}
		else{
			jQuery("#gridBasicDetailsReferral").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});	
		}
	}
	
}

function deleteRow()
{
	var referralSubType=$("#referralSubType").val();
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	if(referralSubType=='Institutional')
	{
	   $("#gridBasicDetails3").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
    }
	else{
		$("#gridBasicDetailsReferral").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});	
	}
}
function searchRow()
{
	var referralSubType=$("#referralSubType").val();
	if(referralSubType=='Institutional')
	{
		 $("#gridBasicDetails3").jqGrid('searchGrid', {sopt:['eq','cn']} );
	}
	else{
		$("#gridBasicDetailsReferral").jqGrid('searchGrid', {sopt:['eq','cn']} );
	}
}


function refreshRow()
{
	var referralSubType=$("#referralSubType").val();
	if(referralSubType=='Institutional')
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralView.action?referralSubType=Institutional&referralStatus=Approved",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	var grid = $("#gridBasicDetails3");
	grid.trigger("reloadGrid",[{current:true}]);
	}
	else{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralView.action?referralSubType=Individual&referralStatus=Approved",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		var grid = $("#gridBasicDetailsReferral");
		grid.trigger("reloadGrid",[{current:true}]);
	}
}	
