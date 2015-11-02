function takeActionOnOpportunity()
	{
		var rowid = $("#gridOpportunityDetails").jqGrid('getGridParam','selarrrow');
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
		$("#data_part").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/beforeTakeActionOpportunity.action?modifyFlag=0&deleteFlag=0&id="+rowid,
		    success : function(subdeptdata) {
				$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function actionOnOpportunity()
	{
		var opportunityType = $("#opportunityTypeId").val();
		var id = $("#id").val();
		var clientName = $("#clientName").val();
		var offeringId = $("#offeringId").val();
		var takeActionId = $("#takeActionId").val();
		//alert("takeActionId "+takeActionId+" opportunityType  "+opportunityType);
		//alert("id   "+id+ "  clientName  "+clientName+" offeringId  "+offeringId+" opportunityType  "+opportunityType);

		$.ajax({
		    type : "post",
	    	url  : "view/Over2Cloud/wfpm/client/beforeTakeActiononOpportunity.action?",
	    	data : "id="+id+"&clientName="+clientName+"&offeringId="+offeringId+"&opportunityType="+opportunityType,
		    success : function(subdeptdata) {
		       $("#"+"actiondatadivId").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		
	}

function viewOpportunityReport()
	{
		
		var acManager="";
		$("#data_part").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/beforeopportunityreportView.action?modifyFlag=0&deleteFlag=0&acManager="+acManager,
		    success : function(subdeptdata) {
				$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function backToOpportunityBoard()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/beforeopportunityDetails.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}
function searchshowOpportunityData()
{
	var paramval=0;
	searchResult(paramval,'','','');
}

function searchResult(param, searchField, searchString, searchOper)
{
	var urls="";
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	var fromDate = $("#fromDate").val();
	var fromDateSearch = $("#fromDateSearchID").val();
	var searchParameter=$("#searchParameter").val();
	var client_Name = $("#opportunityNameId option:selected").val();
	var acManager = $("#acManagerId").val();
	var offeringOpportunity = $("#offeringId").val();
	var opportunityType = $("#opportunityTypeId").val();
	
	// var salesstageName = $("#salesstageNameId option:selected").text();
	var expectency = $("#expectencyId option:selected").text();
	
 	$.ajax({
	    type : "post",
    	url  : "view/Over2Cloud/wfpm/client/searchshowOpportunitydata.action?",
    	data : "status="+param+"&client_Name="+client_Name+"&fromDateSearch="+fromDateSearch+"&acManager="+acManager+"&offeringOpportunity="+offeringOpportunity+"&fromDate="+fromDate+"&opportunityType="+opportunityType+"&expectency="+expectency+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper+"&searchParameter="+searchParameter,
	    success : function(subdeptdata) {
	       $("#"+"opportunitydatapart").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row == 0)
	{
		alert('Please Select Any Row First !');
		return;
	}
	
	jQuery("#gridOpportunityDetails").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:70,modal:true});
}

function deleteRow()
{
	if(row == 0)
	{
		alert('Please Select Any Row First !');
		return;
	}
	row = $("#gridOpportunityDetails").jqGrid('getGridParam','selarrrow');
	$("#gridOpportunityDetails").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}
function searchRow()
{
	jQuery("#gridOpportunityDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:-228,modal:true} );
}
function refreshRow(rowid, result) 
{
	$("#gridOpportunityDetails").trigger("reloadGrid"); 
}

function clickClientRecord(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewClientDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewClientDetails(id) 
{
	var clientName = jQuery("#gridOpportunityDetails").jqGrid('getCell',id, 'clientName');
	var tempdata = clientName.split(">");
	var substring = tempdata[1].split("<");
	var path="";
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/clientStar.action",
		data: "id="+id, 
		success : function(count) 
		{  
			for(var i=1;i<=count;i++)
			{
				path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
			}
		},
		   error: function() {
	            alert("error");
	        }
		 });
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/viewClientDetails.action",
		data: "id="+id, 
		success : function(data) { 
		$("#workOneId").dialog({title:''+substring[0]+" "+path,width: 800,height: 150});
		$("#workOneId").dialog('open');
		$("#workOneId").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function clickOfferingRecord(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewOfferingDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewOfferingDetails(id) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/viewOfferingDetails.action",
		data: "id="+id, 
		success : function(data) {  
			$("#workTwoId").dialog('open');
			$("#workTwoId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$("#workTwoId").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function clientAccountMgrRec(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewAccMgrDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewAccMgrDetails(id) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/viewAccMgrDetails.action",
		data: "id="+id, 
		success : function(data) {  
			$("#workThreeId").dialog('open');
			$("#workThreeId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$("#workThreeId").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
	function oppHistoryRecord(cellvalue, options, row) 
	{
	    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewHistoryRecord("+row.id+");'>"+cellvalue+"</a>&nbsp;";
	}
	
	function viewHistoryRecord(id) 
	{
		var clientName = jQuery("#gridOpportunityDetails").jqGrid('getCell',id, 'clientName');
		var tempdata = clientName.split(">");
		var substring = tempdata[1].split("<");
		
		var offeringlevel3 = jQuery("#gridOpportunityDetails").jqGrid('getCell',id, 'offeringId');
		var tempdata = offeringlevel3.split(">");
		var offeringName = tempdata[1].split("<");
		
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/viewHistoryRecord.action",
			data: "id="+id, 
			success : function(data) { 
			$("#workEightId").dialog({title:'Activity For '+offeringName[0]+" "+substring[0],width: 1000,height: 600});
			$("#workEightId").dialog('open');
			$("#workEightId").html(data);
			
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	function currentStagesRecord(cellvalue, options, row) 
	{
	    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewCurrentStagesRecord("+row.id+");'>"+cellvalue+"</a>&nbsp;";
	}

	function viewCurrentStagesRecord(id) 
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/viewCurrentStagesRecord.action",
			data: "id="+id, 
			success : function(data) {  
				$("#workSevenId").dialog('open');
				$("#workSevenId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$("#workSevenId").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	function expectedOppValRecord(cellvalue, options, row) 
	{
	    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewExpectedOppValRecord("+row.id+");'>"+cellvalue+"</a>&nbsp;";
	}

	function viewExpectedOppValRecord(id) 
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/viewExpectedOppValRecord.action",
			data: "id="+id, 
			success : function(data) {  
				$("#workSixId").dialog('open');
				$("#workSixId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$("#workSixId").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	
	function downloadExcel()
	{
	 	var id = jQuery("#gridOpportunityDetails").jqGrid('getGridParam', 'selarrrow');
	 	var downloadactionurl="downloadreport";
	 	if(id.length==0 )
	 	 	{
	 	 		alert("Please select atleast one check box...");
	 	 	}
	 	else{
	 		$("#downloaddaildetails").load("view/Over2Cloud/wfpm/excel/exceldownload.action?downloadactionurl="+downloadactionurl+"&id="+id);
	 	    $("#downloaddaildetails").dialog('open');
	 	}
	 } 
	
	function getDoc(cellvalue, options, rowObject) {
		
		if(cellvalue !=="NA" && cellvalue.length != 0)
			{
			return "<a href='view/Over2Cloud/wfpm/client/docdownload.action?id="+rowObject.id+"'><img title='"+ cellvalue +"' src='images/docDownlaod.jpg' /></a>";	
			}
		else{
			return "<font color='blue'>NA</font>";
		}
	}
	
	

	function fillAlphabeticalLinks()
	{
		param = $("#selectstatus").val();
		var val = "";
		val += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		for(var i=65; i<=90; i++)
		{
			val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'clientName\',\''
					+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		$("#alphabeticalLinks").html(val);
		//alert("val:"+val);
	}
	fillAlphabeticalLinks();

	