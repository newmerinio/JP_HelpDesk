<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
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
        $('#jqxChart1st').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#jqxChart1st').jqxChart({ enableAnimations: false });
            $('#jqxChart1st').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#jqxChart1st').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#jqxChart1st').jqxChart('categoryAxis');
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
        $('#jqxChartAstPermance2st').jqxChart(settings);
        $("#FlipValueAxis1").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis1").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#jqxChartAstPermance2st').jqxChart({ enableAnimations: false });
            $('#jqxChartAstPermance2st').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis1").on('change', function (event) {
            var groups = $('#jqxChartAstPermance2st').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis1").on('change', function (event) {
            var categoryAxis = $('#jqxChartAstPermance2st').jqxChart('categoryAxis');
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
        $('#jqxChartUserPermance3st').jqxChart(settings);
        $("#FlipValueAxis2").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis2").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#jqxChartUserPermance3st').jqxChart({ enableAnimations: false });
            $('#jqxChartUserPermance3st').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis2").on('change', function (event) {
            var groups = $('#jqxChartUserPermance3st').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis2").on('change', function (event) {
            var categoryAxis = $('#jqxChartUserPermance3st').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
},
   error: function() {
        alert("error");
    }
 });
</SCRIPT>
</head>
<body>
<s:if test="dataFor=='1stBlockGraph1' || dataFor=='refresh1stGraph'">
<div id='jqxChart1st' style="width: 90%; height: 200px; display: block;" align="center" ></div>
</s:if>
<s:if test="dataFor=='2stBlockGraph' || dataFor=='refresh2stGraph'">
<div id='jqxChartAstPermance2st' style="width: 90%; height: 200px" align="center"></div>
</s:if>
<s:if test="dataFor=='3stBlockGraph1' || dataFor=='refresh3stGraphBlock'">
<div id='jqxChartUserPermance3st' style="width: 90%; height: 200px" align="center"></div>
</s:if>
</body>
</html>