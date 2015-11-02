<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<style type="text/css">
.user_form_input{
    margin-bottom:10px;
}
</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/associate/associate.js"/>"></script>

<!-- 
<script type="text/javascript">
$("#refName").fadeOut();
var div = document.getElementById('refId');
div.style.visibility='hidden';
</script>
 -->
<script type="text/javascript">
function loadData()
{
       var data=$("#contactNameID").val();
       if(data=="new_contact")
       {
        document.getElementById("hideThisOption").style.display="none";
        document.getElementById("showThisContact").style.display="block";
       }
       if(data=="not_aware")
       {
        document.getElementById("hideThisOption").style.display="none";
        document.getElementById("existingFreeId").style.display="block";
       }

}
function loadFreeData(value)
{
        document.getElementById("hideThisOption").style.display="none";
        document.getElementById("existingFreeId").style.display="none";
        document.getElementById("showThisContact").style.display="block";
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/wfpm/client/fetchExistingFreeRecord.action?existingfreeId="+value,
	success : function(data) {
	$("#personName").val(data.personName);
	$("#designation").val(data.designation);
	$("#department").val(data.department);
	$("#communicationName").val(data.communicationName);
	$("#degreeOfInfluence").val(data.degreeOfInfluence);
	$("#contactNo").val(data.contactNo);
	$("#emailOfficial").val(data.emailOfficial);
	$("#birthday104").val(data.birthday);
	$("#anniversary104").val(data.anniversary);
	$("#alternateMob").val(data.alternateMob);
	$("#alternateEmail").val(data.alternateEmail);
	},
	error: function() {
	alert("error");
	}
	});
}
function loadrefName()
{
    

}

</script>
<SCRIPT type="text/javascript">
function load(selectId)
{  
   var data=$("#contactNamefor").val();
     
   if(data=="new_contact")
   {
	alert('contactOption'+selectId);
    document.getElementById('contactOption'+selectId).style.display="none";
    document.getElementById('contactt'+selectId).style.display="block";

   }
   else if(data=="not_aware")
   {
	 
    document.getElementById('contactOption'+selectId).style.display="none";
    document.getElementById('existing'+selectId).style.display="block";
   }


}

</SCRIPT>

<SCRIPT type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}
function resetForm2(formtwo)
{
	$('#'+formtwo).trigger("reset");
}


function resetForm3(formThree)
{
	$('#'+formThree).trigger("reset");
	document.getElementById("hideThisOption").style.display="block";
	document.getElementById("showThisContact").style.display="none";
	document.getElementById("existingFreeId").style.display="none";
}

