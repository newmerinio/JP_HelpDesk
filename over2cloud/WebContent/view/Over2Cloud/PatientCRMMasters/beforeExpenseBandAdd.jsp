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

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	
<script type="text/javascript">
$.subscribe('validate', function(event,data)
		{	
			validate(event,data,"pIds");
		});

function validate(event,data, spanClass)
{
	var mystring=$("."+spanClass).text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    var pattern2 = /^\d+$/;
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
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
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
             else if(validationType =="num"){

                 if($("#"+fieldsvalues).val().length == 0 || $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
             else if(validationType =="num2"){

                 if ($("#"+fieldsvalues).val().length > 0 && !pattern2.test($("#"+fieldsvalues).val())) {
                    errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
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
            else if(validationType =="td")
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

function makeTrim(id){
	//alert(id);
	if($("#"+id).val().length > 0){
		$("#"+id).val(($("#"+id).val()).trim());
	}
}

function makeReadFalse(tid,sid,aid){
	if($('#'+sid).is(':checked')){
			$("#"+tid).attr("readonly",false);
			$("#"+aid).attr("readonly",false);
		}
	else{
		$("#"+tid).attr("readonly",true);
		$("#"+aid).attr("readonly",true);
			$("#"+tid).val("");
		}
}

</script>

<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {
			closeAdd() ;
		}, 4000);
		reset("formone");
	});
</script>

<script type="text/javascript">
	function closeAdd() {
		$("#addDialog").dialog('close');
		 backToView();
	}
</script>
<script type="text/javascript">
	function backToView() {
		$("#data_part")
				.html(
						"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
					type : "post",
					url : "view/Over2Cloud/patientCRMMaster/viewExpenseBandHeader.action",
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
<STYLE type="text/css">
th{
		padding:2px;
		border-right: 1px solid #e7e9e9;
		margin:1px;
		text-align:center;
	}
td{
		padding:2px;
		margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center;
}	

</STYLE>
</head>
<body>
	<div class="clear"></div>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="350" height="150">
		<sj:div id="level123"></sj:div>
	</sj:dialog>

	<div class="middle-content">
		<div class="list-icon">
			<div class="head">Expense Mapped Band With Parameters</div>
			<div class="head">
				<img alt="" src="images/forward.png" style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone" name="formone" namespace="/view/Over2Cloud/patientCRMMaster" action="addExpenseBandDetails" theme="css_xhtml" method="post" enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>
						<s:iterator value="packageFields" status="status" begin="0" end="0">
							 <s:if test="%{mandatory}">
				     			<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		                    </s:if>
					       	<div class="newColumn" >
					  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			           		<div class="rightColumn1">
						    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						         <s:if test="%{key == 'band_name'}">
						         	<s:select 
		                                      id="%{key}"
		                                      name="%{key}" 
		                                      list="#{'A':'A','B':'B','C':'C','D':'D','E':'E','F':'F','G':'G'}"
		                                      headerKey="-1"
		                                      headerValue="Select Band Name"
		                                      cssClass="select"
		                                      cssStyle="width:82%"
		                                      theme="simple"
		                                      >
		                          </s:select>
						         	</s:if>
						    </div>
					        </div>
					  </s:iterator>
					  <div id="<s:property value="%{#typeIndx}"/>" style="display: block ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);padding: 1%; overflow: auto; "  class="container_block" >
								    <center>
								    <table style="border: 1px solid;" width="100%">
								    <thead>
								    <tr>
									    <th >Sr. No.</th>
									    <th>Parameter</th>
									    <th>Checkbox</th>
									    <th>Value</th>
									    <th>Rate per unit</th>
								    </tr>
								    </thead>
								    <tbody>
									    <s:property value="parameterMap.size"/> 
									    <s:iterator id="parameterMap"  value="paramList" status="status">
										    <tr>
											    <th><s:property value="#status.count"/>.</th>
											    <th><s:property value="%{value}"/></th>
											    <th><s:checkbox  theme="simple" labelposition="right" name="band_parameter" id="band_parameter%{#status.count}" onchange="makeReadFalse('band_value%{#status.count}','band_parameter%{#status.count}','rate_value%{#status.count}');" fieldValue="%{key}"/></th>
											    <th>
											    <span id="complSpan" class="pIds" style="display: none; ">band_value<s:property value="#status.count"/>#Value#T#num2,</span>
											    <s:textfield  name="band_value" id="band_value%{#status.count}" onkeyup="makeTrim(this.id);"  maxlength="20" readonly="true" cssClass="textField" placeholder="Enter Data"/>
											    </th>
											    <th>
											    <span id="complSpan" class="pIds" style="display: none; ">rate_value<s:property value="#status.count"/>#Value#T#num2,</span>
											    <s:textfield  name="rate_value" id="rate_value%{#status.count}" onkeyup="makeTrim(this.id);"  maxlength="20" readonly="true" cssClass="textField" placeholder="Enter Data"/>
											    </th>
										    </tr>
									    </s:iterator>
								    </tbody>
								    </table>
								</center>    
                         </div> 
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#222222'}"
									effectDuration="5000" button="true" onBeforeTopics="validate"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
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