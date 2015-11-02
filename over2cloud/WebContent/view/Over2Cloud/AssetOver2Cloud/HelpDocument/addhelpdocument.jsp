<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/commanValidation/validation.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton(); }, 4000);
     	 $('#completionResult').dialog('open');
        });


function getLinkName(moduleId,div){
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Help_Doc/getinglinknameformodule.action?moduleId="+moduleId,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Head Office'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function cancelButton()
{
	 $('#completionResult').dialog('close');
	 uploaddocument();
}

</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Document Upload</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Upload</div>
</div>
<div class="clear"></div>
<div style="overflow:false; height:210px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addhelpdata" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
 						<center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                         </center>
<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZoneformtwo" style="float:center; margin-left: 7px"></div></div></center>
		 
	        
   			
   			
   					<span class="pIds" style="display: none; ">modulename#Module Name#D#sc,</span>
                   <div class="newColumn" style=" margin-left: -119px;">
        			   <div class="leftColumn" >Select Module Name:</div>
					        <div class="rightColumn">
					             
				                  <s:select  
				                  			  id="modulename"
				                              name="module_name"
				                              list="moduleNames"
				                              headerKey="-1"
				                              headerValue="Select module" 
				                              cssClass="select"
					                          cssStyle="width:50%"
					                          onchange="getLinkName(this.value,'linkname');"
				                              >
				                  </s:select>
                           </div>
                   </div>
                   
                   <span class="pIds" style="display: none; ">linkname#Link Name#D#sc,</span>
                   <div class="newColumn" style=" margin-left: -119px;">
        			   <div class="leftColumn" >Select Link Name:</div>
					        <div class="rightColumn">
					             
				                  <s:select  
				                  			  id="linkname"
				                              name="link_name"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select module" 
				                              cssClass="select"
					                          cssStyle="width:50%"
				                              >
				                  </s:select>
                           </div>
                   </div>
	        
         
     
	     <span class="pIds" style="display: none; ">file#File#F#doc,</span>
	     <div class="newColumn" style="margin-left: -119px;">
			 <div class="leftColumn" >Select File:</div>
			 <div class="rightColumn">
			     <s:file name="docs" id="file" type="file" multiple="multiple" cssClass="textField" cssStyle=" width: 228px;"/>
			 </div>
		 </div>
	     
	     
	     <div class="clear"></div>
	                  
	  
			
				 <center>
					<div >
	                <sj:submit 
	                        targets="level123" 
	                        clearForm="true"
	                        value=" Upload " 
	                        effect="highlight"
                            effectOptions="{ color : '#222222'}"
                            effectDuration="5000"
	                        button="true"
	                        onBeforeTopics="validate"
	                        onCompleteTopics="level1"
	                        cssStyle="position: absolute;
margin-left: -145px;"
				            />
				            <sj:a cssStyle="" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
				            <sj:a cssStyle="" button="true" href="#" onclick="uploaddocument();">Back</sj:a>
	               </div>
	             <center>
		  
	    
	    
	    <center>
		     <sj:dialog id="ExcelDialog" autoOpen="false" closeOnEscape="true"  modal="true" title="Feedback Draft >> List" width="1150" height="450" showEffect="slide" hideEffect="explode" position="['center','center']">
	                  <center><div id="createExcel"></div></center>
	         </sj:dialog>
         </center>
</s:form>
</div>
</div>

<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
          <div id="complTarget"></div>
</sj:dialog>

</body>
</html>

