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
    //	alert(pageNumber);
    	nextPage(pageNumber);
		},
		currentPage:2
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
			    data:"pname="+pname+"&traceid="+traceId+"&type="+type+"&pageNo=1&positionPage="+positionPage+"&done="+done,
			    success : function(subdeptdata) {
				$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		       $("#"+"datapart").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
		});
}

<%----%>
function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.&nbsp;&nbsp;&nbsp;</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
		 
}

$.subscribe('validate2', function(event,data)
		{
			 for(var i=33 ;i<=51;i++)
				{
								
					if(i!=34 && i!=36 && i!=38 && i!=40 && i!=42 && i!=43 && i!=44 && i!=45 && i!=46  && i!=47 && i!=48 && i!=49 && i!=50 && i!=51){
						$('input[name='+i+']').removeAttr("style");					 
						 if( $('input[name='+i+']:checked').length<=0)
						 {
						  showErrorField();
						  $('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
				     		$('input[name='+i+']').focus();
						  	event.originalEvent.options.submit = false;
						  	return false;
						 }else{<%--
							 $('input[name='+i+']').removeAttr("style");
							 if($('input[name='+i+']:checked').val() === 'Yes' || $('input[name='+i+']:checked').val() === 'No'){
								 	if(($('input[name='+(i+1)+']').val()).trim() === ''){
								 		showErrorField();
									 $('input[name='+(i+1)+']').css("box-shadow","0 0 13px 0px red inset");
						     		$('input[name='+(i+1)+']').focus();
								  	event.originalEvent.options.submit = false;
								  	return false;
								 	}
								 }--%>
							 }
					}
					else if( false && i == 43) {
						if($('textarea[name='+i+']').val() === ""){
					 		//$("#alertDiv").html("Please select Question "+$("textarea[name='"+i+"']").attr("title"));
			  				//$('#messagealert').dialog('open');
			  				errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
            				setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            				setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
							$('textarea[name='+i+']').focus();
			  				$('textarea[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
			  				event.originalEvent.options.submit = false;
			  				return false;
							}
						}
					else if(i == 44 || i == 46 || i == 48 || i == 50) {
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


$( document ).ready(function()
		{

//{"border": "1px solid red","background": "#FFCECE"}
	
	$("textarea").blur(function(){
		 $(this).css({"border": "0px solid red","background": "#FFFFFF"});
    });

	//$('input[name='+i+']:checked').val() === 'Yes' 
	
	$("#43").click(function() {
		
		if($('#43:checked').val() === 'None'){
	        for(var i=1;i<6;i++){
	        	$('#44'+i).prop('checked', false);
	        	$('#44'+i).attr('disabled', 'disabled');
	            }
	        dvPassport.style.display = "none";
	        dvPassport1.style.display = "none";
	        dvPassport2.style.display = "none";
	        dvPassport3.style.display = "none";
	        dvPassport4.style.display = "none";
	        dvPassport5.style.display = "none";
	   		 for(var i=2;i<5;i++){
	    		$('#44'+i).prop('checked', false);
	        }
		}
	    else{
	    	 for(var i=1;i<6;i++){
	         	$('#44'+i).prop('checked', false);
	         	$('#44'+i).removeAttr('disabled');
	             }
	        }
	});


	$("#45").click(function() {
		
		if($('#45:checked').val() === 'None'){
	        for(var i=1;i<6;i++){
	        	$('#46'+i).prop('checked', false);
	        	$('#46'+i).attr('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<6;i++){
	         	$('#46'+i).prop('checked', false);
	         	$('#46'+i).removeAttr('disabled');
	             }
	        }
	});

	$("#47").click(function() {
		
		if($('#47:checked').val() === 'None'){
	        for(var i=1;i<6;i++){
	        	$('#48'+i).prop('checked', false);
	        	$('#48'+i).attr('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<6;i++){
	         	$('#48'+i).prop('checked', false);
	         	$('#48'+i).removeAttr('disabled');
	             }
	        }
	});

	$("#49").click(function() {
		
		if($('#49:checked').val() === 'None'){
	        for(var i=1;i<6;i++){
	        	$('#50'+i).prop('checked', false);
	        	$('#50'+i).prop('disabled', 'disabled');
	            }
		}
	    else{
	    	 for(var i=1;i<6;i++){
	         	$('#50'+i).prop('checked', false);
	         	$('#50'+i).removeAttr('disabled');
	             }
	        }
	});


	

    $("input:text").blur(function(){
        $(this).css("box-shadow","0 0 0 0 red inset");
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
		    	else{
		    		$('input[name=41]').attr("title","Females: 3.5 to 7.2 mg/dl");
		    	}
		    	
		    	if(traceId!='' && traceId!=null)
		    		{
		    	$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A2",
				    success : function(subdeptdata) 
				    {
				    	$.each( subdeptdata, function( key, value )
				    	{
				    		var values = value.split(",");
				    		 value = values[0];
					    	if(key ==='33'){
					    		openDiv(value);
						    	}else if(key ==='35'){
												openDiv1(value);
							    	}else if(key ==='37'){
							    		openDiv2(value);
							    	}
							    	else if(key ==='39'){
							    		openDiv3(value);
							    	}
							    	else if(key ==='41'){
							    		openDiv4(value);	
							    	}else if(key ==='44'){
							    		dvPassport.style.display = "block";
							    	    dvPassport1.style.display = "block";
							    	    dvPassport2.style.display = "block";
							    	    dvPassport3.style.display = "block";
							    	    dvPassport4.style.display = "block";
							    	    dvPassport5.style.display = "block";	
							    	}
					    	//console.log(values+"  #key:"+key);
					    	for(a=0;a<values.length;a++){
					    		//console.log(">>  "+values[a]);
					    		$('input[name="' + key.trim()+ '"][value="' + values[a].trim() + '"]').prop("checked", true);
					    	}
				    	  
				    	   $('input:text[name="'+key+'"]').val(values[0]);
				    	   $('textarea[name="'+key+'"]').val(values[0]);
				    	});
						$('#update').val("update");
				    },
				   error: function() {
			            alert("error");
			        }
				 });
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

		
		 
		 
function closePage(){
		window.location.href = "http://www.medanta.org/";
	}		 

function reset(formId) 
{
	$('#'+formId).trigger("reset");

	openDiv("A");
	openDiv1("B");
	openDiv2("C");
	openDiv3("D");
	openDiv4("E");
	
	for(var i=1;i<6;i++){
     	$('#44'+i).prop('checked', false);
     	$('#44'+i).removeAttr('disabled');
         }
	for(var i=1;i<6;i++){
     	$('#48'+i).prop('checked', false);
     	$('#48'+i).removeAttr('disabled');
         }
	for(var i=1;i<6;i++){
     	$('#46'+i).prop('checked', false);
     	$('#46'+i).removeAttr('disabled');
         }
	for(var i=1;i<5;i++){
     	$('#50'+i).prop('checked', false);
     	$('#50'+i).removeAttr('disabled');
         }
	dvPassport.style.display = "none";
    dvPassport1.style.display = "none";
    dvPassport2.style.display = "none";
    dvPassport3.style.display = "none";
    dvPassport4.style.display = "none";
    dvPassport5.style.display = "none";
	
}

function openDiv(val){
	
	 if(val==='Yes')
		 {
		 document.getElementById('text1').style.display = 'block';
	
		}

	 else if(val==='No')
	 {
	 document.getElementById('text1').style.display = 'block';
	 document.getElementById("field1").value = "";
	}
		
	 
	 else{
			document.getElementById('text1').style.display = 'none';
			document.getElementById("field1").value = "";
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
	 document.getElementById("field2").value = "";

	}	else{
			document.getElementById('text2').style.display = 'none';
			document.getElementById("field2").value = "";
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
	 document.getElementById("field3").value = "";
	}

		else{
			document.getElementById('text3').style.display = 'none';
			document.getElementById("field3").value = "";
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
	 document.getElementById("field4").value = "";
	}

		else{
			document.getElementById('text4').style.display = 'none';
			document.getElementById("field4").value = "";
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
	 document.getElementById("field5").value = "";
	}

		else{
			document.getElementById('text5').style.display = 'none';
			document.getElementById("field5").value = "";
		}
}

function showHeadacheOption(val){
	if($('input[name=44]:checked').length<=0){
		dvPassport.style.display = "none";
        dvPassport1.style.display = "none";
        dvPassport2.style.display = "none";
        dvPassport3.style.display = "none";
        dvPassport4.style.display = "none";
        dvPassport5.style.display = "none";
   		 for(var i=2;i<5;i++){
    		$('#44'+i).prop('checked', false);
        }
}
else{
	dvPassport.style.display = "block";
    dvPassport1.style.display = "block";
    dvPassport2.style.display = "block";
    dvPassport3.style.display = "block";
    dvPassport4.style.display = "block";
    dvPassport5.style.display = "block";
		 for(var i=2;i<5;i++){
		$('#44'+i).prop('checked', false);
    }
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
		<div id="mainDiv">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>
					<br>
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
			<h2>Dear <b id="pnameId">*******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	</div>
	<div id="formDiv">
	
						
<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr style="float: right;">

	<td style="background-color: bisque; padding: 0px 0px; width: 90%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 16%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; " >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 13px;"  id="totalpages">10</div></label>
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
	
	<s:form id="form1" action="submitFeedbackNew" namespace="/view/Over2Cloud/patientActivity" method="post" 
	enctype="multipart/form-data" >
		
		<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		
		<s:hidden id="pageNo" name="pageNo" value="3"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="20" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p2," name="page" />
	
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		
		<table id="feedbackform1">
		
		<tr>
			<td align="center" class="g" colspan="3" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; " >Previous Vitals & Investigations</label></td>
		</tr>
		
		<tr>
			<td class="g" style="border-top: 1px solid #fff;border-bottom: 1px solid #fff;"  width="350px"><label id="titlesection" style="color: #fff; " >Please mention if your following blood parameters (if done earlier) are at acceptable levels or not * </label></td>
			<td class="g" style="border-top: 1px solid #fff;border-bottom: 1px solid #fff;"  align="center" width="200px"><label id="titlesection" style="color: #fff; " >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;&nbsp;&nbsp;Don't Know</label></td>
			<td class="g" style="border-top: 1px solid #fff;border-bottom: 1px solid #fff;"  align="center" width="200px"><label id="titlesection" style="color: #fff; " >Mention Value If Any </label></td>
		
		</tr>
				
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Blood Pressure</label></td>
			<td class="c" align="center"><s:radio onchange="$(this).publish('validate2', this, event)" list="#{'Yes':'','No':'','Do not Know':''}" name="33"  title="Blood pressure: (Normal 110/70 to 130/80)" onclick="openDiv(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text1" style="display: none">
				
				<s:textfield name="34" id="field1" value="" title="Blood Pressure: (Normal 110/70 to 130/80)" theme="simple"/>
				
			</div>
</td>
		</tr>
			
		<tr>
			<td class="e"><label id="labelAns" style="color: #000;" >Total Cholesterol</label></td>
			<td align="center" class="f"><s:radio onchange="$(this).publish('validate2', this, event)" list="#{'Yes':'','No':'','Do not Know':''}" name="35"  title="Total Cholesterol(Normal 100 to 180 mg/dl)" onclick="openDiv1(this.value)" theme="simple"></s:radio></td>
			<td align="center" class="f">
			<div id="text2" style="display: none">
				
				<s:textfield name="36" id="field2" title="Total Cholesterol(Normal 100 to 180 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Fasting Blood Sugar</label></td>
			<td class="c" align="center"><s:radio onchange="$(this).publish('validate2', this, event)" list="#{'Yes':'','No':'','Do not Know':''}" name="37"  title="Fasting Blood sugar(Normal 70 to 100 mg/dl)" onclick="openDiv2(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text3" style="display: none">
				
				<s:textfield name="38" id="field3" title="Fasting Blood Sugar(Normal 70 to 100 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
			
			
		<tr>
			<td class="e"><label id="labelAns" style="color: #000;" >Serum Triglycerides</label></td>
	<td align="center" class="f"><s:radio onchange="$(this).publish('validate2', this, event)" list="#{'Yes':'','No':'','Do not Know':''}" name="39"  title="Serum Triglycerides(< 150 mg/dl)" onclick="openDiv3(this.value)" theme="simple"></s:radio></td>
			<td align="center" class="f">
			<div id="text4" style="display: none">
				
				<s:textfield name="40" id="field4" title="Serum Triglycerides(< 150 mg/dl)" value="" theme="simple"/>
				
			</div>
</td>
		</tr>	
		
		<tr>
			<td class="b"><label id="labelAns" style="color: #000;" >Serum Uric Acid</label></td>
			<td class="c" align="center"><s:radio list="#{'Yes':'','No':'','Do not Know':''}" name="41"  title="Normal :Males : 3.5 to 7.2 mg/dl" onclick="openDiv4(this.value)" theme="simple"></s:radio></td>
			<td class="c" align="center">
			<div id="text5" style="display: none">
				
				<s:textfield name="42" id="field5" title="Serum Uric Acid" value="" theme="simple"/>
				
			</div>
</td>
		</tr>
		
		<tr>
			<td colspan="3" class="f"><label id="labelAns" style="color: #000;" >Are you on any regular medication for any medical reasons</label></td>
		</tr>
		
		 <tr>
			<td class="c"  colspan="3">
			<s:textarea onchange="$(this).publish('validate2', this, event)" name="43" style="margin:0px; width:400px; height:31px; resize:none;" title="Are you on any regular Medication for any medical reasons" value="" theme="simple"/>
			
			</td>
		</tr>
		
	
		<tr>
			<td class="f" ></td>
			<td height="17px" align="center" class="f"></td>
			<td align="center" class="f"">
	
</td>
		</tr>
	
		
	</table>
	
	
		<table style=" border-spacing:0;">
			<tr>
			<td align="center" width="888px" class="g" style="background-color: #CC66FF;">&nbsp;&nbsp;&nbsp;<label id="titlesection" style="color: #fff; " >Present Complaints (Do you have any of the following symptoms frequently)</label></td>
			
		</tr>
		
		
		</table>
		
	<table style="width:450px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px;  height:10px; border-spacing: 0px;">
					<tr>
			<td width="255px" style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  class="g">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>CNS *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  class="g"></td>		
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 
			 <input type="checkbox" name="44" id="43" value="None"  onchange="$(this).publish('validate2', this, event)" />
			 </td>
			 
		</tr>			
							
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Headache" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 
			 <input type="checkbox" name="44" id="441" class="gropu1" value="Headache"  onclick="showHeadacheOption(this.value)" />
			 </td>
			 
		</tr>
		
		
		
		<tr>
			 <td  colspan="2"  class="f">
			 <div id="dvPassport" style="display: none">
			 <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Associated with Aura" theme="simple"></s:label></label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="44" id="442"  class="gropu1" id="txtPassportNumber" value="Associated with Aura"   />
			 </div>
			 </td>
		</tr>
		
		<tr>
			 <td  colspan="2" class="c">
			 <div id="dvPassport1" style="display: none">
			 <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Nausea/Vomiting" theme="simple"></s:label></label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 
			 <input type="checkbox" name="44" class="gropu1" id="443"   id="txtPassportNumber1" value="Nausea/Vomiting"  />
			 </div>
			 </td>
		</tr>
		
		<tr>
			 <td  colspan="2" class="f">
			 <div id="dvPassport2" style="display: none">
			 <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Blurred Vision/Flashes of Light" theme="simple"></s:label></label>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			 <input type="checkbox" name="44" class="gropu1" id="444"   id="txtPassportNumber2" value="Blurred Vision/Flashes of light"  />
			 </div>
			 </td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Memory & Concentration" theme="simple"></s:label></label></td>
			 <td  class="c" width="80px" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input type="checkbox" class="gropu1" name="44" id="445" value="Memory & Concentration"  onchange="$(this).publish('validate2', this, event)" />
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td align="center" class="f"><s:textfield name="45" size="18px" style="height:20px" value=""  theme="simple"/></td>
			
		</tr>
			
		<tr>
			<td class="c" ></td>
			<td height="20px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="20px" align="center" class="f"></td>
	
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="22px" align="center" class="c"></td>
	
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="23px" align="center" class="f"></td>
	
		</tr>
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 1px solid #fff;border-bottom: 2px solid #fff;" width="380px" colspan="2" class="g">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Digestive Problems *</b> </label></td>
				
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" id='45' onchange="$(this).publish('validate2', this, event)" value="None" name="46" /></td>
			 
		</tr>	
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Heartburn" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='461' onchange="$(this).publish('validate2', this, event)" value='Heartburn' name="46" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Excessive Flatulence" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='462' onchange="$(this).publish('validate2', this, event)" value="Excessive Flatulence" name="46" /></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Bloating" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='463' onchange="$(this).publish('validate2', this, event)" value="Bloating" name="46" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Burping" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='464' onchange="$(this).publish('validate2', this, event)" value="Burping" name="46" /></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Altered Bowel Movements" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='465' onchange="$(this).publish('validate2', this, event)" value="Altered Bowel Movements (Constipation/Diarrhoea)" name="46" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td align="center" class="f"><s:textfield name="47" size="18px" cssStyle="height:20px; margin-right: 9px;" value="" theme="simple"/></td>
			
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="20px" align="center" class="c"></td>

		</tr>
		
		<tr>
		<td colspan="2" class="f">
		<div id="dvPassport3" style="display: none; height: 20px;">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="" theme="simple"></s:label>
   </label>
</div>
		</td></tr>
		
		<tr>
		<td colspan="2" class="c">
		<div id="dvPassport4" style="display: none; height: 20px;">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="" theme="simple"></s:label>
   </label>
</div>
		</td></tr>
		
		<tr>
		<td colspan="2" class="f">
		<div id="dvPassport5" style="display: none; height: 20px;">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="" theme="simple"></s:label>
   </label>
</div>
		</td></tr>
		
							</table>
				
				
				</td>
			</tr>
	</table>
	
	
	<table style="width:900px; border-spacing:0; ">
			
		
		
		</table>
		
	<table style="width:450px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:450px;  height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  width="255px" class="g">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Skin Problems *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  class="g"></td>		
		</tr>
					
				<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="None" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='47' onchange="$(this).publish('validate2', this, event)"  value="None" name="48" /></td>
			 
		</tr>				
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Itching" theme="simple"></s:label></label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='481' onchange="$(this).publish('validate2', this, event)" value="Itching" name="48" /> </td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Allergies" theme="simple"></s:label></label></td>
			 <td class="f" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='482' onchange="$(this).publish('validate2', this, event)" value="Allergies" name="48" /></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Discolouration" theme="simple"></s:label></label></td>
			 <td align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='483' onchange="$(this).publish('validate2', this, event)" value="Bloating" name="48" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Eruptions" theme="simple"></s:label></label></td>
			 <td align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='484' onchange="$(this).publish('validate2', this, event)" value="Eruptions" name="48" /></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Boils" theme="simple"></s:label></label></td>
			 <td align="center"  class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='485' onchange="$(this).publish('validate2', this, event)" value="Boils" name="48" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td align="center" class="f"><s:textfield name="49" size="18px" style="height:20px" value="" theme="simple"/></td>
			
		</tr>
		<tr>
			<td class="c" ></td>
			<td height="22px" align="center" class="c"></td>
	
		</tr>
		
		
		
		
	</table>
				</td>
				
	
				<td >
				
				
				<table style=" width:425px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  width="380px" class="g">
			&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>Sleeping Pattern *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  class="g"></td>		
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Normal" theme="simple"></s:label></label></td>
			 <td  width="80px" align="center" class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='49' onchange="$(this).publish('validate2', this, event)" value="None" name="50" /> </td>
			 
		</tr>	
	<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Less than 6 Hours" theme="simple"></s:label></label></td>
			 <td  class="c" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='501' onchange="$(this).publish('validate2', this, event)" value="Less than 6 Hours" name="50" /> </td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="More than 8 Hours" theme="simple"></s:label></label></td>
			 <td class="f" align="center" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="502" onchange="$(this).publish('validate2', this, event)" value="More than 8 Hours" name="50" /> </td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Disturbed Sleep " theme="simple"></s:label></label></td>
			 <td  align="center" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='503' onchange="$(this).publish('validate2', this, event)" value="Disturbed Sleep" name="50" /></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Waking in Between Sleep " theme="simple"></s:label></label></td>
			 <td align="center" class="f" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='504' onchange="$(this).publish('validate2', this, event)" onchange="$(this).publish('validate2', this, event)" value="Waking in Between Sleep" name="50" /></td>
		</tr>
		
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;<s:label value="Others" theme="simple"></s:label></label></td>
			 <td align="center" class="c"><s:textfield name="51" size="18px" cssStyle="height:20px; margin-right: 9px;" value="" theme="simple"/></td>
			
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
	<br>
	<center> <div id="pagesDiv" style="margin-left: 275px;"  > </div> </center>
		   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loaderNew.gif"/>" alt="Loading..." style="display:none"/></center>
   <div class="type-button" style="margin-top: 76px; margin-left: 45px;">
   <div id="bt" style="display: block;">
  		<sj:a cssStyle=" margin-left: -249px; margin-top: -66px; color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important; " button="true" href="#"  onclick="backPrevious('1st')"><< Back</sj:a>
     		<sj:submit 
	           targets="datapart" 
	           clearForm="true"
	           value="Next >>" 
	           button="true"
	           cssStyle="   margin-left: -87px; margin-top: -66px;  color: rgb(255, 255, 255) !important; font-size: 82%;  background: #F04E2F !important;"
	           indicator="indicator1"
	           onBeforeTopics="validate2"
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