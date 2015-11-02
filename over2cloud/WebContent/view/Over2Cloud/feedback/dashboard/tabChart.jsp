<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

showMore2('barChart');
//choosePie();
//displayClusterMetrics();
//showBarChart('deptBarChart');


</script>
</head>
<body>

				<div  style="float:left;overflow: auto;border:1px solid #e1e1e1; margin-top: 5px;width: 40%;height: 340px;">
					
					<div id="choosePie"></div>
				</div>
				<div  style="float:left;overflow: auto;border:1px solid #e1e1e1; margin-top: 5px;width: 58%;height: 340px;">
													 <div style="float: right; width: auto; padding: 0px 4px 0 0;">
															<a href="#" onclick="showMore2('barChart');"> <img
																src="images/Economics.png" width="25" height="20"
																alt="Show Bar Graph" title="Bar Graph" border="1" />
															</a>
														</div>
														<div style="float: right; width: auto; padding: 0px 4px 0 0;">
															<a href="#" onclick="NegativeFeedAnlysisPie();"> <img
																src="images/Pie-chart-icon.png" width="20" height="20"
																alt="Show Counters" title="Pie Chart" />
															</a>
														</div>
					<div id="barChart"></div>
				</div>
				<div style="float:left; overflow: auto;border:1px solid #e1e1e1; margin-top: 5px;width: 40%;height: 200px;">
					<div id="DognutGrpChart"> <center><table> <tr><td>Mode :</td><td><div id='dropDownColors223'></div></td></tr></table></center>
					<table width="100%" height="200px">
									<tr  bgcolor="#F5F1F1" >
										<td> <div id="RatingRem1" style="width: 240px; height: 180px;"></div> </td>
										<td><div id="RatingRem2" style="width: 240px; height: 180px;"></div></td>
									</tr>
					</table>
					</div>
				</div>
				<div  style="float:left;overflow: scroll;border:1px solid #e1e1e1; margin-top: 5px;width: 58%;height: 200px;">
					<div id="deptBarChart" style="height: 200px;"></div>
				</div>
				
</body>
</html>