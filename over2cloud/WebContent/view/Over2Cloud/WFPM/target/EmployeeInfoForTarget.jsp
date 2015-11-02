<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}


	var radioValue = 'kpi';
	function getObj(id) {
		return document.getElementById(id);
	}

	jQuery.struts2_jquery.require( [
			"js/base/jquery.ui.widget" + jQuery.struts2_jquery.minSuffix
					+ ".js",
			"js/base/ jquery.ui.datepicker" + jQuery.struts2_jquery.minSuffix
					+ ".js" ]);
	if (jQuery.struts2_jquery.local != "en") {
		jQuery.struts2_jquery.require("i18n/jquery.ui.datepicker-"
				+ jQuery.struts2_jquery.local + ".min.js");
	}
	var datePick = function(elem) {
		$(elem).datepicker( {
			dateFormat :'mm-yy'
		});
		jQuery(elem).datepicker();
		jQuery('#ui-datepicker-div').css("z-index", 2000);
	};

	$.subscribe('complete', function(event, data) {
		setTimeout( function() {
			$("#foldeffect").fadeIn();
		}, 10);
		setTimeout( function() {
			$("#foldeffect").fadeOut();
		}, 4000);

		$('input:radio[name=targetOn][value='+radioValue+']').attr('checked', true);
		$('radio').find('option:first').attr('selected', 'selected');
	});

	function showTargetPanel(value)
	{
		radioValue = value;
		var kpi = document.getElementById("targetOnKPI");
		var offering = document.getElementById("targetOnOffering");
		if(value == 'kpi')
		{
			kpi.style.display="block";
			offering.style.display="none";
		}
		else
		{
			kpi.style.display="none";
			offering.style.display="block";
		}
		
	}
	
	var totalTarget = 0;
	var remVal=0;
	//var remTarget = 0;
	function setTotalTarget(total)
	{
		//alert(total);
		totalTarget = total;
		//remVal = totalTarget;
		//$("#targetVal").text("Remaining Value : "+ totalTarget);
		//remTarget = 0;
	}
	
	var valPer=100;
	function setOfferingTargetValue(target, id, textId)
	{
		//alert("target : "+ target);
		if(target.toString() == "")
		{
			$('#'+id).val("");
			return;
		}
		var remTarget = 0;
		var v = $(".abc");
		var i;

		if(/^[a-zA-Z0-9- ]*$/.test(target) == false) {
		    alert('Your search string contains illegal characters.');
		    return;
		}
		if(totalTarget != 0)
		{
			if(target != null && target != "")
			{
				$('#'+id).val((totalTarget*target)/100);

				for(i=0; i<v.length; i++)
				{
					if(v[i].value != "" && v[i].value != null)
					{
						remTarget += parseInt(v[i].value);
					}
				}
				if(remTarget >100)
				{
					//alert(" Target overflow ");
					var val = $("#"+textId).val();
					target = val.substring(0, (val.length)-1);
					$("#"+textId).val(target);
					$('#'+id).val((totalTarget*target)/100);
					
					return false;
				}

				remVal = (remVal + parseInt($('#'+id).val()));
				
				if(valPer > 0)
				{
					$("#targetPre").text("Remaining Target : "+ parseInt(valPer-remTarget)  +"%");
					//$("#targetVal").text("Remaining Value : "+ remVal);
				}
			}
		}
		else
		{
			alert("Please enter total target");
			$(".abc").val("");
		}
		
	}

	$("#selectall").click(function () {
    	$('.select').attr('checked', this.checked);
	});

	function viewClient()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/target/beforeTargetView.action",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}


$.subscribe('validate', function(event,data)
{
	if($("#empName").val() == null || $("#empName").val() == "" ||  $("#empName").val() == "-1")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select One Employee !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}

	var selVal = $("input[name='targetOn']:checked").val();
	if(selVal == null || selVal == "")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select Target On !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}
	if($("#targetMonth").val() == null || $("#targetMonth").val() == "")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select Month !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}

	//targetMonth
	/* 
	if(selVal == 'kpi')
	{
		var v = $('input[name="targetvalue"]');
		
		for(var a=0; a<v.length; a++)
			alert($("#"+v[a].name+a).val());


		var flagV = true;
		alert(">>>>>>>>>>"+v.text());
		for(var a=0; a<v.length; a++){if(v[a].val() != "" && v[a].val() != null){flagV = false;break;}}
		if(flagV)
		{
			$("#errZone").html("<div class='user_form_inputError2'>Please Fill One Target Value !</div>");	
	         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			 event.originalEvent.options.submit = false;
			 return;
		}
	}
	else if(selVal == 'offering')
	{
		if($("#totalTarget").val() == "" || $("#totalTarget").val() == null)
		{
			$("#errZone").html("<div class='user_form_inputError2'>Please Fill Total Target !</div>");	
	         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			 event.originalEvent.options.submit = false;
			 return;
		}
	}*/
	
});
	
