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

<script type="text/javascript">
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
		currentPage:currentPage
    });
});

function nextPage(pageNo){
	var traceId=$("#traceid").val();
	var type=$("#type").val();
	var positionPage=pageNo;
	
	if(type === 'M'){
				if(pageNo == 8){
					pageNo=8;	
					}
				else if(pageNo == 9){
					pageNo=11;
					}
				else if(pageNo == 10){
						pageNo=12;
					}
		}
	else if(type === 'F'){
		if(pageNo == 8){
			pageNo=9;	
			}
		else if(pageNo == 9){
			pageNo=10;
			}
		else if(pageNo == 10){
				pageNo=11;
			}
		else if(pageNo == 11){
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
			setTimeout(function(){ $("#complTarget").fadeOut(); },2000);
			 //$('#completionResult').dialog('open');
			 reset('form1');
		  });
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}

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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=7&positionPage="+positionPage+"&done="+done,
		    success : function(subdeptdata) {
			$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#"+"datapart").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
	});

}

$( document ).ready(function()
		{

	$("input:radio").blur(function(){
        $(this).removeAttr("style");
    });					

	$("input").blur(function(){
        $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
    });	
	var traceId=$("#traceid").val();
	var type=$("#type").val();	
	//alert(traceId+traceId);

	var positionPage=parseInt($("#positionPage").val())+1;
	$("#positionPage").val(positionPage);

		    	if(type === 'M'){
					$("#totalpages").text("10");
					}
		    	
		    	$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A6",
				    success : function(subdeptdata) 
				    {
					
				    	 var x = JSON.stringify(subdeptdata);
					    	//alert(x);
					    	//alert(x.length);
					     	if(x.length>0){
					    	$.each( subdeptdata, function( key, value )
					    	{
					    		var values = value.split(",");
					    		 value = values[0];
					    		
					    		//console.log(values+"  #key:"+key);
						    	for(a=0;a<values.length;a++){
						    		console.log(">>  "+values[a]);
						    		$('input[name="' + key.trim()+ '"][value="' + values[a].trim() + '"]').prop("checked", true);
						    	}
					    		
					    		$('input:text[name="'+key+'"]').val(values[0]);
					    	});
							$('#update').val("update");
					    	}
				    },
				   error: function() {
			            alert("error");
			        }
				 });
		});


function reset(formId) 
{
	$('#'+formId).trigger("reset");
	}

