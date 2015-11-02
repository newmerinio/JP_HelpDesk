<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>

<script type="text/javascript" src="<s:url value="/js/pcrm/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
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
					url : "view/Over2Cloud/patientCRMMaster/viewActivityTypeMasterHeader.action",
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
		closeTopics="closeEffectDialog" width="350" height="150">
		<sj:div id="level123"></sj:div>
	</sj:dialog>

	<div class="middle-content">

		<div class="list-icon">
			<div class="head">Activity Type</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
		<sj:accordion id="accordion" autoHeight="false">	
			<!--Add Activity Data ***************************************************************************** -->
		<sj:accordionItem title="Add Activity Type" id="oneId">
			
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone" name="formone"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addActivityTypeMasterDetails" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone1" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="packageFields" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				         <s:if test="%{key == 'act_brief'}">
				         	<s:textfield  name="%{key}" id="%{key}"  value="%{colType.contains('D')}" maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv1('100')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:elseif test="%{key == 'act_rel_type'}">
				         	<s:select id="%{key}" name="%{key}" list="relMap" headerKey="-1" headerValue="Select Relationship Type" 
				         	onchange="commonSelectAjaxCall2('relationship_sub_type','id','rel_subtype','rel_type',this.value,'','','rel_subtype','ASC','act_rel_subtype','--Select Relationship Sub Type--');" 
				         	cssClass="select" 
									cssStyle="width:82%"> </s:select>
				         </s:elseif>
				           <s:elseif test="%{key == 'act_rel_subtype'}">
				         	<s:select id="%{key}" name="%{key}" list="#{1:'Lead',2:'Prospect',3:'Closed'}" headerKey="-1" headerValue="Select Activity Type" cssClass="select" 
									cssStyle="width:82%"> </s:select>
				         </s:elseif>
				           <s:elseif test="%{key == 'act_stage'}">
				         	<s:select id="%{key}" name="%{key}" list="stageMap" headerKey="-1" headerValue="Select Stage" cssClass="select" 
									cssStyle="width:82%"> </s:select>
				         </s:elseif>
				          <s:elseif test="%{key == 'act_type'}">
				         	<s:select id="%{key}" name="%{key}" list="#{'Routine':'Routine For Activity ','Event':'Event For Sales'}" headerKey="-1" headerValue="Select Relationship Type" 
				         	cssClass="select"  cssStyle="width:82%"> </s:select>
				         </s:elseif>
				         <s:elseif test="%{key == 'act_kpi'}">
				         	<s:select id="%{key}" name="%{key}" list="kpiMap" headerKey="-1" headerValue="Select Stage" cssClass="select" 
									cssStyle="width:82%"> </s:select>
				         </s:elseif>      
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				  
				    </div>
			        </div>
			        
						</s:iterator>
						
						
						<div id="extraDiv">
                 <s:iterator begin="100" end="109" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
                             <s:iterator value="packageFields" status="counter" begin="4">
                      <%--        <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/><s:property value="%{#typeIndx}" />#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if> --%>
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                 <s:if test="%{mandatory}">
                                <span class="needed" style="margin-left: -5px;"></span>
                                </s:if>
                                <div class="rightColumn1">
                            
				         <s:if test="%{key == 'act_brief'}">
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
				         <s:elseif test="%{key == 'act_kpi'}">
				         	<s:select id="%{key}" name="%{key}" list="kpiMap" headerKey="-1" headerValue="Select KPI" cssClass="select" 
									cssStyle="width:82%"> </s:select>
				         </s:elseif>      
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
				
		</sj:accordionItem>		
		
		<sj:accordionItem title="Add Activity Reason" id="twoId">
		
		
	<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone2" name="formone2"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addActivityReason" theme="css_xhtml" method="post"
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
				             
				         <s:if test="%{key == 'ract_brief'}">
				         	<s:textfield  name="%{key}" id="%{key}"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv2('200')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				         </s:if>
				         <s:elseif test="%{key == 'ract_name'}">
				         	<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="activityMap"
                                      headerKey="-1"
                                      headerValue="Select Activity Type"
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
                            
				        <s:if test="%{key == 'ract_brief'}">
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
		
		</sj:accordion>
			</div>
		</div>
	</div>

</body>
</html>