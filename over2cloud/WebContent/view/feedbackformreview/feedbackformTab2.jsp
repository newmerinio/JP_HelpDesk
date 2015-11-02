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
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=1&positionPage="+positionPage+"&done="+done,
			    success : function(subdeptdata) {
				$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		       $("#"+"reviewForms").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
		});
}

<%----%>
$.subscribe('validate2', function(event,data)
		{
			 for(var i=33 ;i<=51;i++)
				{
					if(i!=34 && i!=36 && i!=38 && i!=40 && i!=42 && i!=43 && i!=45 && i!=47 && i!=49 && i!=51){					 
						 if( $('input[name='+i+']:checked').length<=0)
						 {
							 //$("#alertDiv").html("Please select Question "+$("input[name='"+i+"']").attr("title"));
						  	//$('#messagealert').dialog('open');
						  	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.</div>";
						  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
				     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
				     		$('input[name='+i+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 }else{
							 if($('input[name='+i+']:checked').val() === 'Yes' || $('input[name='+i+']:checked').val() === 'No'){
								 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
								 	errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.</div>";
								  	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						     		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);     
						     		$('input[name='+(i+1)+']').css("box-shadow","0 0 13px 0px red inset");
						     		$('input[name='+(i+1)+']').focus();
								  	event.originalEvent.options.submit = false;
								  	return false;
								 	}
								 	
								 }
								
							 }
					}else if(i == 43) {
						if($('textarea[name='+i+']').val() === ""){
					 		errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
            				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
							$('textarea[name='+i+']').focus();
			  				$('textarea[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
			  				event.originalEvent.options.submit = false;
			  				return false;
							}
						}
					}
			  });

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

    $("input:text").blur(function(){
        $(this).   css("box-shadow","0 0 0 0 red inset");
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
		    	else{
		    		$('input[name=41]').attr("title","Females: 3.5 to 7.2 mg/dl");
		    	}
		    	
		    	
		});



$.subscribe('mak', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 1000);
			 setTimeout(function(){ $("#complTarget").fadeOut(); nextPage(); }, 2000);
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
					    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=3&positionPage="+positionPage+"&done=20",
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

function reset(formId) 
{
	$('#'+formId).trigger("reset");
	
}

function openDiv(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text1').style.display = 'block';
	
		}

	 else if(val==='No')
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
		 
		}

	 else if(val==='No')
	 {
	 document.getElementById('text2').style.display = 'block';

	}
		
	 
		else{
			document.getElementById('text2').style.display = 'none';
		}
}

function openDiv2(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text3').style.display = 'block';
		 
		}
	 else if(val==='No')
	 {
	 document.getElementById('text3').style.display = 'block';

	}

		else{
			document.getElementById('text3').style.display = 'none';
		}
}

function openDiv3(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text4').style.display = 'block';
		 
		}
	 else if(val==='No')
	 {
	 document.getElementById('text4').style.display = 'block';

	}

		else{
			document.getElementById('text4').style.display = 'none';
		}
}

function openDiv4(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text5').style.display = 'block';
		 
		}
	 else if(val==='No')
	 {
	 document.getElementById('text5').style.display = 'block';

	}

		else{
			document.getElementById('text5').style.display = 'none';
		}
}

