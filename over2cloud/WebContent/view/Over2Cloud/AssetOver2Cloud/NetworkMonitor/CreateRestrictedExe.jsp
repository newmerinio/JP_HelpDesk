<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
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
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Restricted Exe</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    			<s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor" action="AddRestrictedExe" theme="simple"  method="post" enctype="multipart/form-data" >
					<center>
		  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
		  	</div>
		    </center>
				<jsp:include page="/view/Over2Cloud/AssetOver2Cloud/Asset/assetSearchData.jsp"/>
				 <div id="assetDetailsDiv">
				<s:hidden  id="assetid" name="assetid" />
	   			 
				<s:iterator value="netMonitorColumnMap" begin="0" end="0" status="status">
	 			   <s:if test="%{mandatory}">
               		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
                     <div class="newColumn">
							<div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  		    <s:textfield  id="serialno1" maxlength="50"  placeholder="Enter Data" readonly="true"  cssClass="textField"/>
                            </div>
	                  </div>
                </s:iterator>
                
                <s:iterator value="assetColumnMap" status="status">
                      <s:if test="%{mandatory}">
               		       <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
				 	<s:if test="#status.odd">
					  <div class="newColumn">
							<div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  		    <s:textfield  id="%{key}" maxlength="50"  placeholder="Enter Data" readonly="true"  cssClass="textField"/>
                            </div>
	                  </div>
					</s:if>
             		<s:else>
					<div class="newColumn">
							<div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  		    <s:textfield  id="%{key}" maxlength="50" placeholder="Enter Data" readonly="true"   cssClass="textField"/>
				            </div>
				    </div>
				 	</s:else>
				</s:iterator>
				
				</div>
			<s:iterator value="netMonitorColumnMap" begin="1" end="1" status="status">
        	<div class="clear"></div>
        	<s:if test="%{mandatory}">
	        	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       	</s:if>
				<div class="newColumn">
					<div class="leftColumn"><s:property value="%{value}"/>:</div>
			           <div class="rightColumn"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%; margin-left: 1px;"/>
					        </div>
					  <div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -3px;margin-top: 1px;">+</sj:a></div>
				</div>
	        </s:iterator>
	      
	      
		  <s:iterator begin="100" end="118" var="indx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#indx}"/>" style="display: none">
		  <s:iterator value="netMonitorColumnMap" begin="1" end="1">
		  <div class="newColumn">
				 <div class="leftColumn"><s:property value="%{value}"/>:</div>
			     <div class="rightColumn"><sj:textfield name="%{key}" id="%{key}" placeholder="Enter Data" cssClass="textField" />
				 <div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%">	
					<s:if test="#indx==118">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#indx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#indx+1}')" button="true" cssStyle="margin-left: -12px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#indx}')"  button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		  </div>
		  </s:iterator>
		 </div>
		 </s:iterator>
		 
		 <br><br><br><br><br><br><br>
				
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
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
						onclick="getViewData('NetMonitor');"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 </div>
   </s:form>
   </div>
</div>
</body>
</html>