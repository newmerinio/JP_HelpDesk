<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
{
   setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
   setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); }, 12000);
   $('#completionResult').dialog('open');
});
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewGroup() ;
}
function viewGroup() 
{
	 $('#abc').dialog('close');
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="complTarget"></div>
</sj:dialog>
<div class="list-icon">
	 <div class="head">
	 Settlement</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Accounts</div> 
</div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addAdvanceAmount" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden id="id" name="activity_id" value="%{#parameters.id}"/>
	          <s:hidden id="ticket_no" name="ticket_no" value="%{#parameters.ticket_no}"/>
                    <table width="100%" border="1">
				    		<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Name:</strong></td>
								<td align="left" ><s:property value="#parameters.name"/></td>
								
								<td align="left" ><strong>Ticket No:</strong></td>
								<td align="left" ><s:property value="#parameters.ticket_no"/></td>
						    </tr>
						    <tr  bgcolor="white" style="height: 25px">
						          <td align="left" ><strong>Activity For:</strong></td>
								  <td align="left" ><s:property value="#parameters.actFor"/></td>
						          <td align="left" ><strong>Activity Type:</strong></td>
								  <td align="left" ><s:property value="#parameters.actType"/></td>
						   </tr>
						   	<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Month:</strong></td>
								<td align="left" ><s:property value="#parameters.month"/></td>
								 <td align="left" ><strong>Schedule:</strong></td>
								 <td align="left" ><s:property value="#parameters.from"/></td> 
								
						    </tr>
						    <tr  bgcolor="white" style="height: 25px">
								 <td align="left" ><strong>Comments:</strong></td>
								 <td align="left" ><s:property value="#parameters.comment"/></td>
						          <td align="left" ><strong>Approved Advance:</strong></td>
								  <td align="left" ><s:property value="#parameters.amount"/></td>
						   </tr>
				    </table>
				    <s:if test="%{commonList.size()>0}">
				     <table width="100%" border="1" style="border-collapse: collapse;">
						<tr  bgcolor="#D8D8D8" style="height:25px">
							<td colspan="1" width="%">
									<strong>Ticket No</strong>
							</td>	
							<td colspan="1" width="%">
									<strong>Amount</strong>
							</td>	
							<td colspan="1" width="%">
									<strong>Reason</strong>
							</td>	
							<td colspan="1"  width="%">
									<strong>Accountant</strong>
							</td>	
							<td colspan="1"  width="%">
									<strong>Date</strong>
							</td>	
						</tr>
						 <s:iterator value="%{commonList}" id="totalCompl" status="status">
						       <s:if test="#status.odd">
						       		 <tr style="height: 25px;">
							             <td  align="left" width="10%"><s:property value="#totalCompl.ticket_no" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.amount" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.reason" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.manager_name" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.action_date" /></td>
						            </tr>
						       </s:if>
						       <s:else>
						       		<tr bgcolor="#D8D8D8"  style="height: 25px;">
							             <td  align="left" width="10%"><s:property value="#totalCompl.ticket_no" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.amount" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.reason" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.manager_name" /></td>
							             <td  align="left" width="10%"><s:property value="#totalCompl.action_date" /></td>
						            </tr>
						       </s:else>
						</s:iterator>
									<tr  style="height: 25px;">
							             <td  align="left" width="10%"><b>Total</b></td>
							             <td  align="left" width="10%"><s:property value="%{sum}" /></td>
							             <td  align="left" width="10%"></td>
							             <td  align="left" width="10%"></td>
							             <td  align="left" width="10%"></td>
									 </tr>
						</table>
				    </s:if>
				     <s:if test="%{commonList2.size()>0}">
				     <table width="100%" border="1" style="border-collapse: collapse;">
						<tr  bgcolor="#D8D8D8" style="height:25px">
							<td colspan="1" width="%">
									<strong>Expense</strong>
							</td>	
							<td colspan="1" width="%">
									<strong>Value</strong>
							</td>	
							<td colspan="1" width="%">
									<strong>Document</strong>
							</td>
							<td colspan="1"  width="%">
									<strong>Manager Name</strong>
							</td>	
							<td colspan="1"  width="%">
									<strong>Manager Value</strong>
							</td>	
							<td colspan="1"  width="%">
									<strong>Reason</strong>
							</td>	
						</tr>
						 <s:iterator value="%{commonList2}" id="totalCompl" status="status">
						       <s:if test="#status.odd">
						       		 <tr style="height: 25px;">
						             <td  align="left" width="10%"><s:property value="#totalCompl.exp" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.exp_val" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.doc" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_exp_val" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_exp_reason" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_name" /></td>
						            </tr>
						       </s:if>
						       <s:else>
						       		<tr bgcolor="#D8D8D8"  style="height: 25px;">
						             <td  align="left" width="10%"><s:property value="#totalCompl.exp" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.exp_val" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.doc" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_exp_val" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_exp_reason" /></td>
						             <td  align="left" width="10%"><s:property value="#totalCompl.man_name" /></td>
						            </tr>
						       </s:else>
						</s:iterator>
						<tr   style="height: 25px;">
						             <td  align="left" width="10%"><b>Total</b></td>
						             <td  align="left" width="10%"><s:property value="%{sum2}" /></td>
						             <td  align="left" width="10%"></td>
						             <td  align="left" width="10%"></td>
						             <td  align="left" width="10%"></td>
						             <td  align="left" width="10%"></td>
						</tr>
						</table>
				    </s:if>
				<b>Balance: </b> (<s:property value="%{amt}"/> - <s:property value="%{sum2}"/>) = <s:property value="%{finalAmt}"/>   
               <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:a
                            cssStyle="margin-left: 45px;margin-top: 9px;" button="true" href="#" value="View" onclick="advance_main();" 
                        >Back
                    </sj:a>
                  </div>
               </div>
             </s:form> 
             <sj:dialog
		          id="expense"
		          showEffect="slide" 
		          autoOpen="false"
		          title="Create Activity"
		          modal="true"
		          width="1200"
		          position="center"
		          height="450"
		          draggable="true"
		    	  resizable="true"
		          >
		        <center>  <div id="result_data"></div> </center>
			</sj:dialog>         
</div>
</div>
</body>
</html>

