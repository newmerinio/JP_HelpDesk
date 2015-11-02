
	function addPatientStatus()
     {
		var returntype = "ClientAdd";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeStatusAdd.action?",
		    data: "returntype="+returntype,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
     
    function viewPatientStatus(param)
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
        	url  : "view/Over2Cloud/WFPM/Patient/searchpatientStatusdata.action",
        	data : "isExistingClient="+param,
    	    success : function(subdeptdata) {
    	       $("#"+"datapart").html(subdeptdata);
    	},
    	   error: function() {
    	            alert("error");
    	        }
    	 });
    }
    
    function fetchLevelData(val,selectId,Offeringlevel)
    {
    	
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/wfpm/client/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
    	    success : function(data) {
    		    
    		$('#'+selectId+' option').remove();
    		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
        	$(data).each(function(index)
    		{
        		//  alert(this.ID +" "+ this.NAME);
    		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
    		});
    		
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    
    
    
    