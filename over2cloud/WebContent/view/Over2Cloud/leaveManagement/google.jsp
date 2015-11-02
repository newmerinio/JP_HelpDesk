
<%@taglib
	prefix="s"
	uri="/struts-tags"
%>
<%@taglib
	prefix="sj"
	uri="/struts-jquery-tags"
%>
<%

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

	   <sj:head ajaxcache="false" compressed="false" jqueryui="true" jquerytheme="full_demo"
			customBasepath="calender" />
		
		<SCRIPT type="text/javascript">
		
		</SCRIPT>
		<link href="<s:url value="/css/style.css"/>" rel="stylesheet"
			type="text/css" />
	<link rel='stylesheet' type='text/css'
	 href="<%=request.getContextPath()%>/calender/full_demo/reset.css" />
    <link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/calender/libs/css/smoothness/jquery-ui-1.8rc3.custom.css" />
	<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/calender/jquery.weekcalendar.css" />
	<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/calender/full_demo/demo.css" />
	
	<script type='text/javascript' src="<%=request.getContextPath()%>js/jquery.min.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath()%>/calender/libs/jquery-ui-1.8rc3.custom.min.js"></script>
	<script type='text/javascript' src="<%=request.getContextPath()%>/calender/jquery.weekcalendar.js"></script>
	<script type='text/javascript' src="<%=request.getContextPath()%>/calender/full_demo/demo.js"></script>

</head>
<body> 

                
                    <h2>Calender Targets </h2>
					<span class="title_wrapper_left"></span>

					<span class="title_wrapper_right"></span>				
	<!--<div id="about_button_container">
		<button type="button" id="about_button">About this demo</button>
	</div>
	  
	-->

<s:form name="from" method="post" action="usermasterInfo">
	<table>
	<tr>
   <td colspan="2"  width="700px" align="center" bordercolor="red" >
         <FONT color="blue"><s:actionmessage/></FONT>
         <FONT color="red"><s:actionerror/></FONT>
         <FONT color="red"><s:fielderror/></FONT>
   </td>
</tr>
</table>
	<table cellpadding="0" cellspacing="0" style="border-collapse: collapse" width="100%">
	<div id='calendar'></div>
	<div id="event_edit_container">
		<form>
			<input type="hidden" />
			<ul>
				<li>
					<span>Date: </span><span class="date_holder"></span> 
				</li>
				<li>
					<label for="start">From Date:</label><select name="start"><option value="">Select From Date</option></select>
				</li>
				<li>
					<label for="end">To Date:</label><select name="end"><option value="">Select To Date</option></select>
				</li>
				<li>
					<label for="title">Holiday Name:</label><input type="text" name="title" />
				</li>
				<li>
					<label for="body">Holiday Brief:</label><textarea name="body"></textarea>
				
				</li>
				<li>
				
				</li>
				<li>
					
				
				</li>
			</ul>
		</form>
	</div>
	<div id="about">
		<h2>Summary</h2>
		<p>
			This calendar implementation demonstrates further usage of the calendar with editing and deleting of events. 
			It stops short however of implementing a server-side back-end which is out of the scope of this plugin. It 
			should be reasonably evident by viewing the demo source code, where the points for adding ajax should be. 
			Note also that this is **just a demo** and some of the demo code itself is rough. It could certainly be 
			optimised.
		</p>
		<p>
			***Note: This demo is straight out of SVN trunk so may show unreleased features from time to time.
		</p>
		<h2>Demonstrated Features</h2>
		<p>
			This calendar implementation demonstrates the following features:
		</p>
		<ul class="formatted">
			<li>Adding, updating and deleting of calendar events using jquery-ui dialog. Also includes 
				additional calEvent data (body field) not defined by the calEvent data structure to show the storage 
				of the data within the calEvent</li>
			<li>Dragging and resizing of calendar events</li>
			<li>Restricted timeslot rendering based on business hours</li>
			<li>Week starts on Monday instead of the default of Sunday</li>
			<li>Allowing calEvent overlap with staggered rendering of overlapping events</li>
			<li>Use of the 'getTimeslotTimes' method to retrieve valid times for a given event day. This is used to populate
				select fields for adding, updating events.</li>
			<li>Use of the 'eventRender' callback to add a different css class to calEvents in the past</li>
			<li>Use of additional calEvent data to enforce readonly behaviour for a calendar event. See the event
				titled  read-only</li>
		</ul>
	</div>
	 <tr>
	<td><br>
	
	<br></td>
 </tr>	</table>
	</s:form>
	</body>
</html>
