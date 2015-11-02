$(document).ready(function(){
	
	$("#MailConfiguration").click(function(){
		$("#data_part").html("");
		$.ajax({
		    type : "post",
		    url : "/ovemailer2cloud/view/Over2Cloud/Setting/MailConfig.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#RegistrationSpaceConfiguration").click(function(){
		$("#data_part").html("");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Setting/serverconfigSpace.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	//Sandep work starts from here for organization 
	
	

	/*$("#organizationView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/
	
	$("#organizationView").click(function()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationViewTable.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#organizationViewTable").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationViewTable.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});


	
	$("#organizationModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationModify.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#department").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartment.action?deptFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	/*$("#departmentView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentView.action?deptFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/
	
	$("#departmentModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentModify.action?deptFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#subDepartment").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeSubDepartment.action?existFlag=1&subDeptfalg=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#subDepartmentView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeSubDepartmentView.action?subDeptfalg=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	
	
	
	$("#subDepartmentModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeSubDepartmentModify.action?subDeptfalg=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#designation").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDesignation.action?designationFlag=1&empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#designationView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDesignationView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#designationModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDesignationModify.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#employee").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeEmployee.action?empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#employeeView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#employeeModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeEmployeeModify.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#employeeHistory").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeEmployeeHistory.action?empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	$("#mapEmployee").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeEmployeeMap.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	/*$("#userView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeUserView.action?empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/
	
	
	
	

	$(document).ready(function(){
		//Examples of how to assign the ColorBox event to elements
		$(".booking").colorbox({rel:'booking', transition:"elastic",width:"50%",height:"50%"});
						
	});
	
	$(document).ready(function(){
		//Examples of how to assign the ColorBox event to elements
		$(".profile").colorbox({rel:'booking', transition:"elastic",width:"50%",height:"50%"});
						
	});
	
	$("#userModify").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeUserModify.action?empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	// Communication Pannel (CommonOver2Cloud.xml)
	/*$("#resend_sms_email").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationData.action",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/
	
	/*// Analytical Report (Confiuration.xml)
	$("#analytical_report").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/beforeAnalyticalReport.action",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/

///////////////
	//help desk menu click data
	//Help desk modules
	
	$("#viewActivityBoard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#feed_draft_add").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraft.action?empModuleFalgForDeptSubDept=1&feedbackDarft=1",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#feed_draft_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraftView.action?modifyFlag=0&xyz=xx",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#assetfeed_draft_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraftView.action?modifyFlag=0&dataFor=ASTM",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	

	
	//****** Helpdesk Control Start ******//
	$("#feed_via_call").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?feedStatus=call&dataFor=HDM",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#feed_via_online").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?feedStatus=online&dataFor=HDM",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#feed_via_email").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaEmail.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=email&dataFor=HDM",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#shift_add").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Shift_Conf/beforeShift.action?empModuleFalgForDeptSubDept=1&shiftConf=1&dataFor=HDM",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	/*$("#shift_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Shift_Conf/beforeShiftView.action?modifyFlag=0&dataFor=HDM",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/

	$("#shift_modify").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Shift_Conf/beforeShiftView.action?modifyFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#roaster_conf_add").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/beforeRoaster.action?flag=add",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#daily_report_configurtion").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfig.action?pageType=SC",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#feedreport_history").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeReportConfigView.action?pageType=H",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#feed_action_feedreport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeReportConfigView.action?pageType=D",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#feed_action_analyticReport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/beforeAnalyticalReport.action?dataFor=HDM",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	/*$("#resend_sms_email").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationData.action",
		    success : function(feeddraft) {
	       $("#"+"data_part").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});*/
	
	 $("#resend_sms_email").click(function(){
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataHeader.action",
			    success : function(feeddraft) {
		       $("#"+"data_part").html(feeddraft);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
	
	 $("#resend_email").click(function(){
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataHeaderEmail.action",
			    success : function(feeddraft) {
		       $("#"+"data_part").html(feeddraft);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
	 
	 
	//****** Helpdesk Control End ******//
	
	// Control start for Dashboard
	$("#ticket_normal_dashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Config/beforeDashboardConfigView.action?dashboard=N",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#ticket_dept_dashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Config/beforeDashboardConfigView.action?dashboard=D",
		    success : function(deptdashdata) {
	       $("#"+"data_part").html(deptdashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	// Code start for new dashboard
	$("#dash_home").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforeDashboardHome.action",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	// Code start for new dashboard
	$("#ticket_normal_newdashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforeDashboardView.action?flag=N",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	// Code start for HOD dashboard
	$("#ticket_hod_newdashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforeDashboardView.action?flag=H",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	// Code start for Management dashboard
	$("#ticket_mgmt_newdashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforeDashboardView.action?flag=M",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	
	// Start Point for Client SMTP Configuration Setting
	$("#smtp").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/SMTP_Setting/beforeSmtpConfigView.action?smtp=S",
		    success : function(deptdashdata) {
	       $("#"+"data_part").html(deptdashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#feed_action_graphreport").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/getGraph.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	///compliance
	// AJAX FOR COMPLIANCE>>>>>>>>>>>>> STARTED

	$("#my_compl_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/beforeMyComplView.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) 
		    {
				$("#"+"data_part").html(subdeptdata);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	});

	$("#compl_configure_excel").click(function(){
		 
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/beforeComplExcelupload.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}); 


	$("#compl_configure").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    	type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/createConfigurationComplView.action",
			    success : function(data) {
		       $("#"+"data_part").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		 });
	//for current week

	$("#compl_day").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/currentDayDues.action?currentDay=1&currentWeek=0&currentMonth=0",
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	 });

	$("#compl_weekly").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/currentWeekDues.action?currentDay=0&currentWeek=1&currentMonth=0",
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function()
		   {
	            alert("error");
	       }
		 });
	 });

	$("#compl_monthly").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/currentMonthDues.action?currentDay=0&currentWeek=0&currentMonth=1",
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	 });

	$("#compl_action_taken").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/complActionTaken.action",
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	 });

	$("#my_compl_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/beforeMyComplView.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) 
		    {
				$("#"+"data_part").html(subdeptdata);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	});

	$("#dept_compl_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#data_part").html("");
		$.ajax({
		         type : "post",
		         url : "view/Over2Cloud/Compliance/compliance_pages/complDeptView.action",
		         success : function(subdeptdata) {
	             $("#"+"data_part").html(subdeptdata);
		      },
		       error: function() 
		       {
	            alert("error");
	           }
		    });
	});

	$("#compl_dash_board").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/userComplDashboard.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	 
	//Task type master
		 $("#add_compl_task_type").click(function(){
				
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskType.action",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 
		 //modify_compl_task_type
		 $("#modify_compl_task_type").click(function(){
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskTypeModify.action?modifyFlag=1&deleteFlag=0",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 
		 $("#view_compl_task_type").click(function(){
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskTypeView.action?modifyFlag=1&deleteFlag=0",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 
	//Task Master	 
		 	 
		 $("#add_compl_task").click(function(){
				
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/beforeComplTask.action",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		  
		 $("#view_compl_task").click(function(){
				
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action?modifyFlag=1&deleteFlag=0",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 
		 $("#modify_compl_task").click(function(){
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/beforeComplTaskModify.action?modifyFlag=1&deleteFlag=0",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 
	//add_compl_contacts	 
		 $("#add_compl_contacts").click(function(){
			 	
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContact.action",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
		 	 
		 /*$("#view_compl_contacts").click(function(){
			 	
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });*/
		 
		 $("#view_compl_contacts").click(function(){
			 	
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1&moduleName=COMPL",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 }); 
		 
		 $("#view_compl_contacts_sharing").click(function(){
			 	
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactSharing.action?moduleName=COMPL",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 }); 
		
		 
		 $("#modify_compl_contacts").click(function(){
			 	
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactDelete.action?modifyFlag=0&deleteFlag=1",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 }); 

		 $("#hod_compl_dashboard").click(function(){
			 
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compliance_pages/complDashboard.action",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			}); 
		 
	$("#mgmt_compl_dashboard").click(function(){
	 
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/mgmtComplDashboard.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}); 	

	$("#ageing_compl_dashboard").click(function(){
	 
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/ageingComplDashboard.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}); 	

	$("#transfer_compliance").click(function(){
	 
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/complTransferAction.action?modifyFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}); 	

	$("#compl_report").click(function(){
		
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    var fromDate=$("#fromDate").val();
		var toDate=$("#toDate").val();
		var actionStatus=$("#actionStatus").val();
		var periodSort=$("#periodSort").val();
		var deptId=$("#deptId").val();
		var freque =$("#frequency").val();
	 $.ajax({
	 		type :"post",
	 		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getOnloadDataAction.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&periodSort="+periodSort+"&deptId="+deptId+"&frequency="+freque,
	 		success : function(data) 
		    {
				$("#data_part").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}); 

	$("#user_compl_dashboard").click(function(){
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/logedUserComplDashboard.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}); 	

	$("#template_compliance").click(function(){
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/BeforeCompTemplateView.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}); 
	$("#task_history").click(function(){
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/BeforeTaskHistory.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}); 
	
	$("#emp_productivity").click(function(){
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/beforeEmployeeProductivity.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});

	$("#activity_dash").click(function(){
		 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/beforeActivityDashboard.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
	// Budy Setting 

	$("#buddy_action_view").click(function(){
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
	});

	// Buddy Settings Ends
///////////////////////Asset Master Start//////////////////////////////////
	$("#vendor_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#vendor_type_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorTypeViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	

	$("#asset_type_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetTypeView.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#supportcategory_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSuportCatgView.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#sparecategory_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareCatgViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	///////////////////////Asset Master End////////////////////////
	
	
   ///////////////////////Asset Register Start////////////////////////
	
	$("#asset_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#asset_support_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#barcode_config").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/barcodeConfiguration.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#asset_barcode_action_generate").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/barcode.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#asset_reminder_report").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeViewAssetReminderReport.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});


	
	///////////////////////Asset Register End////////////////////////
	
	///////////////////////Asset Allotment Start////////////////////////
	
	$("#asset_allotment_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#asset_preventive_maintenance").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?moduleName=Preventive",
				    success : function(data) 
				    {
			       		$("#data_part").html(data);
					},
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			});
	$("#asset_support_action").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName=Support",
				    success : function(data) 
				    {
			       		$("#data_part").html(data);
					},
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			});
			
			$("#asset_preventive_action").click(function()
					{
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName=Preventive",
						    success : function(data) 
						    {
					       		$("#data_part").html(data);
							},
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					});
	
	///////////////////////Asset Allotment End////////////////////////
	
	///////////////////////Asset Dashboard Start////////////////////////
	
	$("#asset_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createModuleViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#outlet_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/ViewOutletMapping.jsp",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	
	$("#asset_dashboard_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDashboard.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	});
	
	

	// Code for Asset Complaint Dashboard
	$("#asset_cmplaint_dashboard").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/Asset_Dash/beforeDashboardView.action?flag=N",
		    success : function(dashdata) {
	       $("#"+"data_part").html(dashdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	
	
	///////////////////////Asset Dashboard End////////////////////////
	
	///////////////////////Network Monitor Start////////////////////////
	
	$("#network_monitor_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/ViewNetworkMonitor.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	});
	
	$("#viewMachineDetails").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewMachineDetails.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#config_service_alert").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewServiceAlert.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	///////////////////////Network Monitor End////////////////////////
	
	
	///////////////////////Depreciation Start////////////////////////
	
	$("#view_asset_depreciation").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeViewDepreciation.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	});
	
	///////////////////////Depreciation End////////////////////////
	
	///////////////////////Spare Start////////////////////////
	
	$("#spare_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#spare_receive_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createReceiveViewPage.action?",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	$("#remaining_spare_action_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/getSpareInfo.action?",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	
	///////////////////////Spare End////////////////////////
	///////////////////////Configure Utility////////////////////////
	
	
	$("#view_configure_utility").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/beforeViewConfigureGrid.action",
		    success : function(data) {
	       		$("#data_part").html(data);
	       		
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#view_daily_utility").click(function(){
		
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/beforeViewdailyGrid.action",
		    success : function(data) {
	       		$("#data_part").html(data);
	       		
			},
		    error: function() {
	            alert("error");
	        }
		 });
	});
///////////////////////Configure Utility END////////////////////////

//Fedback Starts
	//Paper Form
	$("#feedback_add_paper").click(function(){
		//alert("Hello");
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/beforeFeedbackView.action?modifyFlag=0&deleteFlag=0",
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	});
	
	
	// For PCC
	$("#feed_action_pcc").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/beforePccFeedbackView.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	   alert("error");
	}
		 });
	});

	
	//Positive
	$("#feedback_Total_positive").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/feedback/beforeCustomerPostiveView.action?flage=positive",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	   alert("error");
	}
	});
	});
	
	//Negative
	$("#feedback_Neg_pending").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/feedback/beforeCustomerNegView.action?flage=negative",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	   alert("error");
	}
	});
	});
	
	//Admin Pending Tickets
	$("#feed_action_pending_admin").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	   alert("error");
	}
		 });
	});
	
	//Admin Pending Tickets
	$("#feed_action_pending_fm").click(function()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedActionDept.action?feedStatus=Pending",
		    success : function(subdeptdata) 
		    {
		    	$("#"+"data_part").html(subdeptdata);
		    },
		   error: function() 
		   {
			   alert("error");
		   }
		 });
	});
	
	// Activity Board
	$("#feed_activity").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
				    success : function(subdeptdata) 
				    {
				    	$("#"+"data_part").html(subdeptdata);
				    },
				   error: function() 
				   {
					   alert("error");
				   }
				 });
			});
	
	//View MIS
	$("#feed_Details").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/beforeFeedDetailsSearch.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	alert("error");
	}
		 });
	});

	//View Reports
	$("#feed_Details_Report").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/report/beforeFullSearch.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	alert("error");
	}
		 });
	});
	
	
	

	//View Customers/ Patients
	$("#patdetails_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/beforePatSearchView.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	   alert("error");
	}
		 });
	});



	//View Keywords
	$("#keyWords_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/beforeKeySearchView.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	 alert("error");
	}
		 });
	});
	
	
	//View Reports
	$("#total_feedReport_view").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/report/beforeTotalFeedReport.action",
		    success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	 alert("error");
	}
		 });
	});

	
	
	

