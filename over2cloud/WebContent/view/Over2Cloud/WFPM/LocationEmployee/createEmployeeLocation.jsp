<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/LocationMap/location.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
 {
   setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
   //setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
   setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); }, 4000);
	$('#completionResult').dialog('open');
 });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewGroup();
}
function viewGroup() 
{
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
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
$(document).ready(function()
		{
		$("#employee").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		$("#country").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		$("#state").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		$("#city").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		$("#territory").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		});
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
          <div id="complTarget"></div>
</sj:dialog>
<div class="list-icon">
     <div class="head">Map Location With Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/EmployeeLocation" action="addMappingLocation" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
              
                <s:iterator value="dropDown" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="%{key=='dept'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      list="deptMap"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      onchange="fetchEmployeeVal(this.value,'employeeDiv')"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:elseif test="%{key=='employee'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                  <div class="rightColumn1">
				                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                             	<div id="employeeDiv">
				                             		 <s:select 
					                                      id="%{key}"
					                                      name="%{key}"
					                                      list="{'No Data'}"
					                                      multiple="true"
					                                      headerKey="-1"
					                                      cssClass="textField"
					                                     cssStyle="width:5%"
					                                      onchange="fetchCity(this.value,'city')"
					                                      >
		                                            </s:select> 
		                                        </div>                   
		                                  </div>
		                  </div>
	                 </s:elseif>
	                 <s:elseif test="%{key=='country'}">
	                 <div class="clear"></div>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="commonMap"
			                                      headerKey="-1"
			                                      multiple="true"
			                                      cssClass="textField"
			                                      cssStyle="width:5%"
			                                      onchange="fetchStateMultiple('country','stateDiv')"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:elseif>
	                 <s:elseif test="%{key=='state'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             <div id="stateDiv">
			                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="{'No Data'}"
			                                      multiple="true"
			                                      headerKey="-1"
			                                      cssClass="textField"
			                                       cssStyle="width:5%"
			                                      >
                                            </s:select>   
			                             </div>             
		                                </div>
		                  </div>
	                 </s:elseif>
	                 <s:elseif test="%{key=='city'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                                  <div id="cityDiv">
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="{'No Data'}"
			                                      multiple="true"
			                                      headerKey="-1"
			                                      cssClass="textField"
			                                      cssStyle="width:5%"
			                                      >
                                            </s:select>     
                                            </div>           
		                                </div>
		                  </div>
	                 </s:elseif>
	                 <s:elseif test="%{key=='territory'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                                  <div id="terrDiv">
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="{'No Data'}"
			                                      multiple="true"
			                                      headerKey="-1"
			                                      cssClass="textField"
			                                       cssStyle="width:5%"
			                                      >
                                            </s:select>    
                                            </div>            
		                                </div>
		                  </div>
	                 </s:elseif>
                 </s:iterator>
                   
				
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="complTarget" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onCompleteTopics="level1"
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
             </s:form>          
</div>
</div>
</body>
</html>