

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<style>
.radioClass
{
    color: #ffffff;
}

</style>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}



function viewGroup() {
    var conP = "<%=request.getContextPath()%>";
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
        url : "view/Over2Cloud/hr/beforeGroupHeaderView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
function checkContactType(contact)
{
    
    var mapId=$("#regLevel").val();
    
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/hr/checkContactTypeView.action?selectedorgId="+mapId+"&contact="+contact,
        success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata);
       setTimeout(function(){ $("#result").fadeIn(); }, 10);
       setTimeout(function(){ $("#result").fadeOut(); }, 5000);
    },
       error: function() {
            alert("error");
        }
     });
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
</script>
</head>
<body>
<div class="list-icon">
     <div class="head">Pre Visit  Request</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="form2" name="form2" namespace="/view/Over2Cloud/leaveManagement" action="leavePreVisitRequestAddRecord" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
                           <center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
                  <s:iterator value="contactDD" status="counter">
                  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                  <s:if test="#counter.count%2 !=0">
                 <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                      <span class="needed"></span>
                  </s:if>
                      <s:select 
                                      id="visit"
                                      name="visit" 
                                     list="#{'Prospective':'Prospective Client','Existing Client':'Existing Client'}" 
                                      headerKey="-1"
                                      headerValue="Select Mapped Office" 
                                      cssClass="textField"
                                      >
                          </s:select>
                   
                    </div>
                  </div>
                  </s:if>
                  <s:else>
                  <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                      <span class="needed">
                      </span>
                  </s:if>
                  <s:if test="%{key=='Visiting'}">
                      <s:select 
                                      id="Visiting"
                                      name="Visiting" 
                                      list="officeMap"
                                      headerKey="-1"
                                      headerValue="Select Mapped Office" 
                                      cssClass="textField"
                                      >
                          </s:select>
                  </s:if>
                  </div>
                  </div>
                 </s:else>
                 </s:iterator>
            
 		  <s:iterator value="requestColumnDate" status="counter" >
					     <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
				     <s:if test="#counter.count%2 != 0">
				    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" id="%{key}" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select From date"/>
	                           </div>
	                 </div>
					 </s:if>
					 <s:else>
				     <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="%{key}" id="%{key}" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To date"/>
	                            </div>
	                  </div> 
					 </s:else>
		     		 </s:iterator>	
                       
                 <s:iterator value="requestColumnTime" status="counter">
					        <s:if test="%{mandatory}">
                            <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                            </s:if>
					  <s:if test="#counter.count%2 != 0">
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker timepicker="true" timepickerOnly="true" readonly="true" name="%{key}" id="%{key}"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select From Time"/>
	                            </div>
					  </div>
					  </s:if>
					  <s:else>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if><sj:datepicker timepicker="true" readonly="true" timepickerOnly="true" name="%{key}" id="%{key}"   timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" showOn="focus"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To Time"/></div>
                      </div>	
					  </s:else>
		     		</s:iterator>
	
              <s:iterator value="visitTextBox" status="counter">
                  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                  <s:if test="#counter.count%2 !=0">
                 <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                                      <span class="needed">
                                      </span>
                                  </s:if>
                              <s:textfield name="%{key}" id="%{key}" maxlength="200" onblur="toTitleCase(this.value,this.id)" onchange="checkContactType(this.value);" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>                
                              <div id="result" style="display: block;"  ></div>
                                </div>
                  </div>
                  </s:if>
                  <s:else>
                  <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                      <span class="needed">
                      </span>
                  </s:if>
                                <s:textfield name="%{key}" id="%{key}" maxlength="200"  onblur="toTitleCase(this.value,this.id)" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
                  </div>
                  </div>
                 </s:else>
                 </s:iterator>
             
                
            
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="level123" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                          
 />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>