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


$.subscribe('mak', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 1000);
			setTimeout(function(){ $("#complTarget").fadeOut();nextPage(); }, 1000);
			 reset('form1');
		  });
		 
$.subscribe(
		'validate2',
		function(event, data) {
			for ( var i = 307; i <= 329; i++) {
				console.log(i);
				if (i != 304 && i != 305 && i != 306 && i != 322
						&& i != 324 && i != 325 && i != 326
						&& i != 328 && i != 330 && i != 331 ) {

					if(i==321 || i==323 || i==327 || i==329 || i==329){
						 if($('input[name='+i+']:checked').length<=0 )
						 {
						  	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
		        			setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		        			setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
				     		$('input[name='+i+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 }else{
							 if(i==323 && $('input[name='+i+']:checked').val() === 'Yes'){
								 	
									for(var j=1;j<4;j++){
										if(($('input[name='+(i+j)+']').val()).trim() === ''){
										 	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
										  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
								     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
								     		$('input[name='+(i+j)+']').css({"border": "1px solid red","background": "#FFCECE"});
								     		$('input[name='+(i+j)+']').focus();
										  	event.originalEvent.options.submit = false;
										  	return false;
										 	}
										}
								 } 
							 else if($('input[name='+i+']:checked').val() === 'Yes'){
								 	
								 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
									 	
									console.log(($('input[name='+(i+1)+']').val())+"  in4  "+ (i+1));
								 	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
								  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
						     		$('input[name='+(i+1)+']').css({"border": "1px solid red","background": "#FFCECE"});
						     		$('input[name='+(i+1)+']').focus();
								  	event.originalEvent.options.submit = false;
								  	return false;
								 	}
								 	
								 }	
						 }
						}else{
							if($('input[name='+i+']:checked').length<=0 )
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

					
				} else if (i != 322 && i != 324 && i != 325
						&& i != 326 && i != 328 && i != 330
						&& i != 331 && i != 333 && i != 334
						&& i != 335 && i != 337 && i != 338) {

					if ($.trim($('textarea[name=' + i + ']').val()) == "") {
						errZone.innerHTML = "<div class='user_form_inputError2'>Please fill the value of field.  </div>";
						setTimeout( function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout( function() {
							$("#errZone").fadeOut();
						}, 2000);
						$('textarea[name=' + i + ']').focus();
						$('textarea[name=' + i + ']').css( {
							"border" : "1px solid red",
							"background" : "#FFCECE"
						});
						event.originalEvent.options.submit = false;
						return false;
					}
				}
			}

		});

		 
		 function cancelButton()
		 {
			 $('#completionResult').dialog('close');
			 
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
					    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=6&positionPage="+positionPage+"&done="+done,
					    success : function(subdeptdata) {
						$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				       $("#"+"reviewForms").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
				});
	 }
		 
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
					 $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
				});
			 					
					  	var traceId=$("#traceid1").val();
					    		
					    	if(traceId === 'undefined' || traceId === '' || traceId === null){
					    		traceId=$("#traceid").val();	
					    	}
					    	//alert(traceId);
					    	var type=$('#type1').val();
					    	if(type === 'undefined' || type === '' || type === null){
					    		type=$("#type").val();	
					    	}

					    	if(type === 'M'){
								$("#totalpages").text("10");
								}
					    	
					    		
					});
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
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

		var pageNo=8;
		if(type === 'F'){
			pageNo=9;
		}
				
		
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/patientActivity/reviewQuestionnaireForms.action",
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo="+pageNo+"&positionPage="+positionPage+"&done=60",
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
	 document.getElementById('text28').style.display = 'block';
	 document.getElementById('text29').style.display = 'block';
	 document.getElementById('text30').style.display = 'block';
	 document.getElementById('text31').style.display = 'block';
	 
	}else{
		document.getElementById('text28').style.display = 'none';
		document.getElementById('text29').style.display = 'none';
		document.getElementById('text30').style.display = 'none';
		document.getElementById('text31').style.display = 'none';
	}
}

function openDiv1(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text32').style.display = 'block';
		 document.getElementById('text33').style.display = 'block';
		 document.getElementById('text34').style.display = 'block';
		 document.getElementById('text35').style.display = 'block';
		 document.getElementById('text36').style.display = 'block';
		 document.getElementById('text37').style.display = 'block';
		 
		}else{
			document.getElementById('text32').style.display = 'none';
			document.getElementById('text33').style.display = 'none';
			document.getElementById('text34').style.display = 'none';
			document.getElementById('text35').style.display = 'none';
			document.getElementById('text36').style.display = 'none';
			document.getElementById('text37').style.display = 'none';
		}
}

