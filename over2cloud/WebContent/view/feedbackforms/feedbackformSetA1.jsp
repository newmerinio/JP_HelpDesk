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
		currentPage:1
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





function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
		
}

$.subscribe('mak', function(event,data){
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			setTimeout(function(){ $("#complTarget").fadeOut();},1000);
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
									$('input[name='+i+']').removeAttr("style");		
								 if( $('input[name='+i+']:checked').length<=0)
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
						 setTimeout(function(){ $("#errZone").fadeOut(); $("#errZone2").fadeOut(); }, 1000);
						  });
		 
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}

$( document ).ready(function()
		{

	$("textarea").blur(function(){
		$(this).css({"border": "0px solid red","background": "#FFFFFF"});
	});

		//$("input:radio").blur(function(){
		//	$(this).css({"box-shadow":"0 0 0px 0px red inset"});
		//});	

		$("input:text").blur(function(){
			$(this).css({"box-shadow":"0 0 0px 0px red inset"});
			$(this).publish('validate', this, event);
		});	
		
		
	//$("input:checkbox").blur(function(){
	//	$(this).css({"box-shadow":"0 0 0px 0px red inset"});
	//});	
	
	var traceId=$("#traceid").val();
	var type=$("#type").val();	
		//	alert(traceId+traceId);

	var positionPage=parseInt($("#positionPage").val())+1;
	$("#positionPage").val(positionPage);

	
		if(type === 'M'){
			$("#totalpages").text("9");
			}
	
		    	 $.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A1",
				    success : function(subdeptdata) 
				    {
				    	 var x = JSON.stringify(subdeptdata);
				    	//alert(x);
				    	//alert(x.length);
				    	var i=0;
				     	if(x.length>0){
				    	$.each( subdeptdata, function( key, value )
				    	{
				    		var values = value.split(",");
				    		 value = values[0];
				    	   if(key==='1'){
					    	   openDiv(value);
					    	   }else if(key==='3'){
					    		   openDiv1(value);
						    	   }
					    	   else if(key==='5'){
					    		   openDiv2(value);
					    	   }
					    	   else if(key==='7'){
					    		   openDiv3(value);
					    	   }
					    	   else if(key==='9'){
					    		   openDiv4(value);
					    	   }
					    	   else if(key==='11'){
					    		   openDiv5(value);
					    	   }
					    	   else if(key==='13'){
					    		   openDiv6(value);
					    	   }
					    	   else if(key==='15'){
					    		   openDiv7(value);
					    	   }
					    	   else if(key==='17'){
					    		   openDiv8(value);
					    	   }
					    	   else if(key==='19'){
					    		   openDiv9(value);
					    	   }
					    	   else if(key==='21'){
					    		   openDiv10(value);
					    	   }
					    	   else if(key==='23'){
					    		   openDiv11(value);
					    	   }
					    	   else if(key==='25'){
					    		   openDiv12(value);
					    	   }
					    	   else if(key==='27'){
					    		   openDiv13(value);
					    	   }
					    	   else if(key==='29'){
					    		   openDiv14(value);
					    	   }
					    	   else if(key==='31'){
					    		   openDiv15(value);
					    	   }
				    		
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
	openDiv('No');
	openDiv1('No');openDiv2('No');openDiv3('No');openDiv4('No');openDiv5('No');openDiv6('No');openDiv7('No');openDiv8('No');
	openDiv9('No');openDiv10('No');openDiv11('No');openDiv12('No');openDiv13('No');openDiv14('No');openDiv15('No');
	
}
function openDiv(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text1').style.display = 'block';
		 
		}
	 
	 else{
			document.getElementById('text1').style.display = 'none';
			document.getElementById("field").value = "";
		}
}

function openDiv1(val){
	
	 if(val==='Yes')
		 {
		 
		 document.getElementById('text2').style.display = 'block';
		 
		}else{
			document.getElementById('text2').style.display = 'none';
			document.getElementById("field1").value = "";
		}
}

function openDiv2(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text3').style.display = 'block';
		 
		}else{
			document.getElementById('text3').style.display = 'none';
			document.getElementById("field2").value = "";
		}
}

function openDiv3(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text4').style.display = 'block';
		 
		}else{
			document.getElementById('text4').style.display = 'none';
			document.getElementById("field3").value = "";
		}
}

function openDiv4(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text5').style.display = 'block';
		 
		}else{
			document.getElementById('text5').style.display = 'none';
			document.getElementById("field4").value = "";
		}
}

function openDiv5(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text6').style.display = 'block';
		 
		}else{
			document.getElementById('text6').style.display = 'none';
			document.getElementById("field5").value = "";
		}
}

