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
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
</head>

<body>
	<div class="list-icon">
	<div class="head">Asset >> Repair</div>
</div>
<div style=" float:left; width:100%; height: 350px;">
<div class="border">
     <s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/Asset" action="assetRepairAction" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	 		<center>
	 		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:center; margin-left: 7px"></div>        
            </div>
            </center> 
            
            <s:iterator value="commonDDMap" begin="0" end="0">
	 		<s:if test="%{mandatory}">
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
                                 headerValue="--Select Asset Serial No--" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                 onchange="getAssetDetail(this.value,'1','0','0','0')"
                                  >
                               </s:select>
                               </div>
                  </div>
             </s:iterator>
             
             <s:iterator value="repairColumnMap" begin="0" end="0">
             <s:if test="%{mandatory}">
             <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
              
             <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" disabled="%{editable}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
					            </div>
		     </div>
             </s:iterator>
          
             <s:iterator value="repairColumnMap" begin="1">
             <s:if test="%{mandatory}">
             <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
				 	<%if(temp%2==0){ %>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" disabled="%{editable}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                            </div>
	                  </div>
					<%} else {%>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" disabled="%{editable}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
					            </div>
					  </div>
					
				 	<%}
				  		temp++;
				 	%>
				 </s:iterator>
				 
                  <s:iterator value="commonDDMap" begin="1">
	 			  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            	  </s:if>
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
                  </s:iterator>
                  
				 <s:iterator value="dateColumnMap">
				 <s:if test="%{mandatory}">
            	 <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            	 </s:if>
							<%if(dateFieldCount%2==0){ %>
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<sj:datepicker name="%{key}" id="%{key}"  showOn="focus" readonly="true" minDate="0" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date"/>
								</div>
						   </div>
							   <%} else {%>
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<s:if test="key=='issueat'">
							    <sj:datepicker name="%{key}" id="%{key}" showOn="focus" readonly="true" timepicker="true" timepickerOnly="true" timepickerGridMinute="10" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Time"/>
								</s:if>
								<s:else>
								<sj:datepicker name="%{key}" id="%{key}" showOn="focus" readonly="true" timepicker="true" timepickerOnly="true" timepickerGridMinute="10" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Time"/>
								</s:else>
								</div>
							</div>
							<%}
								dateFieldCount++;
				 			%>
				</s:iterator>
				 
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="buttonStyle" style="text-align: center;">
			       <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value=" Asset Repair  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        />
			          <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewPage();"
						cssStyle="margin-left: 220px;margin-top: -28px;"
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
