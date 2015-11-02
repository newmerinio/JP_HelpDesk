function AjexonProductTargetBoxDetails(ProductBoxDiv,PNoMonth)
{
	var Ekaname=document.getElementById("ekaName").value;
	var productCategory=document.getElementById("PCatagory").value;
	if(Ekaname==-1 || productCategory==-1)
	{
		alert("Please Select The EAK Name And Product Category");
	}
	else
	{
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/DreamSol/Incentive/FewProductOfData.action?PNoMonth="+PNoMonth+"&eKAName="+Ekaname+"&pCategory="+productCategory,
		        success : function(LevelDesigValue) 
		        {
		    	$("#"+ProductBoxDiv).html(LevelDesigValue);},
		        error: function()
		        {alert("error");}
		    }
		  );
		
	}
}
function Percentage(percentaceDiv,obj)
{	
	if(obj=="")
	{
		
	}
	else
	{
		var Average=document.getElementById("Average").value;
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/DreamSol/Incentive/FindPercentageProductData.action?percentage="+obj+"&Average="+Average,
		        success : function(LevelDesigValue) 
		        {
		    	$("#"+percentaceDiv).html(LevelDesigValue);},
		        error: function()
		        {alert("error");}
		    }
		  );
	}
}