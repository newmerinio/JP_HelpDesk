<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>

<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<SCRIPT type="text/javascript">

$("#eplan_from_buddy").multiselect({
	   show: ["", 200],
	   hide: ["explode", 1000]
	});
$("#eplan_from_offering").multiselect({
	   show: ["", 200],
	   hide: ["explode", 1000]
	});
$("#eplan_team").multiselect({
	   show: ["", 200],
	   hide: ["explode", 1000]
	});
function showRoi()
{
	$("#parameter").show();
}
</SCRIPT>
</head>
<body>
		<s:iterator value="packageFields" status="status">
						  <s:if test="%{ !(key.equals('eplan_param_value')) && !(key.equals('eplan_parameter'))  && !(key.equals('eplan_owner'))  
						  && !(key.equals('eplan_parameter'))  && !(key.equals('eplan_pcomments')) 
						   && !(key.equals('eplan_ptotal'))  
						  && !(key.equals('eplan_other'))  && !(key.equals('comments'))  && !(key.equals('sch_from'))  && !(key.equals('sch_to'))  
						  && !(key.equals('for_month'))  && !(key.equals('territory')) 
						   && !(key.equals('schedule_plan'))  && !(key.equals('rel_sub_type')) 
						   && !(key.equals('eplan_rtotal')) && !(key.equals('eplan_rcomments')) }" >
						  
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
               <s:if test="%{key == 'eplan_from_buddy' || key == 'eplan_budget'}">
				       <div class="clear"></div>
				       </s:if>
				    
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				     
				             <s:if test="%{key == 'eplan_type'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="eventMap"
                                      headerKey="-1"
                                      headerValue="Select Event Type"
                                      cssClass="select"
                                      cssStyle="width:80%"
                                      theme="simple"
                                      onchange="fectchRoiParameters(this.id,'parameterRoiFieldsDiv');showRoi();"
                                      >
                          </s:select>
				         </s:if>
				         <s:elseif test="%{key == 'eplan_from_buddy'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="buddyMap"
                                      headerKey="-1"
                                      headerValue="Select Buddy"
                                      cssClass="select"
                                      cssStyle="width:20%"
                                      theme="simple"
                                      multiple="true"
                                      >
                          </s:select>
				         </s:elseif>
				         <s:elseif test="%{key == 'eplan_from_offering'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="offeringMap"
                                      headerKey="-1"
                                      headerValue="Select Offering"
                                      cssClass="select"
                                      cssStyle="width:20%"
                                      theme="simple"
                                      multiple="true"
                                      onchange="fetchMappedTeam(this.id,'teamDiv');"
                                      >
                          </s:select>
				         </s:elseif>
				         <s:elseif test="%{key == 'eplan_team'}">
				         		<div id="teamDiv">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="#{'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Team"
                                      cssClass="select"
                                      cssStyle="width:20%"
                                      theme="simple"
                                      multiple="true"
                                      >
                          </s:select>
                          </div>
				         </s:elseif><!--
				         <s:elseif test="%{key == 'eplan_currency'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="currencyMap"
                                      headerKey="-1"
                                      headerValue="Select Currency"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				         </s:elseif>
				         --><s:elseif test="%{key == 'eplan_unit_type'}">
				         	<s:radio list="#{'Yes':'Yes','No':'No'}" name="%{key}" id="%{key}" value="" onclick="setParameterFields(this.value,'eplan_type');"  theme="simple"/>
				         </s:elseif>
				        <s:elseif test="%{key == 'eplan_address'}">
				        <s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				        </s:elseif>
				        <s:elseif test="%{key == 'eplan_current_month'}">
				        <s:textfield  name="%{key}" id="%{key}" value="%{totalBudget}" readonly="true" maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				        </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
			           <s:if test="%{key == 'eplan_address' || key == 'eplan_team' || key == 'eplan_current_month' } ">
			           				<div class="clear"></div>
			           </s:if>
			           </s:if>
			            <s:elseif test="%{key == 'eplan_parameter'}">
			               <div class="clear"></div>
			               <div id="parameter" style="display: none;">
			            <center>
			           <table width="100%" >
			            <tbody >
							<tr valign="top" >
							   <td width="50%">
							      <center>
							            <table  id="table1" width="100%">
										<tr style="background-color: white; color: black; font-weight: bold;">
											 <td style="padding:2px;margin:1px; text-align:center;" colspan="3">Enter Event Parameters Details</td>
										</tr>
										<tr style="background-color: grey; color: white; font-weight: bold;">
															<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 3%;">Sr.</td>
															<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 8%;">Parameter Name</td>
															<td style="padding:2px;border-right: 1px solid #e7e9e9;margin:1px;text-align:center; width: 10%;">Enter Value</td>
										</tr>
										</table>
							           <div id="parameterFieldsDiv" > Select Unit Type </div>
							      </center>
				             </td>
				             <td width="50%">
				           		 <center>
						            <table  id="table2" width="100%">
										<tr style="background-color: white; color: black; font-weight: bold;">
											 <td style="padding:2px;margin:1px; text-align:center;" colspan="3">Enter ROI Details</td>
										</tr>
											<tr style="background-color: grey; color: white; font-weight: bold;">
												
															<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 3%;">Sr.</td>
															<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 8.4%;">ROI Name</td>
															<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 10%;">Enter Value</td>
											</tr>
										</table>
							            <div id="parameterRoiFieldsDiv" > Select Event Type </div>
				          		 </center>
				             </td>
							</tr>
			            </tbody>
			           </table>
				        </center>
				        </div>
				        </s:elseif>
						</s:iterator>
						</body>
						</html>