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


function getLeaveStatus(value) 
{
	mydiv = document.getElementById('grp');
	if(value=="Absent")
	{
		if( mydiv.style.display = 'none')
		{
			 mydiv.style.display = 'block';
		}
	}
	else if(value=="Half day")
	{
		if( mydiv.style.display = 'none')
		{
			 mydiv.style.display = 'block';
		}
	}
	else if(value=="Present")
	{
		if( mydiv.style.display = 'block')
		{
			 mydiv.style.display = 'none';
		}
	}
}



</script>
</head>

 <div class="list-icon">
	 <div class="head">
	 Attendance </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Mark</div> 
</div>
 
 <div style=" float:left;width:100%;">
 <div class="border">
 <div class="clear"></div>
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="AttendanceAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
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
            
        
   
				 <s:iterator value="attendanceMarkColumnDropDown" >
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
		     	 
		     		 <s:iterator value="attendanceMarkColumnDate" status="counter">
		     		   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Select To date"/>
                  </div>
                  </div>
                  </s:iterator>
                   <div class="clear"></div>
		     		 <s:iterator value="attendanceMarkColumnTime" status="counter">
		     		   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					<s:if test="#counter.count%2 != 0">
					 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker timepicker="true" timepickerOnly="true" readonly="true" name="%{key}" id="%{key}"   showOn="focus" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Select From Time"/>
                  </div>
                  </div>
                  </s:if>
                  <s:else>
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker timepicker="true" timepickerOnly="true" readonly="true" name="%{key}" id="%{key}" value="0"  showOn="focus" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Select From Time"/>
                  </div>
                  </div>	
                 </s:else>
                  </s:iterator>
                  
		     	  <s:iterator value="attendanceMarkColumnText">
		     		 <s:if test="%{key == 'eworking'}">
		     		    <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                       </s:if>
                      <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <s:textfield id="%{Key}" name="%{Key}"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Enter Data"></s:textfield>
                      </div>
                      </div>
                      </s:if>
                    </s:iterator>
                  
                   <s:iterator value="attendanceMarkColumnDropDown" begin="1" end="1" >
                     <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'1','0','0.5','Holiday'}"
                              headerKey="-1"
                              headerValue="Select Status" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  </s:select>
                  </div>
                  </div>
                  </s:iterator>
                   <div class="clear"></div>
                   <s:iterator value="attendanceMarkColumnDropDown" status="counter" begin="2" end="3">
                     <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					<s:if test="#counter.count%2 != 0">
					   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                    <s:select 
		                              id="%{key}"
		                              name="%{key}"
		                              list="{'Present','Absent','Half day','Holiday'}"
		                              headerKey="-1"
		                              headerValue="Select Attendance Type" 
		                              cssClass="select"
                                      cssStyle="width:82%"
		                              onchange="getLeaveStatus(this.value,'grp');"
		                              >
                                </s:select>
                  </div>
                  </div>	
                  </s:if>
                  <s:else>
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'None','Full Day','Half day'}"
                              headerKey="-1"
                              headerValue="Select Client Visit " 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  </s:select>
                  </div>
                  </div>
                  </s:else>
                  </s:iterator>
                  
                  	<div  style="display: none" id="grp">
                    <s:iterator value="attendanceMarkColumnDropDown" status="counter" begin="4" end="5">
                      <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                       </s:if>
					  <s:if test="#counter.count%2 != 0">
					   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="#{'Call':'Call','SMS':'SMS','VMN':'Emergency leave','Mail':'Pre-Schedule Leave','No Intimation':'Uninformed Leave'}" 
                       
                              headerKey="-1"
                              headerValue="Select Update Via" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                                </s:select>
	                  </div>
	                  </div>
	                  </s:if>
                     <s:else>
                     <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                 		    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="employeelist1"
                              headerKey="-1"
                              headerValue="Select Update To" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                               </s:select>
                  </div>
                  </div>
                  </s:else>
                  </s:iterator>
                  </div>
                  
                   <s:iterator value="attendanceMarkColumnText">
					  <s:if test="%{key == 'comment'}">
					      <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                       </s:if>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textarea id="%{Key}" name="%{Key}" value="%{empmob}" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Enter Data"  ></s:textarea>
                  </div>
                  </div>
                  </s:if>
                  </s:iterator>
             	 <div class="clear"></div> 
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
	  <sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="viewAttendanceMark();">Back</sj:a>             
	               </div>
				</center>
				<div class="clear"></div>
				<center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </center>
	</s:form>	
</div>
</div>
</html>