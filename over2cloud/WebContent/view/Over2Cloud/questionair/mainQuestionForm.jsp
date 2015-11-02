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
				
				<td><s:textfield id="name142554"  cssClass="hrDiv"
							placeholder="Enter Name" label="Name" value="%{pname}"/>
				</td><td>	<s:textfield id="age42543"   cssClass="hrDiv" placeholder="Enter Age"
							label="Age"  value="%{page}" >
						</s:textfield>
					</td>
					</tr><tr><td>
				<s:radio label="Gender" id="gender4242"
							list="{'Male','Female'}"  cssClass="hrDiv" onchange="showDiv(this.value,'showf')"  value="%{psex}"/>
					</td><td><s:textfield  cssClass="hrDiv"label="Occuption" id="occupation4242"
							placeholder="Enter Occuption" value="%{poccupation}" ></s:textfield>
							</td></tr>
							
			</table>
		</div>
		<!-- questions -->
		  <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		<br>
		<s:iterator value="questionList" status="status" id="test">
			<s:if test="#test.mandatory">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{#test.questionId}"/>#Select Value#T#sc,</span>
			</s:if>
		
		    <s:hidden id="questionId#status.count" name="questionId" value="%{#test.questionId}"/>
		    <font face="arial" size="2">  <b><s:property value="#status.count"/>.</b> <s:if test="#test.mandatory"><font color="red">*</font></s:if><b><s:property value="#test.questions"/></b></font>
		  <br>
		 <font face="arial" size="2">   <b> Answer  <s:property value="#status.count"/>.</b></font>
		     <s:if test="#test.answers=='Text Only'">
		    	 	<s:textfield name="%{#test.questionId}"  id="%{#test.questionId}" placeholder="Enter Data" value="" />
		     </s:if>
		     <s:elseif test="#test.answers=='Radio Only'">
					<s:radio name="%{#test.questionId}" id="%{#test.questionId}" list="#test.optionsList"  />
		    </s:elseif>
		     <s:elseif test="#test.answers=='Radio & Text'">
					<s:radio name="%{#test.questionId}" id="%{#test.questionId}" list="#test.optionsList" /><br>
					<s:textfield name="%{#test.questionId}"  id="%{#test.questionId}1" placeholder="Enter Data" />
		    </s:elseif>
		     <s:elseif test="#test.answers=='Checkbox Only'">
					<s:checkboxlist name="%{#test.questionId}" id="%{#test.questionId}"  list="#test.optionsList" />
		    </s:elseif>
		     <s:elseif test="#test.answers=='Checkbox & Text'">
					<s:checkboxlist name="%{#test.questionId}" id="%{#test.questionId}" list="#test.optionsList" /><br>
					<s:textfield name="%{#test.questionId}" id="%{#test.questionId}2" placeholder="Enter Data" />
		    </s:elseif>
		     <br>
		<%--   <s:if test="#test.upload"><s:file name="upload" id="upload#status.count"  /></s:if> --%>
		<br>
		    <s:if test="#test.subquestion">
		       <s:iterator value="#test.subQuestion" id="test1" status="stat">
		           <s:hidden id="subquestionId#stat.count" name="subquestionId" value="%{#test1.subquestionId}"/>
		    	   <font face="arial" size="2"> <s:property value="#status.count"/>. <s:property value="#stat.count"/> <s:if test="#test1.submandatory"><font color="red">*</font></s:if><s:property value="#test1.subquestions"/></font>
					  <br>
					 <font face="arial" size="2">  Answer  <s:property value="#status.count"/>.<s:property value="#stat.count"/> </font>
					     <s:if test="#test1.subanswers=='Text Only'">
					    	 	<s:textfield name="%{#test.questionId}#%{#test1.subquestionId}"  id="answers1Subdcvcv#stat.count" placeholder="Enter Data" />
					     </s:if>
					     <s:elseif test="#test1.subanswers=='Radio Only'">
								<s:radio name="%{#test.questionId}#%{#test1.subquestionId}" id="answersRSubOnlydcdvd#stat.count" list="#test1.suboptionsList"  />
					    </s:elseif>
					     <s:elseif test="#test1.subanswers=='Radio & Text'">
								<s:radio name="%{#test.questionId}#%{#test1.subquestionId}" id="answersRadioSubvdvdv#stat.count" list="#test1.suboptionsList" /><br>
								<s:textfield name="%{#test.questionId}#%{#test1.subquestionId}"  id="answersSubText1#stat.count" placeholder="Enter Data" />
					    </s:elseif>
					     <s:elseif test="#test1.subanswers=='Checkbox Only'">
								<s:checkboxlist name="%{#test.questionId}#%{#test1.subquestionId}" id="answersSubCheckboxdvvd#stat.count"  list="#test1.suboptionsList" />
					    </s:elseif>
					     <s:elseif test="#test1.subanswers=='Checkbox & Text'">
								<s:checkboxlist name="%{#test.questionId}#%{#test1.subquestionId}" id="answersSubCTextdvd#stat.count" list="#test1.suboptionsList" /><br>
								<s:textfield name="%{#test.questionId}#%{#test1.subquestionId}" id="answers1Subtext#stat.count" placeholder="Enter Data" />
					    </s:elseif>
					     <br>
					 <%--  <s:if test="#test1.subupload"><s:file name="subupload" id="subupload#stat.count"  /></s:if> --%>
					  <br>
		    </s:iterator>
		    </s:if>
		</s:iterator>
			<s:file name='report' label="Upload Recent Reports (if available)" title="Uploads(e.g. .jpeg,.word,.excel)"></s:file>
	   </div>
		   <div class="clear"></div>
		   <br>  <br>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
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
	            <sj:a cssStyle="margin-left: 3px; margin-top: -47px; color: grey; font-size: 82%;   background: rgba(139, 139, 112, 0.22);" button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	            <%-- <sj:a cssStyle="margin-left: 0px; margin-top: -58px; color: grey;   background: rgb(219, 217, 217);" button="true" href="#" onclick='closePage();'>Close</sj:a> --%>
    <br>  <br>
   </div>
   </div>

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
		
	
			</s:form>
			</div>
		
		</div>	
</div>
</body>

</html>