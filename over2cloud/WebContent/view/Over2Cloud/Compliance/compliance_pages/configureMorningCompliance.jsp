<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#moduleName").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});

function getMorningReport()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/ViewMorningReport.jsp",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Configure Morning Report </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">
<s:form id="configMorningReport" name="configMorningReport" action="configMorningReport"  theme="simple"  method="post"enctype="multipart/form-data" >
 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
    <span id="mandatoryFields" class="pIds" style="display: none; ">moduleName#Module Name#D#,</span>
    <span id="mandatoryFields" class="pIds" style="display: none; ">date#Start Date#Date#,</span>
    <span id="mandatoryFields" class="pIds" style="display: none; ">time#Start Time#Time#,</span>
			<div class="newColumn">
          	<div class="leftColumn">Module Name:</div>
           	<div class="rightColumn">
            <span class="needed"></span>
           	<s:select  
                        id		    ="moduleName"
                        name		="moduleName"
                        list		="appDetails"
                        cssClass="textField"
		 				cssStyle="width:5%"
		 				multiple="true"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">Start Date:</div>
           	<div class="rightColumn">
            <span class="needed"></span>
           	<sj:datepicker id="date" name="date"  readonly="true" cssClass="textField" size="20" minDate="0" value="today" changeMonth="true" changeYear="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Start Date"/>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">Start Time:</div>
           	<div class="rightColumn">
            <span class="needed"></span>
           	<sj:datepicker id="time" name="time" readonly="true" placeholder="Enter Start Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
           	</div>
          	</div>
	      
	        
			<div class="clear"></div>
        	<div class="clear"></div>
        	<br>
			<div class="fields">
			<center>
		 	<ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
     		  	    <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('configMorningReport'); resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						id="viewtaskname"
						onclick="getMorningReport();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		  <center><sj:div id="complTarget"  effect="fold"> </sj:div></center>
		 </center>
</div>
</s:form>
</div>
</div>
</div>
</div>
</body>
</html>