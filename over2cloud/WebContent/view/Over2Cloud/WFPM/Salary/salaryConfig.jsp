<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>

<script type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}

	$.subscribe('level2', function(event,data)
    {
    	setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
        setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
        //$('select').find('option:first').attr('selected', 'selected');
    });

</script>
</head>

<body>
<div class="clear"></div>
	<div class="middle-content">
		<div class="list-icon">
			<%-- <div class="head"><s:property value="%{mainHeaderName}"/> >> Add</div> --%>
			<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
		</div>
			<div class="clear"></div>
<div style="min-height: 10px; height: auto; width: 100%;"></div>


	<s:form id="formOne" name="formOne" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/Salary" 
	        action="saveSalaryConfig" theme="simple"  method="post" enctype="multipart/form-data" >
		<div class="menubox">
	 		<s:if test="holidaysExist">
	 			<div class="newColumn">
               			<div class="leftColumn1"><s:property value="%{holidaysLable}"/>:</div>
               			<div class="rightColumn1"><span class="needed"></span>
               				<s:select 
						id="holidays"
						name="holidays" 
						list="holidayList"
						headerKey="-1"
						headerValue=" Select Holiday In Month " 
						cssClass="textField"
						value="%{hvalue}"
						>
               				</s:select>
               			</div>
				</div>
			</s:if>
               
			<s:if test="allowedLeaveExist">
				<div class="newColumn">
				<div class="leftColumn1"><s:property value="%{allowedLeaveLable}"/>:</div>
				<div class="rightColumn1"><span class="needed"></span>
					<s:select 
						id="allowedLeave"
						name="allowedLeave" 
						list="allowLeaveList"
						headerKey="-1"
						headerValue=" Select Allowed Leave " 
						cssClass="textField"
						value="%{alValue}"
						>
					</s:select>
				</div>
				</div>
			</s:if>
		
	 		<s:if test="deductionExist">
	 			<div class="newColumn">
               			<div class="leftColumn1"><s:property value="%{deductionLable}"/>:</div>
               			<div class="rightColumn1"><span class="needed"></span>
               				<s:select 
						id="deduction"
						name="deduction" 
						list="deductionList"
						headerKey="-1"
						headerValue=" Select Deduction " 
						cssClass="textField"
						value="%{dValue}"
						>
               				</s:select>
               			</div>
				</div>
			</s:if>
               
			<s:if test="extraDayAmountExist">
				<div class="newColumn">
				<div class="leftColumn1"><s:property value="%{extraDayAmountLable}"/>:</div>
				<div class="rightColumn1"><span class="needed"></span>
					<s:select 
						id="extraDayPay"
						name="extraDayAmount" 
						list="extraDayPayList"
						headerKey="-1"
						headerValue=" Select Extra Day Pay " 
						cssClass="textField"
						value="%{edaValue}"
						>
					</s:select>
				<div id="errorlocation" class="errordiv"></div>
				</div>
				</div>
			</s:if>
            
	<!-- Buttons -->
	<div class="clear"></div>
		<div class="buttonStyle">
              <sj:submit 
                      targets="orglevel2" 
                      clearForm="false"
                      value="Save" 
                      effect="highlight"
                      effectOptions="{ color : '#222222'}"
                      effectDuration="5000"
                      button="true"
                      onCompleteTopics="level2"
                      cssClass="submit"
                      indicator="indicator3"
                      onBeforeTopics="validate"
                      />
                      <sj:a 
	          
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formOne');"
								>
								  	Reset
								</sj:a>
	         
                      
		 <sj:div id="orglevel1"  effect="fold">
               			<div id="orglevel2"></div>
          				</sj:div>
	</div>
    	</div>		
</s:form>

</div>
</body>
</html>