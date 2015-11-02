<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<div class="newColumn">
			<div class="leftColumn1">New Due Date:</div>
			<div class="rightColumn1">
			     <sj:datepicker name="maxDateTime" id="maxDateTime" minDate="0" readonly="true" changeMonth="true" changeYear="true" 
			     yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"
			     timepicker="true" />
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Comment:</div>
			<div class="rightColumn1">
			     <s:textfield name="comment"  id="comment" cssClass="textField" placeholder="Enter Data"
			     cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div>   
</body>
</html>