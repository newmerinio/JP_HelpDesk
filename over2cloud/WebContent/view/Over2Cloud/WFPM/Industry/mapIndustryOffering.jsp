<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<!--<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
--><script src="<s:url value="/js/WFPM/industry/targetMap/targetMap.js"/>"></script>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">	
$(document).ready(function(){
$("#targetSegment").multiselect({
	                              show: ["bounce", 200],
	                              hide: ["explode", 1000]
	   	                       });
$("#subofferingname1").multiselect();
$("#offeringname1").multiselect();
$("#verticalname").multiselect({
    show: ["bounce", 200],
    hide: ["explode", 1000]
    });

  }
);
</script>
<script type="text/javascript">
//fetchDepartment();


/*function fetchDepartment()
{
  var data=$("#targetSegment").val();
      alert("data***"+data);
     if(data != null && data != '-1')
     {
    	 fetchDepartmentToMap();
     }

}*/

/*$("#"+"targetSegment").subscribe(function(event,data)
{   alert("azad");
	fetchDepartmentToMap();
});*/

</script>

<SCRIPT type="text/javascript">

$.subscribe('validate1', function(event,data)
{
	validate(event,data,"pIds");
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
		        //alert("fieldsvalues "+fieldsvalues+"fieldsnames "+fieldsnames+" colType "+colType+" validationType "+validationType);
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
			  else{
			      	$('#completionResult').dialog('open');
			     }
		    }       
		}

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
$.subscribe('level3', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel3").fadeOut();cancelButton();}, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	         
	       });	       

function cancelButton()
{
	 $('#completionResult').dialog('close');
	 returnIndustry();
}

