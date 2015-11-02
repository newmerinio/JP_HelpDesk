<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationScheduleMessage.js"/>"></script>
<script src="<s:url value="/js/common/instantacommanvalidation.js"/>"></script>

<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#composemailsend").fadeIn(); }, 10);
	         setTimeout(function(){ $("#composemailsend").fadeOut(); }, 6000);
	       });

</SCRIPT>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

</script>
</head>
<body>

<div class="list-icon">
	<div class="head">Mail </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Editor</div>
</div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>
   <div id="excelError"></div>   
    <div id="txtError"></div>   
        </div>
 
    <s:form id="formone" name="formone"  namespace="/view/Over2Cloud/WFPM/CRM" action="addcomposedmailtext" theme="simple"  method="post" enctype="multipart/form-data"><!--
        
			   
       <div class="newColumn">
       <div class="leftColumn1">Select Attachment:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		<s:file id="file" type="file" name="docs" multiple="multiple"    cssClass="textField" cssStyle="width: 94%;"/>
										    
   									   
       </div>
       </div>
   
			
--><div class="clear"></div>	
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
			<div class="list-icon" style="height: 25px;">
                  <div class="head" style="margin-top: -4px;">Message</div>
                  </div>
		      		   <table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				    <tbody width="100%">
		      		   <tr>
			   <sjr:ckeditor 
				    name="writemail" 
					id="writemail" 
					rows="10" 
					cols="100" 
					width="1000"
				    height="120"
				    cssClass="margin-left: 41px;"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
				
				/>
		      		   </tr>
		      		   
		      		   </tbody>
		      		   </table>
		      		 
					   <div class="clear"></div>	
				<div class="clear"></div>	
				
                <br>
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="composemailId"
                       clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onBeforeTopics="mailpageId"
         />
         
         	<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="resetForm('formone');"
						cssStyle="margin-top: -28px;"
						>
					  	Reset
		   </sj:a>
         	<!--<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="backtoCRMActivityView();"
						cssStyle="margin-top: -28px;"						
						>
					  	Back
		   </sj:a>
        
        
        --><sj:div id="composemailId"  effect="fold">
                    <div id="composemailsend"></div>
               </sj:div>
		  </center> 
   </div>

</s:form>  
</div>
</div>
</div>
</div>
</body>

</html>
