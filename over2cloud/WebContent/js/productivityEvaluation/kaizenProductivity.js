function getPlantValue(ogValue,div)
{
	var value=$("#"+ogValue).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getPlantName.action?ogValue="+value,
	    success : function(data) {
	    	$('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Plant'));
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function addNewData(moduleName)
{
	var module=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getKaizenPage.action?moduleName="+module,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewkaizen(moduleName)
{
	var module=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/viewKaizenPage.action?moduleName="+module,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getReviewNames(value,div)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getReviewNames.action?ogValue="+value,
	    success : function(data) 
	    {
	    	$('#reviewName'+div+' option').remove();
			$('#reviewName'+div).append($("<option></option>").attr("value",-1).text('Select Review Name'));
			$(data).each(function(index)
			{
			   $('#reviewName'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	   error: function() {
	        alert("error");
	    }
	 });
}
function takeActionKaizen(module)
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	var moduleName=$("#"+module).val();
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	$("#actionDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeTakeAction.action?moduleName="+moduleName+"&kaizenId="+valuepassed,
	    success : function(data) 
	    {
		    $("#kaizenAction").dialog('open');
	        $("#"+"actionDataDiv").html(data);
	    },
	    error: function() {
            alert("error");
        }
	 });
	   return false;
}
function addNewDataImproved(module)
{
	var moduleName=$("#"+module).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getImprovementPage.action?moduleName="+moduleName,
	    success : function(data) 
	    {
	    	$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function viewImproved(moduleName)
{
	var module=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getImprovementPageView.action?moduleName="+module,
	    success : function(data) 
	    {
	    	$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function takeActionImproved(module)
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	var moduleName=$("#"+module).val();
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	$("#actionDataDiv1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeTakeAction.action?moduleName="+moduleName+"&kaizenId="+valuepassed,
	    success : function(data) 
	    {
		    $("#improvedAction").dialog('open');
	        $("#"+"actionDataDiv1").html(data);
	    },
	    error: function() {
            alert("error");
        }
	 });
	   return false;
}
var indexVal=1;
function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
  indexVal++;
  $("#indexVal").val(indexVal);
}
function deletediv(divID)
{
	document.getElementById(divID).style.display="none";
	indexVal--;
	$("#indexVal").val(indexVal);
}

function getDueDatePicker(value,divId)
{
	if (value=='D') 
	{
		$("#"+divId).hide();
	} 
	else 
	{
		$("#"+divId).show();
	}
}
function reminderFreeze(value,divId)
{
	if (value=='D') 
	{
		$( "#"+divId).prop("disabled", true);
	} 
	else 
	{
		$( "#"+divId).prop("disabled", false);
	}
}
function addNewDataCMO(moduleName)
{
	var module=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(module=='Special Project' || module=='Specific Assignments' || module=='Monitoring Assignments')
    {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getCMOCommunicationFielsSpecial.action?moduleName="+module,
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
    }
	else
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getCMOCommunicationFiels.action?moduleName="+module,
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function viewCmoData(moduleName)
{
	var module=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if (module=='Special Project' || module=='Specific Assignments' || module=='Monitoring Assignments') 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getCMOCommunicationFielsSpecialView.action?moduleName="+module,
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/getCMOCommunicationFielsView.action?moduleName="+module,
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function takeActionCMOSpecial(moduleName)
{
	var module=$("#"+moduleName).val();
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	var dueDate=jQuery("#gridedittable").jqGrid('getCell',valuepassed,'dueDate');
	var dueTime=jQuery("#gridedittable").jqGrid('getCell',valuepassed,'dueTime');
	$("#actionDataDivCMOAction").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeTakeActionSpecial.action?moduleName="+module+"&specialId="+valuepassed+"&dueDate="+dueDate+"&dueTime="+dueTime,
	    success : function(data) 
	    {
		    $("#CMOAction").dialog('open');
	        $("#"+"actionDataDivCMOAction").html(data);
	    },
	    error: function() {
            alert("error");
        }
	 });
}