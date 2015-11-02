<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reassign Action</title>
</head>
<body>
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">empId#Reassign To#D#,</span>
			<div class="leftColumn1">Reassign To:</div>
			<div class="rightColumn1"><span class="needed"></span>
				<s:select
					id="empId"
					name="empId" 
					list="empMap"
					headerKey="-1"
					headerValue=" Select Employee"
					cssClass="textField"
					></s:select>
			</div>
			</div>   
			<div class="newColumn">
				<div class="leftColumn1">Comment:</div>
				<div class="rightColumn1">
				     <s:textfield name="comment"  id="comment" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
</body>
</html>