<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	 	<script type="text/javascript" src="/over2cloud/testJS/jquery.simplePagination.js"></script>
<link type="text/css" rel="stylesheet" href="/over2cloud/testJS/simplePagination.css"/>
<script type="text/javascript"><!--
$(function() {
	var totalPage=105;

	var type=$("#type").val();	

	if(type === 'M'){
		totalPage=99;
		}
	
    $("#pagesDiv").pagination({
        items: totalPage,
        itemsOnPage: 11,
        cssStyle: 'light-theme',
        onPageClick:function(pageNumber, event) {
    	//alert(pageNumber);
    	nextPage(pageNumber);
		},
		currentPage:4
    });
});

function nextPage(pageNo){
	var traceId=$("#traceid").val();
	var type=$("#type").val();
	var positionPage=pageNo;
	
	if(type === 'M'){
		if(pageNo == 8){
			pageNo=11;	
			}
		else if(pageNo == 9){
			pageNo=12;
			}
		
}
else if(type === 'F'){
if(pageNo == 8){
	pageNo=10;	
	}
else if(pageNo == 9){
	pageNo=11;
	}
else if(pageNo == 10){
		pageNo=12;
	}
 	}
		
		var done=$("#doneStatus").val();

		var pname=$("#pname").val();
		//alert(traceId+type+done+pname+pageNo+'#'+positionPage);
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/patientActivity/viewQuestionnaireForms.action",
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo="+pageNo+"&positionPage="+positionPage+"&done="+done,
			    success : function(subdeptdata) {
				$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		       $("#"+"datapart").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
		});
}



$.subscribe('mak', function(event,data){
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	setTimeout(function(){ $("#complTarget").fadeOut(); },1000);
	 //$('#completionResult').dialog('open');
	 reset('form1');
 });

