<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
customBasepath="%{contextz}"/>

<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url
value="/js/communication/CommuincationBlackList.js"/>"></script>

<SCRIPT type="text/javascript">
function showtime(val,div){
var p=document.getElementById(div);
if(val=="Time"){
   p.style.display='block';

  }else{
   p.style.display='none';
  }
}
$.subscribe('result', function(event,data)
               {
                        //document.getElementById("indicator1").style.display="none";
                 setTimeout(function(){ $("#resultblacklist").fadeIn(); }, 10);
                 setTimeout(function(){ $("#resultblacklist").fadeOut(); }, 4000);
               });

function resetForm(formId)
{
        $('#'+formId).trigger("reset");
}

</script>
</head>
<body>
<div class="list-icon">
        <div class="head">Exclusion</div><div class="head"><img alt=""
src="images/forward.png" style="margin-top:50%; float:
left;"></div><div class="head">Add</div>

</div>
        <div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formtwo" name="formtwo"  namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertDataBlackList" theme="css_xhtml"  method="post" >
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  			<div id="errZone" style="float:left; margin-left: 7px"></div>
        </div>
             <s:iterator value="blackListFor" status="status">

                                    <s:if test="%{key=='module_name'}">
                                     <s:if test="%{mandatory}">
				                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                					 </s:if>
                           				<div class="newColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn1">    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                      						 <s:select 
		                                        list="appDetails"
		                                         cssStyle="width:218px"
		                                        headerKey="-1"
		                                        headerValue="Select Module Name"
		                                        name="%{key}"
		                                        id="%{key}" 
		                                        cssClass="select"/>

                 						 </div></div>
                                    </s:if>
                                    <s:if test="%{key=='blacklist_for'}">
                                     <s:if test="%{mandatory}">
				                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 					</s:if>
                                             <div class="newColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn1">
                                                <s:if test="%{mandatory}"><span class="needed"></span>  </s:if>
                                                 <s:select  cssStyle="width:218px"
			                                        list="#{'Mobile Number':' Mobile','EmailID':' Email','Mobile No & Email':' Both'}"
			                                        headerKey="-1"
		                                            headerValue="Select Black List For"
			                                        name="%{key}"
			                                        id="%{key}" cssClass="select"/>
			                                      
                                                </div></div>
                                   </s:if>

                      </s:iterator>
   							<s:iterator value="blackListFor" status="status">
                                    <s:if test="%{key=='contact_name'}">
                                     <s:if test="%{mandatory}">
				                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                					 </s:if>
                					     <div class="newColumn">
                                       <span id="mandatoryFields" class="pIds" style="display: none; ">department#Contact Sub Type#D,<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
                                            <div class="leftColumn1">Contact Sub Type:</div>
                                            <div class="rightColumn1"><span class="needed"></span>
                                         <s:select
						                       id="department"
						                       list="deptMap"
						                       headerKey="-1"
						                       headerValue="Select Contact Sub Type"
						                       cssClass="select"
						                       cssStyle="width:218px"
						                       onchange="fetchContact(this.value,'contact_name');"
						                       />
						
					                  </div>
					                 </div>
                					 
                           				<div class="newColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn1">    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                      						 <s:select 
                      						     cssStyle="width:218px"
		                                        list="{'No data'}"
		                                        headerKey="-1"
		                                        headerValue="Select Contact Name"
		                                        name="%{key}"
		                                        id="%{key}"
		                                        cssClass="select" 
		                                        />

                 						 </div></div>
                                    </s:if>
                                 

                      </s:iterator>
                                        
			           <s:iterator value="blacklistNameee" status="counter">
			                  
			                          <s:if test="%{key=='reason'}">
						                           <s:if test="%{mandatory}">
						                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						                 </s:if>
				                          		 			<div class="newColumn">
			                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
			                                                <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span>     </s:if>
			                                           <s:textfield name="%{key}" id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			                                             </div></div>
			                                           
			                            </s:if>
			                            
			              </s:iterator>
                              	<s:iterator value="blackListFor" status="status">
                                    <s:if test="%{key=='duration'}">
                                     <s:if test="%{mandatory}">
				                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                					 </s:if>
                					   
                           				<div class="newColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn1">    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                      						 <s:select 
                      						     cssStyle="width:218px"
		                                          list="#{'Forever':'Forever','Period':'Period'}"
		                                        headerKey="-1"
		                                        headerValue="Select period"
		                                        name="%{key}"
		                                        id="%{key}" 
		                                        cssClass="select"
		                                        onchange="changeDateRange(this.value,'dateDiv')"
		                                        />

                 						 </div></div>
                                    </s:if>
                      			</s:iterator>
                      			<div class="clear"></div>
                      			 <div id=dateDiv style="display: none ;" >
			                       <s:iterator value="blacklistNameee" status="counter">
			                          <s:if test="%{key=='from_date'}">
						                           <s:if test="%{mandatory}">
						                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						                 </s:if>
				                          		 			<div class="newColumn">
			                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
			                                                <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span>     </s:if>
			                                     <sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField"   cssStyle="margin:0px 0px 10px 0px;"/>
			                                             </div></div>
			                                           
			                            </s:if>
			                                  <s:elseif test="%{key=='to_date'}">
						                           <s:if test="%{mandatory}">
						                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						                 </s:if>
				                          		 			<div class="newColumn">
			                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
			                                                <div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span>     </s:if>
			                                            <sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField"   cssStyle="margin:0px 0px 10px 0px;"/>
			                                             </div></div>
			                                           
			                            </s:elseif>
			                            
			              </s:iterator>
                                      </div>

             <div class="newColumn">
              <div class="leftColumn1" style="margin-top:-8px;margin-left: -100px">SMS:</div>
             <div class="rightColumn1">
                    <s:checkbox id="smsNotification" name="smsNotification" value="true" ></s:checkbox>
             </div>
             </div>

             <div class="newColumn">
             <div class="leftColumn1" style="margin-top:  -8px;margin-left: -164px" >Email:</div>
             <div class="rightColumn1">
                    <s:checkbox id="emailNotification" name="emailNotification"></s:checkbox>
             </div>
             </div>

<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url
value="/images/indicator.gif"/>" alt="Loading..."
style="display:none"/></center>
        <div class="type-button" style="margin-left: -200px;">
<center>
       <sj:submit
           targets="blacklistresult"
                       clearForm="true"
                       value="Save"
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
         />



         <sj:a
                                        href="#"
                                button="true"
                                onclick="resetForm('formtwo');"
                                                cssStyle="margin-top: -28px;"                                           >
                                                Reset
                   </sj:a>

                   <sj:a
                                        href="#"
                                button="true"
                                onclick="viewBlackList();"
                                                cssStyle="margin-right: -137px;margin-top: -28px;"
                                                >
                                                Back
                   </sj:a>

         <sj:div id="resultblacklist"  effect="fold">
                    <div id="blacklistresult"></div>
               </sj:div>

      </center>

   </div>

</s:form>
</div>
</div>
</div></div>

</body>
</html>