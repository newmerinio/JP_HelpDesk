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
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	setTimeout(function(){ $("#complTarget").fadeOut(); nextPage();},2000);
	 //$('#completionResult').dialog('open');
	 reset('form1');
 });

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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=3&positionPage="+positionPage+"&done="+done,
		    success : function(subdeptdata) {
			$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#"+"reviewForms").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
	})
}


function cancelButton()
{
	 $('#completionResult').dialog('close');
}

$.subscribe('validate2', function(event,data)
		{
 for(var i=106 ;i<=117;i++)
	{
		if(i!=107 && i!=109 && i!=110 && i!=111 && i!=113 && i!=114 && i!=115 && i!=116 && i!=117 ){		
			
			if(i==106 || i==108 || i==112 ){		
				//if($('input[name='+i+']:checked').length<=0 && i!=136 && i!=147 && i!=156)
				 if($('input[name='+i+']:checked').length<=0 )
						 {
						  	//$("#alertDiv").html("Please select Question "+$("input[name='"+i+"']").attr("title"));
						  	//$('#messagealert').dialog('open');
						  	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
	            			setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            			setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
				     		$('input[name='+i+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 }else{
							 console.log("in2"+i);
							 if($('input[name='+i+']:checked').val() === 'Yes'){
								 	console.log($('input[name='+i+']:checked').val() +" in3 "+i);
								 	
								 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
									 	
									console.log(($('input[name='+(i+1)+']').val())+"  in4  "+ (i+1));
								 	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
								  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
						     		$('input[name='+(i+1)+']').css("box-shadow","0 0 13px 0px red inset");
						     		$('input[name='+(i+1)+']').focus();
								  	event.originalEvent.options.submit = false;
								  	return false;
								 	}
								 	
								 }	
							 }
			 	
			 }
		 }
		else if(i == 110 || i == 114 || i == 116) {
			//	alert($('input[name='+(i+1)+']').val())
                if($('input[name='+i+']:checked').length<=0 && $('input[name='+(i+1)+']').val().trim()=='')
				 {
				  	errZone.innerHTML="<div class='user_form_inputError2'>Please select any one from them.</div>";
        			setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        			setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        			$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
        			$('input[name='+i+']').focus();
	
        			$('input[name='+(i+1)+']').focus();
        			$('input[name='+(i+1)+']').css({"border": "1px solid red","background": "#FFCECE"});
	  				        				
        			event.originalEvent.options.submit = false;
				  	return false;
    			    }
                else{
                	$('input[name='+i+']').css("#FFFFFF","0 0 13px 0px white inset");
        			$('input[name='+i+']').focus();
	
        			$('input[name='+(i+1)+']').focus();
        			$('input[name='+(i+1)+']').css({"border": "1px solid white","background": "#FFFFFF"});
                    }
    		}
		}	
  });		 



function closePage(){
window.location.href = "http://www.medanta.org/";
}

$( document ).ready(function()
{	
	$("textarea").blur(function(){
	 $(this).removeAttr("style");
});

	$("input:radio").blur(function(){
    $(this).removeAttr("style");
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
	$("#totalpages").text("9");
	}
	
   	
});




function getUrlParameter(sParam)
{
var sPageURL = window.location.search.substring(1);
var sURLVariables = sPageURL.split('&');
for (var i = 0; i < sURLVariables.length; i++) 
{
var sParameterName = sURLVariables[i].split('=');
if (sParameterName[0] == sParam) 
{
   return sParameterName[1];
}
}
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
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=5&positionPage="+positionPage+"&done=40",
			    success : function(subdeptdata) {
				$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		       $("#"+"reviewForms").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
		});

	
}

function reset(formId) 
{
	$('#'+formId).trigger("reset");
}

function openDiv(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text1').style.display = 'block';
		 
		}
	 
	 else{
			document.getElementById('text1').style.display = 'none';
		}
}

function openDiv7(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text28').style.display = 'block';
		 document.getElementById('text30').style.display = 'block';
		 document.getElementById('three').style.display = 'block';
		 document.getElementById('four').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text28').style.display = 'none';
			document.getElementById('text30').style.display = 'none';
			document.getElementById('three').style.display = 'none';
			document.getElementById('four').style.display = 'none';
		}
}