function backPrevious(val)
{
	var traceId=$("#traceid").val();
	var type=$("#type").val();

		var positionPage=parseInt($("#positionPage").val())-2;
	
	var done=$("#doneStatus").val();

	var pname=$("#pname").val();
			
	$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/patientActivity/viewQuestionnaireForms.action",
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=3&positionPage="+positionPage+"&done="+done,
		    success : function(subdeptdata) {
			$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#"+"datapart").html(subdeptdata);
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


function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
		
}

$.subscribe('validate2', function(event,data)
		{
 for(var i=106 ;i<=117;i++)
	{
		if(i!=107 && i!=109 && i!=110 && i!=111 && i!=113 && i!=114 && i!=115 && i!=116 && i!=117 ){		
			
			if(i==106 || i==108 || i==112 ){		
				$('input[name='+i+']').removeAttr("style");
				//if($('input[name='+i+']:checked').length<=0 && i!=136 && i!=147 && i!=156)
				 if($('input[name='+i+']:checked').length<=0 )
						 {
					 showErrorField();
				     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
				     		$('input[name='+i+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 }else{
							$('input[name='+i+']').removeAttr("style");
							 if($('input[name='+i+']:checked').val() === 'Yes'){
								 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
								 		showErrorField();
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
			 $('input[name='+i+']').removeAttr("style");
                if($('input[name='+i+']:checked').length<=0 && $('input[name='+(i+1)+']').val().trim()=='')
				 {
                	showErrorField();
        			$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
        			$('input[name='+i+']').focus();
	
        			$('input[name='+(i+1)+']').focus();
        			$('input[name='+(i+1)+']').css({"border": "1px solid red","background": "#FFCECE"});
	  				        				
        			event.originalEvent.options.submit = false;
				  	return false;
    			    }
                else{
                	$('input[name='+i+']').removeAttr("style");
                	$('input[name='+(i+1)+']').removeAttr("style");
                    }
    		}
		}
 setTimeout(function(){ $("#errZone").fadeOut(); $("#errZone2").fadeOut(); }, 1000); 	
  });		 



function closePage(){
window.location.href = "http://www.medanta.org/";
}

$( document ).ready(function()
{	
	$("textarea").blur(function(){
	 $(this).removeAttr("style");
});
	
	$("input:text").blur(function(){
	    $(this).css("box-shadow","0 0 0px 0px red inset");
	});
	
$("#115").click(function() {
		
		if($('#115:checked').val() === 'None'){
	        for(var i=1;i<4;i++){
	        	$('#116'+i).prop('checked', false);
	        	$('#116'+i).attr('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<5;i++){
	         	$('#116'+i).prop('checked', false);
	         	$('#116'+i).removeAttr('disabled');
	             }
	        }
	});


	$("#109").click(function() {
		
		if($('#109:checked').val() === 'None'){
	        for(var i=1;i<6;i++){
	        	$('#110'+i).prop('checked', false);
	        	$('#110'+i).attr('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<6;i++){
	         	$('#110'+i).prop('checked', false);
	         	$('#110'+i).removeAttr('disabled');
	             }
	        }
	});

	$("#113").click(function() {
		
		if($('#113:checked').val() === 'None'){
	        for(var i=1;i<8;i++){
	        	$('#114'+i).prop('checked', false);
	        	$('#114'+i).attr('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<8;i++){
	         	$('#114'+i).prop('checked', false);
	         	$('#114'+i).removeAttr('disabled');
	             }
	        }
	});	
			$("input:radio").change( function() {
		$(this).publish('validate2', this, event);
	});

	$("input:checkbox").change( function() {
		$(this).publish('validate2', this, event);
	});
		
	
 
var traceId=$("#traceid").val();
var type=$("#type").val();	
//alert(traceId+traceId);

var positionPage=parseInt($("#positionPage").val())+1;
$("#positionPage").val(positionPage);

if(type === 'M'){
	$("#totalpages").text("9");
	}
	
   	$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A3",
		    success : function(subdeptdata) 
		    {
		    	
		    	$.each( subdeptdata, function( key, value )
		    	{
		    		var values = value.split(",");
		    		 value = values[0];
if(key === '106'){
	openDiv7(value);
}
else if(key === '108'){
	openDiv8(value);	
}else if(key === '112'){
	openDiv9(value);
}
			    	
for(a=0;a<values.length;a++){
	$('input[name="' + key.trim()+ '"][value="' + values[a].trim() + '"]').prop("checked", true);
}
		    		
			    	$('input:text[name="'+key+'"]').val(values[0]);
		    	});
				$('#update').val("update");
		    },
		   error: function() {
	            alert("error");
	        }
		 });
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


function reset(formId) 
{
	$('#'+formId).trigger("reset");
		openDiv7("No");
		openDiv8("No");
		openDiv9("No");
		for(var i=1;i<5;i++){
         	$('#116'+i).prop('checked', false);
         	$('#116'+i).removeAttr('disabled');
             }

		 for(var i=1;i<8;i++){
	         	$('#114'+i).prop('checked', false);
	         	$('#114'+i).removeAttr('disabled');
	             }

		 for(var i=1;i<6;i++){
	         	$('#110'+i).prop('checked', false);
	         	$('#110'+i).removeAttr('disabled');
	             }
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
		
		 document.getElementById('text30').style.display = 'block';
		
		 
		}else{
			
			document.getElementById('text30').style.display = 'none';
			
			document.getElementById("field1").value = "";
		}
}

function openDiv8(val){
	
	 if(val==='Yes')
		 {
		
		 document.getElementById('text32').style.display = 'block';
		
		 
		}else{
			
			document.getElementById('text32').style.display = 'none';
			
			document.getElementById("field2").value = "";
		}
}

function openDiv9(val){
	
	 if(val==='Yes')
		 {
		 
		 document.getElementById('text34').style.display = 'block';
		 
		}else{
			
			document.getElementById('text34').style.display = 'none';
			
			 document.getElementById("field3").value = "";
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
::-webkit-input-placeholder {
 font-size: 14px;
 color: #6E6E6E;
 
 text-align: center;
 font-weight: bold;
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
  background-color: #ff4719;
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
					<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					
	<div id="innerDiv2" >
	<div id="formTitle" style="   margin-top: -7px;display: none" >
		<h2>Dear <b id="pnameId"><s:property value="%{pname}" /></b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	</div>
	<div id="formDiv">
	
						
<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr style="float: right;">
	<td>
	<td style="background-color: bisque; padding: 0px 0px; width: 70%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 20%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 82px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 13px;"  id="totalpages">10</div></label>
	</td>
	<td>
	
	<div class="progress">
	   <s:set id="done" var="doneVar" value="%{done}"/>
	<div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${doneVar}%" >
   
      <div id="complete"><label id="labelAns" style="color: #fff; "><s:property  value="%{done}"/>%&nbsp;Complete</label></div>
    </div>
    </div>
	</td>
	
	</tr>
	</table>
	<center>						<div
								style="float: right; border-color: black; background-color: #FF6666; color: white; font-family: Arial; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: right; margin-left: 7px;"></div>
							</div>
						</center>						
						
						
						
	<s:form id="form1" action="submitFeedbackNew" namespace="/view/Over2Cloud/patientActivity" method="post" enctype="multipart/form-data">
		
	<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{#parameters.traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		
		<s:hidden id="pageNo" name="pageNo" value="5"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="40" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p3," name="page" />
		
		
		<!-- questions -->
		
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		<table style="width:878px; border-spacing:0; height:20px;">
			<tr>
			<td align="center" width="878px" style="background-color: #CC66FF">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " >Past Illness</label></td>
			
		</tr>
		
		
		</table>
		
		
	<table style="width:871px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:425px;  height:10px; border-spacing: 0px;">
		<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any medical illness  or hospitalisation in the past? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="106"  title="Very low" onclick="openDiv7(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
				<div id="text30" style="display: none" >
			<s:textfield name="107" value="" id="field1" placeholder="Enter Details" style="text-align: left;" theme="simple" />
			</div>
			
			</td>
		</tr>
		
		<tr>
			
			<td  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="106"  onclick="openDiv7(this.value)" cssStyle="margin-left: 7px;" title="Very low" theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
			<tr>
			<td width="380px" colspan="2" class="f" height="18px"></td>
				
		</tr>
			
				
			
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any Accident /Surgery in the past? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="108"  title="Any Accident /Surgery in the past?" onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			<div id="text32" style="display: none" >
			<s:textfield name="109" value="" placeholder="Enter Details" style="text-align: left;"  id="field2" theme="simple" />
			</div>
			
			
			
			</td>
		</tr>
		
		
		
		<tr>
			
			<td  width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="108" cssStyle="margin-left: 7px;"  onclick="openDiv8(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		
			
			<tr>
			<td width="380px" colspan="2" class="f" height="18px"></td>
				
		</tr>
	
	<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Do you currently have cancer or have you had cancer in the past? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="112"  title="Very low" onclick="openDiv9(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			<div id="text34" style="display: none" >
			<s:textfield name="113" value="" placeholder="Enter Details" style="text-align: left;"  id="field3" theme="simple" />
			</div>
			
			
			</td>
			
		</tr>
		
		<tr>
			
			<td  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="112" cssStyle="margin-left: 7px;" onclick="openDiv9(this.value)" title="Very low" theme="simple"></s:radio></td>
			<td class="c" align="center">
			
		</tr>
		
		<tr>
			<td width="380px" colspan="2" class="f" height="18px"></td>
				
		</tr>
		
		
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any difficulty in exercising partially or completely due to? *</b> </label></td>
				
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="No Difficulty" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="109" value="None" name="110" /></td>
		</tr>
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Orthopaedic Impairment" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1101" value="Orthopaedic Impairment" name="110" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Muscular Dysfunction" theme="simple"></s:label></label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1102" value="Muscular Dysfunction" name="110" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Paralysis /Stroke" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1103" value="Paralysis /Stroke" name="110" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Polio" theme="simple"></s:label></label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1104" value="Polio" name="110" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Amputation of Limbs" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1105"  value="Amputation of Limbs" name="110" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td  class="f" align="center"><s:textfield name="111" value="" theme="simple" style="width:150px;margin-right: 35px;"/></td>
			 
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="27px" align="center" class="c"></td>
	
		</tr>
	
		
		
		
		
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:450px; height:10px; border-spacing: 0px">
						
			
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any history of frequent Exposures to the following? *</b></label> </td>
				
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="113" value="None" name="114" /></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Gasoline" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1141" value="Gasoline" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Radiation" theme="simple"></s:label></label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1142" value="Radiation" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Dust" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1143" value="Dust" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Fumes" theme="simple"></s:label></label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1144" value="Fumes" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Paint" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1145" value="Paint" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Industrial Chemicals" theme="simple"></s:label></label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1146" value="Industrial Chemicals" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sharp Sun-rays" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1147" value="Sharp Sun-rays" name="114" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td  class="f" align="center"><s:textfield name="115" value="" theme="simple" cssStyle="width:150px;margin-left: 6px;margin-right: 32px;"/></td>
			 
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="17px" align="center" class="c"></td>

		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="18px" align="center" class="f"></td>

		</tr>
		
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Any Drug Allergies? *</b> </label></td>
				
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="115" value="None" name="116" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pencillins" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1161" value="Pencillins" name="116" /></td>
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sulfa" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1162" value="Sulfa" name="116" /></td>
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Painkillers" theme="simple"></s:label></td>
			 <td  class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="1163" value="Painkillers" name="116" /></td>
			 
		</tr>
		
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Any Other Drugs" theme="simple"></s:label></td>
			 <td  class="c" align="center"><s:textfield name="117" value="" theme="simple" style="width:150px;margin-left: 6px;margin-right: 32px;"/></td>
			 
		</tr>
		
	
		
		<tr>
			<td class="f" ></td>
			<td height="25px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="25px" align="center" class="c"></td>

		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="25px" align="center" class="f"></td>

		</tr>
		
		
		
		

					</table>
				
				
				</td>
			</tr>
			
			<tr>
			<td colspan="3" align="right">
				<label id="labelAns"><b style="font-style: italic;">*Are Mandatory Questions</b></label> 
			</td>
		
		</tr>
	</table>
	
	
	
	
	
	
	<center>						<div
								style="float: right; border-color: black; background-color: #FF6666; color: white; font-family: Arial; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone2" style="float: right; margin-left: 7px;"></div>
							</div>
						</center>	
		<center> <div id="pagesDiv" style="margin-left: 267px;" > </div> </center>				
	<br>
	
	<div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
   <div id="bt" style="display: block;margin-left: 158px;display: block;">
  		<sj:a cssStyle="   margin-left: -337px; margin-top: -77px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
  		<sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="Next >>" 
	           button="true"
	           cssStyle=" margin-left: -176px; margin-top: -76px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; "
	           indicator="indicator1"
	           onBeforeTopics="validate2"
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