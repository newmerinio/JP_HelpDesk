	function searchshowMailreportdata()
	{
		var param = 0;
		searchResult(param,'','','');
	}
	
	function searchResult(param, searchField, searchString, searchOper)
	{
		if(param == null || param == '')
		{
			//param = $("#selectstatus").val();
		}
		var urls="";
		//var fromDateSearch=$("#fromDateSearch").val();
		 
		
		$.ajax({
		    type : "post",
	    	url  : "view/Over2Cloud/WFPM/report/DSR/beforsearchcrmmailreport.action",
	    	data : "isExistingClient="+param,
		    success : function(subdeptdata) {
		       $("#"+"maildatapart").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}