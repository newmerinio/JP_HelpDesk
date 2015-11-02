function viewWellnessReport(param)
{
	searchResult(param,'','','');
}

function searchResult(param, searchField, searchString, searchOper)
{
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	
	var urls="";
	var from_date = $("#from_date").val();
	var to_date = $("#to_date").val();
	/*var uh_id = $("#uh_id").val();
	var mobile = $("#mobile").val();
	var patient_name = $("#patient_name").val();
	*/
	var searchParameter = $("#searchParameter").val();
	var status_view = $("#status_view").val();
	//alert(from_date+" todate "+to_date+" uh_id "+uh_id+" mobile "+mobile+" patient_name "+patient_name);
	$.ajax({
	    type : "post",
    	url  : "view/Over2Cloud/WFPM/Patient/searchwellnessReportdata.action",
    	data : "isExistingClient="+param+"&from_date="+from_date+"&to_date="+to_date+"&searchParameter="+searchParameter+"&status="+status_view,
	    success : function(subdeptdata) {
	       $("#"+"mappingGridId").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function viewQuestionnaire(cellvalue, options, row) 
{
	 return "<a href='#' onclick='viewPatientFeedback("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>"; 
	//return "<a><img title='"+ cellvalue +"' src='images/docDownlaod.jpg' onclick='viewPatientFeedback("+row.id+");'  /></a>";	
}

function viewPatientFeedback(id)
{
	 	// alert("Answers ::::::::::::: ");
	 	var patinet = jQuery("#gridBasicDetails").jqGrid('getCell',id, 'patient_name');
	 	var  uhid = jQuery("#gridBasicDetails").jqGrid('getCell',id, 'uh_id');
		$("#patientFeedbackDataView1").dialog({title: 'Submitted Questionnaire of '+patinet+', UHID '+uhid, width:'900', height:'500'});	
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