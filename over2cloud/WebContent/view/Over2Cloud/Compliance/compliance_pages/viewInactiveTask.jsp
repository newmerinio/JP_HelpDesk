<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<script type="text/javascript">

		$.subscribe('makeEffect', function(event,data)
		  {
			/*  setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut();
			 cancelButton();}, 5000); */
			 cancelButton();
		  });
		 function cancelButton()
		 {
			  $('#deleteDialog').dialog('close');
			  gridActivityData();
		 }

</script>

</head>
	<body>
	<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

  <s:form id="complForm" name="complForm" action="editComplBoardData" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" >
   <s:hidden name="id" id="id" value="%{complID}"></s:hidden>
    <center>
    
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
    
	   	  		<div class="newColumn" style="width: 78%">
			  			 <div class="leftColumn">Reason: </div>
	            		 <div class="rightColumn">
				              <span class="needed"></span>
				              		<s:textfield id="reason" name="reason"   cssClass="textField" placeholder="Enter Reason" cssStyle="width: 92%"/>
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
                 		effectDuration	=	"1000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
     		  	 
     		  	  <sj:a 
						button="true" href="#"
						onclick="cancelButton();"
						>
						Cancel
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 
		 </center>
		 <br>
		 <br>
		 </div>
   </s:form>
</div>
</div>
</div>
	</body>
</html>