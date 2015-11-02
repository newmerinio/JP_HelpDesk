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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=2&positionPage="+positionPage+"&done="+done,
		    success : function(subdeptdata) {
			$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#"+"reviewForms").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
	});
	
}
$.subscribe('validate2', function(event,data)
		{
 for(var i=67 ;i<=86;i++)
	{

	  if(i == 67 || i == 69 || i == 71 || i == 73  || i == 75 || i == 77) {
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

	  if(i == 71) {
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
           else if($('input[name='+i+']:checked').val() === 'Food allergy'){
					if(($('input[name=113]').val()).trim() === ''){
					 	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
					  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
			     		$('input[name=113]').css({"border": "1px solid red","background": "#FFCECE"});
			     		$('input[name=113]').focus();
					  	event.originalEvent.options.submit = false;
					  	return false;
					 	}
			 }
					 else{
           	$('input[name='+i+']').css("#FFFFFF","0 0 13px 0px white inset");
   			$('input[name='+i+']').focus();
	
   			$('input[name='+(i+1)+']').focus();
   			$('input[name='+(i+1)+']').css({"border": "1px solid white","background": "#FFFFFF"});
               }
		}
		
	  else	if(i!=67 && i!=68 && i!=69 && i!=70 && i!=71 && i!=72 && i!=73 && i!=74 && i!=75 && i!=76 && i!=77 && i!=78 && i!=80 && i!=81 && i!=83 && i!=84  && i!=85 ){					 
			if( i==79 || i==82 ){
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
			 else{
					 
					 if($('input[name='+i+']:checked').val() === 'Yes'){
						 	
							for(var j=1;j<3;j++){
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
							if(i==82){
								if($('input[name=85]:checked').length<=0 )
								 {
								  	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
				       				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				       				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
						     		$('input[name=85]').css("box-shadow","0 0 13px 0px red inset");
						     		$('input[name=85]').focus();
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
				}
			
			else if(i== 86 && $('input[name='+i+']').val() === ""){
		 			errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
    				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
    				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					$('input[name='+i+']').focus();
	  				$('input[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
	  				event.originalEvent.options.submit = false;
	  				return false;
			 		}
	 		
			}	
		}
 });
<%----%>
$( document ).ready(function()
		{
	$("textarea").blur( function() {
		$(this).css( {
			"background-color" : "white",
			"border" : "1px solid rgb(252, 252, 252)"
		});
	});

	$("input:radio").blur( function() {
		$(this).removeAttr("style");
	});

	$("input:text").blur( function() {
		$(this).css( {
			"background-color" : "white",
			"border" : "1px solid rgb(252, 252, 252)"
		});
	});

	$("input:checkbox").blur( function() {
		$(this).css( {
			"background-color" : "white",
			"border" : "1px solid rgb(252, 252, 252)"
		});
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



$.subscribe('mak', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 1000);
			 setTimeout(function(){ $("#complTarget").fadeOut(); nextPage(); }, 1000);
			 reset('form1');
		  });
		 
		 function cancelButton()
		 {
			 $('#completionResult').dialog('close');
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
					    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=4&positionPage="+positionPage+"&done=30",
					    success : function(subdeptdata) {
						$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				       $("#"+"reviewForms").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
				});
}		 
		 
		 
function closePage(){
		window.location.href = "http://www.medanta.org/";
	}		 

function saveForm(id){
	$('#'+id).submit();
}

function reset(formId) 
{
	$('#'+formId).trigger("reset");
}

function openDiv(val){
	alert(val);
	 if(val==='Yes')
		 {
		 document.getElementById('text1').style.display = 'block';
		 
		}
	 
	 else{
			document.getElementById('text1').style.display = 'none';
		}
}

function openDiv1(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text2').style.display = 'block';
		 
		}else{
			document.getElementById('text2').style.display = 'none';
		}
}

function openDiv2(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text3').style.display = 'block';
		 
		}else{
			document.getElementById('text3').style.display = 'none';
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
		 document.getElementById('text25').style.display = 'block';
		 document.getElementById('text24').style.display = 'block';
		 document.getElementById('text26').style.display = 'block';
		 document.getElementById('text27').style.display = 'block';
		 
		}else{
			document.getElementById('text24').style.display = 'none';
			document.getElementById('text25').style.display = 'none';
			document.getElementById('text26').style.display = 'none';
			document.getElementById('text27').style.display = 'none';
		}
}

function openDiv7(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text28').style.display = 'block';
		 document.getElementById('text29').style.display = 'block';
		 document.getElementById('text30').style.display = 'block';
		 document.getElementById('text31').style.display = 'block';
		 document.getElementById('text32').style.display = 'block';
		 document.getElementById('text33').style.display = 'block';
		 
		}else{
			document.getElementById('text28').style.display = 'none';
			document.getElementById('text29').style.display = 'none';
			document.getElementById('text30').style.display = 'none';
			document.getElementById('text31').style.display = 'none';
			document.getElementById('text32').style.display = 'none';
			document.getElementById('text33').style.display = 'none';
		}
}

function openDiv8(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text9').style.display = 'block';
		 
		}else{
			document.getElementById('text9').style.display = 'none';
		}
}

function openDiv9(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text10').style.display = 'block';
		 
		}else{
			document.getElementById('text10').style.display = 'none';
		}
}

function openDiv10(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text11').style.display = 'block';
		 
		}else{
			document.getElementById('text11').style.display = 'none';
		}
}

function openDiv11(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text12').style.display = 'block';
		 
		}else{
			document.getElementById('text12').style.display = 'none';
		}
}

function openDiv12(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text13').style.display = 'block';
		 
		}else{
			document.getElementById('text13').style.display = 'none';
		}
}

