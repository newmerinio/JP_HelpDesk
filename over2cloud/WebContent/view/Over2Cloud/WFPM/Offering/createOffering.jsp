<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/WFPM/offering.js"/>"></script>
<SCRIPT type="text/javascript">
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
});
$.subscribe('validate4', function(event,data)
{
	validate(event,data,"sIds");
});
$.subscribe('validate6', function(event,data)
{
	validate(event,data,"vIds");
	 $('#completionResult').dialog('open');
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
		            else if(validationType=="ans"){
		            if(!(/^[ A-Za-z0-9_@./#&+-]*$/.test($("#"+fieldsvalues).val()))){
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

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	         allLevel1Offering('level1Offering','offering2HideShow');
	         fillLastLevel();
	         $('select').find('option:first').attr('selected', 'selected');
	       });
$.subscribe('level2', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel2").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel2").fadeOut(); }, 4000);
	         allLevel2Offering('level2Offering','offering3HideShow');
	         fillLastLevel();
	         $('select').find('option:first').attr('selected', 'selected');
	       });
$.subscribe('level3', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel3").fadeOut(); }, 4000);
	         allLevel3Offering('level3Offering','offering4HideShow');
	         fillLastLevel();
	         $('select').find('option:first').attr('selected', 'selected');
	       });
$.subscribe('level4', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel4").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel4").fadeOut(); }, 4000);
	         allLevel4Offering('level4Offering','offering5HideShow');
	         fillLastLevel();
	         $('select').find('option:first').attr('selected', 'selected');
	       });
$.subscribe('level5', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel5").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel5").fadeOut();cancelButton();}, 4000);
	         allLevelOffering('levelAllOffering','offeringAllHideShow',$("#offeringLevelId").val());
	         $('select').find('option:first').attr('selected', 'selected');
	       });


	function returnOffering()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingView.action?modifyFlag=0&deleteFlag=0&status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}

	function cancelButton()
	 {
		 $('#completionResult').dialog('close');
		 returnOffering();
	 }
		
</script>
<SCRIPT type="text/javascript">

$.subscribe('checkMe', function(event,data)
{
	

});
</SCRIPT>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
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
          <div id="orglevel6Div"></div>
</sj:dialog>
<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Offering</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Registration</div>
	</div>
<div class="clear"></div>

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
         </div>
         <br>
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="%{offeringLevel1Name}" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/offering" action="level1AddOffering" theme="simple"  method="post"enctype="multipart/form-data" >
						     
      <div class="menubox">
				 <s:iterator value="offeringLevel1TextBox" status="counter">
                   <s:if test="%{mandatory}">
                   <s:if test="%{key == 'verticalname'}">
                   	<span id="mandatoryFields" class="pIds" style="display: none; ">verticalnameId#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   	<span id="mandatoryField" class="qIds" style="display: none; ">verticalname#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                   </s:if>
                   <s:else>
                   		 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:else>
                     
                 </s:if>  
	<s:if test="#counter.odd">
	<s:if test="%{key == 'verticalname'}">
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
				<span class="needed"></span>
				</s:if>
					<s:textfield name="%{key}"  id="verticalnameId"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div>
	</s:if>
	<s:else>
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
	</s:else>
	</s:if>
	<s:else>
		<s:if test="%{key == 'verticalname'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
			<s:if test="%{mandatory}">
			<span class="needed"></span>
			</s:if>
			<s:textfield name="%{key}"  id="verticalnameId"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
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
	</s:else>   
			 	 <!--<s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
	                   <div class="leftColumn1"><s:property value="%{value}"/>:</div>
					     <div class="rightColumn1">
					     <span class="needed"></span><s:textfield name="%{key}"  id="%{key}"  cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div>
						  </s:if>
					     <s:else>
					     <div class="leftColumn1">
					     <s:property value="%{value}"/>:</div>
					     <div class="rightColumn1" ><s:textfield name="%{key}"  id="%{key}"  cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div>
					     </s:else>
					  </s:if>
					  <s:else>
					   <s:if test="%{mandatory}">
					     <div class="leftColumn1" ><s:property value="%{value}"/>:</div>
					     <div class="rightColumn1" ><span class="needed"></span>
					     <s:textfield name="%{key}"  id="%{key}"  cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					     </div>
					    </s:if>
					    <s:else>
					    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
					     <div class="rightColumn1">
					     <s:textfield name="%{key}"  id="%{key}"  cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					     </div>
					    </s:else>
					  </s:else>
			
				 --></s:iterator>
			 
 
			
             	<div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center;">
	                <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value="  Save  " 
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
								cssClass="button" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formone');"
							>
			  				Reset
							</sj:a>
	                <sj:a
						button="true"
						onclick="returnOffering()"
					>
					Back
					</sj:a>	
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
               </div>
</s:form>
</div>
</div>
</sj:accordionItem>

