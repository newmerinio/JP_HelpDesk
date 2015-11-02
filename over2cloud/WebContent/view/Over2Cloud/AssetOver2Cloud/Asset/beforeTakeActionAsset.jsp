<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
	int temp=0;
%>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
$.subscribe('complete', function(event,data)
 {   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
	 setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	 setTimeout(function(){ $("#takeActionGrid").dialog('close'); }, 800);
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
<title>Compliance</title>
</head>
<body>
    <s:form id="takeActionForm" action="takeActionOnAsset" method="post"  name="takeActionForm" enctype="multipart/form-data" theme="css_xhtml">
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
    <s:hidden name="assetReminderID" id="assetReminderID" value="%{assetReminderID}"></s:hidden>
    <div id="makeItHidden">
    <center>
    	<font face="Arial, Helvetica, sans-serif" size="2"><B><s:property value="%{moduleName}"/> Action For <s:property value="%{assetName}"/>, Code: <s:property value="%{assetCode}"/>, Sr. No. <s:property value="%{assetMfgSl}"/></B></font>
    </center>
    <br><br>
    <s:iterator value="dataMap" status="status">
     				<%if(temp%2==0){ %>
					  <div class="newColumn">
	                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
	                  <div class="rightColumn">
	                  <s:if test="key=='Document 1'">
	                  	<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc1}"/>">
							<s:property value="value"/>
						</a>
	                  </s:if>
	                  <s:elseif test="key=='Document 2'">
	                  	<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc2}"/>">
							<s:property value="value"/>
						</a>
	                  </s:elseif>
	                  <s:else>
	                  	<label id="%{key}"><s:property value="%{value}"/></label>
	                  </s:else>
	                  </div>
	                  </div>
					<%} else {%>
					  <div class="newColumn">
	                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
	                  <div class="rightColumn">
	                  <s:if test="key=='Document 1'">
	                  	<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc1}"/>">
							<s:property value="value"/>
						</a>
	                  </s:if>
	                  <s:elseif test="key=='Document 2'">
	                  	<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc2}"/>">
							<s:property value="value"/>
						</a>
	                  </s:elseif>
	                  <s:else>
	                  	<label id="%{key}"><s:property value="%{value}"/></label>
	                  </s:else>
	                  </div>
	                  </div>
				 	<%}
     					if(temp==6)
     					{%>
     						<s:hidden name="dueDate" value="%{value}"/>
     					<%}
     					else if(temp==4)
     					{%>
     						<s:hidden name="dueTime" value="%{value}"/>
     					<%}
				  		temp++;
				 	%>
     </s:iterator>
     </div>
     <s:hidden id="nextDueDate" value="%{nextDueDate}" />
	    <div class="newColumn">
        <div class="leftColumn">Action Status:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:82%"
                    id="actionTaken"
                    name="actionTaken" 
                    list="takeActionStatus" 
                    headerKey="-1"
                    headerValue="Select Status"
                    onchange="showHide(this.value)"
                    >
        </s:select>
        </div>
        </div>
        <span id="complSpan" class="pIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,</span>
        <span id="snnozeComplSpan" class="snzpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,snooze_date#snooze_date#date#,snooze_time#snooze_time#time#,</span>
        <span id="snnozeComplSpan" class="reschdpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#sc,reschuedule_date#reschuedule_date#date#,reschuedule_time#reschuedule_time#time#,</span>
        <div class="newColumn">
        <div class="leftColumn">Comments:</div>
        <div class="rightColumn"><span class="needed"></span><s:textfield cssClass="textcell" id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
 	    
 	    
 	    <div id="documentDiv" style="display:none">
 					  <div class="newColumn">
	                  <div class="leftColumn">Document:</div>
	                  <div class="rightColumn">
 	    			  <s:file name="takeActionDoc" id="takeActionDoc"  cssClass="textField"/></div>
 	    			  </div>
 	    			  </div>
 	    
 		<div id="snoozeDiv" style="display:none">
					  <div class="newColumn">
	                  <div class="leftColumn">Snooze Date:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="snooze_date" name="snoozeDate" cssClass="textField"size="20" value="tomorrow" readonly="true" minDate="1"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Snooze Date"/>
	                  </div>
	                  </div>
					  <div class="newColumn">
					  <div class="leftColumn">Snooze Time:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="snooze_time" name="snoozeTime" placeholder="Enter Snooze Time" showOn="focus" readonly="true" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </div>
					  </div>
					  
		</div>	
		<div id="reScheduleDiv" style="display:none">			  
					  <div class="newColumn">
	                  <div class="leftColumn">Reschedule Date:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="reschuedule_date" name="reschuedule_date" cssClass="textField" size="20" readonly="true" value="today" minDate="0" changeMonth="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
	                  </div>
	                  </div>
					  <div class="newColumn">
					  <div class="leftColumn">Reschedule Time:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="reschuedule_time" name="rescheduleTime" placeholder="Enter Due Time" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </div>
					  </div>
	
	   </div>
	   <div class="clear"></div>
	   <div class="clear"></div>
	   
	   <center>
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