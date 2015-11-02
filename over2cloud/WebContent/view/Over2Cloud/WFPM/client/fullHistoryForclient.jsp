<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


<head>
<SCRIPT type="text/javascript"><!--
var id='${id}';
var isExistingClient='${isExistingClient}';
var mainHeaderName='${mainHeaderName}';



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

function cancelpage()
{
	var isExistingClient = '${isExistingClient}';
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
		    url : "view/Over2Cloud/wfpm/client/beforeClientView.action",
		    data: "isExistingClient="+isExistingClient,
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}
}

function abc(ver,off,suboff,id)
{
	//alert(ver+"   "+off+"    "+suboff);
//alert(id);
$("#clientOffering").dialog({title: ver +' >> '+ off +' >> '+ suboff ,width: 600,height: 400});
$("#clientOffering").dialog('open');
$("#client").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
     type : "post",
     url : "view/Over2Cloud/wfpm/client/fetchOfferingData.action",
     data: "id="+id,
    success : function(subdeptdata) 
    {
    	$("#client").html(subdeptdata);
    },
   error: function() {
        alert("error");
    }
 });
}
	function editOfferingPage()
	{
		var returntype = "Editoffering";
		$("#clientofferingDialog").dialog('open');
		$("#clientOfferingDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	     url : "view/Over2Cloud/wfpm/client/beforeClientAdd.action",
	    data: "isExistingClient="+isExistingClient+"&returntype="+returntype,
	    success : function(subdeptdata) 
	    {
	    	$("#clientOfferingDiv").html(subdeptdata);
	    },
	   error: function() {
            alert("error");
        }
	 });
	}
	function editClientPage()
	{
		returntype = "Client";
		$("#clientClientDialog").dialog('open');
		clientId = $("#id").val();
		// alert("clientId>>"+clientId+"personName>>"+personName+"department>"+department+"designation>"+designation);
		$("#editClientDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientEdit.action?",
	    data : "id="+clientId+"&returntype="+returntype,
	    success : function(subdeptdata) 
	    {
	    	$("#editClientDiv").html(subdeptdata);
	    },
	   error: function() {
            alert("error");
        }
	 });
	}
	function editContactPage()
	{
		returntype = "Contact";
		$("#clientContactDialog").dialog('open');
		clientId = $("#id").val();
		
		//alert("clientId>>"+clientId+"personName>>"+personName+"department>"+department+"designation>"+designation);
		$("#clientContactDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientEdit.action?",
	    data : "id="+clientId+"&returntype="+returntype,
	    success : function(subdeptdata) 
	    {
	    	$("#clientContactDiv").html(subdeptdata);
	    },
	   error: function() {
            alert("error");
        }
	 });
	}
	
function finishOffering(id,th)
{
	$("#clientDialog").dialog('open');
	clientId = $("#id").val();
	//alert("id>"+id+"th>"+th+"clientId>"+clientId);
	$("#clientDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientConvert.action?clientId="+clientId,
	    data : "id="+id,
	    success : function(subdeptdata) 
	    {
	    	$("#clientDiv").html(subdeptdata);
	    },
	   error: function() {
            alert("error");
        }
	 });

	th.checked = false;
}

//For convert to lost (
function convertToLost(id, obj) 
{
	var th;
	//alert("clientId:"+clientId);
		$("#clientDialogId").dialog('open');
		clientId = $("#id").val();
		// alert("clientId>>>"+clientId);
		$("#clientDivId").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/client/beforeLostTakeActonOnFullview.action?clientId="+clientId,
			data: "id=" + id,
			success : function(data){
				$("#clientDivId").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
		th.disabled = true;
}
	
function convertToClient(id, obj) 
{
		clientId = $("#id").val();
		alert(clientId);
		if(confirm("Do you want to convert?")){
			$.ajax({
	         type: "POST",
	         url: "view/Over2Cloud/wfpm/client/convertToClient.action?clientId="+clientId,
	         data: "id=" + id,
	         success: function(response){
	             // we have the response
	         	$(obj).attr("disabled", true);
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	     });
		}  
}
 function createActivity(uid,id)
 	{
	 //	alert(uid+"      "+id);
 		//$("#clientActivitydialogId").dialog('open');
 		returntype = "createActivityFullView";
 		// alert("Activity"+returntype);
 		var clientName = $("#clientName").val();
 		// alert("id>"+id+"clientName>"+clientName+"isExistingClient>"+isExistingClient);
 		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
 		//$("#clientActivityId2").html("<center><img src=images/ajax-loaderNew.gif></center>");
 		$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeContactTakeAction.action?",
	    data : "id="+id+"&isExistingClient="+isExistingClient+"&clientName="+clientName+"&returntype="+returntype+"&uid="+uid,
	    success : function(subdeptdata) 
	    {
 		  	$("#"+"data_part").html(subdeptdata);
 			  //  	$("#clientActivityId2").html(subdeptdata);
	    },
	   error: function() {
            alert("error");
        }
	 });
		
 	}

