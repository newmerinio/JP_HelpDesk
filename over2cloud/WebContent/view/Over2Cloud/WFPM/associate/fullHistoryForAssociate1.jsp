

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


<head>
<SCRIPT type="text/javascript">
function toggle_visibility(id1,id2) {
	if(document.getElementById(id1).style.display == 'block'){
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"inactive";
		sub1.style.display	=	"none";
	}else{
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"active";
		sub1.style.display	=	"block";
	}
}





</SCRIPT>
</head>
<body>



<div class="block_main repeating_block_head"><a href="javascript:void();" id="tab3" class="inactive" onclick="javascript:toggle_visibility('content3','tab3');"><b>Associate Take Action</b></a></div>
	<div class="content_main" id="content3" style="border-top:3px solid #aaa9ab;  min-width:98%; overflow-x:scroll;">
		<table width="100%" cellspacing="0" cellpadding="9">
		
		<!-- heading -->
			<tr>
				<s:iterator value="namesTakeaction" status="stat">
					<s:if test="%{#stat.index != 7}">
						<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;text-align:center; padding:4px; font-weight:bold;" valign="top" class="tabular_cont">
						<s:property/>
						</th>	
					</s:if>	
				</s:iterator>
				<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; min-width:60px; color:#ffffff;text-align:center; padding:4px; font-weight:bold;" valign="top" class="tabular_cont">
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
							<input type="checkbox" checked="checked" disabled="disabled"">
						</s:else>
					</td>
				</tr>
				
				</s:else>
			</s:iterator>
		</table>
	</div>
		
</body>