function fillName(val,divId,param)
{
	/*
	param is either 1 or 0
	1: for fetching name on both key and value place
	0: for fetching id on key and name on value place
	*/
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	 var refdata=$("#referedBy").val(); 
	
     if(refdata != null && refdata != '-1' )
     {
         document.getElementById("refNameId").style.display="block";
     }else
     {
        document.getElementById("refNameId").style.display="none";

     }
	
	var id = val;
	if(id == null || id == '-1')
	return;


	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchReferredName.action",
	data: "id="+id+"&param="+param, 
	success : function(data) {   
	$('#'+divId+' option').remove();
	$('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
	$(data).each(function(index)
	{
	   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
          fillName('Client','clientNa',0);
          fillName('Client','clientN',0);
});
$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut();cancelButton(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel3").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });

function cancelButton()
{
$('#completionResult').dialog('close');
viewClient();

} 
$.subscribe('validate1', function(event,data)
{
	validate(event,data,"pIds");
});
$.subscribe('validate2', function(event,data)
{
	validate(event,data,"qIds");
});
$.subscribe('validate3', function(event,data)
{
	validate(event,data,"rIds");
	if(event.originalEvent.options.submit != false)
    {
    	$('#completionResult').dialog('open');
    }
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

</SCRIPT>
<script type="text/javascript">
	function viewClient()
	{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient=${isExistingClient}",
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
 <sj:div id="pAssociate"  effect="fold"><div id="assoresult1"></div></sj:div>
          </sj:dialog>
	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">Prospective Opportunity</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
        	<br>
<sj:accordion id="accordion" autoHeight="false">
<!-- Client Basic Data ******************************************************************************* -->
<s:if test="clientForBasicDetails>0">

<sj:accordionItem title="Opportunity Basic Details" id="oneId">  
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/client" action="addClient" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class="menubox">
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
       
         
    
<!-- Text box -->
	
	<s:iterator value="clientTextBox" status="counter">
	<s:if test="%{mandatory}">
	<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>

	<s:if test="#counter.odd">
	<div class="clear"></div>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:if>
	
	<s:else>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:else>
	
	</s:iterator> 
	
    <!-- Target segment -->	
   <s:if test="%{weightageMasterExist != null}">
   <s:if test="%{weightageMasterExist == 'true'}"><span id="mandatoryField" class="pIds" style="display: none; ">weightage_targetsegment#<s:property value="%{weightage_targetsegment}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
     <div class="newColumn">
         <div class="leftColumn1">Target Segment</div>
            <div class="rightColumn1"><s:if test="%{weightageMasterExist == 'true'}"><span class="needed"></span></s:if>
	        <s:select 
	           id="weightage_targetsegment"
	           name="weightage_targetsegment"
	           list="weightageMasterMap"
	           headerKey="-1"
	           headerValue="Select"
	           cssClass="select"
	           cssStyle="width:82%"
	           onchange="fillName()"
	           
	         ></s:select>
	        </div>
	  </div>   
    </s:if>       
	<!-- Industry sub-Industry DD -->
	
	<s:if test="%{industryExist != null}">
	         <s:if test="%{industryExist == 'true'}">
             <span id="mandatoryField" class="pIds" style="display: none; ">industry#<s:property value="%{industry}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	         </s:if>
	         <div class="newColumn">
	         <div class="leftColumn1"><s:property value="%{industry}"/>:</div>
	         <div class="rightColumn1">
	         <s:if test="%{industryExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="industry"
	                     name="industry" 
	                     list="industryMap"
	                     headerKey="-1"
	                     headerValue="Select" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
                       	 onchange="fillName(this.value,'subIndustry',0)"
	                     >
	         </s:select>
	         </div>
	         </div>
    </s:if>
	
	<s:if test="%{subIndustryExist != null}">
	      <s:if test="%{subIndustryExist == 'true'}">
          <span id="mandatoryField" class="pIds" style="display: none; ">subIndustry#<s:property value="%{subIndustry}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	      </s:if>
	      <div class="newColumn">
	      <div class="leftColumn1"><s:property value="%{subIndustry}"/>:</div>
	      <div class="rightColumn1">
	      <s:if test="%{subIndustryExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="subIndustry"
	                     name="subIndustry" 
	                     list="#{'-1':'Select'}"
	                     headerKey="-1"
	                     headerValue="Select" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
	                     >
	         </s:select>
	      </div>
	      </div>
    </s:if>
    
	<!-- Industry sub-Industry DD END -->
	<!-- Drop down -->
	<s:if test="%{sourceMasterExist != null}">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{sourceMaster}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{sourceMasterExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="sourceMaster"
	                     name="sourceMaster" 
	                     list="sourceMasterMap"
	                     headerKey="-1"
	                     headerValue="Select" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
	                     >
	         </s:select>
	</div>
	</div>
    </s:if>
    <s:if test="%{referedByExist != null}">
         	<div class="newColumn">
	        <div class="leftColumn1"><s:property value="%{referedBy}"/>:</div>
	        <div class="rightColumn1">
	        <s:if test="%{sourceMasterExist == 'true'}"><span class="needed"></span></s:if>
	        <s:select 
	      id="referedBy"	
	      name="referedBy" 
	      list="#{'Client':'Opportunity','Existing_Client':'Existing Client','Associate':'Prospective Associate','Existing_Associate':'Existing Associate'}"
	      headerKey="-1"
	      headerValue="Select" 
	      cssClass="select"
	          cssStyle="width:82%"
	      onchange="fillName(this.value,'refName',0)"
	          >
	      </s:select>
	      </div>
	      </div>
     </s:if>
        
     <s:if test="%{refNameExist != null}">
         <div id="refNameId" style="display: none;">
         	<div class="newColumn">
	        <div class="leftColumn1">Referance Name:</div>
	        <div class="rightColumn1">
	        <s:if test="%{refNameExist == 'true'}"><span class="needed"></span></s:if>
	        <s:select 
                       id="refName"
                       name="refName" 
                       list="#{'-1':'Select'}"
                       cssClass="select"
                       cssStyle="width:82%"
                       >
             </s:select>
	         </div>
	         </div>
	     </div>
	  </s:if>   
       
         
         <s:if test="%{starRatingExist != null}">
         	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{starRating}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{starRatingExist == 'true'}"><span class="needed"></span></s:if>
	                  <s:select  
	                              id="starRating"
	                              name="starRating" 
	                              list="#{'1':'1-Low Potential','2':'2-Normal Potential','3':'3-Medium Potential','4':'4-High Potential','5':'5-Very High Potential'}"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              >
	                   
	                  </s:select>
	</div>
	</div>
         </s:if>
         
         <s:if test="%{statusExist != null}">
         	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{status}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{statusExist == 'true'}"><span class="needed"></span></s:if>
	<s:select 
	                              id="status"
	                              name="status" 
	                              list="client_statusMap"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              >
	                   
	                  </s:select>
	</div>
	</div>
         </s:if>
         
         
         
     <s:if test="%{acManagerExist != null}">
     <div class="newColumn">
	<div class="leftColumn1"><s:property value="%{acManager}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{acManagerExist == 'true'}"><span class="needed"></span></s:if>
	<s:select 
	            id="acManager"
	            name="acManager" 
	            list="employee_basicMap"
	            headerKey="-1"
	            headerValue="Select" 
	            cssClass="select"
                cssStyle="width:82%"
	            >
	</s:select>
	</div>
	</div>
    </s:if>
         
    <s:if test="%{locationExist != null}">	
         	 <div class="newColumn">
	         <div class="leftColumn1"><s:property value="%{location}"/>:</div>
	         <div class="rightColumn1">
	             <s:if test="%{locationExist == 'true'}"><span class="needed"></span></s:if>
	                 <s:select 
                              id="location"
                              name="location" 
                              list="location_master_dataMap"
                              headerKey="-1"
                              headerValue="Select" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   
                     </s:select>
	          </div>
	          </div>
    </s:if>   
         
         
         <s:if test="%{targetAchieved != null}">
         	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{targetAchieved}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{targetAchieved == 'true'}"><span class="needed"></span></s:if>
	                  <s:select 
                              id="targetAchieved"
                              name="targetAchieved" 
                              list="targetKpiLiist"
                              headerKey="-1"
                              headerValue="Select Location" 
                              cssClass="select"
                              cssStyle="width:82%">
                   
                  </s:select>
	</div>
	</div>
         </s:if>
         
	</div>
	<div class="clear"></div>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="leadresult" 
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
	onclick="viewClient()"
	cssStyle="margin-top: -43px;"
	
	>
	  	Back
	</sj:a>
	    </div>
	    
	
	<sj:div id="leadgntion"  effect="fold">
           <div id="leadresult"></div>
        </sj:div>
	</s:form>	
</sj:accordionItem>
</s:if>
<!-- Client Offering Mapping ************************************************************************* -->
<s:if test="clientForOfferingDetails>0">
	<sj:accordionItem title="Opportunity Offering Mapping" id="twoId">
	<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/client" action="addOffering" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class='clear'></div>
    <span id="form2MandatoryFields" class="qIds" style="display: none; ">clientN#Client#D#,</span>
    <div class="newColumn">
	<div class="leftColumn1">Opportunity Name:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                   id="clientN"
                   name="clientName" 
                   list="#{'-1':'Select'}"
                   headerKey="-1"
                   headerValue="Select Name" 
                   cssClass="select"
                   cssStyle="width:82%"
                  >
       
    </s:select>
	</div>
	</div>
	
	<s:if test="OLevel1">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="verticalname"
                              name="verticalname" 
                              list="verticalMap"
                              headerKey="-1"
                              headerValue="Select Name" 
                               cssClass="select"
                              cssStyle="width:82%" 
                              onchange="fetchLevelData(this,'offeringname','1')"
                              >
                   
                  </s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel2">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="offeringname"
                              name="offeringname" 
                              list="#{'-1':'Select'}"
                               cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchLevelData(this,'subofferingname','2')"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel3">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="subofferingname"
                              name="subofferingname" 
                              list="#{'-1':'Select'}"
                               cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchLevelData(this,'variantname','3');checkSuboffering(this.value,clientN.value);"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	<div class="clear"></div>
	
	<s:if test="OLevel4">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">variantname#<s:property value="%{OLevel4LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel4LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="variantname"
                              name="variantname" 
                               cssClass="select"
                              cssStyle="width:82%"
                              list="#{'-1':'Select'}"
                              onchange="fetchLevelData(this,'subvariantsize','4')"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	
	<s:if test="OLevel5">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">subvariantsize#<s:property value="%{OLevel5LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
	                             id="subvariantsize"
	                             name="subvariantsize" 
	                             list="#{'-1':'Select'}"
	                              cssClass="select"
                              cssStyle="width:82%"                        
	                             >
	                 	</s:select>
	                 	
	</div>
	</div>
	</s:if><!--
	 <s:if test="%{forecastcategoryExist  != null}">
         	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{forecast_category}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{forecastcategoryExist  == 'true'}"><span class="needed"></span></s:if>
	             <s:select  
	                   id="forecast_category"
	                   name="forecast_category" 
	                   list="forecastcategoryMap"
	                   headerKey="-1"
	                   headerValue="Select" 
	                   cssClass="select"
	                       cssStyle="width:82%"
	                   >
	                   
	              </s:select>
	</div>
	</div>
         </s:if>
         <s:if test="%{salesstagesExist  != null}">
            <div class="newColumn">
                <div class="leftColumn1"><s:property value="%{sales_stages}"/>:</div>
                <div class="rightColumn1">
                   <s:if test="%{salesstagesExist == 'true'}"><span class="needed"></span></s:if>
                      <s:select
                      id="salesStageId"
                      name="sales_stages"
                      list="salesStageMap"
	  	headerKey="-1"
                      headerValue="Select"
                      cssClass="select"
                      cssStyle="width:82%" 
                      >
                      </s:select>
               </div>
             </div>    
         </s:if>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Brief:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_name"  id="opportunity_name1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Value:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_value"  id="opportunity_value1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	
	<div class="newColumn">
	<div class="leftColumn1">Closure Date:</div>
	<div class="rightColumn1">
	<sj:datepicker name="closure_date" id="closure_date1" showOn="focus"	displayFormat="dd-mm-yy" yearRange="2014:2020" 	readonly="true" cssClass="textField"  cssStyle="margin: 0px 6px 10px; width: 82%;" placeholder="Enter Data"></sj:datepicker>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Expectency:</div>
	<div class="rightColumn1">
	  <s:select  
	   id="expectency"
	   name="expectency" 
	   list="#{'High':'High','Medium':'Medium','Low':'Low'}"
	   headerKey="-1"
	   headerValue="Select" 
	   cssClass="select"
	   cssStyle="width:82%"
	  >
	 </s:select>
	</div>
	</div>
	--><div id="newDiv" style="float: right;margin-top: -35px;margin-right: 50px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
	
	<s:iterator begin="100" end="103" var="deptIndx">
	<div class="clear"></div>
	<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
	<s:if test="OLevel1">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                             id="verticalname"
	                             name="verticalname" 
	                             list="verticalMap"
	                             headerKey="-1"
	                             headerValue="Select Name" 
	                             cssClass="select"
                              	 cssStyle="width:82%"
	                             onchange="fetchLevelData(this,'%{#deptIndx}2','1')"
	                             >
	                 </s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel2">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}2"
	                              name="offeringname" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                   cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}3','2')"
	                              >
	                             </s:select>
	</div>
	</div>
	</s:if>
	
	<s:if test="OLevel3">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}3"
	                              name="subofferingname" 
	                              list="#{'-1':'Select'}"
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}4','3')"
	                              />
	</div>
	</div>
	</s:if>
	<s:if test="OLevel4">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel4LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}4"
	                              name="variantname" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                   cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}5','4')"
	                              />
	</div>
	</div>
	</s:if>
	<s:if test="OLevel5">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}5"
	                              name="subvariantsize" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                    cssStyle="width:82%"
	                              />
	</div>
	</div>
	</s:if>
	
	<div style="float: right;margin-top: -35px;">	
	<s:if test="#deptIndx==103">
	    	<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
     </s:if>
       	<s:else>
     	  	<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
           	<div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
         </s:else>
	</div>
	<!--<div class="newColumn">
	<div class="leftColumn1">Opportunity Brief:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_name"  id="opportunity_name1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Value:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_value"  id="opportunity_value1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Closure Date:</div>
	<div class="rightColumn1">
	<sj:datepicker name="closure_date" id="closure_date%{#deptIndx}" showOn="focus"	displayFormat="dd-mm-yy" yearRange="2014:2020" 	readonly="true" cssClass="textField"  cssStyle="margin: 0px 6px 10px; width: 82%;" placeholder="Enter Data"></sj:datepicker>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{forecast_category}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{forecastcategoryExist  == 'true'}"><span class="needed"></span></s:if>
	             <s:select  
	                   id="forecast_category%{#deptIndx}"
	                   name="forecast_category" 
	                   list="forecastcategoryMap"
	                   headerKey="-1"
	                   headerValue="Select" 
	                   cssClass="select"
	                       cssStyle="width:82%"
	                   >
	                   
	              </s:select>
	</div>
	</div>
         
            <div class="newColumn">
                <div class="leftColumn1"><s:property value="%{sales_stages}"/>:</div>
                <div class="rightColumn1">
                   <s:if test="%{salesstagesExist == 'true'}"><span class="needed"></span></s:if>
                      <s:select
                      id="salesStageId%{#deptIndx}"
                      name="sales_stages"
                      list="salesStageMap"
	  	headerKey="-1"
                      headerValue="Select"
                      cssClass="select"
                      cssStyle="width:82%" 
                      >
                      </s:select>
               </div>
             </div>  
	<div class="newColumn">
	<div class="leftColumn1">Expectancy:</div>
	<div class="rightColumn1">
	  <s:select  
	   id="expectency%{#deptIndx}"
	   name="expectency" 
	   list="#{'High':'High','Medium':'Medium','Low':'Low'}"
	   headerKey="-1"
	   headerValue="Select" 
	   cssClass="select"
	   cssStyle="width:82%"
	  >
	 </s:select>
	</div>
	</div>
	--></div>
	</s:iterator>
	<div class="clear"></div>
	<br>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
	         <sj:submit 
	           targets="assOffering" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="form2"
                       cssClass="submit"
                       indicator="indicator3"
                       onBeforeTopics="validate2"
	          />
	           <sj:a 
	           id="two2"
	         	name="Reset"  
	href="#" 
	cssClass="submit" 
	indicator="indicator" 
	button="true" 
	    onclick="resetForm2('formtwo');"
	    cssStyle="margin-left: 195px;margin-top: -43px;"
	>
	  	Reset
	</sj:a>
	          <sj:a 
	          id="back2"
	      	    name="Back"  
	href="#" 
	cssClass="submit" 
	indicator="indicator" 
	button="true" 
	cssStyle="margin-top: -43px;"
	onclick="viewClient()"
	
	>
	  	Back
	</sj:a>
	        
	    </div>
	<sj:div id="orglevel3"  effect="fold">
                    	<div id="assOffering"></div>
               	</sj:div>
	</s:form>  
	</sj:accordionItem>
</s:if>
<!-- Client Contact Data ******************************************************************************** -->
<s:if test="clientForContacDetails>0">
	<sj:accordionItem title="Opportunity Contact Mapping" id="threeId">
	<s:form id="formThree" name="formThree" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/client" action="addContacts" theme="simple"  method="post" enctype="multipart/form-data" >
	<div class="menubox">
	<s:iterator value="leadGenerationDropDown" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if> 	
	</s:iterator>
	<div class="clear"></div>
	<span id="assoMandatoryFields" class="rIds" style="display: none; ">clientNa#Client#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Name:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	            <s:select 
	id="clientNa"
	name="clientName" 
	list="#{'-1':'Select'}"
	headerKey="-1"
	headerValue="Select Name" 
	cssClass="select"
	    cssStyle="width:82%"
	>
	          	</s:select>
	            </div> 
	</div>
	
	 <div class="newColumn" id="hideThisOption">
	 <div class="leftColumn1">Contact Person:</div>
	 <div class="rightColumn1">
	 <span id="assoMandatoryFields" class="rIds" style="display: none; ">contactNameID#contactName#D#,</span>
	 <span class="needed"></span>
	        <s:select 
                              id="contactNameID"
                              name="contactType" 
                              list="#{'not_aware':'Existing Free','new_contact':'New Contact'}" 
                              headerKey="-1"
                              headerValue="Select Contact"
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="loadData();"
                              >
                </s:select>
	 </div>
	 </div>
	 <div id="existingFreeId" style="display: none;">
	
	 <div class="newColumn">
	 <div class="leftColumn1">Existing Free:</div>
	 <div class="rightColumn1">
	           <s:select 
                              id="existing_freeID"
                              name="existing_free" 
                              list="contactPersonMap"
                              headerKey=""
                              headerValue="Select"
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="loadFreeData(this.value);"
                              >
                </s:select>
	 </div>
	 </div>
	 
	 </div>
	 
	 
	 <!-- Text box -->
	<s:iterator value="clientTextBoxForContact" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<!--  
	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
	-->
	<!-- new code for box -->
	   <s:if test="%{key=='personName'}">
	   <div id="showThisContact" style="display: none;">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	    </div>      
	   </s:if>
	 </s:iterator> 

	 <s:iterator value="clientTextBoxForContact" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<!--  
	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
	-->
	<!-- new code for box -->
	
	   <s:if test="%{key=='designation'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	 </s:iterator> 
	 
	
	 <div class="newColumn">
	   <s:if test="%{department != null}">
	     <div class="leftColumn1"><s:property value="%{department}"/>:</div>
	       <div class="rightColumn1">
	   <s:if test="%{departmentExist == true}">
	<span class="needed"></span>
	<span id="assoMandatoryFields" class="rIds" style="display: none; ">department#<s:property value="%{department}"/>#D#,</span>
	    </s:if>	
	                <s:select 
                              id="department"
                              name="department" 
                              list="deptMap"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                    </s:select>
	            </div> 
	  </s:if>
	 </div>
	 
    <div class="clear"></div>	 	 
	  <!-- Text box -->
	<s:iterator value="clientTextBoxForContact" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<!--  
	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
	-->
	<!-- new code for box -->
	   <s:if test="%{key=='communicationName'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	 </s:iterator> 
	 
	 
	 <div class="newColumn">
	 <s:if test="%{degreeOfInfluence != null}">
	 <div class="leftColumn1"><s:property value="%{degreeOfInfluence}"/>:</div>
	 <div class="rightColumn1">
	<s:if test="%{degreeOfInfluenceExist == true}">
	<span class="needed"></span>
	<span id="assoMandatoryFields" class="rIds" style="display: none; ">degreeOfInfluence#<s:property value="%{degreeOfInfluence}"/>#D#,</span>
	</s:if>
	               <s:select 
                              id="degreeOfInfluence"
                              name="degreeOfInfluence" 
                              list="#{'1':'1-Low','2':'2-Normal','3':'3-Medium','4':'4-High','5':'5-Very High'}"
                              headerKey="-1"
                              headerValue="Select Degree of Influence" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	 </div> 
	 </s:if>
	 </div>
	 
	 
	 
	 
	 <s:iterator value="clientTextBoxForContact" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
  
<!--  	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
-->	
	<!-- new code for box -->
	   <s:if test="%{key=='contactNo'}">
	   <div class="clear"></div>
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	   <s:elseif test="%{key=='emailOfficial'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='alternateMob'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	   <s:elseif test="%{key=='alternateEmail'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	       <s:elseif test="%{key=='patient_age'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='pateint_sex'}">
	          <div class="newColumn">
	          <div class="leftColumn1">Gender:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="#{'Male':'Male','Female':'Female'}"
                              headerKey="-1"
                              headerValue="Select Gender" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	          <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          --></div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='allergic_to'}">
	          <div class="newColumn" style="  margin-left: 1px;">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='blood_group'}">
	          <div class="newColumn" style="  margin-left: 548px;  margin-top: -51px;">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	           <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
                              headerKey="-1"
                              headerValue="Select Group" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	          <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          --></div>
	          </div>
	   </s:elseif>
	   
	   
	 </s:iterator>

	<!-- Date --> 
	 <div class="clear"></div>
	<s:iterator value="clientDateControls" status="counter">
	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1"><sj:datepicker name="%{key}" id="%{key}%{#deptIndx+1}" changeMonth="true" changeYear="true" cssStyle="margin:0px 0px 10px 0px"   yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
	</div>
	</s:iterator>	
	
	 <div class="leftColumn" style="margin-left: -385px; margin-top: -12px;">Questionare:</div>
	       <div class="rightColumn" style="margin-left: -1px; margin-top: -9px;">
	       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       Mail <s:checkbox name="mail" id="mail" />
	       SMS <s:checkbox name="sms" id="sms"/>
	 </div>
	<!-- this is for + -->
   <div id="newDiv" style="float: right;margin-top: -45px; margin-right: 40px;"><sj:a value="+" onclick="adddiv('110')" button="true"> + </sj:a></div>
   <div class="clear"></div>
   
<s:iterator begin="110" end="113" var="deptIndx">

<div id="<s:property value="%{#deptIndx}"/>" style="display: none;">

 	 
	 
	<!-- Text box -->
	<s:iterator value="clientTextBoxForContact" status="counter">
	      <s:if test="%{mandatory}">
	           <span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	      </s:if>
	              <!--  
	                  <s:if test="#counter.odd">
	                  <div class="clear"></div>
	                  </s:if>
	               -->
	           <!-- new code for box -->
	        <s:if test="%{key == 'personName'}">
	              
	               <div class="newColumn">
	               <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	               <div class="rightColumn1">
	               <s:if test="%{mandatory}">
	               <span class="needed"></span>
	               </s:if>
	               <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	               </div>
	               </div>
	                     
	        </s:if>
	 </s:iterator> 
	 
	 
	 <s:iterator value="clientTextBoxForContact" status="counter">
	<s:if test="%{mandatory}">
	<span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<!--  
	<s:if test="#counter.odd">
	<div class="clear"></div>
	</s:if>
	-->
	<!-- new code for box -->
	   <s:if test="%{key=='designation'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	 </s:iterator> 
	 
	
	 <div class="newColumn">
	   <s:if test="%{department != null}">
	     <div class="leftColumn1"><s:property value="%{department}"/>:</div>
	       <div class="rightColumn1">
	   <s:if test="%{departmentExist == true}">
	<span class="needed"></span>
	<span id="assoMandatoryFields" class="rIds" style="display: none; ">department#<s:property value="%{department}"/>#D#,</span>
	    </s:if>	
	                <s:select 
                              id="department"
                              name="department" 
                              list="deptMap"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                    </s:select>
	            </div> 
	  </s:if>
	 </div>
	 
    <div class="clear"></div>	 	 
	  <!-- Text box -->
	<s:iterator value="clientTextBoxForContact" status="counter">
	  <s:if test="%{mandatory}">
	  <span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	  </s:if>
	     <!--  
	     <s:if test="#counter.odd">
	     <div class="clear"></div>
	     </s:if>
	     -->
	     <!-- new code for box -->
	   <s:if test="%{key=='communicationName'}">
	          <div class="newColumn" style="  margin-left: 547px; margin-top: -42px;">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	 </s:iterator> 
	 
	 
	 <div class="newColumn">
	 <s:if test="%{degreeOfInfluence != null}">
	 <div class="leftColumn1"><s:property value="%{degreeOfInfluence}"/>:</div>
	 <div class="rightColumn1">
	<s:if test="%{degreeOfInfluenceExist == true}">
	  <span class="needed"></span>
	  <span id="assoMandatoryFields" class="rIds" style="display: none; ">degreeOfInfluence#<s:property value="%{degreeOfInfluence}"/>#D#,</span>
	</s:if>
	               <s:select 
                              id="degreeOfInfluence"
                              name="degreeOfInfluence" 
                              list="#{'1':'1-Low','2':'2-Normal','3':'3-Medium','4':'4-High','5':'5-Very High'}"
                              headerKey="-1"
                             headerValue="Select Degree of Influence" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	 </div> 
	 </s:if>
	 </div>
	 
	 
	 
	 
	<s:iterator value="clientTextBoxForContact" status="counter">
	  <s:if test="%{mandatory}">
	            <span id="assoMandatoryFields" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	  </s:if>
  
       <!--<s:if test="#counter.odd">
	   <div class="clear"></div>
	   </s:if>
       -->	
	   <!-- new code for box -->
	   <s:if test="%{key=='contactNo'}">
	   <div class="clear"></div>
	          <div class="newColumn" style=" margin-left: 548px; margin-top: -45px;">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:if>
	   <s:elseif test="%{key=='emailOfficial'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='alternateMob'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	   <s:elseif test="%{key=='alternateEmail'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	        	       <s:elseif test="%{key=='patient_age'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='pateint_sex'}">
	          <div class="newColumn">
	          <div class="leftColumn1">Gender:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="#{'Male':'Male','Female':'Female'}"
                              headerKey="-1"
                              headerValue="Select Gender" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	          <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          --></div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='allergic_to'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          </div>
	          </div>
	   </s:elseif>
	    <s:elseif test="%{key=='blood_group'}">
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	           <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
                              headerKey="-1"
                              headerValue="Select Group" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
	          <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	          --></div>
	          </div>
	   </s:elseif>
	   
	</s:iterator>

    <!-- Date -->
    <div class="clear"></div> 
	<s:iterator value="clientDateControls" status="counter">
	<s:if test="#counter.odd">
	<div class="clear" ></div>
	</s:if>
	 <s:if test="%{key=='birthday'}">
	          <div class="newColumn" style=" margin-left: 547px; margin-top: -45px;">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <sj:datepicker name="%{key}" id="%{key}%{#deptIndx+1}" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
	          </div>
	          </div>
	   </s:if>
	   <s:else>
	          <div class="newColumn">
	          <div class="leftColumn1"><s:property value="%{value}"/>:</div>
	          <div class="rightColumn1">
	          <s:if test="%{mandatory}">
	          <span class="needed"></span>
	          </s:if>
	          <sj:datepicker name="%{key}" id="%{key}%{#deptIndx+1}" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
	          </div>
	          </div>
	   </s:else>
	
	</s:iterator>
   
    <div class="leftColumn" style="margin-left: -384px; margin-top: 1px;">Questionare:</div>
	       <div class="rightColumn" style="margin-left: 690px; margin-top: -51px;">
	       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       Mail <s:checkbox name="mail" id="mail" />
	       SMS <s:checkbox name="sms" id="sms"/>
	 </div>
	 
    <div style="float: right; margin-top: -45px;">	
	    <s:if test="#deptIndx == 113">
	    	     <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
            </s:if>
       	    <s:else>
     	  	     <div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
           	     <div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
            </s:else>
	</div>
	
	
</div>
</s:iterator>
	<div class="clear"></div>
	<br>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle">
	         	<sj:submit 
	           	   targets="assoresult1"
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="dd"
                       cssClass="submit"
                       indicator="indicator4"
                       onBeforeTopics="validate3"
	         	 />
	          	<sj:a 
	     	name="Reset"  
	href="#" 
	cssClass="submit" 
	button="true" 
	onclick="resetForm3('formThree');"
	>
	  	Reset
	</sj:a>
	          <sj:a
	     	    name="Cancel"  
	href="#" 
	cssClass="submit" 
	indicator="indicator" 
	button="true" 
	onclick="viewClient()"
	>
	  	    Back
	</sj:a>
	    </div>
	   
	</div>
	</s:form>  
	</sj:accordionItem>
</s:if>

</sj:accordion>
</div>
</div>
<SCRIPT type="text/javascript">
$("#clientName").val("");
fillName('Client','clientNa',0);
fillName('Client','clientN',0);
</SCRIPT>
</body>
</html>