function openDiv6(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text7').style.display = 'block';
		 
		}else{
			document.getElementById('text7').style.display = 'none';
			document.getElementById("field6").value = "";
		}
}

function openDiv7(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text8').style.display = 'block';
		 
		}else{
			document.getElementById('text8').style.display = 'none';
			document.getElementById("field7").value = "";
		}
}

function openDiv8(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text9').style.display = 'block';
		 
		}else{
			document.getElementById('text9').style.display = 'none';
			document.getElementById("field8").value = "";
		}
}

function openDiv9(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text10').style.display = 'block';
		 
		}else{
			document.getElementById('text10').style.display = 'none';
			document.getElementById("field9").value = "";
		}
}

function openDiv10(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text11').style.display = 'block';
		 
		}else{
			document.getElementById('text11').style.display = 'none';
			document.getElementById("field10").value = "";
		}
}

function openDiv11(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text12').style.display = 'block';
		 
		}else{
			document.getElementById('text12').style.display = 'none';
			document.getElementById("field11").value = "";
		}
}

function openDiv12(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text13').style.display = 'block';
		 
		}else{
			document.getElementById('text13').style.display = 'none';
			document.getElementById("field12").value = "";
		}
}

function openDiv13(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text14').style.display = 'block';
		 
		}else{
			document.getElementById('text14').style.display = 'none';
			document.getElementById("field13").value = "";
			
		}
}

function openDiv14(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text15').style.display = 'block';
		 
		}else{
			document.getElementById('text15').style.display = 'none';
			document.getElementById("field14").value = "";
		}
}

function openDiv15(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text16').style.display = 'block';
		 
		}else{
			document.getElementById('text16').style.display = 'none';
			document.getElementById("field15").value = "";
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
		color:black; 
		font-style:normal; 
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
    font-family: Trebuchet MS, Arial, Helvetica, sans-serif;
    width: 100%;
    border-collapse: collapse;
}

