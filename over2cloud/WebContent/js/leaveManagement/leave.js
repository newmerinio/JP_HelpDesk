function getContactdata(deptId,divId) 
{	
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/contactData.action",
	data: "deptId="+deptId, 

	success : function(data) 
	{  
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


function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv,empname,fdate,tdate)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName+"&empname="+empname+"&fdate="+fdate+"&tdate="+tdate,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getCurrentColumnss(downloadType,moduleName,dialogId,dataDiv,modifyFlag)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/currentColumnss.action?downloadType="+downloadType+"&download4="+moduleName+"&modifyFlag="+modifyFlag,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function createRequest() {

		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeLeaveRequestCreate.action?flafSeleced=1&empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function viewRequest() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function pendingRequest() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeRequestInResponseView.action?actionStatus=Pending",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addHoliday() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeHolidayList.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}
function importHoliday() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/leaveManagement/holidayExcelUpload.jsp");	
}

function viewHoliday() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeHolidayListView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function uploadPolicy() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeLeavePolicy.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}

function viewDownload() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeLeavePolicyView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function addLeaveType() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveTypeAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewLeavetype() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveTypeView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function addLeaveConfig() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeConfiAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewLeaveConfig() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeConfiView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addTimeSlot() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeTimeSlot.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewTimeSlot() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeTimeSlotView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}
function importAttendance() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/leaveManagement/attendanceExcelUpload.jsp");	

}
function getEmployeeReport(deptId,divId) 
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/employyeeReport.action",
	data: "deptId="+deptId, 
	success : function(data) 
	{ 
		
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




function viewEmployeeType()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeEmployeeTypeView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

}


function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}



function addAttendance() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeAttendanceMark.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}


function viewAttendanceMark() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/viewreportAddAction.action?modifyFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}



function addEmployeeType() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeEmployeeType.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}



function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType) 
{
	
	if (deptLevel=='2') {
    if (subdeptType=='1') {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/leaveManagement/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
		success : function(subDeptData){
		$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
    }
	}
}
function getEmployeeType(subDeptId,destAjaxDiv) 
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/leaveManagement/empTypeAjax.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId,
		success : function(empTypeData){
		$("#" + destAjaxDiv).html(empTypeData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Employee Type");
		}
	});
}

function fetchLeaveRequest() 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?modifyFlag=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}






function fetchholidayCalendar()
{
	alert("fetchholidayCalendar");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/holidayFullView.action",
	    success : function(data) 
	    {
	    	$("#holidayDialog11").dialog('open');
	    	$("#holidayDialogDataDiv11").html(data);
		    
	},
	   error: function() {
	        alert("error");
	    }
	 });
}







function fetchLeavePolicy() 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeLeavePolicyForMyself.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

