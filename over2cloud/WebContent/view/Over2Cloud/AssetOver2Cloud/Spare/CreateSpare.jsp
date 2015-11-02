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
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/SpareValidation.js"/>"></script>
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
	 Spare </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    			<s:form id="formone" name="formone" action="spareAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
                  <center>
                  <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            	  <div id="errZone" style="float:center; margin-left: 7px"></div>        
            	  </div>
            	  </center> 
            	  
                  <s:iterator value="commonDDMap" begin="0" end="0">
                  <s:if test="spareCatgActive">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="normalPIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            	  <span id="nonMoveAlert" class="NMApIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                 <div class="newColumn">
								<div class="leftColumn"><s:property value="%{spareCatgHeadName}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="spare_category" 
                                list="spareCatgMap"
                                headerKey="-1"
                                headerValue="--Select Spare Category--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                >
                               </s:select>
                  </div>
                  </div>
                  </s:if>
                  </s:iterator>
                  
				   <s:iterator value="spareColumnMap" begin="0" end="2">
				   <s:if test="%{mandatory}">
            	   <span id="normal" class="normalPIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   <span id="nonMoveAlert" class="NMApIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
					<s:if test="#status.odd">
					 
					  <s:if test="key!='non_move_level'">
				 	<div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </div>
					  </div>
					  </s:if>
					</s:if>
			 		<s:else>
					<s:if test="key!='non_move_level'">
					<div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </div>
				 	 </div>
				 	 </s:if>
				 	</s:else>
				 </s:iterator>
				 
				  <div class="clear"></div>
                  <s:if test="spareNonMoveAltActive">
                  <div class="newColumn">
								<div class="leftColumn">Non Move Alert</div>
								<div class="rightColumn">
          			            <input type="radio" id="no"  name="non_move_alert" value="0" checked onchange="hideBlock('altBlock');">No
          			            <input type="radio" id="yes" name="non_move_alert" value="1"  onchange="showBlock('altBlock');" >Yes
    			  </div>
    			  </div>
                 </s:if>
                 
				<div id="altBlock" style="display :none"> 
			    <div class="clear"></div>
				<s:iterator value="spareColumnMap" begin="3" end="3">
				<s:if test="%{mandatory}">
            	<span id="nonMoveAlert" class="NMApIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
                <s:if test="key=='non_move_level'">
				<div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </div>
				</div>
				</s:if>
                </s:iterator>
             
                <s:iterator value="commonDDMap" begin="1" end="1">
                <s:if test="%{mandatory}">
            	<span id="nonMoveAlert" class="NMApIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
                  <s:if test="spareNonMovePeriodActive">
                  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="non_move_period" 
                                list="nonMovePeriodMap"
                                headerKey="-1"
                                headerValue="--Select Non-Move Period--" 
                                cssClass="select"
                                cssStyle="width:80%"
                                 >
                                </s:select>
                               </div>
                  </div>             
                    
                  </s:if>
                 </s:iterator>
				</div> 
			
				<br>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<center>
					<div class="clear"></div>
					
					<div class="buttonStyle" style="text-align: center;">
			         <sj:submit 
	                        targets="spare1" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="spare"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
			          <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  	</sj:a>
     		  	  	<sj:a 
						button="true" href="#"
						onclick="getViewData('SpareDetails');"
						>
						Back
					</sj:a>
				    </div>
				    	<sj:div id="spare1"  effect="fold">
                    <div id="spare1Div"></div>
               </sj:div>
               </center>
			</s:form>
   </div>
</div>
</body>
</html>