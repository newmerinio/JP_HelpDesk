<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10*40);
			 setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); window.location.href = "http://www.medanta.org/";}, 4000);
			 $('#completionResult').dialog('open');
			 reset('form1');
			 
		  });
		 
		 function cancelButton()
		 {
			 $('#completionResult').dialog('close');
			 
		 }

function closePage(){
	if ((userBrowser.browser == "Explorer" && (userBrowser.version == "8" || userBrowser.version == "7"))) {
        window.open('', '_self', '');
        window.close();
    } else if ((userBrowser.browser == "Explorer" && userBrowser.version == "6")) {
        window.opener = null;
        window.close();
    } else {
        window.opener = '';
        window.close(); // attempt to close window first, show user warning message if fails
        alert("To avoid data corruption/loss. Please close this window immedietly.");
    }
}		 

function saveForm(id){
	$('#'+id).submit();
}

function reset(formId) 
{
	$('#'+formId).trigger("reset");
	var val='';
	var id='';
	showDiv(val,'showf');
	showDiv2(val,'showHost');
	showDiv3(val,'showSmok');
	showDiv4(val,'showalco');
	showDiv5(val,'showalle');
}

function showDiv(val,id){
	if(val==='Female'&& 'showf'===id){
		$("#"+id).show(200);
	}else{
		$("#"+id).hide(200);
	}
}
 function showDiv2(val,id){
	 if(val==='Yes' && 'showHost'===id){
			$("#"+id).show(200);
		}else{
			$("#"+id).hide(200);
		}
 }
 
 function showDiv3(val,id){
	 if(val==='Yes' && 'showSmok'===id){
			$("#"+id).show(200);
		}else{
			$("#"+id).hide(200);
		}
 }
 
 function showDiv4(val,id){
	 if(val==='Yes' && 'showalco'===id){
			$("#"+id).show(200);
		}else{
			$("#"+id).hide(200);
		}
 }
 function showDiv5(val,id){
	 if(val==='Yes' && 'showalle'===id){
			$("#"+id).show(200);
		}else{
			$("#"+id).hide(200);
		}
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
#logoDiv{
background: url("../over2cloud/medantaImages/logo.jpg");
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
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 128px;width: 1095px;height: 637px;">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline no. 
+91 124 <span id="contactNo" style="  color: red;">4141414 </span></div>
					</div>
					<br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="   margin-top: 476px;">
		<h2>Feedback Form Details</h2>
	</div>
	<div id="formDiv"  style="margin-top: -449px; overflow-y: auto;">
	<s:form id="form1" action="/view/Over2Cloud/patientActivity/submitFeedback.action" method="post" enctype="multipart/form-data">
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<div id="detailPerson" >
			<table align="left" style="margin-left: 44px;">
				<tr>
				<td><s:textfield id="name" name="name" cssClass="hrDiv"
							placeholder="Enter Name" label="Name" value="%{pname}" readonly="true"></s:textfield>
				</td><td>	<s:textfield id="age" name="age"  cssClass="hrDiv" placeholder="Enter Age"
							label="Age"  value="%{page}" readonly="true">
						</s:textfield>
					</td><tr><td>
				<s:radio label="Gender" name="gender"
							list="{'Male','Female'}"  cssClass="hrDiv" onchange="showDiv(this.value,'showf')" />
					</td><td><s:textfield name="occupation"  cssClass="hrDiv"label="Occuption"
							placeholder="Enter Occuption" value="%{poccupation}"></s:textfield>
							</td></tr>
			</table>
		</div>
		<!-- questions -->
		
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
			<br><%-- <s:checkboxlist
				label="Q1. Have you suffered from any of the
					following diseases?"
					id="q1"
				name="q1" 
				list="#{'Hypertension':'Hypertension','Diabetes':'Diabetes','Asthma':'Asthma','Thyroid':'Thyroid','Seizure/Fits':'Seizure/Fits','Cardiac problems':'Cardiac problems','Cancer':'Cancer'}" /> --%>
				<label id="label">1. Have you suffered from any of the
					following diseases? : </label>
					<input type="checkbox" name="q1" value="Hypertension" />Hypertension
					<input type="checkbox" name="q1" value="Diabetes" />Diabetes
					<input type="checkbox" name="q1" value="Asthma" />Asthma
					<input type="checkbox" name="q1" value="Thyroid" />Thyroid
					<input type="checkbox" name="q1" value="Seizure/Fits" />Seizure/Fits
					<input type="checkbox" name="q1" value="Cardiac Problems" />Cardiac Problems
				<div id="bottomLine"></div>
				<br>
			
			<s:radio cssClass="hrDiv" label=" 2. Are you allergic to any drug or substance? "
				name="q2" list="{'Yes','No','Donot know'}" onchange="showDiv5(this.value,'showalle')"/>
				<br>
			<div id="showalle" style=" display: none"><br>
			<s:textfield name="q3" label="Specify the drug/substance. "
							placeholder="Enter Data"></s:textfield><br></div><div id="bottomLine"></div>
							<br>
							<s:radio cssClass="hrDiv" label="3. Do you consume alcohol? "
				name="q4" list="{'Yes','No'}" onchange="showDiv4(this.value,'showalco')"/><br>
				<div id="showalco" style=" display: none"><br>
				<s:radio label="How often? "  cssClass="hrDiv"  
				name="q5" list="{'Once a day','Twice a day','Once a week','Once a month','Others'}" /><br></div><div id="bottomLine"></div><br>
							
		<s:radio label=" 4. Do you smoke? " cssClass="hrDiv"
				name="q6" list="{'Yes','No'}" onchange="showDiv3(this.value,'showSmok');"/><br>
			<div id="showSmok" style=" display: none"><br><s:radio label="How many cigarettes per day? "
				name="q7" list="{'1','2','1 pack','More than a pack'}" /><br></div><div id="bottomLine"></div><br>
			
		<s:radio label=" 5. Do you exercise regularly? " cssClass="hrDiv"
				name="q9" list="{'Yes','No','Sometimes'}" /><div id="bottomLine"></div><br>
		
			<%-- <s:checkboxlist
				label="Q6. Do you experience shortness of breath during ?"
				name="q10"
				list="{'Running','Exercise','Sleeping','At rest'}" /> --%>
				<label id="label" >6. Do you experience shortness of breath during ? : </label>
					<input type="checkbox" name="q10" value="At Rest" />At Rest
					<input type="checkbox" name="q10" value="Sleeping" />Sleeping
					<input type="checkbox" name="q10" value="Exercise" />Exercise
					<input type="checkbox" name="q10" value="Running" />Running
				<div id="bottomLine"></div><br>
			
		<%-- <s:checkboxlist
				label="Q7.	Has any of your family member suffered ?"
				name="q11"
				list="{'Hypertension','Diabetes','Asthma','Thyroid','Seizure/fits','Cancer','Cardiac problems'}" /> --%>
				<label id="label">7.  Has any of your family member suffered ? : </label>
				<input type="checkbox" name="q11" value="Hypertension" />Hypertension
					<input type="checkbox" name="q11" value="Diabetes" />Diabetes
					<input type="checkbox" name="q11" value="Asthma" />Asthma
					<input type="checkbox" name="q11" value="Thyroid" />Thyroid
					<input type="checkbox" name="q11" value="Seizure/Fits" />Seizure/Fits
					<input type="checkbox" name="q11" value="Cardiac Problems" />Cardiac Problems
				<div id="bottomLine"></div><br>
		
		<s:radio label=" 8. Have you been hospitalized in recent years ?" cssClass="hrDiv"
				name="q12" list="{'Yes','No'}" onchange="showDiv2(this.value,'showHost')" /><br>
			<div id="showHost" style=" display: none"><br>
			<s:textfield name="q13" cssClass="hrDiv" label="Please give details "
							placeholder="Enter Data"></s:textfield><br></div><div id="bottomLine"></div><br>
<!-- female gender -->
			<div id="showf" style=" display: none">
			<s:radio  cssClass="hrDiv" label=" 9. Is your menstrual cycle regular? "
				name="q8" list="{'Yes','No'}" /><br><div id="bottomLine"></div></div><br>
			
			
		<!--if q10 yes  -->
		<s:file name='report' label="Upload Recent Reports (if available)" title="Uploads(e.g. .jpeg,.word,.excel)"></s:file>
		</div>

		<!-- Buttons -->
		<br>
		   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateAction"
	           onCompleteTopics="makeEffect"
	           cssStyle="margin-left: -160px; margin-top: -26px; color: grey; font-size: 82%;   background: rgba(139, 139, 112, 0.22);"
	           indicator="indicator1"
			   />
	            <sj:a cssStyle="margin-left: 3px; margin-top: -35px; color: grey; font-size: 82%;   background: rgba(139, 139, 112, 0.22);" button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	            <%-- <sj:a cssStyle="margin-left: 0px; margin-top: -58px; color: grey;   background: rgb(219, 217, 217);" button="true" href="#" onclick='closePage();'>Close</sj:a> --%>
   </div>
   </div>
   </li>
   </ul>
   </div>
		<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="435"
          height="215"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="complTarget"></div>
</sj:dialog>
		
		 <!-- <div style="text-align: center;">
			<button onclick="saveForm('form1');">Submit</button>
			<button onclick="reset('form1');">Reset</button>
			<button type="reset">Reset</reset>
			<button onclick="closePage();">Close</button>
		</div> --> 
						
			</s:form>
			</div>
		
		</div>	
</div>
</body>

</html>