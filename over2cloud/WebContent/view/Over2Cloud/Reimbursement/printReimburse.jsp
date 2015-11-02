<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>

$.subscribe('printTickets', function(event,data) {

	if(confirm("Are You Sure Want to Take Print."))
	{
    	var printContents = document.getElementById(divName).innerHTML;
    	var originalContents = document.body.innerHTML;
	    document.body.innerHTML = printContents;
 		window.print();
		document.body.innerHTML = originalContents;
	}
});

function printDiv(divName) {
	
	if(confirm("Are You Sure Want to Take Print."))
	{
		var printContents = document.getElementById(divName).innerHTML;
	    var originalContents = document.body.innerHTML;

	    document.body.innerHTML = printContents;
	    window.print();
	    window.close();
	}
    
}

</script>

</head>
<body>

<!--<c:set var="orgName" value="${orgName}" />
<c:set var="address" value="${address}" />
<c:set var="city" value="${city}" />
<c:set var="pincode" value="${pincode}" />
-->
<div id="print1">
<table width="100%" align="center" border="1">
<tr><td width="100%"><CENTER><h3><i>Dreamsol TeleSolutions Pvt. Ltd.</i></h3></CENTER></td></tr>
</table>
<table width="100%" align="center" border="1">
<tr><td width="100%"><CENTER><h3><b>Conveyance & Other Expenses Claim Form</b></h3></CENTER></td></tr>
</table>
<table border="0" width="100%" align="center" background="#0000FF">
<tr>
		<td>
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Claiment Name</b></font>
		</td>
		<td>
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{userName}"/></b></font>
		</td>
		<td>
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Department</b></font>
		</td>
		<td>
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;IT<s:property value="%{deptName}"/></b></font>
		</td>
	</tr>
</table>
<table border="1"  width="100%" bordercolor="red" background="blue">

	<tr >
	<s:iterator value="masterViewProp" id="masterView" >
		<th>
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{headingName}"/></b></font>
		</th>
	</s:iterator>
	</tr>
	
	<s:iterator value="gridModel" id="tempList" >
	<tr>
	       <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{id}"/></font>
		   </td>
	      	<td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{created_date}"/></font>
		   </td>
		   <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{particulars}"/></font>
		   </td>
		   <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{uploadsFile}"/></font>
		   </td>
		   <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{created_time}"/></font>
		   </td>
		   <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{purpose_id}"/></font>
		   </td>
		   <td>
			 <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{amount}"/></font>
		   </td>
	</tr>
</s:iterator>
</table>

<table border="1"width="100%">
<s:iterator value="userdata" id="userdata">
    <tr>
	<td>
	    <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Signature<s:property value="%{sum_totalaoumt}"/></font>
	</td>
	<td>
	    <font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Total Amount<s:property value="%{purpose_id}"/></font>
	</td>
    </tr>
</s:iterator>
</table>

<table border="1" bordercolor="red" width="100%">
<tr>
 <td><b>
     <i>Kindly Support with Service Call Report, Other Bills, Bonuse Clause Number etc, in Reference Doc. Column for Approval of respective Claim</i></b>
 </td>
</tr>
        
</table>
<br />
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</font></td> 
        </tr>
	</table>
	</div>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('print1')"
                        />
              </center>
              </div> 
	
</body>
</html>

