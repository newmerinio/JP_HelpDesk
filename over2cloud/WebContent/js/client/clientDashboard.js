//Client Dashboard***************************************************************************************
var CLIENT_FLAG = "PIE"; //TABLE
function showClientPie(div)
{
	CLIENT_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeClientPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function showClientTable(div)
{
	CLIENT_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeClientTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function refreshClientDiv(div)
{
	if(CLIENT_FLAG == "PIE")
	{
		showClientPie(div,userName);
	}
	else if(CLIENT_FLAG == "TABLE")
	{
		showClientTable(div,userName);
	}
}
function maximizeClientDiv(div,legendShow,width,height,userName)
{
	if(CLIENT_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeClientPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(CLIENT_FLAG == "TABLE")
	{
		$("#myclickdialogTable").dialog('open');
		$("#myclickdialogTable").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeClientTable.action",
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}

//Activity Dashboard***************************************************************************************
var ACTIVITY_FLAG = "PIE"; //TABLE
function showClientActivityPie(div)
{
	ACTIVITY_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowClientActivityPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showClientActivityTable(div)
{
	ACTIVITY_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowClientActivityTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshClientActivityDiv(div)
{
	if(ACTIVITY_FLAG == "PIE")
	{
		showClientActivityPie(div);
	}
	else if(ACTIVITY_FLAG == "TABLE")
	{
		showClientActivityTable(div);
	}
}
function maximizeClientActivityDiv(div,legendShow,width,height)
{
	if(ACTIVITY_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeShowClientActivityPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(ACTIVITY_FLAG == "TABLE")
	{
		$("#myclickdialogTable").dialog('open');
		$("#myclickdialogTable").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeShowClientActivityTable.action",
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}

//Status *****************************************************************************************
var STATUS_FLAG = "PIE"; //TABLE
function showClientStatusPie(div)
{
	STATUS_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeStatusPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showClientStatusTable(div)
{
	STATUS_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeStatusTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshClientStatusDiv(div)
{
	if(STATUS_FLAG == "PIE")
	{
		showClientStatusPie(div);
	}
	else if(STATUS_FLAG == "TABLE")
	{
		showClientStatusTable(div);
	}
}
function maximizeClientStatusDiv(div,legendShow,width,height)
{
	if(STATUS_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeStatusPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(STATUS_FLAG == "TABLE")
	{
		$("#myclickdialogTable").dialog('open');
		$("#myclickdialogTable").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeStatusTable.action",
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}

//Offering w.r.t Client ****************************************************************************
var OFFWRTCLIENT_FLAG = "PIE"; //TABLE
function showOffWrtClientPie(div)
{
	OFFWRTCLIENT_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowOffWrtClientPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showOffWrtClientTable(div)
{
	OFFWRTCLIENT_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowOffWrtClientTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshOffWrtClientDiv(div)
{
	if(OFFWRTCLIENT_FLAG == "PIE")
	{
		showOffWrtClientPie(div);
	}
	else if(OFFWRTCLIENT_FLAG == "TABLE")
	{
		showOffWrtClientTable(div);
	}
}
function maximizeOffWrtClientDiv(div,legendShow,width,height)
{
	if(OFFWRTCLIENT_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/client/beforeShowOffWrtClientPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(OFFWRTCLIENT_FLAG == "TABLE")
	{
		$("#"+div).dialog('open');
		showOffWrtClientTable(div);
	}
}

//Birthday Associate ****************************************************************************
function showClientBirthdayTable(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowClientBirthdayTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function showClientBirthdayTableMax(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#"+div).dialog('open');
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowClientBirthdayTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

//Anniversary Associate ****************************************************************************
function showclientAnniversaryTable(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowclientAnniversaryTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showclientAnniversaryTableMax(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#"+div).dialog('open');
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/client/beforeShowclientAnniversaryTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

