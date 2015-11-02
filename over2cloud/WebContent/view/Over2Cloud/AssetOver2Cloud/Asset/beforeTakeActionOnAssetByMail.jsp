<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
	int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
$.subscribe('complete', function(event,data)
 {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
 });

function showHide(statusId)
{
    if(statusId=='Snooze')
	{
    	document.getElementById("snoozeDiv").style.display="block";
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("documentDiv").style.display="none"; 
	}
    else if(statusId=='Reschedule')
    {
    	document.getElementById("reScheduleDiv").style.display="block"; 
    	document.getElementById("snoozeDiv").style.display="none";
    	document.getElementById("documentDiv").style.display="none"; 

    }
    else
    {
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("snoozeDiv").style.display="none";
    	document.getElementById("documentDiv").style.display="block"; 
    }
}
</SCRIPT>
<title>Asset</title>
</head>
<body>
    <s:form id="takeActionFormByMail" action="takeActionOnAssetByMail"  name="takeActionFormByMail" enctype="multipart/form-data" theme="css_xhtml">
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: black;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px; display: none;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
    <s:hidden name="assetReminderID" id="assetReminderID"></s:hidden>
    <s:hidden name="dbName" id="dbName"></s:hidden>
    <s:hidden name="empId" id="empId"></s:hidden>
    <br>
     <br>
    <table width="60%" align="center" border="0">
    	<tr>
			<th colspan="2">
				Asset Action Via Mail
			</th>    	
    	</tr>
    
     <s:iterator value="dataMap" status="status">
      
     <tr>
     	<%if(temp%2==0){ %>
					  <td align="left">
	                  	<s:property value="%{key}"/>:
	                  </td>
	                  <td align="left">
	                  	<label id="%{key}"><s:property value="%{value}"/></label>
	                  </td>
					</tr><%} else {%>
					 <tr> <td align="left">
	                  	<s:property value="%{key}"/>:
	                  </td>
	                  <td align="left"> 
	                  	<label id="%{key}"><s:property value="%{value}"/></label>
	                  </td>
				 	<%}
     					if(temp==4)
     					{%>
     						<s:hidden name="dueDate" value="%{value}"/>
     					<%}
     					else if(temp==5)
     					{%>
     						<s:hidden name="dueTime" value="%{value}"/>
     					<%}
				  		temp++;
				 	%>
	</tr>
     </s:iterator>
     
     <s:hidden id="nextDueDate" value="%{nextDueDate}" />
	    <tr>
        <td>Action Status:</td>
		<td><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:50%"
                    id="actionTaken"
                    name="actionTaken" 
                    list="takeActionStatus" 
                    headerKey="-1"
                    headerValue="Select Status"
                    onchange="showHide(this.value)"
                    >
        </s:select>
        </td>
        <span id="complSpan" class="pIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,</span>
        <span id="snnozeComplSpan" class="snzpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,snooze_date#snooze_date#date#,snooze_time#snooze_time#time#,</span>
        <span id="snnozeComplSpan" class="reschdpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#sc,reschuedule_date#reschuedule_date#date#,reschuedule_time#reschuedule_time#time#,</span>
        
        <tr>
        <td>Comments:</td>
        <td><span class="needed"></span> <s:textfield cssClass="textcell" id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield></td>
 	    </tr>
 	    <tr id="documentDiv" style="display:none">
 	                  <td  align="left" width="30%">
	                   Document:
 	    			  </td>
					  <td  align="left" width="60%">
	                    <s:file name="takeActionDoc" id="takeActionDoc" cssClass="2"/>
 	    			  </td>
		</tr>
 	 
 		<tr>
 			<td width="100%" colspan="2" >
 				<table width="100%" border="0" id="snoozeDiv" style="display:none" align="center">
 					<tr>
 						<td  align="left" width="35%">
	                	 Snooze&nbsp;Date:  
	                 </td>
	                 <td  align="left" width="100%"><span class="needed"></span>
	                  	 <sj:datepicker id="snooze_date" name="snoozeDate" cssClass="textField" size="20" value="tomorrow"  minDate="1"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Snooze Date"/>
	                 </td>
	               
					  <td  align="left" width="100%">
					  		Snooze&nbsp;Time:
					  </td>
	                  <td  align="left" width="100%"><span class="needed"></span>
	                  		<sj:datepicker id="snooze_time" name="snoozeTime" placeholder="Enter Snooze Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </td>
 					</tr>
 				</table>
 			</td>
		</tr>	
		
		<tr >			  
					<td width="100%" colspan="2" > 
					<table id="reScheduleDiv" style="display:none" width="100%" align="center"> 
					<td  align="left" width="34%">
	                  		Reschedule&nbsp;Date:
	                  </td>
	                  <td  align="left" width="100%"><span class="needed"></span>
	                  		<sj:datepicker id="reschuedule_date" name="rescheduleDate" cssClass="textField"size="20" value="today" minDate="0" changeMonth="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
	                  </td>
				
					  <td  align="left" width="100%">
					  		Reschedule&nbsp;Time:
					  </td>
	                 <td  align="left" width="100%"><span class="needed"></span>
	                  		<sj:datepicker id="reschuedule_time" name="rescheduleTime" placeholder="Enter Due Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </td>
					</table> 
	                  </td>
	   </tr>
	    </table>  
   		<center>
   		<br>
   		<br>
             <div class="type-button">
             <sj:submit 
                        targets			   	=		"Result" 
                        clearForm			=		"true"
                        value				=		"Save" 
                        effect				=		"highlight"
                        effectOptions		=		"{color:'#222222'}"
                        effectDuration		=		"5000"
						button				=		"true"
                        onCompleteTopics	=		"complete"
                        onBeforeTopics		=		"validateOnTakeAction"
                        />
              </div> 
      </center>
   
     <sj:div id="foldeffect"  effect="fold">
            <div id="Result"></div>
     </sj:div>
  </s:form>
</body>
</html>