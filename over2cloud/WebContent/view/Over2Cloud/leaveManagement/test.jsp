<%@taglib prefix="s" uri="/struts-tags"%>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
//System.out.println("For validApp   "+validApp);
%>
<%

//Code for checking the level of departments or sub departments
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
////deptlevel,dept1Name#dept2Name#
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}

String userTpe=(String)session.getAttribute("userTpe");
%>
<html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/common/topclient.js"/>"></script>
</head>
<body onload="">
<div class="logo1" id="PageRefresh" style="float: left;"><img
	src="images/orgLogo/orgLogo.jpg" width="180px" height="60px" /></div>
<!-- <div id="serverTimeDiv"
	style="float: left; padding-top: 20px; padding-left: 35px;"></div>
<div id="notificationDiv" style="padding-top: 10px;padding-left: 35px;"><center><b>Notification bar loading</b><img id="indicator2" src="images/notificationloader.gif"/></center></div>
 -->
<div class="wrap_nav">
<ul class="nav_links">
	<li><a href="javascript:void();"><FONT
		style="font-size: 12px; font-family: Arial, Sans-Serif; font-weight: normal; color: #000; font-weight: bold;"><B><s:property
		value="#session['empName']" /></B></FONT>&nbsp;&nbsp;
		<s:if test="%{profilePicName == null || profilePicName == ''}">
				<img src="images/noImage.JPG" width="20px" height="20px"/>
			</s:if>
			<s:else>
				<img src="<s:property value="%{profilePicName}" />" width="20px" height="20px"/>
			</s:else>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="two_column">
	<ul>
		<s:if test="%{profilePicName == null || profilePicName == ''}">
			<img src="images/noImage.JPG" width="20px" height="20px" />
		</s:if>
		<s:else>
			<img src="<s:property value="%{profilePicName}" />" width="20px"
				height="20px" />
		</s:else>
	</ul>
	</div>
	<div class="two_column">
	<ul>
		<li><a href="javascript:void();"><s:property
			value="#session['empName']" /></a></li>
		<li>Dept: <s:property value="%{empDept}" /></li>
		<li>Type: <s:property value="%{userType}" /></li>
		<li><a href="javascript:void();" id="myaccount">My Account</a></li>
		<li><a href="<s:url action='logout'/>"><font color="blue">Sign Out</font></a></li>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>

<div class="clear"></div>
<!-- Header Start Point -->
<div class="black">
<ul id="mega-menu" class="mega-menu">
	
	
	
	
	<%if(validApp.contains("HDM") || validApp.contains("COMPL")|| validApp.contains("FM")){%>
	<li><a href="#">Dashboard</a>
	<ul>
		<!--<li><a href="#" id="asset_dashboard_view">Helpdesk</a></li>
		-->
		<li><a href="#" id="showLeadDashboard">Lead</a></li>
		<li><a href="#" id="showClientDashboard">Client</a></li>
		<li><a href="#" id="showAssociateDashboard">Associate</a></li>
		<li><a href="#" id="asset_cmplaint_dashboard">PM&nbsp;&&nbsp;Support</a></li>

	<%-- 	<%if(validApp.contains("HDM")){%>
		<li><a href="#" id="ticket_normal_newdashboard">Helpdesk</a></li>
				
		<%}%> --%>
		<%if(validApp.contains("COMPL")){%>
		<%if(userRights.contains("port") || userRights.contains("coma") || userRights.contains("tana")
					  || userRights.contains("taty") || userRights.contains("rert") || userRights.contains("cvel")		
					  || userRights.contains("core")) {%>
		<li><a href="#"  id="mgmt_compl_dashboard">Operation Task</a></li>
				
		<%}%>
		<%}%>
		<%
				if(userTpe.equalsIgnoreCase("M"))
				{%>
		<!--<li><a href="#"  id="dash_view">Feedback</a></li>
				
				-->
		<% }
				if(validApp.contains("LM"))
	    		{
	    			%>
		<!--<li><a href="javascript:void();"  id="leave_dashboard_view">Leave</a></li>
	    			-->
		<%
	    		}
	    		%>
		<% 
		if(validApp.contains("DAR"))
		{
			%>
			<li><a href="javascript:void();"  id="project_dashboard_view">Project</a></li>
			<%
		}
	   %>
	<%--    <% 
		if(validApp.contains("FM"))
		{
				if(userRights.contains("dash_VIEW"))
				{%>
				<li><a href="#"  id="dash_view">Management</a></li>
				<% }
				
				if(userRights.contains("deda_VIEW"))
				{%>
				<li><a href="#"  id="dash_viewNormal">HOD</a></li>
				<% }
		}
	   %> --%>
		       <li><a href="#"  id="main_dash_KR">KR</a></li>
	</ul>
	</li>
	
	<%}
	%>
	
	<%-- <%if(validApp.contains("FM"))
	 {
		if(true) 
		{%>    	
	<li>
		<a href="#">Patient Delight</a>
		<ul>
			<%
			if(true)
			{
				%>
				<li>
				<a href="javascript:void();">Admin</a>
				<ul>
					<%//if(userRights.contains("conad_VIEW"))
						{%><li><a href="#" onclick="getContact('FM')" id="view_fm_contacts">Add Contact</a></li><%} %>
						<%//if(userRights.contains("conmp_VIEW"))
						{%><li><a href="#" onclick="getContactMapping('FM')" id="view_fm_contacts_mapping">Map Contact</a></li><%} %>
						<%//if(userRights.contains("consh_VIEW"))
						{%><li><a href="#" onclick="getContactSharing('FM')" id="view_fm_contacts_sharing">Share Contact</a></li><%} %>
						<%//if(userRights.contains("conro_VIEW"))
						{%><li><a href="#" onclick="getRoaster('D','FM')">Roaster</a></li><%} %>
				</ul>
				</li>
				<%
			}
			if(true) 
			{
			%>
			<li>
				<a href="#">Co-ordinator</a>
				<ul>
				<%
		//		if(userRights.contains("nefn_VIEW")) 
				{%>
					<li>
						<a href="#" id="feedback_add_paper">Paper Mode</a>
					</li>
				<%} 	
			//	if(userRights.contains("nefpcc_VIEW"))
				{%>
					<li>
						<a href="#" id="feed_action_pcc">PCC</a></li>
					<%}
				
					%>
				<%
		//		if(userRights.contains("posfeed_VIEW")) 
				{%>
				<li>
						<a href="#" id="feedback_Total_positive">Positive</a>
					</li>
				<%} 
		//		if(userRights.contains("negfeed_VIEW"))
				{%>
					<li>
						<a href="#" id="feedback_Neg_pending">Negative</a>
					</li>
				<%}
		//		if(userRights.contains("dcti_VIEW"))
				{%>	<li>
						<a href="#" id="feed_action_pending_admin">Re-Opened</a>
					</li>
				<%}
				%>
				</ul>
			</li>
			<%
			}
		//	if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H") || userTpe.equalsIgnoreCase("N")) 
		if(userRights.contains("doti_VIEW"))	
		{
			%>
			<li>
				<a href="#">Department</a>
				<ul>
					<li><a href="#"  id="feed_action_pending_fm">Activity</a></li>
				</ul>
			</li>
			<%
			}
			//if(userTpe.equalsIgnoreCase("M"))
			{
			%>
			<li>
			<a  href="#">Report</a>
			<ul>
			<%
					if(userRights.contains("repmode_VIEW"))
					{%>	<li>
							<a href="#" id="feed_Details">Mode&nbsp;Status</a>
						</li>
						<%} 
					
					if(userRights.contains("repsms_VIEW"))
					{%>	<li>
						<a href="#" id="feed_Details_Report">SMS Feedback</a>
					</li>
						<%} 
					if(userRights.contains("prodemp_VIEW"))
					{%>	
					<li>
						<a href="#" onclick="getProductivty('FM','Employee')" >Emp-Productivity</a>
					</li>
					<%}
					if(userRights.contains("prodcat_VIEW"))
					{
					%>
					<li>
						<a href="#" onclick="getProductivty('FM','Category')">Categorywise</a>
					</li>
					<%
					}
					if(userRights.contains("reppat_VIEW"))
					{%>	<li>
							<a href="#" id="patdetails_view">Patient Details</a>
						</li>
						<%} 
					if(userRights.contains("repkey_VIEW"))
					{
					%>
					<li><a href="#" id="keyWords_view">Keywords</a></li>
					<%} %>
			</ul>
			</li>
			<%
			}
			}
			%>
		</ul>
	</li>
	<%
	}
