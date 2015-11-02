<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<script type="text/javascript" src="<s:url value="/js/helpdesk/lodgefeedbackvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
</head>
<body>
<div class="clear"></div>
		///////<s:property value="%{subDept_DeptLevelName.size}"/>\\\\\\\\
      <s:iterator value="subDept_DeptLevelName" status="counter" begin="1" end="1">
        <span id="mandatoryFields" class="pIds" style="display: none; ">deptId#To Department#D#</span>
        <div class="newColumn">
        	<div class="leftColumn"><b>To&nbsp;Department:</b></div>
       	<div class="rightColumn"><span class="needed"></span>
          	 <s:select 
                        id="deptId" 
				        name="deptId" 
				        list="serviceSubDeptList"
				        headerKey="-1"
				        headerValue="Select Department"
				        cssClass="select"
				        cssStyle="width:82%"
				        onchange="commonSelectAjaxCall2('feedback_type','id','fb_type','dept_subdept',this.value,'module_name','FM','fb_type','ASC','feed_type','Select Feedback Type');"
				        />
           </div>
         </div>
     </s:iterator>
    <s:iterator value="feedbackDDColumns" status="status">
         <div class="newColumn">
      <s:if test="#status.odd">
    	<div class="leftColumn" ><b><s:property value="%{value}"/>&nbsp;:&nbsp;</b></div>
	        <div class="rightColumn">
	             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  <s:select  
                  			  id="%{key}"
                              name="feedbackType"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Type" 
                              cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="commonSelectAjaxCall('feedback_category','id','category_name','fb_type',this.value,'','','category_name','ASC','category','Select Category');"
                              >
                  </s:select>
             </div>
       </s:if>
       <s:else>
	   <div class="leftColumn"><b><s:property value="%{value}"/>&nbsp;:&nbsp;</b></div>
	   <div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select 
                            id="category"
                            name="category" 
                            list="{'No Data'}" 
                            headerKey="-1"
                            headerValue="Select Category" 
                            cssClass="select"
                            cssStyle="width:82%"
                            onchange="commonSelectAjaxCall('feedback_subcategory','id','sub_category_name','category_name',this.value,'','','sub_category_name','ASC','subCategory','Select Sub-Category');">
                </s:select>
       </div>
      </s:else>
      </div>
      </s:iterator>
       
       <div class="newColumn">
       <s:iterator value="feedbackDTimeColumns" status="counter" begin="0" end="0">
       	<span id="mandatoryFields" class="pIds" style="display: none; ">subCategory#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        <s:if test="#counter.count%2 !=0">
         <div class="leftColumn" ><b><s:property value="%{value}"/>:</b></div>
         <div class="rightColumn"><span class="needed"></span>
                 <s:select 
                              id="subCategory"
                              name="subCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getFeedBreifViaAjax(this.value);"
                              >
                 </s:select>
         </div>
        </s:if>
       </s:iterator>
       </div>
       
        <div class="newColumn">
        <span id="mandatoryFields" class="pIds" style="display: none; ">reAssignRemark#Re-Assign Reason#T#an,</span>
         <div class="leftColumn" ><b>Re-Assign Reason:</b></div>
         <div class="rightColumn"><span class="needed"></span>
                 <s:textfield id="reAssignRemark" name="reAssignRemark" cssClass="textField" placeHolder="Enter Re-Assign Reason"></s:textfield>
         </div>
       </div>
	    <center>
	         <sj:dialog loadingText="Please wait..."  id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Online" width="450" cssStyle="overflow:hidden;" height="200" showEffect="slide" hideEffect="explode" >
                         <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalation"></div>
                   </sj:dialog>
              </center>
		<sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
</body>
</html>