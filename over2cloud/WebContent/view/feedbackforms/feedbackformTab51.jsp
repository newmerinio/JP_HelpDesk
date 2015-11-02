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
		currentPage:6
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

$.subscribe('mak', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 1000);
			setTimeout(function(){ $("#complTarget").fadeOut(); }, 2000);
			 reset('form1');
		  });

function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
		
}

$.subscribe('validate2', function(event,data)
		{
 for(var i=301 ;i<=306;i++)
	{
	 console.log(i);
		if(i!=304 && i!=305 && i!=306 && i!=322 && i!=324 && i!=325  && i!=326 && i!=328 && i!=330 && i!=331 && i!=333 && i!=334 && i!=335 && i!=337 && i!=338 )
			{					 
			$('input[name='+i+']').removeAttr("style");
			 if($('input[name='+i+']:checked').length<=0)
		 {
				 showErrorField();
		     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
		     		$('input[name='+i+']').focus();
		     		event.originalEvent.options.submit = false; 	
			  		return false;
		 }
		}else if( false){
			
			if($.trim($('textarea[name='+i+']').val())=="")
			 {
				showErrorField();
			  	$('textarea[name='+i+']').focus();
			  	$('textarea[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
			  	event.originalEvent.options.submit = false;
			  	return false;
			}
		}
	}
 setTimeout(function(){ $("#errZone").fadeOut(); $("#errZone2").fadeOut(); }, 2000); 		
			 
  });		 
		 
		 function cancelButton()
		 {
			 $('#completionResult').dialog('close');
			 
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
					    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=5&positionPage="+positionPage+"&done="+done,
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
			
				$("textarea").blur(function(){
					 $(this).css({"background-color":"white","border":"0px solid rgb(252, 252, 252)"});
				});

					//$("input:radio").blur(function(){
					//	 $(this).removeAttr("style");
					//});	

					$("input:text").blur(function(){
						 $(this).css("box-shadow","0 0 0 0 red inset");
					});	
					
					
				//$("input:checkbox").blur(function(){
				//	 $(this).css({"background-color":"white","border":"0px solid rgb(252, 252, 252)"});
				//});
			 					
				var traceId=$("#traceid").val();
				var type=$("#type").val();	
			//	alert(traceId+traceId);
			
					$("input:radio").change( function() {
		$(this).publish('validate2', this, event);
	});

	$("input:checkbox").change( function() {
		$(this).publish('validate2', this, event);
	});

				var positionPage=parseInt($("#positionPage").val())+1;
				$("#positionPage").val(positionPage);

					    	if(type === 'M'){
								$("#totalpages").text("9");
								}
					    	
					    		$.ajax({
							    type : "post",
							    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A5",
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
								    	   $('textarea[name="'+key+'"]').val(values[0]);
								    	});
										$('#update').val("update");
								    }
							    },
							   error: function() {
						            alert("error");
						        }
							 });
					});
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}		 


