<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>

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
	 Registration & Procurement</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    			<s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/Asset" action="assetActionByForm" theme="simple"  method="post" enctype="multipart/form-data" >
				<center>
                  <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            	  <div id="errZone" style="float:center; margin-left: 7px"></div>        
            	  </div>
            	  </center> 
				<s:iterator value="subDept_DeptLevelName" status="status" begin="0" end="0">
	                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                 	 <s:if test="#status.odd">
                          <div class="newColumn">
						  <div class="leftColumn"><s:property value="%{value}"/>:</div>
						  <div class="rightColumn">
                          <span class="needed"></span>
                          <s:select  
	                          id="%{key}"
	                          name="%{key}"
	                          list="deptList"
	                          headerKey="-1"
	                          headerValue="--Select Department--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
                          >
                         </s:select>
                         </div>
                         </div>    
              		</s:if>
              </s:iterator>
			  
		  <s:iterator value="assetDropMap1" var="var" status="status">
                  <s:if test="key=='vendor_for'">  
                  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span> 
                         <div class="newColumn">
						 <div class="leftColumn"><s:property value="%{value}"/>:</div>
	 					 <div class="rightColumn">
	 					 <span class="needed"></span>
                         <s:select 
                         id="%{key}"
                         list="vendorList"
                         headerKey="-1"
                         headerValue="--Select Vendor--" 
                         cssClass="select"
                         cssStyle="width:82%"
                         onchange="commonSelectAjaxCall('createvendor_master','id','vendorname','vendorfor',this.value,'status','Active','vendorname','ASC','vendorname','Select Vendor Name');"
                         >
                         </s:select>
                         </div>
                         </div>
                 </s:if>  
                 <s:if test="key=='vendorname'">  
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span> 
                         <div class="newColumn">
						 <div class="leftColumn"><s:property value="%{value}"/>:</div>
	 					 <div class="rightColumn">
	 					 <span class="needed"></span>
                         <s:select 
                         id="%{key}"
                         name="%{key}" 
                         list="{'No Data'}"
                         headerKey="-1"
                         headerValue="Select Vendor Name" 
                         cssClass="select"
                         cssStyle="width:82%"
                         >
                         </s:select>
                         </div>
                         </div>
                 </s:if>  
              </s:iterator>
              
               <s:iterator value="assetDropMap1" status="status">
                 <s:if test="key=='assettype'">
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                          <div class="newColumn">
						  <div class="leftColumn"><s:property value="%{value}"/>:</div>
						  <div class="rightColumn">
                          <span class="needed"></span>
                          <s:select  
                          id="%{key}"
                          name="%{key}"
                          list="assetTypeList"
                          headerKey="-1"
                          headerValue="--Select Asset Category--" 
                          cssClass="select"
                          cssStyle="width:82%"
                          >
                         </s:select>
                         </div>
                         </div>
                 </s:if> 
              </s:iterator>
				
			  
			  <s:iterator value="assetColumnMap" status="status">
		          <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
				 	<s:if test="#status.odd">
			            <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
                        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                        <s:if test="key=='assetname'">
                 		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data"   cssClass="textField" /><!--
                 		    <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
         				    <div id="SLNoStatus"></div>
         					    <s:hidden id="status"></s:hidden>
                        --></s:if>
                        <s:elseif test="key=='serialno'">
                 		    <s:textfield name="%{key}" id="%{key}" maxlength="50"  placeholder="Enter Data"   cssClass="textField"/>
                        </s:elseif>
                        <s:else>
                 		    <s:textfield name="%{key}" id="%{key}" maxlength="50"  placeholder="Enter Data"   cssClass="textField"/>
                        </s:else>
                        </div>
                        </div>
					</s:if>
              		<s:else>
		                <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
                        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                     	<s:if test="key=='assetname'">
                 		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data"  cssClass="textField" onblur="isExistSLNoAjax(this.value)"/>
                  		<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
          				<div id="SLNoStatus"></div>
          					<s:hidden id="status"></s:hidden>
                        </s:if>
	                    <s:elseif test="key=='serialno'">
	                    	<s:textfield name="%{key}" id="%{key}" maxlength="50"  placeholder="Enter Data" readonly="true"  cssClass="textField"/>
	                    </s:elseif>
	                    <s:else>
	                    	<s:textfield name="%{key}" id="%{key}" maxlength="50"  placeholder="Enter Data"  cssClass="textField"/>
	                    </s:else>
					    </div>
					    </div>
				 	</s:else>
				 </s:iterator>
				
				 <s:iterator value="dateColumnMap">
					 <s:if test="#status.odd">
					 	<div class="newColumn">
					    <div class="leftColumn"><s:property value="%{value}"/>:</div>
					    <div class="rightColumn">
							<sj:datepicker name="%{key}" id="%{key}" minDate="0" showOn="focus" readonly="true" displayFormat="dd-mm-yy"   cssClass="textField" placeholder="Select Date"/>
					    </div>
					</div>
					</s:if>
              		<s:else>
						<div class="newColumn">
					  	<div class="leftColumn"><s:property value="%{value}"/>:</div>
					  	<div class="rightColumn">
					  		<sj:datepicker name="%{key}" id="%{key}" minDate="0" showOn="focus" readonly="true" displayFormat="dd-mm-yy"   cssClass="textField" placeholder="Select Date"/>
					  	</div>
				       </div>
					</s:else>
				</s:iterator>
				
				
				<s:if test="supportTypeActive">
				<s:iterator value="assetDropMap1" status="status">
                 <s:if test="key=='supportfrom'">
                          <div class="newColumn">
						  <div class="leftColumn"><s:property value="%{value}"/>:</div>
						  <div class="rightColumn">
                          <s:select  
                          id="%{key}"
                          name="%{key}"
                          list="supportFrom"
                          headerKey="-1"
                          headerValue="--Select Support From--" 
                          cssClass="select"
                          cssStyle="width:82%"
                          >
                         </s:select>
                         </div>
                         </div>
                 </s:if> 
                 <s:elseif test="key=='supportfor'">   
                         <div class="newColumn">
						 <div class="leftColumn"><s:property value="%{value}"/>:</div>
	 					 <div class="rightColumn">
                         <s:select 
                         id="%{key}"
                         name="%{key}" 
                         list="supportFor"
                         headerKey="-1"
                         headerValue="--Select Support For--" 
                         cssClass="select"
                         cssStyle="width:82%"
                         >
                         </s:select>
                         </div>
                         </div>
                 </s:elseif>  
                             
              </s:iterator>
				</s:if>
<div class="clear"></div>				
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
                        onBeforeTopics  =   "validate"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="getViewData('AssetRegister');"
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