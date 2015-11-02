<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	$('[name = weightage]').keyup( function() {

		totalValue();
	});
	function totalValue() {
		var sum = 0;
		$('[name = weightage]').each( function() {
			if (!isNaN(this.value) && this.value.length != 0) {
				sum += parseInt(this.value);
				if (sum > 100) {
					alert("Should not be greater than 100 %");
					event.originalEvent.options.submit = false;
				}
			}
		});

		$('#total').val(sum);
	}

	jQuery('[name = weightage]').keyup( function() {
		this.value = this.value.replace(/[^0-9\.]/g, '');
			});
</script>
<STYLE type="text/css">
/*new Table CSS for all table view added by sandeep */
* {
	padding: 0px;
	margin: 0px;
}

td.tabular_head {
	color: #ffffff;
	text-indent: 25px;
	font-size: 13px;
	line-height: 30px;
	font-weight: 400;
	border-radius: 5px 5px 0px 0px;
	-webkit-border-radius: 5px 5px 0px 0px;
	-moz-border-radius: 5px 5px 0px 0px;
	border-bottom: 1px solid #e7e9e9;
}

td.footer_block {
	color: #ffffff;
	text-indent: 25px;
	font-size: 13px;
	line-height: 30px;
	font-weight: 400;
	border-radius: 0px 0px 5px 5px;
	-webkit-border-radius: 0px 0px 5px 5px;
	-moz-border-radius: 0px 0px 5px 5px;
}

td.footer_block td {
	font-size: 13px;
	line-height: 30px;
	font-weight: 400;
	border-right: 1px solid #e7e9e9;
	padding: 0px 0.5% 0px 1%;
}

td.tabular_cont td {
	font-size: 13px;
	line-height: 30px;
	font-weight: 400;
	border-right: 1px solid #e7e9e9;
	padding: 0px 0 0px 1%;
}
</STYLE>
</head>
<br>
<body>
<div class="clear"></div>
<div class="clear"></div>
<table width="100%" cellspacing="0" cellpadding="3" style="margin: auto; width: 80%;"> 
	<tr>
		<td bgcolor="#e7e9e9"
			style="border-bottom: 1px solid #e7e9e9; color: #000000;"
			valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%" >
			<tbody>
				<tr>
					<td width="5%" align="center"><b>Sr.</b></td>
					<td valign="middle" width="5%" align="center">
						<s:checkbox cssClass="select" name="deptID" theme="simple" />
					</td>
					<td valign="middle" width="30%" align="center"><b>KRA</b></td>
					<td valign="middle" width="30%" align="center"><b>KPI</b></td>
					<td valign="middle" width="15%" align="center"><b>Weightage</b></td>
					<td valign="middle" width="15%" align="center"><b>Target Value</b></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>

	<s:iterator value="kpiDetailsList" var="listVal" status="counter">
		<tr>
			<td bgcolor="#ffffff"
				style="border-bottom: 1px solid #e7e9e9; color: #181818;"
				valign="top" class="tabular_cont">
			<table cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td valign="middle" width="5%" bgcolor="#ffffff"
							style="color: #000000; text-align: center;"><s:property
							value="#counter.count" /></td>
						<td width="5%">
							<s:checkbox cssClass="select"
								fieldValue="%{#listVal[0]}" name="kpiId" theme="simple" />
						</td>
						<td valign="middle" align="left" width="30%">
							<s:property value="%{#listVal[1]}" />
							</td>
						<td valign="middle" align="left" width="30%">
							<s:property value="%{#listVal[2]}" />
						</td>
						<td valign="middle" width="15%"><s:textfield
							id="%{#counter.count}" name="weightage" placeholder="Enter Weightage"
							cssClass="textField" readonly="false" 
							theme="simple"></s:textfield></td>
						<td valign="middle" width="15%"><s:textfield
							id="%{#counter.count}" name="targetValue" placeholder="Enter Target"
							cssClass="textField" readonly="false" 
							theme="simple"></s:textfield></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</s:iterator>
	<tr>
		<td bgcolor="#e7e9e9"
			style="border-bottom: 1px solid #e7e9e9; color: #000000;"
			valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td width="5%"></td>
					<td valign="middle" width="20%"></td>
					<td valign="middle" width="20%"><s:textfield id='total'
						theme="simple" readonly="true" cssStyle="width: 25px;">Total Weightage :
									</s:textfield>%</td>
					<td valign="middle" width="20%" id='targetVal'></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>
</table>
<br />
<div class="clear"></div>
<!-- Buttons -->
<center><img id="indicator2"
	src="<s:url value="/images/indicator.gif"/>" alt="Loading..."
	style="display: none" /></center>

<center><sj:div id="foldeffect" effect="fold">
	<div id="Result1"></div>
</sj:div></center>

</body>
<script type="text/javascript">
	//totalValue();
</script>
</html>