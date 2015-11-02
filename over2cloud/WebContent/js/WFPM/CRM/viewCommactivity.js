var row=0;
$.subscribe('rowselect', function(event, data) {
	row  = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
});

function totalRecord(totalData)
	{
	var totalCount=$("#"+records).val();
	$("#records").html(totalCount);
	}
function addactivityGroup()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeCRMPage.action",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	}

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(row.length > 1)
	{
		alert("Please select only one row.");
	}
	else{
	jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
}

function deleteRow()
{
	var status=$("#status").val();
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(row.length > 1)
	{
		alert("Please select only one row.");
	}
	else{
	if(status=='Inactive')
		{
		alert("Record Is Already Inactive");
		}
	else{
		$("#gridBasicDetails").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	}
}
function searchRow()
{
	 $("#gridBasicDetails").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/CRM/beforeCommActivityPage.action?status=Active",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	var grid = $("#gridBasicDetails");
	grid.trigger("reloadGrid",[{current:true}]);
}	



function backToMainView()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeCommActivityPage.action?status=Active",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
}

var email,dept,emp,industry;
var emailId="";
function communicateViaMail(groupId)
{
	row  = $("#gridBasic").jqGrid('getGridParam','selarrrow');
	emp=jQuery("#gridBasic").jqGrid('getCell','selarrrow','personName');
	var subIndustry = jQuery("#gridBasic").jqGrid('getCell','selarrrow', 'personName');
	/*alert(subIndustry);*/
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else{
		var ab=row.toString().split(",");
		for(i=0;i< parseInt(ab.length);i++)
		{
			emp=jQuery("#gridBasic").jqGrid('getCell',ab[i],'personName');
			dept=jQuery("#gridBasic").jqGrid('getCell',ab[i],'deptName');
			email=jQuery("#gridBasic").jqGrid('getCell',ab[i],'emailOfficial');
			industry=jQuery("#gridBasic").jqGrid('getCell',ab[i],'industry');
			emailId += emp+","+dept+","+email+","+industry+"__";
		}

		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeMailSendPage1.action?id="+row+"&groupId="+groupId+"&data="+emailId,
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
/*		
	$("#data_part").dialog("open");
	$("#data_part").load("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/CRM/beforeMailSendPage1.action?id="+row+"&groupId="+groupId+"&data="+emailId));*/
	}
}
function addGroupMail()
{
	/*alert(row);*/
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	if(row.length > 1)
	{
		alert("Please select only one row.");
	}
	if(row.length == 1)
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/CRM/beforeCRMMailPage.action?groupId="+row,
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
   }
} 

function searchshowCommActivityData(param)
	{
		var param = 0;
		searchResult(param,'','','');
	}
	
	function searchResult(param, searchField, searchString, searchOper)
	{
	var urls="";
	var status=$("#status").val();
	var groupname=$("#groupnameId option:selected").text();
	$.ajax({
	    type : "post",
    	url  : "view/Over2Cloud/WFPM/CRM/beforeCRMGroupDetailsView.action?groupname="+groupname+"&status="+status,
       success : function(subdeptdata) {
	       $("#"+"crmactivityshowgrid").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
   }

function countGroupRecord(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewGroupTotalDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewGroupTotalDetails(id) 
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/CRM/viewGroupCountDetails.action",
		data: "id="+id, 
		success : function(data) {  
			$("#groupCountDetails").dialog('open');
			$("#groupCountDetails").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$("#groupCountDetails").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}