%> --%>
      <%--  <%
		if(true ||  userRights.contains("roas") || userRights.contains("vica") || userRights.contains("vion") || userRights.contains("acti") || userRights.contains("down") || userRights.contains("anal_rep_ADD") )
		{
		%>
           <li><a href="#">Helpdesk</a>
	             <ul>
				 		 <%if(userRights.contains("vica") || userRights.contains("vion")){%>
						<li><a href="#">Register Task</a>
						<ul>
							<%if(userRights.contains("vica_ADD")) {%>
							    <!--Go in the menucontrol.js for this links-->
							    <li><a href="#" id="feed_via_online">Online</a></li>
							<%}%>
							
							<%if(userRights.contains("vion_ADD")) {%>
							    <!--Go in the menucontrol.js for this links-->
								<li><a href="#" id="feed_via_call">Call</a></li>
							<%}%>
						</ul>
						</li>
					 <%}%>
					 <%if(userRights.contains("acti")){ %>
						<li><a href="#">Action</a>
							<ul>
							    <!--Go in the topclient.js for these links-->
								<li><a href="#" onclick="viewFeedback('Pending','HDM','Y','Y')"       id="activity_feed_action_pending">Activity Dashboard</a></li>
								<li><a href="#" onclick="viewFeedback('Pending','HDM','N','N')"       id="feed_action_pending">Pending</a></li>
								<li><a href="#" onclick="viewFeedback('Snooze','HDM','N','N')"        id="feed_action_snooze">Snooze</a></li>
								<li><a href="#" onclick="viewFeedback('High Priority','HDM','N','N')" id="feed_action_hp">High Priority</a></li>
								<li><a href="#" onclick="viewFeedback('Resolved','HDM','N','Y')"      id="feed_action_resolved">Resolved</a></li>
							</ul>
						</li>
    				 <%}%>
    				 
    				
					<li><a href="#">Report</a>
						<ul>
							<%if(userRights.contains("down")){ %>
							    <!--Go in the menucontrol.js for this links-->
							    <li><a href="#" id="feedreport_history">History</a></li>
								<li><a href="#" id="feed_action_feedreport">Normal</a></li>
							<%}%>
							<%if(userRights.contains("anal_rep_ADD")){ %>
							<!--Go in the topclient.js for these links-->
							<li><a href="#" onclick="getProductivty('HDM','Employee')">Employee&nbsp;Productivity</a></li>
							<li><a href="#" onclick="getProductivty('HDM','Category')">Category&nbsp;Productivity</a></li>
						<%}%>
						</ul>
				    </li>
				    
				    <li>
						<a href="#">Admin</a>
						<ul>
						    <!--Go in the topclient.js for these links-->
							<%if(userRights.contains("roas")){ %>
							    <li><a href="#"  onclick="getRoaster('SD','HDM')">Roaster</a></li>
							<%}%>
							 <li><a href="#" onclick="getContact('HDM')" id="view_hdm_contacts">Manage Contact</a></li>
						</ul>
					 </li>
	    		 </ul>
	    	</li>
	  <%
		}
	%> --%>
	<li>
	    	<a href="#">WFPM Sales</a>
					<ul>
					    	<li>
						   	 	<a href="#">Sales</a>
								<ul>
								<li><a href="javascript:void();" id="showWFPMCommonDashboard">Activity Board</a></li>
								<li><a href="javascript:void();" id="leadAction" >Lead</a></li>
								<li><a href="javascript:void();" id="pclientView">Client</a></li>
								<li><a href="javascript:void();" id="pAssociateView">Associate</a></li>
								</ul>
							</li>
							
							<li>
								<a href="#">Reports</a>
							    <ul>
							    <s:if test="#session['userRights'].contains('targ') || 'true'"><li><a href="javascript:void();" id="kpi_autoFill">KPI&nbsp;Auto</a></li></s:if>
						   		<s:if test="#session['userRights'].contains('targ') || 'true'"><li><a href="javascript:void();" id="offering_autoFill">Offering&nbsp;Auto</a></li></s:if>
							    </ul>
 	   						</li>
 	   						<li>
						    <a href="#">Admin</a>
						    <ul>
						
									<li><a href="#" onclick="getContact('WFPM')" id="view_wfpm_contacts">Add Contact</a></li>
									<li><a href="#" onclick="getContactMapping('WFPM')" id="view_wfpm_contacts_mapping">Map Contact</a></li>
									<li><a href="#" onclick="getContactSharing('WFPM')" id="view_wfpm_contacts_sharing">Share Contact</a></li>
									<li><a href="javascript:void();" id="incentiveView4">Port</a></li>
									<li><a href="javascript:void();" id="mapKraKpi_WFPM">Map KRA-KPI</a></li>
									<!--<s:if test="#session['userRights'].contains('targ')"><li><a href="javascript:void();" id="targetView">Target</a></li></s:if>
									-->
									<s:if test="#session['userRights'].contains('targ') || 'true'"><li><a href="javascript:void();" id="target_WFPM">Target</a></li></s:if>
									<li><a href="javascript:void();" id="incentiveView">Incentive</a></li>
						    </ul>
						 </li>
					 	 <li>
							<a href="#">Invoice</a>
						    <ul>
						        	<s:iterator id="ConfigurationUtilBean" value="configBean" var="variable">
						                <s:if test="%{#variable.field_value =='Invoices'}">
										  <s:if test="#session.userRights.contains('invo')"><li><a href="javascript:void();" onclick="invoicedetailsView(${id},'WFPM');">Invoice</a></li></s:if>
							            </s:if>
						      		 </s:iterator>
						       
						       <li><a href="javascript:void();" onclick="invoicedetailsView('50','WFPM');">Invoice</a></li>
						    </ul>
					 	</li>
 	   					
 	   						
					</ul>
			</li>
		<li>
			<a href="#">WFPM CRM</a>
					<ul>
					    <li>
					    <li><a href="javascript:void();" id="communication_WFPM">Communication</a></li>
						<li><a href="javascript:void();" id="group_WFPM" >Group</a></li>
						<li><a href="javascript:void();" id="templates_WFPM">Templates</a></li>
						<li><a href="javascript:void();" id="blackList__WFPM">Black List</a></li>
						<li><a href="javascript:void();" id="blackOutTime_WFPM">Black Out Time</a></li>
						<li><a href="javascript:void();" id="reports_WFPM">Reports</a></li>
					</ul>
	  </li>
	<!-- ------Offering HDM-------------------------------------------------------- -->
	<li><a href="#">Compliants</a>
	             <ul>
	<li><a href="#">Register&nbsp;Task</a>
	<ul>
	    <!-- <li><a href="#" id="lodge_complaint_via_online">Online</a></li> -->
	<li><a href="#" id="lodge_complaint_via_call">Call&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	 
	</ul>
	</li>
	
	<li><a href="#">Action</a>
	<ul>
	<li><a href="#" onclick="viewComplaint('Pending','DREAM_HDM','Y','Y')">Activity Dashboard</a></li>
	<li><a href="#" onclick="viewComplaint('Pending','DREAM_HDM','N','N')">Pending</a></li>
	<li><a href="#" onclick="viewComplaint('Snooze','DREAM_HDM','N','N')">Snooze</a></li>
	<li><a href="#" onclick="viewComplaint('High Priority','DREAM_HDM','N','N')">High Priority</a></li>
	<li><a href="#" onclick="viewComplaint('Resolved','DREAM_HDM','N','Y')">Resolved</a></li>
	</ul>
	</li>
	    <li>
	<a href="#">Admin</a>
	<ul>
	 <li><a href="#" onclick="getContact('DREAM_HDM')">Add&nbsp;Contact</a></li>
	     <li><a href="#" onclick="getContactMapping('DREAM_HDM')" >Map&nbsp;Contact</a></li>
	   	 <li><a href="#" onclick="getContactSharing('DREAM_HDM')" >Share&nbsp;Contact</a></li>
	 <li><a href="#" onclick="getRCA('DREAM_HDM')" >RCA&nbsp;Master</a></li>
	</ul>
	 </li>
	    	 </ul>
	    	</li>
	
