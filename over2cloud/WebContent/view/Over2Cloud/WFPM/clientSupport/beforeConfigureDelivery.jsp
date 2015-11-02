<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Click View</title>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
	function fetchEmployeeForDept(dept,empNameId)
	{
		
		if(dept == null || dept == '-1')
			return;
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/clientsupport/loadEmploye.action?",
			data: "dept="+dept, 
			success : function(data) {
				$('#empNameId option').remove();
				$('#empNameId').append($("<option></option>").attr("value",-1).text('Select'));
				$(data).each(function(index)
				{
				   $('#empNameId').append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			},
			error: function() {
				alert("error");
			}
			});
	}
</script>

</head>
<body>
 <div class="clear"></div>	
		<div class="newColumn">
	         <div class="leftColumn">Delivery Date:</div>
	         <div class="rightColumn"><span class="needed"></span>
	         <sj:datepicker id="delivery_date" name="delivery_date" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select P.O Date"/>
	         </div>
         </div>
          <div class="clear"></div>	
        <div class="newColumn">
	        <div class="leftColumn">Delivered By:</div>
	        <div class="rightColumn">
	        <s:select 
              id="department"
              name="department" 
              list="deptMap"
              headerKey="-1"
              headerValue="Select Department" 
              cssClass="select"
              cssStyle="width:82%"
              onchange="fetchEmployeeForDept(this.value,'empNameId');"
              >
             </s:select>
	        </div>
        </div>
		 <div class="newColumn">
	        <div class="leftColumn">Name:</div>
	        <div class="rightColumn">
	        <s:select 
			id="empNameId"
			name="empName" 
			list="#{'-1':''}"
			headerKey="-1"
			headerValue="Select Employee" 
			cssClass="select"
			cssStyle="width:82%"
			 >
			</s:select>
	        </div>
        </div>
        <s:iterator value="contactNameToConfigure" var="contact">
        	<div class="newColumn">
			<div class="leftColumn">Contact Person:</div>
			<div class="rightColumn"><span class="needed"></span>
			<s:textfield  id="contact_person" name="contact_person" value="%{contactName}" cssClass="textField" theme="simple"></s:textfield>
			</div>
			</div>   
			
			<div class="newColumn">
				<div class="leftColumn">Ownership:</div>
				<div class="rightColumn">
				 <s:select 
					id="ownershipId" 
					name="ownership"
					list="#{'Primary':'Primary', 'Secondary':'Secondary', 'Tertiary':'Tertiary'}" 
					headerKey="-1"
					headerValue="Select Ownership" 
					cssClass="button"
					theme="simple"
					cssStyle="width: 82%;"
					>
				</s:select>
				</div>
			</div>
        </s:iterator>
        
			
			
			<div class="clear"></div>	
			 <div class="newColumn">
	           <div class="leftColumn">Installation Report:</div>
	           <div class="rightColumn">
		       <s:file name="ins_attachement" id="ins_attachement"  cssClass="textField"/></div>
		  </div>
			
			<div class="newColumn">
	          <div class="leftColumn">Details:</div>
	          <div class="rightColumn">
	          <s:textfield  id="details" name="details" cssClass="textField" theme="simple"></s:textfield>
	        </div>
	        </div>
	        <div id="newDiv" style="float: right;margin-top: -35px;margin-right: 30px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
	 	
	 	<s:iterator begin="100" end="103" var="deptIndx">
		<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
	 		<div class="newColumn">
	          <div class="leftColumn">Details:</div>
	          <div class="rightColumn">
	          <s:textfield  id="details%{#deptIndx}" name="details" cssClass="textField" theme="simple"></s:textfield>
	      </div>
	        </div>
		  <div style="float: right;margin-top: 8px;">	
			<s:if test="#deptIndx==103">
		    	<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
	        </s:if>
	       	<s:else>
	     	  	<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
	           	<div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
	         </s:else>
		</div>
	</div>
	 <div class="clear"></div>
	 	</s:iterator>
	<s:hidden name="actionType" value="%{actionType}" id="actionType" /> 	
</body>
</html>