</script>
<SCRIPT type="text/javascript">
function selectMe(me,one,two)
{
	if(me.checked)
	{
		$("#"+one+two).show();
		$("#"+two).show();
		
	}
	else
	{
		$("#"+one+two).hide();
		$("#"+two).hide();
	}
}
function selectMeKPI(me,one)
{
	if(me.checked)
	{
		$("#"+one).show();
		
	}
	else
	{
		$("#"+one).hide();
	}
}

</SCRIPT>
<STYLE type="text/css">
	/*new Table CSS for all table view added by sandeep */
 
	*{
		padding:0px; margin:0px;
	}
	td.tabular_head{
		color:#ffffff; text-indent:25px; font-size:13px; line-height:30px; font-weight:400; border-radius:5px 5px 0px 0px; -webkit-border-radius:5px 5px 0px 0px; -moz-border-radius:5px 5px 0px 0px;
		border-bottom:1px solid #e7e9e9;
	}
	td.footer_block{
		color:#ffffff; text-indent:25px; font-size:13px; line-height:30px; font-weight:400; border-radius:0px 0px 5px 5px; -webkit-border-radius:0px 0px 5px 5px; -moz-border-radius:0px 0px 5px 5px;
	}
	td.footer_block td{
		font-size:13px; line-height:30px; font-weight:400; border-right:1px solid #e7e9e9; padding:0px 0.5% 0px 1%;
	}
	td.tabular_cont td{
		 font-size:13px; line-height:30px; font-weight:400; border-right:1px solid #e7e9e9; padding:0px 0 0px 1%;
	}
