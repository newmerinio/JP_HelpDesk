<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
int dateFieldCount=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script src="<s:url value="/js/asset/ProcValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
         
        });
</script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
</head>

<body>
<div class="page_title"><h1>Asset >> Allotment On Repair</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Asset Allotment On Repair" id="oneId"> 
 <s:form id="formone" name="formone" namespace="/view/Over2Cloud/AssetOver2Cloud/Asset" action="assetAllotmentAction" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
			  <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZone" style="float:center; margin-left: 7px"></div>        
              </div></center> 
              <div class="form_menubox">
              <s:iterator value="subDept_DeptLevelName" status="status">
              <s:if test="%{mandatory}">
               		<span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              </s:if>
              <s:if test="#status.odd">
              <div class="inputmain">
              <div class="user_form_text"><s:property value="%{value}"/>:</div>
              <div class="user_form_input inputarea">
              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  <s:select  
                              id="%{key}"
                              name="deptname"
                              list="deptList"
                              headerKey="-1"
                              headerValue="--Select Department--" 
                              cssClass="form_menu_inputselect"
                              onchange="getSubDept(this.value,'subDeptDiv1',%{deptHierarchy},'1','0','0','0','1');"
                              >
                  </s:select>
              </div>
              </s:if>
               <s:else>
              <div class="inputmain">
              <div class="user_form_text1"><s:property value="%{value}"/>:</div>
              <div class="user_form_input inputarea">
              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
              <div id="subDeptDiv1">
                  <s:select 
                              id="%{key}"
                              name="subdept"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="--Select Sub-Department--" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select>
              </div>
              </div>
              </div>
                </s:else>
              </s:iterator>
		     </div>
	 		
	 		<div class="form_menubox">
	 				  <s:iterator value="commonDDMap" begin="1" end="1">
	 				  <s:if test="%{mandatory}">
               		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
	 				  <div class="inputmain">
	                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea">
              		  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <div id="EmpDiv1">
	                  <s:select 
	                              cssClass="form_menu_inputselect"
	                              id="employeeid"
	                              name="employeeid" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                              > 
	                  </s:select>
	                  </div>
	                  </div>
	                  </div>
	                  </s:iterator>
	                  <div class="user_form_text1">Mobile No:</div>
	                  <div class="user_form_input">
	                  <div id="AjaxDivMohb">
	                  <s:textfield name="empMobileNo" id="EmpMobDiv1" disabled="true" cssClass="form_menu_inputtext" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
	                  </div>
	                  </div>
			     </div>
	 		
	 		<div class="form_menubox">
                  <s:iterator value="commonDDMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
               	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{value}"/></div>
                  <div class="user_form_input inputarea">
                  <span class="needed"></span>
                  <s:select 
                              id="%{key}"
                              name="assetid" 
                              list="serialNoList"
                              headerKey="-1"
                              headerValue="--Select Asset Serial No--" 
                              cssClass="form_menu_inputselect"
                              onchange="getAssetDetail(this.value,'1','0','0','0')"
                              >
                  </s:select></div>
                  </div>
                  </s:iterator>
                  <s:iterator value="allotmentColumnMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
               	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <s:if test="%{mandatory}">
               		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  	  <div class="inputmain">
					  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea">
	                  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		<s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" disabled="%{editable}" cssClass="form_menu_inputtext"/>
					  </div>
					  </div>
                  </s:iterator>
            </div>
             <s:iterator value="allotmentColumnMap" begin="1">
             <s:if test="%{mandatory}">
             <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
				 	<%if(temp%2==0){ %>
					  <div class="form_menubox">
					  <div class="inputmain">
	                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea">
	                  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:textfield name="%{key}" id="%{key}"maxlength="50"  placeholder="Enter Data" disabled="%{editable}" cssClass="form_menu_inputtext"/>
	                  </div>
	                  </div>
					<%} else {%>
					  <div class="inputmain">
					  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea">
	                  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" disabled="%{editable}" cssClass="form_menu_inputtext"/>
					  </div>
					  </div>
					  </div>
				 	<%}
				  		temp++;
				 	%>
				 </s:iterator>
				 <s:iterator value="dateColumnMap">
				 <s:if test="%{mandatory}">
               	 <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
							<%if(dateFieldCount%2==0){ %>
							<div class="form_menubox">
								<div class="inputmain">
	                  			<div class="user_form_text"><s:property value="%{value}"/>:</div>
	                  			<div class="user_form_input inputarea">
	                  			<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<sj:datepicker name="%{key}" id="%{key}" readonly="true" showOn="focus" minDate="0" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/>
								</div>
								</div>
							   <%} else {%>
							    <div class="inputmain">
								<div class="user_form_text1"><s:property value="%{value}"/>:</div>
								<div class="user_form_input inputarea">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<s:if test="key=='issueat'">
									<sj:datepicker name="%{key}" id="%{key}" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridMinute="10" cssClass="form_menu_inputtext" placeholder="Select Time"/>
								</s:if>
								<s:else>
									<sj:datepicker name="%{key}" id="%{key}" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridMinute="10" cssClass="form_menu_inputtext" placeholder="Select Time"/>
								</s:else>
								</div>
								</div>
							</div>
							<%}
								dateFieldCount++;
				 			%>
				</s:iterator>
				 
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Allot On Repair  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
               </s:form>
               	 </sj:accordionItem>
</sj:accordion>
</div>
</div>
</div>
</div>
</body>
</html>
