function getNotify()
  {
 	notificationAjax();
    hellointerval();
  } 

 notificationAjax();
function hellointerval()
  {
 	 // setInterval("notificationAjax()", (20*1000));
  }
function notificationAjax()
  {
	$.ajax({
    type : "post",
    url : "notificationBar.action",
    success : function(subdeptdata) {
       $("#"+"notificationDiv").html(subdeptdata);
	},
	   error: function() {
            alert("Some Error in Getting Notification Data. Please  Login Once Again or Contact To IT Department !!!");
       }
	 });
  }
    
   
serverTime();
function serverTime()
  {
   	   setInterval("serverTimeAjax()", 60*1000);
  }
function serverTimeAjax()
  {
   	$.ajax({
       type : "post",
  	    url : "serverTime.action",
  	    success : function(subdeptdata) {
          $("#"+"serverTimeDiv").html(subdeptdata);
   	},
   });
 }
   

/* ************  Start of Helpdesk Methods  ****************** */

/* ****  configurationOver2Cloud.xml ****** */
function getWorkTiming(moduleName)
{
	//alert("Inside a method");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommonOver2Cloud/Working_Time/firstActionMethod.action?actionType=View&dataFor="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
          alert("error");
      }
	 });
}


/* *********ConfigurationOver2Cloud.xml ******* */
function addNewWorkingTime(moduleName)
{
	var module =$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommonOver2Cloud/Working_Time/firstActionMethod.action?actionType=Add&dataFor="+module,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

/* ********* FeedbackDraft.xml ******* */
function getConfigureTask(pagetype,module)
{
	alert("Inside getConfigure Task  ");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraftView.action?dataFor="+module+"&viewType="+pagetype,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

/* ********* ShiftConf.xml ******* */
function configureShift(viewType,module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Shift_Conf/beforeShiftView.action?dataFor="+module+"&viewType="+viewType,
	    success : function(shiftdata) {
       $("#"+"data_part").html(shiftdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


/* ********* Configuration.xml ******* */
function getConfigureTicket(module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Ticket_Config/beforeTicketConfigView.action?ticketflag=v&dataFor="+module,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewFeedback(status,moduleName,dataFlag,okFlag)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status+"&moduleName="+moduleName+"&dataFlag="+dataFlag+"&remark="+okFlag,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function viewFeedbackBackData(status,moduleName,dataFlag,okFlag)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status+"&moduleName="+moduleName+"&dataFlag="+dataFlag+"&remark="+okFlag+"&backData=Y",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function getRoaster(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/beforeRoaster.action?dataFor="+moduleName+"&flag=apply",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function viewComplaint(status,moduleName,dataFlag,okFlag)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/beforeComplaintAction.action?feedStatus="+status+"&moduleName="+moduleName+"&dataFlag="+dataFlag+"&remark="+okFlag,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}


function configureShift(viewType,module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Shift_Conf/beforeShiftView.action?dataFor="+module+"&viewType="+viewType,
	    success : function(shiftdata) {
       $("#"+"data_part").html(shiftdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function getRoaster(viewtype,moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/beforeRoaster.action?flag=apply&viewType="+viewtype+"&dataFor="+moduleName,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

/* ************  End of Helpdesk Methods  ****************** */


function getConfigureTask(pagetype,module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraftView.action?dataFor="+module+"&viewType="+pagetype,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function viewAssetFeedback(status,moduleName,dataFlag,okFlag)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/beforeFeedView.action?feedStatus="+status+"&moduleName="+moduleName+"&dataFlag="+dataFlag+"&remark="+okFlag,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function getContact()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
 
function getContactSharing()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactSharing.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
} 

function getContactMapping()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactMapping.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
/*function getContact(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1&moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
 
function getContactSharing(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactSharing.action?moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
} 

function getContactMapping(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactMapping.action?moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}*/ 

//************ Asset Start **********//
function getPage(mode)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/beforeFeedComplaint.action?dataFor="+mode+"&moduleName=CASTM",
	    success : function(confirmation) {
     $("#"+"data_part").html(confirmation);
	},
	   error: function() {
          alert("error");
      }
	 });
}
//********** Asset End **********//
////////////RCA master Code //////
function getRCA(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/RCAOver2Cloud/RCAMaster/beforeRCAMasterView.action?moduleName="+moduleName,
	    success : function(confirmation) {
      $("#"+"data_part").html(confirmation);
	   },
	   error: function() {
          alert("error");
      }
	 });
}

//// Ends RCA master Code /////




/*
 * XML : Configuration.xml
 * */
function getConfigureTicket(module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Ticket_Config/beforeTicketConfigView.action?ticketflag=v&dataFor="+module,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewFeedback(status,moduleName,dataFlag,okFlag)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status+"&moduleName="+moduleName+"&dataFlag="+dataFlag+"&remark="+okFlag,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}





//////////// EMail Resend //////////////
function getEmailSend(module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataHeaderEmail.action?moduleName="+module,
	    success : function(feeddraft) {
	   $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
////////////EMail Resend //////////////

/////// SMS Resend/////

function getSMSSend(module)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataHeader.action?moduleName="+module,
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
/////  SMS Resend/////


////*************Productivity HDM & Feedback *********////

function getProductivty(moduleName,dataFor)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalytics.action?dataFor="+dataFor+"&moduleName="+moduleName,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewSeekApproval(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeekApproval/beforeViewSeekApproval.action?moduleName="+moduleName,
		success : function(data)
		{
			$("#data_part").html(data);
		},
		error : function()
		{
			alert("Problem in data fetch");
		}
	});
}
function getSeverity(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeverityOver2Cloud/beforeViewSeverity.action?moduleName="+moduleName,
		success : function(data)
		{
			$("#data_part").html(data);
		},
		error : function()
		{
			alert("Problem in data fetch");
		}
	});
}
function viewConfigureCheckList()
{
	 
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/viewConfigureCheckList.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
          alert("error");
      }
	 });
}