</SCRIPT>
<style type="text/css">
.leftColumn{
text-align: right;
padding: 6px;
line-height: 9px;
float: left;
width: 25%;
}
.rightColumn{
padding: 6px;
line-height: 9px;
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
		<div class="head"><s:property value="#parameters.mainHeaderName"/></div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
		<div class="head">Full View</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	
	<div class="border">
		<div style="width: 100%; text-align: center; padding-top: 10px;">
<s:hidden name="id" id="id"> </s:hidden>
<s:hidden name="offeringId" id="offeringId"> </s:hidden>
<s:hidden name="clientName" id="clientName" value="%{clientName}"> </s:hidden>
<!-- Client Basic Data ************************************************************* -->
	<div class="content_main" id="content4">
	<table width="100%" cellspacing="0" cellpadding="" style="background:#e2e4e4;"> 
		<tr>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left">
					<B><font size="3"><s:property value="%{clientName}"/></font></B>, <font size="3"><B><s:property value="%{location}"/></B></font>.
					<s:iterator value="numberOfStar" status="counter">
						<img alt="star" src="images/WFPM/commonDashboard/star.jpg">
					</s:iterator>
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left"> 
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				</th>
				
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="right">
					<input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button" onclick="editClientPage();" align="left">
					<input name="cvDelete" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
				</th>
		</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="" >
			<s:iterator value="basicParams" status="counter">
				<div style="float: left; width: 33%">
				<s:if test='%{key != "Closure Date" && key != "Brief" && key != "Value"  && key != "Forecast Category"}'>
					<div class="leftColumn"><H4><s:property value="%{key}"/>:</H4></div>
					<div class="rightColumn"><s:property value="%{value}"/></div>
				</s:if>
				</div>
				<!--<s:if test="%{#counter.count % 3 == 0}">
					<div class="clear"></div>
				</s:if>
			--></s:iterator>
	</table>
	</div>
	<div class="clear"></div>
<!-- client offering **************************************************************** -->		
<s:if test="clientForOfferingDetails>0">

		<div class="content_main" id="content1" style="border-top:3px solid #aaa9ab;min-width:98%;">
			<table width="100%" cellspacing="0" cellpadding="9" style="background:#e2e4e4;">
			<tr>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left">
					<font size="3"><B>Mapped&nbsp;Offering&nbsp;Status</B></font>
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">	
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="right">
					<input name="cvDelete" id="cvDelete" class="button" value="Add" type="button" onclick="editOfferingPage();" align="left">
					<input name="cvDelete" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
				</th>
			</tr>
				<tr>
					<!-- for heading dyanamic -->
					<s:iterator value="namesOffering" status="stat" var="var">
					<s:if test="%{#stat.index != 1 && #var !='ID'}">
						<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" 
						valign="top" class="tabular_cont">
						<B><s:property/></B>
					</th>
					</s:if>
					</s:iterator>
					
					<!-- for check box -->
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					<B>To&nbsp;Existing</B>
					</th>
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					<B>To&nbsp;Lost</B>
					</th>
					<th bgcolor="#e4e4e5" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
					<B>To&nbsp;Client</B>
					</th>
					
				</tr>
				<s:iterator value="parentOffering" status="counter">  
				
				<s:if test="#counter.count%2 != 0">
					<tr bgcolor="white">
						<s:iterator value="childList" status="status">
							<s:if test="%{#status.first}">
								<s:set var="offeringId" value="name"></s:set>
								<s:hidden value="%{name}" id="offeringIdd"></s:hidden>
							</s:if>
							
							<s:if test="%{#status.index == 2}">
								<s:hidden value="%{name}" id="verticalnameId"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 3}">
								<s:hidden value="%{name}" id="offeringname"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 4}">
								<s:hidden  value="%{name}" id="subofferingname"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 1}">
								<s:set var="isFinish" value="name"></s:set>
							</s:if>
							<s:if test="%{#status.index != 1 && #status.index != 0 && #status.index != 2 && #status.index != 3}">
							    <s:if test="%{#status.index == 4}">
							    	<td style="border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
								    	<s:a href="#" cssStyle="color:#4876FF;" onclick="abc('%{verticalname}','%{offeringname}','%{subofferingname}','%{offeringId}');">
							                    <s:property value="name"/>
							             </s:a>
								   </td>
							    </s:if>
							<s:else>
								<td style="border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
									<s:property value="name"/>
								</td>
							</s:else>
							</s:if>	
							
						</s:iterator>
						
						<!-- for check box -->
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
								<s:if test="#isFinish == 0">
									<s:if test="#isFinish != 1">
										<input type="checkbox" onclick="finishOffering('<s:property value="offeringId"/>',this)">
									</s:if>
										
								</s:if>
								<!--<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
								</s:else> -->
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
								<s:if test="#isFinish == 0">
									<s:if test="#isFinish != 2">
									<input type="checkbox" onclick="convertToLost('<s:property value="offeringId"/>',this)">	
									</s:if>
								</s:if>
								<!--<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
								</s:else> -->
								</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
								<s:if test="%{isExistingClient==2}">
									<input type="checkbox" onclick="convertToClient('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
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
							
							<s:if test="%{#status.index != 1 && #status.index != 0 && #status.index != 2 && #status.index != 3}">
								<s:if test="%{#status.index == 4}">
							    	<td style="border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
								    	<s:a href="#" cssStyle="color:#4876FF;" onclick="abc('%{offeringId}');">
							                    <s:property value="name"/>
							             </s:a>
								   </td>
							    </s:if>
							<s:else>
								<td style="border-bottom:1px solid #e7e9e9; color:#181818; text-align:center; padding:4px;" valign="top" class="tabular_cont">
									<s:property value="name"/>
								</td>
							</s:else>
							</s:if>	
						</s:iterator>
						
						<!-- for check box -->
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="finishOffering('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="#isFinish == 0">
									<input type="checkbox" onclick="convertToLost('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
								</s:else>
							</td>
							<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9; background:#e2e4e4;">
								<s:if test="%{isExistingClient==2}">
									<input type="checkbox" onclick="convertToClient('<s:property value="offeringId"/>',this)">	
								</s:if>
								<s:else>
									<input type="checkbox" checked="checked" disabled="disabled">
								</s:else>
							</td>
							
							
					</tr>
					
					</s:else>
					
					
				</s:iterator>
			</table>
		</div>
