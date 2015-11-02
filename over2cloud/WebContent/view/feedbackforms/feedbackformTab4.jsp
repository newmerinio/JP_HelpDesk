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
		currentPage:5
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
			setTimeout(function(){ $("#complTarget").fadeOut();},2000);
			 //$('#completionResult').dialog('open');
			 reset('form1');
		  });
		 
function closePage(){
	window.location.href = "http://www.medanta.org/";
}

function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
		
}

$.subscribe('validate2', function(event,data)
		{
 for(var i=118 ;i<=144;i++)
	{
			if( i!=123 && i!=128 && i!=130 && i!=133 && i!=135 && i!=139 && i!=141 && i!=143 && i!=145){	
		 	if( i==122 || i===129 || i===131 || i==132 || i==134 || i==138 || i==140 || i==142 || i==144 ) 
		 		{
		 		$('input[name='+i+']').removeAttr("style");
		 		 if($('input[name='+i+']:checked').length<=0 )
				 {
		 			showErrorField();
		     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
		     		$('input[name='+i+']').focus();
				  	event.originalEvent.options.submit = false;
				  	return false;
				 }else if(i!=131) {
					 $('input[name='+i+']').removeAttr("style");
					 if( $('input[name='+i+']:checked').val() === 'Yes'){
						 	
						 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
						 		showErrorField();   
				     		$('input[name='+(i+1)+']').css({"border": "1px solid red","background": "#FFCECE"});
				     		$('input[name='+(i+1)+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 	}
						 	
						 }	
					 }
		 		}
		 	else if(false ){
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
		 	else if(i==118 ){
				if(($('input[name='+i+']').val().trim()) == "" ){	
					showErrorField();
				$('input[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
  				$('input[name='+i+']').focus();
  				event.originalEvent.options.submit = false;
  				return false;
				}

		 		}
		 	else if(i == 125) {
				$('input[name='+i+']').removeAttr("style");
		
                if($('input[name='+i+']:checked').length<=0 && $('input[name='+(i+3)+']').val().trim()=='')
				 {
                	showErrorField();
        			$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
        			$('input[name='+i+']').focus();
	
        			$('input[name='+(i+3)+']').focus();
        			$('input[name='+(i+3)+']').css({"border": "1px solid red","background": "#FFCECE"});
	  				        				
        			event.originalEvent.options.submit = false;
				  	return false;
    			    }
                else{
                	$('input[name='+i+']').removeAttr("style");
                	$('input[name='+(i+1)+']').removeAttr("style");
                    }
   
					}


	 		
	 		
			}
	}
 setTimeout(function(){ $("#errZone").fadeOut(); $("#errZone2").fadeOut(); }, 1000); 
  });
  


function backPrevious(val)
{
	var traceId=$("#traceid").val();
	var type=$("#type").val();

		var positionPage=parseInt($("#positionPage").val())-2;
	var done=$("#done").val();

	var pname=$("#pname").val();
			
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/patientActivity/viewQuestionnaireForms.action",
		    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=4&positionPage="+positionPage+"&done="+done,
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
		$(this).css({"border": "0px solid red","background": "#FFFFFF"});
	});

		//$("input:radio").blur(function(){
		//	$(this).css({"box-shadow":"0 0 0px 0px red inset"});
		//});	

		$("input:text").blur(function(){
			$(this).css({"border": "0px solid red","background": "#FFFFFF"});
		});	
		
	//$("input:checkbox").blur(function(){
	//	$(this).removeAttr("style");
	//});		
	
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
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A4",
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
					    		if(key === '122'){
					    			openDiv8(value);
					    		}
					    		else if(key === '129'){
					    			openDiv9(value);	
					    		}else if(key === '132'){
					    			openDiv10(value);
					    		}else if(key === '134'){
					    			openDiv15(value);
					    		}else if(key === '138'){
					    			openDiv21(value);
					    		}else if(key === '140'){
					    			openDiv22(value);
					    		}else if(key === '142'){
					    			openDiv23(value);
					    		}else if(key === '144'){
					    			openDiv24(value);
					    		}					    		
					    		
						    	console.log(values+"  #key:"+key);
						    	for(a=0;a<values.length;a++){
						    		console.log(">>  "+values[a]);
						    		$('input[name="' + key.trim()+ '"][value="' + values[a].trim() + '"]').prop("checked", true);
						    	}
					    		
					    	   $('input:text[name="'+key+'"]').val(value);
					    	   $('textarea[name="'+key+'"]').val(value);
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
	openDiv8("No");
	openDiv9("No");
	openDiv10("No");
	openDiv15("No");
	openDiv21("No");
	openDiv22("No");
	openDiv23("No");
	openDiv24("No");
	
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
			document.getElementById("field3").value = "";
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
			document.getElementById("field2").value = "";
		}
}

