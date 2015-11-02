<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
int dateFieldCount=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script src="<s:url value="/js/asset/ProcValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


</script>
</head>
<body>
<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Asset >> Procurement</div>
  </div>
 <div style=" float:left;  width:100%;">
 <div class="border">
<div class="rightHeaderButton">
<input class="btn importNewBtn" value="Import Procurement" type="button" onclick="importProcurement();">   
</div>
 <div class="clear"></div>

		         <s:form id="formone" name="formone" action="procurementAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
                 <center>
                    <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                    <div id="errZone" style="float:center; margin-left: 7px">
                    </div>        
                    </div>
                 </center> 
                  
                  <s:iterator value="procurementDDMap" begin="0" end="0">
                  <s:if test="serialNoActive">
                  <s:if test="%{mandatory}">
                      <span id="mandatorywithReciv" class="pIdsReciv" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithInstl" class="pIdsInstl" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithComison" class="pIdsComison" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                   <div class="newColumn">
					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					<div class="rightColumn1">
						<s:if test="%{mandatory}"><span class="needed"></span></s:if>
                            <s:select 
                            id="%{key}"
                            name="assetid" 
                            list="serialNoList"
                            headerKey="-1"
                            headerValue="--Select Asset Name--" 
                            cssClass="select"
                            cssStyle="width:82%"
                            onchange="getAssetDetail(this.value,'1','0','0','0')"
                             >
                            </s:select>
                  	</div>
                  </div>
                  </s:if>
                  </s:iterator>
                  
                  <s:iterator value="procurementColumnMap" var="textFiled" begin="0" end="0">
                  <s:if test="%{mandatory}">
                      <span id="mandatorywithReciv" class="pIdsReciv" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithInstl" class="pIdsInstl" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithComison" class="pIdsComison" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>	
                  </s:if>
	               <div class="newColumn">
					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
	                          	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  	    	<s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" disabled="%{editable}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                          </div>
	                  </div>
                  </s:iterator>
            
				 <s:iterator value="procurementColumnMap" var="textFiled" begin="1">
				 <s:if test="%{mandatory}">
                      <span id="mandatorywithReciv" class="pIdsReciv" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithInstl" class="pIdsInstl" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithComison" class="pIdsComison" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span> 
                 </s:if>
				 	<%if(temp%2==0){ %>
					 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:if test="key=='tax'">
	                  	        <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="getTotalAmount('unitcost','quantity','tax')" />
	                            </s:if>
	                            <s:else>
	                  	        <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </s:else>
	                            </div>
	                  </div>
					<%} else {%>
					 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:if test="key=='tax'">
	                  	        <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="getTotalAmount('unitcost','quantity','tax')" />
	                            </s:if>
	                            <s:else>
	                  	        <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </s:else>
	                            </div>
					  </div>
				 	<%}
				  		temp++;
				 	%>
				 </s:iterator>
             	<s:iterator value="dateColumnMap">
             	<s:if test="key=='receivedon'">
                      	<span id="mandatorywithReciv" class="pIdsReciv" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
                      <s:elseif test="key=='installon'">
                      	<span id="mandatorywithInstl" class="pIdsInstl" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:elseif>
                      <s:elseif test="key=='commssioningon'">
                      	<span id="mandatorywithComison" class="pIdsComison" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:elseif>
                      <s:else>
                      	<span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:else>
             	     <s:if test="%{mandatory}">
                     </s:if>
							<%if(dateFieldCount%2==0){ %>
							
							 <div class="newColumn">
								    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
								    <div class="rightColumn1">
								    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
									<sj:datepicker name="%{key}" id="%{key}" minDate="0" showOn="focus" readonly="true" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date"/>
								    </div>
							</div>
							   <%} else {%>
							 <div class="newColumn">
								  <div class="leftColumn1"><s:property value="%{value}"/>:</div>
								  <div class="rightColumn1">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
								  <sj:datepicker name="%{key}" id="%{key}" minDate="0" showOn="focus" readonly="true" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date"/>
								  </div>
						    </div>
							
							<%}
								dateFieldCount++;
				 			%>
				</s:iterator>
             	<s:iterator value="procurementDDMap" begin="1" end="1">
             	<s:if test="%{mandatory}">
                      <span id="mandatorywithReciv" class="pIdsReciv" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithInstl" class="pIdsInstl" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      <span id="mandatorywithComison" class="pIdsComison" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <s:if test="key=='vendorname'">
                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="vendorname" 
                                list="vendorList"
                                headerKey="-1"
                                headerValue="--Select Vendor--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                onchange="getVendorMobNo(this.value);"
                                >
                                </s:select>
                                </div>
                  </div>
                  </s:if>
                  
                  <!--<s:if test="key=='supportfrom'">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="supportfrom" 
                                list="support4List"
                                headerKey="-1"
                                headerValue="--Select Support From--" 
                                cssClass="select"
                                cssStyle="width:82%"
                                >
                                </s:select>
                                </div>
                  </div>
                  </s:if>
                -->
                </s:iterator>
                
                <div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="buttonStyle" style="text-align: center;">
				 	<div class="clear"></div>
			           <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Add Procurement  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        
	                        />
			              <sj:a 
					     	href="#" 
			               	button="true" 
			               	onclick="viewPage();"
							>
					  	Cancel
						   </sj:a>
				    </div>
				    	<sj:div id="orglevel1"  effect="fold">
                        <div id="orglevel1Div"></div>
                        </sj:div>
             
			</s:form>
</div>
</div>
</body>
</html>