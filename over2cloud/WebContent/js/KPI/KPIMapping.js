function viewAddPage()
{
	var moduleName = $("#head").html();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforekra_kpiMapping.action",
	    data: "moduleName="+moduleName,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

$.subscribe('checkEmplyeeSelection', function(event,data)
{
	var val = $("#empName").val();
	if(val == null || val == "" || val == '-1')
	{
		errZone.innerHTML="<div class='user_form_inputError2'>Select employee ! </div>";
		$("#empName").css("background-color","#ff701a");  //255;165;79
		$("#empName").focus();
		setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        event.originalEvent.options.submit = false;
	}
});

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function deleteRow()
{
	//row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	$("#gridedittable").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}

function searchRow()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:400,modal:true} );
}

function refreshRow(rowid, result) 
{
	$("#gridedittable").trigger("reloadGrid"); 
}

function viewKraKpi()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		url : encodeURI("view/Over2Cloud/hr/beforeKpiMapping.action?moduleName=KPI Mapping"),
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
		error: function() {
			alert("error");
		}
	});
}

function viewKraKpiMapping()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		url : encodeURI("view/Over2Cloud/hr/beforeKpiMapping.action?moduleName=KPI Mapping"),
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("error");
	}
	});
}