</s:if>
		
<br>
<!-- Client Contact Details ******************************************************** -->		
<s:if test="clientForContacDetails>0">
		<div class="content_main" id="content2" style="border-top:3px solid #aaa9ab;  min-width:98%;">
			<table width="100%" cellspacing="0" cellpadding="9" style="background:#e2e4e4;">
			
			<!-- heading -->
				<tr>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left">
					<font size="3"><B>Contact&nbsp;Details</B></font>
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="right">
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="right">
					<input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button" onclick="editContactPage();" style="margin-left: -54px;">
					<input name="cvDelete" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
				</th>
			</tr>
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
					<tr bgcolor="white">
						<s:iterator value="childList" >
							<s:if test="%{#status.index == 0}">
								<s:hidden  value="%{name}" id="personName"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 1}">
								<s:hidden  value="%{name}" id="communicationName"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 2}">
								<s:hidden  value="%{name}" id="degreeOfInfluence"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 3}">
								<s:hidden  value="%{name}" id="department"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 4}">
								<s:hidden  value="%{name}" id="designation"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 5}">
								<s:hidden  value="%{name}" id="contactNo"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 6}">
								<s:hidden  value="%{name}" id="emailOfficial"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 7}">
								<s:hidden  value="%{name}" id="birthday"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 8}">
								<s:hidden  value="%{name}" id="anniversary"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 9}">
								<s:hidden  value="%{name}" id="alternateEmail"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 10}">
								<s:hidden  value="%{name}" id="alternateContNo"></s:hidden>
							</s:if><!--
							<s:if test="%{#status.index == 11}">
								<s:hidden  value="%{name}" id="input"></s:hidden>
							</s:if>
								--><td style=" border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont"><s:property value="name"/></td>	
						</s:iterator>
					</tr>
					</s:if>
					<s:else>
					<tr>
						<s:iterator value="childList" >
								<s:if test="%{#status.index == 1}">
								<s:hidden  value="%{name}" id="communicationName"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 2}">
								<s:hidden  value="%{name}" id="degreeOfInfluence"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 3}">
								<s:hidden  value="%{name}" id="department"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 4}">
								<s:hidden  value="%{name}" id="designation"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 5}">
								<s:hidden  value="%{name}" id="contactNo"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 6}">
								<s:hidden  value="%{name}" id="emailOfficial"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 7}">
								<s:hidden  value="%{name}" id="birthday"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 8}">
								<s:hidden  value="%{name}" id="anniversary"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 9}">
								<s:hidden  value="%{name}" id="alternateEmail"></s:hidden>
							</s:if>
							<s:if test="%{#status.index == 10}">
								<s:hidden  value="%{name}" id="alternateContNo"></s:hidden>
							</s:if><!--
							<s:if test="%{#status.index == 11}">
								<s:hidden  value="%{name}" id="input"></s:hidden>
							</s:if>
								-->
								<td style=" background:#e2e4e4; border-bottom:1px solid #e7e9e9; color:#181818;text-align:center; padding:4px;" valign="top" class="tabular_cont"><s:property value="name"/></td>	
							
						</s:iterator>
					</tr>
					</s:else>
				</s:iterator>
			</table>
		</div>
