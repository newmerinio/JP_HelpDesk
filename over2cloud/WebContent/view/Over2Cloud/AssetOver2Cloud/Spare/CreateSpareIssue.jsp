<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script src="<s:url value="/js/asset/SpareValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('spare', function(event,data)
	       {
	         setTimeout(function(){ $("#spare1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#spare1Div").fadeOut(); }, 4000);
	       });

function viewIsse() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createIssueViewPage.action?modifyFlag=0&deleteFlag=0",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
</script>

</head>
	<div class="list-icon">
	<div class="head">Spare Issue Detail >> Registration</div>
</div>

 <div style=" float:left; padding:5px 0%; width:100%;">
	 		   	     <div class="border">
		<s:form id="formone" name="formone" action="spareIssueAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
            <center>
            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:center; margin-left: 7px"></div>        
            </div>
            </center> 
            
               <s:iterator value="dateColumnMap" status="status">
               <s:if test="%{mandatory}">
               <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
               </s:if>
               		<s:if test="#status.odd==true">
               			 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               				    <sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
               			        </div>
               			        </div>
					</s:if>
					<s:else>
						 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               				    <sj:datepicker name="%{key}" id="%{key}" changeMonth="true" readonly="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/>
               			        </div>
               			</div>
					</s:else>
				</s:iterator>
                 
                  
               <s:iterator value="subDept_DeptLevelName" status="status">
               <s:if test="%{mandatory}">
               <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
               </s:if>
               <s:if test="#status.odd==true">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select  
                                 id="%{key}"
                                 name="deptname"
                                 list="deptList"
                                 headerKey="-1"
                                 headerValue="--Select Department--" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                 onchange="getSubDept(this.value,'subDeptDiv1',%{deptHierarchy},'1','0','0','0','0');"
                                   >
                                 </s:select>
                                 </div>
               </div>  
               </s:if>
               <s:else>
            <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <div id="subDeptDiv1">
                                <s:select 
                                 id="%{key}"
                                 name="subdept"
                                 list="{'No Data'}"
                                 headerKey="-1"
                                 headerValue="--Select Sub-Department--" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                   >
                                </s:select>
                                </div>
                                </div>
               </div>
               </s:else>
               </s:iterator>
		   
   
	  			 <!--<div class="form_menubox">
	                  <div class="user_form_text">Employee Name:*</div>
	                  <div class="user_form_input">
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
	                 
	                  <div class="user_form_text1">Mobile No:*</div>
	                  <div class="user_form_input">
	                  <div id="AjaxDivMohb">
	                  <s:textfield name="empMobileNo" id="EmpMobDiv1" cssClass="form_menu_inputtext" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
	                  </div>
	                  </div>
			     </div>
                  -->
                  
                  <s:if test="vendorActive">
                  <s:iterator value="commonDDMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                              id="spare_category"
                              list="spareCatgNoList"
                              headerKey="-1"
                              headerValue="--Select Spare Category--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getNonConsumeSpare(this.value,'spareNameDiv')"
                                 >
                                </s:select>
                               </div>
                  </div>
                  </s:iterator>
                  </s:if>
                  
                  <s:else>
                  <s:iterator value="commonDDMap" begin="0" end="0">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                 id="spare_name"
                                 name="spare_name" 
                                 list="spareNameMap"
                                 headerKey="-1"
                                 headerValue="--Select Spare Name--" 
                                 cssClass="select"
                                   cssStyle="width:82%"
                                       >
                  		        </s:select>	
                                </div>
                  </div>
                  </s:iterator>
                  </s:else>
                  <s:if test="usagesTypeActive && vendorActive">
                  <s:iterator value="commonDDMap" begin="1" end="1">
                  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <div id="spareNameDiv">
                                <s:select 
                                 id="spare_name"
                                 list="{'No Data'}"
                                 headerKey="-1"
                                 headerValue="--Select Spare Name--" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                     >
                                </s:select>
                                </div>
                                </div>
                  </div>
                  </s:iterator>
                  </s:if>
                  
				  <s:iterator value="spareIssueColumnMap" status="status">
				  <s:if test="%{mandatory}">
            	  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
				  <s:if test="#status.odd==true">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:if test="key=='no_issue'">
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="textField" onblur="getAvailableSpareViaAjax(this.value)"/>
	                            <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	          	                <div id="spareStatus"></div><s:hidden id="status"></s:hidden>
	                             </s:if>
	              <s:else>
	                             <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="textField"/>
	              </s:else>
	                               </div>
	              </div>  						
	              </s:if>
				  <s:else>
			     <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                <s:if test="key=='no_issue'">
				                <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="textField" onblur="getAvailableSpareViaAjax(this.value)"/>
				                <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				          	    <div id="spareStatus"></div>
			           		    <s:hidden id="status"></s:hidden>
				                </s:if>
				                <s:else>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="textField"/>
	                            </s:else>
	                             </div>
	              </div>
				  </s:else>
				  </s:iterator>
				
				 <div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="buttonStyle" style="text-align: center;">
			         <sj:submit 
	                        targets="spare1" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="spare"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
			          <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewIsse();"
						cssStyle="margin-left: 5px;margin-top: -0px;"
						>
					  	Cancel
						</sj:a>
				    </div>
				    	<sj:div id="spare1"  effect="fold">
                          <div id="spare1Div"></div>
                       </sj:div>
			</s:form>
</div>
</div>

</html>