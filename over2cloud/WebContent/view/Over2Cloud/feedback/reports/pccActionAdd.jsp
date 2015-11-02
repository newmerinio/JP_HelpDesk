<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html>
<head>
<script type="text/javascript">
$.subscribe('checkActionStatus',function(event,data)
		{
		//	alert("Hello");
		}
		);

$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });


function getFeedbackStatus(actionTaken,feedDataId,feedTicketId1)
{
	var feedId=$("#"+feedDataId).val();
	var feedTicketId=$("#"+feedTicketId1).val();
	var compType=$("#compType").val();
	var clientId=$("#clientId").val();
	var statusType=$("#"+actionTaken).val();
	var visitType=$("#visitType").val();
	//alert(visitType);
	var clientName="";
	var mobNo="";
	var emailID="";
	var comments="";
	if(visitType=='Visitor' || compType=='By Hand' || compType=='Email' || compType=='Verbal')
	{
		clientName=$("#clientName1").val().trim();
		mobNo=$("#mobNo1").val().trim();
		emailID=$("#emailId1").val().trim();
		comments=$("#comments").val().trim();
		if(clientName=='' || clientName==null)
		{
			$("#errZone").html("Please Enter Name.");
			setTimeout(function(){ $("#err").fadeIn(); }, 10);
            setTimeout(function(){ $("#err").fadeOut(); }, 4000);
            return "0";
		}	
	}
	//alert(statusType);
	if(statusType=='1')
	{
		var conP = "<%=request.getContextPath()%>";

		$("#mybuttondialog123").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/beforeFeedViaOnlineTicketPcc.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&fbDataId="+feedId+"&feedTicketId="+feedTicketId+"&compType="+compType.split(" ").join("%20")+"&clientId="+clientId.split(" ").join("%20")+"&visitType="+visitType.split(" ").join("%20")+"&clientName="+clientName.split(" ").join("%20")+"&mobNo="+mobNo.split(" ").join("%20")+"&emailID="+emailID.split(" ").join("%20")+"&comments="+comments.split(" ").join("%20"),
		    success : function(subdeptdata) {
	       $("#"+"mybuttondialog123").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		 $("#mybuttondialog123").dialog('open');
	}else
	{
			document.getElementById("showcommentsId").style.display="block";
	}
}


$.subscribe('validate', function(event,data)
        {

   
	var mystring = $(".validate1").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;

  // alert(">>>>>>>>>>>>>>>>>>>>>>>"+mystring);

    
    for(var i=0; i<fieldtype.length; i++)
    {
       
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";

       // alert("Column Type>>>"+colType);
        if(fieldsvalues!= "" )
        {
            if(colType=="D")
            {
            	if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            	{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
            	}
            }
            else if(colType =="T" || colType =="Text")
            {
            	if(validationType=="n")
            	{
		            var numeric= /^[0-9]+$/;
		            if(!(numeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }   
            	}
            	else if(validationType=="an")
            	{
		            var allphanumeric = /^[A-Za-z0-9 ]{3,50}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }
		           /* else if($("#status").val()==false);
		            {
		            	errZone.innerHTML="<div class='user_form_inputError2'>Enter Serial No Allready Exist</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#serialno").focus();
			            $("#serialno").css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
		            }*/
            	}
            	else if(validationType=="a")
            	{
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;    
		            }
            	}
            	else if(validationType=="m")
            	{
		            if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val()))
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
            	}
            	else if(validationType =="e")
            	{
            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
            		{
            		}
            		else
            		{
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            event.originalEvent.options.submit = false;
			            break;
            		}
            	}
            	else if(validationType =="w")
            	{
                }
            }
            else if(colType=="TextArea")
            {
	            if(validationType=="an")
	            {
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }
	            }
	            else if(validationType=="mg")
	            {
	            }   
            }
            else if(colType=="Time")
            {
            	if($("#"+fieldsvalues).val()=="")
            	{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
            	}   
            }
            else if(colType=="Date")
            {
            	if($("#"+fieldsvalues).val()=="")
            	{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
            	}
            }
            
        }
    }       
        });


