<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
	<head>
	
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">	
$(document).ready(function(){
$("#contactId").multiselect();
$("#empId").multiselect();
});
</script>
	
	<script type="text/javascript">
function resetForm(formClientTakeaction)
{
	$('#'+formClientTakeaction).trigger("reset");
	$("#lastStatus").html("NA");
}

</script>
		<script type="text/javascript">
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
			
			$.subscribe('level2', function(event,data)
			        {
			          setTimeout(function(){ $("#orglevel123").fadeIn(); }, 10);
			          setTimeout(function(){ $("#orglevel123").fadeOut();cancelButton();}, 4000);
			          $('select').find('option:first').attr('selected', 'selected');
			         
			 
			});

			function cancelButton()
			 {
				 $('#completionResult').dialog('close');
				 cancelpage();
			 }
			
			$.subscribe('validateTakeaction', function(event,data)
			        {
			
			    		if(document.formClientTakeaction.offeringId.value!=null && document.formClientTakeaction.offeringId.value=="" || document.formClientTakeaction.offeringId.value=="-1")
			            {
			            	clearAllErroDiv();
			            	errorofferingId.innerHTML="Error: Select data in field";
			            	event.originalEvent.options.submit = false;
			            	return false;
			            }
			    		else if(document.formClientTakeaction.statusId.value!=null && document.formClientTakeaction.statusId.value=="" || document.formClientTakeaction.statusId.value=="-1")
			            {
			            	clearAllErroDiv();
			            	errorstatusId.innerHTML="Error: Select data in field";
			            	event.originalEvent.options.submit = false;
			            	return false;
			            }
			            else if(document.formClientTakeaction.comment.value!=null && document.formClientTakeaction.comment.value=="" || document.formClientTakeaction.comment.value=="-1")
			            {
			            	clearAllErroDiv();
			            	errorcomment.innerHTML="Error: Fill data in field";
			            	event.originalEvent.options.submit = false;
			            	return false;
			            }
			            else if(document.formClientTakeaction.maxDateTime.value!=null && document.formClientTakeaction.maxDateTime.value=="" || document.formClientTakeaction.maxDateTime.value=="-1")
			            {
			            	clearAllErroDiv();
			            	errormaxDateTime.innerHTML="Error: Fill data in field";
			            	event.originalEvent.options.submit = false;
			            	return false;
			            }
			    		clearAllErroDiv();
			
			        });
			
			function clearAllErroDiv()
			{
				$("div[id^='error']").each(function() {
			  	  // `this` now refers to the current element
				     this.innerHTML="";
			  	});
			}
			
			
			$.subscribe('validate22', function(event,data)
					{
					var timValidation = $("#textdataid").val();
					 if(timValidation == "You can schedule." )
					 { 
						 $("#"+"textdataid").css("background-color","#ff701a");
						 event.originalEvent.options.submit = true;
					}
					else
						{
							 $("#"+"textdataid").css("background-color","#ff701a");
							event.originalEvent.options.submit = false;
						}
					    var mystring = $(".pIds").text();
					    var fieldtype = mystring.split(",");
					    var pattern = /^\d{10}$/;
					    for(var i=0; i<fieldtype.length; i++)
					    {
					        
					        var fieldsvalues = fieldtype[i].split("#")[0];
					        var fieldsnames = fieldtype[i].split("#")[1];
					        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
					        var validationType = fieldtype[i].split("#")[3];
					          
					        $("#"+fieldsvalues).css("background-color","");
					        errZone.innerHTML="";
					        if(fieldsvalues!= "" )
					        {
					            if(colType=="D"){
					            if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
					            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
					              }
					            }
					            else if(colType =="T"){
					            if(validationType=="n"){
					            var numeric= /^[0-9]+$/;
					            if(!(numeric.test($("#"+fieldsvalues).val()))){
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
					              }    
					             }
					            else if(validationType=="an"){
					             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
					              }
					            }
					             else if(validationType=="ans"){
						            var allphanumeric =  /^[0-9a-zA-Z\s\r\n@!#\$\^%&*()+=\-\[\]\\\';,\.\/\{\}\|\":<>\?]+$/;
						            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
						             {
							            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric and Special Characters of "+fieldsnames+"</div>";
							            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;
						              }
					             }
					            else if(validationType=="a"){
					            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;     
					              }
					             }
					            else if(validationType=="m"){
					           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
					            {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					            }
					            else if (!pattern.test($("#"+fieldsvalues).val())) {
					                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
					                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					               }
					             } 
					             else if(validationType =="e"){
					             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
					             }else{
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
					            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					            $("#"+fieldsvalues).focus();
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            event.originalEvent.options.submit = false;
					            break;
					               } 
					             }
					             else if(validationType =="w"){
					             
					             
					             
					             }
					           }
					           
					            else if(colType=="TextArea"){
					            if(validationType=="an"){
					            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
					               }
					             }
					            else if(validationType=="mg"){
					             
					             
					             }    
					            }
					            else if(colType=="Time"){
					            if($("#"+fieldsvalues).val()=="")
					            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
					              }    
					            }
					            else if(colType=="Date"){
					            if($("#"+fieldsvalues).val()=="")
					            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
					              }
					             }  
					        }
					        else
					        {
					        	$('#completionResult').dialog('open');
					        }
					    }
					    if(event.originalEvent.options.submit == true)  
					    {
					    	var reason = $("#compelling_reasonee").val();
							if(reason == "")
							{
								errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Compelling Reasons  </div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#compelling_reason").focus();
					            $("#compelling_reason").css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
							}
							else
							{
								event.originalEvent.options.submit = true;
							}
						}      
			});
			function cancelpage(){
				var isExistingClient = '${isExistingClient}';
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

function fetchLastStatus(id, offeringId)
{
	//alert(id+" = "+offeringId);
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/client/fetchLastStatusForOffering.action",
		data : "id="+id+"&offeringId="+offeringId,
		success : function(data){
		//alert(data.status);
			$("#lastStatus").html(data.status);
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
		<script type="text/javascript">
		function showToDate()
			{
			var status = $("#status option:selected").text().trim();
			if(status == "05 Intro Meeting" || status == "06 Intro Presentation")
			{
				document.getElementById("showToDateId").style.display="block";
			}
			else
			{
				document.getElementById("showToDateId").style.display="none";
			}
			}
		</script>
		<script type="text/javascript">
			function showClosureDateDiv()
			{
				document.getElementById("forclosuredatehideId").style.display="none";
				document.getElementById("showeditDivId").style.display="block";
			}
		</script>
		<script type="text/javascript">
			function viewOpportunityValEdit()
			{
				document.getElementById("hideopportunityValueId").style.display="none";
				document.getElementById("showoOportunityvaluefieldsID").style.display="block";
			}
		</script>
		<script type="text/javascript">
			function viewOpportunityNameEdit()
			{
				document.getElementById("hideOppBriefFieldID").style.display="none";
				document.getElementById("showoOportunityNamefieldsID").style.display="block";
			}
		</script>
		<link href="css/style3.css" rel="stylesheet" type="text/css" >
		
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
//				alert(data.timestatus);

document.getElementById("timeValidation11").style.display="block";
			 //$("#timeValidation11").text(data.timestatus);
			 $("#textdataid").val(data.timestatus);         
				}, 
				error   : function(){
					
				  }	
		       });
			}
		function calculateTime(scheduletime)
		{
			var time=$("#time").val();
	//alert(time);
		}
		</script>
	</head>
	<body>
	<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
         <sj:div id="orglevel" effect="fold"></sj:div>
