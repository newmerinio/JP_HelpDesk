<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
	
	<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>

<script type="text/javascript">
$.subscribe('validate', function(event,data)
		{	
			validate(event,data,"cIds");
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
        errZone11.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
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
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }   
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone11.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone11.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone11.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
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
                	  errZone11.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
                   $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                   $("#"+fieldsvalues).focus();
                   setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
                   setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
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
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
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
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone11.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}


</script>

<SCRIPT type="text/javascript">
	$.subscribe('level1233', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {
			closeAdd();
		}, 4000);
		reset("formone");
	});
</script>

<script type="text/javascript">
	function closeAdd() {
		$("#addDialog").dialog('close');
		//backToLinsence();
	}
</script>
<script type="text/javascript">
	function backToView() {
		$("#data_part")
				.html(
						"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
					type : "post",
					url : "/over2cloud/view/Over2Cloud/WFPM/Patient/viewCorporateHeader.action",
					success : function(subdeptdata) {
						$("#" + "data_part").html(subdeptdata);
					},
					error : function() {
						alert("error");
					}
				});
	}

	function reset(formId) {
		$("#" + formId).trigger("reset");
	}
</script>
</head>
<body>
	<div class="clear"></div>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="350" height="250">
		<sj:div id="level123"></sj:div>
	</sj:dialog>


	<div class="middle-content">

		<div class="list-icon">
			<div class="head">Corporate Details</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formoneCorporate" name="formone1"
						namespace="/view/Over2Cloud/WFPM/Patient"
						action="addCorporateDetails" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone11" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

			
				<s:iterator  value="corporateDropDown" begin="0" end="0" >
						<s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="corpTypeMap"
                                      headerKey="-1"
                                      headerValue="--Select Corporate Type--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
			
				    </div>
			        </div>
						</s:iterator>
			
				<s:iterator value="corporateTextBox" status="status" begin="0" end="0">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
						</s:iterator>
					
						<s:iterator value="corporateTextBox" status="status" begin="1" end="1">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
						</s:iterator>
						
					<s:iterator  value="corporateDropDown" begin="1" end="3" >
						<s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							  <s:if test="%{key == 'country'}">
							          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="countryMap"
                                      headerKey="-1"
                                      headerValue="--Select Country--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="commonSelectAjaxCall('state','id','state_name','country_code',this.value,'','','','state_name','state','--Select State--');"
                                      >
                         		 </s:select>
                        		  </s:if>
					
							  <s:elseif test="%{key == 'state'}">
							          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="{'No Data'}"
                                      headerKey="-1"
                                      headerValue="--Select State--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="commonSelectAjaxCall('city','id','city_name','name_state',this.value,'','','','city_name','city','--Select City--');"
                                      >
                          </s:select>
                          </s:elseif>
                          <s:elseif test="%{key == 'city'}">
                          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="{'No Data'}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      ></s:select>
                          
                          </s:elseif>
		
			
				    </div>
			        </div>
						</s:iterator>
				
					<s:iterator value="corporateTextBox" status="status" begin="2" end="4">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
				</s:iterator>
				
				<s:iterator  value="corporateDropDown" begin="4" end="4">
						<s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="{'Gold','Silver','Platinium'}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
			
				    </div>
			        </div>
						</s:iterator>
						
					<s:iterator value="corporateTextBox" status="status" begin="5">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="cIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
				</s:iterator>			
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="level123" 
            clearForm="true"
            value="Save" 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="level1233"
            cssClass="submit"
            indicator="indicator2"
            onBeforeTopics="validate"
            
          />
           <sj:a 
       name="Reset"  
href="#" 
cssClass="submit" 
indicator="indicator" 
button="true" 
onclick="reset('formone1');"
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
onclick="backToView();"
cssStyle="margin-top: -43px;"
>
 	Back
</sj:a>
   </div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>