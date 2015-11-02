function takeActionForPateintPreventive()
	{
		var rowid = $("#gridClientSupportDetails").jqGrid('getGridParam','selarrrow');
	    if(rowid.length==0)
	    {  
	     alert("please select atleast one row....");
	     return false;
	    }
	    else if(rowid.length > 1)
	    {
	       alert("please select only one row....");
	       return false;
	   }
	    var clientName = jQuery("#gridClientSupportDetails").jqGrid('getCell',rowid, 'clientName');
	    var client_id = jQuery("#gridClientSupportDetails").jqGrid('getCell',rowid, 'client_id');
	    var tempClient = clientName.split(">");
		var client = tempClient[1].split("<");
		
		var offeringlevel3 = jQuery("#gridClientSupportDetails").jqGrid('getCell',rowid, 'offeringlevel3');
		var tempOffering = offeringlevel3.split(">");
		var offeringName = tempOffering[1].split("<");
		var isExistingClient='0';
	    $("#data_part").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeTakeActionPreventive.action?modifyFlag=0&deleteFlag=0&id="+rowid+"&clientName="+client[0]+"&isExistingClient="+isExistingClient+"&clientId="+client_id,
		    success : function(subdeptdata) {
				$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


