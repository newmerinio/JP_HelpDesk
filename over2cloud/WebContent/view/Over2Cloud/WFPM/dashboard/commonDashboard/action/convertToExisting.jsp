<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Convert To Existing</title>
</head>
<body>
       <div class="newColumn">
        <div class="leftColumn1">Value:</div>
		<div class="rightColumn1"><span class="needed"></span>
	    <s:textfield  id="ammount" name="ammount" cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"></s:textfield>
        </div>
        </div>
        
		<div class="newColumn">
			<div class="leftColumn1">P.O /W.O Ref.No.:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:textfield name="poNumber"  id="poNumber" cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Dated:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <sj:datepicker name="poDate" id="poDate" readonly="true" value="today" changeMonth="true" changeYear="true" 
			     yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date" />
			</div>
		</div> 
		
		 <div class="newColumn">
           <div class="leftColumn1">Attachment:</div>
           <div class="rightColumn1">
	       <s:file name="po_attach" id="po_attach"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"/></div>
		  </div>
	 				
	     <div class="newColumn">
          <div class="leftColumn1">Remarks:</div>
          <div class="rightColumn1">
          <s:textfield  id="comments" name="comments" cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"></s:textfield>
        </div>
        </div>  
</body>
</html>