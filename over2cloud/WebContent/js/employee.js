
function getEmpDataViaAjax(dataId)
{
       if(dataId=='empName')
       {
    	   var empId = document.getElementById("empName").value;
    	   $.getJSON("/sfa/view/admin_pages/common_admin/EmpDataAjax.action?empId="+empId,
    		function (data) {
    		   $("#mobOne").val(data.mobOne);
			   $("#empId").val(data.empid);
			   $("#emailId").val(data.emailIdOne);
			}	   
    	   );
       }
       else
       {
       var empId = document.getElementById("empName" + dataId).value;
       $.getJSON("/sfa/view/admin_pages/common_admin/EmpDataAjax.action?empId="+ empId, function(data){
               $("#mobNo" + dataId).val(data.mobOne);
       });

}

}

function getEmpNameViaAjax(destAjaxDiv) {
	     
     if(destAjaxDiv=='AjaxDivempNameW')
     {
    	$.ajax( {
			type :"post",
			url :"/sfa/view/hradmin_pages/employee_admin/EmpHistoryNameAjax.action?destination="
					+ destAjaxDiv,
			success : function(empNamedata) {
				$("#" + destAjaxDiv).html(empNamedata);

			},
			error : function() {
				alert("error");
			}
		});
     }
     else{
	    $.ajax( {
				type :"post",
				url :"/sfa/view/hradmin_pages/employee_admin/EmpNameAjax.action?destination="
						+ destAjaxDiv,
				success : function(empNamedata) {
					$("#" + destAjaxDiv).html(empNamedata);

				},
				error : function() {
					alert("error");
				}
			});
}
}

function getAvailableEmpViaAjax(emp)
{
	var mobile=document.getElementById(emp).value;
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/hradmin_pages/employee_admin/empAvailabilty.action?mobone="+mobile,
		function(empdata){
    	     $("#empStatus").html(empdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=empdata.status;
    });
    
}

function getEmpNameMobViaAjax(destEmpAjaxDiv,subdeptId) {
	 var subdept=$("#"+subdeptId).val();
	 $
				.ajax( {
					type :"post",
					url :"/sfa/view/admin_pages/common_admin/EmpNameAjax.action?destination="+ destEmpAjaxDiv+"&subDept="+subdept,
					success : function(empNamedata) {
						$("#" + destEmpAjaxDiv).html(empNamedata);

					},
					error : function() {
						alert("error");
					}
				});
	}
	
function checkMobnoExistViaAjax(mobno)
{
	var mobile=document.getElementById(mobno).value;
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/hradmin_pages/employee_admin/checkMobile.action?mobone="+mobile,
		function(empdata){
    	     $("#empStatus").html(empdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=empdata.status;
    });
    
}

//Method for getting the designation detail on the basis of different parameters
function getEmpDesgnationViaAjax(destDesgAjaxDiv,officeTypeId,comId)
	 {
	 	var officeType=$("#"+officeTypeId).val();
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/desEmpViaAjax.action?office="+officeType,
	 				    success : function(desgnationData) {
	 			        $("#"+destDesgAjaxDiv).html(desgnationData);
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 }

function validateSalary()
{
	if(document.empBasic.salary.value.length>0)
	{
		var a =document.empBasic.salary.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errorSalary.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values.</div>";
				var len = (document.empBasic.salary.value.length) - 1;
				document.empBasic.salary.value = (document.empBasic.salary.value).substring(0, len);
				document.empBasic.salary.focus();
				event.originalEvent.empBasic.submit = false;
			}
			else
			{
				errorSalary.innerHTML="";
			}
		}
	}
}
function validateEmployeeName()
{
	if(document.empBasic.empName.value.length>0)
	{
		var a =document.empBasic.empName.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errName.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.empBasic.empName.value.length) - 1;
				document.empBasic.empName.value = (document.empBasic.empName.value).substring(0, len);
				document.empBasic.empName.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errName.innerHTML="";
			}
		}
	}
}
function validateEmployeeComName()
{
	if(document.empBasic.comName.value.length>0)
	{
		var a =document.empBasic.comName.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errorEMPComName.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.empBasic.comName.value.length) - 1;
				document.empBasic.comName.value = (document.empBasic.comName.value).substring(0, len);
				document.empBasic.comName.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errorEMPComName.innerHTML="";
			}
		}
	}
}
function validadeMobileNO()
{
	if(document.empBasic.mobOne.value.length>0)
	{
		var a =document.empBasic.mobOne.value;
		var iChars = "0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				if(a.charAt(0)=='9' || a.charAt(0)=='8' || a.charAt(0)=='7')
				{
					errorMobBasc1.innerHTML="";
					document.empBasic.mobOne.focus();
				}
				else
				{
					errorMobBasc1.innerHTML="<div class='user_form_inputError2'>Invalid Contact No</div>";
					var len = (document.empBasic.mobOne.value.length) - 1;
					document.empBasic.mobOne.value = (document.empBasic.mobOne.value).substring(0, len);
					document.empBasic.mobOne.focus();
				}
			}
			else
			{
				errorMobBasc1.innerHTML="<div class='user_form_inputError2'>Contact No Should be Numeric.</div>";
				var lent = (document.empBasic.mobOne.value.length) - 1;
				document.empBasic.mobOne.value = (document.empBasic.mobOne.value).substring(0, lent);
				document.empBasic.mobOne.focus();
				event.originalEvent.options.submit = false;
			}
		}
	}
}