//dashboard
//dashboard
$("#dash_view").click(function(){
	//alert("Tabbbb<>>>>>>>>>>>>>>>>>>>");
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
type : "post",
url : "view/Over2Cloud/feedback/showDashBoard.action",
success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
},
error: function() {
   alert("error");
}
});
});
$("#dash_viewNormal").click(function(){
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
type : "post",
url : "view/Over2Cloud/feedback/showDashBoardNormal.action",
success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
},
error: function() {
   alert("error");
}
});
});

$("#feed_sms_config").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/beforeFeedbackSMSConfig.action",
	    success : function(subdeptdata) {
 $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
      alert("error");
  }
	 });
});

// Bed Mapping
$("#bed_Mapping_Detail").click(function(){
    $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/bedMapping/viewBedMapping.action",
        success : function(subdeptdata) {
   $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
        alert("error");
    }
     });
});
//Feedback Ends


$("#emailConfigView").click(function(){
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_email_configuration&mapSubTable=email_configuration&moduleName=Email configuration&dataTable=email_configuration_data&modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	});
//SMS Email draft  work Start here	



//SMS Email draft  work END here
$("#email_setting").click(function(){
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingViewHeader.action",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	});

$("#beforeNewsAlertsView").click(function(){
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/newsAlertsConfig/beforeNewsAlertsView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
});


//For Make History Rahul Srivastava 23-Nov-2013

$("#makeHistory").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "/over2cloud/view/Over2Cloud/hr/beforeEmployeeView.action?makeHistory=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});	

$("#changePassword").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeChangePwd.action?changeType=header",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


/*// For Group
$("#group_add").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforegroupView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});	
*/
//For Group


//For Sub Group
$("#subgroup_add").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeSubGroupView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});	