function openDiv5(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text6').style.display = 'block';
		 
		}
	 else if(val==='No')
	 {
	 document.getElementById('text6').style.display = 'block';

	}

		else{
			document.getElementById('text6').style.display = 'none';
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
  ----
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
  			padding: 1.3%;
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
	
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		
		<table id="feedbackform1">
		
		<tr>
			<td class="g" colspan="3" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; " >Previous vitals & investigations</td>
		</tr>
		
		<tr>
			<td class="g" width="350px"><label id="titlesection" style="color: #fff; " >Please mention if your following blood parameters (if done earlier) are at acceptable levels or not (Reference range provided in brackets)</label></td>
			<td class="g" align="center" width="200px"><label id="titlesection" style="color: #fff; " >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;&nbsp;&nbsp;Don't Know</label></td>
			<td class="g" align="center" width="200px"><label id="titlesection" style="color: #fff; " >Mention Value if any </label></td>
		
		</tr>
				
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Blood Pressure</label></td>
			<td class="c" align="center"><s:radio list="#{'Yes':'','No':'','Donot Know':''}" name="33"  title="Blood pressure: (Normal 110/70 to 130/80)" onclick="openDiv(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text1" style="display: none">
				
				<s:textfield name="34" value="" title="Blood Pressure: (Normal 110/70 to 130/80)" theme="simple"/>
				
			</div>
</td>
		</tr>
			
		<tr>
			<td class="e"><label id="labelAns" style="color: #000;" >Total Cholesterol</label></td>
			<td align="center" class="f"><s:radio list="#{'Yes':'','No':'','Donot Know':''}" name="35"  title="Total Cholesterol(Normal 100 to 180 mg/dl)" onclick="openDiv1(this.value)" theme="simple"></s:radio></td>
			<td align="center" class="f">
			<div id="text2" style="display: none">
				
				<s:textfield name="36" title="Total Cholesterol(Normal 100 to 180 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Fasting Blood sugar</label></td>
			<td class="c" align="center"><s:radio list="#{'Yes':'','No':'','Donot Know':''}" name="37"  title="Fasting Blood sugar(Normal 70 to 100 mg/dl)" onclick="openDiv2(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text3" style="display: none">
				
				<s:textfield name="38" title="Fasting Blood sugar(Normal 70 to 100 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td class="e"><label id="labelAns" style="color: #000;" >Serum Triglycerides</label></td>
	<td align="center" class="f"><s:radio list="#{'Yes':'','No':'','Donot Know':''}" name="39"  title="Serum Triglycerides(< 150 mg/dl)" onclick="openDiv3(this.value)" theme="simple"></s:radio></td>
			<td align="center" class="f">
			<div id="text4" style="display: none">
				
				<s:textfield name="40" title="Serum Triglycerides(< 150 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Serum Uric Acid</label></td>
			<td class="c" align="center"><s:radio list="#{'Yes':'','No':'','Donot Know':''}" name="41"  title="Normal :Males : 3.5 to 7.2 mg/dl" onclick="openDiv4(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text5" style="display: none">
				
				<s:textfield name="42" title="Serum Uric Acid" value="" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td colspan="3" class="f"><label id="labelAns" style="color: #000;" >Are you on any regular Medication for any medical reasons</label></td>
		</tr>
		
		 <tr>
			<td class="c"  colspan="3">
			<s:textarea name="43" style="margin:0px; width:400px; height:31px;" title="Are you on any regular Medication for any medical reasons" value="" theme="simple"/>
			
			</td>
		</tr>
		
	
		<tr>
			<td class="f" border-right: 1px solid #fff !important;"></td>
			<td height="17px" align="center" class="f"></td>
			<td align="center" class="f"">
	
</td>
		</tr>
	
		
	</table>
	
	
		<table style=" border-spacing:0;">
			<tr>
			<td width="888px" class="g">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " >Present Complaints (Do you have Any of the following symptoms frequently)</label></td>
			
		</tr>
		
		
		</table>
		
	<table style="width:450px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px;  border-spacing: 0px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>CNS</b> </label></td>
			<td class="g" style="height: 21px;"></td>		
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="44" theme="simple"/></label></td>
			 
		</tr>			
							
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Headache" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Headache':''}" name="44" theme="simple"/></label></td>
			 
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Associated with Aura" theme="simple"></s:label></td>
			 <td class="f" width="80px" align="center" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Associated with Aura':''}" name="44" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Nausea/Vomiting" theme="simple"></s:label></td>
			 <td class="c" width="80px" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Nausea/Vomiting':''}" name="44" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Blurred Vision/Flashes of light" theme="simple"></s:label></td>
			 <td  class="f" width="80px" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Blurred Vision/Flashes of light':''}" name="44" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Memory & concentration" theme="simple"></s:label></td>
			 <td  class="c" width="80px" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Memory & concentration':''}" name="44" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td width="80px"  class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="45" value="" size="18px" style="height:20px" theme="simple"/></td>
			 
		</tr>
		<tr>
			<td class="c" ></td>
			<td height="17px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="17px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px; height:10px; border-spacing: 0px; padding: 1px 2px 4px 2px;">
						<tr>
			<td width="380px" colspan="2" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Digestive Problems</b> </label></td>
				
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="46" theme="simple"/></label></td>
			 
		</tr>	
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Heartburn" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Heartburn':''}" name="46" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Excessive Flatulence" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Excessive Flatulence':''}" name="46" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Bloating" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Bloating':''}" name="46" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Burping" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Burping':''}" name="46" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Altered Bowel movements" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Altered Bowel movements (Constipation/Diarrhoea)':''}" name="46" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td width="80px"  class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="47" value="" size="18px" style="height:20px; margin-right:8px;" theme="simple"/></td>
			 
		</tr>
		
<tr>
			<td class="c" ></td>
			<td height="27px" align="center" class="c"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
	<table style="width:900px; border-spacing:0; ">
			
		
		
		</table>
		
	<table style="width:450px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px;  border-spacing: 0px; padding: 0px 1px 4px 2px;">
					<tr>
			<td width="255px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Skin Problems</b> </label></td>
			<td class="g"></td>		
		</tr>
					
				<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="48" theme="simple"/></label></td>
			 
		</tr>				
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Itching" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Itching':''}" name="48" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Allergies" theme="simple"></s:label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Allergies':''}" name="48" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Discolouration" theme="simple"></s:label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Bloating':''}" name="48" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Eruptions" theme="simple"></s:label></td>
			 <td align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Eruptions':''}" name="48" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Boils" theme="simple"></s:label></td>
			 <td align="center"  class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Boils':''}" name="48" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td width="50px"  align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="49" size="18px" style="height:20px" value="" theme="simple"/></td>
																										
		</tr>
		<tr>
			<td class="c"></td>
			<td height="17px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="17px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px;  border-spacing: 0px; padding: 0px 2px 4px 1px;">
						<tr>
			<td width="380px" class="g"><label id="titlesection" style="color: #fff; ">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Sleeping Pattern</b> </label></td>
			<td class="g"></td>		
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Normal" theme="simple"></s:label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'None':''}" name="44" theme="simple"/></label></td>
			 
		</tr>	
	<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Less than 6 hours" theme="simple"></s:label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Less than 6 hours ':''}" name="50" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="More than 8 hours" theme="simple"></s:label></td>
			 <td class="f" align="center" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'More than 8 hours':''}" name="50" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Disturbed sleep " theme="simple"></s:label></td>
			 <td  align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Disturbed sleep ':''}" name="50" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="f" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Waking in between sleep " theme="simple"></s:label></td>
			 <td align="center" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist list="#{'Waking in between sleep ':''}" name="50" theme="simple"/></label></td>
		</tr>
		
		
		<tr>
			 <td class="c" style="height: 22px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></td>
			 <td align="center" class="c"><s:textfield name="51" size="18px" style="height:20px; margin-right:8px;" value="" theme="simple"/></td>
			
		</tr>
		
<tr>
			<td class="f" ></td>
			<td height="20px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="24px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="24px" align="center" class="f"></td>
	
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
  		<sj:a cssStyle=" margin-left: -249px; margin-top: -66px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           button="true"
	           cssStyle="   margin-left: -87px; margin-top: -66px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onCompleteTopics="mak"
	           onBeforeTopics="validate2222"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-125px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top: -125px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
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
         <div id="complTarget"></div>
			</s:form>
			</div>
		
		</div>	
</div>
</body>
</html>