<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>jqxChart Pie Series Example</title>
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            // prepare chart data as an array
            var source =
            {
                datatype: "csv",
                datafields: [
                    { name: 'Browser' },
                    { name: 'Share' }
                ],
                url: 'js/text.txt'
            };

            var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });

            // prepare jqxChart settings
            var settings = {
                title: "",
                description: "",
                enableAnimations: true,
                showLegend: false,
                showBorderLine: false,
                legendPosition: { left: 50, top: 20, width: 50, height: 50 },
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
                source: dataAdapter,
                colorScheme: 'scheme02',
                seriesGroups:
                    [
                        {
                            type: 'pie',
                            showLabels: false,
                            series:
                                [
                                    { 
                                        dataField: 'Share',
                                        displayText: 'Browser',
                                        labelRadius: 50,
                                        initialAngle: 5,
                                        radius: 50,
                                        centerOffset: 0,
                                        formatSettings: { sufix: '%', decimalPlaces: 1 }
                                    }
                                ]
                        }
                    ]
            };

            // setup the chart
            $('#pieNegFeedbackChart').jqxChart(settings);
            $("#pieNegFeedbackChart").jqxCheckBox({ width: 50,  checked: false });
        });
    </script>
</head>
<body >
    <div id='host' style="margin: 0 auto; width: 200px; height: 150px;">
        <div id='pieNegFeedbackChart' style="width: 200px; height: 150px; position: relative; left: 0px;
            top: 0px;">
        </div>
    </div>
</body>
</html>
