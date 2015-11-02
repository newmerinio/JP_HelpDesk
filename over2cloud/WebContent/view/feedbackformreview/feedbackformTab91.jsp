<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('mak', function(event,data){
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 1000);
			 $('#messagealert').dialog('open');
			setTimeout(function(){ $("#complTarget").fadeOut(); nextPage(); },1000);
			 reset('form1');
		  });
		 
		 function cancelButton()
		 {
			 $('#messagealert').dialog('close');
		 }

function closePage(){
	window.location.href = "http://www.medanta.org/";
}

function backPrevious(val)
{
	var traceId=$("#traceid1").val();
	if(traceId === 'undefined' || traceId === '' || traceId === null){
		traceId=$("#traceid").val();	
	}
	
	var type=$('#type1').val();
	if(type === 'undefined' || type === '' || type === null){
		type=$("#type").val();	
	}

	var positionPage=parseInt($("#positionPage").val())-1;
	
	var done=$("#done").val();

	var pname=$("#pname").val();
	
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/patientActivity/reviewQuestionnaireForms.action",
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=11&positionPage="+positionPage+"&done="+done,
		    success : function(subdeptdata) {
			$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#"+"reviewForms").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
	});

}

function nextPage(){
	 var traceId=$("#traceid1").val();
		if(traceId === 'undefined' || traceId === '' || traceId === null){
			traceId=$("#traceid").val();	
		}
		
		var type=$('#type1').val();
		if(type === 'undefined' || type === '' || type === null){
			type=$("#type").val();	
		}

		var positionPage=parseInt($("#positionPage").val())+1;
		
		var done=$("#done").val();

		var pname=$("#pname").val();
				
		$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/patientActivity/reviewQuestionnaireForms.action",
			    data:"type="+type+"&pageNo=1&positionPage=1&done=0",
			    success : function(subdeptdata) {
				$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		       $("#"+"reviewForms").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
		});



$( document ).ready(function()
		{

	$("textarea").blur(function(){
		 $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
	});

		$("input:radio").blur(function(){
			 $(this).removeAttr("style");
		});	

		$("input:text").blur(function(){
			 $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
		});	
		
		
	$("input:checkbox").blur(function(){
	    $(this).removeAttr("style");
	});

	var traceId=$("#traceid1").val();
	
	if(traceId === 'undefined' || traceId === '' || traceId === null){
		traceId=$("#traceid").val();	
	}
	var type=$('#type1').val();
	if(type === 'undefined' || type === '' || type === null){
		type=$("#type").val();	
	}

	
	if(type === 'M'){
		$("#totalpages").text("10");
		}
	    	
	
	});
		
$.subscribe('validate2', function(event,data)
		{
						for(var i=705;i<=719;i++)
						{
							if(i!=706 && i!=707 && i!=708 && i!=709 && i!=711 && i!=712){
							if($('input[name='+i+']:checked').length<=0)
		 						{
								errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
							  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
					     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
					     		$('input[name='+i+']').focus();
					     		event.originalEvent.options.submit = false; 	
						  			return false;
		 					}
						}
					}
 		});

function reset(formId) 
{
	$('#'+formId).trigger("reset");
}


</script>
<!-- color: #8B8B70; -->
<style type="text/css">
	#label {
  	padding:7px 0;
  	color:#4A4A4A;
	font-weight:bold !important;
	font:normal 12px arial;
	}
  
  #labelAns {
  	padding:7px 0;
  	color:#4A4A4A;
	font-weight:important;
	font:normal 12px arial;
	}
  .label {  
    	padding:7px 0;
		color:#4A4A4A;
	font-weight:bold !important;
	font:normal 12px arial;
}

h2 {
  color: rgba(74, 74, 74, 0.72);
  margin-left: 44px;
  margin-top: -34px;
  align-content: center;
  /* position: absolute; */
  border-radius: 4px;
  font-size: 17px;
  font-family: monospace;
}
#logoDiv{
background: url("../Over2Cloud/medantaImages/logo.jpg");
  border: rgb(11, 11, 11);
  height: 59px;
  width: 235px;
 height: 88px;
  width: 353px;
  background-size: 354px;
  }
 
