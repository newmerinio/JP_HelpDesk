<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="<s:url value="/js/buddy/buddy.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });
</script>

</head>
<body>
<div class="clear"></div>
	<div class="list-icon">
		<div class="head">Buddy Setting</div>
	</div>
<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
	 <div class="border">
	<sj:accordion id="accordion" autoHeight="false" active="0">
       <sj:accordionItem title="Buddy >> Add Floors" >
       
	 	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/BuddyOver2Cloud/BuddySetting" action="buddyAddFloor" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			 				<center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
              
		      <s:iterator value="buddyFloorColumnMap" begin="0" end="1">
		          <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
				 	<%if(temp%2==0){ %>
				 	
					            <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="isExistSLNoAjax(this.value)"/>
	                            </div>
	                            </div>
					<%} else {%>
				                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="isExistSLNoAjax(this.value)"/>
							    </div>
							    </div>
					 
				 	<%}
				  		temp++;
				 	%>
				 </s:iterator>
				<!-- Buttons -->
				<br><br>
				<br><br>
				
				<div class="clear"></div>
				
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="buttonStyle" style="text-align: center;">
			         <sj:submit 
			            targets="orglevel1" 
                        clearForm="true"
                        value="  Add  " 
                        effect="highlight"
                        effectOptions="{ color : '#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="level1"
                        cssClass="submit"
                        indicator="indicator2"
                        onBeforeTopics="validate"

			          />
			          <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewPage();"
						cssStyle="margin-left: 180px;margin-top: -25px;"
						>
					  	Cancel
						</sj:a>
				    </div>
				    	<sj:div id="orglevel1"  effect="fold">
                        <div id="orglevel1Div"></div>
                        </sj:div>
			</s:form>
			
			</sj:accordionItem>
			<sj:accordionItem title="Buddy >> Add Sub-Floors" onclick="getFloorInfo('testDiv');">
			<s:form id="formone1" name="formone1" namespace="/view/Over2Cloud/BuddyOver2Cloud/BuddySetting" action="buddyAddSubFloor" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
   			<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
			 		
			 	<center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                </center>
              <s:iterator value="buddyDropDowmColumnMap" status="counter">
                          <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                 <s:if test="%{mandatory}">
                                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <span class="needed"></span>
                                <div id="testDiv">
                                <s:select  
                                id="%{key}"
                                name="%{key}"
                                list="{'No Data'}"
                                headerKey="-1"
                                headerValue="Select Location" 
                                cssClass="select"
                                cssStyle="width:82%"
                                >
                               </s:select>
                               </div>
                               </div>
                               </div>    
              </s:if>
              <s:else>
                                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<div id="testDiv">
                                <s:select 
                                id="%{key}"
                                name="%{key}"
                                list="{'No Data'}"
                                headerKey="-1"
                                headerValue="Select Location" 
                                cssClass="select"
                                cssStyle="width:82%"
                                 >
                                </s:select>
                                </div>
                                </div>
                                </div>
                              
              </s:else>
              
              </s:iterator>
              
		      <s:iterator value="buddyFloorColumnMap" begin="2" end="4">
		          <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
				 	<%if(temp%2==0){ %>
				 	
					            <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="isExistSLNoAjax(this.value)"/>
	                            </div>
	                            </div>
					<%} else {%>
				                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onblur="isExistSLNoAjax(this.value)"/>
							    </div>
							    </div>
					 
				 	<%}
				  		temp++;
				 	%>
				 </s:iterator>
				 
				<s:iterator value="subDept_DeptLevelName" status="status">
                 <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                 <s:if test="#status.odd">
                                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select  
                                id="%{key}"
                                name="%{key}"
                                list="deptList"
                                headerKey="-1"
                                headerValue="Select Department" 
                                cssClass="select"
                                cssStyle="width:82%"
                                onchange="getSubDept(this.value,'subDeptDiv1',%{deptHierarchy},'1','0','0','0','1');"
                                >
                               </s:select>
                               </div>
                               </div>    
              </s:if>
              <s:else>
                                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <div id="subDeptDiv1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="%{key}"
                                list="{'No Data'}"
                                headerKey="-1"
                                headerValue="Select Sub-Department" 
                                cssClass="select"
                                cssStyle="width:82%"
                                 >
                                </s:select>
                                </div>
                                </div>
                                </div>
              </s:else>
              </s:iterator>	
              <div class="clear"></div>
               <s:iterator value="buddyFloorColumnMap" begin="5">
	  			 	  <s:if test="%{mandatory}">
               		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
	  			 	  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <div id="EmpDiv1">
	                            <s:select 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              id="%{key}"
	                              name="employeeid" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                              > 
	                            </s:select>
	                            </div>
	                            </div>
	                  </div>
	                  </s:iterator> 
		
				<!-- Buttons -->
				<br><br>
				<br><br>
				
				<div class="clear"></div>
				
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="buttonStyle" style="text-align: center;">
			         <sj:submit 
			            targets="orglevel111" 
                        clearForm="true"
                        value="  Add  " 
                        effect="highlight"
                        effectOptions="{ color : '#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="level1"
                        cssClass="submit"
                        indicator="indicator2"
                        onBeforeTopics="validate"

			          />
			          <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewPage();"
						cssStyle="margin-left: 180px;margin-top: -25px;"
						>
					  	Cancel
						</sj:a>
				    </div>
				    	<sj:div id="orglevel111"  effect="fold">
                        <div id="orglevel1Div"></div>
                        </sj:div>
			</s:form>
			
			
			</sj:accordionItem>
			</sj:accordion>
		</div>  
	 </div>
</body>
</html>