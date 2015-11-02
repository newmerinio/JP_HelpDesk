//Associate Dashboard**********************************************************************************

var ASSOCIATE_FLAG = "PIE"; //TABLE
function showAssociatePie(div,userName)
{
	ASSOCIATE_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeAssociatePie.action",
		data: "userName="+userName,
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showAssociateTable(div,userName)
{
	ASSOCIATE_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeAssociateTable.action",
		data: "userName="+userName,
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function maximizeAssociateDiv(div,legendShow,width,height,userName)
{
	if(ASSOCIATE_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Associate/beforeAssociatePie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height+"&userName="+userName,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(ASSOCIATE_FLAG == "TABLE")
	{
		$("#myclickdialogTable").dialog('open');
		$("#myclickdialogTable").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Associate/beforeAssociateTable.action",
			data: "userName="+userName,
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}
function refreshAssociateDiv(div,userName)
{
	if(ASSOCIATE_FLAG == "PIE")
	{
		showAssociatePie(div,userName);
	}
	else if(ASSOCIATE_FLAG == "TABLE")
	{
		showAssociateTable(div,userName);
	}
}


//Activity Dashboard***************************************************************************************
var ACTIVITY_FLAG = "PIE"; //TABLE
function showAssociateActivityPie(div)
{
	ACTIVITY_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeActivityPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showAssociateActivityTable(div)
{
	ACTIVITY_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeActivityTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshAssociateActivityDiv(div)
{
	if(ACTIVITY_FLAG == "PIE")
	{
		showAssociateActivityPie(div);
	}
	else if(ACTIVITY_FLAG == "TABLE")
	{
		showAssociateActivityTable(div);
	}
}
function maximizeAssociateActivityDiv(div,legendShow,width,height)
{
	if(ACTIVITY_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Associate/beforeActivityPie.action",
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
			url : "view/Over2Cloud/WFPM/Associate/beforeActivityTable.action",
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
function showAssociateStatusPie(div)
{
	STATUS_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeStatusPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showAssociateStatusTable(div)
{
	STATUS_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeStatusTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshAssociateStatusDiv(div)
{
	if(STATUS_FLAG == "PIE")
	{
		showAssociateStatusPie(div);
	}
	else if(STATUS_FLAG == "TABLE")
	{
		showAssociateStatusTable(div);
	}
}
function maximizeAssociateStatusDiv(div,legendShow,width,height)
{
	if(STATUS_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Associate/beforeStatusPie.action",
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
			url : "view/Over2Cloud/WFPM/Associate/beforeStatusTable.action",
			success : function(data) {
			$("#myclickdialogTable").html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
}

//Offering w.r.t Associate ****************************************************************************
var OOWRTASSO_FLAG = "PIE"; //TABLE
function showOffWrtAssoPie(div)
{
	OOWRTASSO_FLAG = "PIE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowOffWrtAssoPie.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showOffWrtAssoTable(div)
{
	OOWRTASSO_FLAG = "TABLE";
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowOffWrtAssoTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function refreshOffWrtAssoDiv(div)
{
	if(OOWRTASSO_FLAG == "PIE")
	{
		showOffWrtAssoPie(div);
	}
	else if(OOWRTASSO_FLAG == "TABLE")
	{
		showOffWrtAssoTable(div);
	}
}
function maximizeOffWrtAssoDiv(div,legendShow,width,height)
{
	if(OOWRTASSO_FLAG == "PIE")
	{
		$("#"+div).dialog('open');
		$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/Associate/beforeShowOffWrtAssoPie.action",
			data: "legendShow="+legendShow+"&width="+width+"&height="+height,
			success : function(data) {
			$("#"+div).html(data);
		},
		error: function() {
			alert("error");
		}
		});
	}
	else if(OOWRTASSO_FLAG == "TABLE")
	{
		$("#"+div).dialog('open');
		showOffWrtAssoTable(div);
	}
}

//Birthday Associate ****************************************************************************
function showAssociateBirthdayTable(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowAssociateBirthdayTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function showAssociateBirthdayTableMax(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#"+div).dialog('open');
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowAssociateBirthdayTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

//Anniversary Associate ****************************************************************************
function showAssociateAnniversaryTable(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowAssociateAnniversaryTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}
function showAssociateAnniversaryTableMax(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#"+div).dialog('open');
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/Associate/beforeShowAssociateAnniversaryTable.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

