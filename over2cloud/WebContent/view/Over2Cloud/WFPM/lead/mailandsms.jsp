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
	function onload()
	{
		$("#result").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeFromKR.action");
	}
</script>
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
		function resetForm(formRichtext)
			{
				$('#'+formRichtext).trigger("reset");
			}
	</script>
<script type="text/javascript">

$.subscribe('cancelleadview',function(event,data){
	var urls="";
	var temp=$("#mainHeaderName").val();
	var isExistingClient=$("#isExistingClient").val();
	var isExistingAssociate=$("#isExistingAssociate").val();
	var status = $("#status").val();
	// alert("temp:"+temp+" isExistingClient:"+isExistingClient+" isExistingAssociate:"+isExistingAssociate+" status:"+status);
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	if(temp.indexOf('Lead') != -1)
	{
		urls = "view/Over2Cloud/WFPM/Lead/beforeleadView.action?status="+status+"&mainHeaderName="+temp;
	}
	else if(temp.indexOf('Client') != -1)
	{
		urls = "view/Over2Cloud/wfpm/client/beforeClientView.action?isExistingClient="+isExistingClient+"&mainHeaderName="+temp;
	}
	else if(temp.indexOf('Associate') != -1)
	{
		urls = "view/Over2Cloud/WFPM/Associate/beforeAssociateView.action?isExistingAssociate="+isExistingAssociate+"&mainHeaderName="+temp;
	}
	$.ajax({
	    type : "post",
	    url : encodeURI(urls),
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
		   
	            alert("error");
	        }
	  });
	event.originalEvent.options.submit = false;
});
</script>
<script type="text/javascript">
	function loadsCCName()
	{
		var id=$("#id").val();
		var emailtofilter=$("#emailone").val();
		// alert("Client id>"+id+"emailtofilter>"+emailtofilter);
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/ccemaildata.action",
	    data: "emailtofilter="+emailtofilter+"&id="+id,
	    success : function(subdeptdata) {
      	$('#emailtwo option').remove();
			$('#emailtwo').append($("<option></option>").attr("value",-1).text('Select CC'));
			$(subdeptdata).each(function(index)
			{
			   $('#emailtwo').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
			});
	},
	   error: function() {
            alert("error");
        }
	 });
	}
	function loadBCCName()
	{
		var id=$("#id").val();
		var emailtofilter=$("#emailone").val();
		var emailtwo=$("#emailtwo").val();
		var emailtofiltertwo = emailtwo.split("-")[1];
		// alert("Client id>"+id+"emailtofilter>"+emailtofilter+"emailtofiltertwo>"+emailtofiltertwo);
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/bccemaildata.action",
	    data: "emailtofilter="+emailtofilter+"&id="+id+"&emailtofiltertwo="+emailtofiltertwo,
	    success : function(subdeptdata) {
      	$('#emailthree option').remove();
			$('#emailthree').append($("<option></option>").attr("value",-1).text('Select BCC'));
			$(subdeptdata).each(function(index)
			{
			   $('#emailthree').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
			});
	},
	   error: function() {
            alert("error");
        }
	 });
	}
</script>
<script type="text/javascript">
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#submitmailform").fadeIn(); }, 10);
          setTimeout(function(){ $("#submitmailform").fadeOut(); }, 6000);
          
});
</script>
<script type="text/javascript">
function changeFrequency(val,div1,div2,div3,div4)
{
 var p=document.getElementById(div1);
var q=document.getElementById(div2);
var r=document.getElementById(div3);
var s=document.getElementById(div4)
if(val=="One-Time")
{
	p.style.display='block';
	q.style.display='none';
	r.style.display='block';
	s.style.display='none';
	}
else if(val=="Daily")
{
	p.style.display='none';
    q.style.display='none';
    r.style.display='block';
    s.style.display='none';
	}
else if(val=="Weekly")
{
	p.style.display='none';
    q.style.display='block';
    r.style.display='block';
    s.style.display='none';
	}
else if(val=="Monthly")
{
	p.style.display='block';
	q.style.display='none';
	r.style.display='block';
	s.style.display='none';
	}
else if(val=="Yearly")
{
	p.style.display='none';
	q.style.display='none';
	r.style.display='block';
	s.style.display='block';
	 }
else if(val=="None")
{
	p.style.display='none';
	q.style.display='none';
	r.style.display='none';
	s.style.display='none';
	}
	}