function openDiv8(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text31').style.display = 'block';
		 document.getElementById('text32').style.display = 'block';
		 document.getElementById('five').style.display = 'block';
		 document.getElementById('six').style.display = 'block';
		 
		}else{
			document.getElementById('text31').style.display = 'none';
			document.getElementById('text32').style.display = 'none';
			document.getElementById('five').style.display = 'none';
			document.getElementById('six').style.display = 'none';
		}
}

function openDiv9(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text33').style.display = 'block';
		 document.getElementById('text34').style.display = 'block';
		 document.getElementById('seven').style.display = 'block';
		 document.getElementById('eight').style.display = 'block';
		 
		}else{
			document.getElementById('text33').style.display = 'none';
			document.getElementById('text34').style.display = 'none';
			document.getElementById('seven').style.display = 'none';
			 document.getElementById('eight').style.display = 'none';
		}
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
  font-size: 15px;
  font-family: monospace;
}
#logoDiv{
background: url("../medantaImages/logo.jpg");
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
  margin-top: -49px;Map
  text-indent: -56px;
  text-decoration: blink;
  margin-right: 56px;
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
      padding: 10px 7px 2px 7px;
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
<body>
	<div class="clear"></div>
		<div id="mainDiv" >
					<!--<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					--><br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="   margin-top: -7px;display: none" >
		<!--<h2>Dear <b id="pnameId"><s:property value="%{pname}" /></b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
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
	Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 18px;" id="totalpages">10</div>
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
		
		<div id="questions" style=" "border: thin; margin-left:46px; clear: both;">
		
		<table style="width:878px; border-spacing:0; height:20px;">
			<tr>
			<td align="center" width="878px" class="g">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " >Past illness</label></td>
			
		</tr>
		
		
		</table>
		
		
	<table style="width:871px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:425px;   padding: 1px 2px 1px 1px; border-spacing: 0px;">
					<tr>
			<td width="350px" height="23px"  colspan="2" class="c"><label id="titlesection"  style="color: #fff;  ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Any medical illness  or hospitalisation in the past?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="106"  title="Very low" onclick="openDiv7(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td  style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="106"  onclick="openDiv7(this.value)" title="Very low" theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text28" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Details</label><br>
				
				</div> 
				
				
				
			<td class="f" colspan="2"> 
			<div id="text30" style="display: none" >
			<s:textfield name="107" value="" theme="simple" style="width:150px;margin-left: -164px;MARGIN-BOTTOM: -15px;"/>
			</div>
			
				</td> 
				
			
			</tr>
			
				
			<tr>
			<td style="height: 22px;" class="f" border-right: 1px solid #fff !important;"></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
		
		
		<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Any Accident /Surgery in the past?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="108"  title="Any Accident /Surgery in the past?" onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td  style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="108"  onclick="openDiv8(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text31" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Details</label><br>
				
				</div> 
				
				
				
			<td class="f" colspan="2"> 
			<div id="text32" style="display: none" >
			<s:textfield name="109" value="" theme="simple" style="width:150px;margin-left: -164px;MARGIN-BOTTOM: -15px;"/>
			</div>
			
				</td> 
				
			
			</tr>
			
			<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Any difficulty in exercising partially or completely due to</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="No difficulty" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="110" theme="simple"/></label></td>
		</tr>
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Orthopaedic impairment" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Orthopaedic impairment':''}" name="110" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Muscular dysfunction" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Muscular Dysfunction':''}" name="110" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Paralysis /Stroke" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Paralysis /Stroke':''}" name="110" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Polio" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Polio':''}" name="110" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Amputation of limbs" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Amputation of limbs':''}" name="110" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td  class="f" align="center"><s:textfield name="111" value="" theme="simple" style="width:150px;margin-right: 35px;"/></label></td>
			 
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="29px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="29px" align="center" class="f"></td>

		</tr>
		
		
		<tr>
			<td class="c">
			<div id="seven" style="display: none; height: 17px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="eight" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:452px; height:10px; padding: 2px 0px 1px 1px; border-spacing: 0px">
						<tr>
			<tr>
			<td width="350px" colspan="2" height="22px" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Do you currently have cancer or have you had cancer in the past?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="112"  title="Very low" onclick="openDiv9(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td  style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="112"  onclick="openDiv9(this.value)" title="Very low" theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text33" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Details</label><br>
				
				</div> 
				
				
				
			<td class="f" colspan="2"> 
			<div id="text34" style="display: none" >
			<s:textfield name="113" value="" theme="simple" style="width:150px;"/>
			</div>
			
				</td> 
				
			
			</tr>
		
		<tr>
			<td width="380px" style="height: 22px;" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Any history of frequent Exposures to the following</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="114" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Gasoline" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Gasoline':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Radiation" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Radiation':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dust" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Dust':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Fumes" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Fumes':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Paint" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Paint':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Industrial Chemicals" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Industrial Chemicals':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sharp sun-rays" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Sharp sun-rays':''}" name="114" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td  class="f" align="center"><s:textfield name="115" value="" theme="simple" cssStyle="width:150px;margin-left: 6px;margin-right: 32px;"/></label></td>
			 
		</tr>
		
	
			
				
			
			</tr>
	
<tr>
			<td class="c" ></td>
			<td height="25px" align="center" class="c"></td>

		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="25px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="24px" align="center" class="c"></td>

		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="27px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="17px" align="center" class="c"></td>

		</tr>
		
		
		
		<tr>
			<td class="c">
			<div id="three" style="display: none; height: 16px;">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="four" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			<tr>
			<td class="f">
			<div id="five" style="display: none; height: 16px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="six" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
	
	
	
	<table style="width:874px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:425px;  height:10px; border-spacing: 0px;">
					
		
		<tr>
			<td width="380px" colspan="2" class="f"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Any Drug Allergies</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="116" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pencillins" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Pencillins':''}" name="116" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sulfa" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Sulfa':''}" name="116" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Painkillers" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Painkillers':''}" name="116" theme="simple"/></label></td>
			 
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Any other drugs" theme="simple"></s:label></td>
			 <td  class="c" align="center"><s:textfield name="117" value="" theme="simple" style="width:150px;margin-left: 6px;margin-right: 32px;"/></label></td>
			 
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="18px" align="center" class="f"></td>
	
		</tr>
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:452px; height:10px; border-spacing: 0px; padding:1px 1px 1px 2px">
		<tr>
			<td width="380px" colspan="2" class="c" height="15px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="f" height="15px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="c" height="15px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="f" height="16px"></td>
				
		</tr>
		
		
		<tr>
			<td width="380px" colspan="2" class="c" height="16px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="f" height="16px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="c" height="17px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="f" height="18px"></td>
				
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="c" height="18px"></td>
				
		</tr>
	
		
		

					</table>
				
				
				</td>
			</tr>
	</table>
	<br>
	
	<div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
   <div id="bt" style="display: block;">
  		<sj:a cssStyle="   margin-left: -337px; margin-top: -77px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
  		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	     
	           button="true"
	           cssStyle=" margin-left: -176px; margin-top: -76px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; "
	           indicator="indicator1"
	           onCompleteTopics="mak"
	           onBeforeTopics="validate22222"
			   />
 	         <sj:a cssStyle="   margin-left: 35px; margin-top: -145px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle="  margin-left: -2px; margin-top: -146px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#" onclick='closePage();'>Close</sj:a>
			<!--    	onBeforeTopics="validate2" -->
   </div>
   </div>
   </div>
	
	
	
	
	
			
		</div>
		
		
		

		<!-- Buttons -->
		<br>
		   
		<sj:dialog
          id="messagealert"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Message"
          modal="true"
          width="300"
          height="150"
          draggable="true"
    	  resizable="true"
    	    
          >
       	<center><div id="label"><div id="alertDiv">Please Select Any One</div></div></center>	   
</sj:dialog>
			</s:form>
			</div>
		 <div id="complTarget"></div>
		</div>	
</div>
</body>

</html>