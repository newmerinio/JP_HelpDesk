<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
<script type="text/javascript"
	src="js/patientActivityjs/patientActivity.js"></script>
<style type="text/css">

	.textField
		{
			height:20px;	
		}
		.select
		{
			height:25px;
			width:150px;	
		}
		
		span.needed
		{
			height:22px;	
		}
</style>	
<script type="text/javascript">


function closeDialogById(id){
	 $("#"+id).dialog('close');
}

function viewPatientDetails(val) 
{
	
	//alert(val);
	if(val.trim().length>0){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/patientAddBasicDetails.action?crm_id="+val,
		success : function(data) {
		alert(JSON.stringify(data));
		 $("#type").val(data.Type);
		 $("#nationality").val(data.Nationality);
		// alert(data.Patient_Name.split[0]);
		 $("#first_name").val(data.First_Name);
		 $("#last_name").val(data.Last_Name);
		 $("#mobile").val(data.Moblie_No);
		 $("#email").val(data.Email);
		 $("#age").val(data.Age);
		 $("#gender").val(data.Gender);
		 $("#address").val(data.Address);
		 $("#city").val(data.City);
		 $("#state").val(data.State);		
		 $("#blood_group").val(data.Blood_Group);
		 $("#allergic_to").val(data.Allergic_To);
		 $("#patient_category").val(data.Patient_Category);	
		 $("#patient_type").val(data.Patient_Type);	
		 $("#territory").val(data.Territory);	
	
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function viewPatientMobDetails(val) 
{
	//alert(val);
	if(val.trim().length>0){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/patientAddBasicDetails.action?mobile="+val,
		success : function(data) {
		 $("#type").val(data.Type);
		 $("#nationality").val(data.Nationality);
		// alert(data.Patient_Name.split[0]);
		 $("#first_name").val(data.First_Name);
		 $("#last_name").val(data.Last_Name);
		 $("#mobile").val(data.Moblie_No);
		 $("#email").val(data.Email);
		 $("#age").val(data.Age);
		 $("#gender").val(data.Gender);
		 $("#address").val(data.Address);
		 $("#city").val(data.City);
		 $("#state").val(data.State);		
		 $("#blood_group").val(data.Blood_Group);
		 $("#allergic_to").val(data.Allergic_To);
		 $("#patient_category").val(data.Patient_Category);	
		 $("#patient_type").val(data.Patient_Type);	
		 $("#territory").val(data.Territory);	 
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}



//  url : "/over2cloud/view/Over2Cloud/WFPM/Patient/beforeAddCorporate.action"
function checkCorAddNew(val){
	 if(val==="newAdd"){
		 $("#addNewCorporateDialog").dialog('open');
		 $("#formCorporateDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		 $.ajax({
				type : "post",
			    url : "view/Over2Cloud/patientCRMMaster/beforeAddAdhocCorporateTemp.action",
				success : function(data){
					 $("#formCorporateDiv").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
					$('#formCorporateDiv').html(data);
				},
				error   : function(){
					alert("error");
				}
			});
	 }
}

//  url : "view/Over2Cloud/WFPM/Associate/beforeAssociateAdd.action?modifyFlag=0&deleteFlag=0"
function checkRefAddNew(val){
	//alert(val);
	 if(val == "newAdd"){
		// 	alert(val + "2");
		 $("#addNewRefferalDialog").dialog('open');
		 $("#formReffDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/patientCRMMaster/beforeAddAdhocAssociateTemp.action",
			    data : "existAssociate="+ 0,
			    success : function(subdeptdata) {
			    	 $("#formReffDiv").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
						$('#formReffDiv').html(subdeptdata);
			},
			   error: function() {
		          alert("error");
		      }
			 });
	 }
}





$.subscribe('getCorporate', function(event,data)
		{
			getCorporate1(data);
		});
function getCorporate1(){
	$("#corporate").val('');
	commonSelectAjaxCall2('corporate_master','id','corp_name','','','','','corp_name','ASC','corporate','Select Corporate');
}

$.subscribe('getRefferal', function(event,data)
		{
			getRefferal1(data);
		});
function getRefferal1(){
	$("#referral").val('');
	commonSelectAjaxCall2('associate_basic_data','id','associateName','','','','','associateName','ASC','referral','Select Referral');
}


$(document).ready(function(){$('#age').val('');});

$.subscribe('vali', function(event,data)
		{
	//alert("Test")
		    var mystring = $(".ppIds").text();
	//alert(mystring);
	var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
			    $("#"+fieldsvalues).css("background-color","");
		        if(fieldsvalues!= "" )
		        {
		            	if(colType=="D")
		                {
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
		            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
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
		            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
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
		             else if(validationType =="sc"){
		                    var a=$("#"+fieldsvalues).val().trim();   
		                  //alert("A-Field Value Is...."+a.length);
		                     if(a == 0)
		                   {
		                      errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
		                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                      $("#"+fieldsvalues).focus();
		                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                      event.originalEvent.options.submit = false;
		                      break;
		                   }
		                else {
		                    $("#"+fieldsvalues).css("background-color","white");
		                }
		             }
		             else {
		                    $("#"+fieldsvalues).css("background-color","white");
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
		            else if(colType=="R"){
		            	var radio = document.getElementsByName("sent_questionair");
		            	//alert("radio[0]"+radio[0])
		            	if (radio[0].checked == false && radio[1].checked == false) {
		        			$("#per").html(" Please Select Radio Button !");
		        			errZone.innerHTML = "<div class='user_form_inputError2' >Please Select Radio Button !</div>";
		        			setTimeout(function() {
		        				$("#errZone").fadeIn();
		        			}, 10);
		        			setTimeout(function() {
		        				$("#errZone").fadeOut();
		        			}, 2000);
		        			$("#per").focus();
		        			$("#per").css("background-color", "#ff701a");
		        			event.originalEvent.options.submit = false;
		        			break;
		        			document.getElementById("eaday").disabled = true;
		        			/* $("#eaday").attr("disabled", "disabled"); */

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
		            else if(colType=="F"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select File "+fieldsnames+" Value  </div>";
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
		});

 function showSelectedDivs(v1){
		$("#"+v1+"Div").show(200);
	}
function hideSelectedDivs(v1){
		$("#"+v1+"Div").hide(200);
	}

function showNonWalkin(value){
	//
	val=$("#patient_type :selected").text();
	if(val=='Referral'){
		showSelectedDivs("Referral");
		hideSelectedDivs("Corporate");
		hideSelectedDivs("Walkin");
		$("#corporate").val('');
		$("#source").val('');
		hideSelectedDivs("event");
		$("#event").val('');
	}
	else if(val=='Corporate'){
		showSelectedDivs("Corporate");
		hideSelectedDivs("Referral");
		hideSelectedDivs("Walkin");
		$("#referral").val('');
		$("#source").val('');
		hideSelectedDivs("event");
		$("#event").val('');
	}
	else if(val=='Event'){
		showSelectedDivs("event");
		hideSelectedDivs("Corporate");
		hideSelectedDivs("Referral");
		hideSelectedDivs("Walkin");
		$("#referral").val('');
		$("#corporate").val('');
		$("#source").val('');
		
	}
	 else{
			showSelectedDivs("Walkin");
			$("#referral").val('');
			$("#corporate").val('');
			hideSelectedDivs("Referral");
			hideSelectedDivs("Corporate");
			hideSelectedDivs("event");
			$("#event").val('');
			commonSelectAjaxCall2('pcrm_source','id','src_subtype','src_type',value,'','','src_subtype','ASC','source','Select Source');
	} 
}

	function showOfferingClosed(val)
	{
		if(val=='4')
		{
			showSelectedDivs("service");
		}
		 else
		 {
				hideSelectedDivs("service");
				$("#service").val('');
		} 	
	}
	
	function showService(val)
	{
		commonSelectAjaxCall2('offeringlevel3','id','subofferingname','offeringname',val,'','','subofferingname','ASC','service','Select Service');
	}

</script>
<script type="text/javascript">
$.subscribe('level1', function(event,data)
       {
         setTimeout(function(){ $("#pBasicaddition").fadeIn(); $("#addDialog").dialog('open'); }, 1000);
         setTimeout(function(){ $("#pBasicaddition").fadeOut(); closeAdd(); }, 6000);
         
		});
function resetForm(formId) 
{
		$('#'+formId).trigger("reset");
		$('#crm_id').val("");
		$('#mobile1111').val("");
}

function closeAdd() {
	$("#addDialog").dialog('close');
	BackPatientBasic();
}


function commonSelectAjaxMyCall(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+'_widget option').remove();
		$('#'+targetid+'_widget').append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid+'_widget').append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }



function setNationality(str){

	str=$("#type :selected").text();	
	//alert("str  "+ str);
	if(str === 'Domestic')
	{
		commonSelectAjaxCall2('country','id','country_name','country_name','India','','','country_name','ASC','nationality','--Select Nationality--');
	}
	else if( str === 'International'){
		commonSelectAjaxCall2('country','id','country_name','','','','','country_name','ASC','nationality','--Select Nationality--');
	}else{
			alert("Select Patient Type");
		}
}

function setStates(val){
	if(val != 'IND'){
		commonSelectAjaxCall2('state','id','state_name','country_code',val,'','','state_name','ASC','state','--Select State--');
	}else if(val == '-1'){
		alert("Select Nationality");
	}
}

function setCityVal(val){
	if( val != '-1'){
		commonSelectAjaxCall2('city','id','city_name','name_state',val,'','','city_name','ASC','city','--Select City--');
	}else{
			alert("Select City");
		}
}


</script>
<script type="text/javascript">
function BackPatientBasic()
{
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
$.ajax({
   type : "post",
   url : "view/Over2Cloud/WFPM/Patient/beforeviewPatient.action",
   
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
<sj:dialog id="addNewCorporateDialog" modal="true" effect="slide"
	autoOpen="false" width="1200" height="540" title="Add New Corporate"
	loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0"
	>
	<div id="formCorporateDiv"></div>
</sj:dialog>
<sj:dialog id="addNewRefferalDialog" modal="true" effect="slide"
	autoOpen="false" width="1200" height="540" title="Add New Associate"
	loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0"
	>
	<div id="formReffDiv"></div>
</sj:dialog>
<div class="clear"></div>
<div class="list-icon"><!-- <div class="head">Add Prospective Client</div> -->
<div class="head">Patient Registration</div>
<div class="head"><img alt="" src="images/forward.png"
	style="margin-top: 60%; float: left;"></div>
<div class="head">Add</div>
</div>
<div class="clear"></div>
<div class="border">
<div class="container_block">
<div id="searchdiv" style="margin-left: 81px;">Record Search By :
<s:textfield name="crm_id" id="crm_id" cssClass="textField"
	placeholder="UHID" onblur="viewPatientDetails(this.value);"
	cssStyle="width: 14%;" />
      <s:textfield name="mobile"  id="mobile1111" maxlength="10" cssClass="textField" placeholder="Mobile No" onblur="viewPatientMobDetails(this.value);" cssStyle="width: 14%;"/> 
</div>
<div id="existenceResult"  style="margin-left: 81px;"></div>
<div style="float: left; padding: 10px 1%; width: 98%;">
<div
	style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
<div id="errZone" style="float: left; margin-left: 7px"></div>
</div>
<br>
<s:form id="formone" name="formone"
	namespace="/view/Over2Cloud/WFPM/Patient" action="addPatientBasicForm"
	theme="css_xhtml" method="post" enctype="multipart/form-data">
	<div class="menubox"><s:hidden id="crm_id" name="crm_id"
		value="0"></s:hidden><!--
   	<s:iterator value="clientDropDown" status="counter">
   <s:if test="%{mandatory}">
      <span id="mandatoryFields" class="ppIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
   </s:if>
</s:iterator> 

       -->
	<div class="newColumn" style="margin-left: -3px;"><span
		id="mandatoryFields" class="ppIds" style="display: none;">type#Patient
	Type#D#D,</span>
	<div class="leftColumn1">Patient Type:</div>
	<div class="rightColumn1"><span class="needed"></span> <s:select
		id="type" name="type"
		list="rhTypeMap"
		headerKey="-1" headerValue="Select Patient Type" cssClass="select"
		style="width:82%;margin-bottom: 5px;"
		onchange="setNationality(this.value);">
	</s:select></div>
	</div>
	<span id="mandatoryFields" class="ppIds" style="display: none;">nationality#Nationality#D#D,</span>
	<div class="newColumn">
	<div class="leftColumn1">Nationality:</div>
	<div class="rightColumn1"><span class="needed"></span> <s:select
		id="nationality" name="nationality" list="%{countryMap}" headerKey="-1"
		headerValue="Select Nationality" onchange="setStates(this.value);" cssClass="select"
		cssStyle="width:82%">
	</s:select></div>
	</div>
	<div class="clear"></div>
	<!-- Drop down offering  --> <!-- Text box --> 
	<!-- FN,LN,Mob -->
	<s:iterator value="patientBasicTextBox" status="counter" begin="0" end="2">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										  <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>	       
				    </div>
			        </div>
</s:iterator>

<!-- Email -->
	<s:iterator value="patientBasicTextBox" status="counter" begin="4" end="4">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										  <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>	       
				    </div>
			        </div>
</s:iterator>


	<!-- DOB -->
<s:iterator value="patientBasicTextBox" status="counter" begin="3" end="3">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						 <sj:datepicker id="%{key}" name="%{key}" readonly="true"
					value="Select Date" cssClass="textField" size="20" changeMonth="true" changeYear="true" maxDate="%{new java.util.Date()}"  yearRange="1900:2020"
					showOn="focus" displayFormat="dd-mm-yy"
					Placeholder="Select Date" />	       
				    </div>
			        </div>
</s:iterator>

<span id="mandatoryFields" class="ppIds" style="display: none;">gender#Gender#D#,</span>
	<div class="newColumn" style="">
	<div class="leftColumn1" style="">Gender : </div>
	<span id="mandatoryField" class="ppIds" style="display: none;">gender#Gender#D#,</span>
	<div class="rightColumn1"><span class="needed"></span><s:select id="gender" name="gender"
		list="#{'Male':'Male','Female':'Female'}" headerKey="-1"
		headerValue="Select Gender" cssClass="select"
		cssStyle="width: 82%; ">
	</s:select></div>
	</div>
	<div class="clear"></div>
	<!-- Addresss -->
		<s:iterator value="patientBasicTextBox" status="counter" begin="5" end="5">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										  <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>	       
				    </div>
			        </div>
	</s:iterator>
			
	<!-- State -->
			<s:iterator value="patientBasicTextBox" status="counter" begin="7" end="7">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#D#,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										  <s:select name="%{key}" id="%{key}"
		list="%{stageMap}"  onchange="setCityVal(this.value);"  headerKey="-1" headerValue="Select State" 
		cssClass="select" cssStyle="width:82%">
	</s:select>	       
				    </div>
			        </div>
			        <div class="clear"></div>
</s:iterator>

	<!-- City -->
	<span id="mandatoryField" class="ppIds" style="display: none;">city#City#D#,</span>
	<div class="newColumn" style="">
	<div class="leftColumn1">City:</div>
	<div class="rightColumn1">
			<span class="needed"></span>
			<s:select id="city" name="city"
				list="%{verticalMap}" headerKey="-1" headerValue="Select City"
				cssClass="select" cssStyle="width:82%">
			</s:select>
	</div>
	</div>
	
	<!-- Territory -->
			<s:iterator value="patientBasicTextBox" status="counter" begin="8" end="8">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>	       
				    </div>
			        </div>
</s:iterator>
	
		<div class="newColumn" style="">
	<div class="leftColumn1">Blood Group:</div>
	<div class="rightColumn1"><s:if test="%{mandatory}">
		<span class="needed"></span>
	</s:if> <s:select id="blood_group" name="blood_group"
		list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
		headerKey="-1" headerValue="Select Group" cssClass="select"
		cssStyle="width:82%">
	</s:select></div>
	</div>
	<div class="clear"></div>
		<s:iterator value="patientBasicTextBox" status="counter" begin="6" end="6">
			<s:if test="%{mandatory}">
						<span id="mandatoryField" class="ppIds" style="display: none;"><s:property
							value="%{key}" />#<s:property value="%{value}" />#<s:property
							value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
		<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										  <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>	       
				    </div>
			        </div>
</s:iterator>
	

	<div class="newColumn" style="">
	<div class="leftColumn1">Patient Category:</div>
	<div class="rightColumn1"><s:select id="patient_category"
		name="patient_category"
		list="rhSubTypeMap"
		headerKey="-1" headerValue="Select Patient Category" cssClass="select"
		cssStyle="width:82%">
	</s:select></div>
	</div>
	<div class="clear"></div>
	<div class="newColumn">
	<div class="leftColumn1">Source:</div>
	<div class="rightColumn1"><s:select id="patient_type"
		name="patient_type" list="#{'2':'Corporate','1':'Referral','4':'Event','3':'Other'}" headerKey="-1"
		headerValue="Select Source" cssClass="select" cssStyle="width:82%"
		onchange="showNonWalkin(this.value);">
	</s:select></div>
	</div>
	<div id="CorporateDiv" style="display: none;">
	<div class="newColumn">
	<div class="leftColumn1">Corporate:</div>
	<div class="rightColumn1"><s:select id="corporate"
		name="corporate" list="corporateMap" headerKey="-1"
		headerValue="Select Corporate" cssClass="select" cssStyle="width:82%"
		onchange="checkCorAddNew(this.value);">
	</s:select></div>
	</div>
	</div>

	<div id="ReferralDiv" style="display: none;">
	<div class="newColumn">
	<div class="leftColumn1">Referral:</div>
	<div class="rightColumn1"><s:select id="referral" name="referral"
		list="associateMap" headerKey="-1" headerValue="Select Referral"
		cssClass="select" cssStyle="width:82%"
		onchange="checkRefAddNew(this.value);">
	</s:select></div>
	</div>
	</div>
	
	<div id="eventDiv" style="display: none;">
	<div class="newColumn">
	<div class="leftColumn1">Event:</div>
	<div class="rightColumn1"><s:select id="event" 
		list="{'No Data'}" headerKey="-1" headerValue="Select Event"
		cssClass="select" cssStyle="width:82%"
		>
	</s:select></div>
	</div>
	</div>

	<div id="WalkinDiv" style="display: none;">
	<div class="newColumn">
	<div class="leftColumn1">Walk-in Source:</div>
	<div class="rightColumn1"><s:select id="source" name="source"
		list="{'No Data'}" headerKey="-1" headerValue="Select Source"
		cssClass="select" cssStyle="width:82%">
	</s:select></div>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Stage:</div>
	<div class="rightColumn1"><s:select id="stage" name="stage"
		list="#{'1':'Lead','2':'Prosoect','3':'Lost','4':'Closed'}"
		 cssClass="select" cssStyle="width:82%"
		onchange="showOfferingClosed(this.value);">
	</s:select></div>
	</div>
	
	<div id="serviceDiv" style="display: none;">
	<div class="newColumn">
	<div class="leftColumn1">Speciality:</div>
	<div class="rightColumn1"><s:select id="offering" 
		list="offeringMap" headerKey="-1" headerValue="Select Speciality"
		cssClass="select" cssStyle="width:82%" onchange="showService(this.value)">
	</s:select></div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Service</div>
	<div class="rightColumn1"><s:select id="service" name="service"
		list="{'No Data'}" headerKey="-1" headerValue="Select Service"
		cssClass="select" cssStyle="width:82%">
	</s:select></div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Remarks</div>
	<div class="rightColumn1">
		<s:textfield id="remark" name="remark" theme="simple" cssClass="textField" placeholder="Enter Remarks"></s:textfield>	
	</div>
	</div>
	</div>
	<!--
 <div class="newColumn" style="margin-top: -38px;">
<div class="leftColumn1">Department:</div>
<div class="rightColumn1">
              			<s:select 
                              id="dept_name"
                              name="dept_name" 
                              list="deptMap"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   		</s:select>
</div> 
</div>
      
--></div>
	<div class="newColumn" style="">
	<div class="leftColumn1">Questionnaire:&nbsp;</div>
	<span id="mandatoryField" class="ppIds" style="display: none;">sent_questionair#Questionnaire#R#,</span>
	<div class="rightColumn1"><span class="needed"></span> <s:radio
		list="#{'true':'Yes','false':'No'}" name="sent_questionair"
		id="sent_questionair" value="sent_questionair"
		onclick="findHiddenDiv(this.value);" /></div>
	</div>

	<div class="clear"></div>
	<!-- Buttons -->
	<center><img id="indicator2"
		src="<s:url value="/images/indicator.gif"/>" alt="Loading..."
		style="display: none" /></center>
	<div class="buttonStyle"
		style="text-align: center; margin-left: -100px;"><sj:submit
		targets="pBasicresult" clearForm="true" value="Save"
		effect="highlight" effectOptions="{ color : '#222222'}"
		effectDuration="5000" button="true" onCompleteTopics="level1"
		cssClass="submit" indicator="indicator2" onBeforeTopics="vali" />
	<sj:a name="Reset" href="#" cssClass="submit" indicator="indicator"
		button="true" onclick="resetForm('formone');"
		cssStyle="margin-left: 193px;margin-top: -43px;">
 	Reset
</sj:a> <sj:a name="Cancel" href="#" cssClass="submit" indicator="indicator"
		button="true" cssStyle="margin-left: 145px; margin-top: -25px;"
		onclick="BackPatientBasic();" cssStyle="margin-top: -43px;">
 	Back
</sj:a></div>

</s:form></div>
</div>
</div>
<sj:dialog id="addDialog" showEffect="slide" hideEffect="explode"
	autoOpen="false" modal="true" title="Status Message"
	openTopics="openEffectDialog" closeTopics="closeEffectDialog"
	width="250" height="110">

	<sj:div id="pBasicaddition" effect="fold">
		<div id="pBasicresult"></div>
	</sj:div>
</sj:dialog>

</body>
</html>