</Script>
<script type="text/javascript">
	function allPatientDetails(clientId)
	{
		var patientId = document.getElementById(clientId).value;
		var compType=$("#compType").val();
		var visitType=$("#visitType").val();
		if(compType!="-1")
		{
				 $.getJSON("view/Over2Cloud/feedback/getPccDataForIdInJson.action?clientId="+patientId+"&visitType="+visitType,
				 function(data){
					 if(visitType=='Patient' && (compType=='Admin Round' || compType=='Ward Rounds'))
					 {	 
				 			if(data.patObj1.patientExists=="Yes")
				 			{
				 				if(data.patObj1.patientName == null){$("#clientName").html("");}
			    	               else {$("#clientName").html(data.patObj1.patientName);}
			    	               
			    	               if(data.patObj1.patientMobileNo == null){$("#mobNo").html("");}
			    	               else {$("#mobNo").html(data.patObj1.patientMobileNo);}
			    	               
			    	               if(data.patObj1.doctorName == null){$("#serviceBy").html("");}
			    	               else {$("#serviceBy").html(data.patObj1.doctorName);}
			    	               
			    	               if(data.patObj1.purOfVisit == null){$("#centreName").html("");}
			    	               else {$("#centreName").html(data.patObj1.purOfVisit);}
			    	             
			    	               if(data.patObj1.patientType == null){$("#compTypeid").html("");}
			    	               else {$("#compTypeid").html(data.patObj1.patientType); }
			    	               
			    	               if(data.patObj1.roomNo == null){$("#centerCode").html("");}
			    	               else {$("#centerCode").html(data.patObj1.roomNo); }
			    	             
			    	             if(data.patObj1.feedTicketNo == null){$("#feedTicketId").val("");}
			    	               else {$("#feedTicketId").val(data.patObj1.feedTicketNo); }
			    	                
			    	              if(data.patObj1.id == null){$("#feedDataId").val("");}
			    	              else {$("#feedDataId").val(data.patObj1.id); }
			    	              
			    	              document.getElementById("patientDetailsDiv").style.display = 'block';
			    	              document.getElementById("visitorDetailsDiv").style.display = 'none';
			    	              document.getElementById("feedbackhisforMRDNo").style.display = 'block';
			    	              patientDetailsInGrid(patientId,compType);
				 			}
				 			else
				 			{
				 				$("#errZone").html("Please Enter Valid Details.");
				 				setTimeout(function(){ $("#err").fadeIn(); }, 10);
					            setTimeout(function(){ $("#err").fadeOut(); }, 4000);
				 			}
					 	}
					    else
					    {
					    	document.getElementById("visitorDetailsDiv").style.display = 'block';
					    	document.getElementById("actionDivId").style.display="block";
					    	document.getElementById("patientDetailsDiv").style.display = 'none';
					    	document.getElementById("feedbackhisforMRDNo").style.display = 'none';
					    }	
	    	       });
		}
		else
		{
			$("#errZone").html("Please Select Feedback Source from DropDown");
			setTimeout(function(){ $("#err").fadeIn(); }, 10);
            setTimeout(function(){ $("#err").fadeOut(); }, 4000);
		}
	}
	function patientDetailsInGrid(patientId, compType)
	{
		var mrdNo = patientId;
	    $.ajax({
	        type : "post",
	        url : "view/Over2Cloud/feedback/report/beforePatAllTicketDetails.action?clientId="+mrdNo,
	        success : function(subdeptdata) {
	        $("#feedbackhisforMRDNo").html(subdeptdata);
	        document.getElementById("actionDivId").style.display="block";
	      
	    },
	       error: function() {
	            alert("error");
	        }
	     });
	}
	
	function viewActivityPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
		    success : function(subdeptdata) 
		    {
		    	$("#"+"data_part").html(subdeptdata);
		    },
		   error: function() 
		   {
			   alert("error");
		   }
		 });
	}
	
	function viewReport()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/report/viewReportHeader.action",
		    success : function(subdeptdata) 
		    {
		    	$("#"+"data_part").html(subdeptdata);
		    },
		   error: function() 
		   {
			   alert("error");
		   }
		 });
	}
	
	function check(val1)
	{
		$("#clientId").attr("onblur","");
		$("#compType").attr("onchange","");
		var feedBy=$("#visitType").val();
		if(feedBy=='Patient' && (val1=='Ward Rounds' || val1=='Admin Round'))
		{
			$("#clientId").css('display','block').css('margin-top','-28px').css('margin-left','271px');
			$("#clientId").attr("onblur","allPatientDetails('clientId')");
		}
		else
		{
			document.getElementById("clientId").style.display="none";
			allPatientDetails('clientId');
		}
	}
	
