<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
	function goBack()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeViewSMSTemplate.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function addNewTemplate()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeAddSMSTemplate.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function viewTemplate()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/SMSTemplate/beforeViewSMSTemplate.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	$.subscribe('level2', function(event,data)
	        {
	          setTimeout(function(){ $("#orglevel").fadeIn(); }, 10);
	          setTimeout(function(){ $("#orglevel").fadeOut(); }, 4000);
	          $('select').find('option:first').attr('selected', 'selected');
	         
	        });
</SCRIPT>
<style type="text/css">
	table
	{
		border-collapse:separate;
		border-spacing:5px 8px;
	}
	
</style>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
</script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">SMS Template</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;">
	</div><div class="head">Add</div>
	</div>
	
	<div class="container_block"><div style=" float:left; width:100%;">
	<div class="tabs" align="left" style="width:40%;text-align: left;">
		<s:url id="createNewTemplate" namespace="/view/Over2Cloud/WFPM/SMSTemplate" action="createTemplate"></s:url>
		</div>
	
		<div class="border" style="max-height: 100%;">
			<s:form id = "formone" name="addTemplate" action="addSMSTemplate">
				<s:hidden name="type" value="kpi"></s:hidden>
				<table border="0" bordercolor="darkgray" rules="groups" cellpadding="10" cellspacing="10" style="width: 100%;">
					<thead>
					<tr>
						<th><center>S.No.</center></th>
						<th><center>Parameter Name</center></th>
						<th><center>Short Code</center></th>
						<th><center>Enable / Disable</center></th>
						<th><center>Validation</center></th>
						<th><center>No. Of Chars</center></th>
					</tr>
					</thead>
					<tbody >
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td align="center"><b>1</b></td>
						<td align="center"><b>Date</b></td>
						<td align="center"><b>DT</b></td>
						<td align="center"><s:checkbox name="status1" theme="simple"></s:checkbox> </td>
						<td align="center"><b>DD-MM-YYYY</b></td>
						<td align="center"><b>NA</b> </td>
					</tr>
					
					<tr>
						<td align="center"><b>2</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" cssClass="textField" theme="simple"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>3</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" cssClass="textField" theme="simple"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>4</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" cssClass="textField" theme="simple"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>5</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" cssClass="textField" theme="simple"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>6</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" theme="simple" cssClass="textField"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>7</b></td>
						<td align="center"><s:select name='paramName' list="kpiList" listKey="id"  listValue="keyword" headerKey="-1" headerValue="-- Select Kpi --" theme="simple"></s:select> </td>
						<td align="center"><s:textfield name="shortCode" placeholder="Enter Short Code" cssClass="textField" theme="simple"></s:textfield></td>
						<td align="center"><s:checkbox name="paramCheck" theme="simple"></s:checkbox></td>
						<td align="center"><s:select name="paramval"  list="#{'0':'Number','1':'Aplha','2':'Alpha Numeric'}" headerKey="-1" headerValue="--Select Validation--" theme="simple"></s:select></td>
						<td align="center"><s:textfield name="paramLength" placeholder="Enter Key Length" cssClass="textField" theme="simple"/></td>
					</tr>
					
					<tr>
						<td align="center"><b>8</b></td>
						<td align="center">Comment</td>
						<td align="center">#</td>
						<td align="center"><s:checkbox name="status2" theme="simple"></s:checkbox></td>
						<td align="center">NA</td>
						<td align="center">NA</td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					</tbody>
				</table>
				
				<div class="form_menubox">
					<div class="user_form_text">Start Keyword*:</div>
					<div class="user_form_input"><s:textfield name="msgStartCode" id="msgStartCode" cssClass="textField"  maxlength="10" placeholder="Enter Unique Keyword"/></div>
					<div class="user_form_text1">Auto Reply SMS*:</div>
					<div class="user_form_input"><s:textarea name="autorplysms" id="autorplysms"  cols="25" rows="2"  value="Hi, thanks for your information. We have updated the same. Wish you all the best. Regards." readonly="true"  style="width: 275px; height: 79px;"/></div>
				</div>
				
				<!-- Buttons -->
				<div class="clear"></div>
   					<div class="buttonStyle">
				         <sj:submit 
				           	targets="orglevel" 
	                        clearForm="true"
	                        value="Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level2"
	                        cssClass="button"
	                        indicator="indicator3"
	                        cssStyle="margin-right: 215px;margin-bottom: -28px;"
				          />
				          <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="button" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formone');"
							>
			  				Reset
							</sj:a>
				          <sj:a
					    	name="Cancel"  
							href="#" 
							targets="result" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
	                        onclick="viewTemplate()"
					    >
					    	Back
					    </sj:a>
					</div>
					<div class="clear"></div>
					<sj:div id="orglevel"  effect="fold">
                		<div id="orglevel2Div"></div>
           			</sj:div>
               </s:form>
          </div>
</div> 
</div>
</body>

</html>