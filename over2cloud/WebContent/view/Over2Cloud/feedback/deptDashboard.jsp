<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboard.js"/>"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
</head>
<body>
<sj:dialog 
    	id="mybuttondialog123" 
    	autoOpen="false" 
    	modal="true" 
    	width="600"
		height="500"
    	resizable="false"
    	title="Selected View Details"
    >
</sj:dialog>
<div class="middle-content">
<div class="contentdiv-small" style="overflow: hidden;" id="div1">
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock','<%=request.getContextPath()%>')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('1stBlock','<%=request.getContextPath()%>')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('1stBlock','<%=request.getContextPath()%>')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stBlock','<%=request.getContextPath()%>')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div class="headding" align="center" style="margin-left: 40px;"><CENTER><h3><s:property value="%{yesNoMapHeader}"  /></h3></CENTER></div>
<div class="clear"></div>
<s:hidden id='1quadrant' name="1quadrant" value="chart"></s:hidden>
<sjc:chart
    	id="chartPie1"
    	cssStyle="width: 250px; height: 200px;"
    	legendShow="true"
    	pie="true"
    	pieLabel="true"
    	cssClass="chartDataCss"
    	crosshairColor="black"
    >
    	<s:iterator value="%{pieDeptMap}">
	    	<s:if test="key=='Excellent'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#556B2F"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='V.Good'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#006400"
	    	/>
	    	</s:elseif>
	    	<s:if test="key=='Good'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#7FFF00"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='Average'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#FF8C00"
	    	/>
	    	</s:elseif>
	    	<s:elseif test="key=='Poor'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#ff0000"
	    	/>
	    	</s:elseif>
	    	<s:else>
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
	    	</s:else>
    	</s:iterator>
    </sjc:chart>