function openDiv13(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text14').style.display = 'block';
		 
		}else{
			document.getElementById('text14').style.display = 'none';
		}
}

function openDiv14(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text15').style.display = 'block';
		 
		}else{
			document.getElementById('text15').style.display = 'none';
		}
}

function openDiv15(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text16').style.display = 'block';
		 
		}else{
			document.getElementById('text16').style.display = 'none';
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
    margin-top: -21px
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
  margin-right:56px;
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

<script type="text/javascript">
   function click11(str,div) {
	  // alert($('#abc').is(":checked"))
       if (str=='Food allergy') 
   		 {
 		  	$("#"+div).show();
 		  	$("#"+div+"1").show();
  	      }else {
   		  	$("#"+div).hide();
   		 $("#"+div+"1").hide();
  	      }
   }
      
</script>
</head>
<body>
	<div class="clear"></div>
		<div id="mainDiv">
					<!--<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					--><br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
			<!--<h2>Dear <b id="pnameId">*******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
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
		
		
		
	
	
	<table style="width:900px; border-spacing:0; ">
			
		
		
		</table>
		
	<table style="width:450px;  border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px; border-spacing: 0px; padding: 1px 0px 1px 2px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Respiratory Ailments</b> </label></td>
			<td class="g"></td>		
		</tr>
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="67" theme="simple"/></label></td>
		</tr>				
							
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cough " theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Cough':''}" name="67" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Chronic Cold" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Chronic Cold':''}" name="67" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Breathlessness" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Breathlessness':''}" name="67" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Waking in between sleep " theme="simple"></s:label></td>
			 <td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Blood in sputum':''}" name="67" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="68" value="" size="18px" style="height:20px" theme="simple"/></td>
			
		</tr>
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="21px" align="center" class="c"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px; border-spacing: 0px; padding: 1px 0px 1px 2px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Muscle and Joint Problems</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="69" theme="simple"/></label></td>
		</tr>			
							
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Muscle Pain " theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Muscle Pain':''}" name="69" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Bone and Joint Pain" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Bone and Joint Pain':''}" name="69" theme="simple"/></label></td>
		</tr>
		
		
		 	 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Muscle Weakness" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Muscle Weakness':''}" name="69" theme="simple"/></label></td>
		</tr>
		
		
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
		<td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="70" value="" size="18px" style="height:20px" theme="simple"/></td>
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
			</tr>
	</table>
	
	
	<table style="width:900px; border-spacing:0; padding: 1px 0px 1px 2px; ">
			
		
		
		</table>
		
	<table style="width:450px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px;  border-spacing: 0px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Immune Disorders or Allergies</b> </label></td>
			<td class="g"></td>		
		</tr>
	
	<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="71" theme="simple"/></td>
		</tr>				
							
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Food allergy " theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Food allergy':''}" onclick="click11(this.value,'dvPassport');" id="abc" name="71" theme="simple"/></label></td>
		</tr>
		<tr>
		<td colspan="2" class="c">
		<div id="dvPassport" style="display: none; height: 22px;">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Specify the food item" theme="simple"></s:label>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="113" value="" size="18px" type="text" id="txtPassportNumber" style="height:20px" theme="simple"/></label>
</div>
		</td></tr>
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dust Allergy" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Dust Allergy':''}" name="71" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;"  class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cold Allergies" theme="simple"></s:label></td>
			 <td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Cold Allergies':''}" name="71" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="72" value="" size="18px" style="height:20px" theme="simple"/></td>
			
		</tr>
		<tr>
			<td class="c" ></td>
			<td height="21px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="21px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="21px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="21px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px;  height:10px; border-spacing: 0px; padding: 1px 0px 1px 2px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any Urinary Disturbances</b> </label></td>
			<td class="g"></td>		
		</tr>
					
			<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="73" theme="simple"/></label></td>
		</tr>				
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Frequency" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Frequency':''}" name="73" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Hesitancy" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Hesitancy':''}" name="73" theme="simple"/></label></td>
		</tr>
		<tr>
		
		 	 <td style="height: 22px;"  class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Burning Sensation" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Burning Sensation':''}" name="73" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;"  class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pain" theme="simple"></s:label></td>
			<td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Pain':''}" name="73" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;"  class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Blood in Urine" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Blood in Urine':''}" name="73" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
		<td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="74" value="" size="18px" style="height:20px" theme="simple"/></td>
		</tr>
		
		
		
		<tr>
			<td class="f" ></td>
			<td height="39px" align="center" class="f"></td>
	
		</tr>
		
		
		
		<tr>
		<td colspan="2" class="c">
		<div id="dvPassport1" style="display: none; height: 22px;">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="" theme="simple"></s:label>
   </label>
