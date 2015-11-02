
//Lead Dashboard***************************************************************************************
var LEAD_FLAG = "PIE"; //TABLE
function showLeadStatusPie(div)
{
	LEAD_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Lead/beforeLeadPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showLeadStatusTable(div)
{
	LEAD_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Lead/beforeshowLeadStatusTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshLeadStatusDiv(div)
{
	if(LEAD_FLAG == "PIE")
	{
		showLeadStatusPie(div);
	}
	else if(LEAD_FLAG == "TABLE")
	{
		showLeadStatusTable(div);
	}
}
function maximizeLeadStatusDiv(div,legendShow,width,height)
{
	if(LEAD_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Lead/beforeLeadPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(LEAD_FLAG == "TABLE")
	{
		$("#myclickdialogTable").dialog('open');
		$("#myclickdialogTable").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Lead/beforeshowLeadStatusTable.action",
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}