
function getServiceSubDept(conditionVar_Value,targetid,conditionVar_Value2)
{
	if (conditionVar_Value=='All') 
	{
		$("#"+targetid).hide();
	} 
	else 
	{
		$("#"+targetid).show();
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/commonModules/getServiceDept.action?conditionVar_Value="+conditionVar_Value+"&conditionVar_Value2="+conditionVar_Value2,
			success : function(empData)
			{
				$('#'+targetid+' option').remove();
				$('#'+targetid).append($("<option></option>").attr("value",-1).text("All Sub Dept"));
				$(empData).each(function(index)
				{
				   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
				});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
}
function getGraphDataProductivity(fdate,tdate,dept,subdept,report,byDept)
{
	var fromdate=$("#"+fdate).val();
	var todate=$("#"+tdate).val();
	var deptID=$("#"+dept).val();
	var subDept=$("#"+subdept).val();
	var dataFor=$("#dataFor").val();
	var byDeptId=$("#"+byDept).val();
	
	if (fromdate=='') 
	{
		alert("Please Select From Date . ");
	}
	else 
	{
		$("#dialogOpen").dialog({title: 'Productivty Graph',width: 500,height: 1000 });
		$("#dialogOpen").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalyticsGraph.action?fromDate="+fromdate+"&toDate="+todate+"&deptId="+deptID+"&subdeptId="+subDept+"&dataFor="+dataFor+"&byDeptId="+byDeptId,
		    success : function(data) {
	       $("#dialogOpen").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function totalDataFunction11(on,off,missed,snooze,ignore)
{
	var value=on+","+off+","+missed+","+snooze+","+ignore;
	$("#dialogOpen").dialog({title: 'Productivty Graph'});
	$("#dialogOpen").dialog('open');
	$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityGraphData.action?graphData="+value,
	    success : function(data) 
	    {
       		$("#dialogOpen").html(data);
	    },
	   error: function() {
            alert("error");
        }
	 });
}
function downloadAction(dataFor)
{
    var data4=$("#"+dataFor).val();
	var fromDate=$("#fromDate").val();
	var subdeptId=$("#subdeptId").val();
	var toDate=$("#toDate").val();
	var deptId=$("#deptId").val();
	var byDept =$("#byDeptId").val();
	if (fromDate=='') 
	{
		alert("Please Select From Date.");
	} 
	else 
	{
		$("#dialogOpen").dialog({title: 'Check Column ',width: 350,height: 600});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#dialogOpen").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/sendMailConfirmAction.action?fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor="+data4,
		    success : function(data) 
		    {
	 			$("#dialogOpen").html(data);
			},
		    error: function() {
		        alert("error");
		    }
		 });
	}
}
function openDialog()
{
	var data4=$("#dataFor").val();
	var fromDate=$("#fromDate").val();
	var subdeptId=$("#subdeptId").val();
	var toDate=$("#toDate").val();
	var deptId=$("#deptId").val();
	var byDept =$("#byDeptId").val();
	if (fromDate=='') 
	{
		alert("Please Select From Date.");
	} 
	else 
	{
		$("#dialogOpen").dialog({title: 'Send Mail ',width: 950,height: 250});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#dialogOpen").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/beforeSendMail.action?fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor="+data4,
		    success : function(data) 
		    {
	 			$("#dialogOpen").html(data);
			},
		    error: function() {
		        alert("error");
		    }
		 });
	}
}
function getEmployeeData(deptId,targetid)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getEmployeeData.action?deptId="+deptId,
		success : function(empData)
		{
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Employee"));
			$(empData).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}