function AjexonMonthBoxDetails(MonthBoxDiv,PNoMonth)
{
	var Ekaname=document.getElementById("ekaName").value;
	if(Ekaname==-1)
	{
		alert("Please Select The EKA Name");
	}
	else
	{
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/DreamSol/Incentive/FewMonthOfData.action?PNoMonth="+PNoMonth+"&EKAName="+Ekaname,
		        success : function(LevelDesigValue) 
		        {
		    	$("#"+MonthBoxDiv).html(LevelDesigValue);},
		        error: function()
		        {alert("error");}
		    }
		  );
		
	}
	
}
function Percentage(percentaceDiv,obj)
{	
	
	if(obj=="null")
	{
		alert("5555555555555");
	}
	if(obj==" ")
	{
		alert("666666666666");
	}
	
	
	if(obj!=null || obj!="")
	{
		var Average=document.getElementById("Average").value;
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/DreamSol/Incentive/FindPercentageData.action?percentage="+obj+"&Average="+Average,
		        success : function(LevelDesigValue) 
		        {
		    	$("#"+percentaceDiv).html(LevelDesigValue);},
		        error: function()
		        {alert("error");}
		    }
		  );
		
	}
	

}