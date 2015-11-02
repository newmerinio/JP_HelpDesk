<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/industry.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('validate', function(event,data)
		{	
			var mystring=$(".pIds").text();
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
		});

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	         allLevel1Offering('level1Offering','offering2HideShow');
	         $('select').find('option:first').attr('selected', 'selected');
	       });
$.subscribe('level2', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel2").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel2").fadeOut(); }, 4000);
	       });
$.subscribe('validateeeee', function(event,data)
        {
            if(document.formone.verticalname.value!=null && document.formone.verticalname.value=="" || document.formone.verticalname.value=="-1")
            {
            	clearAllErroDiv();
            	errorverticalname.innerHTML="Error: Fill data in field";
            	event.originalEvent.options.submit = false;
            	return false;
            }
            clearAllErroDiv();
        });

/* $.subscribe('validate1', function(event,data)
        {

			if(document.formone.industry.value!=null && document.formone.industry.value=="" || document.formone.industry.value=="-1")
		    {
				clearAllErroDiv();
				errorindustry.innerHTML="Error: Select data in field";
            	event.originalEvent.options.submit = false;
            	return false;
		    }
			clearAllErroDiv();
			
        }); */

$.subscribe('validate2', function(event,data)
        {

			if(document.formThree.offeringname.value!=null && document.formThree.offeringname.value=="" || document.formThree.offeringname.value=="-1")
		    {
				clearAllErroDiv();
				errorofferinglevel2.innerHTML="Error: Select data in field";
            	event.originalEvent.options.submit = false;
            	return false;
		    }
			else if(document.formThree.subofferingname.value!=null && document.formThree.subofferingname.value=="" || document.formThree.subofferingname.value=="-1")
            {
				
				clearAllErroDiv();
            	errorsubofferingname.innerHTML="Error: Fill data in field";
            	event.originalEvent.options.submit = false;
            	return false;
            }
			clearAllErroDiv();
        });

$.subscribe('validate3', function(event,data)
        {

			if(document.formFour.subofferingname.value!=null && document.formFour.subofferingname.value=="" || document.formFour.subofferingname.value=="-1")
		    {
				clearAllErroDiv();
				errorsubofferingnamelevel3.innerHTML="Error: Select data in field";
            	event.originalEvent.options.submit = false;
            	return false;
		    }
			else if(document.formFour.variantname.value!=null && document.formFour.variantname.value=="" || document.formFour.variantname.value=="-1")
            {
				clearAllErroDiv();
            	errorvariantname.innerHTML="Error: Fill data in field";
            	event.originalEvent.options.submit = false;
            	return false;
            }
			clearAllErroDiv();
        });

$.subscribe('validate4', function(event,data)
        {

			if(document.formFive.variantname.value!=null && document.formFive.variantname.value=="" || document.formFive.variantname.value=="-1")
		    {
				clearAllErroDiv();
				errorvariantnamelevel4.innerHTML="Error: Select data in field";
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

	function returnIndustry()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryView.action",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error>>>>>>>>>>");
		        }
		 });
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
<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Assign</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Weightage</div>
	</div>
<div class="clear"></div>

<div class="container_block">
		<div style="float: left; padding: 20px 1%; width: 98%;">
			<div class="border">
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
		<s:hidden id="offeringLevel" value="%{offeringLevel}"/>
		<%-- offeringLevel<s:property value="%{offeringLevel}"/> --%>
		<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/industry" action="assignWeightage" theme="simple"  method="post">
      <div class="form_menubox">
      <s:if test="OLevel1">
	<span id="form2MandatoryFields" class="dIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
	<div class="newColumn">
	<s:if test="lastOffering==1">
	<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	</s:if>
	<s:elseif test="lastOffering==2">
	<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	</s:elseif>
	<s:elseif test="lastOffering==3">
	<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	</s:elseif>
	<s:elseif test="lastOffering=4">
	<div class="leftColumn1"><s:property value="%{OLevel4LevelName}"/>:</div>
	</s:elseif>
	<s:elseif test="lastOffering5">
	<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
	</s:elseif>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="offeringId"
                              name="offeringId" 
                              list="offeMasterMap"
                              headerKey="-1"
                              headerValue="Select Name" 
                              cssClass="select"
                              cssStyle="width:82%" 
                              onchange="fillName(this.value,'subIndustry','1')"
                              >
                   
                  </s:select>
	</div>
	</div>
	</s:if>
      <!-- start Drop Down  -->
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{subIndustryHeadingName}"/>:</div>
	  <div class="rightColumn1">
	    <span class="needed"></span>
	         <s:select 
	                     id="subIndustry"
	                     name="subindustryID" 
	                     list="#{}"
	                     headerKey="-1"
	                     headerValue="Select Industry" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
                       	 onchange="assignWeightage(this.value)"
	                     >
	         </s:select>
	</div>
	</div><br>
	
	<div class="clear"></div>
	<div id="mappedWeightageWithDept">	</div>

			 </div>
 
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center;">
	                <sj:submit 
	                        targets="orgleve21Div" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level2"
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
						onclick="returnIndustry()"
					>
					Back
					</sj:a>	
	               </div>
				</ul>
				<sj:div id="orglevel2"  effect="fold">
                    <div id="orgleve21Div"></div>
               </sj:div>
               </div>
</s:form>
</div>
</div>
</div>
</div>
</body>
</html>