function openDiv21(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text103').style.display = 'block';
		 document.getElementById('twentyseven').style.display = 'block';
		 document.getElementById('twentyeight').style.display = 'block';
		 
		 
		 
		}else{
			document.getElementById('text103').style.display = 'none';
			document.getElementById('twentyseven').style.display = 'none';
			 document.getElementById('twentyeight').style.display = 'none';
			document.getElementById("field11").value = "";
		}
}

	 function openDiv22(val){
		
			
		 if(val==='Yes')
			 {
			 document.getElementById('text104').style.display = 'block';
			 document.getElementById('twentynine').style.display = 'block';
			 document.getElementById('thirty').style.display = 'block';
			 
			 
			}else{
				document.getElementById('text104').style.display = 'none';
				document.getElementById('twentynine').style.display = 'none';
				 document.getElementById('thirty').style.display = 'none';
				document.getElementById("field12").value = "";
			}
}


	 function openDiv23(val){
		
			
		 if(val==='Yes')
			 {
			 document.getElementById('text105').style.display = 'block';
			 document.getElementById('thirtyone').style.display = 'block';
			 document.getElementById('thirtytwo').style.display = 'block';
			 
			 
			 
			 
			}else{
				document.getElementById('text105').style.display = 'none';
				document.getElementById('thirtyone').style.display = 'none';
				 document.getElementById('thirtytwo').style.display = 'none';
				document.getElementById("field13").value = "";
			}
}

	 function openDiv24(val){
		
			
		 if(val==='Yes')
			 {
			 document.getElementById('text106').style.display = 'block';
			 document.getElementById('thirtythree').style.display = 'block';
			 document.getElementById('thirtyfour').style.display = 'block';
			 
			 
			 
			}else{
				document.getElementById('text106').style.display = 'none';
				document.getElementById('thirtythree').style.display = 'none';
				 document.getElementById('thirtyfour').style.display = 'none';
				document.getElementById("field14").value = "";
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
			document.getElementById("field1").value = "";
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
			document.getElementById("field4").value = "";
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
	width: 400px;
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
	border-right: 1px solid #fff !important;
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

::-webkit-input-placeholder {
 font-size: 14px;
 color: #6E6E6E;
 
 text-align: center;
 font-weight: bold;
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
	<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr style="float: right;">
	<td>
	<td style="background-color: bisque; padding: 0px 0px; width: 70%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 20%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 113px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 13px;"  id="totalpages">10</div></label>
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
		
		<s:hidden id="pageNo" name="pageNo" value="6"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="50" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p4," name="page" />
			<!-- questions -->
		
		<div id="questions" style=" "border: thin; margin-left:46px; clear: both;">
		
		<table style="width:1027px; border-spacing:0; height:20px;">
			<tr>
			<td align="center" width="1060px" class="g" style="background-color: #CC66FF">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " > Nutrition</label></td>
			
		</tr>
		
		
		</table>
		
		
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:503px;   border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Weight (in Kgs) *</b> </label></td>
				
		</tr>
					
		
		<tr>
			<td  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current Weight</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td  class="c" align="center"><label id=labelAns><s:textfield name="118" value="" cssStyle="height: 17px;" maxlength="3" theme="simple"/><b> Kg</b></label></td>
		</tr>
		
		<tr>
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6 Months Ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td  class="f" align="center"><label id=labelAns><s:textfield onchange="$(this).publish('validate2', this, event);" name="119" value="" cssStyle="height: 17px;" maxlength="3" theme="simple"/><b> Kg</b></label> </label></td>
		</tr>
		
		<tr>
			<td  class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 Year Ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td  class="c" align="center"><label id=labelAns><s:textfield onchange="$(this).publish('validate2', this, event);" name="120" value="" cssStyle="height: 17px;" maxlength="3" theme="simple"/><b> Kg</b></label> </label></td>
		</tr>
		
		<tr>
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3 Years Ago</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td  class="f" align="center"><label id=labelAns><s:textfield onchange="$(this).publish('validate2', this, event);" name="121" value="" cssStyle="height: 17px;" maxlength="3" theme="simple"/><b> Kg</b></label> </td>
		</tr>
		
			<tr>
			<td class="c" ></td>
			<td height="15px" align="center" class="c"></td>
	
		</tr>
		
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Would you want your weight to be different? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="122"  title="Any Accident /Surgery in the past?" onclick="openDiv8(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			</td>
			
		</tr>
		
		<tr>
			<td class="c">
			<div id="text31" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If yes what should be your weight</label><br>
				
				</div> 
				
				
				
			<td align="center" class="c" colspan="2"> 
			<div id="text32" style="display: none" >
			<s:textfield name="123" value="" id="field1" theme="simple"/>
			</div>
				</td> 
		
			</tr>
		
		<tr>
			
			<td  width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="122" cssStyle="margin-left:6px;"  onclick="openDiv8(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f"></td>
			<td height="17px" align="center" class="f"></td>
	
		</tr>
		
		
			
			<tr>
			<td  style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			<label id="labelAns" style="color: #fff;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Why do you want to change your weight?</b></label>
			
		</tr>
		
		<tr>
			
			<td style="height: 48px;" colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea onchange="$(this).publish('validate2', this, event);" name="124" cssStyle="width:450px; height: 35px; resize:none;" value="" theme="simple"/></label></td>
		</tr>
		
			<tr>
			<td class="f" ></td>
			<td height="18px" align="center" class="f"></td>
	
		</tr>
			
			<tr>
			<td class="c"></td>
			<td height="18px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f"></td>
			<td height="19px" align="center" class="f"></td>
	
		</tr>
		
		
			
			<tr>
			<td class="c">
			<div id="nine" style="display: none; height: 20px;" >
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
			<div id="eleven" style="display: none; height: 20px;" >
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
				
				
				<table style=" width:520px;  border-spacing: 0px">
						
			
		<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>How would you describe your diet/appetite? *</b> </label></td>
				
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Hearty" theme="simple"></s:label></label></td>
			 <td  class="c" align="center"><s:radio list="#{'Hearty':''}" name="125"    theme="simple"></s:radio></td>
			 
			 
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Moderate" theme="simple"></s:label></label></td>
			 <td  class="f" align="center"><s:radio list="#{'Moderate':''}" name="125"    theme="simple"></s:radio></td>
			
			 
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Poor" theme="simple"></s:label></label></td>
			 <td  class="c" align="center"><s:radio list="#{'Poor':''}" name="125" theme="simple"/></td>
			 
		</tr>
		
		
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td  class="f" align="center"><s:textfield name="128" value="" theme="simple"/></td>
			 
		</tr>
		
		<tr>
			<td class="c"></td>
			<td height="17px" align="center" class="c"></td>
	
		</tr>
		
		
		<tr>
			<td style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Have you ever followed a special diet? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="129"  title="Any Accident /Surgery in the past?" onclick="openDiv9(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			<td class="c">
			<div id="text33" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Explain from whom and when </label><br>
				
				</div> 
				
				
				
			<td align="center" class="c" colspan="2"> 
			<div id="text34" style="display: none" >
			<s:textfield name="130" value="" cssStyle="height: 17px;" id="field2" theme="simple"/>
			</div>
			
				</td> 
				
			
			</tr>
		
		<tr>
			
			<td  width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="129" cssStyle="margin-left:3px;" onclick="openDiv9(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f"></td>
			<td height="17px" align="center" class="f"></td>
	
		</tr>
		
		
		
			
			<tr>
			
			<td  style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g"><label id="labelAns" style="color: #fff;" >&nbsp;&nbsp;&nbsp;&nbsp;<b>Do you approximately eat the same time every day? *</b></label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label id="labelAns" style="color: #fff;" >
			Yes<s:radio list="#{'Yes':''}" name="131"   title="Do you approximately eat the same time every day" theme="simple"></s:radio>
			No<s:radio list="#{'No':''}" name="131"   title="Do you approximately eat the same time every day" theme="simple"></s:radio>
			</label>
			</td>
			
			
		</tr>
		
		<tr>
			<td class="f"></td>
			<td height="17px" align="center" class="f"></td>
	
		</tr>
	
	<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Do you skip meals? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="132"  onclick="openDiv10(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			<td class="c">
			<div id="text35" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If so when?</label><br>
				
				</div> 
				
				
				
			<td align="center" class="c" colspan="2"> 
			<div id="text36" style="display: none" >
			<s:textfield name="133" value="" id="field3" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
		
		<tr>
			
			<td  width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="132" cssStyle="margin-left:3px;" onclick="openDiv10(this.value)" theme="simple"></s:radio></td>
			
			
		</tr>
		
		
			
		
	

		
		<tr>
			<td class="f" ></td>
			<td height="16px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c">
			<div id="seven" style="display: none; height: 20px;" >
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
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="36px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Do you usually eat between meals? *</b> </label></td>
				
		</tr>
					
		
		<tr>
			
			<td  class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'Yes':''}" name="134"  title="Any Accident /Surgery in the past?" onclick="openDiv15(this.value)" theme="simple"></s:radio></td>
			<td class="f" align="center">
			
		</tr>
		
		<tr>
			<td class="c">
			<div id="text38" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;What do you snack on most often?</label><br>
				
				</div> 
				
				
				
			<td align="center" class="c" colspan="2"> 
			<div id="text39" style="display: none" >
			<s:textfield name="135" value="" cssStyle="height: 17px;" id="field4"  theme="simple"/>
			</div>
			
				</td> 
			
			</tr>
		
		<tr>
			
			<td  width="80px" colspan="2" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:radio list="#{'No':''}" name="134" cssStyle=" margin-left: 6px;
			"onclick="openDiv15(this.value)" title="Any Accident /Surgery in the past?" theme="simple"></s:radio></td>
			
			
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
		
		
			
			<tr>
			<td  style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g"><label id="labelAns" style="color: #fff;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Explain what according to you is a healthy meal?</b></label>
			
		</tr>
		
		<tr>
			
			<td  colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea name="136" onchange="$(this).publish('validate2', this, event);" cssStyle="width:450px; height: 35px; resize:none;" value="" theme="simple"/></label></td>
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="15px" align="center" class="f"></td>
	
		</tr>
		
		
		
			<tr>
			<td  style="border-top: 2px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g"><label id="labelAns" style="color: #fff;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>What are the two things would you like to change about your diet and lifestyle?</b></label>
			
		</tr>
		
		<tr>
			
			<td  colspan="2" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textarea name="137" onchange="$(this).publish('validate2', this, event);" cssStyle="width:450px; height: 35px; resize:none;" value="" theme="simple"/></label></td>
		</tr>
		
			<tr>
			<td class="f" ></td>
			<td height="26px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="26px" align="center" class="c"></td>
	
		</tr>
		
		
		
		<tr>
			<td class="f" ></td>
			<td height="27px" align="center" class="f"></td>
	
		</tr>
		
		
		<tr>
			<td class="c" ></td>
			<td height="27px" align="center" class="c"></td>
	
		</tr>
		
		
		
		
		
		
		<!--<tr>
			<td class="c">
			<div id="twentyseven" style="display: none; height: 22px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="twentyeight" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
		<tr>
			<td class="f">
			<div id="twentynine" style="display: none; height: 22px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="thirty" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			<tr>
			<td class="c">
			<div id="thirtyone" style="display: none; height: 22px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="c" colspan="2"> 
			<div id="thirtytwo" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
			<tr>
			<td class="f">
			<div id="thirtythree" style="display: none; height: 20px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td   align="center" class="f" colspan="2"> 
			<div id="thirtyfour" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
			</tr>
			
		
		
		
	--></table>
				</td>
				
	
				<td >
				
				
				<table style=" width:521px; height:10px; border-spacing: 0px">
					
			
		<tr>
			<td  style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="350px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Please add any additional information you feel may be relevant to understand your <br>&nbsp;&nbsp;&nbsp;&nbsp;nutritional status *</b> </label></td>
				
		</tr>
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Food Allergies" theme="simple"></s:label></td>
		</tr>
		<tr>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="138" onclick="openDiv21(this.value)"   theme="simple"></s:radio></td>
			<td class="f"><div id="text103" style="display: none;" >
			&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="139" id="field11" placeholder="Enter Details" style="text-align: left;" value="" theme="simple"/></label>
			</div></td>
		</tr>
		<tr>	
			<td  colspan="2" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >No</label>
			&nbsp;<s:radio list="#{'No':''}" name="138" onclick="openDiv21(this.value)"   theme="simple"></s:radio>  
			</td>
			
			
		</tr>
		
		<tr>
			<td  class="f" ></td>
			<td height="17px" align="center" class="f"></td>
		

		</tr>
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="In-tolerances to Any Food Item" theme="simple"></s:label></label></td>
		</tr>
		<tr>
			 <td   class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="140" onclick="openDiv22(this.value)"  theme="simple"></s:radio></td>
			<td class="f">
			
			<div id="text104" style="display: none">
			&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="141" value="" placeholder="Enter Details" style="text-align: left;" id="field12" theme="simple"/></label>
			</div>
			
			</td>
		</tr>
		<tr>
			
			<td  class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >No</label>
			&nbsp;<s:radio list="#{'No':''}" name="140" onclick="openDiv22(this.value)"  theme="simple"></s:radio> 
			</td>
			
			<td  class="c">
			
			
			</td>
			
			
		</tr>	
		<tr>
			<td  class="f" ></td>
			<td height="17px" align="center" class="f"></td>
			

		</tr>
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Taking Any Vitamin/Mineral " theme="simple"></s:label></label></td>
		</tr>
		
		
		<tr>
			 <td   class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="142" onclick="openDiv23(this.value)"  theme="simple"></s:radio></td>
			<td class="f">
				<div id="text105" style="display: none">
			&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="143" value="" placeholder="Enter Details" style="text-align: left;" id="field13" theme="simple"/></label>
			</div>
			</td>
		</tr>
		<tr>	
			<td   class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >No</label>
			&nbsp;<s:radio list="#{'No':''}" name="142"  onclick="openDiv23(this.value)"  theme="simple"></s:radio>  
			</td>
			
			<td  class="c">
		</td>
	
		</tr>	
		
		<tr>
			<td  class="f" ></td>
			<td height="17px" align="center" class="f"></td>
			

		</tr>
		
		
		
		<tr>
			 <td colspan="2" class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Taking Any Herbal Supplements" theme="simple"></s:label></label></td>
		</tr>
		<tr>
			 <td   class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Yes</label>
			<s:radio list="#{'Yes':''}" name="144" onclick="openDiv24(this.value)"   theme="simple"></s:radio></td>
			<td  class="f">
			<div id="text106" style="display: none">
			&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="145" value="" placeholder="Enter Details" style="text-align: left;" id="field14" theme="simple"/></label>
			</div>
		
		</td>
		</tr>
		<tr>	
			<td colspan="2" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >No</label>
			&nbsp;<s:radio list="#{'No':''}" name="144" onclick="openDiv24(this.value)"   theme="simple"></s:radio> 
			</td>
			
			
		</tr>
		
		
		
		<tr>
			<td  class="f" ></td>
			<td height="17px" align="center" class="f"></td>
			

		</tr>
		
		<tr>
			<td class="f">
			<div id="forteen" style="display: none; height: 17px;" >
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br>
				
				</div> </td>
		
			<td colspan="2"  align="center" class="f" > 
			<div id="fifteen" style="display: none" >
			<s:label value="" theme="simple"/>
			</div>
			
				</td> 
		
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
	<center> <div id="pagesDiv" style="margin-left: 301px;" > </div> </center>
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
	           onBeforeTopics="validate2"
	           cssStyle="   margin-left: -87px; margin-top: -77px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           
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