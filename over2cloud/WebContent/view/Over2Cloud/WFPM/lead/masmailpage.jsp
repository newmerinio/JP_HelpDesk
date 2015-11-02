<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	
		<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var a = 0;
function chkCheckMe()
{
  if(a == 0){
      $("#smsDiv").css("display","block");
      a = 1;
  }
  else if(a == 1){
 	 $("#smsDiv").css("display","none");
      a = 0;
  }
}

$.subscribe('validateMe', function(event,data)
{
	var msg = $("#smsData").val();
	if(a == 1 && (msg == "" || msg == null))
	{
		errZone.innerHTML="<div class='user_form_inputError2'>SMS body is blank !</div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#smsData").focus();
        $("#smsData").css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        return;   
	}
	var mail = $("#richtextEditor").val();
	if(mail == "" || mail == null)
	{
		errZone.innerHTML="<div class='user_form_inputError2'>Mail body is blank !</div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#richtextEditor").focus();
        event.originalEvent.options.submit = false;
        return; 
	}
});

</script>
<script type="text/javascript">
function cancelleadview(){
$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
$.ajax({
    type : "post",
    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=1&lostLead=0",
    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
},
   error: function() {
            alert("error");
        }
  });
}
</script>
</head>
<body>
<div class="list-icon">
	<div class="head">Lead</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Email and SMS</div>
	</div>
<div class="border">
			<div class="clear"></div>
<div class="middle-content">

	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 80%;"></div>
	
	<!-- <div class="secHead">Email and SMS</div> -->
		<div style="width: 80%; text-align: center; padding-top: 10px;">
								
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
        </div>
							
	<div class="tabs">
<s:form id="formRichtext" action="sendmailiAction" theme="simple" cssClass="yform">
<center>
<s:hidden name="mobileNo" value="%{mobileNo}"></s:hidden>
<s:hidden name="id" value="%{#parameters.id}"></s:hidden>

			<div class="form_menubox">
	              <div class="user_form_text">SMS:</div>
                  <div class="user_form_input"><s:checkbox name="sms" onclick="chkCheckMe()"   cssStyle="margin:0px 0px 10px 0px" /></div>
					
	        </div>
	        <div class="form_menubox">
	        <div id="smsDiv" style="display: none;">
	              <div class="user_form_text">SMS Msg.:</div>
                  <div class="user_form_input">
                  <span class="needed"/>
                  <s:textarea name="smsData" id="smsData" cols="60" rows="2" cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext"/></div>
					
	        </div>
	        </div>
	        
		 <div class="form_menubox">
                  <div class="user_form_text">Emai Id:</div>
                  <div class="user_form_input">
                  <span class="needed"/>
                  <s:textfield name="emailone" readonly="true"  id="emailone" value="%{#parameters.email}"  cssStyle="margin:0px 0px 10px 0px;width: 330%;"  cssClass="form_menu_inputtext"/></div>
				
             	 </div>
	
 <div class="form_menubox">
    
				  <div class="user_form_text">Subject:</div>
                  <div class="user_form_input"><s:textarea name="subject" cssStyle="height: 50px;width: 330%;" value="%{#parameters.subject}"  rows="4" cols="100" /></div>
			 
      </div>
<div class="type-button" style="margin-left: 54px;">
				<sj:submit
					id="richtextSubmitButton"
					formIds="formRichtext" 
					targets="result" 
					listenTopics="saveRichtext"
					value="Send" 
					indicator="indicator" 
					button="true"
					onBeforeTopics="validateMe"
				/>
				<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." 
					style="display:none"/>
					
		      <sj:submit 
                      value="Back" 
                      effect="highlight"
                      effectOptions="{ color : '#222222'}"
                      effectDuration="5000"
                      button="true"
                      cssClass="submit"
                      onclick="cancelleadview();"
                      />
	        </div>
	        <br>
	        <div class="type-text">
				<sjr:ckeditor 
					id="richtextEditor" 
					name="mailbody" 
					rows="5" 
					cols="100" 
					width="850"
					height="150"
					uploads="true"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
				
				/>
	        </div>
	        
	        
	        <br>
      
      </center>
    </s:form>

</div>
</div>
</div>
</div>
</body>
</html>