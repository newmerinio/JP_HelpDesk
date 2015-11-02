
	function addRelationshipMgr()
     {
		var returntype = "RelMgrAdd";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/addRelationshipMgr.action?",
		    data: "returntype="+returntype,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
     
    function viewRelManager(param)
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
    	$.ajax({
    	    type : "post",
        	url  : "view/Over2Cloud/WFPM/Patient/searchrelMgrdata.action",
        	data : "isExistingClient="+param,
    	    success : function(subdeptdata) {
    	       $("#"+"datapart").html(subdeptdata);
    	},
    	   error: function() {
    	            alert("error");
    	        }
    	 });
    }
    
    
    
    
    