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
			setTimeout(function(){ $("#complTarget").fadeOut(); nextPage();},1000);
			 reset('form1');
		  });
		 
		 function cancelButton()
		 {
			 $('#messagealert').dialog('close');
		 }
		 
		 $.subscribe('validate', function(event,data)
					{
							
						 for(var i=1 ;i<=32;i++)
							{
								if(i%2!=0){				
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
									 if($('input[name='+i+']:checked').val() === 'Yes'){
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
								}
							}
						  });
		 
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}





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
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=2&positionPage="+positionPage+"&done=10",
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




</script>
<!-- color: #8B8B70; -->
<style type="text/css">
	#label {
  	padding:7px 0;traceid=18&type=F
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
  align-content: center;traceid=18&type=F
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
  border-bottom-color: #C6C6C6;22
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
  font-weight: bold;1h4ocy
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
    font-size: 1em;22
    border:1px solid #ff6633;
    padding: 3px 7px 2px 7px;
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
  background-color: #337ab7;1h4ocy
  -webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
  box-shadow: inset 0 -1px 0 rgba(0,0,0,.15);
  -webkit-transition: width .6s ease;
  -o-transition: width .6s ease;
  transition: width .6s ease;
}
* {
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
					<br><br><br><br>
	--><div id="innerDiv2" >
	<div id="formTitle" style="   margin-top: -7px;">
		<!--<h2>Dear <b id="pnameId"><s:property value="%{pname}" />*******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	--></div>
	<div id="formDiv">
						<center>
						<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="flo1h4ocyat: center; margin-left: 7px"></div>
							</div>
						</center>
						<div id="class"></div>
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
		
		
		<table id="feedbackform1">
		
		<tr>
			<td align="center" colspan="3" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; " >Family Medical History</label></td>
		</tr>
		
		<tr>
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important;" width="400px"><label id="titlesection" style="color: #fff; " >Does any of your close family relatives (Parents, Brother, Sister, Aunts/Uncles Grandparents) have</label></td>
			<td align="center" style="background-color: #FF6C47" width="150px"><label id="titlesection" style="color: #fff; " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47" width="200px"><label id="titlesection" style="color: #fff; " >Relation </label></td>
		</tr>
		
				<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >1&nbsp;&nbsp; Coronary Artery Disease</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="1" title="Coronary Artery Disease" onclick="openDiv(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text1" style="display: none">
				
				<s:textfield name="2" title="Coronary Artery Disease" value="" height="70px" theme="simple"/>
				
			</div>
</td>
		</tr>
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >2&nbsp;&nbsp; High Blood pressure</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="3" title="High Blood pressure" title="High Blood pressure" onclick="openDiv1(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text2" style="display: none">
				
				<s:textfield name="4" value="" title="High Blood pressure" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >3&nbsp;&nbsp; Brain Stroke</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="5" title="Brain Stroke" onclick="openDiv2(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text3" style="display: none">
				
				<s:textfield name="6" title="Brain Stroke" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >4&nbsp;&nbsp; Diabetes Mellitus</label></td>
	<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="7" title="Diabetes Mellitus" onclick="openDiv3(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text4" style="display: none">
				
				<s:textfield name="8" value="" title="Diabetes Mellitus" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >5&nbsp;&nbsp; High Cholesterol</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="9" title="High Cholesterol" onclick="openDiv4(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text5" style="display: none">
				
				<s:textfield name="10" value="" title="High Cholesterol" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >6&nbsp;&nbsp; Cancer</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="11" title="Cancer" onclick="openDiv5(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text6" style="display: none">
				
				<s:textfield name="12" value="" title="Cancer" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"></td>
			<td height="17px" align="center" style="background-color: #CFB873"></td>
			<td align="center" style="background-color: #CFB873">
	
</td>
		</tr>
		
			
		<tr>
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important;" width="400px"><label id="titlesection" style="color: #fff; " >Medical History (Self)</td>
			<td align="center" style="background-color: #FF6C47" width="150px"><label id="titlesection" style="color: #fff; " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47" width="200px"><label id="titlesection" style="color: #fff; " >Duration </label></td>
		</tr>
		
		
		
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >1&nbsp;&nbsp; Hypertension (High Blood pressure)</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="13" title="Hypertension" onclick="openDiv6(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text7" style="display: none">
				
				<s:textfield name="14" title="Hypertension" value="" theme="simple"/>
				
			</div>
</td>
			
		</tr>
		
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >2&nbsp;&nbsp; Diabetes Mellitus</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="15" title="Diabetes Mellitus" onclick="openDiv7(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text8" style="display: none">
				
				<s:textfield name="16" value="" title="Diabetes Mellitus" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >3&nbsp;&nbsp; High Blood Cholesterols</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="17" value="" title="High Blood Cholesterols" onclick="openDiv8(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text9" style="display: none">
				
				<s:textfield name="18" value="" title="High Blood Cholesterols" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >4&nbsp;&nbsp; Thyroid Disease</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="19" title="Thyroid Disease" onclick="openDiv9(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text10" style="display: none">
				
				<s:textfield name="20" value="" title="Thyroid Disease" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >5&nbsp;&nbsp; Bronchial Asthma</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="21" title="Bronchial Asthma" onclick="openDiv10(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text11" style="display: none">
				
				<s:textfield name="22" value="" title="Bronchial Asthma" value="" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >6&nbsp;&nbsp; Seizure Disorder/Fits</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="23" title="Seizure Disorder/Fits" onclick="openDiv11(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text12" style="display: none">
				
				<s:textfield name="24" title="Seizure Disorder/Fits" value="" theme="simple"/>
				
			</div>
</td>
		</tr>
	
	<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"></td>
			<td height="17px" align="center" style="background-color: #CFB873"></td>
			<td align="center" style="background-color: #CFB873">
	
</td>
		</tr>
		
		<tr>
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important;" width="400px"><label id="titlesection" style="color: #fff; " >Have you ever had any cardiac problems?</td>
			<td align="center" style="background-color: #FF6C47" width="150px"><label id="titlesection" style="color: #fff; " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47" width="200px"><label id="titlesection" style="color: #fff; " >Duration </label></td>
		</tr>
		
		
		
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >1&nbsp;&nbsp; Chest Pain</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="25" title="Chest Pain" onclick="openDiv12(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text13" style="display: none">
				
				<s:textfield name="26" title="Chest Pain" value="" theme="simple"/>
				
			</div>
</td>
			
		</tr>
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >2&nbsp;&nbsp; Heart Attack</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="27" title="Heart Attack" onclick="openDiv13(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text14" style="display: none">
				
				<s:textfield name="28" title="Heart Attack" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >3&nbsp;&nbsp; Angioplasty</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio list="#{'Yes':'','No':''}" name="29" title="Angioplasty" onclick="openDiv14(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text15" style="display: none">
				
				<s:textfield name="30" title="Angioplasty" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >4&nbsp;&nbsp; Bypass Surgery</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio list="#{'Yes':'','No':''}" name="31" title="Bypass Surgery" onclick="openDiv15(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text16" style="display: none">
				<s:textfield name="32" title="Bypass Surgery" value="" theme="simple"/>
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"></td>
			<td height="17px" align="center" style="background-color: #CFB873"></td>
			<td align="center" style="background-color: #CFB873">
	
</td>
		</tr>
		
		
		
			
	</table>
	
			 <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
  <center> <div id="bt" style="display: block;">
  		<table>
  		<tr>
  		<sj:submit 
	           targets="complTarget" 
	           clearForm="true"
	           value="Next >>" 
	           effect="highlight"
	           button="true"
	           cssStyle="   margin-left: -126px; margin-top: -69px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; "
	           indicator="indicator1"
	           onBeforeTopics="validate2222"
	           onCompleteTopics="mak"
			   />
 	         <sj:a cssStyle=" margin-left: 49px; margin-top: -64px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 7px;  margin-top: -64px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#" onclick='closePage();'>Close</sj:a>
</tr>
<!-- onBeforeTopics="validateIst" -->
</table>
   </div></center>
   </div>
   </div>
			</div>
			


		<!-- Buttons -->
		<br>
		   <div class="clear"></div>
  
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