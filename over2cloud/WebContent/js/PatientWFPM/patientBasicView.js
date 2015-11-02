var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function takeAction(cellvalue, options, row) 
{
	return "<a href='#'><img title='Take Action' src='images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+row.id+")'> </a>";
}

function takeActionOnClick(row) 
{
	/*var status = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'patient_status');
	if(status=='1')
	{*/
		if(row!='undefined' && row!=0){
		var first_name = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'first_name');
		first_name =first_name+" "+ jQuery("#gridBasicDetails").jqGrid('getCell',row, 'last_name');	
		first_name=$(first_name).text();
		var crm_id = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'crm_id');
		var mobile=jQuery("#gridBasicDetails").jqGrid('getCell',row, 'mobile');
		var type=jQuery("#gridBasicDetails").jqGrid('getCell',row, 'pType');
		var stage = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'stage');
		var offering = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'service');
		//alert(stage);
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforePatientActivity.action?",
		    data: "rowid="+row+"&first_name="+first_name+"&crm_id="+crm_id+"&mobile="+mobile+"&patient_type="+type+"&stage="+stage+"&offering="+offering,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata).css('overflow','auto');
		},
		   error: function() {
	            alert("error");
	        }
		 });
		}else{
			alert("Select only one record from grid !!");
		}

	
/*	}	
	else
	{
		var returntype = "PatientAdd";
		//var rowid = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
		//alert("rowid  "+row);
		if(row!='undefined' && row!=0){
		var first_name = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'first_name');
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforePatientVisit.action?",
		    data: "rowid="+row+"&first_name="+first_name,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		}else{
			alert("Select only one record from grid !!");
		}

	}	*/
}
		
function addPatientBasic()
     {
	
	//if(row==0){
			var returntype = "PatientAdd";
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/WFPM/Patient/patientBasicAdd.action?",
				data: "returntype="+returntype,
				success : function(subdeptdata) {
				$("#"+"data_part").html(subdeptdata);
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
			val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'first_name\',\''
					+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		$("#alphabeticalLinks").html(val);
		//alert("val:"+val);
	}

function viewPatientBasicDetail(param)
{
	searchResult(param,'','','');
}

function searchResult(param, searchField, searchString, searchOper)
{
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	//var patien_id = $("#patien_id").val();
	var searchParameter = $("#searchParameter").val();
	var gender = $("#gender").val();
	var patient_category = $("#patient_category").val();
	//var status_view = $("#status_view").val();
	//var mobile = $("#mobile").val();
	var type = $("#type").val();
	var patient_type = $("#patient_type").val();
	
	var sent_questionair = $("#sent_questionair").val();
	var status_patient=  $("#status_patient").val();
	var urls="";
	$.ajax({
	    type : "post",
    	url  : "view/Over2Cloud/WFPM/Patient/searchpatientBasicdata.action",
    	data : "sent_questionair="+sent_questionair+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper+"&searchParameter="+searchParameter+"&patient_category="+patient_category+"&gender="+gender+"&status_patient="+status_patient+"&type="+type+"&patient_type="+patient_type,

	    success : function(subdeptdata) {
	       $("#"+"datapart").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}


function patientVisitAction()
{
	var returntype = "PatientAdd";
	//var rowid = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
	//alert("rowid  "+row);
	if(row!='undefined' && row!=0){
	var first_name = jQuery("#gridBasicDetails").jqGrid('getCell',row, 'first_name');
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/beforePatientVisit.action?",
	    data: "rowid="+row+"&first_name="+first_name,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	}else{
		alert("Select only one record from grid !!");
	}
}

function confirmAction(val){
	$("#"+val).dialog('open');
}

function closeDialog(val){
$("#"+val).dialog('close');
}

function resendAction()
{
var returntype = "PatientAdd";
var rowid = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
//alert("rowid  "+row);
if(rowid==0)
{
//$("#dialogData").val("Please select atleast one record from grid.");
alert("Please select atleast one record from grid.");
}
else if(rowid.length>1)
{
//$("#dialogData").val("Please select only one record from grid.");
alert("Please select only one record from grid.");
}
else{
var first_name = jQuery("#gridBasicDetails").jqGrid('getCell',rowid, 'first_name');
//$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
//alert(rowid + ' sending '+first_name);
$.ajax({
type :"post",
url : "view/Over2Cloud/WFPM/Patient/resendQuestions.action?",
data: "id="+rowid+"&first_name="+first_name,
success : function(subdeptdata) {
$("#dialogData").val("Message send succesfully.");
//$("#"+"data_part").html(subdeptdata);
},
error: function() {
    //alert("error");
    $("#dialogData").val("Message is not send succesfully.");
}
});
}
closeDialog('resendConfirmDialog');
searchResult('0','','','');
}



	function viewPatientReport()
	{
		
		var acManager="";
		$("#data_part").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientActivity/beforepatientreportView.action?modifyFlag=0&deleteFlag=0&acManager="+acManager,
		    success : function(subdeptdata) {
				$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	
	function editRow()
	{ 
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
			jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:460, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
		}
	}

	function deleteRow()
	{
		if(row==0)
		{
			alert("Please select atleast one row.");
		}
		$("#gridBasicDetails").jqGrid('delGridRow',row, {height:100,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		 $("#gridBasicDetails").jqGrid('searchGrid', {sopt:['eq','cn']} );
	}
	function refreshRow(rowid, result) 
	{
		$("#gridBasicDetails").trigger("reloadGrid"); 
	}

	
	function showPatientDetails(cellvalue, options, row) 
	{
	    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewPatientDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
	}

	function viewPatientDetails(id) 
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/patientBasicDetails.action",
			data: "id="+id, 
			success : function(data) {  
				$("#patientDataId").dialog('open');
				$("#patientDataId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$("#patientDataId").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	
	function sentStatus(cellvalue, options, row) 
	{
		if(cellvalue ==='Sent'){
		return "<a><img title='"+ cellvalue +"' src='images/check.jpg' style='width: 20px; height: 20px;' );'  onClick='viewSentDetails("+row.id+");' /></a>";
		}else{
			return "<a><img title='"+ cellvalue +"' src='images/noDoc.jpg' style='width: 20px; height: 20px;'  );'  /></a>";
		}
	}
	
	function viewSentDetails(id) 
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/questionSentDetails.action",
			data: "id="+id, 
			success : function(data) {  
				$("#sentDetailId").dialog('open');
				$("#sentDetailId").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$("#sentDetailId").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	
	function dashboardPatientActivity()
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/patientActivityDashboard.action?",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });	
	}