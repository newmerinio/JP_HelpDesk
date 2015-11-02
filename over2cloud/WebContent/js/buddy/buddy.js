var empFlag;

function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType,ftf,fcf,fscf,ef) 
{
	empFlag=ef;
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') 
	{
	    if (subdeptType=='1') 
		{
			$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
				success : function(subDeptData){
				$("#" + destAjaxDiv).html(subDeptData);
			    },
			    error : function () {
					alert("Somthing is wrong to get Sub Department");
				}
			});
		}
	    else if (subdeptType=='2') 
	    {
	    	$.ajax({
	    		type :"post",
	    		url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/BySubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
	    		success : function(subDeptData){
	    		$("#" + destAjaxDiv).html(subDeptData);
	    	    },
	    	    error : function () {
	    			alert("Somthing is wrong to get Sub Department");
	    		}
	    	});
		}
	    else if (subdeptType=='3') 
	    {
			$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/ToSubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
				success : function(subDeptData){
				$("#" + destAjaxDiv).html(subDeptData);
			    },
			    error : function () {
					alert("Somthing is wrong to get Sub Department");
				}
			});
	    }
	}
	else if(deptLevel=='1')
	{
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/EmpData.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+deptId,
			success : function(empData){
			$("#" + destAjaxDiv).html(empData);
		    },
		    error : function () {
				alert("Somthing is wrong to get Employee Data");
			}
		});
		
	}
}

function getEmpData(subDeptId,destAjaxDiv) {
	if (empFlag=='1') {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/EmpData.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId,
		success : function(empData){
		$("#" + destAjaxDiv).html(empData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Employee Data");
		}
	});
	}
}
function getFloorInfo(destAjaxDiv) 
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/floorData.action",
		success : function(empData){
		$("#" + destAjaxDiv).html(empData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Employee Data");
		}
	});
	
	
}
function createBuddy() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/beforeAddBuddy.action",
	    success : function(data) {
       		$("#"+"data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function viewPage() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/beforeViewBuddy.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function importBuddy() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/buddyExcelUpload.jsp");	
}

function getCurrent(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/BuddyOver2Cloud/BuddySetting/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName+"&dialogId="+dialogId,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