function openDiv2(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text40').style.display = 'block';
		 document.getElementById('text41').style.display = 'block';
		 document.getElementById('text42').style.display = 'block';
		 document.getElementById('text43').style.display = 'block';
		 document.getElementById('text44').style.display = 'block';
		 document.getElementById('text45').style.display = 'block';
		 document.getElementById('text46').style.display = 'block';
		 document.getElementById('text47').style.display = 'block';
		 
		}else{
			document.getElementById('text40').style.display = 'none';
			document.getElementById('text41').style.display = 'none';
			document.getElementById('text42').style.display = 'none';
			document.getElementById('text43').style.display = 'none';
			document.getElementById('text44').style.display = 'none';
			document.getElementById('text45').style.display = 'none';
			document.getElementById('text46').style.display = 'none';
			document.getElementById('text47').style.display = 'none';
		}
}

function openDiv3(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text4').style.display = 'block';
		 
		}else{
			document.getElementById('text4').style.display = 'none';
		}
}

function openDiv4(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text5').style.display = 'block';
		 
		}else{
			document.getElementById('text5').style.display = 'none';
		}
}

function openDiv5(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text6').style.display = 'block';
		 
		}else{
			document.getElementById('text6').style.display = 'none';
		}
}

function openDiv6(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text7').style.display = 'block';
		 
		}else{
			document.getElementById('text7').style.display = 'none';
		}
}

function openDiv7(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text8').style.display = 'block';
		 
		}else{
			document.getElementById('text8').style.display = 'none';
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
		 document.getElementById('text38').style.display = 'block';
		 document.getElementById('text39').style.display = 'block';
		 document.getElementById('text40').style.display = 'block';
		 document.getElementById('text41').style.display = 'block';
		 document.getElementById('text42').style.display = 'block';
		 document.getElementById('text43').style.display = 'block';
		 document.getElementById('seven').style.display = 'block';
		 document.getElementById('eight').style.display = 'block';
		 
		}else{
			document.getElementById('text38').style.display = 'none';
			document.getElementById('text39').style.display = 'none';
			document.getElementById('text40').style.display = 'none';
			document.getElementById('text41').style.display = 'none';
			document.getElementById('text42').style.display = 'none';
			document.getElementById('text43').style.display = 'none';
			document.getElementById('seven').style.display = 'none';
			document.getElementById('eight').style.display = 'none';
			
		}
}

function openDiv42(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text50').style.display = 'block';
		 document.getElementById('text51').style.display = 'block';
		 document.getElementById('text52').style.display = 'block';
		 document.getElementById('text53').style.display = 'block';
		 document.getElementById('text54').style.display = 'block';
		 document.getElementById('text55').style.display = 'block';
		 document.getElementById('text56').style.display = 'block';
		 document.getElementById('text57').style.display = 'block';
		 document.getElementById('text58').style.display = 'block';
		 document.getElementById('text59').style.display = 'block';
		 
		}else{
			document.getElementById('text50').style.display = 'none';
			document.getElementById('text51').style.display = 'none';
			document.getElementById('text52').style.display = 'none';
			document.getElementById('text53').style.display = 'none';
			document.getElementById('text54').style.display = 'none';
			document.getElementById('text55').style.display = 'none';
			document.getElementById('text56').style.display = 'none';
			document.getElementById('text57').style.display = 'none';
			document.getElementById('text58').style.display = 'none';
			document.getElementById('text59').style.display = 'none';
			document.getElementById('text43').style.display = 'none';
			
		}
}

function openDiv43(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text60').style.display = 'block';
		 document.getElementById('text61').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text60').style.display = 'none';
			document.getElementById('text61').style.display = 'none';
			
			
		}
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


 


 function openDivQ74(val){
		
	 if(val==='Yes')
		 {
		 document.getElementById('q74').style.display = 'block';
		 
		}else{
			document.getElementById('q74').style.display = 'none';
		}
 }
 
 </script>
<!-- color: #8B8B70; -->
<style type="text/css">
	#label {
  	padding:7px 0;
  	color:#4A4A4A;
	font-weight:!important;
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
  margin-top: -21px;
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
  margin-right:56px;
  
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
    padding: 10px 7px 2px 7px;
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
	border-right: 1px solid #fff !important;
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
  	padding:7px 0;
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
	
		<div id="mainDiv">
					<!--<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>-->
					<br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