</STYLE>
</head>
<body>
	<s:form id="formone" action="alloteTargetToEmploee" theme="css_xhtml" name="formone" method="post" 
			namespace="/view/Over2Cloud/wfpm/target">
		<s:hidden name="kpiIds" value="%{kpiIds}"></s:hidden>
				<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
							<td width="25%" class="label">Emp ID :</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<s:textfield name="empID" id="empID" cssClass="textField" 
		      					value="%{empID}" readonly="true" 
		      					placeholder="ID" />
		      				</td>
					
							<td width="25%" class="label">Emp Name :</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<s:textfield name="empName" id="empName" cssClass="textField" 
		      					value="%{empName}" readonly="true" 
		      					placeholder="Employee Name" />
		      				</td>
							
		      		</tr>
				
					<tr>
							<td width="25%" class="label">Mobile No :</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<s:textfield name="mobileno" id="mobileno" cssClass="textField" 
		      					value="%{mobileno}" readonly="true" 
		      					placeholder="Mobile Number" />
		      				</td>
					
							<td width="25%" class="label">Emp Designation:</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<s:textfield name="empDesg" id="empDesg" cssClass="textField"
						value="%{empDesg}" readonly="true"
						placeholder="Employee Designation" 
					/>
		      				</td>
							
		      		</tr>
		      		<tr>
							<td width="25%" class="label">Target Month :</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<sj:datepicker readonly="true"
						name="targetMonth" id="targetMonth" displayFormat="mm-yy"
						placeholder="Month" cssClass="textField"
						showButtonPanel="false" numberOfMonths="[1,2]" changeMonth="true"
						changeYear="true" showOn="focus" />
		      				</td>
							
		      		</tr>
		      		<tr>
							<td width="25%" class="label">Target On :</td>
		      				<td width="25%">
		      					<s:radio
					id="targetOn" name="targetOn"
					list="#{'kpi':'KPI','offering':'Offering'}" value="%{'kpi'}"
					onclick="showTargetPanel(this.value)"
				 />
		      				</td>
		      		</tr>
				</tbody>
				</table>
			<div class="clear"></div>
			<div  id="targetOnKPI" align="center" style="display: none;">
			<table width="100%" cellspacing="0" cellpadding="3" align="center">
				<tr>
					<td bgcolor="e7e9e9" class="tabular_head" valign="middle"
						colspan="7" style="color: black"><u>Target Allotment</u></td>
				</tr>
				<tr>
					<td bgcolor="#e7e9e9"
						style="border-bottom: 1px solid #e7e9e9; color: #000000;"
						valign="top" class="tabular_cont">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tbody>
							<tr>
							<td valign="middle" width="2.5%">&nbsp;</td>
								<td valign="middle" width="2.5%">&nbsp;</td>
								<td valign="middle" width="20%">KPI Name</td>
								<td valign="middle" width="20%">Target</td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
				
				<s:iterator value="mapKPIList" id="mapKPIList" status="counter">
				<s:if test="#counter.count%2 != 0">
				<tr>
					<td bgcolor="#ffffff"
						style="border-bottom: 1px solid #e7e9e9; color: #181818;"
						valign="top" class="tabular_cont">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tbody>
							<tr>
								<td valign="middle" width="2.5%" bgcolor="#ffffff"
									style="color: #000000; text-align: center;" ><s:property
						value="#counter.count" /></td>
						<td valign="middle" width="2.5%" align="center">
							<s:checkbox name="kpiId" fieldValue="%{key}"  
										onclick="selectMeKPI(this,'targetvalue%{#counter.index}');"></s:checkbox>
						</td>
					<td valign="middle" width="20%"><s:property
						value="%{value}" /></td>
					<td valign="middle" width="20%"><s:textfield
						name="targetvalue" id="targetvalue%{#counter.index}" cssClass="textField" cssStyle="display: none;"
						placeholder="Enter Target Value"></s:textfield></td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
				</s:if>
				<s:else>
				<tr>
					<td bgcolor="#e2e4e4"
						style="border-bottom: 1px solid #e7e9e9; color: #181818;"
						valign="top" class="tabular_cont">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tbody>
							<tr>
								<td valign="middle" width="2.5%" bgcolor="#e7e9e9"
									style="color: #000000; text-align: center;"><s:property
						value="#counter.count" /></td>
						<td valign="middle" width="2.5%" align="center">
							<s:checkbox name="kpiId" fieldValue="%{key}"
								onclick="selectMeKPI(this,'targetvalue%{#counter.index}');"></s:checkbox>
						</td>
					<td valign="middle" width="20%"><s:property
						value="%{value}" /></td>
					<td valign="middle" width="20%"><s:textfield
						name="targetvalue" id="targetvalue%{#counter.index}" cssClass="textField" cssStyle="display: none;"
						placeholder="Enter Target Value"></s:textfield></td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
				</s:else>
				</s:iterator>
				</table>
				<div class="clear"></div>
				<br>
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="buttonStyle" style="text-align: center;">
			         <sj:submit targets="Result1"
						clearForm="true" value="  Allot KPI Target  " effect="highlight"
						effectOptions="{ color : '#222222'}" button="true"
						onCompleteTopics="complete" onSuccessTopics="close"
						onBeforeTopics="validate" />
						
						<sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formone');"
									cssStyle="margin-left: 0px;margin-top: -45px;"
								>
								  	Reset
								</sj:a>
						<sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							cssStyle="margin-left: -2px;margin-top: -45px;margin-right: -291px;"
							onclick="viewClient()"
						>
						  	Back
						</sj:a>
						
				    </div>
			</div>
			<div id="targetOnOffering" style="display: none;">
				<div class="clear"></div>
				<!-- 
				<div class="newColumn">
					<div class="leftColumn">Total Target:</div>
					<div class="rightColumn">
						<s:textfield name="totalTarget"
							id="totalTarget" cssClass="textField"
							placeholder="Please enter target" onkeyup="setTotalTarget(this.value)" 
						/>
					</div>
					
				</div>
				 -->
				
	<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
							<td width="25%" class="label">Total Target:</td>
		      				<td width="25%">
		      					<span class="needed"></span>
		      					<s:textfield name="totalTarget"
							id="totalTarget" cssClass="textField"
							placeholder="Please enter target" onkeyup="setTotalTarget(this.value)" 
						/>
		      				</td>
					
							<td width="25%" class="label"> </td>
		      				<td width="25%"> </td>
	</tr>
	</tbody>
	</table>
		      					
				
				
				
				
				
				
				<div class="clear"></div>
				<table width="100%" cellspacing="0" cellpadding="3">
				<tr>
					<td bgcolor="#e7e9e9" class="tabular_head" valign="middle"
						colspan="7" style="color: black">Offering Allotment</td>
				</tr>
				<tr>
					<td bgcolor="#e7e9e9"
						style="border-bottom: 1px solid #e7e9e9; color: #000000;"
						valign="top" class="tabular_cont">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tbody>
							<tr>
		
								<td width="5%">
								<!-- 
								<s:checkbox name="selectAll" 
								    theme="simple"
									id="selectall" />
								-->	
								</td>
								<td valign="middle" width="20%">Offering</td>
								<td valign="middle" width="20%">Target (%)</td>
								<td valign="middle" width="20%">Value</td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
				<s:iterator value="mapOfferingList" id="mapOfferingList"
					status="counter">
					<s:if test="#counter.count%2 != 0">
						<tr>
							<td bgcolor="#ffffff"
								style="border-bottom: 1px solid #e7e9e9; color: #181818;"
								valign="top" class="tabular_cont">
								
