	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Client Support Type</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/client/addClientSupportType.js"/>"></script>


<script type="text/javascript">
		$.subscribe('form1', function(event,data)
        {
          setTimeout(function(){ $("#addsupportype").fadeIn(); }, 10);
          setTimeout(function(){ $("#addsupportype").fadeOut(); }, 6000);
         
        });
</script>

	<SCRIPT type="text/javascript">
	function resetForm(formone)
	{
		$('#'+formone).trigger("reset");
	}
	</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Client Support Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>
	
	<div class="container_block">
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div class="border">
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
       	 <br>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/clientsupport" action="addClientSupportType" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
    
	<!-- Text box -->
	
	<s:iterator value="clientTypeTextBox" status="counter">
	<s:if test="%{mandatory}">
	<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>

	<s:if test="#counter.odd">
	<div class="clear"></div>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:if>
	
	<s:else>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:else>
	
	</s:iterator> 
	
         
	<div class="clear"></div>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="addtyperesult" 
            clearForm="true"
            value="Save" 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="form1"
            cssClass="submit"
            indicator="indicator2"
            onBeforeTopics="validate1"
            
          />
           <sj:a 
	     	    name="Reset"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="resetForm('formone');"
				cssStyle="margin-left: 193px;margin-top: -43px;"
				>
				  	Reset
				</sj:a>
	          <sj:a 
		     	name="Cancel"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				cssStyle="margin-left: 145px; margin-top: -25px;"
				onclick="viewClientSupportType()"
				cssStyle="margin-top: -43px;"
				>
				  	Back
				</sj:a>
	    </div>
			<sj:div id="addsupportype"  effect="fold">
	           <div id="addtyperesult"></div>
	        </sj:div>
	</s:form>
	
	</div>
	</div>
	</div>
</body>
</html>