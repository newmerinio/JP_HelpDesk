function getheadoffice(country,div){
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/Location/getHeadOfficeList.action?countryId="+country,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Head Office'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getbranchoffice(country,div)
{
	//alert(country);
	//alert(div);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/Location/getBranchOfficeList.action?countryId="+country,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Branch Office'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}