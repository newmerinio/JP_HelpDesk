<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

function feedbackReport(val,data)
{
	
	
	//$("#"+data).text(val);
	$("#"+data).val(val);
	
	
	/*
	alert(val);
	
	 

	*/
}
</script>
<script type="text/javascript">
function submitVal()
{
	var cleanliness = $("#one").text();
	var staffBehaviour = $("#two").text();
	var costingFactor = $("#three").text();
	var treatment = $("#four").text();
	var ambience = $("#five").text();
	var billingServices = $("#six").text();
	var resultSatis = $("#seven").text();
	var overAll = $("#eight").text();
	var mobileNo=$("#mobileNo").val();
	var name=$('#name').val();
	var emailId=$("#emailId").val();
   // alert(name);

	$.ajax( {
		type :"post",
		//url :"/cloudapp/view/Over2Cloud/feedback/addFeedbackData.action?cleanliness="+cleanliness+"&staffBehaviour="+staffBehaviour+"&costingFactor="+costingFactor+"&treatment="+treatment+"&ambience="+ambience+"&billingServices="+billingServices+"&mobileNo="+mobileNo+"&resultSatis="+resultSatis+"&overAll="+overAll+"&name="+name+"&emailId="+emailId, 
		success : function(subdeptdata) {
		},
		error : function() {
		}
	});
	
}	


</script>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<div class="page_title"><h1>Feedback Form >> Registration</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Feedback Form >> Add" id="oneId">  
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/feedback" action="addFeedbackData" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
<div class="form_inner" id="form_reg">
<div class="page_form">
				
				<div class="form_menubox">
				<s:if test="ddTrue">
                 <div class="user_form_text" ><s:property value="%{cleanliness}"/>:</div>
                  <div class="user_form_input" style="width: 400px"><input type="button" style="height: 30px;width: 50px" value="Poor" onclick="this.disabled=true;feedbackReport(this.value,'one')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 70px" value="Average" onclick="this.disabled=true;feedbackReport(this.value,'one')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 50px" value="Good" onclick="this.disabled=true;feedbackReport(this.value,'one')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 75px" value="Very Good" onclick="this.disabled=true;feedbackReport(this.value,'one')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Excellent" style="height: 30px;width: 70px" onclick="this.disabled=true;feedbackReport(this.value,'one')"><input type="text" style="display:none;" name="cleanliness" id="one"></input>
                  </div>
                  </s:if>
                  </div>
                  
                  <s:if test="stafftrue">
                 <div class="user_form_text"><s:property value="%{billingServices}"/>:</div>
                 <div class="user_form_input" style="width: 400px"><input type="button" style="height: 30px;width: 50px" value="Poor" onclick="this.disabled=true;feedbackReport(this.value,'two')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 70px" value="Average" onclick="this.disabled=true;feedbackReport(this.value,'two')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 50px" value="Good" onclick="this.disabled=true;feedbackReport(this.value,'two')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 75px" value="Very Good" onclick="this.disabled=true;feedbackReport(this.value,'two')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Excellent" style="height: 30px;width: 70px" onclick="this.disabled=true;feedbackReport(this.value,'two')"><input type="text" style="display:none;" name="staffBehaviour" id="two"></input>
                  </div>
                  </s:if>
                  
                  
                 <div class="form_menubox">
				<s:if test="costingTrue">
                 <div class="user_form_text"><s:property value="%{overall}"/>:</div>
                  <div class="user_form_input" style="width: 400px"><input type="button" style="height: 30px;width: 50px" value="Poor" onclick="this.disabled=true;feedbackReport(this.value,'three')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 70px" value="Average" onclick="this.disabled=true;feedbackReport(this.value,'three')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 50px" value="Good" onclick="this.disabled=true;feedbackReport(this.value,'three')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 75px" value="Very Good" onclick="this.disabled=true;feedbackReport(this.value,'three')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Excellent" style="height: 30px;width: 70px" onclick="this.disabled=true;feedbackReport(this.value,'three')"><input type="text" style="display:none;" name="costingFactor" id="three"></input>
                  </div>
                  </s:if>
                  </div>
                  
                  <s:if test="treatmentTrue">
                 <div class="user_form_text"><s:property value="%{other}"/>:</div>
                  <div class="user_form_input" style="width: 400px"><input type="button" style="height: 30px;width: 50px" value="Poor" onclick="this.disabled=true;feedbackReport(this.value,'four')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 70px" value="Average" onclick="this.disabled=true;feedbackReport(this.value,'four')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 50px" value="Good" onclick="this.disabled=true;feedbackReport(this.value,'four')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" style="height: 30px;width: 75px" value="Very Good" onclick="this.disabled=true;feedbackReport(this.value,'four')">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Excellent" style="height: 30px;width: 70px" onclick="this.disabled=true;feedbackReport(this.value,'four')"><input type="text" style="display:none;" name="treatment" id="four"></input>
                  </div>
                  </s:if>
                  		 <div class="form_menubox">
				 <s:iterator value="feedbackTextBox" status="counter">
				 <s:property value="key"/>
				 <s:if test="{#counter.count!=0}">
				  <s:if test="%{mandatroy}">
		          <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><span class="needed"> </span><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
			      </div>
			      </s:if>
			      <s:else>
			       <div class="inputmain">
                  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
			      </div>
			      </s:else>
			      </s:if>
			      <s:else>
			        <s:if test="%{mandatroy}">
		          <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><span class="needed"> </span><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
			      </div>
			      </s:if>
			      <s:else>
			       <div class="inputmain">
                  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
			      </div>
			      </s:else>
				 </s:else>
				 </s:iterator>
             	</div>
             	
             	
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
</div>
</div>
</s:form>
</sj:accordionItem>

</sj:accordion>
</div>
</div>
</html>