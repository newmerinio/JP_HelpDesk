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
	 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
  });

</SCRIPT>
</head>
<body>
    	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/Asset" action="assetGroupAllotmentAction" theme="simple"  method="post" enctype="multipart/form-data" >
		<center>
	  		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
	  		</div>
	    </center>
	    <s:hidden id="assetid" name="assetid" value="%{assetId}"/>
	    <s:hidden id="outletid" name="outletid" value="%{outletId}"/>
		<s:iterator value="allotmentRadio">
	       	<div class="newColumn">
		       	<div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       	<div class="rightColumn">
			       	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
			       	<s:radio list="allottoFlagValue" name="%{key}" id="%{key}" value="%{'O'}" onclick="findHiddenDiv(this.value);" />
		       	</div>
	       	</div>
	   	</s:iterator>	
	   	<div id="blockAll" style="display: none">
	   	<s:iterator value="commonDDMap" begin="0" end="0">
	       	<s:if test="%{key=='deptid'}">
	       	<div class="newColumn">
		       	<div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       	<div class="rightColumn">
			       	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
			       	<s:select  
                       	id					=		"%{key}"
                       	name				=		"%{key}"
                       	list				=		"outletMappedDept"
                       	headerKey			=		"-1"
                       	headerValue			=		"Select Department" 
                       	cssClass			=		"select"
						cssStyle			=		"width:82%"
                       	onchange			=		"getEmployeeBydeptId(this.value,'contactid');"
						>
					</s:select>
		       	</div>
	       	</div>
	       </s:if>
	      </s:iterator>	
	      </div>	
	      <s:iterator value="commonDDMap" begin="1" end="1">
	       <div id="blockContact" style="display: none">
	       <s:if test="%{key=='contactid'}">
	       	<div class="newColumn">
		       	<div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       	<div class="rightColumn">
			       	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
			       	<s:select  
                       	id					=		"%{key}"
                       	name				=		"%{key}"
                       	list				=		"{''}"
                       	headerKey			=		"-1"
                       	headerValue			=		"Select Employee" 
                       	multiple			=		"true"
                        cssClass			=  		"select"
						cssStyle			=		"width:82%;height:40%"
						>
					</s:select>
		       	</div>
	       	</div>
	       </s:if>
	       </div>
	   	</s:iterator>	
			
				
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
				
		<center>
			<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="fields">
		<center>
		<ul>
			<li class="submit" style="background: none;">
				<div class="type-button">
		        	<sj:submit 
	         				targets			=	"resultTarget" 
	         				clearForm		=	"true"
	         				value			=	" Save " 
	         				button			=	"true"
	         				cssClass		=	"submit"
	                 		indicator		=	"indicator2"
	                 		effect			=	"highlight"
		                    effectOptions	=	"{ color : '#222222'}"
	                 		effectDuration	=	"5000"
	                 		onCompleteTopics=	"makeEffect"
	                        indicator		=	"indicator2"
	                        
	     		  	  />
	     		  	  <sj:a 
							button="true" href="#"
							onclick="resetForm('formone');"
							>
							Reset
					  </sj:a>
	     		  	  <sj:a 
							button="true" href="#"
							onclick="getCloseData();"
							>
							Back
						</sj:a>
	        	</div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="resultTarget"  effect="fold"></sj:div>
		 </div>
   		 </s:form>
</body>
</html>