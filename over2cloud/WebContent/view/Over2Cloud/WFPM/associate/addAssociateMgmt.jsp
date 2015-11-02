<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
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
<script type="text/javascript">
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
}

</script>
<SCRIPT type="text/javascript">


$.subscribe('level101', function(event,data)
        {
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          fillName('Associate','associateName11','0');
          fillName('Associate','associateName2','0');
         
        });
$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut(); cancelButton(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewAssociate();
}
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#assOffering").fadeIn(); }, 10);
          setTimeout(function(){ $("#assOffering").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });

$.subscribe('validate1', function(event,data)
		{
			validate(event,data,"hIds");
		});
$.subscribe('validate2', function(event,data)
		{
			validate(event,data,"dIds");
		});
$.subscribe('validate3', function(event,data)
		{
			validate(event,data,"aIds");
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
		        errZone007.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
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
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="ans"){
		            var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                errZone007.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                errZone007.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                errZone007.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
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
		                      errZone007.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
		                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                      $("#"+fieldsvalues).focus();
		                      setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		                      setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
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
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
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
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone007.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#errZone007").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone007").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             } 
		        }
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

function viewAssociate()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
$.ajax({
    type : "post",
    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateView.action?modifyFlag=0&deleteFlag=0&isExistingAssociate=${isExistingAssociate}",
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
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <sj:div id="resultTarget"   effect="fold"> </sj:div>
</sj:dialog>
	<div class="clear"></div>
	<div class="list-icon">
		<!-- <div class="head">Add Prospective Associate</div> -->
		<div class="head">Prospective Associate</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>
	
	<div style=" float:left; padding:20px 1%; width:98%;">
						<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             				<div id="errZone007" style="float:left; margin-left: 7px"></div> 
             			</div><br>
		    			
		<sj:accordion id="accordion" autoHeight="false">
<!-- Associate Basic Data ***************************************************************************** -->
			<s:if test="associateForBasicDetails > 0">
				<sj:accordionItem title="Associate Basic Registration" id="oneId">
					<s:form id="formone111" name="formone111" namespace="/view/Over2Cloud/WFPM/Associate" action="addAssociate" theme="css_xhtml"  method="post" >
		    			<div class="menubox">
		    				<div class="clear"></div>
		    				<!-- Drop down -->
		    				<s:iterator value="associateDropDown" status="counter">
								<s:if test="%{mandatory}">
									<span id="mandatoryFields" class="hIds" style="display: none; ">industry#Industry#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
								<span id="mandatoryFields" class="hIds" style="display: none; ">subIndustry#Sub Industry#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
								</s:if> 	
							</s:iterator>
							<div class="clear"></div>
					<s:if test="%{industryExist != null}">
					<div class="newColumn">
					<div class="leftColumn1"><s:property value="%{industry}"/>:</div>
					<div class="rightColumn1">
				<s:if test="%{industryExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="industry"
	                     name="industry" 
	                     list="industryMap"
	                     headerKey="-1"
	                     headerValue="Select Industry" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
                       	 onchange="fillNameSubIndustry(this.value,'subIndustry')"
	                     >
	         </s:select>
	</div>
	</div>
         </s:if>
         
    <s:if test="%{subIndustryExist != null}">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{subIndustry}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{subIndustryExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="subIndustry"
	                     name="subIndustry" 
	                     list="#{'-1':'Select'}"
	                     headerKey="-1"
	                     headerValue="Select Sub-Industry" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
	                     >
	         </s:select>
	</div>
	</div>
         </s:if>    				
		    				
		    				
		    				
		    				
			<!-- Text box -->
			<s:iterator value="associateTextBox" status="counter">
							<s:if test="#counter.odd">
	         					<div class="clear"></div>
	         				</s:if>
	       <s:if test="key=='address'">
	        <s:if test="%{mandatory}">
	              	<span id="mandatoryFields" class="hIds" style="display: none; "><s:property value="%{key}1111"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#sc,</span>
	         	</s:if>
	        <div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
           <s:textfield name="%{key}" id="%{key}1111" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
	          </div>
	       </s:if>
	       <s:else> 
	       <s:if test="%{mandatory}">
	              	<span id="mandatoryFields" class="hIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#sc,</span>
	         	</s:if> 				
	         <div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
           <s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
	          </div>
                 	</s:else>			
                 				
               					<%-- <div class="newColumn">
               						<div class="leftColumn"><s:property value="%{value}"/>:</div>
               						<div class="rightColumn">
               							<s:if test="%{mandatory}">
               								<span class="needed"></span>
               							</s:if>
               							<s:textfield name="%{key}"  id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
               						</div>
               					</div> --%>
		    				</s:iterator>
		    				
		    				<div class="clear"></div>
							<s:if test="%{stateExist != null}">
							<span id="mandatoryFields" class="hIds" style="display: none; ">stateIdD#State#D#,</span>
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{stateValue}"/>:</div>
					                <div class="rightColumn1">
					                  	<s:if test="%{stateExist == 'true'}"><span class="needed"></span></s:if>
						                  <s:select 
						                              id="stateIdD"
						                              name="state" 
						                              list="stateDataMap"
						                              headerKey="-1"
						                              headerValue="Select State" 
						                              cssClass="select"
                          							  cssStyle="width:82%"
						                              onchange="fetchLocation(this.value,'location123')"
						                              >
						                   
						                  </s:select>
					                </div>
								</div>
							</s:if>
							<s:if test="%{locationExist != null}">
							<span id="mandatoryFields" class="hIds" style="display: none; ">location123#Location#D#,</span>
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{locationValue}"/>:</div>
									<div class="rightColumn1">
									<s:if test="%{locationExist == 'true'}"><span class="needed"></span></s:if>
										<s:select 
										  	id="location123"	
							            	name="location" 
							            	list="#{'-1':'No Data'}"
							           		headerKey="-1"
							           		headerValue="Select Location" 
						           		    cssClass="select"
                                            cssStyle="width:82%"
							           	>
										</s:select>
									</div>
								</div>
			                 </s:if>
			                 
			                 <div class="clear"></div>
			                 <s:if test="%{associateTypeExist != null}">
			                 <span id="mandatoryFields" class="hIds" style="display: none; ">associateType#Type#D#,</span>
			                 	<div class="newColumn">
			                 		<div class="leftColumn1"><s:property value="%{associateTypeValue}"/>:</div>
					                  <div class="rightColumn1">
						                  <s:if test="%{associateTypeExist == 'true'}"><span class="needed"></span></s:if>
						                  <s:select 
				                              id="associateType"
				                              name="associateType" 
				                              list="associateTypeMap"
				                              headerKey="-1"
				                              headerValue="Select Type" 
				                              cssClass="select"
                                              cssStyle="width:82%"
				                              onchange="fetchSubType(this.value,'associateSubType')"
				                              >
						                  </s:select>
					                  </div>
			                 	</div>
			                 </s:if>
			                 <s:if test="%{associateSubTypeExist != null}">
			                 <span id="mandatoryFields" class="hIds" style="display: none; ">associateSubType#Sub Type#D#<s:property value="%{validationType}"/>,</span>
			                 	<div class="newColumn">
			                 		<div class="leftColumn1"><s:property value="%{associateSubTypeValue}"/>:</div>
									<div class="rightColumn1">
					                  <s:if test="%{associateSubTypeExist == 'true'}"><span class="needed"></span></s:if>
					                  <s:select  
			                              id="associateSubType"
			                              name="associateSubType" 
			                              list="#{'-1':'No Data'}"
			                              headerKey="-1"
			                              headerValue="Select Sub Type" 
			                              cssClass="select"
                                          cssStyle="width:82%"
			                              >
					                  </s:select>
					                 </div>
			                 	</div>
				             </s:if>
				             <div class="clear"></div>
				             <s:if test="%{associateCategoryExist != null}">
				             <span id="mandatoryFields" class="hIds" style="display: none; ">associateCategory#Category#D#<s:property value="%{validationType}"/>,</span>
					             <div class="newColumn">
					             	<div class="leftColumn1"><s:property value="%{associateCategoryValue}"/>:</div>
				                  	<div class="rightColumn1">
					                  	<s:if test="%{associateCategoryExist == 'true'}"><span class="needed"></span></s:if>
					                  	<s:select 
			                              id="associateCategory"
			                              name="associateCategory" 
			                              list="associateCategoryMap"
			                              headerKey="-1"
			                              headerValue="Select Category" 
			                              cssClass="select Category"
                                          cssStyle="width:82%"
			                              >
					                  	</s:select>
					             	</div>
					             </div>
			                 </s:if>
			                 <s:if test="%{associateRatingExist != null}">
			                 <span id="mandatoryFields" class="hIds" style="display: none; ">associateRating#Star#D#<s:property value="%{validationType}"/>,</span>
			                 	<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{associateRatingValue}"/>:</div>
									<div class="rightColumn1">
										<s:if test="%{associateRatingExist == 'true'}"><span class="needed"></span></s:if>
										<s:select 
								            id="associateRating"
								            name="associateRating" 
								            list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
								            headerKey="-1"
								            headerValue="Select Rating" 
								            cssClass="select"
                                            cssStyle="width:82%"
								            >
										</s:select>
									</div>
			                 	</div>
				             </s:if>
				             <div class="clear"></div>
				             <s:if test="%{sourceNameExist != null}">
				              <span id="mandatoryFields" class="hIds" style="display: none; ">sourceName#Source#D#<s:property value="%{validationType}"/>,</span>
				             	<div class="newColumn">
				                  <div class="leftColumn1"><s:property value="%{sourceNameValue}"/>:</div>
				                  <div class="rightColumn1">
									<s:if test="%{sourceNameExist == 'true'}"><span class="needed"></span></s:if>
									<s:select 
							          id="sourceName"
							          name="sourceName" 
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
							<s:if test="%{targetAchievedExist != null}">
							 <span id="mandatoryFields" class="hIds" style="display: none; ">targetAchieved#<s:property value="%{targetAchievedValue}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{targetAchievedValue}"/>:</div>
									<div class="rightColumn1">
				                  		<s:if test="%{targetAchievedExist == 'true'}"><span class="needed"></span></s:if>
										<s:select 
								            id="targetAchieved"
								            name="targetAchieved" 
								            list="targetKpiLiist"
								            headerKey="-1"
								            headerValue="Select" 
								            cssClass="select"
                                            cssStyle="width:82%">
										</s:select>
				                  	</div>
			                	</div>
							</s:if>
							<s:if test="%{acManagerExist != null}">
							<span id="mandatoryFields" class="hIds" style="display: none; ">acManager#A/C Manager#D#<s:property value="%{validationType}"/>,</span>
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{acManager}"/>:</div>
									<div class="rightColumn1">
									<s:if test="%{acManagerExist == 'true'}"><span class="needed"></span></s:if>
										<s:select 
								            id="acManager"
								            name="acManager" 
								            list="employee_basicMap"
								            headerKey="-1"
								            headerValue="Select A/C Manager" 
								            cssClass="select "
                                            cssStyle="width:82%"
								            >
										</s:select>
									</div>
								</div>
			                 </s:if>
			                 <s:if test="%{statusExist != null}">
			                 <span id="mandatoryFields" class="hIds" style="display: none; ">status#<s:property value="%{status}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{status}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{statusExist == 'true'}"><span class="needed"></span></s:if>
									<s:select 
							            id="status"
							            name="status" 
							            list="associate_statusMap"
							            headerKey="-1"
							            headerValue="Select Status" 
							             cssClass="select"
                                         cssStyle="width:82%"
							            >
									</s:select>
								</div>
			                  </div>
			                </s:if>
			                <div class="clear"></div>
			                <br><br>
							<!-- Buttons -->
							<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
								<div class="buttonStyle" style="text-align: center;margin-left: -10px;">
						         <sj:submit 
						            targets="leadresult" 
			                        clearForm="true"
			                        value="Save" 
			                        effect="highlight"
			                        effectOptions="{ color : '#222222'}"
			                        effectDuration="5000"
			                        button="true"
			                        onCompleteTopics="level101"
			                        cssClass="button"
			                        indicator="indicator2"
			                        onBeforeTopics="validate1"
						          />
						          <sj:a 
						          	id="reset"
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formone111');"
									cssStyle="margin-left: 191px;margin-top: -43px;"
								>
								  	Reset
								</sj:a>
					            <sj:a 
					            id="back"
								name="Cancel"  
								href="#" 
								button="true" 
								onclick="viewAssociate()"
								cssStyle="margin-top: -43px;"
								>
								  	Back
								</sj:a>
							    </div>
								<sj:div id="leadgntion"  effect="fold">
				                    <div id="leadresult"></div>
				               </sj:div>
		    			</div>
					</s:form>  
				</sj:accordionItem>
			</s:if>

<!-- Associate Offering Mapping *********************************************************************** -->			
<s:if test="associateForOfferingDetails > 0">
<sj:accordionItem title="Associate Offering Details" id="twoId">
<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/WFPM/Associate" action="addOffering" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
<div class="menubox">
	
	<div class="clear"></div>
	<div class="newColumn">
		<span id="form2MandatoryFields" class="dIds" style="display: none; ">associateName11#Associate#D#,</span>
		<div class="leftColumn1">Associate:</div>
		<div class="rightColumn1">
		<span class="needed"></span>
			<s:select 
                          id="associateName11"
                          name="associateName" 
                          list="#{'-1':'No Data'}"
                          headerKey="-1"
                          headerValue="Select" 
                          cssClass="select"
                         	  cssStyle="width:82%"
                          >
              			</s:select>
		</div>
	</div>
	
	<div class="clear"></div>
	<s:if test="OLevel1">
		<div class="newColumn">
			<span id="form2MandatoryFields" class="dIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
			<div class="leftColumn1" style="
    margin-left: 570px;
    margin-top: -39px;
"><s:property value="%{OLevel1LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed" style="
    margin-left: 709px;
    margin-top: -41px;
"></span>
				<s:select 
                            id="verticalname"
                            name="verticalname" 
                            list="verticalMap"
                            headerKey="-1"
                            headerValue="Select" 
                            cssClass="select"
                            cssStyle="margin-left: 718px;margin-top: -43px;width:82%"
                            onchange="fetchLevelData(this,'offeringname','1')"
                            >
                		</s:select>
			</div>
		</div>
           </s:if>
           
           <s:if test="OLevel2">
           <span id="form2MandatoryFields" class="dIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
            <div class="newColumn">
            	<div class="leftColumn1" style="
    margin-left: -580px;
"><s:property value="%{OLevel2LevelName}"/>:</div>
            	<div class="rightColumn1">
            	<span class="needed" style="
    margin-left: -438px;
"></span>
            		<s:select 
                           id="offeringname"
                           name="offeringname" 
                           list="#{'-1':'Select'}"
                           cssClass="textField"
                           headerKey="-1"
                           cssClass="select"
                           cssStyle="width:82%;margin-left: -428px;"
                           onchange="fetchLevelData(this,'subofferingname','2')"
                           >
                </s:select>
            	</div>
            </div>
        </s:if>
          <div class="clear"></div>

        <s:if test="OLevel3">
        <span id="form2MandatoryFields" class="dIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
        <div class="newColumn">
        	<div class="leftColumn1" style="
    margin-left: 569px;
    margin-top: -41px;
"><s:property value="%{OLevel3LevelName}"/>:</div>
        	<div class="rightColumn1">
        	<span class="needed" style="
    margin-left: 708px;
    margin-top: -39px;
"></span>
        			<s:select 
                          id="subofferingname"
                          name="subofferingname" 
                          list="#{'-1':'Select'}"
                          headerKey="-1"
                          headerValue="Select" 
                          cssClass="select"
                          cssStyle="width:82%;margin-left: 717px;margin-top: -42px;"
                          onchange="fetchLevelData(this,'variantname','3')"
                          >
            	   	</s:select>
        	</div>
        </div>
          </s:if>

          <s:if test="OLevel4">
        <span id="form2MandatoryFields" class="dIds" style="display: none; ">variantname#<s:property value="%{OLevel4LevelName}"/>#D#,</span>
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
                           headerKey="-1"
                           headerValue="Select" 
                           onchange="fetchLevelData(this,'subvariantsize','4')"
                           >
                 </s:select>
          		</div>
          	</div>
          </s:if>
          <div class="clear"></div>
          <s:if test="OLevel5">
        	<span id="form2MandatoryFields" class="dIds" style="display: none; ">variantname#<s:property value="%{OLevel4LevelName}"/>#D#,</span>
          	<div class="newColumn">
          		<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
          		<div class="rightColumn1">
          		<span class="needed"></span>
          			<s:select 
                           id="subvariantsize"
                           name="subvariantsize" 
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
          
          <div id="newDiv" style="float: right;margin-top: -35px;margin-right: 50px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
          
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
                            headerValue="Select" 
                            cssClass="select"
                                     cssStyle="width:82%" 
                            onchange="fetchLevelData(this,'%{#deptIndx}2','1')"
                            >
                           </s:select>
          			</div>
          		</div>
          		</s:if>
          		<div class="clear"></div>
          		<s:if test="OLevel2">
          			<div class="newColumn">
          				<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
          				<div class="rightColumn1">
          					<s:select 
                             id="%{#deptIndx}2"
                             name="offeringname" 
                             list="#{'-1':'Select'}"
                             headerKey="-1"
                             headerValue="Select" 
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
                             headerKey="-1"
                             headerValue="Select" 
                              cssClass="select"
                                          cssStyle="width:82%"
                             onchange="fetchLevelData(this,'%{#deptIndx}4','3')"
                             >
	                  	</s:select>
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
                             headerKey="-1"
                             headerValue="Select" 
                              cssClass="select"
                                          cssStyle="width:82%"
                             onchange="fetchLevelData(this,'%{#deptIndx}5','4')"
                             >
		                </s:select>
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
                             headerKey="-1"
                             headerValue="Select" 
                             cssClass="select"
                                         cssStyle="width:82%"
                             >
		                </s:select>
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
			
          	</div>
          </s:iterator>
          <div class="clear"></div>
          <br><br>
			<!-- Buttons -->
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="buttonStyle" style="text-align: center;margin-left: -10px;">
	         <sj:submit 
	           	targets="orglevel3" 
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
	          id="resettwo"
		     	name="Reset"  
				href="#" 
				cssClass="button" 
				button="true" 
				onclick="resetForm2('formtwo');"
				cssStyle="margin-left: 191px;margin-top: -43px;"
			>
			  	Reset
			</sj:a>
	           <sj:a 
	           id="backtwo"
			name="Cancel"  
			href="#" 
			cssClass="button" 
			indicator="indicator" 
			button="true" 
			cssStyle="margin-left: 145px;margin-top: -25px;"
			onclick="viewAssociate()"
			cssStyle="margin-top: -43px;"
			>
			  	Back
			</sj:a>
			</div>
			<sj:div id="orglevel3"  effect="fold">
                   <div id="assOffering"></div>
              	</sj:div>
</div>
</s:form>
</sj:accordionItem>
</s:if>
			
<!-- Associate Contact Data ************************************************************************ -->
<s:if test="associateForContacDetails > 0">
<sj:accordionItem title="Associate Contact Details" id="threeId">
<s:form id="formThree" name="formThree" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/Associate" 
	action="addContacts" theme="simple"  method="post" enctype="multipart/form-data" >
	
	<div class="menubox">
		<div class="newColumn">
			<div class="leftColumn1">Associate: </div>
			<span id="assoMandatoryFields" class="aIds" style="display: none; ">associateName2#Associate#D#,</span>
			<div class="rightColumn1">
			<span class="needed"></span>
				         <s:select 
                           id="associateName2"
                           name="associateName" 
                           list="#{'-1':'No Data'}"
                           headerKey="-1"
                           headerValue="Select Name" 
                           cssClass="select"
                           cssStyle="width:82%"
                           >
                 	     </s:select>
			</div>
		</div>
		
		<!--<div class="newColumn">
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
		
		--><div class="clear"></div>
		<div class="newColumn">
		<s:if test="%{degreeOfInfluence != null}">
			<div class="leftColumn1"><s:property value="%{degreeOfInfluence}"/>:</div>
			<div class="rightColumn1">
			<s:if test="%{degreeOfInfluenceExist == true}">
				<span class="needed"></span>
				<span id="assoMandatoryFields" class="aIds" style="display: none; ">degreeOfInfluence#<s:property value="%{degreeOfInfluence}"/>#D#,</span>
			</s:if>
						<s:select 
                           id="degreeOfInfluence"
                           name="degreeOfInfluence" 
                           list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                           headerKey="-1"
                           headerValue="Select"  
                           cssClass="select"
                           cssStyle="width:82%"
                           >
                 		</s:select>
			</div>
		</s:if>
		</div>
		
<!-- For Department dropdown  -->
<div class="newColumn">
<s:if test="%{department != null}">
<div class="leftColumn1"><s:property value="%{department}"/>:</div>
<div class="rightColumn1">
	<s:if test="%{departmentExist == true}">
		<span class="needed"></span>
		<span id="assoMandatoryFields" class="aIds" style="display: none; ">department#<s:property value="%{department}"/>#D#,</span>
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
			
			<!-- Text box -->
		<div class="clear"></div>
		<s:iterator value="associateTextBoxForContact" status="counter">
			<s:if test="%{mandatory}">
                 				<span id="assoMandatoryFields" class="aIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#sc,</span>
            				</s:if>
            				
            				<s:if test="#counter.odd">
            					<div class="clear"></div>
            				</s:if>
            				<div class="newColumn">
            					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
             				<div class="rightColumn1">
             					<s:if test="%{mandatory}">
             						<span class="needed"></span>
             					</s:if>
             					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
             				</div>
            				</div>
            				
		</s:iterator>
		<div class="clear"></div>
		<!-- Time box -->
		<s:iterator value="associateDateControls" status="counter">
			<s:if test="#counter.odd">
				<div class="clear"></div>
			</s:if> 	 
			<div class="newColumn">
				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
				<div class="rightColumn1">
					<sj:datepicker name="%{key}" id="%{key}%{#deptIndx+1}" changeMonth="true" changeYear="true" cssStyle="margin:0px 0px 10px 0px"   yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
				</div>
			</div>
		</s:iterator>
		<div id="newDiv" style="float: right;margin-top: -45px;margin-right: 50px;"><sj:a value="+" onclick="adddiv('110')" button="true">+</sj:a></div>
			<s:iterator begin="110" end="113" var="deptIndx">
				<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
					<s:if test="degreeOfInfluenceExist">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{degreeOfInfluence}"/>:</div>
							<div class="rightColumn1">
								<s:select 
	                              id="degreeOfInfluence"
	                              name="degreeOfInfluence" 
	                              list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
	                              headerKey="-1"
	                              headerValue="Select" 
	                               cssClass="select"
                                                  cssStyle="width:82%"
	                              >
			                  	</s:select>
							</div>
						</div>
					</s:if>
					
					
					<!-- For Department dropdown  -->

<div class="newColumn">
<s:if test="departmentExist">
<span id="assoMandatoryFields" class="aIds" style="display: none; ">department#<s:property value="%{department}"/>#D#,</span>
<div class="leftColumn1"><s:property value="%{department}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
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
					
					
					<!-- Text box -->
					<s:iterator value="associateTextBoxForContact"  status="counter">
						<s:if test="#counter.odd">
							<div class="clear"></div>
						</s:if>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
						</div>
					</s:iterator>
					<div class="clear"></div>
					<s:iterator value="associateDateControls" status="counter">
						<s:if test="#counter.odd">
							<div class="clear"></div>
						</s:if>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		 					<div class="rightColumn1"><sj:datepicker name="%{key}" id="%{key}%{#deptIndx+1}" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
						</div>
					</s:iterator>
					<div style="float: right; margin-top: -45px;">
					<s:if test="#deptIndx==113">
						<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
					</s:if>
         					<s:else>
           	 					<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
           							<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
      						</s:else>
      						</div>
				</div>
			</s:iterator>
			<div class="clear"></div>
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
				cssClass="button" 
				button="true" 
				onclick="resetForm3('formThree');"
			>
			  	Reset
			</sj:a>
		           <sj:a 
			name="Cancel"  
			href="#" 
			cssClass="button" 
			indicator="indicator" 
			button="true" 
			onclick="viewAssociate()"
			
			>
			  	Back
			</sj:a>
			    </div>
				<sj:div id="pAssociate"  effect="fold">
                    <div id="assoresult1"></div>
                </sj:div>
	</div>
</s:form>  
</sj:accordionItem>
			</s:if>
		</sj:accordion>
	</div>
</body>
<script type="text/javascript">
fillName('Associate','associateName11','0');
fillName('Associate','associateName2','0');
</script>
</html>