.hrDiv{
border-bottom-style: groove;
  border-bottom-color: #C6C6C6;
}  
  #innerDiv1{
      height: 99px;
  border-bottom-style: double;
  border-bottom-color: rgb(229, 80, 80);
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
  width: 833px;
  border-width: thin;
}
div#innerDiv2{
  outline-color: grey;
  border-radius: 13px;
  background-color: #FFFFFF;
}
#questions
{
line-height: 0.72em;
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
  
   #set{
  align-content: center;
  text-decoration: underline;
  text-transform: uppercase;
  font-weight: bold;
 }
 
 #section{
   font-weight: bold;
  font-style: normal;
  text-decoration: underline;
 }

 #questionairtitle{
   font-weight: bold !important;
  /* font-size: 6px; */
  font: normal 14px arial;
  color: rgba(74, 74, 74, 0.72);
 }
 
 #titlesection{
 font-weight: bold !important;
  font: normal 16px arial;
  color: rgba(74, 74, 74, 0.72);
 }
 
 #formDiv{
 			margin-top: -449px;
  			overflow-y: visible;
  			width: 96%;
  			margin: 1%;
  			border: 1px solid #e4e4e5;
  			border-radius: 12px;
  			box-shadow: 0 1px 4px rgba(252, 61, 5, 0.37);
  			padding: 1%;
 }
 
 #mainDiv{
  align-content: center;
  align-self: center;
  background-color: #FFFFFF;
    margin: auto;
  height: 900px;
    width: 937px;
 }
 
 
 #pnameId{
   color: brown;
 }
 #feedbackform1 {
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    width: 100%;
    border-collapse: collapse;
}

#feedbackform1 td, #feedbackform1 th {
    font-size: 1em;
    /* border:1px solid #ff6633; */
    
}

#feedbackform1 th {
    font-size: 1.1em;
    text-align: left;
    padding-top: 5px;
    padding-bottom: 4px;
    background-color: #A7C942;
    color: #ffffff;
}

#feedbackform1 tr.alt td {
    color: #000000;
    background-color: #EAF2D3;
}
.tree
{
	position:absolute;;
}

input[type="radio"]
{
	width: 50px;
	
}

td.a
	{
	background-color: #FF6C47; 
	/* border-right: 1px solid #fff !important; */
	width="400px";
	}
	
	td.g
	{
	background-color: #FF6C47; 
	
	
	}
	
	td.b
	{
	background-color: #CFB873; 
	border-right: 1px solid #fff !important;"
	}
	
	td.h
	{
	background-color: #CCCCFF; 
	
	}
	
  	td.c
  	{
  	align-content: center;
  	background-color: #CFB873;
  	}
  	td.e
	{
	background-color: #E6CC80; 
	border-right: 1px solid #fff !important;"
	}
	
  	td.f
  	{
  	align-content: center;
  	background-color: #E6CC80;
  	}
  	#labelAns {
  	
  	color:#4A4A4A;
	font-weight:!important;
	font:normal 12px arial;
	}
 
 .progress {
 	width: 148px;
  height: 16px;
  margin-bottom: 0px;
  overflow: hidden;
  background-color: #906262;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
  box-shadow: inset 0 0px 2px rgba(0,0,0,.1)
}

