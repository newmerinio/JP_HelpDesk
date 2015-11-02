<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lost Opportunity On Action</title>

<script type="text/javascript">
 function showDiv()
 	{
	 	//var data=$("#lostId").val();
	 	var data=$("#lostId option:selected").text();
	       if(data=="Other")
	       {
	        document.getElementById("showotherreason").style.display="block";
	       }
	       else
	       {
	    	   document.getElementById("showotherreason").style.display="none";
	       }
 	}
</script>
</head>
<body>
			<div class="newColumn">
			<div class="leftColumn">Support Type:</div>
			<div class="rightColumn">
			<s:if test="%{mandatory}">
			<span class="needed"></span>
			</s:if>
			<s:select 
				id="supportTypeId"
				name="support_type" 
				list="supportTypeMAP"
				headerKey="-1"
				headerValue="Support Type" 
				cssClass="select"
	            cssStyle="width:82%"
				>
			</s:select>
			</div>
			</div>
			
			<div class="newColumn">
				<div class="leftColumn">Comments:</div>
				<div class="rightColumn">
				     <s:textfield name="comments"  id="comments" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
			
			<div class="newColumn">
	         <div class="leftColumn">Support From:</div>
	         <div class="rightColumn"><span class="needed"></span>
	         <sj:datepicker id="support_from" name="support_from" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select P.O Date"/>
	         </div>
		    </div>
		    <div class="newColumn">
			         <div class="leftColumn">Support To:</div>
			         <div class="rightColumn"><span class="needed"></span>
			         <sj:datepicker id="support_till" name="support_till" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select P.O Date"/>
			         </div>
		    </div>
			<div class="newColumn">
				<div class="leftColumn">Support P.O.:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}">
				<span class="needed"></span>
				</s:if>
				<s:file name="po_attach" id="po_attach"  cssClass="textField"/>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn">Support Agreement:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}">
				<span class="needed"></span>
				</s:if>
				<s:file name="agreement_attach" id="agreement_attach"  cssClass="textField"/>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn">Amount:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}">
				<span class="needed"></span>
				</s:if>
				<s:textfield name="amount"  id="amount"  cssClass="textField" placeholder="Enter Data" theme="simple"/>
				</div>
			</div>
			<s:hidden name="actionType" value="%{actionType}" id="actionType" /> 
</body>
</html>