</script>
<script type="text/javascript">
function showScheduleDiv()
	{
		var val = $("#mailtypeId option:selected").val().trim();
		// alert(val);
	
		if(val == "Schedule")
		{
			 document.getElementById("scheduledateDiv").style.display="block";
			
		}else
		{
			 document.getElementById("scheduledateDiv").style.display="none";
		}
	}
</script>
<script type="text/javascript">
function gettemplatemsg(message){
	$.getJSON("view/Over2Cloud/WFPM/Lead/maildataontemplate.action?message="+message,
		      function(data){
		            $("#richtextEditor").val(data);
		            // var i=document.getElementById("richtextEditor").value.length;
		           //  $("#remLen").val(i);
		    	  });
}
</script>
</head>
<body>
<s:hidden id="mainHeaderName" value="%{#parameters.mainHeaderName}"></s:hidden>
<s:hidden id="isExistingClient" value="%{#parameters.isExistingClient}"></s:hidden>
<s:hidden id="isExistingAssociate" value="%{#parameters.isExistingAssociate}"></s:hidden>
<s:hidden id="status" value="%{#parameters.status}"></s:hidden>
			<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<!-- <div class="head">Email and SMS</div> -->
		<div class="head">Email</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Send</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	
	<div class="border">
	<!-- <div class="secHead">Email and SMS</div> -->
		<div style="width: 100%; text-align: center; padding-top: 10px;">
								
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
        </div>
							
