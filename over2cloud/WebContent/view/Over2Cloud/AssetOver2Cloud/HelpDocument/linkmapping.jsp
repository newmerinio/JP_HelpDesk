<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/commanValidation/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
<title>Id Series Add</title>
	<script type="text/javascript">
	$.subscribe('level1', function(event,data)
	        {
	          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	          setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton(); }, 4000);
	     	 $('#completionResult').dialog('open');
	        });


	function cancelButton()
	{
		 $('#completionResult').dialog('close');
		 helpdocumentmapping();
	}
    
	</script>
	
		<script type="text/javascript">
			function resetForm(formId)
			{
				$('#'+formId).trigger("reset");
			}
		</script>
		
</head>
<body>
	<div class="clear"></div>
	<div class="middle-content">
	<div class="list-icon">
	<div class="head">Document Link Mapping</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div class="container_block" align="center">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/AssetOver2Cloud/Help_Doc" action="modulelinkmapping" theme="simple"  method="post" enctype="multipart/form-data" >
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  <div id="errZone" style="float:left; margin-left: 7px"></div>        
    </div>
	<div class="clear"></div>
	<s:iterator value="idseriesDropDown" status="counter" begin="0">
		<s:if test="%{mandatory}">
		<span id="mandatoryFields" class="dIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		</s:if>
		
		<s:if test="%{key=='module_name'}">
		<span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						
						         <s:select 
						                     id="%{key}"
						                     name="%{key}" 
						                     list="moduleNames"
						                     headerKey="-1"
						                     headerValue="Select module Name" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadPrefixfffff();"
						                     >
						         </s:select>
						</div>
		</div>
		</s:if>
		
		
		<s:elseif test="%{key=='link_name'}">
		<span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						
						         <s:select 
						                     id="%{key}"
						                     name="%{key}" 
						                     list="linkNames"
						                     headerKey="-1"
						                     headerValue="Select Link Name" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadPrefixfffff();"
						                     >
						         </s:select>
						</div>
		</div>
		</s:elseif>
		
		
		
		
		
		
		
		
		
		
	</s:iterator>
					
					<!-- Text box --><!--
					
					
					
					<div class="newColumn">
							<div class="leftColumn1">Start From :</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="startfrom"  id="startfrom"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						
						
						
						
						<div class="newColumn">
							<div class="leftColumn1">Upto :</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="upto"  id="upto"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					
					
					--><div class="clear"></div>
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<center>	
			<div class="buttonStyle">
			<div class="type-button">
	        <sj:submit 
	           			   targets="level123" 
	                       clearForm="true"
	                       value="Save" 
	                       effect="highlight"
	                       effectOptions="{ color : '#ffffff'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="level1"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onBeforeTopics="validate"
	         />
	         <sj:a 
						name="Reset"  
						cssClass="submit" 
						button="true" 
						onclick="resetForm('formone');"
						>
			  			Reset
						</sj:a>
	          <sj:a 
						name="Back"  
						href="#" 
						targets="result" 
						cssClass="submit" 
						indicator="indicator" 
						button="true" 
						onclick="helpdocumentmapping();"
						>
						Back
			</sj:a>
			</div>
			</div>
			   		<sj:div id="idseriesaddition"  effect="fold">
					 <div id="idseriesresult"></div>
					</sj:div>
	   		</center>
	</s:form>
	</div>
	</div>
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