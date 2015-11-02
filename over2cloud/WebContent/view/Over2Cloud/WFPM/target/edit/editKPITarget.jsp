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
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script src="<s:url value="js/WFPM/target/target.js"/>"></script>
</head>

<body>
<div class="list-icon">
<div class="head">Allot Target</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">KPI</div>
</div>
<div class="container_block">
<div style="float: left; padding: 20px 1%; width: 98%;">
<div class="form_inner" id="form_reg" style="margin-top: 10px;">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/target" action="editKpiTarget" theme="simple" method="post" 
	enctype="multipart/form-data">
	<s:hidden name="empId" value="%{empId}"></s:hidden>
	<s:hidden name="applicableFrom" value="%{applicableFrom}"></s:hidden>
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
			
	<div class="menubox">		
		<div class="newColumn">
		<div class="leftColumn1">Employee:</div>
		<div class="rightColumn1">
			<b><s:label id="empName" value="%{empName}"></s:label></b>
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn1">Mobile:</div>
		<div class="rightColumn1">
		     <b><s:label id="empMobile" value="%{empMobile}"></s:label></b>
		</div>
		</div>
		<div class="clear"></div>
		
		<div class="newColumn">
		<div class="leftColumn1">Applicable From:</div>
		<div class="rightColumn1">
		     <b><s:label id="applicableFrom" value="%{applicableFrom}"></s:label></b>
		</div>
		</div>
		<div class="clear"></div>
		 
		
		
		<!-- Automatically KPIs mapped with employee will come to this div on selection of employee dropdown -->
		<div id="kpiListDiv">
			<table width="100%" cellspacing="0" cellpadding="3" style="margin: auto; width: 80%;"> 
	<tr>
		<td bgcolor="#e7e9e9"
			style="border-bottom: 1px solid #e7e9e9; color: #000000;"
			valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%" >
			<tbody>
				<tr>
					<td width="5%" align="center"><b>Sr.</b></td>
					<td valign="middle" width="5%" align="center">
						<s:checkbox cssClass="select" name="deptID" theme="simple" />
					</td>
					<td valign="middle" width="30%" align="center"><b>KRA</b></td>
					<td valign="middle" width="30%" align="center"><b>KPI</b></td>
					<td valign="middle" width="15%" align="center"><b>Weightage</b></td>
					<td valign="middle" width="15%" align="center"><b>Target Value</b></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>

	<s:iterator value="targetForAlreadyMappedKpi" var="listVal" status="counter">
		<tr>
			<td bgcolor="#ffffff"
				style="border-bottom: 1px solid #e7e9e9; color: #181818;"
				valign="top" class="tabular_cont">
			<table cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td valign="middle" width="5%" bgcolor="#ffffff"
							style="color: #000000; text-align: center;"><s:property
							value="#counter.count" /></td>
						<td width="5%">
							<s:checkbox cssClass="select" value="%{#listVal[4] != '' ? true : false }"
								fieldValue="%{#listVal[0]}" name="kpiId" theme="simple" />
						</td>
						<td valign="middle" align="left" width="30%">
							<s:property value="%{#listVal[1]}" />
							</td>
						<td valign="middle" align="left" width="30%">
							<s:property value="%{#listVal[2]}" />
						</td>
						<td valign="middle" width="15%"><s:textfield
							id="%{#counter.count}" name="weightage" placeholder="Enter Weightage"
							cssClass="textField" readonly="false" value="%{#listVal[3]}"
							theme="simple"></s:textfield></td>
						<td valign="middle" width="15%"><s:textfield
							id="%{#counter.count}" name="targetValue" placeholder="Enter Target"
							cssClass="textField" readonly="false" value="%{#listVal[4]}"
							theme="simple"></s:textfield></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</s:iterator>
	<tr>
		<td bgcolor="#e7e9e9"
			style="border-bottom: 1px solid #e7e9e9; color: #000000;"
			valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td width="5%"></td>
					<td valign="middle" width="20%"></td>
					<td valign="middle" width="20%"><s:textfield id='total'
						theme="simple" readonly="true" cssStyle="width: 25px;">Total Weightage :
									</s:textfield>%</td>
					<td valign="middle" width="20%" id='targetVal'></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>
</table>
		
		</div>
		
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
		     onBeforeTopics="validate"
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
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="back('%{targetType}')" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
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