function reset(formId) 
{
	$('#'+formId).trigger("reset");
	var val='';
	var id='';
	showDiv(val,'showf');
	showDiv2(val,'showHost');
	showDiv3(val,'showSmok');
	showDiv4(val,'showalco');
	showDiv5(val,'showalle');
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
		 
		 
		}else{
			document.getElementById('text31').style.display = 'none';
			document.getElementById('text32').style.display = 'none';
			
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
		 
		}else{
			document.getElementById('text38').style.display = 'none';
			document.getElementById('text39').style.display = 'none';
			document.getElementById('text40').style.display = 'none';
			document.getElementById('text41').style.display = 'none';
			document.getElementById('text42').style.display = 'none';
			document.getElementById('text43').style.display = 'none';
			
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
	
		<div id="mainDiv">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
<h2>Dear <b id="pnameId">*******localhost:8080</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
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
		
		<s:hidden id="pageNo" name="pageNo" value="7"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="60" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p51," name="page" />

		
		<!-- questions -->
		
	<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		
		
	
	
		<table style="width:878px; border-spacing:0; height:20px;">
			<tr>
			<td align="center" width="878px" style="background-color: #CC66FF">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " >Physio</label></td>
			
		</tr>
		
		
		</table>
		
	<table style="width:435px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:435px;  height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 1px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="25px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Exercise and Fitness *</b> </label></td>
			<td style="border-top: 1px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="25px"   class="g"></td>		
		</tr>
					
							
		<tr>
			 <td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff; height:23px;" width="350px"   colspan="2" class="g">
			 <label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <s:label value="1. Do you exercise for atleast 30 min daily 6 days a week? " theme="simple"></s:label></label></td>
			 
		</tr>
		
		<tr>
			 <td class="c" style="height:33px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="301"   theme="simple"></s:radio></label></td>
			 <td class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="301"   theme="simple"></s:radio></label></td>
			 
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="16px" align="center" class="f"></td>

		</tr>
		
		<tr>
			 <td style="border-top: 2px solid #fff;border-bottom: 0px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			 &nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff; font-weight: bold;">2. Is your job Sedentary and requires you to remain seated for a long</label></td>
		</tr>
		<tr>	
			 <td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;duration of time?</label></td>
		</tr> 
		
		
		<tr>
			 <td class="c" style="height:33px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="302"   theme="simple"></s:radio></label></td>
			 <td class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="302"   theme="simple"></s:radio></label></td>
			 
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="20px" align="center" class="f"></td>

		</tr>
		
		<tr>
			 <td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff; height:33px;" width="350px"   colspan="2" class="g"><label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="3. Do you maintain good posture while sitting, standing, lifting, etc?" theme="simple"></s:label></label></td>
			 
		</tr>
		
		<tr>
			 <td class="c" style="height:33px;"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Yes" theme="simple"></s:label>
			 <s:radio list="#{'Yes':''}" name="303"   theme="simple"></s:radio></label></td>
			 <td class="c"><label id="labelAns" style="color: #000;"><s:label value="No" theme="simple"></s:label>
			 <s:radio list="#{'No':''}" name="303"   theme="simple"></s:radio></label></td>
			 
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="33px" align="center" class="f"></td>
	
		</tr>
		
		
	
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:435px;  height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 1px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Lifestyle</b> </label></td>
			<td style="border-top: 1px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"   class="g"></td>		
		</tr>
					
			<tr>
			 <td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g"><label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="1. What is the most physically active thing that you do on a typical day? " theme="simple"></s:label></label></td>
			 
		</tr>
		
		<tr>
			 <td class="c" colspan="2">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea  style="margin:0px; width:250px; height:31px; resize:none;" name="304" value="" theme="simple"/></td>
			
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="16px" align="center" class="f"></td>
	
		</tr>
		
		
		<tr>
			 <td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff; height:45px; " width="350px"  colspan="2" class="g"><label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="2. Describe your typical day? " theme="simple"></s:label></label></td>
			 
		</tr>
		
		 
		
		<tr>
			 <td class="c" colspan="2">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea  style="margin:0px; width:250px; height:31px; resize:none;" name="305" value="" theme="simple"/></td>
			
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="16px" align="center" class="f"></td>
	
		</tr>
		
		
		<tr>
			 <td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g"><label id="labelAns" style="color: #fff; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. What are the everyday challenges that you  face managing a healthy &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Lifestyle?</label></td>
			 
		</tr>
		
		<tr>
			 <td class="c" colspan="2">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea style="margin:0px; width:250px; height:31px; resize:none;" name="306" value="" theme="simple"/></td>
			
		</tr>
		
		
		<tr>
			<td class="f" ></td>
			<td height="28px" align="center" class="f"></td>
	
		</tr>
		
		
		
	</table>
				
				
				</td>
			</tr>
			<tr>
			<td colspan="3" align="right">
				<label id="labelAns"><b style="font-style: italic;" >*Are Mandatory Questions</b></label> 
			</td>
		
		</tr>
	</table>
	<center>						<div
								style="float: right; border-color: black; background-color: #FF6666; color: white; font-family: Arial; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone2" style="float: right; margin-left: 7px;"></div>
							</div>
						</center>
	 <div class="clear"></div>
		   <center> <div id="pagesDiv" style="margin-left: 214px;" > </div> </center>
		   <br>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button">
   <div id="bt" style="display: block;">

  		<sj:a cssStyle=" margin-left: -249px; margin-top: -8px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="Next >>" 
	           button="true"
	           onBeforeTopics="validate2"
	           cssStyle="   margin-left: -87px; margin-top: -27px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
			   />  
			   
 	         <sj:a cssStyle="   margin-left: 134px; margin-top:-50px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="reset('form1');">Reset</sj:a>
	        <sj:a cssStyle=" margin-left: 1px;  margin-top: -50px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;" button="true" href="#" onclick='closePage();'>Close</sj:a>
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