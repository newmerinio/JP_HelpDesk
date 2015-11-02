<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<title>Insert title here</title>
	<script type="text/javascript">
	 $(document).ready(function()
	 {
	 	$("#empL1").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#empL2").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#empL3").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#empL4").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	
		 $("#emp").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
		});	 	
	 });
	$.subscribe('level1', function(event,data)
	{
          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
	});
 function resetForm(formId) 
 {
	 $('#'+formId).trigger("reset");
 }
 function viewClient2222()
	{
	 configure_schedule();
	}

	function needEsc(){
		var data=$("#escalation").val();
		if(data=="Yes"){
		$("#l2").fadeIn();
		
		}
		if(data=="No"){
			$("#l2").fadeOut();
		}
	}
	function needEsc2(){

		var data=$("#empL1").val();
		if(data!="-1"){
		$("#l3").fadeIn();
		}
		if(data=="-1"){
			$("#l3").fadeOut();
		}
	}
	function needEsc3(){
		var data=$("#empL2").val();
		if(data!="-1"){
		$("#l4").fadeIn();
		}
		if(data=="-1"){
			$("#l4").fadeOut();
		}
	}
	function needEsc4()
	{
		var data=$("#empL3").val();
		if(data!="-1"){
		$("#l5").fadeIn();
		}
		if(data=="-1"){
			$("#l5").fadeOut();
		}
	}
	function changeinNextEsc(data, div, sId, sName, nId, nName)
	{
			var l1 = $("#empL1").val();
			var l2 = $("#empL2").val();
			var l3 = $("#empL3").val();
			var l4= $("#empL4").val();
			$.ajax({
				type :"post",
				url :"view/Over2Cloud/WFPM/Patient/nxxtLevelEscnext.action?l1="+l1+"&l2="+l2+"&l3="+l3+"&l4="+l4+"&div="+div+"&sId="+sId+"&sName="+sName+"&nId="+nId+"&nName="+nName,
				success : function(data)
				{
					$('#'+div).html(data);
			    },
			    error : function () {
					alert("Somthing is wrong to get get Next excalation Level");
				}
			});
	}
	function fetchEmp(div)
	{
		var deptId= $("#department11").val();
		$.ajax({
			type :"post",
			url :"view/Over2Cloud/patientCRMMaster/gettngEmployee.action?deptId="+deptId,
			success : function(data)
			{
				$('#'+div).html(data);
		    },
		    error : function () {
				alert("Somthing is wrong to get relationship sub type.");
			}
		});
	}
	function changeDiv(val)
	{
		if(val=='Date')
		{
			$("#dateDiv").show();
			$("#frequencyDiv").hide();
		}
		else
		{
			$("#dateDiv").hide();
			$("#frequencyDiv").show();
		}
	}
	function fetchmappedWith(val)
	{
		var aarr=['Activity Plan','Activity Approval','Event Plan','Event Approval','DCR Submission','DCR Approval','Expense','Claim','Expense Approval','Expense Clearance'];
		$('#mappedWith'+' option').remove();
		$('#mappedWith').append($("<option></option>").attr("value",-1).text("Select Mapped With"));
		for(var i=0;i<aarr.length;i++)
		{
			if(aarr[i]!=val)
			{
				 $('#mappedWith').append($("<option></option>").attr("value",aarr[i]).text(aarr[i]));
			}
		}
	}
	</script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Configure Timeline</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
    <div class="border">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
	   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/patientCRMMaster" action="addConfigureSchedule" theme="css_xhtml"  method="post"enctype="multipart/form-data">
    		<s:iterator value="configureSchduleDropDown" status="status">
			             <div class="newColumn">
			             <s:if test="%{key == 'configure_for'}"> 
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					              
					               	<s:if test="%{mandatory}">
					     				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			             			</s:if>
					                  	<s:select 
			                                      id="%{key}"
			                                      name="%{key}" 
			                                      list="#{'Activity Plan':'Activity Plan','Activity Approval':'Activity Approval','Event Plan':'Event Plan','Event Approval':'Event Approval','DCR Submission':'DCR Submission','DCR Approval':'DCR Approval','Expense':'Expense','Claim':'Claim','Expense Approval':'Expense Approval','Expense Clearance':'Expense Clearance'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}"
			                                      cssClass="select"
			                                      cssStyle="width:82%"
			                                      theme="simple"
			                                      onchange="fetchmappedWith(this.value);"
			                                      >
			                          </s:select>
			                        
						</div>
						</s:if>
						</div>
						<div class="newColumn">
						 <s:if test="%{key == 'department'}"> 
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
						
						 <s:if test="%{mandatory}">
					     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>11#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			             </s:if>
						<s:select 
		                              id="%{key}11"
		                              name="%{key}" 
		                              list="departmentData"
		                              headerKey="-1"
                                      headerValue="Select %{value}"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
		                              onchange="fetchEmp('empDiv')"
		                              >
		                   
		                  </s:select>
						</div>
			 			</s:if>
						</div>
						<div class="newColumn">
						<s:if test="%{key == 'emp'}"> 
							
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
							<s:if test="%{mandatory}">
					     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			             	</s:if>
							<div id="empDiv">	
								<s:select 
					                              id="%{key}"
					                              name="%{key}" 
					                              list="{}"
					                              cssClass="select"
					                              cssStyle="width:24.5%"
					                              >
					                  	</s:select>
					         </div>         	
						</div>
						</s:if>
						</div>
    		</s:iterator>
			<div class="newColumn">
				<div class="leftColumn1">Schedule For:</div>
				<div class="rightColumn1">
				   <s:radio 
                              id="scheduleFor"
                              name="scheduleFor" 
                              list="#{'Date':'Date','Frequency':'Frequency'}"
                              onchange="changeDiv(this.value);"
                              >
                  </s:radio>
				</div>
			</div>
			<div class="clear"></div>
    		<div id="dateDiv" style="display: none;">
    		  <s:iterator value="configureSchduleDate" status="status">
    		    <div class="newColumn">
    			<s:if test="%{key == 'deadline_date'}"> 
		          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		          <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
		          
		          	<s:if test="%{mandatory}">
					  <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			        </s:if>
		          
			  		<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
		          </div>
		         </s:if>
        	    </div>
	    		</s:iterator>
	    		<s:iterator value="configureSchduleTime" status="status">
	    		<div class="newColumn">
	    			<s:if test="%{key == 'deadline_time'}"> 
			          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			          <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
			          
			          	<s:if test="%{mandatory}">
						  <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				        </s:if>
				  		<sj:datepicker id="%{key}" name="%{key}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
			          </div>
			         </s:if>
	        	</div>
	    		</s:iterator>
    		</div>
   			<div id="frequencyDiv" style="display: none;">
   			  <div class="newColumn">
						<div class="leftColumn1">Start From:</div>
						<div class="rightColumn1">
						
						<s:select 
		                              id="startFrom"
		                              name="startFrom" 
		                               headerKey="-1"
                                      headerValue="Select Start From"
		                              list="{'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'}"
                                      cssStyle="width:82%"
                                      theme="simple"
		                              >
		                  </s:select>
						</div>
						</div>
						 <div class="newColumn">
						<div class="leftColumn1">Due Till:</div>
						<div class="rightColumn1">
						
						<s:select 
		                              id="dueTill"
		                              name="dueTill" 
		                               headerKey="-1"
                                      headerValue="Select Due Till"
		                              list="{'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'}"
                                      cssStyle="width:82%"
                                      theme="simple"
		                              >
		                  </s:select>
						</div>
						</div>
   			
   			</div>
   			<div class="clear"></div>
   			  <div class="newColumn">
						<div class="leftColumn1">Mapped With:</div>
						<div class="rightColumn1">
						
						<s:select 
		                              id="mappedWith"
		                              name="mappedWith" 
		                              headerKey="-1"
                                      headerValue="Select Mapped With"
		                              list="{}"
                                      cssStyle="width:82%"
                                      theme="simple"
		                              >
		                  </s:select>
						</div>
						</div>
   			
            <s:iterator value="configureSchduleDate" status="status">
    		<div class="newColumn">
    			<s:if test="%{key == 'reminder1'}"> 
		          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		          <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
		          
		          	<s:if test="%{mandatory}">
					  <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			        </s:if>
		          
			  		<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
		          </div>
		         </s:if>
        	</div>
        	<div class="newColumn">
    			<s:if test="%{key == 'reminder2'}"> 
		          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		          <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
		          	<s:if test="%{mandatory}">
					  <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			        </s:if>
		          
			  		<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
		          </div>
		         </s:if>
        	</div>
        	<div class="newColumn">
    			<s:if test="%{key == 'reminder3'}"> 
		          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		          <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
		          
		          	<s:if test="%{mandatory}">
					  <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			        </s:if>
		          
			  		<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
		          </div>
		         </s:if>
        	</div>
    		</s:iterator>
		
			<div class="newColumn">
			<div class="leftColumn1">Escalation:</div>
			<div class="rightColumn1">
			<s:select 
		                              id="escalation"
		                              name="escalation" 
		                              list="#{ 'No':'No','Yes':'Yes'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="needEsc()"
		                              >
		                  	</s:select>
			</div>
			</div>
			<div id = "l2" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
								id="empL1"
		                        name="l2" 
                              	list="escL2Emp"
                              	cssClass	="select"
								cssStyle	="width:25%;height:40%"
                              	multiple	="true"
                              	onchange="changeinNextEsc(this.value, 'l3esc' ,'empL2', 'l3', 'empL3', 'l4')"
		                              >
		                  	</s:select>
			</div>
			</div>
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;2&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  		<sj:datepicker id="l2EscDuration" name="tat2" readonly="true" onchange="needEsc2();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			</div>
			<div id = "l3" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l3esc">
			<s:select 
		                              id="empL2"
		                              name="l3" 
		                              list="escL3Emp"
		                              cssClass="select"
		                              cssStyle	="width:25%;height:40%"
                              	multiple	="true"
                              	onchange="changeinNextEsc(this.value, 'l4esc' ,'empL3', 'l4', 'empL4', 'l5')"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;3&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l3EscDuration" name="tat3" readonly="true" onchange="needEsc3();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			</div>
			<div id = "l4" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l4esc">
			<s:select 
		                              id="empL3"
		                              name="l4" 
		                              list="escL4Emp"
		                              cssClass="select"
		                              cssStyle	="width:25%;height:40%"
                              		multiple	="true"
                              		onchange="changeinNextEsc(this.value, 'l5esc' ,'empL4', 'l5', 'empL5', 'l5')"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;4&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l4EscDuration" name="tat4" readonly="true" onchange="needEsc4();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			</div>
			<div id = "l5" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l5esc">
			<s:select 
		                              id="empL4"
		                              name="l5" 
		                              list="escL5Emp"
		                              cssClass="select"
		                              cssStyle	="width:25%;height:40%"
                              			multiple	="true"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;5&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  		<sj:datepicker id="l5EscDuration" name="tat5" readonly="true"  placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			</div>
	<div class="clear"></div>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="patientresult" 
            clearForm="true"
            value="Save" 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="level1"
            cssClass="submit"
            indicator="indicator2"
            onBeforeTopics="validate"
          />
           <sj:a 
	     	    name="Reset"  
					href="#" 
					cssClass="submit" 
					indicator="indicator" 
					button="true" 
					onclick="resetForm('formone');"
					cssStyle="margin-left: 193px;margin-top: -43px;"
					>
					  	Reset
					</sj:a>
				          <sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="submit" 
							indicator="indicator" 
							button="true" 
							cssStyle="margin-left: 145px; margin-top: -25px;"
							onclick="viewClient2222()"
							cssStyle="margin-top: -43px;"
					
							>
					  	Back
					</sj:a>
					    </div>
	        <sj:div id="patientaddition"  effect="fold">
               <div id="patientresult"></div>
           </sj:div>
	</s:form>
	</div>
</body>
</html>