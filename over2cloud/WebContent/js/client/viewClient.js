function formatImage(cellvalue, options, row) 
{
	
    cellvalue="Search";
    return '&nbsp<a href="#"><img title="Mail and SMS"  src="images/mail-message-new.png" onClick="sendmailandsms('+row.id+');" /></a>'+
    '&nbsp&nbsp<a href="#"><img title="Full View" src="images/WFPM/fullView.jpg" onClick="openDialogFullHistory('+row.id+');" /></a>&nbsp'+
    '&nbsp&nbsp<a href="#"><img title="Create Activity" src="images/WFPM/commonDashboard/action.jpg" onClick="createActivity('+row.id+',\''+row.clientName+'\');" /></a>&nbsp';
}

function createActivity(id, clientName)
{
	$("#data_part").html("");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/beforeContactTakeAction.action?id="+id+"&isExistingClient="+isExistingClient+"&mainHeaderName="+tempHeaderName+"&clientName="+clientName));
}

function formatLink(cellvalue, options, rowObject) 
{
	return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; 
}
 
function sendmailandsms(id) 
{
    var emailidtwo= "services@dreamsol.biz";
	//var emailid = jQuery("#gridBasicDetails").jqGrid('getCell',id, 'emailOfficial');
	//alert("    id>"+id);
	var subject= "Information";
	var clientType = $("#selectstatus").val();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Lead/leadmailandsms.action?id="+id+"&subject="+subject+"&emailidtwo="+emailidtwo+"&mainHeaderName="+tempHeaderName+"&isExistingClient="+clientType));
} 

function openDialogFullHistory(id) 
{
	//alert("isExistingClient:"+isExistingClient);
	var clientName=id;
	$("#data_part").html("");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&isExistingClient="+isExistingClient+"&mainHeaderName="+tempHeaderName));
}

function contactTakeactionLink(cellvalue, options, row) 
{
	return "&nbsp<a href='#referPage'><img title='Create Activity' src='images/WFPM/commonDashboard/action.jpg' onClick='openDialogForContactTakeaction("+row.id+");' /></a>&nbsp";
}
function openDialogForContactTakeaction(id) 
{
	$("#data_part").html("");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/beforeContactTakeAction.action?id="+id+"&isExistingClient="+isExistingClient+"&mainHeaderName="+tempHeaderName));
}