//For Sub Group
/*$("#contactMaster_add").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});	*/




//for Leave Management Added by Karnika ****************

$("#leave_requestView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#leave_responseView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeleaveResponseView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
$("#holiday_listView").click(function()
		{
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/leaveManagement/beforeHolidayListView.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
$("#leave_policyView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeLeavePolicyView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#leave_typeMasterView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeleaveTypeView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
$("#leave_confiMasterView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeConfiView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#time_slotView").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeTimeSlotView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
$("#attendance_View").click(function(){
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
});

$("#report_Add111").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeReportData.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#analytical_Add").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeAnalyticalAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
$("#summary_Add").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeSummary_Add.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#attendanceReport_Confi").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeReportConfig.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


$("#total_report").click(function(){
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeTotalReport.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


$("#automatic_report").click(function()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeAutomaticReport.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#employee_type").click(function()
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
});
// Dashboard work start here !!!!!!!!!

$("#leave_dashboard_view").click(function(){
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/leaveManagement/beforeDashboard.action",
		success : function(data){
			$("#data_part").html(data);
		},
		error : function(){
			alert("error");
		}
	});
	
});

// DAR work start !!!!!!!!!!!!!!!


$("#project_dashboard_view").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/DAROver2Cloud/dashboard.action",
		success : function(data){
			$("#data_part").html(data);
		},
		error : function(){
			alert("error");
		}
	});
	
});
$("#darValidate").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeDarValidate.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
$("#product_sheet_serch").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/productivitySearch.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#task_add_dashboard").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/dashboard.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#product_sheet").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/productivity.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#task").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/taskRegistrationView.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
	