<s:form id="formRichtext" action="sendmailiAction" theme="simple" cssClass="yform">
<s:hidden name="mobileNo" value="%{mobileNo}"></s:hidden>
<s:hidden name="id" value="%{#parameters.id}" id="id"></s:hidden>

			
	        <!--<div class="newColumn">
	        	 <div class="leftColumn1">SMS: </div>
	        	 <div class="rightColumn1">
	        	 	<s:checkbox name="sms" onclick="chkCheckMe()"   cssStyle="margin:0px 0px 10px 0px" />
	        	 	
	        	 </div>
	        </div>
	         <div id="smsDiv" style="display: none;">
	         	<div class="newColumn">
	        	 <div class="leftColumn1">SMS Msg.:</div>
	        	 <div class="rightColumn1">
	        	 	<s:textarea name="smsData" id="smsData" cols="60" rows="2" cssStyle="margin:0px 0px 10px 0px" /></div>
	        	 	
	        	 </div>
	        </div>
	         
	     --><div class="newColumn">
         <div class="leftColumn1">To: </div>
            <div class="rightColumn1">
	        <s:select 
	           id="emailone"
	           name="emailone"
	           list="clientContactandEmailMap"
	           headerKey="-1"
	           headerValue="Select"
	           cssClass="select"
	           cssStyle="width:82%"
	           onchange="loadsCCName();"
	           
	         ></s:select>
	        </div>
	  </div> 
	     <div class="newColumn">
         <div class="leftColumn1">CC: </div>
            <div class="rightColumn1">
	        <s:select 
	           id="emailtwo"
	           name="emailtwo"
	           list="#{'-1':'CC'}" 
	           headerKey="-1"
	           headerValue="Select"
	           cssClass="select"
	           cssStyle="width:82%"
	           onchange="loadBCCName();"
	           
	         ></s:select>
	        </div>
	  </div> 
	     <div class="newColumn">
         <div class="leftColumn1">BCC: </div>
            <div class="rightColumn1">
	        <s:select 
	           id="emailthree"
	           name="emailthree"
	           list="#{'-1':'BCC'}" 
	           headerKey="-1"
	           headerValue="Select"
	           cssClass="select"
	           cssStyle="width:82%"
	         >
	         </s:select>
	        </div>
	  </div> 
							<div class="newColumn">
					         <div class="leftColumn1">Template: </div>
           						 <div class="rightColumn1">
	       							 <s:select 
				                              id="template_name"
				                              name="template_name"
				                              list='templateNameList'
				                              headerKey="-1"
				                              headerValue="-Select Template Name-" 
				                              cssClass="select"
				                               cssStyle="width:82%"
				                              onchange="gettemplatemsg(this.value);"
											>
	         							</s:select>
	       						 </div>
	 					 </div> 			     
  			<div class="newColumn">
	        	 <div class="leftColumn1">Subject:</div>
	        	 <div class="rightColumn1">
	        	 	<s:textarea name="subject"  value="%{#parameters.subject}"  rows="2" cols="60"  cssStyle="width:82%" />
	        	 	
	        	 </div>
	        </div>
		<div class="newColumn">
			<div class="leftColumn1">Attachement:</div>
			<div class="rightColumn1">
			     <s:file id="attachedDoc" name="attachedDoc"></s:file>
			</div>
		</div>  
		<div id = "result"> </div> 
	   <div class="newColumn">
       <div class="leftColumn1">Send: </div>
            <div class="rightColumn1">
	        <s:select 
	           id="mailtypeId"
	           name="mailtype"
	           list="#{'Instant':'Instant', 'Schedule':'Schedule'}" 
	           headerKey="-1"
	           headerValue="Select"
	           cssClass="select"
	           cssStyle="width:82%"
	           onchange="showScheduleDiv();"
	         >
	         </s:select>
	        </div>
	  </div> 
	  <div id="scheduledateDiv" style="display: none">
	   <s:iterator value="frequency" begin="0" end="0">
		<div class="newColumn">
       <div class="leftColumn1">
       <s:property value="%{value}"/>:</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      		 <s:radio list="#{'One-Time':'One-Time','Daily':'Daily','Weekly':'Weekly','Monthly':'Monthly','Yearly':'Yearly'}" name="%{key}" id="%{key}" onclick="changeFrequency(this.value,'dateDiv','dayDiv','timeDiv','dateDiv1')"/>
      
      </div>
       </div>
					
							<div id=dateDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       								<sj:datepicker id="date" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd"  cssClass="textField"  cssStyle="width:82%"/>
      						 </div>
				                  
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
							<div id=dateDiv1 style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      							 <sj:datepicker id="dates" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd" cssClass="textField" />
     						  </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
						<div id=timeDiv style="display: none">	
					     <div class="newColumn">
							<div class="leftColumn1">Time:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      					 <sj:datepicker id="hours" name="hours" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" timepickerFormat="hh:mm:ss" cssClass="textField"  cssStyle="width:82%"/>
      						 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>
							
							<div id=dayDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Day:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
								  <s:select 
                              id="day"
                              name="day" 
                              list="#{'Monday':'Monday','Tuesday':'Tuesday','Wednesday':'Wednesday','Thursday':'Thursday','Friday':'Friday','Saturday':'Saturday'}"
                              headerKey="Sunday"
                              headerValue="Sunday" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
      					 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>
						

</s:iterator>
	    
</div>
	        <div class="clear"></div>	 	
	        <br>
	        <center>
	        	<div class="type-text">
				<sjr:ckeditor 
					id="richtextEditor" 
					name="mailbody" 
					rows="5" 
					cols="100" 
					width="860"
					height="150"
					uploads="true"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
				
				/>
	        </div>
	        </center>
	        
	        <br>
 </s:form>
      <div class="type-button">
				<sj:submit
					id="richtextSubmitButton"
					formIds="formRichtext" 
					targets="result" 
					clearForm="true"
					listenTopics="saveRichtext"
					value="Send" 
					indicator="indicator" 
					button="true"
					onBeforeTopics="validateMe"
					 onCompleteTopics="level1"
				/>
				<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." 
					style="display:none"/>
					<sj:a 
	     	    name="Reset"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="resetForm('formRichtext');"
				>
	  			Reset
			</sj:a>
                  <sj:submit 
                  value="Back" 
                  effect="highlight"
                  effectOptions="{ color : '#222222'}"
                  effectDuration="5000"
                  button="true"
                  cssClass="submit"
                  onClickTopics="cancelleadview"
                  />
	        </div>
      <br><br>
   <sj:div id="submitmailform"  effect="fold">
           <div id="result"></div>
        </sj:div>

</div>
</div>
</div>
</body>
<script type="text/javascript">
onload();
</script>
</html>