</div>
</div>
<div class="contentdiv-small"  style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('2ndBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('2ndBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3>Patient Feedback Details</h3></CENTER></div>
<div class="clear"></div>
<div id="2ndBlock">
<table height="100%" width="100%" align="center" border="1">
	<tr height="30px">
		<th width="30%" bgcolor="#8A0886" >
			<font color="white">Status</font>
		</th>
		<th width="10%" bgcolor="#8A0886">
			<font color="white">Poor</font>
		</th>
		<th width="10%" bgcolor="#8A0886">
			<font color="white">Average</font>
		</th>
		<th width="10%" bgcolor="#8A0886">
			<font color="white">Good</font>
		</th>
		<th width="10%" bgcolor="#8A0886">
			<font color="white">V&nbsp;Good</font>
		</th>
		<th width="10%" bgcolor="#8A0886">
			<font color="white">Excellent</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="30%">
			<b><s:property value="%{subDept}"/></b>
		</td>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="10%" onclick="showMiddleDetails('<s:property value="%{subDept}"/>','Poor','<%=request.getContextPath()%>');">
			<s:property value="%{poor}"/>
		<font color="red"></font>
		</td>
		<td align="center"  bgcolor="#E6E6FA" class="sortable" width="10%" onclick="showMiddleDetails('<s:property value="%{subDept}"/>','Average','<%=request.getContextPath()%>');">
			<s:property value="%{avg}"/>		
		</td>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="10%" onclick="showMiddleDetails('<s:property value="%{subDept}"/>','Good','<%=request.getContextPath()%>');">
			<s:property value="%{good}"/>
		</td>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="10%" onclick="showMiddleDetails('<s:property value="%{subDept}"/>','Very Good','<%=request.getContextPath()%>');">
			<s:property value="%{vgood}"/>
		</td>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="10%" onclick="showMiddleDetails('<s:property value="%{subDept}"/>','Excellent','<%=request.getContextPath()%>');">
			<s:property value="%{exclnt}"/>
		</td>
	</tr>
	</s:iterator>
</table>
</div>
<div class="clear"></div>
</div>
<div class="contentdiv-small"  style="overflow: hidden;">
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('3rdBlock')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('3rdBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('3rdBlock')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div id="3rdBlock">
<s:hidden id='deptDataType' name="deptDataType" value="chart"></s:hidden>
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3><s:property value="%{feedStatHeader}"  /></h3></CENTER></div>
<div class="clear"></div>
        <sjc:chart
    	id="chartPie2"
    	cssStyle="width: 250px; height: 200px;"
    	legendShow="true"
    	pie="true"
    	pieLabel="true"
    	cssClass="chartDataCss"
    	
    >
    	<s:iterator value="%{pieFeedStatMap}">
	    	<s:if test="key=='Pending'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#088A29"
	    		cssClass="positiveLabel"
	    	/>
	    	</s:if>
	    	<s:elseif test="key=='Resolved'">
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    		color="#ff0000"
	    	/>
	    	</s:elseif>
	    	<s:else>
	    		<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
	    	</s:else>
    	</s:iterator>
    </sjc:chart>
    </div>    
<div class="clear"></div>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('4thBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('4thBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 85px;"><h3>Patient Feedback</h3></div>
<div id="4thBlock">
<div class="clear"></div>
			<table align="center" border="0" width="100%" height="338px" style="border-style:dotted solid;">
                        			
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr>
                        			<th align="center" class="headings1" width="40%">Patient&nbsp;Name</th>
                        			<th align="center" class="headings1" width="20%">Mob&nbsp;No</th>
                        			<th align="center" class="headings1" width="40%">Doctor&nbsp;Name</th>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 10px" align="center">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" height="290px">
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" height="100%" >                        		
                              <s:iterator id="fList"  status="status" value="%{feedList}" >
								<tr bordercolor="#ffffff">
                        			<td align="center" width="40%"><b><s:property value="%{name}"/></b></td>
                        			<td align="center" width="20%"><b><s:property value="%{mobNo}"/></b></td>
                        			<td align="center" width="40%"><b><s:property value="%{doctName}"/></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>
              
</div>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('5thBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('5thBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 88px;"><h3>Tickets Status</h3></div>
<div id="5thBlock">
<div class="clear"></div>
<table align="center" border="0" width="100%" height="338px" style="border-style:dotted solid;">
                        			
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr>
                        			<th align="center" class="headings1" width="20%">Ticket&nbsp;No</th>
                        			<th align="center" class="headings1" width="35%">Feedback&nbsp;By</th>
                        			<th align="center" class="headings1" width="20%">Mob&nbsp;No</th>
                        			<th align="center" class="headings1" width="25%">Date</th>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               <tr>
                   <td valign="top" style="padding-top: 10px">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" height="290px">
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" height="100%" >                        		
                              <s:iterator id="fList"  status="status" value="%{feedTicketList}" >
								<tr bordercolor="#ffffff">
                        			<td align="center" width="20%"><b><s:property value="%{ticketNo}"/></b></td>
                        			<td align="center" width="35%"><b><s:property value="%{feedBy}"/></b></td>
                        			<td align="center" width="20%"><b><s:property value="%{feedByMob}"/></b></td>
                        			<td align="center" width="25%"><b><s:property value="%{feedDate}"/></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>
</div>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('6thBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('6thBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div id="6thBlock">
<div class="headding" align="center" style="margin-left: 90px;"><h3>Alerts & Updates</h3></div>
<div class="clear"></div>
<marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" height="310" width="100%" style="color: #000000; text-align:left; font-family:Verdana; font-size:8pt">
	<table border="0" width="100%" height="100%" align="center">
		<tbody>
			<tr>
				<td>
						<table width="100%" height="100%" align="center">
							<s:iterator id="news" value="%{newsList}">
							<tr>
								<td align="center" width="100%">
									<b><s:property value="%{name}"/></b><br/>
									<s:property value="%{desc}"/>
								</td>
							</tr>
							</s:iterator>
						</table>
					
				</td>
			</tr> 
		</tbody>
	</table>
</marquee>

<div class="clear"></div>
</div>

</div>
</div>
</body>
</html>