<!-- ------Offering HDM-------------------------------------------------------- -->	
	<%
		if(true)
		{
	if(userRights.contains("clma") || userRights.contains("tare") || userRights.contains("dasu") || userRights.contains("ttma") 
							|| userRights.contains("prma") || userRights.contains("pesh")){%>
		<li>
			<a href="#">Project</a>
			<ul>
			<% if(userTpe.contains("M") || userTpe.contains("NA")) {%>
		    	   <li><a href="javascript:void();" id="task_add_dashboard">Admin</a> 
		        	<ul>
		        	
		        	     <li><a href="#" onclick="getContact('DAR')">Add&nbsp;Contact</a></li>
					     <li><a href="#" onclick="getContactMapping('DAR')" >Map&nbsp;Contact</a></li>
					   	 <li><a href="#" onclick="getContactSharing('DAR')" >Share&nbsp;Contact</a></li>
						 <li><a href="#" id="transfer_compliance1">Port</a></li>			
						<%if(userRights.contains("clma_ADD") || userRights.contains("clma_VIEW") 
							|| userRights.contains("clma_MODIFY") || userRights.contains("clma_DELETE")){%>
						<li><a href="javascript:void();" id="client_master">Add Client</a></li> 
						<%}%>
					</ul>
		    	    </li>
		    	    	<%
	                  }%>   
		    	     <li><a href="javascript:void();" id="product_sheet">Project</a>
		        	 <ul>
		        	    <li><a href="javascript:void();" id="activity_board">Activity&nbsp;Board</a></li>
		        	  <%if(userRights.contains("tare_ADD") || userRights.contains("tare_VIEW") 
							|| userRights.contains("tare_MODIFY") || userRights.contains("tare_DELETE")){%>
						<li><a href="javascript:void();" id="task">Register</a></li>
						 <%}%>
						<li><a href="javascript:void();" id="dar">Submit&nbsp;DAR</a></li>
						
						<li><a href="javascript:void();" id="darValidate">Validate&nbsp;DAR</a></li>
		        	    <li><a href="javascript:void();" id="darReport">Report</a></li>
		        	     <li><a href="javascript:void();" id="darProductivity">Productivity</a></li>
		        	  <%--  <%if(userRights.contains("pesh_ADD")){%><li><a href="javascript:void();" id="product_sheet_serch">Productivity&nbsp;</a></li><%}%> --%>
					   
					 </ul>
		    	     </li>
		    		    
		 	  </ul>
		 	</li>
		 	<%} %>
		<%
		}%>
	
	<%
	if(true) {%>
	<li><a href="#">Operations&nbsp;Task</a>
					<ul>
					    <li>
						<a href="#">Admin</a>
							<ul>
								<%if(true) 
								{%>
								  <li><a href="#" id="view_compl_task">Task&nbsp;Name&nbsp;</a></li>
								<%} %>
								<%if(true) {%>
								   <li><a href="#" onclick="getContact('COMPL')" id="view_compl_contacts">Add&nbsp;Contact&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
								   <li><a href="#" onclick="getContactMapping('COMPL')" id="view_compl_contacts_mapping">Map&nbsp;Contact&nbsp;&nbsp;</a></li>
								   <li><a href="#" onclick="getContactSharing('COMPL')" id="view_compl_contacts_sharing">Share&nbsp;Contact&nbsp;&nbsp;</a></li>
								
								<%} %>
								<%if(true) {%>
									<li><a href="#" id="transfer_compliance">Port</a></li>
								<%} %>
							</ul>
						</li>
						<li>
							<a href="#">Operations</a>
							<ul>
							      <li><a href="#" id="activity_dash">Activity&nbsp;Dashboard</a></li>
								<%if(true) {%>
									<li><a href="#" id="compl_configure">Configure</a></li>
								<%} %>
								<%if(true) {%>
									<li><a href="#" id="compl_configure_excel">Configure&nbsp;Excel</a></li>
								<%} %>	
								<%if(true) {%>
									<li><a href="#" id="user_compl_dashboard">Action</a></li>
								<%} %>
								
								<%if(true) {%>
									<li><a href="#" id="compl_report">Report</a></li>
								<%} %>
								<li><a href="#" id="task_history">Task&nbsp;History</a></li>
							    <li><a href="#" id="emp_productivity">Productivity</a></li>
							</ul>
						</li>
						<%if(true) {%>
							<li><a href="javascript:void();" id="template_compliance">Template</a></li>
						<%} %>	
					</ul>
				</li>
	<%} %>
	<li>
	    	<%if(true)
	    	{
	    	%>
	    	<a href="#">Asset</a>
	    	<%}%>
	             <ul>
						<li><a href="#">Asset&nbsp;Activity</a>
							<ul>
							<%if(true){%>
								<li><a href="#" id="asset_view">Registration&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
							<%}%>
							<%if(true){%>
								<li><a href="#" id="asset_support_action">Support</a></li>
							<%}%>
							<%if(true){%>
								<li><a href="#" id="asset_preventive_action">Preventive</a></li>
							<%}%>
							<%if(true){%>
								<li><a href="#" id="asset_reminder_report">Reminder Report</a></li>
							<%}%>
							<%if(true){%>
								<li><a href="#" id="asset_allotment_view">Allotment</a></li>
							<%}%>
							<%if(true){%>
								<li><a href="#" id="view_configure_utility">Configure Utility</a></li>
							<%}%>	
							<%if(true){%>
								<li><a href="#" id="view_daily_utility">Daily Utility</a></li>
							<%}%>	
							</ul>
						</li>
						
						<li><a href="#">Complaint</a>
							<ul>
								<%if(true){%>
									<li><a href="#" onclick="getOnlinePage()" id="asset_new_online">Online Mode</a></li>
								<%}%>
								<%if(true){%>
								    <li><a href="#" onclick="viewAssetFeedback('Pending','ASTM','N','N')" id="asset_feed_action_pending">Pending&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
								    <li><a href="#" onclick="viewAssetFeedback('Snooze','ASTM','N','N')" id="asset_feed_action_Snooze">Snooze</a></li>
								    <li><a href="#" onclick="viewAssetFeedback('High Priority','ASTM','N','N')" id="asset_feed_action_Snooze">High Priority</a></li>
								    <li><a href="#" onclick="viewAssetFeedback('Resolved','ASTM','N','Y')" id="asset_feed_action_Resolved">Resolved</a></li>
							    <%}%>
							</ul>
						</li>
						<li><a href="#">Admin</a>
							<ul>
								<%if(true) {%>
									<li><a href="#" onclick="getContact('ASTM')" id="view_astm_contacts">Add Contact</a></li>
								    <li><a href="#" onclick="getContactMapping('ASTM')" id="view_astm_contacts_mapping">Map Contact</a></li>
								    <li><a href="#" onclick="getContactSharing('ASTM')" id="view_astm_contacts_sharing">Share Contact</a></li>
								<%}%>
								<%if(true) {%> 
									<li><a href="#" onclick="getAllotMapping('ASTM')" id="view_astm_allot_mapping">Allotment Mapping</a></li>
								<%}%>   
								<%if(true) {%> 
									<li><a href="javascript:void();" id="vendor_action_view">Add&nbsp;Vendor</a></li>
								<%}%>   
								<%if(true) {%> 
									<li><a href="javascript:void();" id="outlet_action_view">View&nbsp;Outlet</a></li>
								<%}%>   
							</ul>
						</li>

						<li><a href="#">Spare</a>
							<ul>
								<%if(true) {%>
									<li><a href="#" id="spare_action_view">Spare&nbsp;Details</a></li>
								<%}%>  
								<%if(true) {%>
									<li><a href="#" id="spare_receive_action_view">Receiving&nbsp;Spare</a></li>
								<%}%>  
								<%if(true) {%>
									<li><a href="#" id="remaining_spare_action_view">Remaining&nbsp;Spare</a></li>
								<%}%>  
							</ul>
						</li>
	    		 	</ul>
	    	</li>
	    	
 <li>
	<a href="#">Referral</a>
					<ul>
					    <li>
						<a href="#">Admin</a>
							<ul>
							
									<li><a href="#" onclick="getContact('T2M')" >Add Contact</a></li>
			                		<li><a href="#" onclick="getContactMapping('T2M')" >Map Contact</a></li>
			                		<li><a href="#" onclick="getContactSharing('T2M')" >Share Contact</a></li>
									<li><a href="#" onclick="getContact('T2M')" id="view_t2m_contacts">Contact</a></li>
									<li><a href="#" onclick="getContactSharing('T2M')" id="view_t2m_contacts_sharing">Contact Sharing</a></li>
									<li><a href="javascript:void();" id="mapping2">Mapping</a></li>
									<li><a href="#" id="consultants">Consultants</a></li>
									<li><a href="javascript:void();" id="ViewconfigKeywords">Keyword</a></li>
									<li><a href="javascript:void();" id="speciality">Speciality</a></li>
									<li><a href="javascript:void();" id="locationT2M">Location</a></li>
							</ul>
						</li>
						   <li style="width : 78px;">
						
							
									<li><a href="javascript:void();" id="pullReport">Referral</a></li>
					               <li><a href="javascript:void();" id="pullSendEmailReport">Intimation</a></li>
							
						</li>
						
					</ul>
				</li> 
	
					    <li>
					    	<%
			if(validApp.contains("BPM"))
				{
					 %>
      <li>
      	 <a href="#">BPM</a>
       <ul>
       <%if(userRights.contains("milstone_VIEW") || userRights.contains("dfields_VIEW")) {%>
		<li>
		<a href="#">Configuration</a>
				<ul>
				<%if(userRights.contains("processT_VIEW")) {%>
				<li><a href="javascript:void();" id="createprocess_type">Process Repository</a></li>
				<%}%>
				<%if(userRights.contains("milstone_VIEW")) {%>
				<li><a href="javascript:void();" id="bpmsetdatafields">Set Milestone</a></li>
				<%} %>
				<%if(userRights.contains("dfields_VIEW")) {%>
				<li><a href="javascript:void();" id="bpmprocesssetofdatafields">Set Data Fields</a></li>
				<%} %>
				
	    </ul>
	    
	   </li>
	   </ul>
	   </li>
	   <%} %>
	   
	<%if(userRights.contains("pinstant_VIEW") || userRights.contains("process_VIEW")) {%>
	<li><a href="#">Process Instant</a>
				<ul>
				<%if(userRights.contains("pinstant_VIEW")) {%>
				<li><a href="javascript:void();" id="bpmpageview">Process Instant</a></li>
				<%} %>
				<%if(userRights.contains("process_VIEW")) {%>
				<li><a href="javascript:void();" id="allprocessmainpages">View Instant</a></li>
				<%} %>
	    		</ul>
	     </li>
	     
	   <%} %>
	   
<%} %>

		
			<li>
	<a href="#">KR</a>
	<ul>
	<li><a href="#">Admin</a> 
	    <ul>
	     <li><a href="#" onclick="getContact('KR')">Add&nbsp;Contact</a></li>
	     <li><a href="#" onclick="getContactMapping('KR')" >Map&nbsp;Contact</a></li>
	   	 <li><a href="#" onclick="getContactSharing('KR')" >Share&nbsp;Contact</a></li>
	 	 <li><a href="#" id="transfer_compliance1">Port</a></li>
		 <li><a href="#" onclick="getEmailSend('KR')">Resend Email</a></li>
		 <li><a href="#" onclick="getSMSSend('KR')">Resend SMS</a></li>
	
	   </ul>
	</li>
     <li><a href="#">KR</a> 
      <ul>
     	    <li><a href="javascript:void();" id="krGroupHeaderView">Group</a></li>
     	    <li><a href="javascript:void();" id="brforeKRUploadHeader">Library</a></li>
			<li><a href="javascript:void();" id="beforeKRShareView">Share</a></li>
			<!-- <li><a href="javascript:void();" id="krLibrarySearch">Library</a></li> -->
			<li><a href="javascript:void();" id="actionableView">Actionable</a></li>
			<li><a href="javascript:void();" id="brforeKRGroupView2">Productivity</a></li>
	</ul>
	 </li>
	
	    </ul>
	</li>
	

      <%
		if(true)
	  {
	%>
	<li>
		<a href="#">Communication</a>
  		<ul>
  		
			<li>
				<a href="#">SMS</a>
				<ul>
				   <%if(userRights.contains("smsreport_VIEW")) {%>
					<li><a href="javascript:void();" id="instantmessagereport">Push&nbsp;SMS</a></li>
					<%} %>
					<li><a href="javascript:void();" id="groupdetailsView">Group</a></li>
					<%if(userRights.contains("smstmplt_VIEW")) {%>
					<li><a href="javascript:void();" id="templatessView">Templates</a></li>
					<%} %>
				
					<%if(userRights.contains("autopreport_VIEW")) {%>
					<li><a href="javascript:void();" id="dailyautopushreport">Auto&nbsp;Push&nbsp;Reports</a></li>
					<%} %>
					
				</ul>
			</li>
			
			<li><a href="#">Email</a>
				<ul>
				
					<%if(userRights.contains("emailreport_VIEW")) {%>
					<li><a href="javascript:void();" id="instantmailreport">Reports</a></li>
					<%} %>
					<%if(userRights.contains("emailreport_VIEW"))
					{%>
					<li><a href="javascript:void();" id="mailtemplate">Template</a></li>
					<%} %>
				</ul>
			</li>
			
  		</ul>
	</li>
<%} %>
<%
		if(true)
		{
		%>
		<%if(true){%>
		
				
		
		 <li>
			<a href="#">HR</a>
			<ul>
			        
			        <%if(userRights.contains("lconfig")){%>
		    	   <li><a href="#">Admin</a> 
		        	<ul>
						 <li><a href="#" onclick="getContact('LM')" >Add&nbsp;Contact</a></li>
					     <li><a href="#" onclick="getContactMapping('LM')" >Map&nbsp;Contact</a></li>
					   	 <li><a href="#" onclick="getContactSharing('LM')" >Share&nbsp;Contact</a></li>
						 <li><a href="#" id="transfer_compliance1">Port</a></li>
						 
						
					</ul>
		    	   </li>
		    	   <%} %>
		    	   
		    	    <li><a href="#">Myself</a> 
			    	   <ul>
				    	     	<li><a href="#" id="employeeRecord">Record</a></li> 
				    	      	<li><a href="#" id="employeeView1">KRA-KPI&nbsp;</a></li>
				    	       	<li><a href="#" id="attendanceRecords">Leave</a></li>
				    	        <li><a href="#" id="employeeView1">Reimbursement</a></li>
				    	        <li><a href="#" id="employeeView1">Appraisal</a></li>
				    	         <li><a href="#" id="employeeView1">T & D&nbsp;</a></li>
			    	   </ul>
		    	    </li>
		    	   
		    	   <li><a href="#">HR</a>
		    	   <ul>
		    	     			<li><a href="#" id="LeavepullreportView">Pull Report</a></li> 
					    	    <li><a href="#" id="employeeView">Employee</a></li> 
					    	    <li><a href="#" id="view_sop">SOP</a></li>
					    	    <li><a href="#" id="view_kra_kpi">KRA-KPI&nbsp;</a></li>
				                <li><a href="#" id="view_compl_KRA_KPI_Map">Map&nbsp;KRA-KPI</a></li>
				                <li><a href="#" id="schedule_reporting">Schedule&nbsp;Reporting </a></li>
		    	   </ul>
		    	    </li>
		    	   
		    	   <%if(userRights.contains("lreq") || userRights.contains("lres")
		    			  || userRights.contains("drepo") || userRights.contains("arepo") 
							  || userRights.contains("srepo")){%>
		    	   <li><a href="#">Leave</a>
		        	<ul><!--
		        	     <li><a href="#" id="employeeView">Employee</a></li>
		        	    --><%if(userRights.contains("lreq")){ %><li><a href="javascript:void();" id="leave_requestView">Request</a></li><%} %>
		        	    <%if(userRights.contains("lres_VIEW")){ %><li><a href="javascript:void();" id="leave_responseView">Response</a></li><%} %>
		        	    <%if(userRights.contains("matte_ADD")){ %><li><a href="javascript:void();" id="attendance_View">Attendance</a></li> <%} %>
						<%if(userRights.contains("drepo_VIEW")){ %><li><a href="javascript:void();" id="report_Add111">Daily Report</a></li><%} %>
						<%if(userRights.contains("arepo_VIEW")){ %><li><a href="javascript:void();" id="analytical_Add">Leave Status</a></li><%} %>
						<%if(userRights.contains("srepo_VIEW")){ %><li><a href="javascript:void();" id="summary_Add">Summary</a></li><%} %>
					    <li><a href="javascript:void();" id="total_report">Total Report</a></li><!--
					    <li><a href="javascript:void();" id="total_report1">Productivity</a></li>
					   --><!--  <li><a href="javascript:void();" id="automatic_report">Automatic Report</a></li> -->

					</ul>
		    	   </li>
		    	   	<!-- ------Reimbursement BY GUDIYAA-------------------------------------------------------- -->
					<li>
						<a href="#">Reimbursement</a>
		   				<ul>
							<li><a href="javascript:void();" id="paticulars_master">Request Claim</a></li>
							<li><a href="javascript:void();" id="add_reimbursement">Action</a></li>
							<li><a href="javascript:void();" id="add_reimbursement">Claim Status</a></li>
							<li><a href="javascript:void();" id="add_reimbursement">History</a></li>
		   				</ul>
		    	   </li>
		    	   <%} %>
		 	  </ul>
		 	</li>
		 	<%} %>
		<%
		}
		%>




	<%
		if(userTpe.contains("M") || userTpe.contains("H")){
	%>
	<li><a href="#">Common Space</a>
	 <ul>
		<li><a href="#" id="organizationView">Organization</a></li>
		<li><a href="#" id="email_setting">Email Setting</a></li>
		<%if(true){%>
		<li><a href="#" id="configure_report">Configure Report</a></li>
		<%}%>
		<%if(userRights.contains("depa_VIEW"))
	     {%>
		<li><a href="#" id="group_add">Contact Type</a></li>
		<%} %>
		<%if(userRights.contains("sude_VIEW"))
	     {%>
		<li><a href="#" id="departmentView">Contact Sub-Type</a></li>
		
		<li><a href="#" id="subDepartmentView"><%=namesofDepts[1] %></a></li>
		<%} %>
		<%if(userRights.contains("empl_VIEW"))
	     {%>
		<li><a href="#" id="contactMaster_add">Primary Contact</a></li>
		<%} %>
		<%if(userRights.contains("user_VIEW"))
	     {%>
		<li><a href="#" id="userView">User</a></li>
		<%} %>
		<%if(userRights.contains("rpass_VIEW")){%>
		<li><a href="#" id="changePassword">Reset Password</a></li>
		<%}%>
		<li><a href="javascript:void();" id="blackListedView">Black List</a></li>
		<%if(userRights.contains("desi_VIEW"))
	     {%>
	     <li><a href="javascript:void();"  id="accountstatusView">Account Status</a></li>
		<li><a href="#" id="beforeNewsAlertsView">News and Alerts</a></li>
         <%} %>
		<li><a href="#" id="resend_sms_email">Resend SMS</a></li>
	
		<li><a href="#" id="resend_email">Resend Email</a></li>
		 
	    
	</ul>
	</li>
	<%
	}
	if(validApp.contains("HDM") || validApp.contains("COMPL") || validApp.contains("FM") || validApp.contains("WFPM"))
	{
	%>
	<li><a href="#">Setting</a>
	<ul>
	<%
	 if(validApp.contains("FM"))
	 {
		 if(userRights.contains("settick") || userRights.contains("setshift") || userRights.contains("setwork")|| userRights.contains("setsms")|| userRights.contains("setdraft") || userRights.contains("setbed"))
		 {
			%>
			<li>
				<a href="#">Patient Delight</a>
				<ul>
				<%
//				if(userRights.contains("settick_VIEW"))
				{
					%>
					<li><a href="#"  onclick="getConfigureTicket('FM')">Ticket Series</a></li>
					<%
				}
		//		if(userRights.contains("setshift_VIEW"))
				{
					%>
					<li><a href="#" onclick="configureShift('FM')"> Shift</a></li>
					<%
				}
		//		if(userRights.contains("setwork_VIEW"))
				{
					%>
					<li><a href="#" onclick="getWorkTiming('FM')" id="view_work_time">Working Hours</a></li>
					<%
				}
			//	if(userRights.contains("setsms_VIEW"))
				{ %>
				<li><a href="#" id="feed_sms_config">Configure SMS</a></li>
				<%}
				//if(userRights.contains("setdraft_VIEW"))
				{
				%>
				<li><a href="#"  onclick="getConfigureTask('D','FM')">Configure Feedback</a></li>
				<%
				}
				//if(userRights.contains("setbed_VIEW"))
				{
				%>
				 <li><a href="#"  id="bed_Mapping_Detail">Bed Mapping</a></li>
			<%} %>
				</ul>
			</li>
			<% 
		 }
	 }
	%>
	<!-- <li>
							<a href="#">WFPM</a>
							<ul>
								<li><a href="javascript:void();" id="offeringView">Offering</a></li> 
							<s:if test="true || #session.userRights.contains('indu')">
								<li><a href="javascript:void();" id="industryView">Industry & S.Industry</a></li>
							</s:if>
								<li><a href="javascript:void();" id="targetSegmentView">Target&nbsp;Seg</a></li>
								<li><a href="javascript:void();" id="sourceMasterView">Data&nbsp;Source</a></li>
								<li><a href="javascript:void();" id="associateTypeView">Ass.&nbsp;Type</a></li>
								<li><a href="javascript:void();" id="associateCategoryView">Ass.&nbsp;Category</a></li>
								<li><a href="javascript:void();" id="clientStatusView">Client&nbsp;Status</a></li>
								<li><a href="javascript:void();" id="associateStatusView">Ass.&nbsp;Status</a></li>
								<li><a href="javascript:void();" id="lostOperView">Lost&nbsp;Oppor</a></li>
								<li><a href="javascript:void();" id="kra_kpiView">KRA-KPI</a></li>
								<li><a href="javascript:void();" id="smsTemplateKPI">SMS&nbsp;KPI</a></li>
								<li><a href="javascript:void();" id="smsTemplateOffering">SMS&nbsp;Offering</a></li>
							</ul>
	 				</li> -->
	      <!--    	<li>
						<a href="#">HR</a>
						<ul>
								<%if(userRights.contains("ltype")){ %><li><a href="javascript:void();" id="leave_typeMasterView">Leave Type</a></li><%} %>
								<%if(userRights.contains("holi")){ %><li><a href="javascript:void();" id="holiday_listView">Holiday</a></li><%} %>
								<%if(userRights.contains("lpoli")){ %><li><a href="javascript:void();" id="leave_policyView">Policy</a></li><%} %>
								<li><a href="javascript:void();" id="employee_type">Employee Type</a></li>
								<%if(userRights.contains("tslot")){ %><li><a href="javascript:void();" id="time_slotView">Configure Shift</a></li><%} %>
			    				<%if(userRights.contains("arepc_ADD")){ %><li><a href="javascript:void();" id="attendanceReport_Confi">Attendance Starting Up</a></li><%} %>
								<li><a href="javascript:void();" id="employee_type1">Reimbusiments Type</a></li>
						</ul>
					 </li>
					  <li>
						<a href="#">Project</a>
						<ul>
							<%if(userRights.contains("ttma_ADD") || userRights.contains("ttma_VIEW") 
						|| userRights.contains("ttma_MODIFY") || userRights.contains("ttma_DELETE")){%>
						<li><a href="javascript:void();" id="task_type">Task&nbsp;Type</a></li> 
						<%}%>
							<li><a href="#"  onclick="getConfigureTicket('DAR')">Project&nbsp;ID</a></li>
						</ul>
					 </li> -->  
					 
					 <li>
	<a href="#">Referral</a>
				
					   
						
							<ul>
							<!--
									<!--<li><a href="#" onclick="getContact('T2M')" >Add Contact</a></li>
			                		<li><a href="#" onclick="getContactMapping('T2M')" >Map Contact</a></li>
			                		<li><a href="#" onclick="getContactSharing('T2M')" >Share Contact</a></li>
									<li><a href="#" onclick="getContact('T2M')" id="view_t2m_contacts">Contact</a></li>
									<li><a href="#" onclick="getContactSharing('T2M')" id="view_t2m_contacts_sharing">Contact Sharing</a></li>
									<li><a href="javascript:void();" id="mapping2">Mapping</a></li>
									<li><a href="#" id="consultants">Consultants</a></li>
									--><li><a href="javascript:void();" id="ViewconfigKeywords">Keyword</a></li>
									<li><a href="javascript:void();" id="speciality">Speciality</a></li>
									<li><a href="javascript:void();" id="locationT2M">Location</a></li>
							</ul>
						
						
						
					
				</li> 
	 
					 
					 
					 
					  
					 <% if(validApp.contains("COMPL"))
					 {
					 %>
					 <li>
						<a href="#">Operations</a>
						<ul>
							<%if(userRights.contains("taty_VIEW")) {%>
									<li><a href="#" id="view_compl_task_type">Task Type</a></li>
							<%} %>
							<li><a href="#"  onclick="getConfigureTicket('COMPL')">Ticket Series</a></li>
						</ul>
					 </li>
					 <%
					 }
					 %>
					 <li>
						<a href="#">Helpdesk</a>
						<ul>
						    <!--Go in the topclient.js for these links-->
							<%if(userRights.contains("cota_VIEW")){ %>
							  <li><a href="#"  onclick="getConfigureTask('SD','HDM')">Configure Feedback</a></li>
							<%}%>
							<%if(userRights.contains("shift_VIEW")){ %>
						       <li><a href="#" onclick="configureShift('SD','HDM')"> Shift</a></li>
						    <%}%>
							<%if(userRights.contains("tick_VIEW")){ %>
						       <li><a href="#"  onclick="getConfigureTicket('HDM')">Ticket Series</a></li>
						    <%}%>
						    <%if(true){ %>
						       <li><a href="#" onclick="getWorkTiming('HDM')" id="view_work_time">Working Hours</a></li>
						    <%}%>
						    
						</ul>
					 </li>
					     <li>
						<a href="#">Compliants</a>
						<ul>
						  <li><a href="#"  onclick="getConfigureTask('D','DREAM_HDM')">Configure Feedback</a></li>
						  <li><a href="#"  onclick="getConfigureTicket('DREAM_HDM')">Ticket Series</a></li>
					     </ul>
						 </li>
					 <li>
					<a href="#">WFPM</a>
					<ul>
					<li><a href="javascript:void();" id="offeringView">Offering</a></li> 
					<s:if test="true || #session.userRights.contains('indu')">
					<li><a href="javascript:void();" id="industryView">Industry & Sub Industry</a></li>
					</s:if>
					<li><a href="javascript:void();" id="targetSegmentView">Target&nbsp;Segment</a></li>
					<li><a href="javascript:void();" id="sourceMasterView">Data&nbsp;Source</a></li>
					<li><a href="javascript:void();" id="associateTypeView">Associate&nbsp;Type</a></li>
					<li><a href="javascript:void();" id="associateCategoryView">Associate&nbsp;Category</a></li>
					<li><a href="javascript:void();" id="clientStatusView">Client&nbsp;Status</a></li>
					<li><a href="javascript:void();" id="associateStatusView">Associate&nbsp;Status</a></li>
					<li><a href="javascript:void();" id="lostOperView">Lost&nbsp;Opportunity</a></li>
					<li><a href="javascript:void();" id="kra_kpiView">KRA-KPI</a></li>
					<li><a href="javascript:void();" id="smsTemplateKPI">SMS&nbsp;KPI</a></li>
					<li><a href="javascript:void();" id="smsTemplateOffering">SMS&nbsp;Offering</a></li>
					<li><a href="javascript:void();" id="summaryReportView">Summary Report</a></li>
					</ul>
			 	</li>
		 		 <li>
					<a href="#">HR</a>
					<ul>
						<%if(userRights.contains("ltype")){ %><li><a href="javascript:void();" id="leave_typeMasterView">Leave Type</a></li><%} %>
						<%if(userRights.contains("holi")){ %><li><a href="javascript:void();" id="holiday_listView">Holiday</a></li><%} %>
						<%if(userRights.contains("lpoli")){ %><li><a href="javascript:void();" id="leave_policyView">Policy</a></li><%} %>
						<li><a href="javascript:void();" id="employee_type">Employee Type</a></li>
						<%if(userRights.contains("lconfig")){ %><li><a href="javascript:void();" id="leave_confiMasterView">Configure Leave</a></li><%} %>
						<%if(userRights.contains("tslot")){ %><li><a href="javascript:void();" id="time_slotView">Configure Shift</a></li><%} %>
						    	<%if(userRights.contains("arepc_ADD")){ %><li><a href="javascript:void();" id="attendanceReport_Confi">Attendance Starting Up</a></li><%} %>
						<li><a href="javascript:void();" id="employee_type1">Reimbusiments Type</a></li>
					</ul>
 				</li>
					 

	</ul>
	</li>

	<%
	 }%>
</ul>
</div>
<div class="clear"></div>
</body>
</html>