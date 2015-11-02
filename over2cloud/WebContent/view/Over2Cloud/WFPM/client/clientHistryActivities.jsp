

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<SCRIPT type="text/javascript">
function finish(id,th)
{
	if(confirm("Are you sure you want to close this activity !"))
	{
		//alert(id);
		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/WFPM/Associate/finishAssociateActivity.action",
		    data : "id="+id,
		    success : function(subdeptdata) {
			    th.disabled = true;
			    //alert("success");
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else 
	{
		th.checked = false;
	}
}
</script>
<br>

<s:if test="%{fromDashboard != null}">		
	<div class="page_title" id="lead"><h1><s:property value="fromDashboard"/> </h1></div>
</s:if>

<table width="100%" cellspacing="0" cellpadding="9">
		
		<!-- heading -->
			<tr>
				<s:iterator value="namesTakeaction" status="stat">
					<s:if test="%{#stat.index != 7}">
						<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000;text-align:center; padding:4px; font-weight:bold;" valign="top" class="tabular_cont">
						<s:property/>
						</th>	
					</s:if>	
				</s:iterator>
				<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; min-width:60px; color:#000000;text-align:center; padding:4px; font-weight:bold;" valign="top" class="tabular_cont">
				Action
				</th>	
			</tr>
			
			
			<!-- data part -->
			
			<s:iterator value="parentTakeaction" status="status1">
				<s:if test="#status1.count%2 != 0">  
				<tr>
					<s:iterator value="childList" status="status">
						<s:if test="%{#status.first}">
							<s:set var="contactId" value="name"></s:set>
						</s:if>
						<s:if test="%{#status.index == 7}">
							<s:set var="isFinish" value="name"></s:set>
						</s:if>
						<s:if test="%{#status.index != 7}">
							<td style=" border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont">
							<s:property value="name"/>
							</td>	
						</s:if>
					</s:iterator>
					
					
					<td style="text-align:center;">
						<s:if test="#isFinish == 0">
							<input type="checkbox" onclick="finish('<s:property value="#contactId"/>',this)">	
						</s:if>
						<s:else>
							<input type="checkbox" checked="checked" disabled="disabled"">
						</s:else>
					</td>
				</tr>
				
				</s:if>
				
				
				<s:else>
				<tr>
					<s:iterator value="childList" status="status">
						<s:if test="%{#status.first}">
							<s:set var="contactId" value="name"></s:set>
						</s:if>
						<s:if test="%{#status.index == 7}">
							<s:set var="isFinish" value="name"></s:set>
						</s:if>
						<s:if test="%{#status.index != 7}">
							<td style=" background:#e2e4e4;  border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont">
							<s:property value="name"/>
							</td>	
						</s:if>
					</s:iterator>
					
					
					<td style=" background:#e2e4e4; text-align:center;">
						<s:if test="#isFinish == 0">
							<input type="checkbox" onclick="finish('<s:property value="#contactId"/>',this)">	
						</s:if>
						<s:else>
							<input type="checkbox" checked="checked" disabled="disabled">
						</s:else>
					</td>
				</tr>
				
				</s:else>
			</s:iterator>
		</table>