<s:if test="levelOfOffering>1">
<sj:accordionItem title="%{offeringLevel2Name}" id="two">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/offering" action="level2AddOffering" theme="simple"  method="post"enctype="multipart/form-data" >
				 
				 <div id="offering2HideShow">
				  <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="qIds" style="display: none; ">verticalname#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				 <div id="level1Offering"></div>
				<div class="menubox">
				<s:iterator value="offeringLevel2TextBox" status="counter">
					
				 <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="qIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
                 <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="rIds" style="display: none; ">offeringnameID#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                 </s:if>
					<s:if test="#counter.count%2 != 0">
						<s:if test="%{key == 'offeringname'}">
							<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
							<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
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
							<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
							</div>
						</s:else>
					</s:if>
					
					<s:else>
						<s:if test="%{key == 'offeringname'}">
							<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
							<s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
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
						<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						</div>
						</div>
						</s:else>
					</s:else>
				</s:iterator>
			</div>
                  
				<!-- Buttons -->
				<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none; position: absolute; left: 49%;  top: 58%;"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center; float: left; width: 100%;">
	                <sj:submit 
	                        targets="orglevel2Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level2"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        onBeforeTopics="validate2" 
	                        />
	                         <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="button" 
								indicator="indicator3" 
								button="true" 
								onclick="resetForm('formTwo');"
							>
			  				Reset
							</sj:a>
                    <sj:a
						button="true"
						onclick="returnOffering()"
					>
					Back
					</sj:a>	
	               </div>
				</ul>
				<sj:div id="orglevel2"  effect="fold">
                    <div style="clear: both; text-align: center;" id="orglevel2Div"></div>
               </sj:div>
               </div>
               
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test="levelOfOffering>2">
<!-- Level3 -->
<sj:accordionItem title="%{offeringLevel3Name}" id="three">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree" name="formThree" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/offering" action="level3AddOffering" theme="simple"  method="post"enctype="multipart/form-data" >
                  
                <div id="offering3HideShow">
                <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                	 </s:if> 
                <div id="level2Offering"></div>
                
			<div class="menubox">
				<s:iterator value="offeringLevel3TextBox"  status="counter">
					 <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="rIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                	 </s:if>  
                	  <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="vIds" style="display: none; ">subofferingnameId#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                	 </s:if> 
					<s:if test="#counter.count%2 != 0">
						<s:if test="%{key == 'subofferingname'}">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
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
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								<div id="error<s:property value="%{key}"/>" class="errordiv">
								</div>
								</div>
							</div>
						</s:else>
					</s:if>
					
					<s:else>
						<s:if test="%{key == 'subofferingname'}">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
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
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								<div id="error<s:property value="%{key}"/>" class="errordiv">
								</div>
								</div>
							</div>
						</s:else>
					</s:else>
				</s:iterator>
			</div>  
                
				<!-- Buttons -->
				<center><img id="indicator4" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none; position:  absolute; left: 49%; top: 64%;"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button"  style="text-align: center; float: left; width: 100%;">
	                <sj:submit 
	                        targets="orglevel3Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level3"
	                        cssClass="submit"
	                        indicator="indicator4"
	                        onBeforeTopics="validate3"
	                        />
	                       <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="button" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formThree');"
							>
			  				Reset
							</sj:a> 
					<sj:a
						button="true"
						onclick="returnOffering()"
					>
					Back
					</sj:a>
	               </div>
				</ul>
			<sj:div id="orglevel3"  effect="fold">
                    <div style="clear: both; text-align: center;" id="orglevel3Div"></div>
               </sj:div>
               </div>
               
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test="levelOfOffering>3">
<!-- Level4 -->
<sj:accordionItem title="%{offeringLevel4Name}" id="four">  
<div class="form_inner" id="form_reg">
<div class="page_form">
	<s:form id="formFour" name="formFour" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/offering" action="level4AddOffering" theme="simple"  method="post"enctype="multipart/form-data" >
		<div id="offering4HideShow">
			<div id="level3Offering">
		</div>
			<div class="menubox">
				<s:iterator value="offeringLevel4TextBox" status="counter">
					 <s:if test="%{mandatory}">
                      <span id="mandatoryField" class="sIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                	 </s:if> 
					<s:if test="#counter.count%2 != 0">
						<s:if test="%{key == 'variantname'}">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
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
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
							</div>
						</s:else>
					</s:if>
					
					<s:else>
						<s:if test="%{key == 'variantname'}">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
								<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
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
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
							</div>
						</s:else>
					</s:else>
				</s:iterator>
			</div>
                
				<!-- Buttons -->
				<center><img id="indicator5" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button"  style="text-align: center; float: left; width: 100%;">
	                <sj:submit 
	                        targets="orglevel4Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level4"
	                        cssClass="submit"
	                        indicator="indicator5"
	                        onBeforeTopics="validate4"
	                        />
	                        <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="button" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formFour');"
							>
			  				Reset
							</sj:a>  
					<sj:a
						button="true"
						onclick="returnOffering()"
					>
					Back
					</sj:a>
	               </div>
				</ul>
			<sj:div id="orglevel4"  effect="fold">
                    <div id="orglevel4Div"></div>
               </sj:div>
               </div>
               
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test="levelOfOffering>4">
<!-- Level5 -->
<sj:accordionItem title="%{offeringLevel5Name}" id="five">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive" name="formFive" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/offering" action="level5AddOffering" theme="simple"  method="post"enctype="multipart/form-data" >
              
              <div id="offering5HideShow">    
                  <div id="level4Offering"></div>
                  
				<div class="menubox">
					<s:iterator value="offeringLevel5TextBox" status="counter">
						<s:if test="#counter.count%2 != 0">
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
									<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
									<div id="error<s:property value="%{key}"/>" class="errordiv"></div>
									</div>
								</div>
						</s:if>
						<s:else>
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
									<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
									<div id="error<s:property value="%{key}"/>" class="errordiv"></div>
									</div>
								</div>
						</s:else>
					</s:iterator>
				</div>
                
                
				<!-- Buttons -->
				<center><img id="indicator6" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center; float: left; width: 100%;">
	                <sj:submit 
	                        targets="orglevel5Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level5"
	                        cssClass="submit"
	                        indicator="indicator6"
	                        onBeforeTopics="validate4"
	                        />
					<sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="button" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formFive');"
							>
			  				Reset
							</sj:a> 
					<sj:a
						button="true"
						onclick="returnOffering()"
					>
					Back
					</sj:a>

	               </div>
				</ul>
			<sj:div id="orglevel5"  effect="fold">
                    <div id="orglevel5Div"></div>
               </sj:div>
               </div>
               
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>

