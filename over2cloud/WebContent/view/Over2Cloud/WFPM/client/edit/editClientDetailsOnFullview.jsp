<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#resultEditClientId").fadeIn(); }, 10);
          setTimeout(function(){ $("#resultEditClientId").fadeOut();cancelButton(); }, 6000);
      });
	$.subscribe('validate', function(event,data)
			{
		$("#completionResult").dialog('open');
			});
	 function cancelButton()
	 {
	 $('#completionResult').dialog('close');
	 $('#clientClientDialog').dialog('close');
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
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
<sj:div id="resultEditClientId"  effect="fold"><div id="clienteditId"></div></sj:div>
  </sj:dialog>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/client" action="editClient" theme="simple" method="post" 
	enctype="multipart/form-data">
	<s:hidden name="id" value="%{id}" />
	
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
	
	<div class="menubox">		
	<s:iterator value="masterViewProp" status="counter">
		<s:if test='%{colType == "T"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:if>
    </s:iterator>
	
	<s:iterator value="masterViewProp" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'sourceMaster'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="sourceMasterMap"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
			
			<s:elseif test="%{colomnName == 'weightage_targetsegment'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
						    name="%{colomnName}"
							list="weightageMasterMap" 
							headerKey="-1"
							headerValue="Select Target Segment" 
							value="%{value}"
							cssClass="select"
						    cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
			
		<!--<s:elseif test="%{colomnName == 'referedBy'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
						    name="%{colomnName}"
							list="#{'Associate':'Associate','Client':'Client'}" 
							headerKey="-1"
							headerValue="Select Refered By" 
							value="%{value}"
							cssClass="select"
						    cssStyle="width:82%"
							onchange="fillName(this.value,'refName',0)"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'refName'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select'}" 
							headerKey="-1"
							value="%{value}"
							cssClass="select"
                        	cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		-->
		<!--<s:elseif test="%{colomnName == 'starRating'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Select Rating" 
                            value="%{value}"
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'location'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="location_master_dataMap"
							headerKey="-1"
							headerValue="Select Location"
							value="%{value}" 
							cssClass="select"
                            cssStyle="width:82%">
				</s:select>
			</div>
			</div>	
		</s:elseif>
		--><s:elseif test="%{colomnName == 'acManager'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="%{colomnName}" 
						name="%{colomnName}"
			            list="employee_basicMap"
			            headerKey="-1"
			            headerValue="Select Name" 
			            value="%{value}"
		                cssClass="select"
	                    cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'industry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="industry" 
						name="%{colomnName}"
						list="industryMap"
			            headerValue="Select Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
                       	onchange="fillName(this.value,'subIndustry',0)"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'subIndustry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="subIndustry" 
						name="%{colomnName}"
						list="subIndustryMap"
	                    headerKey="-1"
	                    headerValue="Select Sub-Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
			
			<!--<s:elseif test="%{colomnName == 'sales_stages'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="sales_stages" 
						name="%{colomnName}"
						 list="salesStageMap"
	                    headerKey="-1"
	                    headerValue="Select Sales Stages" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'forecast_category'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="forecast_category" 
						name="%{colomnName}"
						list="forecastcategoryMap"
	                    headerKey="-1"
	                    headerValue="Select Forecast Category" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
	-->
	</s:if>
	<s:if test='%{colType == "Date"}'>
		<s:if test="%{colomnName == 'closure_date'}">
				<div class="newColumn">
				<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
				<div class="rightColumn1"><s:if test="%{mandatroy == true}"></s:if>
				    <sj:datepicker name="closure_date" value="%{value}" id="closure_date" showOn="focus"	displayFormat="dd-mm-yy" yearRange="2014:2020" 	readonly="true" cssClass="textField"  cssStyle="margin: 0px 6px 10px; width: 80%;"></sj:datepicker>
				</div>
				</div>	
			</s:if>
	</s:if>
	<s:if test="%{#status.even}">
		<div class="clear"></div>
	</s:if>	
	</s:iterator>	
	</div>

<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="clienteditId" 
		     clearForm="true"
		     value="Save" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level1"
		     onBeforeTopics="validate"
		     cssClass="submit" 
		     indicator="indicator2"
	     />
	  
	</div>
	</div>
	<div class="clear"></div>
	<center>
             </center>
	<br>	
</s:form>
</body>
</html>