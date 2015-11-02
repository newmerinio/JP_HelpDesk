<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		$(document).ready(function()
				{
					$("#contactIdd").multiselect({
						   show: ["", 200],
						   hide: ["explode", 1000]
					});

					$("#empIdd").multiselect({
						   show: ["", 200],
						   hide: ["explode", 1000]
					});
				});

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
			
			$.subscribe('level12', function(event,data)
			        {
		    //    	  id=data.id;
			          setTimeout(function(){ $("#takeactionOnFullViewID").fadeIn(); }, 10);
			          setTimeout(function(){ $("#takeactionOnFullViewID").fadeOut();cancelButton(); }, 6000);
			          // $('select').find('option:first').attr('selected', 'selected');
					});
			 function cancelButton()
			 {
				 $("#completionResult").dialog('close');
				 fullView();
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
			
			
			$.subscribe('validate', function(event,data)
					{

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
					        if(event.originalEvent.options.submit == true)  
						    {
						    	$("#completionResult").dialog('open');		
						    }
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
			$("#lastStatusId").html(data.status);
		}, 
		error   : function(){
			
		}	
});
}

function fullView()
{
<%--	var id = $("#id").val();
	var type="0";
	alert("ID IS >>>>>>>>>>"+id+"              Type Is >>>>>>>>>>>>>>"+type);
	if(type == '0')
	{
		$("#data_part").html("");
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#data_part").load("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&uid="+uid+"&isExistingClient=0&mainHeaderName=Client&currDate="+monthCounter);
	}
	else
	{
		$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&uid="+uid+"&isExistingClient="+isExistingClient+"&mainHeaderName="+tempHeaderName));
		$("#data_part").html("");
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$("#data_part").load(encodeURI("/view/Over2Cloud/WFPM/Associate/viewAssociateFullHistory.action?id="+id+"&isExistingAssociate=0&mainHeaderName=Associate&currDate="+monthCounter));
	}  --%>
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	type : "post",
	url  : "view/Over2Cloud/wfpm/dashboard/beforeCommonDashboard.action",
	data : "currDate=0",
	success : function(data){
		$("#data_part").html(data);
	},
	error   : function(){
		alert("error");
	}
	});
}

function fillMOM(id)
{
	//alert(id);
	//$("#MOMDialog").dialog({title: jsonObject.OFFERING_NAME});
	var actionId = $("#actionType").val();
	if(actionId != -1)
	{
		$("#MOMDialog").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		//Offering address
		$.ajax({
			type: "post",
			url : "view/Over2Cloud/wfpm/dashboard/beforeDashboard.action",
			data: "id="+id+"&temp=0&page=full",
			success: function(data){
				$("#MOMDialog").html(data);
				$("#agendaMOM").val(jsonObject.CURRENT_STATUS);
			},
			error  : function(){
				alert("error");
			}
		});
	}
	else
	{
		
	}
	//$("#MOMDialog").dialog("open");
}
		</script>
		<script type="text/javascript">
			function showCompReason()
			{
				var reason = $("#salesstagesID").val();
				if(reason == "03 Qualify the Opportunity")
				{
					document.getElementById("showCompReasonIdd").style.display="block";
				}
				else
				{
					document.getElementById("showCompReasonIdd").style.display="none";
				}
				
			}
			
			
				</script>
		<link href="css/style3.css" rel="stylesheet" type="text/css" >
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
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
       <div id="takeactionOnFullViewID"></div>
</sj:dialog>
	<div class="middle-content">
	<div class="clear"></div>
	
	<div class="border">
	<s:form id="formClientTakeactionId" name="formClientTakeaction" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/client" 
		action="takeActionOnClient" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="id" value="%{id}"/> 
	<s:hidden id="clientStatus" name="clientStatus" /> 
	
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>
		</div>
		
		<div class="clear"></div>
	
	<div class="form_menubox" style="
    width: 1195px;
">
			<center>
				<div style="font-weight: bold; font-size: large;"><s:property value="%{clientName}"/> </div>
			</center>
			<br>
			<br>
	
			<span id="form2MandatoryFields" class="pIds" style="display: none; ">offeringIdD#Offering#D#,</span>
			<div class="newColumn">	
				<div class="leftColumn1">Offering:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<s:select 
						id="offeringIdD"
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
					<b><div id="lastStatusId">NA</div></b>
				</div>
			</div>			
			
			<div class="clear"></div>
			<div class="newColumn">
			<div class="leftColumn1">Client Contact:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:select 
			     		   id="contactIdd"
			     		   name="contactId"
			     		   list="contactPersonMap"
			     		   headerKey="-1"
			     		   headerValue=" Select"
			     		   multiple="true"
			     		   cssClass="select"
			 			   cssStyle="width:24%"
			     		   size="4"
			     		   >
			     	</s:select>
			</div>
			
			</div>   
			<div class="newColumn">
				<div class="leftColumn1">Employees:</div>
				<div class="rightColumn1"><span class="needed"></span>
				     <s:select 
				     		   id="empIdd"
				     		   name="empId"
				     		   multiple="true"	
				     		   list="employee_basicMap"
				     		   headerKey="-1"
				     		   headerValue=" Select"
				     		   cssClass="select"
				     		 	 cssStyle="width:24%"
				     		   size="4"
				     		   
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
                        onchange="fillStatus();"
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
						placeholder="Select Date" timepicker="true" />
				</div>
			</div>
			<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none; ">salesstagesID#Sales Stages#D#,</span>			
				<div class="leftColumn1">Sales Stages:</div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<s:select 
						id="salesstagesID"
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
			
				<div class="newColumn" style="display: none;" id="showCompReasonIdd">
				<div class="leftColumn1">Compelling Reasons: </div>
				<div class="rightColumn1">
					<span class="needed"></span>
					<textarea rows="1" placeholder="Enter Data" maxlength="255" name="compelling_reason" id="compelling_reason" style="width: 81%;" cssClass="textField"></textarea>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn1">Note: </div>
				<div class="rightColumn1">
					
					<textarea rows="1" placeholder="Enter Data" maxlength="255" name="comments" id="comments" style="width: 81%;" cssClass="textField"></textarea>
				</div>
			</div>
			
			<div class="newColumn">
			<div class="leftColumn1">Fill MOM:</div>
			<div class="rightColumn1">
			     <img alt="star" title="Click To Fill MOM" style="cursor: pointer;" onclick="fillMOM('<s:property value='%{uid}'/>');" 
			          src="images/WFPM/commonDashboard/attach.jpg">
			</div>
		</div>
	<br><br><br><br><br><br>
	
			<div id="MOMDialog"></div>
			
		</div>			
					<div class="clear"></div>
					<br><br>
					<!-- Buttons -->
					<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div class="buttonStyle">
				         <sj:submit
				            targets="takeactionOnFullViewID" 
	                        clearForm="true"
	                        value="Create Activity" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level12"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
				          />
					    </div>
					   
			</s:form>
			 			
		</div>
		</div>
	</body>
</html>

