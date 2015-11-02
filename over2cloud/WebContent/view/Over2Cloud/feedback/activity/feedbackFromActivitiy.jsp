<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">


function viewActivityPage()
{
	//alert("Hello");
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
        success : function(subdeptdata) {
      $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function getActionToDeptDetails(actionTaken,feedDataId)
{
	var feedId=$("#"+feedDataId).val();
	var statusType=$("#"+actionTaken).val();
	if(statusType=='1')
	{
			// url : conP+"/view/Over2Cloud/feedback/beforeFeedViaOnlineTicketPcc.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&fbDataId="+feedId+"&feedTicketId="+feedTicketId+"&compType="+compType.split(" ").join("%20")+"&clientId="+clientId.split(" ").join("%20"),
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/feedback_Activity/beforeFeedViaOnlineTicketActivity.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedDataId="+feedId,
		    success : function(subdeptdata) {
	       $("#"+"mybuttondialog123").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else
	{
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/activity/activityNoAction.jsp",
		    success : function(subdeptdata) {
	       $("#"+"mybuttondialog123").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

</SCRIPT>

<STYLE type="text/css">

	td {
	padding: 5px;
	padding-left: 20px;
}

td.poor
{
	color: #FF0000;
}
td.average
{
	color: #FFCC00;
}
td.good
{
	color: #66FF66;
}

td.vgood
{
	color: #00CC00;
}

td.excellent
{
	color: #006600;
}
tr.color
{
	background-color: #E6E6E6;
}
</STYLE>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	 <div class="head">Open Feedback For MRD No</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:property value="%{patientPojo.clientId}" /></div> 
</div> 
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">  
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>		
<div>
<div style="width: 100%; center;align="center">
       <div  align="center">
	<s:form id="activityForm" name="activityForm" action="feedbackViaOnlineTicketActivity"  name="frm" enctype="multipart/form-data" theme="css_xhtml">
	       <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px; margin-bottom: 23px;">
                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
		<s:hidden name="feedDataId" id="feedDataId" value="%{patientPojo.id}" ></s:hidden>
		
		<div class="newColumn">
								<div class="leftColumn1">MRD No:</div>
								<div class="rightColumn1">
								<s:textfield value="%{patientPojo.clientId}" name="clientId" id="clientId" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	
              	<div class="newColumn">
								<div class="leftColumn1">Patient Name:</div>
								<div class="rightColumn1">
              	               <s:textfield value="%{patientPojo.clientName}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
		
		<div class="newColumn">
								<div class="leftColumn1">Mobile No:</div>
								<div class="rightColumn1">
              	                 <s:textfield value="%{patientPojo.mobNo}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	
              	<div class="newColumn">
								<div class="leftColumn1">Email Id:</div>
								<div class="rightColumn1">
              	               <s:textfield value="%{patientPojo.emailId}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
		
		<div class="newColumn">
								<div class="leftColumn1">Room No:</div>
								<div class="rightColumn1">
              	                <s:textfield value="%{patientPojo.centerCode}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	
              	<div class="newColumn">
								<div class="leftColumn1">Purpose of Visit:</div>
								<div class="rightColumn1">
              	                <s:textfield value="%{patientPojo.centreName}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
		
		<div class="newColumn">
								<div class="leftColumn1">Open Date:</div>
								<div class="rightColumn1">
              	                 <s:textfield value="%{patientPojo.openDate}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	
              	<div class="newColumn">
								<div class="leftColumn1">Open Time:</div>
								<div class="rightColumn1">
              	               <s:textfield value="%{patientPojo.openTime}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	<div class="newColumn">
								<div class="leftColumn1">Patient Type:</div>
								<div class="rightColumn1">
              	               <s:textfield value="%{patientPojo.compType}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
              	<div class="newColumn">
								<div class="leftColumn1"><span id="mandatoryFieldsActionTaken" class="validate1" style="display: none; ">actionName#Action Taken#D#a,</span>
								Take&nbsp;Action:</div>
								<div class="rightColumn1">
              	                <span class="needed"> </span>
					<s:select 
                        id="actionName"
                        name="actionName"
                        list="actionNameMap"
                        headerKey="-1"
                        headerValue="--Select Action Name--" 
                        cssClass="select"
					    cssStyle="width:82%"
                        onchange="getActionToDeptDetails('actionName','feedDataId');"
                        >
				</s:select>
              	</div>
              	</div>
     			<div id="mybuttondialog123">
    	 </div>
	<br>
	<div class="form_menubox">
   <center>	
             <div class="type-button">
                        <sj:submit 
                        targets="Result" 
                        clearForm="true"
                        value="Register" 
                        timeout="5000"
                        effect="highlight"
                        effectOptions="{color:'#222222'}"
                        effectDuration="5000"
                        button="true"
                        cssStyle="margin-left: -160px;"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
		                     value="Reset" 
		                     button="true"
		                     onclick="resetForm('Takeaction_Url');"
		                     cssStyle="margin-top: -43px;"
			            />
			            <sj:a 
		                     value="Back" 
		                     button="true"
		                     cssStyle="margin-top: -43px;margin-right: -137px;"
		                     onclick="viewActivityPage();"
			            >Back
			            </sj:a>
              </div> 
           </center>
           </div>
             <sj:div id="Result"  effect="fold">
                    <div id="foldeffect"></div>
           </sj:div>
           
           </s:form>
   </div>
   </div>
</div>
</div>
<br/>  <br/>
</body>
</html>