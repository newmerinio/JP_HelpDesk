
function getEmpNameViaAjax(destAjaxDiv,subdeptId,dept)
{
	
	var subdept=document.getElementById(subdeptId).value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/EmpNameAjax.action?dept="+dept+"&subdept="+subdept+"&destination="+destAjaxDiv,
	    success : function(empNamedata) {
        $("#"+destAjaxDiv).html(empNamedata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}
	

function getEmpDataViaAjax(dataId)
{
	var empId=document.getElementById("empName"+dataId).value;
	$.getJSON("/sfa/view/admin_pages/common_admin/EmpDataAjax.action?empId="+empId,
			function(data){
            $.each(data,function(key, valueofKey){
            	document.getElementById("empEmail"+dataId).value=valueofKey.emailidone;
                document.getElementById("empMob"+dataId).value=valueofKey.mobone;
            	
            });
        });
	
    
}


function getSubIndustryViaAjax(destAjaxDiv,industry)
{
	var indusrtyId=$("#"+industry).val();
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/SubIndustryAjax.action?indusrtyId="+indusrtyId,
	    success : function(subIndustryData) {
		$("#"+destAjaxDiv).html(subIndustryData);
       
	},
	   error: function() {
            alert("error");
        }
	 });
    
}


function getAvailableCompanyViaAjax(company)
{
	var companyName=$("#"+company).val();
	
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/admin_pages/company_admin/companyAvailabilty.action?companyName="+companyName,
		function(companydata){
    	     $("#companyStatus").html(companydata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	document.getElementById("status").value=companydata.status;
        
    });
    
}


function checkSame(create_company_form,flag) {

	if(flag=='head')
	{
		flag="c";
	}
	
	if(create_company_form.sameCheck.checked){
		
		$("#"+flag+"ompanyOprAddress").val($("#"+flag+"ompanyRegAddress").val());
		$("#"+flag+"ompanyOprCountry").val($("#"+flag+"ompanyRegCountry").val());
		$("#"+flag+"ompanyOprCity").val($("#"+flag+"ompanyRegCity").val());
		$("#"+flag+"ompanyOprPinCode").val($("#"+flag+"ompanyRegPinCode").val());
		$("#"+flag+"ompanyOprFax").val($("#"+flag+"ompanyRegFax").val());
		$("#"+flag+"ompanyOprContact1").val($("#"+flag+"ompanyRegContact1").val());
		$("#"+flag+"ompanyOprContact2").val($("#"+flag+"ompanyRegContact2").val());
		$("#"+flag+"ompanyOprWeb").val($("#"+flag+"ompanyRegWeb").val());
		$("#"+flag+"ompanyOprEmail").val($("#"+flag+"ompanyRegEmail").val());
				
			}

	else {
		$("#"+flag+"ompanyOprAddress").val('');
		$("#"+flag+"ompanyOprCountry").val('');
		$("#"+flag+"ompanyOprCity").val('');
		$("#"+flag+"ompanyOprPinCode").val('');
		$("#"+flag+"ompanyOprFax").val('');
		$("#"+flag+"ompanyOprContact1").val('');
		$("#"+flag+"ompanyOprContact2").val('');
		$("#"+flag+"ompanyOprWeb").val('');
		$("#"+flag+"ompanyOprEmail").val('');
		
	}
	}


function getCompanyViaAjax()
{
    $.ajax({
    type : "post",
    url : "/sfa/view/admin_pages/company_admin/CompanyAjax.action",
    success : function(companyData) {
	$("#AjaxIndustryDiv").html(companyData);
},
   error: function() {
        alert("error");
    }
 });
	
	
    
}



