<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<link href="css/style2.css" rel="stylesheet" type="text/css"/>
	<link href="css/style1.css" rel="stylesheet" type="text/css" />
	<link href="css/stylesheet.css" rel="stylesheet" type="text/css" />
	<link href="css/theme1.css" rel="stylesheet" type="text/css" />
	
<title>Meet Request Accept-Reject</title>
<style type="text/css">
.list-icon {
	width:100%;
	float:left;
	height:35px;
	background-color:#e9e9e9;
	border-bottom: 1px solid;
	border-color: #D6D6D6;
}
.clear {
	clear:both;
}
.border{
	width: 95%;
	margin: 1%; 
	border: 1px solid #e4e4e5; 
	-webkit-border-radius: 6px; 
	-moz-border-radius: 6px; 
	border-radius: 6px; 
	-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); 
	-moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); 
	box-shadow: 0 1px 4px rgba(0, 0, 0, .10); 
	padding: 1%; 
	overflow: auto; 
}
.container_block{
	float:left; width:100%; padding:0px 0px 0px 0px;
}
.newColumn{
	float:left; 
	width:50%;
}
.leftColumn1{
	text-align:right;
	padding:6px;
	line-height:25px;
	float:left; 
	width:24%;
}
.rightColumn1{
	text-align:right;
	padding:6px;
	line-height:25px;
	float:left; 
	text-align:left; 
	width:70%;
}
.buttonStyle{
	width: 100%; 
	text-align: center; 
	padding-bottom: 10px;
}
.button, .dropDownButton, .blueBtn {
	overflow: visible;
	padding:3px 7px 4px;
	border:1px solid #c3c3c3 !important;
	color:#222 !important;
	font:normal 11px/13px Verdana, Arial, Helvetica, sans-serif;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
}
.button:hover, .dropDownButton:hover, .blueBtn:hover {
	border:1px solid #888 !important;
}
.head {
float: left;
font-size: 14px;
padding: 0 4px 0 4px;
color: hsl(205, 13%, 33%);
font-weight: bold;
line-height: 35px;
width: auto;
}

</style>
<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#meetrequestaddition").fadeIn(); }, 10);
		         setTimeout(function(){ $("#meetrequestaddition").fadeOut(); }, 4000);
		       });
	</script>
	<script type="text/javascript">
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
	</script>
</head>
<body>
<div class="list-icon">
	<div class="head">Visitor Request Accept-Reject</div> 
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/VAM/master" action="visitactionsubmit" theme="css_xhtml"  method="post" >
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  	<div id="errZone" style="float:left; margin-left: 7px"></div>        
	    </div>
		<div class="clear"></div>
		<s:iterator value="oneList" status="counter">
			<s:if test="%{mandatory}">
			<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
			</s:if>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{key}"/>:</div>
			<div class="rightColumn1">
			<s:textfield name="aaa%{#counter.count}"  id="aaa%{#counter.count}" value="%{value}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
			</div>
			</div>
		</s:iterator>
		<div class="newColumn">
		<div class="leftColumn1">Request Action:</div>
		<div class="rightColumn1">
			<s:select 
				id="statusActionid"
				name="visitorstatusAction" 
				list="#{'Approved':'Approved', 'Rejected':'Rejected'}"
				headerKey="-1"
				headerValue="Select Action" 
				cssClass="select"
				cssStyle="width: 39%;"
				>
			 </s:select>
		</div>
		</div>
		<s:hidden name="srnumberfrommail"  id="srnumberfrommail" value="%{srnumberfrommail}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
		
		<!-- Buttons -->
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        <sj:submit 
	                       targets="meetrequestresult" 
	                       clearForm="true"
	                       value="Save" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       indicator="indicator2"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left; margin-left: 10%;"
	         />
	         			<sj:a 
							name="Reset"  
							button="true" 
							onclick="resetForm('formone');"
						>
			  			Reset
						</sj:a>
			</div>
			</div></div>
					<sj:div id="meetrequestaddition"  effect="fold">
					  <div id="meetrequestresult"></div>
					</sj:div>
	   		</center>	
	</s:form>
</div>
</div>
</div>
</div>
 <!-- <div id="actionresultid"></div> -->
</body>
</html>