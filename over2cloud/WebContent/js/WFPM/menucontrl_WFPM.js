//All menucontrol functions for WFPM
//Start: javascript functions

function showLeadView(reVal)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action?status="+reVal+"&mainHeaderName=Lead",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function showClientView(refVal)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient="+refVal+"&mainHeaderName=Client",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function showAssociateView(refVal)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateView.action?isExistingAssociate="+refVal+"&mainHeaderName=Associate",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function showReportView(userID, mode) 
{
	//mode = ONLICE, SMS, BOTH
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/beforeDSRView.action",
	    data: "userID="+userID+"&mode="+mode,
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function showOfferingDSR(userID, mode)
{
	//alert(userID+"####"+mode);
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/beforeOfferingWise.action",
	    data: "userID="+userID+"&mode="+mode,
	    success : function(subdeptdata) {
     $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
          alert("error");
      }
	 });
}

$(document).ready(function(){
	
$("#showWFPMCommonDashboard").click(function(){
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
$.ajax({
type : "post",
url  : "view/Over2Cloud/wfpm/dashboard/beforeCommonDashboard.action",
data : "currDate=0",
success : function(data){
	$("#data_part").html(data);
},
error   : function(){
	alert("error");
}
});
});

$("#showLeadDashboard").click(function(){
	leadDashboard();
	});

	$("#showClientDashboard").click(function(){
	clientDashboard();
	});

	$("#showAssociateDashboard").click(function(){
	associateDashboard();
});
	
	//Lead links
	$("#leadAction").click(function(){
		showLeadView(0);
	});
	$("#leadTakeActionID").click(function(){
		showLeadView(0);
	});
	$("#leadLostLeadID").click(function(){
		showLeadView(4);
	});

	//Client links
	$("#pclientView").click(function(){
		showClientView(0);
	});

	$("#clientTakeActionID").click(function(){
		showClientView(0);
	});

	$("#clientExistingID").click(function(){
		showClientView(1);
	});

	$("#clientLostClientID").click(function(){
		showClientView(2);
	});
	
	//Associate links
	$("#pAssociateView").click(function(){
		showAssociateView(0);
	});
	$("#associateTakeActionID").click(function(){
		showAssociateView(0);
	});
	$("#associateExistingID").click(function(){
		showAssociateView(1);
	});
	$("#associateLostAssociateID").click(function(){
		showAssociateView(2);
	});

	//KPI
	$("#kpiGraph").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/beforeDashboardView.action",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	//Offering
	$("#offeringGraph").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/offeringDashboardView.action",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	$("#ivrsView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/beforeIVRSMainPage.action",    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});	
	
	$("#calculateSalary").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Salary/beforeCalculation.action",
		    data: "mainHeaderName=Salary Calculation",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	//Target vs Achievement report
	$("#kpiTargAchiID").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	type:"post",
	url:"view/Over2Cloud/WFPM/report/DSR/beforeKPIMainPage.action",
	success:function(data){
		$("#data_part").html(data);
	},
	error:function(){
		alert("error");
	}
	});	
	});
	
	$("#offeringTargAchiID").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		type:"post",
		url:"view/Over2Cloud/WFPM/report/DSR/beforeOfferingMainPage.action",
		success:function(data){
			$("#data_part").html(data);
		},
		error:function(){
			alert("error");
		}
		});	
		});
	
	$("#kra_kpiView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/hr/beforekra_kpiView.action",
		    success : function(subdeptdata) {
		   $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		        alert("error");
		    }
		 });
		});
	
	$("#mappedkra_kpiView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/hr/beforeKRAKPIView.action",
		    success : function(subdeptdata) {
		   $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		        alert("error");
		    }
		 });
		});
	
	// Start By Sanjiv on 15-05-2014.
	$("#incentiveView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/incentive/beforeIncentiveMainView.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#targetView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/target/beforeTargetView.action",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	//Target Add and View and Modify
	$("#obdTarget").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({  
		    type : "post",
		    url : "view/Over2Cloud/wfpm/target/beforeOBDTarget.action?headerName= OBD Target >> View",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	});

	$("#empAttend").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Salary/beforeAttendance.action",
		    data: "mainHeaderName=Attendance",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});

	$("#salaryConfig").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Salary/beforeSalaryConfig.action",
		    data: "mainHeaderName=Salary Configuration",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	//Anoop, User wise report 12-12-2013
	$("#userHierarchyReport").click(function(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/userHierarchy/beforeUserHierarchy.action",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	});
	
	$("#lostOperView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_lost_master&mapSubTable=lost_master&moduleName=Lost Opportunity&dataTable=lostoportunity&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	$("#clientStatusView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_client_status_master&mapSubTable=client_status_master&moduleName=Client Status&dataTable=client_status&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	
	$("#weightageView").click(function(){
		  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");	
			$.ajax({
				    type : "post",
				    url  : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_weightage_master&mapSubTable=weightage_master&moduleName=Weightage&dataTable=weightagemaster&status=Active",
				    success : function(subdeptdata){
				          $("#"+"data_part").html(subdeptdata);
			        },
			        error: function(){
			        	alert("error");
			         }
			       });
			
	 });

	$("#locationView").click(function(){
		  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");	
			$.ajax({
				    type : "post",
				    url  : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_location_master&mapSubTable=location_master&moduleName=Location&dataTable=location&modifyFlag=0&deleteFlag=0&status=Active",
				    success : function(subdeptdata){
				          $("#"+"data_part").html(subdeptdata);
			        },
			        error: function(){
			        	alert("error");
			         }
			       });
			
	 });
	$("#forcastcategaryView").click(function(){
		 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		  $.ajax({
			  type : "post",
			  	url  : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_forcastcategary_master&mapSubTable=forcastcategary_master_configuration&moduleName=Forcast Categary&dataTable=forcastcategarymaster&status=Active",
			    success : function(subdeptdata){
			          $("#"+"data_part").html(subdeptdata);
		        },
		        error: function(){
		        	alert("error");
		         }
			    
			  });
		
	});
	$("#salesstageView").click(function(){
		 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		  $.ajax({
			  type : "post",
			  url  : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_salesstage_master&mapSubTable=salesstage_master_configuration&moduleName=Sales Stage&dataTable=salesstagemaster&status=Active",
			    success : function(subdeptdata){
			          $("#"+"data_part").html(subdeptdata);
		        },
		        error: function(){
		        	alert("error");
		         }
			  
			  });
		
	});
	
   	$("#associateStatusView").click(function(){
    $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?modifyFlag=0&deleteFlag=0&mappingTable=mapped_associate_status_master&mapSubTable=associate_status_master&moduleName=Associate Status&dataTable=associatestatus&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	$("#associateTypeView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_type_master&mapSubTable=associate_type_master&moduleName=Associate Type&dataTable=associatetype&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	$("#associateCategoryView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_category_master&mapSubTable=associate_category_master&moduleName=Associate Category&dataTable=associatecategory&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	/**
	* @author Anurag
	* SMS Template
	* */

	$("#smsTemplateKPI").click(function(){
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeViewSMSTemplate.action",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	});
	
	$("#smsTemplateOffering").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeViewSMSTemplateOffring.action",
		    success : function(subdeptdata) {
		   $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		        alert("error");
		    }
		 });
		});
	
	// summary report....
	$("#summaryReportView").click(function(){alert("Summary");
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/CRM/beforeViewSummaryReport.action",
		    success : function(subdeptdata) {
		   $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		        alert("error");
		    }
		 });
		});
	$("#sourceMasterView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=Source&dataTable=sourcemaster&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	$("#offeringView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingView.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
		});
	
	//19-02-2014 Gudiya Industry
	$("#industryView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryView.action",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error>>>>>>>>>>");
		        }
		 });
		});
	
	$("#targetSegmentView").click(function(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/industry/beforeTargetSegmentView.action",
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	});
	
	// Target for offering and KPI
	$("#target_WFPM").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/target/beforeTargetMainView.action",
			data : "targetType=0",
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	});

	$("#mapKraKpi_WFPM").click(function(){
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
	});
	
	//KPI auto
	$("#kpi_autoFill").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/WFPM/report/DSR/showGraph.action",
				    success : function(subdeptdata) {
					$("#data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});	
	
	//Offering auto
	$("#offering_autoFill").click(function()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/showGraphOffering.action",
		    success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#opportunity_report_View").click(function(){
		var acManager="";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/beforeopportunityreportView.action?modifyFlag=0&deleteFlag=0&acManager="+acManager,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#communication_activity_WFPM").click(function()
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
	});
	
	$("#dynamic_mailtag_WFPM").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
				$.ajax({
					type : "post",
					url : "view/Over2Cloud/WFPM/CRM/beforeMailTagPage.action",
					success : function(subdeptdata) {
					$("#data_part").html(subdeptdata);
				},
				error: function() {
					alert("error");
				}
				});
			});
	
	// for rich_mailtext_WFPM
	$("#rich_mailtext_WFPM").click(function()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
				$.ajax({
					type : "post",
					url : "view/Over2Cloud/WFPM/CRM/beforeMailEditor.action",
					success : function(subdeptdata) {
					$("#data_part").html(subdeptdata);
				},
				error: function() {
					alert("error");
				}
				});
			});
	
	$("#communication_WFPM").click(function()
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
			});
			
	$("#group_WFPM").click(function()
			{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeCRMGroupPage.action",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
			});
	
	$("#reports_WFPM").click(function()
			{
			
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/report/DSR/beforinstantcrmmailreport.action",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
			});
	
	
	$("#showLeadDashboard").click(function(){
	leadDashboard();
	});

	$("#showClientDashboard").click(function(){
	clientDashboard();
	});

	$("#showAssociateDashboard").click(function(){
	associateDashboard();
});

	//For User History.....
	
	$("#user_history_View").click(function()
			{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeUserActionHistoryView.action",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
			});
	$("#opportunityDetails").click(function()
			{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/client/beforeopportunityDetails.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
			});
	
	$("#clientSupportView").click(function()
			{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/clientsupport/beforeclientDetails.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
			});
	$("#viewSupportType_WFPM").click(function()
			{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/clientsupport/beforeviewSupportType.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
			});
	// patientStatusId
	$("#patientStatusId").click(function patientStatusId(){
	//alert("One ");
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/beforePatientStatus.action",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	});
	
	$("#patientWFPM").click(function(){
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
			});
	
$("#relationshipMgrView").click(function(){
		
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeviewManager.action",
		    
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
			});
	
	
});
//End of jquery functions

//Start: javascript functions
function leadDashboard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/WFPM/Lead/dashboardView.action",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	alert("error");
	}
	});
}

function clientDashboard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/wfpm/client/clientDashboard.action",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	alert("error");
	}
	});
}

function associateDashboard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/WFPM/Associate/associateDashboard.action",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	alert("error");
	}
	});	
}
function patientVisitView(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/viewVisitheader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function questionairView()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/viewQuestionConf.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}


function viewFeedbackAll(){
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/headFeedbackAllDetails.jsp",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function mapDoctorOfferingView()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeDoctorOfferingMap.jsp",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function patientReportView()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforewellnessreport.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function profileView()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeprofileView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function referralView()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralView.action",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function calendarView()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/referral/calendar.jsp",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function calendarViewManager()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/referral/calendarManager.jsp",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function dashboardPatientActivity()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/patientActivityDashboard.action?",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}
