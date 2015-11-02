<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>

<link rel="stylesheet" href="newcss/w3.css">
<script type="text/javascript" src="<s:url value="/js/pcrm/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
<script type="text/javascript">

  $.subscribe('level1', function(event, data) {
	$("#addDialog").dialog('open');
	setTimeout(function() {
		closeAdd();
	}, 4000);
	reset("formone");
});
function closeAdd() {
	$("#addDialog").dialog('close');
	backToView();
}

function backToView() {
	$("#data_part")
			.html(
					"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
				type : "post",
				url : "view/Over2Cloud/patientCRMMaster/viewBudgetPlanHeader.action",
				success : function(subdeptdata) {
					$("#" + "data_part").html(subdeptdata);
				},
				error : function() {
					alert("error");
				}
			});
}

function reset(formId) {
	$("#" + formId).trigger("reset");
}

function fetchMonthValues(){

	var val=$("#month_for :selected").text();
	//alert(state);
	$.ajax({
	    type : "post",
	    //url : "view/Over2Cloud/Compliance/Location/getHeadOfficeList.action?countryId="+country,
	    url : "view/Over2Cloud/patientCRMMaster/getRemainMonths.action?searchField="+val,
	    success : function(data) {
	    	$('#month_for option').remove();
	$('#month_for').append($("<option></option>").attr("value",-1).text('Select Month'));
	$(data).each(function(index)
	{
	   $('#month_for').append($("<option></option>").text("value",this.NAME).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function onChangeTitleCase(val,id){
	
}

function getRemainingBalance(val,id){
	var pattern2 = /^\d+$/;
	//alert(id+" id  " +val+"  " + (pattern2.test(val)));
	$("#month_amount").css("background-color","#FFFFFF");
	var yr=$("#month_year").val();
	if((pattern2.test(val))){

		if( parseInt(val) > parseInt($("#"+id).val()) ){
		   	errZone2.innerHTML="<div class='user_form_inputError2'>Please Entered Amount Not Be Greater Than Remaining Amount </div>";
	        $("#month_amount").css("background-color","#ff701a");  //255;165;79
	        $("#month_amount").focus();
	        setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
	        setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
		}else{
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/patientCRMMaster/fetchRemainingBalance.action?searchField="+val.trim()+"&id="+yr,
			    success : function(data) {
			    	$("#"+id).val(data.remainBalance);
			},
			   error: function() {
			        alert("error");
			    }
			 });
		}
	}
	else{
	   	errZone2.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
        $("#month_amount").css("background-color","#ff701a");  //255;165;79
        $("#month_amount").focus();
        setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
		}
}

function fetchMonthRemainingBalance(val,id){
	var yr=$("#for_year").val();
	var pattern2 = /^\d+$/;
	$("#for_month").css("background-color","#FFFFFF");
	if((pattern2.test(val))){
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/patientCRMMaster/fetchMonthRemainingBalance.action?id="+val.trim()+"&yearName="+yr,
			    success : function(data) {
			    	$("#"+id).html(data.remainBalance);
			    	$("#monthBalance").val(data.remainBalance);
			},
			   error: function() {
			        alert("error");
			    }
			 });
	}
	else{
	   	errZone3.innerHTML="<div class='user_form_inputError2'>Please Select Month </div>";
        $("#for_month").css("background-color","#ff701a");  //255;165;79
        $("#for_month").focus();
        setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
		}
}


function validAmountForEmployee(val,id){
	//alert(id+val);
	var pattern2 = /^\d+$/;
	$("#"+val).css("background-color","#FFFFFF");

	if( (pattern2.test($("#"+val).val())) ){

		if( parseInt($("#"+val).val()) > parseInt($("#"+id).val()) ){
		   	errZone3.innerHTML="<div class='user_form_inputError2'>Please Entered Amount Not Be Greater Than Remaining Amount Or Select Month </div>";
	        $("#"+val).css("background-color","#ff701a");  //255;165;79
	        $("#"+val).focus();
	        setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
	        setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
		}else{
			
		}
	}
	else{
	   	errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Number </div>";
        $("#"+val).css("background-color","#ff701a");  //255;165;79
        $("#"+val).focus();
        $("#"+val).val("0");
        setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
		}
}

function viewMonthBudgetByGraph(){
		
var searchField=$("#month_year").val();
		
	$.ajax({
	    type : "post",
	    //url : "view/Over2Cloud/Compliance/Location/getHeadOfficeList.action?countryId="+country,
	    url : "view/Over2Cloud/patientCRMMaster/fetchBudgetMonthByGraph.action?searchField="+searchField,
	    success : function(data) {
	    	$('#result').html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}


function fetchEmployeeVal(deptid,div){

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/fetchEmployeeName.action?id='"+deptid+"&searchField="+$("#for_month").val(),
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select'));
	$(data).each(function(index)
	{
		
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getMonthByYear(id,div){
	var yr=$("#"+id).val();

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/fetchMonthByYear.action?id="+yr,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select'));
	$(data).each(function(index)
	{
		
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

</script>

<script>

   	viewMonthBudgetByGraph();

</script>

</head>
<body>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		buttons="{ 
    		'Close':function() { closeAdd() } 
    		}"
		closeTopics="closeEffectDialog" width="350" height="250">
		<sj:div id="level123"></sj:div>
	</sj:dialog>



<sj:dialog id="completionResult" showEffect="slide" hideEffect="explode"
	autoOpen="false" title="Confirmation Message" modal="true" width="400"
	height="150" draggable="true" resizable="true"
	buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}">
	<sj:div id="resultTarget" effect="fold">
	</sj:div>
	
</sj:dialog>
<div class="clear"></div>
<div class="list-icon"><!-- <div class="head">Add Prospective Associate</div> -->
<div class="head">Configure Budget Plan</div>
<div class="head"><img alt="" src="images/forward.png"
	style="margin-top: 60%; float: left;"></div>
<div class="head">Add</div>
</div>

<div style="float: left; padding: 20px 1%; width: 98%;">

<br>

<sj:accordion id="accordion" autoHeight="false">
	<!-- Country Data ***************************************************************************** -->
		<sj:accordionItem title="Plan Annual Budget" id="oneId">
	<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone1" name="formone1"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addFinancialYear" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone1" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="packageFields1" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             
				         <s:if test="%{key == 'year_for'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="financialYear"
                                      headerKey="-1"
                                      headerValue="Select Year"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      value="defaultValue"
                                      >
                          </s:select>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				    </div>
			        </div>
						</s:iterator>
							
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#222222'}"
									effectDuration="5000" button="true" onBeforeTopics="validate1"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone1');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
		</sj:accordionItem>
	
	<!--Country State Mapping *********************************************************************** -->
	<sj:accordionItem title="Plan Month Budget" id="twoId" >
	<div style="float: left; padding: 20px 1%; width: 98%;">
	
	<table>
	<tr>
	<td style="width: 60%; overflow: auto; " >
		<div class="border" id="result">
			GRAPH Coming SOON
		</div>
	</td>
	<td style="width: 35%; height: 500px; overflow: auto;  " >
				<div class="border" style="height: 97%">
				<s:form id="formone2" name="formone2" class="w3-container w3-card-8"
									namespace="/view/Over2Cloud/patientCRMMaster"
									action="addBudgetPlanMonth" theme="simple" method="post"
									enctype="multipart/form-data">
									<center>
										<div
											style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
											<div id="errZone2" style="float: center; margin-left: 7px;"></div>
										</div>
									</center>
								
								 <div class="w3-group">
    							<label class="w3-label">Budget&nbsp;Yearly&nbsp;for&nbsp;:</label>
  								<strong><div id="totalYearBalance" class="w3-select">
  								<s:property value="%{totalBalance}" /></div></strong>
    							
  								</div>
								
								    	
							<div class="clear"></div>
							<s:iterator value="packageFields2" status="status">
							
							<s:if test="%{key == 'month_reduce_amount'}">
							<div class="clear" ></div>
							</s:if>
							
							
									  <s:if test="%{mandatory}">
					     	<span id="complSpan" class="pIds2" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			             </s:if>
			             		<div class="w3-group" >
						  		<div class="w3-label"><s:property value="%{value}"/><s:if test="%{mandatory}">*</s:if> :</div>
				           		
							         <s:if test="%{key == 'month_year'}">
							         	<s:select 
			                                      id="%{key}"
			                                      name="%{key}" 
			                                      list="yearMap"
			                                      headerKey="-1"
			                                      headerValue="Select Year"
			                                      cssClass="w3-select"
			                                      cssStyle="width:82%"
			                                      theme="simple"
			                                      value="defaultValue"
			                                      onchange="viewMonthBudgetByGraph();"
			                                      >
			                          </s:select>
			                        
			                           </s:if>
			                            <s:elseif test="%{key == 'month_for'}">
							         	<s:select 
			                                      id="%{key}"
			                                      name="%{key}" 
			                                      list="monthMap"
			                                      headerKey="-1"
			                                      headerValue="Select Month"
			                                      cssClass="w3-select"
			                                      cssStyle="width:82%"
			                                      theme="simple"
			                                      >
			                          </s:select>
			                           </s:elseif>
			                           <s:elseif test="%{key == 'month_reduce_amount'}">
			                         		 <s:textfield  name="%{key}" id="%{key}" readonly="true" value="%{remainBalance}" maxlength="50" cssClass="w3-input" placeholder="Enter Data"/>
			                           </s:elseif>
			                           <s:elseif test="%{key == 'month_amount'}">
			                         		 <s:textfield  name="%{key}" id="%{key}"  onkeyup="getRemainingBalance(this.value,'month_reduce_amount');" maxlength="15" cssClass="w3-input" placeholder="Enter Amount"/>
			                           </s:elseif>
							         <s:else>
							         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="w3-input" placeholder="Enter Data"/>
							         </s:else>
							  
							    
						        </div>
									</s:iterator>
								<div class="w3-group" >
						  		<div class="w3-label">Currency:</div>
						  				<s:select 
			                                      id="currency"
			                                      name="currency" 
			                                      list="currencyMap"
			                                      headerKey="-1"
			                                      headerValue="Select Currency"
			                                      cssClass="w3-select"
			                                      cssStyle="width:82%"
			                                      theme="simple"
			                                      >
						  				</s:select>
						  		
				           		</div>	
									
									
												<!-- Buttons -->
									<center>
										<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
											alt="Loading..." style="display: none" />
									</center>
			
									<div class="clear">
										<div style="padding-bottom: 10px; margin-left: -76px"
											align="center">
											<sj:submit targets="level123" clearForm="true" value="Save"
												effect="highlight" effectOptions="{ color : '#222222'}"
												effectDuration="5000" button="true" onBeforeTopics="validate2"
												onCompleteTopics="level1" cssClass="w3-btn" />
											&nbsp;&nbsp;
											<sj:submit value="Reset" button="true"
												cssClass="w3-btn"
												onclick="reset('formone2');" />
											&nbsp;&nbsp;
											<sj:a 
												button="true" href="#" cssStyle="padding:0px;" onclick="backToView()"
												cssClass="w3-btn" >Back</sj:a>
										</div>
									</div>
								</s:form>
				
				</div>
	</td>
	</tr>
	</table>


</div>

	</sj:accordionItem>


	<!-- State City Data ************************************************************************ -->
	<sj:accordionItem title="Allocate Budget To Employee" id="threeId">
<div style="float: left; padding: 20px 1%; width: 98%;">

<s:hidden id="monthBalance" value="0"></s:hidden>
					<s:form id="formone3" name="formone3"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addBudgetPlanForEmployee" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone3" style="float: center; margin-left: 7px;"></div>
							</div>
						</center>
						
						<%-- <div class="newColumn" >
			  		    	<div class="leftColumn1">Budget&nbsp;Yearly&nbsp;for&nbsp;<s:property value="yearName"/>:</div>
	           				<div class="rightColumn1">
									<label><s:property value="%{totalBalance}" /></label>	           				
	           				 </div>
						</div> --%>
						<div class="newColumn" >
			  		    	<div class="leftColumn1">Remain&nbsp;Amount&nbsp;:</div>
	           				<div class="rightColumn1">
									<label><div id="remainBalForMonth">Select&nbsp;Month</div></label>	           				
	           				 </div>
						</div>
			

				<s:iterator value="packageFields3" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds3" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             
				         <s:if test="%{key == 'city_name'}">
				         	<s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv3('300')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:elseif test="%{key == 'for_year'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="yearMap"
                                      headerKey="-1"
                                      headerValue="Select Year"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                       value="defaultValue"
                                       onchange="getMonthByYear(this.id,'for_month')"
                                      >
                          </s:select>
                           </s:elseif>
                             <s:elseif test="%{key == 'dept'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="deptMap"
                                      headerKey="-1"
                                      headerValue="Select Department"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                       onchange="fetchEmployeeVal(this.value,'emp')"
                                      >
                          </s:select>
                           </s:elseif>
                            <s:elseif test="%{key == 'emp'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="{'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Employee"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
                           </s:elseif>
                            <s:elseif test="%{key == 'for_month'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="existMonthMap"
                                      headerKey="-1"
                                      headerValue="Select Month"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                     onchange="fetchMonthRemainingBalance(this.value,'remainBalForMonth')"
                                      >
                          </s:select><!--
                           onchange="fetchMonthRemainingBalance(this.value,'remainBalForMonth')"
                           --></s:elseif>
				         <s:elseif test="%{key == 'amount'}">
				         <s:textfield  name="%{key}" id="%{key}" onkeyup="validAmountForEmployee(this.id,'monthBalance')" maxlength="15" cssClass="textField" placeholder="Enter Data"/>
				         </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
						</s:iterator>
						
							
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#222222'}"
									effectDuration="5000" button="true" onBeforeTopics="validate3"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone3');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
			</div>





	</sj:accordionItem>
</sj:accordion></div>
</body>

</html>
