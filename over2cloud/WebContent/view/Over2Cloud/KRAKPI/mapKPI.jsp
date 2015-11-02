<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600" rel="stylesheet" type="text/css" />
<script type="text/javascript">


$.subscribe('complete1', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          $("#empView").html("");
        });

function viewKraKpi()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforekra_kpiView.action?modifyFlag=0&deleteFlag=0&moduleName=KRA-KPI",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>

<table width="70%" cellspacing="0" cellpadding="3" align="center">
<tr><td bgcolor="#e7e9e9" class="tabular_head" valign="middle" colspan="7" style="color: black"align="center" ><b><!--Map KRA-KPI With Employee</b>--></td></tr>
	<tr><td bgcolor="#e7e9e9" style=" border-bottom:1px solid #e7e9e9; color:#000000;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%"><b>Sr.</b></td>
				<td valign="middle" width="2.5%"><s:checkbox  name="selectall" id="selectall" onclick="selectAll(this);" value="true"  theme="simple"/></td>
				<td valign="middle" width="20%"><b>KRA</b></td>
				<td valign="middle" width="20%"><b>KPI</b></td>
			</tr>
		</tbody></table>
	</td>
	</tr>
<s:iterator value="empKRAKPIList" id="empKRAKPIList" var="var" status="counter">  
<s:if test="#counter.count%2 != 0">
	<tr><td bgcolor="#ffffff" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#ffffff" style=" color:#181818; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:checkbox theme="simple" name="pid" id="pid" value="%{#var[3]}" fieldValue="%{#var[0]}"/></td>
				<td valign="middle" width="20%"><s:property value="#var[1]"/></td>
				<td valign="middle" width="20%"><s:property value="#var[2]"/></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:if><s:else>
<tr><td bgcolor="#e7e9e9" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#e7e9e9" style=" color:#181818; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:checkbox theme="simple"  name="pid" id="pid" value="%{#var[3]}" fieldValue="%{#var[0]}"/></td>
				<td valign="middle" width="20%"><s:property value="#var[1]"/></td>
				<td valign="middle" width="20%"><s:property value="#var[2]"/></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:else>
</s:iterator> 
</table>
<br>
             

</body>
</html>