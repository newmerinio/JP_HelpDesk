<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
 {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 1000);
	 $("#kaizenAction").dialog('close');
	 $("#improvedAction").dialog('close');
 });
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

  <s:form id="takeActionData" name="takeActionData" action="takeActionKaizen" namespace="/view/Over2Cloud/ProductivityEvaluationOver2Cloud" theme="simple"  method="post" enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
	<s:hidden name="kaizenId" value="%{kaizenId}"/>
	<s:hidden name="moduleName" value="%{moduleName}"/>
           <div class="newColumn">
          		 <div class="leftColumn">Action:</div>
           		 <div class="rightColumn">
             		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
             		<s:select  
                             id		    ="status"
                             name		="status"
                             headerValue="Select Action"
                             list		="#{'Already Running':'Already Running','Can Not Be Implemented':'Can Not Be Implemented','Implemented':'Implemented'}"
                             headerKey	="-1"
                             cssClass="select"
							 cssStyle="width:82%;"
                             >
                   </s:select>
        	</div>
        	   </div>
         	<div class="newColumn">
          	   <div class="leftColumn">Comments:</div>
           	   <div class="rightColumn">
          	 	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           		<s:textarea name="comment" id="comment"  maxlength="50"  cssClass="textField" placeholder="Enter Data"/>
      		</div>
        	 </div>
          
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
         				value			=	" Action " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
	        </div>
	        </li>
		 </ul>
		 </center>
		 <br>
		 <br>
		 	 <center><sj:div id="complTarget"  effect="fold"> </sj:div></center>
		 </div>
   </s:form>
</div>
</div>
</div>
</div>
</body>
</html>