$.subscribe('validate2', function(event,data)
		{
 for(var i=401 ;i<=405;i++)
	{
	 
 
 
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
  });



function sumUp(){
	var total=0;
	var temp='0';
	for(var i=401;i<=405;i++)
	{			
		if( $('input[name='+i+']:checked').val() != null && $('input[name='+i+']:checked').val() != 'undefined' ){
			temp=$('input[name='+i+']:checked').val();
			total+=parseInt(temp);
			}
	}
	$('input[name=406]').val(total);
}

</script>
<style type="text/css">
/*  
input[type='radio'] {
  -webkit-appearance:none;
  width:20px;
  height:20px;
  border:1px solid darkgray;
  border-radius:50%;
  outline:none;
  box-shadow:0 0 5px 0px gray inset;
}


input[type='radio']:hover {
  box-shadow:0 0 13px 0px white inset;
  webkit-appearance:none;
}

input[type='radio']:before {
  content:'';
  display:block;
  width:60%;
  height:60%;
  margin: 20% auto;    
  border-radius:50%;    
}   */


input[type='radio']:checked:after {
  background:yellow !important;
   border:1px solid yellow !important;
  border-radius:50%;
}


</style>

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
  margin-right:56px;
  
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
	width:400px;
	}
	
	td.g
	{
	background-color: #FF6C47; 
	
	}
	
	td.h
	{
	background-color: #CCCCFF; 
	
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
<body style=" #ECECEC width: 1295px;">
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 128px;width: 1095px;height: 637px;">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none" >
	<h2>Dear <b id="pnameId">******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	</div>
	<div id="formDiv">
	<center>
						<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>
						
	<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr >
	<td style="background-color: bisque; padding: 0px 0px; width: 70%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 20%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 114px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 13px;"  id="totalpages">11</div></label>
	</td>
	<td>
	
	<div class="progress">
	   <s:set id="done" var="doneVar" value="%{done}"/>
	<div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${doneVar}%" >
      <div id="complete" style="margin-left: 7px;"> <s:property  value="%{done}"/><label id="labelAns" style="color: #fff; " >%&nbsp;Complete</label></div>
    </div>
    </div>
	</td>
	
	</tr>
	</table>				
						
	<s:form id="form1" action="submitFeedbackNew" namespace="/view/Over2Cloud/patientActivity" method="post" enctype="multipart/form-data">
<s:hidden id="pname" value="%{pname}"></s:hidden>
	<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{#parameters.traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		<s:hidden id="pageNo" name="pageNo" value="11"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="80" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<!-- questions -->
		
		<div id="questions" style=" "border: thin; margin-left:46px; clear: both;">
		<table style="width:100%; ">
					
			<tr>
				<td  align="center" class="a" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; ">
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff; font-size: 16px;" ><b>Sexual  Health Inventory For Men</b> </label>
		</td>
		</tr>
		</table>
		
		<table style="width:100%; ">
					
			<tr>
				<td  >
					<table style=" width:100%; border-spacing: 0px;">
					
					<tr>
							<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff; height: 36px"  colspan="2" class="g" >
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>1. How do you rate your confidence that you could get and keep an erection?</b> </label></td>
							
							
						</tr>
						
							
							
							
						
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Very Low</label></td>
							
							<td class="c" style=" width:10%;"  ><s:radio list="#{'1':''}"  title="Very low" name="401" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="f"  >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Low</label></td>
							
							<td class="f"  ><s:radio list="#{'2':''}" title="Low" name="401" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="c" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Moderate</label></td>
							
							<td class="c"   ><s:radio list="#{'3':''}" title="Moderate" name="401" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="f"  >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;High</label></td>
							
							<td class="f" ><s:radio list="#{'4':''}" title="High" name="401" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="c" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Very High</label></td>
							
							<td class="c"  ><s:radio list="#{'5':''}" title="Very High" name="401" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="f" ></td>
							<td height="21px" align="center" class="f"></td>
					</tr>
					
						<tr>
							<td class="c" ></td>
							<td height="18px" align="center" class="c"></td>
					</tr>
					
					
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:100%; border-spacing: 0px;">
				
				<tr>
							<td  style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" colspan="2" class="g">
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>2. When you had erections with sexual stimulation, how often were your erections hard &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;enough for penetration (entering your partner)?</b> </label></td>
							
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No Sexual Acitivity</label></td>
							<td class="c" style=" width:10%;"><s:radio list="#{'0':''}" onchange="sumUp();" title="No sexual acitivity" name="402"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
							<td class="f"  >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost Never or Never</label></td>
							<td class="f" ><s:radio list="#{'1':''}" onchange="sumUp();" title="Almost never or never" name="402"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
							<td class="c"  >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A Few Times</label></td>
							<td class="c" ><s:radio list="#{'2':''}" title="A few times" name="402" onchange="sumUp();"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
							<td class="f"  >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes</label></td>
							<td class="f" ><s:radio list="#{'3':''}" title="Sometimes" name="402" onchange="sumUp();"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most Times</label></td>
							<td class="c" style=" width:10%;"><s:radio list="#{'4':''}" title="Most times" name="402" onchange="sumUp();"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
							<td class="f" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost Always or Always</label></td>
							<td class="f" ><s:radio list="#{'5':''}" title="Almost always or always" name="402" onchange="sumUp();"  theme="simple"></s:radio></td>
							
						</tr>
						
							<tr>
							<td class="c" ></td>
							<td height="20px" align="center" class="c"></td>
					</tr>
						
						
						
					</table>
					
					
					
				
				
				</td>
			</tr>
			
			
			
			
			
			<tr>
				<td  >
					<table style=" width:100%; border-spacing: 0px;">
					
					<tr>
							<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" colspan="2" class="g">
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>3. During sexual intercourse, how often were you able to maintain your erection &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;after you had penetrated (entered) your partner?</b> </label></td>
							
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;Did Not Attempt Intercourse</label></td>
							
							<td class="c" style=" width:10%;"  ><s:radio list="#{'0':''}" title="Did not attempt intercourse" name="403" onchange="sumUp();"  theme="simple"></s:radio></td>
						</tr>
						
						<tr>
							<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes</label></td>
							<td class="f" align="center"><s:radio list="#{'1':''}" title="Sometimes" name="403" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
		
						<tr>
							<td  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most Times </label></td>
							<td class="c" align="center"><s:radio list="#{'2':''}" title="Most times (much more than half the time)" name="403" onchange="sumUp();"  theme="simple"></s:radio></td>
						</tr>	
		 
						<tr>
							<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost Always or Always</label></td>
							<td class="f" align="center"><s:radio list="#{'3':''}" title="Almost always or always" name="403" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>

						
						<tr>
							<td class="c"></td>
							<td height="18px" align="center" class="c"></td>
					</tr>
					
						<tr>
							<td class="f" ></td>
							<td height="17px" align="center" class="f"></td>
					</tr>
					
					
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:100%; border-spacing: 0px;">
				
				<tr>
							<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" colspan="2" class="g">
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>4. During sexual intercourse, how difficult was it to maintain your erection to completion &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of intercourse?</b> </label></td>
							
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Did Not Attempt Intercourse</label></td>
							<td class="c" style=" width:10%;"><s:radio list="#{'0':''}" title="Did not attempt intercourse" name="404" onchange="sumUp();"  theme="simple"></s:radio></td>
							
						</tr>
						
						<tr>
						<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Extremely Difficult</label></td>
						<td class="f" align="center"><s:radio list="#{'1':''}" title="Extremely difficult" name="404" onchange="sumUp();"  theme="simple"></s:radio></td>
						</tr>
		
						<tr>
						<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Very Difficult</label></td>
						<td class="c" align="center"><s:radio list="#{'2':''}" title="Very difficult" name="404" onchange="sumUp();"  theme="simple"></s:radio></td>
			
						</tr>	
		 
						<tr>
						<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Difficult</label></td>
						<td class="f" align="center"><s:radio list="#{'3':''}" title="Difficult" name="404" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>

						<tr>
						<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Slightly Difficult</label></td>
						<td class="c" align="center"><s:radio list="#{'4':''}" title="Slightly difficult" name="404" onchange="sumUp();" theme="simple"></s:radio></td>
						</tr>
		
						<tr>
						<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not Difficult</label></td>
						<td class="f" align="center"><s:radio list="#{'5':''}" title="Not difficult" name="404" onchange="sumUp();"  theme="simple"></s:radio></td>
						</tr>
					</table>
					
					
					
				
				
				</td>
			</tr>
			
			
			<tr>
				<td  >
					<table style=" width:100%; border-spacing: 0px;">
					
					<tr>
							<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" colspan="2" class="g" >
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>5. When you attempted sexual intercourse, how often was it satisfactory for you?</b> </label></td>
							
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Did Not Attempt Intercourse</label></td>
							
							<td class="c" style=" width:10%;"  ><s:radio list="#{'0':''}" title="Did not attempt intercourse" name="405" onchange="sumUp();"  theme="simple"></s:radio></td>
						</tr>
						
						<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost Never or Never</label></td>
			<td class="f" align="center"><s:radio list="#{'1':''}" title="Almost never or never" name="405" onchange="sumUp();"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A Few Times</label></td>
			<td class="c" align="center"><s:radio list="#{'2':''}" title="A few times" name="405" onchange="sumUp();"  theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes</label></td>
			<td class="f" align="center"><s:radio list="#{'3':''}" title="Sometimes" name="405" onchange="sumUp();"  theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most Times</label></td>
			<td class="c" align="center"><s:radio list="#{'4':''}" title="Most times" name="405" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost Always or Always</label></td>
			<td class="f" align="center"><s:radio list="#{'5':''}" title="Almost always or always" name="405" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
					
						<tr>
							<td class="c" ></td>
							<td height="15px" align="center" class="c"></td>
					</tr>
					
					
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:100%; border-spacing: 0px;">
				
				<tr>
							<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" colspan="2" class="g" >
							&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b></b> </label></td>
							
							
						</tr>
						
						<tr>
							<td class="c" style=" width:90%;" >&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
							<td class="c" style=" width:10%;"></td>
							
						</tr>
						
						<tr>
						<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
						<td class="f" align="center"></td>
						</tr>
		
						<tr>
						<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
						<td class="c" align="center"></td>
			
						</tr>	
		 
						<tr>
						<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
						<td class="f" align="center"></td>
						</tr>

						<tr>
						<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
						<td class="c" align="center"></td>
						</tr>
		
						<tr>
							<td class="f" border-right: 1px solid #fff !important;"></td>
							<td height="13px" align="center" class="f"></td>
					</tr>
					<tr>
							<td class="c" border-right: 1px solid #fff !important;"></td>
							<td height="15px" align="center" class="c"></td>
					</tr>
					
					<tr>
							<td class="f" border-right: 1px solid #fff !important;"></td>
							<td height="13px" align="center" class="f"></td>
					</tr>
						
						
					</table>
					
					
					
				
				
				</td>
			</tr>
			
		</table>
		
		<!--2nd Part-->
		
		<div  style="display: none;" >
			<td class="e"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Score:</label></td>
			<td class="e"><s:textfield value="0" title="Total Score" name="406" cssStyle="  width: 3%;"  readonly="true" theme="simple"></s:textfield></td>
		</div>	
		<center> <div id="pagesDiv" style="margin-left: 328px;" > </div> </center>
		
		
		<!-- Buttons -->
		<br>
		   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
   <div id="bt" style="display: block;">
   
      <sj:a cssStyle=" margin-left: -249px; margin-top: -77px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="Next >>" 
	           button="true"
	           cssStyle="   margin-left: -87px; margin-top: -77px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-147px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top:-147px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
 		<!-- onBeforeTopics="validate2" -->
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
          width="300"
          height="150"
          draggable="true"
    	  resizable="true"
    	    
          >
       	<center><div id="label"><div id="alertDiv">Please Select Any One</div></div></center>	   
</sj:dialog>
			</s:form>
			<div id="complTarget"></div>	
			</div>
		
		</div>	
</div>
</body>

</html>