function checkLoc(orgId) {
	
var orgName=$("#"+orgId).val();
	
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/admin_pages/company_admin/companyLoc.action?orgName="+orgName,
		function(locCompanyData){
    	 $("#locCompanyName").val(locCompanyData.companyName);
    	 $("#locCompanyReg").val(locCompanyData.companyReg);
 		 $("#locCompanyPanCard").val(locCompanyData.companyPanCard);
 		 $("#locCompanyTin").val(locCompanyData.companyTin);
 		 $("#locCompanySrvcTax").val(locCompanyData.companySrvcTax);
 		 $("#locCompanySin").val(locCompanyData.companySin);
 		 $("#locCompanyOtherNo").val(locCompanyData.companyOtherNo);
 		
 		$("#locCompanyRegAddress").val(locCompanyData.companyOprAddress);
		$("#locCompanyRegCountry").val(locCompanyData.companyRegCountry);
		$("#locCompanyRegCity").val(locCompanyData.companyRegCity);
		$("#locCompanyRegPinCode").val(locCompanyData.companyRegPinCode);
		$("#locCompanyRegFax").val(locCompanyData.companyRegFax);
		$("#locCompanyRegContact1").val(locCompanyData.companyRegContact1);
		$("#locCompanyRegContact2").val(locCompanyData.companyRegContact2);
		$("#locCompanyRegWeb").val(locCompanyData.companyRegWeb);
		$("#locCompanyRegEmail").val(locCompanyData.companyRegEmail);
   	 
	    $("#locCompanyOprAddress").val(locCompanyData.companyOprAddress);
		$("#locCompanyOprCountry").val(locCompanyData.companyOprCountry);
	    $("#locCompanyOprCity").val(locCompanyData.companyOprCity);
		$("#locCompanyOprPinCode").val(locCompanyData.companyOprPinCode);
		$("#locCompanyOprFax").val(locCompanyData.companyOprFax);
		$("#locCompanyOprContact1").val(locCompanyData.companyOprContact1);
		$("#locCompanyOprContact2").val(locCompanyData.companyOprContact2);
		$("#locCompanyOprWeb").val(locCompanyData.companyOprWeb);
		$("#locCompanyOprEmail").val(locCompanyData.companyOprEmail);
	   	 
		$("#locCompanyBankAc").val(locCompanyData.companyBankAc);
		$("#locCompanyBankName").val(locCompanyData.companyBankName);
		$("#locCompanyBankBranch").val(locCompanyData.companyBankBranch);
		$("#locCompanyBankTransactionNo").val(locCompanyData.companyBankTransactionNo);
		$("#locCompanyBankContact").val(locCompanyData.companyBankContact);
				
		   	 
		    	 
	    	 	 
    	 
    	 
    	 
    });

}



function getCompanyLocationViaAjax(countryId)
{
	var country=$("#"+countryId).val();
    $.ajax({
    type : "post",
    url : "/sfa/view/admin_pages/company_admin/CompanyLocationAjax.action?country="+country,
    success : function(companyLocationData) {
	$("#AjaxLocationDiv").html(companyLocationData);
},
   error: function() {
        alert("error");
    }
 });
	
	
    
}


function getAvailableCompanyLocationViaAjax(companyLocId)
{
	var companyLoc=$("#"+companyLocId).val();
	
	document.getElementById("indicator3").style.display="block";
     $.getJSON("/sfa/view/admin_pages/company_admin/companyLocationAvailabilty.action?locCompany="+companyLoc,
		function(companyLocationdata){
    	     $("#companyLocationStatus").html(companyLocationdata.msg);
    	     document.getElementById("indicator3").style.display="none";
        	document.getElementById("locationStatus").value=companyLocationdata.status;
        
    });
    
}


function getAvailableCompanyBranchViaAjax(branchAddressId,indicatorId,branchStatusId,companyBranchStatus)
{
	var branchAddress=$("#"+branchAddressId).val();
	
	document.getElementById(indicatorId).style.display="block";
     $.getJSON("/sfa/view/admin_pages/company_admin/companyBranchAvailabilty.action?branchAddress="+branchAddress,
		function(companyBranchdata){
    	     $("#"+companyBranchStatus).html(companyBranchdata.msg);
    	     document.getElementById(indicatorId).style.display="none";
        	document.getElementById(branchStatusId).value=companyBranchdata.status;
        
    });
    
}
