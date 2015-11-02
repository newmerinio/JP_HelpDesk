function getOnClickDataOfOGTask(moduleName,dataFor) 
{
	$("#productivityDialog").dialog('open');
	$("#result_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/viewOGTaskDash.action?moduleName="+moduleName+"&dataFor="+dataFor,
	    success : function(data) {
       $("#"+"result_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}