.progress-bar {
  color: #fff;
  text-align: center;
  background-color: #337ab7;
  -webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
  box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
  -webkit-transition: width .6s ease;
  -o-transition: width .6s ease;
  transition: width .6s ease;
}

 *{
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
 
 
</style>


</head>

<body style=" #ECECEC width: 1295px;">
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 128px;width: 1095px;height: 637px;">
					<!--<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline no. 
+91 124 <span id="contactNo" style="  color: red;">4141414 </span></div>
					</div>
					--><br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
		<!--<h2>Dear <b>*******</b>, Welcome To Wellness Questionnaire designed specially for you. Kindly fill all the required information in various section to assist you better.</h2>
	--></div>
	<div id="formDiv">
	<center>
						<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>
	<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr style="float: right;">
	<td>
	Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 18px;" id="totalpages">11</div>
	</td>
	<td>
	
	<div class="progress">
	   <s:set id="done" var="doneVar" value="%{done}"/>
	<div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${doneVar}%" >
   
      <div id="complete"> <s:property  value="%{done}"/>% Complete</div>
    </div>
    </div>
	</td>
	
	</tr>
	</table>					
						
	<s:form id="form1" action="" namespace="/view/Over2Cloud/patientActivity" method="post" enctype="multipart/form-data">
		<s:hidden id="nop" name="nop" value="lasttemp"></s:hidden>
		<s:hidden id="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{#parameters.traceid}"></s:hidden>
		<s:hidden id="type" value="%{type}"/>
		<s:hidden id="traceid1" value="%{#parameters.traceid}"/>
		<s:hidden id="type1" value="%{#parameters.type}"/>
		<s:hidden id="pageNo1" value="%{#parameters.setNo}"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" value="%{positionPage}" />
		<s:hidden id="done" value="%{done}" />

		<!-- questions -->
		<br>
		<div id="questions" style=" "border: thin; margin-left:46px; clear: both;">
		
		<table style="width:1029px; height:20px;">
			<tr>
			<td class="g"><label id="titlesection" style="color: #fff; " >PRAKRUTI QUESTIONNAIRE: MEDANTA WELLNESS CENTRE</label></td>
			
		</tr>
		
		<tr>
			<td colspan="2" ><label id="labelAns"  ><b> Tick the correct box against each question. All questions can have multiple answers. Select all that is right</b></label></td>
		</tr>
	
		</table>
		
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>11. How would your friends describe your voice?</b> </label></td>
			<td class="g"></td>		
		</tr>
					
					<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Low" theme="simple"></s:label></td>
			<td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Low" list="#{'Low':''}" name="713" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="High pitched" theme="simple"></s:label></td>
			<td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="High pitched" list="#{'High pitched':''}" name="713" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Slow" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Slow" list="#{'Slow':''}" name="713" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Weak" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Weak" list="#{'Weak':''}" name="713" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sharp" theme="simple"></s:label></td>
			<td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Sharp" list="#{'Sharp':''}" name="713" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sweet" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Sweet" list="#{'Sweet':''}" name="713" theme="simple"/></label></td>
		</tr>
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="16px" align="center" class="c"></td>
			
	

		</tr>
		
		<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="16px" align="center" class="f"></td>
			
	

		</tr>
	
	
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>12. Describe your lips?</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Thin" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Thin" list="#{'Thin':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Medium" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Medium" list="#{'Medium':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Large" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Large" list="#{'Large':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dry" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Dry" list="#{'Dry':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Soft" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Soft" list="#{'Soft':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Smooth" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Smooth" list="#{'Smooth':''}" name="714" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cracking" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Cracking" list="#{'Cracking':''}" name="714" theme="simple"/></label></td>
		</tr>
<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="10px" align="center" class="f"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>13. Grade your Speech?</b> </label></td>
			<td class="g"></td>		
		</tr>
					
					<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Talks Quick" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Talks Quick" list="#{'Talks Quick':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Moderate Quantity of Speech" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Moderate Quantity of Speech" list="#{'Moderate Quantity of Speech':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Deep voice" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Deep voice" list="#{'Deep voice':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Very Talkative" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Very Talkativ" list="#{'Very Talkative':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Argues" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Argues" list="#{'Argues':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Tonal Voice" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Tonal Voice" list="#{'Tonal Voice':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Stammers" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Stammers" list="#{'Stammers':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Almost Silent" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Almost Silent" list="#{'Almost Silent':''}" name="715" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="16px" align="center" class="c"></td>

		</tr>
	
	
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>14. Describe your Sleep?</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Light" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Light':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Normal Depth of Sleep" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Normal Depth of Sleep':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Deep" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Deep':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Short Duration" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Short Duration':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Moderate to Age" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Moderate to Age':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Long Duration" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Long Duration':''}" name="717" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="18px" align="center" class="c"></td>

		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="19px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td  class="c" ></td>
			<td height="22px" align="center" class="c"></td>

		</tr>

		

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>15. Which of the following interests you?</b> </label></td>
			<td class="g"></td>		
		</tr>
					
					<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Travel" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Travel':''}" name="718" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Politics" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Politics':''}" name="718" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Flowers" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Flowers':''}" name="718" theme="simple"/></label></td>
		</tr>

		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Nature" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Nature':''}" name="718" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sports" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Sports':''}" name="718" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Water" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Water':''}" name="718" theme="simple"/></label></td>
		</tr>
