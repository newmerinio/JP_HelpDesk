function getDeptViaAjax(deptAjaxDiv)
	{
		$.ajax({
		    type : "post",
		    url : "/sfa/view/admin_pages/common_admin/DeptAjax.action?",
		    success : function(deptdata) {
	        $("#"+deptAjaxDiv).html(deptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}



function getSubDeptViaAjax(sourceAjaxDiv,destAjaxDiv,deptId)
{
	
	//alert(document.getElementById(deptId).value+"dept");
	
	var dept=document.getElementById(deptId).value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/SubDeptAjax.action?dept="+dept+"&destination="+destAjaxDiv+"&source="+sourceAjaxDiv,
	    success : function(subdeptdata) {
        $("#"+sourceAjaxDiv).html(subdeptdata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
	
}



function getCompanyViaAjax(destAjaxDiv)
{
	
	
	
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/CompanyNameAjax.action",
	    success : function(companyData) {
        $("#"+destAjaxDiv).html(companyData);
       
	},
	   error: function() {
            alert("error");
        }
	 });
    
}

	