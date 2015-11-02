$(document).ready(function(){
	
	$("#locationmappingView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/emplocationgatemapping.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#frontOfficeView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeFrntOfficeView.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#visitorentryView").click(function(){
		var app_name = 'VAM';
		var oneWeekAgo = new Date();
		var currDate = new Date();
		oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
		oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
		currDate.setMonth(currDate.getMonth()+1);
		var fromDate;
		var toDate;
		if(oneWeekAgo.getMonth() < 10)
		{
		    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}
		else
		{
		    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}

		if(currDate.getMonth() < 10)
		{
		    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		else
		{
		    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&selectedId=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#visitorexitid").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/visitorexit.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#visitormisgridView").click(function(){
		var app_name = 'VAM';
		var oneWeekAgo = new Date();
		var currDate = new Date();
		oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
		oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
		currDate.setMonth(currDate.getMonth()+1);
		var fromDate;
		var toDate;
		if(oneWeekAgo.getMonth() < 10)
		{
		    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}
		else
		{
		    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}

		if(currDate.getMonth() < 10)
		{
		    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		else
		{
		    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/beforevisitormisgridview.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&from_date="+fromDate+"&to_date="+toDate,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#acknowledgepostView").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeacknowledgepostAdd.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#addacknowledgepostId").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/acknowledgepostadd.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#acknowledgepostAddCancel").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeacknowledgepostAdd.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#vehicleEntryView").click(function(){
		var app_name = 'VAM';
		var oneWeekAgo = new Date();
		var currDate = new Date();
		oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
		oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
		currDate.setMonth(currDate.getMonth()+1);
		var fromDate;
		var toDate;
		if(oneWeekAgo.getMonth() < 10)
		{
		    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}
		else
		{
		    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
		}

		if(currDate.getMonth() < 10)
		{
		    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		else
		{
		    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
		}
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&selectedId=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#addVehicleId").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/vehicleEntry.action?modifyFlag=0&deleteFlag=0&vehicleExitStatus=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#vehicleAddCancel").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforevehicleEntry.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#vehicleExitid").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/vehicleExitAdd.action?modifyFlag=0&deleteFlag=0&vehicleExitStatus=1",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
	
	$("#vehiclemisgridView").click(function(){
		var app_name = 'VAM';
			var oneWeekAgo = new Date();
			var currDate = new Date();
			oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
			oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
			currDate.setMonth(currDate.getMonth()+1);
			var fromDate;
			var toDate;
			if(oneWeekAgo.getMonth() < 10)
			{
			    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
			}
			else
			{
			    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
			}

			if(currDate.getMonth() < 10)
			{
			    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
			}
			else
			{
			    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
			}
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/beforevehiclemisgridview.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&from_date="+fromDate+"&to_date="+toDate,
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		$("#vehiclemissearchbackid").click(function(){
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/vehiclemisdataView.action?modifyFlag=0&deleteFlag=0",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		$("#summaryreportView").click(function(){
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/summaryrepdataView.action?modifyFlag=0&deleteFlag=0",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		$("#addsummaryrepId").click(function(){
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/addsummaryrepData.action?modifyFlag=0&deleteFlag=0",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		$("#summaryrepaddCancel").click(function(){
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/summaryrepdataView.action?modifyFlag=0&deleteFlag=0",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		 $("#productivityViewId").click(function(){
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/beforeproductivityreport.action?modifyFlag=0&deleteFlag=0",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		});
		 $("#visitorstatus").click(function(){
				var visitor_status = "visitorstatus";
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorInOut.action?modifyFlag=0&deleteFlag=0"+"&visitor_status="+visitor_status,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			
			$("#visitorexitid").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/visitorexit.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});	
			
			$("#visitorprerequestView").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforePreRequestRecords.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#addPreRequestId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/addPreRequest.action",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#preRequestAddCancel").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforePreRequestRecords.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#instantvisitorView").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#instantActionCancel").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforeinstantVisitorAdd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#visitorReportView").click(function(){
				var app_name = 'VAM';
				var oneWeekAgo = new Date();
				var currDate = new Date();
				oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
				oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
				currDate.setMonth(currDate.getMonth()+1);
				var fromDate;
				var toDate;
				if(oneWeekAgo.getMonth() < 10)
				{
				    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
				}
				else
				{
				    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
				}

				if(currDate.getMonth() < 10)
				{
				    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
				}
				else
				{
				    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
				}
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforeVisitorReportView.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&from_date="+fromDate+"&to_date="+toDate,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#postacknowledgeView").click(function(){
				var app_name = 'VAM';
				var oneWeekAgo = new Date();
				var currDate = new Date();
				oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
				oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
				currDate.setMonth(currDate.getMonth()+1);
				var fromDate;
				var toDate;
				if(oneWeekAgo.getMonth() < 10)
				{
				    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
				}
				else
				{
				    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
				}

				if(currDate.getMonth() < 10)
				{
				    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
				}
				else
				{
				    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
				}
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforepostAckreportView.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&from_date="+fromDate+"&to_date="+toDate,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#totalreportview").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorreportmenuview.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#visitormisView").click(function(){
				var visitorMIS = "MIS";
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforevisitorreportview.action?modifyFlag=0&deleteFlag=0"+"&visitorMIS="+visitorMIS,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			
			$("#visitorreportID").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforevisitorreportview.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#cancelVisitorReportId").click(function(){
				var visitorMIS = "MIS";
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/beforevisitorreportview.action?modifyFlag=0&deleteFlag=0"+"&visitorMIS="+visitorMIS,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#visitorPhotoSearchID").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorphotosearchview.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#scheduledvisitorreportid").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/scheduledvisitorreport.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#cancelscheduledvisitorview").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorreportmenuview.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#visitormovementID").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorInOut.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#stillInVisitorId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorIn.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#outVisitorId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorOut.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#cancelVisitorMovementId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorreportmenuview.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#cancelVisitorINId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorInOut.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#cancelVisitorOUTId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/reports/visitorInOut.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});	
			$("#gateLocationView").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforeGateLocationRecords.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#addgateLocationId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/addGateLocation.action",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#gateAddCancel").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforeGateLocationRecords.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#purposeAdmin").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforePurposeRecords.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			
			$("#purposeAddCancel").click(function(){
						$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
						$.ajax({
						    type : "post",
						    url : "view/Over2Cloud/VAM/master/beforePurposeRecords.action?modifyFlag=0&deleteFlag=0",
						    success : function(subdeptdata) {
					       $("#"+"data_part").html(subdeptdata);
						},
						   error: function() {
					            alert("error");
					        }
						 });
					});
			
			$("#addPurposeId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/addPurpose.action",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#vendertypeView").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforevendorTypeAdd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			
			$("#visitoridseriesView").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforeidseriesAdd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#addidseriesId").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/idseriesadd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			$("#idSeriesCancel").click(function(){
				$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/beforeidseriesAdd.action?modifyFlag=0&deleteFlag=0",
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			});
			 $("#vam_dashboard_view").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
						type : "post",
						url : "view/Over2Cloud/VAM/master/dashboardVAM.action",
						success : function(data){
							$("#data_part").html(data);
						},
						error : function(){
							alert("error");
						}
					});
					
				});
			 $("#vehicle_dashboard_view").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
						type : "post",
						url : "view/Over2Cloud/VAM/master/vehicledashboardVAM.action",
						success : function(data){
							$("#data_part").html(data);
						},
						error : function(){
							alert("error");
						}
					});
					
				});
			 
			 $("#addVisitorId").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/addNewVisitor.action",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				$("#visitorAddCancel").click(function(){
					var app_name = 'VAM';
						var oneWeekAgo = new Date();
						var currDate = new Date();
						oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
						oneWeekAgo.setMonth(oneWeekAgo.getMonth()+1);
						currDate.setMonth(currDate.getMonth()+1);
						var fromDate;
						var toDate;
						if(oneWeekAgo.getMonth() < 10)
						{
						    fromDate = oneWeekAgo.getDate()+"-0"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
						}
						else
						{
						    fromDate = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
						}
	
						if(currDate.getMonth() < 10)
						{
						    toDate = currDate.getDate()+"-0"+currDate.getMonth()+"-"+currDate.getFullYear();
						}
						else
						{
						    toDate = currDate.getDate()+"-"+currDate.getMonth()+"-"+currDate.getFullYear();
						}
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?modifyFlag=0&deleteFlag=0&app_name="+app_name+"&from_date="+fromDate+"&to_date="+toDate,
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				$("#addemplocgatemap").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/emplocationgatemappingadd.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				$("#emplocgateMapAddCancel").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/emplocationgatemapping.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				}); 
				$("#addvendorId").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/vendorDetailadd.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				$("#vendorAddCancel").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/beforevendorTypeAdd.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				$("#addVenderId").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/addVender.action",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});
				
				$("#venderAddCancel").click(function(){
					$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/beforeFrntOfficeView.action?modifyFlag=0&deleteFlag=0",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				});	
});
 function resetColor(id)
            {    
                     var mystring = $(id).text();
                    var fieldtype = mystring.split(",");
                    for(var i=0; i<fieldtype.length; i++)
                    {        
                        var fieldsvalues = fieldtype[i].split("#")[0];
                        $("#"+fieldsvalues).css("background-color","");
                }
            }
            function setVisitorDownloadType(type){
    var from_date = $("#from_date").val();
    var to_date = $("#to_date").val();
    var downloadactionurl="downloadVisitorreport";
    $("#downloaddaildetails").load("view/Over2Cloud/VAM/download/visitorexceldownload.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&from_date="+from_date+"&to_date="+to_date);
    $("#downloaddaildetails").dialog('open');
}  