<!-- Offering Level Cost Mapping -->
<sj:accordionItem title="%{lastLevelOffering} Cost Mapping" id="costMapping">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formCostMapping" name="formCostMapping" cssClass="cssn_form" 
		namespace="/view/Over2Cloud/wfpm/offering" action="addOfferingLevelCostMapping" 
		theme="simple"  method="post">
              <s:hidden id="offeringLevelId" name="offeringLevelId" value="%{levelOfOffering}"></s:hidden>
              <div id="offeringAllHideShow">    
                   
                      <span id="mandatoryField" class="vIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  <div id="levelAllOffering"></div>
                  
                  <div class="menubox">
						<div class="newColumn">
							<div class="leftColumn1">Size:</div>
							<div class="rightColumn1">
							<s:textfield name="size"  id="size"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
							</div> 
						</div>
				
						<div class="newColumn">
							<div class="leftColumn1">SKU NO:</div>
							<div class="rightColumn1">
							<s:textfield name="skuNo"  id="skuNo"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
				
				</div>
               
                <div class="menubox">
						<div class="newColumn">
							<div class="leftColumn1">DAP:</div>
							<div class="rightColumn1">
							<s:textfield name="dap"  id="dap"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
							</div> 
						</div>
						<div class="newColumn">
							<div class="leftColumn1">MRP:</div>
							<div class="rightColumn1">
							 <span id="mandatoryField" class="vIds" style="display: none; ">mrp#MRP:#T#ans,</span>
							<span class="needed"></span>
							<s:textfield name="mrp"  id="mrp"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
                </div>
                <div class="menubox">
                
						<div class="newColumn">
							<div class="leftColumn1">Cut Off Price:</div>
							<div class="rightColumn1">
							<s:textfield name="cutOffPrice"  id="cutOffPrice"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
							</div> 
						</div>
						<div class="newColumn">
							<div class="leftColumn1">Warranty:</div>
							<div class="rightColumn1">
							<s:textfield name="description"  id="description"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
                </div>
                <div class="menubox">
                
						<div class="newColumn">
							<div class="leftColumn1">Code:</div>
							<div class="rightColumn1">
							<s:textfield name="code"  id="code"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" />
							</div> 
						</div>
						<div class="newColumn">
							<div class="leftColumn1">Bar Code:</div>
							<div class="rightColumn1">
							<s:textfield name="barCode"  id="barCode"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
                </div>
                
                
				<!-- Buttons -->
				<center><img id="indicator6" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center; float: left; width: 100%;">
	                <sj:submit 
	                        targets="orglevel6Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level5"
	                        cssClass="submit"
	                        indicator="indicator6" 
	                        onBeforeTopics="validate6" 
	                        />
	                        <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="submit" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formCostMapping');"
							>
			  				Reset
							</sj:a> 
					<sj:a
						button="true"
						cssClass="submit"
						onclick="returnOffering()"
					>
					Back
					</sj:a>
	               </div>
	               </li>
				</ul>
		       </div>
               
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>

</sj:accordion>
</div>
</div>
</body>
<SCRIPT type="text/javascript">
allLevel1Offering('level1Offering','offering2HideShow');
allLevel2Offering('level2Offering','offering3HideShow');
allLevel3Offering('level3Offering','offering4HideShow');
allLevel4Offering('level4Offering','offering5HideShow');
allLevelOffering('levelAllOffering','offeringAllHideShow',$("#offeringLevelId").val());
</SCRIPT>
</html>