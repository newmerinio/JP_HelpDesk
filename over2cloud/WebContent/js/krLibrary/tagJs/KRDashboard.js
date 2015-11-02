function getOnClickDataForKRScore(status,dataFor) 
{
	alert("Status ::::  >>>>>>>>>"+status);
	alert("Data For:::: >>>>>>>>>"+dataFor);
	$("#result_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeFullViewDashboard.action?status="+status.split(" ").join("%20")+"&dataFor="+dataFor,
	    success : function(data) 
	    {
	    	$("#KrDashboardDialog").dialog({title: 'KR Score Details'});
	    	$("#KrDashboardDialog").dialog('open');
			$("#"+"result_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function getOnClickDataForKRTScore()
{
	$("#result_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeFullViewDashboardTotal.action",
	    success : function(data) 
	    {
	    	$("#KrDashboardDialog").dialog({title: 'KR Score Details'});
	    	$("#KrDashboardDialog").dialog('open');
			$("#"+"result_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}