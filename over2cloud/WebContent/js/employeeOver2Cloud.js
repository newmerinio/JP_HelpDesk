function checkMobnoExistViaAjax(mobno)
{
	 var mobile=document.getElementById(mobno).value;
	 document.getElementById("indicator2").style.display="block";
	 $.getJSON("/cloudapp/view/Over2Cloud/hr/checkMobile.action?mobOne="+mobile,
     //$.getJSON("/cloudapp/view/hradmin_pages/employee_admin/checkMobile.action?mobone="+mobile,
		function(empdata){
    	     $("#empStatus").html(empdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=empdata.status;
    });
    
}

function getAllEmpList(AjaxDivpTemp)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/hr/getEmplDataList.action",
	    success : function(companyData) {
		$("#"+AjaxDivpTemp).html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function fillMob(valueTemp)
{
	document.formTwo.mobNoPerTemp.value=valueTemp;
}

var flag="0";
function checkStatus()
{
	if(flag=="0")
	{
		flag="1";
		document.getElementById('workExprncDiv').style.display='block';
		
	}
	else
	{
		flag="0";
		document.getElementById('workExprncDiv').style.display='none';
	}
	
}