$.subscribe('validate2', function(event,data)
        {
      		$("#verticalnameId").val($("#verticalname").val());
		    $("#offeringnameId").val($("#offeringname").val());
		    $("#subofferingnameId").val($("#subofferingname").val());
		    $("#variantnameId").val($("#variantname").val());
		    $("#subvariantsizeId").val($("#subvariantsize").val());
		    $("#subIndustryId").val($("#subIndustry").val());
		    /*  alert("Hellooooo:"+$("#verticalnameId").val());
		    alert("Hellooooo:"+$("#offeringnameId").val());
		    alert("Hellooooo:"+$("#subofferingnameId").val());
		    alert("Hellooooo:"+$("#variantnameId").val());
		    alert("Hellooooo:"+$("#subvariantsizeId").val());
		    alert("subIndustryId:"+$("#subIndustryId").val()); */
		   
		    //alert("hello>>>>>>>>> "+$("#total").val());		    
		   // if($("#total").val() < 100 || $("#total").val() > 100)
		    //	{
              //      alert("Total weightage should be 100%");		    	
              //      event.originalEvent.options.submit = false;
		    //}
        });

	function returnIndustry()
	{
			$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/wfpm/industry/beforeTargetSegmentView.action",
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
          <sj:div id="orgleve22Div"   effect="fold"> </sj:div>
</sj:dialog>
<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Target Segment</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Map Offering with Target Segment and Department</div>
	</div>
<div class="clear"></div>
   <div class="container_block">
		 <div style="float: left; padding: 20px 1%; width: 98%;">
		
			 <div class="border">
					        <div class="form_inner" id="form_reg" style="margin-top: 10px;">
		                    <s:hidden id="offeringLevel" value="%{offeringLevel}"/>
		                    <s:hidden id="offeringId" value="%{offeringId}"/>
		                    <%-- offeringLevel<s:property value="%{offeringLevel}"/> --%>
		                    <s:form id="formone" name="formone" cssClass="cssn_form" 
			                                   namespace="/view/Over2Cloud/wfpm/industry" action="addMapIndustryOffering" 
			                                                    theme="simple"  method="post">
		                     <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:left; margin-left: 7px">
                             </div> 
                             </div>
                             <br>
			
                             <div class="menubox">
                                   <s:if test="OLevel1">
	                                     <span id="form2MandatoryFields" class="pIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
	                                     <div class="newColumn">
	                                     <div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	                                     <div class="rightColumn1">
	                                     <span class="needed"></span>
	                                     <s:select 
                                                  id="verticalname"
                                                  name="verticalname" 
                                                  list="verticalMap"
                                                  cssClass="select"
                                                  multiple="true"
                                                  cssStyle="width:24%"
                                                  onchange="fetchLevelDataForoffering(this.value,'offeringid','1')"
                                                  >
                   
                                         </s:select>
	                                     </div>
	                                     </div>
	                               </s:if>
	                        <div id="offeringid">
	                               <s:if test="OLevel2">
	                                     <span id="form2MandatoryFields" class="pIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
	                                     <div class="newColumn">
	                                     <div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	                                     <div class="rightColumn1">
	                                     <span class="needed"></span>
	                                     <s:select 
                                                  id="offeringname1"
                                                  name="offeringname" 
                                                  list="#{'-1':'Select'}"
                                                  cssClass="select"
                                                  multiple="true"
                                                  cssStyle="width:24%"
                                                 
                                                  >
                  	                     </s:select>
	                                     </div>
	                                     </div>
	                               </s:if>
	                        </div>          
	                        <div id="subofferingid">
	                               <s:if test="OLevel3">
	                                      <span id="form2MandatoryFields" class="pIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
	                                      <div class="newColumn">
	                                      <div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	                                      <div class="rightColumn1">
	                                      <span class="needed"></span>
	                                      <s:select 
                                                id="subofferingname1"
                                                name="subofferingname" 
                                                list="{'no data'}"
                                                cssClass="select"
                                                multiple="true"
                                                name="subofferingname" 
                                                cssStyle="width:24%"
                                               
                                                >
                  	                      </s:select>
	                                      </div>
	                                      </div>
	                               </s:if>
	                       </div>
	                               <s:if test="OLevel4">
	                                      <span id="form2MandatoryFields" class="pIds" style="display: none; ">variantname#<s:property value="%{OLevel4LevelName}"/>#D#,</span>
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
	                                       <span id="form2MandatoryFields" class="pIds" style="display: none; ">subvariantsize#<s:property value="%{OLevel5LevelName}"/>#D#,</span>
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
	                               </s:if>
                           <!-- start Drop Down  -->
                                   <span id="form2MandatoryFields" class="pIds" style="display: none; ">targetSegment#<s:property value="%{industryDDHeadingName}"/>#D#,</span>
                                   <div class="newColumn">
	                               <div class="leftColumn1">Target Segment</div>
	                               <div class="rightColumn1">
	                               <span class="needed"></span>
	                                        <s:select 
	                                           id="targetSegment"
	                                           name="targetSegment" 
	                                           list="targetSegmentMap"
	                                           multiple="true"
	                                           cssClass="select"
                       	                       cssStyle="width:24%"
                       	                       onchange="fetchDepartmentToMap()"
                       	                      
                       	                       >
	                                        </s:select>
	                               </div>
	                               </div>
	                               <!--<span id="form2MandatoryFields" class="pIds" style="display: none; ">subIndustry#<s:property value="%{subIndustryHeadingName}"/>#D#,</span>
	                               <div class="newColumn">
	                               <div class="leftColumn1"><s:property value="%{subIndustryHeadingName}"/>:</div>
	                               <div class="rightColumn1">
	                               <span class="needed"></span>
	                                         <s:select 
	                                            id="subIndustry"
	                                            name="subIndustry" 
	                                            list="#{'-1':'Select'}"
	                                            headerKey="-1"
	                                            headerValue="Select Industry" 
	                                            cssClass="select"
                       	                        cssStyle="width:82%"                       	 
                       	                        onchange="fetchDepartmentToMap()" 
	                                            >
	                                         </s:select>
	                               </div>
	                               </div>
	
	                               --><!--<div class="newColumn">
	                                  <div class="leftColumn1"><s:property value="%{deptHeadingName}"/>:</div>
	                                  <div class="rightColumn1">
	                                  <span class="needed"></span>
	                                  <s:select 
	                                  id="deptName"
	                                  name="deptName" 
	                                  list="deptMasterMap"
	                                  headerKey="-1"
	                                  headerValue="Select Department" 
	                                  cssClass="select"
                       	              cssStyle="width:82%"
	                                  >
	                                 </s:select>
	                                 </div>
	                                 </div>
	
	                              --><div class="clear"></div><br>
			                         </div>
 
                                     <div class="clear"></div>
                                     <div id="mappedWeightageWithDept">	</div>
                           </s:form>
 
                           <s:form id="formTwo" action="saveAssignWeightage" theme="css_xhtml" name="formTwo" method="post" 
 	                                        namespace="/view/Over2Cloud/wfpm/industry"> 
 	                             <s:hidden id="verticalnameId" name="verticalname"/>
 	                             <s:hidden id="offeringnameId" name="offeringname"/>
 	                             <s:hidden id="subofferingnameId" name="subofferingname"/>
 	                             <s:hidden id="variantnameId" name="variantname"/>
 	                             <s:hidden id="subvariantsizeId" name="subvariantsize"/>
	                             <s:hidden id="targetSegmentId" name="targetSegment"/>
	                                       <div id="mappedWeightageWithDept"></div>
	
                           </s:form>
 
				           <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				           <div class="fields">
				           <ul>
				              <li class="submit" style="background: none;">
					           <div class="type-button" style="text-align: center;">
	                           <!--<sj:submit 
	                                   formIds="formone"
	                                   targets="orgleve21Div" 
	                                   clearForm="true"
	                                   value="  Map  " 
	                                   effect="highlight"
	                                   effectOptions="{ color : '#222222'}"
	                                   effectDuration="5000"
	                                   button="true"
	                                   onCompleteTopics="level2"
	                                   cssClass="submit"
	                                   indicator="indicator2"
	                                   onBeforeTopics="validate1"
	                                   />
	                      
	                           --><%--  <sj:a 
	                                   button="true" 
	                                   cssClass="button" 
	                                   cssStyle="height:25px;" 
	                                   title="Add" 
	                                   buttonIcon="" 
	                                   onclick="weightageAssinge();">
	                                   Weightage</sj:a> --%>
	                        
	                           <!--<sj:a 
	                                   button="true" 
	                                   cssClass="button" 
	                                   cssStyle="height:25px;" 
	                                   title="Add" 
	                                   buttonIcon="" 
	                                   onclick="assignWeightage('subIndustry');">
	                                   Weightage</sj:a>
	                       
	                          --><sj:submit 
	                                   formIds="formone"
	                                   targets="orgleve22Div" 
	                                   clearForm="true"
	                                   value=" Save " 
	                                   effect="highlight"
	                                   effectOptions="{ color : '#222222'}"
	                                   effectDuration="5000"
	                                   button="true"
	                                   onCompleteTopics="level3"
	                                   cssClass="submit"
	                                   indicator="indicator2"
	                                   onBeforeTopics="validate12"
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
                            </sj:div><!--
               	
               	            <sj:div id="orglevel3"  effect="fold">
                                <div id="orgleve22Div"></div>
                            </sj:div>
                  --></div><!-- class fields end here --> 

</div>
</div>
</div>
</div>
</body>
</html>