$("#dar").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/darView.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#client_master").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/clientView.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#task_type").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/taskView.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#project_type").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/managementView.action",
	    success : function(clientdata) {
       $("#"+"data_part").html(clientdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#darReport").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeDarReport.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#darProductivity").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeDarProductivity.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#activity_board").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeActivityBoard.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
});
///////////////////////// DREAMSOL CALL COMPLAINT ////////////////
$("#lodge_complaint_via_call").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/beforeLodgeComplaintCall.action",
	    success : function(data) 
	    {
	    	$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
});

$("#lodge_complaint_via_online").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/beforeLodgeComplaintCall.action",
	    success : function(data) 
	    {
	    	$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
});


		 
//Text2Mail works Start here	 

$("#ViewconfigKeywords").click(function()
		 {
	
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeViewConfigKeyword.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	 }); 

/* $("#pullReport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforePullReport.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });pullReportViewGridHeader.jsp
	 });*/
$("#pullReport").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportViewHeader.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 });


/* 
$("#pullReport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	 });*/

/* $("#pullSendEmailReport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforePullSendEmailReport.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	 }); */



$("#pullSendEmailReport").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeSendMailPullReportView.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	 }); 


	 $("#speciality").click(function(){
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/beforeSpecialityView.action",
			    success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
		 }); 
	 
	 $("#locationT2M").click(function(){
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/beforeLocationView.action",
			    success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
		 }); 
	 
	 $("#consultants").click(function(){
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/beforeConsultantsView.action",
			    success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
		 }); 
		 
		 $("#mapping").click(function(){
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Text2Mail/beforeMappingView.action",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 }); 
		 
		 $("#mapping2").click(function(){
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Text2Mail/beforeMappingView2.action",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
			 });
	 
//Text2Mail works END here	
				 
				 // Productivity Evaluation Part start here ***************
				 // Dashboard Start here 
				 $("#kaizen_bestPractices_view").click(function(){
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeDashboardKaizen.action",
						    success : function(data) 
						    {
								$("#"+"data_part").html(data);
						    },
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					 });
				 
				 $("#improved_dashboard_view").click(function(){
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeDashboardImproved.action",
						    success : function(data) 
						    {
								$("#"+"data_part").html(data);
						    },
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					 });
				 
				 
				 $("#reviewDates_notes_view").click(function(){
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeDashboardReviewDates.action",
						    success : function(data) 
						    {
								$("#"+"data_part").html(data);
						    },
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					 });
				 
				 
				 $("#operational_bsc_view").click(function(){
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeDashboardOperationalBSC.action",
						    success : function(data) 
						    {
								$("#"+"data_part").html(data);
						    },
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					 });
				 
				     $("#main_dash_cmo").click(function()
					 {
						$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
						$.ajax({
						    type : "post",
						    url : "/over2cloud/view/Over2Cloud/ProductivityEvaluationOver2Cloud/beforeDashboardMain.action",
						    success : function(data) 
						    {
								$("#"+"data_part").html(data);
						    },
						    error: function() 
						    {
					            alert("error");
					        }
						 });
					 });	 
		 
		 
				     //KR, Rahul Srivastav 01-10-2013

				       $("#main_dash_KR").click(function()
						 {
							$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
							$.ajax({
							    type : "post",
							    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKRDashboard.action?userType=N",
							    success : function(data) 
							    {
									$("#"+"data_part").html(data);
							    },
							    error: function() 
							    {
						            alert("error");
						        }
							 });
						 });
				       
				       $("#main_dash_KR_modify").click(function()
								 {
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKRDashboardModify.action?userType=N",
									    success : function(data) 
									    {
											$("#"+"data_part").html(data);
									    },
									    error: function() 
									    {
								            alert("error");
								        }
									 });
								 });
						
						
							 
						 $("#beforeKRShareView").click(function(){
								
								$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
								$.ajax({
								    type : "post",
								    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewHeaderPage.action?shareStatus=2",
								    success : function(data) 
								    {
										$("#"+"data_part").html(data);
								    },
								    error: function() 
								    {
							            alert("error");
							        }
								 });
							 });
							 
							 $("#krLibraryDelete").click(function(){
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?modifyFlag=0&deleteFlag=1&formater=1",
									    success : function(subdeptdata) {
								       $("#"+"data_part").html(subdeptdata);
									},
									   error: function() {
								            alert("error");
								        }
									 });
								});
							 
							 $("#krLibrarySearch").click(function(){
								
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrSearch.action",
									    success : function(subdeptdata) {
								       $("#"+"data_part").html(subdeptdata);
									},
									   error: function() {
								            alert("error");
								        }
									 });
								});
							 
							 $("#actionableView").click(function(){
									
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/actionableViewHeaderPage.action?shareStatus=2",
									    success : function(data) 
									    {
											$("#"+"data_part").html(data);
									    },
									    error: function() 
									    {
								            alert("error");
								        }
									 });
								 });
							 $("#krLibraryTakeAction").click(function(){
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/takeActionFromLeftPanel.action?modifyFlag=0&deleteFlag=0&formater=1&actionTaken=1",
									    success : function(data) {
								       $("#"+"data_part").html(data);
									},
									   error: function() {
								            alert("error");
								        }
									 });
								 });
								
							 $("#brforeKRSharedbyWithMe").click(function(){
									$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
									$.ajax({
									    type : "post",
									    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=1&modifyFlag=0&deleteFlag=0&formater=1",
									    success : function(data) 
									    {
											$("#"+"data_part").html(data);
									    },
									    error: function() 
									    {
								            alert("error");
								        }
									 });
								 });
								 
								 
								 $("#beforekrModify").click(function(){
										$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
										$.ajax({
										    type : "post",
										    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforekrModify.action",
										    success : function(data) 
										    {
												$("#"+"data_part").html(data);
										    },
										    error: function() 
										    {
									            alert("error");
									        }
										 });
									 });
								 
								 $("#takeActionreport").click(function(){
										$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
										$.ajax({
										    type : "post",
										    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrActionTaken.action?KRSharedbyWithMe=1&modifyFlag=0&deleteFlag=0&formater=1",
										    success : function(data) 
										    {
												$("#"+"data_part").html(data);
										    },
										    error: function() 
										    {
									            alert("error");
									        }
										 });
									 });

						//END: KR, Rahul Srivastav 01-10-2013
		 
		//Reimbursement Start from Here -------------------------------------------
		 
		 $("#paticulars_master").click(function(){
			 //alert("><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	         $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	         $.ajax({
	             type : "post",
	             url : "view/Over2Cloud/Reimbursement/particularsView.action",
	             success : function(subdeptdata) {
	             $("#"+"data_part").html(subdeptdata);
	         },
	         error: function() {
	             alert("error");
	         }
	         });
	     });
		 
		 $("#add_reimbursement").click(function(){
			 //alert("><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	         $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	         $.ajax({
	             type : "post",
	             url : "view/Over2Cloud/Reimbursement/beforeReimbursementview.action",
	             success : function(subdeptdata) {
	             $("#"+"data_part").html(subdeptdata);
	         },
	         error: function() {
	             alert("error");
	         }
	         });
	     });
		 
		 $("#reimbursement_details").click(function(){
			 //alert("><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	         $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	         $.ajax({
	             type : "post",
	             url : "view/Over2Cloud/Reimbursement/beforeReimbursementdetails.action",
	             success : function(subdeptdata) {
	             $("#"+"data_part").html(subdeptdata);
	         },
	         error: function() {
	             alert("error");
	         }
	         });
	     });
		 
		 $("#add_reimbursement11").click(function(){
			 //alert("><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	         $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	         $.ajax({
	             type : "post",
	             url : "view/Over2Cloud/Reimbursement/reimbursementView.action",
	             success : function(subdeptdata) {
	             $("#"+"data_part").html(subdeptdata);
	         },
	         error: function() {
	             alert("error");
	         }
	         });
	     });
