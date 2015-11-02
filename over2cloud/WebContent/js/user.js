
function getEmpNameViaAjax(destAjaxDiv,subdeptId,dept)
{
	
	var subdept=document.getElementById(subdeptId).value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/EmpNameAjax.action?subdept="+subdept+"&destination="+destAjaxDiv,
	    success : function(empNamedata) {
        $("#"+destAjaxDiv).html(empNamedata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}


function getEmpNameMobViaAjax(destEmpAjaxDiv,subdeptId) {
	 var subdept=$("#"+subdeptId).val();
	 $
				.ajax( {
					type :"post",
					url :"/sfa/view/admin_pages/common_admin/EmpNameAjax.action?destination="
							+ destEmpAjaxDiv+"&subDept="+subdept,
					success : function(empNamedata) {
						$("#" + destEmpAjaxDiv).html(empNamedata);

					},
					error : function() {
						alert("error");
					}
				});
	}
	
	
function getEmpDataViaAjax(empId)
{
	var emp=$("#"+empId).val();
	$.getJSON("/sfa/view/admin_pages/common_admin/EmpDataAjax.action?empId="+emp,
			function(empData){
		$("#emailid").val(empData.emailIdOne);
            	$("#mobileno").val(empData.mobOne);
            	$("#name").val(empData.empName);
            
        });
	
    
}

function getAvailableUserViaAjax(userId)
{
	var user=document.getElementById(userId).value;
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/admin_pages/user_admin/userAvailabilty.action?user="+user,
		function(userdata){
    	     $("#userStatus").html(userdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	document.getElementById("status").value=userdata.status;
        
    });
    
}

