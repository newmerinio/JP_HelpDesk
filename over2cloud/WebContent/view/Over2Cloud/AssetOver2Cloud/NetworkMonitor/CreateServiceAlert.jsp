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
	 Service Alert </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    			<s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor" action="configServiceAlert" theme="simple"  method="post" enctype="multipart/form-data" >
				<s:iterator value="assetColumnMap" begin="0" end="0" status="status">
	 			   <s:if test="%{mandatory}">
               		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
                     <div class="newColumn">
					 <div class="leftColumn"><s:property value="%{value}"/>:</div>
					 <div class="rightColumn">
   	                 <span class="needed"></span>
                     <s:select 
                     id="%{key}"
                     name="assetid" 
                     list="serialNoList"
                     headerKey="-1"
                     headerValue="--Select Asset Serial No--" 
                     cssClass="select"
                     cssStyle="width:82%"
                     onchange="getAssetDetail(this.value,'0','0','1','0')"
                      >
                     </s:select>
                     </div>
                  </div>
                </s:iterator>
                
                <s:iterator value="assetColumnMap" begin="1">
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
				
				<div class="clear"></div>
				<s:iterator value="empDetailColumnMap" status="status" begin="0" end="0">
	                 <s:if test="%{mandatory}">
	                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
                 	 <s:if test="#status.odd">
                          <div class="newColumn">
						  <div class="leftColumn"><s:property value="%{value}"/>:</div>
						  <div class="rightColumn">
                          <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                          <s:select  
	                          id="%{key}"
	                          list="contMappedDeptList"
	                          headerKey="-1"
	                          headerValue="--Select Department--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="getEmployeeBydeptId(this.value,'contactid')"
                          >
                         </s:select>
                         </div>
                         </div>    
              		</s:if>
              	</s:iterator>
				
				<s:iterator value="empDetailColumnMap" begin="1" end="1">
	 	          <s:if test="%{mandatory}">
          		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
	 			    <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
            		            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                        <s:select 
                          cssClass="select"
                          cssStyle="width:82%; height:0%;"
                          
                          id="%{key}"
                          name="%{key}" 
                          list="{''}" 
                          headerKey="-1"
                          headerValue="Select Employee Name" 
                          multiple="true"
                          > 
                        </s:select>
                        </div>
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