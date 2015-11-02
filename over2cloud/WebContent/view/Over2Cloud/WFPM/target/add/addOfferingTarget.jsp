<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
</style>
<html>
<head>
<script src="<s:url value="js/WFPM/target/target.js"/>"></script>
<script type="text/javascript">
	$.subscribe('validate2', function(event,data)
        {
		    if($("#total").val() < 100 || $("#total").val() > 100)
		    	{
                    alert("Total weightage should be 100%");		    	
                    event.originalEvent.options.submit = false;
		    	}
        });
</script>
</head>

<body>
<div class="list-icon">
<div class="head">Allot Target</div><div class="head">
<img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
<div class="head">Offering</div>
</div>
<div class="container_block">
<div style="float: left; padding: 20px 1%; width: 98%;">
<div class="form_inner" id="form_reg" style="margin-top: 10px;">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/target" action="addOfferingTarget" theme="simple" method="post" 
	enctype="multipart/form-data">
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
			
	<div class="menubox">	
	<span id="mandatoryFields" class="pIds" style="display: none; ">empId#Employee#D#<s:property value="%{validationType}"/>,</span>	
		<div class="newColumn">
		<div class="leftColumn1">Employee:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:select
						id="empId" 
						name="empId" 
						list="empMap" 
						headerKey="-1"
						headerValue="Select Employee" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="fillOffering(this.value)"
                        >
				</s:select>
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn1">Mobile:</div>
		<div class="rightColumn1">
		     <b><s:label id="mobile" value="NA"></s:label></b>
		</div>
		</div>
		<div class="clear"></div>
		
		<div class="newColumn">
		<span id="mandatoryFields" class="pIds" style="display: none; ">applicableFrom#Applicable From#Date#<s:property value="%{validationType}"/>,</span>
		<div class="leftColumn1">Applicable From:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <sj:datepicker name="applicableFrom" id="applicableFrom" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  
		     	changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="mm-yy" cssClass="textField" 
	     		placeholder="Select Date"/>
		</div>
		</div>
		<div class="clear"></div>
		 
		<!-- Automatically KPIs mapped with employee will come to this div on selection of employee dropdown -->
		<div id="kpiListDiv"></div>
		
	</div>	

	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="KpiResult" 
		     clearForm="true"
		     value="Save" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level1"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate1,validate2"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetFormKPI('formone');"
		>
		  	Reset
		</sj:a> 
	    
	    <div style="display: none;">
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="back('%{targetType}')" 
			cssStyle="margin-left:4px;"
		>
		  	Back
		</sj:a>
		</div>
		
	</div>
	</div>
	<div class="clear"></div>
	<br>
	</s:form>
	<center>
		<sj:div id="KpiResultOuter"  effect="fold">
 	       <div id="KpiResult"></div>
        </sj:div>
   </center>
	</div>
</div>
</div>

</body>
</html>