#feedbackform1 td, #feedbackform1 th {
    font-size: 1em;
   
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
	
	td.b
	{
	background-color: #CFB873; 
	border-right: 1px solid #fff !important;
	}
	
  	td.c
  	{
  	align-content: center;
  	background-color: #CFB873;
  	}
  	td.e
	{
	background-color: #E6CC80; 
	border-right: 1px solid #fff !important;
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
.paginate.pag1 { /* first page styles */ }
 
.paginate.pag1 li { font-weight: bold;}
 
.paginate.pag1 li a {
  display: block;
  float: left;
  color: #717171;
  background: #e9e9e9;
  text-decoration: none;
  padding: 5px 7px;
  margin-right: 6px;
  border-radius: 3px;
  border: solid 1px #c0c0c0;
  box-shadow: inset 0px 1px 0px rgba(255,255,255, .7), 0px 1px 3px rgba(0,0,0, .1);
  text-shadow: 1px 1px 0px rgba(255,255,255, 0.7);
}
.paginate.pag1 li a:hover {
  background: #eee;
  color: #555;
}
.paginate.pag1 li a:active {
  -webkit-box-shadow: inset -1px 2px 5px rgba(0,0,0,0.25);
  -moz-box-shadow: inset -1px 2px 5px rgba(0,0,0,0.25);
  box-shadow: inset -1px 2px 5px rgba(0,0,0,0.25);
}
 
.paginate.pag1 li.single, .paginate.pag1 li.current {
  display: block;
  float: left;
  border: solid 1px #c0c0c0;
  padding: 5px 7px;
  margin-right: 6px;
  border-radius: 3px;
  color: #444;
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
<script>

</script>

</head>
<body>
	<div class="clear"></div>
		<div id="mainDiv" >
					<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					
	<div id="innerDiv2" >
	<!--<div id="formTitle" style="   margin-top: -7px;"><br><br>
		<h2>Dear <b id="pnameId"><s:property value="%{pname}" /></b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	</div>
	--><div id="formDiv">
						
						<div id="class"></div>
	<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr >
	<td style="background-color: bisque; padding: 0px 0px; width: 70%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 20%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 85px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 12px;"  id="totalpages">10</div></label>
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
			<center>						<div
								style="float: right; border-color: black; background-color: #FF6666; color: white; font-family: Arial; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: right; margin-left: 7px;"></div>
							</div>
						</center>
	<s:form id="form1" action="submitFeedbackNew" namespace="/view/Over2Cloud/patientActivity" 
	method="post" enctype="multipart/form-data" >
		
	<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		
		<s:hidden id="pageNo" name="pageNo" value="2"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="10" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p1," name="page" />
		

		<!-- questions -->
		
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		
		<table id="feedbackform1">
		
		<tr>
			<td align="center" colspan="3" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; " >Medical History</label></td>
		</tr>
		
		<tr>
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="400px"><label id="titlesection" style="color: #fff; " >Does any of your close family relatives (Parents, Brothers, Sisters, Aunts/Uncles Grandparents) have *</label></td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="150px"><label id="titlesection" style="color: #fff; " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="200px"><label id="titlesection" style="color: #fff; " >Relation </label></td>
		</tr>
		
				<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Coronary Artery Disease</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="1" title="Coronary Artery Disease" onclick="openDiv(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text1" style="display: none">
				
				<s:textfield name="2" title="Coronary Artery Disease" id="field" value="" height="70px" theme="simple"/>
				
			</div>
</td>
		</tr>
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; High Blood Pressure</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)"  list="#{'Yes':'','No':''}" name="3" title="High Blood pressure" title="High Blood pressure" onclick="openDiv1(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text2" style="display: none">
				
				<s:textfield name="4" value="" id="field1" title="High Blood pressure" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Brain Stroke</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="5" title="Brain Stroke" onclick="openDiv2(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text3" style="display: none">
				
				<s:textfield name="6" title="Brain Stroke" id="field2" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Diabetes Mellitus</label></td>
	<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="7" title="Diabetes Mellitus" onclick="openDiv3(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text4" style="display: none">
				
				<s:textfield name="8" value="" id="field3" title="Diabetes Mellitus" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; High Cholesterol</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="9" title="High Cholesterol" onclick="openDiv4(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text5" style="display: none">
				
				<s:textfield name="10" value="" id="field4" title="High Cholesterol" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Cancer</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="11" title="Cancer" onclick="openDiv5(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text6" style="display: none">
				
				<s:textfield name="12" value="" id="field5" title="Cancer" theme="simple"/>
				
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
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important; border-top: 1px solid #fff;border-bottom: 1px solid #fff;"" width="400px"><label id="titlesection" style="color: #fff; " >Medical History (Self) *</td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="150px"><label id="titlesection" style="color: #fff;  " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="200px"><label id="titlesection" style="color: #fff; " >Duration </label></td>
		</tr>
		
		
		
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Hypertension (High Blood Pressure)</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="13" title="Hypertension" onclick="openDiv6(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text7" style="display: none">
				
				<s:textfield name="14" title="Hypertension" id="field6" value="" theme="simple"/>
				
			</div>
</td>
			
		</tr>
		
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Diabetes Mellitus</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="15" title="Diabetes Mellitus" onclick="openDiv7(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text8" style="display: none">
				
				<s:textfield name="16" value="" id="field7" title="Diabetes Mellitus" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; High Blood Cholesterols</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="17" value="" title="High Blood Cholesterols" onclick="openDiv8(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text9" style="display: none">
				
				<s:textfield name="18" value="" id="field8" title="High Blood Cholesterols" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Thyroid Disease</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="19" title="Thyroid Disease" onclick="openDiv9(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text10" style="display: none">
				
				<s:textfield name="20" value="" id="field9" title="Thyroid Disease" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Bronchial Asthma</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="21" title="Bronchial Asthma" onclick="openDiv10(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text11" style="display: none">
				
				<s:textfield name="22" value="" id="field10" title="Bronchial Asthma" value="" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Seizure Disorder/Fits</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="23" title="Seizure Disorder/Fits" onclick="openDiv11(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text12" style="display: none">
				
				<s:textfield name="24" title="Seizure Disorder/Fits" id="field11" value="" theme="simple"/>
				
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
			<td style="background-color: #FF6C47; border-right: 1px solid #fff !important; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="400px"><label id="titlesection" style="color: #fff; " >Have you ever had any cardiac problems? *</td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="150px"><label id="titlesection" style="color: #fff; " >Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label></td>
			<td align="center" style="background-color: #FF6C47; border-top: 1px solid #fff;border-bottom: 1px solid #fff;" width="200px"><label id="titlesection" style="color: #fff; " >Duration </label></td>
		</tr>
		
		
		
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Chest Pain</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="25" title="Chest Pain" onclick="openDiv12(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text13" style="display: none">
				
				<s:textfield name="26" id="field12" title="Chest Pain" value="" theme="simple"/>
				
			</div>
</td>
			
		</tr>
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Heart Attack</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="27" title="Heart Attack" onclick="openDiv13(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text14" style="display: none">
				
				<s:textfield name="28" id="field13" title="Heart Attack" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Angioplasty</label></td>
			<td align="center" style="background-color: #CFB873"><s:radio onchange="$(this).publish('validate', this, event)"  list="#{'Yes':'','No':''}" name="29" title="Angioplasty" onclick="openDiv14(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #CFB873">
			<div id="text15" style="display: none">
				
				<s:textfield name="30" id="field14" title="Angioplasty" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td style="background-color: #E6CC80; border-right: 1px solid #fff !important;"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp; Bypass Surgery</label></td>
			<td align="center" style="background-color: #E6CC80"><s:radio onchange="$(this).publish('validate', this, event)" list="#{'Yes':'','No':''}" name="31" title="Bypass Surgery" onclick="openDiv15(this.value)" theme="css_xhtml"  ></s:radio></td>
			<td align="center" style="background-color: #E6CC80">
			<div id="text16" style="display: none">
				<s:textfield name="32" id="field15" title="Bypass Surgery" value="" theme="simple"/>
			</div>
</td>
		</tr>	
		
		<tr>
			<td style="background-color: #CFB873; border-right: 1px solid #fff !important;"></td>
			<td height="28px" align="center" style="background-color: #CFB873"></td>
			<td align="center" style="background-color: #CFB873">
	
</td>
		</tr>

		<tr>
			<td colspan="3" align="right">
				<label id="labelAns"><b style="font-style: italic;">*Are Mandatory Questions</b></label> 
			</td>
		
		</tr>
		
		
		
			
	</table>
	
	<center>
		<div style="float: right; border-color: black; background-color: #FF6666; color: white; width: 100%; font-family: Arial; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone2" style="float: right; margin-left: 7px;"></div>
							</div>
						</center>
	<center> <div id="pagesDiv" style="margin-left: 267px;" > </div> </center>
			 <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 80px;">
  <center> <div id="bt" style="display: block;">
  		<table>
  		<tr>
  		<sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="Next >>" 
	           
	           button="true"
	           cssStyle="   margin-left: -126px; margin-top: -69px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; "
	           indicator="indicator1"
	           onBeforeTopics="validate"
			   />
 	         <sj:a cssStyle=" margin-left: 49px; margin-top: -64px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 7px;  margin-top: -64px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#" onclick='closePage();'>Close</sj:a>
</tr>
<!-- onBeforeTopics="validateIst" -->
</table>
   </div></center>
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