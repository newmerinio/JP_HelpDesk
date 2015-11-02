function allLevel1Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/wfpm/offering/getallLevel1Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
		document.getElementById("offering2HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}

function allLevel2Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/wfpm/offering/getallLevel2Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
			document.getElementById("offering3HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}

function allLevel3Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/wfpm/offering/getallLevel3Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
			document.getElementById("offering4HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}

function allLevel4Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/wfpm/offering/getallLevel4Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
			document.getElementById("offering5HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}