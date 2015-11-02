<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
 {
   setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
   //setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
   setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton(); }, 4000);
	$('#completionResult').dialog('open');
 });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	// viewGroup();
}
function viewGroup() 
{
	 $('#abc').dialog('close');
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAccountsView.action",
 	    success : function(subdeptdata) 
 	   {
 			$("#"+"data_part").html(subdeptdata);
 	   },
 	   error: function() 
 	   {
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
          ><sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
          <div id="complTarget"></div>
</sj:dialog>
<div class="list-icon">
	<div class="head">Reimbursement for </div>
	<div class="head">
		<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;"> <s:property value="#parameters.name"/> for Month  <s:property value="#parameters.for_month"/> till  <s:property value="#parameters.date"/> , Balance <s:property value="#parameters.bal"/>
	</div>
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addAcountDetail" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden name="given_to_contact_id" value="%{emp_id}"></s:hidden>
	            <s:iterator value="dropDown" status="status" begin="0" end="0">
	                 <s:if test="%{key=='payment_for'}">
	                   <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}" 
			                                      list="{'Advance','Balance Pending'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:if>
                 </s:iterator>
                 <s:iterator value="textBox" status="counter"  begin="0" end="0">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                 <s:iterator value="dateField" status="counter" begin="0" end="0">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             	 <sj:datepicker id="%{key}"  name="%{key}" value="today"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			 <sj:datepicker id="%{key}"  name="%{key}"  value="today"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                <s:iterator value="dropDown" status="counter" >
	                 <s:if test="%{key=='mode'}">
	                   <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}" 
			                                      list="{'Cheque','Cash','Demand Draft','Online'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:if>
	                 <s:elseif test="%{key=='given_by'}">
	                   <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
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
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                               </div>
		                  </div>
	                 </s:elseif>
                 </s:iterator>
         
                     <div class="newColumn">
		                                <div class="leftColumn1">Authorized By:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             			<s:textfield id="AuthorizedBy" readonly="true" maxlength="50" value="%{#parameters.by}" cssClass="textField" placeholder="Enter Data"/>              
		                               </div>
		                  </div>
		                    <div class="newColumn">
		                                <div class="leftColumn1">Authorized Date:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             	 <s:textfield id="AuthorizedDate" readonly="true"  maxlength="50" value="%{#parameters.date}" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
		                  
                   <s:iterator value="textBox" status="counter"  begin="1" >
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                  <s:iterator value="file" status="counter"  >
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:file name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:file name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
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
                             effectOptions="{ color : '#FFFFFF'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -41px;"
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