function addProspectiveClient()
{
	var returntype = "ClientAdd";
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/beforeClientAdd.action?returntype="+returntype,
	    data: "isExistingClient="+isExistingClient,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function excelUpload()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientExcelUpload.action",
	    //data : "tableName=client_basic_data&mappedTableName=mapped_client_configuration&masterTableName=client_basic_configuration&level=client",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function searchshowclientdata(param)
{
	changeHeader(param);
	isExistingClient = param;
	searchResult(param,'','','');
}

function searchResult(param, searchField, searchString, searchOper)
{
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	var urls="";
	var industry = $("#industryID").val();
	var subIndustry = $("#subIndustryID").val();
	var starRating = $("#starRating").val();
	var sourceName = $("#sourceName").val();
	var location = $("#locationID").val();
	var clientstatus = $("#clientStatusId").val(); 
	var client_Name = $("#clientNameId").val(); 
	var opportunity_name = $("#opportunitynameId").val(); 
	var acManager = $("#acManagerId").val();
	var salesstages = $("#salesstages").val();
	var forecastCategary=$("#foreCastCategaryID").val();
	var fromDateSearch=$("#fromDateSearch").val();
	 
	
	$.ajax({
	    type : "post",
    	url  : "view/Over2Cloud/wfpm/client/searchshowclientdata.action",
    	data : "isExistingClient="+param+"&industry="+industry+"&subIndustry="+subIndustry+"&starRating="
	    		+starRating+"&sourceName="+sourceName+"&location="+location+"&clientstatus="+clientstatus+"&client_Name="+client_Name
	    		+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper+"&opportunity_name="+opportunity_name+"&acManager="+acManager+"&salesstages="+salesstages+"&forecastCategary="+forecastCategary+"&fromDateSearch="+fromDateSearch,
	    success : function(subdeptdata) {
	       $("#"+"datapart").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function totalRecord(totalData)
	{
		
	var totalCount=$("#"+totalData).val();
	$("#totalCount").html(totalCount);
	
	}

var tempHeaderName;
function changeHeader(param)
{
	if(param==0)
	{
		tempHeaderName = 'View Prospective Client';
		$("#Cid").html(tempHeaderName);
	}
	else if(param==1)
	{
		tempHeaderName = 'View Existing Client';
		$("#Cid").html(tempHeaderName);
	}
	else if(param==2)
	{
		tempHeaderName = 'View Lost Client';
		$("#Cid").html(tempHeaderName);
	}
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
	var returntype = "ClientandContact";
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/beforeClientEdit.action",
	    data: "id="+row+"&returntype="+returntype,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	//jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:70,modal:true});
}

function deleteRow()
{
	row = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
	$("#gridBasicDetails").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}
function searchRow()
{
	jQuery("#gridBasicDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:-228,modal:true} );
}
function refreshRow(rowid, result) 
{
	$("#gridBasicDetails").trigger("reloadGrid"); 
}


function fillClientTypeDD(a,b,c,divId)
{
	$('#'+divId+' option').remove();
	if(a == "1")
	{
		$('#'+divId).append($("<option></option>").attr("value","0").text("Prospective Client"));
	}
	if(b == "1")
	{
		$('#'+divId).append($("<option></option>").attr("value","1").text("Existing Client"));
	}
	if(c == "1")
	{
		$('#'+divId).append($("<option></option>").attr("value","2").text("Lost Client"));
	}
}
 
function fillName(val,divId)
{
	var id = val;
	if(id != null && id != '-1')
	{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/fetchSubIndustry.action",
		data: "id="+id, 
		success : function(data) {  
			data = data.jsonArray;
			if(data != null){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Sub-Industry'));
				$(data).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			}
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	//fetch records in grid
	searchResult('','','','');
}

function fillAlphabeticalLinks()
{
	param = $("#selectstatus").val();
	var val = "";
	val += '&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'clientName\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}

//viewWeightage
function viewWeightage(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewWeightageDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewWeightageDetails(id) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/viewWeightageDetails.action",
		data: "id="+id, 
		success : function(data) {  
			$("#downloadWeightageDetails").dialog('open');
			$("#downloadWeightageDetails").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$("#downloadWeightageDetails").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function fillStatus()
{
	$("#clientStatus").val($("#status option:selected").text().trim());
}

//counter 

function count1(data, data1, data2){
	/*alert("data "+data);
	alert("data1 "+data1);*/
	var industry = $("#industryID").val();
	var subIndustry = $("#subIndustryID").val();
	var starRating = $("#starRating").val();
	var sourceName = $("#sourceName").val();
	var location = $("#locationID").val();
	var clientstatus = $("#clientStatusId").val(); 
	var client_Name = $("#clientNameId").val(); 
	var opportunity_name = $("#opportunitynameId").val(); 
	var acManager = $("#acManagerId").val();
	var salesstages = $("#salesstages").val();
	var forecastCategary=$("#foreCastCategaryID").val();
	var fromDateSearch=$("#fromDateSearch").val();
	var selectstatus=$("#selectstatus").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/counter.action?indr="+industry+"&subindr="+subIndustry+"&rate="+starRating+
	    "&sourceN="+sourceName+"&locatn="+location+"&clintSts="+clientstatus+"&clintName="+client_Name+"&acmng="+acManager+
	    "&salesstag="+salesstages+"&forcstcat="+forecastCategary+"&frmdateSear="+fromDateSearch,
	    success : function(feeddraft) {
	    	
	    	$("#totalCount").hide();
	    	    $("#totalCount1").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
	function mapOppValueToClient()
	{
		if(row == 0)
		{
			alert('Please Select Any Row First !');
			return;
		}
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/beforemapOppvalToClient.action",
		    data: "id="+row,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