//End Here
		 
		 
// for profile page 
$("#myaccount").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "/over2cloud/view/Over2Cloud/hr/beforemyaccountPages.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});	



});



var addFlag=0, viewFlag=0, modifyFlag=0, deleteFlag=0;//variables to hold coming flags values in all js function
function group_add()
		{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeGroupHeaderView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function departmentView()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/commonModules/beforeDepartmentViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function organization(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/commonModules/beforeOrgAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function Country_add(){
	//alert("in eneter");
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		 url : "view/Over2Cloud/Compliance/Location/beforeCountryConfig.action",
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("errorPMS");
	}
	});
	
}

//Head Office mapping for employees
function HeadOffice_add(){
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		 url : "view/Over2Cloud/Compliance/Location/beforeHeadOfficeConfig.action",
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("errorPMS");
	}
	});
}

//Branch Office mapping for employees
function BranchOffice_add(){
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		 url : "view/Over2Cloud/Compliance/Location/beforebranchOfficeConfig.action",
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("errorPMS");
	}
	});
	
}
//For Sub Group
function contactMaster_add(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function userView(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/beforeUserViewHeader.action?empModuleFalgForDeptSubDept=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function working()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WorkingHrs/getHeaderBeforeView.action",
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

function configure_report(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigViewHeader.action?pageType=SC",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function sms_Draft_View(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	       type : "post",
	       url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/viewsmsdraftHeader.action",
	       success : function(data) {
	       	$("#data_part").html(data);
	      },
	     error: function() {
	            alert("error");
	        }
	 });
	}