<!--<h2>Dear <b id="pnameId">*******localhost:8080</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
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
		
	<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		
		
	
	
	<table style="width:870px; border-spacing:0; ">
			
		
		
		</table>
		
		<table style="width:879px;">
			<tr>
			<td align="center"  colspan="2" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b style="font-size: 16px;">Stress</b> </label></td>
					
			</tr>
		
		</table>
		
	<table style="width:435px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:435px;  height:10px; border-spacing: 0px;">
						
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="1. Do you have difficulty in " theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Relaxing" theme="simple"></s:label>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Relaxing':''}" name="307" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Staying calm" theme="simple"></s:label>
			&nbsp;<s:checkboxlist list="#{'Staying calm':''}" name="307" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Letting go" theme="simple"></s:label>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Letting go':''}" name="307" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="2. Are you frequently time pressured and impatient?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="308"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="308"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="3. Are you basically competitive?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="309"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="309"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. Do you prefer to avoid conflicts and avoid discussing any differences &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;at all costs?</label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="310"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="310"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5. Do you find it difficult to give your needs as much importance as &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;others needs?</label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="311"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="311"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td  style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="6. When faced with a problem are you able to plan a solution?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="312"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="312"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7. Are you able to evaluate and accept the merits of ideas different from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;yours and are able to delegate<label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;"  class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="313"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="313"   theme="simple"></s:radio></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="8. Do you have friends, relatives whom you can turn to support for?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="314"   theme="simple"></s:radio></td>
			 <td style="height: 22px;"  class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="314"   theme="simple"></s:radio></td>
			 
		</tr>
		
		
		
			
		<tr>
			<td class="f" ></td>
			<td height="21px" align="center" class="f"></td>
	
		</tr>
		
		
		
		
		
		<tr>
			<td class="c" ></td>
			<td height="21px" align="center" class="c"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:441px;   border-spacing: 0px; padding:1px;">
				
				<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;">9. Are you emotionally / physically compatible with your family members?</label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="315"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="315"   theme="simple"></s:radio></td>
			 
		</tr>
				
				
				<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;"><s:label value="10 Do you have friends, relatives whom you can turn to support for?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="316"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="316"   theme="simple"></s:radio></td>
			 
		</tr>
		
			
		
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;"><s:label value="11. Are you emotionally/Physically compatible with your family members?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="317"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="317"   theme="simple"></s:radio></td>
			 
		</tr>
		
						
							
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;"><s:label value="12. Does your occupation give you sufficient returns and challenge
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="318"   theme="simple"></s:radio></td>
			 <td class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="318"   theme="simple"></s:radio></td>
			 
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;"><s:label value="13. Does your work overburden you and create a work life imbalance?
" theme="simple"></s:label></td>
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="319"   theme="simple"></s:radio></td>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="319"   theme="simple"></s:radio></td>
			 
		</tr>
		
		
		
		
		
		<tr>
		
		 	 <td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;font-weight: bold; margin-left: 13px;">14. Are you currently facing any personal loss or crisis mentioned below?</label></td>
			
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Death of a significant person?" theme="simple"></s:label></td>
			<td style="height: 22px;" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Death of a significant person?':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Major illness of family members" theme="simple"></s:label></td>
			<td style="height: 22px;" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Major illness of Family members':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Seperation from Family / Divorce" theme="simple"></s:label></td>
			<td style="height: 22px;" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Seperation from Family/Divorce':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;"  class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Constant daily hassles" theme="simple"></s:label></td>
			<td style="height: 22px;" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Constant daily hassles':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Major Health Problem" theme="simple"></s:label></td>
			<td style="height: 22px;" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Major Health Problem':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Major Career Problem" theme="simple"></s:label></td>
			<td style="height: 22px;" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Major Career Problem':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Major Legal / Financial Problem" theme="simple"></s:label></td>
			<td style="height: 22px;" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Major Legal/Financial Problem':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Major Family Problem" theme="simple"></s:label></td>
			<td style="height: 22px;" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Major Family Problem':''}" name="320" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="20px" align="center" class="c"></td>
	
		</tr>
		
		
		
		
	</table>
				
				
				</td>
			</tr>
	</table>
	
	
	
	
	<table style="width:900px; border-spacing:0; ">
			
		
		
		</table>
		
		
		<table style="width:877px;">
			<tr>
			<td align="center" colspan="2" class="g"><label id="titlesection" style="color: #fff; ">
 		&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b style="font-size: 16px;">Dependency</b> </label></td>
					
			</tr>
		
		</table>
		
	<table style="width:435px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:435px;  height:10px; border-spacing: 0px;">
					
		<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>1. Do you smoke</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="321"  onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="321"  onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text31" style="display: none; height: 22px;" >
				<label id="labelAns" style="color: #000; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;How many cigarettes do you smoke  daily </label><br>
				
				</div> 
				
				
				
			<td align="center" class="f" colspan="2"> 
			<div id="text32" style="display: none; height: 22px; " >
			<s:textfield name="322" value=""  style="width:125px;" theme="simple"/>
			</div>
			
				</td> 
				
			
			
			
			
			</tr>
			
			
			<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>2. Do you Consume Alcohol?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="323"  onclick="openDiv9(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="323"  onclick="openDiv9(this.value)"  theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td height="20px"; class="f">
			<div id="text38" style="display: none">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">Since when</label><br>
				
				</div> 
				<br>
				
			<div  id="text39" style="display: none">	
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">How frequently do you consume it? 
				</label></div>
				<br>
			<div  id="text40" style="display: none">	
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">Quantity of Pegs (Per week) 
				</label></div> 
				
			<td height="41px"; class="f" colspan="2"> 
			<div id="text41" style="display: none" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield size="10px" name="324" value=""  theme="simple"/>
			</div>
			<br>
			<div id="text42" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield size="10px" name="325" value="" theme="simple"/>
				 </div>
				 <br>
				 
			<div id="text43" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield size="10px" name="326" value="" theme="simple"/>
				 </div>
				 
				</td> 
				
			
			</tr>
			
			<tr>
			<td style="height: 22px;" colspan="2" class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>3. Do you chew Tobacco/Paan products</b></label></td>
			</tr>
			<tr>
			
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="327"   onclick="openDiv42(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="327"  onclick="openDiv42(this.value)"  theme="simple"></s:radio></td>
			
			
		</tr>
		

			<tr>
			<td height="24px"; class="c">
			<div id="text50" style="display: none">
				&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">What is the quantity</label><br>
				
				</div> 
				
				
			
			<td height="20px"; class="c" colspan="2"> 
			
			
	
				 
			<div id="text51" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield size="10px" name="328" value=""  theme="simple"/>
				 </div>
				 
				</td> 
				
			
			</tr>
				
			
			
					
							
			
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:441px;  height:10px; border-spacing: 0px; padding:1px;">
					<tr>
			<td style="height: 22px;" colspan="2" width="255px" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>4. Are you a passive smoker exposed to smokers for long hours? </b> </label></td>
				
		</tr>
		
		<tr>
			
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="329"   onclick="openDiv43(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="329"  onclick="openDiv433(this.value)"  theme="simple"></s:radio></td>
			
			
		</tr>
		
			<tr>
			<td style="height: 22px;" colspan="2"  class="c">
			
				&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;"><b>5. Do you use Narcotic drugs? </b></label><br>
				
				
			</td>	
			</tr>
			
			<tr>
			
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="330"    theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="330"   theme="simple"></s:radio></td>
			
			
		</tr>
			<tr>
			<td style="height: 22px;" width="80px" colspan="2" class="c" > 	
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield size="10px" name="331" value=""  theme="simple"/>	
			</td>
			</tr> 
				
		
	<tr>
			<td class="f" ></td>
			<td height="23px" align="center" class="f"></td>
	
		</tr>
	
	
	<tr>
			<td class="c" ></td>
			<td height="28px" align="center" class="c"></td>
	
		</tr>
	
	<tr>
			<td class="f" ></td>
			<td height="28px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c"></td>
			<td height="28px" align="center" class="c"></td>
	
		</tr>
		
		
	<tr>
			<td class="f">
			<div id="five" style="display: none; height: 24px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="six" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			<tr>
			<td class="c">
			<div id="seven" style="display: none; height: 29px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="eight" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			<tr>
			<td class="f">
			<div id="nine" style="display: none; height: 17px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="ten" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
	
		
	</table>
				
				
				</td>
			</tr>
	</table>
	<br></br><br>
		 <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button">
   <div id="bt" style="display: block;">

  		<sj:a cssStyle=" margin-left: -249px; margin-top: -8px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           button="true"
	           onBeforeTopics="validate2222"
	           cssStyle="   margin-left: -87px; margin-top: -27px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onCompleteTopics="mak"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-50px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top: -50px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
 		<!-- onBeforeTopics="validate2" -->
   </div>
   </div>
   </div>
	
	</table>
	
	
			
		</div>

		<!-- Buttons -->
		
		   <div class="clear"></div>
		   
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
          height="120"
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