<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Opportunity On Action</title>
</head>
<body>
	<div class="newColumn">
	<div class="leftColumn">First Value:</div>
	<div class="rightColumn">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="opportunityValue"  id="opportunityValue"  value="%{opportunityValue}"  cssClass="textField" placeholder="Enter Data" theme="simple" readonly="true"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn">Date:</div>
	<div class="rightColumn">
	<s:textfield name="closureDate"  id="closureDate"  value="%{closureDate}"  cssClass="textField" placeholder="Enter Data" theme="simple" readonly="true"/>
	
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn">Current Expected Value:</div>
	<div class="rightColumn">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="opportunity_value"  id="opportunity_value"  cssClass="textField" placeholder="Enter Data" theme="simple"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn">Expected Date:</div>
	<div class="rightColumn">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<sj:datepicker id="closure_date" name="closure_date" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Expected Date"/>
	</div>
	</div>
         	<div class="newColumn">
			<div class="leftColumn">Forecast Category:</div>
			<div class="rightColumn">
			             <s:select  
			                   id="forecast_category"
			                   name="forecast_category" 
			                   list="forecastcategoryMap"
			                   headerKey="-1"
			                   headerValue="Select" 
			                   cssClass="select"
			                   value="%{preforecastcategory}"
			                   cssStyle="width:82%"
			                   >
			              </s:select>
			</div>
			</div>
            <div class="newColumn">
                <div class="leftColumn">Sales Stages:</div>
                <div class="rightColumn">
                      <s:select
                      id="salesStageId"
                      name="sales_stages"
                      list="salesStageMap"
	  				  headerKey="-1"
                      headerValue="Select"
                      cssClass="select"
                      value="%{presalesStage}"
                      cssStyle="width:82%" 
                      >
                      </s:select>
               </div>
             </div>    
	<div class="newColumn">
	<div class="leftColumn">Remarks:</div>
	<div class="rightColumn">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="comments"  id="comments" cssClass="textField" placeholder="Enter Data" theme="simple"/>
	</div>
	</div>
	
	<s:hidden name="presalesStage"  id="presalesStage" value="%{presalesStage}" cssClass="textField" placeholder="Enter Data" theme="simple"/>
	<s:hidden name="preforecastcategory"  id="preforecastcategory"  value="%{preforecastcategory}" cssClass="textField" placeholder="Enter Data" theme="simple"/>
		
</body>
</html>
