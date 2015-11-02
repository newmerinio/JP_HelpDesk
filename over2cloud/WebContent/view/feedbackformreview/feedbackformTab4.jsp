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
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}
$.subscribe('validate2', function(event,data)
		{
 for(var i=118 ;i<=144;i++)
	{
			if( i!=123 && i!=128 && i!=130 && i!=133 && i!=135 && i!=139 && i!=141 && i!=143 && i!=145){	
		 	if( i==122 || i===129 || i===131 || i==132 || i==134 || i==138 || i==140 || i==142 || i==144 ) 
		 		{
		 		 if($('input[name='+i+']:checked').length<=0 )
				 {
				  	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
        			setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        			setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
		     		$('input[name='+i+']').focus();
				  	event.originalEvent.options.submit = false;
				  	return false;
				 }else if(i!=131) {
					 if( $('input[name='+i+']:checked').val() === 'Yes'){
						 	
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
		 	else if(i==124 || i==136 || i==137 ){
				if(($('textarea[name='+i+']').val().trim()) == ""){	
	 			errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
  				$('textarea[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
  				$('textarea[name='+i+']').focus();
  				event.originalEvent.options.submit = false;
  				return false;
		 		}
		 	}
		 	else if(i==118 || i==119 || i==120 || i==121 ){
				if(($('input[name='+i+']').val().trim()) == "" ){	
	 			errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				$('input[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
  				$('input[name='+i+']').focus();
  				event.originalEvent.options.submit = false;
  				return false;
				}

		 		}
	 		
			}
	}
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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=4&positionPage="+positionPage+"&done="+done,
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
		$(this).css({"border": "0px solid red","background": "#FFFFFF"});
	});

		$("input:radio").blur(function(){
			$(this).css({"box-shadow":"0 0 0px 0px red inset"});
		});	

		$("input:text").blur(function(){
			$(this).css({"border": "0px solid red","background": "#FFFFFF"});
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
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=6&positionPage="+positionPage+"&done=50",
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

function openDiv7(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text28').style.display = 'block';
		 document.getElementById('text30').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text28').style.display = 'none';
			document.getElementById('text30').style.display = 'none';
			
		}
}

function openDiv10(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text35').style.display = 'block';
		 document.getElementById('text36').style.display = 'block';
		 document.getElementById('eleven').style.display = 'block';
		 document.getElementById('twelve').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text35').style.display = 'none';
			document.getElementById('text36').style.display = 'none';
			document.getElementById('eleven').style.display = 'none';
			document.getElementById('twelve').style.display = 'none';
			
		}
}

function openDiv9(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text33').style.display = 'block';
		 document.getElementById('text34').style.display = 'block';
		 document.getElementById('nine').style.display = 'block';
		 document.getElementById('ten').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text33').style.display = 'none';
			document.getElementById('text34').style.display = 'none';
			document.getElementById('nine').style.display = 'none';
			document.getElementById('ten').style.display = 'none';
			
		}
}

function openDiv8(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text31').style.display = 'block';
		 document.getElementById('text32').style.display = 'block';
		 document.getElementById('seven').style.display = 'block';
		 document.getElementById('eight').style.display = 'block';
		 
		 
		}else{
			document.getElementById('text31').style.display = 'none';
			document.getElementById('text32').style.display = 'none';
			document.getElementById('seven').style.display = 'none';
			document.getElementById('eight').style.display = 'none';
			
		}
}


function openDiv15(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text38').style.display = 'block';
		 document.getElementById('text39').style.display = 'block';
		 document.getElementById('forteen').style.display = 'block';
		 document.getElementById('fifteen').style.display = 'block';
		 
		}else{
			document.getElementById('text38').style.display = 'none';
			document.getElementById('text39').style.display = 'none';
			document.getElementById('forteen').style.display = 'none';
			document.getElementById('fifteen').style.display = 'none';
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
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					--><br><br>
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none" >
	<!--<h2>Dear <b id="pnameId">******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	--></div>
	<div style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>
	<div id="formDiv">
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
		
		<div id="questions" style=" "border: thin; margin-left:46px; clear: both;">
		
		<table style="width:1027px; border-spacing:0; height:20px;">
			<tr>
			<td align="center" width="1060px" class="g">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " > Nutrition</label></td>
			
		</tr>
		
		
		</table>
		
		
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:503px;  height:10px; border-spacing: 0px;">
					<tr>
			<td width="350px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Weight</b> </label></td>
				
		</tr>
					
		
		<tr>
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current weight</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<td  class="f" align="center"><s:textfield name="118" value="" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td  style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6 months ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<td  class="c" align="center"><s:textfield name="119" value="" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 year ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<td  class="f" align="center"><s:textfield name="120" value="" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3 years ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<td  class="c" align="center"><s:textfield name="121" value="" theme="simple"/></label></td>
		</tr>
		
			<tr>
			<td class="f"></td>
			<td height="22px" align="center" class="f"></td>
	
		</tr>
		
		
		<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Would you want your weight to be different?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="122"  title="Any Accident /Surgery in the past?" onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="122"  onclick="openDiv8(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text31" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If yes what should be your weight</label><br>
				
				</div> 
				
				
				
			<td align="center" class="f" colspan="2"> 
			<div id="text32" style="display: none" >
			<s:textfield name="123" value="" theme="simple"/>
			</div>
			
				</td> 
				
			
			
			
			
			</tr>
			
			<tr>
			<td  style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Why do you want to change your weight?</b></label>
			
		</tr>
		
		<tr>
			
			<td  colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea name="124" style="width:450px;" value="" theme="simple"/></label></td>
		</tr>
		
			<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
			
			<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
			
			<tr>
			<td class="c">
			<div id="nine" style="display: none; height: 14px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="ten" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			
			<tr>
			<td class="c">
			<div id="eleven" style="display: none; height: 14px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="twelve" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			
		
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:525px; height:10px; border-spacing: 0px; padding: 1px 2px 2px 1px;">
						<tr>
			
		<tr>
			<td width="380px" height="20px" colspan="2" class="f"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>How would you describe your diet/appetite</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Hearty" theme="simple"></s:label></td>
			 <td  class="c" align="center"><s:radio list="#{'Hearty':''}" name="125"    theme="simple"></s:radio>
			 
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Moderate" theme="simple"></s:label></td>
			 <td  class="f" align="center"><s:radio list="#{'Moderate':''}" name="125"    theme="simple"></s:radio>
			
			 
		</tr>
		
		<tr>
			 <td style="height: 22px;" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Poor" theme="simple"></s:label></td>
			 <td  class="c" align="center"><s:radio list="#{'Poor':''}" name="125" theme="simple"/></td>
			 
		</tr>
		
		
		
		
		
		<tr>
			 <td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td  class="f" align="center"><s:textfield name="128" value="" theme="simple"/></label></td>
			 
		</tr>
		
		
		<tr>
			<td width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Have you ever followed a special diet</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="129"  title="Any Accident /Surgery in the past?" onclick="openDiv9(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="129"  onclick="openDiv9(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text33" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Explain from whom and when </label><br>
				
				</div> 
				
				
				
			<td align="center" class="f" colspan="2"> 
			<div id="text34" style="display: none" >
			<s:textfield name="130" value="" theme="simple"/>
			</div>
			
				</td> 
				
			
			
			
			
			</tr>
			
			<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;<b>Do you approximately eat the same time every day</b></label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':'','No':''}" name="131"   title="Do you approximately eat the same time every day" theme="simple"></s:radio></td>
			
			
		</tr>
	
	<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="f"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Do you skip meals ?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="132"  onclick="openDiv10(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="132"  onclick="openDiv10(this.value)" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f">
			<div id="text35" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If so when ?</label><br>
				
				</div> 
				
				
				
			<td align="center" class="f" colspan="2"> 
			<div id="text36" style="display: none" >
			<s:textfield name="133" value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
				
			
			</tr>
	

		
		<tr>
			<td class="f" ></td>
			<td height="23px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c">
			<div id="seven" style="display: none; height: 14px;" >
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
			</tr>
	</table>
	

	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:503px;  height:10px; border-spacing: 0px;">
					
		
		<tr>
			<td style="height: 22px;" width="380px" colspan="2" class="c"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Do you usually eat between meals?</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="134"  title="Any Accident /Surgery in the past?" onclick="openDiv15(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			
			<td style="height: 22px;" width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="134"  onclick="openDiv15(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="c">
			<div id="text38" style="display: none; height: 22px; ">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;What do you snack on most often</label><br>
				
				</div> 
				
				
				
			<td align="center" class="c" colspan="2"> 
			<div id="text39" style="display: none" >
			<s:textfield name="135" value="" theme="simple"/>
			</div>
			
				</td> 
				
			
			
			
			
			</tr>
			
			<tr>
			<td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Explain what according to you is a healthy meal?</b></label>
			
		</tr>
		
		<tr>
			
			<td  colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea name="136" style="width:450px;" value="" theme="simple"/></label></td>
		</tr>
		
		
			<tr>
			<td style="height: 22px;" colspan="2" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>What are the two things would you like to change about your diet and lifestyle?</b></label>
			
		</tr>
		
		<tr>
			
			<td  colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea name="137" style="width:450px;" value="" theme="simple"/></label></td>
		</tr>
		
			<tr>
			<td class="f" ></td>
			<td height="23px" align="center" class="f"></td>
	
		</tr>
			
			<tr>
			<td class="c" ></td>
			<td height="24px" align="center" class="c"></td>
	
		</tr>
			
			
		
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:525px; height:10px; border-spacing: 0px; padding: 0px 1px 1px 1px;">
						<tr>
			
		<tr>
			<td  height="20px" colspan="2" class="f"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" ><b>Please add any additional information you feel may be relevant to understand your <br>&nbsp;&nbsp;&nbsp;&nbsp;nutritional status</b> </label></td>
				
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Food allergies" theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td  style="height: 22px;" class="f">&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="138"   theme="simple"></s:radio></td>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >No</label>
			<s:radio list="#{'No':''}" name="138"   theme="simple"></s:radio>  <s:textfield name="139" value="" theme="simple"/></td>
			</td>
			
			
		</tr>
		
		
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="In-tolerances to any food item" theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td style="height: 22px;"  class="f">&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="140"   theme="simple"></s:radio></td>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >No</label>
			<s:radio list="#{'No':''}" name="140"   theme="simple"></s:radio>  <s:textfield name="141" value="" theme="simple"/></td>
			</td>
			
			
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Taking any vitamin/mineral " theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td style="height: 22px;"  class="f">&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="142"   theme="simple"></s:radio></td>
			
			<td style="height: 22px;"  class="f"><label id="labelAns" style="color: #000;" >No</label>
			<s:radio list="#{'No':''}" name="142"   theme="simple"></s:radio>  <s:textfield name="143" value="" theme="simple"/></td>
			</td>
			
			
		</tr>
		
		<tr>
			 <td style="height: 22px;" colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Taking any herbal supplements  " theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td  style="height: 22px;" class="f">&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="144"   theme="simple"></s:radio></td>
			
			<td  style="height: 22px;" class="f"><label id="labelAns" style="color: #000;" >No</label>
			<s:radio list="#{'No':''}" name="144"   theme="simple"></s:radio>  <s:textfield name="145" value="" theme="simple"/></td>
			</td>
			
			
		</tr>
		
		

		
		
		<tr>
			<td class="f" ></td>
			<td height="17px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="f">
			<div id="forteen" style="display: none; height: 23px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="fifteen" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
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
   
      <sj:a cssStyle=" margin-left: -249px; margin-top: -77px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           button="true"
	           onBeforeTopics="validate222"
	           cssStyle="   margin-left: -87px; margin-top: -77px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onCompleteTopics="mak"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-147px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top:-147px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
 		<!-- onBeforeTopics="validate2" -->
   </div>
   </div>
   </div>
	
	
	
	
	
		</div>

		<!-- Buttons -->
		
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