function uploaddocument()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Help_Doc/beforeAddingDocument.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function helpdocumentmapping()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Help_Doc/beforeHelpDocumentView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


    function email_Draft_View(){
	  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/viewEmaildraftsHeader.action",
		    success : function(data)
		     {
		       	$("#data_part").html(data);
		     },
		    error: function() {
		            alert("error");
		        }
	 });
	}
    
//KR WORKS START HERE.............. 
    
     function krGroupHeaderView()
     {
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/KRLibraryOver2Cloud/groupHeaderKR.action",
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
    function libraryView()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadViewHeader.action",
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
    function schedule_planner()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityPlanner.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function advance_main()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAdvanceGridMain.action?dataFor=Accounts",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function advance()
    {
    	$("#result").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAdvanceGrid.action?dataFor=Accounts",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"result").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function advance_settlement()
    {
    	$("#result").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAdvanceGridSettlement.action?dataFor=Accounts",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"result").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function activity_plan()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
   /* 	*/
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeViewActivityPlan.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function map_employee_location()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/EmployeeLocation/beforeMapEmployee.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function approval_activity()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeApproveActivity.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function locStaff_dashboard(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforeDashboardViewLoc.action?",
    	    success : function(dashdata) {
           $("#"+"data_part").html(dashdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

     
    function patientActivityView(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "/over2cloud/view/Over2Cloud/patientActivity/viewPatientheader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function moreSettings()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "moreSettings.action",
    	    success : function(subdeptdata) {
    	   $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
    	        alert("error");
    	    }
    	 });

    }

    function ManualpullReport(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/Text2Mail/beforePullReportViewHeaderManual.action",
    	    success : function(data) 
    	    {
    			$("#"+"data_part").html(data);
    	    },
    	    error: function() 
    	    {
                alert("error");
            }
    	 });
     };
     
     
     
     function budget_planning()
     {
     	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     	$.ajax({
     	    type : "post",
     	    url : "view/Over2Cloud/patientCRMMaster/viewBudgetPlanningHeader.action",
     	    success : function(subdeptdata) {
     	
            $("#"+"data_part").html(subdeptdata);
     	},
     	   error: function() {
                 alert("error");
             }
     	 });
     }
 	
 	
 	 function allocate_budget()
     {
     	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     	$.ajax({
     	    type : "post",
     	    url : "view/Over2Cloud/patientCRMMaster/viewAllocateBudgetHeader.action",
     	    success : function(subdeptdata) {
     	
            $("#"+"data_part").html(subdeptdata);
     	},
     	   error: function() {
                 alert("error");
             }
     	 });
     }
 	 
 	 
 	function eventPlanning_addMaster(){

    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewEventPlanHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
 	}
     
     
     function corporate_add(){
    		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    		$.ajax({
    		    type : "post",
    		    url : "/over2cloud/view/Over2Cloud/WFPM/Patient/viewCorporateHeader.action",
    		    success : function(subdeptdata) {
    	       $("#"+"data_part").html(subdeptdata);
    		},
    		   error: function() {
    	            alert("error");
    	        }
    		 });
    	}
    /* function fetchQuestions()
     {
    	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    		$.ajax({
    		    type : "post",
    		    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/createQuestionConf.action",
    		    success : function(subdeptdata) {
    	       $("#"+"data_part").html(subdeptdata);
    		},
    		   error: function() {
    	            alert("error");
    	        }
    		 });
     }
     function fetchAnswers()
     {
    	// alert("Answers ::::::::::::: ");
    	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    		$.ajax({
    		    type : "post",
    		    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/createAnswerConf.action",
    		    success : function(subdeptdata) {
    	       $("#"+"data_part").html(subdeptdata);
    		},
    		   error: function() {
    	            alert("error");
    	        }
    		 });
     }*/
     
     
     

     function  relationship_addMaster(){
    		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    		$.ajax({
    		    type : "post",
    		    url : "view/Over2Cloud/patientCRMMaster/viewRelationshipMasterHeader.action",
    		    success : function(subdeptdata) {
    	       $("#"+"data_part").html(subdeptdata);
    		},
    		   error: function() {
    	            alert("error");
    	        }
    		 });
    }

    function  category_addMaster(){
    		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    		$.ajax({
    		    type : "post",
    		    url : "view/Over2Cloud/patientCRMMaster/viewCategoryMasterHeader.action",
    		    success : function(subdeptdata) {
    	       $("#"+"data_part").html(subdeptdata);
    		},
    		   error: function() {
    	            alert("error");
    	        }
    		 });
    } 
     
    function  source_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewSourceMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    } 

    function  adhocCrop_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewAdhocCorporateHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function  adhocAsso_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewAdhocAssociateHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }


    function  relationshipType_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewRelationshipHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function lostOpportunity(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewPatientLostMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    
    
    function configure_schedule()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewConfigureScheduleHeader.action",
    	    success : function(subdeptdata) {
    	
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    
    function configureCCTerr(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewConfigureLocationHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    
    function activity_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewActivityTypeMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
//viewEventPlanHeader
    function eventttt_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewEventConfigureHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    	
    }
    
    function expenseparameter_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewExpenseParameterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    function account_dept()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAccountsPage.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function account_dept_view()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAccountsView.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    

    function currency_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewCurrencyTypeMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });	
    }
