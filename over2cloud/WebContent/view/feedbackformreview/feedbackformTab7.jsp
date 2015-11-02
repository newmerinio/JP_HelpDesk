<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">$.subscribe('mak', function(event,data){
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
		setTimeout(function(){ $("#complTarget").fadeOut(); nextPage();},2000);
		 reset('form1');
	  });
	 
	 function cancelButton()
	 {
		 $('#completionResult').dialog('close');
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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=7&positionPage="+positionPage+"&done="+done,
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
for(var i=501 ;i<=506;i++)
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
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=10&positionPage="+positionPage+"&done=80",
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


function sumUp(){
	var total=0;
	var temp='0';
	for(var i=501;i<=506;i++)
	{			
		if( $('input[name='+i+']:checked').val() != null && $('input[name='+i+']:checked').val() != 'undefined' ){
			temp=$('input[name='+i+']:checked').val();
			total+=parseInt(temp);
			}
	}
	$('input[name='+507+']').val(total);
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
		
		<table style="width:1029px; height:20px;">
			<tr>
			<td width="1060px" class="g" align="center" style="background-color: #CC66FF">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; ">WOMEN SEXUAL FUNCTION</label></td>
			
		</tr>
		
		
		</table>
		
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:500px;  height:10px; border-spacing: 0px;">
					<tr>
			<td style="height: 22px;" width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>1. Degree of sexual interest or desire</b> </label></td>
			<td class="g"></td>		
		</tr>
					
							
		<tr>
			<td style="height: 22px;" class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never</label></td>
			<td width="120px" class="c" align="center"><s:radio list="#{'1':''}" onchange="sumUp();" title="(Degree of sexual interest or desire) Almost never or never" name="501"  theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;"  class="f">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" onchange="sumUp();" title="(Degree of sexual interest or desire) A few times (less than half the time)" name="501"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;"  class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time)</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" onchange="sumUp();" title="(Degree of sexual interest or desire) Sometimes (about half the time)" name="501"  theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td style="height: 22px;"  class="f">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" onchange="sumUp();" title="(Degree of sexual interest or desire) Most times (more than half the time)" name="501"  theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;"  class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always</label></td>
			<td class="c" align="center"><s:radio list="#{'5':''}" onchange="sumUp();" title="(Degree of sexual interest or desire) Almost always or always" name="501"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:526px; height:10px; border-spacing: 0px; padding: 1px 1px 1px 1px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>2. Level of Sexual Arousal during Sexual Activity</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never</label></td>
			<td class="c" align="center"><s:radio list="#{'1':''}" title="(Level of Sexual Arousal during Sexual Activity) Almost never or never" name="502" onchange="sumUp();" theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;" class="f">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" title="(Level of Sexual Arousal during Sexual Activity) A few times (less than half the time)" name="502" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time)</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" title="(Level of Sexual Arousal during Sexual Activity) Sometimes (about half the time)" name="502" onchange="sumUp();"  theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td style="height: 22px;" class="f">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" title="(Level of Sexual Arousal during Sexual Activity) Most times (more than half the time)" name="502" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;" class="c">&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always</label></td>
			<td class="c" align="center"><s:radio list="#{'5':''}" title="(Level of Sexual Arousal during Sexual Activity) Almost always or always" name="502" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
<tr>
			<td class="f" ></td>
			<td height="19px" align="center" class="f"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:500px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="height: 22px;" width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>3. Natural Lubrication During Sex</b> </label></td>
			<td class="g"></td>		
		</tr>
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never</label></td>
			<td class="c" align="center"><s:radio list="#{'1':''}" title="(Natural Lubrication During Sex) Almost never or never" onchange="sumUp();" name="503"  theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" title="(Natural Lubrication During Sex) A few times (less than half the time)" onchange="sumUp();" name="503"  theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;"  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time)</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" title="(Natural Lubrication During Sex) Sometimes (about half the time)" name="503" onchange="sumUp();"  theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" title="(Natural Lubrication During Sex) Most times (more than half the time)" name="503" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always</label></td>
			<td class="c" align="center"><s:radio list="#{'Yes':''}" title="(Natural Lubrication During Sex) Almost always or always" name="503" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:526px; height:10px; border-spacing: 0px; padding: 1px 1px 1px 1px;">
						<tr>
			<td style="height: 22px;" width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>4. Frequency of Orgasm</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never</label></td>
			<td class="c" align="center"><s:radio list="#{'1':''}" title="(Frequency of Orgasm) Almost never or never" name="504" onchange="sumUp();" theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time)</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" title="(Frequency of Orgasm) A few times (less than half the time)" name="504" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time):</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" title="(Frequency of Orgasm) Sometimes (about half the time)" name="504" onchange="sumUp();" theme="simple"></s:radio></td>
			
		</tr>	
		 
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time):</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" title="(Frequency of Orgasm) Most times (more than half the time)" name="504" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always:</label></td>
			<td class="c" align="center"><s:radio list="#{'5':''}" title="(Frequency of Orgasm) Almost always or always" name="504" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		
