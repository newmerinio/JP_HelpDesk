<%@taglib prefix="s" uri="/struts-tags"%>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();

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
//System.out.println("Valid Appss on top client::: "+validApp);
%>

<%@page import="org.hibernate.usertype.UserType"%><html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/common/topclient.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	
</head>
<body onload="getNotify();">
<div class="logo1" id="PageRefresh" style="float: left;">
<img
	src="<%request.getContextPath();%><s:property value="%{orgImgPath}" />" width="180px" height="65px" /></div>
<div id="serverTimeDiv"
	style="float: left; padding-top: 20px; padding-left: 35px;"></div>
    <div id="notificationDiv" style="padding-top: 10px;padding-left: 35px;"><center><b>Notification bar loading</b><img id="indicator2" src="images/notificationloader.gif"/></center></div>

<div class="wrap_nav">
<ul class="nav_links">
	<li><a href="javascript:void();">
	 <FONT style="font-size: 12px; font-family: Arial, Sans-Serif; font-weight: normal; color: #000; font-weight: bold;"><B><s:property
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
			<img src="<s:property value="%{profilePicName}" />" width="20px" height="30px" />
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
	<ul><!--
		<li><a href="#" id="asset_dashboard_view">Asset</a></li>
		<li><a href="#" id="asset_cmplaint_dashboard">Asset Complaint</a></li>

		--><%if(validApp.contains("HDM")){%>
			<li><a href="#" id="ticket_normal_newdashboard">Helpdesk</a></li>
		<%}%>
	   <%if(validApp.contains("COMPL"))
		{
		%>
		<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("port") || userRights.contains("coma") || userRights.contains("tana")
					  || userRights.contains("taty") || userRights.contains("rert") || userRights.contains("cvel")		
					  || userRights.contains("core")) {%>
		<!-- <li><a href="#"  id="mgmt_compl_dashboard">Operational Task</a></li> -->
				
		<%}%>
		<%
		}
		%>
		<li><a href="#"  onclick="dashboardPatientActivity();">Patient Activity</a></li>
		<li><a href="#" >Referral Activity</a></li>
		<li><a href="#" >Corporate Activity</a></li>
		<li><a href="#" >Sales Activity</a></li>
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
	   <%if(validApp.contains("VAM"))
		{
		%>
		
		<li><a href="javascript:void();"  id="vam_dashboard_view">Visitor Alert</a></li>
		<li><a href="javascript:void();" id="vehicle_dashboard_view">Vehicle Alert</a></li>
		<%
		}
		%>
	   <% 
		if(validApp.contains("FM"))
		{
				if(userTpe.equalsIgnoreCase("o") || userTpe.equalsIgnoreCase("M"))
				{%>
				<li><a href="#"  id="dash_view">Patient Delight</a></li>
				<% }
				if( userTpe.equalsIgnoreCase("o") ||  userTpe.equalsIgnoreCase("H") || userTpe.equalsIgnoreCase("N"))
				{%>
					<li><a href="#"  id="dash_viewNormal">Feedback</a></li>
				<% }
		}
	   %>
	     	<%if(userTpe.equalsIgnoreCase("M"))
		     {%>
			<li><a href="#" onclick="calendarViewManager();">Manager Activity Calendar</a></li>
			<li><a href="#" onclick="extendApproval();">Extend Activity</a></li>
			<%} %>
			
			<%if(userTpe.equalsIgnoreCase("N"))
		     {%>
				<li><a href="#" onclick="calendarView();">Activity Calendar</a></li>
				 <li><a href="#" onclick="mainPage();">Activity Page</a></li>
			<%} %>
	        
							
		       <!--<li><a href="#"  id="main_dash_KR">KR</a></li>-->
	</ul>
	</li>
	<%}
	%>
	<%if(validApp.contains("FM"))
	 {
		if(true) 
		{%>    	
	<li>
		<a href="#">Patient Delight</a>
		<ul>
			<%
			//if(userRights.contains("nefn_VIEW") || userRights.contains("nefpcc_VIEW") || userRights.contains("posfeed_VIEW") || userRights.contains("negfeed_VIEW")|| userRights.contains("dcti_VIEW")) 
			{
			%>
			<!--<li><a href="#" id="feed_action_pending_admin">Re-Opened</a> </li>-->
					<li><a href="#" onclick="viewFeedbackActivityBoard();">Activity Board</a></li>
					<li><a href="#" onclick="viewFeedbackActivityReport();">Activity Report</a></li>
					<%if(userRights.contains("audt_VIEW")){%>
					<li><a href="#" onclick="viewAuditReport();">Audit Report</a></li>
					<%} %>
					<%
					if(false)
					{%>	<li>
							<a href="#" id="feed_Details">Mode&nbsp;Status</a>
						</li>
						<%} 
					
					if(false)
					{%>	<li>
						<a href="#" id="feed_Details_Report">SMS Feedback</a>
					</li>
						<%} 
					if(userRights.contains("prodemp_VIEW") || userRights.contains("prodcat_VIEW"))
					{%>	
					<li>
						<a href="#" onclick="getProductivty('FM','Employee')" >Productivity</a>
					</li>
					<%}
					if(false)
					{
					%>
					<li>
						<a href="#" onclick="getProductivty('FM','Category')">Categorywise</a>
					</li>
					<%
					}
					if(false)
					{%>	<li>
							<a href="#" id="patdetails_view">Patient Details</a>
						</li>
						<%} 
					if(false)
					{
					%>
					<li><a href="#" id="keyWords_view">Keywords</a></li>
					<li><a href="#" id="total_feedReport_view">Reports</a></li>
					<%} %>
					
					
			<%
			}
			}
			%>
		</ul>
	</li>
	<%
	}%>
    <% if(validApp.contains("WFPM")){%>
	    <li>
	    	<a href="#">Healthcare&nbsp;Sales</a>
					<ul>
					    	<li>
								<ul>
								<li><a href="javascript:void();" id="communication_activity_MCRM" onclick="patientVisitView();">Activity&nbsp;Board</a></li>
								<li><a href="javascript:void();" id="patientWFPM">Patient&nbsp;Leads</a></li>
								<li><a href="#" onclick="corporate_add();">Corporate&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
							    <li><a href="#" onclick="referralView();">Referral</a></li>
								<li><a href="javascript:void();"  onclick="schedule_planner();">Schedule&nbsp;Plan</a></li>
								<li><a href="javascript:void();"  onclick="activity_view();">Activity View</a></li>
								<!-- <li><a href="javascript:void();"  onclick="account_dept();">Account Dept</a></li> -->
								<%if(userTpe.equalsIgnoreCase("H"))
							     {%>
									<li><a href="javascript:void();"  onclick="account_dept_view();">Reimbursement</a></li>
									<li><a href="javascript:void();"  onclick="advance_main();">Advance/Settlement</a></li>
								<%} %>
								<!--<li><a href="javascript:void();"  onclick="activity_plan();">Activity&nbsp;Plan</a></li>
								<li><a href="javascript:void();"  onclick="approval_activity();">Approval of Activity</a></li>
								<li><a href="javascript:void();" >Event&nbsp;Planner</a></li>-->
								<li><a href="javascript:void();"  onclick="eventPlanning_addMaster();">View&nbsp;Event&nbsp;Plan</a></li>
								<%-- <li><a href="javascript:void();" id="showWFPMCommonDashboard">Activity Board</a></li>
								<li><a href="javascript:void();" id="opportunityDetails">Review Board</a></li>
								<li><a href="javascript:void();" id="leadAction" >Lead</a></li>
								<li><a href="javascript:void();" id="pclientView">Opportunity</a></li>
								<li><a href="javascript:void();" id="pAssociateView">Associate</a></li>
								<li><a href="javascript:void();" id="clientSupportView">Client Support</a></li>
								<s:if test="#session['userRights'].contains('targ') || 'true'"><li><a href="javascript:void();" id="kpi_autoFill">KPI&nbsp;Reports</a></li></s:if>
						   		<s:if test="#session['userRights'].contains('targ') || 'true'"><li><a href="javascript:void();" id="offering_autoFill">Offering&nbsp;Reports</a></li></s:if>
							    <li><a href="javascript:void();" onclick="patientActivityView();">Patient Activity</a></li> --%>
							    <%--<s:if test="#session['userRights'].contains('targ') || 'true'"> <li><a href="javascript:void();" id="opportunity_report_View">Opportunity&nbsp;Reports</a></li></s:if> --%>
						    	</ul>
						   </li>
					</ul>
			</li>
		<li>
			<a href="#">CRM</a>
					<ul>
					     <li><a href="javascript:void();" id="communication_activity_WFPM">Activity Board</a></li>
					    <li><a href="javascript:void();" id="dynamic_mailtag_WFPM">Dynamic Mail Tag</a></li><!--
					     <li><a href="javascript:void();" id="rich_mailtext_WFPM">Mail Editor</a></li>
					    <li><a href="javascript:void();" id="communication_WFPM">Communication</a></li>-->
						<!--<li><a href="javascript:void();" id="group_WFPM" >Group</a></li>
						<li><a href="javascript:void();" id="templates_WFPM">Templates</a></li>
						<li><a href="javascript:void();" id="blackList__WFPM">Black List</a></li>
						<li><a href="javascript:void();" id="blackOutTime_WFPM">Black Out Time</a></li>-->
						<li><a href="javascript:void();" id="reports_WFPM">Email Reports</a></li>
					</ul>
	  </li>
	 
	  
	    <li>
			<a href="#">Wellness</a>
					<ul>
					 <li><a href="javascript:void();" id="communication_activity_MCRM" onclick="patientVisitView();">Patient&nbsp;Activity</a></li>
					   <%
			if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H")) 
			{
			%>
					     <li><a href="javascript:void();" id="patient_report_MCRM" onclick="patientReportView();">Reports</a></li>
					    <li><a href="javascript:void();" onclick="patientWellnessReg();">Patient&nbsp;Registration</a></li>
						<li><a href="javascript:void();" id="reports_MCRM" onclick="questionairView();">Questionnaire</a></li>
						
				 <%
			}
			%>
						<!-- <li><a href="javascript:void();" id="reports_MCRM" onclick="profileView();">Patient Profile</a></li> -->
					</ul>
	  </li>
	  	<%
		}
	   %>
		<%if(validApp.contains("KR"))
		{%>
	
	<li><a href="#">KR</a>
	 <ul>
      <li>
       <ul>
       	    <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("library_VIEW")) {%>
     	    <li><a href="javascript:void();" onclick="libraryView();">Library</a></li>
     	    <%} %>
     	    <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("share_VIEW")|| true) {%>
			<li><a href="javascript:void();" id="beforeKRShareView">Share By Me</a></li>
			<%} %>
			<!-- <li><a href="javascript:void();" id="krLibrarySearch">Library</a></li> -->
		    <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("actionable_VIEW")) {%>
			<li><a href="javascript:void();" id="actionableView">Share With Me</a></li>
			<%} %><!--
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("productivity_VIEW")) {%>
			<li><a href="javascript:void();" id="brforeKRGroupView2">Productivity</a></li>
			<%} %>
			--><%if(userTpe.equalsIgnoreCase("o") || userRights.contains("group_VIEW")) {%>
		   <li><a href="javascript:void();" onclick="krGroupHeaderView();" id="">Group</a></li>
			<%} %>
	</ul>
	 </li>
	    </ul>
	</li>
		<%}%>	
	    <%
		if(validApp.contains("DAR"))
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
		if(validApp.contains("COMM"))
	  {
	%>
	<li>
		<a href="#">Communication</a>
  		<ul>
  		 <%if(true) {%>
			<li>
				<a href="#">SMS</a>
				<ul>
				   <%if(true) {%>
					<li><a href="javascript:void();" id="instantmessagereport">Push&nbsp;SMS</a></li>
					<%} %>
					<li><a href="javascript:void();" id="groupdetailsView">Group</a></li>
					<%if(true) {%>
					<li><a href="javascript:void();" id="templatessView">Templates</a></li>
					<%} %>
				
					<%if(true) {%>
					<li><a href="javascript:void();" id="dailyautopushreport">Auto&nbsp;Push&nbsp;Reports</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			
			
			<li><a href="#">Email</a>
				<ul>
				
					<%if(true) {%>
					<li><a href="javascript:void();" id="instantmailsender">Send&nbsp;Mail</a></li>
					<%} %>
						<%if(true) {%>
					<li><a href="javascript:void();" id="instantmailreport">Reports</a></li>
					<%} %>
					<%if(true) {%>
					<li><a href="javascript:void();" id="mailtemplate">Template</a></li>
					<%} %>
				</ul>
			</li>
			
  		</ul>
	</li>
<%} %>

		<%if(true){%>
		 <!--<li>
			<a href="#">HR</a>
			<ul>
			        <%if(userRights.contains("lconfig")){%>
		    	   <li><a href="#">Admin</a> 
		        	<ul>
						 <li><a href="#" onclick="getContact('LM')" >Add&nbsp;Contact</a></li>
					     <li><a href="#" onclick="getContactMapping('LM')" >Map&nbsp;Contact</a></li>
					   	 <li><a href="#" onclick="getContactSharing('LM')" >Share&nbsp;Contact</a></li>
						 <li><a href="#" id="transfer_compliance1">Port</a></li>
						 <%if(userRights.contains("lconfig")){ %><li><a href="javascript:void();" id="leave_confiMasterView">Configure Leave</a></li><%} %>
					</ul>
		    	   </li>
		    	   <%} %>
		    	   <%if(userRights.contains("lreq") || userRights.contains("lres")
		    			  || userRights.contains("drepo") || userRights.contains("arepo") 
							  || userRights.contains("srepo")){%>
		    	   <li><a href="#">Leave</a>
		        	<ul>
		        	     <li><a href="#" id="employeeView">Employee</a></li>
		        	    <%if(userRights.contains("lreq")){ %><li><a href="javascript:void();" id="leave_requestView">Request</a></li><%} %>
		        	    <%if(userRights.contains("lres_VIEW")){ %><li><a href="javascript:void();" id="leave_responseView">Response</a></li><%} %>
		        	    <%if(userRights.contains("matte_ADD")){ %><li><a href="javascript:void();" id="attendance_View">Attendance</a></li> <%} %>
						<%if(userRights.contains("drepo_VIEW")){ %><li><a href="javascript:void();" id="report_Add111">Daily Report</a></li><%} %>
						<%if(userRights.contains("arepo_VIEW")){ %><li><a href="javascript:void();" id="analytical_Add">Leave Status</a></li><%} %>
						<%if(userRights.contains("srepo_VIEW")){ %><li><a href="javascript:void();" id="summary_Add">Summary</a></li><%} %>
					    <li><a href="javascript:void();" id="total_report">Total Report</a></li>
					    <li><a href="javascript:void();" id="total_report1">Productivity</a></li>
					    <li><a href="javascript:void();" id="automatic_report">Automatic Report</a></li> 
					</ul>
		    	   </li>
		    	   	 ------Reimbursement BY GUDIYAA-------------------------------------------------------- 
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
		 	--><%} %>
		<%//if(validApp.contains("CS"))
		{%>
	<li><a href="#">Admin</a>
	 <ul>
      <li>
          <ul>
		     <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("orgn")){%>
			    <li><a href="#" onclick="organization();" id="">Organization</a></li>
				<!--<li><a href="#" id="organizationView">Logo</a></li>
			--><%}%>
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("empl_VIEW"))
		     {%>
			<li><a href="#"  onclick="contactMaster_add();">Primary Contact</a></li>
			<%} %>
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("user_VIEW"))
		     {%>
			<li><a href="#" onclick="userView();">User</a></li>
			<%} %>
			<%//if(userRights.contains("coma_VIEW"))
		     {%>
			<li><a href="#" onclick="getContact()">Manage&nbsp;Contact&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	        <%} %><!--
	        <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("mapcon_VIEW"))
		     {%>
	        <li><a href="#" onclick="getContactMapping()" >Map&nbsp;Contact</a></li>
	   	  <%} %>
	   	  <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("sharecon_VIEW"))
		     {%>
	   	    <li><a href="#" onclick="getContactSharing()" >Share&nbsp;Contact</a></li>
	        <%} %>-->
	        <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("port_VIEW"))
		     {%>
	        <li><a href="#" id="transfer_compliance1">Port</a></li>
	        <%} %>
	         <li><a href="#" id="" onclick="working();">Working Hours</a></li>
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("rpass_VIEW")){%>
			<li><a href="#" id="changePassword">Reset Password</a></li>
			<%}%>
			<li><a href="moreSettings.action"  class="moreSettings">More Setting</a></li>
			 <li><a href="javascript:void();" id="blackListedView">Exclusion</a></li>
			 <li><a href="javascript:void();"  id="accountstatusView">Account Status</a></li>
		
	         <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("rese"))
		     {%>
			<li><a href="#" id="resend_sms_email">Resend SMS</a></li>
		   <%} %>
		    	<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("resmail"))
		     {%>
			<li><a href="#" id="resend_email">Resend Email</a></li>
			   <%} %>
			   	<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("desi_VIEW"))
		     {%>
			<li><a href="#" id="beforeNewsAlertsView">News and Alerts</a></li>
	         <%} %>
	         <li><a href="#" onclick="helpdocumentmapping()">Help Document Mapping</a></li>
			 <li><a href="#" onclick="uploaddocument()">Upload Doc</a></li>
	    </ul>
	    </li>
	</ul>
	</li>
	
	<%
	}
		
		
	
	%>
	<li><a href="#">Starting Up</a>
	<ul>
	     <li><a href="#">Starting Up</a>
          <ul>
		   <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("depa"))
		     {%>
		        <li><a href="#" onclick="BranchOffice_add()">Organization Hierarchy</a></li>
				<li><a href="#" onclick="group_add();" >Contact Type</a></li>
			<%} %>
		     <%if(userTpe.equalsIgnoreCase("o") || userRights.contains("sude"))
		     {%>
				<li><a href="#" id="" onclick="departmentView();">Contact Sub Type</a></li>
			<%} %>
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("vemail")){%>
			<!--
				<li><a href="#" id="email_setting">Email Setting</a></li>
			-->
			<%}%>
				<li><a href="#"  onclick="sms_Draft_View();">SMS Auto Alerts</a></li>
				<li><a href="#" id="" onclick="email_Draft_View();">Email Auto Alerts</a></li>
			<%if(userTpe.equalsIgnoreCase("o") || userRights.contains("conf")){%>
				<li><a href="#" onclick="configure_report();" >Configure Report</a></li>
			<%}%>
				<li><a href="javascript:void();" id="holiday_listView">Holiday</a></li>
		<!-- 	<li><a href="javascript:void();" id="user_history_View">Action&nbsp;History</a></li> -->
	    </ul>
	    </li>
	    <%
	   
	 if(validApp.contains("FM"))
	 {
		 //if(userRights.contains("settick") || userRights.contains("setshift") || userRights.contains("setwork")|| userRights.contains("setsms")|| userRights.contains("setdraft") || userRights.contains("setbed"))
		 {
			%>
			<li>
				<a href="#">Patient Delight</a>
				<ul>
				<%
				if(userRights.contains("settick_VIEW"))
				{
					%>
					<li><a href="#"  onclick="getConfigureTicket('FM')">Ticket Series</a></li>
					<%
				}
				if(userRights.contains("setshift_VIEW"))
				{
					%>
					<li><a href="#" onclick="configureShift('D','FM')"> Shift</a></li>
					<%
				}
				if(userRights.contains("setwork_VIEW"))
				{
					%>
					<!--<li><a href="#" onclick="getWorkTiming('FM')" id="view_work_time">Working Hours</a></li>
					--><%
				}
				if(userRights.contains("setsms_VIEW"))
				{ %>
				<li><a href="#" id="feed_sms_config">Configure SMS</a></li>
				<%}
		//		if(userRights.contains("setdraft_VIEW"))
				{
				%>
				<li><a href="#"  onclick="getConfigureTask('D','FM')">Configure Feedback</a></li>
				<%
				}
				if(userRights.contains("setbed_VIEW"))
				{
				%>
				 <li><a href="#"  id="bed_Mapping_Detail">Bed Mapping</a></li>
				 <li><a href="#" onclick="getRoaster('D','FM')">Roaster</a></li>
			      <li><a href="#" onclick="escTypeConfig()">Escalation Type Config</a></li>
			      <li><a href="#" onclick="escDeptConfig()">Escalation Dept Config</a></li>
			      <li><a href="#" onclick="ticketViewConfig()">Ticket Type Config</a></li>
			<%} %>
			     
				</ul>
			</li>
			<% 
		 }
	 }
	%>
		<%--  <li>
			<a href="#">Referral</a>
			<ul>
			    <li><a href="#" id="consultants">Consultants</a></li> 
				<li><a href="javascript:void();" id="ViewconfigKeywords">Keyword</a></li>
				<li><a href="javascript:void();" id="speciality">Speciality</a></li>
				<li><a href="javascript:void();" id="locationT2M">Location</a></li>
			</ul>
		 </li>
		 <li><a href="#">Helathcare&nbsp;Sales </a>
			<ul>
			<li><a href="javascript:void();" id="offeringView">Offering</a></li>
			<li><a href="#" onclick="relationshipType_addMaster();">Realtionship&nbsp;Type</a></li>
			<li><a href="#" onclick="relationship_addMaster();">Realtionship&nbsp;Sub-Type</a></li>
			<li><a href="#" onclick="category_addMaster();">Category&nbsp;Type</a></li>
			<li><a href="#" onclick="source_addMaster();">Source&nbsp;Type</a></li>
			<li><a href="javascript:void();" id="forcastcategaryView">Forcast&nbsp;Categary</a></li>
		    <li><a href="javascript:void();" id="lostOperView">Lost&nbsp;Opportunity</a></li>
			<li><a href="#" onclick="lostOpportunity();">Lost&nbsp;Opportunity</a></li><!--
			<li><a href="#" onclick="territory_addMaster();">Configure&nbsp;Territory</a></li>
			<li><a href="#" id="locationView">Location</a></li>-->
			<li><a href="javascript:void();" onclick="mapDoctorOfferingView();">Map&nbsp;Doctor</a></li>
			<li><a href="javascript:void();" id="relationshipMgrView">Relationship&nbsp;Manager</a></li>
			<li><a href="#" onclick="configure_schedule();">Configure&nbsp;Timelines</a></li>
			<li><a href="#" onclick="configureCCTerr();">Configure&nbsp;Location</a></li>
			<li><a href="#" onclick="activity_addMaster();">Configure&nbsp;Actvity</a></li>
			<li><a href="javascript:void();" id="patientStatusId">Activity Escalation</a></li>
			<li><a href="#" onclick="eventttt_addMaster();">Configure&nbsp;Event</a></li>
			<li><a href="#" onclick="expenseparameter_addMaster();">Expense&nbsp;Parameter</a></li>
			<li><a href="javascript:void();"  onclick="map_employee_location();">Employee&nbsp;With&nbsp;Location</a></li>
			<!--<li><a href="#" onclick="budget_planning();">Budget&nbsp;Planning</a></li>
			--><!-- <li><a href="#" onclick="allocate_budget();">Allocate&nbsp;Budget</a></li> -->
			<li><a href="#" onclick="currency_addMaster();">Currency&nbsp;Mapping</a></li>
			<li><a href="#" onclick="budgetPlan_addMaster();">Budget&nbsp;Plan</a></li>
			<li><a href="#" onclick="kpi_addMaster();">Configure&nbsp;KPI</a></li>
            <li><a href="javascript:void();"  onclick="expenseband_addMaster();">Map&nbsp;Band&nbsp;With&nbsp;Cost&nbsp;Parameter</a></li>
			<li><a href="javascript:void();" onclick="bandEmployee_addMaster();" >Employee&nbsp;Map&nbsp;With&nbsp;Band</a></li>								
			<!--new Merge 07Oct2015   -->
			<!-- <li><a href="#" onclick="adhocCrop_addMaster();">Adhoc&nbsp;Croporate&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
			<li><a href="#" onclick="adhocAsso_addMaster();">Adhoc&nbsp;Associate&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> -->
			</ul>
		</li>
		  <li> <a href="#">CPS</a>
			 <ul>
		        <li><a href="#"  onclick="CpsEscalation();">Manage&nbsp;Escalation</a></li>
				<li><a href="#" onclick="corporate_add();">Corporate</a></li>
				<li><a href="#" onclick="package_addMaster();">Package</a></li>
				<li><a href="#" onclick="package_add();">Manage&nbsp;Package</a></li>
				<li><a href="#" onclick="service_addMaster();">Service&nbsp;Master</a></li>
				<li><a href="#" onclick="speciality_addMaster();">Speciality&nbsp;Master</a></li>
				<li><a href="#" onclick="USGServices_addMaster();">USG&nbsp;&&nbsp;Lab&nbsp;Services</a></li>
				<li><a href="#" onclick="DocSchedule_addMaster();">Manage&nbsp;Doctor&nbsp;Schedule</a></li>
				<li><a href="#" onclick="speciality_addMaster();">Speciality&nbsp;Master</a></li>
				<li><a href="#" onclick="USGServices_addMaster();">USG&nbsp;&&nbsp;Lab&nbsp;Services</a></li>
				<li><a href="#" onclick="DocSchedule_addMaster();">Manage&nbsp;Doctor&nbsp;Schedule</a></li>
			</ul>
			  </li>
	</ul>
	</li> --%>
	  <%
					 %>
					  <%
	if(userTpe.equalsIgnoreCase("o"))
	{
	 %>
	<li><a href="#" onclick="fetchEditorData();">Configuration Editor</a>
	<%--  <ul>
		<li>
          <ul>
          	<s:iterator id="ConfigurationUtilBean" value="configBean" >
				<li><a href="javascript:void();" name="<s:property value="%{field_value}"/>" onclick="OnlickFunction(${id},name);"><b>   <s:property value="%{field_value}"/></b></a>
			
			</s:iterator>
		  </ul>
		</li>
	    </ul> --%>
	    </li>
	<%} %>
	</ul>
</div>
<div class="clear"></div>
</body>
</html>