</div>
		</td></tr>
		
	</table>
				
				
				</td>
			</tr>
	</table>
	
	
	
	
	
		
	<table style="width:450px; height:60px; border-spacing:0; padding: 1px 0px 1px 2px; ">
			<tr>
				<td>
					<table style=" width:450px;  border-spacing: 0px;">
					<tr>
			<td width="255px" colspan="2" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any Problems Associated with Teeth and Gums</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="75" theme="simple"/></label></td>
		</tr>			
							
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Bleeding gums " theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Bleeding gums':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pain in the teeth" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Pain in the teeth':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sensitivity" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Sensitivity':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Carious tooth" theme="simple"></s:label></td>
			 <td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Carious tooth':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Extraction" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Extraction':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Replacement of Teeth" theme="simple"></s:label></td>
			 <td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Replacement of Teeth':''}" name="75" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="76" value="" size="18px" style="height:20px" theme="simple"/></td>
			
		</tr>
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="23px" align="center" class="c"></td>
	
		</tr>
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px;  height:10px; border-spacing: 0px; padding: 1px 0px 1px 2px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Hearing Disturbances</b> </label></td>
			<td class="g"></td>		
		</tr>
					
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="77" theme="simple"/></label></td>
		</tr>			
							
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Difficulty in hearing" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Difficulty in hearing':''}" name="77" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Ringing Sound in the Ears" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Ringing Sound in the Ears':''}" name="77" theme="simple"/></label></td>
		</tr>
		<tr>
		
		 	 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Discharge from the Ears" theme="simple"></s:label></td>
			<td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Discharge from the Ears':''}" name="77" theme="simple"/></label></td>
		</tr>
		
		<tr>
		
		 	 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Giddiness" theme="simple"></s:label></td>
			<td class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Giddiness':''}" name="77" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
		<td class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="78"  value="" size="18px"  theme="simple" style="height:20px;margin-right: 8px;"/></td>
		</tr>
		
		
		
		<tr>
			<td class="c" ></td>
			<td height="21px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="22px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="23px" align="center" class="c"></td>
	
		</tr>
	
	</table>
				
				
				</td>
			</tr>
	</table>
	
	<table style=" width:880px;  height:10px; border-spacing: 0px;">
	<tr>
			<td class="a">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff; " >Excessive Weight Gain or Weight loss in the past 2-3 years:</label></td>
			<td  class="a" colspan="2"><s:radio list="#{'Yes':'','No':''}"  name="79"  onclick="openDiv6(this.value)" theme="simple"></s:radio></td>
			
			</tr>
			<tr>
			<td class="b">
			<div id="text25" style="display: none">
				&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">If Weight Gained Please Mention in Kgs</label><br>
				
				</div> 
				<br>
				
			<div id="text26" style="display: none">	
				&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">If Weight Loss Please Mention in kgs
				</label></div> 
				
			<td class="b" colspan="2"> 
			<div id="text24" style="display: none" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="80" value="" theme="simple"/>
			</div>
			<br>
			<div id="text27" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="81" value="" theme="simple"/>
				 </div>
				</td> 
				
			
			</tr>
			
			
			<tr>
			<td class="a">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff; " >Any Diminution (decreased) vision or visual complaints</label></td>
			<td class="a" colspan="2"><s:radio list="#{'Yes':'','No':''}" name="82"  onclick="openDiv7(this.value)" theme="simple"></s:radio></td>
			</tr>
			<tr>
			<td class="b">
			<div id="text28" style="display: none">
				&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">Decreased Near Vision</label><br>
				
				</div> 
				<br>
				
			<div  id="text29" style="display: none">	
				&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">Decreased Far Vision
				</label></div> 
				
			<div  id="text30" style="display: none">	
				&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;">Corrected
				</label></div> 
				
			<td class="b" colspan="2"> 
			<div id="text31" style="display: none" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="83" value="" theme="simple"/>
			</div>
			<br>
			<div id="text32" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="84" value="" theme="simple"/>
				 </div>
			
			<div id="text33" style="display: none" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':'','No':''}" name="85"  theme="simple"></s:radio></td>
				 </div>
				 
				 
				</td> 
				
			
			</tr>
			
			<tr>
			 <td class="a"><label id="labelAns" style="color: #fff; " >&nbsp;&nbsp;&nbsp;<s:label value="Any Others Complains Please Specify?" theme="simple"></s:label></td>
			 <td class="a" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield value="" name="86" size="30px" theme="simple"/></td>
			
		</tr>
		
		<tr>
			<td class="c" border-right: 1px solid #fff !important;"></td>
			<td height="17px" align="center" class="c"></td>
			<td align="center" class="c">
	
</td>
		</tr>
	
	
	
	</table>
	
	<div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
   <div id="bt" style="display: block;">
  		<sj:a cssStyle=" margin-left: -249px; margin-top: -66px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           button="true"
	         onBeforeTopics="validate2"  
	           cssStyle="   margin-left: -87px; margin-top: -66px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onCompleteTopics="mak"
	           onBeforeTopics="validate222"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-125px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top: -125px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
 		<!-- onBeforeTopics="validate2" -->
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
         <div id="complTarget"></div>
			</s:form>
			</div>
		
		</div>	
</div>
</body>
</html>