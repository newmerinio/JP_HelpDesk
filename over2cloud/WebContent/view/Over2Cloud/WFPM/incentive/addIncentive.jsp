<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<SCRIPT type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}

function returnPage()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/incentive/selectIncentiveView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</SCRIPT>
<SCRIPT type="text/javascript">

var ddName;
var submitForm = true;
$.subscribe('validate', function(event,data)
{
	ddName =  $('input:radio[name=targetOn]:checked').val();

    
	var mystring=$(".dIds").text();
	
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
    
    /****************** Check incentive slab range ********************************************************************************************/
    /*
    var targetOn, slabFrom, slabTo, kpiId, kpiName;   
    targetOn = $('input:radio[name=targetOn]:checked').val();
    slabFrom = $("#slabFrom").val();
    slabTo = $("#slabTo").val();
    kpiId = $("#kpiId").val();
    kpiName = $("#kpiId option:selected").text();
	$.ajax({
		type : "POST",
		url  : "view/Over2Cloud/WFPM/incentive/checkIncentive.action",
		data : "targetOn="+targetOn+"&slabFrom="+slabFrom+"&slabTo="+slabTo+"&id="+kpiId,
		success : function(data){
			if(data.slabFrom != null || data.slabFrom != "")
			{
				$("#confirmationDialog").html("<center>"+kpiName+"<==>"+data.slabFrom+"<==>"+data.slabTo+"<==>"+data.incentiveAmount+"</center>");
				$("#confirmationDialog").dialog('open');
		    	event.originalEvent.options.submit = false;
			}
		},
		error : function(){
			alert("error");
		}		
	});
    */
    /*********************************************************************************************************************/
});
function clearAllErroDiv()
{
	$("div[id^='error']").each(function() {
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}
function showTargetPanel123(value, selectId)
{
	targetOn = value;
	if(value == "kpi")
		$("#offeringType").text("KPI:");
	else
		$("#offeringType").text("Offering:");
		
	$.ajax({
        type: "POST",
        url: "view/Over2Cloud/WFPM/incentive/getTargetValue.action",
        data: "targetType=" + value,
        success: function(response){
        	$('#'+selectId+' option').remove();
    		$('#'+selectId).append($("<option></option>").attr("value",-1).text('--Select Target--'));
        	$(response).each(function(index){
    		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
    		});
      },
       error: function(e){
            alert('Error: ' + e);
       }
    });
}

$.subscribe('incentivelevel2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel2").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel2").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
          if(ddName == 'kpi')
          	$('input:radio[name=targetOn]')[0].checked = true;
          else if(ddName == 'offering')
       	    $('input:radio[name=targetOn]')[1].checked = true;  
});


</SCRIPT>

</head>
<sj:dialog
	id="confirmationDialog"
	autoOpen="false"
	title="Confirmation"
	modal="true"
	width="600"
	height="600"
	></sj:dialog>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<!-- <div class="head">Add Incentive</div> -->
		<div class="head">Incentive</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div style=" float:left;  width:98%;">
		<div class="border">
			<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/incentive" action="createIncentive" theme="simple"  method="post" enctype="multipart/form-data" >
				<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>	
				
				<div class="menubox">
					<div class="newColumn">
						<div class="leftColumn1">Target on :</div>
						<div class="rightColumn1">
						<s:radio id="targetOn" 
							 	 cssStyle="margin-left:10px" 
							 	 name="targetOn" 
							 	 list="#{'kpi':'KPI','offering':'Offering'}" 
							 	 value="%{'kpi'}" onclick="showTargetPanel123(this.value, 'kpiId');" /></div>
					</div>
					
					<s:if test="kpiDetailExist">
					<span id="form2MandatoryFields" class="dIds" style="display: none; ">kpiId#KPI / Offering#D#,</span>
					<div class="newColumn">
						<div class="leftColumn1"><div id="offeringType"></div></div>
						<div class="rightColumn1">
							<span class="needed"></span>
							<s:select 
	                             id="kpiId"
	                             name="kpiId" 
	                             list="kpiList"
	                             headerKey="-1"
	                             headerValue="--Select Target--" 
	                             cssClass="textField"
	                             >
		                  	</s:select>
		                  </div>
						</div>
					
					</s:if>
					
					<div class="clear"></div>
					<s:iterator value="incentiveTextField" status="status">
					<s:if test="%{field_value != 'incentive'}">
						<s:if test="#status.odd">
							<div class="clear"></div>
						</s:if>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{field_value}"  id="%{field_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								
								<%-- <s:if test="%{field_value == 'slabFrom'}">
									<sj:spinner name="%{field_value}" id="%{field_value}"  min="1" max="491" step="10" value="1" cssStyle="width: 83% " cssClass="textField"  mouseWheel="false" placeholder="Enter Data"/>
								</s:if>
								<s:else>
									<sj:spinner name="%{field_value}" readonly="true" id="%{field_value}" min="10" max="500" cssStyle="width: 83% " step="10" value="10" cssClass="textField" mouseWheel="false" placeholder="Enter Data"/>
								</s:else> --%>
								
							</div>
						</div>
					</s:if>
					</s:iterator>
					
					<!-- For incentive field -->
					<s:iterator value="incentiveTextField" status="status">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					</s:if>
					<s:if test="%{field_value == 'incentive'}">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{field_value}"  id="%{field_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					</s:if>
					</s:iterator>
					
					
					
					<s:if test="monthDetailExist">
					<span id="form2MandatoryFields" class="dIds" style="display: none; ">month#Month#D#,</span>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="monthLabel"/>:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<sj:datepicker name="month" id="month" minDate="0" readonly="true" changeMonth="true" 
								changeYear="true" yearRange="1890:2050" showOn="focus" displayFormat="mm-yy"  cssClass="textField" 
								placeholder="Select Date"/>
							</div>
						</div>
					</s:if>
					
					<div class="clear"></div>
					<!-- Buttons -->
					<div class="buttonStyle" style="width: 100%; text-align: center; padding-bottom: 10px;">
				    <sj:submit 
				           	targets="orglevel2Div" 
	                        clearForm="true"
	                        value="Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="incentivelevel2"
	                        cssClass="button"
	                        indicator="indicator3"
	                        onBeforeTopics="validate" 
				          />
				          
				          <sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formone');"
									cssStyle="margin-left: 0px;margin-top: -1px;"
								>
								  	Reset
								</sj:a>
								<sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							cssStyle="margin-left: 164px;margin-top: -27px;"
							onclick="returnPage()"
							cssStyle="margin-top: 0px;"
							
						>
						  	Back
						</sj:a>
								
					<!-- <input id="sendMailButton" class="button"  value="Back" type="button" onclick="returnPage();"/> -->
					</div>
					
					    
						<sj:div id="orglevel2"  effect="fold">
		                    <div id="orglevel2Div"></div>
		               </sj:div>
				</div>
			</s:form>
		</div>
	</div>
	
	
	
	
	
</body>
<SCRIPT type="text/javascript">
showTargetPanel123('kpi', 'kpiId');
</SCRIPT>
</html>
