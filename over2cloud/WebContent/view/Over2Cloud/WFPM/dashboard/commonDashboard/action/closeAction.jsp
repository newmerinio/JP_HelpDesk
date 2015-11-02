<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/WFPM/dashboard/beforeTakeAction.js"></script>
<title>Insert title here</title>
</head>
<body>

<s:hidden name="closeType" value="0"></s:hidden>
		<span id="mandatoryFields" class="pIds" style="display: none; ">statusIdNew#Next Activity#D#,</span>
			<div class="newColumn">
				<div class="leftColumn1">Next Activity:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <s:select 
				     	id="statusIdNew"
				     	name="statusIdNew"
				     	list="clientStatus"
				     	headerKey="-1"
				     	headerValue=" Select Activity "
				     	cssClass="textField"
				     	onchange="showToDate();"
				     	></s:select>
				</div>
			</div>   
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">maxDateTime#Due Date#Date#,</span>
				<div class="leftColumn1">Due Date:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <sj:datepicker name="maxDateTime" id="maxDateTime" minDate="0" changeMonth="true" changeYear="true" 
				     	yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
				     	placeholder="Select Date" timepicker="true" readonly="true" />
				</div>
			</div>   
			<div class="newColumn" style="display: none;" id="showToDateId">
				<div class="leftColumn1">Schedule To:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<sj:datepicker name="to_maxDateTime" id="to_maxDateTime" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true"/>
				</div>
			</div>
		<div class="clear"></div>
			<div class="newColumn">
				<div class="leftColumn1">Comment:</div>
				<div class="rightColumn1">
				     <s:textfield name="comment"  id="comment" cssClass="textField" placeholder="Enter Comment" 
				     cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div> 
</body>
</html>