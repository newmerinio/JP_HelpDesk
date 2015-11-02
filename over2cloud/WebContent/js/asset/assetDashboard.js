function refresh(div)
{
	if(div=='1stDataBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action?dataFor="+div+"&type=show",
		    success : function(data) {
			$("#"+div).html(data);
			$("#1stDataBlock").show();
			$("#1stBlock").hide();
		},
		   error: function() {
	            alert("error");
	        }
		 });	
	}
	else if (div=='2stBlock') 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#refreshDiv2nd").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
			 $("#refreshDiv3rd").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='refresh3stGraphBlock')
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#refresh3stGraphBlock").html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='refresh2stGraph' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#refresh2stGraph").html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='refresh1stGraph' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#refresh1stGraph").html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlockSupportPreventiveRefresh' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
		    success : function(subdeptdata) 
		    {
			 $("#"+div).html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlockSupportGraphRefresh' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=refresh",
		    success : function(subdeptdata) 
		    {
			 $("#"+div).html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlockPreventiveGraphRefresh' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=refresh",
		    success : function(subdeptdata) 
		    {
			 $("#"+div).html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='5stBlockRefresh' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAssetDataTicket.action?dataFor=Ticket",
		    success : function(subdeptdata) 
		    {
			 $("#"+div).html(subdeptdata);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function showDetails(div)
{
	if(div=='1stBlockGraph1' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='1stDataBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action?dataFor="+div+"&type=hide",
		    success : function(data) {
		    	 $("#maxmizeViewDialog").html(data);
			     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='2stBlock') 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='2stBlockGraph' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') 
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
		    	 $("#maxmizeViewDialog").html(subdeptdata);
			     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='3stBlockGraph1' )
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?dataFor="+div,
		    success : function(subdeptdata) 
		    {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
		    success : function(subdeptdata) {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	  }
	else if(div=='4stBlockSupport')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=maximize",
		    success : function(subdeptdata) {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	  }
	else if(div=='4stBlockPreventiveData')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=maximize",
		    success : function(subdeptdata) {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	  }
	else if(div=='5stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetDataTicket.action?dataFor=Ticket",
		    success : function(subdeptdata) {
			 $("#maxmizeViewDialog").html(subdeptdata);
		     $("#maxmizeViewDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	  }
}
function showDetailsPie(div)
{
	if(div=='1stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=1&dataFor="+div,
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='4stBlockSupportPie') 
	{
		$('#'+div).show();
		$('#4stBlock').hide();
		$('#4stBlockPreventivePie').hide();
	}
	else if (div=='4stBlockPreventivePie') 
	{
		$('#4stBlockSupportPie').hide();
		$('#4stBlock').hide();
		$('#4stBlockPreventivePie').show();
	}
}

function showDetailsData(div)
{
	if(div=='1stDataBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action?dataFor="+div+"&type=show",
		    success : function(data) {
			$("#"+div).html(data);
			$("#1stDataBlock").show();
			$("#1stBlock").hide();
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	if(div=='2stBlock')
	{
		$('#graphBar').hide();
		$('#2stBlock').show();
	}
	if(div=='3stBlock')
	{
		$('#graphBar1').hide();
		$('#3stBlock').show();
	}
	else if (div=='4stBlock') 
	{
		$("#4stBlock").show();
		$('#4stBlockSupportPie').hide();
		$('#4stBlockPreventivePie').hide();
	}
}

    var  sampleData =null;
    var  assetTypePerformance =null;
    var  assetUserPerformance =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/AssetOver2Cloud/Asset/jsonChartDataDept.action",
	    type : "json",
	    success : function(data) 
	    {
	    	sampleData = data;
	    	var settings = {

	    			title: 'Asset Score Sheet',
	                description: '',
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 50, top: 0, right: 0, bottom: 10 },
	                source: sampleData,
	                categoryAxis:
	                    {
	                        text: 'Category Axis',
	                        textRotationAngle: 0,
	                        dataField: 'AssetType',
	                        showTickMarks: true,
	                        tickMarksInterval: 1,
	                        tickMarksColor: '#888888',
	                        unitInterval: 1,
	                        showGridLines: false,
	                        gridLinesInterval: 1,
	                        gridLinesColor: '#888888',
	                        axisSize: 'auto'
	                    },
	                colorScheme: 'scheme01',
	                seriesGroups:
	                    [
	                        {
	                            type: 'stackedcolumn',
	                            columnsGapPercent: 100,
	                            seriesGapPercent: 5,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                description: 'Total Assets',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: [
	                                    { dataField: 'Total Allot', displayText: 'Total Allot' },
	                                    { dataField: 'Total Free', displayText: 'Total Free' }
	                                ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
            $('#jqxChart').jqxChart(settings);
            $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
            $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
            var refreshChart = function () {
                $('#jqxChart').jqxChart({ enableAnimations: false });
                $('#jqxChart').jqxChart('refresh');
            }
            // update greyScale values.
            $("#FlipValueAxis").on('change', function (event) {
                var groups = $('#jqxChart').jqxChart('seriesGroups');
                groups[0].valueAxis.flip = event.args.checked;
                refreshChart();
            });
            $("#FlipCategoryAxis").on('change', function (event) {
                var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
                categoryAxis.flip = event.args.checked;
                refreshChart();
            });
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });



	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/AssetOver2Cloud/Asset/jsonChartAssetTypeRank.action",
	    type : "json",
	    success : function(data) {
		assetTypePerformance = data;
	    	var settings = {

	    			title: 'Asset Type Performance Rank',
	                description: '',
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 50, top: 0, right: 0, bottom: 10 },
	                source: assetTypePerformance,
	                categoryAxis:
	                    {
	                        text: 'Category Axis',
	                        textRotationAngle: 0,
	                        dataField: 'AssetType',
	                        showTickMarks: true,
	                        tickMarksInterval: 1,
	                        tickMarksColor: '#888888',
	                        unitInterval: 1,
	                        showGridLines: false,
	                        gridLinesInterval: 1,
	                        gridLinesColor: '#888888',
	                        axisSize: 'auto'
	                    },
	                colorScheme: 'scheme01',
	                seriesGroups:
	                    [
	                        {
	                            type: 'stackedcolumn',
	                            columnsGapPercent: 100,
	                            seriesGapPercent: 5,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                description: 'Total Break Down',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: [
	                                    { dataField: 'TotalBreakScore', displayText: 'Total Break Score'}
	                                   
	                                ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
            $('#jqxChartAstPermance').jqxChart(settings);
            $("#FlipValueAxis1").jqxCheckBox({ width: 120,  checked: false });
            $("#FlipCategoryAxis1").jqxCheckBox({ width: 120,  checked: false });
            var refreshChart = function () {
                $('#jqxChartAstPermance').jqxChart({ enableAnimations: false });
                $('#jqxChartAstPermance').jqxChart('refresh');
            }
            // update greyScale values.
            $("#FlipValueAxis1").on('change', function (event) {
                var groups = $('#jqxChartAstPermance').jqxChart('seriesGroups');
                groups[0].valueAxis.flip = event.args.checked;
                refreshChart();
            });
            $("#FlipCategoryAxis1").on('change', function (event) {
                var categoryAxis = $('#jqxChartAstPermance').jqxChart('categoryAxis');
                categoryAxis.flip = event.args.checked;
                refreshChart();
            });
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/AssetOver2Cloud/Asset/jsonChartUserTypeRank.action",
	    type : "json",
	    success : function(data) {
		assetUserPerformance = data;
	    	var settings = {

	    			title: 'User Performance Rank',
	                description: '',
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 50, top: 0, right: 0, bottom: 10 },
	                source: assetUserPerformance,
	                categoryAxis:
	                    {
	                        text: 'Category Axis',
	                        textRotationAngle: 0,
	                        dataField: 'EmpName',
	                        showTickMarks: true,
	                        tickMarksInterval: 1,
	                        tickMarksColor: '#888888',
	                        unitInterval: 1,
	                        showGridLines: false,
	                        gridLinesInterval: 1,
	                        gridLinesColor: '#888888',
	                        axisSize: 'auto'
	                    },
	                colorScheme: 'scheme01',
	                seriesGroups:
	                    [
	                        {
	                            type: 'stackedcolumn',
	                            columnsGapPercent: 100,
	                            seriesGapPercent: 5,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                description: 'Total Break Down',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: [
	                                    { dataField: 'TotalBreakScore', displayText: 'Total Break Score'}
	                                   
	                                ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
            $('#jqxChartUserPermance').jqxChart(settings);
            $("#FlipValueAxis2").jqxCheckBox({ width: 120,  checked: false });
            $("#FlipCategoryAxis2").jqxCheckBox({ width: 120,  checked: false });
            var refreshChart = function () {
                $('#jqxChartUserPermance').jqxChart({ enableAnimations: false });
                $('#jqxChartUserPermance').jqxChart('refresh');
            }
            // update greyScale values.
            $("#FlipValueAxis2").on('change', function (event) {
                var groups = $('#jqxChartUserPermance').jqxChart('seriesGroups');
                groups[0].valueAxis.flip = event.args.checked;
                refreshChart();
            });
            $("#FlipCategoryAxis2").on('change', function (event) {
                var categoryAxis = $('#jqxChartUserPermance').jqxChart('categoryAxis');
                categoryAxis.flip = event.args.checked;
                refreshChart();
            });
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });

function showDetailsBar(divBlock)
{
	if (divBlock=='1stBlock') 
	{
		$('#1stDataBlock').hide();
	} 
	else if(divBlock=='graphBar')
	{
		$('#2stBlock').hide();
	}
	else
	{
		$('#3stBlock').hide();	
	}
	$('#'+divBlock).show();
}
function showAssetDetails(deptId,dataFor,type)
{
	if(dataFor=='Total'){
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Total Asset'});
		}
	if(dataFor=='Free'){
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Free Asset'});
		}
	if(dataFor=='Allot'){
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Alloted Asset'});
		}
	if(dataFor=='performanceRank'){
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Performance Rank'});
		}
	if(dataFor=='User'){
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'User Rank'});
		}
	$("#dataDialog").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllAssetDetails.action?deptId="+deptId+"&dataFor="+dataFor+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog").dialog('open');
			$("#dataDialog").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function showSuportDetails(deptId,dataFor,type)
{
	if (type=='Support') {
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Support Status'});
	}
	if (type=='Preventive') {
		$("#dataDialog").dialog({width: 1050 , height: 500,title: 'Preventive Status'});
	}
	
	$("#dataDialog").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetails.action?deptId="+deptId+"&dataFor="+dataFor+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog").dialog('open');
			$("#dataDialog").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}


function showAllAssetReminder(reminderId,parameterName,moduleName)
{
	if (moduleName=='Support') {
		$("#dataDialog").dialog({width: 900 , height: 450,title: 'Support Status'});
	}
	if (moduleName=='Preventive') {
		$("#dataDialog").dialog({width: 900 , height: 450,title: 'Preventive Status'});
	}
	$("#dataDialog").dialog('open');
	$("#dataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetails.action",
	    data: "reminderId="+reminderId+"&dataFor="+parameterName+"&moduleName="+moduleName+"",
	    success : function(data) 
	    {
			$("#dataDiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getSupportPreventiveData(reminderId)
{
	$("#remindDialog").dialog('open');
	$("#remindDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetails.action",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetailsWithA4Sheet.action",
	    data: "reminderId="+reminderId+"",
	    success : function(data) 
	    {
			$("#remindDataDiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getOnClickDataForTicket(outletId,dataFor)
{
	$("#dataDialog").dialog('open');
	$("#dataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllTicketDetails.action",
	    data: "outletId="+outletId+"&status="+dataFor+"",
	    success : function(data) 
	    {
			$("#dataDiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getPendingTicketData(dataFor,feedID)
{
	$("#dataDialog").dialog('open');
	$("#dataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllTicketDetails.action",
	    data: "feedId="+feedID+"&status="+dataFor+"",
	    success : function(data) 
	    {
			$("#dataDiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	
}

function getNextPreviousData(dataFor)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getSupprotPMById.action",
		data: "nextOrPrevious="+dataFor,
	    success : function(data) {
       		$("#overlapAll").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
$.subscribe('removeSessionValue', function(event,data)
		{
		$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/removeSessionValue.action",
		data: "removeSession4=reminder",
		    success : function(data) {
		    	
		    },
		   error: function() {
		        alert("error");
		    }
		 });
});

function reminderAction()
{
	$.ajax({
		
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getSuprtPMSessionValue.action",
	    success : function(data) {
       		$("#actionData").html(data);
       		$("#makeItHidden").hide();
       		$("#action").hide();
		},
	    error: function() {
            alert("error");
        }
	 });
}

