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
	var currentPage=9;
	var type=$("#type").val();	

	if(type === 'M'){
		totalPage=99;
		currentPage=8;
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

$.subscribe('mak', function(event,data){
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			setTimeout(function(){ $("#complTarget").fadeOut(); },1000);
			 reset('form1');
		  });
		 
		 function cancelButton()
		 {
			 $('#completionResult').dialog('close');
		 }

function closePage(){
	window.location.href = "http://www.medanta.org/";
}


function showErrorField(){
	 errZone.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.</div>";
	 errZone2.innerHTML="<div class='user_form_inputError2'>Please fill the highlighted field.</div>";
	 setTimeout(function(){ $("#errZone").fadeIn(); $("#errZone2").fadeIn(); }, 10);
	
}


$.subscribe('validate2', function(event,data)
		{
						for(var i=700;i<=712;i++)
						{
							if(i!=705 && i!=710 && i!=706){
								$('input[name='+i+']').removeAttr("style");
							if($('input[name='+i+']:checked').length<=0)
		 						{
								showErrorField();
					     		$('input[name='+i+']').css("box-shadow","0 0 13px 0px red inset");
					     		//$('input[name='+i+']').focus();
					     		event.originalEvent.options.submit = false; 	
						  			return false;
								}
							}
					}
						setTimeout(function(){ $("#errZone").fadeOut(); $("#errZone2").fadeOut(); }, 2000); 
 		});


function backPrevious(val)
{
	var traceId=$("#traceid").val();
	var type=$("#type").val();
	var positionPage=parseInt($("#positionPage").val())-2;
	
	var pageNo=7;
	if(type === 'F'){
		pageNo=10;
	}
	
	var done=$("#doneStatus").val();

	var pname=$("#pname").val();
			
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

$( document ).ready(function()
		{


	$("textarea").blur(function(){
		 $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
	});

			$("input:text").blur(function(){
			 $(this).css({"background-color":"white","border":"1px solid rgb(252, 252, 252)"});
		});	
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
				    url : "/over2cloud/view/Over2Cloud/patientActivity/backFormViewChange.action?traceid="+traceId+"&type="+type+"&setNo=A9",
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
						    		//console.log(">>  "+values[a]);
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
 
 
 
 
 
</style>

</head>

<body style=" #ECECEC width: 1295px;">
	<div class="clear"></div>
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 128px;width: 1095px;height: 637px;">
					<div id="innerDiv1">
								<div id="logoDiv"></div>
								<div id="compDetail">hotline no. 
+91 124 <span id="contactNo" style="  color: red;">4141414 </span></div>
					</div>
				
	<div id="innerDiv2" >
	<div id="formTitle" style="display: none">
		<h2>Dear <b>*******</b>, Welcome To Wellness Questionnaire designed specially for you. Kindly fill all the required information in various section to assist you better.</h2>
	</div>
	<div id="formDiv">
	
		<table style="background-color: bisque; padding: 0px 0px; width: 100%;">
	<tr >
	<td style="background-color: bisque; padding: 0px 0px; width: 70%;"><label  style="color: #000; font-weight: bold !important;  font: normal 14px arial; " ><b> Welcome <s:property value="%{pname}"/></b>,</label></td>
	<td style="background-color: bisque; padding: 0px 0px; width: 20%">
	<label  style="color: #990000; font-weight: bold !important;  font: normal 14px arial; margin-left: 114px;" >Page: <s:property value="%{positionPage}"/>/<div style="float: right; margin-right: 15px;"  id="totalpages">10</div></label>
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
	<s:form id="form1" action="submitFeedbackNew" namespace="/view/Over2Cloud/patientActivity" method="post" enctype="multipart/form-data">
<s:hidden id="pname" name="pname" value="%{pname}"></s:hidden>
		<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
		<s:hidden id="type" name="type" value="%{type}"/>
		
		<s:hidden id="pageNo" name="pageNo" value="12"/>
		<s:hidden id="update" name="update" value="add" />
		<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
		<s:hidden id="done" name="done" value="90" />
		<s:hidden id="doneStatus" value="%{done}" name="doneStatus" />
		<s:hidden id="page" value="p9," name="page" />
		
	<!-- questions -->
		
		<div id="questions" style="border: thin; margin-left:46px; clear: both;">
		
		<table style="width:1034px; height:20px;">
			<tr>
			<center><td align="center" class="g" style="background-color: #CC66FF;"><label id="titlesection" style="color: #fff; " >Prakruti Questionnaire</label></center></td>
																					
		</tr>
		
		<tr>
			<td colspan="2" ><label id="labelAns"  ><b> Tick the correct box against each question. All questions can have multiple answers. Select all that is right</b></label></td>
		</tr>
	
		</table>
		
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:504px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>1. How would you describe your memory? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" height="23px"   class="g"></td>		
		</tr>
					
						<tr>
							<td width="380px" class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Quickly Grasps" theme="simple"></s:label></label></td>
			 				<td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Quickly Grasps" list="#{'Quickly Grasps':''}" name="700" theme="simple"/></td>						
						</tr>
						
						<tr>
							<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Easily Forgets" theme="simple"></s:label></label></td>
			 				<td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Easily Forgets" list="#{'Easily Forgets':''}" name="700" theme="simple"/></td>						
						</tr>
						<tr>
			 				<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Recollection Less" theme="simple"></s:label></label></td>
			 				<td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Recollection: Less" list="#{'Recollection Less':''}" name="700" theme="simple"/></td>
						</tr>
						
						<tr>
			 				 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sharp with Details" theme="simple"></s:label></label></td>
			 				<td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Sharp, with details" list="#{'Sharp with Details':''}" name="700" theme="simple"/></td>
						</tr>
						
						<tr>
							 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Clear with Sequence and Order" theme="simple"></s:label></label></td>
							 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Clear, with sequence and order" list="#{'Clear with Sequence and Order':''}" name="700" theme="simple"/></td>
						</tr>
						
						<tr>
			 					<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Considerably Better than Average" theme="simple"></s:label></label></td>
								 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Considerably better than average" list="#{'Considerably Better than Average':''}" name="700" theme="simple"/></td>
						</tr>
						
						<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Slow to Learn" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Slow to learn" list="#{'Slow to Learn':''}" name="700" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Never Forgets" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Never forgets" list="#{'Never Forgets':''}" name="700" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Good Recollection" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Good recollection" list="#{'Good Recollection':''}" name="700" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="12px" align="center" class="f"></td>
		
		</tr>
	
		
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:520px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>2. How would you classify your faiths and belief systems? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" height="23px"   class="g"></td>		
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="No Faith in a Superior Power" theme="simple"></s:label></label></td>
			 <td width="120px" class="c" >&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="No Faith in a superior power" list="#{'No Faith in a Superior Power':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Swings to Radical" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Swings to Radical" list="#{'Swings to Radical':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Keeps Changing" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Keeps Changing" list="#{'Keeps Changing':''}" name="701" theme="simple"/></td>
		</tr>
		
		
		<tr>
			  <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Is a Leader" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Is a Leader" list="#{'Is a Leader':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Is Goal Oriented" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Is Goal oriented" list="#{'Is Goal Oriented':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Likes to be Considered Magnanimous, including by your Enemies" theme="simple"></s:label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Likes to be considered magnanimous,including by your enemies" list="#{'Likes to be Considered Magnanimous,including by your Enemies.':''}" name="701" theme="simple"/></label></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Is Loyal" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="is Loyal" list="#{'Is Loyal':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Has been Constant" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Has been Constant" list="#{'Has been Constant':''}" name="701" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Content with Available Resources" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Content with available resources" list="#{'Content with Available Resources':''}" name="701" theme="simple"/></td>
		</tr>
<tr>
			<td class="f" ></td>
			<td height="12px" align="center" class="f"></td>
			
	
</td>
		</tr>

					</table>
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:504px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>3. Which is predominantly true regarding your dreams? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" height="23px"   class="g"></td>		
		</tr>
					
						<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Flying" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Flying" list="#{'Flying':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Lightning / War etc" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Lightning / War etc" list="#{'Lightning / War etc':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Few Dreams" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Few Dreams" list="#{'Few Dreams':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Anxious" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Anxious" list="#{'Anxious':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="In Colour" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="In colour" list="#{'In Colour':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Romantic" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Romantic" list="#{'Romantic':''}" name="702" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="12px" align="center" class="c"></td>

		</tr>
	
	
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:520px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>4. How would you classify you emotions? *</b> </label></td>
			<td  style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  height="23px"   class="g"></td>		
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Enthusiastic" theme="simple"></s:label></label></td>
			 <td width="140px" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Enthusiastic" list="#{'Enthusiastic':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Warm" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Warm" list="#{'Warm':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Calm" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Calm" list="#{'Calm':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Worries" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Worries" list="#{'Worries':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Angry" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Angry" list="#{'Angry':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Attached" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Attached" list="#{'Attached':''}" name="703" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="12px" align="center" class="c"></td>

		</tr>

		

					</table>
				
				
				</td>
			</tr>
	</table>
	
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:504px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>5. What is your opinion about your mind and its responses? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" height="23px"   class="g"></td>		
		</tr>
					
						<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Quick" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Quick" list="#{'Quick':''}" name="704" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Penetrating" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Penetrating" list="#{'Penetrating':''}" name="704" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Slow" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Slow" list="#{'Slow':''}" name="704" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Adaptable" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Adaptable" list="#{'Adaptable':''}" name="704" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Critical" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Critical" list="#{'Critical':''}" name="704" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Lethargic" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Lethargic" list="#{'Lethargic':''}" name="704" theme="simple"/></td>
		</tr>
<tr>
			<td class="c" ></td>
			<td height="12px" align="center" class="c"></td>
			
	
</td>
		</tr>
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:520px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>6. How would you describe your body frame? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  height="23px"   class="g"></td>		
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Slim" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Slim" list="#{'Slim':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Medium Built" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Medium built" list="#{'Medium Built':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Large" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Large" list="#{'Large':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Bony / Skinny" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Bony / Skinny" list="#{'Bony / Skinny':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Loose Muscle Type" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Loose muscle type" list="#{'Loose Muscle Type':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Firm Muscular" theme="simple"></s:label></label></td>
			  <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Firm Muscular" list="#{'Firm Muscular':''}" name="707" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="12px" align="center" class="c"></td>
			
	
</td>
		</tr>

		

					</table>
					
	
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:504px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>7. Describe your finger nails? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  height="23px"   class="g"></td>		
		</tr>
					
						<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Thin" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Thin" list="#{'Thin':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Pink" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Pink" list="#{'Pink':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="White" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="White"  list="#{'White':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Cracking" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Cracking" list="#{'Cracking':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Medium" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Medium" list="#{'Medium':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Wide" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Wide" list="#{'Wide':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Growth Defects of the Nails / Diseases of the Nail" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Growth defects of the nails /diseases of the nail" list="#{'Growth Defects of the Nails / Diseases of the Nail':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Soft" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Soft" list="#{'Soft':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Thick" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Thick" list="#{'Thick':''}" name="708" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="17px" align="center" class="f"></td>

		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="15px" align="center" class="c"></td>

		</tr>
						
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:520px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>8. How would you describe your bowel movements? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  height="23px"   class="g"></td>		
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Small Quantity" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Small quantity" list="#{'Small Quantity':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Moderate to Large Quantity" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Moderate to large quantity" list="#{'Moderate to Large Quantity':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Solid" theme="simple"></s:label></label></td>
			  <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Solid" list="#{'Solid':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Hard" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Hard" list="#{'Hard':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Loose" theme="simple"></s:label></label></td>
			  <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Loose" list="#{'Loose':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Neither Constipated Nor Loose" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Neither constipated nor loose" list="#{'Neither Constipated Nor Loose':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Gas" theme="simple"></s:label></label></td>
			  <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Gas" list="#{'Gas':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Often Causing Burning Sensation and Irritability in GI Tract" theme="simple"></s:label></label></td>
			  <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="'Often causing burning sensation and irritability in GI tract" list="#{'Often Causing Burning Sensation and Irritability in GI Tract':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			  <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Constipated" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Constipated" list="#{'Constipated':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Frequent Motions" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist  title="Frequent Motions" list="#{'Frequent Motions':''}" name="709" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="c" ></td>
			<td height="12px" align="center" class="c"></td>
			
	
</td>
		</tr>

		

					</table>
					
	
				
				
				</td>
			</tr>
	</table>
	
	<table style="width:980px; height:60px; border-spacing:0; ">
			<tr>
				<td>
					<table style=" width:504px; height:10px; border-spacing: 0px;">
					<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"  colspan="2" class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>9. Describe your appetite on a normal day (normal physical activity and health)? *</b> </label></td>
					
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Variable" theme="simple"></s:label></label></td>
			 <td  width="130" class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Variable" list="#{'Variable':''}" name="711" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Strong" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Strong" list="#{'Strong':''}" name="711" theme="simple"/></td>
		</tr>
		
		<tr>
			  <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Sharp" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Sharp" list="#{'Sharp':''}" name="711" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Constant" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Constant" list="#{'Constant':''}" name="711" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Low" theme="simple"></s:label></label></td>
			  <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Low" list="#{'Low':''}" name="711" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="f" ></td>
			<td height="20px" align="center" class="f"></td>
	
		</tr>	
		
		<tr>
			<td class="c" ></td>
			<td height="20px" align="center" class="c"></td>
	
		</tr>	
						
						
					</table>
				</td>
				
				
				
				
				<td >
				
				
				<table style=" width:520px; height:10px; border-spacing: 0px">
						<tr>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;" width="380px" height="23px"   class="g">
			&nbsp;&nbsp;&nbsp;<label id="labelAns" style="color: #fff;" ><b>10. How would your friends describe your eyes? *</b> </label></td>
			<td style="border-top: 0px solid #fff;border-bottom: 2px solid #fff;"  height="23px"   class="g"></td>		
		</tr>
		
		<tr>
			<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Small" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Small" list="#{'Small':''}" name="712" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Reddish" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Reddish" list="#{'Reddish':''}" name="712" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Wide" theme="simple"></s:label></label></td>
			  <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Wide" list="#{'Wide':''}" name="712" theme="simple"/></td>
		</tr>
		
		<tr>
			 <td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Unsteady" theme="simple"></s:label></label></td>
			  <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Unsteady" list="#{'Unsteady':''}" name="712" theme="simple"/></td>
		</tr>
		
		<tr>
			<td class="c"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="Focused" theme="simple"></s:label></label></td>
			 <td class="c">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="Focused" list="#{'Focused':''}" name="712" theme="simple"/></td>
		</tr>

		<tr>
			<td class="f"><label id="labelAns" style="color: #000;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label value="White" theme="simple"></s:label></label></td>
			 <td class="f">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist title="White" list="#{'White':''}" name="712" theme="simple"/></td>
		</tr>

<tr>
			<td class="c" ></td>
			<td height="18px" align="center" class="c"></td>
			
	
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
						</center>	<br>
						<center> <div id="pagesDiv" style="margin-left: 327px;" > </div> </center>
	<!-- Buttons -->
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
</div>
</body>

</html>