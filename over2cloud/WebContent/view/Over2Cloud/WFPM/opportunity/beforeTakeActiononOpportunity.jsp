<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Click View</title>
</head>
<body>
		 <div class="newColumn">
	        <div class="leftColumn">Actual Value:</div>
			<div class="rightColumn"><span class="needed"></span>
		    <s:textfield  id="ammount" name="ammount" cssClass="textField" theme="simple"></s:textfield>
	        </div>
        </div>
        
        <div class="newColumn">
	        <div class="leftColumn">P.O/W.O Ref.No.:</div>
	        <div class="rightColumn">
	        <s:textfield  id="poNumber" name="poNumber" cssClass="textField" theme="simple"></s:textfield>
	        </div>
        </div>
		<div class="newColumn">
	         <div class="leftColumn">Dated:</div>
	         <div class="rightColumn"><span class="needed"></span>
	         <sj:datepicker id="po_date" name="po_date" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select P.O Date"/>
	         </div>
         </div>
		<div class="newColumn">
          <div class="leftColumn">Remarks:</div>
          <div class="rightColumn">
          <s:textfield  id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
	 		<div class="newColumn">
	           <div class="leftColumn">Attachment:</div>
	           <div class="rightColumn">
		       <s:file name="po_attach" id="po_attach"  cssClass="textField"/></div>
		  </div>
		
</body>
</html>
