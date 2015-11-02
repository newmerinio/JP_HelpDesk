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
	 Spare Receive</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    			<s:form id="formone" name="formone" action="spareReceiveAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
                  <center>
                  <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            	  <div id="errZone" style="float:center; margin-left: 7px"></div>        
            	  </div>
            	  </center> 
            	  
                  <s:iterator value="commonDDMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <s:if test="spareCatgActive">
                 <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                list="spareCatgMap"
                                headerKey="-1"
                                headerValue="--Select Spare Category--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                onchange="getSpareName(this.value,'spareNameDiv')"
                                 >
                               </s:select>
                               </div>
                 </div>
                  </s:if>
                  <s:else>
                 <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="spare_name" 
                                list="spareNameMap"
                                headerKey="-1"
                                headerValue="--Select Spare Name--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                  >
                  		        </s:select>	
                                </div>
                 </div>               
                 </s:else>
                 </s:iterator>
                 
                  <s:if test="spareNonMoveAltActive && spareCatgActive">
                  <s:iterator value="commonDDMap" begin="1" end="1">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                   <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <div id="spareNameDiv">
                                <s:select 
                                id="%{key}"
                                name="spare_name" 
                                list="{'No Data'}"
                                headerKey="-1"
                                headerValue="--Select Spare Name--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                 >
                              </s:select>
                              </div>
                              </div>
                  </div>
                  </s:iterator>
                  </s:if>
              
                  <s:iterator value="commonDDMap" begin="2" end="2">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <s:if test="vendorActive">
                   <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                 id="%{key}"
                                 name="vendorname" 
                                 list="vendorMap"
                                 headerKey="-1"
                                 headerValue="--Select Vendor--" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                  >
                                </s:select>
                               </div>
                   </div>            
                  </s:if>
                  </s:iterator>
                  
                  <s:iterator value="dateMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
					  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
					            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" readonly="true" id="%{key}" changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				 	</div>
                  </s:iterator>
                  
				  <s:iterator value="spareColumnMap" status="status">
				  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
					<s:if test="#status.odd">
					 
					    <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:if test="key=='tax'">
	                  	        <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssClass="textField" onblur="getTotalAmount('unitcost','quantity','tax')" />
	                            </s:if>
	                            <s:else>
	                  	        <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data"  cssClass="textField"/>
	                            </s:else>
	                            </div>
	                  </div>
					</s:if>
			 		<s:else>
					    <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <s:if test="key=='tax'">
	                  	       <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssClass="textField" onblur="getTotalAmount('unitcost','quantity','tax')" />
	                           </s:if>
	                           <s:else>
	                  	       <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data"  cssClass="textField"/>
	                           </s:else>
	                           </div>
					  </div>
					
				 	</s:else>
				 </s:iterator>
				
                  <s:iterator value="dateMap" begin="1">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  	  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="%{key}" id="%{key}" changeMonth="true" readonly="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
	                            </div>
				 	</div>
                  </s:iterator>
                
			<br>
			<br>
				<div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="clear"></div>
				<div class="buttonStyle" style="text-align: center;">
			         <sj:submit 
	                        targets="spareReceive1" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="spareReceive"
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
						onclick="getViewData('SpareReceiveDetails');"
						>
						Back
					</sj:a>
				    </div>
				    	<sj:div id="spareReceive1"  effect="fold">
                           <div id="spareReceive1Div"></div>
                        </sj:div>
			</s:form>
   </div>
</div>
</body>
</html>