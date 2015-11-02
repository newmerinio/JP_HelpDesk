<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lost Action</title>
</head>
<body>
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">lostId#Lost Reason#D#,</span>
			<div class="leftColumn1">Lost Reason:</div>
			<div class="rightColumn1"><span class="needed"></span>
				<s:select
					id="lostId"
					name="lostId" 
					list="lostStatusMAP"
					headerKey="-1"
					headerValue=" Select Reason"
					cssClass="textField"
					></s:select>
			</div>
			</div>   
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">RCA#RCA#T#a,</span>
				<div class="leftColumn1">RCA:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <s:textfield name="RCA"  id="RCA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
			
			<div class="clear"></div>
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">CAPA#CAPA#T#a,</span>
				<div class="leftColumn1">CAPA:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <s:textfield name="CAPA"  id="CAPA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
</body>
</html>