<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:500px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="height: 22px;" width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>5. Satisfaction with your overall sex life</b> </label></td>
			<td class="g"></td>		
		</tr>
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never:</label></td>
			<td class="c" align="center"><s:radio list="#{'1':''}" title="(Satisfaction with your overall sex life) Almost never or never" name="505" onchange="sumUp();" theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time):</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" title="(Satisfaction with your overall sex life) A few times (less than half the time)" name="505" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time):</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" title="(Satisfaction with your overall sex life) Sometimes (about half the time)" name="505" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time):</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" title="(Satisfaction with your overall sex life) Most times (more than half the time)" name="505" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always:</label></td>
			<td class="c" align="center"><s:radio list="#{'5':''}" title="(Satisfaction with your overall sex life) Almost always or always" name="505" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		<tr>
			<td style="height: 22px;" class="f" border-right: 1px solid #fff !important;"></td>
			<td height="15px" align="center" class="f"></td>
		</tr>
				
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:526px; height:10px; border-spacing: 0px; padding: 1px 1px 1px 1px;">
						<tr>
			<td style="height: 22px;" width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>6. Discomfort or Pain during Sex</b> </label></td>
			<td class="g"></td>		
		</tr>
		
			
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost never or never:</label></td>
			<td class="c" align="center"><s:radio list="#{'1':''}" title="(Discomfort or Pain during Sex) Almost never or never" name="506" onchange="sumUp();" theme="simple"></s:radio></td>
			
		</tr>	
		
		<tr>
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A few times (less than half the time):</label></td>
			<td class="f" align="center"><s:radio list="#{'2':''}" title="(Discomfort or Pain during Sex) A few times (less than half the time)" name="506" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sometimes (about half the time):</label></td>
			<td class="c" align="center"><s:radio list="#{'3':''}" title="(Discomfort or Pain during Sex) Sometimes (about half the time)" name="506" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>	
		 
		<tr>
			<td style="height: 22px;"  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most times (more than half the time):</label></td>
			<td class="f" align="center"><s:radio list="#{'4':''}" title="(Discomfort or Pain during Sex) Most times (more than half the time)" name="506" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>

		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Almost always or always:</label></td>
			<td class="c" align="center"><s:radio list="#{'5':''}" title="(Discomfort or Pain during Sex) Almost always or always" name="506" onchange="sumUp();" theme="simple"></s:radio></td>
		</tr>
		
		
<tr>
			<td class="f" ></td>
			<td height="21px" align="center" class="f"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
<center>
<label id="label"><b>A score of 19 or less indicated a Likelihood of FSD. Remember that the test is not a diagnosis</b></label></center>
	
	
	
	<tr>
			<td class="e"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Score:</label></td>
			<td class="e"><s:textfield value="0" title="Total Score" name="507" cssStyle="  width: 3%;"  readonly="true" theme="simple"></s:textfield></td>
		</tr>	
	
		<br>
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
	           onBeforeTopics="validate22"
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
       	<center><div id="label"><div id="alertDiv">Please Select Any One</div></div></center>   
</sj:dialog>
			</s:form>
			</div>
		<div id="complTarget"></div>	
		</div>	
</div>
</body>

</html>