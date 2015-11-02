<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<script type="text/javascript">
function viewAssociate()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralView.action?referralSubType=Individual&referralStatus=Approved",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
          setTimeout(function(){ $("#patientaddition").fadeOut(); cancelButton();}, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          getOrgNames();
        });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	viewAssociate();	
	}


function commonSelectAjaxCall(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	var module=$("#"+conditionVar_Value2).val();
	//alert("module  "+module)
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+module+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }


function getTerritory(val)
{
//	alert("Hello");
	commonSelectAjaxCall('territory','id','trr_name','trr_city',val,'','','','','territory','Select Territory');
}
$.subscribe('validate1', function(event,data)
		{
			validate(event,data,"pIds");
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
}

function getViewAdd(val)
{
	var referralSubType=$("#referralSubType").val();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/createIndivisualRef.action?referralSubType="+referralSubType,
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
}
fetchState('4179','state');
</script>
		
</head>
<body>
<div class="clear"></div>
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
		<sj:div id="patientaddition"  effect="fold">
           <div id="patientresult"></div>
        </sj:div>
    </sj:dialog>

	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">Referral</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add For</div>
    <div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
    <div class="head"><s:select
			id="referralSubType" 
			name="referralSubType" 
			list="#{'Individual':'Individual','Institutional':'Institutional'}" 
            theme="simple"
            cssStyle="height: 26px;margin-top: 4px;"
            onchange="getViewAdd(this.value)"
			cssClass="button"
            />
	</div>
	</div>
	<div class="clear"></div>
<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
        	<br>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/wfpmReferral" action="addindivisualRef" theme="css_xhtml"  method="post"enctype="multipart/form-data">
    <div class="menubox">
          <s:iterator value="referralDropDown" begin="0" end="0" status="status">
            <s:if test="%{mandatory}">
				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			</s:if>
	  	    <s:if test="#status.odd">
	  	    <s:if test="field_value=='category'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						          list="#{'Clinic':'Clinic','Hospital':'Hospital'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
	  	    </s:if>
	  	   <s:else>
	  	   <s:if test="field_value=='category'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="{field_value}"
								name="%{field_value}"
						          list="#{'Clinic':'Clinic','Hospital':'Hospital'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>        
	  	   </s:else>
  	   </s:iterator>
   
   		<s:iterator value="referralColumnMap" begin="0" end="3">
		   <s:if test="%{mandatory}">
		   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       </s:if>
		   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1" id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
		   <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1"  id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
	       </s:else>
  	   </s:iterator>
   
      <s:iterator value="referralCalender" begin="0" end="1" status="status">
       <s:if test="%{mandatory}">
	    <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{value}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
       <s:if test="#status.odd">
       <div class="newColumn">
       <div class="leftColumn1"><s:property value="%{key}"/>:&nbsp;</div>
       <div class="rightColumn1">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{value}" name="%{value}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField"size="20" maxDate="0" changeMonth="true" changeYear="true" yearRange="1900:2030" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"
       	/>
       </div>
       </div>
       </s:if>
  	   <s:else>
  	   <div class="newColumn">
       <div class="leftColumn1"><s:property value="%{key}"/>:&nbsp;</div>
       <div class="rightColumn1">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{value}" name="%{value}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField"size="20" maxDate="0" changeMonth="true" changeYear="true" yearRange="1900:2030" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"/>
       </div>
       </div>
  	   </s:else>
       </s:iterator>
   	
   <s:iterator value="referralDropDown" begin="1" end="4" status="status">
            <s:if test="%{mandatory}">
				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			</s:if>
			
			 <s:if test="#status.odd">
			<s:if test="field_value=='gender'">
	  	    <s:if test="#status.odd">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="#{'Male':'Male','Female':'Female'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    </s:if>
			    <s:elseif test="field_value=='country'">
			    <s:if test="#status.odd">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="countryMap"
						        headerKey="-1"
						        headerValue="Select Country" 
						        cssClass="select"
								cssStyle="width:82%"
								 onchange="fetchState(this.value,'state')"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    </s:elseif>
			    <s:elseif test="field_value=='state'">
			    <s:if test="#status.odd">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select State" 
						        cssClass="select"
								cssStyle="width:82%"
								  onchange="fetchCity(this.value,'city')"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    </s:elseif>
			    <s:else>
			    <div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						          list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:else>
	  	    </s:if>
	  	   <s:else>
	  	      	<s:if test="field_value=='gender'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="#{'Male':'Male','Female':'Female'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    <s:elseif test="field_value=='country'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="countryMap"
						        headerKey="-1"
						        headerValue="Select Country" 
						        cssClass="select"
						        cssStyle="width:82%"
								 onchange="fetchState(this.value,'state')"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:elseif>
			    <s:elseif test="field_value=='state'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select State" 
						        cssClass="select"
								cssStyle="width:82%"
								  onchange="fetchCity(this.value,'city')"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:elseif>
			     <s:elseif test="field_value=='city'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select City" 
						        cssClass="select"
								cssStyle="width:82%"
								onchange="getTerritory(this.value)"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:elseif>
			    <s:else>
			    <div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						          list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select Category" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:else>
			        
	  	   </s:else>
  	   </s:iterator>
   
   <s:iterator value="referralColumnMap" begin="4" end="4">
		   <s:if test="%{mandatory}">
		   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       </s:if>
		   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1"  id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
		   <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1"  id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
	       </s:else>
  	   </s:iterator>
   
   <s:iterator value="referralDropDown" begin="5" end="6" status="status">
            <s:if test="%{mandatory}">
				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			</s:if>
	  	 	 <s:if test="field_value=='territory'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select Territory" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
	  	    <s:else>
	  	     <s:if test="#status.odd">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						         list="#{'1':'1-Low','2':'2-Normal','3':'3-Medium','4':'4-High','5':'5-Very High'}"
						        headerKey="-1"
		                              headerValue="Select Degree Of Influence" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
	  	    </s:else>
	  	    
	  	   <s:else>
	  	      	 <s:if test="field_value=='territory'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="{'No Data'}"
						        headerKey="-1"
						        headerValue="Select Territory" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
	  	    <s:else>
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						         list="#{'1':'1-Low','2':'2-Normal','3':'3-Medium','4':'4-High','5':'5-Very High'}"
						        headerKey="-1"
		                              headerValue="Select Degree Of Influence" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
	  	    </s:else>
			        
	  	   </s:else>
  	   </s:iterator>
   
     <s:iterator value="referralColumnMap" begin="5" end="5">
		   <s:if test="%{mandatory}">
		   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       </s:if>
		   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1" id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
		   <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
	       <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{key}" rows="1"  id="%{key}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
	       </s:else>
  	   </s:iterator>
   
   
     <s:iterator value="referralDropDown" begin="7" end="8" status="status">
            <s:if test="%{mandatory}">
				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>1#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			</s:if>
	  	    <s:if test="#status.odd">
	  	     <s:if test="field_value=='from_source'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="sourceMap"
						        headerKey="-1"
						        headerValue="Select Source" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    <s:else>
			    <div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="accMgrMap"
						        headerKey="-1"
						        headerValue="Select Account Manager" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:else>
	  	    </s:if>
	  	   <s:else>
	  	      	<s:if test="field_value=='from_source'">
	  	      	<div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="sourceMap"
						        headerKey="-1"
						        headerValue="Select Source" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:if>
			    <s:else>
			    <div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:select  
								id="%{field_value}"
								name="%{field_value}"
						        list="accMgrMap"
						        headerKey="-1"
						        headerValue="Select Account Manager" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
			    </s:else>
	  	   </s:else>
  	   </s:iterator>
   <div class="clear"></div>
	
	
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: 17px;margin-top: 23px;">
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
            onBeforeTopics="validate1"
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
							onclick="viewAssociate()"
							cssStyle="margin-top: -43px;"
					
							>
					  	Back
					</sj:a>
					    </div>
	</s:form>
	
	
	
	</div>
	</div>
	</div>
</body>
</html>