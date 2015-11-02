function addRelationshipMgr2()
	{
		var returntype = "RelMgrAdd";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/addDocOffering.action?",
		    data: "returntype="+returntype,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

function viewMapDoctorOffering(param)
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
    	url  : "view/Over2Cloud/WFPM/Patient/searchMapDoctorOffdata.action",
    	data : "isExistingClient="+param,
	    success : function(subdeptdata) {
	       $("#"+"mappingGridId").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
	function backToDocOfferingMapView()
	{$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/beforeDoctorOfferingMap.jsp",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });}