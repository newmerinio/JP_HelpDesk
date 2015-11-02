
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	$("#resultDiv").css("display","none");
	$('#MOMDialog').html("");
	$(".justForRemovingIt").remove();
	$(".newColumnChk").remove();
}

function back()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$("#data_part").load("view/Over2Cloud/wfpm/dashboard/beforeCommonDashboard.action?currDate="+monthCounter);
}

function fillFields_AUTO()//CURRENT_STATUS_ID
{
	if(jsonObject != null)
	{
		$("#contactId").val(jsonObject.CONTACT_ID);
		$("#offeringId").val(jsonObject.OFFERING_ID);
		$("#clientId").val(jsonObject.CLIENT_ID);
		$("#statusId").val(jsonObject.CURRENT_STATUS_ID);
		$("#maxDateTimeOld").val(jsonObject.MAX_DATE_TIME);
		
		$("#offeringNameID").val(jsonObject.OFFERING_NAME);
		$("#currentStatusID").val(jsonObject.CURRENT_STATUS);
		$("#clientNameID").val(jsonObject.CLIENT_NAME);
		$("#clientAddressID").val(jsonObject.CLIENT_ADDRESS);
		
		
		var divContent = jsonObject.CLIENT_NAME+"&nbsp;&nbsp;";
		for(var i=0; i<jsonObject.CLIENT_RATING; i++)
		{
			divContent += '<img alt="star" src="images/WFPM/commonDashboard/star.jpg">&nbsp;';
		}
		$("#clientNameDIV").html(divContent);
		
		$("#offName").text(jsonObject.OFFERING_NAME);
		$("#contactName").text(jsonObject.CONTACT_NAME);
		$("#currentStatus").text(jsonObject.CURRENT_STATUS);
		
	}
}


function selectAction(value)
{
	$("#resultDiv").css("display","block");
	if(value == "0")
	{
		$("#resultDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeCloseTakeActon.action",
			data : "temp="+0,
			success : function(data){
				$("#resultDiv").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	else if(value == "1")
	{
		$("#resultDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeRescheduleTakeActon.action",
			success : function(data){
				$("#resultDiv").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	else if(value == "2")
	{
		$("#resultDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeLostTakeActon.action",
			success : function(data){
				$("#resultDiv").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	else if(value == "3")
	{
		$("#resultDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeReassignTakeActon.action",
			success : function(data){
				$("#resultDiv").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	else if(value == "4")
	{
		$("#resultDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/beforeConvertToExistingTakeActon.action",
			success : function(data){
				$("#resultDiv").html(data);
			},
			error   : function(){
				alert("error");
			}
		});
	}
	else
	{
		$("#resultDiv").html("");
		$("#resultDiv").css("display","none");
	}
}

function showCloseAction(value)
{
	if(value == "0")
	{
		$("#nextActivityDiv").css("display","block");
		$("#convertToExistingDiv").css("display","none");
	}
	else if(value == "1")
	{
		$("#convertToExistingDiv").css("display","block");
		$("#nextActivityDiv").css("display","none");
	}
}

$.subscribe('completeAction', function(event,data)
        {
          setTimeout(function(){ $("#resultIdOuter").fadeIn(); }, 10);
          setTimeout(function(){ $("#resultIdOuter").fadeOut(); cancelButton();}, 4000);
          //$('#actionForm').trigger("reset");
});

function fillMOM(id)
{
	//$("#MOMDialog").dialog({title: jsonObject.OFFERING_NAME});
	var actionId = $("#actionType").val();
	if(actionId != -1)
	{
		$("#MOMDialog").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		//Offering address
		$.ajax({
			type: "post",
			url : "view/Over2Cloud/wfpm/dashboard/beforeDashboard.action",
			data: "id="+id+"&temp="+temp,
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

function fillMomFields()
{
	var actionType = $("#actionType").val();
	var index = 1;
	if(actionType == "0")
	{
		var futureActionText = "For '";
		futureActionText += jsonObject.OFFERING_NAME;
		futureActionText += "' next activity is '";
		futureActionText += $("#statusIdNew option:selected").text();
		futureActionText += "' and due date is '";
		futureActionText += $("#maxDateTime").val();
		futureActionText += "' with comment '";
		futureActionText += $("#comment").val();
		futureActionText += "'.";
		//alert(">>"+futureActionText);
		
		$("#futureActionMOM1").val(futureActionText);
		index++;
	}
	
	var activityCheckbox = $("[name=chkOther]:checked");
	//alert(activityCheckbox.length);
	var futureActionText1;
	var chkId;
	var dynamicFutureActionDiv;
	for(var i=0; i<activityCheckbox.length; i++)
	{
		chkId = activityCheckbox[i].value;
		//alert("chkId:"+chkId);
		futureActionText1 = "For \'";
		futureActionText1 += $("#offeringIdOther"+chkId+" option:selected").text();
		futureActionText1 += "\' next activity is \'";
		futureActionText1 += $("#statusIdOther"+chkId+" option:selected").text();
		futureActionText1 += "\' and due date is \'";
		futureActionText1 += $("#maxDateTimeOther"+chkId).val();
		futureActionText1 += "\' with comment \'";
		futureActionText1 += $("#commentOther"+chkId).val();
		futureActionText1 += "\'.";
		
		if(index > 1)
		{
			dynamicFutureActionDiv = '<div style="float: left; width: 100%;" id="futureActionId'+index+'">';
			dynamicFutureActionDiv += '<div class="leftColumn2">Future Action '+index+':</div>';
			dynamicFutureActionDiv += '<div class="rightColumn2"><span class="needed"></span>';
			dynamicFutureActionDiv += '<textarea name="futureActionMOM"  id="futureActionMOM'+index+'" class="textField2" style="margin:0px 0px 10px 0px;">';
			dynamicFutureActionDiv += futureActionText1;
			dynamicFutureActionDiv += '</textarea></div></div>';
			//alert(dynamicFutureActionDiv);
			$("#futureActionId"+(index-1)).after($(dynamicFutureActionDiv));			
		}
		else
		{
			$("#futureActionMOM1").val(futureActionText1);
		}
		
		index++;
	}
}

function createOtherOfferingActivity(count, checked)
{
	//alert(count+"#"+checked);
	if(checked)
	{
		//$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var offeringId1 = $("#offeringId").val();
		for(var i=1; i<count; i++)
		{
			if($("#offeringIdOther"+i).val() != null && $("#offeringIdOther"+i).val() !="" 
				&& $("#offeringIdOther"+i).val() != "-1" && $("#offeringIdOther"+i).val() != "undefined")
			{
				offeringId1 += ","+$("#offeringIdOther"+i).val();
			}
		}
		//alert(count+"#"+offeringId1+"#"+temp);
		
		$.ajax({
			type: "post",
			url : "view/Over2Cloud/wfpm/dashboard/showActivityBlock.action",
			data: "offeringId="+offeringId1+"&temp="+temp+"&count="+count,
			success: function(data){
				//alert(data);
				$("#chkContainer"+count).after($(data));
			},
			error  : function(){
				alert("error");
			}
		});
	}
	else
	{
		$("#activityDiv"+count).remove();
		$("#chkContainer"+count).remove();
	}
}
$.subscribe('validate1', function(event,data)
{
	validate(event,data,"pIds");
	/*if(event.originalEvent.options.submit != false)
    {
    	$('#completionResult').dialog('open');
    }*/
});
function validate(event,data, spanClass)
{	
			var mystring=$("."+spanClass).text();
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
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
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
}

function showReferenceContacts(chkval, checked)
{
	if(checked)
	{
		document.getElementById("showReferenceContactID").style.display="block";
	}
	else
	{
		document.getElementById("showReferenceContactID").style.display="none";
	}
}
