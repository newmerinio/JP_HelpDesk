function viewIncentiveGrid()
{
	var incentiveType = $("[name='incentiveType']:checked").val();
	if(incentiveType == 0)
	{
		$("#headDynamic").html("KPI");
	}
	else if(incentiveType == 1)
	{
		$("#headDynamic").html("Offering");
	}
	$("#dynamicDataPart").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 8%;'></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/incentive/beforeIncentiveView.action",
		data : "incentiveType="+incentiveType,
		success : function(data) {
		$("#dynamicDataPart").html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function addIncentivePage()
{
	//0 : KPI		
	//1 : OFFERING
	
	var incentiveType = $("[name='incentiveType']:checked").val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/WFPM/incentive/beforeIncentiveAdd.action",
	    data : "incentiveType="+incentiveType,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function back(incenticeType)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/incentive/beforeIncentiveMainView.action",
		data : "incenticeType="+incenticeType,
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("error");
	}
	});
}
function resetFormKPI(formone)
{
	$('#'+formone).trigger("reset");
	$("#kpiincentiveListDiv").html("");
}
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#KpiIncentiveResultOuter").fadeIn(); }, 10);
          setTimeout(function(){ $("#KpiIncentiveResultOuter").fadeOut(); }, 4000);
          resetFormKPI('formone');
});
