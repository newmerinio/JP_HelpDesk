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

function cancelpage(){
	var isExistingAssociate='${isExistingAssociate}';
	var currDate = '${currDate}';
	//alert("currDate:"+currDate);
	if(currDate != null && currDate != '')
	{
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeCommonDashboard.action",
			data : "currDate="+currDate,
			success : function(data){
				$("#data_part").html(data);
			},
			error   : function(){
				alert("error");
			}
			});
	}
	else
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateView.action",
		    data: "isExistingAssociate="+isExistingAssociate,
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}
}
</SCRIPT>
<link href="css/style3.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.leftColumn{
text-align: right;
padding: 6px;
line-height: 7px;
float: left;
width: 25%;
}
.rightColumn{
padding: 6px;
line-height: 7px;
float: left;
text-align: left;
width: 68%;
}
</style>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<!-- <div class="head">Associate History</div> -->
		    <div class="head"><s:property value="#parameters.mainHeaderName"/></div>
			<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
			<div class="head">Full View</div>
			<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
			<div class="head"><s:property value="%{associateName}"/></div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	
	<div class="border">
	
		<div style="width: 100%; text-align: center; padding-top: 10px;">
<s:hidden name="id" id="id"> </s:hidden>
<s:hidden name="offeringId" id="offeringId"> </s:hidden>
<!-- Associate Basic Data ************************************************************* -->
	<div class="content_main" id="content4">
			<s:iterator value="basicParams" status="counter">
				<div style="float: left; width: 33%">
					<div class="leftColumn"><H4><s:property value="%{key}"/>:</H4></div>
					<div class="rightColumn"><s:property value="%{value}"/></div>
				</div>
				<s:if test="%{#counter.count % 3 == 0}">
					<div class="clear"></div>
				</s:if>
			</s:iterator>
	</div>
<div class="clear"></div>
<br>
<input name="cvDelete" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
<br>
<br>
<!-- Associate offering *****************************************************************-->		
<s:if test="associateForOfferingDetails > 0">
	<div style="color: black;">
		<b>Offering Details</b>
	</div>
		<div class="content_main" id="content1" style="border-top:3px solid #aaa9ab; min-width:98%;">
			<table width="100%" cellspacing="0" cellpadding="9" style="background:#e2e4e4;">
				<tr>
					<!-- for heading dyanamic -->
					<s:iterator value="namesOffering" status="stat" var="var">
					<s:if test="%{#stat.index != 1 && #var !='ID'}">
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					<s:property/>
					</th>
					</s:if>
					</s:iterator>
					
					<!-- for check box -->
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					Convert&nbsp;to&nbsp;existing
					</th>
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					Convert&nbsp;to&nbsp;Lost
					</th>
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					Convert&nbsp;to&nbsp;Associate
					</th>
				</tr>
			
			
				<s:iterator value="parentOffering" status="counter">  
				
				<s:if test="#counter.count%2 != 0">
					<tr>
						<s:iterator value="childList" status="status">
							<s:if test="%{#status.first}">
								<s:set var="offeringId" value="name"></s:set>
							</s:if>
							
							<s:if test="%{#status.index == 1}">
								<s:set var="isFinish" value="name"></s:set>
							</s:if>
							
							<s:if test="%{#status.index != 1 && #status.index != 0}">
								<td style="border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
									<s:property value="name"/>
								</td>
							</s:if>	
							
						</s:iterator>
						
						<!-- for check box -->
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; ">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="finishOffering('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled"">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="convertToLost('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled"">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
								<s:if test="%{convertToAssociate == 1}">
									<input type="checkbox" onclick="convertToAssociate('<s:property value="offeringId"/>',this)">	
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
								<s:set var="offeringId" value="name"></s:set>
							</s:if>
							
							<s:if test="%{#status.index == 1}">
								<s:set var="isFinish" value="name"></s:set>
							</s:if>
							
							<s:if test="%{#status.index != 1 && #status.index != 0}">
								<td style=" background:#e2e4e4; border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
									<s:property value="name"/>
								</td>
							</s:if>	
							
						</s:iterator>
						
						<!-- for check box -->
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="finishOffering('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled"">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="convertToLost('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled"">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="%{convertToAssociate == 1}">
									<input type="checkbox" onclick="convertToAssociate('<s:property value="offeringId"/>',this)">	
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
</s:if>

<br>		
<!-- Associate Contact Data *********************************************************************** -->		
<s:if test="associateForContacDetails > 0">
	<div style="color: black;">
		<b>Contact Details</b>
	</div>
		<div class="content_main" id="content2" style="border-top:3px solid #aaa9ab;  min-width:98%;">
			<table width="100%" cellspacing="0" cellpadding="9" style="background:#e2e4e4;">
			
			<!-- heading -->
				<tr>
					<s:iterator value="namesContact">
						<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; text-align:center; padding:4px; font-weight:bold;" valign="top" class="tabular_cont">
						<s:property/>
						</th>
					</s:iterator>
				</tr>
				
				<!-- data part -->
				<s:iterator value="parentContact" status="status">
				<s:if test="#status.count%2 != 0">
					<tr>
						<s:iterator value="childList" >
								<td style=" border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont"><s:property value="name"/></td>	
							
						</s:iterator>
					</tr>
					</s:if>
					<s:else>
					<tr>
						<s:iterator value="childList" >
								<td style=" background:#e2e4e4; border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont"><s:property value="name"/></td>	
							
						</s:iterator>
					</tr>
					</s:else>
				</s:iterator>
			</table>
		</div>
</s:if>

<!-- Associate Activities *************************************************************** -->
<br>
	<div style="color: black;">
		<b>Take Action Details</b>
	</div>
	<div class="content_main" id="content3" style="border-top:3px solid #aaa9ab;  min-width:98%;">
		<s:include value="./associateActivities.jsp"></s:include>
	</div>
</div>
</div>
			<center>
	<input name="cvDelete" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
	</center>
	<br>
	<br>
	<br>
</div>

<sj:dialog 
	id="MomDiloagforAsso" 
	title="MOM View" 
	autoOpen="false" 
    modal="true" 
    position="center"
	height="470" width="800" 
	showEffect="drop">
</sj:dialog>
<sj:dialog 
	id="MomDiloagforAssoNoMom" 
	title="MOM View" 
	autoOpen="false" 
    modal="true" 
    position="center"
	width="250" 
	height="100" 
	showEffect="drop">
</sj:dialog>

</body>