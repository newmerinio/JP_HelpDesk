<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });

</script>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
</head>


 <div style=" float:left; padding:5px 0%; width:100%;">
 <div class="border">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="responseAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
		     	<s:hidden name="id" id="id" value="%{#parameters.id}" ></s:hidden>
		     	<s:hidden name="validate" id="validate" value="%{#parameters.validate}" ></s:hidden>
		     		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                         <div id="errZone" style="float:left; margin-left: 7px"></div>        
                       </div>
		     		 <s:iterator value="responseColumnText">
					  <s:if test="%{key == 'name'}">
	                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}" value="%{empname}" readonly="true" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                   </div>
                   </div>
                   </s:if>
                   <s:if test="%{key == 'email'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if><s:textfield id="%{Key}" name="%{Key}" value="%{empemail}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>	
                  </s:if>
                  </s:iterator>
                   <div class="clear"></div> 
                  <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'mobno'}">
	              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if><s:textfield id="%{Key}" name="%{Key}" value="%{empmob}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>
                  </s:if>
                  </s:iterator>
		  
				 <s:iterator value="responseColumnDropdown">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'status'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
	                              id="%{key}"
	                              name="%{key}" 
	                              list="{'Approve','Disapprove'}"
	                              headerKey="-1"
	                              headerValue="Select Status" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              >
                               </s:select>
                               </div>
                      </div>        
                      </s:if> 
                 </s:iterator>  
                 <div class="clear"></div> 
				 <s:iterator value="responseColumnText">
				         <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
				 	<s:if test="%{key == 'comment'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                    <s:textarea id="%{Key}" name="%{Key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textarea>
			                    </div>
			         </div>           
			         </s:if>
                  </s:iterator>
             	
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div> 
				<center>
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value=" OK " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
	                  
	               </div>
			</center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
	</s:form>	
</div>
</div>
</html>