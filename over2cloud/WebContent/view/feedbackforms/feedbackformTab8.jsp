<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	
	<script type="text/javascript" src="/over2cloud/testJS/jquery.simplePagination.js"></script>
<link type="text/css" rel="stylesheet" href="/over2cloud/testJS/simplePagination.css"/>

<script type="text/javascript">
$(function() {
	var totalPage=105;
	var currentPage=8;
	var type=$("#type").val();	

	if(type === 'M'){
		totalPage=99;
		currentPage=7;
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
</script>	
	<script type="text/javascript">
  $(function() {
  //$( "#datepicker2" ).datepicker();
  });
  </script>
<script type="text/javascript">
$.subscribe('mak', function(event,data){
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			setTimeout(function(){ $("#complTarget").fadeOut(); },2000);
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

var type=$('#type1').val();
if(type === 'undefined' || type === '' || type === null){
	type=$("#type").val();	
}

$( document ).ready(function()
		{
	$("textarea").blur(function(){
	    $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
	});

	$("input").blur(function(){
	    $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
	});	

	$("select").blur(function(){
	    $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
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
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A8",
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
					    		
					    		if(key === '607'){
					    			openDiv(value);
					    		}
					    								
						  //  	console.log(values+"  #key:"+key);
						    	for(a=0;a<values.length;a++){
						    		//console.log(">>  "+values[a]);
						    		$('input[name="' + key.trim()+ '"][value="' + values[a].trim() + '"]').prop("checked", true);
						    	}
				
					    		$('input:text[name="'+key+'"]').val(values[0]);
					    	   $('select[name="'+key+'"]').val(values[0]);
					    	});
							$('#update').val("update");
					    	}
					    },
				   error: function() {
			            alert("error");
			        }
				 });
		});

function openDiv(val){
	
	
	 if(val==='Yes')
		 {		 
		 document.getElementById('text40').style.display = 'block';
		 document.getElementById('text41').style.display = 'block';
		 document.getElementById('text42').style.display = 'block';
		 document.getElementById('text43').style.display = 'block';
		}
	 
	 else{
		 document.getElementById('text40').style.display = 'none';
		 document.getElementById('text41').style.display = 'none';
		 document.getElementById('text42').style.display = 'none';
		 document.getElementById('text43').style.display = 'none';
		}
}

function reset(formId) 
{
	$('#'+formId).trigger("reset");
	openDiv("No");
	document.getElementById("dvPassport").style.display = "none";
	document.getElementById("dvPassport1").style.display = "none";
}

$.subscribe('validate2', function(event,data)
		{
						for(var i=601;i<=612;i++)
						{
							if(i!=602 && i!=603 && i!=604 && i!=608  && i!=609  && i!=610 ) {
							if($('input[name='+i+']:checked').length<=0 && i!=601 && i!=611 && i!=612 )
		 						{
		  							errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
            						setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            						setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		  							$('input[name='+i+']').focus();
									$('input[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
		  							event.originalEvent.options.submit = false;
		  							return false;
		 					}else if($('input[name='+i+']').val().trim().length <= 0){
		 						errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
        						setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        						setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        						$('input[name='+i+']').val('');
	  							$('input[name='+i+']').focus();
								$('input[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
	  							event.originalEvent.options.submit = false;
	  							return false;
			 					}
		 					
						}else if(i!=611 && i!=612 && i!=608  && i!=609  && i!=610 ){
								if($('select[name='+i+']').val().length >= 2 && $('select[name='+i+']').val()==='-1' ){
							//$("#alertDiv").html("Please select Question "+ i +$('input[name='+i+']').attr("title"));
  							//$('#messagealert').dialog('open');
  									errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.  </div>";
            						setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            						setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		  							$('select[name='+i+']').focus();
									$('select[name='+i+']').css({"border": "1px solid red","background": "#FFCECE"});
  									event.originalEvent.options.submit = false;
  									return false;
								}
						}
				}
 		});
  
</script>
<!-- color: #8B8B70; -->
<style type="text/css">
	#label {
  	padding:7px 0;
  	color:#4A4A4A;
	font-weight: !important;
	font:normal 12px arial;
	}
  
.label {
  padding: 7px 0;
  color: #1D0202;
  font-weight: bold !important;
  font: normal 12px arial;
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
  margin-top: -49px;
  text-indent: -56px;
  text-decoration: blink;
  margin-right:56px;
  animation-fill-mode: both;
  font-style: italic;
  text-transform: capitalize;
  font-size: 18px;
    animation-direction: reverse;	
}
.tdLabel{
background-color: #CFB873; 
border-right: 1px solid #fff !important;
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
	{r0w8s6
	background-color: #FF6C47; 
	
	
	}
	
	td.g
	{
	background-color: #FF6C47; 
	
	
	}r0w8s6
	td.h
	{
	background-color: #CCCCFF; 
	
	}
	
	 tr td{
	background-color: #CFB873; 
	margin-left: 7px;
	}
	
	td.b
	{
	background-color: #CFB873; 
	}
	
  	td.c
  	{
  	align-content: center;
  	background-color: #CFB873;
  	border-right: 1px solid #fff !important;
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

<script type="text/javascript">
    function ShowHideDiv(chkPassport) {
        var dvPassport = document.getElementById("dvPassport");
        dvPassport.style.display = chkPassport.checked ? "block" : "none";
    }
</script>

<script type="text/javascript">
    function ShowHideDiv1(chkPassport1) {
        var dvPassport1 = document.getElementById("dvPassport1");
        dvPassport1.style.display = chkPassport1.checked ? "block" : "none";
    }
</script>

</head>

<body style=" #ECECEC width: 1295px;">
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 128px;width: 1095px;height: 637px;">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
									<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
					</div>

	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
	<h2>Dear <b id="pnameId">*******</b>, Welcome To Wellness Questionnaire designed specially for you. <br>Kindly fill all the required information in various sections to assist you better. </h2>
	</div>
	<div id="formDiv" >
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
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 122px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 13px;"  id="totalpages">10</div></label>
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
	<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		
		<s:hidden id="pageNo" name="pageNo" value="11"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="80" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		
		
		<!-- questions -->
		
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		<table id="feedbackform1">
		
		<tr>
			<td colspan="2" align="center" style="background-color: #CC66FF"><label id="titlesection" style="color: #fff; " >Gynaecology</td>
			
		</tr>
		<tr>
			
			<sj:datepicker value="" id="date1" name="601" label="1. Date of last menstrual period" displayFormat="dd/mm/yy" cssStyle="margin-left:16px;"/>						
		</tr>
			
		
		<tr>
			<td class="e" ><label id="labelAns" style="color: #000;" ><b>2. Are your menstrual periods regular</b></label></td>
			<td class="f" >		
		</tr>	
		
		<tr>
			<td width="500px" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Duration</label></td>
			<td width="500px" class="b"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="602" title="Menstrual periods regular Duration" name="602"> 
    <option value="-1">Days</option> 
    <option value="1">1</option>     <option value="2">2</option> 
    <option value="3">3</option>     <option value="4">4</option>	<option value="5">5</option>     <option value="6">6</option>	<option value="7">7</option>     <option value="8">8</option>
    <option value="9">9</option>     <option value="10">10</option>	<option value="11">11</option>     <option value="12">12</option>	<option value="13">13</option>     <option value="14">14</option>
    <option value="15">15</option>     <option value="16">16</option>	<option value="17">17</option>     <option value="18">18</option>	<option value="19">19</option>     <option value="20">20</option>
    <option value="21">21</option>     <option value="22">22</option>	<option value="23">23</option>     <option value="24">24</option>	<option value="25">25</option>     <option value="26">26</option>
    <option value="27">27</option>     <option value="28">28</option>	<option value="29">29</option>     <option value="30">30</option>	<option value="31">31</option>     
    
</select></td>
		</tr>
		
		
		<tr>
			<td width="500px" class="e"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #000;" >Flow</label></td>
			<td width="500px" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="603" title="Menstrual periods regular Flow" name="603"> 
    <option value="-1">Days</option> 
    <option value="1">1</option>     <option value="2">2</option> 
    <option value="3">3</option>     <option value="4">4</option>	<option value="5">5</option>     <option value="6">6</option>	<option value="7">7</option>     <option value="8">8</option>
    <option value="9">9</option>     <option value="10">10</option>	<option value="11">11</option>     <option value="12">12</option>	<option value="13">13</option>     <option value="14">14</option>
    <option value="15">15</option>     <option value="16">16</option>	<option value="17">17</option>     <option value="18">18</option>	<option value="19">19</option>     <option value="20">20</option>
    <option value="21">21</option>     <option value="22">22</option>	<option value="23">23</option>     <option value="24">24</option>	<option value="25">25</option>     <option value="26">26</option>
    <option value="27">27</option>     <option value="28">28</option>	<option value="29">29</option>     <option value="30">30</option>	<option value="31">31</option>     
    
</select></td>
		</tr>
		
		<tr>
			<td width="500px" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Frequency</label></td>
			<td width="500px" class="b"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select title="Menstrual periods regular Frequency" id="604" name="604"> 
    <option value="-1">Select</option> 
    <option value="Heavy">Heavy</option>     <option value="Moderate">Moderate</option> 
    <option value="Scanned">Scanned</option> 
</select></td>
		</tr>
	
		<tr>
			<td width="500px" class="e"><label id="labelAns" style="color: #000;" ><b>3. Are you currently pregnant or breastfeeding?</label></td>
			<td width="500px" class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes<s:radio list="#{'Yes':''}" name="605"  title="Are you currently pregnant or Breastfeeding"  theme="simple"></s:radio>
			<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No<s:radio list="#{'No':''}" name="605"  title="Are you currently pregnant or Breastfeeding"  theme="simple"></s:radio>
			</label></td>
			
		</tr>

		<tr>
			<td width="500px" class="c"><label id="labelAns" style="color: #000;" ><b>4. Do you frequently suffer discomfort during menstruation?</b></label></td>
			<td width="500px" class="b"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes<s:radio list="#{'Yes':''}" name="606"  title="Do you frequently suffer discomfort during menstruation?"  theme="simple"></s:radio>
			<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No<s:radio list="#{'No':''}" name="606"  title="Do you frequently suffer discomfort during menstruation?"  theme="simple"></s:radio>
			</label></td>
		</tr>

	
			<tr>
			<td width="500px" class="e"><label id="labelAns" style="color: #000;" ><b>5. Have you attained menopause?</b></label>
			<td width="500px" class="e"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes<s:radio list="#{'Yes':''}" name="607"  title="Have you Attained Menopause?"  onclick="openDiv(this.value)" theme="simple"></s:radio>
			<label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No<s:radio list="#{'No':''}" name="607"  title="Have you Attained Menopause?" onclick="openDiv(this.value)" theme="simple"></s:radio>
			</label></td>
			
			
			</tr>
			
			<tr>
			<td class="e">
			<div id="text40" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;<b>Surgical</b> </label><br>
				
				</div> 
				
				
				
			<td class="e" colspan="2"> 
			<div id="text41" style="display: none" >
			&nbsp;&nbsp;&nbsp;<s:checkbox name="608" value="unchecked" id="chkPassport" onclick="ShowHideDiv(this)" theme="simple" />
			
			
			</div>
			
				</td> 
				
			
			</tr>
			
			<tr>
		<td colspan="2" class="b">
		<div id="dvPassport" style="display: none">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <s:label value="Reason" theme="simple"></s:label>
   
   <s:textfield name="113" value="" size="18px" type="text" id="txtPassportNumber" cssStyle="height: 20px; border: 1px solid rgb(252, 252, 252); width:176px; margin-left: 472px; background-color: white;" theme="simple"/></label><br><br>
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Year of Surgery" theme="simple"></s:label>
   <s:textfield name="114" value="" size="18px" type="text" id="txtPassportNumber" cssStyle="height: 20px; border: 1px solid rgb(252, 252, 252); margin-left: 433px; width:176px; background-color: white;" theme="simple"/></label>
   
  	
   </label><br>
   
   
</div>
		</td></tr>
		
		
			
			<tr>
			<td class="e">
			<div id="text42" style="display: none">
				<label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;<b>Natural</b> </label><br>
				
				</div> 
				
				
				
			<td class="e" colspan="2"> 
			<div id="text43" style="display: none" >
			&nbsp;&nbsp;&nbsp;<s:checkbox name="608"  id="chkPassport1" value="unchecked" onclick="ShowHideDiv1(this)" theme="simple" />
			</div>
			
				</td> 
				
			
			</tr>
			
			<tr>
		<td colspan="2" class="b">
		<div id="dvPassport1" style="display: none">
    <label id="labelAns" style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Duration" theme="simple"></s:label></label>
   <input type="text" name="610" id="datepicker2" style="margin-left: 469px; width:176px;">
</div>
		</td></tr>
				
			
				 
			
			
			
			
			
			
			<tr>
			<td width="500px" class="e"><label id="labelAns" style="color: #000;" ><b>6. Do you feel subjected to discrimination and harassment at your workplace?</b></label></td>
			<td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="611" value="" title="Do you feel subjected to discrimination and harassment at your Workplace?" theme="simple"/></td>
		</tr>
			
			
			<tr>
			<td width="500px" class="c"><label id="labelAns" style="color: #000;" ><b>7. Do you feel dominated by your family and society?</b></label></td>
			<td class="b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="612" value="" title="Do you feel dominated by your Family and Society?"  theme="simple"/></td>
		</tr>
	<tr>
			<td class="f" ></td>
			<td height="12px" align="center" class="f"></td>
			
	
</td>
		</tr>
	</table>
	<br>
	
		<center> <div id="pagesDiv" style="margin-left: 327px;" > </div> </center>
		
			<div class="clear"></div>
<br>
<center>

	
		   
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