// New merge 07Oct2015 
    function corporate_add(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewCorporateHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }



    function package_add(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewPackageHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }


    function package_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewPackageMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function service_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewServiceMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function speciality_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewSpecialityMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
      
    }


    function USGServices_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewUSGServicesMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }

    function  DocSchedule_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewDocScheduleHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    
    function patientWellnessReg(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeviewPatient.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata).css('overflow','auto');
		},
		   error: function() {
		            alert("error");
		        }
		 });
			}
    
    function budgetPlan_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewBudgetPlanHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });	
    }
    function activity_view()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityViewHeader.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    

    function kpi_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewKPIMasterHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });	
    }
    
    function expenseband_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewExpenseBandHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    
    function bandEmployee_addMaster(){
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",  
    	    url : "view/Over2Cloud/patientCRMMaster/viewBandEmployeeMappingHeader.action",
    	    success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
    	},
    	   error: function() {
                alert("error");
            }
    	 });
    }
    function extendApproval()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeViewActivityPlan.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    function mainPage()
    {
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/mainpageShow.action",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"data_part").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
    }
    
    function viewFeedbackActivityBoard()
	{
    	//alert("hdbgskjhgf");
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
			    success : function(subdeptdata) 
			    {
			    	$("#"+"data_part").html(subdeptdata);
			    },
			   error: function() 
			   {
				   alert("error");
			   }
			 });
		}
	
	function viewFeedbackActivityReport()
	{
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action?dataFor=report",
			    success : function(subdeptdata) 
			    {
			    	$("#"+"data_part").html(subdeptdata);
			    },
			   error: function() 
			   {
				   alert("error");
			   }
			 });
		}
	
	function viewAuditReport()
	{
		//alert("khbdfskj");
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/feedback/feedback_Activity/beforeAuditHeader.action",
			    success : function(subdeptdata) 
			    {
			    	$("#"+"data_part").html(subdeptdata);
			    },
			   error: function() 
			   {
				   alert("error");
			   }
			 });
		}
	
	 function escTypeConfig()
		{
		// alert("djbjgvkbdhvjbdf");
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/feedback/Escalation_Conf/beforeEscConf.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}		 

function ticketViewConfig()
{
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	 type : "post",
	 url : "view/Over2Cloud/feedback/Escalation_Conf/beforeTicketViewConf.action",
	 success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	     alert("error");
	 }
	});
}

function escDeptConfig()
{
 
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/Escalation_Conf/ViewEscDept.jsp",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}