</s:if>

<br>
<!-- Client activity *********************************************************** -->
	
	<div class="content_main" id="content3" style="border-top:3px solid #aaa9ab;  min-width:98%;">
	<table width="100%" cellspacing="0" cellpadding="" style="background:#e2e4e4;"> 
		<tr>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left">
					<B><font size="3">Activity&nbsp;Details</font></B> 
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="left"> 
				</th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				</th>
				
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont"></th>
				<th bgcolor="#cccccc" style=" border-bottom:1px solid #e7e9e9; color:#000000; padding:4px; font-weight:600;" valign="top" class="tabular_cont" align="right">
				<input name="#cccccc" id="cvDelete" class="button" value="Create Activity" type="button" onclick="createActivity('<s:property value='%{uid}'/>','<s:property value='%{id}'/>');">
				<input name="#cccccc" id="cvDelete" class="button" value="Back" type="button" onclick="cancelpage();">
				</th>
		</tr>
	</table>
		<s:include value="./clientActivities.jsp"></s:include>
	</div>
</div>
</div>

<br>
</div>
	
	<sj:dialog 
	id="MomDiloag" 
	title="MOM View" 
	autoOpen="false" 
    modal="true" 
    position="center"
	height="470" width="800" 
	showEffect="drop">
</sj:dialog>
<sj:dialog 
	id="MomDiloagNoMom" 
	title="MOM View" 
	autoOpen="false" 
    modal="true" 
    position="center"
	width="250" 
	height="100" 
	showEffect="drop">
</sj:dialog>
<sj:dialog 
		id="clientDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="250" 
	    title="Convert Client From Prospective to Existing" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientDiv"></div>
</sj:dialog>
<sj:dialog 
		id="clientofferingDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="350" 
	    title="Add Offering Details" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientOfferingDiv"></div>
</sj:dialog>
<sj:dialog 
		id="clientContactDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="450" 
	    title="Edit Contact Details" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientContactDiv"></div>
</sj:dialog>
<sj:dialog 
		id="clientClientDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="450" 
	    title="Edit Client Details" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="editClientDiv"></div>
</sj:dialog>

<sj:dialog 
		id="clientDialogId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="250" 
	    title="Convert Client From Prospective to Lost" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientDivId"></div>
</sj:dialog>

<sj:dialog 
		id="clientDialogIdd"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="250" 
	    title="Take Action To Finish Client Activity" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientDivIdd"></div>
</sj:dialog>

<sj:dialog 
		id="clientOffering"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="950" 
	    height="250" 
	    title="Take Action To Finish Client Activity" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="client"></div>
</sj:dialog>

<sj:dialog 
		id="clientActivitydialogId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="1500" 
	    height="500" 
	    title="Create Activity" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center','center']">
	<div id="clientActivityId2"></div>
</sj:dialog>
</body>