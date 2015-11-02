<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
</style>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/pcrm/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>

<script type="text/javascript">
$.subscribe('level1', function(event, data) {
	$("#addDialog").dialog('open');
	setTimeout(function() {
		closeAdd();
	}, 4000);
	reset("formone");
});
function closeAdd() {
	$("#addDialog").dialog('close');
	backToView();
}

function backToView() {
	$("#data_part")
			.html(
					"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
				type : "post",
				url : "view/Over2Cloud/patientCRMMaster/viewConfigureLocationHeader.action",
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
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		buttons="{ 
    		'Close':function() { closeAdd() } 
    		}"
		closeTopics="closeEffectDialog" width="350" height="250">
		<sj:div id="level123"></sj:div>
	</sj:dialog>



<sj:dialog id="completionResult" showEffect="slide" hideEffect="explode"
	autoOpen="false" title="Confirmation Message" modal="true" width="400"
	height="150" draggable="true" resizable="true"
	buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}">
	<sj:div id="resultTarget" effect="fold">
	</sj:div>
	
</sj:dialog>
<div class="clear"></div>
<div class="list-icon"><!-- <div class="head">Add Prospective Associate</div> -->
<div class="head">Configure Location</div>
<div class="head"><img alt="" src="images/forward.png"
	style="margin-top: 60%; float: left;"></div>
<div class="head">Add</div>
</div>

<div style="float: left; padding: 20px 1%; width: 98%;">

<br>

<sj:accordion id="accordion" autoHeight="false">
	<!-- Country Data ***************************************************************************** -->
		<sj:accordionItem title="Configure Country" id="oneId">
	<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone1" name="formone1"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addCountry" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone1" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="packageFields1" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             
				         <s:if test="%{key == 'country_name'}">
				         	<s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv1('100')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
						</s:iterator>
						
						
						<div id="extraDiv1">
                 <s:iterator begin="100" end="109" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
                             <s:iterator value="packageFields1" status="counter" begin="0">
                      <%--        <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/><s:property value="%{#typeIndx}" />#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if> --%>
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                 <s:if test="%{mandatory}">
                                <span class="needed" style="margin-left: -5px;"></span>
                                </s:if>
                                <div class="rightColumn1">
                            
				        <s:if test="%{key == 'country_name'}">
				         	 <s:textfield name="%{key}"  id="%{key}%{#typeIndx}"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" placeholder="Enter Data" />
                                   
				       <div style="">
                            <s:if test="#typeIndx==110">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv1('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv1('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv1('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>      
                                    </div>
                                    
                            
                            </div>
                            
                            </s:iterator>
                        </div>
                    </s:iterator>
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
									effectDuration="5000" button="true" onBeforeTopics="validate1"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone1');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
		</sj:accordionItem>
	
	<!--Country State Mapping *********************************************************************** -->
	<sj:accordionItem title="Configure State" id="twoId">

	<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone2" name="formone2"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addState" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone2" style="float: center; margin-left: 7px;"></div>
							</div>
						</center>

				<s:iterator value="packageFields2" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds2" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             
				         <s:if test="%{key == 'state_name'}">
				         	<s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv2('200')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:elseif test="%{key == 'country_code'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="countryMap"
                                      headerKey="-1"
                                      headerValue="Select Country"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
                           </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
						</s:iterator>
						
						
						<div id="extraDiv2">
                 <s:iterator begin="200" end="209" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
                             <s:iterator value="packageFields2" status="counter" begin="1">
                      <%--        <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds2" style="display: none; "><s:property value="%{key}"/><s:property value="%{#typeIndx}" />#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if> --%>
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                 <s:if test="%{mandatory}">
                                <span class="needed" style="margin-left: -5px;"></span>
                                </s:if>
                                <div class="rightColumn1">
                            
				        <s:if test="%{key == 'state_name'}">
				         	 <s:textfield name="%{key}"  id="%{key}%{#typeIndx}"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" placeholder="Enter Data" />
				       <div style="">
                            <s:if test="#typeIndx==210">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv2('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv2('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv2('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>      
                                    </div>
                                    
                            
                            </div>
                            
                            </s:iterator>
                        </div>
                    </s:iterator>
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
									effectDuration="5000" button="true" onBeforeTopics="validate2"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone2');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
			</div>



	</sj:accordionItem>


	<!-- State City Data ************************************************************************ -->
	<sj:accordionItem title="Configure City" id="threeId">
<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone3" name="formone3"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addCity" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone3" style="float: center; margin-left: 7px;"></div>
							</div>
						</center>

				<s:iterator value="packageFields3" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds3" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             
				         <s:if test="%{key == 'city_name'}">
				         	<s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv3('300')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:elseif test="%{key == 'code_country'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="countryMap"
                                      headerKey="-1"
                                      headerValue="Select Country"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="commonSelectAjaxCall2('state','id','state_name','country_code',this.value,'','','state_name','ASC','name_state','--Select State--');"
                                      >
                          </s:select>
                           </s:elseif>
                             <s:elseif test="%{key == 'name_state'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="stateMap"
                                      headerKey="-1"
                                      headerValue="Select State"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
                           </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
						</s:iterator>
						
						
						<div id="extraDiv3">
                 <s:iterator begin="300" end="309" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
                             <s:iterator value="packageFields3" status="counter" begin="2">
                      <%--        <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds3" style="display: none; "><s:property value="%{key}"/><s:property value="%{#typeIndx}" />#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if> --%>
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                 <s:if test="%{mandatory}">
                                <span class="needed" style="margin-left: -5px;"></span>
                                </s:if>
                                <div class="rightColumn1">
                            
				        <s:if test="%{key == 'city_name'}">
				         	 <s:textfield name="%{key}"  id="%{key}%{#typeIndx}"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" placeholder="Enter Data" />
				       <div style="">
                            <s:if test="#typeIndx==310">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv3('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv3('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv3('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>      
                                    </div>
                                    
                            
                            </div>
                            
                            </s:iterator>
                        </div>
                    </s:iterator>
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
									effectDuration="5000" button="true" onBeforeTopics="validate3"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone3');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
			</div>





	</sj:accordionItem>
</sj:accordion></div>
</body>

</html>