<tr>
			<td style="height: 22px;" class="c" border-right: 1px solid #fff !important;"></td>
			<td height="12px" align="center" class="c"></td>
			
	
</td>
		</tr>
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>16. Rate Your Temperament?</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Nervous / Anxious" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Nervous / Anxious':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Irritable" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Irritable':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Easy Going" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Easy Going':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Fearful of Situations" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Fearful of Situations':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Impatient" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Impatient':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Having Patience" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Having Patience':''}" name="719" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c" border-right: 1px solid #fff !important;"></td>
			<td height="12px" align="center" class="c"></td>
			
	
</td>
		</tr>

		

					</table>
					
	
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>17. How would you describe your forehead?</b> </label></td>
			<td class="g"></td>		
		</tr>
					
						<tr>
			  <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Small" theme="simple"></s:label></td>
			<td class="c"><s:radio title="Small" list="#{'Small':''}" name="710"  theme="simple"></s:radio></td>
		</tr>

		<tr>
			  <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Medium" theme="simple"></s:label></td>
			 <td class="f"><s:radio title="Medium" list="#{'Medium':''}" name="710"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Large" theme="simple"></s:label></td>
			 <td class="c"><s:radio title="Large" list="#{'Large':''}" name="710"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="24px" align="center" class="f"></td>

		</tr>
		
							</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>18. Tell us which kind of Weather bothers you?</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cold and Dry" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Cold and Dry':''}" name="716" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Heat and Sun" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Heat and Sun':''}" name="716" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cold and Damp" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Cold and Damp':''}" name="716" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="23px" align="center" class="f"></td>
			
	
</td>
		</tr>

		

					</table>
					
	
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:1028px; height:20px;">
			<tr>
			
			<td class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>19. Which of the following characters would represent characteristics of your skin?</b> </label></td>
		</tr>
	
		</table>
	<!-- Question for 19 -->
		<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
				
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Lack of Tone" theme="simple"></s:label></td>
			 <td width="130" class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Lack of Tone" list="#{'Lack of Tone':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Rashes" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Rashes" list="#{'Rashes':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dull" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Dull" list="#{'Dull':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Lack of Luster" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Lack of Luster" list="#{'Lack of Luster':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Inflammatory reactions" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Inflammatory reactions" list="#{'Inflammatory reactions':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sluggish" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Sluggish" list="#{'Sluggish':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Rough patches" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Rough patches" list="#{'Rough patches':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Itching" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Itching" list="#{'Itching':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			  <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Congested" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Congested" list="#{'Congested':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cracking" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Cracking" list="#{'Cracking':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Oily T-Zone" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Oily T-Zone" list="#{'Oily T-Zone':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Black heads" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Black heads" list="#{'Black heads':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Premature wrinkling" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Premature wrinkling" list="#{'Premature wrinkling':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cystic formations" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Cystic formations" list="#{'Cystic formations':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="12px" align="center" class="c"></td>
	
		</tr>	
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Corns" theme="simple"></s:label></td>
			 <td width="130px" class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Corns" list="#{'Corns':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Large pustules" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Large pustules" list="#{'Large pustules':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			  <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="White pustules" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="White pustules" list="#{'White pustules':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Calluses" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Calluses" list="#{'Calluses':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dry eczema" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Dry eczema" list="#{'Dry eczema':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Oily scalp" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Oily scalp" list="#{'Oily scalp':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label title="Watery bullae" value="Watery bullae" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Watery bullae':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Acne" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Acne" list="#{'Acne':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			  <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yellow pustules" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Yellow pustules" list="#{'Yellow pustules':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dandruff" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Dandruff" list="#{'Dandruff':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Discolouration of natural  pigmentation (Melasma)" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Discolouration of natural  pigmentation (Melasma)" list="#{'Discolouration of natural  pigmentation (Melasma)':''}" name="q389" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dry scalp" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Dry scalp" list="#{'Dry scalp':''}" name="705" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pinkish tinge of soles and palms" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Pinkish tinge of soles and palms" list="#{'Pinkish tinge of soles and palms':''}" name="q391" theme="simple"/></label></td>
		</tr>
