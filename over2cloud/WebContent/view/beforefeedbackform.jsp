<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<%-- <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
 --%>
<script type="text/javascript">

$.subscribe('valid111',function(event,data){
	alert("Hello");
		    var ot = $("#ot").val();
			var otp= $("#otp").val();
			if(ot!=otp)
			{
				alert("Please Enter Valid OTP.....");
				event.originalEvent.options.submit = false;
            	
			}	
		});


function resend()
{
	var traceid=$("#traceid").val();
	var pname=$("#pname").val();
	$("#result").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#openDialog").dialog({title: 'One Time Password(OTP) Generation Alert',width: 600,height: 200});
	$("#openDialog").dialog('open');
	$("#openDialog").html("Dear "+pname+", your OTP has been successfully regenerated, kindly check your SMS & Mail!");
	 setTimeout(function(){ window.location.reload(true); }, 5000);
	//alert("Your OTP has been successfully regenerated, kindly check your SMS & Mail!");
	
<%--
	$.ajax({
	    type : "post",

	url : "http://localhost:8091/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?traceid="+traceid,
	    success : function(data) {
		$("#result").html(data);
    	alert("Your OTP has been successfully regenerated, kindly check your SMS & Mail!");
				
			
       		
		},
	    error: function() {
            alert("error");
        }
	 });--%>
}

function reset(formId) 
{
	$('#'+formId).trigger("reset");
	
}

</script>
<!-- color: #8B8B70; -->
<style type="text/css">
	#label {
  font-weight: bold;
	}
  .label {  
  font-weight: bold;
}
h2 {  
  color: #F04E2F;
  margin-left: 44px;
  margin-top: -30px;
  align-content: center;
  position: absolute;
  border-radius: 4px;
  font-size: 25px;
  font-family: monospace;
}
h3
{
font-family:Arial;
}

#logoDiv{
 background: url("../medantaImages/logo.jpg");
  border: rgb(11, 11, 11);
  height: 59px;
  width: 235px;
  background-size: contain;
  }

.hrDiv{
border-bottom-style: groove;
  border-bottom-color: #C6C6C6;
}  
  #innerDiv1{
  border-bottom-style: hidden;
  border-bottom-color: black;
  }
  
  #detailPerson{  border-bottom-style: groove;
  border-bottom-color: #C6C6C6;
 } 
 
.checkboxLabel{
		color:black; font-style:normal; 
}

div#bottomLine{
 border-bottom-style: groove;
  border-bottom-color: rgba(198, 198, 198, 0.56);
  width: 1019px;
  border-width: thin;
}
div#innerDiv2{
  outline-color: grey;
  border-radius: 13px;
  background-color: #FFFFFF;
}
#questions
{
	margin-left: 200px;
}
div#compDetail{
 border: rgb(11, 11, 11);
  height: 62px;
  width: 181px;
  background-size: contain;
  float: right;
  margin-top: -49px;
  text-indent: -56px;
  text-decoration: blink;
  animation-fill-mode: both;
  font-style: italic;
  text-transform: capitalize;
  font-size: 18px;
    animation-direction: reverse;	
}

</style>
 
<style type="text/css">
  body{
  background-size: cover;
  background-color: #F8F8F8;
  }
  
 
</style>

</head>
<body style=" #ECECEC width: 1295px;">
	<div id="datapart">
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left:95px;width: 1095px;height: 637px;">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline no. 
+91 124 <span id="contactNo" style="  color: red;">4141414 </span></div>
					</div>
					<br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="   margin-top: 476px;">
		<h2 style="margin-top: -397px;">Wellness Questionnaire</h2>
		<br>
		<h3 style=" margin-top: -351px; margin-left: 46px;">Dear <s:property value="%{pname}"/>, kindly fill your wellness questionnaire to serve you better. <br> To proceed please enter the One Time Password (OTP) that has been sent to your mobile no: <s:property value="%{mobno}"/>. </h3>  
	</div>
	<div id="formDiv"  style="margin-top: -449px; overflow-y: auto;">
	<s:form id="form1" action="/view/Over2Cloud/patientActivity/patientFeedbackForm.action" method="post" enctype="multipart/form-data">
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<s:hidden id="otp1" name="otp1" value="%{ot}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"></s:hidden>
		<s:hidden id="mobile" name="mobile" value="%{mobile}"></s:hidden>
		<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		
	<div id="detailPerson">
			<table align="left" style="margin-left: 44px;margin-top: 146px;width: 61%;">
				<tr>
				<td><s:textfield 
				id="otp" 
				name="otp" 
				cssClass="textfield"
				placeholder="OTP" style="width: 221px;height: 32px;"
				/>
				</td>
				</tr>
			</table>
		</div>
			<!-- Buttons -->
		<br>
		   <div class="clear"></div>
   <div class="fields" align="center">
   <div class="type-button">
   <div id="bt" style="display: block;">
         <sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="  Proceed  " 
	           effect="highlight"
	           effectOptions="{ color : '#E00000'}"
	           effectDuration="5000"
	           button="true"
	           cssStyle="margin-left: -774px; margin-top: 5px; color: white; font-size: 134%;   background:#E00000;width: 225px;height: 37px;"
	           indicator="indicator1"
			   />
	</div>
	
   </div>
   
   </div>
		 <!-- <div style="text-align: center;">
			<button onclick="saveForm('form1');">Submit</button>
			<button onclick="reset('form1');">Reset</button>
			<button type="reset">Reset</reset>
			<button onclick="closePage();">Close</button>
		</div> --> 
						
			</s:form>
			
		<div id="test" style=" margin-left: 307px; margin-top: -25px; font-family: Arial;">	If you don't receive the OTP click to <a onclick="resend()" href="#"><font color="#E00000">Resend OTP</font></a>. </div>
			
			</div>
	<div id="complTarget"></div>	
		</div>	
</div>
</div>
 <sj:dialog 
	id="showAgreement"
	buttons="{'Agree':function() { closedownload(); },'Not Agree':function() { closedownload(); } }"
	showEffect="slide" 
	hideEffect="explode" 
	autoOpen="false" 
	modal="true"
	title="View History" 
	openTopics="openEffectDialog"
	closeTopics="closeEffectDialog"
	width="460"
	height="800"
	>
<div id="showAgreementDataDiv"></div>
	
	</sj:dialog>
	
<sj:dialog
          id="openDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Upload"
          modal="true"
          width="1200"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="view"></div>
</sj:dialog>
</body>

</html>