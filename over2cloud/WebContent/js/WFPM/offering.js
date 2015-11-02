function allLevel1Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/offering/getallLevel1Offering.action",
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
	    url : "view/Over2Cloud/wfpm/offering/getallLevel2Offering.action",
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
	    url : "view/Over2Cloud/wfpm/offering/getallLevel3Offering.action",
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
	    url : "view/Over2Cloud/wfpm/offering/getallLevel4Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
			document.getElementById("offering5HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}

function allLevel5Offering(divID,hideOther)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/offering/getallLevel5Offering.action",
	    success : function(companyData) {
		$("#"+divID).html(companyData);
	},
	   error: function() {
			document.getElementById("offering5HideShow").style.display="none";
	        alert("No Data!!");
	    }
	 });
}

function allLevelOffering(divID,hideOther,level)
{
	//alert("level:"+level);
	if(level == 1)
		allLevel1Offering(divID,hideOther);
	else if(level == 2)
		allLevel2Offering(divID,hideOther);
	else if(level == 3)
		allLevel3Offering(divID,hideOther);
	else if(level == 4)
		allLevel4Offering(divID,hideOther);
	else if(level == 5)
		allLevel5Offering(divID,hideOther);
}

function fillLastLevel()
{
	allLevelOffering('levelAllOffering','offeringAllHideShow',$("#offeringLevelId").val());
}