<tr>
			<td class="f" ></td>
			<td height="16px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="18px" align="center" class="c"></td>
	
		</tr>	
				
		

					</table>
					
					
					
	
				
				
				</td>
			</tr>
	</table>
	
	<!-- Question for 20 -->
	
	<table style="width:1028px; height:20px;">
			<tr>
			
			<td class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>20. How would you describe your hair?</b> </label></td>
		</tr>
	
		</table>
	<!-- Question for 19 -->
		<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:520px; height:10px; border-spacing: 0px;">
					<tr>
				
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dry" theme="simple"></s:label></td>
			 <td class="c" width="120px"><s:checkboxlist title="Dry" list="#{'Dry':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Rough" theme="simple"></s:label></td>
			 <td class="f"><s:checkboxlist title="Rough" list="#{'Rough':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Thick" theme="simple"></s:label></td>
			 <td class="c"><s:checkboxlist title="Thick" list="#{'Thick':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dull" theme="simple"></s:label></td>
			<td class="f"><s:checkboxlist title="Dull" list="#{'Dull':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Balding" theme="simple"></s:label></td>
			 <td class="c"><s:checkboxlist title="Balding" list="#{'Balding':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Glossy" theme="simple"></s:label></td>
			 <td class="f"><s:checkboxlist title="Glossy" list="#{'Glossy':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Frizzy" theme="simple"></s:label></td>
			 <td class="c"><s:checkboxlist title="Frizzy" list="#{'Frizzy':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Excessive loss of hair" theme="simple"></s:label></td>
			 <td class="f"><s:checkboxlist title="Excessive loss of hair" list="#{'Excessive loss of hair':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="20px" align="center" class="c"></td>
	
		</tr>	
						
						
					</table>
				</td>
							
				<td >
				
				
				<table style=" width:505px; height:10px; border-spacing: 0px;padding: 1px 1px 1px 2px;">
						
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Shiny" theme="simple"></s:label></td>
			 <td width="130px" class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Shiny" list="#{'Shiny':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Scaliness" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Scaliness" list="#{'Scaliness':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Thinning" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Thinning" list="#{'Thinning':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Grows easily" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Grows easily" list="#{'Grows easily':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Split ends" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Split ends" list="#{'Split ends':''}" name="706" theme="simple"/></label></td>
		</tr>

		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Brownish" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Brownish" list="#{'Brownish':''}" name="706" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Oily" theme="simple"></s:label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Oily" list="#{'Oily':''}" name="706" theme="simple"/></label></td>
		</tr>
		<tr>
			<td class="f" ></td>
			<td height="22px" align="center" class="f"></td>
	
		</tr>

			
				<tr>
			<td class="c" ></td>
			<td height="27px" align="center" class="c"></td>
	
		</tr>
				
		

					</table>
					
					
					
	
				
				
				</td>
			</tr>
	</table>
	
	
	
			
		
		

		<!-- Buttons -->
		<br>
		   <div class="clear"></div>
    <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button">
   <div id="bt" style="display: block;">

  		<sj:a cssStyle=" margin-left: -267px; margin-top: -8px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="I am done" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           button="true"
	           onBeforeTopics="validate22"
	           cssStyle="   margin-left: -94px; margin-top: -27px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onCompleteTopics="mak"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-47px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: -2px;  margin-top: -47px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" >Close</sj:a>
 	
   </div>
   </div>
   </div>

			<sj:dialog
          id="messagealert"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Message"
          modal="true"
          width="250"
          height="120"
          draggable="true"
    	  resizable="true"
          >
       	<center><div id="label"><div id="alertDiv">All Questionnaire are filled out successfully.</div></div></center>   
</sj:dialog>
			</s:form>
			</div>
		<div id="complTarget"></div>	
		</div>	
</div>
</div>
</body>

</html>