<table cellspacing="0" cellpadding="0" width="100%">
	<tbody>
		<tr>
			<td width="5%"><s:checkbox cssClass="select"
				fieldValue="%{key}" name="offeringId" theme="simple" 
				onclick="selectMe(this,'targetInPer','%{#counter.count}');" /></td>
			<td valign="middle" width="20%"><s:property
				value="%{value}" /></td>
			<td valign="middle" width="20%"><s:textfield cssClass="abc"
				id="targetInPer%{#counter.count}"  name="targetInPercent"
				cssStyle="display: none; border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;padding:2px;font-size:12px;outline:medium none;height: 25px;width: 80%;line-height: 25px;"
				placeholder="Enter Target"
				onkeyup="setOfferingTargetValue(this.value, %{#counter.count}, this.id )"
				onblur="resetValue()"></s:textfield></td>
			<td valign="middle" width="20%"><s:textfield
				id="%{#counter.count}" name="offeringTargetValues" cssStyle="display: none;"
				placeholder="Enter Target Value" cssClass="textField" readonly="true"></s:textfield>
			</td>
		</tr>
	</tbody>
</table>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td bgcolor="#e2e4e4"
								style="border-bottom: 1px solid #e7e9e9; color: #181818;"
								valign="top" class="tabular_cont">
<table cellspacing="0" cellpadding="0" width="100%">
	<tbody>
		<tr>
		<td width="5%"><s:checkbox cssClass="select" 
		    onclick="selectMe(this,'targetInPer','%{#counter.count}');"
			fieldValue="%{key}" name="offeringId" theme="simple" /></td>
		<td valign="middle" width="20%"><s:property
			value="%{value}" /></td>
		<td valign="middle" width="20%"><s:textfield cssClass="abc"
			id="targetInPer%{#counter.count}" name="targetInPercent"
			placeholder="Enter Target"
			cssStyle="display: none; border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;padding:2px;font-size:12px;outline:medium none;height: 25px;width: 80%;line-height: 25px;"
			onkeyup="setOfferingTargetValue(this.value, %{#counter.count}, this.id )"
			onblur="resetValue()"></s:textfield></td>
		<td valign="middle" width="20%"><s:textfield
			id="%{#counter.count}" name="offeringTargetValues"  cssStyle="display: none;"
			placeholder="Enter Target Value" cssClass="textField" readonly="true"></s:textfield>
			</td>
		</tr>
	</tbody>
</table>
							</td>
						</tr>
					</s:else>
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
									<td valign="middle" width="20%" id='targetPre'><u>Remaining Target : 100%</u></td>
									<td valign="middle" width="20%" id='targetVal'></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table><br/>
			<div class="clear"></div>
			<!-- Buttons -->
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="buttonStyle" style="text-align: center;">
		         <sj:submit targets="Result1"
					clearForm="true" 
					value="  Allot Offering Target  " 
					effect="highlight"
					effectOptions="{ color : '#222222'}" 
					button="true"
					onCompleteTopics="complete" 
					onSuccessTopics="close"
					onBeforeTopics="validate" />
					
					<sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formone');"
									cssStyle="margin-left: 0px;margin-top: -45px;"
								>
								  	Reset
								</sj:a>
					
			    <sj:a 
			     	name="Cancel"  
					href="#" 
					cssClass="button" 
					indicator="indicator" 
					button="true" 
					onclick="viewClient()"
					cssStyle="margin-left: -2px;margin-top: -45px;margin-right: -323px;"
				>
				  	Back
				</sj:a>
			    </div>
			</div>
			<center><sj:div id="foldeffect" effect="fold">
				<div id="Result1"></div>
			</sj:div></center>
			<br><br><br>
	</s:form>
	
<SCRIPT type="text/javascript">
showTargetPanel('kpi');
</SCRIPT>
</body>
</html>