<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>k
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<script type="text/javascript">
	function showDetails(div)
	{
		var conP = "<%=request.getContextPath()%>";
		if(div=='1stBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPositiveNegativeGraphMax.action",
			    success : function(subdeptdata) {
		       $("#mybuttondialog").html(subdeptdata);
		       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='2ndBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPatientFeedDetails.action",
			    success : function(subdeptdata) {
		       $("#mybuttondialog").html(subdeptdata);
		       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='3rdBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDeptFeedDetailsGraphMax.action",
			    success : function(subdeptdata) {
		       $("#mybuttondialog").html(subdeptdata);
		       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='4thBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPatientFeedbckDetails.action",
			    success : function(subdeptdata) {
				$("#mybuttondialog").html(subdeptdata);
			       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='5thBlock')
		{
			//alert(div);
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDeptFeedbckDetails.action",
			    success : function(subdeptdata) {
				$("#mybuttondialog").html(subdeptdata);
			       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='6thBlock')
		{
		//	alert(div);
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDashboardCounters.action",
			    success : function(subdeptdata) {
				$("#mybuttondialog").html(subdeptdata);
			       $("#mybuttondialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}

	function refresh(div)
	{
		
		if(div=='1stBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPositiveNegativeGraph.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='2ndBlock')
		{
		//	alert(div);
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPatientFeedDetails.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='3rdBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDeptFeedDetailsGraph.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='4thBlock')
		{
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showPatientFeedbckDetails.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='5thBlock')
		{
			//alert(div);
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDeptFeedbckDetails.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(div=='6thBlock')
		{
		//	alert(div);
			$.ajax({
			    type : "post",
			    url : "/feedback/view/Over2Cloud/feedback/showDashboardCounters.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
</script>
</head>
<body>
<sj:dialog 
    	id="mybuttondialog" 
    	autoOpen="false" 
    	modal="true" 
    	width="600"
		height="650"
    	resizable="false"
    	title="Selected View"
    >
</sj:dialog>
<div class="middle-content">
<div class="contentdiv-small" style="overflow: hidden;" id="div1">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('1stBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 40px;"><CENTER><h3><s:property value="%{yesNoMapHeader}"  /></h3></CENTER></div>
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<sjc:chart
    	id="chartPie1"
    	cssStyle="width: 300px; height: 250px;"
    	legendShow="true"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieYesNoMap}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
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
<table height="210px" width="100%" align="center" border="1">
	<tr>
		<th width="30%" bgcolor="grey" >
			<font color="white">Department</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Poor</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Average</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Good</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">V&nbsp;Good</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Excellent</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="lightgrey" class="sortable" width="30%">
			<s:property value="%{subDept}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%">
			<s:property value="%{poor}"/>
		<font color="red"></font>
		</td>
		<td align="center"  bgcolor="lightgrey" class="sortable" width="10%" >
			<s:property value="%{avg}"/>		
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%">
			<s:property value="%{good}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" >
			<s:property value="%{vgood}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" >
			<s:property value="%{exclnt}"/>
		</td>
	</tr>
	</s:iterator>
</table>
</div>
<div class="clear"></div>
</div>
<div class="contentdiv-small"  style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('3rdBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 85px;"><CENTER><h3><s:property value="%{feedStatHeader}"  /></h3></CENTER></div>
<div class="clear"></div>
<div id="3rdBlock">
        <sjc:chart
    	id="chartPie2"
    	cssStyle="width: 300px; height: 250px;"
    	legendShow="true"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieFeedStatMap}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>
    </div>    
<div class="clear"></div>
</div>
<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="refresh('4thBlock')"><img src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#" onclick="showDetails('4thBlock')"><img src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="headding" align="center" style="margin-left: 85px;">Patient Feedback</div>
<div id="4thBlock">
<div class="clear"></div>
			<table align="center" border="0" width="100%" height="338px" style="border-style:dotted solid;">
                        			
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr>
                        			<th align="center" class="headings1" >Patient&nbsp;Name</th>
                        			<th align="center" class="headings1" >Mob&nbsp;No</th>
                        			<th align="center" class="headings1" >Doctor&nbsp;Name</th>
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
                        			<td align="center" ><b><s:property value="%{name}"/></b></td>
                        			<td align="center"><b><s:property value="%{mobNo}"/></b></td>
                        			<td align="center"><b><s:property value="%{doctName}"/></b></td>
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
<div class="headding" align="center" style="margin-left: 88px;">Department Feedback</div>
<div id="5thBlock">
<div class="clear"></div>
<table align="center" border="0" width="100%" height="338px" style="border-style:dotted solid;">
                        			
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="0" style="border-style:dotted solid;" width="100%">
                        	  <tr>
                        			<th align="center" class="headings1" >Ticket&nbsp;No</th>
                        			<th align="center" class="headings1" >Feedback&nbsp;By</th>
                        			<th align="center" class="headings1" >Mob&nbsp;No</th>
                        			<th align="center" class="headings1" >Date</th>
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
                        			<td align="center" ><b><s:property value="%{ticketNo}"/></b></td>
                        			<td align="center"><b><s:property value="%{feedBy}"/></b></td>
                        			<td align="center"><b><s:property value="%{feedByMob}"/></b></td>
                        			<td align="center"><b><s:property value="%{feedDate}"/></b></td>
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
<div class="headding" align="center" style="margin-left: 90px;">Ticket's Counter</div>
<div class="clear"></div>
<div style="height:auto;">
<s:iterator id="countList" value="%{dashCounterList}">
<div class="content-left">
	<ul>
    	<li>
    		<a href="#"><s:property value="%{name}"/></a>
    	</li>
    </ul>
</div>
<div class="content-middle">
	<ul>
    	<li><a href="#"><s:property value="%{count}"/></a>
    	</li>
    </ul>
</div>
</s:iterator>
</div>

<marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" height="310" width="100%" style="color: #000000; text-align:left; font-family:Verdana; font-size:8pt">
	<table border="0" width="100%" height="100%" align="center">
		<tbody>
			<tr>
				<td>
						<table width="100%" height="100%" align="center">
							<tr>
								<th align="center">
									News & Alerts
								</th>
							</tr>
							<s:iterator id="news" value="%{newsList}">
							<tr>
								<td align="center" width="100%">
									<s:property value="%{name}"/>
								</td>
							</tr>
							</s:iterator>
						</table>
					
				</td>
			</tr> 
			<tr>
				<td>
					<hr height="10" />
				</td>
			</tr>
			<tr>
				<td>
						<table width="100%" height="100%" align="center">
							<tr>
								<th align="center">
									Todays Alerts
								</th>
							</tr>
							<s:iterator id="countList" value="%{alertsList}">
							<tr>
								<td align="center" width="100%">
									<s:property value="%{name}"/>
								</td>
							</tr>
							</s:iterator>
						</table>
				</td>
			</tr>
			<tr>
				<td>
					<hr height="10" />
				</td>
			</tr>
			<tr>
				<td>
						<table width="100%" height="100%" align="center">
							<tr>
								<th align="center">
									Joke of the Day
								</th>
							</tr>
							<s:iterator id="countList" value="%{jokesList}">
							<tr>
								<td align="center" width="100%">
									<s:property value="%{name}"/>
								</td>
								<td align="center" height="5px">
									
								</td>
							</tr>
							</s:iterator>
						</table>
				</td>
			</tr>
			<tr>
				<td>
					<hr height="10" />
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