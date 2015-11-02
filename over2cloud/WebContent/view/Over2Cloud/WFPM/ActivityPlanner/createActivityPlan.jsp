<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
 {
   setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
   //setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
   setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton(); }, 4000);
	$('#completionResult').dialog('open');
 });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	// viewGroup();
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}

</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          ><sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
          <div id="complTarget"></div>
</sj:dialog>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addActivityPlan" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
                    <s:iterator value="dateField" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<sj:datepicker id="%{key}"  name="%{key}" minDate="0"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Month"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			 <sj:datepicker id="%{key}"  name="%{key}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Month"/>
		                  				</div>
		                  </div>
	                 </s:else>
                   </s:iterator>
                   <s:iterator value="textBox" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="level123" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
             </s:form>          
</div>
</div>
</body>
</html>