</sj:dialog>
	
	<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<!-- <div class="head">Take Action</div> -->
		<div class="head"><%-- <s:property value="#parameters.mainHeaderName"/>  --%>Prospective Client
		</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;">
		</div><div class="head">Create Activity</div>
	</div>
	<div class="clear"></div>
	
	<div class="border">
	<s:form id="formClientTakeaction" name="formClientTakeaction" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/client" 
		action="takeActionOnClient" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="id" value="%{id}" /> 
	<s:hidden id="clientStatus" name="clientStatus" />
	<s:hidden name="id" value="%{id}" />  
	<s:hidden id="time" name="time" value="%{time}" />  
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:left; margin-left: 7px"></div> 
		</div>
		
		<div class="clear"></div>
	
	<div class="form_menubox">
			<div style="font-weight: bold; font-size: large;" align="center"><s:property value="%{clientName}"/> </div>
			<br>
			<br>
	
			<span id="form2MandatoryFields" class="pIds" style="display: none; ">offeringId#Offering#D#,</span>
			<div class="newColumn">	
				<div class="leftColumn1">Offering:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<s:select 
						id="offeringId"
						name="offeringId" 
						list="offeringList"
						headerKey="-1"
						headerValue="Select" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="fetchLastStatus(%{id},this.value);"
						>
					</s:select>
				</div>
			</div>	
			
			<div class="newColumn">
				<div class="leftColumn1">Last Status: </div>
				<div class="rightColumn1">
					<b><div id="lastStatus">NA</div></b>
				</div>
			</div>			
			
			<div class="clear"></div>
			<div class="newColumn">
			<div class="leftColumn1">Client Contact:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:select 
			     		   id="contactId"
			     		   name="contactId"
			     		   list="contactPersonMap"
			     		   multiple="true"	
			     		   cssClass="select"
			     		   cssStyle="width:18%"
			     		   >
			     	</s:select>
			</div>
			
			</div>   
			<div class="newColumn">
				<div class="leftColumn1">Employees:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <s:select 
				     		   id="empId"
				     		   name="empId"
				     		   multiple="true"	
				     		   list="employee_basicMap"
				     		   cssClass="select"
				     		   cssStyle="width:18%"
				     		   >
				     	</s:select>
				</div>
			</div>
			
			<div class="clear"></div>
			<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none; ">statusId#Next Activity#D#,</span>			
				<div class="leftColumn1">Next Activity</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<s:select 
						id="status"
						name="status" 
						list="clientStatusList"
						headerKey="-1"
						headerValue="Select" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="fillStatus();showToDate();"
						>
					</s:select>
				</div>
			</div>
			<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none; ">maxDateTime#Date Time#Date#,</span>
				<div class="leftColumn1">Next Schedule:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<sj:datepicker name="date" id="date" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true" onchange="validateTime(%{id}, this.value);"/>
				</div>
			</div>
			<div class="newColumn" style="display: none;" id="showToDateId">
				<div class="leftColumn1">Schedule To:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<sj:datepicker name="to_maxDateTime" id="to_maxDateTime" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true"/>
				</div>
			</div>
			<div id="timeValidation11" style="margin-left: 81%; display: none;width: 20%; color: red;" >
				<s:textfield name="textdata" id="textdataid" value="" />
			</div>
			<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none; ">salesstages#Sales Stages#D#,</span>			
				<div class="leftColumn1">Sales Stages:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					 <s:select
                      id="salesStageId"
                      name="sales_stages"
                      list="salesStageMap"
	  				  headerKey="-1"
                      headerValue="Select"
                      cssClass="select"
                      value="%{presalesStage}"
                      cssStyle="width:82%" 
                      >
                      </s:select>
				</div>
			</div>
			
				<div class="newColumn" style="display: none;" id="showCompReasonId">
				<div class="leftColumn1">Compelling Reasons: </div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<textarea rows="1" placeholder="Enter Data" maxlength="255" name="compelling_reason" id="compelling_reason" style="width: 81%;" cssClass="textField"></textarea>
				</div>
				</div>
			<div class="newColumn">
				<div class="leftColumn1">Comment: </div>
				<div class="rightColumn1">
					<textarea rows="1" placeholder="Enter Data" maxlength="255" name="comments" id="comments" style="width: 81%;" cssClass="textField"></textarea>
				</div>
			</div>
			
			
		</div>			
					<div class="clear"></div>
					<br><br>
					<!-- Buttons -->
					<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div class="buttonStyle">
				         <sj:submit 
				            targets="orglevel" 
	                        clearForm="true"
	                        value="Create Activity" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level2"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        onBeforeTopics="validate22"
				          />
				          <sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="submit" 
									button="true" 
									onclick="resetForm('formClientTakeaction');" 
								>
								  	Reset
								</sj:a>
							     <sj:a 
							     	name="Cancel"  
									href="#" 
									targets="result" 
									cssClass="submit" 
									indicator="indicator" 
									button="true" 
									onclick="cancelpage()"
								>
								  	Back
								</sj:a>
					    </div>
			</s:form>
		</div>
		</div>
	</body>
</html>

