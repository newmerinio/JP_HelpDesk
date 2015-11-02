function MapIndustry()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/industry/beforeIndustryMap.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function assignWeightage(subindustryID) {
	var offeringId=$("#subofferingname").val();
	var subIndustry=$("#subIndustry").val();
	if(subindustryID != "-1" && subindustryID != null)
	{
		$("#mappedWeightageWithDept").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforMapWeightage.action?subindustryID="+subIndustry+"&offeringId="+offeringId,
		    success : function(subdeptdata) {
		   
	       $("#"+"mappedWeightageWithDept").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
//1 sep 2014
/*function fetchDepartmentToMap()
{
	var offeringId=$("#subofferingname").val();
	var subIndustry=$("#subIndustry").val();
	 $("#subIndustryId").val(subIndustry);
	//alert("subindustryId="+subIndustry);
	//alert("offeringId="+offeringId);
	if(subIndustry != "-1" && subIndustry != null)
	{
		$("#mappedWeightageWithDept").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforMapWeightage.action?subindustryID="+subIndustry+"&offeringId="+offeringId,
		    success : function(subdeptdata) {
		   
	       $("#"+"mappedWeightageWithDept").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	 
}*/
function fetchDepartmentToMap()
{
	
	var offeringId=$("#subofferingname").val();
	var targetSegment=$("#targetSegment").val();
	 $("#targetSegmentID").val(targetSegment);
	if(targetSegment != "-1" && targetSegment != null)
    	{
		$("#mappedWeightageWithDept").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforMapWeightage.action?targetSegmentID="+targetSegment+"&offeringId="+offeringId,
		    success : function(subdeptdata) {
		   
	       $("#"+"mappedWeightageWithDept").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	 
}

function fetchLevelData(val,selectId,Offeringlevel)
{
	if(Offeringlevel == "1")
		$("#verticalnameId").val(val.value);
	if(Offeringlevel == "2")
		$("#offeringnameId").val(val.value);
	if(Offeringlevel == "3")
		$("#subofferingnameId").val(val.value);
	if(Offeringlevel == "4")
		$("#variantnameId").val(val.value);
	if(Offeringlevel == "5")
		$("#subvariantsizeId").val(val.value);
	
	
	var offeringLevel=$("#offeringLevel").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/industry/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel+"&offeringLevel="+offeringLevel,
	    success : function(data) {
		    
		$('#'+selectId+' option').remove();
		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
    	$(data).each(function(index)
		{
    		//alert(this.ID +" "+ this.NAME);
		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		
	},
	   error: function() {
            alert("error");
        }
	 });
}

function fetchLevelDataForSuboffering(val,selectedId,Offeringlevel)
{  
	if(Offeringlevel == "1")
		$("#verticalnameId").val(val.value);
	if(Offeringlevel == "2")
		$("#offeringnameId").val(val.value);
	if(Offeringlevel == "3")
		$("#subofferingnameId").val(val.value);
	if(Offeringlevel == "4")
		$("#variantnameId").val(val.value);
	if(Offeringlevel == "5")
		$("#subvariantsizeId").val(val.value);
	
	var offeringLevel=$("#offeringLevel").val();
	var offering=$("#offeringname").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/industry/fetchOfferingLevelDataforsubOffering.action?offeringId="+offering+"&Offeringlevel="+Offeringlevel+"&offeringLevel="+offeringLevel,
	    success : function(datapart) {
		            $("#"+selectedId).html(datapart);
		},
		
	   error: function() {
             alert("error");
            }
	   });
}
function fetchLevelDataForoffering(val,selectedId,Offeringlevel)
{  
	if(Offeringlevel == "1")
		$("#verticalnameId").val(val.value);
	if(Offeringlevel == "2")
		$("#offeringnameId").val(val.value);
	if(Offeringlevel == "3")
		$("#subofferingnameId").val(val.value);
	if(Offeringlevel == "4")
		$("#variantnameId").val(val.value);
	if(Offeringlevel == "5")
		$("#subvariantsizeId").val(val.value);

	var offeringLevel=$("#offeringLevel").val();
	var verticalname = $("#verticalname").val();
	
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/industry/fetchOfferingLevelDataforOffering.action?offeringId="+verticalname+"&Offeringlevel="+Offeringlevel+"&offeringLevel="+offeringLevel,
	    success : function(datapart) {
		            $("#"+selectedId).html(datapart);
		},
		
	   error: function() {
             alert("error");
            }
	   });
}

function fillName(val,divId,param)
{
	var offeringId=$("#offeringId").val();
	//alert("offeringId="+offeringId);
	/*
	param is either 1 or 0
	1: for fetching name on both key and value place
	0: for fetching id on key and name on value place
	*/
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	var id = val;
	if(id == null || id == '-1')
	return;

    // alert("id>>>>>>>"+id);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/industry/fetchsubIndustryName.action",
	data: "id="+id+"&param="+param+"&offeringId="+offeringId, 
	success : function(data) {   
	$('#'+divId+' option').remove();
	$('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
	$(data).each(function(index)
	{
	   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
            alert("error");
        }
	 });
}





