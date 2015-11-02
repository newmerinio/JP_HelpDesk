<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>jqxChart Pie Series Example</title>
    <link rel="stylesheet" href="css/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            // prepare the data
            var data = [
                { "month": "Jan", "min": -1.9, "max": 3.7, "avg": 0.2 },
                { "month": "Feb", "min": -0.9, "max": 5.9, "avg": 1.1 },
                { "month": "Mar", "min": 0.8, "max": 9.8, "avg": 4.9 },
                { "month": "Apr", "min": 4.1, "max": 13.9, "avg": 8.7 },
                { "month": "May", "min": 8.0, "max": 18.4, "avg": 13.1 },
                { "month": "Jun", "min": 11.3, "max": 22.2, "avg": 16.6 },
                { "month": "Jul", "min": 13.3, "max": 25.3, "avg": 18.4 },
                { "month": "Aug", "min": 13.0, "max": 24.4, "avg": 17.6 },
                { "month": "Sep", "min": 10.3, "max": 20.8, "avg": 14.3 },
                { "month": "Oct", "min": 6.6, "max": 14.9, "avg": 9.2 },
                { "month": "Nov", "min": 2.1, "max": 8.4, "avg": 4.2 },
                { "month": "Dec", "min": -0.5, "max": 4.5, "avg": 1.5 }
                ];
            var toolTipCustomFormatFn = function (value, itemIndex, serie, group, categoryValue, categoryAxis) {
                var dataItem = data[itemIndex];
                return '<DIV style="text-align:left"><b>Month: ' +
                        categoryValue + '</b><br />Min: ' +
                        dataItem.min + '�<br />Max: ' +
                        dataItem.max + '�<br />Average: ' + 
                        dataItem.avg + '�</DIV>';
            };
            // prepare jqxChart settings
            var settings = {
                title: "Feedback Got For Whole Year",
                description: "Positive,Negative and Average",
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                enableCrosshairs: true,
                source: data,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'month',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: true,
                        gridLinesInterval: 3,
                        gridLinesColor: '#888888'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'rangecolumn',
                            columnsGapPercent: 100,
                            toolTipFormatFunction: toolTipCustomFormatFn,
                            valueAxis:
                            {
                                unitInterval: 5,
                                displayValueAxis: true,
                                description: 'Counters [C]',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                minValue: -5,
                                maxValue: 30
                            },
                            series: [
                                    { dataFieldTo: 'max', displayText: 'Feedback Types', dataFieldFrom: 'min', opacity: 1 }
                                ]                        
                        },
                        {
                            type: 'spline',
                            toolTipFormatFunction: toolTipCustomFormatFn,
                            valueAxis:
                            {
                                unitInterval: 5,
                                displayValueAxis: false,
                                minValue: -5,
                                maxValue: 30
                            },
                            series: [
                                    { dataField: 'avg', displayText: 'Averag Feedback', opacity: 1, lineWidth: 2 }
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#chartContainer').jqxChart(settings);
        });
    </script>
</head>
<body class='default'>
    <div id='chartContainer' style="width:850px; height:500px">
    </div>    
</body>
</html>