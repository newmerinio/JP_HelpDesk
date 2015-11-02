<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">

$.subscribe('level1', function(event,data)
      {
        setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
        setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
      });



$.subscribe('validate', function(event,data)
{
    var mystring = $(".pIds").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2]; 
        //fieldsType[i]=first_name#t
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
            var allphanumeric = /^[-+]?[0-9]+$/; 
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
           }
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[-+]?[0-9]+$/; 
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
            else if(colType=="Date"){
                alert("***");
                if($("#"+fieldsvalues).val()=="")
                {
                    alert(fieldsvalues);
                errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+"</div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;    
                  }
            }
            else if(colType=="Time"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" </div>";
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


</script>

<SCRIPT type="text/javascript">
function displayValues(value)
{
mydiv = document.getElementById('showhis');
mydiv1 = document.getElementById('showhist');
if(value=="Days"){
if( mydiv.style.display = 'none'){
mydiv.style.display = 'block';
mydiv1.style.display = 'none';
}
}
if(value=="Hour"){
if( mydiv1.style.display = 'none'){
mydiv1.style.display = 'block';
mydiv.style.display = 'none';
}
}}

</script>

</head>
<div class="page_title"><h1>Leave Request >> Registration</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">

<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title=" Leave Request >> Add" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="formone"  namespace="/view/Over2Cloud/leaveManagement" action="requestAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
                                                                                                  
                    <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
                    <s:hidden name="leaveLft" id="leaveLft" value="%{leaveLeft}"></s:hidden>
                        <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
                    <div class="form_menubox">
         <div class="user_form_text">Leave Unit:</div>                                   Leave Left:: <s:label name="leaveLft" value="%{leaveLeft}"></s:label>   
         <div class="user_form_input">
                      <s:radio cssClass="abc" name="leaveunit" id="leaveunit" list="{'Days','Hour'}" onclick="displayValues(this.value);" ondblclick="disableValue(this.value);"></s:radio></div>
      </div>
      
       <div id="showhis" style="display: none;">
                 
 <div class="form_menubox">
 <s:iterator value="requestColumnDate" status="counter"  begin="0" end="1">
       <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
    <s:if test="#counter.count%2 != 0">
    <s:if test="%{mandatory}">
                 <div class="user_form_text" ><s:property value="%{value}" />:</div>
                 <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select From date"/></div>
</s:if>
<s:else>
 <div class="user_form_text"><s:property value="%{value}" />:</div>
                 <div class="user_form_input"> <sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select From date"/></div>
</s:else>
</s:if>
<s:else>
 
 	<s:if test="%{mandatory}">
 <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select To date"/></div>
</s:if>
<s:else>
 <div class="user_form_text1" ><s:property value="%{value}" />:</div>
                 <div class="user_form_input"><sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select To date"/></div>
</s:else>
</s:else>
    </s:iterator>	
     </div>
     
     </div>
   
    <div id="showhist" style="display: none;">
     <div class="form_menubox">
    <s:iterator value="requestColumnDate" begin="2">
      <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
<s:if test="%{mandatory}">
                 <div class="user_form_text" ><s:property value="%{value}" />:</div>
                 <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select To date"/></div>
    </s:if>
    <s:else>
     <div class="user_form_text" ><s:property value="%{value}" />:</div>
                 <div class="user_form_input"><sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select To date"/></div>
    </s:else>
    </s:iterator>
     </div>
                  
 <div class="form_menubox">
 <s:iterator value="requestColumnTime" status="counter">
       <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
 <s:if test="#counter.count%2 != 0">
 <s:if test="%{mandatory}">
 
                 <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span><sj:datepicker timepicker="true" timepickerOnly="true"  name="%{key}" id="%{key}"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="form_menu_inputtext" placeholder="Select From Time"/></div>
 </s:if>
 <s:else>
 <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><sj:datepicker timepicker="true" timepickerOnly="true"  name="%{key}" id="%{key}"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="form_menu_inputtext" placeholder="Select From Time"/></div>
 </s:else>
 </s:if>
 
 <s:else>
 <s:if test="%{mandatory}">
 <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span><sj:datepicker timepicker="true" timepickerOnly="true" name="%{key}" id="%{key}"   timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" showOn="focus"  cssClass="form_menu_inputtext" placeholder="Select To Time"/></div>
 </s:if>
 <s:else>
 <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><sj:datepicker timepicker="true" timepickerOnly="true" name="%{key}" id="%{key}"   timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" showOn="focus"  cssClass="form_menu_inputtext" placeholder="Select To Time"/></div>
 </s:else>
 </s:else>
    </s:iterator>
    </div>
           </div>
                      <div class="form_menubox">
     <s:iterator value="requestColumnDropdown">
           <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
 				<s:if test="%{key == 'empname'}">
 				<s:if test="%{mandatory}">
                 <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span>
                 <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="employeelist"
                              headerKey="-1"
                              multiple="true"
                              headerValue="Select Validate By" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
                  </s:if>
                  <s:else>
                   <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input">
              
                 <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="employeelist"
                              headerKey="-1"
                              multiple="true"
                              headerValue="Select Validate By" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
                  </s:else>
                  </s:if>
                   
                  <s:if test="%{key == 'leavestatus'}">
                  <s:if test="%{mandatory}">
 <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span><s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="leaveType"
                              headerKey="-1"
                              headerValue="Select Type" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
                  </s:if>
                  <s:else>
                
                   <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="leaveType"
                              headerKey="-1"
                              headerValue="Select Type" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
                  
                  </s:else>
                  </s:if>
</s:iterator>  
</div>
 
 <div class="form_menubox">
<s:iterator value="requestColumnText">
      <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
<s:if test="%{key == 'reason'}">
<s:if test="%{mandatory}">
                 <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><span class="needed"></span>
                  <s:textarea id="%{Key}" name="%{Key}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textarea>
                 </div>
                 </s:if>
                 <s:else>
                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input">
                  <s:textarea id="%{Key}" name="%{Key}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textarea>
                 </div>
                 
                 </s:else>
                  </s:if>
                  </s:iterator>
                    </div>
       
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="fields">
                <ul>
                <li class="submit" style="background: none;">
                    <div class="type-button">
                    <sj:submit 
                            targets="orglevel1" 
                            clearForm="true"
                            value="  OK  " 
                            effect="highlight"
                            effectOptions="{ color : '#222222'}"
                            effectDuration="5000"
                            button="true"
                            onCompleteTopics="level1"
                            cssClass="submit"
                            indicator="indicator2"
                            onBeforeTopics="validate"
                            />
                   </div>
                   </li>
                </ul>
             	<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               
               </div>
               
               </s:form>
</div>
</div></sj:accordionItem></sj:accordion>

</div>
</div>  

</html>