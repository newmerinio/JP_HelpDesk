<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });
</script>
</head> 
 <div class="list-icon">
	 <div class="head">
	 Attendance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Starting Up</div> 
</div>
 <div class="clear"></div>
 <div style=" float:left;width:100%;">
 <div class="border">
 <div class="clear"></div>
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="attendanceConfiguration" theme="simple"  method="post" enctype="multipart/form-data" >
		   <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>  	
		    <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>        
            </div>
                <div class="newColumn">
								<div class="leftColumn1">Department:</div>
								<div class="rightColumn1">
                               <span id="mandatoryFields" class="pIds" style="display: none; ">deptName#Department#D#a,</span>
                                <span class="needed"></span>
			                  <s:select  
							    	id=	"deptName"
							    	list="deptDataList"
							    	headerKey="-1"
							    	headerValue="Select"
							    	cssClass="select"
									cssStyle="width:82%"
									onchange="getContactdata(this.value,'empname');"
							       >
							      </s:select>
              </div>
              </div>
     
            
             
   
			  <s:iterator value="attendanceConfigColumnDrop" >
				<s:if test="%{key == 'empname'}">
					   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                       </s:if>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            
	                             <s:select  
							    	id=	"empname"
							    	name="empname"
							    	list="#{'-1':'Select'}"
							    	cssClass="select"
									cssStyle="width:82%"
							       >
							      </s:select>
                              
                              </div>
                      </div>
                  </s:if>
                </s:iterator>
		     	<div class="clear"></div>
		     	<s:iterator value="attendanceConfigColumnDate" status="counter">
		     		   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="%{key}" id="%{key}" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020"  showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Select date"/>
                  </div>
                  </div>
                 </s:iterator>
                 <div class="clear"></div>
		     	 <s:iterator value="attendanceConfigColumnText" status="counter">
		     		   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
				  <s:if test="#counter.count%2 != 0">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield id="%{Key}" name="%{Key}" value=""  cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Enter Data"></s:textfield>
                  </div>
                  </div>
                  </s:if>
                  <s:else>
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield id="%{Key}" name="%{Key}" value=""  cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Enter Data"></s:textfield>
                  </div>
                  </div>	
                 </s:else>
                  </s:iterator>
                  
             	 
             	 <div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<center>
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value=" Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
	                        <sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="resetForm('formone');resetColor('.pIds')">Reset</sj:a>
	               </div>
	               
				</center>
				<center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </center>
	</s:form>	
</div>
</div>
</html>