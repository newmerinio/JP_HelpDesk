<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="<s:url value="/js/WFPM/industry/industry.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('validate1', function(event,data)
{
	validate(event,data,"pIds");
});
$.subscribe('validate2', function(event,data)
{
	validate(event,data,"qIds");
	$('#completionResult').dialog('open');
});

	function validate(event,data,spanClass)
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
		       // alert("fieldsvalues "+fieldsvalues+"fieldsnames "+fieldsnames+" colType "+colType+" validationType "+validationType);
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
$.subscribe('validateFormTwo', function(event,data)
		{	
		var mystring=$(".aIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		        //alert("fieldsvalues "+fieldsvalues+"fieldsnames "+fieldsnames+" colType "+colType+" validationType "+validationType+" Val:"+$("#"+fieldsvalues).val());
		        $("#"+fieldsvalues).css("background-color","");
		        assErrZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
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
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            	//alert("annnnnnnnnnnnnnnnn");
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }else if(validationType=="ans"){
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
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
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
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
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
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
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
	         setTimeout(function(){ $("#orglevel2").fadeOut();  cancelButton(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });

function cancelButton()
{
	 $('#completionResult').dialog('close');
	 returnIndustry();
}

	function returnIndustry()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/industry/beforeIndustryView.action?status=Active",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
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
	if(formId==formone)
		{
		$('#'+formId).trigger("reset");
		}
	else
	{
		$('#'+formId).trigger("reset");
	}
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
          <sj:div id="orgleve21Div"   effect="fold"> </sj:div>
</sj:dialog>
<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Industry</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>
<div class="clear"></div>

<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">
			<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>
              <br>
<sj:accordion id="accordion" autoHeight="false">
<s:if test="levelOfIndustry>0">
<sj:accordionItem title="Industry" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
 <s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/industry" action="level1AddIndustry" theme="simple"  method="post"enctype="multipart/form-data" >
						     
      <div class="menubox">
				 <s:iterator value="industryLevel1TextBox" status="counter">
                   <s:if test="%{mandatory}">
	                   <s:if test="%{key == 'industry'}">
                      	<span id="mandatoryFields" class="pIds" style="display: none; ">industryId#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:else>
	                      	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:else>  
                  </s:if>  
                  
				 	 <s:if test="%{key == 'industry'}">
	                     <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{mandatory}">
						<span class="needed"></span>
						</s:if>
					     <s:textfield id="industryId" name="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
					  </s:if>
					  <s:else>
					    <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{mandatory}">
						<span class="needed"></span>
						</s:if>
					     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
					  </s:else>
			
				 </s:iterator>
			 
             
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
						onclick="returnIndustry()"
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
</s:if>
<!-- Industry  Mapping with SubIndustry -->
<s:if test="levelOfIndustry>1">
<sj:accordionItem title="Sub-Industry" id="costMapping">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/industry" action="level2AddSubIndustry" theme="simple"  method="post">
			<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="assErrZone" style="float:left; margin-left: 7px">
             </div> 
             </div><br>
			<div id="offering2HideShow">
			<span id="mandatoryField" class="qIds" style="display: none; ">industry#Industry#D#,</span>
				 <div id="level1Offering"></div>
		
      			<div class="menubox">
				 <s:iterator value="industryLevel2TextBox" status="counter">
           			<s:if test="%{mandatory}">
                      <span id="mandatoryField" class="qIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>  
				 	 <s:if test="#counter.count%2 != 0">
				 
				
	                   <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						 <s:if test="%{mandatory}">
						 <span class="needed"></span>
						 </s:if>
					     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
				    
					  <s:else>
					     <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
					     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
					     </s:else>
					  </s:if>
					  <s:else>
					   <s:if test="%{mandatory}">
					     <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1"><span class="needed"></span>
					     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
					    </s:if>
					    <s:else>
					    <div class="newColumn">
	     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
					     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
					     </div>
					    </s:else>
					  </s:else>
			
				 </s:iterator>
				<div id="newDiv" style="float: right;width: 7%;margin-top: -41px; margin-right: -10px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
	
	
	<s:iterator begin="100" end="105" var="deptIndx">
	<div class="clear"></div>
	<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
				<s:iterator value="industryLevel2TextBox" status="counter">
				                   <s:if test="%{mandatory}">
				                      <span id="mandatoryField" class="qIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				                 </s:if>  
								 
			                   <div class="newColumn">
				     				<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
								     <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
							     </div>
           		 <s:if test="#counter.even">
	           		 	<s:if test="#deptIndx==105">
		                	<div id="newDiv" style="float: right;width: 7%;margin-top: -41px; margin-right: 25px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
		                </s:if>
		           		<s:else>
		                  	<div id="newDiv" style="float: right;margin-top: -41px; margin-right: 50px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
		                  	<div id="newDiv" style="float: right;margin-top: -41px; margin-right: 10px;"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
	           		   </s:else>
           		 <div class="clear"></div>
           		 </s:if>
				 </s:iterator>
				 
	</div>
</s:iterator>
				 
	<div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center;">
	                <sj:submit 
	                        targets="orgleve21Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level2"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate2"
	                        /><!--
	                        onBeforeTopics="validateFormTwo"
                        --><sj:a 
					     	name="Reset"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							onclick="resetForm('formTwo');"
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
				<!--<sj:div id="orglevel2"  effect="fold">
                    <div id="orgleve21Div"></div>
               </sj:div>
               --></div>
               </div>
               </div>
               
</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
</sj:accordion>
</div>
</div>
</body>
<SCRIPT type="text/javascript">
allLevel1Offering('level1Offering','offering2HideShow');
</SCRIPT>
</html>