</script>

<STYLE type="text/css">

	td {
	padding: 3px;
	padding-left: 20px;
}

</STYLE>

</head>
<body>
<sj:dialog 
    	id="viewpaperratingdata" 
    	autoOpen="false" 
    	modal="true" 
    	width="800"
		height="600"
    	title=" Paper Feedback Rating"
    	resizable="false"
    >
</sj:dialog>
<sj:dialog 
    	id="mybuttondialog123" 
    	autoOpen="false" 
    	modal="true" 
    	width="1000"
		height="400"
    	title=" Open Ticket For Department"
    	resizable="false"
    >
</sj:dialog>

<div class="list-icon">
	 <div class="head">Feedback</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Add</div>
	 <div align="right"><sj:a  buttonIcon="ui-icon-person" button="true" onclick="viewActivityPage();" title="Activity Board" cssClass="button" cssStyle = "width: 140px;margin-right: 3px;margin-top:3px;height:20px">Activity Board</sj:a></div>
</div>
<div class="clear"></div>
    <div class="border" style="height: 50%" align="center">
     <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<s:form id="Takeaction_Url" name="Takeaction_Url" name="frm" enctype="multipart/form-data" theme="css_xhtml">
	 <input type="hidden" name="feedDataId" id="feedDataId">
     <input type="hidden" name="feedTicketId" id="feedTicketId">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    <td class="pL10"> 
					    
					     <s:select 
	                      id="visitType" 
	                      name="visitType" 
	                      list="{'Patient','Visitor'}"
	                      headerKey="-1"
	                      headerValue="Feedback By"
	                      cssClass="button"
	                      theme="simple"
	                      cssStyle = "height:28px;margin-top:-2px;;margin-left: 3px"
	                      
                     	 />
                     	 
					    <s:select 
                        id="compType"
                        name="compType"
                        list="%{feedbackActionStatus}" 
                        headerKey="-1"
                        headerValue="Feedback Source" 
                        cssClass="button"
                        cssStyle = "height:28px;margin-top:-2px;;margin-left: 3px"
                        theme="simple"
                        onchange="check(this.value)"
                        >
				</s:select>
				<s:textfield theme="simple" id="clientId" name="clientId" placeholder="Enter MRD No" cssClass="button" cssStyle = "height:15px;margin-top:-26px;margin-left: 3px" onblur="allPatientDetails('clientId');" theme="simple"/>
					     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <s:if test="%{visitType=='PCC'}">
				  		<sj:a button="true"onclick="viewReport();"  title="View Report" cssClass="button"cssStyle="width: 80px;margin-right: 3px;margin-top: 4px;height:25px">Report</sj:a>
	      		  </s:if>
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	       <div id="err" style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px; margin-bottom: 23px;">
                  <div id="errZone" style=" margin-left: 7px;" align="center"></div>        
           </div>
         
	<div id="patientDetailsDiv" style="display:none">
		<table border='0' align="left"   width="100%">
			<tr bgcolor="#E6E6E6">
				<td ><b>Patient&nbsp;Name:</b></td>
				<td style="" id="clientName"></td>
				<td><b>Mobile&nbsp;No:</b></td>
				<td id="mobNo"></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
		    </tr>
		    <tr>
				<td><b>Doctor&nbsp;Name:</b></td>
				<td id="serviceBy"></td>
				<td ><b>Specialty:</b></td>
				<td id="centreName"></td>
				<td ><b>Patient&nbsp;Type:</b></td>
				<td id="compTypeid"></td>
				<td><b>Room No/&nbsp;Bed No.:</b></td>
				<td id="centerCode"></td>
			
		    </tr>
		   
		</table>
	</div>
	<div id="visitorDetailsDiv" style="display:none">
		<table width="100%" align="center" border="0">
			<tr bgcolor="#E6E6E6">
				<td align="left" ><b>Name:</b><font size="+2" color="#FF1111">|</font></td><td align="center" ><s:textfield theme="simple"  id="clientName1" name="clientName"  cssClass="textField"/></td>
				<td align="left" ><b>Mobile&nbsp;No:</b><td align="center" ><s:textfield theme="simple"  id="mobNo1" name="mobNo"  cssClass="textField"/></td>
				<td align="left" ><b>Email&nbsp;ID:</b></td><td align="center" ><s:textfield theme="simple"  id="emailId1" name="emailId"  cssClass="textField"/></td> 
				<td align="left" ><b>Comments:</b></td><td align="center" ><s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea></td>
			</tr>
		</table>
	</div>
  <br>
  <br/>
	<div id="feedbackhisforMRDNo" style="margin-top: 66px;margin-bottom: 31px;"></div>
	
	<div class="newColumn" id="actionDivId" style="display: none">
		<div class="leftColumn1">Take&nbsp;Action:</div>
			<div class="rightColumn1">
				<span id="mandatoryFieldsActionTaken" class="validate1" style="display: none; ">actionName#Action Taken#D#a,</span>
				<s:select 
                        id="actionName"
                        name="actionName"
                        list="actionNameMap"
                        headerKey="-1"
                        headerValue="--Select Action Name--" 
                        cssClass="select"
					    cssStyle="width:58%"
                        onchange="getFeedbackStatus('actionName','feedDataId','feedTicketId')"
                        >
				</s:select>
			</div>
	</div>
	<div id="showcommentsId" style="display: none;">
	<div class="newColumn" >
		<div class="leftColumn1">Comments:</div>
			<div class="rightColumn1">
			    <s:if test="mandatory">	<span class="needed"></span>
			    </s:if>
				<span id="mandatoryFieldsActionTaken" class="validate1" style="display: none; ">actionName#Action Taken#D#a,</span>
				<s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea>
			</div>
	</div>
	</div>
	<div class="clear"></div><br>
        <%--  <center>	
             <div class="type-button" align="center">
                          <sj:submit 
                          targets="Result" 
                          clearForm="true"
                          value="Register" 
                          timeout="5000"
                          effect="highlight"
                          effectOptions="{color:'#222222'}"
                          effectDuration="5000"
                          button="true"
                          onCompleteTopics="complete"
                        	cssStyle="margin-top: -28px"
                          />
                             <div id=reset style="margin-top: -28px; margin-left: 165px;"> 
                          <sj:submit 
		                     value="Reset" 
		                     button="true"
		                     onclick="resetForm('Takeaction_Url');"
		                    cssStyle="margin-left: -12px;"
			              />
			              <sj:a 
		                     value="Back" 
		                     button="true"
		                     onclick="viewActivityPage();"
		                    cssStyle="margin-left: 119px;margin-top: -28px;"
			              >Back</sj:a>
             </div> </div>
        </center> --%> 
                 <sj:div id="Result"  effect="fold">
                    <div id="foldeffect"></div>
                 </sj:div>
 
    </s:form>
  </div>
  
</body>
</html>