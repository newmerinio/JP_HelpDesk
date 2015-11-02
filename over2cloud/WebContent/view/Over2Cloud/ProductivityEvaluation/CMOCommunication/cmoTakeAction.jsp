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
	 $("#CMOAction").dialog('close');
	
 });
 function showDoc(div)
 {
 	$("#"+div).show();
 }
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

 	 <s:form id="takeActionCMO" name="takeActionCMO" action="takeActionCMOSpecial" namespace="/view/Over2Cloud/ProductivityEvaluationOver2Cloud" theme="simple"  method="post" enctype="multipart/form-data" >
		    <center>
			  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
					<div id="errZone" style="float:center; margin-left: 7px"></div>
			  	</div>
			</center>
			<s:hidden name="specialId" value="%{specialId}"/>
			<s:hidden name="moduleName" value="%{moduleName}"/>
			<s:hidden name="dueDate" value="%{dueDate}"/>
			<s:textfield name="dueTime" value="%{dueTime}"/>
       
         	 <div class="newColumn">
          	   <div class="leftColumn">Remark:</div>
           	   <div class="rightColumn">
          	 	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           		<s:textarea name="comment" id="comment"  maxlength="50"  cssClass="textField" placeholder="Enter Data"/>
      		   </div>
        	 </div>
        	 
             <div class="newColumn">
            		 <div class="leftColumn">Upload 1:</div>
             		 <div class="rightColumn">
	              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	              		<s:file name="docUpload1" id="docUpload1" maxlength="50"  placeholder="Enter Data" cssClass="textField" onchange="showDoc('doc2')"/>
	         </div>
             </div>
		  
		     <div id="doc2" style="display: none;">
	             <div class="newColumn">
	            		 <div class="leftColumn">Upload 2:</div>
              		 <div class="rightColumn">
		              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		              	<s:file name="docUpload2" id="docUpload2" maxlength="50"  placeholder="Enter Data" cssClass="textField" onchange="showDoc('doc3')"/>
		         </div>
	             </div>
		     </div>
		  
		     <div id="doc3" style="display: none;">
	             <div class="newColumn">
	            		 <div class="leftColumn">Upload 3:</div>
              		 <div class="rightColumn">
		              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		              	<s:file name="docUpload3" id="docUpload3" maxlength="50"  placeholder="Enter Data" cssClass="textField" />
		         </div>
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