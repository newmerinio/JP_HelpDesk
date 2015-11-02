var maxDivId1=null;
var maxDivId2=null;
var maxDivId3=null;




function showData(id) {

$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	
	if(id=='jqxChart')
	{
		restoreBoard("prevPie","nextPie","jqxChart");
		maxDivId1='1stTable';
		
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action",
		success : function(statusdata) 
		{   console.log("statusdata");
			console.log(statusdata);
			$("#"+id).html(statusdata);
		},
		error : function() {
			alert("error");
		}
	});
 }else if(id=='levelChart')
	{
	 restoreBoard("prevPie1","nextPie1","levelChart");
	 maxDivId2='2ndTable';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterLevel.action",
			success : function(statusdata) 
			{
				$("#"+id).html(statusdata);
			},
			error : function() {
				alert("error");
			}
		});
	 }else if(id=='CategChart')
		{
		 maxDivId3='3rdTable';
			$.ajax( {
				type :"post",
				url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterCateg.action",
				success : function(statusdata) 
				{
					$("#"+id).html(statusdata);
				},
				error : function() {
					alert("error");
				}
			});
		 }
	
}//show data end 


