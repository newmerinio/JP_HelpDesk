<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Activity Page</title>

<script type="text/javascript">
		function validateTime(id, scheduletime)
			{
			var offeringId = $("#offeringId").val();
			//alert("Time not be same:offId  "+offeringId+"  %{id}  "+id);
			$.ajax({
				type : "post",
				url  : "view/Over2Cloud/wfpm/client/validateTimeForOffering.action",
				data : "id="+id+"&offeringId="+offeringId+"&scheduletime="+scheduletime,
				success : function(data){
				//alert(data.timestatus);
				
					document.getElementById("timeValidation11").style.display="block";
					//$("#timeValidation11").text(data.timestatus);
					 $("#textdataid").val(data.timestatus);
				}, 
				error   : function(){
					
				}	
		});
			}
		</script>
		<script type="text/javascript">
			function showCompReason()
			{
				var reason = $("#salesstages").val();
				if(reason == "03 Qualify the Opportunity")
				{
					document.getElementById("showCompReasonId").style.display="block";
				}
				else
				{
					document.getElementById("showCompReasonId").style.display="none";
				}
				
			}
		</script>
</head>
<body>
			<div class="newColumn">
				<div class="leftColumn">Next Activity</div>
				<div class="rightColumn">
					<span class="needed"></span>
					<s:select 
						id="status"
						name="status" 
						list="clientStatusList"
						headerKey="-1"
						headerValue="Select" 
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
				</div>
			</div>
			<div class="newColumn">
				
				<div class="leftColumn">Next Schedule:</div>
				<div class="rightColumn">
					<span class="needed"></span>
					<sj:datepicker name="maxDateTime" id="maxDateTime" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true" onchange="validateTime(%{id}, this.value);"/>
				</div>
			</div>
			
			<div id="timeValidation11" style="margin-left: 81%; display: none;width: 20%; color: red;" >
				<s:textfield name="textdata" id="textdataid" value="" />
			</div>
			<div class="newColumn">
						
				<div class="leftColumn">Sales Stages:</div>
				<div class="rightColumn">
					<span class="needed"></span>
					<s:select 
						id="salesstages"
						name="sales_stages" 
						list="#{'01 Understand Customer':'01 Understand Customer', '02 Validate Opportunity':'02 Validate Opportunity', '03 Qualify the Opportunity':'03 Qualify the Opportunity', '04A Develop Solution':'04A Develop Solution', '04B Propose Solution':'04B Propose Solution', '05 Negotiate & Close':'05 Negotiate & Close', '06 Won, Deploy & Expand':'06 Won, Deploy & Expand', 'Lost':'Lost','CSPL not pursed':'CSPL not pursed', 'Error':'Error'}"
						headerKey="-1"
						headerValue="Select" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="showCompReason();"
						>
					</s:select>
				</div>
			</div>
			
				<div class="newColumn" style="display: none;" id="showCompReasonId">
				<div class="leftColumn">Compelling Reasons: </div>
				<div class="rightColumn">
					<span class="needed"></span>
					<textarea rows="1" placeholder="Enter Data" maxlength="255" name="compelling_reason" id="compelling_reason" style="width: 81%;" cssClass="textField"></textarea>
				</div>
			</div>
				<s:hidden id="clientContactId" name="clientContactId" value="%{clientContactId}"></s:hidden>
			
</body>
</html>